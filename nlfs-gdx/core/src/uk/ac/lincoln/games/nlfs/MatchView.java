package uk.ac.lincoln.games.nlfs;

import java.util.ArrayList;
import java.util.Collections;

import uk.ac.lincoln.games.nlfs.logic.GameState;
import uk.ac.lincoln.games.nlfs.logic.Goal;
import uk.ac.lincoln.games.nlfs.logic.Match;
import uk.ac.lincoln.games.nlfs.logic.MatchEvent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Display the ongoing match and calculate the results
 * @author bkirman
 *
 */
public class MatchView extends BaseScreen{
	private Match match;
	private TextButton button;
	private Label home_label,away_label,home_score_label,away_score_label,clock_label;
	private ScrollPane action_pane;
	private VerticalGroup action_group;
	private ArrayList<Goal> goals;
	//private int mins_in_match;
	private int current_minute, current_home, current_away;
	public static float SIMULATION_S_PER_MIN = 0.4f;
	private enum MatchState {PRE,H1,HT,H2,FT};
	private MatchState current_state;
	
	/**
	 * Schedulable task that runs a minute worth of game.
	 * @author bkirman
	 *
	 */
	private class RunMinute extends Task {
		@Override
		public void run() {
			if(current_state==MatchState.H2&&current_minute>match.result.match_length) {
				this.cancel();
				current_state = MatchState.FT;
				button.setText("Leave Match");
				button.setDisabled(false);
				action_group.addActor(new Label("Full Time at "+match.home.stadium ,Assets.skin,"score_report"));
				
				return;
			}
			if(current_state==MatchState.H1&&current_minute>(match.result.match_length/2)) {
				this.cancel();
				current_state = MatchState.HT;
				button.setText("Second Half");
				button.setDisabled(false);
				action_group.addActor(new Label("Half Time at "+match.home.stadium ,Assets.skin,"score_report"));
				
				return;
			}
			//resolve a minute's worth of match time
			
			
			clock_label.setText(" "+String.valueOf(current_minute)+" ");
			for(MatchEvent me:match.result.match_events) {
				if(current_minute==me.minute) {
					action_group.addActor(new Label(String.valueOf(current_minute)+": "+me.getDescription() ,Assets.skin,"event_report"));
				}
			}
			for(Goal g : goals) {
				if(g.time==current_minute){
					if(g.scorer.team==match.home) {
						current_home++;
						home_score_label.setText(" "+String.valueOf(current_home)+" ");
					}
					else {
						current_away++;
						away_score_label.setText(" "+String.valueOf(current_away)+" ");
					}
					
					//add text
					HorizontalGroup hg = new HorizontalGroup();
					Image ball = new Image(Assets.skin,"football");
					hg.addActor(ball);
					hg.addActor(new Label(String.valueOf(g.time)+": Goal for "+g.scorer.team.name ,Assets.skin,"score_report"));
					action_group.addActor(hg);
					Label scorer = new Label(g.scorer.getName()+" ("+g.scorer.getPosition().toString()+")",Assets.skin,"scorer_report");
					scorer.setWidth(300);
					scorer.setAlignment(Align.right);
					action_group.addActor(scorer);
					
				}
					
			}
			current_minute++;
			action_pane.fling(0f, 0f, 90f);
		}
	}
	
	
	
	public MatchView (final NonLeague game) {
		super(game);
		//NB remember none of this stuff is in memory yet
		home_label = new Label("[HOME TEAM]",Assets.skin,"teamname");
		away_label = new Label("[AWAY TEAM]",Assets.skin,"teamname");
		home_score_label = new Label(" 0 ",Assets.skin,"score");
		away_score_label = new Label(" 0 ",Assets.skin,"score");
		
		
		table.add(home_label).expandX().fillX().left();
		table.add(home_score_label).right().padLeft(2);
		table.row().padTop(2);
		table.add(away_label).expandX().fillX().left();
		table.add(away_score_label).right().padLeft(2);
		home_score_label.getStyle().background = Assets.skin.newDrawable("base",Color.WHITE); //TODO should be properly assigned in skin
		table.row().padTop(2);
		Table table2 = new Table();
		Image stopwatch = new Image(Assets.skin,"stopwatch");
		//stopwatch.scaleBy(-0.5f);
		table2.add(stopwatch).maxSize(17,25);
		clock_label = new Label(" 0 ",Assets.skin,"timer");
		clock_label.getStyle().background = Assets.skin.newDrawable("base",Color.WHITE); //TODO should be properly assigned in skin
		table2.add(clock_label);
		table.add(table2).colspan(2);
		table.row().padTop(2);
		
		
		Table action_table = new Table();
		action_table.setSize(300, 500);
		action_table.setDebug(true);
		action_table.setBackground(Assets.skin.getDrawable("darken"));
		
		action_group = new VerticalGroup();
		action_group.align(Align.left);
		action_pane = new ScrollPane(action_group);
		action_pane.setScrollingDisabled(true, false);
		
		
		action_pane.setSize(300, 500);
		
		action_table.add(action_pane).expand().align(Align.left);
		table.add(action_table).colspan(2).prefSize(300, 500);
		
		table.row();
		button = new TextButton("Leave Match", Assets.skin);	

		table.add(button).width(200).height(40).colspan(2);
		table.row();
		button.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				if(current_state == MatchState.PRE || current_state == MatchState.HT) {
					button.setDisabled(true);
					button.setText("Please Wait");
					Timer.schedule(new RunMinute(), SIMULATION_S_PER_MIN, SIMULATION_S_PER_MIN);
					if(current_state==MatchState.PRE)
						current_state = MatchState.H1;
					else
						current_state = MatchState.H2;
				}
				
				if(current_state == MatchState.FT) {
					game.postmatch_screen.setResult(match.result);
					game.changeScreen(game.postmatch_screen);
				}
		}
		});
	}
	
	public void update() {
		current_minute = 0;
		current_home = 0;
		current_away = 0;
		//mins_in_match = 94;
		match = GameState.league.findTeamsNextFixture(GameState.player_team);
		GameState.league.playWeek();
		
		
		goals = new ArrayList<Goal>();
		goals.addAll(match.result.home_goals);
		goals.addAll(match.result.away_goals);
		Collections.sort(goals);
		clock_label.setText(" 0 ");
		home_label.setText(" "+match.home.name+" ");
		home_label.setStyle(new LabelStyle(Assets.skin.get("teamname", LabelStyle.class)));
		home_label.getStyle().background = Assets.skin.newDrawable("base",Assets.skin.getColor(match.home.colour_base));
		home_label.getStyle().fontColor = Assets.skin.getColor(match.home.colour_primary);
		away_label.setText(" "+match.away.name+" ");
		away_label.setStyle(new LabelStyle(Assets.skin.get("teamname", LabelStyle.class)));
		//if same kits, invert away
		if(match.away.colour_base==match.home.colour_base&&match.away.colour_primary==match.home.colour_primary) {
			away_label.getStyle().background = Assets.skin.newDrawable("base",Assets.skin.getColor(match.away.colour_primary));
			away_label.getStyle().fontColor = Assets.skin.getColor(match.away.colour_base);

		} else {
			away_label.getStyle().background = Assets.skin.newDrawable("base",Assets.skin.getColor(match.away.colour_base));
			away_label.getStyle().fontColor = Assets.skin.getColor(match.away.colour_primary);

		}
		
		button.setDisabled(false);
		button.setChecked(false);
		button.setText("Kick Off");
		away_score_label.setText(" "+String.valueOf(current_away)+" ");
		home_score_label.setText(" "+String.valueOf(current_home)+" ");
		action_group.clear();
		current_state = MatchState.PRE;
		
	}
	
	
	
	@Override
	public void render(float delta){
		super.render(delta);
	}

	
}

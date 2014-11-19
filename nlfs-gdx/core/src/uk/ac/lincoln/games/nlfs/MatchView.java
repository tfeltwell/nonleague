package uk.ac.lincoln.games.nlfs;

import java.util.ArrayList;
import java.util.Collections;

import uk.ac.lincoln.games.nlfs.logic.GameState;
import uk.ac.lincoln.games.nlfs.logic.Match;
import uk.ac.lincoln.games.nlfs.logic.Goal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

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
	
	private int current_minute, current_home, current_away;
	
	public MatchView (final NonLeague game) {
		super(game);
		//NB remember none of this stuff is in memory yet
		home_label = new Label("[HOME TEAM]",GameState.assets.skin,"teamname");
		away_label = new Label("[AWAY TEAM]",GameState.assets.skin,"teamname");
		home_score_label = new Label(" 0 ",GameState.assets.skin,"score");
		away_score_label = new Label(" 0 ",GameState.assets.skin,"score");
		
		table.add(home_label).expandX().fillX().left();
		table.add(home_score_label).right().padLeft(2);
		table.row().padTop(2);
		table.add(away_label).expandX().fillX().left();
		table.add(away_score_label).right().padLeft(2);
	
		table.row().padTop(2);
		clock_label = new Label("(C): 0",GameState.assets.skin,"score_report");
		table.add(clock_label).colspan(2);
		table.row().padTop(2);
		
		Table action_table = new Table();
		action_table.setSize(300, 500);
		action_table.setBackground(GameState.assets.skin.getDrawable("darken"));
		
		action_group = new VerticalGroup();
		action_pane = new ScrollPane(action_group);
		action_pane.setScrollingDisabled(true, false);
		
		action_pane.setSize(300, 500);
		
		action_table.add(action_pane);
		table.add(action_table).colspan(2).prefSize(300, 500);
		
		table.row();
		button = new TextButton("Leave Match", GameState.assets.skin);	

		table.add(button).width(200).height(40).colspan(2);
		table.row();
		button.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.postmatch_screen.setResult(match.result);
				game.changeScreen(game.postmatch_screen);
		}
		});
	}
	
	public void setMatch(Match m) {
		current_minute = 0;
		current_home = 0;
		current_away = 0;
		match = m;
		match.run();
		goals = new ArrayList<Goal>();
		goals.addAll(m.result.home_goals);
		goals.addAll(m.result.away_goals);
		Collections.sort(goals);
		home_label.setText(" "+match.home.name+" ");
		home_label.setStyle(new LabelStyle(GameState.assets.skin.get("teamname", LabelStyle.class)));
		home_label.getStyle().background = GameState.assets.skin.newDrawable("base",GameState.assets.skin.getColor(match.home.colour_base));
		home_label.getStyle().fontColor = GameState.assets.skin.getColor(match.home.colour_primary);
		away_label.setText(" "+match.away.name+" ");
		away_label.setStyle(new LabelStyle(GameState.assets.skin.get("teamname", LabelStyle.class)));
		away_label.getStyle().background = GameState.assets.skin.newDrawable("base",GameState.assets.skin.getColor(match.away.colour_base));
		away_label.getStyle().fontColor = GameState.assets.skin.getColor(match.away.colour_primary);
		home_score_label.getStyle().background = GameState.assets.skin.newDrawable("base",Color.WHITE); //TODO should be properly assigned in skin
		button.setDisabled(true);
		button.setChecked(false);
		away_score_label.setText(" "+String.valueOf(current_away)+" ");
		home_score_label.setText(" "+String.valueOf(current_home)+" ");
		action_group.clear();
		//TODO set yellow & red cards?
	}
	
	private void doMinute() {
		if(current_minute>94) {
			return;
		}
		//resolve a minute's worth of match time
		
		
		clock_label.setText("(C) "+String.valueOf(current_minute));
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
				action_group.addActor(new Label(String.valueOf(g.time)+": Goal for "+g.scorer.team.name ,Assets.skin,"score_report"));
				Label scorer = new Label(g.scorer.getName()+" ("+g.scorer.getPosition().toString()+")",Assets.skin,"scorer_report");
				scorer.setWidth(300);
				scorer.setAlignment(Align.right);
				action_group.addActor(scorer);
				
			}
				
		}
		current_minute++;
		//if finished
		if(current_minute>94)
			button.setDisabled(false);
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
		//TODO resolve a minute per time unit
		doMinute();
	}
}

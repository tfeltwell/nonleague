package uk.ac.lincoln.games.nlfs;

import uk.ac.lincoln.games.nlfs.logic.GameState;
import uk.ac.lincoln.games.nlfs.logic.MatchResult;
import uk.ac.lincoln.games.nlfs.ui.TeamLabel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Show summary of match results. Score, newspaper article, etc.
 * @author bkirman
 *
 */
public class PostMatch extends BaseScreen {
	private MatchResult result;
	private Label home_label,away_label,stadium_label,description_label;
	private Table results_table;
	
	public PostMatch (final NonLeague game) {
		super(game);
		home_label = new Label("[HOME TEAM]: 0",Assets.skin);
		away_label = new Label("[AWAY TEAM]: 0",Assets.skin);
		stadium_label = new Label("at [STADIUM]",Assets.skin);
		description_label = new Label("[DESCRIPTION]",Assets.skin);
		description_label.setWrap(true);
		//description_label.setWidth(200);
		
		table.add(home_label).expandX();
		table.row();
		table.add(away_label).expandX();
		table.row();
		table.add(stadium_label).expandX().right();
		table.row();
		table.add(description_label).expand().left().width(200);
		table.row();
		
		TextButton button = new TextButton("Leave Match", Assets.skin);	

		table.add(button).width(200).height(40);
		table.row();
		button.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.changeScreen(game.teamstatus_screen);
			}});
		
		results_table = new Table();
		results_table.setSkin(Assets.skin);
		results_table.setBackground(Assets.skin.getDrawable("darken"));
		results_table.pad(10f);
		root_table.row();
		root_table.add(results_table);
		
	}
	
	public void setResult(MatchResult mr) {
		result = mr;
	}
	
	public void update() {
		//update screen since at this point we should have a result
		home_label.setText(result.match.home.name + ": "+String.valueOf(result.home_goals.size()));
		away_label.setText(result.match.away.name + ": "+String.valueOf(result.away_goals.size()));
		stadium_label.setText("at "+result.match.home.stadium);
		description_label.setText(result.getDescription(GameState.player_team));
		results_table.clear();
		
		if(GameState.league.weekly_results.size()==0) return;
		results_table.add(new Label("Other Results:",Assets.skin)).colspan(3);
		TeamLabel h,a;
		for(MatchResult mr2:GameState.league.weekly_results) {
			results_table.row();
			h = new TeamLabel(mr2.match.home);
			a = new TeamLabel(mr2.match.away);
			results_table.add(h).fillX().expandX().pad(5f);
			results_table.add(new Label(" "+String.valueOf(mr2.home_goals.size())+" - "+String.valueOf(mr2.away_goals.size())+" ",Assets.skin,"score")).pad(5f);
			results_table.add(a).fillX().expandX().pad(5f).align(Align.right).right();
		}
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
	}
}

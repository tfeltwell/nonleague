package uk.ac.lincoln.games.nlfs;

import uk.ac.lincoln.games.nlfs.logic.GameState;
import uk.ac.lincoln.games.nlfs.logic.LeagueTableItem;
import uk.ac.lincoln.games.nlfs.ui.TeamLabel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

/**
 * table view that shows current league
 * @author bkirman
 *
 */
public class LeagueTable extends BaseScreen {
	private Table league_table;
	private Label title;
	public LeagueTable(final NonLeague game) {
		super(game);
		title = new Label(" ",Assets.skin,"pagetitle");
		table.add(title).expandX().fillX();
		table.row();
		league_table = new Table(Assets.skin);
		league_table.setBackground(Assets.skin.getDrawable("darken"));
		table.add(league_table).expand().fill();
		table.row();
		
		TextButton button = new TextButton("Back to Team", Assets.skin);	
		table.add(button).width(200).height(40);
		table.row().pad(2);
		
		//button.setText(match.result.getDescription(match.home));
		
		button.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.changeScreen(game.teamstatus_screen);
		}
		});
	}
	
	/**
	 * Regenerate table
	 */
	public void update() {
		title.setText(GameState.league.name);
		league_table.clear();
		for(LeagueTableItem lti:GameState.league.table) {
			league_table.add(new TeamLabel(lti.team)).align(Align.left).fillX();
			league_table.add(new Label(String.valueOf(lti.getGamesPlayed()),Assets.skin,"lt_points"));
			league_table.add(new Label(String.valueOf(lti.wins),Assets.skin,"lt_points"));
			league_table.add(new Label(String.valueOf(lti.losses),Assets.skin,"lt_points"));
			league_table.add(new Label(String.valueOf(lti.draws),Assets.skin,"lt_points"));
			league_table.add(new Label(String.valueOf(lti.points),Assets.skin,"lt_points"));
			league_table.row().pad(2);
		}
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
	}

}

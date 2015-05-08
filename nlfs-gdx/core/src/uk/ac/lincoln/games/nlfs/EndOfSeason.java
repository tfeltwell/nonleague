package uk.ac.lincoln.games.nlfs;

import uk.ac.lincoln.games.nlfs.logic.GameState;
import uk.ac.lincoln.games.nlfs.ui.LeaguePositionGraph;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * show season results. final league positions. Calculate new league in case of promotion/relegation
 * @author bkirman
 *
 */
public class EndOfSeason extends BaseScreen {
	private Label title, description_label;
	private Texture paper;
	private Table position_table;
	
	public EndOfSeason(final NonLeague game) {
		super(game);
		paper = new Texture(Gdx.files.internal("paper.png"));
		title = new Label("END OF SEASON",Assets.skin,"pagetitle");
		position_table = new Table();
		table.add(title).expandX().fillX();
		table.row();
		
		description_label = new Label("[DESCRIPTION]",Assets.skin,"newspaper");
		description_label.setWrap(true);
		
		Table newspaper_table = new Table();
		newspaper_table.background(new Image(paper).getDrawable());
		newspaper_table.add(description_label).expandX().fill().pad(20);
			
		table.add(newspaper_table).expand().fillX().colspan(2).padBottom(10).minWidth(600);
		table.row();
		table.add(position_table).colspan(2).expand();
		table.row();
		
		TextButton button = new TextButton("Start New Season", Assets.skin);	
		table.add(button).width(480).height(85);
		table.row();
		
		button.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.changeScreen(game.teamstatus_screen);
			}});
	}
	public void update(){
		
		position_table.clear();
		Image graph = new Image(LeaguePositionGraph.generateLeaguePositionGraph(GameState.league.getTeamPositionHistory(GameState.player_team)));
		//graph.scaleBy(4.0f);
		//graph.setSize(480, 80);
		position_table.add(graph).size(480, 80);
		
		String old_league = GameState.league.name;
		String ordinal_pos = GameState.league.getTeamPositionOrdinal(GameState.player_team);
		int promotion;//hacky
		if (GameState.league.isPromoted(GameState.player_team)) promotion = 1;
		else if (GameState.league.isRelegated(GameState.player_team)) promotion = 2;
		else promotion = 3;
		
		GameState.league.newSeason(GameState.player_team);
		
		String description;
		if(promotion==1) description = "Jubilant fans fill "+GameState.player_team.stadium+" to celebrate "+GameState.player_team.name+"'s "+ordinal_pos+" place finish in the "+old_league+". The team now faces tougher challenges as they are promoted to the "+GameState.league.name+".";
		else if(promotion==2) description = "Tears before bedtime in "+GameState.player_team.stadium+" as "+GameState.player_team.name+"'s "+ordinal_pos+" place finish means relegation from the "+old_league+". The team continues their downward slide into the "+GameState.league.name+".";
		else description = "A disappointing season for "+GameState.player_team.name+" sees a "+ordinal_pos+" place finish, and another year battling on in the "+old_league;
		description_label.setText(description);
		
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
	}
}

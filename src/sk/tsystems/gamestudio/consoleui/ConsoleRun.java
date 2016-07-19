package sk.tsystems.gamestudio.consoleui;
import sk.tsystems.gamestudio.services.GameService;
import sk.tsystems.gamestudio.services.RatingService;
import sk.tsystems.gamestudio.services.UserService;
import sk.tsystems.gamestudio.entity.GameEntity;
import sk.tsystems.gamestudio.services.CommentService;
import sk.tsystems.gamestudio.services.ScoreService;


public class ConsoleRun {

	public static void main(String[] args) {
		try(
				/*GameService game = new sk.tsystems.gamestudio.services.jpa.GameSvc();
				UserService user = new sk.tsystems.gamestudio.services.jpa.UserSvc();
				CommentService comme = new sk.tsystems.gamestudio.services.jpa.CommentSvc();
				ScoreService score =  new sk.tsystems.gamestudio.services.jpa.ScoreSvc();
				RatingService rating = new sk.tsystems.gamestudio.services.jpa.RatingSvc();*/
								
				GameService game = new sk.tsystems.gamestudio.services.jdbc.GameSvc();
				UserService user = new sk.tsystems.gamestudio.services.jdbc.UserSvc();
				CommentService comme = new sk.tsystems.gamestudio.services.jdbc.CommentSvc(user, game);
				ScoreService score =  new sk.tsystems.gamestudio.services.jdbc.ScoreSvc(user);
				RatingService rating = new sk.tsystems.gamestudio.services.jdbc.RatingSvc(user);
			)
		{
			if(game.listGames().isEmpty()) // we need to create games 
			{
				GameEntity ga = new GameEntity(0, "Minesweeper");
				ga.setRunnable(sk.tsystems.gamestudio.game.minesweeper.Minesweeper.class);
				game.addGame(ga);
				
				game.addGame(new GameEntity(0, "Kamene"));
				

				ga = new GameEntity(0, "Guess the number");
				ga.setRunnable(sk.tsystems.gamestudio.game.guessnumber.ui.GuesNumRun.class);
				game.addGame(ga);

				
			}
			
			ConsoleUI ui = new ConsoleUI(game, user, comme, score, rating);
			ui.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package sk.tsystems.gamestudio.consoleui;
import sk.tsystems.gamestudio.services.GameService;
import sk.tsystems.gamestudio.services.UserService;
import sk.tsystems.gamestudio.services.CommentService;
import sk.tsystems.gamestudio.services.ScoreService;


public class ConsoleRun {

	public static void main(String[] args) {
		try(
			GameService game = new sk.tsystems.gamestudio.services.jdbc.GameSvc();
			UserService user = new sk.tsystems.gamestudio.services.jdbc.UserSvc();
			CommentService comme = new sk.tsystems.gamestudio.services.jdbc.CommentSvc(user, game);
			ScoreService score =  new sk.tsystems.gamestudio.services.jdbc.ScoreSvc(user);
			)
		{
			ConsoleUI ui = new ConsoleUI(game, user, comme, score);
			ui.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

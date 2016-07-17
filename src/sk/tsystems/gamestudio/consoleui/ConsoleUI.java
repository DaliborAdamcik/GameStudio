package sk.tsystems.gamestudio.consoleui;

import java.util.Date;
import java.util.List;
import java.util.Random;

import sk.tsystems.gamestudio.entity.CommentEntity;
import sk.tsystems.gamestudio.entity.GameEntity;
import sk.tsystems.gamestudio.entity.RatingEntity;
import sk.tsystems.gamestudio.entity.ScoreEntity;
import sk.tsystems.gamestudio.entity.UserEntity;
import sk.tsystems.gamestudio.services.GameService;
import sk.tsystems.gamestudio.services.RatingService;
import sk.tsystems.gamestudio.services.ScoreService;
import sk.tsystems.gamestudio.services.UserService;
import sk.tsystems.gamestudio.services.CommentService;


public class ConsoleUI extends ConsoleInput {
	private GameService games;
	private UserService users;
	private CommentService comments;
	private ScoreService scores;
	private RatingService ratings;
	
	public ConsoleUI(GameService games, UserService users, CommentService comments, ScoreService scores, RatingService ratings) {
		super();
		this.games = games;
		this.users = users;
		this.comments = comments;
		this.scores = scores;
		this.ratings = ratings;
	}
	
	public void run()
	{
		users.auth(askMyName()); // TODO
		
		System.out.println(users.me().getName()+" welcome in game center!");
		
		boolean running = true;
		Object menuOpt;
		do
		{
			menuOpt = getMenuOption(printMainMenu());
			if(menuOpt instanceof Integer) // selected game
				RunGame((Integer) menuOpt);
			
			if (menuOpt instanceof Character) {
				switch(((Character) menuOpt).charValue())
				{
					case 'e':
					case 'x': 
						running = false;
						break;
						
					case 's': 
						commentsShow(games.getGame(1));
						break;
					case 'a':
						commentAdder(games.getGame(1));
						commentsShow(games.getGame(1));
						break;
						
					case 'w': // test add score into table
						ScoreEntity sco = new ScoreEntity(games.getGame(1), users.me(), 10);
						scores.addScore(sco);
						for(ScoreEntity s : scores.topScores(games.getGame(1)))
						{
							System.out.println(s);
						}
						break;
						
					case 'q': // test add rate into table
						Random ra = new Random();
						UserEntity usr = users.addUser("rnd_usr_"+ra.nextInt(100));
						
						System.out.println(usr);

						RatingEntity rat = new RatingEntity(games.getGame(1), usr, 10);
						ratings.addRating(rat);
						ratings.gameRating(games.getGame(1));
						break;
					default:
						System.out.println("!! Ivalid menu option.");
						break;
				}
			}
		}
		while(running);
		System.out.println("....> Bye bye :-)");
	}
	
	private int printMainMenu()
	{
		System.out.println("...:> GAME STUDIO <:... maiN");
		int optcount = 1;
		System.out.println(" e,x: Exit");
		
		for(GameEntity game: games.listGames())
			System.out.printf("\t%2d: RUN %s game (id: %d, cls: %s) rating %f\r\n", optcount++, game.getName(), game.getID(), game.className(), ratings.gameRating(game));

		return optcount-1;
	}
	
	private Object getMenuOption(int maxmenu)
	{
		do
		{
			System.out.print("> Enter option: ");
			String option = this.readLine().trim().toLowerCase();
			
			try
			{
				int i = Integer.parseInt(option);
				if(i<1 || i>maxmenu)
					throw new NumberFormatException();
				return new Integer(i);
			}
			catch(Exception e) // TODO we need to check there values, ! Lambda??
			{
				if (option.length()<1)
					return null;
				
				return new Character(Character.toLowerCase(option.charAt(0))); 
			}
		
		}
		while(true);
	}
	
	private String askMyName()
	{
		String name ="dalik"; // TODO
		//System.out.println("TODO: my name is "+name); // TODO ask name
		return name;
	}
	
	private void RunGame(int id)
	{
		GameEntity ga = games.getGame(id);
		System.out.println(">>>Run game: "+ga.getName());
		scoreShow(ga);

		try {
			if(ga.run() == 0)
			{
				// TODO add my score
				scoreShow(ga);
				//TODO ad line show my score
				
				
				if(this.readYN("Do you want add comment"))
					commentAdder(ga);
				else
					if(this.readYN("Do you want show comments"))
						commentsShow(ga);
				
				// TODO: rating 

				
				
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void commentAdder(GameEntity game)
	{
		System.out.print("Enter your comment: ");
		String com = this.readLine().trim();
		if(com.length()>0)
		{
			comments.addComment(new CommentEntity(game, users.me(), com, 0, new Date()));
		}
		commentsShow(game);
	}
	
	private void commentsShow(GameEntity game)
	{
		List<CommentEntity> comml = comments.commentsFor(game);
		for (CommentEntity commi :comml) {
			System.out.printf("%d: %s %s %s\r\n", commi.getID(), commi.getUser().getName(), 
					commi.getDate().toString(), commi.getComment());
		}
	}
	
	private void scoreShow(GameEntity game)
	{
		System.out.printf("** TOP 10 Scores for -> %s <- ***\r\n", game.getName());
		
		List<ScoreEntity> scrl = scores.topScores(game);
		int cnt = 1;
		
		for (ScoreEntity scri: scrl) {
			System.out.printf("%2d. %d %s %s\r\n", cnt++, scri.getScore(), scri.getUser().getName(),  
					scri.getDate().toString());
		}
	}
	

}

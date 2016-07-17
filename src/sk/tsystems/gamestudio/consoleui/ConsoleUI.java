package sk.tsystems.gamestudio.consoleui;

import java.util.List;

import sk.tsystems.gamestudio.entity.CommentEntity;
import sk.tsystems.gamestudio.entity.GameEntity;
import sk.tsystems.gamestudio.entity.ScoreEntity;
import sk.tsystems.gamestudio.services.GameService;
import sk.tsystems.gamestudio.services.ScoreService;
import sk.tsystems.gamestudio.services.UserService;
import sk.tsystems.gamestudio.services.CommentService;


public class ConsoleUI extends ConsoleInput {
	private GameService games;
	private UserService users;
	private CommentService comments;
	private ScoreService scores;
	
	public ConsoleUI(GameService games, UserService users, CommentService comments, ScoreService scores) {
		super();
		this.games = games;
		this.users = users;
		this.comments = comments;
		this.scores = scores;
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
			System.out.printf("\t%2d: RUN %s game (id: %d, cls: %s)\r\n", optcount++, game.getName(), game.getID(), game.className());

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
			comments.addComment(new CommentEntity(game, users.me(), com));
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

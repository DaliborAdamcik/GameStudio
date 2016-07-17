package sk.tsystems.gamestudio.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import sk.tsystems.gamestudio.consoleui.ConsoleUiRun;

@Entity
public class GameEntity {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private Class<?> runnable = null;

	public GameEntity(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String className()
	{
		if(runnable==null)
			return "<unset>";
		return runnable.getCanonicalName();
	}

	public void setRunnable(Class<?> runnable) {
		if(!checkRunnable(runnable))
			throw new RuntimeException("Claas not implements Console UI run method"); // TODO exception class
		this.runnable = runnable;
	}
	
	public int run() throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		if (runnable == null)
		{
			throw new ClassNotFoundException();
		}
		ConsoleUiRun instance = (ConsoleUiRun) this.runnable.newInstance();
		return instance.runCsl();
	}
	
	private boolean checkRunnable(Class<?> runnable)
	{
		return true; // TODO check class contain runnable method
	}
	
	
	
}

/*			Class<?> clz = Class.forName(ga.className());
if(clz.isAnnotationPresent(ConsoleUiRun.class))
{
	clz.newInstance(); 
}
else
System.out.println("no EP");*/

	
//sk.tsystems.gamestudio.game.minesweeper.Minesweeper

/*Class<?>[] impl = clz.getInterfaces();
for(Class<?> ifac : impl)
{
	if(ifac.getSimpleName().compareTo("ConsoleUiRun")==0)
}*/
/*		catch (ClassNotFoundException e) 
{
	e.printStackTrace();
	System.out.println("Game executive not found");
}*/ 



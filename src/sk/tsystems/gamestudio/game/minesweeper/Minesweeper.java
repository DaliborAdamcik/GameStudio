package sk.tsystems.gamestudio.game.minesweeper;

import sk.tsystems.gamestudio.consoleui.ConsoleUiRun;
import sk.tsystems.gamestudio.game.minesweeper.consoleui.ConsoleUI;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper implements ConsoleUiRun {
    /** User interface. */
    private UserInterface userInterface;
    private long startMillis;
    private static Minesweeper instance;
	private Settings setting;
	private Field field;
 
    /**
     * Constructor.
     */
	public Minesweeper() {
        instance = this;
        setting = Settings.load();
        this.field = new Field(setting.getRowCount(), setting.getColumnCount(), setting.getMineCount());
    }
	
	@Override
	public int runCsl() {
        userInterface = new ConsoleUI();
        startMillis = System.currentTimeMillis();
        userInterface.newGameStarted(field);
		return 0;
	}
	

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(String[] args) {
        Minesweeper min = new Minesweeper();
        min.runCsl();
        
    }
    
    public int getPlayingSeconds()
    {
    	long time =(System.currentTimeMillis() - startMillis); 
    	return (int)(time / 1000);
    }
    
    public static Minesweeper getInstance()
    {
    	return instance;
    }
    
	public Settings getSetting() {
		return setting;
	}

	public void setSetting(Settings setting) {
		this.setting = setting;
		this.setting.save();
	}
}

package sk.tsystems.gamestudio.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class ConsoleInput {
    private static BufferedReader input = null;
    

    
    
    public ConsoleInput() {
		super();
		
		if(input==null)
		input = new BufferedReader(new InputStreamReader(System.in));
	}



	/**
     * Reads line of text from the reader (cosnole).
     * @return line as a string
     */
    public String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }
    
    public boolean readYN(String message)
    {
    	System.out.print(message+"? (y/[N=any]):");
    	try
    	{
        	return readLine().toLowerCase().charAt(0)=='y';
    	}
    	catch(Exception e)
    	{
    		return false;
    	}
    }
}

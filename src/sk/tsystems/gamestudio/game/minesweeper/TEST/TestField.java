/**
 * 
 */
package sk.tsystems.gamestudio.game.minesweeper.TEST;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;

import junit.framework.TestCase;
import sk.tsystems.gamestudio.game.minesweeper.core.*;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile.State;

/**
 * @author Dalibor Adamèík
 *
 */
public class TestField extends TestCase {

	private static int crow;
	private static int ccol;
	private static int cmines;
	private static sk.tsystems.gamestudio.game.minesweeper.core.Field field = null;
	/**
	 * @param name
	 */
	public TestField(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		
		if(field!=null)
			return;
		
		java.util.Random ra = new java.util.Random();
		
		do{
			crow= ra.nextInt(50);
		} while(crow<2);
		do{
			ccol= ra.nextInt(50);
		} while(ccol<2);
		do{
			cmines= ra.nextInt(crow*ccol);
		} while(cmines<2);
		
		
		field = new sk.tsystems.gamestudio.game.minesweeper.core.Field(crow, ccol, cmines);
		assertNotNull("Initialization of object failed.", field);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	/*@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}*/

	/**
	 * Test method for {@link sk.tsystems.gamestudio.game.minesweeper.core.Field#Field(int, int, int)}.
	 */

	@Test
	public final void testField() {
		for(int r = 0; r < field.getRowCount(); r++) 
            for(int c = 0; c < field.getColumnCount(); c++) 
                	assertEquals("Initial state of tile is invalid. ["+r+","+c+"]("+crow+","+ccol+") is "+field.getTile(r, c).getClass().getSimpleName(), State.CLOSED, field.getTile(r, c).getState());
	}

	/**
	 * Test method for {@link sk.tsystems.gamestudio.game.minesweeper.core.Field#getRowCount()}.
	 */
	@Test
	public final void testGetRowCount() {
		assertEquals(crow, field.getRowCount());
	}

	/**
	 * Test method for {@link sk.tsystems.gamestudio.game.minesweeper.core.Field#getColumnCount()}.
	 */
	@Test
	public final void testGetColumnCount() {
		assertEquals(ccol, field.getColumnCount());
	}

	/**
	 * Test method for {@link sk.tsystems.gamestudio.game.minesweeper.core.Field#getMineCount()}.
	 */
	@Test
	public final void testGetMineCount() {
		assertEquals(cmines, field.getMineCount());
	}

	/**
	 * Test method for {@link sk.tsystems.gamestudio.game.minesweeper.core.Field#getTile(int, int)}.
	 */
	@Test
	public final void testGetTile() {
		int minec = 0;
		int cluec = 0;
		for(int r = 0; r<field.getRowCount();r++)
			for(int c = 0; c<field.getColumnCount();c++)
			{
				sk.tsystems.gamestudio.game.minesweeper.core.Tile tile =field.getTile(r, c);
				assertNotNull(tile);
				
				assertEquals(tile.getState(), sk.tsystems.gamestudio.game.minesweeper.core.Tile.State.CLOSED);
				
				if(tile instanceof sk.tsystems.gamestudio.game.minesweeper.core.Clue)
					cluec++;

				if(tile instanceof sk.tsystems.gamestudio.game.minesweeper.core.Mine)
					minec++;
			}
		assertEquals(cmines, minec);
		assertEquals(crow*ccol, minec+cluec);
		assertEquals(crow*ccol-minec, cluec);
	}
	
	/** 
	 * Tests:
	 * - opening tile (clue)
	 * - mark tile (mine)
	 * - mark / open state (tile.getState)
	 */
	@Test                
    public final void testIsSolved() {
		assertNotNull("'field' is unitialized. Cant continue.", field);

		assertEquals(GameState.PLAYING, field.getState());
        
        int open = 0;
        int mark = 0;
        int nothing = 0;
        for(int r = 0; r < field.getRowCount(); r++) {
            for(int c = 0; c < field.getColumnCount(); c++) {

            	//
            	//assertTrue("Initial state of tile is invalid. ["+r+","+c+"]("+crow+","+ccol+") is "+field.getTile(r, c).getClass().getSimpleName(), field.getTile(r, c).getState()==State.CLOSED);
            	
                if(field.getTile(r, c) instanceof Clue) {
                	field.openTile(r, c);
                    assertEquals("Tile state after OPEN is invalid", State.OPEN, field.getTile(r, c).getState());

                    open++;
                }
                else
                if(field.getTile(r, c) instanceof Mine) {
                    field.markTile(r, c);
                    assertEquals("Tile state after MARK is invalid", State.MARKED, field.getTile(r, c).getState());
                    mark++;

                    // marked tile cant be open - test it
                    field.openTile(r, c);
                    assertEquals("We cant open MARKED tile.", State.MARKED, field.getTile(r, c).getState());
                    
                    // unmark it
                    field.markTile(r, c);
                    assertEquals("Tile state after unMARK is invalid", State.CLOSED, field.getTile(r, c).getState());
                }
                else 
                	nothing++; // this is very bad 
                
                if(field.getRowCount() * field.getColumnCount() - open == field.getMineCount()) {
                    assertEquals("Game MUST be SOLVED.. bud?", GameState.SOLVED, field.getState());
                } else {
                    assertNotSame("Game over? Why??", GameState.FAILED, field.getState());
                }
            }
        }
        
        assertEquals("Game must be SOLVED.", GameState.SOLVED, field.getState());
        assertEquals("We open only Clue.", field.getColumnCount()*field.getRowCount()-field.getMineCount(), open);
        assertEquals("There is umarked mines. Bad initialization?", field.getMineCount(), mark);
        assertEquals("There is unknown / uniinicialized tiles in field.", 0, nothing);
    }
	
	@After
	public final void testIsZGameOver()
	{
		//return;
		int mines = 0;
        for(int row = 0; row < field.getRowCount(); row++) {
            for(int column = 0; column < field.getColumnCount(); column++) {
            	if(field.getTile(row, column) instanceof Mine)
            	{
            		field.openTile(row, column);
                    assertEquals("Tile state after OPEN is invalid", State.OPEN, field.getTile(row, column).getState());
            		mines++;
                    assertEquals("Game must be over.", GameState.FAILED, field.getState());
            	}
            }
        }
        assertEquals("number of opened mines is incorrent.", cmines, mines);
	}
	
	@Test
	public final void testIncorrectTile() {
		assertNotNull("'field' is unitialized. Cant continue.", field);
		assertNull("Invalid value for invalid tile -1,-1", field.getTile(-1, -1));
		assertNull("Invalid value for invalid tile 0,-1", field.getTile(0, -1));
		assertNull("Invalid value for invalid tile -1,0", field.getTile(-1, 0));
		assertNull("Invalid value for invalid tile CC,RC", field.getTile(crow, ccol));
		assertNull("Invalid value for invalid tile CC+1,RC+1", field.getTile(crow+1, ccol+1));
	}
	
}

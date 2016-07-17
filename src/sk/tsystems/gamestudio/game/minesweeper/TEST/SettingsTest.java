/**
 * 
 */
package sk.tsystems.gamestudio.game.minesweeper.TEST;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sk.tsystems.gamestudio.game.minesweeper.Settings;

/**
 * @author Dalibor Adamèík
 *
 */
public class SettingsTest {
	private static Settings sets;
	private static int rowc;
	private static int colc;
	private static int minc;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Random ran = new Random();
		rowc = ran.nextInt(49)+2;
		colc = ran.nextInt(49)+2;
		minc = ran.nextInt(rowc*colc)+1;
		sets = new Settings(rowc, colc, minc);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link sk.tsystems.gamestudio.game.minesweeper.Settings#hashCode()}.
	 */
	@Test
	public final void testHashCode() {
		assertEquals("Invalid hashcode", sets.hashCode(), rowc*colc*minc);
	}

	/**
	 * Test method for {@link sk.tsystems.gamestudio.game.minesweeper.Settings#save()} and {@link sk.tsystems.gamestudio.game.minesweeper.Settings#load()}.
	 */
	@Test
	public final void testSaveLoad() {
		try
		{
			sets.save();
		}
		catch(Exception e)
		{
			assertFalse("Save: "+e.getMessage(), true);
		}
		
		Settings loaded = null;
		try
		{
			loaded = Settings.load();
		}
		catch(Exception e)
		{
			assertFalse("Load: "+e.getMessage(), true);
		}

		assertNotNull("No setting loaded by Class", loaded);
		assertEquals("No rowcount match", rowc, loaded.getRowCount());
		assertEquals("No colcount match", colc, loaded.getColumnCount());
		assertEquals("No minecount match", minc, loaded.getMineCount());
	}


	/**
	 * Test method for {@link sk.tsystems.gamestudio.game.minesweeper.Settings#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {
		Settings se2t = new Settings(rowc, colc, minc);
		assertTrue("Object not equals", sets.equals(se2t));
	}

	/**
	 * Test method for {@link sk.tsystems.gamestudio.game.minesweeper.Settings#getRowCount()}.
	 */
	@Test
	public final void testGetRowCount() {
		assertEquals("No rowcount match", rowc, sets.getRowCount());
	}

	/**
	 * Test method for {@link sk.tsystems.gamestudio.game.minesweeper.Settings#getColumnCount()}.
	 */
	@Test
	public final void testGetColumnCount() {
		assertEquals("No colcount match", colc, sets.getColumnCount());
	}

	/**
	 * Test method for {@link sk.tsystems.gamestudio.game.minesweeper.Settings#getMineCount()}.
	 */
	@Test
	public final void testGetMineCount() {
		assertEquals("No minecount match", minc, sets.getMineCount());
	}

	/**
	 * Test method for predefined setting BEGINNER
	 */
	@Test
	public final void testBEGINNER() {
		Settings loaded = Settings.BEGINNER;
		assertNotNull("No setting loaded by Class", loaded);
		assertEquals("No rowcount match", 9, loaded.getRowCount());
		assertEquals("No colcount match", 9, loaded.getColumnCount());
		assertEquals("No minecount match", 10, loaded.getMineCount());
	}
	
	@Test
	public final void testINTERMEDIATE() {
		Settings loaded = Settings.INTERMEDIATE;
		assertNotNull("No setting loaded by Class", loaded);
		assertEquals("No rowcount match", 16, loaded.getRowCount());
		assertEquals("No colcount match", 16, loaded.getColumnCount());
		assertEquals("No minecount match", 40, loaded.getMineCount());
	}
	
	@Test
	public final void testEXPERT() {
		Settings loaded = Settings.EXPERT;
		assertNotNull("No setting loaded by Class", loaded);
		assertEquals("No rowcount match", 16, loaded.getRowCount());
		assertEquals("No colcount match", 30, loaded.getColumnCount());
		assertEquals("No minecount match", 99, loaded.getMineCount());
	}

	
}

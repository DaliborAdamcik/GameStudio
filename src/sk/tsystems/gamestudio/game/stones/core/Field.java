package sk.tsystems.gamestudio.game.stones.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Field implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int EMPTY_CELL = -1;

	private static final String SAVE_PATH = System.getProperty("user.home") + System.getProperty("file.separator")
			+ "stones.sav";

	private final int[][] tiles;

	private final int rowCount;

	private final int columnCount;

	private long startMillis;

	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		tiles = new int[rowCount][columnCount];
		generate();
		startMillis = System.currentTimeMillis();
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getValueAt(int row, int column) {
		return tiles[row][column];
	}

	private void generate() {
		int count = rowCount * columnCount;
		List<Integer> values = new ArrayList<>(count);
		for (int i = 1; i < count; i++) {
			values.add(i);
		}
		values.add(EMPTY_CELL);

		//Collections.shuffle(values);

		int index = 0;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				// tiles[row][column] = values.get(row * columnCount + column);
				tiles[row][column] = values.get(index);
				index++;
			}
		}
		shuffle();
	}

	private void shuffle()
	{
		int shufcnt = columnCount * rowCount * 10*4;
		Random rnd = new Random();
		for(int sc = 0; sc<shufcnt;sc++)
		{
			int rn = rnd.nextInt(5);
			switch(rn)
			{
				case 0: moveDown();break;
				case 1: moveLeft();break;
				case 2: moveRight();break;
				default: moveUp(); break;
			}
		}
	}
	

	private Position getPositionOf(int value) {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == value) {
					return new Position(row, column);
				}
			}
		}
		return null;
	}

	private Position getEmptyPosition() {
		return getPositionOf(EMPTY_CELL);
	}

	private void move(int dr, int dc) {
		Position emptyPosition = getEmptyPosition();
		int er = emptyPosition.getRow();
		int ec = emptyPosition.getColumn();
		if (er + dr >= 0 && er + dr < rowCount && ec + dc >= 0 && ec + dc < columnCount) {
			tiles[er][ec] = tiles[er + dr][ec + dc];
			tiles[er + dr][ec + dc] = EMPTY_CELL;
		}
	}

	public void moveDown() {
		move(-1, 0);
	}

	public void moveUp() {
		move(1, 0);
	}

	public void moveRight() {
		move(0, -1);
	}

	public void moveLeft() {
		move(0, 1);
	}

	public boolean move(int value) {
		Position pos = getPositionOf(value);
		if (pos != null) {
			Position emptyPosition = getEmptyPosition();
			int dr = pos.getRow() - emptyPosition.getRow();
			int dc = pos.getColumn() - emptyPosition.getColumn();

			if ((Math.abs(dr) == 1 && dc == 0) || (dr == 0 && Math.abs(dc) == 1)) {
				move(dr, dc);
				return true;
			}
		}
		return false;
	}

	public boolean isSolved() {
		int value = 1;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] != value) {
					return false;
				}
				value++;
				if (value == rowCount * columnCount) {
					return true;
				}
			}
		}
		throw new StonesException("This should never happen");
	}

	public int getPlayingSeconds() {
		return (int) ((System.currentTimeMillis() - startMillis) / 1000);
	}

	public void save() {
		try (FileOutputStream fos = new FileOutputStream(SAVE_PATH);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(this);
		} catch (Exception e) {
			throw new StonesException("Error saving game", e);
		}
	}

	public static Field load() {
		try (FileInputStream fis = new FileInputStream(SAVE_PATH); ObjectInputStream ois = new ObjectInputStream(fis)) {
			return (Field) ois.readObject();
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
}

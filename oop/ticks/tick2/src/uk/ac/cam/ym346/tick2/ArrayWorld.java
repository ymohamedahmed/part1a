package uk.ac.cam.ym346.tick2;

public class ArrayWorld extends World {

	private boolean[][] mWorld;

	public ArrayWorld(String serial) {
		super(serial);
		mWorld = new boolean[getHeight()][getWidth()];
		getPattern().initialise(this);
	}

	@Override
	protected void nextGenerationImpl() {
		boolean[][] nextGeneration = new boolean[mWorld.length][];
		for (int y = 0; y < mWorld.length; ++y) {
			nextGeneration[y] = new boolean[mWorld[y].length];
			for (int x = 0; x < mWorld[y].length; ++x) {
				boolean nextCell = computeCell(x, y);
				nextGeneration[y][x] = nextCell;
			}
		}
		mWorld = nextGeneration;
	}

	@Override
	boolean getCell(int col, int row) {
		if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
			return false;
		}
		return mWorld[row][col];
	}

	@Override
	void setCell(int col, int row, boolean living) {
		if (!(row < 0 || row >= getHeight() || col < 0 || col >= getWidth())) {
			mWorld[row][col] = living;
		}
	}

}

package uk.ac.cam.rkh23.oop.tick5;

public class ArrayWorld extends World implements Cloneable {

	private boolean[][] mWorld;
	private boolean[] mDeadRow;

	public ArrayWorld(Pattern pattern) throws PatternFormatException {
		super(pattern);
		mWorld = new boolean[getHeight()][getWidth()];
		mDeadRow = new boolean[getWidth()];
		getPattern().initialise(this);
		updateToDeadRow();
	}

	public ArrayWorld(String serial) throws PatternFormatException {
		super(serial);
		mWorld = new boolean[getHeight()][getWidth()];
		mDeadRow = new boolean[getWidth()];
		getPattern().initialise(this);
		updateToDeadRow();
	}

	private void updateToDeadRow() {
		for (int i = 0; i < mWorld.length; i++) {
			if (deadRow(mWorld[i])) {
				mWorld[i] = mDeadRow;
			}
		}
	}

	private boolean deadRow(boolean[] row) {
		for (boolean b : row) {
			if (b) {
				return false;
			}
		}
		return true;
	}

	public ArrayWorld(ArrayWorld toCopy) {
		super(toCopy);
		mWorld = new boolean[toCopy.mWorld.length][toCopy.mWorld[0].length];
		for (int i = 0; i < mWorld.length; i++) {
			for (int j = 0; j < mWorld[i].length; j++) {
				mWorld[i][j] = toCopy.mWorld[i][j];
			}
		}
		mDeadRow = toCopy.mDeadRow;
		updateToDeadRow();
	}

	public Object clone() throws CloneNotSupportedException {
		ArrayWorld clone = (ArrayWorld) super.clone();
		clone.mWorld = new boolean[mWorld.length][mWorld[0].length];
		for (int i = 0; i < mWorld.length; i++) {
			for (int j = 0; j < mWorld[i].length; j++) {
				clone.mWorld[i][j] = mWorld[i][j];
			}
		}
		clone.mDeadRow = mDeadRow;
		clone.updateToDeadRow();
		return clone;
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
	public boolean getCell(int col, int row) {
		if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
			return false;
		}
		return mWorld[row][col];
	}

	@Override
	public void setCell(int col, int row, boolean living) {
		if (!(row < 0 || row >= getHeight() || col < 0 || col >= getWidth())) {
			mWorld[row][col] = living;
		}
	}

}

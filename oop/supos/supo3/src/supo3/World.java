package supo3;

public abstract class World {
	private int mGeneration;
	private Pattern mPattern;

	public World(String pattern) throws PatternFormatException {
			this.mPattern = new Pattern(pattern);
	}

	public World(Pattern pattern) throws PatternFormatException {
		this.mPattern = pattern;
	}

	public int getWidth() {
		return mPattern.getWidth();
	}

	public int getHeight() {
		return mPattern.getHeight();
	}

	public int getGenerationCount() {
		return mGeneration;
	}

	protected void incrementGenerationCount() {
		mGeneration++;
	}

	protected Pattern getPattern() {
		return mPattern;
	}

	public void nextGeneration() {
		nextGenerationImpl();
		mGeneration++;
	}

	protected abstract void nextGenerationImpl();

	abstract boolean getCell(int col, int row);

	abstract void setCell(int col, int row, boolean living);

	protected int countNeighbours(int col, int row) {
		int count = 0;
		for (int x = col - 1; x <= col + 1; x++) {
			for (int y = row - 1; y <= row + 1; y++) {
				if (!(x == col && y == row)) {
					if (getCell(x, y)) {
						count++;
					}
				}
			}
		}
		return count;
	}

	protected boolean computeCell(int col, int row) {
		// liveCell is true if the cell at position (col,row) in world is live
		boolean liveCell = getCell(col, row);

		// neighbours is the number of live neighbours to cell (col,row)
		int neighbours = countNeighbours(col, row);

		// we will return this value at the end of the method to indicate whether
		// cell (col,row) should be live in the next generation
		boolean nextCell = false;

		// A live cell with less than two neighbours dies (underpopulation)
		if (neighbours < 2) {
			nextCell = false;
		}

		// A live cell with two or three neighbours lives (a balanced population)
		// TODO: write a if statement to check neighbours and update nextCell
		if (liveCell && (neighbours == 2 || neighbours == 3)) {
			nextCell = true;
		}
		// A live cell with with more than three neighbours dies (overcrowding)
		// TODO: write a if statement to check neighbours and update nextCell
		if (liveCell && neighbours > 3) {
			nextCell = false;
		}
		// A dead cell with exactly three live neighbours comes alive
		// TODO: write a if statement to check neighbours and update nextCell
		if (!liveCell && neighbours == 3) {
			nextCell = true;
		}
		return nextCell;
	}

}

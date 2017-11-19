package uk.ac.cam.ym346.tick2;

public class PackedWorld extends World {
	private long mWorld;

	public PackedWorld(String pattern) throws Exception {
		super(pattern);
		getPattern().initialise(this);
		if (getPattern().getWidth() * getPattern().getHeight() > 64) {
			throw new Exception("World too big for packed long");
		}

	}

	@Override
	protected void nextGenerationImpl() {
		long newWorld = mWorld;
		for (int col = 0; col < getWidth(); col++) {
			for (int row = 0; row < getHeight(); row++) {
				newWorld = setCellLong(newWorld, col, row, computeCell(col, row));
			}
		}
		mWorld = newWorld;
	}

	public void setWorld(long world) {
		mWorld = world;
	}

	@Override
	boolean getCell(int col, int row) {
		int position = (row * getWidth()) + col;
		long check = (mWorld >>> position) & 1;
		return check == 1;
	}

	@Override
	void setCell(int col, int row, boolean living) {
		mWorld = setCellLong(mWorld, col, row, living);
	}

	long setCellLong(long world, int col, int row, boolean living) {
		int position = (row * getWidth()) + col;
		if (living) {
			world |= (1L << position);
		} else {
			world &= ~(1L << position);
		}
		return world;
	}

}

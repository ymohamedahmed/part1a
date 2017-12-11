package uk.ac.cam.rkh23.oop.tick5;

public class PackedWorld extends World implements Cloneable {
	private long mWorld;

	public PackedWorld(Pattern pattern) throws PatternFormatException {
		super(pattern);
		getPattern().initialise(this);
	}

	public PackedWorld(String pattern) throws Exception {
		super(pattern);
		getPattern().initialise(this);
		if (getPattern().getWidth() * getPattern().getHeight() > 64) {
			throw new Exception("World too big for packed long");
		}

	}

	public PackedWorld(PackedWorld toCopy) {
		super(toCopy);
		mWorld = toCopy.mWorld;
	}

	public Object clone() throws CloneNotSupportedException {
		PackedWorld clone = (PackedWorld) super.clone();
		return clone;
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
	public boolean getCell(int col, int row) {
		int position = (row * getWidth()) + col;
		long check = (mWorld >>> position) & 1;
		return check == 1;
	}

	@Override
	public void setCell(int col, int row, boolean living) {
		mWorld = setCellLong(mWorld, col, row, living);
	}

	public long setCellLong(long world, int col, int row, boolean living) {
		int position = (row * getWidth()) + col;
		if (living) {
			world |= (1L << position);
		} else {
			world &= ~(1L << position);
		}
		return world;
	}

}

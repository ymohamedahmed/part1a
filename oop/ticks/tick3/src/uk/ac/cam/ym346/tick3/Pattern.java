package uk.ac.cam.ym346.tick3;

public class Pattern implements Comparable<Pattern> {

	private String mName;
	private String mAuthor;
	private int mWidth;
	private int mHeight;
	private int mStartCol;
	private int mStartRow;
	private String mCells;

	public String getName() {
		return mName;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public int getWidth() {
		return mWidth;
	}

	public int getHeight() {
		return mHeight;
	}

	public int getStartColumn() {
		return mStartCol;
	}

	public int getStartRow() {
		return mStartRow;
	}

	public String getCells() {
		return mCells;
	}

	public Pattern(String format) throws PatternFormatException {
		String[] input = format.split(":");
		if (format.equals("")) {
			throw new PatternFormatException("Please specify a pattern.");
		}
		if (input.length != 7) {
			throw new PatternFormatException(
					"Invalid pattern format: Incorrect number of fields in pattern (found " + input.length + ").");
		}
		mName = input[0];
		mAuthor = input[1];
		try {
			mWidth = Integer.parseInt(input[2]);
		} catch (NumberFormatException e) {
			throw new PatternFormatException(
					"Invalid pattern format: Could not interpret the width field as a number ('" + input[2]
							+ "' given).");
		}
		try {
			mHeight = Integer.parseInt(input[3]);
		} catch (NumberFormatException e) {
			throw new PatternFormatException(
					"Invalid pattern format: Could not interpret the height field as a number ('" + input[3]
							+ "' given).");
		}
		try {
			mStartCol = Integer.parseInt(input[4]);
		} catch (NumberFormatException e) {
			throw new PatternFormatException(
					"Invalid pattern format: Could not interpret the startX field as a number ('" + input[4]
							+ "' given).");
		}
		try {
			mStartRow = Integer.parseInt(input[5]);
		} catch (NumberFormatException e) {
			throw new PatternFormatException(
					"Invalid pattern format: Could not interpret the startY field as a number ('" + input[5]
							+ "' given).");
		}
		mCells = input[6];
	}

	public void initialise(World world) throws PatternFormatException {
		String[] rows = mCells.split(" ");
		for (int i = 0; i < rows.length; i++) {
			char[] row = rows[i].toCharArray();
			for (int x = 0; x < row.length; x++) {
				try {
					System.out.println(String.valueOf(row[x]));
					int val = Integer.parseInt(String.valueOf(row[x]));
					world.setCell(x + mStartCol, i + mStartRow, val == 1);
				} catch (NumberFormatException e) {
					System.out.println("error");
					throw new PatternFormatException("Invalid pattern format: Malformed pattern " + mCells);
				}
			}
		}

	}

	@Override
	public int compareTo(Pattern p) {
		return mName.compareTo(p.getName());
	}
}

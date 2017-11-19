package uk.ac.cam.ym346.tick2;

public class GameOfLife {
	private World mWorld;

	public GameOfLife(World w) {
		mWorld = w;
	}

	public void play() throws java.io.IOException {
		int userResponse = 0;
		while (userResponse != 'q') {
			print();
			userResponse = System.in.read();
			mWorld.nextGeneration();
		}
	}

	public void print() {
		System.out.println("- " + mWorld.getGenerationCount());
		for (int row = 0; row < mWorld.getHeight(); row++) {
			for (int col = 0; col < mWorld.getWidth(); col++) {
				System.out.print(mWorld.getCell(col, row) ? "#" : "_");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) throws Exception {
		World w = null;
		if (args.length == 1) {
			w = new ArrayWorld(args[0]);
		} else if (args.length == 2 && args[0].equals("--array")) {
			w = new ArrayWorld(args[1]);
		} else if (args.length == 2 && args[0].equals("--packed")) {
			w = new PackedWorld(args[1]);
		}

		GameOfLife gol = new GameOfLife(w);
		gol.play();
	}
}

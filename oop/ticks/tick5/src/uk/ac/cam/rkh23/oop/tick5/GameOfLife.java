package uk.ac.cam.rkh23.oop.tick5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GameOfLife {
	private World mWorld;
	private PatternStore mStore;
	private ArrayList<World> mCachedWorlds;

	public GameOfLife(PatternStore ps) {
		mStore = ps;
		mCachedWorlds = new ArrayList<World>();
	}

	public void play() throws IOException, PatternFormatException, CloneNotSupportedException {

		String response = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int generationInCache = 0;
		System.out.println("Please select a pattern to play (l to list:");
		while (!response.equals("q")) {
			response = in.readLine();
			System.out.println(response);
			if (response.equals("f")) {
				if (mWorld == null)
					System.out.println("Please select a pattern to play (l to list):");
				else {
					if (mWorld.getGenerationCount() < generationInCache) {
						mWorld = mCachedWorlds.get(mWorld.getGenerationCount() + 1);
					} else {
						World copy = copyWorld(true);
						copy.nextGeneration();
						mCachedWorlds.add(copy);
						mWorld = copy;
						generationInCache++;
					}
					print();
				}
			} else if (response.equals("l")) {
				List<Pattern> names = mStore.getPatternsNameSorted();
				int i = 0;
				for (Pattern p : names) {
					System.out.println(i + " " + p.getName() + "  (" + p.getAuthor() + ")");
					i++;
				}
			} else if (response.startsWith("p")) {
				List<Pattern> names = mStore.getPatternsNameSorted();
				// TODO: Extract the integer after the p in response
				int x = Integer.valueOf(String.valueOf(response.substring(2)));
				// TODO: Get the associated pattern
				Pattern pattern = names.get(x);
				// TODO: Initialise mWorld using PackedWorld or ArrayWorld based
				// on pattern world size
				if (pattern.getWidth() * pattern.getHeight() > 64) {
					mWorld = new ArrayWorld(pattern);
				} else {
					mWorld = new PackedWorld(pattern);
				}
				pattern.initialise(mWorld);
				mCachedWorlds.add(mWorld);
				print();
			} else if (response.equals("b")) {
				mWorld = mCachedWorlds.get(mWorld.getGenerationCount()-1);
				print();

			}
		}
	}

	public static void main(String args[]) throws IOException, PatternFormatException, CloneNotSupportedException {

		if (args.length != 1) {
			System.out.println("Usage: java GameOfLife <path/url to store>");
			return;
		}

		try {
			PatternStore ps = new PatternStore(args[0]);
			GameOfLife gol = new GameOfLife(ps);
			gol.play();
		} catch (IOException ioe) {
			System.out.println("Failed to load pattern store");
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

	private World copyWorld(boolean useCloning) throws CloneNotSupportedException {
		if (!useCloning) {
			if (mWorld instanceof PackedWorld) {
				return new PackedWorld((PackedWorld) mWorld);
			} else if (mWorld instanceof ArrayWorld) {
				return new ArrayWorld((ArrayWorld) mWorld);
			}
		} else {
			return (World) mWorld.clone();
		}
		return null;
	}
}

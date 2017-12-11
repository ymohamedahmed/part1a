package uk.ac.cam.rkh23.oop.tick5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PatternStore {
	private List<Pattern> mPatterns = new LinkedList<>();
	private Map<String, List<Pattern>> mMapAuths = new HashMap<>();
	private Map<String, Pattern> mMapName = new HashMap<>();

	public PatternStore(String source) throws IOException {
		if (source.startsWith("http://")) {
			loadFromURL(source);
		} else {
			loadFromDisk(source);
		}
	}

	public PatternStore(Reader source) throws IOException {
		load(source);
	}

	private void load(Reader r) throws IOException {
		// TODO: read each line from the reader and print it to the screen
		BufferedReader b = new BufferedReader(r);
		String line = b.readLine();
		while (line != null) {
			System.out.println(line);
			try {
				Pattern pattern = new Pattern(line);
				mPatterns.add(pattern);
				if (mMapAuths.get(pattern.getAuthor()) != null) {
					mMapAuths.get(pattern.getAuthor()).add(pattern);
				} else {
					List<Pattern> authPat = new LinkedList<>();
					authPat.add(pattern);
					mMapAuths.put(pattern.getAuthor(), authPat);
				}
				mMapName.put(pattern.getName(), pattern);
			} catch (PatternFormatException e) {
				System.out.println(line);
			}
			line = b.readLine();
		}
	}

	private void loadFromURL(String url) throws IOException {
		// TODO: Create a Reader for the URL and then call load on it
		URL destination = new URL(url);
		URLConnection conn = destination.openConnection();
		Reader r = new java.io.InputStreamReader(conn.getInputStream());
		load(r);
	}

	private void loadFromDisk(String filename) throws IOException {
		// TODO: Create a Reader for the file and then call load on it
		Reader r = new FileReader(filename);
		load(r);
	}

	public List<Pattern> getPatternsNameSorted() {
		// TODO: Get a list of all patterns sorted by name
		Collections.sort(mPatterns);
		return new LinkedList<Pattern>(mPatterns);
	}

	public List<Pattern> getPatternsAuthorSorted() {
		// TODO: Get a list of all patterns sorted by author then name
		Collections.sort(mPatterns, new Comparator<Pattern>() {
			public int compare(Pattern p1, Pattern p2) {
				int authComp = (p1.getAuthor()).compareTo(p2.getAuthor());
				int nameComp = (p1.getName()).compareTo(p2.getName());
				if (authComp != 0) {
					return authComp;
				} else {
					return nameComp;
				}
			}
		});
		return new LinkedList<Pattern>(mPatterns);
	}

	public List<Pattern> getPatternsByAuthor(String author) throws PatternNotFound {
		// TODO: return a list of patterns from a particular author sorted by name
		List<Pattern> result = mMapAuths.get(author);
		if (result == null) {
			throw new PatternNotFound();
		}
		Collections.sort(result);
		return new LinkedList<Pattern>(result);
	}

	public Pattern getPatternByName(String name) throws PatternNotFound {
		// TODO: Get a particular pattern by name
		Pattern result = mMapName.get(name);
		if (result == null) {
			throw new PatternNotFound();
		}
		return mMapName.get(name);

	}

	public List<String> getPatternAuthors() {
		// TODO: Get a sorted list of all pattern authors in the store
		List<String> authors = new LinkedList<>(mMapAuths.keySet());
		Collections.sort(authors);
		return new LinkedList<String>(authors);
	}

	public List<String> getPatternNames() {
		// TODO: Get a list of all pattern names in the store,
		// sorted by name
		List<String> authors = new LinkedList<>(mMapName.keySet());
		Collections.sort(authors);
		return new LinkedList<String>(authors);
	}

}

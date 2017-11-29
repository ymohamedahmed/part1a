package supo3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StudentMarks {
	// TreeMap used since this way they are permanently stored in alphabetical order
	private Map<String, Float> mMapNameMark = new TreeMap<>();
	private Map<Float, String> mMapMarkName = new TreeMap<>();

	public List<String> listOfStudents() {
		return new ArrayList<String>(mMapNameMark.keySet());
	}

	public List<String> listOfTopPercent(float p) {
		List<String> result = new ArrayList<>();
		// Find a list of all the marks scored
		List<Float> listOfMarks = listOfMarks();
		// Use the percentage to calculate the index to start from in the sorted list
		int index = (int) Math.floor(listOfMarks.size() - (listOfMarks.size() * p / 100));
		// Add all the names of the relevant students
		for (int i = index; i < listOfMarks.size(); i++) {
			result.add(mMapMarkName.get(listOfMarks.get(i)));
		}
		return result;
	}

	private List<Float> listOfMarks() {
		return new ArrayList<Float>(mMapMarkName.keySet());
	}

	public float medianMark() {
		int median = listOfMarks().size() / 2;
		if (listOfMarks().size() % 2 == 0) {
			return (listOfMarks().get(median - 1) + listOfMarks().get(median)) / 2;
		} else {
			return listOfMarks().get(median);
		}
	}

	public void add(String name, float mark) {
		mMapNameMark.put(name, mark);
		mMapMarkName.put(mark, name);
	}

	public static void main(String[] args) {
		StudentMarks s = new StudentMarks();
		s.add("A", 98.1f);
		s.add("B", 100f);
		s.add("C", 60f);
		s.add("D", 75f);
		System.out.println(s.listOfStudents());
		System.out.println(s.listOfMarks());
		System.out.println(s.listOfTopPercent(50));
		System.out.println(s.medianMark());

	}

}

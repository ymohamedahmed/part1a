package uk.ac.cam.cl.mlrd.testing;

import uk.ac.cam.cl.mlrd.exercises.social_networks.Exercise10;
import uk.ac.cam.cl.mlrd.exercises.social_networks.Exercise12;
import uk.ac.cam.cl.mlrd.exercises.social_networks.IExercise10;
import uk.ac.cam.cl.mlrd.exercises.social_networks.IExercise12;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

//TODO: Replace with your packages.

public class Exercise12Tester {
    static final Path graphFile = Paths.get("data/network_files/supo-1.edges");

    public static void main(String[] args) throws IOException {
	IExercise10 ex10 = new Exercise10();
	IExercise12 ex12 = new Exercise12();
	Map<Integer, Set<Integer>> graph = ex10.loadGraph(graphFile);

	int numEdges = ex12.getNumberOfEdges(graph);
	System.out.println("Number of edges: "+numEdges);
	System.out.println();

	List<Set<Integer>> components = ex12.getComponents(graph);
	System.out.println("Components:");
	System.out.println(components);
	System.out.println();
	
	Map<Integer, Map<Integer, Double>> edgeBetweennesses = ex12.getEdgeBetweenness(graph);
	System.out.println("Edge betweennesses:");
	System.out.println(edgeBetweennesses);
	System.out.println();

	List<Set<Integer>> clustering = ex12.GirvanNewman(graph, 20);
	System.out.println("Clusters:");
	System.out.println(clustering);
	System.out.println();
    }
}

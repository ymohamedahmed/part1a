package uk.ac.cam.cl.mlrd.exercises.social_networks;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Exercise10 implements IExercise10 {
    @Override
    public Map<Integer, Set<Integer>> loadGraph(Path graphFile) throws IOException {
        BufferedReader br = Files.newBufferedReader(graphFile);
        String line;
        Map<Integer, Set<Integer>> result = new HashMap<>();
        while((line=br.readLine()) != null){
            String[] split = line.split(" ");
            int key = Integer.valueOf(split[0]);
            int value = Integer.valueOf(split[1]);
            if(result.containsKey(key)){
                result.get(key).add(value);
            }else{
                Set<Integer> nodes = new HashSet<>();
                nodes.add(value);
                result.put(key, nodes);
            }
            if(result.containsKey(value)){
                result.get(value).add(key);
            }else{
                Set<Integer> nodes = new HashSet<>();
                nodes.add(key);
                result.put(value, nodes);
            }
        }
        return result;
    }

    @Override
    public Map<Integer, Integer> getConnectivities(Map<Integer, Set<Integer>> graph) {
        Map<Integer, Integer> result = new HashMap<>();
        for(int key : graph.keySet()){
            result.put(key, graph.get(key).size());
        }
        return result;
    }

    @Override
    public int getDiameter(Map<Integer, Set<Integer>> graph) {
        int diameter = 0;
        for(int root : graph.keySet()){
            HashSet<Integer> visited = new HashSet<>();
            Deque<Integer> toExplore = new ArrayDeque<>();
            toExplore.add(root);
            visited.add(root);
            int[] distance = new int[graph.size()+1];
            int last = root;
            distance[root] = 0;

            while(!toExplore.isEmpty()) {
                int v = toExplore.pollLast();
                for (int w : graph.get(v)) {
                    if(!visited.contains(w)){
                        toExplore.addFirst(w);
                        visited.add(w);
                        last = w;
                        distance[w] = distance[v] + 1;
                    }
                }
            }
            if(distance[last] > diameter){
                diameter = distance[last];
            }
        }
        return diameter;
    }
}

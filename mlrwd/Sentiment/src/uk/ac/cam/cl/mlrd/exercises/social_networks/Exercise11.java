package uk.ac.cam.cl.mlrd.exercises.social_networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Exercise11 implements IExercise11 {
    @Override
    public Map<Integer, Double> getNodeBetweenness(Path graphFile) throws IOException {
        Map<Integer, Set<Integer>> graph = loadGraph(graphFile);
        Map<Integer, Double> cb = new HashMap<>();
        Set<Integer> V = new HashSet<>();
        for(int v : graph.keySet()){
            V.add(v);
            V.addAll(graph.get(v));
        }
        for(int i : V){
            cb.put(i, 0.0);
        }
        
        for(int s : V) {
            Queue<Integer> queue = new LinkedList<>();
            Stack<Integer> stack = new Stack<>();
            HashMap<Integer, Double> dist = new HashMap<>();
            HashMap<Integer, ArrayList<Integer>> pred = new HashMap<>();

            HashMap<Integer, Integer> sigma = new HashMap<>();
            HashMap<Integer, Double> delta = new HashMap<>();
            // initialization
            for (int i : V) {
                pred.put(i, new ArrayList<>());
                dist.put(i, -1.0);
                sigma.put(i, 0);
            }
            dist.replace(s, 0.0);
            sigma.replace(s, 1);
            queue.add(s);

            while(!queue.isEmpty()){
                int v = queue.remove();
                stack.push(v);
                for(int w : graph.get(v)){
                    if(dist.get(w) < 0){
                        queue.add(w);
                        dist.replace(w, dist.get(v) + 1.0);
                    }
                    if(dist.get(w) == (dist.get(v) + 1)){
                        sigma.replace(w, sigma.get(w) + sigma.get(v));
                        pred.get(w).add(v);
                    }
                }
            }
            for(int v : V){
                delta.put(v, 0.0);
            }
            while(!stack.isEmpty()){
                int w = stack.pop();
                for(int v : pred.get(w)){
                    double ratio = (double)sigma.get(v)/sigma.get(w);
                    double factor = 1 + delta.get(w);
                    delta.replace(v, delta.get(v) + (ratio*factor));
                }
                if(w != s){
                    cb.replace(w, cb.get(w) + delta.get(w));
                }
            }
        }

        for(int v : V){
            cb.replace(v, cb.get(v)/2.0);
        }
        return cb;
    }
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
}

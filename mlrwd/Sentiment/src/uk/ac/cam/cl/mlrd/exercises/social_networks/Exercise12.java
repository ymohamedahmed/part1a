package uk.ac.cam.cl.mlrd.exercises.social_networks;

import java.util.*;

public class Exercise12 implements IExercise12 {
    @Override
    public List<Set<Integer>> GirvanNewman(Map<Integer, Set<Integer>> graph, int minimumComponents) {
        boolean complete = false;
        double threshold = Math.pow(10, -6);
        while(!complete){
            int numComponents = getComponents(graph).size();
            int numEdges = getNumberOfEdges(graph);
            complete = numComponents >= minimumComponents || numEdges <= 0;

            Map<Integer, Map<Integer, Double>> betweeness = getEdgeBetweenness(graph);
            Map<Integer, Integer> edges = new HashMap<>();
            double maxBetweeness = Double.NEGATIVE_INFINITY;
            for(int startpoint : graph.keySet()){
                for(int endpoint : graph.keySet()){
                    double edgeBetweeness = betweeness.get(startpoint).get(endpoint);
                    // Check if new maximum betweeness has been found
                    if(edgeBetweeness > maxBetweeness + threshold){
                        maxBetweeness = edgeBetweeness;
                        edges.clear();
                        edges.put(startpoint, endpoint);
                    }

                    // Check if another edge meets the minimum requirement
                    else if(Math.abs(maxBetweeness-edgeBetweeness)<threshold){
                        edges.put(startpoint, endpoint);
                    }
                }
            }
            for(int startpoint : edges.keySet()){
                graph.get(startpoint).remove(edges.get(startpoint));
                graph.get(edges.get(startpoint)).remove(startpoint);
            }
        }
        return getComponents(graph);
    }

    @Override
    public int getNumberOfEdges(Map<Integer, Set<Integer>> graph) {
        int sum = 0;
        for(int i : graph.keySet()){
            sum += graph.get(i).size();
        }

        return sum/2;
    }

    @Override
    public List<Set<Integer>> getComponents(Map<Integer, Set<Integer>> graph) {
        List<Set<Integer>> result = new ArrayList<>();
        Set<Integer> vertices = graph.keySet();
        Iterator<Integer> iter = vertices.iterator();
        Set<Integer> seen = new HashSet<>();
        // depth first search
        boolean finished = false;
        while(!finished) {
            int v = 0;
            for(int i : vertices){
                if(!seen.contains(i)){
                    v = i;
                    break;
                }
            }
            Stack<Integer> s = new Stack<>();
            HashSet<Integer> discovered = new HashSet<>();
            s.push(v);
            while(!s.isEmpty()){
                int x = s.pop();
                if(!discovered.contains(x)){
                    discovered.add(x);
                    seen.add(x);
                    if(graph.containsKey(x)) {
                        for (int neighbour : graph.get(x)) {
                            s.push(neighbour);
                        }
                    }
                }
            }
            result.add(discovered);
            finished = true;
            for(int i : vertices){
                if(!seen.contains(i)){
                    finished = false;
                }
            }
        }
        return result;
    }

    @Override
    public Map<Integer, Map<Integer, Double>> getEdgeBetweenness(Map<Integer, Set<Integer>> graph) {
        Map<Integer, Map<Integer,Double>> cb = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();
        HashMap<Integer, Double> dist = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> pred = new HashMap<>();

        HashMap<Integer, Integer> sigma = new HashMap<>();
        HashMap<Integer, Double> delta = new HashMap<>();
        for(int startpoint : graph.keySet()){
            Map<Integer, Double> temp = new HashMap<>();
            for(int endpoint : graph.keySet()){
                temp.put(endpoint, 0.0);
            }
            cb.put(startpoint, temp);
        }

        for(int s : graph.keySet()) {
            // initialization
            for (int i : graph.keySet()) {
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
            for(int v : graph.keySet()){
                delta.put(v, 0.0);
            }
            while(!stack.isEmpty()){
                int w = stack.pop();
                for(int v : pred.get(w)){
                    double ratio = (double)sigma.get(v)/sigma.get(w);
                    double factor = 1 + delta.get(w);
                    delta.replace(v, delta.get(v) + (ratio*factor));
                    cb.get(v).put(w, cb.get(v).get(w) + (ratio*factor));
                }
            }
        }

        return cb;
    }
}

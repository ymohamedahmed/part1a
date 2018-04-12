package uk.ac.cam.ym346.Algorithms.Tick3;

import java.io.IOException;
import java.lang.annotation.Target;
import java.net.URL;
import java.util.*;

public class Graph extends GraphBase {
    private int[][] flow;
    public Graph(URL url) throws IOException {
        super(url);
        flow = new int[getNumVertices()][getNumVertices()];
    }
    public Graph(String file) throws IOException {
        super(file);
        flow = new int[getNumVertices()][getNumVertices()];
    }

    public Graph(int[][] adj){
        super(adj);
        flow = new int[getNumVertices()][getNumVertices()];
    }

    @Override
    public List<Integer> getFewestEdgesPath(int src, int target) throws TargetUnreachable {
        HashSet<Integer> seen = new HashSet<>();
        HashMap<Integer, Integer> comeFrom = new HashMap<>();
        seen.add(src);
        Queue<Integer> toExplore = new ArrayDeque<>();
        toExplore.add(src);
        while(!toExplore.isEmpty()){
            int v = toExplore.poll();
            for(int neighbour = 0; neighbour < getNumVertices(); neighbour++){
                if((mAdj[v][neighbour]!=0 && flow[v][neighbour] < mAdj[v][neighbour]) || (mAdj[neighbour][v] != 0 && flow[neighbour][v] > 0)){
                    if(!seen.contains(neighbour)){
                        toExplore.add(neighbour);
                        seen.add(neighbour);
                        comeFrom.put(neighbour, v);
                    }
                }
            }
        }
        if(!comeFrom.containsKey(target)){
            throw new TargetUnreachable();
        }else{
            List<Integer> path = new ArrayList<>();
            path.add(target);
            while(comeFrom.get(path.get(path.size()-1)) != src){
                path.add(comeFrom.get(path.get(path.size()-1)));
            }
            path.add(src);
            Collections.reverse(path);
            return path;
        }
    }

    @Override
    public MaxFlowNetwork getMaxFlow(int s, int t) {
        int maxFlow = 0;
        while(true) {
            try {
                List<Integer> path = getFewestEdgesPath(s, t);
                int df = Integer.MAX_VALUE;
                for(int i = 0; i < path.size()-1; i++){
                    if(mAdj[path.get(i)][path.get(i+1)]!=0){
                        df = Math.min(df, mAdj[path.get(i)][path.get(i+1)]-flow[path.get(i)][path.get(i+1)]);
                    }else{
                        df = Math.min(df, flow[path.get(i+1)][path.get(i)]);
                    }
                }
                for(int i = 0; i < path.size()-1; i++){
                    if(mAdj[path.get(i)][path.get(i+1)]!=0){
                        flow[path.get(i)][path.get(i+1)] += df;
                    }else{
                        flow[path.get(i+1)][path.get(i)] -= df;
                    }
                }
                maxFlow += df;
            } catch (TargetUnreachable targetUnreachable) {
                break;
            }
        }
        MaxFlowNetwork mfn = new MaxFlowNetwork(maxFlow, this);
        return mfn;
    }
}

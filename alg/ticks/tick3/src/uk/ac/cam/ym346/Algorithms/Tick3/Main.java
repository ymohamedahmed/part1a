package uk.ac.cam.ym346.Algorithms.Tick3;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Graph g = new Graph("test3.txt");
           MaxFlowNetwork mfn =  g.getMaxFlow(0,5);
           int flow = mfn.getFlow();
            System.out.println("flow = " + flow);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

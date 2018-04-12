package uk.ac.cam.cl.mlrd.exercises.markov_models;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Exercise8 implements IExercise8 {
    @Override
    public List<DiceType> viterbi(HiddenMarkovModel<DiceRoll, DiceType> model, List<DiceRoll> observedSequence) {
        List<DiceType> result = new ArrayList<>(observedSequence.size());
        List<Map<DiceType, DiceType>> phi = new ArrayList<>();
        List<Map<DiceType, Double>> delta = new ArrayList<>();

        DiceType[] dts = {DiceType.FAIR, DiceType.WEIGHTED};
        for (int t = 0; t < observedSequence.size(); t++) {
            DiceRoll obs = observedSequence.get(t);

            Map<DiceType, Double> deltaMap = new HashMap<>();
            Map<DiceType, DiceType> phiMap = new HashMap<>();

            if (obs == DiceRoll.START) {
                for (DiceType dt : DiceType.values()) {
                    deltaMap.put(dt, 0.0);
                }
                deltaMap.replace(DiceType.START, 1.0);
            } else {
                for (DiceType dt : DiceType.values()) {
                    double max = Double.NEGATIVE_INFINITY;
                    DiceType maxState = null;
                    double b = model.getEmissions(dt).get(obs);
                    for (DiceType i : dts) {
                        double del = delta.get(t - 1).get(i);
                        double a = model.getTransitions(i).get(dt);
                        double score = del + Math.log(a) + Math.log(b);
                        if (score >= max) {
                            max = score;
                            maxState = i;
                        }
                    }
                    deltaMap.put(dt, max);
                    phiMap.put(dt, maxState);
                }
            }
            delta.add(deltaMap);
            phi.add(phiMap);
        }
        DiceType prev = phi.get(observedSequence.size() - 2).get(DiceType.END);
        result.add(DiceType.END);
        for (int t = observedSequence.size() - 2; t > 0; t--) {
            result.add(prev);
            prev = phi.get(t).get(prev);
        }
        result.add(DiceType.START);
        Collections.reverse(result);
        return result;
    }

    @Override
    public Map<List<DiceType>, List<DiceType>> predictAll(HiddenMarkovModel<DiceRoll, DiceType> model, List<Path> testFiles) throws IOException {
        Map<List<DiceType>, List<DiceType>> result = new HashMap<>();
        for (Path p : testFiles) {
            HMMDataStore<DiceRoll, DiceType> file = HMMDataStore.loadDiceFile(p);
            List<DiceType> pred = viterbi(model, file.observedSequence);
            result.put(file.hiddenSequence, pred);
        }
            return result;
    }

    @Override
    public double precision(Map<List<DiceType>, List<DiceType>> true2PredictedMap) {
        double total = 0;
        double correct = 0;
        for(List<DiceType> hidden : true2PredictedMap.keySet()){
            List<DiceType> predicted = true2PredictedMap.get(hidden);
            for(int i = 0; i < hidden.size(); i++){
                if(predicted.get(i) == DiceType.WEIGHTED){
                    total++;
                    if(predicted.get(i) == hidden.get(i)){
                        correct++;
                    }
                }
            }
        }
        return (double)correct / (double)total ;
    }

    @Override
    public double recall(Map<List<DiceType>, List<DiceType>> true2PredictedMap) {
        double total = 0;
        double correct = 0;
        for(List<DiceType> key : true2PredictedMap.keySet()){
            List<DiceType> value = true2PredictedMap.get(key);
            for(int i = 0; i < key.size(); i++){
                if(key.get(i) == DiceType.WEIGHTED){
                    total++;
                    if(value.get(i) == key.get(i)){
                        correct++;
                    }
                }
            }
        }
        return (double) correct / (double) total ;
    }

    @Override
    public double fOneMeasure(Map<List<DiceType>, List<DiceType>> true2PredictedMap) {
        double precision = precision(true2PredictedMap);
        double recall = recall(true2PredictedMap);

        return 2 * (precision*recall)/(precision+recall);
    }
}

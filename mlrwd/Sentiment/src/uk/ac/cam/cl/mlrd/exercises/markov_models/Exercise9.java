package uk.ac.cam.cl.mlrd.exercises.markov_models;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Exercise9 implements IExercise9 {
    @Override
    public HiddenMarkovModel<AminoAcid, Feature> estimateHMM(List<HMMDataStore<AminoAcid, Feature>> sequencePairs) throws IOException {
        List<HMMDataStore<AminoAcid, Feature>> dataStore = sequencePairs;
        Map<Feature,Map<Feature, Double>> transitions = new HashMap<>();
        for(HMMDataStore<AminoAcid, Feature> store : dataStore){
            for(int i = 0; i < store.hiddenSequence.size()-1; i++){
                Feature prev = store.hiddenSequence.get(i);
                Feature next = store.hiddenSequence.get(i+1);
                if(transitions.containsKey(prev)){
                    transitions.get(prev).put(next, transitions.get(prev).get(next)==null ? 1.0 : transitions.get(prev).get(next)+1.0);
                }else{
                    Map<Feature, Double> map = new HashMap<>();
                    map.put(next, 1.0);
                    transitions.put(prev,map);
                }
            }
        }
        double[] countsTransition = new double[Feature.values().length];
        for(Feature d : Feature.values()){
            double result = 0;
            if(transitions.containsKey(d)){
                for (double count : transitions.get(d).values()) {
                    result += count;
                }
            }
            countsTransition[d.ordinal()] = result;
        }
        for(Feature d1 : Feature.values()){
            for(Feature d2 : Feature.values()){
                if(transitions.containsKey(d1)){
                    transitions.get(d1).put(d2, transitions.get(d1).get(d2) == null ? 0.0 : transitions.get(d1).get(d2)/countsTransition[d1.ordinal()]);
                }else{
                    Map<Feature, Double> map = new HashMap<>();
                    map.put(d2, 0.0);
                    transitions.put(d1, map);
                }
            }
        }
        Map<Feature, Map<AminoAcid, Double>> emission = new HashMap<>();
        for(HMMDataStore<AminoAcid, Feature> store : dataStore){
            for(int i = 0; i < store.hiddenSequence.size(); i++){
                Feature diceType = store.hiddenSequence.get(i);
                AminoAcid diceRoll = store.observedSequence.get(i);
                if(emission.containsKey(diceType)){
                    emission.get(diceType).put(diceRoll, emission.get(diceType).get(diceRoll) == null ? 1.0 : emission.get(diceType).get(diceRoll)+1.0);
                }else{
                    Map<AminoAcid, Double> map = new HashMap<>();
                    map.put(diceRoll, 1.0);
                    emission.put(diceType,map);
                }
            }
        }
        double[] countsEmission = new double[Feature.values().length];
        for(Feature d : Feature.values()){
            double result = 0;
            if(emission.containsKey(d)){
                for (double count : emission.get(d).values()) {
                    result += count;
                }
            }
            countsEmission[d.ordinal()] = result;
        }
        for(Feature d1 : Feature.values()){
            for(AminoAcid d2 : AminoAcid.values()){
                if(transitions.containsKey(d1)){
                    emission.get(d1).put(d2, emission.get(d1).get(d2) == null ? 0.0 : emission.get(d1).get(d2) / countsEmission[d1.ordinal()]);
                }else{
                    Map<AminoAcid, Double> map = new HashMap<>();
                    map.put(d2, 0.0);
                    emission.put(d1, map);
                }
            }
        }
        HiddenMarkovModel<AminoAcid, Feature> hmm = new HiddenMarkovModel<>(transitions, emission);
        return hmm;
    }

    @Override
    public List<Feature> viterbi(HiddenMarkovModel<AminoAcid, Feature> model, List<AminoAcid> observedSequence) {
        List<Feature> result = new ArrayList<>(observedSequence.size());
        List<Map<Feature, Feature>> phi = new ArrayList<>();
        List<Map<Feature, Double>> delta = new ArrayList<>();

        Feature[] dts = {Feature.INSIDE, Feature.MEMBRANE, Feature.OUTSIDE};
        for (int t = 0; t < observedSequence.size(); t++) {
            AminoAcid obs = observedSequence.get(t);

            Map<Feature, Double> deltaMap = new HashMap<>();
            Map<Feature, Feature> phiMap = new HashMap<>();

            if (obs == AminoAcid.START) {
                for (Feature dt : Feature.values()) {
                    deltaMap.put(dt, 0.0);
                }
                deltaMap.replace(Feature.START, 1.0);
            } else {
                for (Feature dt : Feature.values()) {
                    double max = Double.NEGATIVE_INFINITY;
                    Feature maxState = null;
                    double b = model.getEmissions(dt).get(obs);
                    for (Feature i : dts) {
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
        Feature prev = phi.get(observedSequence.size() - 1).get(Feature.END);
        result.add(Feature.END);
        for (int t = observedSequence.size() - 2; t > 0; t--) {
            result.add(prev);
            prev = phi.get(t).get(prev);
        }
        result.add(Feature.START);
        Collections.reverse(result);
        return result;
    }

    @Override
    public Map<List<Feature>, List<Feature>> predictAll(HiddenMarkovModel<AminoAcid, Feature> model, List<HMMDataStore<AminoAcid, Feature>> testSequencePairs) throws IOException {
        Map<List<Feature>, List<Feature>> result = new HashMap<>();
        for(HMMDataStore p : testSequencePairs){
            List<Feature> pred = viterbi(model, p.observedSequence);
            result.put(p.hiddenSequence, pred);
        }
        return result;
   }

    @Override
    public double precision(Map<List<Feature>, List<Feature>> true2PredictedMap) {
        double total = 0;
        double correct = 0;
        for(List<Feature> hidden : true2PredictedMap.keySet()){
            List<Feature> predicted = true2PredictedMap.get(hidden);
            for(int i = 0; i < hidden.size(); i++){
                if(predicted.get(i) == Feature.MEMBRANE){
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
    public double recall(Map<List<Feature>, List<Feature>> true2PredictedMap) {
        double total = 0;
        double correct = 0;
        for(List<Feature> key : true2PredictedMap.keySet()){
            List<Feature> value = true2PredictedMap.get(key);
            for(int i = 0; i < key.size(); i++){
                if(key.get(i) == Feature.MEMBRANE){
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
    public double fOneMeasure(Map<List<Feature>, List<Feature>> true2PredictedMap) {
        double precision = precision(true2PredictedMap);
        double recall = recall(true2PredictedMap);

        return 2 * (precision*recall)/(precision+recall);
    }
}

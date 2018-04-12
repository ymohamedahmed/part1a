package uk.ac.cam.cl.mlrd.exercises.markov_models;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;


public class Exercise7 implements IExercise7 {
    @Override
    public HiddenMarkovModel<DiceRoll, DiceType> estimateHMM(Collection<Path> sequenceFiles) throws IOException {
        List<HMMDataStore<DiceRoll, DiceType>> dataStore = HMMDataStore.loadDiceFiles(sequenceFiles);
        Map<DiceType,Map<DiceType, Double>> transitions = new HashMap<>();
        for(HMMDataStore<DiceRoll, DiceType> store : dataStore){
            for(int i = 0; i < store.hiddenSequence.size()-1; i++){
                DiceType prev = store.hiddenSequence.get(i);
                DiceType next = store.hiddenSequence.get(i+1);
                if(transitions.containsKey(prev)){
                    transitions.get(prev).put(next, transitions.get(prev).get(next)==null ? 1.0 : transitions.get(prev).get(next)+1.0);
                }else{
                    Map<DiceType, Double> map = new HashMap<>();
                    map.put(next, 1.0);
                    transitions.put(prev,map);
                }
            }
        }
        double[] countsTransition = new double[DiceType.values().length];
        for(DiceType d : DiceType.values()){
            double result = 0;
            if(transitions.containsKey(d)){
                for (double count : transitions.get(d).values()) {
                    result += count;
                }
            }
            countsTransition[d.ordinal()] = result;
        }
        for(DiceType d1 : DiceType.values()){
            for(DiceType d2 : DiceType.values()){
                if(transitions.containsKey(d1)){
                    transitions.get(d1).put(d2, transitions.get(d1).get(d2) == null ? 0.0 : transitions.get(d1).get(d2)/countsTransition[d1.ordinal()]);
                }else{
                    Map<DiceType, Double> map = new HashMap<>();
                    map.put(d2, 0.0);
                    transitions.put(d1, map);
                }
            }
        }
        Map<DiceType, Map<DiceRoll, Double>> emission = new HashMap<>();
        for(HMMDataStore<DiceRoll, DiceType> store : dataStore){
            for(int i = 0; i < store.hiddenSequence.size(); i++){
                DiceType diceType = store.hiddenSequence.get(i);
                DiceRoll diceRoll = store.observedSequence.get(i);
                if(emission.containsKey(diceType)){
                    emission.get(diceType).put(diceRoll, emission.get(diceType).get(diceRoll) == null ? 1.0 : emission.get(diceType).get(diceRoll)+1.0);
                }else{
                    Map<DiceRoll, Double> map = new HashMap<>();
                    map.put(diceRoll, 1.0);
                    emission.put(diceType,map);
                }
            }
        }
        double[] countsEmission = new double[DiceType.values().length];
        for(DiceType d : DiceType.values()){
            double result = 0;
            if(emission.containsKey(d)){
                for (double count : emission.get(d).values()) {
                    result += count;
                }
            }
            countsEmission[d.ordinal()] = result;
        }
        for(DiceType d1 : DiceType.values()){
            for(DiceRoll d2 : DiceRoll.values()){
                if(transitions.containsKey(d1)){
                    emission.get(d1).put(d2, emission.get(d1).get(d2) == null ? 0.0 : emission.get(d1).get(d2) / countsEmission[d1.ordinal()]);
                }else{
                    Map<DiceRoll, Double> map = new HashMap<>();
                    map.put(d2, 0.0);
                    emission.put(d1, map);
                }
            }
        }
        HiddenMarkovModel<DiceRoll, DiceType> hmm = new HiddenMarkovModel<>(transitions, emission);
        return hmm;
    }
}

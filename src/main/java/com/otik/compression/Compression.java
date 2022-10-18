package com.otik.compression;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Compression<T extends Comparable<? super T>> {
    private Map<T, Double> probability = new LinkedHashMap<>();
//    private Map<T,>

    public Compression(Map<T, Double> probability) {
        this.probability = probability;
    }

    private void sortProbability() {
        probability = probability.entrySet().stream()
                .sorted(Map.Entry.<T, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (k, v) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new));
    }

    private void defineCiphers() {
        Double sumProbabilityOfFirstPart = 0.0; //которая стоит слева
        Double sumProbabilityOfSecondPart = 0.0;
        Iterator<Map.Entry<T, Double>> firstIterator = probability.entrySet().iterator();
        Iterator<Map.Entry<T, Double>> secondIterator = probability.entrySet().iterator();
        while (sumProbabilityOfFirstPart <= sumProbabilityOfSecondPart) {

        }
    }
}

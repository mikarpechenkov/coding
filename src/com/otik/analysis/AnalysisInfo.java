package com.otik.analysis;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AnalysisInfo <T extends Comparable<? super T>>{
    private Map<T, Integer> data;
    private Integer length = null;
    private Map<T, Double> probability = new LinkedHashMap<>();
    private Map<T, Double> symbolInfo = new LinkedHashMap<>();
    private Double totalInfo = null;

    public AnalysisInfo(Map<T, Integer> data) {
        this.data = data;
        calculateLength();
        calculateProbability();
        calculateSymbolInfo();
        calculateTotalInfo();
    }

    public Integer getLength() {
        return length;
    }

    private void calculateLength() {
        AtomicInteger atomicLength = new AtomicInteger(0);
        data.values().forEach(atomicLength::addAndGet);
        length = atomicLength.get();
    }

    private void calculateProbability() {
        data.forEach((key, value) -> probability.put(key, (double) value / length));
    }

    private void calculateSymbolInfo() {
        probability.forEach((key, value) -> symbolInfo.put(key, -Math.log(value) / Math.log(2)));
    }

    private void calculateTotalInfo() {
        totalInfo = 0.0;
        symbolInfo.values().forEach(value -> totalInfo += value);
    }

    public Map<T, Double> getProbability() {
        return probability;
    }

    public Map<T, Double> getSymbolInfo() {
        return symbolInfo;
    }

    public Double getTotalInfo() {
        return totalInfo;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder("Length = " + length + "\n");
        data = data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (k, v) -> {
                            throw new AssertionError();
                        }, LinkedHashMap::new));
        info.append("\nSort by byte name:\n");
        Iterator<T> keyIter = data.keySet().iterator();
        while (keyIter.hasNext()) {
            T key = keyIter.next();
            info.append("Byte: ").append(key)
                    .append(", repetition: ").append(data.get(key))
                    .append(", probability: ").append(probability.get(key))
                    .append(", symbol info: ").append(symbolInfo.get(key)).append("\n");
        }
        info.append("\nSort by repetition:\n");
        data = data.entrySet().stream()
                .sorted(Map.Entry.<T, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (k, v) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new));
        keyIter = data.keySet().iterator();
        while (keyIter.hasNext()) {
            T key = keyIter.next();
            info.append("Byte: ").append(key)
                    .append(", repetition: ").append(data.get(key))
                    .append(", probability: ").append(probability.get(key))
                    .append(", symbol info: ").append(symbolInfo.get(key)).append("\n");
        }
        info.append("\nTotal info: ").append(totalInfo);
        info.append("\n");
        return info.toString();
    }
}

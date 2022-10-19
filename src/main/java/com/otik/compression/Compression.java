package com.otik.compression;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class Compression<T extends Comparable<? super T>> {
    /*
    TreeMap нам не поможет. Оно по сути только для уменьшения времени работы с мэпами, в которых мы часто ищем.
    Также для в них есть методы, которые позволяют делить карты,
    но для того, чтобы их сортировать по значению (в данном случае нужно именно так) используются костыли,
    поэтому решил, что LinkedHashMap подходит больше
     */
    private Map<T, Double> probability = new LinkedHashMap<>();
    /*
    В этой мэпе будут лежать:
     ключи – значение байта и значения – код для конкретного байта.
     TreeMap подходит лучше, потому что мы будем поэтапно формировать код для каждого байта,
     следовательно, часто выполнять поиск по мэпе.
     По этой же причине используем StringBuilder, он менее ресурсоемкий, чем String, когда необходимо часто изменять строку
     Кроме того, к этой же мэпе будем обращаться и при дешифровке.
     На порядок элементов нам все равно, мы ее не сортируем.
     */
    private TreeMap<T, StringBuilder> codes = new TreeMap<>();

    public Compression(Map<T, Double> probability) {
        this.probability = probability;
        codes.keySet().addAll(this.probability.keySet());
        codes.values().forEach(v->v=new StringBuilder(""));
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

    //Осталось прописывать код перед рекурсивными вызовами
    private void defineCiphers(@NotNull Map<T, Double> map) {
        if (map.size() != 1) {
            double mediumProbability = 0.0;
            double sumProbabilityOfFirstPart = 0.0; //которая стоит справа, где 1
            Map<T, Double> firstPart = new HashMap<>();
            Map<T, Double> secondPart = new HashMap<>();

            for (double value : map.values())
                mediumProbability += (value / 2);

            for (Map.Entry<T, Double> entry : map.entrySet())
                if (sumProbabilityOfFirstPart < mediumProbability) {
                    firstPart.put(entry.getKey(), entry.getValue());
                    codes.get(entry.getKey()).append("1");
                    sumProbabilityOfFirstPart += entry.getValue();
                } else {
                    secondPart.put(entry.getKey(), entry.getValue());
                    codes.get(entry.getKey()).append("0");
                }
            defineCiphers(firstPart);
            defineCiphers(secondPart);
        }

    }
}
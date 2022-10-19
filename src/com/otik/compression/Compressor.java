package com.otik.compression;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class Compressor<T extends Comparable<? super T>> {
    /*
    TreeMap нам не поможет. Оно по сути только для уменьшения времени работы с мэпами, в которых мы часто ищем.
    Также для в них есть методы, которые позволяют делить карты,
    но для того, чтобы их сортировать по значению (в данном случае нужно именно так) используются костыли,
    поэтому решил, что LinkedHashMap подходит больше
     */
    private Map<T, Integer> repetition = new LinkedHashMap<>();
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

    public Compressor(Map<T, Integer> repetition) {
        this.repetition = repetition;
        this.repetition.forEach((k, v) -> codes.put(k, new StringBuilder("")));
    }

    public TreeMap<T, StringBuilder> getCodes() {
        sortProbability();
        defineCiphers(repetition);
        return codes;
    }

    private void sortProbability() {
        repetition = repetition.entrySet().stream()
                .sorted(Map.Entry.<T, Integer>comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (k, v) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new));
    }


    private void defineCiphers(@NotNull Map<T, Integer> map) {
        if (map.size() != 1) {
            double mediumProbability = 0.0;
            double sumProbabilityOfFirstPart = 0.0; //которая стоит справа, где 1
            Map<T, Integer> firstPart = new LinkedHashMap<>();
            Map<T, Integer> secondPart = new LinkedHashMap<>();

            for (double value : map.values())
                mediumProbability += (value / 2);

            for (Map.Entry<T, Integer> entry : map.entrySet())
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
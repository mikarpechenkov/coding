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
        defineCiphers(repetition);
        return codes;
    }

    private Map<T, Integer> sortRepetitionDescending(Map<T, Integer> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<T, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (k, v) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new));

    }

    private Map<T, Integer> sortRepetitionAscending(Map<T, Integer> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<T, Integer>comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (k, v) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new));

    }

    private void defineCiphers(@NotNull Map<T, Integer> map) {
        if (map.size() != 1) {
            double mediumRepetition = 0.0;
            double sumRepetitionOfMostPartVariant1 = 0.0; //которая стоит справа, где 1
            double sumRepetitionOfMostPartVariant2 = 0.0;
            Map<T, Integer> firstPart = new LinkedHashMap<>();
            Map<T, Integer> secondPart = new LinkedHashMap<>();

            for (double value : map.values())
                mediumRepetition += (value / 2);

            map = sortRepetitionDescending(map);
            for (Map.Entry<T, Integer> entry : map.entrySet())
                if (sumRepetitionOfMostPartVariant1 < mediumRepetition)
                    sumRepetitionOfMostPartVariant1 += entry.getValue();

            map = sortRepetitionAscending(map);
            for (Map.Entry<T, Integer> entry : map.entrySet())
                if (sumRepetitionOfMostPartVariant2 < mediumRepetition)
                    sumRepetitionOfMostPartVariant2 += entry.getValue();

            if (sumRepetitionOfMostPartVariant1 < sumRepetitionOfMostPartVariant2) {
                map=sortRepetitionDescending(map);
                sumRepetitionOfMostPartVariant1=0;
                for (Map.Entry<T, Integer> entry : map.entrySet())
                    if (sumRepetitionOfMostPartVariant1 < mediumRepetition) {
                        firstPart.put(entry.getKey(), entry.getValue());
                        codes.get(entry.getKey()).append("1");
                        sumRepetitionOfMostPartVariant1 += entry.getValue();
                    }else{
                        secondPart.put(entry.getKey(), entry.getValue());
                        codes.get(entry.getKey()).append("0");
                    }
            }else {
                map=sortRepetitionAscending(map);
                sumRepetitionOfMostPartVariant2=0;
                for (Map.Entry<T, Integer> entry : map.entrySet())
                    if (sumRepetitionOfMostPartVariant2 < mediumRepetition) {
                        firstPart.put(entry.getKey(), entry.getValue());
                        codes.get(entry.getKey()).append("1");
                        sumRepetitionOfMostPartVariant2 += entry.getValue();
                    }else{
                        secondPart.put(entry.getKey(), entry.getValue());
                        codes.get(entry.getKey()).append("0");
                    }
            }
            defineCiphers(firstPart);
            defineCiphers(secondPart);
        }
    }
}
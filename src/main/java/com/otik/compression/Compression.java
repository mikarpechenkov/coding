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

    //Думаю лекче всего сделать рекурсией
    //Не забыть убрать лишние скобки
    private void defineCiphers(@NotNull Map<T, Double> map) {
        if (map.size() != 1) {
            Double sumProbabilityOfFirstPart = 0.0; //которая стоит справа, где 1
            Double sumProbabilityOfSecondPart = 0.0;
            Iterator<Map.Entry<T, Double>> firstIterator = map.entrySet().iterator();

            while (sumProbabilityOfFirstPart <= sumProbabilityOfSecondPart) {
                sumProbabilityOfSecondPart = 0.0;
                sumProbabilityOfFirstPart += firstIterator.next().getValue();
                Iterator<Map.Entry<T, Double>> secondIterator = firstIterator;
                while (secondIterator.hasNext())
                    sumProbabilityOfSecondPart += secondIterator.next().getValue();
            }

            Map<T, Double> firstPart = new HashMap<>();
            Map<T, Double> secondPart = new HashMap<>();
            firstIterator.forEachRemaining(

            );
    }
}
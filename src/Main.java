import com.otik.compression.Compressor;

import java.util.LinkedHashMap;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        LinkedHashMap<Character,Integer> map=new LinkedHashMap<>();
        map.put('е',3);
        map.put('о',3);
        map.put('в',2);
        map.put('д',2);
        map.put('т',1);
        map.put('н',1);
        map.put('и',1);
        Compressor<Character> compressor=new Compressor<>(map);
        TreeMap <Character,StringBuilder>codes=compressor.getCodes();
        System.out.println(codes);
    }
}
package com.otik.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class ReaderFileByOсtet{
    private Map<String, Integer> dataOfFile;
    private File file;

    public ReaderFileByOсtet(String file) {
        this.file = new File(file);
        dataOfFile= new LinkedHashMap<>();
    }

    public void setFile(String file) {
        this.file = new File(file);
    }

    public Map<String, Integer> getDataOfFile() {
        if (dataOfFile != null) return dataOfFile;
        else return null;
    }

    public void readData() throws IOException {
        int tmp;
        FileInputStream inputStream = new FileInputStream(file);
        while ((tmp = inputStream.read()) != -1) {
            String tmpKey=String.format("%8s", Integer.toBinaryString(tmp)).replaceAll(" ", "0");
            dataOfFile.computeIfPresent(tmpKey, (key, value) -> ++value);
            dataOfFile.putIfAbsent(tmpKey, 1);
        }
        inputStream.close();
    }
}

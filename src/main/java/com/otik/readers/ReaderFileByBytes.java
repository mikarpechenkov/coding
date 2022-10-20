package com.otik.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReaderFileByBytes {
    private Map<Integer, Integer> dataOfFile;
    private File file;

    public ReaderFileByBytes(String file) {
        this.file = new File(file);
        dataOfFile= new LinkedHashMap<>();
    }

    public void setFile(String file) {
        this.file = new File(file);
    }

    public Map<Integer, Integer> getDataOfFile() {
        if (dataOfFile != null) return dataOfFile;
        else return null;
    }

    public void readData() throws IOException {
        int tmp;
        FileInputStream inputStream = new FileInputStream(file);
        while ((tmp = inputStream.read()) != -1) {
            dataOfFile.computeIfPresent(tmp, (key, value) -> ++value);
            dataOfFile.putIfAbsent(tmp, 1);
        }
        inputStream.close();
    }
}

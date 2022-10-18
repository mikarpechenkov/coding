package com.otik.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReaderFileByCharacter {
    private Map<Character, Integer> dataOfFile;
    private File file;

    public ReaderFileByCharacter(String file) {
        this.file = new File(file);
        dataOfFile= new LinkedHashMap<>();
    }

    public void setFile(String file) {
        this.file = new File(file);
    }

    public Map<Character, Integer> getDataOfFile() {
        if (dataOfFile != null) return dataOfFile;
        else return null;
    }

    public void readData() throws IOException {
        int tmp;
        InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        while((tmp=inputStreamReader.read())!=-1)
        {
            dataOfFile.computeIfPresent((char)tmp, (key, value) -> ++value);
            dataOfFile.putIfAbsent((char)tmp, 1);
        }
        inputStreamReader.close();
    }
}

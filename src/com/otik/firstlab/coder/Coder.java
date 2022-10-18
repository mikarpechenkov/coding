package com.otik.firstlab.coder;

import com.otik.firstlab.header.Header;

import java.io.*;

public class Coder {
    private File source;
    private File target;
    private Header header;

    public Coder() {
        source = new File("");
        setTarget("archive");
        header = new Header();
    }

    public File source() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
        header.setSizeOfOriginalFile(this.source.length());
        header.setNameOfOriginalFile(this.source.getName());
    }

    public void setTarget(String target) {
        this.target = new File(target + ".mkenit");
    }

    public void setSource(String source) {
        this.source = new File(source);
        header.setSizeOfOriginalFile(this.source.length());
        header.setNameOfOriginalFile(this.source.getName());
    }

    public File getSource() {
        return source;
    }

    public File getTarget() {
        return target;
    }

    public Header getHeader() {
        return header;
    }

    public boolean encode() throws IOException {
        FileOutputStream outputStream = new FileOutputStream(target);
        for (int i = 0; i != header.getHeader().length; i++)
            outputStream.write(header.getHeader()[i]);
        FileInputStream inputStream = new FileInputStream(source);
        while (inputStream.available() > 0)
            outputStream.write(inputStream.read());
        inputStream.close();
        outputStream.close();
        return true;
    }
}

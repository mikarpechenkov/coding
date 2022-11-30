package com.otik.coder;

import com.otik.header.Header;
import com.otik.readers.ReaderFileByBytes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class Coder {
    private File source;
    private File target;
    private Header header;

    private Map<Integer,Integer> repetition;

    public Coder() throws IOException {
        source = new File("");
        setTarget("archive");
        setRepetition();
        header = new Header();
    }

    public File source() {
        return source;
    }

    private void setRepetition() throws IOException {
        ReaderFileByBytes readerFileByBytes=new ReaderFileByBytes(source);
        repetition=readerFileByBytes.getDataOfFile();
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
        //Записываем header
        FileOutputStream outputStream = new FileOutputStream(target);
        for(byte byteOfHeader: header.getHeader())
            outputStream.write(byteOfHeader);


//        FileInputStream inputStream = new FileInputStream(source);
//        while (inputStream.available() > 0)
//            outputStream.write(inputStream.read());
//        inputStream.close();
        outputStream.close();
        return true;
    }
}

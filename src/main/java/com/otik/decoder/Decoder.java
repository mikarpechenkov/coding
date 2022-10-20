package com.otik.decoder;

import com.otik.header.Header;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Decoder {
    private File source;
    private File targetDirectory;
    private String targetFileName;
    private Header header;

    public Decoder() {
        source = new File("");
        targetDirectory = new File("");
        header = new Header();
    }

    public void setTargetDirectory(String targetDirectory) {
        this.targetDirectory = new File(targetDirectory);
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    public void setSource(String source) {
        this.source = new File(source);
    }

    public File getSource() {
        return source;
    }

    public File getTargetDirectory() {
        return targetDirectory;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    private boolean checkArchive(FileInputStream file) throws IOException {
        boolean checker = false;
        if (file.available() >= 64)
            if (Arrays.equals(file.readNBytes(6),header.getSignature())){
                file.getChannel().position(54);
                if (Arrays.equals(file.readNBytes(header.getCompressionWithContext().length),header.getCompressionWithContext()) &&
                        Arrays.equals(file.readNBytes(header.getCompressionWithoutContext().length),header.getCompressionWithoutContext()) &&
                        Arrays.equals(file.readNBytes(header.getInterferenceProtection().length),header.getInterferenceProtection()))
                    checker = true;
            }
        file.getChannel().position(22);
        return checker;
    }

    public boolean decode() throws IOException {
        StringBuilder originalFileName = new StringBuilder();
        byte tmp;
        FileInputStream inputStream = new FileInputStream(source);
        if (checkArchive(inputStream)) {
            while ((tmp = (byte) inputStream.read()) != 0x0)
                originalFileName.append((char) tmp);
            if (targetFileName.isEmpty())
                targetFileName = originalFileName.toString();
            else
                targetFileName += "." + originalFileName.toString().split("\\.")[originalFileName.toString().split("\\.").length - 1];
            File targetFile = new File(targetDirectory + targetFileName);
            FileOutputStream outputStream = new FileOutputStream(targetFile);
            inputStream.getChannel().position(64);
            while (inputStream.available() > 0)
                outputStream.write(inputStream.read());
            outputStream.close();
            return true;
        }
        inputStream.close();
        return false;
    }
}

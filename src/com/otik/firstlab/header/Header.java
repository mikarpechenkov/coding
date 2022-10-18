package com.otik.firstlab.header;

public class Header {
    private byte[] signature;
    private byte[] version;
    private byte[] sizeOfOriginalFile;
    private byte[] nameOfOriginalFile;
    private byte[] compressionWithContext;
    private byte[] compressionWithoutContext;
    private byte[] interferenceProtection;
    private byte[] reserve;

    public Header() {
        signature = "MKENIT".getBytes();
        version = new byte[8];
        for (int i = 0; i != "1.0".getBytes().length; i++)
            version[i] = "1.0".getBytes()[i];
        for (int i = "1.0".getBytes().length; i != 8; i++)
            version[i] = 0x0;
        sizeOfOriginalFile = new byte[8];
        setSizeOfOriginalFile(0);
        nameOfOriginalFile = new byte[32];
        setNameOfOriginalFile("");
        compressionWithContext = new byte[]{0x0};
        compressionWithoutContext = new byte[]{0x0};
        interferenceProtection = new byte[]{0x0};
        reserve = new byte[6];
        for (int i = 0; i != 6; i++)
            reserve[i] = 0x0;
    }

    public void setSizeOfOriginalFile(long sizeOfOriginalFile) {
        for (int i = 0; i != String.valueOf(sizeOfOriginalFile).getBytes().length; i++)
            this.sizeOfOriginalFile[i] = String.valueOf(sizeOfOriginalFile).getBytes()[i];
        for (int i = String.valueOf(sizeOfOriginalFile).getBytes().length; i != 8; i++)
            this.sizeOfOriginalFile[i] = 0x0;
    }

    public void setNameOfOriginalFile(String nameOfOriginalFile) {
        for (int i = 0; i != nameOfOriginalFile.getBytes().length; i++)
            this.nameOfOriginalFile[i] = nameOfOriginalFile.getBytes()[i];
        for (int i = nameOfOriginalFile.getBytes().length; i != 32; i++)
            this.nameOfOriginalFile[i] = 0x0;
    }

    public byte[] getSignature() {
        return signature;
    }

    public byte[] getNameOfOriginalFile() {
        return nameOfOriginalFile;
    }

    public byte[] getCompressionWithContext() {
        return compressionWithContext;
    }

    public byte[] getCompressionWithoutContext() {
        return compressionWithoutContext;
    }

    public byte[] getInterferenceProtection() {
        return interferenceProtection;
    }

    public byte[] getHeader() {
        byte[] header = new byte[64];
        int count = 0;
        for (int i = 0; i != signature.length; i++)
            header[i + count] = signature[i];
        count += signature.length;
        for (int i = 0; i != version.length; i++)
            header[i + count] = version[i];
        count += version.length;
        for (int i = 0; i != sizeOfOriginalFile.length; i++)
            header[i + count] = sizeOfOriginalFile[i];
        count += sizeOfOriginalFile.length;
        for (int i = 0; i != nameOfOriginalFile.length; i++)
            header[i + count] = nameOfOriginalFile[i];
        count += nameOfOriginalFile.length;
        for (int i = 0; i != compressionWithContext.length; i++)
            header[i + count] = compressionWithContext[i];
        count += compressionWithContext.length;
        for (int i = 0; i != compressionWithoutContext.length; i++)
            header[i + count] = compressionWithoutContext[i];
        count += compressionWithoutContext.length;
        for (int i = 0; i != interferenceProtection.length; i++)
            header[i + count] = interferenceProtection[i];
        count += interferenceProtection.length;
        for (int i = 0; i != reserve.length; i++)
            header[i + count] = reserve[i];
        return header;
    }
}

package Files;

import main.ByteWrapper;

import java.io.*;
import java.util.ArrayList;

public class FileClass extends FileOperations {
    private final String name;
    private final String directory;
    private final int n;
    private final byte[] byteSequence;
    private final ArrayList<ByteWrapper> wrappers;

    public FileClass(String name, String directory, int n) throws IOException {
        this.name = name;
        this.directory = directory;
        this.n = n;
        this.byteSequence = read(directory + "\\" + name);
        this.wrappers = parse(byteSequence,n);
    }

    public String getName() {
        return name;
    }

    public String getDirectory() {
        return directory;
    }

    public int getN() {
        return n;
    }

    public byte[] getByteSequence() {
        return byteSequence;
    }

    public ArrayList<ByteWrapper> getWrappers() {
        return wrappers;
    }
}
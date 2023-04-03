package Files;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ExtractClass extends FileOperations {
    private final String name;
    private final String directory;
    private final byte[] byteSequence;
    private byte[] encodings;
    private byte[] keys;
    private byte[] file;
    private int n;

    public ExtractClass(String name, String directory) throws IOException {
        this.name = name;
        this.directory = directory;
        this.byteSequence = read(directory + "\\" + name);
        process();
    }

    public void process() {
        int i = 0;
        n = ByteBuffer.wrap(byteSequence,i,4).getInt();
        i+=4;
        int encodingsSize = ByteBuffer.wrap(byteSequence,i,4).getInt();
        i+=4;
        encodings = Arrays.copyOfRange(byteSequence, i,encodingsSize+i);
        i+=encodingsSize;
        int keysSize = ByteBuffer.wrap(byteSequence,i,4).getInt();
        i+=4;
        keys = Arrays.copyOfRange(byteSequence, i,keysSize+i);
        i+=keysSize;
        file = Arrays.copyOfRange(byteSequence, i,byteSequence.length);
    }

    public String getName() {
        return name;
    }

    public String getDirectory() {
        return directory;
    }

    public byte[] getByteSequence() {
        return byteSequence;
    }

    public byte[] getEncodings() {
        return encodings;
    }

    public byte[] getKeys() {
        return keys;
    }

    public byte[] getFile() {
        return file;
    }

    public int getN() {
        return n;
    }
}

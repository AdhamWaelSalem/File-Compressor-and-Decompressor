package Process;

import Files.FileClass;
import huffman.Huffman;
import main.ByteWrapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Compress {
    private final FileClass file;
    Huffman huffman;
    private HashMap<ByteWrapper, Integer> nBytesFrequencies;
    StringBuilder encodedFile;
    byte[] header;

    public Compress(FileClass file) {
        this.file = file;
        generateBytesFrequencies();
        generateEncodedSequence();
        generateHeader();
    }

    private void generateBytesFrequencies() {
        nBytesFrequencies = new HashMap<>();
        ArrayList<ByteWrapper> wrappers = file.getWrappers();
        for (ByteWrapper byteWrapped : wrappers) {
            nBytesFrequencies.computeIfPresent(byteWrapped, (k, v) -> v + 1);
            nBytesFrequencies.putIfAbsent(byteWrapped, 1);
        }
    }

    private void generateEncodedSequence() {
        huffman = new Huffman(nBytesFrequencies);
        encodedFile = new StringBuilder("1");
        ArrayList<ByteWrapper> wrappers = file.getWrappers();
        HashMap<ByteWrapper, String> Encodings = huffman.getEncodings();
        for (ByteWrapper byteWrapped : wrappers) {
            encodedFile.append(Encodings.get(byteWrapped));
        }
    }

    private void generateHeader() {
        byte[] n = ByteBuffer.allocate(4).putInt(file.getN()).array();
        byte[] encodingsSize;
        byte[] encodings;
        byte[] keysSize;
        byte[] keys;
        HashMap<ByteWrapper, String> Encodings = huffman.getEncodings();
        StringBuilder encodingsString = new StringBuilder();
        keys = new byte[0];
        for (Map.Entry<ByteWrapper, String> entry : Encodings.entrySet()) {
            byte[] bytes = entry.getKey().getByteSequence();
            byte[] temp = new byte[keys.length + bytes.length];
            System.arraycopy(keys, 0, temp, 0, keys.length);
            System.arraycopy(bytes, 0, temp, keys.length, bytes.length);
            keys = temp;
            encodingsString.append(entry.getValue());
            encodingsString.append('-');
        }
        encodingsString.deleteCharAt(encodingsString.length() - 1);
        encodings = encodingsString.toString().getBytes(StandardCharsets.UTF_8);
        encodingsSize = ByteBuffer.allocate(4).putInt(encodings.length).array();
        keysSize = ByteBuffer.allocate(4).putInt(keys.length).array();
        header = new byte[12 + encodings.length + keys.length];
        System.arraycopy(n, 0, header, 0, 4);
        System.arraycopy(encodingsSize, 0, header, 4, 4);
        System.arraycopy(encodings, 0, header, 8, encodings.length);
        System.arraycopy(keysSize, 0, header, 8 + encodings.length, 4);
        System.arraycopy(keys, 0, header, 12 + encodings.length, keys.length);
    }

    private void checkValidHeader() {
        int i = 0;
        int n = ByteBuffer.wrap(header,i,4).getInt();
        i+=4;
        int encodingsSize = ByteBuffer.wrap(header,i,4).getInt();
        i+=4;
        byte[] encodings = Arrays.copyOfRange(header, i,encodingsSize+i);
        i+=encodingsSize;
        int keysSize = ByteBuffer.wrap(header,i,4).getInt();
        i+=4;
        byte[] keys = Arrays.copyOfRange(header, i,keysSize+i);
        i+=keysSize;
        byte[] file = Arrays.copyOfRange(header, i,header.length);
    }


    private byte[] DRAFTtoByteArray(HashMap<ByteWrapper, String> Encoding) throws IOException {
        byte[] bytes = new byte[0];
        for (Map.Entry<ByteWrapper, String> entry : Encoding.entrySet()) {
            byte[] A = entry.getKey().getByteSequence();
            byte[] B = entry.getValue().getBytes();
            byte[] temp = new byte[B.length + A.length + bytes.length];
            System.arraycopy(B, 0, temp, 0, B.length);
            System.arraycopy(A, 0, temp, B.length, A.length);
            System.arraycopy(bytes, 0, temp, B.length + A.length, bytes.length);
            bytes = temp;
        }
        byte[] A = ByteBuffer.allocate(4).putInt(bytes.length).array();
        byte[] B = ByteBuffer.allocate(4).putInt(file.getN()).array();
        byte[] temp = new byte[B.length + A.length + bytes.length];
        System.arraycopy(B, 0, temp, 0, B.length);
        System.arraycopy(A, 0, temp, B.length, A.length);
        System.arraycopy(bytes, 0, temp, B.length + A.length, bytes.length);
        bytes = temp;
        return bytes;
    }

    public void save() throws IOException {
        byte[] encodedBytes = new BigInteger(encodedFile.toString(), 2).toByteArray();
        byte[] temp = new byte[encodedBytes.length + header.length];
        System.arraycopy(header, 0, temp, 0, header.length);
        System.arraycopy(encodedBytes, 0, temp, header.length, encodedBytes.length);
        encodedBytes = temp;
        FileOutputStream FOS = new FileOutputStream(file.getDirectory() + "\\6287." + file.getN() + "." + file.getName() + ".hc");
        FOS.write(encodedBytes);
        FOS.close();
    }
}
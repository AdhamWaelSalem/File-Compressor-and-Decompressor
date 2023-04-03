package Process;

import huffman.Huffman;
import main.ByteWrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OptimizedVersion {
    public static void compress(String fileName, String directory, int n) throws IOException {
        File file = new File(directory+"\\"+fileName);
        byte[] byteSequence = Files.readAllBytes(file.toPath());
        int size = byteSequence.length;
        HashMap<ByteWrapper, Integer> nBytesFrequencies = new HashMap<>();
        byte[] temp;
        ByteWrapper byteWrapped;

//        for (int i = 0; i < size - size%n - n; i+=n) {
//            byteBuffer.get(temp,0,n);
//            byteWrapped = new ByteWrapper(temp);
//            nBytesFrequencies.computeIfPresent(byteWrapped, (k, v) -> v + 1);
//            nBytesFrequencies.putIfAbsent(byteWrapped, 1);
//        }
//        Huffman huffman = new Huffman(nBytesFrequencies);
//        StringBuilder encodedFile = new StringBuilder("1");
//        HashMap<ByteWrapper, String> Encodings = huffman.getEncodings();
//        for (int i = 0; i < size - size%n - n; i+=n) {
//            byteBuffer1.get(temp,0,n);
//            byteWrapped = new ByteWrapper(temp);
//            encodedFile.append(Encodings.get(byteWrapped));
//        }
        if (n > 0) {
            for (int i = 0; i < size; i += n) {
                if (n > size - i) {
                    temp = new byte[n];
                    System.arraycopy(
                            Arrays.copyOfRange(byteSequence, i, byteSequence.length),
                            0, temp,
                            n - size + i,
                            size - i
                    );
                } else
                    temp = Arrays.copyOfRange(byteSequence, i, i + n);
                byteWrapped = new ByteWrapper(temp);
                nBytesFrequencies.computeIfPresent(byteWrapped, (k, v) -> v + 1);
                nBytesFrequencies.putIfAbsent(byteWrapped, 1);
            }
        }
        Huffman huffman = new Huffman(nBytesFrequencies);
        StringBuilder encodedFile = new StringBuilder("1");
        HashMap<ByteWrapper, String> Encodings = huffman.getEncodings();

        if (n > 0) {
            for (int i = 0; i < size; i += n) {
                if (n > size - i) {
                    temp = new byte[n];
                    System.arraycopy(
                            Arrays.copyOfRange(byteSequence, i, byteSequence.length),
                            0, temp,
                            n - size + i,
                            size - i
                    );
                } else
                    temp = Arrays.copyOfRange(byteSequence, i, i + n);
                String value = Encodings.get(new ByteWrapper(temp));
                encodedFile.append(value);
            }
        }
//        ByteBuffer byteBuffer = ByteBuffer.wrap(byteSequence);
//        byte[] arr = new byte[size/n];
//        byteBuffer.get(arr);
//        if (n > 0) {
//            for (int i = 0; i < n; i++) {
//                for (int j = 0; j < size/n; j += n) {
//                    temp = Arrays.copyOfRange(arr, j, j + n);
//                    String value = Encodings.get(new ByteWrapper(temp));
//                    encodedFile.append(value);
//                }
//                byteBuffer.get(arr);
//            }
//            arr = new byte[byteBuffer.remaining()];
//            byteBuffer.get(arr);
//            temp = new  byte[n];
//            System.arraycopy(
//                    arr,
//                    0, temp,
//                    n - byteBuffer.remaining(),
//                    byteBuffer.remaining()
//            );
//            String value = Encodings.get(new ByteWrapper(temp));
//            encodedFile.append(value);
//        }

        byte[] nValue = ByteBuffer.allocate(4).putInt(n).array();
        byte[] encodingsSize;
        byte[] encodings;
        byte[] keysSize;
        byte[] keys;
        StringBuilder encodingsString = new StringBuilder();
        keys = new byte[0];
        for (Map.Entry<ByteWrapper, String> entry : Encodings.entrySet()) {
            byte[] bytes = entry.getKey().getByteSequence();
            byte[] bytes1 = new byte[keys.length + bytes.length];
            System.arraycopy(keys, 0, bytes1, 0, keys.length);
            System.arraycopy(bytes, 0, bytes1, keys.length, bytes.length);
            keys = bytes1;
            encodingsString.append(entry.getValue());
            encodingsString.append('-');
        }

        encodingsString.deleteCharAt(encodingsString.length() - 1);
        encodings = encodingsString.toString().getBytes(StandardCharsets.UTF_8);
        encodingsSize = ByteBuffer.allocate(4).putInt(encodings.length).array();
        keysSize = ByteBuffer.allocate(4).putInt(keys.length).array();
        byte[] header = new byte[12 + encodings.length + keys.length];
        System.arraycopy(nValue, 0, header, 0, 4);
        System.arraycopy(encodingsSize, 0, header, 4, 4);
        System.arraycopy(encodings, 0, header, 8, encodings.length);
        System.arraycopy(keysSize, 0, header, 8 + encodings.length, 4);
        System.arraycopy(keys, 0, header, 12 + encodings.length, keys.length);



        byte[] encodedBytes = new BigInteger(encodedFile.toString(), 2).toByteArray();
        byte[] temp22 = new byte[encodedBytes.length + header.length];
        System.arraycopy(header, 0, temp22, 0, header.length);
        System.arraycopy(encodedBytes, 0, temp22, header.length, encodedBytes.length);
        encodedBytes = temp22;
        FileOutputStream FOS = new FileOutputStream(directory + "\\6287." + n + "." + fileName + ".hc");
        FOS.write(encodedBytes);
        FOS.close();
    }
}


//        String path = "C:\\Users\\adham\\OneDrive\\Desktop\\Files For Project\\JAVA.txt";
//        FileInputStream FIS = new FileInputStream(path);
//        byte[] buffer = new byte[4];
//        FIS.read(buffer,0,4);
//        int size = ByteBuffer.wrap(buffer).getInt();
//        byte[] header = new byte[size];
//        FIS.read(header,0,size);


//        byte[] byteSequence = {0,0,0,1,0,0,0,0,0,0,0,1};
//        byte[] byteArray = {7,0,0,0,3,1,2,3,22,23,24};
//        byte[] temp = new byte[byteSequence.length + byteArray.length];
//        System.arraycopy(byteSequence, 0, temp, 0, byteSequence.length);
//        System.arraycopy(byteArray, 0, temp, byteSequence.length, byteArray.length);
//        byteSequence = temp;
//        System.out.println(Arrays.toString(byteSequence));
//
//        int i = 0;
//        int n = ByteBuffer.wrap(byteSequence,i,4).getInt();
//        i+=4;
//        int encodingsSize = ByteBuffer.wrap(byteSequence,i,4).getInt();
//        i+=4;
//        byte[] encodings = Arrays.copyOfRange(byteSequence, i,encodingsSize+i);
//        i+=encodingsSize;
//        int keysSize = ByteBuffer.wrap(byteSequence,i,4).getInt();
//        i+=4;
//        byte[] keys = Arrays.copyOfRange(byteSequence, i,keysSize+i);
//        i+=keysSize;
//        byte[] file = Arrays.copyOfRange(byteSequence, i,byteSequence.length);
//
//        System.out.println(encodingsSize);
//        System.out.println(Arrays.toString(encodings));
//        System.out.println(keysSize);
//        System.out.println(Arrays.toString(keys));
//        System.out.println(Arrays.toString(file));

//        String str = new String(encodings, StandardCharsets.UTF_8);
//        String[] array = str.split("-");
//        HashMap<ByteWrapper,String> Encodings = new HashMap<>();
//        ArrayList<ByteWrapper> wrappers = FileClass.parse(keys,n);
//        for (int k = 0; k < wrappers.size(); k++) {
//            Encodings.put(wrappers.get(k),array[k]);
//        }
//        System.out.println(Encodings);



//        byte[] byteSequence = {0,0,0,1,0,0,0,0,0,0,0,1};
//        byte[] byteArray = {7,0,0,0,3,1,2,3,22,23,24};
//        byte[] temp = new byte[byteSequence.length + byteArray.length];
//        System.arraycopy(byteSequence, 0, temp, 0, byteSequence.length);
//        System.arraycopy(byteArray, 0, temp, byteSequence.length, byteArray.length);
//        byteSequence = temp;
//        System.out.println(Arrays.toString(byteSequence));

//        String encodedFile = "010000010100110101010010";
//        byte[] encodedBytes = new BigInteger(encodedFile, 2).toByteArray();
//        byte[] encodedBytes2 = encodedFile.getBytes(StandardCharsets.UTF_8);
//        System.out.println(Arrays.toString(encodedBytes));
//        System.out.println(Arrays.toString(encodedBytes2));

//        String codeSequence = new String(encodedBytes, StandardCharsets.UTF_8);
//        System.out.println(codeSequence);

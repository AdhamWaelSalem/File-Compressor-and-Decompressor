package Files;

import main.ByteWrapper;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class FileOperations {


    public static byte[] read(String path) throws IOException {
        File file = new File(path);
        return Files.readAllBytes(file.toPath());
    }

    public static ArrayList<ByteWrapper> parse(byte[] byteSequence, int n) {
        ArrayList<ByteWrapper> wrappers = new ArrayList<>();
        if (n > 0) {
            byte[] temp;
            for (int i = 0; i < byteSequence.length; i += n) {
                if (n > byteSequence.length - i) {
                    temp = new byte[n];
                    System.arraycopy(
                            Arrays.copyOfRange(byteSequence, i, byteSequence.length),
                            0, temp,
                            n - byteSequence.length + i,
                            byteSequence.length - i
                    );
                } else
                    temp = Arrays.copyOfRange(byteSequence, i, i + n);
                wrappers.add(new ByteWrapper(temp));
            }
        }
        return wrappers;
    }
    public static ArrayList<ByteWrapper> parse2(byte[] byteSequence, int n) {
        ArrayList<ByteWrapper> wrappers = new ArrayList<>();
        if (n > 0) {
            byte[] temp;
            for (int i = 0; i < byteSequence.length; i += n) {
                if (n > byteSequence.length - i) {
                    temp = new byte[n];
                    System.arraycopy(
                            Arrays.copyOfRange(byteSequence, i, byteSequence.length),
                            0, temp,
                            n - byteSequence.length + i,
                            byteSequence.length - i
                    );
                } else
                    temp = Arrays.copyOfRange(byteSequence, i, i + n);
                wrappers.add(new ByteWrapper(temp));
            }
        }
        return wrappers;
    }
}

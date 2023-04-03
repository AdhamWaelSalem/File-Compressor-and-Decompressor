package Process;

import Files.ExtractClass;
import Files.FileClass;
import main.ByteWrapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Decompress {
    ExtractClass extract;
    HashMap<String, ByteWrapper> Encodings;
    ArrayList<ByteWrapper> wrappers;
    byte[] bytesSequence;

    public Decompress(ExtractClass extract) {
        this.extract = extract;
        generateEncodings();
        generateDecodedWrappedSequence();
        unWrap();
    }

    private void generateEncodings() {
        String str = new String(extract.getEncodings(), StandardCharsets.UTF_8);
        String[] array = str.split("-");
        Encodings = new HashMap<>();
        ArrayList<ByteWrapper> wrappers = FileClass.parse(extract.getKeys(),extract.getN());
        for (int k = 0; k < wrappers.size(); k++) {
            Encodings.put(array[k],wrappers.get(k));
        }
    }

    private void generateDecodedWrappedSequence() {
        byte[] bytes = extract.getFile();
        BigInteger buffer = new BigInteger(bytes);
        StringBuilder code = new StringBuilder(buffer.toString(2));
        wrappers = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        for (int i = 1; i < code.length(); i++) {
            temp.append(code.charAt(i));
            if(Encodings.containsKey(temp.toString())){
                wrappers.add(Encodings.get(temp.toString()));
                temp = new StringBuilder();
            }
        }
    }

    private void unWrap(){
        bytesSequence = new byte[0];
        for (ByteWrapper byteWrapped: wrappers) {
            byte[] byteArray = byteWrapped.getByteSequence();
            byte[] temp = new byte[bytesSequence.length + byteArray.length];
            System.arraycopy(bytesSequence, 0, temp, 0, bytesSequence.length);
            System.arraycopy(byteArray, 0, temp, bytesSequence.length, byteArray.length);
            bytesSequence = temp;
        }
    }

    public void save() throws IOException {
        String name = extract.getName();
        name = name.substring(0,name.lastIndexOf("."));
        FileOutputStream FOS = new FileOutputStream(extract.getDirectory()+"\\extracted."+name);
        FOS.write(bytesSequence);
        FOS.close();
    }
}

package huffman;

import main.ByteWrapper;

public class Leaf extends Node {
    ByteWrapper wrappedByte;

    public Leaf(Integer frequency, ByteWrapper wrappedByte) {
        this.frequency = frequency;
        this.wrappedByte = wrappedByte;
    }
}

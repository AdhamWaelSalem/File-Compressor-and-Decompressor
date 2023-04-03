package huffman;


import org.jetbrains.annotations.NotNull;

public class Node implements Comparable<Node>{
    int frequency;

    public int getFrequency() {
        return frequency;
    }

    @Override
    public int compareTo(@NotNull Node node) {
        return Integer.compare(frequency,node.getFrequency());
    }
}

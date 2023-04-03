package huffman;

import main.ByteWrapper;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {
    private Node root;
    private PriorityQueue<Node> PQ;
    private final HashMap<ByteWrapper, String> Encodings = new HashMap<>();

    public Huffman(HashMap<ByteWrapper, Integer> nBytesFrequencies) {
        initiatePQ(nBytesFrequencies);
        buildHuffmanTree();
        mapEncodings(root,"");
    }

    private void initiatePQ(HashMap<ByteWrapper, Integer> nBytesFrequencies) {
        PQ = new PriorityQueue<>();
        nBytesFrequencies.forEach(
                (byteWrapper, integer) ->
                        PQ.add(new Leaf(integer, byteWrapper))
        );
    }

    private void buildHuffmanTree() {
        Node left, right;
        while (PQ.size() > 1) {
            left = PQ.poll();
            right = PQ.poll();
            assert right != null;
            PQ.add(new NonLeaf(left, right));
        }
        root = PQ.poll();
    }

    private void mapEncodings(Node node, String code) {
        if (node instanceof Leaf) {
            Encodings.put(((Leaf) node).wrappedByte, code);
        } else {
            mapEncodings(((NonLeaf) node).getLeft(), code.concat("0"));
            mapEncodings(((NonLeaf) node).getRight(), code.concat("1"));
        }
    }

    public HashMap<ByteWrapper, String> getEncodings() {
        return Encodings;
    }
}

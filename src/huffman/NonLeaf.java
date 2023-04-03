package huffman;

public class NonLeaf extends Node{
    Node left;
    Node right;

    public NonLeaf(Node left, Node right) {
        this.left = left;
        this.right = right;
        this.frequency = left.getFrequency() + right.getFrequency();
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}

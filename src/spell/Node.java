package spell;

public class Node implements INode {
    private int counter;
    private Node[] children = new Node[26];

    public Node()
    {
        counter = 0;
    }

    @Override
    public int getValue() {
        return counter;
    }

    @Override
    public void incrementValue() {
        counter = counter + 1;
    }

    @Override
    public Node[] getChildren() {
        return children;
    }
}

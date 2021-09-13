package spell;

public class Trie implements  ITrie {

    private Node root;
    private int wordCount;
    private int nodeCount;

    @Override
    public void add(String word) {

    }

    @Override
    public INode find(String word) {
        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString()
    {
        StringBuilder curWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toString_Helper(root, curWord, output);
        return output.toString();
    }

    private void toString_Helper(Node n, StringBuilder curWord, StringBuilder output)
    {
        if (n.getValue() > 0)
        {
            output.append(curWord.toString());
            output.append("\n");
        }
        for (int i = 0; i < n.getChildren().length; ++i)
        {
            Node child = n.getChildren()[i];
            if (child != null) {

                char childLetter = (char)('a' + i);
                curWord.append(childLetter);

                toString_Helper(child, curWord, output);

                curWord.deleteCharAt(curWord.length() - 1);
            }
        }
    }

    @Override
    public boolean equals (Object o)
    {
        if (o == null)
        {
            return false;
        }
        if (this == o)
        {
            return true;
        }
        if (this.getClass() != o.getClass())
        {
            return false;
        }
        Trie d = (Trie) o;
        if ((this.getWordCount() != d.getWordCount()) || (this.getNodeCount() != d.getNodeCount()))
        {
            return false;
        }
        return equals_Helper(this.root, d.root);
    }

    private boolean equals_Helper (Node n1, Node n2)
    {
        if (n1.getValue() != n2.getValue())
        {
            return false;
        }
        for (int i = 0; i < n1.getChildren().length; ++i)
        {
            if ((n1.getChildren()[i] == null) && n2.getChildren()[i] != null)
            {
                return false;
            }
            if ((n1.getChildren()[i] != null) && n2.getChildren()[i] == null)
            {
                return false;
            }
        }
        for (int i = 0; i < n1.getChildren().length; ++i)
        {
            if (n1.getChildren()[i] != null && n2.getChildren()[i] != null)
            {
                equals_Helper(n1.getChildren()[i], n2.getChildren()[i]);
            }
        }
        return true;
    }

    @Override
    public int hashCode ()
    {
        int sum = 0;
        for (int i = 0; i < root.getChildren().length; i++)
        {
            if (root.getChildren()[i] != null)
            {
                sum = sum + i;
            }
        }
        return (wordCount + nodeCount + sum);
    }
}

package spell;

public class Trie implements  ITrie {

    private Node root;
    private int wordCount = 0;
    private int nodeCount = 1;

    @Override
    public void add(String word)
    {
        String myWord = word.toLowerCase();
        char myChar = myWord.charAt(0);
        int index = myChar - 'a';

        //see if this is a one letter word
        if (myWord.length() == 1)
        {
            if (root.getChildren()[index] == null)
            {
                root.getChildren()[index] = new Node();
                nodeCount = nodeCount + 1;
                root.getChildren()[index].incrementValue();
                wordCount = wordCount + 1;
            }
            else
            {
                root.getChildren()[index].incrementValue();
            }
            return;
        }

        //if it has more than one letter then start recursion
        myWord = myWord.substring(1);
        if (root.getChildren()[index] == null)
        {
            root.getChildren()[index] = new Node();
            nodeCount = nodeCount + 1;
            add_Helper(myWord, root.getChildren()[index]);
            return;
        }
        else
        {
            add_Helper(myWord, root.getChildren()[index]);
            return;
        }
    }

    private void add_Helper (String word, Node n)
    {
        char myChar = word.charAt(0);
        int index = myChar - 'a';

        //see if this is the last letter
        if (word.length() == 1)
        {
            if (n.getChildren()[index] == null) {
                n.getChildren()[index] = new Node();
                nodeCount = nodeCount + 1;
                n.getChildren()[index].incrementValue();
                wordCount = wordCount + 1;
            }
            else
            {
                n.getChildren()[index].incrementValue();
            }
            return;
        }

        //if it is not the last letter then recurse
        word = word.substring(1);
        if (n.getChildren()[index] == null)
        {
            n.getChildren()[index] = new Node();
            nodeCount = nodeCount + 1;
            add_Helper(word, n.getChildren()[index]);
            return;
        }
        else
        {
            add_Helper(word, n.getChildren()[index]);
            return;
        }
    }

    @Override
    public Node find(String word)
    {
        String myWord = word.toLowerCase();
        return find_Helper(myWord, root);
    }

    private Node find_Helper(String word, Node n)
    {
        char myChar = word.charAt(0);
        int index = myChar - 'a';

        //if it is not found then return null
        if (n.getChildren()[index] == null)
        {
            return null;
        }

        //if this is the last letter then return the node
        if (word.length() == 1)
        {
            return n.getChildren()[index];
        }

        //if it is not the last letter then recurse
        word = word.substring(1);
        return find_Helper(word, n.getChildren()[index]);
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

package spell;

import com.sun.source.tree.Tree;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {
    Trie myTrie = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);

        //populate my trie
        while (scanner.hasNext())
        {
            String str = scanner.next();
            myTrie.add(str);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        //make set edit d 1 with set
        TreeMap<String, Integer> deletionsMap = getPossibleDeletions(inputWord);
        TreeMap<String, Integer> transpositionsMap = getPossibleTranspositions(inputWord);
        TreeMap<String, Integer> alterationsMap = getPossibleAlterations(inputWord);
        TreeMap<String, Integer> insertionsMap = getPossibleInsertions(inputWord);

        //add em all up
        TreeMap<String, Integer> allMap = new TreeMap<String, Integer>();
        allMap.putAll(deletionsMap);
        allMap.putAll(transpositionsMap);
        allMap.putAll(alterationsMap);
        allMap.putAll(insertionsMap);

        //if only one found, send it
        if (allMap.size() == 1)
        {
            for (String name: allMap.keySet())
            {
                return name;
            }
        }

        //if more than one found
        if (allMap.size() > 1)
        {
            for (String name: allMap.keySet())
            {

            }
        }

        //1. if only one is found, return
        //return frequency of each and highest returned
        //deal with other tie breakers
        //if none are found, call helper functions on all edit d 1 words
        return null;
    }

    private TreeMap<String, Integer> getPossibleDeletions (String word)
    {
        TreeMap<String, Integer> myMap = new TreeMap<String, Integer>();
        for (int i = 0; i < word.length(); ++i)
        {
            StringBuilder stringbuilder = new StringBuilder(word);
            String newWord = stringbuilder.deleteCharAt(i).toString();
            Node myNode = myTrie.find(newWord);
            if (myNode != null)
            {
                myMap.put(newWord, myNode.getValue());
            }
        }
        return myMap;
    }

    private TreeMap<String, Integer> getPossibleTranspositions (String word)
    {
        TreeMap<String, Integer> myMap = new TreeMap<String, Integer>();
        for (int i = 0; i < word.length() - 1; ++i)
        {
            StringBuilder stringbuilder = new StringBuilder(word);
            char firstChar = stringbuilder.charAt(i);
            stringbuilder.deleteCharAt(i);
            stringbuilder.insert((i+1), firstChar);
            String newWord = stringbuilder.toString();
            Node myNode = myTrie.find(newWord);
            if (myNode != null)
            {
                myMap.put(newWord, myNode.getValue());
            }
        }
        return myMap;
    }

    private TreeMap<String, Integer> getPossibleAlterations (String word)
    {
        TreeMap<String, Integer> myMap = new TreeMap<String, Integer>();
        for (int i = 0; i < word.length(); ++i) {
            for (int j = 0; j < 26; ++j)
            {
                StringBuilder stringbuilder = new StringBuilder(word);
                char myChar = (char)('a' + j);
                stringbuilder.setCharAt(i,myChar);
                String newWord = stringbuilder.toString();
                Node myNode = myTrie.find(newWord);
                if (myNode != null)
                {
                    myMap.put(newWord, myNode.getValue());
                }
            }
        }
        return myMap;
    }

    private TreeMap<String, Integer> getPossibleInsertions (String word)
    {
        TreeMap<String, Integer> myMap = new TreeMap<String, Integer>();
        for (int i = 0; i < word.length() + 1; ++i) {
            for (int j = 0; j < 26; ++j) {
                StringBuilder stringbuilder = new StringBuilder(word);
                char myChar = (char)('a' + j);
                stringbuilder.insert(i, myChar);
                String newWord = stringbuilder.toString();
                Node myNode = myTrie.find(newWord);
                if (myNode != null)
                {
                    myMap.put(newWord, myNode.getValue());
                }
            }
        }
        return myMap;
    }

}

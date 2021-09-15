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
        inputWord = inputWord.toLowerCase();

        //check if the word is already there or empty
        if (inputWord.length() < 1)
        {
            return null;
        }
        if (myTrie.find(inputWord) != null)
        {
            return inputWord;
        }

        //make set edit d 1 with set
        TreeSet<String> deletionsSet = getPossibleDeletions(inputWord);
        TreeSet<String> transpositionsSet = getPossibleTranspositions(inputWord);
        TreeSet<String> alterationsSet = getPossibleAlterations(inputWord);
        TreeSet<String> insertionsSet = getPossibleInsertions(inputWord);
        TreeSet<String> allSet = new TreeSet<String>();

        //add em all up
        allSet.addAll(deletionsSet);
        allSet.addAll(transpositionsSet);
        allSet.addAll(alterationsSet);
        allSet.addAll(insertionsSet);

        //put the combined set into a map that contains frequency values
        TreeMap<String, Integer> allMap = new TreeMap<String, Integer>();
        for (String word : allSet) {
            if (word.length() > 0) {
                Node myNode = myTrie.find(word);
                if (myNode != null) {
                    allMap.put(word, myNode.getValue());
                }
            }
        }

        //if only one found, send it
        if (allMap.size() == 1)
        {
            for (String name: allMap.keySet())
            {
                return name;
            }
        }

        //if more than one found find most used
        if (allMap.size() > 1)
        {
            int max = 1;
            TreeMap<String, Integer> allMap2 = new TreeMap<String, Integer>();
            for (String name: allMap.keySet())
            {
                int myInt = allMap.get(name);
                //if they tie
                if (myInt == max)
                {
                    allMap2.put(name, allMap.get(name));
                }
                //if there is a new winner
                if (myInt > max)
                {
                    max = myInt;
                    allMap2.clear();
                    allMap2.put(name, allMap.get(name));
                }
            }
            allMap = allMap2;
        }

        //if only one most used
        if (allMap.size() == 1)
        {
            for (String name: allMap.keySet())
            {
                return name;
            }
        }

        //this should be alphabetical
        if (allMap.size() > 1)
        {
            for (String word : allMap.keySet())
            {
                return word;
            }
        }

        //create edit distance 2 words
        TreeSet<String> alled2Set = new TreeSet<String>();
        for (String word : allSet) {
            deletionsSet = getPossibleDeletions(word);
            transpositionsSet = getPossibleTranspositions(word);
            alterationsSet = getPossibleAlterations(word);
            insertionsSet = getPossibleInsertions(word);

            alled2Set.addAll(deletionsSet);
            alled2Set.addAll(transpositionsSet);
            alled2Set.addAll(alterationsSet);
            alled2Set.addAll(insertionsSet);
        }

        //put the combined set into a map that contains frequency values
        allMap.clear();
        for (String word : alled2Set) {
            if (word.length() > 0) {
                Node myNode = myTrie.find(word);
                if (myNode != null) {
                    allMap.put(word, myNode.getValue());
                }
            }
        }

        //if only one found, send it
        if (allMap.size() == 1)
        {
            for (String name: allMap.keySet())
            {
                return name;
            }
        }

        //if more than one found find most used
        if (allMap.size() > 1)
        {
            int max = 1;
            TreeMap<String, Integer> allMap2 = new TreeMap<String, Integer>();
            for (String name: allMap.keySet())
            {
                int myInt = allMap.get(name);
                //if they tie
                if (myInt == max)
                {
                    allMap2.put(name, allMap.get(name));
                }
                //if there is a new winner
                if (myInt > max)
                {
                    max = myInt;
                    allMap2.clear();
                    allMap2.put(name, allMap.get(name));
                }
            }
            allMap = allMap2;
        }

        //if only one most used
        if (allMap.size() == 1)
        {
            for (String name: allMap.keySet())
            {
                return name;
            }
        }

        //this should be alphabetical
        if (allMap.size() > 1)
        {
            for (String word : allMap.keySet())
            {
                return word;
            }
        }

        return null;
    }

    private TreeSet<String> getPossibleDeletions (String word)
    {
        TreeSet<String> mySet = new TreeSet<>();
        for (int i = 0; i < word.length(); ++i)
        {
            StringBuilder stringbuilder = new StringBuilder(word);
            String newWord = stringbuilder.deleteCharAt(i).toString();
            mySet.add(newWord);
        }
        return mySet;
    }

    private TreeSet<String> getPossibleTranspositions (String word)
    {
        TreeSet<String> mySet = new TreeSet<>();
        if (word.length() > 1) {
            for (int i = 0; i < word.length() - 1; ++i) {
                StringBuilder stringbuilder = new StringBuilder(word);
                char firstChar = stringbuilder.charAt(i);
                stringbuilder.deleteCharAt(i);
                stringbuilder.insert((i + 1), firstChar);
                String newWord = stringbuilder.toString();
                mySet.add(newWord);
            }
        }
        return mySet;
    }

    private TreeSet<String> getPossibleAlterations (String word)
    {
        TreeSet<String> mySet = new TreeSet<>();
        for (int i = 0; i < word.length(); ++i) {
            for (int j = 0; j < 26; ++j)
            {
                StringBuilder stringbuilder = new StringBuilder(word);
                char myChar = (char)('a' + j);
                stringbuilder.setCharAt(i,myChar);
                String newWord = stringbuilder.toString();
                mySet.add(newWord);
            }
        }
        return mySet;
    }

    private TreeSet<String> getPossibleInsertions (String word)
    {
        TreeSet<String> mySet = new TreeSet<>();
        for (int i = 0; i < word.length() + 1; ++i) {
            for (int j = 0; j < 26; ++j) {
                StringBuilder stringbuilder = new StringBuilder(word);
                char myChar = (char)('a' + j);
                stringbuilder.insert(i, myChar);
                String newWord = stringbuilder.toString();
                mySet.add(newWord);
            }
        }
        return mySet;
    }

}

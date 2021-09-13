package spell;

import java.io.IOException;

public class SpellCorrector implements ISpellCorrector {
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        //popuate trie
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        //make set edit d 1 with set
        //make set
        //search trie for each word and add to another found set
        //1. if only one is found, return
        //return frequency of each and highest returned
        //deal with other tie breakers
        //if none are found, call helper functions on all edit d 1 words
        return null;
    }
}

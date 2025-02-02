package hk.edu.hkbu.comp.search_engine.model;

import com.fasterxml.jackson.core.JsonToken;

import java.io.Serializable;
import java.util.*;

public class WordTable implements Serializable {
    HashMap<String, HashSet<String>> wordRecords = new HashMap<String, HashSet<String>>();

    public void addWord(String word) {
        wordRecords.put(word, new HashSet<>());
    }

    public HashSet<String> getPageIDsByWord(String word) {
        return wordRecords.get(word);
    }

    public void addPageToWord(String word, Page page) {
        if (!wordRecords.containsKey(word)) {
            wordRecords.put(word, new HashSet<>());
        }
        wordRecords.get(word).add(page.getHash());
    }

    public void addPageToWords(ArrayList<String> words, Page page) {
        for (String word : words) {
            addPageToWord(word, page);
        }
    }

    public void printWords() {
        for (Map.Entry<String, HashSet<String>> wordRecord : wordRecords.entrySet()) {
            System.out.println(wordRecord.getKey());
        }
    }

}

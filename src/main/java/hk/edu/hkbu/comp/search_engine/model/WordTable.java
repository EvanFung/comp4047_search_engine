package hk.edu.hkbu.comp.search_engine.model;

import com.fasterxml.jackson.core.JsonToken;

import java.io.Serializable;
import java.util.*;

public class WordTable implements Serializable
{
    HashMap<String, ArrayList<String>> wordRecords = new HashMap<String, ArrayList<String>>();

    public void addWord(String word)
    {
        wordRecords.put(word, new ArrayList<>());
    }

    public ArrayList<String> getPageIDsByWord(String word)
    {
        return wordRecords.get(word);
    }

    public void addPageToWord(String word, Page page)
    {
        if(!wordRecords.containsKey(word))
        {
            wordRecords.put(word, new ArrayList<>());
        }
        wordRecords.get(word).add(page.getHash());
    }

    public void addPageToWords(ArrayList<String> words, Page page)
    {
        for (String word : words)
        {
            addPageToWord(word, page);
        }
    }

    public void printWords()
    {
        for (Map.Entry<String, ArrayList<String>> wordRecord: wordRecords.entrySet())
        {
            System.out.println(wordRecord.getKey());
        }
    }

//    public void printAll()
//    {
//        for (Map.Entry<String, List<Page>> wordRecord: wordRecords.entrySet())
//        {
//            System.out.println(wordRecord.getKey() + ":");
//            for (Page page : wordRecord.getValue())
//            {
//                System.out.println(page.getUrl());
//            }
//        }
//    }
}

package hk.edu.hkbu.comp.search_engine.model;

import com.fasterxml.jackson.core.JsonToken;

import java.util.*;

public class WordTable
{
    HashMap<String, List<Page>> wordRecords = new HashMap<String, List<Page>>();

    public void addWord(String word)
    {
        wordRecords.put(word, new ArrayList<>());
    }

    public void addPageToWord(String word, Page page)
    {
        if(wordRecords.get(word) == null)
        {
            wordRecords.put(word, new ArrayList<>());
        }
        wordRecords.get(word).add(page);
    }

    public void printWords()
    {
        for (Map.Entry<String, List<Page>> wordRecord: wordRecords.entrySet())
        {
            System.out.println(wordRecord.getKey());
        }
    }

    public void printAll()
    {
        for (Map.Entry<String, List<Page>> wordRecord: wordRecords.entrySet())
        {
            System.out.println(wordRecord.getKey() + ":");
            for (Page page : wordRecord.getValue())
            {
                System.out.println(page.getUrl());
            }
        }
    }
}

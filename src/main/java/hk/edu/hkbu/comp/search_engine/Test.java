package hk.edu.hkbu.comp.search_engine;

import hk.edu.hkbu.comp.search_engine.crawler.Crawler;
import hk.edu.hkbu.comp.search_engine.crawler.FilterTool;
import hk.edu.hkbu.comp.search_engine.crawler.HTMLParser;
import hk.edu.hkbu.comp.search_engine.crawler.UrlQueue;
import hk.edu.hkbu.comp.search_engine.model.ConnectionPack;
import hk.edu.hkbu.comp.search_engine.model.Page;
import hk.edu.hkbu.comp.search_engine.model.WordTable;
import hk.edu.hkbu.comp.search_engine.parsing.SplitWord;

import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Properties;

public class Test {
    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("./src/SearchEngineProperties.txt");
        properties.load(fileInputStream);
        int X = Integer.parseInt(properties.getProperty("X"));
        int Y = Integer.parseInt(properties.getProperty("Y"));
        String SEED = properties.getProperty("SEED");

//
        WordTable wordTable = new WordTable();
        Crawler crawler = new Crawler(wordTable, SEED, X,Y);
        crawler.crawling();


//        ConnectionPack connectionPack = new ConnectionPack();
//        Page page = new Page();
//
//        if (!connectionPack.setConnectionPack("https://www.bbc.com/news/election-2019-50258139") || !page.setPage(connectionPack))
//        {
//            UrlQueue.addToDeadpool("https://www.bbc.com/news/election-2019-50258139");
//            System.out.println("Add to deadpool: " + "https://www.bbc.com/news/election-2019-50258139");
//            return;
//        }
//
//        System.out.println(page.getOriginalContent());
//        ArrayList<String> UniqueWords = SplitWord.splitToUniqueWords(page.getOriginalContent());
//        ArrayList<String> filteredWords = FilterTool.filterWords(UniqueWords);
//
//        int i = 0;
//        for (String word: filteredWords)
//        {
//            System.out.print(word + " ");
//            if (++i % 20 == 0) System.out.println();
//        }

        {
            FileOutputStream fileOutputStream = new FileOutputStream("./src/main/java/hk/edu/hkbu/comp/search_engine/Record/wordTable.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(wordTable);
        }

//        wordTable.addPageToWord("apple", new Page("https://www.google.com/search?q=apple",  "Apple",  new ArrayList<>()));
//        wordTable.addPageToWord("banana", new Page("https://www.google.com/search?q=banana",  "Apple",  new ArrayList<>()));
//        wordTable.addPageToWord("apple", new Page("https://www.apple.com/hk/",  "100",  new ArrayList<>()));
//        wordTable.addPageToWord("apple", new Page("https://hk.appledaily.com",  "good",  new ArrayList<>()));
//        wordTable.printAll();


//        Crawler crawler = new Crawler(wordTable, "https://2cat.komica.org/~tedc21thc/anime/ ", 10,20);
//        crawler.crawling();
//
//        String s = loadPlainText("https://matthung0807.blogspot.com/2019/01/java-serializedeserialize.html");
//        System.out.println(s);
//
//        crawler.setPage(crawler.getConnectionPack("https://matthung0807.blogspot.com/2019/01/java-serializedeserialize.html"));
    }


    public static String loadPlainText(String urlString) throws IOException {
        HTMLParser callback = new HTMLParser();
        ParserDelegator parser = new ParserDelegator();

        URL url = new URL(urlString);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        parser.parse(reader, callback, true); // call MyParserCallback to process the URL stream

        return callback.title;
    }


    public static void loadKeyWord(String urlString) throws IOException {
        HTMLParser callback = new HTMLParser();
        ParserDelegator parser = new ParserDelegator();
        URL url = new URL(urlString);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        parser.parse(reader, callback, true);
        System.out.println(callback.keywordContent);
    }


}

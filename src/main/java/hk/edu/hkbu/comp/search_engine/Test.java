package hk.edu.hkbu.comp.search_engine;

import hk.edu.hkbu.comp.search_engine.crawler.Crawler;
import hk.edu.hkbu.comp.search_engine.crawler.HTMLParser;
import hk.edu.hkbu.comp.search_engine.model.ConnectionPack;
import hk.edu.hkbu.comp.search_engine.model.Page;
import hk.edu.hkbu.comp.search_engine.model.WordTable;
import hk.edu.hkbu.comp.search_engine.utils.Utils;

import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        WordTable wordTable = new WordTable();
        String sb = loadBodyText("https://www.bbc.com/news/world-us-canada-50246324");
        List<String> list = Utils.getUniqueWords(sb);
        for(String s : list) {
            System.out.println(s);
        }
//        List<String> s = Crawler.getUniqueWords(Utils.toUsefulText(sb));

//        ConnectionPack connectionPack = Crawler.getConnectionPack("https://www.bbc.co.uk/news");
//        Page page = Crawler.getPage(connectionPack);
//
//        for (String word:s)
//        {
//            System.out.println(word + " ");
//        }


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


    public static String loadBodyText(String urlString) throws IOException {
        URL url = new URL(urlString);
        InputStreamReader reader = new InputStreamReader(url.openStream());

        ParserDelegator parser = new ParserDelegator();
        HTMLParser callback = new HTMLParser();
        parser.parse(reader, callback, true);

        return callback.bodyContent;
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

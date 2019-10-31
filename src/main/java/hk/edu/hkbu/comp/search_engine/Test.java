package hk.edu.hkbu.comp.search_engine;

import hk.edu.hkbu.comp.search_engine.crawler.Crawler;
import hk.edu.hkbu.comp.search_engine.crawler.HTMLParser;
import hk.edu.hkbu.comp.search_engine.crawler.UrlFilter;
import hk.edu.hkbu.comp.search_engine.model.ConnectionPack;
import hk.edu.hkbu.comp.search_engine.model.Page;
import hk.edu.hkbu.comp.search_engine.model.WordTable;
import hk.edu.hkbu.comp.search_engine.parsing.SplitWord;
import hk.edu.hkbu.comp.search_engine.utils.Utils;

import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws Exception {
        WordTable wordTable = new WordTable();

        Crawler crawler = new Crawler(wordTable,"https://www.comp.hkbu.edu.hk/v1/",10,20);
        Set<String> urls = crawler.crawling();



        //test unit for the url filter
//        UrlFilter urlFilter = new UrlFilter();
//        boolean isAccept = urlFilter.accept("http://www.sci.hkbu.edu.hk/eng/about-us/deans-message/sahudhasu");
//        boolean isAccept = urlFilter.accept("http://www.sci.hkbu.edu.hk/eng/about-us/");

//        boolean isAccept = urlFilter.accept("http://www.comp.hkbu.edu.hk/v1/?page=people");
//        System.out.println(isAccept);





//        SplitWord splitWord = new SplitWord(urls, "/target/wordsDir/");
//        splitWord.split();

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

}

package hk.edu.hkbu.comp.search_engine;

import hk.edu.hkbu.comp.search_engine.crawler.Crawler;


public class Test {
    public static void main(String[] args) throws Exception {
        Crawler crawler = new Crawler("http://www.comp.hkbu.edu.hk/", 10,100);
        crawler.crawling();
    }
}

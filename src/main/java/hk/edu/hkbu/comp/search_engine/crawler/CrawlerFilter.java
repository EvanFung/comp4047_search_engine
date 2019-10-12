package hk.edu.hkbu.comp.search_engine.crawler;

public interface CrawlerFilter {
    void accept(String url);
}

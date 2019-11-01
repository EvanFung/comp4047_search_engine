package hk.edu.hkbu.comp.search_engine.model;

import hk.edu.hkbu.comp.search_engine.crawler.HTMLParser;
import hk.edu.hkbu.comp.search_engine.parsing.SplitWord;
import hk.edu.hkbu.comp.search_engine.utils.Utils;

import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Page implements Serializable {
    private String url;
    private String title;
    private List<String> originalWords;
    private String hash;

    private int wordCount;

    public Page() {

    }

    public Page(String url, String title, List<String> originalWords) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.url = url;
        this.title = title;
        this.originalWords = originalWords;
        this.wordCount = originalWords.size();
        this.hash = Utils.getSHA256(this.url);
    }

    public List<String> getOriginalWords() {
        return originalWords;
    }

    public void setOriginalWords(List<String> originalWords) {
        this.originalWords = originalWords;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public void incrementWord() {
        wordCount++;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}

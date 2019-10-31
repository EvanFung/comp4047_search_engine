package hk.edu.hkbu.comp.search_engine.model;

import java.io.Serializable;
import java.util.List;

public class Page implements Serializable {
    private String url;
    private String title;
    private List<String> words;
    private int wordCount;

    public Page() {
    }

    public Page(String url, String title, int wordCount, List<String> words) {
        this.url = url;
        this.title = title;
        this.words = words;
        this.wordCount = wordCount;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
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

}

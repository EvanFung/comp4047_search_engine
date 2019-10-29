package hk.edu.hkbu.comp.search_engine.model;

public class Page {
    private String url;
    private String title;
    private int wordCount;

    public Page() {}

    public Page(String url, String title, int wordCount) {
        this.url = url;
        this.title = title;
        this.wordCount = wordCount;
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

    public void increamentWord() {
        wordCount++;
    }
}

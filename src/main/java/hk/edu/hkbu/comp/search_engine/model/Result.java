package hk.edu.hkbu.comp.search_engine.model;

/**
 * Used for storing single result
 * @author EVAN FUNG
 */
public class Result {
    // word
    private String word;
    //src url
    private String url;
    //title of the document
    private String title;
    //The content of the article which is contained in the search word
    private String partialContent;
    //keyWords Count
    private int keyWordsCount;

    public Result() {
    }

    public Result(String word, String url, String title, String partialContent, int keyWordsCount) {
        this.word = word;
        this.url = url;
        this.title = title;
        this.partialContent = partialContent;
        this.keyWordsCount = keyWordsCount;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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

    public String getPartialContent() {
        return partialContent;
    }

    public void setPartialContent(String partialContent) {
        this.partialContent = partialContent;
    }

    public int getKeyWordsCount() {
        return keyWordsCount;
    }

    public void setKeyWordsCount(int keyWordsCount) {
        this.keyWordsCount = keyWordsCount;
    }
}

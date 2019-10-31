package hk.edu.hkbu.comp.search_engine.model;

public class Query {
    private String queryWord;

    public Query(String queryWord) {
        this.queryWord = queryWord;
    }

    public Query() {
    }

    public String getQueryWord() {
        return queryWord;
    }

    public void setQueryWord(String queryWord) {
        this.queryWord = queryWord;
    }
}

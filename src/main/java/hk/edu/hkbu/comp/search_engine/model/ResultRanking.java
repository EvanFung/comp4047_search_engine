package hk.edu.hkbu.comp.search_engine.model;

import java.util.Comparator;

public class ResultRanking implements Comparator<Result> {
    @Override
    public int compare(Result result, Result t1) {
        return t1.getKeyWordsCount() - result.getKeyWordsCount();
    }
}

package hk.edu.hkbu.comp.search_engine.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {


    public static List<String> getUniqueWords(String text) {
        String[] words = text.split("[\\d\\W]+");
        ArrayList<String> uniqueWords = new ArrayList<String>();

        for (String w : words) {
            w = w.toLowerCase();

            if (!uniqueWords.contains(w))
                uniqueWords.add(w);
        }

        return uniqueWords;
    }
}

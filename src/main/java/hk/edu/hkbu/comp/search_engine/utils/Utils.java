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
    //        SensitiveFilterService filter = new SensitiveFilterService(SensitiveType.url);
//        String txt = "http://www.comp.hkbu.edu.hk/v1/?page=people";
//        //如果需要过滤则用“”替换
//        //如果需要屏蔽，则用“*”替换
//        String hou = filter.replaceSensitiveWord(txt, 1, "*");
//        int a = filter.CheckSensitiveWord("http://www.hkbu.edu.hk",0,1);
//        System.out.println(a);
//        System.out.println("替换前的文字为：" + txt);
//        System.out.println("替换后的文字为：" + hou);
}

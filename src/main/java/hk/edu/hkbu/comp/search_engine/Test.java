package hk.edu.hkbu.comp.search_engine;

import hk.edu.hkbu.comp.search_engine.crawler.Crawler;
import hk.edu.hkbu.comp.search_engine.crawler.HTMLParser;
import hk.edu.hkbu.comp.search_engine.utils.SensitiveFilterService;
import hk.edu.hkbu.comp.search_engine.utils.SensitiveType;


import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) throws Exception {
        //       Crawler crawler = new Crawler("http://www.comp.hkbu.edu.hk/", 10,100);
        //  crawler.crawling();
//
//        System.out.println(getSearchEquation());
//
//        getResult(getSearchEquation());
    }

//public static String getSearchEquation() {
//    //String test = "APPLE AnD ORANGE Pear";
//    String test = "Apple NOT ( Pear OR Corn ) AND ASD SAD NOT (pear and os)";
//
//    test = test.toUpperCase();
//    String[] parts = test.split(" ");
////            System.out.println(parts[0]);
////            System.out.println(parts[1]);
////            System.out.println(parts[2]);
//    boolean nextEqualsKeywords = true;
//    String SearchEquation = "";
//    for (int i = 0; i < parts.length; i++) {
//        if (parts[i].equals("")) {
//
//        } else if (parts[i].equals("AND")) {
//            //System.out.print(" && ");
//            SearchEquation += " && ";
//            nextEqualsKeywords = true;
//        } else if (parts[i].equals("OR")) {
//            // System.out.print(" || ");
//            SearchEquation += " || ";
//            nextEqualsKeywords = true;
//        } else if (parts[i].equals("NOT")) {
//            //System.out.print(" ! ");
//            SearchEquation += " ! ";
//            nextEqualsKeywords = true;
//        } else if (parts[i].equals("(")) {
//            // System.out.print(" ( ");
//            SearchEquation += "( ";
//            nextEqualsKeywords = true;
//        } else if (parts[i].equals(")")) {
//            //System.out.print(" ) ");
//            SearchEquation += " ) ";
//            nextEqualsKeywords = true;
//        } else {
//            if (nextEqualsKeywords == false)
//                //System.out.print(" && ");
//                SearchEquation += " && ";
//            if (parts[i].substring(0, 1).equals("("))
//                // System.out.print(" ( " + parts[i].substring(1, parts[i].length()));
//                SearchEquation += "( " + parts[i].substring(1, parts[i].length());
//            else if (parts[i].substring(parts[i].length() - 1).equals(")"))
//                // System.out.print(parts[i].substring(0, parts[i].length() - 1) + " ) ");
//                SearchEquation += parts[i].substring(0, parts[i].length() - 1) + " ) ";
//            else
//                // System.out.print(parts[i]);
//                SearchEquation += parts[i];
//            nextEqualsKeywords = false;
//
//        }
//    }
//    //System.out.println(SearchEquation);
//    return  SearchEquation;
//}
//
//    public static void getResult(String SearchEquation) {
//        ArrayList<String> tempArray = new ArrayList<>();
//        String temp = "";
//        boolean isBracketsOpen = false;
//            String[] parts = SearchEquation.split(" ");
//            for (int i = 0; i < parts.length; i++) {
//                if (isBracketsOpen == true && !parts[i].equals(")")) {
//                    temp += parts[i] + " ";
//                }
//                if (parts[i].equals("(")) {
//                    isBracketsOpen = true;
//                    temp = "";
//                }
//                if (parts[i].equals(")")) {
//                    isBracketsOpen = false;
//                    tempArray.add(temp);
//                }
//            }
//
//            for (int i = 0; i < tempArray.size() ; i++) {
//                System.out.println(tempArray.get(i));
//            }
//
//    }

    public static String loadPlainText(String urlString) throws IOException {
        HTMLParser callback = new HTMLParser();
        ParserDelegator parser = new ParserDelegator();

        URL url = new URL(urlString);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        parser.parse(reader, callback, true); // call MyParserCallback to process the URL stream

        return callback.content;
    }



    public static void loadKeyWord(String urlString) throws IOException {
        HTMLParser callback = new HTMLParser();
        ParserDelegator parser = new ParserDelegator();
        URL url = new URL(urlString);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        parser.parse(reader, callback, true);
        System.out.println(callback.keywordContent);
    }


}

package hk.edu.hkbu.comp.search_engine.test;

import hk.edu.hkbu.comp.search_engine.crawler.Crawler;
import hk.edu.hkbu.comp.search_engine.crawler.HTMLParser;
import hk.edu.hkbu.comp.search_engine.crawler.UrlFilter;

import javax.swing.text.html.HTML;
import java.io.IOException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InfixToPostfix {
    static int Prec(String ch) {
        switch (ch) {
            case "AND":
            case "OR":
            case "NOT":
                return 1;
        }
        return -1;
    }

    static String infixToPostfix(String exp) {
        String result = new String();
        Stack<String> stack = new Stack<>();
        String text = "Apple NOT (Pear OR Corn)";

        String[] word = exp.split("(AND|OR|NOT|\\(|\\))", 100);
        for (int i = 0; i < exp.length(); i++) {

        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        String s = Crawler.loadWebContent("http://www.comp.hkbu.edu.hk/v1/").replaceAll("//(.*?)\n","").replaceAll("\n"," ")
                .replaceAll("<!--(.*?)-->","");
//        System.out.println(s);


        Pattern pattern = Pattern.compile("//(.*?)\n");
        Matcher matcher = pattern.matcher("gfuygyu//dshfuedshfiuhdsiuhfudsh\nhfgghfhg");
//        String s = "gfuygyu//dshfuedshfiuhdsiuhfudsh\nhfgghfhg".replaceAll("//(.*?)\n","");
//        if(matcher.find()) {
//            System.out.println(matcher.group(1));
//        }
        System.out.println(s);
    }
}

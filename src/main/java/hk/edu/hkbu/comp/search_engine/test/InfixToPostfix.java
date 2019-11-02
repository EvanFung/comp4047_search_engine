package hk.edu.hkbu.comp.search_engine.test;

import hk.edu.hkbu.comp.search_engine.crawler.UrlFilter;

import java.util.Stack;


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

    public static void main(String[] args) {


    }
}

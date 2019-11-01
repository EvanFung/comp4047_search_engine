package hk.edu.hkbu.comp.search_engine.test;

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
        for(int i = 0;i < exp.length(); i++) {

        }
        return result;
    }

    public static void main(String[] args) {
        String result = new String();
        Stack<String> stack = new Stack<>();
        String text = "Apple NOT (Pear OR Corn)".replaceAll(" ","");

    }
}

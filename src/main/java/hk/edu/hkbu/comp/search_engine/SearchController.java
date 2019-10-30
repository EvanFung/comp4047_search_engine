package hk.edu.hkbu.comp.search_engine;
import hk.edu.hkbu.comp.search_engine.model.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class GreetingController {

    @GetMapping("/")
    public String greeting(String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("greeting", new Greeting());
        return "index";//view
    }

    @PostMapping("/")
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
        System.out.println(greeting.getSearch());
        return "index";
    }

    public static String getSearchEquation() {
        //String test = "APPLE AnD ORANGE Pear";
        String test = "Apple NOT ( Pear OR Corn ) AND ASD SAD NOT (pear and os)";

        test = test.toUpperCase();
        String[] parts = test.split(" ");
//            System.out.println(parts[0]);
//            System.out.println(parts[1]);
//            System.out.println(parts[2]);
        boolean nextEqualsKeywords = true;
        String SearchEquation = "";
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("")) {

            } else if (parts[i].equals("AND")) {
                //System.out.print(" && ");
                SearchEquation += " && ";
                nextEqualsKeywords = true;
            } else if (parts[i].equals("OR")) {
                // System.out.print(" || ");
                SearchEquation += " || ";
                nextEqualsKeywords = true;
            } else if (parts[i].equals("NOT")) {
                //System.out.print(" ! ");
                SearchEquation += " ! ";
                nextEqualsKeywords = true;
            } else if (parts[i].equals("(")) {
                // System.out.print(" ( ");
                SearchEquation += "( ";
                nextEqualsKeywords = true;
            } else if (parts[i].equals(")")) {
                //System.out.print(" ) ");
                SearchEquation += " ) ";
                nextEqualsKeywords = true;
            } else {
                if (nextEqualsKeywords == false)
                    //System.out.print(" && ");
                    SearchEquation += " && ";
                if (parts[i].substring(0, 1).equals("("))
                    // System.out.print(" ( " + parts[i].substring(1, parts[i].length()));
                    SearchEquation += "( " + parts[i].substring(1, parts[i].length());
                else if (parts[i].substring(parts[i].length() - 1).equals(")"))
                    // System.out.print(parts[i].substring(0, parts[i].length() - 1) + " ) ");
                    SearchEquation += parts[i].substring(0, parts[i].length() - 1) + " ) ";
                else
                    // System.out.print(parts[i]);
                    SearchEquation += parts[i];
                nextEqualsKeywords = false;

            }
        }
        //System.out.println(SearchEquation);
        return  SearchEquation;
    }

    public static void getResult(String SearchEquation) {
        ArrayList<String> tempArray = new ArrayList<>();
        String temp = "";
        boolean isBracketsOpen = false;
        String[] parts = SearchEquation.split(" ");
        for (int i = 0; i < parts.length; i++) {
            if (isBracketsOpen == true && !parts[i].equals(")")) {
                temp += parts[i] + " ";
            }
            if (parts[i].equals("(")) {
                isBracketsOpen = true;
                temp = "";
            }
            if (parts[i].equals(")")) {
                isBracketsOpen = false;
                tempArray.add(temp);
            }
        }

        for (int i = 0; i < tempArray.size() ; i++) {
            System.out.println(tempArray.get(i));
        }

    }

}
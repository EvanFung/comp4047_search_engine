package hk.edu.hkbu.comp.search_engine;

import hk.edu.hkbu.comp.search_engine.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

@Controller
public class SearchController {
    public static String final_SearchEquation;
    public static ArrayList<String> keywords_list= new ArrayList<>();
    @GetMapping("/")
    public String greeting(String name, Model model) {
        model.addAttribute("query", new Query());
        return "index";//view
    }

    @PostMapping("/")
    public String greetingSubmit(@ModelAttribute Query query, @ModelAttribute ResultList resultList, Model model) {
        ArrayList<String> keywords_list= new ArrayList<>();
        final_SearchEquation = "";
        ArrayList<String> ResultArray = new ArrayList<>();
        ArrayList<Result> Final_ResultArray = new ArrayList<>();
        HashMap<String, ArrayList<String>> bucketsHash = solveBuckets(getSearchEquation(query.getQueryWord()));
        ResultArray = getResult(final_SearchEquation, bucketsHash);
        for (int i = 0; i < ResultArray.size(); i++) {
        Result result = new Result();
            System.out.println(ResultArray.get(i).toString());
           System.out.println(deSer_page(ResultArray.get(i).toString()).getUrl());
           if (!deSer_page(ResultArray.get(i).toString()).getTitle().equals(""))
               result.setTitle(deSer_page(ResultArray.get(i).toString()).getTitle());
           else
               result.setTitle(deSer_page(ResultArray.get(i).toString()).getUrl());
            result.setUrl(deSer_page(ResultArray.get(i).toString()).getUrl());
            result.setPartialContent(getPartial_content(deSer_page(ResultArray.get(i).toString()).getOriginalContent()));
            System.out.println(getPartial_content(deSer_page(ResultArray.get(i).toString()).getOriginalContent()));
            Final_ResultArray.add(result);
        }
        resultList.setResults(Final_ResultArray);
        model.addAttribute("results", resultList.getResults());
        deSer("hot");
        return "results";//view
    }

    public static void main(String[] args) {
        ArrayList<String> ResultArray = new ArrayList<>();
        //deSer("hot");
        String test = "(visit or kdsfjksd) or (visitkhkj or hot)";

        //String test = "hot or visit";

        HashMap<String, ArrayList<String>> bucketsHash = solveBuckets(getSearchEquation(test));
//        String x = "";
//        for (Map.Entry<String, ArrayList<String>> bucketsHashs : bucketsHash.entrySet()) {
//            System.out.println(bucketsHashs.getKey());
//            x = bucketsHashs.getKey();
//            ResultArray = bucketsHash.get(x);
//            for (int i = 0; i < ResultArray.size(); i++) {
//                System.out.println(ResultArray.get(i).toString());
//            }
//        }
        System.out.println(final_SearchEquation);
        ResultArray = getResult(final_SearchEquation, bucketsHash);
        for (int i = 0; i < ResultArray.size(); i++) {
            System.out.println(ResultArray.get(i).toString());
            System.out.println(deSer_page(ResultArray.get(i).toString()).getTitle());
        }

    }

    public static ArrayList<String> deSer(String keywords) {
        keywords = keywords.toLowerCase();
        // Deserialization
        WordTable workTable_object = new WordTable();
        ArrayList<String> tempArray = new ArrayList<>();
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("./src/main/java/hk/edu/hkbu/comp/search_engine/Record/wordTable.ser");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            workTable_object = (WordTable) in.readObject();

            in.close();
            file.close();

            System.out.println("Object has been deserialized");
            //workTable_object.printWords();
            //System.out.println(workTable_object.getPageIDsByWord("apple").toString().substring(1,workTable_object.getPageIDsByWord("apple").toString().length()-1));

            if (workTable_object.getPageIDsByWord(keywords) != null) {
                Set<String> set = new HashSet<>(workTable_object.getPageIDsByWord(keywords));
                tempArray.addAll(set);
                //tempArray = workTable_object.getPageIDsByWord(keywords);

                //for (int i = 0; i < tempArray.size(); i++) {
                    // System.out.println(tempArray.get(i).toString());
          //      }
            } else {
                System.out.println("No result found!.");
            }
            // System.out.println(workTable_object.getPageIDsByWord("apple"));


        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
        return tempArray;
    }

    public static String getSearchEquation(String test) {
        //String test = "APPLE AnD ORANGE Pear";
//        test = "Apple NOT ( Pear OR Corn ) AND ASD SAD NOT (pear and os)";

        test = test.toUpperCase();
        String[] parts = test.split(" ");
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
                SearchEquation += " )";
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
                    SearchEquation += parts[i].substring(0, parts[i].length() - 1) + " )";
                else
                    // System.out.print(parts[i]);
                    SearchEquation += parts[i];
                nextEqualsKeywords = false;

            }
        }
        return SearchEquation;
    }

    public static HashMap<String, ArrayList<String>> solveBuckets(String SearchEquation) {
        HashMap<String, ArrayList<String>> bucketsHash = new HashMap<String, ArrayList<String>>();
        String[] parts = SearchEquation.split(" ");
        boolean bucketsOpen = false;
        boolean bucketsOpen_first_null_found = false;
        String temp = "";
        String operator = "";
        ArrayList<String> tempArrayList = new ArrayList<>();
        final_SearchEquation = "";

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("(")) {
                bucketsOpen = true;

            } else if (parts[i].equals((")"))) {
                temp += parts[i];
                bucketsOpen = false;
                bucketsOpen_first_null_found = false;
                bucketsHash.put(temp.trim(), tempArrayList);
                final_SearchEquation += temp + " ";
                tempArrayList = new ArrayList<>();
                temp = "";
            }

            if (bucketsOpen) {
                temp += parts[i];
                if (!parts[i].equals("(")) {
                    if (parts[i].equals("&&") || parts[i].equals("||") || parts[i].equals("!")) {
                        operator = parts[i];
                    } else if (bucketsOpen_first_null_found) {
                        System.out.println("found!");
                        if (operator.equals("||")) {
                            tempArrayList = deSer(parts[i]);
                            bucketsOpen_first_null_found = false;
                        }
                    } else if (tempArrayList.isEmpty()) {
                        if (deSer(parts[i]).isEmpty())
                            bucketsOpen_first_null_found = true;
                        else
                            tempArrayList = deSer(parts[i]);
                    } else {
                        tempArrayList = solveOperators(operator, tempArrayList, deSer(parts[i]));
                        if (operator!="!")
                            keywords_list.add(parts[i]);
                    }
                }
            } else if (!parts[i].equals((")"))) {
                final_SearchEquation += parts[i] + " ";
            }
        }
        return bucketsHash;
    }

    public static ArrayList<String> solveOperators(String operator, ArrayList<String> tempArrayList, ArrayList<String> new_List) {
        if (new_List == null) {
            return tempArrayList;
        }
        if (operator.equals("&&")) {
            tempArrayList.retainAll(new_List);
        } else if (operator.equals("||")) {
            tempArrayList.addAll(new_List);

            Set<String> set = new HashSet<>(tempArrayList);
            tempArrayList.clear();
            tempArrayList.addAll(set);
        } else if (operator.equals("!")) {
            tempArrayList.removeAll(new_List);
        }
        return tempArrayList;
    }

    public static ArrayList<String> getResult(String SearchEquation, HashMap<String, ArrayList<String>> bucketsHashs) {
        String[] parts = SearchEquation.split(" ");
        boolean first_null_found = false;
        String operator = "";
        ArrayList<String> tempArrayList = new ArrayList<>();
        final_SearchEquation = "";
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("&&") || parts[i].equals("||") || parts[i].equals("!")) {
                operator = parts[i];
            } else if (first_null_found) {
                System.out.println("found!");
                if (operator.equals("||")) {
                    if (bucketsHashs.containsKey(parts[i]))
                        tempArrayList = bucketsHashs.get(parts[i]);
                    else
                        tempArrayList = deSer(parts[i]);
                    first_null_found = false;
                }
            } else if (tempArrayList.isEmpty()) {
                if (deSer(parts[i]).isEmpty() && !bucketsHashs.containsKey(parts[i]))
                    first_null_found = true;
                else {
                    if (bucketsHashs.containsKey(parts[i]))
                        tempArrayList = bucketsHashs.get(parts[i]);
                    else
                        tempArrayList = deSer(parts[i]);
                }
            } else {
                if (bucketsHashs.containsKey(parts[i]))
                    solveOperators(operator, tempArrayList, bucketsHashs.get(parts[i]));
                else {
                    tempArrayList = solveOperators(operator, tempArrayList, deSer(parts[i]));
                    if (operator!="!")
                        keywords_list.add(parts[i]);
                }
            }
        }
        return tempArrayList;
    }

    public static Page deSer_page(String hash) {
        Page page = new Page();
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("./src/main/java/hk/edu/hkbu/comp/search_engine/Record/Pages/" + hash + ".ser");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            page = (Page) in.readObject();

            in.close();
            file.close();
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
        return page;
    }

    public static String getPartial_content(String orc){
        int found = 0;
        boolean isFound = false;
        String temp = "";
        String[] parts = orc.split(" ");
        for (int i=0; i <keywords_list.size();i++){
            for (int j=0; j <parts.length;j++){
                    if (keywords_list.get(i).equalsIgnoreCase(parts[j])){
                        found = j;
                        isFound = true;
                        break;
                    }
            }
            if (isFound)
                break;
        }
        for (int i = 0; i < parts.length; i++) {
            if (found - i <= 15 && i - found <= 15)
                temp += parts[i] + " ";
        }
        return temp;
    }


}
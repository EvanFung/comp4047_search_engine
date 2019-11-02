package hk.edu.hkbu.comp.search_engine.search;

import java.util.HashSet;

public class Operator {
    public final static String operatorAND = "AND";

    public final static String operatorOR = "OR";

    public final static String operatorNOT = "NOT";

    public static HashSet<String> OperationOR(HashSet<String> set1, HashSet<String> set2) {
        HashSet<String> result = (HashSet<String>) set1.clone();
        for (String s : set2) {
            result.add(s);
        }
        return result;
    }

    public static HashSet<String> OperationAND(HashSet<String> set1, HashSet<String> set2) {
        HashSet<String> result = new HashSet<>();
        for(String s: set1) {
            if(set2.contains(s)) {
                result.add(s);
            }
        }
        return result;
    }

    public static HashSet<String> OperationNOT(HashSet<String> set1, HashSet<String> set2) {
        HashSet<String> result = (HashSet<String>) set1.clone();
        for(String s : set1) {
            if(set2.contains(s)) {
                result.remove(s);
            }
        }
        return result;
    }
}

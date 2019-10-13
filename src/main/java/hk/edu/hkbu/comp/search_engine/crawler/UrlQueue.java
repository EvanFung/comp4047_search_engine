package hk.edu.hkbu.comp.search_engine.crawler;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class UrlQueue {

    private static Set<String> visitedUrl = new HashSet<>();

    private static Queue<String> unVisitedUrl = new PriorityQueue<>();

    public static void addVisitedUrl(String url) {
        visitedUrl.add(url);
        System.out.println("visited url has " + visitedUrl.size());
    }

    public static void removeVisitedUrl(String url) {
        visitedUrl.remove(url);
    }

    public static int getVisitedUrlSize() {
        return visitedUrl.size();
    }

    public static Queue<String> getUnVisitedUrl() {
        return unVisitedUrl;
    }

    public static Object unVisitedUrlDeQueue() {
        String visitUrl = unVisitedUrl.poll();
        System.out.println(visitUrl + " leave unvisited url queue");
        return visitUrl;
    }

    public static int getUnvisitedUrlSize() {
        return unVisitedUrl.size();
    }

    public static boolean isUnVisitedUrlEmpty() {
        return unVisitedUrl.isEmpty();
    }

    public static void addUnvisitedUrl(String url) {
        if (url != null && !url.trim().equals("") && !visitedUrl.contains(url) && !unVisitedUrl.contains(url)) {
            unVisitedUrl.add(url);
            System.out.println(url + " enter unvisited url queue!!!");
            System.out.println("the size of unvisited queue is " + UrlQueue.getUnvisitedUrlSize());
        }
    }

}

package hk.edu.hkbu.comp.search_engine.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class FilterTool {
    private String ENCODING = "UTF-8";

    public boolean accept(String url) {
        return false;
    }

    public Set<String> readIgnoreWordFile(String fileName) {
        Set<String> wordSets = null;
        File file = new File(fileName);
        try {
            // 读取文件输入流
            InputStreamReader read = new InputStreamReader(new FileInputStream(
                    file), ENCODING);
            // 文件是否是文件 和 是否存在
            if (file.isFile() && file.exists()) {
                wordSets = new HashSet<String>();
                // BufferedReader是包装类，先把字符读到缓存里，到缓存满了，再读入内存，提高了读的效率。
                BufferedReader br = new BufferedReader(read);
                String txt = null;
                // 读取文件，将文件内容放入到set中
                while ((txt = br.readLine()) != null) {
                    wordSets.add(txt);
                }
                br.close();
            }
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordSets;
    }
}


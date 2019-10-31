package hk.edu.hkbu.comp.search_engine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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


    public static String loadWebConent(String urlStr) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        String result = "";
        try {
            url = new URL(urlStr);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }

        return result;
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

    public static String toUsefulText(String str) {
        str = str.replaceAll("\\<(img)[^>]*>|<\\/(img)>", "");
        str = str.replaceAll("\\<(table|tbody|tr|td|th|)[^>]*>|<\\/(table|tbody|tr|td|th|)>", "");
        str = str.replaceAll("\\<(div|blockquote|fieldset|legend)[^>]*>|<\\/(div|blockquote|fieldset|legend)>", "");
        str = str.replaceAll("\\<(font|i|u|h[1-9]|s)[^>]*>|<\\/(font|i|u|h[1-9]|s)>", "");
        str = str.replaceAll("\\<(style|strong)[^>]*>|<\\/(style|strong)>", "");
        str = str.replaceAll("\\<a[^>]*>|<\\/a>", "");
        str = str.replaceAll("\\<(meta|iframe|frame|span|tbody|layer)[^>]*>|<\\/(iframe|frame|meta|span|tbody|layer)>",
                "");
        str = str.replaceAll("\\<br[^>]*", "");
        str = str.replaceAll("<script[\\s\\s]+</script *>", "");
        str = str.replaceAll("\\s", "");
        return str;
    }

}

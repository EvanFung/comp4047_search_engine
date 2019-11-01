package hk.edu.hkbu.comp.search_engine.utils;

import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String loadWebContent(String urlStr) {
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

    public static String getSHA256(String input){

        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }

}

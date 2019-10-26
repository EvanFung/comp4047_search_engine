package hk.edu.hkbu.comp.search_engine.crawler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;

import java.io.*;
import java.net.URL;

/*
 *   download file IO
 * */
public class DownloadFile {

    private void saveFile(InputStream data, String filePath) {
        File result = new File(filePath);
        result.getParentFile().mkdirs();

        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(result));
             BufferedInputStream in = new BufferedInputStream(data)) {
            int r;
            while ((r = in.read()) != -1) {
                out.write((byte) r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileNameByUrl(String url) {
        System.out.println(url);
        url = url.substring(7); //remove http://
//        url = url.replaceAll("[\\?/:*|<>\"]", "_"); //将特殊字符替换，以生成合法的本地文件名
        return url;
    }

    public String downloadFile(String url) {
        String filePath = "";
        URL urlObject;
        InputStream is = null;
        try {
            urlObject = new URL(url);
            is = urlObject.openStream();
            filePath = "target/html/" + getFileNameByUrl(url);
            saveFile(is, filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully downloaded" + filePath + "to /html folder"); // not very bad
        return filePath;
    }
}

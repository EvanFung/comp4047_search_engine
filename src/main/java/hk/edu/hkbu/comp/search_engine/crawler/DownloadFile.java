package hk.edu.hkbu.comp.search_engine.crawler;

import java.io.*;
import java.net.MalformedURLException;
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
        url = url.substring(7); //remove http://
        url = url.replaceAll("[\\?/:*|<>\"]", "_");
        return url;
    }

    public String downloadFile(String url) throws IOException {
        String filePath = "";
        URL urlToDownload = new URL(url);
        InputStream inputStream = urlToDownload.openStream();
        filePath = "target/html/" + getFileNameByUrl(url);
        saveFile(inputStream, filePath);
        System.out.println("successfully download file " + filePath + " to local");
        return filePath;
    }
}

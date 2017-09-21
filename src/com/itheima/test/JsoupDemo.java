package com.itheima.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class JsoupDemo {
	
	public static void main(String[] args) throws IOException {

        String baseURL = "http://meijiecao.net/2016/04/5086.html";


        List<String> urlList = new ArrayList<>();
        List<String> imageList = new ArrayList<>();
        Document doc = Jsoup.connect(baseURL).get();
        Elements elements = doc.select("a[href]");
        for (Element element : elements) {
            String attr = element.attr("abs:href");
            if(attr.contains(".html") && !urlList.contains(attr)) {
            	//System.out.println(attr);
                urlList.add(attr);
            }
        }
        for (String url : urlList) {
            Document docs = Jsoup.connect(url).get();
             //<img src="/uploads/allimg/161020/2-1610201532440-L.jpg"
            Elements links = docs.select("img[src]");
            for (Element link : links) {
                String attr = link.attr("abs:src");
                imageList.add(attr);
                URLConnection connection = new URL(attr).openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                InputStream in = connection.getInputStream();
                String fileSize = connection.getHeaderField("Content-Length");
                if(Long.parseLong(fileSize) > 100000){
                	System.out.println(attr);
                	System.out.println(fileSize);
                	 FileOutputStream out = new FileOutputStream("F:\\jsoup\\01\\"+new Random().nextInt(10000) + ".jpg");
                     int len;
                     byte[] b = new byte[1024*8];
                     while ((len = in.read(b))!= -1) {
                         out.write(b, 0, len);
                     }
                     out.close();
                     in.close();
                }
               
            }
        }
    }

	/*public void deleteFile(){
		File file = new File("F:\\jsoup\\04");
		deleteAllFilesOfDir(file);
	}
	
	
	public static void deleteAllFilesOfDir(File path) {  
	    if (!path.exists())  
	        return;  
	    if (path.isFile()) {  
	        path.delete();  
	        return;  
	    }  
	    File[] files = path.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        deleteAllFilesOfDir(files[i]);  
	    }  
	    path.delete();  
	}  */

}

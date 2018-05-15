package com.ala;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class DriverTest {
    public void run(){
        new tests().t3();
    }
    private void fetchUrlFromLine(List<URL> urls, String line) {
        int i;
        if (line.contains("href=")){
            int x=0;
            while (x != -1){
                System.out.println(x=line.indexOf("href=",x));
            }
            String temp = line.substring(  (i=line.indexOf("href=")+6)   , line.indexOf("\"",i)  );
            System.out.println("temp="+temp);
        }
    }

    private void startDownload(URL u)
    {
        List<URL> listOfURL;
        listOfURL = downloadAndFillVector(u);
        /*
         * downloadAndFillVector downloads the file (and also
         * manipulates the link) and returns a vector of URLs
         * in the file specified by URL u.
         * After the execution of this statement, listOfURL contains
         * the URLs in the current page that needs to be downloaded.
         */
        int sizeOfVector = listOfURL.size();
        for(int i = 0; i < sizeOfVector; i++)
            startDownload((URL)listOfURL.get(i));
        /*
         * Loop through all the elements of the vector and
         * call startDownload recursively. The process repeats
         * downloading all the pages
         */
    }
    public List<URL> downloadAndFillVector(URL url){
        try {

            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bi = new BufferedInputStream(inputStream);
            byte b[] = new byte[bi.available()];
            while (bi.read(b)!=-1){

            }
        }catch (Exception e){e.printStackTrace();}
        return null;
    }
}

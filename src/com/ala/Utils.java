package com.ala;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void print(List<URL> urls){
        if(urls == null)
            return;
        System.out.println("print list size="+urls.size());
        for (URL u :
                urls) {
            System.out.println(u);
        }
    }

    /**
     * this method used to download url index file then extract the urls and saves it in a list
     * @param url : source url
     * @return : returns list of urls
     */
    public static List<URL> extractUrls(URL url){
        BufferedWriter writer=null;
        try {
            URLConnection connection = url.openConnection();
            InputStreamReader in = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String name = prepareName(url);

            writer = new BufferedWriter(new FileWriter("C:\\Users\\ALa\\Desktop\\website\\"+name));



            List<URL> urls = new ArrayList<>();
            String line;
            //search fo URLs
            while ( (line=br.readLine()) != null){
                writer.write(line);

                if (line.contains("href=")){
                    int x=0;
                    //get all URLs in one line
                    while (true){
                        x=line.indexOf("href=",x+1);

                        if(x == -1)
                            break;


                        int start = x+6;
                        String ch = line.substring(x+5,x+6);// ch will be ' or "
                        if(!(ch.equals("'") || ch.equals("\"")))
                            break;
                        int end = line.indexOf(ch,x+7);

                        if(start != -1 && end != -1){
                            String temp = line.substring(start, end);
                            try {
                                    URI uri = new URI(temp);
                                    if(!uri.isAbsolute())
                                        urls.add(url.toURI().resolve(uri).toURL()); //if relative resolve it
                                    else
                                        urls.add(uri.toURL());
                            }catch (Exception ex){ex.printStackTrace();}
                        }
                    }


                }
            }
            writer.flush();
            return urls;

        }catch (Exception e){e.printStackTrace();}
        finally {
            if(writer != null)
                try { writer.close(); } catch (IOException e) {e.printStackTrace();}
        }

        return null;
    }

    /**
     * this method returns a name for the file by it's url
     * @param url : url contains path
     * @return : suitable name
     */
    private static String prepareName(URL url) {
        String name;
        if(url.getPath() != null && !url.getPath().isEmpty() && !url.getPath().equals("/")){
            String p = url.getPath();
            if(p.lastIndexOf("/") == p.length()-1) //if there is / at last , remove it
                p=p.substring(0,p.length()-1);

            System.out.println(url.getPath());
            System.out.println(p);
            name= url.getPath().substring(p.lastIndexOf("/"),p.length());
            System.out.println("name="+name);
        }
        else
            name = "index.html";
        return name;
    }
}

package com.ala;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String PATH = "C:\\Users\\ALa\\Desktop\\website\\";

    /**
     * this method used to download url index file then extract the urls and saves it in a list
     * @param url : source url
     * @return : returns list of urls
     */
    public static List<URL> downloadAndExtractUrls(URL url){

        BufferedWriter writer=null;
        try {
            URLConnection connection = url.openConnection();
            InputStreamReader in = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(in);

            String name = prepareName(url);
            writer = new BufferedWriter(new FileWriter(PATH+name));



            List<URL> urls = new ArrayList<>();
            String line;
            //search fo URLs
            while ( (line=br.readLine()) != null){
                writer.write(line);

                if (line.contains("href=")){
                    int x=0;
                    //one line may contains many URLs
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
     * this method create directories for url path and returns the file name
     * @param url : url contains path
     * @return : suitable name
     */
    private static String prepareName(URL url) {

        if(url.getPath() != null && !url.getPath().isEmpty() && !url.getPath().equals("/")){


                String p = url.getPath();
                if(p.lastIndexOf("/") == p.length()-1) //if there is / at last , remove it
                    p=p.substring(0,p.length()-1);

                if(p.contains(".")){
                    String d  = p.substring(0,p.lastIndexOf("/"));
                    File file = new File(PATH +  d);
                    file.mkdirs();
                    return p;

                }else{
                    File file = new File(PATH+p);
                    file.mkdirs();
                    return "index.html";
                }

        }
        return "index.html";
    }
}

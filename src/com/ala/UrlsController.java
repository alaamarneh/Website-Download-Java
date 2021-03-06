package com.ala;

import java.net.URL;
import java.util.*;

public class UrlsController{
    private static Queue<URL> mQueue = new ArrayDeque<>(); // running Queue that holds fresh urls
    private static Queue<URL> mBaseQueue = new ArrayDeque<>(); // queue holds all urls ( old and new)

    public UrlsController() {
    }
    public boolean isEmpty(){
        return mQueue.isEmpty();
    }
    public int size(){
        return mQueue.size();
    }


    public void addUrls(List<URL> urls){
        if(urls == null)
            return;
        for (int i=0;i<urls.size();i++){
            URL newUrl = urls.get(i);
            boolean valid = true;
            // check for validity
            Iterator<URL> iterator = mBaseQueue.iterator();
            while (iterator.hasNext()){
                URL baseUrl = iterator.next();

                if(baseUrl.sameFile(newUrl)){ // file exists
                    valid = false;
                    break;
                }
                if(!baseUrl.getHost().equals(newUrl.getHost())){ //not the same host
                    valid =false;
                    break;
                }
            }
            if(valid){
                mQueue.add(newUrl);
                mBaseQueue.add(newUrl);
            }
        }
    }

    /**
     * get block of urls from running queue (mQueue) and then remove them from the queue
     * @param size : size of block
     * @return : list of urls
     */
    public List<URL> fetchUrls(int size){
        List<URL> list = new ArrayList<>(size);
        for(int i=0;i<size && !mQueue.isEmpty();i++)
            list.add(mQueue.remove());
        return list;
    }
}

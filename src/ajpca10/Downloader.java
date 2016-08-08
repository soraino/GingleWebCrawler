/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpca10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 *
 * @author Darren
 */
public class Downloader implements Runnable {

    //this is to contain all the url that has been accepted 
    private String Links;
    private int i = -1;
    private AddedResults addedResults;
    public static HashMap<Integer , AddedResults> Urls = new HashMap<Integer, AddedResults>();
    public Downloader() {
        
    }
    public void SetAttributes(String Links,String Description, int i){
        this.Links = Links;
        addedResults = new AddedResults(Links, Description);
        this.i = i;
    }
    public String GetLinks() {
        return this.Links;
    }
    public HashMap GetURLNumCorres(){
        return Urls;
    }

    @Override
    public void run() {
        
        
        StringBuilder content = new StringBuilder("");
        ArrayList<String> SearchResults = new ArrayList<String>();
        URL myURL = null;
        BufferedReader br;
        InputStream is = null;
        try {
            
            Urls.put(i, addedResults);
            
            myURL = new URL(Links);

            URLConnection myURLConnection = myURL.openConnection();
            
            myURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)");
            
            is = myURLConnection.getInputStream();
            
            br = new BufferedReader(new InputStreamReader(is));
            
            String StringinReading;
            
            while ((StringinReading = br.readLine()) != null) {
                content.append(StringinReading);
            }

            try {
                  PrintWriter out = new PrintWriter(i+".html");
                  out.println(content);
                  //Thread.sleep(2000);
                  System.out.println(i+"    "+Links);
            } catch (IOException e) {
                
            } 
        } catch (IOException e) {
            System.out.println("Unable to download "+Links);
            Urls.remove(i);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {

            }
        }
    }

}

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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Darren
 */
public enum SearchEngine implements Runnable {
    //this are the enum that contain the nessecery data for the 
    //user (if i plan to implement it) to use a selection of 
    //search engines and their respective data for them to begin 
    //running their search function
    Google("www.google.com", "/search?q="), Bing("www.bing.com", "/search?q=");

    /**
     * EarchEngine is to contain the correct search engine site url like
     * www.google.com SearchKeyword is the specific search engines keyword when
     * they search for a somethings for example google uses "www.google.com"
     * followed by "/search?q=" continue with what you search go generate the
     * search results SearchWords is to contain what the user has search for
     * like "ice"
     */
    private String SearchEngine = "";
    private String SearchKeyword = "";
    private String SearchWords = "";

    /**
     *
     * @param a contains the SearchEngines URL
     * @param b contains the SearchEgine specific keywords to generate the
     * search results
     */
    SearchEngine(String a, String b) {
        this.SearchEngine = a;
        this.SearchKeyword = b;
    }

    public String GetEngine() {
        return SearchEngine;
    }

    public String GetSearchKeyword() {
        return SearchKeyword;
    }

    /**
     *
     * @param Search is to take whatever the user has search and staores it in
     * the SearchWords
     */
    public void SetSearchWords(String Search) {
        this.SearchWords = Search;
    }

    /**
     * this is to run the search function after in a thread so that the user can
     * run multilple searches at a time
     */
    @Override
    public void run() {
        StringBuffer content = new StringBuffer("");
        ArrayList<String> SearchResults = new ArrayList<String>();
        ArrayList<String> Description = new ArrayList<String>();
        URL myURL = null;
        BufferedReader br;
        InputStream is = null;
        try {
            myURL = new URL("http", SearchEngine, SearchKeyword + SearchWords);

            URLConnection myURLConnection = myURL.openConnection();

            myURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)");

            is = myURLConnection.getInputStream();

            br = new BufferedReader(new InputStreamReader(is));

            String StringinReading;

            while ((StringinReading = br.readLine()) != null) {
                content.append(StringinReading);
            }//end of while loop

            //this is to go get the data urls links 
            // System.out.println(content);
            // Pattern p = Pattern.compile("(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?");
            //Pattern p = Pattern.compile("<h3 class=\\\"r\\\"(?:[^>\\\"']|\\\"[^\\\"]*\\\"|'[^']*')*>(.*?)(?=</h3>)");
            Pattern p = null;

            if (SearchEngine.equalsIgnoreCase("www.google.com")) {
                p = Pattern.compile("<div\\s?class=\"g\"><h3\\s?class=\"r\">(.*?)<br><\\/div>");
            }//emd of if statement
            else if (SearchEngine.equalsIgnoreCase("www.bing.com")) {
                p = Pattern.compile("<li\\s?class=\"b_algo\">(.*?)<\\/div><\\/li>");
            }//end of else if statement

            Matcher m = p.matcher(content);

            while (m.find()) {
                SearchResults.add(m.group());
            }

            int lol = SearchResults.size();

            for (String SearchUrl : SearchResults) {
                if (SearchEngine.equalsIgnoreCase("www.google.com")) {
                    p = Pattern.compile("(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-](?=(&amp|/\")))");
                    Matcher mm = p.matcher(SearchUrl);
                    Pattern p2 = Pattern.compile("<span\\s?class=\\\"st\\\">(.*?)<\\/span>");
                    Matcher m2 = p2.matcher(SearchUrl);

                    while (mm.find() && m2.find()) {
                        AddUniqueUrl(mm.group(), m2.group());
                    }
                }//end of if statement
                else if (SearchEngine.equalsIgnoreCase("www.bing.com")) {
                    p = Pattern.compile("(http|ftp|https):\\/\\/([\\w_-]*(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])");
                    Matcher mm = p.matcher(SearchUrl);
                    Pattern p2 = Pattern.compile("<p>(.*?)<\\/p>");
                    Matcher m2 = p2.matcher(SearchUrl);

                    while (mm.find() && m2.find()) {
                        AddUniqueUrl(mm.group(), m2.group());
                    }
                }//end of else if statement
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {

            }
        }

    }

    public synchronized void AddUniqueUrl(String url, String desc) {

        boolean IsUnique = false;

        if (Searcher.Links.size() == 0) {
            Searcher.Links.add(url);
                Searcher.Description.add(desc);
                System.out.println("<-----------------"+SearchEngine+"------------->");
                System.out.println(url);
                System.out.println(desc+"\n");

        } else if (Searcher.Links.remainingCapacity() != 0) {
            for (String data : Searcher.Links) {
                if (data.contains(url)) {
                    IsUnique = false;
                } else if (url.contains(data)) {
                    IsUnique = false;
                } else {
                    IsUnique = true;
                }

            }

        }

        if (IsUnique == true) {
            if (Searcher.Links.remainingCapacity() != 0
                    && Searcher.Description.remainingCapacity() != 0) {
                Searcher.Links.add(url);
                Searcher.Description.add(desc);
                System.out.println("<-----------------"+SearchEngine+"------------->");
                System.out.println(url);
                System.out.println(desc+"\n");
            }

        }

    }

}

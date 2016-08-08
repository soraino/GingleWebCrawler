/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpca10;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Darren
 */
public class Searcher {

    private boolean IsFinish = false;
    public static BlockingQueue<String> Links = new ArrayBlockingQueue<String>(12);
    public static BlockingQueue<String> Description = new ArrayBlockingQueue<String>(12);
   
    public Searcher() {
        
    }

    public boolean GetIsFinish() {
        return IsFinish;
    }

    public void StartSearch(String searches, int NumOfThread) throws InterruptedException {

        SearchEngine.Bing.SetSearchWords(searches);
        SearchEngine.Google.SetSearchWords(searches);

        Thread FirstSearchEngine = new Thread(SearchEngine.Bing);
        Thread SecondSearchEngine = new Thread(SearchEngine.Google);

        FirstSearchEngine.start();
        SecondSearchEngine.start();
        try {
            while (true) {
                if (!FirstSearchEngine.isAlive() && !SecondSearchEngine.isAlive()) {
                    break;
                }
                Thread.sleep(1);
            }
        } catch (Exception e) {

        }

        ExecutorService executor = Executors.newFixedThreadPool(NumOfThread);
        int lol = Links.size();
        System.out.println("<------------------Initializing download------------------------>");

        for (int i = 0; i < lol; i++) {
            Downloader d;
            d = new Downloader();
            d.SetAttributes(Links.take(), Description.take(), i);
            executor.execute(d);

        }
        executor.shutdown();
        while (true) {
            if (executor.isTerminated()) {
                IsFinish = true;
                break;
            }
        }

    }
}

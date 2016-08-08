/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpca10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JEditorPane;
import javax.swing.JLabel;

/**
 *
 * @author Darren
 */
public class getHTMLText implements Runnable {

    private String FileName = "";
    private JEditorPane pane = null;
    private String SearchPhrase = "";
    private Pattern p ;
    private JLabel Number = null;
    /**
     * this a thread runner to get the data from my downloaded HTML and show it in Text form
     * @param Filename
     * @param pane 
     */
    public getHTMLText(String Filename, JEditorPane pane, JLabel Number , String SearchPhrase) {
        this.pane = pane;
        this.FileName = Filename;
        this.SearchPhrase = SearchPhrase;
        this.Number = Number;
    }

    @Override
    public void run() {
        try {
            
            
            
            FileReader fr = new FileReader(FileName + ".html");

            BufferedReader br = new BufferedReader(fr);

            StringBuilder content = new StringBuilder();

            String StringinReading;
            while ((StringinReading = br.readLine()) != null) {
                content.append(StringinReading);
            }
            pane.setText(content.toString() + "\n");
            int Occurences = 0;
            
            p = Pattern.compile(SearchPhrase);
            Matcher m = p.matcher(content);
            while(m.find()){
                Occurences++;
            }
            
            Number.setText("Number of Occurance of search query :"+ Occurences );

            
        } catch (IOException e) {

        }
    }
}

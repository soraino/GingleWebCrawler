/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpca10;

/**
 *
 * @author Darren
 */
public class AddedResults {
    private String URLS = "";
    private String Description = "";

    public AddedResults(String URLS , String Description) {
        this.URLS = URLS;
        this.Description = Description;
    }
    
    public String getURLS() {
        return URLS;
    }

    public void setURLS(String URLS) {
        this.URLS = URLS;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
    
    
}

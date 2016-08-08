/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialize;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Darren
 */
public class PreviousSearch implements Serializable{
    private Set<String> PastSearches = new TreeSet<>();

    public PreviousSearch() {
    }

    public Set<String> getPastSearches() {
        return PastSearches;
    }

    public void setPastSearches(Set<String> PastSearches) {
        this.PastSearches = PastSearches;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Entity;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class ObjectLookUpHelper {
    
    private ArrayList<String> isi;

    public ObjectLookUpHelper() {
        isi = new ArrayList<>();
    }
    
    public void addLookUp(String isiItem){
        getIsi().add(isiItem);
    }

    /**
     * @return the isi
     */
    public ArrayList<String> getIsi() {
        return isi;
    }

    /**
     * @param isi the isi to set
     */
    public void setIsi(ArrayList<String> isi) {
        this.isi = isi;
    }
    
}

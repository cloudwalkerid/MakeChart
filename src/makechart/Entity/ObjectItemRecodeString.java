/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Entity;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class ObjectItemRecodeString {

    private ArrayList<String> Daris;
    private String Hasil;
    private EnumTypeRecodeString type;

    public ObjectItemRecodeString() {
        this.Daris = new ArrayList<>();
    }

    public ObjectItemRecodeString(ArrayList<String> Daris, String Hasil, EnumTypeRecodeString type) {
        this.Daris = Daris;
        this.Hasil = Hasil;
        this.type = type;
    }
    
    public ObjectItemRecodeString cloneObjectItemRecodeString(){
        ObjectItemRecodeString cloneThis = new ObjectItemRecodeString();
        ArrayList<String> dariClone = new ArrayList<>();
        for(String itemDari : Daris){
            dariClone.add(itemDari);
        }
        cloneThis.setDaris(dariClone);
        cloneThis.setHasil(Hasil);
        cloneThis.setType(type);
        return cloneThis;
    }

    /**
     * @return the Daris
     */
    public ArrayList<String> getDaris() {
        return Daris;
    }

    /**
     * @param Daris the Daris to set
     */
    public void setDaris(ArrayList<String> Daris) {
        this.Daris = Daris;
    }

    /**
     * @return the Hasil
     */
    public String getHasil() {
        return Hasil;
    }

    /**
     * @param Hasil the Hasil to set
     */
    public void setHasil(String Hasil) {
        this.Hasil = Hasil;
    }

    public void addDari(String baru) {
        this.Daris.add(baru);
    }

    public void removeDari(String hapus) {
        this.Daris.remove(hapus);
    }

    public void setJsonObject(JSONObject object) {
        try {
            this.Hasil = object.getString("Hasil");
            if (object.has("Daris")) {
                JSONArray dariArray = object.getJSONArray("Daris");
                for (int i = 0; i < dariArray.length(); i++) {
                    this.Daris.add(dariArray.getString(i));
                }
            }
            this.type = EnumTypeRecodeString.valueOf(object.getString("type"));
        } catch (Exception ex) {
            System.out.println("JSON 1 " + ex);
            ex.printStackTrace();
        }
    }

    public JSONObject getJSONObject() {
        try {
            JSONObject object = new JSONObject();
            object.put("Hasil", Hasil);
            JSONArray dariArray = new JSONArray();
            for (String itemDari : Daris) {
                dariArray.put(itemDari);
            }
            object.put("Daris", dariArray);
            object.put("type", type.name());
            return object;
        } catch (Exception ex) {
            System.out.println("JSON 2 " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @return the type
     */
    public EnumTypeRecodeString getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(EnumTypeRecodeString type) {
        this.type = type;
    }

}

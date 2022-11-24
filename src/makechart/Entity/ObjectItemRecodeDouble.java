/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Entity;

import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class ObjectItemRecodeDouble {

    private double BatasBawah;
    private double BatasAtas;
    private String Hasil;
    private EnumTypeRecodeDouble type;

    public ObjectItemRecodeDouble() {
    }

    public ObjectItemRecodeDouble(double BatasBawah, double BatasAtas, EnumTypeRecodeDouble type, String Hasil) {
        this.BatasBawah = BatasBawah;
        this.BatasAtas = BatasAtas;
        this.type = type;
        this.Hasil = Hasil;
    }
    
    public ObjectItemRecodeDouble cloneObjectItemRecodeDouble(){
        ObjectItemRecodeDouble cloneThis = new ObjectItemRecodeDouble();
        cloneThis.setBatasAtas(BatasAtas);
        cloneThis.setBatasBawah(BatasBawah);
        cloneThis.setHasil(Hasil);
        cloneThis.setType(type);
        return cloneThis;
    }

    public void setJsonObject(JSONObject object) {
        try {
            this.BatasBawah = object.getDouble("BatasBawah");
            this.BatasAtas = object.getDouble("BatasAtas");
            this.setType(EnumTypeRecodeDouble.valueOf(object.getString("type")));
            this.setHasil(object.getString("Hasil"));
        } catch (Exception ex) {
            System.out.println("JSON 1 " + ex);
            ex.printStackTrace();
        }
    }

    public JSONObject getJSONObject() {
        try {
            JSONObject object = new JSONObject();
            object.put("BatasBawah", BatasBawah);
            object.put("BatasAtas", BatasAtas);
            object.put("type", getType().name());
            object.put("Hasil", getHasil());
            return object;
        } catch (Exception ex) {
            System.out.println("JSON 2 " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @return the BatasBawah
     */
    public double getBatasBawah() {
        return BatasBawah;
    }

    /**
     * @param BatasBawah the BatasBawah to set
     */
    public void setBatasBawah(double BatasBawah) {
        this.BatasBawah = BatasBawah;
    }

    /**
     * @return the BatasAtas
     */
    public double getBatasAtas() {
        return BatasAtas;
    }

    /**
     * @param BatasAtas the BatasAtas to set
     */
    public void setBatasAtas(double BatasAtas) {
        this.BatasAtas = BatasAtas;
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

    /**
     * @return the type
     */
    public EnumTypeRecodeDouble getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(EnumTypeRecodeDouble type) {
        this.type = type;
    }
}

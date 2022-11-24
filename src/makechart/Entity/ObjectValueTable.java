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
public class ObjectValueTable {

    private String nameColumn;
    private String nameColumnHasil;
    private EnumDataType dataType;
    private EnumSQLColumnFunction function;

    public ObjectValueTable() {
    }

    public ObjectValueTable(String nameColumn, String nameColumnHasil, EnumDataType dataType, EnumSQLColumnFunction function) {
        this.nameColumn = nameColumn;
        this.nameColumnHasil = nameColumnHasil.replaceAll("-", "");
        this.dataType = dataType;
        this.function = function;
    }

    /**
     * @return the nameColumn
     */
    public String getNameColumn() {
        return nameColumn;
    }

    /**
     * @param nameColumn the nameColumn to set
     */
    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    /**
     * @return the function
     */
    public EnumSQLColumnFunction getFunction() {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(EnumSQLColumnFunction function) {
        this.function = function;
    }

    public void setJSONObject(JSONObject object) {
        try {
            this.setNameColumn(object.getString("nameColumn"));
            this.setDataType(EnumDataType.valueOf(object.getString("dataType")));
            this.setFunction(EnumSQLColumnFunction.valueOf(object.getString("function")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public JSONObject getJSONObject(){
        try{
            JSONObject hasil = new JSONObject();
            hasil.put("nameColumn", getNameColumn());
            hasil.put("nameColumnHasil", nameColumnHasil);
            hasil.put("dataType", dataType.name());
            hasil.put("function", getFunction().name());
            return hasil;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @return the dataType
     */
    public EnumDataType getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(EnumDataType dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the nameColumnHasil
     */
    public String getNameColumnHasil() {
        return nameColumnHasil;
    }

    /**
     * @param nameColumnHasil the nameColumnHasil to set
     */
    public void setNameColumnHasil(String nameColumnHasil) {
        this.nameColumnHasil = nameColumnHasil.replaceAll("-", "");;
    }

}

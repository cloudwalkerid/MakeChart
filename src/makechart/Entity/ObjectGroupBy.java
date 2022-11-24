/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Entity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class ObjectGroupBy {

    private String nameColumn;

    public ObjectGroupBy() {
    }

    public ObjectGroupBy(String nameColumn) {
        this.nameColumn = nameColumn;
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

    public void setJsonObject(JSONObject object) {
        try {
            this.nameColumn = object.getString("nameColumn");
        } catch (Exception ex) {
            System.out.println("JSON 1 " + ex);
            ex.printStackTrace();
        }
    }

    public JSONObject getJSONObject() {
        try {
            JSONObject object = new JSONObject();
            object.put("nameColumn", nameColumn);
            return object;
        } catch (Exception ex) {
            System.out.println("JSON 2 " + ex);
            ex.printStackTrace();
            return null;
        }
    }

}

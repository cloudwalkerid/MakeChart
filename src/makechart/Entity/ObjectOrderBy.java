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
public class ObjectOrderBy {

    private String nameColumn;
    private EnumTypeOrder type;

    public ObjectOrderBy() {
    }

    public ObjectOrderBy(String nameColumn, EnumTypeOrder type) {
        this.nameColumn = nameColumn;
        this.type = type;
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
     * @return the type
     */
    public EnumTypeOrder getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(EnumTypeOrder type) {
        this.type = type;
    }

    public void setJsonObject(JSONObject object) {
        try {
            this.nameColumn = object.getString("nameColumn");
            this.type = EnumTypeOrder.valueOf(object.getString("type"));
        } catch (Exception ex) {
            System.out.println("JSON 1 " + ex);
            ex.printStackTrace();
        }
    }

    public JSONObject getJSONObject() {
        try {
            JSONObject object = new JSONObject();
            object.put("nameColumn", nameColumn);
            object.put("type", type.name());
            return object;
        } catch (Exception ex) {
            System.out.println("JSON 2 " + ex);
            ex.printStackTrace();
            return null;
        }
    }

}

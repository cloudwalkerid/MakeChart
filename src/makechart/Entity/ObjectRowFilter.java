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
public class ObjectRowFilter {

    private String nameColumn;
    private EnumDataType dataType;
    private EnumSQLRowFunction rowFunction;
    private String valueOp;

    public ObjectRowFilter() {
    }

    public ObjectRowFilter(String nameColumn, EnumDataType dataType, EnumSQLRowFunction rowFunction, String valueOp) {
        this.nameColumn = nameColumn;
        this.dataType = dataType;
        this.rowFunction = rowFunction;
        this.valueOp = valueOp;
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
     * @return the rowFunction
     */
    public EnumSQLRowFunction getRowFunction() {
        return rowFunction;
    }

    /**
     * @param rowFunction the rowFunction to set
     */
    public void setRowFunction(EnumSQLRowFunction rowFunction) {
        this.rowFunction = rowFunction;
    }

    /**
     * @return the valueOp
     */
    public String getValueOp() {
        return valueOp;
    }

    /**
     * @param valueOp the valueOp to set
     */
    public void setValueOp(String valueOp) {
        this.valueOp = valueOp;
    }

    public void setJsonObject(JSONObject object) {
        try {
            this.nameColumn = object.getString("nameColumn");
            this.dataType = EnumDataType.valueOf(object.getString("dataType"));
            this.rowFunction = EnumSQLRowFunction.valueOf(object.getString("rowFunction"));
            this.valueOp = object.getString("valueOp");

        } catch (Exception ex) {
            System.out.println("JSON 1 " + ex);
            ex.printStackTrace();
        }
    }

    public JSONObject getJSONObject() {
        try {
            JSONObject object = new JSONObject();
            object.put("nameColumn", nameColumn);
            object.put("dataType", dataType.name());
            object.put("rowFunction", rowFunction.name());
            object.put("valueOp", valueOp);
            return object;
        } catch (Exception ex) {
            System.out.println("JSON 2 " + ex);
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
}

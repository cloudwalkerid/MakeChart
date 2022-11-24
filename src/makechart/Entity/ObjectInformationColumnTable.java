/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Entity;

/**
 *
 * @author ASUS
 */
public class ObjectInformationColumnTable {
    
    private String columnName;
    private EnumDataType dataType;

    public ObjectInformationColumnTable() {
    }

    public ObjectInformationColumnTable(String columnName, EnumDataType dataType) {
        this.columnName = columnName;
        this.dataType = dataType;
    }
    
    

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
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

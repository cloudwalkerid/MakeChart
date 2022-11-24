/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ObjectCategory {

    protected int jenisRecode;
    protected int jenisLabel;
    protected String nameColumn;
    protected EnumDataType dataType;
    protected String nameColumnHasil;
    protected String labelValue;
    protected String labelPercentage;
    protected EnumCategoryTable enumCategoryTable;
    protected List<ObjectCategory> objectCategorys;

    public List<ObjectCategory> getObjectCategoryAll() {
        return getObjectCategorys();
    }

    public ObjectCategory cloneObjectCategory() {
        if (this instanceof ObjectCategorySimple) {
            return ((ObjectCategorySimple) this).cloneObjectCategorySimpleWithOutChild();
        } else if (this instanceof ObjectCategoryRecode) {
            return ((ObjectCategoryRecode) this).cloneObjectCategoryRecodeWithOutChild();
        } else {
            return null;
        }
    }

    public ArrayList<TableResult> processResultTable(TableResult ParentTableResult, boolean isRow) {
        ArrayList<TableResult> result = new ArrayList<>();
        String select = "";
        String groupBy = "";
//        if(isRow){
//            ParentTableResult.getRowCategory().add(this);
//        }else{
//            ParentTableResult.getColumnCategory().add(this);
//        }
        if (this.getEnumCategoryTable() == EnumCategoryTable.SIMPLE) {
            select = select + ((ObjectCategorySimple) this).getSelect(isRow);
            groupBy = groupBy + ((ObjectCategorySimple) this).getGroupBy(isRow);
        } else if (this.getEnumCategoryTable() == EnumCategoryTable.RECODE) {
            select = select + ((ObjectCategoryRecode) this).getSelect(isRow);
            groupBy = groupBy + ((ObjectCategoryRecode) this).getGroupBy(isRow);
        }
        if (getObjectCategorys() == null) {
            TableResult tableResult = new TableResult();
            ParentTableResult.cloneCategory(tableResult);
            if (isRow) {
                tableResult.getRowCategory().add(this.cloneObjectCategory());
            } else {
                tableResult.getColumnCategory().add(this.cloneObjectCategory());
            }
            tableResult.setSelect(ParentTableResult.getSelect() + select + " , ");
            tableResult.setGroupby(ParentTableResult.getGroupby() + groupBy + " , ");
            tableResult.setOrderby(ParentTableResult.getOrderby());
            tableResult.setWhere(ParentTableResult.getWhere());
            result.add(tableResult);
            return result;
        } else if (getObjectCategorys().size() == 0) {
            TableResult tableResult = new TableResult();
            ParentTableResult.cloneCategory(tableResult);
            if (isRow) {
                tableResult.getRowCategory().add(this.cloneObjectCategory());
            } else {
                tableResult.getColumnCategory().add(this.cloneObjectCategory());
            }
            tableResult.setSelect(ParentTableResult.getSelect() + select + " , ");
            tableResult.setGroupby(ParentTableResult.getGroupby() + groupBy + " , ");
            tableResult.setOrderby(ParentTableResult.getOrderby());
            tableResult.setWhere(ParentTableResult.getWhere());
            result.add(tableResult);
            return result;
        } else if (getObjectCategorys().size() == 1) {
            TableResult tableResult = new TableResult();
            ParentTableResult.cloneCategory(tableResult);
            if (isRow) {
                tableResult.getRowCategory().add(this.cloneObjectCategory());
            } else {
                tableResult.getColumnCategory().add(this.cloneObjectCategory());
            }
            tableResult.setSelect(ParentTableResult.getSelect() + select + " , ");
            tableResult.setGroupby(ParentTableResult.getGroupby() + groupBy + " , ");
            tableResult.setOrderby(ParentTableResult.getOrderby());
            tableResult.setWhere(ParentTableResult.getWhere());
            result.addAll(getObjectCategorys().get(0).processResultTable(tableResult, isRow));
            return result;
        } else {
            for (ObjectCategory itemChild : getObjectCategorys()) {
                TableResult tableResult = new TableResult();
                ParentTableResult.cloneCategory(tableResult);
                if (isRow) {
                    tableResult.getRowCategory().add(this.cloneObjectCategory());
                } else {
                    tableResult.getColumnCategory().add(this.cloneObjectCategory());
                }
                tableResult.setSelect(ParentTableResult.getSelect() + select + " , ");
                tableResult.setGroupby(ParentTableResult.getGroupby() + groupBy + " , ");
                tableResult.setOrderby(ParentTableResult.getOrderby());
                tableResult.setWhere(ParentTableResult.getWhere());
                result.addAll(itemChild.processResultTable(tableResult, isRow));
            }
            return result;
        }
    }

    /**
     * @return the jenisLabel
     */
    public int getJenisLabel() {
        return jenisLabel;
    }

    /**
     * @param jenisLabel the jenisLabel to set
     */
    public void setJenisLabel(int jenisLabel) {
        this.jenisLabel = jenisLabel;
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
        this.nameColumnHasil = nameColumnHasil.replaceAll("-", "");
    }

    /**
     * @return the labelValue
     */
    public String getLabelValue() {
        return labelValue;
    }

    /**
     * @param labelValue the labelValue to set
     */
    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    /**
     * @return the labelPercentage
     */
    public String getLabelPercentage() {
        return labelPercentage;
    }

    /**
     * @param labelPercentage the labelPercentage to set
     */
    public void setLabelPercentage(String labelPercentage) {
        this.labelPercentage = labelPercentage;
    }

    /**
     * @return the enumCategoryTable
     */
    public EnumCategoryTable getEnumCategoryTable() {
        return enumCategoryTable;
    }

    /**
     * @param enumCategoryTable the enumCategoryTable to set
     */
    public void setEnumCategoryTable(EnumCategoryTable enumCategoryTable) {
        this.enumCategoryTable = enumCategoryTable;
    }

    /**
     * @return the objectCategorys
     */
    public List<ObjectCategory> getObjectCategorys() {
        return objectCategorys;
    }

    /**
     * @param objectCategorys the objectCategorys to set
     */
    public void setObjectCategorys(List<ObjectCategory> objectCategorys) {
        this.objectCategorys = objectCategorys;
    }

    /**
     * @return the jenisRecode
     */
    public int getJenisRecode() {
        return jenisRecode;
    }

    /**
     * @param jenisRecode the jenisRecode to set
     */
    public void setJenisRecode(int jenisRecode) {
        this.jenisRecode = jenisRecode;
    }

    public int getSizeItem() {
        if (jenisRecode == 0) {
            return ((ObjectCategorySimple) this).getPilihanOption().size();
        } else if (jenisRecode == 1) {
            return ((ObjectCategoryRecode) this).getObjectItemRecodeDoubles().size();
        } else if (jenisRecode == 2) {
            return ((ObjectCategoryRecode) this).getObjectItemRecodeStrings().size();
        } else {
            return 0;
        }
    }

    public String getItemByIndex(int index) {
        if (jenisRecode == 0) {
            return ((ObjectCategorySimple) this).getPilihanOption().get(index);
        } else if (jenisRecode == 1) {
            return ((ObjectCategoryRecode) this).getObjectItemRecodeDoubles().get(index).getHasil();
        } else if (jenisRecode == 2) {
            return ((ObjectCategoryRecode) this).getObjectItemRecodeStrings().get(index).getHasil();
        } else {
            return null;
        }
    }

    public ArrayList<String> getAllColumn() {
        ArrayList<String> returnValue = new ArrayList<>();
        returnValue.add(getNameColumn());
        if (objectCategorys != null) {
            for (ObjectCategory itemChild : objectCategorys) {
                returnValue.addAll(itemChild.getAllColumn());
            }
        }
        return returnValue;
    }

}

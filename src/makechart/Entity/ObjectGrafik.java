/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Entity;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class ObjectGrafik {

    private String uid;
    private String name;
    private String sourceData;
    private String desc;
    private boolean editable;
    private List<ObjectRowFilter> objectRowFilters;
    private List<ObjectCategory> rowCategory;
    private List<ObjectCategory> columnCategory;
    private List<ObjectGroupBy> objectGroupBys;
    private List<ObjectOrderBy> objectOrderBys;
    private String nameColumValue;
    private EnumTypeColumnResult typeResult;

    public ObjectGrafik() {
        objectRowFilters = new ArrayList<>();
        rowCategory = new ArrayList<>();
        columnCategory = new ArrayList<>();
        objectGroupBys = new ArrayList<>();
        objectOrderBys = new ArrayList<>();
    }

    public ObjectGrafik(String uid, String name, String desc, boolean editable) {
        this.uid = uid;
        this.name = name;
        this.desc = desc;
        this.editable = editable;
        rowCategory = new ArrayList<>();
        columnCategory = new ArrayList<>();
        objectRowFilters = new ArrayList<>();
        objectGroupBys = new ArrayList<>();
        objectOrderBys = new ArrayList<>();
    }

    public ObjectGrafik(String uid, String name, String sourceData, String desc, boolean editable,
            List<ObjectCategory> rowCategory, List<ObjectCategory> columnCategory,
            List<ObjectRowFilter> objectRowFilters,
            List<ObjectGroupBy> objectGroupBys, List<ObjectOrderBy> objectOrderBys,
            String nameColumValue, EnumTypeColumnResult typeResult) {
        this.uid = uid;
        this.name = name;
        this.sourceData = sourceData;
        this.desc = desc;
        this.editable = editable;
        this.rowCategory = rowCategory;
        this.columnCategory = columnCategory;
        this.objectRowFilters = objectRowFilters;
        this.objectGroupBys = objectGroupBys;
        this.objectOrderBys = objectOrderBys;
        this.nameColumValue = nameColumValue;
        this.typeResult = typeResult;
    }

    /**
     * @return the sourceData
     */
    public String getSourceData() {
        return sourceData;
    }

    /**
     * @param sourceData the sourceData to set
     */
    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
    }

    /**
     * @return the objectRowFilters
     */
    public List<ObjectRowFilter> getObjectRowFilters() {
        return objectRowFilters;
    }

    /**
     * @param objectRowFilters the objectRowFilters to set
     */
    public void setObjectRowFilters(List<ObjectRowFilter> objectRowFilters) {
        this.objectRowFilters = objectRowFilters;
    }

    public void addRowFilter(ObjectRowFilter item) {
        this.objectRowFilters.add(item);
    }

    public void removeRowFilter(ObjectRowFilter item) {
        this.objectRowFilters.remove(item);
    }

    public void addColumnCategory(ObjectCategory item) {
        this.getColumnCategory().add(item);
    }

    public void removeColumnCategory(ObjectCategorySimple item) {
        this.getColumnCategory().remove(item);
    }

    public void addRowCategory(ObjectCategory item) {
        this.getRowCategory().add(item);
    }

    public void removeRowCategory(ObjectCategorySimple item) {
        this.getRowCategory().remove(item);
    }

    /**
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @return the objectGroupBys
     */
    public List<ObjectGroupBy> getObjectGroupBys() {
        return objectGroupBys;
    }

    /**
     * @param objectGroupBys the objectGroupBys to set
     */
    public void setObjectGroupBys(List<ObjectGroupBy> objectGroupBys) {
        this.objectGroupBys = objectGroupBys;
    }

    /**
     * @return the objectOrderBys
     */
    public List<ObjectOrderBy> getObjectOrderBys() {
        return objectOrderBys;
    }

    /**
     * @param objectOrderBys the objectOrderBys to set
     */
    public void setObjectOrderBys(List<ObjectOrderBy> objectOrderBys) {
        this.objectOrderBys = objectOrderBys;
    }

    public void addGroubBy(ObjectGroupBy objectGroupBy) {
        this.objectGroupBys.add(objectGroupBy);
    }

    public void removeGroubBy(ObjectGroupBy objectGroupBy) {
        this.objectGroupBys.remove(objectGroupBy);
    }

    public void addOrderBy(ObjectOrderBy objectOrderBy) {
        this.objectOrderBys.add(objectOrderBy);
    }

    public void removeOrderBy(ObjectOrderBy objectOrderBy) {
        this.objectOrderBys.remove(objectOrderBy);
    }

    public void setJsonObject(JSONObject object) {
        try {
            this.uid = object.getString("uid");
            this.name = object.getString("name");
            this.sourceData = object.getString("sourceData");
            this.desc = object.getString("desc");
            this.editable = object.getBoolean("editable");
            this.nameColumValue = object.getString("nameColumValue");
            this.typeResult = EnumTypeColumnResult.valueOf(object.getString("typeResult"));
            if (object.has("rowCategory") && object.get("rowCategory") != null) {
                JSONArray objectCategoryArr = object.getJSONArray("rowCategory");
                for (int i = 0; i < objectCategoryArr.length(); i++) {
                    JSONObject itemCategoryJSON = objectCategoryArr.getJSONObject(i);
                    EnumCategoryTable itemCategoryType = EnumCategoryTable.valueOf(itemCategoryJSON.getString("enumCategoryTable"));
                    if (itemCategoryType == EnumCategoryTable.SIMPLE) {
                        ObjectCategorySimple itemCategoryClass = new ObjectCategorySimple();
                        itemCategoryClass.setJsonObject(itemCategoryJSON);
                        this.rowCategory.add(itemCategoryClass);
                    } else if (itemCategoryType == EnumCategoryTable.RECODE) {
                        ObjectCategoryRecode itemCategoryClass = new ObjectCategoryRecode();
                        itemCategoryClass.setJsonObject(itemCategoryJSON);
                        this.rowCategory.add(itemCategoryClass);
                    }
                }
            }
            if (object.has("columnCategory") && object.get("columnCategory") != null) {
                JSONArray objectCategoryArr = object.getJSONArray("columnCategory");
                for (int i = 0; i < objectCategoryArr.length(); i++) {
                    JSONObject itemCategoryJSON = objectCategoryArr.getJSONObject(i);
                    EnumCategoryTable itemCategoryType = EnumCategoryTable.valueOf(itemCategoryJSON.getString("enumCategoryTable"));
                    if (itemCategoryType == EnumCategoryTable.SIMPLE) {
                        ObjectCategorySimple itemCategoryClass = new ObjectCategorySimple();
                        itemCategoryClass.setJsonObject(itemCategoryJSON);
                        this.columnCategory.add(itemCategoryClass);
                    } else if (itemCategoryType == EnumCategoryTable.RECODE) {
                        ObjectCategoryRecode itemCategoryClass = new ObjectCategoryRecode();
                        itemCategoryClass.setJsonObject(itemCategoryJSON);
                        this.columnCategory.add(itemCategoryClass);
                    }
                }
            }
            if (object.has("objectRowFilters") && object.get("objectRowFilters") != null) {
                JSONArray objectRowFiltersArr = object.getJSONArray("objectRowFilters");
                for (int i = 0; i < objectRowFiltersArr.length(); i++) {
                    JSONObject itemRecodeJSON = objectRowFiltersArr.getJSONObject(i);
                    ObjectRowFilter itemRecde = new ObjectRowFilter();
                    itemRecde.setJsonObject(itemRecodeJSON);
                    this.objectRowFilters.add(itemRecde);
                }
            }
            if (object.has("objectOrderBys") && object.get("objectOrderBys") != null) {
                JSONArray ObjectOrderByArr = object.getJSONArray("objectOrderBys");
                for (int i = 0; i < ObjectOrderByArr.length(); i++) {
                    ObjectOrderBy objectOrderBy = new ObjectOrderBy();
                    objectOrderBy.setJsonObject(ObjectOrderByArr.getJSONObject(i));
                    this.objectOrderBys.add(objectOrderBy);
                }
            }
            if (object.has("objectGroupBys") && object.get("objectGroupBys") != null) {
                JSONArray ObjectGroubByArr = object.getJSONArray("objectGroupBys");
                for (int i = 0; i < ObjectGroubByArr.length(); i++) {
                    ObjectGroupBy objectGroupBy = new ObjectGroupBy();
                    objectGroupBy.setJsonObject(ObjectGroubByArr.getJSONObject(i));
                    this.objectGroupBys.add(objectGroupBy);
                }
            }
        } catch (Exception ex) {
            System.out.println("JSON 1 " + ex);
            ex.printStackTrace();
        }
    }

    public JSONObject getJSONObject() {
        try {
            JSONObject object = new JSONObject();
            object.put("uid", uid);
            object.put("name", name);
            object.put("sourceData", sourceData);
            object.put("desc", desc);
            object.put("editable", editable);

            if (rowCategory == null || rowCategory.size() == 0) {
                object.put("rowCategory", (String) null);
            } else {
                JSONArray objectCategoryArray = new JSONArray();
                for (ObjectCategory itemCategory : rowCategory) {
                    if (itemCategory instanceof ObjectCategorySimple) {
                        objectCategoryArray.put(((ObjectCategorySimple) itemCategory).getJSONObject());
                    } else if (itemCategory instanceof ObjectCategoryRecode) {
                        objectCategoryArray.put(((ObjectCategoryRecode) itemCategory).getJSONObject());
                    }
                }
                object.put("rowCategory", objectCategoryArray);
            }
            if (columnCategory == null || columnCategory.size() == 0) {
                object.put("columnCategory", (String) null);
            } else {
                JSONArray objectCategoryArray = new JSONArray();
                for (ObjectCategory itemCategory : columnCategory) {
                    if (itemCategory instanceof ObjectCategorySimple) {
                        objectCategoryArray.put(((ObjectCategorySimple) itemCategory).getJSONObject());
                    } else if (itemCategory instanceof ObjectCategoryRecode) {
                        objectCategoryArray.put(((ObjectCategoryRecode) itemCategory).getJSONObject());
                    }
                }
                object.put("columnCategory", objectCategoryArray);
            }

            if (objectRowFilters == null || objectRowFilters.size() == 0) {
                object.put("objectRowFilters", (String) null);
            } else {
                JSONArray rowFilterArray = new JSONArray();
                for (ObjectRowFilter itemRow : objectRowFilters) {
                    rowFilterArray.put(itemRow.getJSONObject());
                }
                object.put("objectRowFilters", rowFilterArray);
            }

            if (objectOrderBys == null || objectOrderBys.size() == 0) {
                object.put("objectOrderBys", (String) null);
            } else {
                JSONArray objectOrderArray = new JSONArray();
                for (ObjectOrderBy itemOrder : objectOrderBys) {
                    objectOrderArray.put(itemOrder.getJSONObject());
                }
                object.put("objectOrderBys", objectOrderArray);
            }

            if (objectGroupBys == null || objectGroupBys.size() == 0) {
                object.put("objectGroupBys", (String) null);
            } else {
                JSONArray objectGroupArray = new JSONArray();
                for (ObjectGroupBy itemGroup : objectGroupBys) {
                    objectGroupArray.put(itemGroup.getJSONObject());
                }
                object.put("objectGroupBys", objectGroupArray);
            }
            object.put("nameColumValue", nameColumValue);
            object.put("typeResult", typeResult.name());

            return object;
        } catch (Exception ex) {
            System.out.println("JSON 2 " + ex);
            ex.printStackTrace();
            return null;
        }
    }

//    public String getQueryGrafik(){
//        String query = "SELECT ";
//        for(ObjectCategorySimple itemColumFilter: objectColumnFilters){
//            if(itemColumFilter.getColumnFunction() == EnumSQLColumnFunction.NORMAL){
//                query = query +  itemColumFilter.getNameColumn()+" , ";
//            }else if(itemColumFilter.getColumnFunction() == EnumSQLColumnFunction.AVERAGE){
//                query = query + " AVG(" +itemColumFilter.getNameColumn()+") , ";
//            }else if(itemColumFilter.getColumnFunction() == EnumSQLColumnFunction.COUNT){
//                query = query + " COUNT(" +itemColumFilter.getNameColumn()+") , ";
//            }else if(itemColumFilter.getColumnFunction() == EnumSQLColumnFunction.SUM){
//                query = query + " SUM(" +itemColumFilter.getNameColumn()+") , ";
//            }
//        }
//        for(ObjectCategoryRecode itemRecode: objectRecodes){
//            query = query +itemRecode.getCaseRecode()+" . ";
//        }
//        query = query.substring(0, query.length()-2)+ " FROM ISIDATA ";
//        
//        if(objectRowFilters.size()>0){
//            String whereClouse = " WHERE ";
//            for(ObjectRowFilter itemRowFilter: objectRowFilters){
//                if(itemRowFilter.getRowFunction()== EnumSQLRowFunction.EQUAL){
//                    if(itemRowFilter.getDataType() == EnumDataType.TEXT){
//                        whereClouse = whereClouse +itemRowFilter.getNameColumn()+" = '"+itemRowFilter.getValueOp()+"' AND ";
//                    }else if(itemRowFilter.getDataType() == EnumDataType.INTEGER || 
//                            itemRowFilter.getDataType() == EnumDataType.REAL){
//                        whereClouse = whereClouse +itemRowFilter.getNameColumn()+" = "+itemRowFilter.getValueOp()+" AND ";
//                    }
//                }else if(itemRowFilter.getRowFunction()== EnumSQLRowFunction.NOT_EQUAL){
//                    if(itemRowFilter.getDataType() == EnumDataType.TEXT){
//                        whereClouse = whereClouse +itemRowFilter.getNameColumn()+" != '"+itemRowFilter.getValueOp()+"' AND ";
//                    }else if(itemRowFilter.getDataType() == EnumDataType.INTEGER || 
//                            itemRowFilter.getDataType() == EnumDataType.REAL){
//                        whereClouse = whereClouse +itemRowFilter.getNameColumn()+" != "+itemRowFilter.getValueOp()+" AND ";
//                    }
//                }else if(itemRowFilter.getRowFunction()== EnumSQLRowFunction.LESS_THAN){
//                    if(itemRowFilter.getDataType() == EnumDataType.TEXT){
//                        whereClouse = whereClouse +itemRowFilter.getNameColumn()+" < '"+itemRowFilter.getValueOp()+"' AND ";
//                    }else if(itemRowFilter.getDataType() == EnumDataType.INTEGER || 
//                            itemRowFilter.getDataType() == EnumDataType.REAL){
//                        whereClouse = whereClouse +itemRowFilter.getNameColumn()+" < "+itemRowFilter.getValueOp()+" AND ";
//                    }
//                }else if(itemRowFilter.getRowFunction()== EnumSQLRowFunction.LESS_THAN_OR_EQUAL){
//                    if(itemRowFilter.getDataType() == EnumDataType.TEXT){
//                        whereClouse = whereClouse +itemRowFilter.getNameColumn()+" <= '"+itemRowFilter.getValueOp()+"' AND ";
//                    }else if(itemRowFilter.getDataType() == EnumDataType.INTEGER || 
//                            itemRowFilter.getDataType() == EnumDataType.REAL){
//                        whereClouse = whereClouse +itemRowFilter.getNameColumn()+" <= "+itemRowFilter.getValueOp()+" AND ";
//                    }
//                }else if(itemRowFilter.getRowFunction()== EnumSQLRowFunction.MORE_THAN){
//                    if(itemRowFilter.getDataType() == EnumDataType.TEXT){
//                        whereClouse = whereClouse +itemRowFilter.getNameColumn()+" > '"+itemRowFilter.getValueOp()+"' AND ";
//                    }else if(itemRowFilter.getDataType() == EnumDataType.INTEGER || 
//                            itemRowFilter.getDataType() == EnumDataType.REAL){
//                        whereClouse = whereClouse +itemRowFilter.getNameColumn()+" > "+itemRowFilter.getValueOp()+" AND ";
//                    }
//                }else if(itemRowFilter.getRowFunction()== EnumSQLRowFunction.MORE_THAN_OR_EQUAL){
//                    if(itemRowFilter.getDataType() == EnumDataType.TEXT){
//                        whereClouse = whereClouse +itemRowFilter.getNameColumn()+" >= '"+itemRowFilter.getValueOp()+"' AND ";
//                    }else if(itemRowFilter.getDataType() == EnumDataType.INTEGER || 
//                            itemRowFilter.getDataType() == EnumDataType.REAL){
//                        whereClouse = whereClouse +itemRowFilter.getNameColumn()+" >= "+itemRowFilter.getValueOp()+" AND ";
//                    }
//                }
//                query = query + whereClouse.substring(0, whereClouse.length()-4);
//            }
//        }
//        if(objectGroupBys.size()>0){
//            String groupByClouse = " GROUP BY ";
//            for(ObjectGroupBy itemGroupBy: objectGroupBys){
//                groupByClouse = groupByClouse + itemGroupBy.getNameColumn()+" , ";
//            }
//            query = query + groupByClouse.substring(0, groupByClouse.length()-2);
//        }
//        if(objectOrderBys.size()>0){
//            String orderByClouse = " ORDER BY ";
//            for(ObjectOrderBy itemOrder: objectOrderBys){
//                if(itemOrder.getType() == EnumTypeOrder.ASC){
//                    orderByClouse = orderByClouse + itemOrder.getNameColumn()+" ASC , ";
//                }else if(itemOrder.getType() == EnumTypeOrder.DESC){
//                    orderByClouse = orderByClouse + itemOrder.getNameColumn()+" DESC , ";
//                }
//            }
//            query = query + orderByClouse.substring(0, orderByClouse.length()-2);
//        }
//        
//        return query;
//    }
    /**
     * @return the nameColumValue
     */
    public String getNameColumValue() {
        return nameColumValue;
    }

    /**
     * @param nameColumValue the nameColumValue to set
     */
    public void setNameColumValue(String nameColumValue) {
        this.nameColumValue = nameColumValue;
    }

    /**
     * @return the typeResult
     */
    public EnumTypeColumnResult getTypeResult() {
        return typeResult;
    }

    /**
     * @param typeResult the typeResult to set
     */
    public void setTypeResult(EnumTypeColumnResult typeResult) {
        this.typeResult = typeResult;
    }

    /**
     * @return the rowCategory
     */
    public List<ObjectCategory> getRowCategory() {
        return rowCategory;
    }

    /**
     * @param rowCategory the rowCategory to set
     */
    public void setRowCategory(List<ObjectCategory> rowCategory) {
        this.rowCategory = rowCategory;
    }

    /**
     * @return the columnCategory
     */
    public List<ObjectCategory> getColumnCategory() {
        return columnCategory;
    }

    /**
     * @param columnCategory the columnCategory to set
     */
    public void setColumnCategory(List<ObjectCategory> columnCategory) {
        this.columnCategory = columnCategory;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Entity;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class ObjectTable {

    private String uid;
    private String name;
    private String sourceData;
    private String desc;
    private boolean editable;
    private List<ObjectCategory> rowCategory;
    private List<ObjectCategory> columnCategory;
    private List<ObjectRowFilter> objectRowFilters;
    private List<EnumAdditional> enumAdditionals;
    private List<ObjectGroupBy> objectGroupBys;
    private List<ObjectOrderBy> objectOrderBys;
    private String nameColumWeight;
    private List<ObjectValueTable> values;
    public static final String DEfAULT_NAME_HASIL = "v_aaaaaaaaabbbbbbbbb";

    public ObjectTable() {
        rowCategory = new ArrayList<>();
        columnCategory = new ArrayList<>();
        objectRowFilters = new ArrayList<>();
        enumAdditionals = new ArrayList<>();
        objectGroupBys = new ArrayList<>();
        objectOrderBys = new ArrayList<>();
        values = new ArrayList<>();
    }

    public ObjectTable(String uid, String name, String desc, boolean editable) {
        this.uid = uid;
        this.name = name;
        this.desc = desc;
        this.editable = editable;
        columnCategory = new ArrayList<>();
        rowCategory = new ArrayList<>();
        objectRowFilters = new ArrayList<>();
        enumAdditionals = new ArrayList<>();
        objectGroupBys = new ArrayList<>();
        objectOrderBys = new ArrayList<>();
        values = new ArrayList<>();
    }

    public ObjectTable(String uid, String name, String sourceData, String desc, boolean editable,
            List<ObjectCategory> rowCategory, List<ObjectCategory> columnCategory,
            List<ObjectRowFilter> objectRowFilters, List<EnumAdditional> enumAdditionals,
            List<ObjectGroupBy> objectGroupBys, List<ObjectOrderBy> objectOrderBys,
            String nameColumWeight, List<ObjectValueTable> values) {
        this.uid = uid;
        this.name = name;
        this.sourceData = sourceData;
        this.desc = desc;
        this.editable = editable;
        this.rowCategory = rowCategory;
        this.columnCategory = columnCategory;
        this.objectRowFilters = objectRowFilters;
        this.enumAdditionals = enumAdditionals;
        this.objectGroupBys = objectGroupBys;
        this.objectOrderBys = objectOrderBys;
        this.nameColumWeight = nameColumWeight;
        this.values = values;
    }

    /**
     * @return the nameColumWeight
     */
    public String getNameColumWeight() {
        return nameColumWeight;
    }

    /**
     * @param nameColumWeight the nameColumWeight to set
     */
    public void setNameColumWeight(String nameColumWeight) {
        this.nameColumWeight = nameColumWeight;
    }

    /**
     * @return the values
     */
    public List<ObjectValueTable> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(List<ObjectValueTable> values) {
        this.values = values;
    }

    public void addColumnCategory(ObjectCategory item) {
        this.columnCategory.add(item);
    }

    public void removeColumnCategory(ObjectCategorySimple item) {
        this.columnCategory.remove(item);
    }

    public void addRowCategory(ObjectCategory item) {
        this.rowCategory.add(item);
    }

    public void removeRowCategory(ObjectCategorySimple item) {
        this.rowCategory.remove(item);
    }

    public void addRowFilter(ObjectRowFilter item) {
        this.getObjectRowFilters().add(item);
    }

    public void removeRowFilter(ObjectRowFilter item) {
        this.getObjectRowFilters().remove(item);
    }

    public void addEnumAdditional(EnumAdditional item) {
        this.getEnumAdditionals().add(item);
    }

    public void removeEnumAdditional(EnumAdditional item) {
        this.getEnumAdditionals().remove(item);
    }

    public String getStringEnumValue() {
        String returnString = "";
        for (EnumAdditional enumItem : getEnumAdditionals()) {
            returnString = returnString + enumItem.name() + ",";
        }
        return returnString.substring(0, returnString.length() - 1);
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

    /**
     * @return the enumAdditionals
     */
    public List<EnumAdditional> getEnumAdditionals() {
        return enumAdditionals;
    }

    /**
     * @param enumAdditionals the enumAdditionals to set
     */
    public void setEnumAdditionals(List<EnumAdditional> enumAdditionals) {
        this.enumAdditionals = enumAdditionals;
    }

    public void setJsonObject(JSONObject object) {
        try {
            this.uid = object.getString("uid");
            this.name = object.getString("name");
            if (object.has("sourceData")) {
                this.sourceData = object.getString("sourceData");
            }else{
                this.sourceData = "";
            }
            this.desc = object.getString("desc");
            this.editable = object.getBoolean("editable");
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
                    this.getObjectRowFilters().add(itemRecde);
                }
            }
            if (object.has("enumAdditionals") && object.get("enumAdditionals") != null) {
                JSONArray EnumAdditionalArr = object.getJSONArray("enumAdditionals");
                for (int i = 0; i < EnumAdditionalArr.length(); i++) {
                    this.getEnumAdditionals().add(EnumAdditional.valueOf(EnumAdditionalArr.getString(i)));
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
            if (object.has("objectOrderBys") && object.get("objectGroupBys") != null) {
                JSONArray ObjectGroubByArr = object.getJSONArray("objectOrderBys");
                for (int i = 0; i < ObjectGroubByArr.length(); i++) {
                    ObjectGroupBy objectGroupBy = new ObjectGroupBy();
                    objectGroupBy.setJsonObject(ObjectGroubByArr.getJSONObject(i));
                    this.objectGroupBys.add(objectGroupBy);
                }
            }
            if (object.has("values") && object.get("values") != null) {
                JSONArray ObjectValuesArr = object.getJSONArray("values");
                for (int i = 0; i < ObjectValuesArr.length(); i++) {
                    ObjectValueTable itemTable = new ObjectValueTable();
                    itemTable.setJSONObject(ObjectValuesArr.getJSONObject(i));
                    this.values.add(itemTable);
                }
            }
            this.nameColumWeight = object.getString("nameColumWeight");
            //this.typeResult = EnumTypeColumnResult.valueOf(object.getString("typeResult"));
        } catch (Exception ex) {
            System.out.println("JSON 1 " + ex);
            ex.printStackTrace();
        }
    }

    public JSONObject getJSONObject() {
        try {
            JSONObject object = new JSONObject();
            object.put("uid", getUid());
            object.put("name", getName());
            object.put("sourceData", getSourceData());
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

            if (enumAdditionals == null || enumAdditionals.size() == 0) {
                object.put("enumAdditionals", (String) null);
            } else {
                JSONArray enumAddArray = new JSONArray();
                for (EnumAdditional itemAdditional : enumAdditionals) {
                    enumAddArray.put(itemAdditional.name());
                }
                object.put("enumAdditionals", enumAddArray);
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

            if (getValues() == null || getValues().size() == 0) {
                object.put("values", (String) null);
            } else {
                JSONArray objectGroupArray = new JSONArray();
                for (ObjectValueTable itemValue : getValues()) {
                    objectGroupArray.put(itemValue.getJSONObject());
                }
                object.put("values", objectGroupArray);
            }
            object.put("nameColumWeight", nameColumWeight);

            //object.put("typeResult", typeResult.name());
            return object;
        } catch (Exception ex) {
            System.out.println("JSON 2 " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    public JSONObject getExportJSONObject() {
        try {
            JSONObject object = new JSONObject();
            object.put("uid", getUid());
            object.put("name", getName());
            object.put("sourceData", "");
            object.put("desc", desc);
            object.put("editable", false);
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

            if (enumAdditionals == null || enumAdditionals.size() == 0) {
                object.put("enumAdditionals", (String) null);
            } else {
                JSONArray enumAddArray = new JSONArray();
                for (EnumAdditional itemAdditional : enumAdditionals) {
                    enumAddArray.put(itemAdditional.name());
                }
                object.put("enumAdditionals", enumAddArray);
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

            if (getValues() == null || getValues().size() == 0) {
                object.put("values", (String) null);
            } else {
                JSONArray objectGroupArray = new JSONArray();
                for (ObjectValueTable itemValue : getValues()) {
                    objectGroupArray.put(itemValue.getJSONObject());
                }
                object.put("values", objectGroupArray);
            }
            object.put("nameColumWeight", nameColumWeight);

            //object.put("typeResult", typeResult.name());
            return object;
        } catch (Exception ex) {
            System.out.println("JSON 2 " + ex);
            ex.printStackTrace();
            return null;
        }
    }
//    public String getQueryTable(){
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
//        if(getObjectRowFilters().size()>0){
//            String whereClouse = " WHERE ";
//            for(ObjectRowFilter itemRowFilter: getObjectRowFilters()){
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

    public ArrayList<TableResult> getTableResults() {
        ArrayList<TableResult> hasil = new ArrayList<>();
        ArrayList<TableResult> row = new ArrayList<>();

        String orderByClouse = "";
        String whereClouse = "";
        if (objectOrderBys.size() > 0) {
            orderByClouse = " ORDER BY ";
            for (ObjectOrderBy itemOrder : objectOrderBys) {
                if (itemOrder.getType() == EnumTypeOrder.ASC) {
                    orderByClouse = orderByClouse + itemOrder.getNameColumn() + " ASC , ";
                } else if (itemOrder.getType() == EnumTypeOrder.DESC) {
                    orderByClouse = orderByClouse + itemOrder.getNameColumn() + " DESC , ";
                }
            }
            orderByClouse = orderByClouse.substring(0, orderByClouse.length() - 2);
        }

        if (getObjectRowFilters().size() > 0) {
            whereClouse = " WHERE ";
            for (ObjectRowFilter itemRowFilter : getObjectRowFilters()) {
                if (itemRowFilter.getRowFunction() == EnumSQLRowFunction.EQUAL) {
                    if (itemRowFilter.getDataType() == EnumDataType.TEXT) {
                        whereClouse = whereClouse + itemRowFilter.getNameColumn() + " = '" + itemRowFilter.getValueOp() + "' AND ";
                    } else if (itemRowFilter.getDataType() == EnumDataType.INTEGER
                            || itemRowFilter.getDataType() == EnumDataType.REAL) {
                        whereClouse = whereClouse + itemRowFilter.getNameColumn() + " = " + itemRowFilter.getValueOp() + " AND ";
                    }
                } else if (itemRowFilter.getRowFunction() == EnumSQLRowFunction.NOT_EQUAL) {
                    if (itemRowFilter.getDataType() == EnumDataType.TEXT) {
                        whereClouse = whereClouse + itemRowFilter.getNameColumn() + " != '" + itemRowFilter.getValueOp() + "' AND ";
                    } else if (itemRowFilter.getDataType() == EnumDataType.INTEGER
                            || itemRowFilter.getDataType() == EnumDataType.REAL) {
                        whereClouse = whereClouse + itemRowFilter.getNameColumn() + " != " + itemRowFilter.getValueOp() + " AND ";
                    }
                } else if (itemRowFilter.getRowFunction() == EnumSQLRowFunction.LESS_THAN) {
                    if (itemRowFilter.getDataType() == EnumDataType.TEXT) {
                        whereClouse = whereClouse + itemRowFilter.getNameColumn() + " < '" + itemRowFilter.getValueOp() + "' AND ";
                    } else if (itemRowFilter.getDataType() == EnumDataType.INTEGER
                            || itemRowFilter.getDataType() == EnumDataType.REAL) {
                        whereClouse = whereClouse + itemRowFilter.getNameColumn() + " < " + itemRowFilter.getValueOp() + " AND ";
                    }
                } else if (itemRowFilter.getRowFunction() == EnumSQLRowFunction.LESS_THAN_OR_EQUAL) {
                    if (itemRowFilter.getDataType() == EnumDataType.TEXT) {
                        whereClouse = whereClouse + itemRowFilter.getNameColumn() + " <= '" + itemRowFilter.getValueOp() + "' AND ";
                    } else if (itemRowFilter.getDataType() == EnumDataType.INTEGER
                            || itemRowFilter.getDataType() == EnumDataType.REAL) {
                        whereClouse = whereClouse + itemRowFilter.getNameColumn() + " <= " + itemRowFilter.getValueOp() + " AND ";
                    }
                } else if (itemRowFilter.getRowFunction() == EnumSQLRowFunction.MORE_THAN) {
                    if (itemRowFilter.getDataType() == EnumDataType.TEXT) {
                        whereClouse = whereClouse + itemRowFilter.getNameColumn() + " > '" + itemRowFilter.getValueOp() + "' AND ";
                    } else if (itemRowFilter.getDataType() == EnumDataType.INTEGER
                            || itemRowFilter.getDataType() == EnumDataType.REAL) {
                        whereClouse = whereClouse + itemRowFilter.getNameColumn() + " > " + itemRowFilter.getValueOp() + " AND ";
                    }
                } else if (itemRowFilter.getRowFunction() == EnumSQLRowFunction.MORE_THAN_OR_EQUAL) {
                    if (itemRowFilter.getDataType() == EnumDataType.TEXT) {
                        whereClouse = whereClouse + itemRowFilter.getNameColumn() + " >= '" + itemRowFilter.getValueOp() + "' AND ";
                    } else if (itemRowFilter.getDataType() == EnumDataType.INTEGER
                            || itemRowFilter.getDataType() == EnumDataType.REAL) {
                        whereClouse = whereClouse + itemRowFilter.getNameColumn() + " >= " + itemRowFilter.getValueOp() + " AND ";
                    }
                }
            }
            whereClouse = whereClouse.substring(0, whereClouse.length() - 4);
        }
        String selecet = "";
        if (getNameColumWeight() != null && !getNameColumWeight().trim().equals("")) {
            if (values.size() == 0) {
                selecet = selecet + " SUM(" + getNameColumWeight() + ") AS " + DEfAULT_NAME_HASIL + " , ";
            } else {
                for (ObjectValueTable itemValues : getValues()) {
                    if (itemValues.getFunction() == EnumSQLColumnFunction.COUNT) {
                        selecet = selecet + " SUM(" + getNameColumWeight() + ") AS " + itemValues.getNameColumnHasil() + " , ";
                    } else if (itemValues.getFunction() == EnumSQLColumnFunction.AVERAGE) {
                        selecet = selecet + " SUM(" + itemValues.getNameColumn() + "*" + getNameColumWeight() + ") / SUM(" + getNameColumWeight() + ") AS v_" + itemValues.getNameColumnHasil() + " , ";
                    } else if (itemValues.getFunction() == EnumSQLColumnFunction.NORMAL) {
                        selecet = selecet + " " + itemValues.getNameColumn() + "*" + getNameColumWeight() + " AS v_" + itemValues.getNameColumnHasil() + " , ";
                    } else if (itemValues.getFunction() == EnumSQLColumnFunction.SUM) {
                        selecet = selecet + " SUM(" + itemValues.getNameColumn() + "*" + getNameColumWeight() + ") AS v_" + itemValues.getNameColumnHasil() + " , ";
                    }
                }
            }
        } else {
            if (values.size() == 0) {
                selecet = selecet + " COUNT(*) AS " + DEfAULT_NAME_HASIL + " , ";
            } else {
                for (ObjectValueTable itemValues : getValues()) {
                    if (itemValues.getFunction() == EnumSQLColumnFunction.COUNT) {
                        selecet = selecet + " COUNT(" + itemValues.getNameColumn() + ") AS " + itemValues.getNameColumnHasil() + " , ";
                    } else if (itemValues.getFunction() == EnumSQLColumnFunction.AVERAGE) {
                        selecet = selecet + " AVG(" + itemValues.getNameColumn() + ") AS v_" + itemValues.getNameColumnHasil() + " , ";
                    } else if (itemValues.getFunction() == EnumSQLColumnFunction.NORMAL) {
                        selecet = selecet + " " + itemValues.getNameColumn() + " AS v_" + itemValues.getNameColumnHasil() + " , ";
                    } else if (itemValues.getFunction() == EnumSQLColumnFunction.SUM) {
                        selecet = selecet + " SUM(" + itemValues.getNameColumn() + ") AS v_" + itemValues.getNameColumnHasil() + " , ";
                    }
                }
            }
        }

        TableResult root = new TableResult();
        root.setSelect(selecet);
        root.setGroupby("");
        root.setOrderby(orderByClouse);
        root.setWhere(whereClouse);
        root.setKordinatColumn(0);
        root.setKordinatRow(0);
        //ArrayList<>
        if (rowCategory.size() == 0) {
            row.add(root);
        } else {
            for (ObjectCategory itemRow : rowCategory) {
                row.addAll(itemRow.processResultTable(root, true));
            }
        }
        if (columnCategory.size() == 0) {
            hasil.addAll(row);
        } else {
            for (TableResult itemRow : row) {
                for (ObjectCategory itemColumn : columnCategory) {
                    hasil.addAll(itemColumn.processResultTable(itemRow, false));
                }
            }
        }
        for (int i = 0; i < hasil.size(); i++) {
            hasil.get(i).setKordinatColumn(i);
            hasil.get(i).setKordinatRow(i);
        }
        return hasil;
    }

    public boolean isDataSourceMemenuhi(ArrayList<String> columns) {
        ArrayList<String> allColumn = new ArrayList<>();
        for (ObjectCategory item : rowCategory) {
            allColumn.addAll(item.getAllColumn());
        }
        for (ObjectCategory item : columnCategory) {
            allColumn.addAll(item.getAllColumn());
        }
        if (nameColumWeight != null && !nameColumWeight.trim().equals("")) {
            allColumn.add(nameColumWeight);
        }
        for (ObjectRowFilter item : objectRowFilters) {
            allColumn.add(item.getNameColumn());
        }
        for (ObjectValueTable item : values) {
            allColumn.add(item.getNameColumn());
        }
        System.out.println(name);
        for(String item : allColumn){
            System.out.println(item);
        }
        return columns.containsAll(allColumn);
    }
}

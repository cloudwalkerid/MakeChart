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
public class ObjectCategoryRecode extends ObjectCategory{

    private EnumTypeRecode type;
    private ArrayList<ObjectItemRecodeDouble> objectItemRecodeDoubles;
    private ArrayList<ObjectItemRecodeString> objectItemRecodeStrings;

    public ObjectCategoryRecode() {
        this.objectItemRecodeDoubles = new ArrayList<>();
        this.objectItemRecodeStrings = new ArrayList<>();
        this.objectCategorys = new ArrayList<ObjectCategory>();
    }

    public ObjectCategoryRecode(int jenisRecode, int jenisLabel, EnumDataType dataType, String nameColumn, String nameColumnHasil, String labelValue, 
            String labelPercentage, EnumTypeRecode type, ArrayList<ObjectItemRecodeDouble> objectItemRecodeDoubles, 
            ArrayList<ObjectItemRecodeString> objectItemRecodeStrings) {
        this.jenisRecode = jenisRecode;
        this.jenisLabel = jenisLabel;
        this.nameColumn = nameColumn;
        this.dataType = dataType;
        this.nameColumnHasil = nameColumnHasil.replaceAll("-", "");;
        this.type = type;
        this.objectItemRecodeDoubles = objectItemRecodeDoubles;
        this.objectItemRecodeStrings = objectItemRecodeStrings;
        this.objectCategorys = new ArrayList<ObjectCategory>();
        this.enumCategoryTable = EnumCategoryTable.RECODE;
        this.labelValue = labelValue;
        this.labelPercentage = labelPercentage;
    }
    
    public ObjectCategoryRecode cloneObjectCategoryRecode(){
        ObjectCategoryRecode cloneThis = new ObjectCategoryRecode();
        cloneThis.setJenisRecode(jenisRecode);
        cloneThis.setJenisLabel(jenisLabel);
        cloneThis.setNameColumn(nameColumn);
        cloneThis.setDataType(dataType);
        cloneThis.setNameColumnHasil(nameColumnHasil);
        cloneThis.setLabelValue(labelValue);
        cloneThis.setLabelPercentage(labelPercentage);
        cloneThis.setEnumCategoryTable(enumCategoryTable);
        ArrayList<ObjectCategory> cloneChild = new ArrayList<>();
        for(ObjectCategory item : objectCategorys){
            if(item instanceof ObjectCategorySimple){
                cloneChild.add(((ObjectCategorySimple) item).cloneObjectCategorySimple());
            }else if(item instanceof ObjectCategoryRecode){
                cloneChild.add(((ObjectCategoryRecode) item).cloneObjectCategoryRecode());
            }
        }
        cloneThis.setObjectCategorys(cloneChild);
        if(objectItemRecodeDoubles!=null){
            ArrayList<ObjectItemRecodeDouble> cloneItemDouble = new ArrayList<>();
            for(ObjectItemRecodeDouble item : objectItemRecodeDoubles){
                cloneItemDouble.add(item.cloneObjectItemRecodeDouble());
            }
            cloneThis.setObjectItemRecodeDoubles(cloneItemDouble);
        }
        if(objectItemRecodeStrings!=null){
            ArrayList<ObjectItemRecodeString> cloneItemString = new ArrayList<>();
            for(ObjectItemRecodeString item : objectItemRecodeStrings){
                cloneItemString.add(item.cloneObjectItemRecodeString());
            }
            cloneThis.setObjectItemRecodeStrings(cloneItemString);
        }
        cloneThis.setType(type);
        return cloneThis;
    }
    public ObjectCategoryRecode cloneObjectCategoryRecodeWithOutChild(){
        ObjectCategoryRecode cloneThis = new ObjectCategoryRecode();
        cloneThis.setJenisRecode(jenisRecode);
        cloneThis.setJenisLabel(jenisLabel);
        cloneThis.setNameColumn(nameColumn);
        cloneThis.setDataType(dataType);
        cloneThis.setNameColumnHasil(nameColumnHasil);
        cloneThis.setLabelValue(labelValue);
        cloneThis.setLabelPercentage(labelPercentage);
        cloneThis.setEnumCategoryTable(enumCategoryTable);
//        ArrayList<ObjectCategory> cloneChild = new ArrayList<>();
//        for(ObjectCategory item : objectCategorys){
//            if(item instanceof ObjectCategorySimple){
//                cloneChild.add(((ObjectCategorySimple) item).cloneObjectCategorySimple());
//            }else if(item instanceof ObjectCategoryRecode){
//                cloneChild.add(((ObjectCategoryRecode) item).cloneObjectCategoryRecode());
//            }
//        }
//        cloneThis.setObjectCategorys(cloneChild);
        if(objectItemRecodeDoubles!=null){
            ArrayList<ObjectItemRecodeDouble> cloneItemDouble = new ArrayList<>();
            for(ObjectItemRecodeDouble item : objectItemRecodeDoubles){
                cloneItemDouble.add(item.cloneObjectItemRecodeDouble());
            }
            cloneThis.setObjectItemRecodeDoubles(cloneItemDouble);
        }
        if(objectItemRecodeStrings!=null){
            ArrayList<ObjectItemRecodeString> cloneItemString = new ArrayList<>();
            for(ObjectItemRecodeString item : objectItemRecodeStrings){
                cloneItemString.add(item.cloneObjectItemRecodeString());
            }
            cloneThis.setObjectItemRecodeStrings(cloneItemString);
        }
        cloneThis.setType(type);
        return cloneThis;
    }

    /**
     * @return the type
     */
    public EnumTypeRecode getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(EnumTypeRecode type) {
        this.type = type;
    }

    /**
     * @return the objectItemRecodeDoubles
     */
    public ArrayList<ObjectItemRecodeDouble> getObjectItemRecodeDoubles() {
        return objectItemRecodeDoubles;
    }

    /**
     * @param objectItemRecodeDoubles the objectItemRecodeDoubles to set
     */
    public void setObjectItemRecodeDoubles(ArrayList<ObjectItemRecodeDouble> objectItemRecodeDoubles) {
        this.objectItemRecodeDoubles = objectItemRecodeDoubles;
    }

    /**
     * @return the objectItemRecodeStrings
     */
    public ArrayList<ObjectItemRecodeString> getObjectItemRecodeStrings() {
        return objectItemRecodeStrings;
    }

    /**
     * @param objectItemRecodeStrings the objectItemRecodeStrings to set
     */
    public void setObjectItemRecodeStrings(ArrayList<ObjectItemRecodeString> objectItemRecodeStrings) {
        this.objectItemRecodeStrings = objectItemRecodeStrings;
    }

    public void addItemRecode(Object itemRecode) {
        if (itemRecode instanceof ObjectItemRecodeDouble) {
            this.objectItemRecodeDoubles.add((ObjectItemRecodeDouble) itemRecode);
        } else if (itemRecode instanceof ObjectItemRecodeString) {
            this.objectItemRecodeStrings.add((ObjectItemRecodeString) itemRecode);
        }
    }

    public void removeItemRecode(Object itemRecode) {
        if (itemRecode instanceof ObjectItemRecodeDouble) {
            this.objectItemRecodeDoubles.remove((ObjectItemRecodeDouble) itemRecode);
        } else if (itemRecode instanceof ObjectItemRecodeString) {
            this.objectItemRecodeStrings.remove((ObjectItemRecodeString) itemRecode);
        }
    }
    
    public void addObjectCategory(ObjectCategory objectCategory){
        if(this.getObjectCategorys()==null){
            this.setObjectCategorys(new ArrayList<ObjectCategory>());
        }
        this.getObjectCategorys().add(objectCategory);
    }
    
    public void removeObjectCategory(ObjectCategory objectCategory){
         if(this.getObjectCategorys()==null){
            this.setObjectCategorys(new ArrayList<ObjectCategory>());
        }
         this.getObjectCategorys().remove(objectCategory);
    }

    public void setJsonObject(JSONObject object) {
        try {
            this.setJenisLabel(object.getInt("jenisLabel"));
            this.setJenisRecode(object.getInt("jenisRecode"));
            this.setNameColumn(object.getString("nameColumn"));
            this.setDataType(EnumDataType.valueOf(object.getString("dataType")));
            this.setNameColumnHasil(object.getString("nameColumnHasil"));
            this.setLabelValue(object.getString("labelValue"));
            this.setLabelPercentage(object.getString("labelPercentage"));
            this.setEnumCategoryTable(EnumCategoryTable.valueOf(object.getString("enumCategoryTable")));
            this.type = EnumTypeRecode.valueOf(object.getString("type"));
            if (object.has("objectItemRecodeDoubles") && this.type == EnumTypeRecode.RECODE_DOUBLE) {
                System.out.println("masuk Double");
                JSONArray doubleRecodeArr = object.getJSONArray("objectItemRecodeDoubles");
                for (int i = 0; i < doubleRecodeArr.length(); i++) {
                    ObjectItemRecodeDouble objectItemRecodeDouble = new ObjectItemRecodeDouble();
                    objectItemRecodeDouble.setJsonObject(doubleRecodeArr.getJSONObject(i));
                    this.objectItemRecodeDoubles.add(objectItemRecodeDouble);
                }
                System.out.println(""+this.objectItemRecodeDoubles.size());
            } else if (object.has("objectItemRecodeStrings") && this.type == EnumTypeRecode.RECODE_STRING){
                System.out.println("masuk string");
                JSONArray doubleRecodeArr = object.getJSONArray("objectItemRecodeStrings");
                for (int i = 0; i < doubleRecodeArr.length(); i++) {
                    ObjectItemRecodeString objectItemRecodeString = new ObjectItemRecodeString();
                    objectItemRecodeString.setJsonObject(doubleRecodeArr.getJSONObject(i));
                    this.objectItemRecodeStrings.add(objectItemRecodeString);
                }
                System.out.println(""+this.objectItemRecodeStrings.size());
            }
            if(object.has("objectCategorys")){
                JSONArray arrayCategorys = object.getJSONArray("objectCategorys");
                for(int i=0; i<arrayCategorys.length(); i++){
                    JSONObject itemCategoryJSON = arrayCategorys.getJSONObject(i);
                    EnumCategoryTable itemCategoryType = getEnumCategoryTable().valueOf(itemCategoryJSON.getString("enumCategoryTable"));
                    if(itemCategoryType == EnumCategoryTable.SIMPLE){
                        ObjectCategorySimple itemCategoryClass = new ObjectCategorySimple();
                        itemCategoryClass.setJsonObject(itemCategoryJSON);
                        this.addObjectCategory(itemCategoryClass);
                    }else if(itemCategoryType == EnumCategoryTable.RECODE){
                        ObjectCategoryRecode itemCategoryClass = new ObjectCategoryRecode();
                        itemCategoryClass.setJsonObject(itemCategoryJSON);
                        this.addObjectCategory(itemCategoryClass);
                    }
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
            object.put("jenisLabel", jenisLabel);
            object.put("jenisRecode", jenisRecode);
            object.put("nameColumn", getNameColumn());
            object.put("dataType", getDataType().name());
            object.put("nameColumnHasil", getNameColumnHasil());
            object.put("labelValue", getLabelValue());
            object.put("labelPercentage", getLabelPercentage());
            object.put("enumCategoryTable", getEnumCategoryTable().name());
            object.put("type", type.name());
            if (this.type == EnumTypeRecode.RECODE_DOUBLE) {
                JSONArray doubleRecodeArr = new JSONArray();
                for (ObjectItemRecodeDouble itemRecode : objectItemRecodeDoubles) {
                    doubleRecodeArr.put(itemRecode.getJSONObject());
                }
                object.put("objectItemRecodeDoubles", doubleRecodeArr);
            } else if (this.type == EnumTypeRecode.RECODE_STRING) {
                JSONArray doubleRecodeArr = new JSONArray();
                for (ObjectItemRecodeString itemRecode : objectItemRecodeStrings) {
                    doubleRecodeArr.put(itemRecode.getJSONObject());
                }
                object.put("objectItemRecodeStrings", doubleRecodeArr);
            } else {
                return null;
            }
            if(getObjectCategorys() != null && getObjectCategorys().size()!=0){
                JSONArray objectCategoryArray = new JSONArray();
                for(ObjectCategory itemCategory: getObjectCategorys()){
                    if(itemCategory instanceof ObjectCategorySimple){
                        objectCategoryArray.put(((ObjectCategorySimple)itemCategory).getJSONObject());
                    }else if (itemCategory instanceof ObjectCategoryRecode){
                        objectCategoryArray.put(((ObjectCategoryRecode)itemCategory).getJSONObject());
                    }
                }
                object.put("objectCategorys", objectCategoryArray);
            }
            return object;
        } catch (Exception ex) {
            System.out.println("JSON 2 " + ex);
            ex.printStackTrace();
            return null;
        }
    }
    
    public String getCaseRecode(boolean isRow){
        String recodeString = "CASE ";
        if(type == EnumTypeRecode.RECODE_DOUBLE){
            for(ObjectItemRecodeDouble item: objectItemRecodeDoubles){
                if(item.getType() == EnumTypeRecodeDouble.RECODE_FIRST){
                    recodeString = recodeString + " WHEN "+getNameColumn()+" <= "+item.getBatasAtas()+" THEN '"+item.getHasil()+"' ";
                }else if(item.getType() == EnumTypeRecodeDouble.RECODE_MIDDLE){
                    recodeString = recodeString + " WHEN "+getNameColumn() +" > "+item.getBatasBawah()+" AND "+getNameColumn()+" <= "+item.getBatasAtas()+" THEN '"+item.getHasil()+"' ";
                }else if(item.getType() == EnumTypeRecodeDouble.RECODE_LAST){
                    recodeString = recodeString + " WHEN "+getNameColumn()+" > "+item.getBatasBawah()+" THEN '"+item.getHasil()+"' ";
                }
            }
            recodeString = recodeString + " END AS "+getNameColumnHasil();
        }else if(type == EnumTypeRecode.RECODE_STRING){
             for(ObjectItemRecodeString item: objectItemRecodeStrings){
                if(item.getType() == EnumTypeRecodeString.NORMAL){
                    String orClouse = "";
                    for(String itemDari: item.getDaris()){
                        orClouse = orClouse + getNameColumn()+" = '"+itemDari+"' OR ";
                    }
                    orClouse = orClouse.substring(0,orClouse.length()-3 )+" THEN '"+item.getHasil()+"' ";
                    recodeString = recodeString + " WHEN "+orClouse +" ";
                }else if(item.getType() == EnumTypeRecodeString.OTHER){
                    recodeString = recodeString +" ELSE '"+ item.getHasil()+"' ";
                }
            }
            if(isRow){
                recodeString = recodeString + " END AS "+getNameColumnHasil();
            }else{
                recodeString = recodeString + " END AS "+getNameColumnHasil();
            }
        }
        return recodeString;
    }
    
    public String getSelect(boolean isRow){
        return " "+getCaseRecode(isRow)+" ";
    }
    public String getGroupBy(boolean isRow){
        return getNameColumnHasil()+" ";
    }
}

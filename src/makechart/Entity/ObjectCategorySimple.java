/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class ObjectCategorySimple extends ObjectCategory {
    
    private ArrayList<String> pilihanOption;

    public ObjectCategorySimple() {
    }

    public ObjectCategorySimple(int jenisRecode, int jenisLabel, String nameColumn, EnumDataType dataType, String nameColumnHasil,
            String labelValue, String labelPercentage) {
        this.jenisRecode = jenisRecode;
        this.jenisLabel = jenisLabel;
        this.nameColumn = nameColumn;
        this.dataType = dataType;
        this.nameColumnHasil = nameColumnHasil.replaceAll("-", "");
        this.labelValue = labelValue;
        this.labelPercentage = labelPercentage;
        this.enumCategoryTable = EnumCategoryTable.SIMPLE;
        this.objectCategorys = new ArrayList<ObjectCategory>();
    }
    
    public ObjectCategorySimple cloneObjectCategorySimple(){
        ObjectCategorySimple cloneThis = new ObjectCategorySimple();
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
        return cloneThis;
    }
    
    public ObjectCategorySimple cloneObjectCategorySimpleWithOutChild(){
        ObjectCategorySimple cloneThis = new ObjectCategorySimple();
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
        return cloneThis;
    }

    public void addObjectCategory(ObjectCategory objectCategory) {
        if (this.getObjectCategorys() == null) {
            this.setObjectCategorys(new ArrayList<ObjectCategory>());
        }
        this.getObjectCategorys().add(objectCategory);
    }

    public void removeObjectCategory(ObjectCategory objectCategory) {
        if (this.getObjectCategorys() == null) {
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
            this.setEnumCategoryTable(getEnumCategoryTable().valueOf(object.getString("enumCategoryTable")));
            if (object.has("objectCategorys")) {
                JSONArray arrayCategorys = object.getJSONArray("objectCategorys");
                for (int i = 0; i < arrayCategorys.length(); i++) {
                    JSONObject itemCategoryJSON = arrayCategorys.getJSONObject(i);
                    EnumCategoryTable itemCategoryType = EnumCategoryTable.valueOf(itemCategoryJSON.getString("enumCategoryTable"));
                    if (itemCategoryType == EnumCategoryTable.SIMPLE) {
                        ObjectCategorySimple itemCategoryClass = new ObjectCategorySimple();
                        itemCategoryClass.setJsonObject(itemCategoryJSON);
                        this.addObjectCategory(itemCategoryClass);
                    } else if (itemCategoryType == EnumCategoryTable.RECODE) {
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
            object.put("jenisRecode", jenisRecode);
            object.put("jenisLabel", jenisLabel);
            object.put("nameColumn", getNameColumn());
            object.put("dataType", getDataType().name());
            object.put("nameColumnHasil", getNameColumnHasil());
            object.put("labelValue", getLabelValue());
            object.put("labelPercentage", getLabelPercentage());
            object.put("enumCategoryTable", getEnumCategoryTable().name());
            if (getObjectCategorys() != null && getObjectCategorys().size() != 0) {
                JSONArray objectCategoryArray = new JSONArray();
                for (ObjectCategory itemCategory : getObjectCategorys()) {
                    if (itemCategory instanceof ObjectCategorySimple) {
                        objectCategoryArray.put(((ObjectCategorySimple) itemCategory).getJSONObject());
                    } else if (itemCategory instanceof ObjectCategoryRecode) {
                        objectCategoryArray.put(((ObjectCategoryRecode) itemCategory).getJSONObject());
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

    public String getSelect(boolean isRow) {
        return " " + getNameColumn() + " AS " + getNameColumnHasil() + " ";
    }

    public String getGroupBy(boolean isRow) {
        return getNameColumnHasil() + " ";
    }
    
    public ArrayList<String> getItemCategorysSimple(Connection conn){
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT DISTINCT "+nameColumn+" FROM ISIDATA");
            ArrayList<String> hasil = new ArrayList<>();
            while (resultSet.next()) {
                hasil.add(resultSet.getString(nameColumn));
            }
            setPilihanOption(hasil);
            conn.close();
            conn = null;
            return hasil;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @param pilihanOption the pilihanOption to set
     */
    public void setPilihanOption(ArrayList<String> pilihanOption) {
        this.pilihanOption = pilihanOption;
    }

    /**
     * @return the pilihanOption
     */
    public ArrayList<String> getPilihanOption() {
        return pilihanOption;
    }

}

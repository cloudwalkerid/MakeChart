/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class ObjectGeoJson {

    private String uid;
    private String name;
    private String desc;
    private List<String> property;
    private String pathGeoJson;
    private JSONObject geoJson;

    public ObjectGeoJson() {
        property = new ArrayList<>();
    }

    public ObjectGeoJson(String uid, String name, String desc) {
        this.uid = uid;
        this.name = name;
        this.desc = desc;
    }

    public ObjectGeoJson(String uid, String name, String desc, List<String> property, String pathGeoJson) {
        this.uid = uid;
        this.name = name;
        this.desc = desc;
        this.property = property;
        this.pathGeoJson = pathGeoJson;
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
     * @return the property
     */
    public List<String> getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(List<String> property) {
        this.property = property;
    }

    /**
     * @return the geoJson
     */
    public JSONObject getGeoJson() {
        return geoJson;
    }

    /**
     * @param geoJson the geoJson to set
     */
    public void setGeoJson(JSONObject geoJson) {
        this.geoJson = geoJson;
    }

    /**
     * @return the pathGeoJson
     */
    public String getPathGeoJson() {
        return pathGeoJson;
    }

    /**
     * @param pathGeoJson the pathGeoJson to set
     */
    public void setPathGeoJson(String pathGeoJson) {
        this.pathGeoJson = pathGeoJson;
    }

    public void setGeoJSonFromFile() {
        try {
            File file = new File(pathGeoJson);
            String content = FileUtils.readFileToString(file, "utf-8");
            // Convert JSON string to JSONObject
            geoJson = new JSONObject(content);
        } catch (Exception ex) {
            System.out.println("JSON 3 " + ex);
        }
    }

    public void setJsonObject(JSONObject object) {
        try {
            this.uid = object.getString("uid");
            this.name = object.getString("name");
            this.setDesc(object.getString("desc"));
            this.pathGeoJson = object.getString("pathGeoJson");
            if (object.has("property")) {
                JSONArray propertyArray = object.getJSONArray("property");
                for (int i = 0; i < propertyArray.length(); i++) {
                    this.property.add(propertyArray.getString(i));
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
            object.put("desc", getDesc());
            object.put("pathGeoJson", pathGeoJson);
            JSONArray propertyArray = new JSONArray();
            for (String item : this.property) {
                propertyArray.put(item);
            }
            object.put("property", propertyArray);
            return object;
        } catch (Exception ex) {
            System.out.println("JSON 2 " + ex);
            ex.printStackTrace();
            return null;
        }
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

}

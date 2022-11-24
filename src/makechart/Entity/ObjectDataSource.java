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
public class ObjectDataSource {

    private String uid;
    private String name;
    private String desc;

    public ObjectDataSource() {
    }

    public ObjectDataSource(String uid, String name, String desc) {
        this.uid = uid;
        this.name = name;
        this.desc = desc;
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

    public void setJsonObject(JSONObject object) {
        try {
            this.uid = object.getString("uid");
            this.name = object.getString("name");
            this.desc = object.getString("desc");
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
            object.put("desc", desc);
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

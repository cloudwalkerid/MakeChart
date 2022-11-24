/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Database;

import java.io.File;
import makechart.Entity.ObjectGeoJson;
import makechart.Entity.ObjectGrafik;
import makechart.Entity.ObjectMap;
import makechart.Entity.ObjectTable;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class databaseHelper {
    
    private static final String FolderAppString = System.getenv("APPDATA") + "\\MakeChart";
    private static final String FolderGeoJSON = System.getenv("APPDATA") + "\\MakeChart\\GeoJsonDB";
    private static final String FolderDataSource = System.getenv("APPDATA") + "\\MakeChart\\DataSource";
    private static final String FolderMakeChart = System.getenv("APPDATA") + "\\MakeChart\\MakeChart";
    private static final String FolderMakeTable = System.getenv("APPDATA") + "\\MakeChart\\MakeTable";
    private static final String FolderMakeMap = System.getenv("APPDATA") + "\\MakeChart\\MakeMap";
    
    
    public static void completeObjectTable(ObjectTable objectTable){
        try{
            File fileTarget = new File(FolderMakeTable+"\\"+objectTable.getUid()+".json");
            String content = FileUtils.readFileToString(fileTarget, "utf-8");
            JSONObject jsonTarget = new JSONObject(content);
            objectTable.setJsonObject(jsonTarget);
        }catch(Exception ex){
            
        }
    }
    
    public static void completeObjectGrafik(ObjectGrafik objectGrafik){
        try{
            File fileTarget = new File(FolderMakeChart+"\\"+objectGrafik.getUid()+".json");
            String content = FileUtils.readFileToString(fileTarget, "utf-8");
            JSONObject jsonTarget = new JSONObject(content);
            objectGrafik.setJsonObject(jsonTarget);
        }catch(Exception ex){
            
        }
    }
    
    public static void completeObjectMap(ObjectMap objectmap){
        try{
            File fileTarget = new File(FolderMakeMap+"\\"+objectmap.getUid()+".json");
            String content = FileUtils.readFileToString(fileTarget, "utf-8");
            JSONObject jsonTarget = new JSONObject(content);
            objectmap.setJsonObject(jsonTarget);
        }catch(Exception ex){
            
        }
    }
    public static void completeObjectGeoJsonDb(ObjectGeoJson objectGeoJson){
        try{
            File fileTarget = new File(FolderGeoJSON+"\\"+objectGeoJson.getUid()+".json");
            String content = FileUtils.readFileToString(fileTarget, "utf-8");
            JSONObject jsonTarget = new JSONObject(content);
            objectGeoJson.setJsonObject(jsonTarget);
        }catch(Exception ex){
            
        }
    }
    
}

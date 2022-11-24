/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Database;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import makechart.Entity.EnumAdditional;
import makechart.Entity.EnumDataType;
import makechart.Entity.ObjectCategory;
import makechart.Entity.ObjectCategorySimple;
import makechart.Entity.ObjectDataSource;
import makechart.Entity.ObjectGeoJson;
import makechart.Entity.ObjectGrafik;
import makechart.Entity.ObjectMap;
import makechart.Entity.ObjectCategoryRecode;
import makechart.Entity.ObjectInformationColumnTable;
import makechart.Entity.ObjectLookUpHelper;
import makechart.Entity.ObjectProgress;
import makechart.Entity.ObjectRowFilter;
import makechart.Entity.ObjectTable;
import makechart.Entity.TableResult;
import makechart.Entity.TableResultHasil;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class database {

    public static final String TABLE_SOURCE = "table_source";
    public static final String TABLE_FILTER = "table_filter";
    public static final String TABLE_TABEL = "table_table";
    public static final String TABLE_GRAFIK = "table_grafik";
    public static final String TABLE_MAP = "table_map";
    public static final String TABLE_GEOJSON = "table_geojson";
    public static final String TABLE_RECODE = "table_recode";

    public static final String K_UID = "uid";
    public static final String K_NAME = "name";
    public static final String K_DESC = "desc";
    public static final String K_EDITABLE = "editable";

    private static final String url = "jdbc:sqlite:" + System.getenv("APPDATA") + "\\MakeChart\\makechart.db";

    private static final String FolderAppString = System.getenv("APPDATA") + "\\MakeChart";
    private static final String FolderGeoJSON = System.getenv("APPDATA") + "\\MakeChart\\GeoJsonDB";
    private static final String FolderDataSource = System.getenv("APPDATA") + "\\MakeChart\\DataSource";
    private static final String FolderMakeChart = System.getenv("APPDATA") + "\\MakeChart\\MakeChart";
    private static final String FolderMakeTable = System.getenv("APPDATA") + "\\MakeChart\\MakeTable";
    private static final String FolderMakeMap = System.getenv("APPDATA") + "\\MakeChart\\MakeMap";
    private static final String dbLocation = System.getenv("APPDATA") + "\\MakeChart\\makechart.db";

    private static final String geoJsonFolderPath = System.getenv("APPDATA") + "\\MakeChart\\GeoJson";

    private static final String CREATE_DATA_SOURCE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SOURCE + " ( "
            + K_UID + " text PRIMARY KEY, "
            + K_NAME + " text, "
            + K_DESC + " text "
            + " )";
    private static final String CREATE_TABLE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TABEL + " ( "
            + K_UID + " text PRIMARY KEY, "
            + K_NAME + " text, "
            + K_DESC + " text, "
            + K_EDITABLE + " integer "
            + " )";
    private static final String CREATE_GRAFIK_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GRAFIK + " ( "
            + K_UID + " text PRIMARY KEY, "
            + K_NAME + " text, "
            + K_DESC + " text, "
            + K_EDITABLE + " integer "
            + " )";
    private static final String CREATE_MAP_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MAP + " ( "
            + K_UID + " text PRIMARY KEY, "
            + K_NAME + " text, "
            + K_DESC + " text, "
            + K_EDITABLE + " integer "
            + " )";
    private static final String CREATE_GEO_JSON_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GEOJSON + " ( "
            + K_UID + " text PRIMARY KEY, "
            + K_NAME + " text, "
            + K_DESC + " text "
            + " )";

    private static final String INSERT_DATA_SOURCE_TABLE = "INSERT INTO " + TABLE_SOURCE + " ( "
            + K_UID + ", "
            + K_NAME + ", "
            + K_DESC
            + " ) VALUES (?,?,?)";

    private static final String INSERT_TABLE_TABLE = "INSERT INTO " + TABLE_TABEL + " ( "
            + K_UID + ", "
            + K_NAME + ", "
            + K_DESC + ", "
            + K_EDITABLE
            + " ) VALUES (?,?,?,?)";

    private static final String INSERT_GRAFIK_TABLE = "INSERT INTO " + TABLE_GRAFIK + " ( "
            + K_UID + ", "
            + K_NAME + ", "
            + K_DESC + ", "
            + K_EDITABLE
            + " ) VALUES (?,?,?,?)";

    private static final String INSERT_MAP_TABLE = "INSERT INTO " + TABLE_MAP + " ( "
            + K_UID + ", "
            + K_NAME + ", "
            + K_DESC + ", "
            + K_EDITABLE
            + " ) VALUES (?,?,?,?)";

    private static final String INSERT_GEO_JSON_TABLE = "INSERT INTO " + TABLE_GEOJSON + " ( "
            + K_UID + ", "
            + K_NAME + ", "
            + K_DESC
            + " ) VALUES (?,?,?)";

    private static final String UPDATE_DATA_SOURCE_TABLE = "UPDATE " + TABLE_SOURCE + " SET "
            + K_NAME + " = ? , "
            + K_DESC + " = ? "
            + " WHERE " + K_UID + " = ? ";

    private static final String UPDATE_TABLE_TABLE = "UPDATE " + TABLE_TABEL + " SET "
            + K_NAME + " = ? , "
            + K_DESC + " = ? "
            + " WHERE " + K_UID + " = ? AND " + K_EDITABLE + " = 1 ";

    private static final String UPDATE_GRAFIK_TABLE = "UPDATE " + TABLE_GRAFIK + " SET "
            + K_NAME + " = ? , "
            + K_DESC + " = ? "
            + " WHERE " + K_UID + " = ? AND " + K_EDITABLE + " = 1 ";

    private static final String UPDATE_MAP_TABLE = "UPDATE " + TABLE_MAP + " SET "
            + K_NAME + " = ? , "
            + K_DESC + " = ? "
            + " WHERE " + K_UID + " = ? AND " + K_EDITABLE + " = 1 ";

    private static final String UPDATE_GEO_JSON_TABLE = "UPDATE " + TABLE_GEOJSON + " SET "
            + K_NAME + " = ? , "
            + K_DESC + " = ? "
            + " WHERE " + K_UID + " = ? ";

    private ObjectProgress objectProgress;

    public boolean createNewTable() {
        // SQLite connection string

        Connection conn = getConnection(url);
        if (conn == null) {
            return false;
        }
        try {
            conn.setAutoCommit(false);

            Statement stmt1 = conn.createStatement();
            stmt1.execute(CREATE_DATA_SOURCE_TABLE);

            Statement stmt2 = conn.createStatement();
            stmt2.execute(CREATE_TABLE_TABLE);

            Statement stmt3 = conn.createStatement();
            stmt3.execute(CREATE_GRAFIK_TABLE);

            Statement stmt4 = conn.createStatement();
            stmt4.execute(CREATE_MAP_TABLE);

            Statement stmt5 = conn.createStatement();
            stmt5.execute(CREATE_GEO_JSON_TABLE);

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean createDatabase(String uid) {
        if (!(new File(FolderDataSource + "\\" + uid + ".db")).exists()) {

            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + FolderDataSource + "\\" + uid + ".db")) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    String dataFolder = System.getenv("APPDATA");
                    System.out.println(dataFolder);
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                }
                conn.close();
                return true;
            } catch (SQLException e) {
                System.out.println("errp");
                System.out.println(e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    public Connection getConnection(String url2) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public boolean insertDataSourceNew(String name, String desc, String dbfFile) {
        String uid = "source_" + UUID.randomUUID();
        DBFReader reader = null;

        if (!createDatabase(uid)) {
            return false;
        }
        Connection conn2 = getConnection("jdbc:sqlite:" + FolderDataSource + "\\" + uid + ".db");

        try {
            System.out.println("-1");
            conn2.setAutoCommit(false);
            System.out.println("0");

            reader = new DBFReader(new FileInputStream(dbfFile));
            int allRecord = reader.getRecordCount();

            //String sql = "INSERT INTO " + TABLE_SOURCE + "(" + K_UID + "," + K_NAME + ") VALUES(?,?)";
            String createTable = "CREATE TABLE ISIDATA (";
            System.out.println("1");
            int numberOfFields = reader.getFieldCount();

            String argsname = "";
            String args = "";
            int[] type = new int[numberOfFields];
            System.out.println("2");
            for (int i = 0; i < numberOfFields; i++) {
                DBFField field = reader.getField(i);
                createTable = createTable + " " + field.getName() + " ";
                argsname = argsname + " " + field.getName() + " ,";
                args = args + "?,";
                if (field.getType().name().toUpperCase().equals("NUMERIC")) {
                    createTable = createTable + "  integer ,";
                    type[i] = 0;
                } else if (field.getType().name().toUpperCase().equals("DOUBLE")) {
                    createTable = createTable + "  real ,";
                    type[i] = 1;
                } else if (field.getType().name().toUpperCase().equals("CHARACTER")) {
                    createTable = createTable + "  text ,";
                    type[i] = 2;
                } else {
                    createTable = createTable + "  text ,";
                    type[i] = 3;
                }
                System.out.println(field.getName() + "|" + field.getType());
            }
            System.out.println("3");
            createTable = createTable.substring(0, createTable.length() - 1) + ")";
            Statement stmt = conn2.createStatement();
            stmt.execute(createTable);

            argsname = argsname.substring(0, argsname.length() - 1);
            args = args.substring(0, args.length() - 1);

            Object[] rowObjects;
            String sqlInsertItem = "Insert into ISIDATA (" + argsname + ") values (" + args + ")";
            System.out.println("1. " + sqlInsertItem);
            int iTer = 0;
            while ((rowObjects = reader.nextRecord()) != null) {
                PreparedStatement psmtItem = conn2.prepareStatement(sqlInsertItem);
                for (int i = 0; i < rowObjects.length; i++) {
                    if (type[i] == 0) {
                        psmtItem.setBigDecimal(i + 1, (BigDecimal) rowObjects[i]);
                    } else if (type[i] == 1) {
                        psmtItem.setBigDecimal(i + 1, (BigDecimal) rowObjects[i]);
                    } else if (type[i] == 2) {
                        psmtItem.setString(i + 1, (String) rowObjects[i]);
                    } else if (type[i] == 3) {
                        psmtItem.setString(i + 1, (String) rowObjects[i]);
                    }
                }

                psmtItem.executeUpdate();

                //System.out.println("Progress => "+iTer+":"+allRecord);
                objectProgress.updateProgress(iTer++, allRecord);
            }
            conn2.commit();
            //return true;
        } catch (Exception ex) {
            System.out.println("1.err " + ex);
            try {
                conn2.rollback();
            } catch (Exception exx) {
                System.out.println("2.err " + exx);
            }
            return false;
        } finally {
            try {
                conn2.close();
                conn2 = null;
            } catch (Exception exx) {
                System.out.println("3.err " + exx);
            }
        }

        Connection conn = getConnection(url);
        try {
            conn.setAutoCommit(false);
            PreparedStatement psmt = conn.prepareStatement(INSERT_DATA_SOURCE_TABLE);
            psmt.setString(1, uid);
            psmt.setString(2, name);
            psmt.setString(3, desc);
            psmt.executeUpdate();
            conn.commit();
            return true;
        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (Exception exx) {
                System.out.println("0.2.err " + exx);
            }
            return false;
        } finally {
            try {
                conn.close();
                conn = null;
            } catch (Exception exx) {
                System.out.println("0.3.err " + exx);
            }
        }
    }

//    public boolean insertDataSourceCombine() {
//
//        Connection conn = getConnection();
//        if (conn == null) {
//            return false;
//        }
//        try {
//            conn.setAutoCommit(false);
//
//            conn.commit();
//            return true;
//        } catch (Exception ex) {
//            try {
//                conn.rollback();
//            } catch (Exception exx) {
//
//            }
//            return false;
//        } finally {
//            try {
//                conn.close();
//            } catch (Exception exx) {
//
//            }
//        }
//    }
    public boolean deleteDataSource(ObjectDataSource objectDataSource) {
        Connection conn = getConnection(url);
        if (conn == null) {
            return false;
        }
        try {
            conn.setAutoCommit(false);

            PreparedStatement pstmtDelete = conn.prepareStatement("DELETE FROM " + TABLE_SOURCE + " WHERE " + K_UID + " = ?");
            pstmtDelete.setString(1, objectDataSource.getUid());
            pstmtDelete.executeUpdate();

            conn.commit();

            (new File(FolderDataSource + "\\" + objectDataSource.getUid() + ".db")).delete();
            return true;
        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (Exception exx) {

            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (Exception exx) {

            }
        }
    }

    public boolean insertTable(ObjectTable objectTable) {
        Connection conn = getConnection(url);
        if (conn == null) {
            return false;
        }
        try {
            conn.setAutoCommit(false);
            PreparedStatement psmt = conn.prepareStatement(INSERT_TABLE_TABLE);
            psmt.setString(1, objectTable.getUid());
            psmt.setString(2, objectTable.getName());
            psmt.setString(3, objectTable.getDesc());
            if (objectTable.isEditable()) {
                psmt.setInt(4, 1);
            } else {
                psmt.setInt(4, 0);
            }
            psmt.executeUpdate();

            FileOutputStream outputStream = new FileOutputStream(FolderMakeTable + "\\" + objectTable.getUid() + ".json");
            byte[] strToBytes = objectTable.getJSONObject().toString().getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
            conn.commit();
            return true;
        } catch (Exception ex) {
            System.out.println("err 0");
            ex.printStackTrace();
            try {
                conn.rollback();
                (new File(FolderMakeTable + "\\" + objectTable.getUid() + ".json")).delete();
            } catch (Exception exx) {
                System.out.println("err 1");
                exx.printStackTrace();
            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (Exception exx) {
                System.out.println("err 2");
                exx.printStackTrace();
            }
        }
    }

    public boolean updateTable(ObjectTable objectTable) {
        System.out.println("update table");
        Connection conn = getConnection(url);
        if (conn == null) {
            return false;
        }
        try {
            conn.setAutoCommit(false);
            PreparedStatement psmt = conn.prepareStatement(UPDATE_TABLE_TABLE);
            psmt.setString(1, objectTable.getName());
            psmt.setString(2, objectTable.getDesc());
            psmt.setString(3, objectTable.getUid());
            psmt.executeUpdate();

            FileOutputStream outputStream = new FileOutputStream(FolderMakeTable + "\\" + objectTable.getUid() + "-update.json");
            byte[] strToBytes = objectTable.getJSONObject().toString().getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
            conn.commit();
            (new File(FolderMakeTable + "\\" + objectTable.getUid() + ".json")).delete();
            (new File(FolderMakeTable + "\\" + objectTable.getUid() + "-update.json")).renameTo(new File(FolderMakeTable + "\\" + objectTable.getUid() + ".json"));
            return true;
        } catch (Exception ex) {
            try {
                conn.rollback();
                (new File(FolderMakeTable + "\\" + objectTable.getUid() + "-update.json")).delete();
            } catch (Exception exx) {

            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (Exception exx) {

            }
        }
    }

    public boolean deleteTable(ObjectTable objectTable) {
        Connection conn = getConnection(url);
        if (conn == null) {
            return false;
        }
        try {
            conn.setAutoCommit(false);

            PreparedStatement pstmtDelete = conn.prepareStatement("DELETE FROM " + TABLE_TABEL + " WHERE " + K_UID + " = ?");
            pstmtDelete.setString(1, objectTable.getUid());
            pstmtDelete.executeUpdate();

            conn.commit();

            (new File(FolderMakeTable + "\\" + objectTable.getUid() + ".json")).delete();
            return true;
        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (Exception exx) {

            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (Exception exx) {

            }
        }
    }

    public boolean insertGrafik(ObjectGrafik objectGrafik) {
        Connection conn = getConnection(url);
        if (conn == null) {
            return false;
        }
        try {
            conn.setAutoCommit(false);
            PreparedStatement psmt = conn.prepareStatement(INSERT_GRAFIK_TABLE);
            psmt.setString(1, objectGrafik.getUid());
            psmt.setString(2, objectGrafik.getName());
            psmt.setString(3, objectGrafik.getDesc());
            if (objectGrafik.isEditable()) {
                psmt.setInt(4, 1);
            } else {
                psmt.setInt(4, 0);
            }
            psmt.executeUpdate();

            FileOutputStream outputStream = new FileOutputStream(FolderMakeChart + "\\" + objectGrafik.getUid() + ".json");
            byte[] strToBytes = objectGrafik.getJSONObject().toString().getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
            conn.commit();
            return true;
        } catch (Exception ex) {
            try {
                conn.rollback();
                (new File(FolderMakeChart + "\\" + objectGrafik.getUid() + ".json")).delete();
            } catch (Exception exx) {

            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (Exception exx) {

            }
        }
    }

    public boolean updateGrafik(ObjectGrafik objectGrafik) {
        Connection conn = getConnection(url);
        if (conn == null) {
            return false;
        }
        try {
            conn.setAutoCommit(false);
            PreparedStatement psmt = conn.prepareStatement(UPDATE_GRAFIK_TABLE);
            psmt.setString(1, objectGrafik.getName());
            psmt.setString(2, objectGrafik.getDesc());
            psmt.setString(3, objectGrafik.getUid());
            psmt.executeUpdate();

            FileOutputStream outputStream = new FileOutputStream(FolderMakeChart + "\\" + objectGrafik.getUid() + "-update.json");
            byte[] strToBytes = objectGrafik.getJSONObject().toString().getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
            conn.commit();
            (new File(FolderMakeChart + "\\" + objectGrafik.getUid() + ".json")).delete();
            (new File(FolderMakeChart + "\\" + objectGrafik.getUid() + "-update.json")).renameTo(new File(FolderMakeChart + "\\" + objectGrafik.getUid() + ".json"));
            return true;
        } catch (Exception ex) {
            try {
                conn.rollback();
                (new File(FolderMakeChart + "\\" + objectGrafik.getUid() + "-update.json")).delete();
            } catch (Exception exx) {

            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (Exception exx) {

            }
        }
    }

    public boolean deleteGrafik(ObjectGrafik objectGrafik) {
        Connection conn = getConnection(url);
        if (conn == null) {
            return false;
        }
        try {
            conn.setAutoCommit(false);

            PreparedStatement pstmtDelete = conn.prepareStatement("DELETE FROM " + TABLE_GRAFIK + " WHERE " + K_UID + " = ?");
            pstmtDelete.setString(1, objectGrafik.getUid());
            pstmtDelete.executeUpdate();

            conn.commit();

            (new File(FolderMakeChart + "\\" + objectGrafik.getUid() + ".json")).delete();
            return true;
        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (Exception exx) {

            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (Exception exx) {

            }
        }
    }

    public boolean insertMap(ObjectMap objectMap) {
        Connection conn = getConnection(url);
        if (conn == null) {
            return false;
        }
        try {
            conn.setAutoCommit(false);
            PreparedStatement psmt = conn.prepareStatement(INSERT_MAP_TABLE);
            psmt.setString(1, objectMap.getUid());
            psmt.setString(2, objectMap.getName());
            psmt.setString(3, objectMap.getDesc());
            if (objectMap.isEditable()) {
                psmt.setInt(4, 1);
            } else {
                psmt.setInt(4, 0);
            }
            psmt.executeUpdate();

            FileOutputStream outputStream = new FileOutputStream(FolderMakeMap + "\\" + objectMap.getUid() + ".json");
            byte[] strToBytes = objectMap.getJSONObject().toString().getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
            conn.commit();
            return true;
        } catch (Exception ex) {
            try {
                conn.rollback();
                (new File(FolderMakeMap + "\\" + objectMap.getUid() + ".json")).delete();
            } catch (Exception exx) {

            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (Exception exx) {

            }
        }
    }

    public boolean updateMap(ObjectMap objectMap) {
        Connection conn = getConnection(url);
        if (conn == null) {
            return false;
        }
        try {
            conn.setAutoCommit(false);
            PreparedStatement psmt = conn.prepareStatement(UPDATE_MAP_TABLE);
            psmt.setString(1, objectMap.getName());
            psmt.setString(2, objectMap.getDesc());
            psmt.setString(3, objectMap.getUid());
            psmt.executeUpdate();

            FileOutputStream outputStream = new FileOutputStream(FolderMakeTable + "\\" + objectMap.getUid() + "-update.json");
            byte[] strToBytes = objectMap.getJSONObject().toString().getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
            conn.commit();
            (new File(FolderMakeMap + "\\" + objectMap.getUid() + ".json")).delete();
            (new File(FolderMakeMap + "\\" + objectMap.getUid() + "-update.json")).renameTo(new File(FolderMakeMap + "\\" + objectMap.getUid() + ".json"));
            return true;
        } catch (Exception ex) {
            try {
                conn.rollback();
                (new File(FolderMakeMap + "\\" + objectMap.getUid() + "-update.json")).delete();
            } catch (Exception exx) {

            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (Exception exx) {

            }
        }
    }

    public boolean deleteMap(ObjectMap objectMap) {
        Connection conn = getConnection(url);
        if (conn == null) {
            return false;
        }
        try {
            conn.setAutoCommit(false);

            PreparedStatement pstmtDelete = conn.prepareStatement("DELETE FROM " + TABLE_MAP + " WHERE " + K_UID + " = ?");
            pstmtDelete.setString(1, objectMap.getUid());
            pstmtDelete.executeUpdate();

            conn.commit();

            (new File(FolderMakeMap + "\\" + objectMap.getUid() + ".json")).delete();
            return true;
        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (Exception exx) {

            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (Exception exx) {

            }
        }
    }

    public boolean insertGeoJson(ObjectGeoJson objectGeoJson) {
        Connection conn = getConnection(url);
        if (conn == null) {
            return false;
        }
        try {
            conn.setAutoCommit(false);
            PreparedStatement psmt = conn.prepareStatement(INSERT_GEO_JSON_TABLE);
            psmt.setString(1, objectGeoJson.getUid());
            psmt.setString(2, objectGeoJson.getName());
            psmt.setString(3, objectGeoJson.getDesc());

            psmt.executeUpdate();

            FileOutputStream outputStream = new FileOutputStream(FolderGeoJSON + "\\" + objectGeoJson.getUid() + ".json");
            byte[] strToBytes = objectGeoJson.getJSONObject().toString().getBytes();
            outputStream.write(strToBytes);
            outputStream.close();

            FileOutputStream outputStream2 = new FileOutputStream(geoJsonFolderPath + "\\" + objectGeoJson.getUid() + ".json");
            byte[] strToBytes2 = objectGeoJson.getGeoJson().toString().getBytes();
            outputStream2.write(strToBytes2);
            outputStream2.close();
            conn.commit();
            return true;
        } catch (Exception ex) {
            try {
                conn.rollback();
                (new File(FolderGeoJSON + "\\" + objectGeoJson.getUid() + ".json")).delete();
                (new File(geoJsonFolderPath + "\\" + objectGeoJson.getUid() + ".json")).delete();
            } catch (Exception exx) {

            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (Exception exx) {

            }
        }
    }

    public boolean deleteGeoJson(ObjectGeoJson objectGeoJson) {
        Connection conn = getConnection(url);
        if (conn == null) {
            return false;
        }
        try {
            conn.setAutoCommit(false);

            PreparedStatement pstmtDelete = conn.prepareStatement("DELETE FROM " + TABLE_GEOJSON + " WHERE " + K_UID + " = ?");
            pstmtDelete.setString(1, objectGeoJson.getUid());
            pstmtDelete.executeUpdate();

            conn.commit();

            (new File(FolderGeoJSON + "\\" + objectGeoJson.getUid() + ".json")).delete();
            (new File(geoJsonFolderPath + "\\" + objectGeoJson.getUid() + ".json")).delete();
            return true;
        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (Exception exx) {

            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (Exception exx) {

            }
        }
    }

    public ArrayList<ObjectDataSource> getObjectDataSources() {
        String sql = "SELECT * FROM " + TABLE_SOURCE;
        ArrayList<ObjectDataSource> hasil = new ArrayList<>();
        Connection conn = getConnection(url);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                hasil.add(new ObjectDataSource(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC)));
            }
            return hasil;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ObjectTable> getObjectTables() {
        String sql = "SELECT * FROM " + TABLE_TABEL;
        ArrayList<ObjectTable> hasil = new ArrayList<>();
        Connection conn = getConnection(url);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                if (rs.getInt(K_EDITABLE) == 0) {
                    hasil.add(new ObjectTable(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC), false));
                } else if (rs.getInt(K_EDITABLE) == 1) {
                    hasil.add(new ObjectTable(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC), true));
                }
            }
            conn.close();
            conn = null;
            return hasil;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ObjectGrafik> getObjectGrafiks() {
        String sql = "SELECT * FROM " + TABLE_GRAFIK;
        ArrayList<ObjectGrafik> hasil = new ArrayList<>();
        Connection conn = getConnection(url);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                if (rs.getInt(K_EDITABLE) == 0) {
                    hasil.add(new ObjectGrafik(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC), false));
                } else if (rs.getInt(K_EDITABLE) == 1) {
                    hasil.add(new ObjectGrafik(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC), true));
                }
            }
            return hasil;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ObjectMap> getObjectMaps() {
        String sql = "SELECT * FROM " + TABLE_MAP;
        ArrayList<ObjectMap> hasil = new ArrayList<>();
        Connection conn = getConnection(url);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                if (rs.getInt(K_EDITABLE) == 0) {
                    hasil.add(new ObjectMap(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC), false));
                } else if (rs.getInt(K_EDITABLE) == 1) {
                    hasil.add(new ObjectMap(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC), true));
                }
            }
            return hasil;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ObjectGeoJson> getObjectGeoJson() {
        String sql = "SELECT * FROM " + TABLE_GEOJSON;
        ArrayList<ObjectGeoJson> hasil = new ArrayList<>();
        Connection conn = getConnection(url);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                hasil.add(new ObjectGeoJson(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC)));
            }
            return hasil;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ObjectInformationColumnTable> getInformationFromDataSource(ObjectDataSource objectDataSource) {
        String sql = "PRAGMA table_info('ISIDATA')";
        ArrayList<ObjectInformationColumnTable> hasil = new ArrayList<>();
        Connection conn = getConnection("jdbc:sqlite:" + FolderDataSource + "\\" + objectDataSource.getUid() + ".db");
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                hasil.add(new ObjectInformationColumnTable(rs.getString("name"), EnumDataType.valueOf(rs.getString("type").toUpperCase())));
            }
            conn.close();
            conn = null;
            return hasil;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ArrayList<String> getColumnsInTable(ObjectDataSource objectDataSource) {
        String sql = "PRAGMA table_info('ISIDATA')";
        ArrayList<String> hasil = new ArrayList<>();
        Connection conn = getConnection("jdbc:sqlite:" + FolderDataSource + "\\" + objectDataSource.getUid() + ".db");
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                hasil.add(rs.getString("name"));
            }
            conn.close();
            conn = null;
            return hasil;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ArrayList<ObjectLookUpHelper> getLookHelpers(ObjectDataSource objectDataSource, ArrayList<ObjectInformationColumnTable> column) {
        String sql = "SELECT * FROM ISIDATA Limit 10";
        ArrayList<ObjectLookUpHelper> hasil = new ArrayList<>();
        Connection conn = getConnection("jdbc:sqlite:" + FolderDataSource + "\\" + objectDataSource.getUid() + ".db");
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                ObjectLookUpHelper itemHelper = new ObjectLookUpHelper();
                for(ObjectInformationColumnTable itemInfo: column){
                    itemHelper.addLookUp(rs.getString(itemInfo.getColumnName()));
                }
                hasil.add(itemHelper);
            }
            conn.close();
            conn = null;
            return hasil;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

//    public JSONObject processTable(ObjectTable objectTable) {
//        Connection conn = getConnection("jdbc:sqlite:" + FolderDataSource + "\\" + objectTable.getUid() + ".db");
//        String query = objectTable.getQueryTable();
//        try {
//            
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//
//            JSONArray jsonArray = new JSONArray();
//            JSONArray title = new JSONArray();
//            for (int i = 0; i < objectColums.size(); i++) {
//                title.put(objectColums.get(i).getLabelValue());
//            }
//            while (resultSet.next()) {
//                int total_rows = resultSet.getMetaData().getColumnCount();
//                for (int i = 0; i < total_rows; i++) {
//                    if (i < objectColums.size()) {
//
//                    } else {
//                        int iNew = i - objectColums.size();
//                    }
//                    JSONObject obj = new JSONObject();
//                    obj.put(resultSet.getMetaData().getColumnLabel(i + 1),
//                             resultSet.getObject(i + 1));
//                    jsonArray.put(obj);
//                }
//            }
//            return hasil;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//
//    }
    public TableResultHasil processTable(ObjectTable objectTable) {
        ArrayList<TableResult> result = objectTable.getTableResults();
        ArrayList<JSONArray> resultJSON = new ArrayList<>();
        for (TableResult itemResult : result) {
            try {
                Connection conn = getConnection("jdbc:sqlite:" + FolderDataSource + "\\" + objectTable.getUid() + ".db");
                itemResult.doQuery(conn);
                if (itemResult.getHasil() == null) {
                    return null;
                }
                resultJSON.add(itemResult.getHasil());
                conn.close();
                conn = null;
            } catch (Exception ex) {
                System.out.println("Except Process Table " + ex);
            }
        }
        TableResultHasil tableResulHasil = new TableResultHasil(objectTable, result, resultJSON);
        return tableResulHasil;
    }

//    SELECT OrderID, Quantity,
//CASE
//    WHEN Quantity > 30 THEN "The quantity is greater than 30"
//    WHEN Quantity = 30 THEN "The quantity is 30"
//    ELSE "The quantity is something else"
//END,
//CASE
//    WHEN Quantity > 30 THEN "The quantity is greater than 30"
//    WHEN Quantity = 30 THEN "The quantity is 30"
//    ELSE "The quantity is something else"
//END
//FROM OrderDetails;
//
//        
    /**
     * @return the objectProgress
     */
    public ObjectProgress getObjectProgress() {
        return objectProgress;
    }

    /**
     * @param objectProgress the objectProgress to set
     */
    public void setObjectProgress(ObjectProgress objectProgress) {
        this.objectProgress = objectProgress;
    }

    public ObjectDataSource getObjectDataSources(String uid) {
        String sql = "SELECT * FROM " + TABLE_SOURCE + " WHERE " + K_UID + " = '" + uid + "'";
        ArrayList<ObjectDataSource> hasil = new ArrayList<>();
        Connection conn = getConnection(url);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                hasil.add(new ObjectDataSource(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC)));
            }
            return hasil.get(0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ObjectTable getObjectTables(String uid) {
        String sql = "SELECT * FROM " + TABLE_TABEL + " WHERE " + K_UID + " = '" + uid + "'";
        ArrayList<ObjectTable> hasil = new ArrayList<>();
        Connection conn = getConnection(url);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                if (rs.getInt(K_EDITABLE) == 0) {
                    hasil.add(new ObjectTable(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC), false));
                } else if (rs.getInt(K_EDITABLE) == 1) {
                    hasil.add(new ObjectTable(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC), true));
                }
            }
            ObjectTable result = hasil.get(0);
            databaseHelper.completeObjectTable(result);
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ObjectGrafik getObjectGrafiks(String uid) {
        String sql = "SELECT * FROM " + TABLE_GRAFIK + " WHERE " + K_UID + " = '" + uid + "'";
        ArrayList<ObjectGrafik> hasil = new ArrayList<>();
        Connection conn = getConnection(url);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                if (rs.getInt(K_EDITABLE) == 0) {
                    hasil.add(new ObjectGrafik(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC), false));
                } else if (rs.getInt(K_EDITABLE) == 1) {
                    hasil.add(new ObjectGrafik(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC), true));
                }
            }
            ObjectGrafik result = hasil.get(0);
            databaseHelper.completeObjectGrafik(result);
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ObjectMap getOneObjectMaps(String uid) {
        String sql = "SELECT * FROM " + TABLE_MAP + " WHERE " + K_UID + " = '" + uid + "'";
        ArrayList<ObjectMap> hasil = new ArrayList<>();
        Connection conn = getConnection(url);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                if (rs.getInt(K_EDITABLE) == 0) {
                    hasil.add(new ObjectMap(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC), false));
                } else if (rs.getInt(K_EDITABLE) == 1) {
                    hasil.add(new ObjectMap(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC), true));
                }
            }
            ObjectMap result = hasil.get(0);
            databaseHelper.completeObjectMap(result);
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ObjectGeoJson getOneObjectGeoJson(String uid) {
        String sql = "SELECT * FROM " + TABLE_GEOJSON;
        ArrayList<ObjectGeoJson> hasil = new ArrayList<>();
        Connection conn = getConnection(url);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                hasil.add(new ObjectGeoJson(rs.getString(K_UID), rs.getString(K_NAME), rs.getString(K_DESC)));
            }
            ObjectGeoJson result = hasil.get(0);
            databaseHelper.completeObjectGeoJsonDb(result);
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

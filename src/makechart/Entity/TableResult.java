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
import makechart.Database.database;
import makechart.Process.MakeTableHelper;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class TableResult {

    private int kordinatRow;
    private int kordinatColumn;
    private String query;
    private String select;
    private String where;
    private String groupby;
    private String orderby;
    private JSONArray hasil;

    private ArrayList<ObjectCategory> rowCategory;
    private ArrayList<ObjectCategory> columnCategory;

    private ArrayList<JSONObject> rowTotal;
    private ArrayList<JSONObject> columnTotal;
    private ArrayList<JSONObject> rowTotalPercentage;
    private ArrayList<JSONObject> columnTotalPercentage;
    private JSONObject hasilTran;
    private JSONArray hasilPercentage;
    private ObjectTable objectTable;

    private static final String totalIdRow = "t_ed12d54518799999999";
    private static final String totalIdColumn = "t_ed12d5sd79955669999";

    private JSONObject total;

    public TableResult() {
        rowCategory = new ArrayList<>();
        columnCategory = new ArrayList<>();
        rowTotal = new ArrayList<>();
        columnTotal = new ArrayList<>();
        rowTotalPercentage = new ArrayList<>();
        columnTotalPercentage = new ArrayList<>();
        total = new JSONObject();
    }

    public TableResult(int kordinatRow, int kordinatColumn, String query, String select, String where, String groupby, String orderby, JSONArray hasil) {
        this.kordinatRow = kordinatRow;
        this.kordinatColumn = kordinatColumn;
        this.query = query;
        this.select = select;
        this.where = where;
        this.groupby = groupby;
        this.orderby = orderby;
        this.hasil = hasil;
        rowCategory = new ArrayList<>();
        columnCategory = new ArrayList<>();
        rowTotal = new ArrayList<>();
        columnTotal = new ArrayList<>();
        rowTotalPercentage = new ArrayList<>();
        columnTotalPercentage = new ArrayList<>();
        total = new JSONObject();
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return the select
     */
    public String getSelect() {
        return select;
    }

    /**
     * @param select the select to set
     */
    public void setSelect(String select) {
        this.select = select;
    }

    /**
     * @return the groupby
     */
    public String getGroupby() {
        return groupby;
    }

    /**
     * @param groupby the groupby to set
     */
    public void setGroupby(String groupby) {
        System.out.println("group " + groupby);
        this.groupby = groupby;
    }

    /**
     * @return the orderby
     */
    public String getOrderby() {
        return orderby;
    }

    /**
     * @param orderby the orderby to set
     */
    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    /**
     * @return the hasil
     */
    public JSONArray getHasil() {
        return hasil;
    }

    /**
     * @param hasil the hasil to set
     */
    public void setHasil(JSONArray hasil) {
        this.hasil = hasil;
    }

    /**
     * @return the where
     */
    public String getWhere() {
        return where;
    }

    /**
     * @param where the where to set
     */
    public void setWhere(String where) {
        this.where = where;
    }

    public void doQuery(Connection conn) {

        if (select.endsWith(" , ")) {
            select = select.substring(0, select.length() - 3);
        }
        if (groupby.endsWith(" , ") && groupby.startsWith(" GROUP BY ")) {
            groupby = groupby.substring(0, groupby.length() - 3);
        } else if (!groupby.endsWith(" , ") && groupby.startsWith(" GROUP BY ")) {
            groupby = groupby;
        } else if (groupby.endsWith(" , ") && !groupby.startsWith(" GROUP BY ")) {
            groupby = " GROUP BY " + groupby.substring(0, groupby.length() - 3);
        } else if (!groupby.endsWith(" , ") && !groupby.startsWith(" GROUP BY ")) {
            groupby = " GROUP BY " + groupby;
        }
        query = "SELECT " + select + " FROM ISIDATA " + where + " " + groupby + " " + orderby;
        System.out.println("Query Tabnle " + query);
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            JSONArray jsonArray = new JSONArray();
            //Map<String, Double> totalROwPercolum = new HashMap<String, Double>();
            while (resultSet.next()) {
                int total_column = resultSet.getMetaData().getColumnCount();
                JSONObject obj = new JSONObject();
                //double totalColum = 0;
                for (int i = 0; i < total_column; i++) {
                    obj.put(resultSet.getMetaData().getColumnLabel(i + 1),
                            resultSet.getObject(i + 1));
//                    if (!totalROwPercolum.containsKey(resultSet.getMetaData().getColumnLabel(i + 1))) {
//                        totalROwPercolum.put(resultSet.getMetaData().getColumnLabel(i + 1), Double.valueOf("0"));
//                    }
//                    if (resultSet.getObject(i + 1) instanceof Integer) {
//                        int stringInt = resultSet.getInt(i + 1);
//                        totalColum = totalColum + stringInt;
//                        totalROwPercolum.put(resultSet.getMetaData().getColumnLabel(i + 1),
//                                 totalROwPercolum.get(resultSet.getMetaData().getColumnLabel(i + 1)) + stringInt);
//                    } else if (resultSet.getObject(i + 1) instanceof Double) {
//                        double stringDouble = resultSet.getDouble(i + 1);
//                        totalColum = totalColum + stringDouble;
//                        totalROwPercolum.put(resultSet.getMetaData().getColumnLabel(i + 1),
//                                 totalROwPercolum.get(resultSet.getMetaData().getColumnLabel(i + 1)) + stringDouble);
//                    } else if (resultSet.getObject(i + 1) instanceof String) {
//                        try {
//                            int stringInt = resultSet.getInt(i + 1);
//                            totalColum = totalColum + stringInt;
//                            totalROwPercolum.put(resultSet.getMetaData().getColumnLabel(i + 1),
//                                     totalROwPercolum.get(resultSet.getMetaData().getColumnLabel(i + 1)) + stringInt);
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                            try {
//                                double stringDouble = resultSet.getDouble(i + 1);
//                                totalColum = totalColum + stringDouble;
//                                totalROwPercolum.put(resultSet.getMetaData().getColumnLabel(i + 1),
//                                         totalROwPercolum.get(resultSet.getMetaData().getColumnLabel(i + 1)) + stringDouble);
//                            } catch (Exception exx) {
//                                exx.printStackTrace();
//                            }
//                        }
//                    }
                }
                //obj.put(totalIdColumn, totalColum);
                jsonArray.put(obj);
            }
//            JSONObject obj = new JSONObject();
//            totalROwPercolum.forEach((t, u) -> {
//                try{
//                    obj.put(t, u);
//                }catch(Exception ex){
//                    ex.printStackTrace();
//                }
//            });
//            jsonArray.put(obj);-
            System.out.println("Row : ");
            for (ObjectCategory item : rowCategory) {
                System.out.println(item.nameColumn);
            }
            System.out.println("");
            System.out.println("Column : ");
            for (ObjectCategory item : columnCategory) {
                System.out.println(item.nameColumn);
            }
            hasil = jsonArray;
            conn.close();
            conn = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @return the rowCategory
     */
    public ArrayList<ObjectCategory> getRowCategory() {
        return rowCategory;
    }

    /**
     * @param rowCategory the rowCategory to set
     */
    public void setRowCategory(ArrayList<ObjectCategory> rowCategory) {
        this.rowCategory = rowCategory;
    }

    /**
     * @return the columnCategory
     */
    public ArrayList<ObjectCategory> getColumnCategory() {
        return columnCategory;
    }

    /**
     * @param columnCategory the columnCategory to set
     */
    public void setColumnCategory(ArrayList<ObjectCategory> columnCategory) {
        this.columnCategory = columnCategory;
    }

    public void cloneCategory(TableResult cloneTable) {
        for (ObjectCategory item : rowCategory) {
            if (item.cloneObjectCategory() != null) {
                cloneTable.getRowCategory().add(item.cloneObjectCategory());
            }
        }
        for (ObjectCategory item : columnCategory) {
            if (item.cloneObjectCategory() != null) {
                cloneTable.getColumnCategory().add(item.cloneObjectCategory());
            }
        }
    }

    /**
     * @return the objectTable
     */
    public ObjectTable getObjectTable() {
        return objectTable;
    }

    /**
     * @param objectTable the objectTable to set
     */
    public void setObjectTable(ObjectTable objectTable) {
        this.objectTable = objectTable;
    }

    public void processValue() {
        for (int i = 0; i < rowCategory.size(); i++) {
            ObjectCategory item = rowCategory.get(i);
            JSONObject var = new JSONObject();
            if (item instanceof ObjectCategorySimple) {
                database dBase = new database();
                Connection conn = dBase.getConnection("jdbc:sqlite:" + System.getenv("APPDATA")
                        + "\\MakeChart\\DataSource" + "\\" + objectTable.getSourceData() + ".db");
                ArrayList<String> pilihan = ((ObjectCategorySimple) item).getItemCategorysSimple(conn);
                for (String itemString : pilihan) {

                }

            } else if (item instanceof ObjectCategoryRecode) {
                if (item.getJenisRecode() == 1) {
                    for (ObjectItemRecodeDouble itemDouble : ((ObjectCategoryRecode) item).getObjectItemRecodeDoubles()) {

                    }
                } else if (item.getJenisRecode() == 2) {
                    for (ObjectItemRecodeString itemString : ((ObjectCategoryRecode) item).getObjectItemRecodeStrings()) {

                    }
                }
            }
            JSONObject jSONObject = new JSONObject();
        }
        for (int i = 0; i < rowCategory.size(); i++) {

        }
    }

    public void processCategoryTotal(boolean isRow, int index) {
        ObjectCategory item = null;
        int size = 0;
        if (isRow) {
            item = rowCategory.get(index);
            size = rowTotal.size();
        } else {
            item = columnCategory.get(index);

            size = columnTotal.size();
        }
        if (index == 0) {
            if (isRow) {
                rowTotal = new ArrayList<>();
            } else {
                columnTotal = new ArrayList<>();
            }
            size = 0;
        }
        if (item instanceof ObjectCategorySimple) {
            database dBase = new database();
            Connection conn = dBase.getConnection("jdbc:sqlite:" + System.getenv("APPDATA")
                    + "\\MakeChart\\DataSource" + "\\" + objectTable.getSourceData() + ".db");
            ArrayList<String> pilihan = ((ObjectCategorySimple) item).getItemCategorysSimple(conn);
            try {
                if (size == 0) {
                    for (String itemString : pilihan) {
                        JSONObject itemStringJSOn = new JSONObject();
                        itemStringJSOn.put(item.getNameColumnHasil(), itemString);
                        if (isRow) {
                            rowTotal.add(itemStringJSOn);
                        } else {
                            columnTotal.add(itemStringJSOn);
                        }
                    }
                } else {
                    if (isRow) {
                        ArrayList<JSONObject> rowTotalTemp = new ArrayList<>();
                        for (int jjj = 0; jjj < rowTotal.size(); jjj++) {
                            JSONObject itemJSONTotal = rowTotal.get(jjj);
                            for (String itemString : pilihan) {
                                JSONObject baru = new JSONObject();
                                baru.put(item.getNameColumnHasil(), itemString);
                                for (int ij = 0; ij < itemJSONTotal.names().length(); ij++) {
                                    baru.put(itemJSONTotal.names().getString(ij),
                                            itemJSONTotal.getString(itemJSONTotal.names().getString(ij)));
                                }
                                rowTotalTemp.add(baru);
                            }
                        }
                        rowTotal.addAll(rowTotalTemp);
                    } else {
                        ArrayList<JSONObject> columnTotalTemp = new ArrayList<>();
                        for (int jjj = 0; jjj < columnTotal.size(); jjj++) {
                            JSONObject itemJSONTotal = columnTotal.get(jjj);
                            for (String itemString : pilihan) {
                                JSONObject baru = new JSONObject();
                                baru.put(item.getNameColumnHasil(), itemString);
                                for (int ij = 0; ij < itemJSONTotal.names().length(); ij++) {
                                    baru.put(itemJSONTotal.names().getString(ij),
                                            itemJSONTotal.getString(itemJSONTotal.names().getString(ij)));
                                }
                                columnTotalTemp.add(baru);
                            }
                        }
                        columnTotal.addAll(columnTotalTemp);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (item instanceof ObjectCategoryRecode) {
            try {
                if (size == 0) {
                    if (item.getJenisRecode() == 1) {
                        for (ObjectItemRecodeDouble itemDouble : ((ObjectCategoryRecode) item).getObjectItemRecodeDoubles()) {
                            JSONObject itemStringJSOn = new JSONObject();
                            itemStringJSOn.put(item.getNameColumnHasil(), itemDouble.getHasil());
                            if (isRow) {
                                rowTotal.add(itemStringJSOn);
                            } else {
                                columnTotal.add(itemStringJSOn);
                            }
                        }
                    } else if (item.getJenisRecode() == 2) {
                        for (ObjectItemRecodeString itemString : ((ObjectCategoryRecode) item).getObjectItemRecodeStrings()) {
                            JSONObject itemStringJSOn = new JSONObject();
                            itemStringJSOn.put(item.getNameColumnHasil(), itemString.getHasil());
                            if (isRow) {
                                rowTotal.add(itemStringJSOn);
                            } else {
                                columnTotal.add(itemStringJSOn);
                            }
                        }
                    }
                } else {
                    if (isRow) {
                        ArrayList<JSONObject> rowTotalTemp = new ArrayList<>();
                        for (int jjj = 0; jjj < rowTotal.size(); jjj++) {
                            JSONObject itemJSONTotal = rowTotal.get(jjj);
                            if (item.getJenisRecode() == 1) {
                                for (ObjectItemRecodeDouble itemDouble : ((ObjectCategoryRecode) item).getObjectItemRecodeDoubles()) {
                                    JSONObject baru = new JSONObject();
                                    baru.put(item.getNameColumnHasil(), itemDouble.getHasil());
                                    for (int ij = 0; ij < itemJSONTotal.names().length(); ij++) {
                                        baru.put(itemJSONTotal.names().getString(ij),
                                                itemJSONTotal.getString(itemJSONTotal.names().getString(ij)));
                                    }
                                    rowTotalTemp.add(baru);
                                }
                            } else if (item.getJenisRecode() == 2) {
                                for (ObjectItemRecodeString itemString : ((ObjectCategoryRecode) item).getObjectItemRecodeStrings()) {
                                    JSONObject baru = new JSONObject();
                                    baru.put(item.getNameColumnHasil(), itemString.getHasil());
                                    for (int ij = 0; ij < itemJSONTotal.names().length(); ij++) {
                                        baru.put(itemJSONTotal.names().getString(ij),
                                                itemJSONTotal.getString(itemJSONTotal.names().getString(ij)));
                                    }
                                    rowTotalTemp.add(baru);
                                }
                            }
                        }
                        rowTotal.addAll(rowTotalTemp);
                    } else {
                        ArrayList<JSONObject> columnTotalTemp = new ArrayList<>();
                        for (int jjj = 0; jjj < columnTotal.size(); jjj++) {
                            JSONObject itemJSONTotal = columnTotal.get(jjj);
                            if (item.getJenisRecode() == 1) {
                                for (ObjectItemRecodeDouble itemDouble : ((ObjectCategoryRecode) item).getObjectItemRecodeDoubles()) {
                                    JSONObject baru = new JSONObject();
                                    baru.put(item.getNameColumnHasil(), itemDouble.getHasil());
                                    for (int ij = 0; ij < itemJSONTotal.names().length(); ij++) {
                                        baru.put(itemJSONTotal.names().getString(ij),
                                                itemJSONTotal.getString(itemJSONTotal.names().getString(ij)));
                                    }
                                    columnTotalTemp.add(baru);
                                }
                            } else if (item.getJenisRecode() == 2) {
                                for (ObjectItemRecodeString itemString : ((ObjectCategoryRecode) item).getObjectItemRecodeStrings()) {
                                    JSONObject baru = new JSONObject();
                                    baru.put(item.getNameColumnHasil(), itemString.getHasil());
                                    for (int ij = 0; ij < itemJSONTotal.names().length(); ij++) {
                                        baru.put(itemJSONTotal.names().getString(ij),
                                                itemJSONTotal.getString(itemJSONTotal.names().getString(ij)));
                                    }
                                    columnTotalTemp.add(baru);
                                }
                            }
                        }
                        columnTotal.addAll(columnTotalTemp);
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void filterTheUnnecity() {
        JSONArray hasilFilter = new JSONArray();
        for (int i = 0; i < hasil.length(); i++) {
            try {
                JSONObject itemHasil = hasil.getJSONObject(i);
                boolean continu = false;
                for (ObjectCategory item : rowCategory) {
                    if (!itemHasil.has(item.getNameColumnHasil())) {
                        continu = true;
                        break;
                    }
                }
                if (continu) {
                    continue;
                }
                for (ObjectCategory item : columnCategory) {
                    if (!itemHasil.has(item.getNameColumnHasil())) {
                        continu = true;
                        break;
                    }
                }
                if (continu) {
                    continue;
                }
                hasilFilter.put(hasil.getJSONObject(i));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        hasil = hasilFilter;
    }

    public void processTotal() {
        try {
            for (int i = 0; i < hasil.length(); i++) {
                JSONObject itemHasil = hasil.getJSONObject(i);
                if (objectTable.getValues().size() == 0) {
                    if (total.has(ObjectTable.DEfAULT_NAME_HASIL)) {
                        total.put(ObjectTable.DEfAULT_NAME_HASIL,
                                total.getDouble(ObjectTable.DEfAULT_NAME_HASIL)
                                + hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                    } else {
                        total.put(ObjectTable.DEfAULT_NAME_HASIL,
                                hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                    }
                } else {
                    for (ObjectValueTable itemValue : objectTable.getValues()) {
                        if (total.has(itemValue.getNameColumnHasil())) {
                            total.put(itemValue.getNameColumnHasil(),
                                    total.getDouble(itemValue.getNameColumnHasil())
                                    + hasil.getJSONObject(i).getDouble(itemValue.getNameColumnHasil()));
                        } else {
                            total.put(itemValue.getNameColumnHasil(),
                                    hasil.getJSONObject(i).getDouble(itemValue.getNameColumnHasil()));
                        }
                    }
                }
                for (int j = 0; j < rowTotal.size(); j++) {
                    if (objectTable.getValues().size() == 0) {
                        boolean masuk = true;
                        for (int ij = 0; ij < rowTotal.get(j).names().length(); ij++) {
                            String name = rowTotal.get(j).names().getString(ij);
                            if (!name.startsWith("v_")) {
                                masuk = rowTotal.get(j).getString(name).equals(itemHasil.getString(name));
                                if (!masuk) {
                                    break;
                                }
                            }
                        }
                        if (masuk) {
                            if (rowTotal.get(j).has(ObjectTable.DEfAULT_NAME_HASIL)) {
                                rowTotal.get(j).put(ObjectTable.DEfAULT_NAME_HASIL,
                                        rowTotal.get(j).getDouble(ObjectTable.DEfAULT_NAME_HASIL)
                                        + itemHasil.getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            } else {
                                rowTotal.get(j).put(ObjectTable.DEfAULT_NAME_HASIL,
                                        itemHasil.getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        }
                    } else {
                        for (ObjectValueTable itemValue : objectTable.getValues()) {
                            boolean masuk = true;
                            for (int ij = 0; ij < rowTotal.get(j).names().length(); ij++) {
                                String name = rowTotal.get(j).names().getString(ij);
                                if (!name.startsWith("v_")) {
                                    masuk = rowTotal.get(j).getString(name).equals(itemHasil.getString(name));
                                    if (!masuk) {
                                        break;
                                    }
                                }
                            }
                            if (masuk) {
                                if (rowTotal.get(j).has(itemValue.getNameColumnHasil())) {
                                    rowTotal.get(j).put(itemValue.getNameColumnHasil(),
                                            rowTotal.get(j).getDouble(itemValue.getNameColumnHasil())
                                            + itemHasil.getDouble(itemValue.getNameColumnHasil()));
                                } else {
                                    rowTotal.get(j).put(itemValue.getNameColumnHasil(),
                                            itemHasil.getDouble(itemValue.getNameColumnHasil()));
                                }
                            }
                        }
                    }
                }
                for (int j = 0; j < columnTotal.size(); j++) {
                    if (objectTable.getValues().size() == 0) {
                        boolean masuk = true;
                        for (int ij = 0; ij < columnTotal.get(j).names().length(); ij++) {
                            String name = columnTotal.get(j).names().getString(ij);
                            if (!name.startsWith("v_")) {
                                masuk = columnTotal.get(j).getString(name).equals(itemHasil.getString(name));
                                if (!masuk) {
                                    break;
                                }
                            }
                        }
                        if (masuk) {
                            if (columnTotal.get(j).has(ObjectTable.DEfAULT_NAME_HASIL)) {
                                columnTotal.get(j).put(ObjectTable.DEfAULT_NAME_HASIL,
                                        columnTotal.get(j).getDouble(ObjectTable.DEfAULT_NAME_HASIL)
                                        + itemHasil.getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            } else {
                                columnTotal.get(j).put(ObjectTable.DEfAULT_NAME_HASIL,
                                        itemHasil.getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        }
                    } else {
                        for (ObjectValueTable itemValue : objectTable.getValues()) {
                            boolean masuk = true;
                            for (int ij = 0; ij < columnTotal.get(j).names().length(); ij++) {
                                String name = columnTotal.get(j).names().getString(ij);
                                if (!name.startsWith("v_")) {
                                    masuk = columnTotal.get(j).getString(name).equals(itemHasil.getString(name));
                                    if (!masuk) {
                                        break;
                                    }
                                }
                            }
                            if (masuk) {
                                if (columnTotal.get(j).has(itemValue.getNameColumnHasil())) {
                                    columnTotal.get(j).put(itemValue.getNameColumnHasil(),
                                            columnTotal.get(j).getDouble(itemValue.getNameColumnHasil())
                                            + itemHasil.getDouble(itemValue.getNameColumnHasil()));
                                } else {
                                    columnTotal.get(j).put(itemValue.getNameColumnHasil(),
                                            itemHasil.getDouble(itemValue.getNameColumnHasil()));
                                }
                            }
                        }
                    }
                }
            }
//            int i = 0;
//            System.out.println("Row Total");
//            for (JSONObject item : rowTotal) {
//                System.out.println(i + "." + item.toString());
//            }
//            i = 0;
//            System.out.println("Column Total");
//            for (JSONObject item : columnTotal) {
//                System.out.println(i + "." + item.toString());
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public double firstRowTotal(String values, String category, String valueCategory) {
        try {
            for (JSONObject item : rowTotal) {
                if (item.has(category)) {
                    JSONArray arr = item.names();
                    int banyak = 0;
                    for (int i = 0; i < arr.length(); i++) {
                        if (arr.getString(i).startsWith("r_")) {
                            banyak = banyak + 1;
                        }
                    }
                    if (banyak == 1) {
                        if (item.getString(category).equals(valueCategory)) {
                            //System.out.println(item.toString());
                            return item.getDouble(values);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
        return 0;
    }

    public double firstColumnTotal(String values, String category, String valueCategory) {
        try {
            for (JSONObject item : columnTotal) {
                if (item.has(category)) {
                    JSONArray arr = item.names();
                    int banyak = 0;
                    for (int i = 0; i < arr.length(); i++) {
                        if (arr.getString(i).startsWith("c_")) {
                            banyak = banyak + 1;
                        }
                    }
                    if (banyak == 1) {
                        if (item.getString(category).equals(valueCategory)) {
                            return item.getDouble(values);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
        return 0;
    }

    public void processPencentage() {
        hasilPercentage = new JSONArray();

        boolean havePercentage = false;
        boolean isRowPercentage = false;
        //System.out.println("1. . " + columnCategory.get(0).getNameColumnHasil() + " " + columnCategory.get(0).getJenisLabel());
        //System.out.println("1. . " + objectTable.getColumnCategory().get(0).getNameColumnHasil() + " " + objectTable.getColumnCategory().get(0).getJenisLabel());

        if (rowCategory.size() > 0) {
            if (rowCategory.get(0).getJenisLabel() != 0) {
                havePercentage = true;
                isRowPercentage = true;
            }
        }
        if (columnCategory.size() > 0) {
            if (columnCategory.get(0).getJenisLabel() != 0) {
                havePercentage = true;
                isRowPercentage = false;

            }
        }
        //System.out.println("MAsuk "+havePercentage);
        //System.out.println("Masuk "+isRowPercentage);
        if (havePercentage) {
            if (isRowPercentage) {
                if (columnCategory.size() > 0) {
                    System.out.println("111");
                    try {
                        for (int i = 0; i < hasil.length(); i++) {
                            JSONObject itemHasil = new JSONObject();
                            for (int ij = 0; ij < hasil.getJSONObject(i).names().length(); ij++) {
                                if (hasil.getJSONObject(i).names().getString(ij).startsWith("v_")) {
                                    double valuHasil = (double) hasil.getJSONObject(i).getDouble(hasil.getJSONObject(i).names().getString(ij))
                                            / firstColumnTotal(hasil.getJSONObject(i).names().getString(ij), columnCategory.get(0).getNameColumnHasil(),
                                                    hasil.getJSONObject(i).getString(columnCategory.get(0).getNameColumnHasil()));
                                    itemHasil.put(hasil.getJSONObject(i).names().getString(ij),
                                            valuHasil * 100);
                                } else {
                                    itemHasil.put(hasil.getJSONObject(i).names().getString(ij),
                                            hasil.getJSONObject(i).get(hasil.getJSONObject(i).names().getString(ij)));
                                }
                            }
                            hasilPercentage.put(itemHasil);
                        }
                        for (int i = 0; i < rowTotal.size(); i++) {
                            JSONObject itemHasil = new JSONObject();
                            for (int ij = 0; ij < rowTotal.get(i).names().length(); ij++) {
                                if (rowTotal.get(i).names().getString(ij).startsWith("v_")) {
                                    double valuHasil = (double) rowTotal.get(i).getDouble(rowTotal.get(i).names().getString(ij))
                                            / total.getDouble(rowTotal.get(i).names().getString(ij));
//                                    System.out.println(rowTotal.get(i).getString(rowCategory.get(0).getNameColumnHasil())+":"+firstRowTotal(rowTotal.get(i).names().getString(ij), rowCategory.get(0).getNameColumnHasil(),
//                                                    rowTotal.get(i).getString(rowCategory.get(0).getNameColumnHasil())));
                                    itemHasil.put(rowTotal.get(i).names().getString(ij),
                                            valuHasil * 100);
                                } else {
                                    itemHasil.put(rowTotal.get(i).names().getString(ij),
                                            rowTotal.get(i).get(rowTotal.get(i).names().getString(ij)));
                                }
                            }
                            rowTotalPercentage.add(itemHasil);
                        }
                        for (int i = 0; i < columnTotal.size(); i++) {
                            JSONObject itemHasil = new JSONObject();
                            for (int ij = 0; ij < columnTotal.get(i).names().length(); ij++) {
                                if (columnTotal.get(i).names().getString(ij).startsWith("v_")) {
                                    double valuHasil = (double) columnTotal.get(i).getDouble(columnTotal.get(i).names().getString(ij))
                                            / firstColumnTotal(columnTotal.get(i).names().getString(ij), columnCategory.get(0).getNameColumnHasil(),
                                                    columnTotal.get(i).getString(columnCategory.get(0).getNameColumnHasil()));
                                    itemHasil.put(columnTotal.get(i).names().getString(ij),
                                            valuHasil * 100);
                                } else {
                                    itemHasil.put(columnTotal.get(i).names().getString(ij),
                                            columnTotal.get(i).get(columnTotal.get(i).names().getString(ij)));
                                }
                            }
                            columnTotalPercentage.add(itemHasil);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    //System.out.println("112");
                    try {
                        for (int i = 0; i < hasil.length(); i++) {
                            JSONObject itemHasil = new JSONObject();
                            for (int ij = 0; ij < hasil.getJSONObject(i).names().length(); ij++) {
                                if (hasil.getJSONObject(i).names().getString(ij).startsWith("v_")) {
                                    double valuHasil = (double) hasil.getJSONObject(i).getDouble(hasil.getJSONObject(i).names().getString(ij))
                                            / total.getDouble(hasil.getJSONObject(i).names().getString(ij));
                                    itemHasil.put(hasil.getJSONObject(i).names().getString(ij),
                                            valuHasil * 100);
                                } else {
                                    itemHasil.put(hasil.getJSONObject(i).names().getString(ij),
                                            hasil.getJSONObject(i).get(hasil.getJSONObject(i).names().getString(ij)));
                                }
                            }
                            hasilPercentage.put(itemHasil);
                        }
                        for (int i = 0; i < columnTotal.size(); i++) {
                            JSONObject itemHasil = new JSONObject();
                            for (int ij = 0; ij < columnTotal.get(i).names().length(); ij++) {
                                if (columnTotal.get(i).names().getString(ij).startsWith("v_")) {
                                    double valuHasil = (double) columnTotal.get(i).getDouble(columnTotal.get(i).names().getString(ij))
                                            / total.getDouble(columnTotal.get(i).names().getString(ij));
                                    itemHasil.put(columnTotal.get(i).names().getString(ij),
                                            valuHasil * 100);
                                } else {
                                    itemHasil.put(columnTotal.get(i).names().getString(ij),
                                            columnTotal.get(i).get(hasil.getJSONObject(i).names().getString(ij)));
                                }
                            }
                            columnTotalPercentage.add(itemHasil);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                //System.out.println("113");
                if (rowCategory.size() > 0) {
                    try {
                        for (int i = 0; i < hasil.length(); i++) {
                            JSONObject itemHasil = new JSONObject();
                            for (int ij = 0; ij < hasil.getJSONObject(i).names().length(); ij++) {
                                if (hasil.getJSONObject(i).names().getString(ij).startsWith("v_")) {
                                    double valuHasil = (double) hasil.getJSONObject(i).getDouble(hasil.getJSONObject(i).names().getString(ij))
                                            / firstRowTotal(hasil.getJSONObject(i).names().getString(ij), rowCategory.get(0).getNameColumnHasil(),
                                                    hasil.getJSONObject(i).getString(rowCategory.get(0).getNameColumnHasil()));
                                    itemHasil.put(hasil.getJSONObject(i).names().getString(ij),
                                            valuHasil * 100);
                                } else {
                                    itemHasil.put(hasil.getJSONObject(i).names().getString(ij),
                                            hasil.getJSONObject(i).get(hasil.getJSONObject(i).names().getString(ij)));
                                }
                            }
                            hasilPercentage.put(itemHasil);
                        }
                        for (int i = 0; i < rowTotal.size(); i++) {
                            JSONObject itemHasil = new JSONObject();
                            for (int ij = 0; ij < rowTotal.get(i).names().length(); ij++) {
                                if (rowTotal.get(i).names().getString(ij).startsWith("v_")) {
                                    double valuHasil = (double) rowTotal.get(i).getDouble(rowTotal.get(i).names().getString(ij))
                                            / firstRowTotal(rowTotal.get(i).names().getString(ij), rowCategory.get(0).getNameColumnHasil(),
                                                    rowTotal.get(i).getString(rowCategory.get(0).getNameColumnHasil()));
                                    itemHasil.put(rowTotal.get(i).names().getString(ij),
                                            valuHasil * 100);
                                } else {
                                    itemHasil.put(rowTotal.get(i).names().getString(ij),
                                            rowTotal.get(i).get(rowTotal.get(i).names().getString(ij)));
                                }
                            }
                            rowTotalPercentage.add(itemHasil);
                        }
                        for (int i = 0; i < columnTotal.size(); i++) {
                            JSONObject itemHasil = new JSONObject();
                            for (int ij = 0; ij < columnTotal.get(i).names().length(); ij++) {
                                if (columnTotal.get(i).names().getString(ij).startsWith("v_")) {
                                    System.out.println("2. " + columnTotal.get(i).toString());
                                    double valuHasil = (double) columnTotal.get(i).getDouble(columnTotal.get(i).names().getString(ij))
                                            / total.getDouble(columnTotal.get(i).names().getString(ij));
                                    itemHasil.put(columnTotal.get(i).names().getString(ij),
                                            valuHasil * 100);
                                } else {
                                    itemHasil.put(columnTotal.get(i).names().getString(ij),
                                            columnTotal.get(i).get(columnTotal.get(i).names().getString(ij)));
                                }
                            }
                            columnTotalPercentage.add(itemHasil);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        for (int i = 0; i < hasil.length(); i++) {
                            JSONObject itemHasil = new JSONObject();
                            for (int ij = 0; ij < hasil.getJSONObject(i).names().length(); ij++) {
                                if (hasil.getJSONObject(i).names().getString(ij).startsWith("v_")) {
                                    double valuHasil = (double) hasil.getJSONObject(i).getDouble(hasil.getJSONObject(i).names().getString(ij))
                                            / total.getDouble(hasil.getJSONObject(i).names().getString(ij));
                                    itemHasil.put(hasil.getJSONObject(i).names().getString(ij),
                                            valuHasil * 100);
                                } else {
                                    itemHasil.put(hasil.getJSONObject(i).names().getString(ij),
                                            hasil.getJSONObject(i).get(hasil.getJSONObject(i).names().getString(ij)));
                                }
                            }
                            hasilPercentage.put(itemHasil);
                        }
                        for (int i = 0; i < columnTotal.size(); i++) {
                            JSONObject itemHasil = new JSONObject();
                            for (int ij = 0; ij < columnTotal.get(i).names().length(); ij++) {
                                if (columnTotal.get(i).names().getString(ij).startsWith("v_")) {
                                    double valuHasil = (double) columnTotal.get(i).getDouble(columnTotal.get(i).names().getString(ij))
                                            / total.getDouble(columnTotal.get(i).names().getString(ij));
                                    itemHasil.put(columnTotal.get(i).names().getString(ij),
                                            valuHasil * 100);
                                } else {
                                    itemHasil.put(columnTotal.get(i).names().getString(ij),
                                            columnTotal.get(i).get(hasil.getJSONObject(i).names().getString(ij)));
                                }
                            }
                            columnTotalPercentage.add(itemHasil);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public int getRowLength() {
        if (rowCategory.size() == 0) {
            if (rowCategory.get(0).getJenisLabel() != 2) {
                if (objectTable.getValues().size() == 0) {
                    return 1;
                } else {
                    return objectTable.getValues().size();
                }
            } else {
                if (objectTable.getValues().size() == 0) {
                    return 2;
                } else {
                    return 2 * objectTable.getValues().size();
                }
            }
        } else {
            int hasil = 1;
            int hasilTotal = 1;
            int pengali = 1;
            for (ObjectCategory itemCategory : rowCategory) {
                if (itemCategory.getJenisRecode() == 0) {
                    hasil = hasil * ((ObjectCategorySimple) itemCategory).getObjectCategorys().size();
                } else if (itemCategory.getJenisRecode() == 1) {
                    hasil = hasil * ((ObjectCategoryRecode) itemCategory).getObjectItemRecodeDoubles().size();
                } else if (itemCategory.getJenisRecode() == 2) {
                    hasil = hasil * ((ObjectCategoryRecode) itemCategory).getObjectItemRecodeStrings().size();
                }
                if (rowCategory.get(rowCategory.size() - 1) != itemCategory) {
                    if (itemCategory.getJenisRecode() == 0) {
                        pengali = pengali * ((ObjectCategorySimple) itemCategory).getObjectCategorys().size();
                        hasilTotal = hasilTotal + pengali;
                    } else if (itemCategory.getJenisRecode() == 1) {
                        pengali = pengali * ((ObjectCategoryRecode) itemCategory).getObjectItemRecodeDoubles().size();
                        hasilTotal = hasilTotal + pengali;
                    } else if (itemCategory.getJenisRecode() == 2) {
                        pengali = pengali * ((ObjectCategoryRecode) itemCategory).getObjectItemRecodeDoubles().size();
                        hasilTotal = hasilTotal + pengali;
                    }
                }
            }

            hasil = hasil + hasilTotal;
            if (rowCategory.get(0).getJenisLabel() != 2) {
                if (objectTable.getValues().size() == 0) {
                    return hasil;
                } else {
                    return hasil * objectTable.getValues().size();
                }
            } else {
                if (objectTable.getValues().size() == 0) {
                    return hasil * 2;
                } else {
                    return hasil * 2 * objectTable.getValues().size();
                }
            }
        }
    }

    public int getColumnLength() {
        if (columnCategory.size() == 0) {
            if (columnCategory.get(0).getJenisLabel() != 2) {
                if (objectTable.getValues().size() == 0) {
                    return 1;
                } else {
                    return objectTable.getValues().size();
                }
            } else {
                if (objectTable.getValues().size() == 0) {
                    return 2;
                } else {
                    return 2 * objectTable.getValues().size();
                }
            }
        } else {
            int hasil = 1;
            int hasilTotal = 1;
            int pengali = 1;
            for (ObjectCategory itemCategory : columnCategory) {
                if (itemCategory.getJenisRecode() == 0) {
                    hasil = hasil * ((ObjectCategorySimple) itemCategory).getObjectCategorys().size();
                } else if (itemCategory.getJenisRecode() == 1) {
                    hasil = hasil * ((ObjectCategoryRecode) itemCategory).getObjectItemRecodeDoubles().size();
                } else if (itemCategory.getJenisRecode() == 2) {
                    hasil = hasil * ((ObjectCategoryRecode) itemCategory).getObjectItemRecodeStrings().size();
                }
                if (columnCategory.get(columnCategory.size() - 1) != itemCategory) {
                    if (itemCategory.getJenisRecode() == 0) {
                        pengali = pengali * ((ObjectCategorySimple) itemCategory).getObjectCategorys().size();
                        hasilTotal = hasilTotal + pengali;
                    } else if (itemCategory.getJenisRecode() == 1) {
                        pengali = pengali * ((ObjectCategoryRecode) itemCategory).getObjectItemRecodeDoubles().size();
                        hasilTotal = hasilTotal + pengali;
                    } else if (itemCategory.getJenisRecode() == 2) {
                        pengali = pengali * ((ObjectCategoryRecode) itemCategory).getObjectItemRecodeStrings().size();
                        hasilTotal = hasilTotal + pengali;
                    }
                }
            }

            hasil = hasil + hasilTotal;
            if (columnCategory.get(0).getJenisLabel() != 2) {
                if (objectTable.getValues().size() == 0) {
                    return hasil;
                } else {
                    return hasil * objectTable.getValues().size();
                }
            } else {
                if (objectTable.getValues().size() == 0) {
                    return hasil * 2;
                } else {
                    return hasil * 2 * objectTable.getValues().size();
                }
            }
        }
    }

//    public int lengthCategory(boolean isRow, int index) {
//        if (isRow) {
//            if (rowCategory.get(index).getJenisRecode() == 0) {
//                return ((ObjectCategorySimple) rowCategory.get(index)).getPilihanOption().size();
//            } else if (rowCategory.get(index).getJenisRecode() == 1) {
//                return ((ObjectCategoryRecode) rowCategory.get(index)).getObjectItemRecodeDoubles().size();
//            } else if (rowCategory.get(index).getJenisRecode() == 2) {
//                return ((ObjectCategoryRecode) rowCategory.get(index)).getObjectItemRecodeStrings().size();
//            }
//        } else {
//            if (columnCategory.get(index).getJenisRecode() == 0) {
//                return ((ObjectCategorySimple) columnCategory.get(index)).getPilihanOption().size();
//            } else if (columnCategory.get(index).getJenisRecode() == 1) {
//                return ((ObjectCategoryRecode) columnCategory.get(index)).getObjectItemRecodeDoubles().size();
//            } else if (columnCategory.get(index).getJenisRecode() == 2) {
//                return ((ObjectCategoryRecode) columnCategory.get(index)).getObjectItemRecodeStrings().size();
//            }
//        }
//        return 0;
//    }
    public int weightCategory(boolean isRow, int index) {
        if (isRow) {
            int weightDef = 1;
            for (int i = index + 1; i < rowCategory.size(); i++) {
                weightDef = weightDef * rowCategory.get(i).getSizeItem();
            }
            return weightDef;
        } else {
            int weightDef = 1;
            for (int i = index + 1; i < columnCategory.size(); i++) {
                weightDef = weightDef * columnCategory.get(i).getSizeItem();
            }
            return weightDef;
        }
    }

    public int getIndexRow(JSONObject item) {
        try {
            int hasil = 0;
            for (int i = 0; i < rowCategory.size(); i++) {
                int pilihan = 0;
                if (rowCategory.get(i).getJenisRecode() == 0) {
                    for (int j = 0; j < ((ObjectCategorySimple) rowCategory.get(i)).getPilihanOption().size(); j++) {
                        if (((ObjectCategorySimple) rowCategory.get(i)).getPilihanOption().get(j)
                                .equals(item.getString(rowCategory.get(i).getNameColumnHasil()))) {
                            pilihan = j;
                        }
                    }
                } else if (rowCategory.get(i).getJenisRecode() == 1) {
                    for (int j = 0; j < ((ObjectCategoryRecode) rowCategory.get(i)).getObjectItemRecodeDoubles().size(); j++) {
                        if (((ObjectCategoryRecode) rowCategory.get(i)).getObjectItemRecodeDoubles().get(j).getHasil()
                                .equals(item.getString(rowCategory.get(i).getNameColumnHasil()))) {
                            pilihan = j;
                        }
                    }
                } else if (rowCategory.get(i).getJenisRecode() == 2) {
                    for (int j = 0; j < ((ObjectCategoryRecode) rowCategory.get(i)).getObjectItemRecodeStrings().size(); j++) {
                        if (((ObjectCategoryRecode) rowCategory.get(i)).getObjectItemRecodeStrings().get(j).getHasil()
                                .equals(item.getString(rowCategory.get(i).getNameColumnHasil()))) {
                            pilihan = j;
                        }
                    }
                }
                hasil = hasil + (pilihan * weightCategory(true, i));
            }
            return hasil;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
    }

    public int getIndexColumn(JSONObject item) {
        try {
            int hasil = 0;
            for (int i = 0; i < columnCategory.size(); i++) {
                int pilihan = 0;
                if (columnCategory.get(i).getJenisRecode() == 0) {
                    for (int j = 0; j < ((ObjectCategorySimple) columnCategory.get(i)).getPilihanOption().size(); j++) {
                        if (((ObjectCategorySimple) columnCategory.get(i)).getPilihanOption().get(j)
                                .equals(item.getString(columnCategory.get(i).getNameColumnHasil()))) {
                            pilihan = j;
                        }
                    }
                } else if (columnCategory.get(i).getJenisRecode() == 1) {
                    for (int j = 0; j < ((ObjectCategoryRecode) columnCategory.get(i)).getObjectItemRecodeDoubles().size(); j++) {
                        if (((ObjectCategoryRecode) columnCategory.get(i)).getObjectItemRecodeDoubles().get(j).getHasil()
                                .equals(item.getString(columnCategory.get(i).getNameColumnHasil()))) {
                            pilihan = j;
                        }
                    }
                } else if (columnCategory.get(i).getJenisRecode() == 2) {
                    for (int j = 0; j < ((ObjectCategoryRecode) columnCategory.get(i)).getObjectItemRecodeStrings().size(); j++) {
                        if (((ObjectCategoryRecode) columnCategory.get(i)).getObjectItemRecodeStrings().get(j).getHasil()
                                .equals(item.getString(columnCategory.get(i).getNameColumnHasil()))) {
                            pilihan = j;
                        }
                    }
                }
                hasil = hasil + (pilihan * weightCategory(false, i));
            }
//            System.out.println("Column index : "+item.toString()+"|"+hasil);
            return hasil;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
    }

    public void arrange() {
        hasilTran = new JSONObject();
        if (columnCategory.size() > 0 && rowCategory.size() == 0) {
            if (objectTable.getValues().size() == 0) {
                if (columnCategory.get(0).getJenisLabel() == 0) {
                    for (int i = 0; i < hasil.length(); i++) {
                        try {
                            int columnindex = getIndexColumn(hasil.getJSONObject(i));
                            if (!hasilTran.has("0")) {
                                JSONObject column = new JSONObject();
                                column.put("" + columnindex, hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                                hasilTran.put("0", column);
                            } else {
                                hasilTran.getJSONObject("0").put("" + columnindex, hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (columnCategory.get(0).getJenisLabel() == 1) {
                    for (int i = 0; i < hasilPercentage.length(); i++) {
                        try {
                            int columnindex = getIndexColumn(hasilPercentage.getJSONObject(i));
                            if (!hasilTran.has("0")) {
                                JSONObject column = new JSONObject();
                                column.put("" + columnindex, hasilPercentage.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                                hasilTran.put("0", column);
                            } else {
                                hasilTran.getJSONObject("0").put("" + columnindex, hasilPercentage.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (columnCategory.get(0).getJenisLabel() == 2) {
                    for (int i = 0; i < hasil.length(); i++) {
                        try {
                            int columnindex = (getIndexColumn(hasil.getJSONObject(i)) * 2);
                            if (!hasilTran.has("0")) {
                                JSONObject column = new JSONObject();
                                column.put("" + columnindex, hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                                hasilTran.put("0", column);
                            } else {
                                hasilTran.getJSONObject("0").put("" + columnindex, hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    for (int i = 0; i < hasilPercentage.length(); i++) {
                        try {
                            int columnindex = (getIndexColumn(hasil.getJSONObject(i)) * 2) + 1;
                            if (!hasilTran.has("0")) {
                                JSONObject column = new JSONObject();
                                column.put("" + columnindex, hasilPercentage.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                                hasilTran.put("0", column);
                            } else {
                                hasilTran.getJSONObject("0").put("" + columnindex, hasilPercentage.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                if (columnCategory.get(0).getJenisLabel() == 0) {
                    for (int i = 0; i < hasil.length(); i++) {
                        try {
                            int columnindex = getIndexColumn(hasil.getJSONObject(i)) * objectTable.getValues().size();
                            if (!hasilTran.has("0")) {
                                JSONObject column = new JSONObject();
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    int columnindexLocal = columnindex + valueIndex;
                                    column.put("" + columnindexLocal, hasil.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                                hasilTran.put("0", column);
                            } else {
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    int columnindexLocal = columnindex + valueIndex;
                                    hasilTran.getJSONObject("0").put("" + columnindexLocal, hasil.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (columnCategory.get(0).getJenisLabel() == 1) {
                    for (int i = 0; i < hasilPercentage.length(); i++) {
                        try {
                            int columnindex = getIndexColumn(hasilPercentage.getJSONObject(i)) * objectTable.getValues().size();
                            if (!hasilTran.has("0")) {
                                JSONObject column = new JSONObject();
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    int columnindexLocal = columnindex + valueIndex;
                                    column.put("" + columnindexLocal, hasilPercentage.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                                hasilTran.put("0", column);
                            } else {
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    int columnindexLocal = columnindex + valueIndex;
                                    hasilTran.getJSONObject("0").put("" + columnindexLocal, hasilPercentage.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (columnCategory.get(0).getJenisLabel() == 2) {
                    for (int i = 0; i < hasil.length(); i++) {
                        try {
                            int columnindex = getIndexColumn(hasil.getJSONObject(i)) * objectTable.getValues().size() * 2;
                            if (!hasilTran.has("0")) {
                                JSONObject column = new JSONObject();
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    int columnindexLocal = columnindex + (valueIndex * 2);
                                    column.put("" + columnindexLocal, hasil.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                                hasilTran.put("0", column);
                            } else {
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    int columnindexLocal = columnindex + (valueIndex * 2);
                                    hasilTran.getJSONObject("0").put("" + columnindexLocal, hasil.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    for (int i = 0; i < hasilPercentage.length(); i++) {
                        try {
                            int columnindex = getIndexColumn(hasilPercentage.getJSONObject(i)) * objectTable.getValues().size();
                            if (!hasilTran.has("0")) {
                                JSONObject column = new JSONObject();
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    int columnindexLocal = columnindex + (valueIndex * 2) + 1;
                                    column.put("" + columnindexLocal, hasilPercentage.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                                hasilTran.put("0", column);
                            } else {
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    int columnindexLocal = columnindex + +(valueIndex * 2) + 1;
                                    hasilTran.getJSONObject("0").put("" + columnindexLocal, hasilPercentage.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        } else if (rowCategory.size() > 0 && columnCategory.size() == 0) {
            if (objectTable.getValues().size() == 0) {
                if (rowCategory.get(0).getJenisLabel() == 0) {
                    for (int i = 0; i < hasil.length(); i++) {
                        try {
                            int rowIndex = getIndexRow(hasil.getJSONObject(i));
                            if (!hasilTran.has("" + rowIndex)) {
                                JSONObject column = new JSONObject();
                                column.put("0", hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                                hasilTran.put("" + rowIndex, column);
                            } else {
                                hasilTran.getJSONObject("" + rowIndex).put("0", hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (rowCategory.get(0).getJenisLabel() == 1) {
                    for (int i = 0; i < hasilPercentage.length(); i++) {
                        try {
                            int rowIndex = getIndexRow(hasilPercentage.getJSONObject(i));
                            if (!hasilTran.has("" + rowIndex)) {
                                JSONObject column = new JSONObject();
                                column.put("0", hasilPercentage.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                                hasilTran.put("" + rowIndex, column);
                            } else {
                                hasilTran.getJSONObject("" + rowIndex).put("0", hasilPercentage.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (rowCategory.get(0).getJenisLabel() == 2) {
                    for (int i = 0; i < hasil.length(); i++) {
                        try {
                            int rowIndex = (getIndexRow(hasil.getJSONObject(i)) * 2);
                            if (!hasilTran.has("" + rowIndex)) {
                                JSONObject column = new JSONObject();
                                column.put("0", hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                                hasilTran.put("" + rowIndex, column);
                            } else {
                                hasilTran.getJSONObject("" + rowIndex).put("0", hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    for (int i = 0; i < hasilPercentage.length(); i++) {
                        try {
                            int rowIndex = (getIndexRow(hasil.getJSONObject(i)) * 2) + 1;
                            if (!hasilTran.has("" + rowIndex)) {
                                JSONObject column = new JSONObject();
                                column.put("0", hasilPercentage.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                                hasilTran.put("" + rowIndex, column);
                            } else {
                                hasilTran.getJSONObject("" + rowIndex).put("0", hasilPercentage.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                if (rowCategory.get(0).getJenisLabel() == 0) {
                    for (int i = 0; i < hasil.length(); i++) {
                        try {
                            int rowIndex = getIndexRow(hasil.getJSONObject(i));
                            if (!hasilTran.has("" + rowIndex)) {
                                JSONObject column = new JSONObject();
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    column.put("" + valueIndex, hasil.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                                hasilTran.put("" + rowIndex, column);
                            } else {
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    hasilTran.getJSONObject("" + rowIndex).put("" + valueIndex, hasil.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (rowCategory.get(0).getJenisLabel() == 1) {
                    for (int i = 0; i < hasilPercentage.length(); i++) {
                        try {
                            int rowIndex = getIndexRow(hasilPercentage.getJSONObject(i));
                            if (!hasilTran.has("" + rowIndex)) {
                                JSONObject column = new JSONObject();
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    column.put("" + valueIndex, hasilPercentage.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                                hasilTran.put("" + rowIndex, column);
                            } else {
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    hasilTran.getJSONObject("" + rowIndex).put("" + valueIndex, hasilPercentage.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (rowCategory.get(0).getJenisLabel() == 2) {
                    for (int i = 0; i < hasil.length(); i++) {
                        try {
                            int rowIndex = getIndexRow(hasil.getJSONObject(i)) * 2;
                            if (!hasilTran.has("" + rowIndex)) {
                                JSONObject column = new JSONObject();
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    column.put("" + valueIndex, hasil.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                                hasilTran.put("" + rowIndex, column);
                            } else {
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    hasilTran.getJSONObject("" + rowIndex).put("" + valueIndex, hasil.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    for (int i = 0; i < hasilPercentage.length(); i++) {
                        try {
                            int rowIndex = getIndexRow(hasilPercentage.getJSONObject(i)) * 2 + 1;
                            if (!hasilTran.has("" + rowIndex)) {
                                JSONObject column = new JSONObject();
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    column.put("" + valueIndex, hasilPercentage.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                                hasilTran.put("" + rowIndex, column);
                            } else {
                                for (int valueIndex = 0; valueIndex < objectTable.getValues().size(); valueIndex++) {
                                    hasilTran.getJSONObject("" + rowIndex).put("" + valueIndex, hasilPercentage.getJSONObject(i)
                                            .getDouble(objectTable.getValues().get(valueIndex).getNameColumnHasil()));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        } else {
            if (objectTable.getValues().size() == 0) {
                if (columnCategory.get(0).getJenisLabel() == 0 && rowCategory.get(0).getJenisLabel() == 0) {
                    for (int i = 0; i < hasil.length(); i++) {
                        try {
                            int indexRow = getIndexRow(hasil.getJSONObject(i));
                            int indexColumn = getIndexColumn(hasil.getJSONObject(i));
                            if (!hasilTran.has("" + indexRow)) {
                                JSONObject column = new JSONObject();
                                column.put("" + indexColumn, hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                                hasilTran.put("" + indexRow, column);
                            } else {
                                hasilTran.getJSONObject("" + indexRow).put("" + indexColumn, hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if ((columnCategory.get(0).getJenisLabel() == 1 && rowCategory.get(0).getJenisLabel() == 0)
                        || (columnCategory.get(0).getJenisLabel() == 0 && rowCategory.get(0).getJenisLabel() == 1)) {
                    for (int i = 0; i < hasilPercentage.length(); i++) {
                        try {
                            int indexRow = getIndexRow(hasilPercentage.getJSONObject(i));
                            int indexColumn = getIndexColumn(hasilPercentage.getJSONObject(i));
                            if (!hasilTran.has("" + indexRow)) {
                                JSONObject column = new JSONObject();
                                column.put("" + indexColumn, hasilPercentage.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                                hasilTran.put("" + indexRow, column);
                            } else {
                                hasilTran.getJSONObject("" + indexRow).put("" + indexColumn, hasilPercentage.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if ((columnCategory.get(0).getJenisLabel() == 2 && rowCategory.get(0).getJenisLabel() == 0)
                        || (columnCategory.get(0).getJenisLabel() == 0 && rowCategory.get(0).getJenisLabel() == 2)) {
                    boolean isRow = true;
                    if (columnCategory.get(0).getJenisLabel() == 2) {
                        isRow = false;
                    }
                    for (int i = 0; i < hasil.length(); i++) {
                        try {
                            int indexRow = 0;
                            int indexColumn = 0;
                            if (isRow) {
                                indexRow = getIndexRow(hasil.getJSONObject(i)) * 2;
                                indexColumn = getIndexColumn(hasil.getJSONObject(i));
                            } else {
                                indexRow = getIndexRow(hasil.getJSONObject(i));
                                indexColumn = getIndexColumn(hasil.getJSONObject(i)) * 2;
                            }
                            if (!hasilTran.has("" + indexRow)) {
                                JSONObject column = new JSONObject();
                                column.put("" + indexColumn, hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                                hasilTran.put("" + indexRow, column);
                            } else {
                                hasilTran.getJSONObject("" + indexRow).put("" + indexColumn, hasil.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    for (int i = 0; i < hasilPercentage.length(); i++) {
                        try {
                            int indexRow = 0;
                            int indexColumn = 0;
                            if (isRow) {
                                indexRow = getIndexRow(hasilPercentage.getJSONObject(i)) * 2 + 1;
                                indexColumn = getIndexColumn(hasilPercentage.getJSONObject(i));
                            } else {
                                indexRow = getIndexRow(hasilPercentage.getJSONObject(i));
                                indexColumn = getIndexColumn(hasilPercentage.getJSONObject(i)) * 2 + 1;
                            }
                            if (!hasilTran.has("" + indexRow)) {
                                JSONObject column = new JSONObject();
                                column.put("" + indexColumn, hasilPercentage.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                                hasilTran.put("" + indexRow, column);
                            } else {
                                hasilTran.getJSONObject("" + indexRow).put("" + indexColumn, hasilPercentage.getJSONObject(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                if (columnCategory.get(0).getJenisLabel() == 0 && rowCategory.get(0).getJenisLabel() == 0) {
                    for (int i = 0; i < hasil.length(); i++) {
                        try {
                            int indexRow = getIndexRow(hasil.getJSONObject(i));
                            int indexColumn = getIndexColumn(hasil.getJSONObject(i)) * objectTable.getValues().size();
                            for (int indexValue = 0; indexValue < objectTable.getValues().size(); indexValue++) {
                                int indexColumnLocal = indexColumn + indexValue;
                                if (!hasilTran.has("" + indexRow)) {
                                    JSONObject column = new JSONObject();
                                    column.put("" + indexColumnLocal,
                                            hasil.getJSONObject(i).getDouble(objectTable.getValues().get(indexValue).getNameColumnHasil()));
                                    hasilTran.put("" + indexRow, column);
                                } else {
                                    hasilTran.getJSONObject("" + indexRow).put("" + indexColumnLocal,
                                            hasil.getJSONObject(i).getDouble(objectTable.getValues().get(indexValue).getNameColumnHasil()));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if ((columnCategory.get(0).getJenisLabel() == 1 && rowCategory.get(0).getJenisLabel() == 0)
                        || (columnCategory.get(0).getJenisLabel() == 0 && rowCategory.get(0).getJenisLabel() == 1)) {
                    for (int i = 0; i < hasilPercentage.length(); i++) {
                        try {
                            int indexRow = getIndexRow(hasilPercentage.getJSONObject(i));
                            int indexColumn = getIndexColumn(hasilPercentage.getJSONObject(i)) * objectTable.getValues().size();
                            for (int indexValue = 0; indexValue < objectTable.getValues().size(); indexValue++) {
                                int indexColumnLocal = indexColumn + indexValue;
                                if (!hasilTran.has("" + indexRow)) {
                                    JSONObject column = new JSONObject();
                                    column.put("" + indexColumnLocal,
                                            hasilPercentage.getJSONObject(i).getDouble(objectTable.getValues().get(indexValue).getNameColumnHasil()));
                                    hasilTran.put("" + indexRow, column);
                                } else {
                                    hasilTran.getJSONObject("" + indexRow).put("" + indexColumnLocal,
                                            hasilPercentage.getJSONObject(i).getDouble(objectTable.getValues().get(indexValue).getNameColumnHasil()));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if ((columnCategory.get(0).getJenisLabel() == 2 && rowCategory.get(0).getJenisLabel() == 0)
                        || (columnCategory.get(0).getJenisLabel() == 0 && rowCategory.get(0).getJenisLabel() == 2)) {
                    boolean isRow = true;
                    if (columnCategory.get(0).getJenisLabel() == 2) {
                        isRow = false;
                    }
                    for (int i = 0; i < hasil.length(); i++) {
                        try {
                            int indexRow = 0;
                            int indexColumn = 0;
                            if (isRow) {
                                indexRow = getIndexRow(hasil.getJSONObject(i)) * 2;
                                indexColumn = getIndexColumn(hasil.getJSONObject(i)) * objectTable.getValues().size();
                            } else {
                                indexRow = getIndexRow(hasil.getJSONObject(i));
                                indexColumn = getIndexColumn(hasil.getJSONObject(i)) * 2 * objectTable.getValues().size();
                            }
                            for (int indexValue = 0; indexValue < objectTable.getValues().size(); indexValue++) {
                                int indexColumnLocal = indexColumn + indexValue;
                                if (!hasilTran.has("" + indexRow)) {
                                    JSONObject column = new JSONObject();
                                    column.put("" + indexColumnLocal,
                                            hasilPercentage.getJSONObject(i).getDouble(objectTable.getValues().get(indexValue).getNameColumnHasil()));
                                    hasilTran.put("" + indexRow, column);
                                } else {
                                    hasilTran.getJSONObject("" + indexRow).put("" + indexColumnLocal,
                                            hasilPercentage.getJSONObject(i).getDouble(objectTable.getValues().get(indexValue).getNameColumnHasil()));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    for (int i = 0; i < hasilPercentage.length(); i++) {
                        try {
                            int indexRow = 0;
                            int indexColumn = 0;
                            if (isRow) {
                                indexRow = getIndexRow(hasilPercentage.getJSONObject(i)) * 2 + 1;
                                indexColumn = getIndexColumn(hasilPercentage.getJSONObject(i)) * objectTable.getValues().size();
                            } else {
                                indexRow = getIndexRow(hasilPercentage.getJSONObject(i));
                                indexColumn = (getIndexColumn(hasilPercentage.getJSONObject(i)) * 2 * objectTable.getValues().size()) + 1;
                            }
                            for (int indexValue = 0; indexValue < objectTable.getValues().size(); indexValue++) {
                                int indexColumnLocal = indexColumn + indexValue;
                                if (!hasilTran.has("" + indexRow)) {
                                    JSONObject column = new JSONObject();
                                    column.put("" + indexColumnLocal,
                                            hasilPercentage.getJSONObject(i).getDouble(objectTable.getValues().get(indexValue).getNameColumnHasil()));
                                    hasilTran.put("" + indexRow, column);
                                } else {
                                    hasilTran.getJSONObject("" + indexRow).put("" + indexColumnLocal,
                                            hasilPercentage.getJSONObject(i).getDouble(objectTable.getValues().get(indexValue).getNameColumnHasil()));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void addArrangeTotal() {

    }

    public void processAll() {
        filterTheUnnecity();
        for (int i = 0; i < rowCategory.size(); i++) {
            processCategoryTotal(true, i);
        }
        for (int i = 0; i < columnCategory.size(); i++) {
            processCategoryTotal(false, i);
        }
        processTotal();
        processPencentage();
        for (int i = 0; i < rowTotal.size(); i++) {
            System.out.println("Total Row : " + i + ". " + rowTotal.get(i).toString());
        }
        for (int i = 0; i < columnTotal.size(); i++) {
            System.out.println("Total Column : " + i + ". " + columnTotal.get(i).toString());
        }
        arrange();
        addArrangeTotal();
        System.out.println(hasilPercentage.toString());
        System.out.println(rowTotalPercentage.toString());
        System.out.println(columnTotalPercentage.toString());
        System.out.println(rowTotal.toString());
        System.out.println(columnTotal.toString());
        System.out.println(hasilTran.toString());
    }

    /**
     * @return the kordinatRow
     */
    public int getKordinatRow() {
        return kordinatRow;
    }

    /**
     * @param kordinatRow the kordinatRow to set
     */
    public void setKordinatRow(int kordinatRow) {
        this.kordinatRow = kordinatRow;
    }

    /**
     * @return the kordinatColumn
     */
    public int getKordinatColumn() {
        return kordinatColumn;
    }

    /**
     * @param kordinatColumn the kordinatColumn to set
     */
    public void setKordinatColumn(int kordinatColumn) {
        this.kordinatColumn = kordinatColumn;
    }

    public MakeTableHelper makeColumnHeader(int startRow, int startColumn, int maxColumnCategory, Sheet sheet) {
        MakeTableHelper returnValue = new MakeTableHelper();
        returnValue.setColumnStart(startColumn);
        returnValue.setRowStart(startRow);
        System.out.println("start:" + startRow + "|" + startColumn);
        if (columnCategory.size() > 0) {
            returnValue.setLastIDColumn(columnCategory.get(columnCategory.size() - 1).getNameColumnHasil());
            int banyaKolom = 1;
            for (int i = 0; i < columnCategory.size(); i++) {
                banyaKolom = banyaKolom * columnCategory.get(i).getSizeItem();
            }
            if (objectTable.getValues().size() <= 1) {
                for (int i = 0; i < columnCategory.size(); i++) {
                    int weight = (weightCategory(false, i) * columnCategory.get(i).getSizeItem());
                    int pengulangan = banyaKolom / weight;
                    int weightItem = weightCategory(false, i);
                    int pengulanganItem = banyaKolom / weightItem;
                    Row row = sheet.getRow(startRow + (i * 2));
                    if (row == null) {
                        row = sheet.createRow(startRow + (i * 2));
                    }
                    Row itemRow = sheet.getRow(startRow + (i * 2) + 1);
                    if (itemRow == null) {
                        itemRow = sheet.createRow(startRow + (i * 2) + 1);
                    }
                    System.out.println("weight :" + weight);
                    System.out.println("weight Item :" + weightItem);
                    System.out.println("Pengulangan :" + pengulangan);
                    System.out.println("Pengulangan Item :" + pengulanganItem);
                    for (int j = 0; j < pengulangan; j++) {
                        Cell cell = row.getCell(startColumn + (j * weight));
                        if (cell == null) {
                            cell = row.createCell(startColumn + (j * weight));
                        }
                        cell.setCellValue(columnCategory.get(i).getNameColumn());
                        CellUtil.setVerticalAlignment(cell, VerticalAlignment.CENTER);
                        if ((startColumn + (j * weight)) != (startColumn + (j * weight) + weight - 1)) {
                            System.out.println(startColumn + (j * weight));
                            System.out.println(startColumn + (j * weight) + weight - 1);
                            sheet.addMergedRegion(new CellRangeAddress(startRow + (i * 2), startRow + (i * 2),
                                    startColumn + (j * weight), startColumn + (j * weight) + weight - 1));
                        }
                    }
                    for (int k = 0; k < pengulanganItem; k++) {
                        Cell itemCell = itemRow.getCell(startColumn + (k * weightItem));
                        if (itemCell == null) {
                            itemCell = itemRow.createCell(startColumn + (k * weightItem));
                        }
                        itemCell.setCellValue(columnCategory.get(i).getItemByIndex(k % columnCategory.get(i).getSizeItem()));
                        CellUtil.setVerticalAlignment(itemCell, VerticalAlignment.CENTER);
                        if ((startColumn + (k * weightItem)) != (startColumn + (k * weightItem) + weightItem - 1)) {
                            sheet.addMergedRegion(new CellRangeAddress(startRow + (i * 2) + 1, startRow + (i * 2) + 1,
                                    startColumn + (k * weightItem), startColumn + (k * weightItem) + weightItem - 1));
                        }
                        if ((i == (columnCategory.size() - 1)) && ((maxColumnCategory - 1) != i)) {
                            sheet.addMergedRegion(new CellRangeAddress(startRow + (i * 2) + 1, startRow + (maxColumnCategory * 2) - 1,
                                    startColumn + (k * weightItem), startColumn + (k * weightItem)));
                        }
                    }
                }
            } else {
                banyaKolom = banyaKolom * objectTable.getValues().size();
                for (int i = 0; i < columnCategory.size(); i++) {
                    int weight = (weightCategory(false, i) * columnCategory.get(i).getSizeItem()) * objectTable.getValues().size();
                    int pengulangan = banyaKolom / weight;
                    int weightItem = weightCategory(false, i) * objectTable.getValues().size();
                    int pengulanganItem = banyaKolom / weightItem;
                    Row row = sheet.getRow(startRow + (i * 2));
                    if (row == null) {
                        row = sheet.createRow(startRow + (i * 2));
                    }
                    Row itemRow = sheet.getRow(startRow + (i * 2) + 1);
                    if (itemRow == null) {
                        itemRow = sheet.createRow(startRow + (i * 2) + 1);
                    }
                    for (int j = 0; j < pengulangan; j++) {
                        Cell cell = row.getCell(startColumn + (j * weight));
                        if (cell == null) {
                            cell = row.createCell(startColumn + (j * weight));
                        }
                        cell.setCellValue(columnCategory.get(i).getNameColumn());
                        CellUtil.setVerticalAlignment(cell, VerticalAlignment.CENTER);
                        if ((startColumn + (j * weight)) != (startColumn + (j * weight) + weight - 1)) {
                            sheet.addMergedRegion(new CellRangeAddress(startRow + (i * 2), startRow + (i * 2),
                                    startColumn + (j * weight), startColumn + (j * weight) + weight - 1));
                        }
                        for (int k = 0; k < pengulanganItem; k++) {
                            Cell itemCell = itemRow.getCell(startColumn + (j * weight) + (k * weightItem));
                            if (itemCell == null) {
                                itemCell = itemRow.createCell(startColumn + (j * weight) + (k * weightItem));
                            }
                            CellUtil.setVerticalAlignment(itemCell, VerticalAlignment.CENTER);
                            itemCell.setCellValue(columnCategory.get(i).getItemByIndex(k % columnCategory.get(i).getSizeItem()));
                            if ((startColumn + (j * weight) + (k * weightItem)) != (startColumn + (j * weight) + (k * weightItem) + weightItem - 1)) {
                                sheet.addMergedRegion(new CellRangeAddress(startRow + (i * 2), startRow + (i * 2),
                                        startColumn + (j * weight) + (k * weightItem), startColumn + (j * weight) + (k * weightItem) + weightItem - 1));
                            }
                            if ((i == (columnCategory.size() - 1)) && ((maxColumnCategory - 1) != i)) {
                                sheet.addMergedRegion(new CellRangeAddress(startRow + (i * 2) + 1, startRow + (maxColumnCategory * 2) - 1,
                                        startColumn + (j * weight) + (k * weightItem), startColumn + (j * weight) + (k * weightItem)));
                            }
                        }
                    }
                }
                int pengulanganValue = banyaKolom / objectTable.getValues().size();
                int weightValue = objectTable.getValues().size();
                for (int i = 0; i < pengulanganValue; i++) {
                    Row row = sheet.getRow(maxColumnCategory);
                    if (row == null) {
                        row = sheet.createRow(maxColumnCategory);
                    }
                    for (int j = 0; j < weightValue; j++) {
                        Cell cell = row.getCell((i * weightValue) + j);
                        if (cell == null) {
                            cell = row.createCell((i * weightValue) + j);
                        }
                        cell.setCellValue(objectTable.getValues().get(j).getNameColumn());
                    }
                }
            }
            if (rowCategory.size() > 0) {
                Row row = sheet.getRow(startRow);
                if (row == null) {
                    row = sheet.createRow(startRow);
                }
                Cell cell = row.getCell(startColumn + banyaKolom);
                if (cell == null) {
                    cell = row.createCell(startColumn + banyaKolom);
                }
                cell.setCellValue("Total");
                CellUtil.setVerticalAlignment(cell, VerticalAlignment.CENTER);
                if (objectTable.getValues().size() > 1) {
                    Row rowValues = sheet.getRow(startRow + maxColumnCategory);
                    if (rowValues == null) {
                        rowValues = sheet.createRow(startRow + maxColumnCategory);
                    }
                    for (int m = 0; m < rowCategory.size(); m++) {
                        for (int l = 0; l < objectTable.getValues().size(); l++) {
                            Cell cellValues = rowValues.getCell(startColumn + banyaKolom + (objectTable.getValues().size() * m) + l);
                            if (cell == null) {
                                cellValues = rowValues.createCell(startColumn + banyaKolom + (objectTable.getValues().size() * m) + l);
                            }
                            cellValues.setCellValue(objectTable.getValues().get(l).getNameColumn());
                        }
                    }

                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow + (maxColumnCategory * 2) - 1,
                            startColumn + banyaKolom, startColumn + banyaKolom + (rowCategory.size() * objectTable.getValues().size()) - 1));
                    returnValue.setRowLast(startRow + (maxColumnCategory * 2));
                    returnValue.setColumnLast(startColumn + banyaKolom + (rowCategory.size() * objectTable.getValues().size()) - 1);
                } else {
                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow + (maxColumnCategory * 2) - 1,
                            startColumn + banyaKolom, startColumn + banyaKolom + rowCategory.size() - 1));
                    returnValue.setRowLast(startRow + (maxColumnCategory * 2) - 1);
                    returnValue.setColumnLast(startColumn + banyaKolom + rowCategory.size() - 1);
                }
            } else {
                returnValue.setRowLast(-1);
                returnValue.setColumnLast(startColumn + banyaKolom);
            }
        } else {
            if (objectTable.getValues().size() > 0) {
                Row itemRow = sheet.getRow(startRow);
                if (itemRow == null) {
                    itemRow = sheet.createRow(startRow);
                }
                for (int i = 0; i < objectTable.getValues().size(); i++) {
                    Cell itemCell = itemRow.getCell(startColumn + i);
                    if (itemCell == null) {
                        itemCell = itemRow.createCell(startColumn + i);
                    }
                    itemCell.setCellValue(objectTable.getValues().get(i).getNameColumn());
                }
                returnValue.setRowLast(startRow);
                returnValue.setColumnLast(startColumn + objectTable.getValues().size());
            } else {
                returnValue.setRowLast(startRow);
                returnValue.setColumnLast(startColumn);
            }
        }
        return returnValue;
    }

    public MakeTableHelper makeRowHeader(int startRow, int startColumn, int maxRowCategory, Sheet sheet) {
        MakeTableHelper returnValue = new MakeTableHelper();
        returnValue.setColumnStart(startColumn);
        returnValue.setRowStart(startRow);
        if (rowCategory.size() > 0) {
            returnValue.setLastIDRow(rowCategory.get(rowCategory.size() - 1).getNameColumnHasil());
            int banyaRow = 1;
            for (int i = 0; i < rowCategory.size(); i++) {
                banyaRow = banyaRow * rowCategory.get(i).getSizeItem();
            }
            for (int i = 0; i < rowCategory.size(); i++) {
                int weight = (weightCategory(true, i) * rowCategory.get(i).getSizeItem());
                int pengulangan = banyaRow / weight;
                int weightItem = weightCategory(true, i);
                int pengulanganItem = banyaRow / weightItem;
                for (int j = 0; j < pengulangan; j++) {
                    Row row = sheet.getRow(startRow + (j * weight));
                    if (row == null) {
                        row = sheet.createRow(startRow + (j * weight));
                    }
                    Cell cell = row.getCell(startColumn + (i * 2));
                    if (cell == null) {
                        cell = row.createCell(startColumn + (i * 2));
                    }
                    cell.setCellValue(rowCategory.get(i).getNameColumn());
                    CellUtil.setVerticalAlignment(cell, VerticalAlignment.CENTER);
                    if ((startRow + (j * weight)) != (startRow + (j * weight) + weight - 1)) {
                        sheet.addMergedRegion(new CellRangeAddress(startRow + (j * weight), startRow + (j * weight) + weight - 1,
                                startColumn + (i * 2), startColumn + (i * 2)));
                    }
                }
                for (int k = 0; k < pengulanganItem; k++) {
                    Row itemRow = sheet.getRow(startRow + (k * weightItem));
                    if (itemRow == null) {
                        itemRow = sheet.createRow(startRow + (k * weightItem));
                    }
                    Cell itemCell = itemRow.getCell(startColumn + (i * 2) + 1);
                    if (itemCell == null) {
                        itemCell = itemRow.createCell(startColumn + (i * 2) + 1);
                    }
                    itemCell.setCellValue(rowCategory.get(i).getItemByIndex(k % rowCategory.get(i).getSizeItem()));
                    CellUtil.setVerticalAlignment(itemCell, VerticalAlignment.CENTER);
                    if ((startRow + (k * weightItem) + weightItem - 1)
                            != (startRow + (k * weightItem))) {
                        sheet.addMergedRegion(new CellRangeAddress(startRow + (k * weightItem),
                                startRow + (k * weightItem) + weightItem - 1,
                                startColumn + (i * 2) + 1, startColumn + (i * 2) + 1));
                    }
                    if ((i == (columnCategory.size() - 1)) && ((maxRowCategory - 1) != i)) {
                        sheet.addMergedRegion(new CellRangeAddress(startRow + (k * weightItem), startRow + (k * weightItem),
                                startColumn + (i * 2) + 1, startColumn + (maxRowCategory * 2) - 1));
                    }
                }
            }

            if (columnCategory.size() > 0) {
                Row row = sheet.getRow(startRow + banyaRow);
                if (row == null) {
                    row = sheet.createRow(startRow + banyaRow);
                }
                Cell cell = row.getCell(startColumn);
                if (cell == null) {
                    cell = row.createCell(startColumn);
                }
                cell.setCellValue("Total");
                CellUtil.setVerticalAlignment(cell, VerticalAlignment.CENTER);
                sheet.addMergedRegion(new CellRangeAddress(startRow + banyaRow, startRow + banyaRow + columnCategory.size() - 1,
                        startColumn, startColumn + (maxRowCategory * 2) - 1));

                returnValue.setColumnLast(startColumn + (maxRowCategory * 2) - 1);
                returnValue.setRowLast(startRow + banyaRow + columnCategory.size() - 1);
            } else {
                returnValue.setColumnLast(-1);
                returnValue.setRowLast(startRow + banyaRow);
            }
        } else {
            returnValue.setColumnLast(-1);
            returnValue.setRowLast(startRow + 1);
        }
        return returnValue;
    }

    public void makeInside(int startRow, int startColumn, Sheet sheet) {
        //input isi
        int banyaKolom = 1;
        for (int i = 0; i < columnCategory.size(); i++) {
            if (columnCategory.get(i).getJenisRecode() == 0) {
                banyaKolom = banyaKolom * ((ObjectCategorySimple) columnCategory.get(i)).getPilihanOption().size();
            } else if (columnCategory.get(i).getJenisRecode() == 1) {
                banyaKolom = banyaKolom * ((ObjectCategoryRecode) columnCategory.get(i)).getObjectItemRecodeDoubles().size();
            } else if (columnCategory.get(i).getJenisRecode() == 2) {
                banyaKolom = banyaKolom * ((ObjectCategoryRecode) columnCategory.get(i)).getObjectItemRecodeStrings().size();
            }
        }
        int banyaRow = 1;
        for (int i = 0; i < rowCategory.size(); i++) {
            if (rowCategory.get(i).getJenisRecode() == 0) {
                banyaRow = banyaRow * ((ObjectCategorySimple) rowCategory.get(i)).getPilihanOption().size();
            } else if (rowCategory.get(i).getJenisRecode() == 1) {
                banyaRow = banyaRow * ((ObjectCategoryRecode) rowCategory.get(i)).getObjectItemRecodeDoubles().size();
            } else if (rowCategory.get(i).getJenisRecode() == 2) {
                banyaRow = banyaRow * ((ObjectCategoryRecode) rowCategory.get(i)).getObjectItemRecodeStrings().size();
            }
        }
        if (objectTable.getValues().size() > 1) {
            banyaKolom = banyaKolom * objectTable.getValues().size();
        }
        System.out.println(hasilTran.length() + "||" + banyaKolom);
        for (int i = 0; i < hasilTran.length(); i++) {
            int rowLocal = startRow + i;
            Row itemRow = sheet.getRow(rowLocal);
            if (itemRow == null) {
                itemRow = sheet.createRow(rowLocal);
            }
            for (int j = 0; j < banyaKolom; j++) {
                try {
                    int columnLocal = startColumn + j;
                    Cell itemColumn = itemRow.getCell(columnLocal);
                    if (itemColumn == null) {
                        itemColumn = itemRow.createCell(columnLocal);
                    }
                    //System.out.println(hasilTran.getJSONObject("" + i).toString());
                    itemColumn.setCellValue(hasilTran.getJSONObject("" + i).getDouble("" + j));
                } catch (Exception ex) {
                    int columnLocal = startColumn + j;
                    Cell itemColumn = itemRow.getCell(columnLocal);
                    if (itemColumn == null) {
                        itemColumn = itemRow.createCell(columnLocal);
                    }
                    //System.out.println(hasilTran.getJSONObject("" + i).toString());
                    itemColumn.setCellValue(0.0);
                    ex.printStackTrace();
                }
            }
        }
        int startTotalRowRow = startRow;
        int stratTotalRowColumn = startColumn + banyaKolom;
        int startTotalColumnRow = startRow + banyaRow;
        int stratTotalColumnColumn = startColumn;

//        int LastRow = startTotalColumnRow + columnCategory.size();
//        int LastColumn = stratTotalRowColumn + rowCategory.size();
//        returnValue.setLastColumn(LastColumn);
//        returnValue.setLastRow(LastRow);
//        if (columnCategory.size() > 0) {
//            returnValue.setLastIDColumn(columnCategory.get(columnCategory.size() - 1).getNameColumnHasil());
//        }
//        if (rowCategory.size() > 0) {
//            returnValue.setLastIDRow(rowCategory.get(rowCategory.size() - 1).getNameColumnHasil());
//        }
        if (rowCategory.size() == 0 || columnCategory.size() == 0) {
            return;
        }
        if (rowCategory.get(0).getJenisLabel() == 0 && columnCategory.get(0).getJenisLabel() == 0) {
            if (objectTable.getValues().size() == 0) {
                for (int i = 0; i < rowTotal.size(); i++) {
                    try {
                        int[] index = getTotalRowIndex(rowTotal.get(i), startTotalRowRow, stratTotalRowColumn);
                        if (index != null) {
                            Row row = sheet.getRow(index[1]);
                            if (row == null) {
                                row = sheet.createRow(index[1]);
                            }
                            Cell cell = row.getCell(index[0]);
                            if (cell == null) {
                                cell = row.createCell(index[0]);
                            }
                            cell.setCellValue(0.0);
                            cell.setCellValue(rowTotal.get(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            if (index[1] != index[2] || index[0] != index[0]) {
                                sheet.addMergedRegion(new CellRangeAddress(index[1], index[2], index[0], index[0]));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                for (int i = 0; i < columnTotal.size(); i++) {
                    try {
                        int[] index = getTotalColumnIndex(columnTotal.get(i), startTotalColumnRow, stratTotalColumnColumn);
//                        System.out.println("JSON : "+columnTotal.get(i).toString());
//                        System.out.println("Index "+index[0]+"|"+index[1]+"|"+index[2]);
                        if (index != null) {
                            Row row = sheet.getRow(index[0]);
                            if (row == null) {
                                row = sheet.createRow(index[0]);
                            }
                            Cell cell = row.getCell(index[1]);
                            if (cell == null) {
                                cell = row.createCell(index[1]);
                            }
                            cell.setCellValue(0.0);
                            cell.setCellValue(columnTotal.get(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            if (index[0] != index[0] || index[1] != index[2]) {
                                sheet.addMergedRegion(new CellRangeAddress(index[0], index[0], index[1], index[2]));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    Row row = sheet.getRow(startRow + banyaRow);
                    if (row == null) {
                        row = sheet.createRow(startRow + banyaRow);
                    }
                    Cell cell = row.getCell(startColumn + banyaKolom);
                    if (cell == null) {
                        cell = row.createCell(startColumn + banyaKolom);
                    }
                    cell.setCellValue(0.0);
                    cell.setCellValue(total.getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                for (int i = 0; i < rowTotal.size(); i++) {
                    try {
                        int[] index = getTotalRowIndex(rowTotal.get(i), startTotalRowRow, stratTotalRowColumn);
                        if (index != null) {
                            Row row = sheet.getRow(index[1]);
                            if (row == null) {
                                row = sheet.createRow(index[1]);
                            }
                            Cell cell = row.getCell(index[0]);
                            if (cell == null) {
                                cell = row.createCell(index[0]);
                            }
                            double value = 0;
                            for (int j = 0; j < objectTable.getValues().size(); j++) {
                                value = value + rowTotal.get(i).getDouble(objectTable.getValues().get(j).getNameColumnHasil());
                            }
                            cell.setCellValue(0.0);
                            cell.setCellValue(value);
                            if (index[1] != index[2] || index[0] != index[0]) {
                                sheet.addMergedRegion(new CellRangeAddress(index[1], index[2], index[0], index[0]));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                for (int i = 0; i < columnTotal.size(); i++) {
                    try {
                        int[] index = getTotalColumnIndex(columnTotal.get(i), startTotalColumnRow, stratTotalColumnColumn);
                        if (index[0] == startTotalColumnRow) {
                            int column = index[1] * objectTable.getValues().size();
                            for (int j = 0; j < objectTable.getValues().size(); j++) {
                                if (index != null) {
                                    Row row = sheet.getRow(index[0]);
                                    if (row == null) {
                                        row = sheet.createRow(index[0]);
                                    }
                                    Cell cell = row.getCell(column + j);
                                    if (cell == null) {
                                        cell = row.createCell(column + j);
                                    }
                                    cell.setCellValue(0.0);
                                    cell.setCellValue(columnTotal.get(i).getDouble(objectTable.getValues().get(j).getNameColumnHasil()));
                                    //sheet.addMergedRegion(new CellRangeAddress(index[0], index[0], index[1], index[2]));
                                }
                            }
                        }
                        index[2] = (index[2] - index[1]) * objectTable.getValues().size();
                        index[1] = index[1] * objectTable.getValues().size();
                        index[2] = index[1] + index[2];
                        index[0] = index[0] + 1;
                        if (index != null) {
                            Row row = sheet.getRow(index[0]);
                            if (row == null) {
                                row = sheet.createRow(index[0]);
                            }
                            Cell cell = row.getCell(index[1]);
                            if (cell == null) {
                                cell = row.createCell(index[1]);
                            }
                            double value = 0;
                            for (int j = 0; j < objectTable.getValues().size(); j++) {
                                value = value + columnTotal.get(i).getDouble(objectTable.getValues().get(j).getNameColumnHasil());
                            }
                            cell.setCellValue(0.0);
                            cell.setCellValue(value);
                            if (index[0] != index[0] || index[1] != index[2]) {
                                sheet.addMergedRegion(new CellRangeAddress(index[0], index[0], index[1], index[2]));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
//                Row row = sheet.getRow(startRow + banyaRow);
//                if (row == null) {
//                    row = sheet.createRow(startRow + banyaRow);
//                }
//                Cell cell = row.getCell(startColumn + banyaKolom);
//                if (cell == null) {
//                    cell = row.getCell(startColumn + banyaKolom);
//                }
//                cell.setCellValue();
            }
        } else if ((columnCategory.get(0).getJenisLabel() == 1 && rowCategory.get(0).getJenisLabel() == 0)
                || (columnCategory.get(0).getJenisLabel() == 0 && rowCategory.get(0).getJenisLabel() == 1)) {
            if (objectTable.getValues().size() == 0) {
                for (int i = 0; i < rowTotalPercentage.size(); i++) {
                    try {
                        int[] index = getTotalRowIndex(rowTotalPercentage.get(i), startTotalRowRow, stratTotalRowColumn);
                        if (index != null) {
                            Row row = sheet.getRow(index[1]);
                            if (row == null) {
                                row = sheet.createRow(index[1]);
                            }
                            Cell cell = row.getCell(index[0]);
                            if (cell == null) {
                                cell = row.createCell(index[0]);
                            }
                            cell.setCellValue(0.0);
                            cell.setCellValue(rowTotalPercentage.get(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            if (index[1] != index[2] || index[0] != index[0]) {
                                sheet.addMergedRegion(new CellRangeAddress(index[1], index[2], index[0], index[0]));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                for (int i = 0; i < columnTotalPercentage.size(); i++) {
                    try {
                        int[] index = getTotalColumnIndex(columnTotalPercentage.get(i), startTotalColumnRow, stratTotalColumnColumn);
                        System.out.println(index[0] + "|" + index[1] + "|" + index[2] + "|");
                        if (index != null) {
                            Row row = sheet.getRow(index[0]);
                            if (row == null) {
                                row = sheet.createRow(index[0]);
                            }
                            Cell cell = row.getCell(index[1]);
                            if (cell == null) {
                                cell = row.createCell(index[1]);
                            }
                            cell.setCellValue(0.0);
                            cell.setCellValue(columnTotalPercentage.get(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
                            if (index[0] != index[0] || index[1] != index[2]) {
                                sheet.addMergedRegion(new CellRangeAddress(index[0], index[0], index[1], index[2]));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    Row row = sheet.getRow(startRow + banyaRow);
                    if (row == null) {
                        row = sheet.createRow(startRow + banyaRow);
                    }
                    Cell cell = row.getCell(startColumn + banyaKolom);
                    if (cell == null) {
                        cell = row.createCell(startColumn + banyaKolom);
                    }
                    cell.setCellValue(0.0);
                    cell.setCellValue(100.0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                for (int i = 0; i < rowTotalPercentage.size(); i++) {
                    try {
                        int[] index = getTotalRowIndex(rowTotalPercentage.get(i), startTotalRowRow, stratTotalRowColumn);
                        if (index != null) {
                            Row row = sheet.getRow(index[1]);
                            if (row == null) {
                                row = sheet.createRow(index[1]);
                            }
                            Cell cell = row.getCell(index[0]);
                            if (cell == null) {
                                cell = row.createCell(index[0]);
                            }
                            double value = 0;
                            for (int j = 0; j < objectTable.getValues().size(); j++) {
                                value = value + rowTotalPercentage.get(i).getDouble(objectTable.getValues().get(j).getNameColumnHasil());
                            }
                            cell.setCellValue(0.0);
                            cell.setCellValue(value);
                            if (index[1] != index[2] || index[0] != index[0]) {
                                sheet.addMergedRegion(new CellRangeAddress(index[1], index[2], index[0], index[0]));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                for (int i = 0; i < columnTotalPercentage.size(); i++) {
                    try {
                        int[] index = getTotalColumnIndex(columnTotalPercentage.get(i), startTotalColumnRow, stratTotalColumnColumn);
                        if (index[0] == startTotalColumnRow) {
                            int column = index[1] * objectTable.getValues().size();
                            for (int j = 0; j < objectTable.getValues().size(); j++) {
                                if (index != null) {
                                    Row row = sheet.getRow(index[0]);
                                    if (row == null) {
                                        row = sheet.createRow(index[0]);
                                    }
                                    Cell cell = row.getCell(column + j);
                                    if (cell == null) {
                                        cell = row.createCell(column + j);
                                    }
                                    cell.setCellValue(0.0);
                                    cell.setCellValue(columnTotalPercentage.get(i).getDouble(objectTable.getValues().get(j).getNameColumnHasil()));
                                    //sheet.addMergedRegion(new CellRangeAddress(index[0], index[0], index[1], index[2]));
                                }
                            }
                        }
                        index[2] = (index[2] - index[1]) * objectTable.getValues().size();
                        index[1] = index[1] * objectTable.getValues().size();
                        index[2] = index[1] + index[2];
                        index[0] = index[0] + 1;
                        if (index != null) {
                            Row row = sheet.getRow(index[0]);
                            if (row == null) {
                                row = sheet.createRow(index[0]);
                            }
                            Cell cell = row.getCell(index[1]);
                            if (cell == null) {
                                cell = row.createCell(index[1]);
                            }
                            double value = 0;
                            for (int j = 0; j < objectTable.getValues().size(); j++) {
                                value = value + columnTotalPercentage.get(i).getDouble(objectTable.getValues().get(j).getNameColumnHasil());
                            }
                            cell.setCellValue(0.0);
                            cell.setCellValue(value);
                            if (index[0] != index[0] || index[1] != index[2]) {
                                sheet.addMergedRegion(new CellRangeAddress(index[0], index[0], index[1], index[2]));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
//                Row row = sheet.getRow(startRow + banyaRow);
//                if (row == null) {
//                    row = sheet.createRow(startRow + banyaRow);
//                }
//                for(int k=0; k<objectTable.getValues().size(); k++){
//                    
//                }
//                Cell cell = row.getCell(startColumn + banyaKolom);
//                if (cell == null) {
//                    cell = row.getCell(startColumn + banyaKolom);
//                }
//                cell.setCellValue();
            }
        }

//        if (columnCategory.size() > 0 && rowCategory.size() == 0) {
//            if (columnCategory.get(0).getJenisLabel() == 0) {
//                if (objectTable.getValues().size() == 0) {
//                    for (int i = 0; i < columnTotal.size(); i++) {
//                        try {
//                            int[] index = getTotalColumnIndex(columnTotal.get(i), startTotalColumnRow, stratTotalColumnColumn);
//                            if (index != null) {
//                                Row row = sheet.getRow(index[0]);
//                                if (row == null) {
//                                    row = sheet.createRow(index[0]);
//                                }
//                                Cell cell = row.getCell(index[1]);
//                                if (cell == null) {
//                                    cell = row.createCell(index[1]);
//                                }
//                                cell.setCellValue(columnTotal.get(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
//                                sheet.addMergedRegion(new CellRangeAddress(index[0], index[0], index[1], index[2]));
//                            }
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                } else {
//                    for (int i = 0; i < columnTotal.size(); i++) {
//                        try {
//                            int[] index = getTotalColumnIndex(columnTotal.get(i), startTotalColumnRow, stratTotalColumnColumn);
//                            if (index[0] == startTotalColumnRow) {
//                                int column = index[1] * objectTable.getValues().size();
//                                for (int j = 0; j < objectTable.getValues().size(); j++) {
//                                    if (index != null) {
//                                        Row row = sheet.getRow(index[0]);
//                                        if (row == null) {
//                                            row = sheet.createRow(index[0]);
//                                        }
//                                        Cell cell = row.getCell(column + j);
//                                        if (cell == null) {
//                                            cell = row.createCell(column + j);
//                                        }
//                                        cell.setCellValue(columnTotal.get(i).getDouble(objectTable.getValues().get(j).getNameColumnHasil()));
//                                        //sheet.addMergedRegion(new CellRangeAddress(index[0], index[0], index[1], index[2]));
//                                    }
//                                }
//                            }
//                            index[2] = (index[2] - index[1]) * objectTable.getValues().size();
//                            index[1] = index[1] * objectTable.getValues().size();
//                            index[2] = index[1] + index[2];
//                            index[0] = index[0] + 1;
//                            if (index != null) {
//                                Row row = sheet.getRow(index[0]);
//                                if (row == null) {
//                                    row = sheet.createRow(index[0]);
//                                }
//                                Cell cell = row.getCell(index[1]);
//                                if (cell == null) {
//                                    cell = row.createCell(index[1]);
//                                }
//                                double value = 0;
//                                for (int j = 0; j < objectTable.getValues().size(); j++) {
//                                    value = value + columnTotal.get(i).getDouble(objectTable.getValues().get(j).getNameColumnHasil());
//                                }
//                                cell.setCellValue(value);
//                                sheet.addMergedRegion(new CellRangeAddress(index[0], index[0], index[1], index[2]));
//                            }
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }
//            } else if (columnCategory.get(0).getJenisLabel() == 1) {
//                if (objectTable.getValues().size() == 0) {
//                    for (int i = 0; i < columnTotalPercentage.size(); i++) {
//                        try {
//                            int[] index = getTotalColumnIndex(columnTotalPercentage.get(i), startTotalColumnRow, stratTotalColumnColumn);
//                            if (index != null) {
//                                Row row = sheet.getRow(index[0]);
//                                if (row == null) {
//                                    row = sheet.createRow(index[0]);
//                                }
//                                Cell cell = row.getCell(index[1]);
//                                if (cell == null) {
//                                    cell = row.createCell(index[1]);
//                                }
//                                cell.setCellValue(columnTotalPercentage.get(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
//                                sheet.addMergedRegion(new CellRangeAddress(index[0], index[0], index[1], index[2]));
//                            }
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                } else {
//                    for (int i = 0; i < columnTotalPercentage.size(); i++) {
//                        try {
//                            int[] index = getTotalColumnIndex(columnTotalPercentage.get(i), startTotalColumnRow, stratTotalColumnColumn);
//                            if (index[0] == startTotalColumnRow) {
//                                int column = index[1] * objectTable.getValues().size();
//                                for (int j = 0; j < objectTable.getValues().size(); j++) {
//                                    if (index != null) {
//                                        Row row = sheet.getRow(index[0]);
//                                        if (row == null) {
//                                            row = sheet.createRow(index[0]);
//                                        }
//                                        Cell cell = row.getCell(column + j);
//                                        if (cell == null) {
//                                            cell = row.createCell(column + j);
//                                        }
//                                        cell.setCellValue(columnTotalPercentage.get(i).getDouble(objectTable.getValues().get(j).getNameColumnHasil()));
//                                        //sheet.addMergedRegion(new CellRangeAddress(index[0], index[0], index[1], index[2]));
//                                    }
//                                }
//                            }
//                            index[2] = (index[2] - index[1]) * objectTable.getValues().size();
//                            index[1] = index[1] * objectTable.getValues().size();
//                            index[2] = index[1] + index[2];
//                            index[0] = index[0] + 1;
//                            if (index != null) {
//                                Row row = sheet.getRow(index[0]);
//                                if (row == null) {
//                                    row = sheet.createRow(index[0]);
//                                }
//                                Cell cell = row.getCell(index[1]);
//                                if (cell == null) {
//                                    cell = row.createCell(index[1]);
//                                }
//                                double value = 0;
//                                for (int j = 0; j < objectTable.getValues().size(); j++) {
//                                    value = value + columnTotalPercentage.get(i).getDouble(objectTable.getValues().get(j).getNameColumnHasil());
//                                }
//                                cell.setCellValue(value);
//                                sheet.addMergedRegion(new CellRangeAddress(index[0], index[0], index[1], index[2]));
//                            }
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }
//            }
//
//        } else if (rowCategory.size() > 0 && columnCategory.size() == 0) {
//            if (rowCategory.get(0).getJenisLabel() == 0) {
//                if (objectTable.getValues().size() == 0) {
//                    for (int i = 0; i < rowTotal.size(); i++) {
//                        try {
//                            int[] index = getTotalRowIndex(rowTotal.get(i), startTotalRowRow, stratTotalRowColumn);
//                            if (index != null) {
//                                Row row = sheet.getRow(index[1]);
//                                if (row == null) {
//                                    row = sheet.createRow(index[1]);
//                                }
//                                Cell cell = row.getCell(index[0]);
//                                if (cell == null) {
//                                    cell = row.createCell(index[0]);
//                                }
//                                cell.setCellValue(rowTotal.get(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
//                                sheet.addMergedRegion(new CellRangeAddress(index[1], index[2], index[0], index[0]));
//                            }
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                } else {
//                    for (int i = 0; i < rowTotal.size(); i++) {
//                        try {
//                            int[] index = getTotalRowIndex(rowTotal.get(i), startTotalRowRow, stratTotalRowColumn);
//                            if (index != null) {
//                                Row row = sheet.getRow(index[1]);
//                                if (row == null) {
//                                    row = sheet.createRow(index[1]);
//                                }
//                                Cell cell = row.getCell(index[0]);
//                                if (cell == null) {
//                                    cell = row.createCell(index[0]);
//                                }
//                                double value = 0;
//                                for (int j = 0; j < objectTable.getValues().size(); j++) {
//                                    value = value + rowTotal.get(i).getDouble(objectTable.getValues().get(j).getNameColumnHasil());
//                                }
//                                cell.setCellValue(value);
//                                sheet.addMergedRegion(new CellRangeAddress(index[1], index[2], index[0], index[0]));
//                            }
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }
//            } else if (rowCategory.get(0).getJenisLabel() == 1) {
//                if (objectTable.getValues().size() == 0) {
//                    for (int i = 0; i < rowTotalPercentage.size(); i++) {
//                        try {
//                            int[] index = getTotalRowIndex(rowTotalPercentage.get(i), startTotalRowRow, stratTotalRowColumn);
//                            if (index != null) {
//                                Row row = sheet.getRow(index[1]);
//                                if (row == null) {
//                                    row = sheet.createRow(index[1]);
//                                }
//                                Cell cell = row.getCell(index[0]);
//                                if (cell == null) {
//                                    cell = row.createCell(index[0]);
//                                }
//                                cell.setCellValue(rowTotalPercentage.get(i).getDouble(ObjectTable.DEfAULT_NAME_HASIL));
//                                sheet.addMergedRegion(new CellRangeAddress(index[1], index[2], index[0], index[0]));
//                            }
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                } else {
//                    for (int i = 0; i < rowTotalPercentage.size(); i++) {
//                        try {
//                            int[] index = getTotalRowIndex(rowTotalPercentage.get(i), startTotalRowRow, stratTotalRowColumn);
//                            if (index != null) {
//                                Row row = sheet.getRow(index[1]);
//                                if (row == null) {
//                                    row = sheet.createRow(index[1]);
//                                }
//                                Cell cell = row.getCell(index[0]);
//                                if (cell == null) {
//                                    cell = row.createCell(index[0]);
//                                }
//                                double value = 0;
//                                for (int j = 0; j < objectTable.getValues().size(); j++) {
//                                    value = value + rowTotalPercentage.get(i).getDouble(objectTable.getValues().get(j).getNameColumnHasil());
//                                }
//                                cell.setCellValue(value);
//                                sheet.addMergedRegion(new CellRangeAddress(index[1], index[2], index[0], index[0]));
//                            }
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }
//            }
//        } else {
//            
//        }
//        return returnValue;
    }

    public int[] getTotalRowIndex(JSONObject item, int rowStart, int columnStart) {
        try {
            int[] hasil = new int[3];//column, row awal, row akhir
            int rowAwal = rowStart;
            int rowAkhir = 0;
            int column = columnStart;

            int tingkatan = 0;
            int indexTab = 0;

            for (int i = 0; i < rowCategory.size(); i++) {
                int pilihan = 0;
                if (item.has(rowCategory.get(i).getNameColumnHasil())) {
                    if (rowCategory.get(i).getJenisRecode() == 0) {
                        for (int j = 0; j < ((ObjectCategorySimple) rowCategory.get(i)).getPilihanOption().size(); j++) {
                            if (((ObjectCategorySimple) rowCategory.get(i)).getPilihanOption().get(j)
                                    .equals(item.getString(rowCategory.get(i).getNameColumnHasil()))) {
                                pilihan = j;
                            }
                        }
                    } else if (rowCategory.get(i).getJenisRecode() == 1) {
                        for (int j = 0; j < ((ObjectCategoryRecode) rowCategory.get(i)).getObjectItemRecodeDoubles().size(); j++) {
                            if (((ObjectCategoryRecode) rowCategory.get(i)).getObjectItemRecodeDoubles().get(j).getHasil()
                                    .equals(item.getString(rowCategory.get(i).getNameColumnHasil()))) {
                                pilihan = j;
                            }
                        }
                    } else if (rowCategory.get(i).getJenisRecode() == 2) {
                        for (int j = 0; j < ((ObjectCategoryRecode) rowCategory.get(i)).getObjectItemRecodeStrings().size(); j++) {
                            if (((ObjectCategoryRecode) rowCategory.get(i)).getObjectItemRecodeStrings().get(j).getHasil()
                                    .equals(item.getString(rowCategory.get(i).getNameColumnHasil()))) {
                                pilihan = j;
                            }
                        }
                    }
                } else {
                    tingkatan = tingkatan + 1;
                }
                indexTab = indexTab + (pilihan * weightCategory(true, i));
            }
            rowAwal = rowAwal + indexTab;
            rowAkhir = rowAwal + weightCategory(true, rowCategory.size() - tingkatan - 1) - 1;
            column = columnStart + tingkatan;
            hasil[0] = column;
            hasil[1] = rowAwal;
            hasil[2] = rowAkhir;
            return hasil;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int[] getTotalColumnIndex(JSONObject item, int rowStart, int columnStart) {
        try {
            int[] hasil = new int[3];//row, column awal, column akhir
            int columnAwal = columnStart;
            // System.out.println("ggg."+columnAwal);
            int columnAkhir = 0;
            int row = rowStart;

            int tingkatan = 0;
            int indexTab = 0;

            for (int i = 0; i < columnCategory.size(); i++) {
                int pilihan = 0;
                if (item.has(columnCategory.get(i).getNameColumnHasil())) {
                    if (columnCategory.get(i).getJenisRecode() == 0) {
                        for (int j = 0; j < ((ObjectCategorySimple) columnCategory.get(i)).getPilihanOption().size(); j++) {
                            if (((ObjectCategorySimple) columnCategory.get(i)).getPilihanOption().get(j)
                                    .equals(item.getString(columnCategory.get(i).getNameColumnHasil()))) {
                                pilihan = j;
                            }
                        }
                    } else if (columnCategory.get(i).getJenisRecode() == 1) {
                        for (int j = 0; j < ((ObjectCategoryRecode) columnCategory.get(i)).getObjectItemRecodeDoubles().size(); j++) {
                            if (((ObjectCategoryRecode) columnCategory.get(i)).getObjectItemRecodeDoubles().get(j).getHasil()
                                    .equals(item.getString(columnCategory.get(i).getNameColumnHasil()))) {
                                pilihan = j;
                            }
                        }
                    } else if (columnCategory.get(i).getJenisRecode() == 2) {
                        for (int j = 0; j < ((ObjectCategoryRecode) columnCategory.get(i)).getObjectItemRecodeStrings().size(); j++) {
                            if (((ObjectCategoryRecode) columnCategory.get(i)).getObjectItemRecodeStrings().get(j).getHasil()
                                    .equals(item.getString(columnCategory.get(i).getNameColumnHasil()))) {
                                pilihan = j;
                            }
                        }
                    }
                } else {
                    tingkatan = tingkatan + 1;
                }
                indexTab = indexTab + (pilihan * weightCategory(false, i));
            }
            columnAwal = columnAwal + indexTab;
            columnAkhir = columnAwal + weightCategory(false, columnCategory.size() - tingkatan - 1) - 1;
            row = rowStart + tingkatan;
            hasil[0] = row;
            hasil[1] = columnAwal;
            hasil[2] = columnAkhir;
            return hasil;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}

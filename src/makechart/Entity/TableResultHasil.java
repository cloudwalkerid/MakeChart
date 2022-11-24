/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Entity;

import java.util.ArrayList;
import org.json.JSONArray;

/**
 *
 * @author ASUS
 */
public class TableResultHasil {
    
    private ObjectTable objectTable;
    private ArrayList<TableResult> tableResults;
    private ArrayList<JSONArray> arrays;

    public TableResultHasil() {
        tableResults = new ArrayList<>();
        arrays = new ArrayList<>();
    }

    public TableResultHasil(ObjectTable objectTable, ArrayList<TableResult> tableResults, 
            ArrayList<JSONArray> arrays) {
        this.objectTable = objectTable;
        this.tableResults = tableResults;
        this.arrays = arrays;
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

    /**
     * @return the tableResults
     */
    public ArrayList<TableResult> getTableResults() {
        return tableResults;
    }

    /**
     * @param tableResults the tableResults to set
     */
    public void setTableResults(ArrayList<TableResult> tableResults) {
        this.tableResults = tableResults;
    }

    /**
     * @return the arrays
     */
    public ArrayList<JSONArray> getArrays() {
        return arrays;
    }

    /**
     * @param arrays the arrays to set
     */
    public void setArrays(ArrayList<JSONArray> arrays) {
        this.arrays = arrays;
    }
    
}

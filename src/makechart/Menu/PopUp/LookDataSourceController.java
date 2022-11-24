/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Menu.PopUp;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import makechart.Database.database;
import makechart.Entity.ObjectDataSource;
import makechart.Entity.ObjectInformationColumnTable;
import makechart.Entity.ObjectLookUpHelper;
import makechart.Entity.ObjectTable;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class LookDataSourceController implements Initializable {

    @FXML
    private TableView<ObjectLookUpHelper> obTableView;
    @FXML
    private Label name;
    @FXML
    private Label desc;
    
    ObservableList<ObjectLookUpHelper> optionsData
            = FXCollections.observableArrayList(new ArrayList<ObjectLookUpHelper>());
    
    private ObjectDataSource objectDataSource;
    private ArrayList<ObjectInformationColumnTable> columns; 
    private ArrayList<ObjectLookUpHelper> content; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        columns= new ArrayList<>();
        content = new ArrayList<>();
    } 
    
    public void setObjectDataSource(ObjectDataSource objectDataSource){
        this.objectDataSource = objectDataSource;
        database dBase = new database();
        columns = dBase.getInformationFromDataSource(objectDataSource);
        if(columns==null){
            columns = new ArrayList<>();
            return;
        }
        content = dBase.getLookHelpers(objectDataSource, columns);
        if(content==null){
            content = new ArrayList<>();
        }else{
            optionsData.addAll(content);
        }
        name.setText(objectDataSource.getName());
        desc.setText(objectDataSource.getDesc());
        for(int i=0; i<columns.size(); i++){
            final int j =i;
            TableColumn<ObjectLookUpHelper, String> item = new TableColumn<ObjectLookUpHelper, String>(columns.get(i).getColumnName());
            item.setCellValueFactory((param) -> {
                return new ReadOnlyObjectWrapper(param.getValue().getIsi().get(j));
            });
            obTableView.getColumns().add(item);
        }
        obTableView.setItems(optionsData);
        
        
    }
    
}

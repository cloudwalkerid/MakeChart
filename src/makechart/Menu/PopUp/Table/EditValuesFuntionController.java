/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Menu.PopUp.Table;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import makechart.Entity.EnumSQLColumnFunction;
import makechart.Entity.EnumSQLRowFunction;
import makechart.Entity.ObjectDataSource;
import makechart.Entity.ObjectValueTable;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class EditValuesFuntionController implements Initializable {
    
    @FXML
    private ComboBox<String> optionTypeFunction;
    @FXML
    private Button simpanButton;
    @FXML
    private Button kembaliButton;
    
    private EnumSQLColumnFunction selectedFunction = EnumSQLColumnFunction.COUNT;
    
    private ValueCategoryController valueController;
    ObservableList<String> optionsValueType
            = FXCollections.observableArrayList(new ArrayList<String>());
    
    ObjectValueTable objectValueTable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        optionTypeFunction.valueProperty().addListener((observable, oldValue, newValue) -> {
            int index = optionsValueType.indexOf(newValue);
            if(index==0){
                selectedFunction = EnumSQLColumnFunction.COUNT;
            }else if(index==1){
                selectedFunction = EnumSQLColumnFunction.NORMAL;
            }else if(index==2){
                selectedFunction = EnumSQLColumnFunction.AVERAGE;
            }else if(index==3){
                selectedFunction = EnumSQLColumnFunction.SUM;
            }
        });
    }    
    
    public void setControl(ValueCategoryController valueController, ObjectValueTable objectValueTable){
        this.valueController = valueController;
        this.objectValueTable = objectValueTable;
        optionsValueType.clear();
        optionsValueType.add("COUNT("+objectValueTable.getNameColumn()+")");
        optionsValueType.add(objectValueTable.getNameColumn());
        optionsValueType.add("AVERAGE("+objectValueTable.getNameColumn()+")");
        optionsValueType.add("SUM("+objectValueTable.getNameColumn()+")");
        optionTypeFunction.setItems(optionsValueType);
        if(objectValueTable.getFunction()==EnumSQLColumnFunction.COUNT){
            optionTypeFunction.getSelectionModel().select(0);
        }else if(objectValueTable.getFunction()==EnumSQLColumnFunction.NORMAL){
            optionTypeFunction.getSelectionModel().select(1);
        }else if(objectValueTable.getFunction()==EnumSQLColumnFunction.AVERAGE){
            optionTypeFunction.getSelectionModel().select(2);
        }else if(objectValueTable.getFunction()==EnumSQLColumnFunction.SUM){
            optionTypeFunction.getSelectionModel().select(3);
        }
    }
    @FXML
    public void simpanAction(){
        this.valueController.saveValueChange(new ObjectValueTable(this.objectValueTable.getNameColumn(), 
                this.objectValueTable.getNameColumnHasil(), this.objectValueTable.getDataType(), 
                selectedFunction));
        kembaliAction();
    }
    @FXML
    public void kembaliAction(){
        ((Stage) kembaliButton.getScene().getWindow()).fireEvent(
                new WindowEvent(
                        ((Stage) kembaliButton.getScene().getWindow()),
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
        
    }
    
}

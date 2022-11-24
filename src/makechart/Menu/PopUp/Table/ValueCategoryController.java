/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Menu.PopUp.Table;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import makechart.Entity.EnumSQLColumnFunction;
import makechart.Entity.ObjectValueTable;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ValueCategoryController implements Initializable {

    @FXML
    private Label labelName;
    ObjectValueTable objectValueTable;
    Pane thisPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setObjectValues(Pane thisPane, ObjectValueTable objectValueTable) {
        this.thisPane = thisPane;
        if (objectValueTable != null) {
            this.objectValueTable = objectValueTable;
            if (objectValueTable.getFunction() == EnumSQLColumnFunction.NORMAL) {
                labelName.setText(objectValueTable.getNameColumn());
            } else if (objectValueTable.getFunction() == EnumSQLColumnFunction.AVERAGE) {
                labelName.setText("Average(" + objectValueTable.getNameColumn() + ")");
            } else if (objectValueTable.getFunction() == EnumSQLColumnFunction.COUNT) {
                labelName.setText("COUNT(" + objectValueTable.getNameColumn() + ")");
            } else if (objectValueTable.getFunction() == EnumSQLColumnFunction.SUM) {
                labelName.setText("SUM(" + objectValueTable.getNameColumn() + ")");
            }
        }
        this.thisPane.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    //thisPane.setStyle("-fx-border-color: #e9ff00;-fx-background-color: #ffffff");
                    OpenValueMenu();
                }
                event.consume();
            }
        });
    }

    private void OpenValueMenu() {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("EditValuesFuntion.fxml"));
            //loader.setController(new DialogController(message));
            //System.out.println("1");
            final Parent root = loader.load();

            EditValuesFuntionController addController = loader.<EditValuesFuntionController>getController();
            //System.out.println("00.2");
            addController.setControl(this, objectValueTable);
            //System.out.println("00.3");

            //System.out.println("2");
            final Scene scene = new Scene(root);
            //System.out.println("3");
            Stage stage = new Stage();
            stage.setTitle("Pengaturan nilai " + objectValueTable.getNameColumn());
            stage.initStyle(StageStyle.UTILITY);
            //System.out.println("4");
            stage.initModality(Modality.APPLICATION_MODAL);
            //System.out.println("5");
            //stage.initStyle(StageStyle.UNDECORATED);
            //System.out.println("6");
            stage.initOwner(this.thisPane.getScene().getWindow());
            //System.out.println("7");
            stage.setScene(scene);
            //System.out.println("8");
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("Satu : " + );
        }
    }

    public void saveValueChange(ObjectValueTable objectValue) {
        this.objectValueTable = objectValue;
        if (objectValueTable.getFunction() == EnumSQLColumnFunction.NORMAL) {
            labelName.setText(objectValueTable.getNameColumn());
        } else if (objectValueTable.getFunction() == EnumSQLColumnFunction.AVERAGE) {
            labelName.setText("Average(" + objectValueTable.getNameColumn() + ")");
        } else if (objectValueTable.getFunction() == EnumSQLColumnFunction.COUNT) {
            labelName.setText("COUNT(" + objectValueTable.getNameColumn() + ")");
        } else if (objectValueTable.getFunction() == EnumSQLColumnFunction.SUM) {
            labelName.setText("SUM(" + objectValueTable.getNameColumn() + ")");
        }
    }
    public ObjectValueTable getObjectValueTable(){
        return this.objectValueTable;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Menu.PopUp.Table;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import makechart.Entity.EnumTypeRecodeDouble;
import makechart.Entity.EnumTypeRecodeString;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class StringRecodePopUpController implements Initializable {

    @FXML
    private TextField dariText;
    @FXML
    private TextField hasilText;
    @FXML
    private ComboBox<String> type;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    public boolean haveOther = false;

    private EnumTypeRecodeString typeChoose = EnumTypeRecodeString.NORMAL;
    EditObjectCategoryController editObjectCategoryController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setEditObjectRecode(EditObjectCategoryController editObjectCategoryController, boolean haveOther) {
        this.editObjectCategoryController = editObjectCategoryController;
        this.haveOther = haveOther;
        if (!haveOther) {
            ObservableList<String> optionsColumn
                    = FXCollections.observableArrayList(
                            "Normal",
                            "Lainnya"
                    );
            type.setItems(optionsColumn);
        } else {
            ObservableList<String> optionsColumn
                    = FXCollections.observableArrayList(
                            "Normal"
                    );
            type.setItems(optionsColumn);
        }
        type.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.trim().equals("Normal")) {
                    typeChoose = EnumTypeRecodeString.NORMAL;
                } else if (newValue.trim().equals("Lainnya")) {
                    typeChoose = EnumTypeRecodeString.OTHER;
                    dariText.setVisible(false);
                }
            }
        });
        type.getSelectionModel().selectFirst();
    }

    @FXML
    public void saveObject() {
        if (hasilText.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Hasil tidak boleh kosong!");
            alert.showAndWait();
            return;
        }
        if (typeChoose == EnumTypeRecodeString.NORMAL) {
            if (dariText.getText().trim().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Hasil tidak boleh kosong!");
                alert.showAndWait();
                return;
            } else {
                this.editObjectCategoryController.addItemStringRecode(typeChoose, dariText.getText().trim(), hasilText.getText().trim());
                cancelOptionAction();
            }
        } else if (typeChoose == EnumTypeRecodeString.OTHER) {
            this.editObjectCategoryController.addItemStringRecode(typeChoose, "", hasilText.getText().trim());
            cancelOptionAction();
        }
    }

    @FXML
    public void cancelOptionAction() {
        ((Stage) cancelButton.getScene().getWindow()).fireEvent(
                new WindowEvent(
                        ((Stage) cancelButton.getScene().getWindow()),
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
    }
}

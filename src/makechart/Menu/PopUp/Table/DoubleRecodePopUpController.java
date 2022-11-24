/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Menu.PopUp.Table;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import makechart.Entity.EnumTypeRecodeDouble;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DoubleRecodePopUpController implements Initializable {

    @FXML
    private TextField batasBawah;
    @FXML
    private TextField batasAtas;
    @FXML
    private TextField hasil;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    EditObjectCategoryController editObjectCategoryController;

    public boolean haveFirst = false;
    public boolean haveLast = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {

                if (t.isReplaced()) {
                    if (t.getText().matches("[^0-9]")) {
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                    }
                }

                if (t.isAdded()) {
                    if (t.getControlText().contains(".")) {
                        if (t.getText().matches("[^0-9]")) {
                            t.setText("");
                        }
                    } else if (t.getText().matches("[^0-9.]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };

        batasAtas.setTextFormatter(new TextFormatter<>(filter));
        batasBawah.setTextFormatter(new TextFormatter<>(filter));

    }

    public void setEditObjectRecode(EditObjectCategoryController editObjectCategoryController, boolean haveFirst, boolean haveLast) {
        this.editObjectCategoryController = editObjectCategoryController;
        this.haveFirst = haveFirst;
        this.haveLast = haveLast;
    }

    @FXML
    public void saveObject() {
        if (hasil.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Hasil tidak boleh kosong!");
            alert.showAndWait();
            return;
        }
        if (haveFirst && batasBawah.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Batas bawah tidak boleh kosong lagi!");
            alert.showAndWait();
            return;
        }
        if (haveLast && batasAtas.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Batas atas tidak boleh kosong lagi!");
            alert.showAndWait();
            return;
        }
        if (batasAtas.getText().trim().equals("") && batasBawah.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Batas bawah, batas akhir tidak boleh kosong!");
            alert.showAndWait();
        }
        if (!batasBawah.getText().trim().equals("") && !batasAtas.getText().trim().equals("")) {
            this.editObjectCategoryController.addItemDoubleRecode(EnumTypeRecodeDouble.RECODE_MIDDLE,
                    Double.valueOf(batasBawah.getText().trim()),
                    Double.valueOf(batasAtas.getText().trim()),
                    hasil.getText().trim());
            cancelOptionAction();
        } else if (batasBawah.getText().trim().equals("")) {
            this.editObjectCategoryController.addItemDoubleRecode(EnumTypeRecodeDouble.RECODE_FIRST,
                    0,
                    Double.valueOf(batasAtas.getText().trim()),
                    hasil.getText().trim());
            cancelOptionAction();
        } else if (batasAtas.getText().trim().equals("")) {
            this.editObjectCategoryController.addItemDoubleRecode(EnumTypeRecodeDouble.RECODE_LAST,
                    Double.valueOf(batasBawah.getText().trim()),
                    0,
                    hasil.getText().trim());
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

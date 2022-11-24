/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Menu.PopUp.Table;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import makechart.Entity.EnumDataType;
import makechart.Entity.EnumSQLRowFunction;
import makechart.Entity.ObjectDataSource;
import makechart.Entity.ObjectInformationColumnTable;
import makechart.Menu.PopUp.AddUpdateTableController;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AddFilterController implements Initializable {

    @FXML
    private Button simpanButton;
    @FXML
    private Button kembaliButton;
    @FXML
    private ComboBox<ObjectInformationColumnTable> var;
    @FXML
    private ComboBox<String> tanda;
    @FXML
    private TextField pembanding;

    ObservableList<String> optionsTanda
            = FXCollections.observableArrayList(
                    "=",
                    "!=",
                    "<",
                    "<=",
                    ">",
                    ">="
            );
    AddUpdateTableController addUpdateController;

    EnumSQLRowFunction enumTanda = EnumSQLRowFunction.EQUAL;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tanda.setItems(optionsTanda);
        tanda.getSelectionModel().selectFirst();
        tanda.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().equals("=")) {
                enumTanda = EnumSQLRowFunction.EQUAL;
            } else if (newValue.trim().equals("!=")) {
                enumTanda = EnumSQLRowFunction.NOT_EQUAL;
            } else if (newValue.trim().equals("<")) {
                enumTanda = EnumSQLRowFunction.LESS_THAN;
            } else if (newValue.trim().equals("<=")) {
                enumTanda = EnumSQLRowFunction.LESS_THAN_OR_EQUAL;
            } else if (newValue.trim().equals(">")) {
                enumTanda = EnumSQLRowFunction.MORE_THAN;
            } else if (newValue.trim().equals(">=")) {
                enumTanda = EnumSQLRowFunction.MORE_THAN_OR_EQUAL;
            }
        });
        var.setCellFactory((param) -> {
            final ListCell<ObjectInformationColumnTable> cell = new ListCell<ObjectInformationColumnTable>() {

                @Override
                protected void updateItem(ObjectInformationColumnTable t, boolean bln) {
                    super.updateItem(t, bln);

                    if (t != null) {
                        setText(t.getColumnName());
                    } else {
                        setText(null);
                    }
                }

            };
            cell.setContentDisplay(ContentDisplay.TEXT_ONLY);

            return cell;
        });
        var.setButtonCell(new ListCell<ObjectInformationColumnTable>() {
            @Override
            protected void updateItem(ObjectInformationColumnTable item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getColumnName());
                }
            }
        });
        var.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getDataType() == EnumDataType.INTEGER || newValue.getDataType() == EnumDataType.REAL) {
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

                pembanding.setTextFormatter(new TextFormatter<>(filter));
            } else {
                pembanding.setTextFormatter(null);

            }
        });
    }

    public void setVar(AddUpdateTableController addUpdateController, ObservableList<ObjectInformationColumnTable> optionsVar) {
        this.addUpdateController = addUpdateController;
        var.setItems(optionsVar);
        var.getSelectionModel().selectFirst();
    }
    
    @FXML
    public void simpanAction(){
        this.addUpdateController.addFilterOptionFromAddFilter(var.getValue(), enumTanda, pembanding.getText());
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

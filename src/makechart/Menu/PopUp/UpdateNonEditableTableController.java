/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Menu.PopUp;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import makechart.Database.database;
import makechart.Database.databaseHelper;
import makechart.Entity.ObjectDataSource;
import makechart.Entity.ObjectTable;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class UpdateNonEditableTableController implements Initializable {

    @FXML
    private Label jenis;
    @FXML
    private Label nama;
    @FXML
    private Button simpan;
    @FXML
    private Button kembali;

    @FXML
    private ComboBox<ObjectDataSource> comboDataSource;
    ObservableList<ObjectDataSource> optionsDataSource
            = FXCollections.observableArrayList(new ArrayList<ObjectDataSource>());
    private ObjectTable objectTable;
    
    private static final String FolderMakeTable = System.getenv("APPDATA") + "\\MakeChart\\MakeTable";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comboDataSource.setItems(optionsDataSource);
        comboDataSource.setCellFactory((param) -> {
            final ListCell<ObjectDataSource> cell = new ListCell<ObjectDataSource>() {
                @Override
                protected void updateItem(ObjectDataSource t, boolean bln) {
                    super.updateItem(t, bln);

                    if (t != null) {
                        setText(t.getName());
                    } else {
                        setText(null);
                    }
                }
            };
            cell.setContentDisplay(ContentDisplay.TEXT_ONLY);

            return cell;
        });
        comboDataSource.setButtonCell(new ListCell<ObjectDataSource>() {
            @Override
            protected void updateItem(ObjectDataSource item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        comboDataSource.getSelectionModel().selectFirst();

    }

    public void setObjecetTable(ObjectTable objectTable) {
        this.objectTable = objectTable;
        optionsDataSource.clear();
        jenis.setText("Tabel : ");
        nama.setText(objectTable.getName());
        database dBase = new database();
        ArrayList<ObjectDataSource> dataSource = dBase.getObjectDataSources();
        if (dataSource == null) {
            return;
        } else if (dataSource.size() == 0) {
            return;
        }
        for (ObjectDataSource item : dataSource) {
            database itemDbase = new database();
            ArrayList<String> column = itemDbase.getColumnsInTable(item);
            if (objectTable.isDataSourceMemenuhi(column)) {
                optionsDataSource.add(item);
            }
        }
        comboDataSource.setItems(optionsDataSource);
        comboDataSource.getSelectionModel().selectFirst();
    }

    @FXML
    public void simpanAction() {
        if(optionsDataSource.size()>0){
            ObjectDataSource selected = comboDataSource.getValue();
            objectTable.setSourceData(selected.getUid());
            updateTable(objectTable);
        }
        ((Stage) simpan.getScene().getWindow()).fireEvent(
                new WindowEvent(
                        ((Stage) simpan.getScene().getWindow()),
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
    }

    @FXML
    public void kembaliAction() {
        ((Stage) kembali.getScene().getWindow()).fireEvent(
                new WindowEvent(
                        ((Stage) kembali.getScene().getWindow()),
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
    }
    public boolean updateTable(ObjectTable objectTable) {
        try {
            FileOutputStream outputStream = new FileOutputStream(FolderMakeTable + "\\" + objectTable.getUid() + "-update.json");
            byte[] strToBytes = objectTable.getJSONObject().toString().getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
            (new File(FolderMakeTable + "\\" + objectTable.getUid() + ".json")).delete();
            (new File(FolderMakeTable + "\\" + objectTable.getUid() + "-update.json")).renameTo(new File(FolderMakeTable + "\\" + objectTable.getUid() + ".json"));
            return true;
        } catch (Exception ex) {
            try {
                (new File(FolderMakeTable + "\\" + objectTable.getUid() + "-update.json")).delete();
            } catch (Exception exx) {

            }
            return false;
        }
    }
}

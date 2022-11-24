/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Menu;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import makechart.Database.database;
import makechart.Database.databaseHelper;
import makechart.Entity.ObjectDataSource;
import makechart.Entity.ObjectTable;
import makechart.Entity.TableResult;
import makechart.Entity.TableResultHasil;
import makechart.Menu.PopUp.AddUpdateTableController;
import makechart.Menu.PopUp.UpdateNonEditableTableController;
import makechart.Process.MakeExcell;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class MakeTableMenuController implements Initializable {

    private boolean alreadyHaveProgress;

    @FXML
    private Button openAddMakeTable;
    @FXML
    private TableView obTableView;
    @FXML
    private Button importButton;

    private ObservableList<ObjectTable> data
            = FXCollections.observableArrayList(new ArrayList<ObjectTable>());

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        alreadyHaveProgress = false;

        database dBase = new database();
        ArrayList<ObjectDataSource> dataB = dBase.getObjectDataSources();
        if (dataB == null) {
            openAddMakeTable.setVisible(false);
        } else if (dataB.size() == 0) {
            openAddMakeTable.setVisible(false);
        } else {
            openAddMakeTable.setVisible(true);
        }

        TableColumn<ObjectTable, String> no = new TableColumn<ObjectTable, String>("No");
        TableColumn<ObjectTable, String> name = new TableColumn<ObjectTable, String>("Name");
        TableColumn<ObjectTable, String> desc = new TableColumn<ObjectTable, String>("Deskripsi");
        TableColumn<ObjectTable, Boolean> updateAction = new TableColumn<ObjectTable, Boolean>("Ubah");
        TableColumn<ObjectTable, Boolean> delAction = new TableColumn<ObjectTable, Boolean>("Hapus");
        TableColumn<ObjectTable, Boolean> exportAction = new TableColumn<ObjectTable, Boolean>("Export");
        TableColumn<ObjectTable, Boolean> runAction = new TableColumn<ObjectTable, Boolean>("Run");

        no.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObjectTable, String> param) {
                return new ReadOnlyObjectWrapper("" + (data.indexOf(param.getValue()) + 1));
            }
        });
        name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObjectTable, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getName());
            }
        });
        desc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObjectTable, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getDesc());
            }
        });
        updateAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectTable, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ObjectTable, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue() != null);
            }
        });
        updateAction.setCellFactory(new Callback<TableColumn<ObjectTable, Boolean>, TableCell<ObjectTable, Boolean>>() {
            @Override
            public TableCell<ObjectTable, Boolean> call(TableColumn<ObjectTable, Boolean> param) {
                return new ButtonCellUpdate();
            }
        });
        delAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectTable, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ObjectTable, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue() != null);
            }
        });
        delAction.setCellFactory(new Callback<TableColumn<ObjectTable, Boolean>, TableCell<ObjectTable, Boolean>>() {
            @Override
            public TableCell<ObjectTable, Boolean> call(TableColumn<ObjectTable, Boolean> param) {
                return new ButtonCellDelete();
            }
        });
        exportAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectTable, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ObjectTable, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue() != null);
            }
        });
        exportAction.setCellFactory(new Callback<TableColumn<ObjectTable, Boolean>, TableCell<ObjectTable, Boolean>>() {
            @Override
            public TableCell<ObjectTable, Boolean> call(TableColumn<ObjectTable, Boolean> param) {
                return new ButtonCellExport();
            }
        });
        runAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectTable, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ObjectTable, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue() != null);
            }
        });
        runAction.setCellFactory(new Callback<TableColumn<ObjectTable, Boolean>, TableCell<ObjectTable, Boolean>>() {
            @Override
            public TableCell<ObjectTable, Boolean> call(TableColumn<ObjectTable, Boolean> param) {
                return new ButtonCellRun();
            }
        });

        obTableView.getColumns().add(no);
        obTableView.getColumns().add(name);
        obTableView.getColumns().add(desc);
        obTableView.getColumns().add(updateAction);
        obTableView.getColumns().add(delAction);
        obTableView.getColumns().add(exportAction);
        obTableView.getColumns().add(runAction);
        obTableView.setItems(data);
        refreshTable();
    }

    public boolean alreadyHaveProgress() {
        return alreadyHaveProgress;
    }

    public void refreshTable() {
        database dBase = new database();
        ArrayList<ObjectTable> dataB = dBase.getObjectTables();
        System.out.println("refresh table");
        if (dataB != null) {
            data = FXCollections.observableArrayList(new ArrayList<ObjectTable>());
            data.addAll(dataB);
            obTableView.refresh();
            obTableView.setItems(data);
        }
    }

    @FXML
    private void openAddTable() {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("PopUp/AddUpdateTable.fxml"));
            //loader.setController(new DialogController(message));
            //System.out.println("1");
            final Parent root = loader.load();

            AddUpdateTableController addController = loader.<AddUpdateTableController>getController();
            System.out.println("00.2");
            addController.setObjectTable(null);
            System.out.println("00.3");

            //System.out.println("2");
            final Scene scene = new Scene(root);
            //System.out.println("3");
            Stage stage = new Stage();
            stage.setTitle("Tambah Tabel");
            stage.initStyle(StageStyle.UTILITY);
            //System.out.println("4");
            stage.initModality(Modality.APPLICATION_MODAL);
            //System.out.println("5");
            //stage.initStyle(StageStyle.UNDECORATED);
            //System.out.println("6");
            stage.initOwner(openAddMakeTable.getScene().getWindow());
            //System.out.println("7");
            stage.setScene(scene);
            //System.out.println("8");
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    System.out.println("close sss");
                    refreshTable();
                }
            });
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("Satu : " + );
        }

    }

    @FXML
    public void openImport() {
        Stage openStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Imported File");
        List<String> acceptedExt = new ArrayList<>();
        acceptedExt.add("*.json");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON File", acceptedExt));
        File file = fileChooser.showOpenDialog(openStage);

        if (file != null) {
            try {
                ObjectTable objectTable = new ObjectTable();
                String content = FileUtils.readFileToString(file, "utf-8");
                JSONObject jsonTarget = new JSONObject(content);
                objectTable.setJsonObject(jsonTarget);
                objectTable.setUid("table_" + UUID.randomUUID());
                database dBase = new database();
                dBase.insertTable(objectTable);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            refreshTable();
        }
    }

    private void openUpdateTable(ObjectTable objectTable) {
        try {
            databaseHelper.completeObjectTable(objectTable);
            if (objectTable.isEditable()) {
                final FXMLLoader loader = new FXMLLoader(getClass().getResource("PopUp/AddUpdateTable.fxml"));
                final Parent root = loader.load();

                AddUpdateTableController addController = loader.<AddUpdateTableController>getController();
                addController.setObjectTable(objectTable);
                final Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Perbarui Tabel");
                stage.initStyle(StageStyle.UTILITY);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(openAddMakeTable.getScene().getWindow());
                //System.out.println("7");
                stage.setScene(scene);
                //System.out.println("8");
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        refreshTable();
                    }
                });
                stage.show();
            } else {
                final FXMLLoader loader = new FXMLLoader(getClass().getResource("PopUp/UpdateNonEditableTable.fxml"));
                final Parent root = loader.load();

                UpdateNonEditableTableController addController = loader.<UpdateNonEditableTableController>getController();
                addController.setObjecetTable(objectTable);
                final Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Perbarui Tabel");
                stage.initStyle(StageStyle.UTILITY);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(openAddMakeTable.getScene().getWindow());
                //System.out.println("7");
                stage.setScene(scene);
                //System.out.println("8");
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        refreshTable();
                    }
                });
                stage.show();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("Satu : " + );
        }

    }

    private class ButtonCellDelete extends TableCell<ObjectTable, Boolean> {

        final Button cellButton = new Button("Hapus");

        ButtonCellDelete() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    ObjectTable currentPerson = (ObjectTable) ButtonCellDelete.this.getTableView().getItems().get(ButtonCellDelete.this.getIndex());
                    //remove selected item from the table list
                    database dBase = new database();
                    dBase.deleteTable(currentPerson);
                    refreshTable();
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }

    }

    private class ButtonCellUpdate extends TableCell<ObjectTable, Boolean> {

        final Button cellButton = new Button("Ubah");

        ButtonCellUpdate() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    ObjectTable currentPerson = (ObjectTable) ButtonCellUpdate.this.getTableView().getItems().get(ButtonCellUpdate.this.getIndex());
                    //remove selected item from the table list
                    openUpdateTable(currentPerson);
                    refreshTable();
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }

    private class ButtonCellExport extends TableCell<ObjectTable, Boolean> {

        final Button cellButton = new Button("Export");

        ButtonCellExport() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {

                    ObjectTable currentPerson = (ObjectTable) ButtonCellExport.this.getTableView().getItems().get(ButtonCellExport.this.getIndex());
                    databaseHelper.completeObjectTable(currentPerson);

                    Stage saveStage = new Stage();
                    FileChooser fileChooser = new FileChooser();

                    //Set extension filter
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON File", "*.json");
                    fileChooser.getExtensionFilters().add(extFilter);

                    //Show save file dialog
                    File file = fileChooser.showSaveDialog(saveStage);

                    if (file != null) {
                        try {
                            FileOutputStream outputStream = new FileOutputStream(file);
                            byte[] strToBytes = currentPerson.getExportJSONObject().toString().getBytes();
                            outputStream.write(strToBytes);
                            outputStream.close();
                        } catch (Exception ex) {
                            System.out.println("Error export");
                            ex.printStackTrace();
                        }
                    }
                    refreshTable();
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }

    private class ButtonCellRun extends TableCell<ObjectTable, Boolean> {

        final Button cellButton = new Button("Run");

        ButtonCellRun() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    try {
                        ObjectTable currentPerson = (ObjectTable) ButtonCellRun.this.getTableView().getItems().get(ButtonCellRun.this.getIndex());
                        databaseHelper.completeObjectTable(currentPerson);
                        if (currentPerson.getSourceData() == null) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText("Sumber data kosong!");

                            alert.showAndWait();
                            return;
                        }
                        if (currentPerson.getSourceData().trim().equals("")) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText("Sumber data kosong!");

                            alert.showAndWait();
                            return;
                        }
                        ArrayList<TableResult> tableResults = currentPerson.getTableResults();
                        TableResultHasil tableResultHasil = new TableResultHasil();
                        tableResultHasil.setObjectTable(currentPerson);
                        for (TableResult item : tableResults) {
                            System.out.println("" + currentPerson.getUid());
                            item.setObjectTable(currentPerson);
                            database dBase = new database();
                            item.doQuery(dBase.getConnection("jdbc:sqlite:" + System.getenv("APPDATA")
                                    + "\\MakeChart\\DataSource" + "\\" + currentPerson.getSourceData() + ".db"));
                            System.out.println("Hasil => " + item.getHasil().toString());
                            tableResultHasil.getTableResults().add(item);
                            tableResultHasil.getArrays().add(item.getHasil());
                            item.processAll();
                        }
                        MakeExcell makeExcell = new MakeExcell();
                        makeExcell.doProcess(tableResultHasil);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        refreshTable();
                    }
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }
}

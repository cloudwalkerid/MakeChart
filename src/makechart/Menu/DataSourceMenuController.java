/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Menu;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import makechart.Database.database;
import makechart.Database.databaseHelper;
import makechart.Entity.ObjectDataSource;
import makechart.Entity.ObjectTable;
import makechart.Menu.PopUp.AddUpdateTableController;
import makechart.Menu.PopUp.LookDataSourceController;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DataSourceMenuController implements Initializable {

    @FXML
    private Button openAddDataSourceButton;
    @FXML
    private TableView obTableView;

    private ObservableList<ObjectDataSource> data
            = FXCollections.observableArrayList(new ArrayList<ObjectDataSource>());

    private boolean alreadyHaveProgress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        alreadyHaveProgress = false;
        TableColumn<ObjectDataSource, String> no = new TableColumn<ObjectDataSource, String>("No");
        TableColumn<ObjectDataSource, String> name = new TableColumn<ObjectDataSource, String>("Name");
        TableColumn<ObjectDataSource, String> desc = new TableColumn<ObjectDataSource, String>("Deskripsi");
        TableColumn<ObjectDataSource, Boolean> lihatAction = new TableColumn<ObjectDataSource, Boolean>("Lihat");
        TableColumn<ObjectDataSource, Boolean> delAction = new TableColumn<ObjectDataSource, Boolean>("Hapus");

        no.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectDataSource, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObjectDataSource, String> param) {
                return new ReadOnlyObjectWrapper("" + (data.indexOf(param.getValue()) + 1));
            }
        });
        name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectDataSource, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObjectDataSource, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getName());
            }
        });
        desc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectDataSource, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObjectDataSource, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getDesc());
            }
        });
        delAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectDataSource, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ObjectDataSource, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue() != null);
            }
        });
        delAction.setCellFactory(new Callback<TableColumn<ObjectDataSource, Boolean>, TableCell<ObjectDataSource, Boolean>>() {
            @Override
            public TableCell<ObjectDataSource, Boolean> call(TableColumn<ObjectDataSource, Boolean> param) {
                return new ButtonCellDelete();
            }
        });
        lihatAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectDataSource, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ObjectDataSource, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue() != null);
            }
        });
        lihatAction.setCellFactory(new Callback<TableColumn<ObjectDataSource, Boolean>, TableCell<ObjectDataSource, Boolean>>() {
            @Override
            public TableCell<ObjectDataSource, Boolean> call(TableColumn<ObjectDataSource, Boolean> param) {
                return new ButtonCellLihat();
            }
        });
        obTableView.getColumns().add(no);
        obTableView.getColumns().add(name);
        obTableView.getColumns().add(desc);
        obTableView.getColumns().add(lihatAction);
        obTableView.getColumns().add(delAction);
        obTableView.setItems(data);
        refreshTable();
    }

    public boolean alreadyHaveProgress() {
        return alreadyHaveProgress;
    }

    @FXML
    private void openAddDataSource() {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("PopUp/AddDataSource.fxml"));
            //loader.setController(new DialogController(message));
            //System.out.println("1");
            final Parent root = loader.load();
            //System.out.println("2");
            final Scene scene = new Scene(root);
            //System.out.println("3");
            Stage stage = new Stage();
            stage.setTitle("Tambah DBF");
            stage.initStyle(StageStyle.UTILITY);
            //System.out.println("4");
            stage.initModality(Modality.APPLICATION_MODAL);
            //System.out.println("5");
            //stage.initStyle(StageStyle.UNDECORATED);
            //System.out.println("6");
            stage.initOwner(openAddDataSourceButton.getScene().getWindow());
            //System.out.println("7");
            stage.setScene(scene);
            //System.out.println("8");
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    refreshTable();
                }
            });

        } catch (Exception ex) {
            System.out.println("Satu : " + ex);
        }

    }

    public void refreshTable() {
        database dBase = new database();
        ArrayList<ObjectDataSource> dataB = dBase.getObjectDataSources();
        System.out.println("refresh table");
        if (dataB != null) {
            data = FXCollections.observableArrayList(new ArrayList<ObjectDataSource>());
            data.addAll(dataB);
            obTableView.refresh();
            obTableView.setItems(data);
        }
    }

    private class ButtonCellLihat extends TableCell<ObjectDataSource, Boolean> {

        final Button cellButton = new Button("Lihat");

        ButtonCellLihat() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    ObjectDataSource currentPerson = (ObjectDataSource) ButtonCellLihat.this.getTableView().getItems().get(ButtonCellLihat.this.getIndex());
                    //remove selected item from the table list
                    openLookUp(currentPerson);
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

    private class ButtonCellDelete extends TableCell<ObjectDataSource, Boolean> {

        final Button cellButton = new Button("Hapus");

        ButtonCellDelete() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    ObjectDataSource currentPerson = (ObjectDataSource) ButtonCellDelete.this.getTableView().getItems().get(ButtonCellDelete.this.getIndex());
                    //remove selected item from the table list
                    database dBase = new database();
                    dBase.deleteDataSource(currentPerson);
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

    private void openLookUp(ObjectDataSource objectDataSource) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("PopUp/LookDataSource.fxml"));
            //loader.setController(new DialogController(message));
            //System.out.println("1");
            final Parent root = loader.load();

            LookDataSourceController lookController = loader.<LookDataSourceController>getController();
            lookController.setObjectDataSource(objectDataSource);

            //System.out.println("2");
            final Scene scene = new Scene(root);
            //System.out.println("3");
            Stage stage = new Stage();
            stage.setTitle("Lihat Tabel");
            stage.initStyle(StageStyle.UTILITY);
            //System.out.println("4");
            stage.initModality(Modality.APPLICATION_MODAL);
            //System.out.println("5");
            //stage.initStyle(StageStyle.UNDECORATED);
            //System.out.println("6");
            stage.initOwner(obTableView.getScene().getWindow());
            //System.out.println("7");
            stage.setScene(scene);
            //System.out.println("8");
//            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//                public void handle(WindowEvent we) {
//                    refreshTable();
//                }
//            });
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("Satu : " + );
        }

    }
}

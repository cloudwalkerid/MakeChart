/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Menu.PopUp.Table;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import makechart.Database.database;
import makechart.Entity.EnumCategoryTable;
import makechart.Entity.EnumDataType;
import makechart.Entity.EnumSQLColumnFunction;
import makechart.Entity.EnumTypeRecode;
import makechart.Entity.EnumTypeRecodeDouble;
import makechart.Entity.EnumTypeRecodeString;
import makechart.Entity.ObjectCategory;
import makechart.Entity.ObjectCategoryRecode;
import makechart.Entity.ObjectCategorySimple;
import makechart.Entity.ObjectDataSource;
import makechart.Entity.ObjectInformationColumnTable;
import makechart.Entity.ObjectItemRecodeDouble;
import makechart.Entity.ObjectItemRecodeString;
import makechart.Menu.PopUp.AddUpdateTableController;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class EditObjectCategoryController implements Initializable {

    private ObjectCategoryColumnController columnController;
    private ObjectCategoryRowController rowController;

    @FXML
    private TextField labelNormal;
    @FXML
    private TextField labelPercentase;
    @FXML
    private ComboBox<String> tipeColumn;
    @FXML
    private ComboBox<String> tipeRecode;
    @FXML
    private Pane tablePanel;
    @FXML
    private Button tambahItem;
    @FXML
    private Button simpanButton;
    @FXML
    private Button kembaliButton;
    
    boolean isRow;

    private TableView<ObjectItemRecodeDouble> tabelRecodeDouble;
    private TableView<itemRecodeString> tabelRecodeString;

    private ObjectInformationColumnTable columnTable;

    ObservableList<String> optionsColumn
            = FXCollections.observableArrayList(
                    "Normal",
                    "Persentase"
            );
    ObservableList<String> optionsColumnTerbatas
            = FXCollections.observableArrayList(
                    "Normal"
            );
    ObservableList<String> optionsRecode
            = FXCollections.observableArrayList(
                    "Tanpa Recode",
                    "Recode Range",
                    "Recode String"
            );
    ObservableList<ObjectItemRecodeDouble> tableRecodeDoubleData
            = FXCollections.observableArrayList(
                    new ArrayList<ObjectItemRecodeDouble>()
            );
    ObservableList<itemRecodeString> tableRecodeStringData
            = FXCollections.observableArrayList(
                    new ArrayList<itemRecodeString>()
            );

    boolean haveStringOther = false;
    boolean haveDoubleFirst = false;
    boolean haveDoubleLast = false;
    private int jenisRecode = 0;
    private int jenisLabel = 0;
    private ObjectCategory objectCategory;
    private AddUpdateTableController addUpdateTableController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tipeColumn.setItems(optionsColumn);
        tipeRecode.setItems(optionsRecode);
        labelPercentase.setVisible(false);
        tipeColumn.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.trim().equals("Normal")) {
                    jenisLabel = 0;
                    labelNormal.setVisible(true);
                    labelPercentase.setVisible(false);
                    System.out.println("masuk");
                } else if (newValue.trim().equals("Persentase")) {
                    jenisLabel = 1;
                    labelNormal.setVisible(false);
                    labelPercentase.setVisible(true);
                    System.out.println("masuk");
                } else if (newValue.trim().equals("Normal Dan Persentase")) {
                    jenisLabel = 2;
                    labelNormal.setVisible(true);
                    labelPercentase.setVisible(true);
                    System.out.println("masuk");
                }
            }
        });
        tipeRecode.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.trim().equals("Tanpa Recode")) {
                    tablePanel.getChildren().clear();
                    tambahItem.setVisible(false);
                    jenisRecode = 0;
                } else if (newValue.trim().equals("Recode Range")) {
                    if (!oldValue.trim().equals(newValue.trim())) {
                        tablePanel.getChildren().clear();
                        setTableToDouble();
                    }
                    tambahItem.setVisible(true);
                    jenisRecode = 1;
                } else if (newValue.trim().equals("Recode String")) {
                    if (!oldValue.trim().equals(newValue.trim())) {
                        tablePanel.getChildren().clear();
                        setTableToString();
                    }
                    jenisRecode = 2;
                    tambahItem.setVisible(true);
                }
            }
        });
        tipeColumn.getSelectionModel().selectFirst();
        tipeRecode.getSelectionModel().selectFirst();
    }

    public void setColumnController(ObjectInformationColumnTable columnTable, ObjectCategory objectCategory,
            ObjectCategoryColumnController columnController, boolean isHead, AddUpdateTableController addUpdateTableController) {
        this.columnTable = columnTable;
        this.columnController = columnController;
        this.addUpdateTableController = addUpdateTableController;
        isRow = false;
        loadData(objectCategory, isHead);
    }

    public void setRowController(ObjectInformationColumnTable columnTable, ObjectCategory objectCategory,
            ObjectCategoryRowController rowController, boolean isHead, AddUpdateTableController addUpdateTableController) {
        this.columnTable = columnTable;
        this.rowController = rowController;
        this.addUpdateTableController = addUpdateTableController;
        isRow = true;
        loadData(objectCategory, isHead);
    }

    public void loadData(ObjectCategory objectLocal, boolean isHead) {
        if (objectLocal != null) {
            if (objectLocal.getJenisLabel() == 0) {
                System.out.println("is Head : "+isHead);
                //System.out.println("have percentage : "+this.addUpdateTableController.havePercentage());
                if (isHead && !this.addUpdateTableController.canHavePercentage(isRow)) {
                    tipeColumn.setItems(optionsColumn);
                } else {
                    tipeColumn.setItems(optionsColumnTerbatas);
                }
            } else {
                if (isHead) {
                    tipeColumn.setItems(optionsColumn);
                } else {
                    tipeColumn.setItems(optionsColumnTerbatas);
                }
            }
            jenisLabel = objectLocal.getJenisLabel();
            jenisRecode = objectLocal.getJenisRecode();
            if (objectLocal.getJenisLabel() == 0) {
                tipeColumn.getSelectionModel().select("Normal");
                labelNormal.setText(objectLocal.getLabelValue());
                labelPercentase.setText(objectLocal.getLabelPercentage());
            } else if (objectLocal.getJenisLabel() == 1) {
                tipeColumn.getSelectionModel().select("Persentase");
                labelNormal.setText(objectLocal.getLabelValue());
                labelPercentase.setText(objectLocal.getLabelPercentage());
            } else if (objectLocal.getJenisLabel() == 2) {
                tipeColumn.getSelectionModel().select("Normal Dan Persentase");
                labelNormal.setText(objectLocal.getLabelValue());
                labelPercentase.setText(objectLocal.getLabelPercentage());
            }
            if (objectLocal.getJenisRecode() == 0) {
                tipeRecode.getSelectionModel().select("Tanpa Recode");
            } else if (objectLocal.getJenisRecode() == 1) {
                System.out.println("sd " + ((ObjectCategoryRecode) objectLocal).getObjectItemRecodeDoubles());
                tipeRecode.getSelectionModel().select("Redode Range");
                tableRecodeDoubleData.clear();
                tableRecodeDoubleData.addAll(((ObjectCategoryRecode) objectLocal).getObjectItemRecodeDoubles());
                setTableToDouble();
            } else if (objectLocal.getJenisRecode() == 2) {
                tipeRecode.getSelectionModel().select("Recode String");
                tableRecodeStringData.clear();
                //System.out.println("Banyak Load " + ((ObjectCategoryRecode) objectLocal).getObjectItemRecodeStrings().size());
                for (ObjectItemRecodeString itemString : ((ObjectCategoryRecode) objectLocal).getObjectItemRecodeStrings()) {
                    if (itemString.getType() == EnumTypeRecodeString.NORMAL) {
                        for (String itemDari : itemString.getDaris()) {
                            tableRecodeStringData.add(new itemRecodeString(itemString.getType(), itemDari, itemString.getHasil()));
                        }
                    } else if (itemString.getType() == EnumTypeRecodeString.OTHER) {
                        tableRecodeStringData.add(new itemRecodeString(EnumTypeRecodeString.OTHER, "", itemString.getHasil()));
                    }
                }
                setTableToString();
            }
        }
//        objectCategory = null;
        this.objectCategory = objectLocal;
    }

    public void setTableToDouble() {
        tabelRecodeDouble = new TableView<ObjectItemRecodeDouble>();
        tabelRecodeDouble.setMinSize(tablePanel.getMinWidth(), tablePanel.getMinHeight());
        tabelRecodeDouble.setPrefSize(tablePanel.getPrefWidth(), tablePanel.getPrefHeight());
        tabelRecodeDouble.setMaxSize(tablePanel.getMaxWidth(), tablePanel.getMaxHeight());

        TableColumn<ObjectItemRecodeDouble, Double> bawah = new TableColumn<ObjectItemRecodeDouble, Double>("Bawah");
        TableColumn<ObjectItemRecodeDouble, String> tanda = new TableColumn<ObjectItemRecodeDouble, String>("-");
        TableColumn<ObjectItemRecodeDouble, Double> atas = new TableColumn<ObjectItemRecodeDouble, Double>("Atas");
        TableColumn<ObjectItemRecodeDouble, String> hasil = new TableColumn<ObjectItemRecodeDouble, String>("Hasil");
        TableColumn<ObjectItemRecodeDouble, Boolean> delAction = new TableColumn<ObjectItemRecodeDouble, Boolean>("Hapus");

        bawah.setCellValueFactory((param) -> {
            ObjectItemRecodeDouble item = param.getValue();
            if (item.getType() == EnumTypeRecodeDouble.RECODE_FIRST) {
                return new ReadOnlyObjectWrapper("");
            } else if (item.getType() == EnumTypeRecodeDouble.RECODE_MIDDLE) {
                return new ReadOnlyObjectWrapper(item.getBatasBawah());
            } else if (item.getType() == EnumTypeRecodeDouble.RECODE_LAST) {
                return new ReadOnlyObjectWrapper(item.getBatasBawah());
            } else {
                return new ReadOnlyObjectWrapper("");
            }
        });
        tanda.setCellValueFactory((param) -> {
            ObjectItemRecodeDouble item = param.getValue();
            if (item.getType() == EnumTypeRecodeDouble.RECODE_FIRST) {
                return new ReadOnlyObjectWrapper(columnTable.getColumnName() + " <= ");
            } else if (item.getType() == EnumTypeRecodeDouble.RECODE_MIDDLE) {
                return new ReadOnlyObjectWrapper(" < " + columnTable.getColumnName() + " <= ");
            } else if (item.getType() == EnumTypeRecodeDouble.RECODE_LAST) {
                return new ReadOnlyObjectWrapper(" < " + columnTable.getColumnName());
            } else {
                return new ReadOnlyObjectWrapper("");
            }
        });
        atas.setCellValueFactory((param) -> {
            ObjectItemRecodeDouble item = param.getValue();
            if (item.getType() == EnumTypeRecodeDouble.RECODE_FIRST) {
                return new ReadOnlyObjectWrapper(item.getBatasAtas());
            } else if (item.getType() == EnumTypeRecodeDouble.RECODE_MIDDLE) {
                return new ReadOnlyObjectWrapper(item.getBatasAtas());
            } else if (item.getType() == EnumTypeRecodeDouble.RECODE_LAST) {
                return new ReadOnlyObjectWrapper("");
            } else {
                return new ReadOnlyObjectWrapper("");
            }
        });
        hasil.setCellValueFactory((param) -> {
            return new ReadOnlyObjectWrapper(param.getValue().getHasil());
        });
        delAction.setCellValueFactory((param) -> {
            return new SimpleBooleanProperty(param.getValue() != null);
        });
        delAction.setCellFactory((param) -> {
            return new ButtonDoubleCell();
        });

        tabelRecodeDouble.getColumns().add(bawah);
        tabelRecodeDouble.getColumns().add(tanda);
        tabelRecodeDouble.getColumns().add(atas);
        tabelRecodeDouble.getColumns().add(hasil);
        tabelRecodeDouble.getColumns().add(delAction);
        tabelRecodeDouble.setItems(tableRecodeDoubleData);

        tablePanel.getChildren().add(tabelRecodeDouble);
    }

    public void setTableToString() {
        tabelRecodeString = new TableView<itemRecodeString>();

        tabelRecodeString.setMinSize(tablePanel.getMinWidth(), tablePanel.getMinHeight());
        tabelRecodeString.setPrefSize(tablePanel.getPrefWidth(), tablePanel.getPrefHeight());
        tabelRecodeString.setMaxSize(tablePanel.getMaxWidth(), tablePanel.getMaxHeight());

        TableColumn<itemRecodeString, String> dari = new TableColumn<itemRecodeString, String>("Dari");
        TableColumn<itemRecodeString, String> hasil = new TableColumn<itemRecodeString, String>("Hasil");
        TableColumn<itemRecodeString, Boolean> delAction = new TableColumn<itemRecodeString, Boolean>("Hapus");

        dari.setCellValueFactory((param) -> {
            itemRecodeString item = param.getValue();
            if (item.type == EnumTypeRecodeString.NORMAL) {
                return new ReadOnlyObjectWrapper(item.dari);
            } else if (item.type == EnumTypeRecodeString.OTHER) {
                return new ReadOnlyObjectWrapper("Lainnya[Other]");
            } else {
                return new ReadOnlyObjectWrapper("");
            }
        });
        hasil.setCellValueFactory((param) -> {
            itemRecodeString item = param.getValue();
            return new ReadOnlyObjectWrapper(item.hasil);
        });
        delAction.setCellValueFactory((param) -> {
            return new SimpleBooleanProperty(param.getValue() != null);
        });
        delAction.setCellFactory((param) -> {
            return new ButtonStringCell();
        });
        tabelRecodeString.getColumns().add(dari);
        tabelRecodeString.getColumns().add(hasil);
        tabelRecodeString.getColumns().add(delAction);
        tabelRecodeString.setItems(tableRecodeStringData);

        tablePanel.getChildren().add(tabelRecodeString);
    }

    private class itemRecodeString {

        private EnumTypeRecodeString type;
        private String dari;
        private String hasil;

        public itemRecodeString(EnumTypeRecodeString type, String dari, String hasil) {
            this.dari = dari;
            this.hasil = hasil;
            this.type = type;
        }
    }

    private class ButtonDoubleCell extends TableCell<ObjectItemRecodeDouble, Boolean> {

        final Button cellButton = new Button("Hapus");

        ButtonDoubleCell() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    ObjectItemRecodeDouble currentPerson = (ObjectItemRecodeDouble) ButtonDoubleCell.this.getTableView().getItems().get(ButtonDoubleCell.this.getIndex());
                    //remove selected item from the table list
                    if (currentPerson.getType() == EnumTypeRecodeDouble.RECODE_FIRST) {
                        haveDoubleFirst = false;
                    } else if (currentPerson.getType() == EnumTypeRecodeDouble.RECODE_LAST) {
                        haveDoubleLast = false;
                    }
                    tableRecodeDoubleData.remove(currentPerson);
                    //refreshTable();
                    tabelRecodeDouble.refresh();
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

    private class ButtonStringCell extends TableCell<itemRecodeString, Boolean> {

        final Button cellButton = new Button("Hapus");

        ButtonStringCell() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    itemRecodeString currentPerson = (itemRecodeString) ButtonStringCell.this.getTableView().getItems().get(ButtonStringCell.this.getIndex());
                    //remove selected item from the table list
                    if (currentPerson.type == EnumTypeRecodeString.OTHER) {
                        haveStringOther = false;
                    }
                    tableRecodeStringData.remove(currentPerson);
                    //refreshTable();
                    tabelRecodeString.refresh();
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

    @FXML
    public void addItemTable() {
        System.out.println("Masuk click " + jenisRecode);
        if (jenisRecode == 1) {
            try {
                final FXMLLoader loader = new FXMLLoader(EditObjectCategoryController.this.getClass().getResource("DoubleRecodePopUp.fxml"));

                final Parent root = loader.load();
                DoubleRecodePopUpController itemController = loader.<DoubleRecodePopUpController>getController();
                itemController.setEditObjectRecode(this, haveDoubleFirst, haveDoubleLast);
                final Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Tambah Recode");
                stage.initStyle(StageStyle.UTILITY);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(tambahItem.getScene().getWindow());
                stage.setScene(scene);
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (jenisRecode == 2) {
            try {
                final FXMLLoader loader = new FXMLLoader(EditObjectCategoryController.this.getClass().getResource("StringRecodePopUp.fxml"));

                final Parent root = loader.load();
                StringRecodePopUpController itemController = loader.<StringRecodePopUpController>getController();
                itemController.setEditObjectRecode(this, haveStringOther);
                final Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Tambah Recode");
                stage.initStyle(StageStyle.UTILITY);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(tambahItem.getScene().getWindow());
                stage.setScene(scene);
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addItemStringRecode(EnumTypeRecodeString type, String dari, String hasil) {
        if (type == EnumTypeRecodeString.OTHER) {
            haveStringOther = true;
        }
        tableRecodeStringData.add(new itemRecodeString(type, dari, hasil));
        tabelRecodeString.refresh();
    }

    public void addItemDoubleRecode(EnumTypeRecodeDouble type, double BatasBawah, double BatasAtas, String Hasil) {
        //System.out.println("masuk " +table);
        if (type == EnumTypeRecodeDouble.RECODE_FIRST) {
            haveDoubleFirst = true;
        }
        if (type == EnumTypeRecodeDouble.RECODE_LAST) {
            haveDoubleLast = true;
        }
        tableRecodeDoubleData.add(new ObjectItemRecodeDouble(BatasBawah, BatasAtas, type, Hasil));
        tabelRecodeDouble.refresh();
    }

    @FXML
    public void simpanAction() {
        //System.out.println("Simpan = " + this.objectCategory.getJenisRecode() + "," + jenisRecode);
        if (jenisLabel == 0) {
            if (this.objectCategory.getJenisLabel() != 0) {
                this.addUpdateTableController.setHavePercentage(isRow, false);
            }
        } else {
            if (this.objectCategory.getJenisLabel() == 0) {
                this.addUpdateTableController.setHavePercentage(isRow, true);
            }
        }
        if (jenisRecode == 0) {
            ObjectCategorySimple objectCategorySimple = new ObjectCategorySimple(jenisRecode, jenisLabel, columnTable.getColumnName(),
                    columnTable.getDataType(), "" + UUID.randomUUID(), labelNormal.getText(), labelPercentase.getText());
            sendToVariabel(objectCategorySimple);
        } else if (jenisRecode == 1) {
            ArrayList<ObjectItemRecodeDouble> items = new ArrayList<>();
            for (int i = 0; i < tableRecodeDoubleData.size(); i++) {
                items.add(tableRecodeDoubleData.get(i));
            }
            ObjectCategoryRecode objectCategoryRecode = new ObjectCategoryRecode(jenisRecode, jenisLabel, columnTable.getDataType(),
                    columnTable.getColumnName(), "" + UUID.randomUUID(), labelNormal.getText(),
                    labelPercentase.getText(), EnumTypeRecode.RECODE_DOUBLE, items, null);
            sendToVariabel(objectCategoryRecode);
        } else if (jenisRecode == 2) {
            ArrayList<ObjectItemRecodeString> items = new ArrayList<>();
            for (int i = 0; i < tableRecodeStringData.size(); i++) {
                if (tableRecodeStringData.get(i).type == EnumTypeRecodeString.OTHER) {
                    ObjectItemRecodeString itemOther = new ObjectItemRecodeString(new ArrayList<String>(), tableRecodeStringData.get(i).hasil, EnumTypeRecodeString.OTHER);
                    items.add(itemOther);
                    break;
                }
                boolean have = false;
                for (int j = 0; j < items.size(); j++) {
                    if (items.get(j).getHasil().equals(tableRecodeStringData.get(i).hasil.trim())) {
                        have = true;
                        items.get(j).addDari(tableRecodeStringData.get(i).dari);
                        break;
                    }
                }
                if (!have) {
                    itemRecodeString itemRR = tableRecodeStringData.get(i);
                    ArrayList<String> dariss = new ArrayList<String>();
                    dariss.add(itemRR.dari);
                    ObjectItemRecodeString item = new ObjectItemRecodeString(dariss, itemRR.hasil, EnumTypeRecodeString.NORMAL);
                    items.add(item);
                }
            }
            //System.out.println("Banyak string " + items.size());
            ObjectCategoryRecode objectCategoryRecode = new ObjectCategoryRecode(jenisRecode, jenisLabel, columnTable.getDataType(),
                    columnTable.getColumnName(), "" + UUID.randomUUID(), labelNormal.getText(),
                    labelPercentase.getText(), EnumTypeRecode.RECODE_STRING, null, items);
            sendToVariabel(objectCategoryRecode);
        }
    }

    public void sendToVariabel(ObjectCategory item) {
        if (columnController != null) {
            columnController.setObjectCategory(item);
            kembaliAction();
        }
        if (rowController != null) {
            rowController.setObjectCategory(item);
            kembaliAction();
        }
    }

    @FXML
    public void kembaliAction() {
        ((Stage) kembaliButton.getScene().getWindow()).fireEvent(
                new WindowEvent(
                        ((Stage) kembaliButton.getScene().getWindow()),
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Menu.PopUp;

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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import makechart.Database.database;
import makechart.Entity.EnumAdditional;
import makechart.Entity.EnumDataType;
import makechart.Entity.EnumSQLColumnFunction;
import makechart.Entity.EnumSQLRowFunction;
import makechart.Entity.EnumTypeColumnResult;
import makechart.Entity.EnumTypeRecodeDouble;
import makechart.Entity.ObjectCategory;
import makechart.Entity.ObjectDataSource;
import makechart.Entity.ObjectInformationColumnTable;
import makechart.Entity.ObjectItemRecodeDouble;
import makechart.Entity.ObjectRowFilter;
import makechart.Entity.ObjectTable;
import makechart.Entity.ObjectValueTable;
import makechart.Menu.DataSourceMenuController;
import makechart.Menu.PopUp.Table.AddFilterController;
import makechart.Menu.PopUp.Table.EditObjectCategoryController;
import makechart.Menu.PopUp.Table.ObjectCategoryColumnController;
import makechart.Menu.PopUp.Table.ObjectCategoryRowController;
import makechart.Menu.PopUp.Table.ValueCategoryController;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AddUpdateTableController implements Initializable {

    @FXML
    private TableView<ObjectRowFilter> obTableFilterView;
    @FXML
    private Button addFilter;
    @FXML
    private ComboBox<ObjectDataSource> comboDataSource;
    @FXML
    private TextField name;
    @FXML
    private TextField desc;
    @FXML
    private ListView<ObjectInformationColumnTable> listVar;
    @FXML
    private Pane columPane;
    @FXML
    private Pane rowPane;
    @FXML
    private Pane resultPane;

    @FXML
    private VBox rowTable;

    @FXML
    private HBox columnTable;

    @FXML
    private VBox resultPaneVBox;

    @FXML
    private Button resetDesigner;

    @FXML
    private ComboBox<String> weight;
    @FXML
    private Button simpanTabel;
    @FXML
    private Button kembaliTabel;

    private ObjectDataSource dataSourceSlected;

    private ArrayList<ObjectCategoryColumnController> objectCategoryColumnControllers;
    private ArrayList<ObjectCategoryRowController> objectCategoryRowControllers;
    private ArrayList<ValueCategoryController> valueControllers;

    ObservableList<ObjectDataSource> optionsDataSource
            = FXCollections.observableArrayList(new ArrayList<ObjectDataSource>());
    ObservableList<ObjectInformationColumnTable> optionsDataVar
            = FXCollections.observableArrayList(new ArrayList<ObjectInformationColumnTable>());
    ObservableList<String> optionsWeight
            = FXCollections.observableArrayList(new ArrayList<String>());

    ObservableList<ObjectRowFilter> rowFilterData
            = FXCollections.observableArrayList(new ArrayList<ObjectRowFilter>());

    private ObjectTable objectTable = null;

    private boolean isNew = true;

    private boolean isRowPercentage = false;
    private boolean isColumnPercentage = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        database dBase = new database();
        ArrayList<ObjectDataSource> dataB = dBase.getObjectDataSources();
        objectCategoryColumnControllers = new ArrayList<>();
        objectCategoryRowControllers = new ArrayList<>();
        valueControllers = new ArrayList<>();

        comboDataSource.valueProperty().addListener(new ChangeListener<ObjectDataSource>() {
            @Override
            public void changed(ObservableValue<? extends ObjectDataSource> observable, ObjectDataSource oldValue, ObjectDataSource newValue) {
                dataSourceSlected = newValue;
                initiateListVar();
                resetDesigner();
            }
        });

        if (objectTable == null) {
            //objectTable = new ObjectTable();
            System.out.println("refresh table");
            optionsDataSource = FXCollections.observableArrayList(new ArrayList<ObjectDataSource>());
            optionsDataSource.addAll(dataB);
            comboDataSource.setItems(optionsDataSource);
            comboDataSource.setCellFactory(new Callback<ListView<ObjectDataSource>, ListCell<ObjectDataSource>>() {
                @Override
                public ListCell<ObjectDataSource> call(ListView<ObjectDataSource> param) {
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
                }
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
            if (dataB != null) {

                comboDataSource.getSelectionModel().select(dataB.get(0));
                //comboDataSource.setValue(dataB.get(0));
                this.dataSourceSlected = dataB.get(0);
                initiateListVar();
            }
        } else {
            if (dataB != null) {
                for (ObjectDataSource item : dataB) {
                    if (item.getUid() == this.objectTable.getSourceData()) {
                        comboDataSource.getSelectionModel().select(item);
                        this.dataSourceSlected = item;
                        initiateListVar();
                        break;
                    }
                }

            }
        }
//        columPane.setOnDragEntered(new EventHandler<DragEvent>() {
//            @Override
//            public void handle(DragEvent event) {
//                //event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//                //columPane.setStyle("-fx-background-color: #f4ee42"
//                //event.consume();
//            }
//        });
        columPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                columPane.setStyle("-fx-border-color: #f4ee42");
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }
        });
        columPane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                columPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ffffff");
                event.consume();
            }
        });
        columPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                columPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ffffff");
                columnTable.setVisible(true);
                try {
                    String content = event.getDragboard().getString();
                    System.out.println(content);
                    String[] part = content.split("<>");
                    System.out.println(part[0] + "|" + part[1]);
                    ObjectInformationColumnTable newD = new ObjectInformationColumnTable(part[0], EnumDataType.valueOf(part[1]));

                    FXMLLoader loader = new FXMLLoader(AddUpdateTableController.this.getClass().getResource("Table/ObjectCategoryColumn.fxml"));
                    Pane newLoadedPane = loader.load();
                    ObjectCategoryColumnController columnItemController = loader.<ObjectCategoryColumnController>getController();
                    columnItemController.setObjectInformationColumn(newD);
                    columnTable.getChildren().add(newLoadedPane);
                    objectCategoryColumnControllers.add(columnItemController);
                    columnItemController.setPane(newLoadedPane, null, AddUpdateTableController.this, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                event.consume();
            }
        });
        rowPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                rowPane.setStyle("-fx-border-color: #f4ee42");
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }
        });
        rowPane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                rowPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ffffff");
                event.consume();
            }
        });
        rowPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                rowPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ffffff");
                rowTable.setVisible(true);
                try {
                    String content = event.getDragboard().getString();
                    System.out.println(content);
                    String[] part = content.split("<>");
                    System.out.println(part[0] + "|" + part[1]);
                    ObjectInformationColumnTable newD = new ObjectInformationColumnTable(part[0], EnumDataType.valueOf(part[1]));

                    FXMLLoader loader = new FXMLLoader(AddUpdateTableController.this.getClass().getResource("Table/ObjectCategoryRow.fxml"));
                    Pane newLoadedPane = loader.load();
                    ObjectCategoryRowController rowItemController = loader.<ObjectCategoryRowController>getController();
                    rowItemController.setObjectInformationColumn(newD);
                    rowTable.getChildren().add(newLoadedPane);
                    objectCategoryRowControllers.add(rowItemController);
                    rowItemController.setPane(newLoadedPane, null, AddUpdateTableController.this, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                event.consume();
            }
        });

        resultPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                resultPane.setStyle("-fx-border-color: #f4ee42");
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }
        });
        resultPane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                resultPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ffffff");
                event.consume();
            }
        });
        resultPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                resultPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ffffff");
                //rowTable.setVisible(true);
                try {
                    String content = event.getDragboard().getString();
                    System.out.println(content);
                    String[] part = content.split("<>");
                    System.out.println(part[0] + "|" + part[1]);
                    ObjectValueTable newD = new ObjectValueTable(part[0], "v_" + UUID.randomUUID(), EnumDataType.valueOf(part[1]), EnumSQLColumnFunction.COUNT);

                    FXMLLoader loader = new FXMLLoader(AddUpdateTableController.this.getClass().getResource("Table/valueCategory.fxml"));
                    Pane newLoadedPane = loader.load();
                    ValueCategoryController valueController = loader.<ValueCategoryController>getController();
                    valueController.setObjectValues(newLoadedPane, newD);
                    resultPaneVBox.getChildren().add(newLoadedPane);
                    valueControllers.add(valueController);
                    resultPaneVBox.setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
                    resultPane.setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                event.consume();
            }
        });

        columnTable.widthProperty().addListener((observable, oldValue, newValue) -> {
            changeSizeRowColumn();
        });
        columnTable.heightProperty().addListener((observable, oldValue, newValue) -> {
            changeSizeRowColumn();
        });
        rowTable.widthProperty().addListener((observable, oldValue, newValue) -> {
            changeSizeRowColumn();
        });
        rowTable.heightProperty().addListener((observable, oldValue, newValue) -> {
            changeSizeRowColumn();
        });
        resultPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            changeSizeRowColumn();
        });
        resultPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            changeSizeRowColumn();
        });

        //tabel filter
        TableColumn<ObjectRowFilter, String> variabelColumn = new TableColumn<ObjectRowFilter, String>("Variabel");
        TableColumn<ObjectRowFilter, String> tandaColumn = new TableColumn<ObjectRowFilter, String>("Tanda");
        TableColumn<ObjectRowFilter, String> pembandingColumn = new TableColumn<ObjectRowFilter, String>("Pembanding");
        TableColumn<ObjectRowFilter, Boolean> delAction = new TableColumn<ObjectRowFilter, Boolean>("Hapus");

        variabelColumn.setCellValueFactory((param) -> {
            return new ReadOnlyObjectWrapper(param.getValue().getNameColumn());
        });
        tandaColumn.setCellValueFactory((param) -> {
            EnumSQLRowFunction funct = param.getValue().getRowFunction();
            if (funct == EnumSQLRowFunction.EQUAL) {
                return new ReadOnlyObjectWrapper("=");
            } else if (funct == EnumSQLRowFunction.NOT_EQUAL) {
                return new ReadOnlyObjectWrapper("!=");
            } else if (funct == EnumSQLRowFunction.LESS_THAN) {
                return new ReadOnlyObjectWrapper("<");
            } else if (funct == EnumSQLRowFunction.LESS_THAN_OR_EQUAL) {
                return new ReadOnlyObjectWrapper("<=");
            } else if (funct == EnumSQLRowFunction.MORE_THAN) {
                return new ReadOnlyObjectWrapper(">");
            } else if (funct == EnumSQLRowFunction.MORE_THAN_OR_EQUAL) {
                return new ReadOnlyObjectWrapper(">=");
            } else {
                return new ReadOnlyObjectWrapper("");
            }
        });
        pembandingColumn.setCellValueFactory((param) -> {
            return new ReadOnlyObjectWrapper(param.getValue().getValueOp());
        });
        delAction.setCellValueFactory((param) -> {
            return new SimpleBooleanProperty(param.getValue() != null);
        });
        delAction.setCellFactory((param) -> {
            return new ButtonDeleteFilter();
        });
        obTableFilterView.getColumns().add(variabelColumn);
        obTableFilterView.getColumns().add(tandaColumn);
        obTableFilterView.getColumns().add(pembandingColumn);
        obTableFilterView.getColumns().add(delAction);
        obTableFilterView.setItems(rowFilterData);
    }

    public void setOnDragAndDropVar(Pane item, ObjectCategoryColumnController itemColumn, ObjectCategoryRowController itemRow) {
        //item.setOnDragDetected(value);
    }

    public void setObjectTable(ObjectTable objectTable) {
        this.objectTable = objectTable;
        if (objectTable != null) {
            isNew = false;
            for (int i = 0; i < optionsDataSource.size(); i++) {
                if (optionsDataSource.get(i).getUid() == this.objectTable.getSourceData()) {
                    comboDataSource.getSelectionModel().select(optionsDataSource.get(i));
                    dataSourceSlected = optionsDataSource.get(i);
                    initiateListVar();
                    break;
                }
            }
            rowFilterData.addAll(objectTable.getObjectRowFilters());
            obTableFilterView.refresh();
            name.setText(objectTable.getName());
            desc.setText(objectTable.getDesc());
            if(objectTable.getNameColumWeight()==null){
                weight.getSelectionModel().selectFirst();
            }else if (!objectTable.getNameColumWeight().equals("")) {
                weight.getSelectionModel().select(objectTable.getNameColumWeight());
            } else {
                weight.getSelectionModel().selectFirst();
            }
            if (objectTable.getColumnCategory().size() > 0) {
                columnTable.setVisible(true);
            }
            if (objectTable.getRowCategory().size() > 0) {
                rowTable.setVisible(true);
            }
            for (ObjectCategory item : objectTable.getColumnCategory()) {
                try {
                    ObjectInformationColumnTable newD = new ObjectInformationColumnTable(item.getNameColumn(), item.getDataType());
                    FXMLLoader loader = new FXMLLoader(AddUpdateTableController.this.getClass().getResource("Table/ObjectCategoryColumn.fxml"));
                    Pane newLoadedPane = loader.load();
                    ObjectCategoryColumnController columnItemController = loader.<ObjectCategoryColumnController>getController();
                    columnItemController.setObjectInformationColumn(newD);
                    columnTable.getChildren().add(newLoadedPane);
                    objectCategoryColumnControllers.add(columnItemController);
                    columnItemController.setPane(newLoadedPane, null, AddUpdateTableController.this, item);
                    if (item.getJenisLabel() == 1 || item.getJenisLabel() == 2) {
                        isColumnPercentage = true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                changeSizeRowColumn();
            }
            for (ObjectCategory item : objectTable.getRowCategory()) {
                try {
                    ObjectInformationColumnTable newD = new ObjectInformationColumnTable(item.getNameColumn(), item.getDataType());
                    FXMLLoader loader = new FXMLLoader(AddUpdateTableController.this.getClass().getResource("Table/ObjectCategoryRow.fxml"));
                    Pane newLoadedPane = loader.load();
                    ObjectCategoryRowController rowItemController = loader.<ObjectCategoryRowController>getController();
                    rowItemController.setObjectInformationColumn(newD);
                    rowTable.getChildren().add(newLoadedPane);
                    objectCategoryRowControllers.add(rowItemController);
                    rowItemController.setPane(newLoadedPane, null, AddUpdateTableController.this, item);
                    if (item.getJenisLabel() == 1 || item.getJenisLabel() == 2) {
                        isRowPercentage = true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                changeSizeRowColumn();
            }
            //System.out.println("valus:" + objectTable.getValues().size());
            for (ObjectValueTable item : objectTable.getValues()) {
                try {
                    ObjectValueTable newD = new ObjectValueTable(item.getNameColumn(), item.getNameColumnHasil(), item.getDataType(), item.getFunction());
                    FXMLLoader loader = new FXMLLoader(AddUpdateTableController.this.getClass().getResource("Table/valueCategory.fxml"));
                    Pane newLoadedPane = loader.load();
                    ValueCategoryController valueController = loader.<ValueCategoryController>getController();
                    valueController.setObjectValues(newLoadedPane, newD);
                    resultPaneVBox.getChildren().add(newLoadedPane);
                    valueControllers.add(valueController);
                    resultPaneVBox.setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
                    resultPane.setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void initiateListVar() {
        database dBase = new database();
        ArrayList<ObjectInformationColumnTable> dataB = dBase.getInformationFromDataSource(this.dataSourceSlected);
        System.out.println("refresh table");
        if (dataB != null) {
            optionsDataVar = FXCollections.observableArrayList(new ArrayList<ObjectInformationColumnTable>());
            optionsDataVar.addAll(dataB);
            optionsWeight.clear();
            optionsWeight.add("-Tidak Menggunakan-");
            for (ObjectInformationColumnTable item : dataB) {
                optionsWeight.add(item.getColumnName());
            }
            weight.setItems(optionsWeight);
            weight.getSelectionModel().selectFirst();
            listVar.setItems(optionsDataVar);
            listVar.setCellFactory(new Callback<ListView<ObjectInformationColumnTable>, ListCell<ObjectInformationColumnTable>>() {
                @Override
                public ListCell<ObjectInformationColumnTable> call(ListView<ObjectInformationColumnTable> param) {
                    final ListCell<ObjectInformationColumnTable> cell = new ItemListVarCell(AddUpdateTableController.this);

                    return cell;
                }
            });
        }
        rowFilterData.clear();
        obTableFilterView.refresh();
    }

    public void changeSizeRowColumn() {
        this.rowTable.setLayoutY(this.columnTable.getLayoutY() + this.columnTable.getHeight() + 10);
        this.columnTable.setLayoutX(this.rowTable.getLayoutX() + this.rowTable.getWidth() + 10);
        this.resultPane.setLayoutX(this.rowTable.getLayoutX() + this.rowTable.getWidth() + 10 + this.columnTable.getWidth() / 2);
        this.resultPane.setLayoutY(this.columnTable.getLayoutY() + this.columnTable.getHeight() + 10 + this.rowTable.getHeight() / 2);
    }

    @FXML
    public void resetDesigner() {
        rowTable.getChildren().clear();
        columnTable.getChildren().clear();
        resultPaneVBox.getChildren().clear();
        objectCategoryColumnControllers.clear();
        objectCategoryRowControllers.clear();
        valueControllers.clear();
        changeSizeRowColumn();
    }

    @FXML
    public void addFilter() {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("Table/AddFilter.fxml"));
            //loader.setController(new DialogController(message));
            //System.out.println("1");
            final Parent root = loader.load();

            AddFilterController addController = loader.<AddFilterController>getController();
            addController.setVar(this, optionsDataVar);
            final Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Tambah Filter");
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addFilter.getScene().getWindow());
            //System.out.println("7");
            stage.setScene(scene);
            //System.out.println("8");
//            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//                public void handle(WindowEvent we) {
//                    System.out.println("close");
//                    thisPane.setStyle("-fx-border-color: #000000;-fx-background-color: #ffffff");
//                }
//            });
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("Satu : " + );
        }
    }

    public void addFilterOptionFromAddFilter(ObjectInformationColumnTable infor, EnumSQLRowFunction funtion, String pembanding) {
        rowFilterData.add(new ObjectRowFilter(infor.getColumnName(), infor.getDataType(), funtion, pembanding));
        obTableFilterView.refresh();
    }

    private class ItemListVarCell extends ListCell<ObjectInformationColumnTable> {

        //private final ImageView imageView = new ImageView();
        public ItemListVarCell(AddUpdateTableController add) {
            ListCell thisCell = this;

            setContentDisplay(ContentDisplay.CENTER);
            setAlignment(Pos.CENTER);

            setOnDragDetected(event -> {
                if (getItem() == null) {
                    return;
                }

                ObservableList<ObjectInformationColumnTable> items = getListView().getItems();

                Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(getItem().getColumnName() + "<>" + getItem().getDataType().name());
                //content.put(DataFormat., name)
                //content.put(DataFormat., name)

                dragboard.setContent(content);
                //add.setSelectedVar(getItem());
                event.consume();
            });
        }

        @Override
        protected void updateItem(ObjectInformationColumnTable item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
            } else {
                if (item != null) {
                    setText(item.getColumnName());
                } else {
                    setText(null);
                }
            }
        }
    }

    private class ButtonDeleteFilter extends TableCell<ObjectRowFilter, Boolean> {

        final Button cellButton = new Button("Hapus");

        ButtonDeleteFilter() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    ObjectRowFilter currentPerson = (ObjectRowFilter) ButtonDeleteFilter.this.getTableView().getItems().get(ButtonDeleteFilter.this.getIndex());
                    //remove selected item from the table list
                    rowFilterData.remove(currentPerson);
                    obTableFilterView.refresh();
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

    public boolean canHavePercentage(boolean isRow) {
        if (isRow) {
            return isColumnPercentage;
        } else {
            return isRowPercentage;
        }
    }

    public void setHavePercentage(boolean isRow, boolean havePercentage) {
        //System.out.println("Have Percentage "+havePercentage);
        if (isRow) {
            if (havePercentage) {
                isRowPercentage = true;
                isColumnPercentage = false;
            } else {
                isRowPercentage = false;
            }
        } else {
            if (havePercentage) {
                isRowPercentage = false;
                isColumnPercentage = true;
            } else {
                isColumnPercentage = false;
            }
        }
    }

    @FXML
    public void simpanTabelAction() {
        if (name.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Nama Harus terisi!");
            alert.showAndWait();
            return;
        }
        if (desc.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Deskripsi Harus terisi!");
            alert.showAndWait();
            return;
        }
        if (objectCategoryColumnControllers.size() + objectCategoryRowControllers.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Minimal harus ada satu kolom atau baris!");
            alert.showAndWait();
            return;
        }
        ObjectTable objectTable = null;
        if (isNew) {
            objectTable = new ObjectTable("table_" + UUID.randomUUID(), name.getText(), desc.getText(), true);
        } else {
            objectTable = new ObjectTable(this.objectTable.getUid(), name.getText(), desc.getText(), true);
        }
        ArrayList<ObjectCategory> columCategoris = new ArrayList<>();
        ArrayList<ObjectCategory> rowCategoris = new ArrayList<>();
        for (ObjectCategoryColumnController item : objectCategoryColumnControllers) {
            columCategoris.add(item.getObjectCategory());
        }
        for (ObjectCategoryRowController item : objectCategoryRowControllers) {
            rowCategoris.add(item.getObjectCategory());
        }
        objectTable.setColumnCategory(columCategoris);
        objectTable.setRowCategory(rowCategoris);
        objectTable.setEnumAdditionals(null);
        ArrayList<ObjectRowFilter> rowFilter = new ArrayList<>();
        for (int i = 0; i < rowFilterData.size(); i++) {
            rowFilter.add(rowFilterData.get(i));
        }
        ArrayList<ObjectValueTable> valuesRr = new ArrayList<>();
        for (int i = 0; i < valueControllers.size(); i++) {
            valuesRr.add(valueControllers.get(i).getObjectValueTable());
        }
        objectTable.setValues(valuesRr);
        objectTable.setObjectRowFilters(rowFilter);
        objectTable.setSourceData(dataSourceSlected.getUid());
        if (!weight.getValue().equals("-Tidak Menggunakan-")) {
            objectTable.setNameColumWeight(weight.getValue());
        } else {
            objectTable.setNameColumWeight("");
        }
        database dBase = new database();
        if (isNew) {
            dBase.insertTable(objectTable);
        } else {
            dBase.updateTable(objectTable);
        }
        kembaliTabelAction();
    }

    @FXML
    public void kembaliTabelAction() {
        ((Stage) kembaliTabel.getScene().getWindow()).fireEvent(
                new WindowEvent(
                        ((Stage) kembaliTabel.getScene().getWindow()),
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
        //stage.close();
    }

}

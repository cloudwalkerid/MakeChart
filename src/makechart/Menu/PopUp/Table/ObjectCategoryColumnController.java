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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import makechart.Entity.EnumDataType;
import makechart.Entity.ObjectInformationColumnTable;
import makechart.Entity.ObjectCategory;
import makechart.Entity.ObjectCategoryRecode;
import makechart.Entity.ObjectCategorySimple;
import makechart.Menu.PopUp.AddUpdateTableController;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ObjectCategoryColumnController implements Initializable {

    @FXML
    private Label nameColumnLabel;
    @FXML
    private HBox hBoxCategory;

    private ObjectInformationColumnTable objectInformationColumnTable;

    private ObjectCategory objectCategory;

    private StringProperty nameColumnProp;

    private Pane thisPane;

    private Pane parentPane;

    private ObjectCategoryColumnController parentController;

    private ArrayList<ObjectCategoryColumnController> childController;

    private boolean onThisPane;

    private AddUpdateTableController addUpdateTableController;

    /**
     * Initializes the controller class.ObservableValue
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nameColumnProp = new SimpleStringProperty();
        //Platform.setImplicitExit(false);
        //nameColumnLabel.textProperty().bind(nameColumnProp);
        nameColumnLabel.textProperty().bind(nameColumnProp);
        //System.out.println("Initiate");
        childController = new ArrayList<>();
    }

    public void setObjectInformationColumn(ObjectInformationColumnTable objectInformationColumnTable) {
        this.objectInformationColumnTable = objectInformationColumnTable;
        nameColumnProp.set(objectInformationColumnTable.getColumnName());
        System.out.println(nameColumnProp.get() + "||" + nameColumnLabel.getText());

    }

    public void setPane(Pane thisPane, ObjectCategoryColumnController parentController, 
            AddUpdateTableController addUpdateTableController, ObjectCategory objectCategory) {
        this.thisPane = thisPane;
        this.addUpdateTableController = addUpdateTableController;
        //this.nameColumnLabel.setMinSize(this.thisPane.getPrefWidth(), this.thisPane.getPrefHeight());
        //this.thisPane.
        //System.out.println(""+this.nameColumnLabel.getWidth());
        this.nameColumnLabel.setAlignment(Pos.CENTER);
        this.parentController = parentController;
        this.thisPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //System.out.println(thisPane.getStyle());
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
                OnDragOver(event);
                //System.out.println(thisPane.getStyle());
            }
        });
        this.thisPane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                OnExited();
            }
        });
        this.thisPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                String content = event.getDragboard().getString();
                OnDrop(content);
                event.consume();
            }
        });
        this.thisPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        //thisPane.setStyle("-fx-border-color: #e9ff00;-fx-background-color: #ffffff");
                        OpenCategoryMenu();
                    }
                    event.consume();
                }
            }
        });
        this.addUpdateTableController.changeSizeRowColumn();
        if (objectCategory != null) {
            if (objectCategory.getObjectCategorys() != null && objectCategory.getObjectCategorys().size() != 0) {
                for (ObjectCategory item : objectCategory.getObjectCategorys()) {
                    try {
                        ObjectInformationColumnTable newD = new ObjectInformationColumnTable(item.getNameColumn(), item.getDataType());

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ObjectCategoryColumn.fxml"));
                        Pane newLoadedPane = loader.load();
                        newLoadedPane.setId("Baru");
                        ObjectCategoryColumnController columnItemController = loader.<ObjectCategoryColumnController>getController();
                        columnItemController.setObjectInformationColumn(newD);
                        hBoxCategory.setVisible(true);
                        hBoxCategory.getChildren().add(newLoadedPane);
                        childController.add(columnItemController);
                        //this.thisPane.setMinSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
                        this.thisPane.setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
                        columnItemController.setPane(newLoadedPane, this, this.addUpdateTableController, item);
                    } catch (Exception ex) {
                        System.out.println("Load Error Category Column");
                        ex.printStackTrace();
                    }
                }
            }
            if(objectCategory.getJenisRecode()==0){
                ObjectCategorySimple transObject = (ObjectCategorySimple) objectCategory;
                this.objectCategory = new ObjectCategorySimple(transObject.getJenisRecode(), transObject.getJenisLabel(), 
                        transObject.getNameColumn(), transObject.getDataType(), transObject.getNameColumnHasil(), 
                        transObject.getLabelValue(), transObject.getLabelPercentage());
            }else if(objectCategory.getJenisRecode()==1 || objectCategory.getJenisRecode()==2){
                ObjectCategoryRecode transObject = (ObjectCategoryRecode) objectCategory;
                this.objectCategory = new ObjectCategoryRecode(transObject.getJenisRecode(), transObject.getJenisLabel(), 
                        transObject.getDataType(), transObject.getNameColumn(), transObject.getNameColumnHasil(), 
                        transObject.getLabelValue(), transObject.getLabelPercentage(), transObject.getType(), 
                        transObject.getObjectItemRecodeDoubles(), transObject.getObjectItemRecodeStrings());
            }
        }else{
            this.objectCategory = new ObjectCategorySimple(0, 0, objectInformationColumnTable.getColumnName(), 
                    objectInformationColumnTable.getDataType(), "" + UUID.randomUUID(), 
                    objectInformationColumnTable.getColumnName(), objectInformationColumnTable.getColumnName());
        }
    }

    public void OnDrop(String content) {
        try {
            System.out.println(content);
            String[] part = content.split("<>");
            //System.out.println(part[0] + "|" + part[1]);
            ObjectInformationColumnTable newD = new ObjectInformationColumnTable(part[0], EnumDataType.valueOf(part[1]));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ObjectCategoryColumn.fxml"));
            Pane newLoadedPane = loader.load();
            newLoadedPane.setId("Baru");
            ObjectCategoryColumnController columnItemController = loader.<ObjectCategoryColumnController>getController();
            columnItemController.setObjectInformationColumn(newD);
            hBoxCategory.setVisible(true);
            hBoxCategory.getChildren().add(newLoadedPane);
            childController.add(columnItemController);
            //this.thisPane.setMinSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
            this.thisPane.setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
            columnItemController.setPane(newLoadedPane, this, this.addUpdateTableController, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.thisPane.setStyle("-fx-border-color: #000000;-fx-background-color: #ffffff");
        //event.consume();
    }

    public void OnDragOver(DragEvent event) {
        if (parentController != null) {
            parentController.OnExited();
        }
        this.thisPane.setStyle("-fx-border-color: #e9ff00;-fx-background-color: #ffffff");
    }

    public void OnExited() {
        if (parentController != null) {
            //parentController.OnDragOver(null);
        }
        this.thisPane.setStyle("-fx-border-color: #000000;-fx-background-color: #ffffff");
    }

    public void OpenCategoryMenu() {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("EditObjectCategory.fxml"));
            //loader.setController(new DialogController(message));
            //System.out.println("1");
            final Parent root = loader.load();

            EditObjectCategoryController addController = loader.<EditObjectCategoryController>getController();
            //System.out.println("00.2");
            if(parentController==null){
                addController.setColumnController(objectInformationColumnTable, objectCategory, this, true, addUpdateTableController);
            }else{
                addController.setColumnController(objectInformationColumnTable, objectCategory, this, false, addUpdateTableController);
            }
            //System.out.println("00.3");

            //System.out.println("2");
            final Scene scene = new Scene(root);
            //System.out.println("3");
            Stage stage = new Stage();
            stage.setTitle("Pengaturan Tambahan Kolom " + objectInformationColumnTable.getColumnName());
            stage.initStyle(StageStyle.UTILITY);
            //System.out.println("4");
            stage.initModality(Modality.APPLICATION_MODAL);
            //System.out.println("5");
            //stage.initStyle(StageStyle.UNDECORATED);
            //System.out.println("6");
            stage.initOwner(nameColumnLabel.getScene().getWindow());
            //System.out.println("7");
            stage.setScene(scene);
            //System.out.println("8");
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    System.out.println("close");
                    thisPane.setStyle("-fx-border-color: #000000;-fx-background-color: #ffffff");
                }
            });
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("Satu : " + );
        }
    }

    public void setObjectCategory(ObjectCategory objectCategory) {
        this.objectCategory = objectCategory;
    }

    public ObjectCategory getObjectCategory() {
        objectCategory.setNameColumnHasil("c_"+objectCategory.getNameColumnHasil());
        if (childController.size() != 0) {
            for (int i = 0; i < childController.size(); i++) {
                objectCategory.getObjectCategorys().add(childController.get(i).getObjectCategory());
            }
        }
        return objectCategory;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javax.xml.transform.Source;
import makechart.Menu.DataSourceMenuController;
import makechart.Menu.MakeGrafikMenuController;
import makechart.Menu.MakeMapMenuController;
import makechart.Menu.MakeTableMenuController;

/**
 *
 * @author ASUS
 */
public class MainMenuController implements Initializable {

    @FXML
    private Pane sumberData;
    @FXML
    private Pane makeTable;
    @FXML
    private Pane makeGrafik;
    @FXML
    private Pane makeMap;
    @FXML
    private VBox content;

    private DataSourceMenuController dataSourceMenuController;
    private MakeGrafikMenuController makeGrafikMenuController;
    private MakeTableMenuController makeTableMenuController;
    private MakeMapMenuController makeMapMenuController;
    private int indexMenuOpen = 0;

    @FXML
    private void overMenuSatu() {
        if (indexMenuOpen != 0) {
            sumberData.setStyle("-fx-background-color:#009688");
        }
    }

    @FXML
    private void overMenuDua() {
        if (indexMenuOpen != 1) {
            makeTable.setStyle("-fx-background-color:#009688");
        }
    }

    @FXML
    private void overMenuTiga() {
        if (indexMenuOpen != 2) {
            makeGrafik.setStyle("-fx-background-color:#009688");
        }
    }

    @FXML
    private void overMenuEmpat() {
        if (indexMenuOpen != 3) {
            makeMap.setStyle("-fx-background-color:#009688");
        }
    }

    @FXML
    private void exitMenuSatu() {
        if (indexMenuOpen != 0) {
            sumberData.setStyle("-fx-background-color:#26a69a");
        }
    }

    @FXML
    private void exitMenuDua() {
        if (indexMenuOpen != 1) {
            makeTable.setStyle("-fx-background-color:#26a69a");
        }
    }

    @FXML
    private void exitMenuTiga() {
        if (indexMenuOpen != 2) {
            makeGrafik.setStyle("-fx-background-color:#26a69a");
        }
    }

    @FXML
    private void exit2MenuEmpat() {
        if (indexMenuOpen != 3) {
            makeMap.setStyle("-fx-background-color:#26a69a");
        }
    }

    @FXML
    private void clicMenuSatu() {
        if (indexMenuOpen != 0) {
            sumberData.setStyle("-fx-background-color: #00897b");
            makeTable.setStyle("-fx-background-color:#26a69a");
            makeGrafik.setStyle("-fx-background-color:#26a69a");
            makeMap.setStyle("-fx-background-color:#26a69a");
            makeTableMenuController = null;
            makeGrafikMenuController = null;
            makeMapMenuController = null;
            indexMenuOpen = 0;

            try {
                content.getChildren().clear();
                Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Menu/DataSourceMenu.fxml"));
                content.getChildren().add(newLoadedPane);

                FXMLLoader fxmloader = new FXMLLoader();
                fxmloader.load(getClass().getResource("Menu/DataSourceMenu.fxml").openStream());
                dataSourceMenuController = (DataSourceMenuController) fxmloader.getController();
            } catch (Exception e) {
                System.out.println("Error 1: " + e);
            }
        } else {
            if (dataSourceMenuController != null) {
                if (dataSourceMenuController.alreadyHaveProgress()) {

                } else {
                    try {
                        content.getChildren().clear();
                        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Menu/DataSourceMenu.fxml"));
                        content.getChildren().add(newLoadedPane);

                        FXMLLoader fxmloader = new FXMLLoader();
                        fxmloader.load(getClass().getResource("Menu/DataSourceMenu.fxml").openStream());
                        dataSourceMenuController = (DataSourceMenuController) fxmloader.getController();
                    } catch (Exception e) {
                        System.out.println("Error 1: " + e);
                    }
                }
            }
        }
    }

    @FXML
    private void clicMenuDua() {
        if (indexMenuOpen != 1) {
            makeTable.setStyle("-fx-background-color: #00897b");
            sumberData.setStyle("-fx-background-color:#26a69a");
            makeGrafik.setStyle("-fx-background-color:#26a69a");
            makeMap.setStyle("-fx-background-color:#26a69a");
            dataSourceMenuController = null;
            makeGrafikMenuController = null;
            makeMapMenuController = null;
            indexMenuOpen = 1;

            try {
                content.getChildren().clear();
                Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Menu/MakeTableMenu.fxml"));
                content.getChildren().add(newLoadedPane);
                FXMLLoader fxmloader = new FXMLLoader();
                fxmloader.load(getClass().getResource("Menu/MakeTableMenu.fxml").openStream());
                makeTableMenuController = (MakeTableMenuController) fxmloader.getController();
            } catch (Exception e) {
                System.out.println("Error 1: " + e);
            }
        } else {
            if (makeTableMenuController != null) {
                if (makeTableMenuController.alreadyHaveProgress()) {

                } else {
                    try {
                        content.getChildren().clear();
                        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Menu/MakeTableMenu.fxml"));
                        content.getChildren().add(newLoadedPane);

                        FXMLLoader fxmloader = new FXMLLoader();
                        fxmloader.load(getClass().getResource("Menu/MakeTableMenu.fxml").openStream());
                        makeTableMenuController = (MakeTableMenuController) fxmloader.getController();
                    } catch (Exception e) {
                        System.out.println("Error 2: " + e);
                    }
                }
            }
        }
    }

    @FXML
    private void clicMenuTiga() {
        if (indexMenuOpen != 2) {
            makeGrafik.setStyle("-fx-background-color: #00897b");
            makeTable.setStyle("-fx-background-color:#26a69a");
            sumberData.setStyle("-fx-background-color:#26a69a");
            makeMap.setStyle("-fx-background-color:#26a69a");
            makeTableMenuController = null;
            dataSourceMenuController = null;
            makeMapMenuController = null;
            indexMenuOpen = 2;

            try {
                content.getChildren().clear();
                Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Menu/MakeGrafikMenu.fxml"));
                content.getChildren().add(newLoadedPane);

                FXMLLoader fxmloader = new FXMLLoader();
                fxmloader.load(getClass().getResource("Menu/MakeGrafikMenu.fxml").openStream());
                makeGrafikMenuController = (MakeGrafikMenuController) fxmloader.getController();
            } catch (Exception e) {
                System.out.println("Error 1: " + e);
            }
        } else {
            if (makeGrafikMenuController != null) {
                if (makeGrafikMenuController.alreadyHaveProgress()) {

                } else {
                    try {
                        content.getChildren().clear();
                        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Menu/MakeGrafikMenu.fxml"));
                        content.getChildren().add(newLoadedPane);

                        FXMLLoader fxmloader = new FXMLLoader();
                        fxmloader.load(getClass().getResource("Menu/MakeGrafikMenu.fxml").openStream());
                        makeGrafikMenuController = (MakeGrafikMenuController) fxmloader.getController();
                    } catch (Exception e) {
                        System.out.println("Error 1: " + e);
                    }
                }
            }
        }
    }

    @FXML
    private void clicMenuEmpat() {
        if (indexMenuOpen != 3) {
            makeMap.setStyle("-fx-background-color: #00897b");
            makeTable.setStyle("-fx-background-color:#26a69a");
            makeGrafik.setStyle("-fx-background-color:#26a69a");
            sumberData.setStyle("-fx-background-color:#26a69a");
            makeTableMenuController = null;
            makeGrafikMenuController = null;
            dataSourceMenuController = null;
            indexMenuOpen = 0;

            try {
                content.getChildren().clear();
                Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Menu/MakeMapMenu.fxml"));
                content.getChildren().add(newLoadedPane);

                FXMLLoader fxmloader = new FXMLLoader();
                fxmloader.load(getClass().getResource("Menu/MakeMapMenu.fxml").openStream());
                makeMapMenuController = (MakeMapMenuController) fxmloader.getController();
            } catch (Exception e) {
                System.out.println("Error 1: " + e);
            }
        } else {
            if (makeMapMenuController != null) {
                if (makeMapMenuController.alreadyHaveProgress()) {

                } else {
                    try {
                        content.getChildren().clear();
                        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Menu/MakeMapMenu.fxml"));
                        content.getChildren().add(newLoadedPane);

                        FXMLLoader fxmloader = new FXMLLoader();
                        fxmloader.load(getClass().getResource("Menu/MakeMapMenu.fxml").openStream());
                        makeMapMenuController = (MakeMapMenuController) fxmloader.getController();
                    } catch (Exception e) {
                        System.out.println("Error 1: " + e);
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            content.getChildren().clear();
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Menu/DataSourceMenu.fxml"));
            content.getChildren().add(newLoadedPane);

            FXMLLoader fxmloader = new FXMLLoader();
            fxmloader.load(getClass().getResource("Menu/DataSourceMenu.fxml").openStream());
            dataSourceMenuController = (DataSourceMenuController) fxmloader.getController();
        } catch (Exception e) {
            System.out.println("Error 1: " + e);
        }

    }

}

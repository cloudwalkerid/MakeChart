/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import makechart.Database.database;

/**
 *
 * @author ASUS
 */
public class MakeChart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("SITANKTOP");
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuDoc.fxml"));

        Scene scene = new Scene(root);
        //aplikasi tabulasi data Susenas berbasis desktop
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResource("image/sitangtop.png").toString()));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createFolderInitiate();
        launch(args);
    }

    public static void createFolderInitiate() {

        File FolderApp = new File(System.getenv("APPDATA") + "\\MakeChart");
        File FolderGeoJSon = new File(System.getenv("APPDATA") + "\\MakeChart\\GeoJson");
        File dataSourceDb = new File(System.getenv("APPDATA") + "\\MakeChart\\DataSource");
        File makeChartFolder = new File(System.getenv("APPDATA") + "\\MakeChart\\MakeChart");
        File makeTableFolder = new File(System.getenv("APPDATA") + "\\MakeChart\\MakeTable");
        File makeMapFolder = new File(System.getenv("APPDATA") + "\\MakeChart\\MakeMap");
        File excellFolder = new File(System.getenv("APPDATA") + "\\MakeChart\\MakeExcell");
        File csvFolder = new File(System.getenv("APPDATA") + "\\MakeChart\\MakeTableCsv");
        File pictureFolder = new File(System.getenv("APPDATA") + "\\MakeChart\\MakePicture");
        File htmlFolder = new File(System.getenv("APPDATA") + "\\MakeChart\\MakeHtml");
        String dbLocation = System.getenv("APPDATA") + "\\MakeChart\\makechart.db";
        
        if (!FolderApp.exists()) {
            FolderApp.mkdir();
        }
        if (!FolderGeoJSon.exists()) {
            FolderGeoJSon.mkdir();
        }
        if (!dataSourceDb.exists()) {
            dataSourceDb.mkdir();
        }
        if (!makeChartFolder.exists()) {
            makeChartFolder.mkdir();
        }
        if (!makeTableFolder.exists()) {
            makeTableFolder.mkdir();
        }
        if (!makeMapFolder.exists()) {
            makeMapFolder.mkdir();
        }
        if (!excellFolder.exists()) {
            excellFolder.mkdir();
        }
        if (!csvFolder.exists()) {
            csvFolder.mkdir();
        }
        if (!pictureFolder.exists()) {
            pictureFolder.mkdir();
        }
        if (!htmlFolder.exists()) {
            htmlFolder.mkdir();
        }
        if (!(new File(dbLocation)).exists()) {
            String url = "jdbc:sqlite:" + dbLocation;

            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    String dataFolder = System.getenv("APPDATA");
                    System.out.println(dataFolder);
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                }

            } catch (SQLException e) {
                System.out.println("errp");
                System.out.println(e.getMessage());
            }
        }
        database dBase = new database();
        dBase.createNewTable();
    }

}

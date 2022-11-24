/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Menu.PopUp;

import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import makechart.Database.database;
import makechart.Entity.ObjectProgress;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AddDataSourceController implements Initializable, ObjectProgress {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField name;
    @FXML
    private TextField fileLocationString;
    @FXML
    private TextArea description;
    @FXML
    private Button chooseFile;
    @FXML
    private Button OkBdutton;
    @FXML
    private Button CancelBdutton;
    @FXML
    private ProgressBar progressBar;

    private Task copyWorker;
    private boolean hasilPublic;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fileLocationString.setEditable(false);
        OkBdutton.setDisable(true);
        progressBar.setVisible(false);
    }

    @FXML
    public void chooseFile() {
        Stage openStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose DBF File");
        List<String> acceptedExt = new ArrayList<>();
        acceptedExt.add("*.dbf");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DBF File", acceptedExt));
        File file = fileChooser.showOpenDialog(openStage);

        if (file != null) {
            fileLocationString.setText(file.getAbsolutePath());
            evaluate();
        } else {
            fileLocationString.setText("");
            evaluate();
        }

    }

    @FXML
    public void okButtonClick() {
        progressBar.setVisible(true);
        //progressBar = new ProgressBar(0);
        progressBar.setProgress(0);
        copyWorker = createWorker();

        progressBar.progressProperty().unbind();
        //progressBar.progressProperty().bind(copyWorker.progressProperty());

        Thread as = new Thread(copyWorker);
        as.start();
        if(!as.isAlive()){
            success(hasilPublic);
        }
    }

    @FXML
    public void cancelButtonClick() {
       ((Stage) CancelBdutton.getScene().getWindow()).fireEvent(
                new WindowEvent(
                        ((Stage) CancelBdutton.getScene().getWindow()),
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
    }

    @FXML
    public void keyPressedAll() {
        evaluate();
    }

    public void evaluate() {
        //System.out.println("evaluate");
        if (!name.getText().trim().equals("") && !fileLocationString.getText().trim().equals("")
                && !description.getText().trim().equals("")) {
            OkBdutton.setDisable(false);
        } else {
            OkBdutton.setDisable(true);
        }
    }

    public Task createWorker() {
        Task workAs = new Task() {
            @Override
            protected Object call() throws Exception {
                database dbBase = new database();
                dbBase.setObjectProgress(AddDataSourceController.this);

                boolean hasil = dbBase.insertDataSourceNew(name.getText(), description.getText(), fileLocationString.getText());
                System.out.println("berhasil");
                progressBar.setProgress(1);
                hasilPublic = hasil;
                return true;
            }

        };
        workAs.setOnSucceeded(new EventHandler() {
            @Override
            public void handle(Event event) {
                success(hasilPublic);
            }
        });
        return workAs;
    }

    @Override
    public void updateProgress(int now, int from) {
        double progress = ((double) now) / (from);
        progressBar.setProgress(progress);
        //copyWorker.updateProgress(now, from);
    }

    public void success(boolean hasil) {
        if (hasil) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Berhasil Menginput Database!");

            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Gagal Menginput Database!");

            alert.showAndWait();
        }
        progressBar.setProgress(0);
        progressBar.setVisible(false);
        cancelButtonClick();
    }
}
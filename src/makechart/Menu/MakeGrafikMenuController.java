/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Menu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class MakeGrafikMenuController implements Initializable {

    private boolean alreadyHaveProgress;
    
    /**
     * Initializes the controller class.
     */
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        alreadyHaveProgress = false;
    }   
    
    public boolean alreadyHaveProgress(){
        return alreadyHaveProgress;
    }
    
}

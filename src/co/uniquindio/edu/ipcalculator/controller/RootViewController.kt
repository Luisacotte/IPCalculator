package co.uniquindio.edu.ipcalculator.controller

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.layout.BorderPane

class RootViewController {

    @FXML lateinit var rootPane: BorderPane
    @FXML lateinit var initPaneController: InitViewController

    companion object{
        @JvmStatic
        fun showAlert(message:String, title:String, header:String, alertType: Alert.AlertType){
            val alert:Alert = Alert(alertType)
            alert.title = title
            alert.contentText = message
            alert.headerText = header
            alert.isResizable = true
            alert.showAndWait()
        }
    }
}
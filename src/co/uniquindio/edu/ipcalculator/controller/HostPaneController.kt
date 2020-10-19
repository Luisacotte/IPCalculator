package co.uniquindio.edu.ipcalculator.controller

import co.uniquindio.edu.ipcalculator.exceptions.MalformedIPAddress
import co.uniquindio.edu.ipcalculator.model.IPCalculator
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Label
import javafx.scene.control.TextField

class HostPaneController {
    @FXML lateinit var netAddressLabel:Label
    @FXML lateinit var broadcastAddressLabel:Label
    @FXML lateinit var numberHostLabel:Label
    @FXML lateinit var hostRangeIPAddressLabel:Label
    @FXML lateinit var ipAddressCompleteField:TextField

    @FXML
    fun analyzeIP(e:ActionEvent){
        if(isInputValid()){
            try{
                val ipCalculator = IPCalculator(ipAddressCompleteField.text)
                netAddressLabel.text = ipCalculator.getNetIPAddress()
                broadcastAddressLabel.text = ipCalculator.getBroadcastAddress()
                numberHostLabel.text = "".plus(ipCalculator.getNumberOfAssignableIPs())
                hostRangeIPAddressLabel.text = ipCalculator.getRangeIPAddressAssignable()
            }catch(exception: MalformedIPAddress){
                RootViewController.showAlert(exception.message.toString(), "ERROR", "", Alert.AlertType.ERROR)
            }
        }
    }
    private fun isInputValid():Boolean{
        var valid = false
        var errorMessage = ""
        var alertType: Alert.AlertType = Alert.AlertType.NONE
        if(ipAddressCompleteField.text.isNullOrEmpty()){
            errorMessage += "Debes ingresar la dirección IP"
            alertType = Alert.AlertType.WARNING
        }else{
            val split = ipAddressCompleteField.text.split("/")
            if(split.size==2){
                val split2 = split[0].split(".")
                if(split2.size == 4){
                    try{
                        var aux1 = split2[0].toInt()
                        var aux2 = split2[1].toInt()
                        var aux3 = split2[2].toInt()
                        var aux4 = split2[3].toInt()
                        if(!(aux1>=0&&aux1<=255)||!(aux2>=0&&aux2<=255)
                                || !(aux3>=0&&aux3<=255)||!(aux4>=0&&aux4<=255)){
                            errorMessage += "La dirección IP está mal formada\n"
                            alertType = Alert.AlertType.ERROR
                        }

                    }catch (e: Exception){
                        errorMessage += "La dirección IP está mal formada\n"
                        alertType = Alert.AlertType.ERROR
                    }
                }else{
                    errorMessage += "La dirección IP necesita tener 4 octetos\n"
                    alertType = Alert.AlertType.WARNING
                }
            }else{
                errorMessage += "La dirección IP no tiene el formato con mascara simplificado /(x)"
                alertType = Alert.AlertType.WARNING
            }
        }
        if(errorMessage.isEmpty()){
            valid = true
        }else{
            RootViewController.showAlert(errorMessage, "INFORMACIÓN", "", alertType)
        }
        return valid
    }
}
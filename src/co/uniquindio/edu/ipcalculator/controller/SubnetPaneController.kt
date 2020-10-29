package co.uniquindio.edu.ipcalculator.controller

import co.uniquindio.edu.ipcalculator.exceptions.MalformedIPAddress
import co.uniquindio.edu.ipcalculator.model.IPCalculator2
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TextField

/**
 * Subnet Pane Controller Class
 *
 * @author Cristian Giovanny Sánchez Pineda
 * @author Luisa Fernanda Cotte Sánchez
 */
class SubnetPaneController {

    private lateinit var ipCalculator: IPCalculator2

    @FXML
    private lateinit var ipAddressCompleteTxt: TextField

    @FXML
    private lateinit var bitQuantityFieldTxt: TextField

    @FXML
    private lateinit var principalNetAddressLabel: Label

    @FXML
    private lateinit var principalBroadcastAddressLabel: Label

    @FXML
    private lateinit var principalNumberHostLabel: Label

    @FXML
    private lateinit var principalNumberNetLabel: Label

    @FXML
    private lateinit var subnetList: ListView<String>

    @FXML
    private lateinit var subnetNumberTxt: TextField

    @FXML
    private lateinit var assignableSubnetRangeLabel: Label

    @FXML
    private lateinit var subnetRangeLabel: Label

    @FXML
    private lateinit var subnetAddressLabel: Label

    @FXML
    private lateinit var subnetBroadcastAddressLabel: Label

    @FXML
    private lateinit var hostNumberTxt: TextField

    @FXML
    private lateinit var hostAddressLabel: Label

    @FXML
    private lateinit var ipHostAddressTxt: TextField

    @FXML
    private lateinit var subnetAddressHostLabel: Label

    @FXML
    private lateinit var ipHost1Txt: TextField

    @FXML
    private lateinit var ipHost2Txt: TextField

    @FXML
    private lateinit var yesNotLabel: Label

    @FXML
    private lateinit var ipSubnetTxt: TextField

    @FXML
    private lateinit var addressQuantityTxt: TextField

    @FXML
    private lateinit var hostList: ListView<String>

    /**
     * This method allows to compare two host
     */
    @FXML
    fun analyze2Host(event: ActionEvent) {
        if(isInputInfoBonusValid2()) {
            var auxBit = bitQuantityFieldTxt.text.toInt()
            var aux = ipAddressCompleteTxt.text
            try {
                ipCalculator = IPCalculator2(aux, auxBit)
                var containsHost = ipCalculator.containsTwoHost(ipHost1Txt.text, ipHost2Txt.text)
                yesNotLabel.text = containsHost
            }catch (exception:MalformedIPAddress){
                RootViewController.showAlert(exception.message.toString(), "ERROR", "", Alert.AlertType.ERROR)
            }
        }

    }

    /**
     * this method allows to get a specific host address from a specific subnet number
     */
    @FXML
    fun analyzeAddressHost(event: ActionEvent) {
        if(isInputSpecificSubnetValid2()) {
            var auxBit = bitQuantityFieldTxt.text.toInt()
            var aux = ipAddressCompleteTxt.text
            try {
                ipCalculator = IPCalculator2(aux, auxBit)
                var getNumberHost = ipCalculator.subnetContainsHost(subnetNumberTxt.text, hostNumberTxt.text)
                hostAddressLabel.text = getNumberHost
            } catch (exception: MalformedIPAddress) {
                RootViewController.showAlert(exception.message.toString(), "ERROR", "", Alert.AlertType.ERROR)
            }
        }
    }

    /**
     * This method allow to show all basic elements from first page
     * those like subnet address, broadcast address, host quantity...
     */
    @FXML
    fun analyzeAll(event: ActionEvent) {
        if(isInputValid()) {
            var auxBit = bitQuantityFieldTxt.text.toInt()
            var aux = ipAddressCompleteTxt.text
            try {
                ipCalculator = IPCalculator2(aux, auxBit)

                var broadcast = ipCalculator.getBroadcastAddress()
                principalBroadcastAddressLabel.text = broadcast

                var quantitySubnet = ipCalculator.getSubnetQuantity()
                principalNumberNetLabel.text = quantitySubnet.toString()

                var quantityHost = ipCalculator.getHostQuantity()
                principalNumberHostLabel.text = quantityHost.toString()

                var subnetsL = ipCalculator.getSubnetBroadcastList()
                subnetList.items = FXCollections.observableList(subnetsL)
                subnetList.refresh()

                principalNetAddressLabel.text = ipCalculator.getOnlySubnetList()[0]
            }catch (exception: MalformedIPAddress){
                RootViewController.showAlert(exception.message.toString(), "ERROR", "", Alert.AlertType.ERROR)
            }
        }
    }

    /**
     * Thos method allows to know the subnet address of a host
     */
    @FXML
    fun analyzeIpHostAddress(event: ActionEvent) {
        if(isInputInfoBonusValid()) {
            var auxBit = bitQuantityFieldTxt.text.toInt()
            var aux = ipAddressCompleteTxt.text
            try {
                ipCalculator = IPCalculator2(aux, auxBit)
                var ipAddressHost = ipCalculator.getSubnet(ipHostAddressTxt.text)
                subnetAddressHostLabel.text = ipAddressHost
            }catch (exception:MalformedIPAddress){
                RootViewController.showAlert(exception.message.toString(), "ERROR", "", Alert.AlertType.ERROR)
            }
        }
    }

    /**
     * This method allows to get some host
     */
    @FXML
    fun analyzeQuantityHost(event: ActionEvent) {
        if(isInputHostListValid()) {
            var auxBit = bitQuantityFieldTxt.text.toInt()
            var aux = ipAddressCompleteTxt.text
            try{
            ipCalculator = IPCalculator2(aux, auxBit)
            var quantity = addressQuantityTxt.text.toInt()
            var hostL = ipCalculator.hostList(aux.split("/")[0])
            hostList.items = FXCollections.observableList(hostL.slice(0 until quantity))
            hostList.refresh()
            }catch (exception:MalformedIPAddress){
                RootViewController.showAlert(exception.message.toString(), "ERROR", "", Alert.AlertType.ERROR)
            }
        }
    }

    /**
     * This method allows to know some information general of a subnet address
     */
    @FXML
    fun analyzeSubnet(event: ActionEvent) {
        if(isInputSpecificSubnetValid()) {
            var auxBit = bitQuantityFieldTxt.text.toInt()
            var aux = ipAddressCompleteTxt.text
            try {
                ipCalculator = IPCalculator2(aux, auxBit)
                var subnetNumber = subnetNumberTxt.text.toInt()
                var subnetInfo = ipCalculator.getSubnetBroadcastList()[subnetNumber].split("\t\t-\t\t")
                var assignable = ipCalculator.assignableHostList(subnetNumber)

                subnetAddressLabel.text = subnetInfo[0]
                subnetBroadcastAddressLabel.text = subnetInfo[1]
                subnetRangeLabel.text = subnetInfo[0] + " - " + subnetInfo[1]
                assignableSubnetRangeLabel.text = assignable
            }catch (exception: MalformedIPAddress){
                RootViewController.showAlert(exception.message.toString(), "ERROR", "", Alert.AlertType.ERROR)

            }
        }
    }

    /**
     * This method allows to get validations for the InfoBonus tab
     */
    private fun isInputInfoBonusValid():Boolean{
        var valid = false
        var errorMessage = ""
        var alertType: Alert.AlertType = Alert.AlertType.NONE

        if(ipHostAddressTxt.text.isNullOrEmpty()){
            errorMessage += "Debes ingresar la dirección IP del host para analizarla\n"
            alertType = Alert.AlertType.WARNING
        }else{

                val split = ipHostAddressTxt.text.split(".")
                if(split.size == 4){
                    try{
                        var aux1 = split[0].toInt()
                        var aux2 = split[1].toInt()
                        var aux3 = split[2].toInt()
                        var aux4 = split[3].toInt()
                        if(aux1 !in 0..255 || aux2 !in 0..255
                                || aux3 !in 0..255 || aux4 !in 0..255){
                            errorMessage += "La dirección IP del host está mal formada\n"
                            alertType = Alert.AlertType.ERROR
                        }

                    }catch (e: Exception){
                        errorMessage += "La dirección IP del host está mal formada\n"
                        alertType = Alert.AlertType.ERROR
                    }
                }else{
                    errorMessage += "La dirección IP del host necesita tener 4 octetos\n"
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

    /**
     * This method allows to get validations for the InfoBonus tab
     */
    private fun isInputInfoBonusValid2():Boolean{
        var valid = false
        var errorMessage = ""
        var alertType: Alert.AlertType = Alert.AlertType.NONE


        if(ipHost1Txt.text.isNullOrEmpty()){
            errorMessage += "Debes ingresar la dirección IP del host 1 para analizarla\n"
            alertType = Alert.AlertType.WARNING
        }else{

            val split = ipHost1Txt.text.split(".")
            if(split.size == 4){
                try{
                    var aux1 = split[0].toInt()
                    var aux2 = split[1].toInt()
                    var aux3 = split[2].toInt()
                    var aux4 = split[3].toInt()
                    if(aux1 !in 0..255 || aux2 !in 0..255
                            || aux3 !in 0..255 || aux4 !in 0..255){
                        errorMessage += "La dirección IP del host 1 está mal formada\n"
                        alertType = Alert.AlertType.ERROR
                    }

                }catch (e: Exception){
                    errorMessage += "La dirección IP del host 1 está mal formada\n"
                    alertType = Alert.AlertType.ERROR
                }
            }else{
                errorMessage += "La dirección IP del host 1 necesita tener 4 octetos\n"
                alertType = Alert.AlertType.WARNING
            }

        }
        if(ipHost2Txt.text.isNullOrEmpty()){
            errorMessage += "Debes ingresar la dirección IP del host 2 para analizarla\n"
            alertType = Alert.AlertType.WARNING
        }else{

            val split = ipHost2Txt.text.split(".")
            if(split.size == 4){
                try{
                    var aux1 = split[0].toInt()
                    var aux2 = split[1].toInt()
                    var aux3 = split[2].toInt()
                    var aux4 = split[3].toInt()
                    if(aux1 !in 0..255 || aux2 !in 0..255
                            || aux3 !in 0..255 || aux4 !in 0..255){
                        errorMessage += "La dirección IP del host 2 está mal formada\n"
                        alertType = Alert.AlertType.ERROR
                    }

                }catch (e: Exception){
                    errorMessage += "La dirección IP del host 2 está mal formada\n"
                    alertType = Alert.AlertType.ERROR
                }
            }else{
                errorMessage += "La dirección IP del host 2 necesita tener 4 octetos\n"
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

    /**
     * This method allows to get validations for the HostList tab
     */
    private fun isInputHostListValid():Boolean{
        var valid = false
        var errorMessage = ""
        var alertType: Alert.AlertType = Alert.AlertType.NONE

        if(addressQuantityTxt.text.isNullOrEmpty()){
            errorMessage += "Debes ingresar la cantidad de direcciones IP para listarlas\n"
            alertType = Alert.AlertType.WARNING
        }else{
            try{
                addressQuantityTxt.text.toInt()
            }catch (e:Exception){
                errorMessage += "La cantidad de direcciones IP a listar deben ser dígitos\n"
                alertType = Alert.AlertType.ERROR
            }
        }

        if(errorMessage.isEmpty()){
            valid = true
        }else{
            RootViewController.showAlert(errorMessage, "INFORMACIÓN", "", alertType)
        }
        return valid
    }

    /**
     * This method allows to get validations for the SpecificSubnet tab
     */
    private fun isInputSpecificSubnetValid():Boolean{
        var valid = false
        var errorMessage = ""
        var alertType: Alert.AlertType = Alert.AlertType.NONE

        if(subnetNumberTxt.text.isNullOrEmpty()){
            errorMessage += "Debes ingresar la subred a analizar\n"
            alertType = Alert.AlertType.WARNING
        }else{
            try{
                subnetNumberTxt.text.toInt()
            }catch (e:Exception){
                errorMessage += "La subred a analizar deben ser dígitos\n"
                alertType = Alert.AlertType.ERROR
            }
        }

        if(errorMessage.isEmpty()){
            valid = true
        }else{
            RootViewController.showAlert(errorMessage, "INFORMACIÓN", "", alertType)
        }
        return valid
    }

    /**
     * This method allows to get validations for the SpecificSubnet tab
     */
    private fun isInputSpecificSubnetValid2():Boolean{
        var valid = false
        var errorMessage = ""
        var alertType: Alert.AlertType = Alert.AlertType.NONE

        if(hostNumberTxt.text.isNullOrEmpty()){
            errorMessage += "Debes ingresar el número de host que vas a analizar\n"
            alertType = Alert.AlertType.WARNING
        }else{
            try{
                hostNumberTxt.text.toInt()
            }catch (e:Exception){
                errorMessage += "Para analizar el host debes ingresar dígitos\n"
                alertType = Alert.AlertType.ERROR
            }
        }

        if(errorMessage.isEmpty()){
            valid = true
        }else{
            RootViewController.showAlert(errorMessage, "INFORMACIÓN", "", alertType)
        }
        return valid
    }

    /**
     * This method allows to get validations
     */
    private fun isInputValid():Boolean{
        var valid = false
        var errorMessage = ""
        var alertType: Alert.AlertType = Alert.AlertType.NONE
        if(ipAddressCompleteTxt.text.isNullOrEmpty()){
            errorMessage += "Debes ingresar la dirección IP\n"
            alertType = Alert.AlertType.WARNING
        }else{
            val split = ipAddressCompleteTxt.text.split("/")
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
                errorMessage += "La dirección IP no tiene el formato con mascara simplificado /(x)\n"
                alertType = Alert.AlertType.WARNING
            }
        }
        if(bitQuantityFieldTxt.text.isNullOrEmpty()){
            errorMessage += "Debes ingresar la cantidad de bits para las subredes\n"
        }else{
            try{
                bitQuantityFieldTxt.text.toInt()
            }catch (e:Exception){
                errorMessage += "La cantidad de bits deben ser dígitos\n"
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
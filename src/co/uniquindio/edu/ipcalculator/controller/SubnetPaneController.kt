package co.uniquindio.edu.ipcalculator.controller

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
        var auxBit = bitQuantityFieldTxt.text.toInt()
        var aux = ipAddressCompleteTxt.text
        ipCalculator = IPCalculator2(aux, auxBit)
        var containsHost = ipCalculator.containsHost(ipHost1Txt.text,ipHost2Txt.text)
        yesNotLabel.text = containsHost

    }

    /**
     * this method allows to get a specific address host from a specific number subnet
     */
    @FXML
    fun analyzeAddressHost(event: ActionEvent) {
        var auxBit = bitQuantityFieldTxt.text.toInt()
        var aux = ipAddressCompleteTxt.text
        ipCalculator = IPCalculator2(aux, auxBit)
        var getNumberHost = ipCalculator.subnetContainsHost(subnetNumberTxt.text,hostNumberTxt.text)
        hostAddressLabel.text = getNumberHost
    }

    /**
     * This method allow to show all basic elements from first page
     * those like address subnet, address broadcast subnet, host quantity...
     */
    @FXML
    fun analyzeAll(event: ActionEvent) {

        var auxBit = bitQuantityFieldTxt.text.toInt()
        var aux = ipAddressCompleteTxt.text
        ipCalculator = IPCalculator2(aux, auxBit)

        var broadcast = ipCalculator.getBroadcastAddress()
        principalBroadcastAddressLabel.text = broadcast

        var quantitySubnet = ipCalculator.getQuantitySubnet()
        principalNumberNetLabel.text = quantitySubnet.toString()

        var quantityHost = ipCalculator.getQuantityHost()
        principalNumberHostLabel.text = quantityHost.toString()

        var subnetsL = ipCalculator.getSubnetList()
        subnetList.items = FXCollections.observableList(subnetsL)
        subnetList.refresh()

        principalNetAddressLabel.text = ipCalculator.getOnlySubnetList()[0]
    }

    /**
     *
     */
    @FXML
    fun analyzeIpHostAddress(event: ActionEvent) {
        var auxBit = bitQuantityFieldTxt.text.toInt()
        var aux = ipAddressCompleteTxt.text
        ipCalculator = IPCalculator2(aux, auxBit)
        var ipAddressHost = ipCalculator.getSubnet(ipHostAddressTxt.text)
        subnetAddressHostLabel.text = ipAddressHost
    }

    @FXML
    fun analyzeQuantityHost(event: ActionEvent) {
        var auxBit = bitQuantityFieldTxt.text.toInt()
        var aux = ipAddressCompleteTxt.text
        ipCalculator = IPCalculator2(aux, auxBit)
        var quantity = addressQuantityTxt.text.toInt()
        var hostL = ipCalculator.hostList(aux.split("/")[0])
        hostList.items = FXCollections.observableList(hostL.slice(0 until quantity))
        hostList.refresh()
    }

    @FXML
    fun analyzeSubnet(event: ActionEvent) {

        var auxBit = bitQuantityFieldTxt.text.toInt()
        var aux = ipAddressCompleteTxt.text
        ipCalculator = IPCalculator2(aux, auxBit)
        var subnetNumber = subnetNumberTxt.text.toInt()
        var subnetInfo = ipCalculator.getSubnetList()[subnetNumber].split("\t\t-\t\t")
        var assignable = ipCalculator.assignableSubnetList(subnetNumber)

        subnetAddressLabel.text = subnetInfo[0]
        subnetBroadcastAddressLabel.text = subnetInfo[1]
        subnetRangeLabel.text = subnetInfo[0] +" - "+subnetInfo[1]
        assignableSubnetRangeLabel.text = assignable

    }

    /**
     * Intento fallido de validación
     */
    fun isInputValid(): Boolean {
        var valid = false
        var errorMessage = ""
        var alertType: Alert.AlertType = Alert.AlertType.NONE

        if (subnetNumberTxt.text.isNullOrEmpty()) {
            errorMessage += "Debes llenar todos los campos."
            alertType = Alert.AlertType.WARNING
        } else {
            if (subnetNumberTxt.text.length <= ipCalculator.getSubnetList().size && subnetNumberTxt.text.length >= 0) {
            } else {
                errorMessage += "Sobrepasa la cantidad de Subredes\n"
                alertType = Alert.AlertType.WARNING
            }
        }
        if (errorMessage.isEmpty()) {
            valid = true
        } else {
            RootViewController.showAlert(errorMessage, "INFORMACIÓN", "", alertType)
        }
        return valid
    }
}
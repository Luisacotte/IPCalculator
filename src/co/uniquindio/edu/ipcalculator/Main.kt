package co.uniquindio.edu.ipcalculator

import co.uniquindio.edu.ipcalculator.controller.RootViewController
import co.uniquindio.edu.ipcalculator.model.IPCalculator
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
/**
 * Main Class
 *
 * @author Cristian Giovanny Sánchez Pineda
 * @author Luisa Fernanda Cotte Sánchez
 */
class Main: Application() {

    override fun start(primaryStage: Stage?) {
        val loader:FXMLLoader = FXMLLoader(Main::class.java.getResource("/rootView.fxml"))
        val parent:Parent = loader.load()
        val rootView:RootViewController = loader.getController()
        val scene:Scene = Scene(parent)

        primaryStage?.scene = scene
        primaryStage?.title = "Calculadora IP"
        primaryStage?.show()
    }

    fun test(){
        var ipCalculator:IPCalculator = IPCalculator("192.35.77.15/22")
        println(ipCalculator.getNetIPAddress())
        println(ipCalculator.getSubnetMaskInDecimalFormat())
        println(ipCalculator.getBroadcastAddress())
        println(ipCalculator.getBitsNumberForTheNet())
        println(ipCalculator.getBitsNumberForTheHost())
        println(ipCalculator.getNumberOfAssignableIPs())
        println(ipCalculator.getRangeIPAddressComplete())
        println(ipCalculator.getRangeIPAddressAssignable())
        System.exit(0)
    }
    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            launch(Main::class.java)
        }
    }
}
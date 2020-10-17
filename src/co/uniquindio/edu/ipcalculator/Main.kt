package co.uniquindio.edu.ipcalculator

import co.uniquindio.edu.ipcalculator.model.IPCalculator
import javafx.application.Application
import javafx.stage.Stage

class Main: Application() {

    override fun start(primaryStage: Stage?) {
        var ipCalculator:IPCalculator = IPCalculator("173.34.64.0/19")
        println(ipCalculator.getAddress())
        println(ipCalculator.getSubnetMask())
        println(ipCalculator.getSubnetMaskInDecimalFormat())
        println(ipCalculator.getBroadcastAddress())
        System.exit(0)

    }
    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            launch(Main::class.java)
        }
    }
}
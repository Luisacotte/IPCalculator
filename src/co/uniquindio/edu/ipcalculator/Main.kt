package co.uniquindio.edu.ipcalculator

import co.uniquindio.edu.ipcalculator.model.IPCalculator
import javafx.application.Application
import javafx.stage.Stage

class Main: Application() {

    override fun start(primaryStage: Stage?) {
        var ipCalculator:IPCalculator = IPCalculator("184.35.77.15/22")
        println(ipCalculator.getNetIPAddress())
        println(ipCalculator.getSubnetMaskInDecimalFormat())
        println(ipCalculator.getBroadcastAddress())
        println(ipCalculator.getBitsNumberForTheNet())
        println(ipCalculator.getBitsNumberForTheHost())
        println(ipCalculator.getBitsNumberOfAssignableIPs())
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
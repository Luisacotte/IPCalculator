package co.uniquindio.edu.ipcalculator.model

import co.uniquindio.edu.ipcalculator.util.Util

class IPCalculator(IPAddressComplete:String) {

    private lateinit var IPAddress:String
    private var subnetMask:Int = 16

    init{
        getSubnetMaskInDecimalFormat(IPAddressComplete)
        var slashIndex = IPAddressComplete.indexOf('/')
        IPAddress = IPAddressComplete.substring(0, slashIndex)
    }

    /**
     * This method allows to
     */
    fun getSubnetMaskInDecimalFormat(addressComplete: String):Int{

        var slashIndex = addressComplete.indexOf('/')
        subnetMask = addressComplete.substring(slashIndex+1, addressComplete.length).toInt()
        return subnetMask
    }

    fun getAddress():String{
        return IPAddress
    }
    fun getSubnetMask():Int{
        return subnetMask
    }

    /**
     * This method allows to return the subnet mask in decimal format
     * @return a String with the decimal format of the subnet mask
     */
    fun getSubnetMaskInDecimalFormat():String{
        var subnetMaskString:String = ""
        for(i in 0..subnetMask){
            subnetMaskString += "1"
        }
        var iLimit:Int = 32-subnetMask
        for(i in 0..iLimit){
            subnetMaskString += "0"
        }
        var firstOctect:String = subnetMaskString.substring(0, 8)
        var secondOctect:String = subnetMaskString.substring(8,16)
        var thirdOctect:String = subnetMaskString.substring(16,24)
        var fourthOctect:String = subnetMaskString.substring(24, 32)
        return  "".plus(firstOctect.toLong(2))
                .plus(".").plus(secondOctect.toLong(2))
                .plus(".").plus(thirdOctect.toLong(2))
                .plus(".").plus(fourthOctect.toLong(2))

    }

    /**
     * This method allows to get the broadcast address on the net
     * @return the String with the correct format
     */
    fun getBroadcastAddress():String{
        var dotList:ArrayList<Int> = ArrayList()
        dotList = Util.getDotsPositions(IPAddress, dotList, 0)
        var firstOctect:String = Integer.toBinaryString(IPAddress.substring(0, dotList.get(0)).toInt())
        var secondOctect:String = Integer.toBinaryString(IPAddress.substring(dotList.get(0)+1,dotList.get(1)).toInt())
        var thirdOctect:String = Integer.toBinaryString(IPAddress.substring(dotList.get(1)+1,dotList.get(2)).toInt())
        var fourthOctect:String = Integer.toBinaryString(IPAddress.substring(dotList.get(2)+1, IPAddress.length).toInt())
        var copyAux:String = ""
        for(i in 0..((8-firstOctect.length)-1)){
            copyAux += "0"
        }
        copyAux += firstOctect
        firstOctect = copyAux

        var copyAux2:String = ""

        for(i in 0..((8-secondOctect.length)-1)){
            copyAux2 +="0"
        }
        copyAux2 += secondOctect
        secondOctect = copyAux2

        var copyAux3:String = ""
        for(i in 0..((8-thirdOctect.length)-1)){
            copyAux3 += "0"
        }
        copyAux3+= thirdOctect
        thirdOctect = copyAux3

        var copyAux4:String = ""
        for(i in 0..((8-fourthOctect.length)-1)){
            copyAux4 += 0
        }
        copyAux4 += fourthOctect
        fourthOctect = copyAux4
        var IPComplete:String = firstOctect +secondOctect + thirdOctect+fourthOctect

        var broadCastAddress: String = IPComplete.substring(0, subnetMask)
        for(i in subnetMask..32){
            broadCastAddress+= "1"
        }
        var firstOctect2:String = broadCastAddress.substring(0, 8)
        var secondOctect2:String = broadCastAddress.substring(8,16)
        var thirdOctect2:String = broadCastAddress.substring(16,24)
        var fourthOctect2:String = broadCastAddress.substring(24, 32)
        return  "".plus(firstOctect2.toLong(2))
                .plus(".").plus(secondOctect2.toLong(2))
                .plus(".").plus(thirdOctect2.toLong(2))
                .plus(".").plus(fourthOctect2.toLong(2))

    }

}
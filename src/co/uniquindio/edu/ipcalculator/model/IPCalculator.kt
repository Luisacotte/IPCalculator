package co.uniquindio.edu.ipcalculator.model
import co.uniquindio.edu.ipcalculator.exceptions.MalformedIPAddress
import co.uniquindio.edu.ipcalculator.util.Util
import kotlin.math.pow

/**
 * This Class allows operations with subnets to work
 *
 * @author Cristian Giovanny Sánchez Pineda
 * @author Luisa Fernanda Cotte Sánchez
 */
class IPCalculator(IPAddressComplete:String) {

    private lateinit var IPAddress:String
    private var subnetMask:Int = 16
    private var bit: Int = 12

    init{
        setIPAddressAndSubnetMask(IPAddressComplete)
    }

    /**
     * This method allows to set the net address and the subnet mask only if they are well formed
     * @throws MalformedIPAddress if: 1 the complete address is null or empty
     *                                2 the complete address does not contains the / character
     *                                3 some of the octects of the net address are major than 255
     *                                4 the subnet mask is not on the range of [8, 255]
     */
    @Throws(MalformedIPAddress::class)
    private fun setIPAddressAndSubnetMask(ipAddressComplete:String){
        if(!ipAddressComplete.isNullOrEmpty()){
            if(ipAddressComplete.contains("/")){
                var slashIndex = ipAddressComplete.indexOf('/')
                IPAddress = ipAddressComplete.substring(0, slashIndex)
                val array = IPAddress.split(".")
                if(array[0].toInt()<=255&&array[1].toInt()<=255
                        &&array[2].toInt()<=255
                        &&array[3].toInt()<=255) {
                    var auxSubnetMask = ipAddressComplete.substring(slashIndex + 1, ipAddressComplete.length).toInt()
                    if (auxSubnetMask >= 8 && auxSubnetMask <= 32) {
                        subnetMask = auxSubnetMask
                    } else {
                        throw MalformedIPAddress("La máscara de subred debe estar en el rango de 8 a 32")
                    }
                }else{
                    throw MalformedIPAddress("La dirección IP está mal formada")
                }
            }
        }else{
            throw MalformedIPAddress("Debes ingresar la IP")
        }
    }

    /**
     * This method allows to get the net IP address
     * @return a String with the net IP address
     */
    fun getNetIPAddress():String{
        var dotList:ArrayList<Int> = ArrayList()
        dotList = Util.getDotsPositions(IPAddress, dotList, 0)
        var firstOctect:String = Integer.toBinaryString(IPAddress.substring(0, dotList.get(0)).toInt())
        var secondOctect:String = Integer.toBinaryString(IPAddress.substring(dotList.get(0)+1,dotList.get(1)).toInt())
        var thirdOctect:String = Integer.toBinaryString(IPAddress.substring(dotList.get(1)+1,dotList.get(2)).toInt())
        var fourthOctect:String = Integer.toBinaryString(IPAddress.substring(dotList.get(2)+1, IPAddress.length).toInt())
        var ipAddress:String = firstOctect+secondOctect+thirdOctect+fourthOctect
        var subnetMaskString:String = ""
        for(i in 0..subnetMask){
            subnetMaskString += "1"
        }
        var iLimit:Int = 32-subnetMask
        for(i in 0..iLimit){
            subnetMaskString += "0"
        }
        var firstOctect2:String = subnetMaskString.substring(0, 8)
        var secondOctect2:String = subnetMaskString.substring(8,16)
        var thirdOctect2:String = subnetMaskString.substring(16,24)
        var fourthOctect2:String = subnetMaskString.substring(24, 32)
        var subnetMask:String = firstOctect2+secondOctect2+thirdOctect2+fourthOctect2
        var netIPAddress:String = ""

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
        ipAddress= firstOctect +secondOctect + thirdOctect+fourthOctect
        for(i in 0..(subnetMask.length-1)){
            if(subnetMask[i]=='1'){
                netIPAddress += ipAddress[i]
            }else{
                netIPAddress += "0"
            }
        }
        var firstOctect3:String = netIPAddress.substring(0, 8)
        var secondOctect3:String = netIPAddress.substring(8,16)
        var thirdOctect3:String = netIPAddress.substring(16,24)
        var fourthOctect3:String = netIPAddress.substring(24, 32)
        return  "".plus(firstOctect3.toLong(2))
                .plus(".").plus(secondOctect3.toLong(2))
                .plus(".").plus(thirdOctect3.toLong(2))
                .plus(".").plus(fourthOctect3.toLong(2))
    }

    /**
     * This method allows to get the subnetMask
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
    fun setBits(bit:Int){
       this.bit = bit
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
        var ipAddress:String = getNetIPAddress()
        dotList = Util.getDotsPositions(ipAddress, dotList, 0)
        var firstOctect:String = Integer.toBinaryString(ipAddress.substring(0, dotList.get(0)).toInt())
        var secondOctect:String = Integer.toBinaryString(ipAddress.substring(dotList.get(0)+1,dotList.get(1)).toInt())
        var thirdOctect:String = Integer.toBinaryString(ipAddress.substring(dotList.get(1)+1,dotList.get(2)).toInt())
        var fourthOctect:String = Integer.toBinaryString(ipAddress.substring(dotList.get(2)+1, ipAddress.length).toInt())
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

    /**
     * This method allows to get the bits number of the net
     * @return an int to the bits number for the net
     */
    fun getBitsNumberForTheNet():Int{
        return subnetMask
    }

    /**
     * This method allows to get the bits number of the host
     * @return an int with the bits number of the host
     */
    fun getBitsNumberForTheHost():Int{
        return 32-subnetMask
    }

    /**
     * This method allows to get the bits number of the assignable IP addresses
     * @return an int with the assignable IP addresses
     */
    fun getNumberOfAssignableIPs():Int{
        var bitsForTheHost:Int = getBitsNumberForTheHost()
        var assignableIPs:Int = 0
        if(bitsForTheHost>1){
            assignableIPs = (2.0.pow(bitsForTheHost)-2).toInt()
        }
        return assignableIPs
    }

    /**
     * This method allows to get the complete range of the IP addresses
     * @return a String with the range of the IP addresses
     */
    fun getRangeIPAddressComplete():String{
        return getNetIPAddress()+" - "+getBroadcastAddress()
    }

    /**
     * This method allows to get the IP Assignable range
     */
    fun getRangeIPAddressAssignable():String{
        var ipAddress:String = getNetIPAddress()
        var broadcastAddress:String = getBroadcastAddress()
        var dotList:ArrayList<Int> = ArrayList()
        dotList = Util.getDotsPositions(ipAddress, dotList, 0)
        var dotList2:ArrayList<Int> = ArrayList()
        dotList2 = Util.getDotsPositions(broadcastAddress, dotList2, 0)
        var lowerLimit:Int = ipAddress.substring(dotList[dotList.size-1]+1, ipAddress.length).toInt() +1
        var upperLimit:Int = broadcastAddress.substring(dotList2[dotList2.size-1]+1, broadcastAddress.length).toInt()-1
        return ipAddress.substring(0, dotList.get(dotList.size-1))+"."+lowerLimit+
                " - "+broadcastAddress.substring(0,dotList2.get(dotList2.size-1))+"."+upperLimit
    }

    /**
     * This method allows to get the subnets quantity IPC2
     * @return the subnets quantity
     */
    fun getSubnetQuantity(): Int {
        return 2.toDouble().pow(bit).toInt()
    }
    /**
     * This method allows to get the host quantity IPC2
     * @return the host quantity by subnet
     */
    fun getHostQuantity(): Int {
        var mask = subnetMask
        var bitHost = 32 - (mask + bit)
        return 2.toDouble().pow(bitHost).toInt()
    }
    /**
     * This method allows to get the subnet and broadcast list IPC2
     * @return the list with both elements
     */
    fun getSubnetBroadcastList(): ArrayList<String> {
        var array = ArrayList<String>()
        array.add("Dirección de Subred\t\t-\t\tDirección de Broadcast")
        var address = convertToBinary(IPAddress)
        var a = address.substring(0,subnetMask)

        for (i in 0 until getSubnetQuantity()){
            var bina = Integer.toBinaryString(i)
            var complete = completeZeros(bina,bit)
            complete = a + complete

            while (complete.length < 32){
                complete += "0"
            }
            var netIP = binaryAddressToIpAddress(complete)
            var result = netIP + "\t\t-\t\t" + getBroadcastBySubnet(netIP)
            array.add(result)
        }
        return array
    }
    /**
     * This method allows to get only the subnet list IPC2
     * @return the list with only subnets
     */
    fun getOnlySubnetList(): ArrayList<String> {
        var array = ArrayList<String>()
        var address = convertToBinary(IPAddress)
        var a = address.substring(0,subnetMask)

        for (i in 0 until getSubnetQuantity()){
            var bina = Integer.toBinaryString(i)
            var complete = completeZeros(bina,bit)
            complete = a + complete

            while (complete.length < 32){
                complete += "0"
            }
            var netIP = binaryAddressToIpAddress(complete)
            var result = netIP
            array.add(result)
        }
        return array
    }
    /**
     * This method allows complete with zeros IPC2
     * @return an address with some zeros
     */
    private fun completeZeros(binary:String, digit:Int):String{
        var newBinary = binary
        while (newBinary.length < digit){
            newBinary = "0$newBinary"
        }
        return newBinary
    }
    /**
     * This method allows to convert the ip address to binary IPC2
     * @return the binary ip address
     */
    private fun convertToBinary(address: String): String {
        var ipAddressBinary = ""
        val partition = address.split(".")
        for (i in partition) {
            val aux = i.toInt()
            var bin = Integer.toBinaryString(aux)
            while (bin.length < 8) {
                bin = "0$bin"
            }
            ipAddressBinary += bin
        }
        return ipAddressBinary
    }
    /**
     * This method allows to get the broadcast address subnet IPC2
     * @return the broadcast address by subnet address
     */
    private fun getBroadcastBySubnet(subnet:String): String {
        var broadcastBinary = ""
        val ipAddressBinary = convertToBinary(subnet)
        for (i in ipAddressBinary.indices) broadcastBinary += if (i < bit+subnetMask) {
            ipAddressBinary[i]
        } else {
            '1'
        }
        return binaryAddressToIpAddress(broadcastBinary)
    }
    /**
     * This method allows to convert the binary ip address to normal ip address IPC2
     * @return the ip address
     */
    private fun binaryAddressToIpAddress(bbinary: String): String {
        var maskDecimal = ""
        for (i in 0..3) {
            val binary = bbinary.substring((i * 8) + 0, (i * 8) + 8)
            maskDecimal += binary.toInt(2)
            if (i < 3) {
                maskDecimal += '.'
            }
        }
        return maskDecimal
    }
    /**
     * This method allows to get the assignable host list IPC2
     * @return the list with assignable host by subnet
     */
    fun assignableHostList(subnetNumber: Int): String {
        var array = getSubnetBroadcastList()[subnetNumber].split("\t\t-\t\t")
        var first = array[0].split(".")
        var second = array[1].split(".")
        var net = first[3].toInt()+1
        var broad = second[3].toInt()-1
        var assignable = first[0] + "." + first[1] + "." + first[2] + "." + net.toString() + " - " +
                second[0] + "." + second[1] + "." + second[2] + "." + broad.toString()
        return assignable
    }
    /**
     * This method allows to get the host list IPC2
     * @return the list with host by subnet
     */
    fun hostList(subnet:String): List<String> {
        var array = ArrayList<String>()
        var bin = convertToBinary(subnet)
        var a =  bin.substring(0,subnetMask+bit)
        for (i in 0 until getHostQuantity()){
            var converte = Integer.toBinaryString(i)
            var complete = completeZeros(converte,32-(bit+subnetMask))
            array.add(binaryAddressToIpAddress(a+complete))
        }
        return array
    }
    /**
     * This method allows to get one address host from one number subnet IPC2
     * @return the address host
     */
    fun subnetContainsHost(subnet:String,host:String):String{
        var net = getOnlySubnetList()[subnet.toInt()-1]
        var host1 = hostList(net)[host.toInt()-1]
        return host1
    }
    /**
     * This method allows to know if two host belong the same subnet IPC2
     * @return "yes" if two host belong the same subnet or "no"
     */
    fun containsTwoHost(host1:String, host2:String):String{
        if(getSubnet(host1) == getSubnet(host2)){
            return "SI"
        }
        return "NO"
    }
    /**
     * This method allows to get the subnet from one address IPC2
     * @return the subnet by ip address
     */
    fun getSubnet(ip:String):String{
        var broadcastBinary = ""
        val ipAddressBinary = convertToBinary(ip)
        for (i in ipAddressBinary.indices) broadcastBinary += if (i < bit+subnetMask) {
            ipAddressBinary[i]
        } else {
            '0'
        }
        return binaryAddressToIpAddress(broadcastBinary)
    }

}
package co.uniquindio.edu.ipcalculator.util

class Util {
    companion object{
        @JvmStatic
        fun getDotsPositions(IPAddress:String, list:ArrayList<Int>, i:Int):ArrayList<Int>{
            if(i==IPAddress.length){
                return list
            }
            if(IPAddress[i]=='.'){
                list.add(i)
            }
            return getDotsPositions(IPAddress, list, i+1)
        }
    }
}
package com.example.moonreadproject


interface customerID {

    companion object{
        var id :Int = 0
        var order_id : Int=0

        fun setData(id : Int) {
            this.id = id
        }
        fun setOrderID(order_id:Int){
            this.order_id=order_id
        }
    }


}
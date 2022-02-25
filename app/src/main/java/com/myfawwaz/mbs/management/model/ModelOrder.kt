package com.myfawwaz.mbs.management.model

import java.io.Serializable

class ModelOrder : Serializable {
    @JvmField
    var order_id: String? = null

    @JvmField
    var customer_id: String? = null

    @JvmField
    var firstname: String? = null

    @JvmField
    var lastname: String? = null

    @JvmField
    var telephone: String? = null

    @JvmField
    var date_added: String? = null

    @JvmField
    var payment_address_1: String? = null

    @JvmField
    var total: String? = null
   var totalb: String? = null

}

annotation class JvmField
/* "order_id": "107",
        "customer_id": "0 62085729232960",
        "firstname": "Fitri",
        "lastname": "",
        "telephone": "62085729232960",
        "date_added": "2021-04-06 10:06:18",
        "payment_address_1": "Kenjer rt01\/06",
        "total": "141000.0000",*/

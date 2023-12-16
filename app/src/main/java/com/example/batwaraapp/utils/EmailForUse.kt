package com.example.batwaraapp.utils

object EmailForUse {
    fun getFirstPartEmail(currEmail: String) : String {
        var tmp = ""
        for(i in currEmail) {
            if(i == '@') break
            else tmp += i
        }
        return tmp
    }
}
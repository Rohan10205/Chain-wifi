package com.chainnet.network.discovery

import org.json.JSONObject

class QrCodeManager {
    fun encodeCredentials(ssid: String, passphrase: String): String {
        val json = JSONObject()
        json.put("ssid", ssid)
        json.put("passphrase", passphrase)
        return json.toString()
    }

    fun decodeCredentials(payload: String): Pair<String, String> {
        val json = JSONObject(payload)
        return json.getString("ssid") to json.getString("passphrase")
    }
}

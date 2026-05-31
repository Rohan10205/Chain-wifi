package com.chainnet.network.routing

class NatConfigurator {
    fun applyNatRules(upstreamInterface: String, downstreamInterface: String) {
        ProcessBuilder(
            "sh", "-c",
            "iptables -t nat -A POSTROUTING -o ${'$'}upstreamInterface -j MASQUERADE && " +
                "iptables -A FORWARD -i ${'$'}downstreamInterface -o ${'$'}upstreamInterface -j ACCEPT"
        ).start()
    }

    fun clearNatRules(upstreamInterface: String, downstreamInterface: String) {
        ProcessBuilder(
            "sh", "-c",
            "iptables -t nat -D POSTROUTING -o ${'$'}upstreamInterface -j MASQUERADE && " +
                "iptables -D FORWARD -i ${'$'}downstreamInterface -o ${'$'}upstreamInterface -j ACCEPT"
        ).start()
    }
}

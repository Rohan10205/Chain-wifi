package com.chainnet.network.control

enum class ControlPlaneMessageType(val code: Byte) {
    HELLO(1),
    TOPOLOGY_UPDATE(2),
    ROUTE_REQUEST(3),
    ROUTE_REPLY(4),
    KEEPALIVE(5),
    KEEPALIVE_ACK(6),
    CREDENTIAL_SHARE(7),
    HANDOFF(8);

    companion object {
        fun fromCode(code: Byte): ControlPlaneMessageType? = values().firstOrNull { it.code == code }
    }
}

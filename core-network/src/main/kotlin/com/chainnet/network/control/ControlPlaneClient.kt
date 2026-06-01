package com.chainnet.network.control

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class ControlPlaneClient(private val host: String, private val port: Int = 47800) {
    fun send(frame: ControlPlaneFrame) {
        Socket(host, port).use { socket ->
            DataOutputStream(socket.getOutputStream()).use { output ->
                ControlPlaneCodec.writeFrame(output, frame)
            }
        }
    }

    fun readOnce(): ControlPlaneFrame? {
        Socket(host, port).use { socket ->
            DataInputStream(socket.getInputStream()).use { input ->
                return ControlPlaneCodec.readFrame(input)
            }
        }
    }
}

package com.chainnet.network.control

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ServerSocket
import java.net.Socket

class ControlPlaneServer(
    private val port: Int = 47800,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
) {
    private var serverSocket: ServerSocket? = null
    private var acceptJob: Job? = null
    private val _frames = MutableSharedFlow<ControlPlaneFrame>(extraBufferCapacity = 32)
    val frames: SharedFlow<ControlPlaneFrame> = _frames

    fun start() {
        if (acceptJob != null) return
        acceptJob = scope.launch {
            serverSocket = ServerSocket(port)
            while (true) {
                val socket = serverSocket?.accept() ?: break
                handleClient(socket)
            }
        }
    }

    fun stop() {
        acceptJob?.cancel()
        acceptJob = null
        serverSocket?.close()
        serverSocket = null
    }

    fun sendFrame(socket: Socket, frame: ControlPlaneFrame) {
        DataOutputStream(socket.getOutputStream()).use { output ->
            ControlPlaneCodec.writeFrame(output, frame)
        }
    }

    private fun handleClient(socket: Socket) {
        scope.launch {
            socket.use {
                val input = DataInputStream(it.getInputStream())
                while (true) {
                    val frame = ControlPlaneCodec.readFrame(input) ?: break
                    _frames.tryEmit(frame)
                }
            }
        }
    }
}

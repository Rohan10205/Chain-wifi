package com.chainnet.network.control

import java.io.DataInputStream
import java.io.DataOutputStream

object ControlPlaneCodec {
    private const val MAGIC = 0xCE11

    fun writeFrame(output: DataOutputStream, frame: ControlPlaneFrame) {
        output.writeShort(MAGIC)
        output.writeByte(frame.type.code.toInt())
        output.writeShort(frame.payload.size)
        output.write(frame.payload)
        output.flush()
    }

    fun readFrame(input: DataInputStream): ControlPlaneFrame? {
        val magic = input.readUnsignedShort()
        if (magic != MAGIC) {
            return null
        }
        val typeCode = input.readByte()
        val length = input.readUnsignedShort()
        val payload = ByteArray(length)
        input.readFully(payload)
        val type = ControlPlaneMessageType.fromCode(typeCode) ?: return null
        return ControlPlaneFrame(type, payload)
    }
}

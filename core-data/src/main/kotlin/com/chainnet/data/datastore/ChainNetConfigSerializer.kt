package com.chainnet.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object ChainNetConfigSerializer : Serializer<ChainNetConfig> {
    override val defaultValue: ChainNetConfig = ChainNetConfig.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ChainNetConfig {
        try {
            return ChainNetConfig.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Unable to read ChainNetConfig", exception)
        }
    }

    override suspend fun writeTo(t: ChainNetConfig, output: OutputStream) {
        t.writeTo(output)
    }
}

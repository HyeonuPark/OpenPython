package kr.pe.ecmaxp.openpie.arch.utils.msgpack

import li.cil.oc.api.machine.Signal
import org.msgpack.core.MessagePack


class MsgpackPacker {
    val packer = MessagePack.newDefaultBufferPacker()!!

    fun toByteArray(): ByteArray = packer.toByteArray()

    fun pack(o: Any?) {
        packer.apply {
            when (o) {
                null -> packNil()
                is Boolean -> packBoolean(o)
                is Int -> packInt(o)
                is Long -> packLong(o)
                is Float -> packFloat(o)
                is Double -> packDouble(o)
                is String -> packString(o)
                is ByteArray -> {
                    packBinaryHeader(o.size)
                    writePayload(o)
                }
                is Array<*> -> {
                    packArrayHeader(o.size)
                    for (item in o)
                        pack(item)
                }
                is Collection<*> -> {
                    packArrayHeader(o.size)
                    for (item in o)
                        pack(item)
                }
                is Map<*, *> -> {
                    packMapHeader(o.size)
                    for (pair in o) {
                        pack(pair.key)
                        pack(pair.value)
                    }
                }
                is Signal -> {
                    packArrayHeader(2)
                    packString(o.name())
                    packArrayHeader(o.args().size)
                    for (item in o.args()) {
                        pack(item)
                    }
                }
                is Throwable -> {
                    packArrayHeader(2)
                    packString(o.javaClass.canonicalName)
                    packString(o.toString())
                }
                else -> {
                    val num = o.toString().toIntOrNull()
                    if (num != null)
                        packInt(num) // li.cil.oc.server.component.HandleValue
                    else
                        throw Exception("mismatch type ${o.javaClass} => $o")
                }
            }
        }
    }
}
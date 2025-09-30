package com.knowledge.get.controller

import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.KClass

fun maskObject(obj: Any?, fields: Set<String>): Any? {
    if (obj == null) return null

    val kClass = obj::class

    // If it is a primitive type or String, just return as is (masking will happen via field name)
    if (kClass.isSubclassOf(String::class) ||
        kClass.java.isPrimitive ||
        obj is Number ||
        obj is Boolean
    ) return obj

    // Handle collections
    if (obj is Iterable<*>) return obj.map { maskObject(it, fields) }
    if (obj is Map<*, *>) return obj.mapValues { maskObject(it.value, fields) }

    // Must be a data class
    val copyFunc = kClass.declaredFunctions.find { it.name == "copy" } ?: return obj
    val args: MutableMap<KParameter, Any?> = mutableMapOf()

    copyFunc.parameters.forEach { param ->
        when (param.kind) {
            KParameter.Kind.INSTANCE -> args[param] = obj
            KParameter.Kind.VALUE -> {
                val name = param.name
                val prop = kClass.memberProperties.find { it.name == name } as? KProperty1<Any, Any?>
                val value = prop?.get(obj)

                args[param] = when {
                    name != null && name in fields -> "****" // mask field
                    else -> maskObject(value, fields)        // recursively mask nested objects
                }
            }
            else -> {}
        }
    }

    return copyFunc.callBy(args)
}

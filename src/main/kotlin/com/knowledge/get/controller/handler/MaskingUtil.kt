package com.knowledge.get.controller.handler

import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

object MaskingUtil {

    fun maskObject(obj: Any?, fieldsToMask: Set<String>): Any? {
        if (obj == null) return null

        // Простий тип — повертаємо як є
        if (isSimpleType(obj)) return obj

        return when (obj) {
            is Collection<*> -> obj.map { maskObject(it, fieldsToMask) }
            is Map<*, *> -> obj.mapValues { (_, v) -> maskObject(v, fieldsToMask) }
            else -> maskDataClass(obj, fieldsToMask)
        }
    }

    private fun maskDataClass(obj: Any, fieldsToMask: Set<String>): Any {
        val kClass = obj::class
        val constructor = kClass.primaryConstructor ?: return obj

        // Створюємо копію data-класу з рекурсивно замаскованими полями
        val args = constructor.parameters.associateWith { param ->
            val prop = kClass.memberProperties.firstOrNull { it.name == param.name }
            val value = prop?.getter?.call(obj)

            when {
                param.name in fieldsToMask -> "***" // Маскуємо поле
                else -> maskObject(value, fieldsToMask) // Рекурсивно заходимо у вкладений об’єкт
            }
        }

        return constructor.callBy(args)
    }

    private fun isSimpleType(obj: Any): Boolean {
        return obj is String ||
                obj is Number ||
                obj is Boolean ||
                obj is Char ||
                obj.javaClass.isPrimitive ||
                obj.javaClass.`package`?.name?.startsWith("java.time") == true // LocalDate, LocalDateTime тощо
    }
}

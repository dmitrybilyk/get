package com.knowledge.get.controller.on_field

import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier


class MaskingBeanSerializerModifier : BeanSerializerModifier() {
    private val maskingSerializer = MaskingSerializer()

    init {
        println("MaskingBeanSerializerModifier created")
    }

    override fun changeProperties(
        config: SerializationConfig,
        beanDesc: BeanDescription,
        beanProperties: MutableList<BeanPropertyWriter>
    ): MutableList<BeanPropertyWriter> {
//        println("changeProperties for ${beanDesc.name}")
        beanProperties.forEach { writer ->
            val hasMasked = writer.member?.getAnnotation(Masked::class.java) != null
            println("  property=${writer.name} masked=$hasMasked")
            if (hasMasked && writer.type.rawClass == String::class.java) {
                writer.assignSerializer(maskingSerializer)
            }
        }
        return beanProperties
    }
}
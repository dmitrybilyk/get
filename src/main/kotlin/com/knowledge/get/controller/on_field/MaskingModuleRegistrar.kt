package com.knowledge.get.controller.on_field//package com.knowledge.get.controller// MaskingModuleRegistrar.kt
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.databind.module.SimpleModule
//import org.springframework.stereotype.Component
//import javax.annotation.PostConstruct
//
//@Component
//class MaskingModuleRegistrar(private val objectMapper: ObjectMapper) {
//
//    @PostConstruct
//    fun register() {
//        println("MaskingModuleRegistrar.register() registering masking-module")
//        val module = SimpleModule("masking-module")
//        module.setSerializerModifier(MaskingBeanSerializerModifier())
//        objectMapper.registerModule(module)
//        println("masking-module registered on ObjectMapper")
//    }
//}

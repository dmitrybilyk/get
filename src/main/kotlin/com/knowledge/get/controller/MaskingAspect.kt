//package com.knowledge.get.controller
//
//import org.aspectj.lang.ProceedingJoinPoint
//import org.aspectj.lang.annotation.Around
//import org.aspectj.lang.annotation.Aspect
//import org.springframework.stereotype.Component
//import reactor.core.publisher.Flux
//import reactor.core.publisher.Mono
//
//@Aspect
//@Component
//class MaskingAspect {
//
//    @Around("@annotation(maskFields)")
//    fun maskFields(joinPoint: ProceedingJoinPoint, maskFields: MaskFields): Any? {
//        val result = joinPoint.proceed() // call the controller method
//
//        val fieldsToMask = maskFields.value.toSet()
//
//        return when (result) {
//            is Mono<*> -> result.map { maskObject(it, fieldsToMask) }
//            is Flux<*> -> result.map { maskObject(it, fieldsToMask) }
//            else -> maskObject(result, fieldsToMask)
//        }
//    }
//}

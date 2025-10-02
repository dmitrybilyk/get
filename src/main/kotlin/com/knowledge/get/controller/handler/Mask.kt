package com.knowledge.get.controller.handler

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Mask(val fields: Array<String>)

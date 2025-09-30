package com.knowledge.get.controller

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class MaskFields(val value: Array<String>)

package com.knowledge.get.patterns.creational.singleton

class JavaLikeSingleton private constructor() {

    private object HOLDER {
        val INSTANCE = JavaLikeSingleton()
    }

    companion object {
        val instance: JavaLikeSingleton by lazy { HOLDER.INSTANCE }
    }
}
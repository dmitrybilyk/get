package com.knowledge.get.controller.handler

import org.springframework.core.MethodParameter
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.reactive.HandlerResult
import org.springframework.web.reactive.HandlerResultHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


//@Component
class MaskingResultHandler(
    private val delegates: List<HandlerResultHandler>
) : HandlerResultHandler, Ordered {

    // find the ResponseBodyResultHandler
    private val delegate: HandlerResultHandler =
        delegates.first { it::class.java.simpleName == "ResponseBodyResultHandler" }

    override fun supports(result: HandlerResult): Boolean =
        delegate.supports(result)

    override fun handleResult(exchange: ServerWebExchange, result: HandlerResult): Mono<Void> {
        val returnType = result.returnTypeSource
        val method = (returnType as? MethodParameter)?.method
        val maskAnnotation = method?.getAnnotation(Mask::class.java)

        if (maskAnnotation == null) {
            return delegate.handleResult(exchange, result)
        }

        val fieldsToMask = maskAnnotation.fields.toSet()
        val returnValue = result.returnValue

        val maskedValue = when (returnValue) {
            is Mono<*> -> returnValue.map { MaskingUtil.maskObject(it, fieldsToMask) }
            is Flux<*> -> returnValue.map { MaskingUtil.maskObject(it, fieldsToMask) }
            else -> MaskingUtil.maskObject(returnValue, fieldsToMask)
        }

        val newResult = HandlerResult(
            result.handler,
            maskedValue,
            result.returnTypeSource,
            result.bindingContext
        )

        return delegate.handleResult(exchange, newResult)
    }

    override fun getOrder(): Int {
        return -1
    }
}
package br.com.academy.mercadolivro.aspect.advice

import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Aspect
@Component
class LoggerAdvice() {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)


}
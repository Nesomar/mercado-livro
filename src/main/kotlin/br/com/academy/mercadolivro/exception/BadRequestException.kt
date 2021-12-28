package br.com.academy.mercadolivro.exception

class BadRequestException(override val message: String, val errorCode: String): Exception()
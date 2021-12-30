package br.com.academy.mercadolivro.security

import br.com.academy.mercadolivro.controller.request.LoginRequest
import br.com.academy.mercadolivro.exception.AuthenticationException
import br.com.academy.mercadolivro.service.CustomerService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val customerService: CustomerService
) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {
        try {

            val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java)

            val idUser = customerService.findByEmail(loginRequest.email)?.id

            val authToken = UsernamePasswordAuthenticationToken(idUser, loginRequest.password)

            return authenticationManager.authenticate(authToken)
        } catch (exception: Exception) {
            throw AuthenticationException("Falha ao tentar autenticar", "999")
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val idUser = (authResult.principal as UserCustomDetails).id

        response.addHeader("Authorization", "123456")
    }
}
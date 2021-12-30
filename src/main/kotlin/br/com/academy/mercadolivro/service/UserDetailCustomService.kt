package br.com.academy.mercadolivro.service

import br.com.academy.mercadolivro.exception.AuthenticationException
import br.com.academy.mercadolivro.security.UserCustomDetails
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailCustomService(@Lazy private val customerService: CustomerService) : UserDetailsService {

    override fun loadUserByUsername(idUser: String): UserDetails {

        val customer = customerService.findById(idUser.toInt())
            .orElseThrow { AuthenticationException("Usuário não encontrado", "999") }

        return UserCustomDetails(customer)
    }
}
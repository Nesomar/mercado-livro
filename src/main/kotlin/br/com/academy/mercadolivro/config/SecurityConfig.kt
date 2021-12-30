package br.com.academy.mercadolivro.config

import br.com.academy.mercadolivro.security.AuthenticationFilter
import br.com.academy.mercadolivro.service.CustomerService
import br.com.academy.mercadolivro.service.UserDetailCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Lazy private val customerService: CustomerService,
    private val userDetailCustomService: UserDetailCustomService
) :
    WebSecurityConfigurerAdapter() {

    private val publicPostMatchers = arrayOf("/customers")

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailCustomService).passwordEncoder(bCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, *publicPostMatchers).permitAll()
            .anyRequest().authenticated()
        http.addFilter(AuthenticationFilter(authenticationManager(), customerService))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()
}
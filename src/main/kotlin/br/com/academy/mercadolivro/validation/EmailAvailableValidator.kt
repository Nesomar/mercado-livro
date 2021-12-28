package br.com.academy.mercadolivro.validation

import br.com.academy.mercadolivro.service.CustomerService
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class EmailAvailableValidator(var customerService: CustomerService): ConstraintValidator<EmailAvailable, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        var isValid = true
        if (value.isNullOrEmpty()) {
            isValid = false
        }
        return customerService.emailAvailable(value)
    }
}
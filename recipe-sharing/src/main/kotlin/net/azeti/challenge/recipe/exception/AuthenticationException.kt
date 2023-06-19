package net.azeti.challenge.recipe.exception

import org.springframework.security.core.AuthenticationException

class AuthenticationException(override val message: String?): AuthenticationException(message) {
}
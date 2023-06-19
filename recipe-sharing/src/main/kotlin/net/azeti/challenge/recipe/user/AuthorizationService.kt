package net.azeti.challenge.recipe.user

import org.springframework.security.core.userdetails.UserDetailsService




interface AuthorizationService {
    fun userDetailsService(): UserDetailsService
}
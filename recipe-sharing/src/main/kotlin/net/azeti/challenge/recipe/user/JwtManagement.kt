package net.azeti.challenge.recipe.user

import org.springframework.security.core.userdetails.UserDetails

interface JwtManagement {

    fun extractUserName(token: String): String

    fun generateToken(userDetails: UserDetails): String

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean
}
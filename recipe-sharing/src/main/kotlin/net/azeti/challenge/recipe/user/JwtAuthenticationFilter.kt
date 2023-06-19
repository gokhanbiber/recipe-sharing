package net.azeti.challenge.recipe.user

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtAuthenticationFilter(
        private val jwtHandler: JwtHandler,
        private val authorizationHandler: AuthorizationHandler): OncePerRequestFilter() {

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain) {

        val authHeader: String? = request.getHeader("Authorization")
        val userEmail: String
        if (!StringUtils.hasLength(authHeader) ||
                !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        val jwt: String = authHeader!!.substring(7)
        userEmail = jwtHandler.extractUserName(jwt)
        if (StringUtils.hasLength(userEmail)
                && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = authorizationHandler.userDetailsService()
                    .loadUserByUsername(userEmail)
            if (jwtHandler.isTokenValid(jwt, userDetails)) {
                val context = SecurityContextHolder.createEmptyContext()
                val authToken = UsernamePasswordAuthenticationToken(
                        userDetails.username, null, userDetails.authorities)
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                context.authentication = authToken
                SecurityContextHolder.setContext(context)
            }
        }
        filterChain.doFilter(request, response)
    }
}
package net.azeti.challenge.recipe.user

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthorizationHandler(private var userRepository: UserRepository): AuthorizationService {
    override fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            userRepository.findByUsername(username)
                    .orElseThrow { UsernameNotFoundException("User not found") }
        }
    }
}
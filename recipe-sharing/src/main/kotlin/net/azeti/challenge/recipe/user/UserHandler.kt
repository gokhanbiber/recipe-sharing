package net.azeti.challenge.recipe.user

import net.azeti.challenge.recipe.exception.AuthenticationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserHandler(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val jwtHandler: JwtHandler,
        private val authenticationManager: AuthenticationManager): UserManagement {

    override fun register(registration: Registration) {

        val encodedRegistration = Registration(
                email = registration.email,
                username = registration.username,
                password = passwordEncoder.encode(registration.password),
                role = registration.role
                )

        userRepository.save(encodedRegistration);
        return;
    }
/*
    override fun login(login: Login): Token {
        val user = userRepository.findByUsername(login.username)
                .orElseThrow{UsernameNotFoundException("User not found")};

        if (passwordEncoder.matches(login.password, user.password)) {
            return generateAccessToken(user.id);
        }
        throw AuthenticationException("Unauthorized");
    }

 */

    override fun login(request: Login): Token {
        //authenticationManager.authenticate(
             //   UsernamePasswordAuthenticationToken(request.username, request.password))
        val user = userRepository.findByUsername(request.username)
                .orElseThrow { IllegalArgumentException("Invalid username or password") }
        val jwt: String = jwtHandler.generateToken(user)
        return Token(jwt);
    }

}
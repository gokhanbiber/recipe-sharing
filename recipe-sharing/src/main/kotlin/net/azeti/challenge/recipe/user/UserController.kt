package net.azeti.challenge.recipe.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User Management")
@RestController
@RequestMapping("api/v1/auth")
class UserController(private var userHandler: UserHandler) {

    @PostMapping("register")
    fun register(@RequestBody registration: Registration): ResponseEntity<Unit> {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userHandler.register(registration));
    }

    @PostMapping("login")
    fun login(@RequestBody login: Login): ResponseEntity<Token>{
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(userHandler.login(login));
    }

}
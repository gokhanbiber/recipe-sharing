package net.azeti.challenge.recipe.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException.Unauthorized

@RestControllerAdvice
class ErrorHandling {

    var logger: Logger = LoggerFactory.getLogger(ErrorHandling::class.java)

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handle(ise: IllegalStateException, request: HttpServletRequest): ProblemDetail? {
        logger.info(":::IllegalStateException")
        val pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value())
        pd.detail = ise.message
        return pd
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handle(nsee: NoSuchElementException, request: HttpServletRequest): ProblemDetail? {
        logger.info(":::NoSuchElementException")
        val pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND.value())
        pd.detail = nsee.message
        return pd
    }

}
package net.azeti.challenge.recipe.user

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import java.util.*


data class Token(
    val accessToken: String
)

fun generateAccessToken(userId: Long): Token {
    val expirationTime = 3600000 // 1 hour
    val secretKey = "your-secret-key" // This will be moved to Env Variables

    val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    val base64Key = Encoders.BASE64.encode(key.encoded)

    val issuedAt = Date()
    val expiration = Date(issuedAt.time + expirationTime)

    val accessToken = Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()

    return Token(accessToken)
}
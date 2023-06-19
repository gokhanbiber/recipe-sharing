package net.azeti.challenge.recipe.user

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date
import java.util.function.Function
import kotlin.collections.HashMap


@Service
class JwtHandler: JwtManagement {

    @Value("\${token.key}")
    private val jwtKey: String? = null

    override fun extractUserName(token: String): String {
        return extractClaim(token, Claims::getSubject);
    }

    override fun generateToken(userDetails: UserDetails): String {
        return generateToken(HashMap(), userDetails)!!
    }

    override fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val userName = extractUserName(token)
        return userName == userDetails.username && !isTokenExpired(token)
    }

    private fun <T> extractClaim(token: String, claimsResolvers: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolvers.apply(claims)
    }

    private fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String? {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.username)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact()
    }

    private fun extractExpiration(token: String): Date? {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody()
    }

    private fun getSigningKey(): Key {
        val keyBytes = Decoders.BASE64.decode(jwtKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token)!!.before(Date())
    }
}
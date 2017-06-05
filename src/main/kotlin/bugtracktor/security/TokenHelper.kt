package bugtracktor.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import java.util.Date
import java.util.concurrent.TimeUnit


/**
 * Created by fan.jin on 2016-10-19.
 */

@Component
class TokenHelper {

    @Value("\${app.name}")
    private lateinit var APP_NAME: String

    @Value("\${jwt.secret}")
    private lateinit var SECRET: String

    @Value("\${jwt.expires_in}")
    private val EXPIRES_IN: Int = TimeUnit.DAYS.toSeconds(365).toInt()

    private val SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512

    fun getUsernameFromToken(token: String): String? {
        val claims = this.getClaimsFromToken(token)
        return claims?.subject
    }

    fun generateToken(username: String): String {
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(username)
                .setIssuedAt(generateCurrentDate())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, SECRET)
                .compact()
    }

    private fun getClaimsFromToken(token: String): Claims? {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .body
        } catch (e: Exception) {
            return null
        }
    }

    private val currentTimeMillis: Long
        get() = System.currentTimeMillis()

    private fun generateCurrentDate() = Date(currentTimeMillis)

    private fun generateExpirationDate(): Date {
        return Date(currentTimeMillis + EXPIRES_IN * 1000)
    }
}
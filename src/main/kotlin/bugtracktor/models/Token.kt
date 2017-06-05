package bugtracktor.models

data class Token(val token: String)
data class UserTokenState(val accessToken: String, val expiresIn: Int)
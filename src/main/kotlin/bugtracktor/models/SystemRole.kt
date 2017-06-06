package bugtracktor.models

enum class SystemRole {
    USER,
    PROJECT_CREATOR,
    ADMIN
    ;

    override fun toString() = "ROLE_$name"
}
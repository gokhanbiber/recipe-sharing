package net.azeti.challenge.recipe.user

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
class Registration(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        @NotNull
        val email: String,
        @NotNull
        private val username: String,
        @NotNull
        private var password: String,
        @Enumerated(EnumType.STRING)
        var role: Role = Role.USER
): UserDetails {
        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
                return mutableListOf(SimpleGrantedAuthority(role.name))
        }

        override fun getPassword(): String {
                return password
        }

        override fun getUsername(): String {
                return username;
        }

        override fun isAccountNonExpired(): Boolean {
                return true
        }

        override fun isAccountNonLocked(): Boolean {
                return true
        }

        override fun isCredentialsNonExpired(): Boolean {
                return true
        }

        override fun isEnabled(): Boolean {
                return true
        }
}

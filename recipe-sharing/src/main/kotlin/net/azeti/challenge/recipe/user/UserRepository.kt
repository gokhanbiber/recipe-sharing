package net.azeti.challenge.recipe.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository: JpaRepository<Registration,Long> {
    fun findByUsername(username:String): Optional<Registration>;
}

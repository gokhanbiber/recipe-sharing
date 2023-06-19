package net.azeti.challenge.recipe.user

interface UserManagement {

    fun register(registration: Registration)

    fun login(login: Login): Token
}
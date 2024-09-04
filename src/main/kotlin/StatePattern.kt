package org.example

fun main() {}//STATE
interface UserState {
    fun singUp(user: User, email: String)
    fun verifyEmail(user: User, token: String)
    fun viewContent()
    fun viewProfile(user: User)
    fun editProfile(user: User, newEmail: String)
}

// Concrete State
object Anonymous : UserState {
    override fun singUp(user: User, email: String) {
        println("Signing up with email: $email")
        user.email = email
        user.state = Unverified
    }

    override fun verifyEmail(user: User, token: String) = println("You must sign up before verifying your email.")
    override fun viewContent() = println("Viewing public content.")
    override fun viewProfile(user: User) = println("You must sign in to view your profile.")
    override fun editProfile(user: User, newEmail: String) = println("You must sign in to edit your profile")
}

object Unverified : UserState {
    override fun singUp(user: User, email: String) = println("You are already signed up.")

    override fun verifyEmail(user: User, token: String) {
        println("Verifying email with token: $token")
        user.state = Authenticated
    }

    override fun viewContent() = println("Viewing personalized content for unverified account.")
    override fun viewProfile(user: User) = println("Profile: $user.email (Unverified account, please verify your email.)")
    override fun editProfile(user: User, newEmail: String) = println("Please verify your account before editing your profile.")

}

object Authenticated : UserState {
    override fun singUp(user: User, email: String) = println("You are already signed up and authenticated.")
    override fun verifyEmail(user: User, token: String) = println("You are already verified.")
    override fun viewContent() = println("Viewing personalized content.")
    override fun viewProfile(user: User) = println("Profile: $user.email (Fully authenticated.)")
    override fun editProfile(user: User, newEmail: String) {
        println("Profile: Updating email from ${user.email} to $newEmail")
        user.email = newEmail
    }
}

// CONTEXT
class User(var email: String? = null, var state: UserState = Anonymous) {
    fun signUp(email: String) = state.singUp(this, email)
    fun verifyEmail(token: String) = state.verifyEmail(this, token)
    fun viewContent() = state.viewContent()
    fun viewProfile() = state.viewProfile(this)
    fun editProfile(newEmail: String) = state.editProfile(this, newEmail)
}
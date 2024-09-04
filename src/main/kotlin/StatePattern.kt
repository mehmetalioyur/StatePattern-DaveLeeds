package org.example

fun main() {}
enum class UserState(
    val signUp: (User, String) -> Unit,
    val verifyEmail: (User, String) -> Unit,
    val viewContent: () -> Unit,
    val viewProfile: (User) -> Unit,
    val editProfile: (User, String) -> Unit,
) {
    ANONYMOUS(
        signUp = { user, email -> println("Signing up with email: $email"); user.email = email; user.state = UNVERIFIED },
        verifyEmail = { _, _ -> println("You must sign up before verifying your email.") },
        viewContent = { println("Viewing public content.") },
        viewProfile = { println("You must sign in to view your profile.") },
        editProfile = { _, _ -> println("You must sign in to edit your profile") }
    ),
    UNVERIFIED(
        signUp = { _, _ -> println("You are already signed up.") },
        verifyEmail = { user, token -> println("Verifying email with token: $token"); user.state = AUTHENTICATED },
        viewContent = { println("Viewing personalized content for unverified account.") },
        viewProfile = { user -> println("Profile: ${user.email} (Unverified account, please verify your email).") },
        editProfile = { _, _ -> println("Please verify your account before editing your profile.") }
    ),

    AUTHENTICATED(
        signUp = { _, _ -> println("You are already signed up and authenticated.") },
        verifyEmail = { _, _ -> println("You are already verified.") },
        viewContent = { println("Viewing personalized content.") },
        viewProfile = { user -> println("Profile: ${user.email} (Fully authenticated).") },
        editProfile = { user, newEmail -> println("Profile: Updating email from ${user.email} to $newEmail.");user.email = newEmail }
    )
}



// CONTEXT
class User(var email: String? = null, var state: UserState = UserState.ANONYMOUS) {
    fun signUp(email: String) = state.signUp(this, email)
    fun verifyEmail(token: String) = state.verifyEmail(this, token)
    fun viewContent() = state.viewContent()
    fun viewProfile() = state.viewProfile(this)
    fun editProfile(newEmail: String) = state.editProfile(this, newEmail)
}
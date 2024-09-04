package org.example

enum class UserState {
    ANONYMOUS, UNVERIFIED, AUTHENTICATED
}

class User(var email: String? = null, var state: UserState = UserState.ANONYMOUS) {
    fun signUp(email: String) {
        when (state) {
            UserState.ANONYMOUS -> {
                println("Signing up with email: $email")
                this.email = email
                state = UserState.UNVERIFIED
            }

            UserState.UNVERIFIED -> println("You are already signed up.")
            UserState.AUTHENTICATED -> println("You are already signed up and authenticated.")
        }
    }

    fun verifyEmail(token: String) {
        when (state) {
            UserState.ANONYMOUS -> println("You must sign up before verifying your email.")
            UserState.UNVERIFIED -> {
                println("Verifying email with token: $token")
                state = UserState.AUTHENTICATED
            }

            UserState.AUTHENTICATED -> println("You are already verified.")
        }
    }

    fun viewContent() {
        when (state) {
            UserState.ANONYMOUS -> println("Viewing public content.")
            UserState.UNVERIFIED -> println("Viewing personalized content for unverified account.")
            UserState.AUTHENTICATED -> println("Viewing personalized content.")
        }
    }

    fun viewProfile() {
        when (state) {
            UserState.ANONYMOUS -> println("You must sign in to view your profile.")
            UserState.UNVERIFIED -> println("Profile: $email (Unverified account, please verify your email.)")
            UserState.AUTHENTICATED -> println("Profile: $email (Fully authenticated.)")
        }
    }

    fun editProfile(newEmail: String) {
        when (state) {
            UserState.ANONYMOUS -> println("You must sign in to edit your profile")
            UserState.UNVERIFIED -> println("Please verify your account before editing your profile.")
            UserState.AUTHENTICATED -> {
                println("Profile: Updating email from ${email} to $newEmail")
                email = newEmail
            }
        }
    }
}
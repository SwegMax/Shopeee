package com.example.shopeee.handlers

import java.util.regex.Pattern
object Validator {
    fun isUsernameValid(username: String): Boolean {
        // Check if username doesn't already exist in the database (pseudo-code)
        if (usernameExistsInDatabase(username)) {
            return false
        }
        return true
    }

    fun isPasswordValid(password: String): Boolean {
        // Check if password contains a special character
        val pattern = Pattern.compile("[^a-zA-Z0-9]")
        val matcher = pattern.matcher(password)
        return matcher.find()
    }

    private fun usernameExistsInDatabase(username: String): Boolean {
        // Simulated database check
        // Replace this with actual database query

        return false
    }
}

/** example usage
 *
 * if (Validator.isUsernameValid(username)) {
 *         println("Username is valid.")
 *     } else {
 *         println("Username is invalid.")
 *     }
 */
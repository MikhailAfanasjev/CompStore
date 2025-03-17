package com.example.compstore.utils

fun validateInputs(name: String, phone: String, email: String): Boolean {
    return name.isNotEmpty() &&
            phone.matches(Regex("\\+?[0-9]{10,15}")) && // Validate phone number
            email.matches(Regex("^[\\w-.]+@[\\w-]+\\.[a-z]{2,4}$")) // Validate email
}
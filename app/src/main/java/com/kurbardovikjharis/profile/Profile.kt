package com.kurbardovikjharis.profile

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Profile() {
    Profile(hiltViewModel())
}

@Composable
internal fun Profile(profileViewModel: ProfileViewModel) {
    Text(text = "profile")
}
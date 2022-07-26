package com.kurbardovikjharis.discovery

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Discovery() {
    Discovery(hiltViewModel())
}

@Composable
internal fun Discovery(discoveryViewModel: DiscoveryViewModel) {
    Text(text = "discovery")
}
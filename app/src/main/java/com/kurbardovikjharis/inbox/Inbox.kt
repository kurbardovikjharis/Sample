package com.kurbardovikjharis.inbox

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Inbox() {
    Inbox(hiltViewModel())
}

@Composable
internal fun Inbox(inboxViewModel: InboxViewModel) {
    Text(text = "inbox")
}
package com

data class ChatState(
    val isEnteringText : Boolean = true,
    val remoteToken: String = "",
    val messageText: String = "",
)

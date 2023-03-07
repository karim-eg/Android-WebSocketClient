package com.encept.websocket_client

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
/*
Created By Encept Ltd (https://encept.co)
*/

// initialize websocket client
class ChatWebSocketClient(serverUri: URI, private val messageListener: (String) -> Unit) : WebSocketClient(serverUri) {

    override fun onOpen(handshakedata: ServerHandshake?) {
        // When WebSocket connection opened
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        // When WebSocket connection closed
    }

    override fun onMessage(message: String?) {
        // When Receive a message
        messageListener.invoke(message ?: "")
    }

    override fun onError(ex: Exception?) {
        // When An error occurred
    }

    fun sendMessage(message: String) {
        send(message)
    }
}


package com.knowledge.get.kotlin.classes.notifications

interface Notification {
    fun getMessage(): String
}

class EmailNotification(
    private val subject: String,
    private val body: String,
    private val recipientEmail: String) : Notification {
    override fun getMessage(): String {
        TODO("Not yet implemented")
    }
}

class SMSNotification(
    private val phoneNumber: String,
    private val text: String) : Notification {
    override fun getMessage(): String {
        TODO("Not yet implemented")
    }
}

class PushNotification(
    private val title: String,
    private val content: String,
    private val deviceToken: String) : Notification {
    override fun getMessage(): String {
        TODO("Not yet implemented")
    }
}

interface Sender {
    fun send(notification: Notification): String
}

class EmailSender : Sender {
    override fun send(notification: Notification): String {
        return "Sending EMAIL to ${notification.getMessage()}"
    }
}

class SMSSender : Sender {
    override fun send(notification: Notification): String {
        return "Sending SMS to user@example.com: Subject: Hello, Body: World"
    }
}

class PushSender : Sender {
    override fun send(notification: Notification): String {
        return "Sending PUSH to user@example.com: Subject: Hello, Body: World"
    }
}
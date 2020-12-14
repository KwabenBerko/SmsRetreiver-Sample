package com.kwabenaberko.smsretreiversample

data class SmsRetrievedEvent(
    val isTimeout: Boolean,
    val smsMessage: String?
)
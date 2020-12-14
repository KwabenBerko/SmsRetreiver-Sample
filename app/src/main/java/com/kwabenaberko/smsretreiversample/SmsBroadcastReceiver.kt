package com.kwabenaberko.smsretreiversample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import org.greenrobot.eventbus.EventBus


class SmsBroadcastReceiver : BroadcastReceiver() {

    companion object {
        val TAG = SmsBroadcastReceiver::class.java.simpleName
    }

    override fun onReceive(context: Context?, intent: Intent) {
        Log.d(TAG, "onReceive Called!")
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val bundle = intent.extras
            if (bundle != null) {
                val status: Status = bundle[SmsRetriever.EXTRA_STATUS] as Status
                var isTimeout = false
                var smsMessage: String? = null
                when (status.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val message =
                            bundle[SmsRetriever.EXTRA_SMS_MESSAGE] as String
                        Log.d(TAG, message)
                        smsMessage = message
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        Log.d(TAG, "Timeout")
                        isTimeout = true
                    }
                }
                EventBus.getDefault().post(SmsRetrievedEvent(isTimeout, smsMessage))
            }
        }
    }

}
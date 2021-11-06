package com.task.mathongo.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.task.mathongo.databinding.LayoutUserDialogBinding
import com.task.mathongo.databinding.LoadingdialogBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class AppUtils {

    companion object{

        fun getUserEmail(context:Context):Dialog{

            val bindings = LayoutUserDialogBinding.inflate(LayoutInflater.from(context))


            val dialog = Dialog(context)
            dialog.setContentView(bindings.root)
            dialog.setCancelable(true)


            bindings.saveBtn.setOnClickListener {
                val userEmail = bindings.userInput.text.toString()
                if(TextUtils.isEmpty(userEmail)){
                    showToastMsg(context,"Please enter your email id")
                }else{

                    PreferenceHelper.setEmailId(context,userEmail)
                    dialog.dismiss()
                }
            }
            return dialog
        }

        fun showToastMsg(context: Context,msg:String){

            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
        }


        fun okHttpClient(): OkHttpClient {
            val httploggers =  HttpLoggingInterceptor()
            httploggers.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .addInterceptor(httploggers)
                .build()

            return client
        }

        fun showProgress(context: Context): Dialog {
            val binding = LoadingdialogBinding.inflate(LayoutInflater.from(context.applicationContext)).root
            val dialog = Dialog(context)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.getWindow()?.setBackgroundDrawable(
                ColorDrawable(0)
            )
            dialog.setContentView(binding);

            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }
        fun isNetworkAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val cap = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
                return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networks = cm.allNetworks
                for (n in networks) {
                    val nInfo = cm.getNetworkInfo(n)
                    if (nInfo != null && nInfo.isConnected) return true
                }
            } else {
                val networks = cm.allNetworkInfo
                for (nInfo in networks) {
                    if (nInfo != null && nInfo.isConnected) return true
                }
            }
            return false
        }
    }
}
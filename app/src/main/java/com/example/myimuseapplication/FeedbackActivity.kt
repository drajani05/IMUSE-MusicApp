package com.example.myimuseapplication

import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myimuseapplication.databinding.ActivityFeedbackBinding
import java.util.*
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class FeedbackActivity : AppCompatActivity() {

    lateinit var binding: ActivityFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Feedback"


        binding.sendFA.setOnClickListener {

            val feedbackMsg =
                binding.feedbackMsgFA.text.toString() + "\n" + binding.emailFA.text.toString()

            val subject = binding.topicFA.text.toString()

            val userName = "imusemusic05@gmail.com"

            val pass = "musicplayer05"
            val cm = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

            if (feedbackMsg.isNotEmpty() && subject.isNotEmpty() && (cm.activeNetworkInfo?.isConnectedOrConnecting == true)) {
                Thread {
                    try { val properties = Properties()
                        properties["mail.smtp.auth"] = "true"
                        properties["mail.smtp.starttls.enable"] = "true"
                        properties["mail.smtp.host"] = "smtp.gmail.com"
                        properties["mail.smtp.port"] = "587"
                        val session =
                            Session.getInstance(properties, object : javax.mail.Authenticator() {
                                override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
                                    return javax.mail.PasswordAuthentication(userName, pass)
                                }

                            })

                        val mail = MimeMessage(session)
                        mail.subject = subject
                        mail.setText(feedbackMsg)
                        mail.setFrom(InternetAddress(userName))
                        mail.setRecipients(javax.mail.Message.RecipientType.TO,InternetAddress.parse(userName))
                        javax.mail.Transport.send(mail)

                    } catch(e:Exception){Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()}

                }.start()
                Toast.makeText(this,"Thanks for Feedback!!",Toast.LENGTH_SHORT).show()
                finish()
            }
            else  Toast.makeText(this,"Went Something Wrong!!",Toast.LENGTH_SHORT).show()

        }
    }

}
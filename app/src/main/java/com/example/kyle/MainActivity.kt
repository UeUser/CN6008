// MainActivity.kt

package com.example.kyle

import com.example.kyle.databinding.ActivityMainBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 初始化Firebase数据库引用
        database = FirebaseDatabase.getInstance().reference

        binding.sendButton.setOnClickListener {
            val message = binding.messageInput.text.toString()
            if (message.isNotEmpty()) {
                sendMessage(message)
                binding.messageInput.text.clear()
            }
        }

        // 监听数据库，实时更新聊天
        database.child("messages").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val newMessage = snapshot.getValue(String::class.java)
                binding.chatView.append("\n$newMessage")
            }
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        })

        binding.sendButton.setOnClickListener {
            val message = binding.messageInput.text.toString()
            if (message.isNotEmpty()) {
                sendMessage(message)
                binding.messageInput.text.clear()
            }
        }
    }
    
    private fun sendMessage(message: String) {
        database.child("messages").push().setValue(message)
    }

}

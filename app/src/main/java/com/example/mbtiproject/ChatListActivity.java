
package com.example.mbtiproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ChatListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        findViewById(R.id.btnRoom1).setOnClickListener(v ->
                startActivity(new Intent(this, ChatRoomActivity.class)));


        findViewById(R.id.home_04).setOnClickListener(v ->
                startActivity(new Intent(this, AppMainActivity.class)));

        findViewById(R.id.message_cha).setOnClickListener(v -> {});
        findViewById(R.id.settings).setOnClickListener(v -> {});
    }
}

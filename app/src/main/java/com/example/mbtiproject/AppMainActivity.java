
package com.example.mbtiproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AppMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        TextView tvNickname = findViewById(R.id.tvNickname);
        TextView tvHello    = findViewById(R.id.tvHello);

        String nick = NicknameUtil.getOrCreateNickname(this); // 예시
        if (tvNickname != null) tvNickname.setText(nick + " 님,");
        if (tvHello != null)    tvHello.setText("안녕하세요!");

        Switch sw = findViewById(R.id.swBig);
        sw.setChecked(FontUtil.isBig(this));
        sw.setOnCheckedChangeListener((b,checked)->{
            FontUtil.setBig(this, checked);
            FontUtil.apply(findViewById(android.R.id.content), checked);
        });
        FontUtil.apply(findViewById(android.R.id.content), FontUtil.isBig(this));


        findViewById(R.id.btnMatch).setOnClickListener(v ->
                startActivity(new Intent(this, MatchingActivity.class)));
        findViewById(R.id.btnEdit).setOnClickListener(v -> {

        });
        findViewById(R.id.btnTraits).setOnClickListener(v ->
                startActivity(new Intent(this, TraitsActivity.class)));


        findViewById(R.id.home_04).setOnClickListener(v -> {

        });

        findViewById(R.id.message_cha).setOnClickListener(v ->
                startActivity(new Intent(this, ChatListActivity.class)));

        findViewById(R.id.settings).setOnClickListener(v -> {

        });
    }
}

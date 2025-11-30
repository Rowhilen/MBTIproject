package com.example.mbtiproject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ChatRoomActivity extends AppCompatActivity {

    private LinearLayout messageContainer;
    private ScrollView scroll;
    private EditText etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        TextView btnBack = findViewById(R.id.btnBack);
        TextView tvRoomTitle = findViewById(R.id.tvRoomTitle);
        btnBack.setOnClickListener(v -> finish());


        String title = getIntent().getStringExtra("room_title");
        if (title != null && !title.isEmpty()) tvRoomTitle.setText(title);


        messageContainer = findViewById(R.id.messageContainer);
        scroll = findViewById(R.id.scroll);
        etMessage = findViewById(R.id.etMessage);
        Button btnSend = findViewById(R.id.btnSend);


        addIncoming("안녕하세요!");

        btnSend.setOnClickListener(v -> sendCurrentText());


        etMessage.setImeOptions(EditorInfo.IME_ACTION_SEND);
        etMessage.setOnEditorActionListener((tv, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendCurrentText();
                return true;
            }
            return false;
        });
    }

    private void sendCurrentText() {
        String text = etMessage.getText().toString();
        if (text.trim().isEmpty()) return;           // 공백은 무시(원하면 토스트로 안내)
        addOutgoing(text.trim());
        etMessage.setText("");
    }


    private void addIncoming(String text) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.START);
        row.setPadding(0, dp(10), 0, 0);

        ImageView avatar = new ImageView(this);
        LinearLayout.LayoutParams lpAv = new LinearLayout.LayoutParams(dp(32), dp(32));
        avatar.setLayoutParams(lpAv);
        avatar.setImageResource(R.drawable.avatar_circle);
        avatar.setContentDescription(getString(R.string.cd_user_avatar));

        TextView bubble = new TextView(this);
        bubble.setText(text);
        bubble.setTextSize(15);
        bubble.setTextColor(Color.WHITE);
        bubble.setBackground(ContextCompat.getDrawable(this, R.drawable.bubble_incoming));
        bubble.setPadding(dp(12), dp(12), dp(12), dp(12));
        bubble.setMaxWidth(dp(260));
        LinearLayout.LayoutParams lpBubble = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpBubble.setMargins(dp(8), 0, 0, 0);
        bubble.setLayoutParams(lpBubble);

        row.addView(avatar);
        row.addView(bubble);
        messageContainer.addView(row);
        scrollToBottom();
    }


    private void addOutgoing(String text) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.END);
        row.setPadding(0, dp(10), 0, 0);

        TextView bubble = new TextView(this);
        bubble.setText(text);
        bubble.setTextSize(15);
        bubble.setTextColor(Color.WHITE);
        bubble.setBackground(ContextCompat.getDrawable(this, R.drawable.bubble_outgoing));
        bubble.setPadding(dp(12), dp(12), dp(12), dp(12));
        bubble.setMaxWidth(dp(260));

        row.addView(bubble);
        messageContainer.addView(row);
        scrollToBottom();
    }

    private void scrollToBottom() {
        if (scroll != null) {
            scroll.post(() -> scroll.fullScroll(View.FOCUS_DOWN));
        }
    }

    private int dp(int v) {
        return Math.round(getResources().getDisplayMetrics().density * v);
    }
}

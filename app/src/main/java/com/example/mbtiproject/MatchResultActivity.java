
package com.example.mbtiproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MatchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_result);


        String mbti    = getIntent().getStringExtra("mbti");
        String partner = getIntent().getStringExtra("partner");

        TextView tvMyType    = findViewById(R.id.tvMyType);
        TextView tvOtherType = findViewById(R.id.tvOtherType);
        if (tvMyType != null && mbti != null)    tvMyType.setText("성격유형 : " + mbti);
        if (tvOtherType != null && partner != null) tvOtherType.setText("성격유형 : " + partner);


        Button btnAccept = findViewById(R.id.btnAccept);
        Button btnCancel = findViewById(R.id.btnCancel);

        if (btnAccept != null) {
            btnAccept.setOnClickListener(v -> {

                Intent i = new Intent(this, ChatListActivity.class);
                startActivity(i);
                finish();
            });
        }

        if (btnCancel != null) {
            btnCancel.setOnClickListener(v -> {

                Intent i = new Intent(this, AppMainActivity.class);

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();
            });
        }
    }
}

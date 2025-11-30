package com.example.mbtiproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    private TextView tvQuestion, tvProgress;
    private Button btnYes, btnNo;
    private int idx = 0;

    private final String[] Q = new String[]{
        "나는 사교모임에 가는 것을 좋아한다.",
        "나는 활동적인 모임에 참석하는게 부담스럽다.",
        "나는 소수의 친구를 만나는 것보다 여러명의 친구와 만나는 것이 좋다.",
        "나는 나무보다는 숲을 보는 편이다.",
        "나는 판단의 기로에서 경험보다는 감에 의존하는 편이다",
        "나는 익숙한 일보다는 새로운 일을 선호하는 편이다",
        "나는 감정적이기 보다는 이성적인 편이다.",
        "나는 슬픈 영화를 보면 눈물을 흘리는 편이다.",
        "실수를 하고 낙담하는 친구에게 위로보다는 도움이 되는 조언을 해주는 편이다.",
        "나는 여행을 갈 때 계획을 짜놓지 않으면 불안하다.",
        "나는 캘린더에 일정을 꼼꼼하게 정리해 놓는 편이다.",
        "계획적이기 보다는 즉흥적으로 움직이는 편이다."
    };

    private void applyYes(int i){ switch(i){
        case 0: e++; break; case 1: i_cnt++; break; case 2: e++; break;
        case 3: n++; break; case 4: n++; break; case 5: n++; break;
        case 6: t++; break; case 7: f++; break; case 8: t++; break;
        case 9: j++; break; case 10: j++; break; case 11: p++; break;
    }}
    private void applyNo(int i){ switch(i){
        case 0: i_cnt++; break; case 1: e++; break; case 2: i_cnt++; break;
        case 3: s++; break; case 4: s++; break; case 5: s++; break;
        case 6: f++; break; case 7: t++; break; case 8: f++; break;
        case 9: p++; break; case 10: p++; break; case 11: j++; break;
    }}

    private int e=0,i_cnt=0,n=0,s=0,f=0,t=0,p=0,j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvQuestion = findViewById(R.id.tvQuestion);
        tvProgress = findViewById(R.id.tvProgress);
        btnYes = findViewById(R.id.btnYes);
        btnNo  = findViewById(R.id.btnNo);

        render();
        btnYes.setOnClickListener(v -> { applyYes(idx); next(); });
        btnNo.setOnClickListener(v -> { applyNo(idx); next(); });
    }

    private void render(){
        tvQuestion.setText(Q[idx]);
        tvProgress.setText((idx+1) + " / " + Q.length);
    }
    private void next(){
        idx++;
        if (idx < Q.length) {
            render();
            return;
        }
        String mbti = MbtiLogic.computeMbti(e,i_cnt,n,s,f,t,p,j);
        String partner = MbtiLogic.partnerFor(mbti);
        String docId = getSharedPreferences(AppConstants.PREFS, MODE_PRIVATE)
                .getString(AppConstants.KEY_CURRENT_USER_DOC, null);
        if (docId == null) {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        FirestoreService.updateMbtiAndPartner(docId, mbti, partner)
                .addOnSuccessListener(unused -> {
                    Intent i = new Intent(this, MatchResultActivity.class);
                    i.putExtra("mbti", mbti);
                    i.putExtra("partner", partner);
                    startActivity(i);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "저장 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}

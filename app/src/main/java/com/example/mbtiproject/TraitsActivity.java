package com.example.mbtiproject;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class TraitsActivity extends AppCompatActivity {

    private TextView findByIdName(String name) {
        int id = getResources().getIdentifier(name, "id", getPackageName());
        if (id != 0) return findViewById(id);
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traits);

        TextView btnBack = findByIdName("btnBack");
        View btnConfirm = findByIdName("btnConfirm");
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        if (btnConfirm != null) btnConfirm.setOnClickListener(v -> finish());

        String docId = getSharedPreferences(AppConstants.PREFS, MODE_PRIVATE)
                .getString(AppConstants.KEY_CURRENT_USER_DOC, null);
        if (docId == null) return;

        FirestoreService.getUserDoc(docId)
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override public void onSuccess(DocumentSnapshot ds) {
                        String myMbti = ds.getString("mbti");
                        String partnerMbti = ds.getString("partnerMbti");


                        TextView tvMyTitle = findByIdName("tvMyTitle");
                        TextView tvPartnerTitle = findByIdName("tvPartnerTitle");
                        if (tvMyTitle != null && myMbti != null) tvMyTitle.setText("나의 성격유형은 " + myMbti);
                        if (tvPartnerTitle != null && partnerMbti != null) tvPartnerTitle.setText("내 파트너의 성격 유형은 " + partnerMbti);


                        replaceInlineIfContains((ViewGroup)findViewById(android.R.id.content), "나의 성격유형은", myMbti);
                        replaceInlineIfContains((ViewGroup)findViewById(android.R.id.content), "내 파트너의 성격 유형은", partnerMbti);
                    }
                });
    }


    private void replaceInlineIfContains(ViewGroup root, String prefix, String value) {
        if (root == null || value == null) return;
        int count = root.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                CharSequence cs = ((TextView) v).getText();
                if (cs != null) {
                    String s = cs.toString();
                    if (s.startsWith(prefix)) {
                        ((TextView) v).setText(prefix + " " + value);
                    }
                }
            } else if (v instanceof ViewGroup) {
                replaceInlineIfContains((ViewGroup) v, prefix, value);
            }
        }
    }
}

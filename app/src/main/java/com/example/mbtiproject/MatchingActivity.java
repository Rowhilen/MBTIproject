package com.example.mbtiproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MatchingActivity extends AppCompatActivity {

    private EditText etOrg, etClass;
    private Button btnSearchOrg, btnSearchClass, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);


        etOrg         = findViewById(R.id.etOrg);
        etClass       = findViewById(R.id.etClass);
        btnSearchOrg  = findViewById(R.id.btnSearchOrg);
        btnSearchClass= findViewById(R.id.btnSearchClass);
        btnNext       = findViewById(R.id.btnNext);


        btnSearchOrg.setOnClickListener(v -> showOrgPicker());


        btnSearchClass.setOnClickListener(v -> showClassPicker());


        btnNext.setOnClickListener(v -> {
            String org   = etOrg.getText().toString().trim();
            String klass = etClass.getText().toString().trim();

            if (org.isEmpty() || klass.isEmpty()) {
                Toast.makeText(this, "소속기관과 수강 반을 모두 선택하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            String docId = getSharedPreferences(AppConstants.PREFS, MODE_PRIVATE)
                    .getString(AppConstants.KEY_CURRENT_USER_DOC, null);
            if (docId == null) {
                Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            FirestoreService.updateOrgKlass(docId, org, klass)
                    .addOnSuccessListener(unused ->
                            startActivity(new android.content.Intent(this, TestActivity.class)))
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "저장 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });


    }

    private void showOrgPicker() {
        final String[] items = AppConstants.ORGS;
        new AlertDialog.Builder(this)
                .setTitle("소속기관 선택")
                .setItems(items, (dialog, which) -> etOrg.setText(items[which]))
                .show();
    }

    private void showClassPicker() {
        final String[] items = AppConstants.CLASSES;
        new AlertDialog.Builder(this)
                .setTitle("수강 반 선택")
                .setItems(items, (dialog, which) -> etClass.setText(items[which]))
                .show();
    }
}

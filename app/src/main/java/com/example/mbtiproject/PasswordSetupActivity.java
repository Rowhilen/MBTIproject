package com.example.mbtiproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.regex.Pattern;

public class PasswordSetupActivity extends AppCompatActivity {
    private EditText etPassword;
    private Button btnNext;
    private static final Pattern HAS_LETTER = Pattern.compile(".*[A-Za-z].*");
    private static final Pattern HAS_DIGIT  = Pattern.compile(".*\\d.*");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_setup);

        etPassword = findViewById(R.id.etPassword);
        btnNext = findViewById(R.id.btnNext);

        String phone = getIntent().getStringExtra("phone");

        btnNext.setOnClickListener(v -> {
            String pw = etPassword.getText().toString();
            if (pw.length() < AppConstants.MIN_PASSWORD_LEN) {
                Toast.makeText(this, "비밀번호는 최소 " + AppConstants.MIN_PASSWORD_LEN + "자 이상으로 해야 됩니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!HAS_LETTER.matcher(pw).matches() || !HAS_DIGIT.matcher(pw).matches()) {
                Toast.makeText(this, "비밀번호는 숫자와 영문이 모두 포함되어야 합니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            String hash = HashUtil.sha256(pw);
            String nickname = NicknameUtil.getOrCreateNickname(this);

            FirestoreService.createUser(phone, hash, nickname)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override public void onSuccess(DocumentReference docRef) {
                            getSharedPreferences(AppConstants.PREFS, MODE_PRIVATE)
                                    .edit().putString(AppConstants.KEY_CURRENT_USER_DOC, docRef.getId()).apply();
                            startActivity(new Intent(PasswordSetupActivity.this, LoginActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PasswordSetupActivity.this, "가입 중 오류: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}

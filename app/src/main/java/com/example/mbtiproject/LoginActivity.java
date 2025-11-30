package com.example.mbtiproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private EditText etPhone, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String phone = etPhone.getText().toString().replaceAll("[^0-9]", "");
            String pw = etPassword.getText().toString();
            String hash = HashUtil.sha256(pw);

            FirestoreService.getUsersByPhone(phone)
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (!task.isSuccessful() || task.getResult() == null) {
                                Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            DocumentSnapshot matched = null;
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                String ph = doc.getString("phone");
                                String h  = doc.getString("passwordHash");
                                if (phone.equals(ph) && hash.equals(h)) { matched = doc; break; }
                            }
                            if (matched == null) {
                                Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                getSharedPreferences(AppConstants.PREFS, MODE_PRIVATE)
                                        .edit().putString(AppConstants.KEY_CURRENT_USER_DOC, matched.getId()).apply();
                                startActivity(new Intent(LoginActivity.this, AppMainActivity.class));
                                finish();
                            }
                        }
                    });
        });
    }
}

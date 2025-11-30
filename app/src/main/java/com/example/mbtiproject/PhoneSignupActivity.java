package com.example.mbtiproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class PhoneSignupActivity extends AppCompatActivity {
    private EditText etPhone;
    private Button btnNext;
    private static final Pattern PHONE11 = Pattern.compile("^010\\d{8}$"); // 010 + 8자리 = 총 11자리

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_signup);

        etPhone = findViewById(R.id.etPhone);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            String phone = etPhone.getText().toString().replaceAll("[^0-9]", "");
            if (!PHONE11.matcher(phone).matches()) {
                Toast.makeText(this, "전화번호를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(this, PasswordSetupActivity.class);
            i.putExtra("phone", phone);
            startActivity(i);
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                String digits = s.toString().replaceAll("[^0-9]", "");
                if (!digits.equals(s.toString())) {
                    etPhone.setText(digits);
                    etPhone.setSelection(digits.length());
                }
            }
        });
    }
}

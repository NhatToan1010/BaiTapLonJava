package com.acer.baitaplonjava.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.baitaplonjava.fragments.HomeViewModel;
import com.acer.baitaplonjava.MainActivity;
import com.acer.baitaplonjava.R;
import com.acer.baitaplonjava.database.AppDatabase;
import com.acer.baitaplonjava.model.Account;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword, edtEmail;
    private HomeViewModel mViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.edt_RegUserName);
        edtPassword = findViewById(R.id.edt_RegPassword);
        edtEmail = findViewById(R.id.edt_RegEmail);

        Button btnRegis = findViewById(R.id.btn_RegRegister);
        TextView btnRegLogin = findViewById(R.id.btn_RegLogin);

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strRegUsername = edtUsername.getText().toString().trim();
                String strRegPassword = edtPassword.getText().toString().trim();
                String strRegEmail = edtEmail.getText().toString().trim();
                Account account = new Account(strRegUsername, strRegPassword);
                if (TextUtils.isEmpty(strRegUsername) || TextUtils.isEmpty(strRegPassword)){
                    Toast.makeText(RegisterActivity.this, "All information must be filled!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (typeExit(account)){
                    Toast.makeText(RegisterActivity.this, "Username has been created!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                mViewModel.insertAccount(account);
                Toast.makeText(RegisterActivity.this, "Success!",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnRegLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean typeExit(Account account){
        List<Account> mList = AppDatabase.getDatabase(this)
                .accountDAO()
                .checkUsername(account.getUsername());
        return mList != null && !mList.isEmpty();
    }
}
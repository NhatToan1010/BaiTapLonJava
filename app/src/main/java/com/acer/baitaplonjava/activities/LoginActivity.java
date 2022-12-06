package com.acer.baitaplonjava.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.baitaplonjava.MainActivity;
import com.acer.baitaplonjava.R;
import com.acer.baitaplonjava.database.AppDatabase;
import com.acer.baitaplonjava.model.Account;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView btnRegis;
    private CheckBox chkRemember;
    private String strUsername, strPassword;
    private Account account;
    private SharedPreferences loginPreference;
    private SharedPreferences.Editor loginEditor;
    private boolean saveLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edt_UserName);
        edtPassword = findViewById(R.id.edt_Password);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegis = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(this);
        btnRegis.setOnClickListener(this);
        chkRemember = findViewById(R.id.chkRemember);

        loginPreference = getSharedPreferences("loginPref", MODE_PRIVATE);
        loginEditor = loginPreference.edit();
        saveLogin = loginPreference.getBoolean("saveLogin", false);
        if (saveLogin){
            edtUsername.setText(loginPreference.getString("username", ""));
            edtPassword.setText(loginPreference.getString("password", ""));
            chkRemember.setChecked(true);
        }
    }



    @SuppressLint({"NonConstantResourceId", "ResourceAsColor"})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtUsername.getWindowToken(), 0);
                strUsername = edtUsername.getText().toString().trim();
                strPassword = edtPassword.getText().toString().trim();
                account = new Account(strUsername, strPassword);
                if (TextUtils.isEmpty(strUsername) || TextUtils.isEmpty((strPassword))){
                    Toast.makeText(this, "All information must be filled!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkUsername(strUsername)){
                    Toast.makeText(this, "Wrong username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkPassword(strPassword)){
                    Toast.makeText(this, "Wrong password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(chkRemember.isChecked()){
                    loginEditor.putBoolean("saveLogin", true);
                    loginEditor.putString("username", strUsername);
                    loginEditor.putString("password", strPassword);
                    loginEditor.commit();
                }else {
                    loginEditor.clear();
                    loginEditor.commit();
                }

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Login success!",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnRegister:
                Intent regIntent = new Intent(this, RegisterActivity.class);
                startActivity(regIntent);
                break;
        }
    }

    private boolean checkUsername(String userName){
        String isUsername = AppDatabase.getDatabase(this).accountDAO().findUsername();
        return userName.equals(isUsername);
    }

    private boolean checkPassword(String passWord){
        String isPassword = AppDatabase.getDatabase(this).accountDAO().findPassword();
        return passWord.equals(isPassword);
    }

    private boolean typeExit(Account account){
        List<Account> mList = AppDatabase.getDatabase(this)
                .accountDAO()
                .checkUsername(account.getUsername());
        return mList != null && !mList.isEmpty();
    }
}
package com.example.murodjonrahimov.finalassessment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

  public static final String USER_NAME_KEY = "username";
  public static final String PASSWORD_NAME_KEY = "password";
  private final static String PASSWORD_KEY = "passwordKey";

  EditText emailEditText;
  EditText passwordEditText;
  Button submitButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    emailEditText = findViewById(R.id.email);
    passwordEditText = findViewById(R.id.password);
    submitButton = findViewById(R.id.email_sign_in_button);

    final SharedPreferences sharedPreferences = getSharedPreferences(USER_NAME_KEY, MODE_PRIVATE);
    String savedUsername = sharedPreferences.getString(PASSWORD_NAME_KEY, null);
    String savedPassword = sharedPreferences.getString(PASSWORD_KEY, null);

    if (!TextUtils.isEmpty(savedUsername) && !TextUtils.isEmpty(savedPassword)) {
      Intent intent = new Intent(LoginActivity.this, BreedsActivity.class);
      startActivity(intent);
    }

    submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (emailEditText.getText()
          .toString()
          .matches("")) {
          emailEditText.setHint(R.string.empty_user_name);
        }
        if (passwordEditText.getText()
          .toString()
          .matches("")) {
          passwordEditText.setHint(R.string.password_is_empty);
        }
        if (emailEditText.getText()
          .toString()
          .equalsIgnoreCase("charlie") && passwordEditText.getText()
          .toString()
          .equalsIgnoreCase("abc123")) {

        } else if (emailEditText.getText()
          .toString()
          .equalsIgnoreCase("charlie")) {
          passwordEditText.setText("");
          passwordEditText.setHint("cannot contain password");
        } else if (passwordEditText.getText()
          .toString()
          .equalsIgnoreCase("abc123")) {
          emailEditText.setText("");
          emailEditText.setHint("cannot contain username");
        } else if (!emailEditText.getText()
          .toString().equals("") && !passwordEditText.getText()
          .toString().equals("")) {
          SharedPreferences.Editor editor = sharedPreferences.edit();
          editor.putString(PASSWORD_NAME_KEY, emailEditText.getText()
            .toString());
          editor.putString(PASSWORD_KEY, passwordEditText.getText()
            .toString());
          editor.apply();
          Intent intent = new Intent(LoginActivity.this, BreedsActivity.class);
          startActivity(intent);
        }
      }
    });
  }
}

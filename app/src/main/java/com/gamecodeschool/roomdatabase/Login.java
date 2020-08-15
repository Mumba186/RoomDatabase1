package com.gamecodeschool.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gamecodeschool.roomdatabase.Database.UserDao;
import com.gamecodeschool.roomdatabase.Database.UserDatabase;
import com.gamecodeschool.roomdatabase.Database.UserEntity;

public class Login extends AppCompatActivity {

    EditText userId,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userIdText = userId.getText().toString();
                final String passwordText = password.getText().toString();
                //create user Entity
               // final UserEntity userEntity = new UserEntity();
               // userEntity.setUserId(userId.getText().toString());
              //  userEntity.setPassword(password.getText().toString());

                if (userIdText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Fill all Fields!",Toast.LENGTH_SHORT).show();
                }else{
                    //perform query

                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    final UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity = userDao.login(userIdText,passwordText);
                            if(userEntity==null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                        userId.setText("");
                                        password.setText("");
                                    }
                                });
                            }else{
                                String name = userEntity.name;
                                startActivity(new Intent(Login.this,HomeScreen.class)
                                        .putExtra("name",name));

                            }

                        }
                    }).start();

                }

            }
        });
    }
}
package com.example.itschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.itschool.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private EditText user_name_field, user_bio_field;
    Button btnInput, btnReg;
    FirebaseAuth auth; //база для авторизации
    FirebaseDatabase db; // для подключения к базе данных
    DatabaseReference users; // для работы с таблицей внутри базы данных
    RelativeLayout startFon;
  //  private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //   mAuth = FirebaseAuth.getInstance();
        auth = FirebaseAuth.getInstance(); // запускаем авторизацию в базе данных
        db = FirebaseDatabase.getInstance(); //подключаемся к базе данных
        users = db.getReference("Users"); // указываем с какой таблицей работаем

        btnInput = findViewById(R.id.btnInput);
        btnReg = findViewById(R.id.btnReg);
        startFon = findViewById(R.id.startFon);

        user_name_field = findViewById(R.id.user_name_field);
        user_bio_field = findViewById(R.id.user_bio_field);
//вызывает функцию при нажатие кнопки регистрация
        btnReg.setOnClickListener(v -> showRegWindow());

        //вызывает функцию при нажатие кнопки вход
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputWindow();
            }
        });
    }
    // вызываем всплывающее окно на вход
    private void showInputWindow (){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Авторизация"); //заголовок для всполывающего окна
            dialog.setMessage("Введите данные для входа"); // подпись под заголовком

            LayoutInflater inflater = LayoutInflater.from(this); //создали объект
            View input_window = inflater.inflate(R.layout.input_window, null); // получаем нужный шаблон
            dialog.setView(input_window); // шаблон для всплывающего окна
// получение данных из полей
            final MaterialEditText email = input_window.findViewById(R.id.emailField);
            final MaterialEditText pass = input_window.findViewById(R.id.passwordField);
            //кнопка отмены вызова всплывающего окна
            dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    dialogInterface.dismiss();
                }
            });
            // кнопка получения данных , проверка  пользователя и всплывающее окно в случае не ввода данных
            dialog.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                     if (pass.getText().toString().length() <5) {
                        Snackbar.make(startFon,"Введите пароль, который более 5 символов", Snackbar.LENGTH_SHORT).show();
                        return;
                    } if (TextUtils.isEmpty(email.getText().toString())) {
                        Snackbar.make(startFon,"Введите вашу почту", Snackbar.LENGTH_SHORT).show();
                        return;
                    } // авторизация
                     auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                             .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                 @Override
                                 public void onSuccess(AuthResult authResult) {
                                     startActivity(new Intent(MainActivity.this, MapActivity.class));
                                     finish();
                                 } // при НЕ успешной авторизации
                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     Snackbar.make(startFon, "Ошибка авторизации" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                 }
                             });
                }
            });
            dialog.show();
        }



    // вызываем всплывающее окно на регистрацию
    private void showRegWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Зарегистрироваться"); //заголовок для всполывающего окна
        dialog.setMessage("Введите все данные для регистрации"); // подпись под заголовком

        LayoutInflater inflater = LayoutInflater.from(this); //создали объект
        View registr_window = inflater.inflate(R.layout.registr_window, null); // получаем нужный шаблон
        dialog.setView(registr_window); // шаблон для всплывающего окна
// получение данных из полей
        final MaterialEditText name = registr_window.findViewById(R.id.nameField);
        final MaterialEditText pass = registr_window.findViewById(R.id.passwordField);
        final MaterialEditText email = registr_window.findViewById(R.id.emailField);
        final MaterialEditText telephone = registr_window.findViewById(R.id.telephoneField);
 //кнопка отмены вызова всплывающего окна
        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        // кнопка получения данных , проверка  пользователя и всплывающее окно в случае не ввода данных
        dialog.setPositiveButton("Зарегистрироваться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(startFon,"Введите ваше имя", Snackbar.LENGTH_SHORT).show();
                    return;
                } if (pass.getText().toString().length() <5) {
                    Snackbar.make(startFon,"Введите пароль, который более 5 символов", Snackbar.LENGTH_SHORT).show();
                    return;
                } if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(startFon,"Введите вашу почту", Snackbar.LENGTH_SHORT).show();
                    return;
                } if (TextUtils.isEmpty(telephone.getText().toString())) {
                    Snackbar.make(startFon,"Введите ваш телефон", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                // регистрация пользователя , вызывает обработчик, пользователь успешно добавлен в базу данных
                auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user= new User();
                                user.setName(name.getText().toString());
                                user.setPass(pass.getText().toString());
                                user.setEmail(email.getText().toString());
                                user.setTelephone(telephone.getText().toString());

                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Snackbar.make(startFon, "Вы зарегистрировались!", Snackbar.LENGTH_SHORT).show();
                                            }
                                        });


                            }
                        });

            }
        });
        dialog.show();
    }

    // сохраняем текст введеный пользователем на заявку
    public void saveData(View view) {
        String user_name = user_name_field.getText().toString();
        String user_bio = user_bio_field.getText().toString();

        try {
            FileOutputStream fileOutput = openFileOutput("user_data.txt", MODE_PRIVATE);
            fileOutput.write((user_name + ". " + user_bio).getBytes());
            fileOutput.close();

            user_name_field.setText("");
            user_bio_field.setText("");

            Toast.makeText(this, "Заявка отправлена!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Не можем обработать файл", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // показываем текст введеный пользователем
    public void getData(View view) {
        try {
            FileInputStream fileInput = openFileInput("user_data.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader bR = new BufferedReader(reader);

            StringBuilder stringBuffer = new StringBuilder();
            String lines = "";
            while ((lines = bR.readLine()) != null) {
                stringBuffer.append(lines).append("\n");
            }
            Toast.makeText(this, stringBuffer, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // переход между страницами
    public void goContacts(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    public void goWeb(View view) {
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
    }
}
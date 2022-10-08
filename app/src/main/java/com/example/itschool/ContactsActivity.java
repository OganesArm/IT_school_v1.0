package com.example.itschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ContactsActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private String[] newArr= new String[] {"Sergey","Aram","George","Bob","Svetlana", "Mark", "Vlad","Anatoliy", "Jon","Karina","Mariya","Denis","Irina","Natalia","Alex","Tigran"};
    private ListView listView;

    private GestureDetectorCompat gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listView = findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.name_item, R.id.user_name, newArr);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ContactsActivity.this, (String)listView.getItemAtPosition(i), Toast.LENGTH_LONG).show();
            }
        });
        gd=new GestureDetectorCompat(this,this);
        gd.setIsLongpressEnabled(true);
        gd.setOnDoubleTapListener(this);
    }
// в наш текущий проект помещаем событие
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gd.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void goHome (View view) {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
    public void goWeb (View view) {
        Intent intent = new Intent (this, WebActivity.class);
        startActivity(intent);
    }
// отслеживание касания экрана
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
// отслеживание скрола экрана
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }
// отслеживание долгое нажатие экрана
    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Toast.makeText(this, motionEvent.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
// прикоснулся пальцем и убрал с экрана
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }
// двойное нажатие
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
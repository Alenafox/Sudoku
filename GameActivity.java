package com.example.sudoku;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity{
    private GridView grid;
    private Button num1,num2,num3,num4,num5, num6,num7,num8,num9, clearGrid, clearNum;
    private String selectedBtn = "n1";
    private Game game;
    private int seconds = 0; // Секунды таймера
    private int timeOff = 180; // Время отведенное на игру
    private boolean running = true; // Секунды идут
    TextView taimer;


    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            taimer = (TextView) findViewById(R.id.taimer);
            int minute_s_1 = (((timeOff - seconds) % 3600) / 60);
            int sec_s_1 = ((timeOff - seconds) % 60);
            String time_v_1 = String.format("%02d:%02d", minute_s_1, sec_s_1);

            if((timeOff - seconds) >0) {
                taimer.setText(time_v_1);
            } else {
                seconds = 0;
                taimer.setText(""+0);
                running = false;
                Toast.makeText(getApplicationContext(),"Время вышло!", Toast.LENGTH_SHORT).show();
                finish();
            }
            if (running) {
                seconds++;
            }
            handler.postDelayed(this, 1000);
        }
    };
    @Override
    protected void onPause()
    {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        handler.post(runnable);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(handler != null){
            handler.removeCallbacks(runnable);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game = new Game(this,9,9);
        getItems();

        grid.setNumColumns(9);
        grid.setEnabled(true);
        grid.setAdapter(game);

        View.OnClickListener butViewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.num1: selectedBtn = "num1";
                        break;
                    case R.id.num2: selectedBtn = "num2";
                        break;
                    case R.id.num3: selectedBtn = "num3";
                        break;
                    case R.id.num4: selectedBtn = "num4";
                        break;
                    case R.id.num5: selectedBtn = "num5";
                        break;
                    case R.id.num6: selectedBtn = "num6";
                        break;
                    case R.id.num7: selectedBtn = "num7";
                        break;
                    case R.id.num8: selectedBtn = "num8";
                        break;
                    case R.id.num9: selectedBtn = "num9";
                        break;
                }
                Log.d("mytag", "selectedBtn: " + selectedBtn);
            };
        };
        num1.setOnClickListener(butViewClickListener);
        num2.setOnClickListener(butViewClickListener);
        num3.setOnClickListener(butViewClickListener);
        num4.setOnClickListener(butViewClickListener);
        num5.setOnClickListener(butViewClickListener);
        num6.setOnClickListener(butViewClickListener);
        num7.setOnClickListener(butViewClickListener);
        num8.setOnClickListener(butViewClickListener);
        num9.setOnClickListener(butViewClickListener);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                game.setNumber(position, selectedBtn);
                if (game.checkRepeats(selectedBtn)) {
                    Toast.makeText(getApplicationContext(), "Числа повторяются", Toast.LENGTH_SHORT).show();
                    game.clearField(position);
                }
                if (game.checkWin()){
                    Toast.makeText(getApplicationContext(),"Вы выиграли!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        View.OnClickListener menuViewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                setResult(RESULT_OK, intent);
                startActivity(intent);
            };
        };
        findViewById(R.id.mainMenu).setOnClickListener(menuViewClickListener);
    }

    private void getItems() {
        grid = findViewById(R.id.field);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);
    }
}

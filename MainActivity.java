package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button newGame, continueGame, exit;
    TextView titleGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        newGame = findViewById(R.id.btnNewGame);
        continueGame = findViewById(R.id.btnContinue);
        exit = findViewById(R.id.btnExit);
        titleGame = findViewById(R.id.title);

        setFont();
    }
    @Override
    public void onDestroy() {
        moveTaskToBack(true);
        super.onDestroy();
        System.exit(0);
    }

    public void continueGame(View view){
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(intent);
    }
    public void newGame(View view){
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(intent);
    }
    public void exitGame(View view){
        //finish();
        onDestroy();
    }
    private void setFont(){
        Typeface type = Typeface.createFromAsset(getAssets(), "font.ttf");
        titleGame.setTypeface(type);
        newGame.setTypeface(type);
        continueGame.setTypeface(type);
        exit.setTypeface(type);
    }
}

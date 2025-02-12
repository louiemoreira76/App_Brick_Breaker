package com.example.brickbreaker2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

   @Override
    public void onCreate(@Nullable Bundle saveInstanceState){
       super.onCreate(saveInstanceState);
       setContentView(R.layout.game_over);

       ivNewHighest = findViewById(R.id.ivNewHeighest);
       tvPoints = findViewById(R.id.tvPoints);
       int Points = getIntent().getExtras().getInt("points");

        if (points == 240){
            ivNewHighest.setVisibility(View.VISIBLE);
        }
        tvPoints.setText("" + points);
   }

   public void Restart(View view){
       Intent intent = new Intent(GameOver.this, MainActivity.class);
       startActivity(intent);
       finish();
   }

   public void Exit(View view){
       finish();
   }
}

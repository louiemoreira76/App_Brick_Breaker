package com.example.brickbreaker2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView; // Importação adicionada
import android.widget.TextView;  // Importação adicionada

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {
    private ImageView ivNewHighest;
    private TextView tvPoints;
    private int points;

    @Override
    public void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.game_over);

        ivNewHighest = findViewById(R.id.ivNewHeighest); // Corrija o nome do ID se necessário
        tvPoints = findViewById(R.id.tvPoints);
        points = getIntent().getExtras().getInt("points"); // Corrigido: use "points" em vez de "Points"

        if (points == 240) {
            ivNewHighest.setVisibility(View.VISIBLE);
        }
        tvPoints.setText("" + points);
    }

    public void Restart(View view) {
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void Exit(View view) {
        finish();
    }
}
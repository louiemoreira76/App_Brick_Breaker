package com.example.brickbreaker2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
import java.util.logging.Handler;

public class GameView extends View {
    Context context;
    float ballX, ballY;
    Velocity velocity = new Velocity(25,32);
    Handler handler;
    final long Update_Millis = 30; //limite
    Runnable runnable;
    Paint texPaint = new Paint();
    Paint healthPaint = new Paint();
    Paint brickPaint =  new Paint();
    float Text_Size = 120;
    float paddleX, paddleY;
    float oldX, oldPaddleX;
    int points = 0;
    byte life = 3;
    Bitmap ball, paddle;
    int dWidth, dHeight;
    int ballWidth, ballHeight;
    MediaPlayer mpHit, mpMiss, mpBreak;
    Random random;
    Brick[] bricks = new Brick[30];
    int numBricks = 0;
    int brokenBricks = 0;
    boolean gameOver = false;

    public GameView(Context context){
        super(context);
        this.context = context;
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.better_ball_saul);
        paddle = BitmapFactory.decodeResource(getResources(), R.drawable.bar);

        handler = new Handler(); //criar uma instancia executavel substituir metodo run
        runnable = new Runnable(){
            @Override
            public  void run(){
                invalidate();//atualiza a visulização chama indiretamente ondraw pele framework android
            }
        };
        mpHit = MediaPlayer.create(context, R.raw.hiit);
        mpMiss = MediaPlayer.create(context, R.raw.miss);
        mpBreak = MediaPlayer.create(context, R.raw.breakingb);
        texPaint.setColor(Color.RED);
        texPaint.setTextSize(Text_Size);
        texPaint.setTextAlign(Paint.Align.LEFT);
        healthPaint.setColor(Color.GREEN);
        brickPaint.setColor(Color.argb(255,249,129,0));
        //altura e largura da tela calculo
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point(); // objeto de exibixao Point tem a largura da tela
        dWidth = size.x;
        dHeight = size.y;
        //iniciar cordenadas da bola
        random = new Random();
        ballX = random.nextInt(dWidth - 50);
        ballY = dHeight/3;
        paddleY = (dHeight * 4) /5;
        paddleX = dWidth/2 - paddle.getWidth()/2;
        //inicializar a largura e altura da bola
        ballWidth = ball.getWidth();
        ballHeight = ball.getHeight();
        createBricks();
    }

    private void createBricks(){
        int brickWidth = dWidth / 8;
        int brickHeght = dHeight / 16;
        //contruindo parede de tijolos
        for (short column = 0; column < 8; column++){
            for (short row = 0; row < 3; row++){
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeght);
                numBricks++;
            }
        }
    }
    //animação de quebras de bola
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        cavas.drawColor(Color.BLACK);
        ballX += velocity.getX();
        ballY += velocity.getY();
        //se
        if ((ballX >= dWidth - ball.getWidth()) || ballX <= 0){
            velocity.setX(velocity.getX() * -1);
        }
        if (ballY <= 0){
            velocity.setY(velocity.getY() * -1);
        }
        if (ballY > paddleY + paddle.getHeight()){
            ballX = 1 + random.nextInt(dWidth - ball.getWidth() -1);
            ballY = dHeight/3;
            if (mpMiss != null){
                mpMiss.start();
            }
            //
            velocity.setX(xVelocity());
            velocity.setY(32);
            life--;
            if (life == 0){
                gameOver = true;
                //launchGameOver();
            }
            if (((ballX + ball.getWidth()) >= paddleX)
                && (ballX <= paddleX + paddle.getWidth())
                && (ballY + ball.getHeight() >= paddleY)
                && (ballY + ball.getHeight() <= paddleY + paddle.getHeight())){
                    if (mpHit != null){
                        mpHit.start();
                    }
                    velocity.setX(velocity.getX() + 1);
                    velocity.setY((velocity.getY() + 1) * -1);
            }
            canvas.drawBitmap(ball, ballX, ballX, null);
            canvas.drawBitmap(paddle, paddleX, paddleY, null);

            for(short i=0; i<numBricks; i++){
                if (bricks[i].getVisible()){
                    canvas.drawRect(bricks[i].column * bricks[i].width + 1, bricks[i].row * bricks[i].height + 1, bricks[i].column * bricks[i].width + bricks[i].width -1, bricks[i].row * bricks[i].height + bricks[i].height -1, brickPaint);
                }
            }
            canvas.drawText("" + points, 20, Text_Size, texPaint);
            if (life == 2){
                healthPaint.setColor(Color.YELLOW);
            }else if(life == 1){
                healthPaint.setColor(Color.RED);
            }
            canvas.drawRect(dWidth-200, 30, dWidth - 200 + 60 * life, 80, healthPaint);
            for(short i=0; i<numBricks; i++){
                if (bricks[i].getVisible()){
                    if (ballX + ballWidth >= bricks[i].column * bricks[i].width
                    && ballX <= bricks[i].column * bricks[i].width + bricks[i].width
                    && ballY <= bricks[i].row * bricks[i].height + bricks[i].height
                    && ballY >= bricks[i].row * bricks[i].height){
                        if (mpBreak != null){
                            mpBreak.start();
                        }
                        velocity.setY((velocity.getY() + 1) * -1);
                        bricks[i].setVisible();
                        points += 10;
                        brokenBricks++;
                        if (brokenBricks == 24){
                            //launchGameOver();
                        }
                    }
                }
            }
            if (brokenBricks == numBricks){
                gameOver = true;
            }
            if (!gameOver){
                handler.postDelayed(runnable, Update_Millis);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if (touchY >= paddleY){
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN){
                oldX = event.getX();
                oldPaddleX = paddleX;
            }
            if (action == MotionEvent.ACTION_MOVE){
                float shift = oldX - touchX;
                float newPaddlex = oldPaddleX - shift;
                if (newPaddlex <= 0)
                    paddleX = 0;
                else if (newPaddlex >= dWidth - paddle.getWidth())
                    paddleX = dWidth - paddle.getWidth();
                else
                    paddleX = newPaddlex;
            }
        }
        return true;
    }

    private void launchGameOver(){
        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(context, GameOver.class);
        intent.putExtra("points", points);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    private int xVelocity(){
        int[] values = {-35, -30, -25, 25, 30, 35};
        int index = random.nextInt(6);
        return values[index];
    }
}
//desenhar animação de manipilação da bola detequetando tijolos e colisao exibindo pontos e barra
//CLASSE MAIS INPORTANTE
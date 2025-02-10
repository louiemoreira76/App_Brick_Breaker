package com.example.brickbreaker2;

public class Velocity {
    // Variáveis da direção de x e y
    private int x, y;
    //6:25
    // Construtor
    public Velocity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Métodos get
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Métodos set
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

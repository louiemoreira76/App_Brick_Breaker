package com.example.brickbreaker2;

public class Brick {
    private boolean isVisible;//visibilidade do tijolo
    public int row, column, width, height;

    public Brick(int row, int column, int width, int height){
        isVisible = true; //pode ser atigindo
        this.row = row;
        this.column = column;
        this.width = width;
        this.height = height;
    }

    public void setVisible(){
        isVisible = false;
    }

    public boolean getVisible(){
        return isVisible;
    }

}

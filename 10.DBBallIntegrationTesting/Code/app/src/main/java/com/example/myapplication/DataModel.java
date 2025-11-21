package com.example.myapplication;

public class DataModel {

    private long id;

    private float sizeX;
    private float sizeY;
    private float positionX;
    private float positionY;
    private float speedX;
    private float speedY;

    private int r;
    private int g;
    private int b;

    public DataModel() {
        this.sizeX = 50f;
        this.sizeY = 50f;
        this.positionX = 0f;
        this.positionY = 0f;
        this.speedX = 0f;
        this.speedY = 0f;
        this.r = 255;
        this.g = 0;
        this.b = 0;
    }

    public DataModel(long id,
                     float sizeX, float sizeY,
                     float positionX, float positionY,
                     float speedX, float speedY,
                     int r, int g, int b) {

        this.id = id;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.positionX = positionX;
        this.positionY = positionY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    // NO ID
    public DataModel(float sizeX, float sizeY,
                     float positionX, float positionY,
                     float speedX, float speedY,
                     int r, int g, int b) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.positionX = positionX;
        this.positionY = positionY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "id=" + id +
                ", PosX=" + positionX +
                ", PosY=" + positionY +
                ", SizX=" + sizeX +
                ", SizY=" + sizeY +
                ", SpdX=" + speedX +
                ", SpdY=" + speedY +
                ", R=" + r +
                ", G=" + g +
                ", B=" + b +
                '}';
    }

    // Getters & Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public float getSizeX() { return sizeX; }
    public float getSizeY() { return sizeY; }
    public float getPositionX() { return positionX; }
    public float getPositionY() { return positionY; }
    public float getSpeedX() { return speedX; }
    public float getSpeedY() { return speedY; }

    public void setSizeX(float s) { sizeX = s; }
    public void setSizeY(float s) { sizeY = s; }
    public void setPositionX(float p) { positionX = p; }
    public void setPositionY(float p) { positionY = p; }
    public void setSpeedX(float s) { speedX = s; }
    public void setSpeedY(float s) { speedY = s; }

    public int getR() { return r; }
    public int getG() { return g; }
    public int getB() { return b; }
    public void setR(int r) { this.r = r; }
    public void setG(int g) { this.g = g; }
    public void setB(int b) { this.b = b; }
}

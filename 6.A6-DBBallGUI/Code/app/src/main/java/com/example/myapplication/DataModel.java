package com.example.myapplication;

public class DataModel {

    private long id;

    private float sizeX;
    private float sizeY;
    private float positionX;
    private float positionY;
    private float speedX;
    private float speedY;

    public DataModel() {
        this.sizeX = 50f;
        this.sizeY = 50f;
        this.positionX = 0f;
        this.positionY = 0f;
        this.speedX = 0f;
        this.speedY = 0f;

    }

    public DataModel(long id, float sizeX, float sizeY, float positionX, float positionY, float speedX, float speedY) {
        this.id = id;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.positionX = positionX;
        this.positionY = positionY;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "id=" + getId() +
                ", PosX='" + positionX + '\'' +
                ", PosY=" + positionY + '\'' +
                ", SizX='" + sizeX + '\'' +
                ", SizY=" + sizeY + '\'' +
                ", SpdX=" + speedX + '\'' +
                ", SpdY=" + speedY;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getSizeX() {
        return sizeX;
    }

    public void setSizeX(float sizeX) {
        this.sizeX = sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

    public void setSizeY(float sizeY) {
        this.sizeY = sizeY;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
}


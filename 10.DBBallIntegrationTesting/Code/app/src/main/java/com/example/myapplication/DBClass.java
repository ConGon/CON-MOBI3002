package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBClass extends SQLiteOpenHelper {

    private static final int DB_VERSION = 36;
    private static final String TABLE_NAME = "Balls";

    private String dbName;

    public DBClass(Context context, String dbName) {
        super(context, dbName, null, DB_VERSION);
        this.dbName = dbName;
    }

    public DBClass(Context context) {
        this(context, "balls.db");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "sizeX REAL," +
                        "sizeY REAL," +
                        "positionX REAL," +
                        "positionY REAL," +
                        "speedX REAL," +
                        "speedY REAL," +
                        "r INTEGER," +
                        "g INTEGER," +
                        "b INTEGER" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldV, int newV) {
        onUpgrade(db, oldV, newV); // safe downgrade
    }

    public void save(DataModel data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("sizeX", data.getSizeX());
        cv.put("sizeY", data.getSizeY());
        cv.put("positionX", data.getPositionX());
        cv.put("positionY", data.getPositionY());
        cv.put("speedX", data.getSpeedX());
        cv.put("speedY", data.getSpeedY());
        cv.put("r", data.getR());
        cv.put("g", data.getG());
        cv.put("b", data.getB());

        db.insert(TABLE_NAME, null, cv);
    }

    public void deleteById(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
    }

    public void clearAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }


    public List<DataModel> findAll() {
        List<DataModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        while (cursor.moveToNext()) {
            list.add(new DataModel(
                    cursor.getLong(0),
                    cursor.getFloat(1), cursor.getFloat(2),
                    cursor.getFloat(3), cursor.getFloat(4),
                    cursor.getFloat(5), cursor.getFloat(6),
                    cursor.getInt(7), cursor.getInt(8), cursor.getInt(9)
            ));
        }
        cursor.close();
        return list;
    }
}


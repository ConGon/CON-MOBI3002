package com.codelab.basics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBClass extends SQLiteOpenHelper implements DB_Interface {

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "DB_Name.db";

    private static final String TABLE_NAME = "sample_table";

    private static final String COL_ID = "id";
    private static final String COL_NAME = "name_col";
    private static final String COL_TYPE = "type_col";
    private static final String COL_NUMBER = "num_col";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COL_NAME + " TEXT, " +
                    COL_TYPE + " TEXT, " +
                    COL_NUMBER + " INTEGER)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBClass", "DB onCreate() " + SQL_CREATE_TABLE);
        db.execSQL(SQL_CREATE_TABLE);

        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + COL_NAME + ", " + COL_TYPE + ", " + COL_NUMBER + ") VALUES('Bulbasaur', 'Grass, Poison', 1)");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + COL_NAME + ", " + COL_TYPE + ", " + COL_NUMBER + ") VALUES('Charmander', 'Fire', 4)");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + COL_NAME + ", " + COL_TYPE + ", " + COL_NUMBER + ") VALUES('Squirtle', 'Water', 7)");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + COL_NAME + ", " + COL_TYPE + ", " + COL_NUMBER + ") VALUES('Caterpie', 'Bug', 10)");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + COL_NAME + ", " + COL_TYPE + ", " + COL_NUMBER + ") VALUES('Pikachu', 'Electric', 25)");

        Log.d("DBClass", "DB onCreate() complete.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DBClass", "DB onUpgrade() to version " + DATABASE_VERSION);
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    @Override
    public int count() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        Log.v("DBClass", "getCount=" + cnt);
        return cnt;
    }

    @Override
    public int save(Pokemon pokemon) {
        Log.v("DBClass", "add => " + pokemon.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, pokemon.getPokemon_Name());
        values.put(COL_TYPE, pokemon.getPokemon_Type());
        values.put(COL_NUMBER, pokemon.getPokemon_Number());

        db.insert(TABLE_NAME, null, values);
        db.close();

//        dump();
        return 0;
    }

    @Override
    public int update(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, pokemon.getPokemon_Name());
        values.put(COL_TYPE, pokemon.getPokemon_Type());
        values.put(COL_NUMBER, pokemon.getPokemon_Number());

        int rowsAffected = db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{String.valueOf(pokemon.getId())});
        db.close();

        Log.v("DBClass", "update => rows affected: " + rowsAffected);
        return rowsAffected;
    }

    @Override
    public int deleteById(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deleted = db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        Log.v("DBClass", "deleteById => " + deleted + " row(s) deleted.");
        return deleted;
    }

    private final Random r = new Random();

    private void addDefaultRows() {
        if (count() > 1) {
            Log.v("DBClass", "Rows already exist in DB.");
        } else {
            Log.v("DBClass", "No rows in DB... adding sample Pokémon.");

            save(new Pokemon(1, "Bulbasaur", "Grass, Poison", 1));
            save(new Pokemon(2, "Charmander", "Fire", 4));
            save(new Pokemon(3, "Squirtle", "Water", 7));
            save(new Pokemon(4, "Caterpie", "Bug", 10));
            save(new Pokemon(5, "Pikachu", "Electric", 25));
            save(new Pokemon(6, "Onix", "Rock, Ground", 95));
        }

        save(new Pokemon(7, "Drowzee", "Psychic", 96));
    }

    @Override
    public List<Pokemon> findAll() {
        List<Pokemon> temp = new ArrayList<>();

        addDefaultRows();

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Pokemon item = new Pokemon(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_NUMBER))
                );
                temp.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        Log.v("DBClass", "findAll => " + temp.toString());
        return temp;
    }

    @Override
    public String getNameById(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String name = null;

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_NAME},
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
        }

        cursor.close();
        db.close();

        return name;
    }

    @Override
    public Pokemon getMax() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_NUMBER + " DESC LIMIT 1", null);

        Pokemon maxData = null;
        if (cursor.moveToFirst()) {
            maxData = new Pokemon(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_NUMBER))
            );
        }

        cursor.close();
        db.close();
        return maxData;
    }

    @Override
    public void incAccessCount(long id) {
        Log.v("DBClass", "Incrementing access count for ID = " + id);

        String cmdString = "UPDATE " + TABLE_NAME +
                " SET " + COL_NUMBER + " = " + COL_NUMBER + " + 1 WHERE " + COL_ID + "=" + id;

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(cmdString);
        db.close();
    }

    @Override
    public long getMostAccessed() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_ID + " FROM " + TABLE_NAME +
                " ORDER BY " + COL_NUMBER + " DESC LIMIT 1", null);

        long mostID = 0;
        if (cursor.moveToFirst()) {
            mostID = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID));
        }

        cursor.close();
        db.close();
        return mostID;
    }

    private void dump() {
        List<Pokemon> all = findAll();
        for (Pokemon d : all) {
            Log.v("DBClass", d.toString());
        }
    }
}

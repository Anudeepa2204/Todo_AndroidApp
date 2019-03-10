package com.application.todo.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.application.todo.Model.Todo;
import com.application.todo.Model.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";

    private static final String COLUMN_TODO_ID = "id";
    private static final String COLUMN_USER_EMAIL = "useremail";
    private static final String COLUMN_TODO_TITLE = "title";
    private static final String COLUMN_TODO_CONTENT = "content";

    private static final String USER_TABLE = "user";
    private static final String TODO_TABLE = "todos";

    private static final String DATABASE_NAME = "todo.db";

    private static final String TABLE_USER_CREATE = "CREATE TABLE "
            + USER_TABLE + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EMAIL + " TEXT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_PASSWORD + " TEXT)";

    private static final String TABLE_TODO_CREATE = "CREATE TABLE "
            + TODO_TABLE + " ("
            + COLUMN_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_EMAIL + " TEXT, "
            + COLUMN_TODO_TITLE + " TEXT, "
            + COLUMN_TODO_CONTENT + " TEXT)";

    private static final String TABLE_USER_DELETE = "DROP TABLE IF EXISTS " + USER_TABLE;
    private static final String TABLE_TODO_DELETE = "DROP TABLE IF EXISTS " + TODO_TABLE;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USER_CREATE);
        db.execSQL(TABLE_TODO_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_USER_DELETE);
        db.execSQL(TABLE_TODO_DELETE);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EMAIL, user.getEmail());
        cv.put(COLUMN_NAME, user.getName());
        cv.put(COLUMN_PASSWORD, user.getPassword());
        db.insert(USER_TABLE, null, cv);
        db.close();
    }

    public void addTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_EMAIL, todo.getUser_id());
        cv.put(COLUMN_TODO_TITLE, todo.getTitle());
        cv.put(COLUMN_TODO_CONTENT, todo.getContent());
        db.insert(TODO_TABLE, null, cv);
        db.close();
    }

//    public void getAllTodos(String email){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Todo[] result;
//        String[] columns = {COLUMN_TODO_TITLE, COLUMN_TODO_CONTENT};
//        String[] params = {email};
//        String selection = COLUMN_USER_EMAIL + " = ?";
//        Cursor cursor = db.query(TODO_TABLE, columns, selection, params, null, null, null);
//        int count = cursor.getCount();
//    }

    public User getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User result = new User();
        String[] columns = {COLUMN_ID, COLUMN_EMAIL, COLUMN_NAME, COLUMN_PASSWORD};
        String[] params = {email};
        String selection = COLUMN_EMAIL + " = ?";
        Cursor cursor = db.query(USER_TABLE, columns, selection, params, null, null, null);
        int count = cursor.getCount();
        if (count > 0) {
            result.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            result.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            result.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
            cursor.close();
            db.close();
            return new User();
        }
        cursor.close();
        db.close();
        return result;
    }

    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_EMAIL, COLUMN_NAME, COLUMN_PASSWORD};
        String[] params = {email, password};
        String selection = COLUMN_EMAIL + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.query(USER_TABLE, columns, selection, params, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0) {
            return true;
        }
        return false;
    }

    public boolean emailAlreadyExists(String user_email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_EMAIL};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {user_email};
        Cursor c = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = c.getCount();
        c.close();
        db.close();
        if (count > 0) {
            return true;
        }
        return false;
    }

}

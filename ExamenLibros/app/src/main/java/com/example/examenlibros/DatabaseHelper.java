package com.example.examenlibros;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Definición de la base de datos y de la tabla de libros
    private static final String DATABASE_NAME = "LibrosDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "libros";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_PUBLISHER = "publisher";
    private static final String COLUMN_PAGES = "pages";
    private static final String COLUMN_ISBN = "isbn";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de libros
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_AUTHOR + " TEXT, "
                + COLUMN_PUBLISHER + " TEXT, "
                + COLUMN_PAGES + " INTEGER, "
                + COLUMN_ISBN + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar la tabla y crearla de nuevo si se actualiza la base de datos
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Método para insertar un nuevo libro
    public long insertBook(String title, String author, String publisher, int pages, String isbn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_AUTHOR, author);
        values.put(COLUMN_PUBLISHER, publisher);
        values.put(COLUMN_PAGES, pages);
        values.put(COLUMN_ISBN, isbn);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    // Método para actualizar un libro existente
    public int updateBook(int id, String title, String author, String publisher, int pages, String isbn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_AUTHOR, author);
        values.put(COLUMN_PUBLISHER, publisher);
        values.put(COLUMN_PAGES, pages);
        values.put(COLUMN_ISBN, isbn);

        int result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    // Método para eliminar un libro
    public int deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    // Método para obtener todos los libros
    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    // Método para buscar libros por título o autor
    public Cursor searchBooks(String titleQuery, String authorQuery) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_TITLE + " LIKE ? OR " + COLUMN_AUTHOR + " LIKE ?";
        String[] args = new String[]{"%" + titleQuery + "%", "%" + authorQuery + "%"};
        return db.rawQuery(query, args);
    }
}

package com.example.examenlibros;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import android.content.res.Resources;



public class MainActivity extends AppCompatActivity {

    // Declaración de los elementos de UI y base de datos
    private DatabaseHelper dbHelper;
    private EditText etTitle, etAuthor, etPublisher, etPages, etIsbn, etSearch;
    private ListView lvBooks;
    private Button btnSave, btnUpdate, btnDelete, btnSearch;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> bookList;
    private int selectedBookId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar la base de datos y elementos UI
        dbHelper = new DatabaseHelper(this);
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etPublisher = findViewById(R.id.etPublisher);
        etPages = findViewById(R.id.etPages);
        etIsbn = findViewById(R.id.etIsbn);
        etSearch = findViewById(R.id.etSearch);
        lvBooks = findViewById(R.id.lvBooks);
        btnSave = findViewById(R.id.btnSave);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnSearch = findViewById(R.id.btnSearch);

        bookList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookList);
        lvBooks.setAdapter(adapter);

        // Configurar eventos de los botones
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBooks();
            }
        });

        // Cargar todos los libros al iniciar la actividad
        loadAllBooks();
    }

    // Método para guardar un nuevo libro
    private void saveBook() {
        String title = etTitle.getText().toString();
        String author = etAuthor.getText().toString();
        String publisher = etPublisher.getText().toString();
        int pages = Integer.parseInt(etPages.getText().toString());
        String isbn = etIsbn.getText().toString();

        long result = dbHelper.insertBook(title, author, publisher, pages, isbn);

        if (result != -1) {
            Toast.makeText(this, "Libro guardado", Toast.LENGTH_SHORT).show();
            clearFields();
            loadAllBooks();
        } else {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para actualizar un libro existente
    private void updateBook() {
        if (selectedBookId == -1) {
            Toast.makeText(this, "Seleccione un libro para actualizar", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = etTitle.getText().toString();
        String author = etAuthor.getText().toString();
        String publisher = etPublisher.getText().toString();
        int pages = Integer.parseInt(etPages.getText().toString());
        String isbn = etIsbn.getText().toString();

        int result = dbHelper.updateBook(selectedBookId, title, author, publisher, pages, isbn);

        if (result > 0) {
            Toast.makeText(this, "Libro actualizado", Toast.LENGTH_SHORT).show();
            clearFields();
            selectedBookId = -1;
            loadAllBooks();
        } else {
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para eliminar un libro
    private void deleteBook() {
        if (selectedBookId == -1) {
            Toast.makeText(this, "Seleccione un libro para eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        int result = dbHelper.deleteBook(selectedBookId);

        if (result > 0) {
            Toast.makeText(this, "Libro eliminado", Toast.LENGTH_SHORT).show();
            clearFields();
            selectedBookId = -1;
            loadAllBooks();
        } else {
            Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para buscar libros por título o autor
    private void searchBooks() {
        String searchQuery = etSearch.getText().toString();
        Cursor cursor = dbHelper.searchBooks(searchQuery, searchQuery);

        bookList.clear();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Verificar que el índice de las columnas sea válido
                int titleIndex = cursor.getColumnIndex("title");
                int authorIndex = cursor.getColumnIndex("author");

                String title = (titleIndex != -1) ? cursor.getString(titleIndex) : "N/A";
                String author = (authorIndex != -1) ? cursor.getString(authorIndex) : "N/A";

                String bookInfo = title + " - " + author;
                bookList.add(bookInfo);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }

    // Método para cargar todos los libros
    private void loadAllBooks() {
        Cursor cursor = dbHelper.getAllBooks();
        bookList.clear();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Verificar que el índice de las columnas sea válido
                int titleIndex = cursor.getColumnIndex("title");
                int authorIndex = cursor.getColumnIndex("author");

                String title = (titleIndex != -1) ? cursor.getString(titleIndex) : "N/A";
                String author = (authorIndex != -1) ? cursor.getString(authorIndex) : "N/A";

                String bookInfo = title + " - " + author;
                bookList.add(bookInfo);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }

    // Método para limpiar los campos de entrada
    private void clearFields() {
        etTitle.setText("");
        etAuthor.setText("");
        etPublisher.setText("");
        etPages.setText("");
        etIsbn.setText("");
        etSearch.setText("");
    }
}

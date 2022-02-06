package com.example.clientecontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtNombre, txtApellido, txtid;
    TextView lista;
    private void imprimir(){
        Cursor cursor = getContentResolver().query(UserDictionary.Words.CONTENT_URI,
                new String[]{UserDictionary.Words.WORD, UserDictionary.Words.LOCALE},
                null, null, null);
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                Log.d("DICCIONARIOUSUARIO",
                        cursor.getString(0)
                                + " - " + cursor.getString(1));
            }
        }

        Cursor cursorUsuario = getContentResolver().query(
                UsuarioContrato.CONTENT_URI,
                UsuarioContrato.COLUMNS_NAME,
                null, null, null);
        if(cursorUsuario!=null) {
            lista.setText("");
            String tmp = "";
            while (cursorUsuario.moveToNext()) {
                Log.d("USUARIO", cursorUsuario.getString(0) + " - "
                        + cursorUsuario.getString(1));
                tmp += cursorUsuario.getString(0)
                        + " - " + cursorUsuario.getString(1) + "\n";
            }
            lista.setText(tmp);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtApellido = findViewById(R.id.txtApellido);
        txtNombre = findViewById(R.id.txtNombre);
        txtid = findViewById(R.id.txtID);
        lista = findViewById(R.id.txtlista);

        findViewById(R.id.btnInsert).setOnClickListener(
                view -> {
                    ContentValues cv = new ContentValues();
                    cv.put(UsuarioContrato.COLUMN_FIRSTNAME, String.valueOf(txtNombre.getText()));
                    cv.put(UsuarioContrato.COLUMN_LASTNAME, String.valueOf(txtApellido.getText()));

                    Uri uriInsert = getContentResolver().insert(
                            UsuarioContrato.CONTENT_URI,
                            cv
                    );

                    Log.d("CPCliente", uriInsert.toString());

                    Toast.makeText(this, "Usuario insert: \n"+
                            uriInsert.toString(), Toast.LENGTH_LONG).show();
                }

        );
        findViewById(R.id.btnUpdate).setOnClickListener(
                view -> {

                    ContentValues cv = new ContentValues();
                    cv.put(UsuarioContrato.COLUMN_FIRSTNAME, txtNombre.getText().toString());
                    cv.put(UsuarioContrato.COLUMN_LASTNAME, txtApellido.getText().toString());



                    int elementAfectados = getContentResolver().update (
                            Uri.withAppendedPath(UsuarioContrato.CONTENT_URI, txtid.getText().toString()) ,
                            cv,null, null
                    );

                    Log.d("CPCliente","Elementos afectadps: " + elementAfectados);

                    Toast.makeText(this, "Usuario Update: \n"+
                            elementAfectados, Toast.LENGTH_LONG).show();
                }

        );
        findViewById(R.id.btnConsultar).setOnClickListener(
                view -> {
                    imprimir();
                }
        );

        findViewById(R.id.btnEliminar).setOnClickListener(
                view -> {
                    if(!txtid.getText().toString().isEmpty()) {
                        int elemEliminados = getContentResolver().delete(
                                Uri.withAppendedPath(UsuarioContrato.CONTENT_URI, txtid.getText().toString()),
                                null, null
                        );
                        Log.d("CPCliente", "Elemento eliminados: " + elemEliminados);
                        Toast.makeText(this, "Usuario eliminado: \n" +
                                elemEliminados, Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_LONG).show();
                    }
                }
        );
        findViewById(R.id.btnVerUsuario).setOnClickListener(
                view -> {
                    Cursor cursorUsuario = getContentResolver().query(
                            Uri.withAppendedPath(UsuarioContrato.CONTENT_URI,txtid.getText().toString()) ,
                            UsuarioContrato.COLUMNS_NAME,
                            null, null, null);
                    if(cursorUsuario!=null) {

                        lista.setText("");
                        String tmp = "";
                        while (cursorUsuario.moveToNext()) {
                            Log.d("USUARIO", cursorUsuario.getString(0) + " - " + cursorUsuario.getString(1)+ " - " + cursorUsuario.getString(2));
                            tmp += cursorUsuario.getString(0) + "\nNombre: " + cursorUsuario.getString(1) + "\nApellido:"+cursorUsuario.getString(2) +"\n";
                        }
                        lista.setText(tmp);
                    }else{
                        lista.setText("Usuario no existe");
                    }
                }
        );

        findViewById(R.id.btnBuscarNombre).setOnClickListener(
                view -> {
                    Cursor cursorUsuario = getContentResolver().query(
                            Uri.withAppendedPath(UsuarioContrato.CONTENT_URI, txtNombre.getText().toString()) ,
                            UsuarioContrato.COLUMNS_NAME,
                            null, null, null);
                    if(cursorUsuario!=null) {
                        lista.setText("");
                        String tmp = "";
                        while (cursorUsuario.moveToNext()) {
                            Log.d("USUARIO", cursorUsuario.getString(0) + " - " + cursorUsuario.getString(1)+ " - " + cursorUsuario.getString(2));
                            tmp += cursorUsuario.getString(0) + " - " + cursorUsuario.getString(1) + " - " +cursorUsuario.getString(2) +"\n";
                        }
                        lista.setText(tmp);
                    }else{
                        lista.setText("Usuario no existe");
                    }
                }
        );

    }
}
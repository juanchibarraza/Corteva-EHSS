package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class ControladorUsuarios extends AdminSQLiteOpenHelper {

    public ControladorUsuarios(Context context) {
        super(context);
    }

    public boolean InsertOrUpdate(Integer idUsuario, String user, String password, String nombre, String site) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idUsuario", idUsuario);
        values.put("user", user);
        values.put("password", password);
        values.put("nombre", nombre);
        values.put("site", site);
        //Intento hacer un UPDATE si falla hago INSERT
        String where = "idUsuario = ?";
        String[] whereArgs = { Integer.toString(idUsuario) };
        boolean success = db.update("usuarios", values, where, whereArgs) > 0;
        if (!success){
            success = db.insert("usuarios", null, values) > 0;
        }
        db.close();
        return success;
    }

    public ArrayList<String> validarUsuario(String Email,String Password){
        String sql = "SELECT nombre, password, site FROM usuarios WHERE user ='" + Email + "' COLLATE NOCASE;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<String> rows = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            String hassPass = cursor.getString(cursor.getColumnIndex("password"));
            BCrypt.Result result = BCrypt.verifyer(BCrypt.Version.VERSION_2Y)
                    .verifyStrict(Password.getBytes(StandardCharsets.UTF_8), hassPass.getBytes(StandardCharsets.UTF_8));
            if (result.verified) {
                rows.add(cursor.getString(cursor.getColumnIndex("nombre")));
                rows.add(cursor.getString(cursor.getColumnIndex("site")));
            }
        }
        cursor.close();
        db.close();
        return rows;
    }
}
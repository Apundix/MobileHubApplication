package com.evan.chattest2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "NoteDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Note";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "Id";
    private static final String TITLE = "Title";
    private static final String IP_FIELD = "Ip";
    private static final String MAC_ADDRESS = "Mac";
    private static final String DELETED_FIELD = "Deleted";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");



    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceofDatabase(Context context){

        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(TITLE)
                .append(" TEXT, ")
                .append(IP_FIELD)
                .append(" TEXT, ")
                .append(MAC_ADDRESS)
                .append(" TEXT, ")
                .append(DELETED_FIELD)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void addNoteToDatabase(Note note){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, note.getId());
        contentValues.put(TITLE, note.getTitle());
        contentValues.put(IP_FIELD, note.getDescription());
        contentValues.put(MAC_ADDRESS, note.getMac());
        contentValues.put(DELETED_FIELD, getStringFromData (note.getDeleted()));

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

    }


    public void populateNoteListArray(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null)){

            if (result.getCount() !=0)
            {
                while (result.moveToNext())
                {
                    int Id = result.getInt((1));
                    String Title = result.getString(2);
                    String Ip = result.getString(3);
                    String Mac = result.getString(4);
                    String stringDeleted = result.getString(5);
                    Date Deleted = getDateFromString(stringDeleted);
                    Note note = new Note(Id, Mac, Title, Ip, Deleted);
                    Note.noteArrayList.add(note);


                }
            }

        }


    }


    public void updateNoteInDb(Note note){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, note.getId());
        contentValues.put(TITLE, note.getTitle());
        contentValues.put(IP_FIELD, note.getDescription());
        contentValues.put(MAC_ADDRESS, note.getMac());
        contentValues.put(DELETED_FIELD, getStringFromData (note.getDeleted()));

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(note.getId())});
    }


    private String getStringFromData(Date date) {

        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string){

        try{
            return dateFormat.parse(string);
        } catch(ParseException | NullPointerException e){

            return null;
        }

    }

}

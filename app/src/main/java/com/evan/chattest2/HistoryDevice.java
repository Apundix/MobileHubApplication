package com.evan.chattest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class HistoryDevice extends AppCompatActivity {

    private ListView noteListView;
    private static boolean firstRun = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_device);
        initWidgets();
        loadFromDbToMemory();
        setNoteAdapter();
        setOnClickListener();

        getSupportActionBar().hide();
    }

    private void initWidgets() {

        noteListView = findViewById(R.id.noteListView);

    }


    private void loadFromDbToMemory() {


        if(firstRun){
            SQLiteManager sqLiteManager = SQLiteManager.instanceofDatabase(this);
            sqLiteManager.populateNoteListArray();
            sqLiteManager.close();
            finish();
        }
        firstRun = false;

    }

    private void setNoteAdapter() {

        noteAdapter NoteAdapter = new noteAdapter(getApplicationContext(), Note.nonDeletedNotes());
        noteListView.setAdapter(NoteAdapter);

    }

    private void setOnClickListener() {

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Note selectNote = (Note) noteListView.getItemAtPosition(position);
                Intent editNoteIntent = new Intent(getApplicationContext(), Device.class);
                editNoteIntent.putExtra(Note.NOTE_EDIT_EXTRA, selectNote.getId());
                startActivity(editNoteIntent);

            }
        });

    }

    public void newNote(View view){

        Intent newNoteIntent = new Intent(this, Device.class);
        startActivity(newNoteIntent);

    }

    @Override
    protected void onResume(){
        super.onResume();
        setNoteAdapter();

    }

}
package com.evan.chattest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Date;

public class Device extends AppCompatActivity {

    //Todo: One of the main things that are left is to implement that of encryption to the users message
    //Todo: I also need to fix the devices page and potentially add a setting to store the users devices, rather than having to manually implement them every time.

    Button Wake, Delete;
    EditText ipText, macText, titleDevice;
    ImageView History;
    Note selectedNote;
    TextView Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        getSupportActionBar().hide();

        initWidgets();
        checkForEditNote();



        Save = (TextView) findViewById(R.id.Save);
        Delete = (Button) findViewById(R.id.Delete);
        titleDevice = (EditText) findViewById(R.id.titleDevice);
        ipText = (EditText) findViewById(R.id.ipText);
        macText = (EditText) findViewById(R.id.macText);
        Wake = (Button) findViewById(R.id.Wake);
        History = (ImageView) findViewById(R.id.historyClick);

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteNote();
            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openHistory();

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveNote();
                Toast.makeText(Device.this, "Device Saved", Toast.LENGTH_LONG).show();
            }


        });

        Wake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String ip = ipText.getText().toString();
                final String mac = macText.getText().toString();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            WakeOnLan.main(new String[]{ip,mac});
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                Toast.makeText(Device.this, "Device Turned on!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initWidgets(){
        ipText = findViewById(R.id.ipText);
        macText = findViewById(R.id.macText);
        titleDevice = findViewById(R.id.titleDevice);
    }

    private void checkForEditNote() {

        Button Delete;
        Delete = (Button) findViewById(R.id.Delete);

        Intent previousIntent = getIntent();

        int passedNoteId = previousIntent.getIntExtra(Note.NOTE_EDIT_EXTRA, -1);
        selectedNote = Note.getNoteForId(passedNoteId);

        if (selectedNote != null){

            titleDevice.setText(selectedNote.getTitle());
            ipText.setText(selectedNote.getDescription());
            macText.setText(selectedNote.getMac());
        }
        else{

            //This is throwing an error
            Delete.setVisibility(View.INVISIBLE);

        }

    }

    private void saveNote() {

        SQLiteManager sqLiteManager = SQLiteManager.instanceofDatabase(this);

        String ip = String.valueOf(ipText.getText());
        String mac = String.valueOf(macText.getText());
        String title = String.valueOf(titleDevice.getText());

        if (selectedNote == null){

            int id = Note.noteArrayList.size();
            Note newNote = new Note(id, mac, title, ip);
            Note.noteArrayList.add(newNote);
            sqLiteManager.addNoteToDatabase(newNote);
        }
        else{
            selectedNote.setTitle(title);
            selectedNote.setDescription(ip);
            selectedNote.setMac(mac);
            sqLiteManager.updateNoteInDb(selectedNote);

        }

    }


    public void openHistory() {

        Intent intent = new Intent(this, HistoryDevice.class);
        startActivity(intent);

    }

    public void deleteNote(){

        selectedNote.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceofDatabase(this);
        sqLiteManager.updateNoteInDb(selectedNote);
        finish();

    }

    //Todo:Add a logout menu to the devices page and tie the wake device and save button together
}
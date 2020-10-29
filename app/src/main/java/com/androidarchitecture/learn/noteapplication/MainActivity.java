package com.androidarchitecture.learn.noteapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements NoteListAdapter.OnDeleteClickListener {

    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    private String TAG = this.getClass().getSimpleName();
    private NoteViewModel noteViewModel;
    private NoteListAdapter noteListAdapter;
    final String note_id = UUID.randomUUID().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        noteListAdapter = new NoteListAdapter(this, this);
        recyclerView.setAdapter(noteListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                assert notes != null;
                if(notes.isEmpty()){
                    addData();
                }
                noteListAdapter.setNotes(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to insert note

            Note note = new Note(note_id, data.getStringExtra(NewNoteActivity.NOTE_ADDED),data.getStringExtra(NewNoteActivity.DESC_ADDED),data.getByteArrayExtra(NewNoteActivity.IMAGE_ADDED));
            noteViewModel.insert(note);




            Toast.makeText(
                    getApplicationContext(),
                    R.string.saved,
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Code to update the note
            Note note = new Note(
                    data.getStringExtra(EditNoteActivity.NOTE_ID),
                    data.getStringExtra(EditNoteActivity.UPDATED_NOTE),
                    data.getStringExtra(EditNoteActivity.UPDATED_DESC),
                    data.getByteArrayExtra(EditNoteActivity.UPDATED_IMAGE));
            noteViewModel.update(note);

            Toast.makeText(
                    getApplicationContext(),
                    R.string.updated,
                    Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void OnDeleteClickListener(Note myNote) {
        // Code for Delete operation
        noteViewModel.delete(myNote);
    }


    private void addData(){

        final NoteDao dao = NoteRoomDatabase.getDatabase(this).noteDao();
        final Note note = new Note("cap","Captain America",getApplicationContext().getResources().getString(R.string.cap),compress(R.drawable.cap));
        final Note note2 = new Note("iron","Iron Man",getApplicationContext().getResources().getString(R.string.iron),compress(R.drawable.ironman));
        final Note note3 = new Note("thor","Thor",getApplicationContext().getResources().getString(R.string.thor),compress(R.drawable.thor));
        final Note note4 = new Note("hulk","Hulk",getApplicationContext().getResources().getString(R.string.hulk),compress(R.drawable.hulk));
        final Note note5 = new Note("nat","Black Widow",getApplicationContext().getResources().getString(R.string.nat),compress(R.drawable.nat));
        final Note note6 = new Note("vision","Vision",getApplicationContext().getResources().getString(R.string.vision),compress(R.drawable.vision));
        final Note note7 = new Note("wanda","Scarlet Witch",getApplicationContext().getResources().getString(R.string.wanda),compress(R.drawable.wanda));
        final Note note8 = new Note("pan","Black Panther",getApplicationContext().getResources().getString(R.string.pan),compress(R.drawable.pan));
        final Note note9 = new Note("spider","Spider Man",getApplicationContext().getResources().getString(R.string.spider),compress(R.drawable.spider));
        final Note note10 = new Note("marvel","Captain Marvel",getApplicationContext().getResources().getString(R.string.marvel),compress(R.drawable.marvel));
        final Note note11 = new Note("thanos","Thanos",getApplicationContext().getResources().getString(R.string.thanos),compress(R.drawable.thanos));

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(note);
                dao.insert(note2);
                dao.insert(note3);
                dao.insert(note4);
                dao.insert(note5);
                dao.insert(note6);
                dao.insert(note7);
                dao.insert(note8);
                dao.insert(note9);
                dao.insert(note10);
                dao.insert(note11);
            }
        });

    }

    byte[] compress(int drawable){
        Bitmap mIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(),drawable);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mIcon.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }


}

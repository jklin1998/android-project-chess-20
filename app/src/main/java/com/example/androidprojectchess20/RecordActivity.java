package com.example.androidprojectchess20;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;

public class RecordActivity extends MainActivity {
    public int pageNum = 1;

    public void sortByName()
    {

    }

    public void sortByDate()
    {

    }

    public void newIntent()
    {
        Intent intent = new Intent(this, PlaybackActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Button backButton = (Button)findViewById(R.id.close);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        Button timesort = (Button)findViewById(R.id.datesort);
        timesort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sortByDate();
            }
        });

        Button namesort = (Button)findViewById(R.id.namesort);
        namesort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sortByName();
            }
        });

        ImageView play1 = (ImageView)findViewById(R.id.file1);
        ImageView play2 = (ImageView)findViewById(R.id.file2);
        ImageView play3 = (ImageView)findViewById(R.id.file3);
        ImageView play4 = (ImageView)findViewById(R.id.file4);
        ImageView play5 = (ImageView)findViewById(R.id.file5);
        ImageView play6 = (ImageView)findViewById(R.id.file6);
        play1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog alert = new AlertDialog.Builder(RecordActivity.this)
                        .setTitle("Play Recording")
                        .setMessage("The current game will be forfeited. Are you sure you want to play this recording?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("Playing...");
                                MainActivity.feed = "b2 b4\nb7 b5\nresign";
                                MainActivity.playbackMode = true;
                                MainActivity.turnCount = 0;
                                showMessage("Tap on Restart Game to replay game.");
                                //playbackRecord();
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        play2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog alert = new AlertDialog.Builder(RecordActivity.this)
                        .setTitle("Play Recording")
                        .setMessage("The current game will be forfeited. Are you sure you want to play this recording?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        play3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog alert = new AlertDialog.Builder(RecordActivity.this)
                        .setTitle("Play Recording")
                        .setMessage("The current game will be forfeited. Are you sure you want to play this recording?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        play4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog alert = new AlertDialog.Builder(RecordActivity.this)
                        .setTitle("Play Recording")
                        .setMessage("The current game will be forfeited. Are you sure you want to play this recording?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        play5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog alert = new AlertDialog.Builder(RecordActivity.this)
                        .setTitle("Play Recording")
                        .setMessage("The current game will be forfeited. Are you sure you want to play this recording?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });


        play6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog alert = new AlertDialog.Builder(RecordActivity.this)
                        .setTitle("Play Recording")
                        .setMessage("The current game will be forfeited. Are you sure you want to play this recording?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        final EditText pageText = (EditText)findViewById(R.id.pageInput);
        pageText.setText(Integer.toString(pageNum));
    }
}

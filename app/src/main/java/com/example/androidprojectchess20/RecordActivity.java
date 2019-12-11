package com.example.androidprojectchess20;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class RecordActivity extends MainActivity {
    public int pageNum = 0;
    ArrayList<Playback> playlist = new ArrayList<Playback>();
    ArrayList<String> playlistNames = new ArrayList<String>();

    public String searchGetFeed(String filename)
    {
        for(Playback play : playlist)
        {
            if(play.getName().equals(filename))
            {
                return play.getEntirePlayback();
            }
        }
        return "N/A";
    }

    public long searchGetDate(String filename)
    {
        for(Playback play : playlist)
        {
            if(play.getName().equals(filename))
            {
                return play.getDateTime();
            }
        }
        return 0;
    }

    /**
     * Loads all of the saved games onto the Record Menu. Called whenever the list is sorted by name or date.
     */
    public void loadEverything()
    {
        TextView pla1 = (TextView)findViewById(R.id.text1);
        try {
            String temp = playlistNames.get(0+(6*(pageNum)));
            long time = searchGetDate(temp);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            pla1.setText((1+pageNum*6)+": "+temp+" ("+(String)sdf.format(time)+")");
        } catch(Exception e)
        {
            pla1.setText("Playback "+(1+pageNum*6)+": N/A");
        }

        TextView pla2 = (TextView)findViewById(R.id.text2);
        try {
            String temp = playlistNames.get(1+(6*(pageNum)));
            long time = searchGetDate(temp);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            pla2.setText((2+pageNum*6)+": "+temp+" ("+(String)sdf.format(time)+")");
        } catch(Exception e)
        {
            pla2.setText("Playback "+(2+pageNum*6)+": N/A");
        }

        TextView pla3 = (TextView)findViewById(R.id.text3);
        try {
            String temp = playlistNames.get(2+(6*(pageNum)));
            long time = searchGetDate(temp);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            pla3.setText((3+pageNum*6)+": "+temp+" ("+(String)sdf.format(time)+")");
        } catch(Exception e)
        {
            pla3.setText("Playback "+(3+pageNum*6)+": N/A");
        }

        TextView pla4 = (TextView)findViewById(R.id.text4);
        try {
            String temp = playlistNames.get(3+(6*(pageNum)));
            long time = searchGetDate(temp);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            pla4.setText((4+pageNum*6)+": "+temp+" ("+(String)sdf.format(time)+")");
        } catch(Exception e)
        {
            pla4.setText("Playback "+(4+pageNum*6)+": N/A");
        }

        TextView pla5 = (TextView)findViewById(R.id.text5);
        try {
            String temp = playlistNames.get(4+(6*(pageNum)));
            long time = searchGetDate(temp);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            pla5.setText((5+pageNum*6)+": "+temp+" ("+(String)sdf.format(time)+")");
        } catch(Exception e)
        {
            pla5.setText("Playback "+(5+pageNum*6)+": N/A");
        }

        TextView pla6 = (TextView)findViewById(R.id.text6);
        try {
            String temp = playlistNames.get(5+(6*(pageNum)));
            long time = searchGetDate(temp);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            pla6.setText((6+pageNum*6)+": "+temp+" ("+(String)sdf.format(time)+")");
        } catch(Exception e)
        {
            pla6.setText("Playback "+(6+pageNum*6)+": N/A");
        }
    }

    /**
     * Sorts everything in the ArrayList by name.
     */
    public void sortByName()
    {
        playlistNames.clear();
        for(Playback p : playlist)
        {
            playlistNames.add(p.getName());
        }
        Collections.sort(playlistNames);
        loadEverything();
    }

    /**
     * Sorts everything in the ArrayList by date.
     */
    public void sortByDate()
    {
        playlistNames.clear();
        ArrayList<Long> dates = new ArrayList<Long>();
        for(Playback p: playlist)
        {
            dates.add(p.getDateTime());
        }
        dates.sort(null);
        for(Long l : dates)
        {
            for(Playback p : playlist)
            {
                if(l == p.getDateTime())
                {
                    playlistNames.add(p.getName());
                }
            }
        }
        loadEverything();
    }

    /**
     * This class directs to another window.
     * NOTE: This function went unused.
     */
    public void newIntent()
    {
        Intent intent = new Intent(this, PlaybackActivity.class);
        startActivity(intent);
    }

    /**
     * Loads the entire page with recorded games.
     */
    public void loadAllGames()
    {
        playlist.clear();
        try (ObjectInputStream replayinput = new ObjectInputStream(new FileInputStream(RecordActivity.this.getFilesDir().getPath().toString() + "/replays.txt"))) {
            while(true)
            {
                Playback temp = (Playback)replayinput.readObject();
                System.out.println("Pulled: "+temp.getName());
                playlist.add(temp);
            }
        } catch(EOFException eof)
        {
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method is only used for testing purposes.
     */
    public void testModule()
    {

    }

    /**
     * This method is called when the Record Menu is called via StartActivity().
     * @param savedInstanceState
     */
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
                                if((1+(pageNum*6)) > playlistNames.size())
                                {
                                    showMessage("Error: No game found.");
                                } else {
                                    System.out.println("Playing...");
                                    MainActivity.feed = searchGetFeed(playlistNames.get(0+(pageNum*6)));
                                    MainActivity.playbackMode = true;
                                    MainActivity.turnCount = 0;
                                    showMessage("Tap on Restart Game to replay game.");
                                    //playbackRecord();
                                    finish();
                                }
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
                                if((2+(pageNum*6)) > playlistNames.size())
                                {
                                    showMessage("Error: No game found.");
                                } else {
                                    System.out.println("Playing...");
                                    MainActivity.feed = searchGetFeed(playlistNames.get(1+(pageNum*6)));
                                    MainActivity.playbackMode = true;
                                    MainActivity.turnCount = 0;
                                    showMessage("Tap on Restart Game to replay game.");
                                    //playbackRecord();
                                    finish();
                                }
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
                                if((3+(pageNum*6)) > playlistNames.size())
                                {
                                    showMessage("Error: No game found.");
                                } else {
                                    System.out.println("Playing...");
                                    MainActivity.feed = searchGetFeed(playlistNames.get(2+(pageNum*6)));
                                    MainActivity.playbackMode = true;
                                    MainActivity.turnCount = 0;
                                    showMessage("Tap on Restart Game to replay game.");
                                    //playbackRecord();
                                    finish();
                                }
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
                                if((4+(pageNum*6)) > playlistNames.size())
                                {
                                    showMessage("Error: No game found.");
                                } else {
                                    System.out.println("Playing...");
                                    MainActivity.feed = searchGetFeed(playlistNames.get(3+(pageNum*6)));
                                    MainActivity.playbackMode = true;
                                    MainActivity.turnCount = 0;
                                    showMessage("Tap on Restart Game to replay game.");
                                    //playbackRecord();
                                    finish();
                                }
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
                                if((5+(pageNum*6)) > playlistNames.size())
                                {
                                    showMessage("Error: No game found.");
                                } else {
                                    System.out.println("Playing...");
                                    MainActivity.feed = searchGetFeed(playlistNames.get(4+(pageNum*6)));
                                    MainActivity.playbackMode = true;
                                    MainActivity.turnCount = 0;
                                    showMessage("Tap on Restart Game to replay game.");
                                    //playbackRecord();
                                    finish();
                                }
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
                                if((6+(pageNum*6)) > playlistNames.size())
                                {
                                    showMessage("Error: No game found.");
                                } else {
                                    System.out.println("Playing...");
                                    MainActivity.feed = searchGetFeed(playlistNames.get(5+(pageNum*6)));
                                    MainActivity.playbackMode = true;
                                    MainActivity.turnCount = 0;
                                    showMessage("Tap on Restart Game to replay game.");
                                    //playbackRecord();
                                    finish();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        final EditText pageText = (EditText)findViewById(R.id.pageInput);
        pageText.setText(Integer.toString(pageNum+1));

        Button nextButton = (Button)findViewById(R.id.next_page);
        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int tempS = playlist.size() / 6;
                int remain = playlist.size() % 6;
                int add = 0;
                if(remain != 0)
                {
                    add = 1;
                }
                if((pageNum+1) > (playlist.size() / 6) + add)
                {

                } else {
                    pageNum++;
                    pageText.setText(Integer.toString(pageNum+1));
                    loadEverything();
                }
            }
        });

        Button prevButton = (Button)findViewById(R.id.prev_page);
        prevButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if((pageNum+1) < 1)
                {

                } else {
                    pageNum--;
                    pageText.setText(Integer.toString(pageNum+1));
                    loadEverything();
                }
            }
        });

        loadAllGames();
        sortByName();
    }
}

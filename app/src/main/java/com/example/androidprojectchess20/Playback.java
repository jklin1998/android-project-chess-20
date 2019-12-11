package com.example.androidprojectchess20;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Playback implements Serializable {
    private String name;
    private long dateTime;
    private String entirePlayback;

    public Playback()
    {

    }

    public Playback(String name, long dateTime, String entirePlayback)
    {
        this.name = name;
        this.dateTime = dateTime;
        this.entirePlayback = entirePlayback;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getDateTime()
    {
        return this.dateTime;
    }

    public void setDateTime(long dateTime)
    {
        this.dateTime = dateTime;
    }

    public String getEntirePlayback()
    {
        return this.entirePlayback;
    }

    public void setEntirePlayback(String entirePlayback)
    {
        this.entirePlayback = entirePlayback;
    }

    public String toString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return this.name + ": " + (String)sdf.format(this.dateTime);
    }
}

package com.poly.dotsave.model;

import android.content.Context;

public class History {
    public String link, time;
    public int id;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    public String title, thumb;



    public History(int id,String link, String title, String thumb, String time) {
        this.link = link;
        this.time = time;
        this.id = id;
        this.title = title;
        this.thumb = thumb;
    }

    public History(String link, String time, String title, String thumb) {
        this.link = link;
        this.time = time;
        this.title = title;
        this.thumb = thumb;
    }

    public String getTime() {
        long now = System.currentTimeMillis();
        // TODO: localize
        final long diff = now - Long.parseLong(time);
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }
}

package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Routine {
    private int id;
    private String type;
    private String name;
    private String note;
    private Date feed_timing;

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setFeed_timing(Date feed_timing) {
        this.feed_timing = feed_timing;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public Date getFeed_timing() {
        return feed_timing;
    }

    public Routine(int id, String type, String name, String note, Date feed_timing) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.note = note;
        this.feed_timing = feed_timing;
    }
}

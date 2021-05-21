package com.LibSys.OctSky.backend.model;

public class Reason {
    int id;
    String reason;

    public Reason(int id, String reason)
    {
        this.id = id;
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

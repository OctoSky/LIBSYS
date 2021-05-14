package com.LibSys.OctSky.backend.model;

public class Ebook {
    String option;

    public Ebook(String option)
    {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
    @Override
    public String toString()
    {
        return option;
    }
    public void setOption(String option) {
        this.option = option;
    }
}

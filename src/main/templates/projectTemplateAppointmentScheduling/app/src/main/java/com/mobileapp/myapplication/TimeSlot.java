package com.mobileapp.myapplication;

public class TimeSlot {
    private String time;
    private boolean isOccupied;
    private boolean selected;

    public TimeSlot(String time, boolean isOccupied) {
        this.time = time;
        this.isOccupied = isOccupied;
        this.selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public boolean isOccupied() {
        return isOccupied;
    }
}

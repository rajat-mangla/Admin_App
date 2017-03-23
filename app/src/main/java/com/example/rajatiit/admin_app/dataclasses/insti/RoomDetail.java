package com.example.rajatiit.admin_app.dataclasses.insti;

import java.io.Serializable;

/**
 * Created by rajat on 25/2/17.
 */

public class RoomDetail implements Serializable{
    private int roomNo;
    private int capacity;
    private boolean isProjector;

    public RoomDetail() {
        roomNo = -1;
    }

    public boolean getProjector() {
        return isProjector;
    }

    public void setProjector(boolean projector) {
        isProjector = projector;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }
}

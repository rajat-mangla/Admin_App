package com.example.rajatiit.admin_app.TimeTableDisplay;

import com.example.rajatiit.admin_app.dataclasses.Classroom;

import java.util.ArrayList;

/**
 * Created by rajat on 28/2/17.
 */

// it will be displayed in gridview
public class SlotDetails {
    private ArrayList<Classroom> classrooms;
    private String name;

    public SlotDetails() {
    }

    public SlotDetails(String name) {
        this.name = name;
        classrooms = new ArrayList<>();
    }

    public ArrayList<Classroom> getClassrooms() {
        return classrooms;
    }

    public Classroom getClassroomDetail(Integer index){
        return classrooms.get(index);
    }

    public void addClassroom(Classroom classroom){
        classrooms.add(classroom);
    }

    public int getTotalClassrooms() {
        return classrooms.size();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
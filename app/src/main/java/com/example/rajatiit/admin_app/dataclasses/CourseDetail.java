package com.example.rajatiit.admin_app.dataclasses;

/**
 * Created by rajat on 22/2/17.
 */

public class CourseDetail {
    private String name;
    private String id;
    private String department;
    private Integer numLectures;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getNumLectures() {
        return numLectures;
    }

    public void setNumLectures(Integer numLectures) {
        this.numLectures = numLectures;
    }
}
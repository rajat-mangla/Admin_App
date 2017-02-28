package com.example.rajatiit.admin_app.dataclasses;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rajat on 22/2/17.
 */

public class Classroom implements Serializable{

    // Id to store in teacher and batch
    private int classroomId;

    // Stores the course data
    private CourseDetail courseDetail;

    // Used to get the Batch in array of batches ...
    private int batchId;

    // Used to get the Particular by the array index ...
    private int teacherId;

    // Used to get roomDetail
    private int roomId;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Classroom() {
        courseDetail = new CourseDetail();
        batchId = -1;
        teacherId = -1;
        roomId = -1;
    }

    public void generateCourseId(){
        String courseId = "";
        switch (courseDetail.getDepartmentName()){
            case "CSE":
                courseId = "CS";
                break;
            case "ME":
                courseId = "ME";
                break;
            case "EE":
                courseId = "EE";
                break;
        }

        // added using which year batch
        ArrayList<String> years = Institute.getBatchYears();

        if (years.get(0).equals(UserStorage.getBatchDetail(batchId).getYear())){
            courseId += Integer.toString(1);
        }
        else if (years.get(1).equals(UserStorage.getBatchDetail(batchId).getYear())){
            courseId += Integer.toString(2);
        }
        else if (years.get(2).equals(UserStorage.getBatchDetail(batchId).getYear())){
            courseId += Integer.toString(3);
        }
        else if (years.get(3).equals(UserStorage.getBatchDetail(batchId).getYear())){
            courseId += Integer.toString(4);
        }

        // additional for semester
        courseId += Integer.toString(2);
        courseId += Integer.toString(UserStorage.getBatchDetail(batchId).totalNoOfClassrooms());

        courseDetail.setId(courseId);
    }
    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public CourseDetail getCourseDetail() {
        return courseDetail;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}

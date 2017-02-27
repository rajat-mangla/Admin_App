package com.example.rajatiit.admin_app.dataclasses;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;

import java.io.Serializable;

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

    public Classroom() {
        courseDetail = new CourseDetail();
        batchId = -1;
        teacherId = -1;
    }

    public void generateCourseId(){
        String courseId;
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
        switch (UserStorage.getBatchDetail(batchId).getYear()){
            case Integer.toString(R.string.First_Year):
                courseId += Integer.toString(1);
                break;
            case Integer.toString(R.string.First_Year):
                courseId += Integer.toString(2);

        }
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

    public void setCourseDetail(CourseDetail courseDetail) {
        this.courseDetail = courseDetail;
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

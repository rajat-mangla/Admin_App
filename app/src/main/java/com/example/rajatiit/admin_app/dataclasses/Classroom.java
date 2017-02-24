package com.example.rajatiit.admin_app.dataclasses;

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

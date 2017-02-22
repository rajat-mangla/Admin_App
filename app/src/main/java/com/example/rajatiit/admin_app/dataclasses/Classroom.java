package com.example.rajatiit.admin_app.dataclasses;

/**
 * Created by rajat on 22/2/17.
 */

public class Classroom {
    private CourseDetail courseDetail;

    // Used to get the Batch in array of batches ...
    private int batchId;

    // Used to get the Particular by the array index ...
    private int teacherId;

    public Classroom() {

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

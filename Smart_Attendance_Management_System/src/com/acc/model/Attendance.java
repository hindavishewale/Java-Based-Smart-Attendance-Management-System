package com.acc.model;

public class Attendance {

    private int attendanceId;
    private int studentId;
    private String status;

    public Attendance(int attendanceId, int studentId, String status) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.status = status;
    }

    public Attendance(int studentId, String status) {
        this.studentId = studentId;
        this.status = status;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStatus() {
        return status;
    }
}

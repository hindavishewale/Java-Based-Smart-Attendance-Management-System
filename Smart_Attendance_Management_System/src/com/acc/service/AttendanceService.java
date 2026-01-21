package com.acc.service;

import java.util.List;
import com.acc.model.Attendance;

public interface AttendanceService {

    void markAttendance(Attendance attendance);
    List<Attendance> viewAttendance();
    void updateAttendance(int id, String status);
    void deleteAttendance(int id);
}

package com.acc.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.acc.model.Attendance;
import com.acc.service.AttendanceService;
import com.acc.util.DBConnection;

public class AttendanceServiceImpl implements AttendanceService {

    // CREATE
    public void markAttendance(Attendance att) {
        String sql = "INSERT INTO attendance (student_id, status) VALUES (?, ?)";

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, att.getStudentId());
            ps.setString(2, att.getStatus());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ
    public List<Attendance> viewAttendance() {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT attendance_id, student_id, status FROM attendance";

        try (Connection con = DBConnection.getDBConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Attendance(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public void updateAttendance(int id, String status) {
        String sql = "UPDATE attendance SET status=? WHERE attendance_id=?";

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteAttendance(int id) {
        String sql = "DELETE FROM attendance WHERE attendance_id=?";

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Lectures attended by ONE student
    public int getAttendedLecturesByStudent(int studentId) {

        String sql = "SELECT COUNT(*) FROM attendance WHERE student_id=? AND status='Present'";

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    	//ALL students with lecture count
    public List<Object[]> getAllStudentsLectureCount() {

        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT s.id, s.name,
                   COUNT(a.attendance_id) AS total,
                   SUM(a.status='Present') AS attended
            FROM students s
            LEFT JOIN attendance a ON s.id = a.student_id
            GROUP BY s.id, s.name
        """;

        try (Connection con = DBConnection.getDBConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("total"),
                        rs.getInt("attended")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    
}

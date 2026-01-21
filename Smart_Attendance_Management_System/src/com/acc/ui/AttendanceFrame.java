package com.acc.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import com.acc.dao.AttendanceServiceImpl;
import com.acc.model.Attendance;

public class AttendanceFrame extends JFrame {

    JTextField txtStudentId;
    JComboBox<String> status;
    JTable table;
    DefaultTableModel model;

    AttendanceServiceImpl service = new AttendanceServiceImpl();

    public AttendanceFrame() {

        setTitle("Smart Attendance Management System");
        setSize(750, 600);
        setLayout(null);

        JLabel l1 = new JLabel("Student ID:");
        JLabel l2 = new JLabel("Status:");

        l1.setBounds(30, 20, 100, 25);
        l2.setBounds(30, 60, 100, 25);

        txtStudentId = new JTextField();
        status = new JComboBox<>(new String[]{"Present", "Absent"});

        txtStudentId.setBounds(140, 20, 200, 25);
        status.setBounds(140, 60, 200, 25);

        JButton btnMark = new JButton("Mark");
        JButton btnView = new JButton("View");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnCount = new JButton("Count Lectures");
        JButton btnAll = new JButton("All Students Report");

        btnMark.setBounds(380, 20, 120, 30);
        btnView.setBounds(380, 60, 120, 30);
        btnUpdate.setBounds(380, 100, 120, 30);
        btnDelete.setBounds(380, 140, 120, 30);
        btnCount.setBounds(520, 20, 180, 30);
        btnAll.setBounds(520, 60, 180, 30);

        model = new DefaultTableModel(
                new String[]{"Attendance ID", "Student ID", "Status"}, 0
        );

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 200, 700, 330);

        add(l1); add(txtStudentId);
        add(l2); add(status);
        add(btnMark); add(btnView);
        add(btnUpdate); add(btnDelete);
        add(btnCount); add(btnAll);
        add(sp);

        // MARK
        btnMark.addActionListener(e -> {
            if (txtStudentId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Student ID");
                return;
            }
            Attendance att = new Attendance(
                    Integer.parseInt(txtStudentId.getText()),
                    status.getSelectedItem().toString()
            );
            service.markAttendance(att);
            loadTable();
            JOptionPane.showMessageDialog(this, "Attendance Marked");
        });

        // VIEW
        btnView.addActionListener(e -> loadTable());

        // UPDATE
        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            int id = (int) model.getValueAt(row, 0);
            service.updateAttendance(id, status.getSelectedItem().toString());
            loadTable();
        });

        // DELETE
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            int id = (int) model.getValueAt(row, 0);
            service.deleteAttendance(id);
            loadTable();
        });

        // COUNT LECTURES (ONE STUDENT)
        btnCount.addActionListener(e -> {

            if (txtStudentId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Student ID");
                return;
            }

            int sid = Integer.parseInt(txtStudentId.getText());
            int attended = service.getAttendedLecturesByStudent(sid);

            JOptionPane.showMessageDialog(this,
                    "Student ID: " + sid +
                    "\nLectures Attended: " + attended);
        });

        // ALL STUDENTS REPORT
        btnAll.addActionListener(e -> {

            model.setRowCount(0);
            model.setColumnIdentifiers(
                    new String[]{"Student ID", "Name", "Total Lectures", "Attended"}
            );

            List<Object[]> list = service.getAllStudentsLectureCount();
            for (Object[] row : list) {
                model.addRow(row);
            }
        });

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void loadTable() {
        model.setRowCount(0);
        List<Attendance> list = service.viewAttendance();
        for (Attendance a : list) {
            model.addRow(new Object[]{
                    a.getAttendanceId(),
                    a.getStudentId(),
                    a.getStatus()
            });
        }
    }
}

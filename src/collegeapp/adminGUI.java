/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Maksym Vavilov 16856
 */
public class adminGUI extends adminDatabase {

    /**
     * Creates new form adminGUI
     */
    private String selectStudentQuery = "SELECT student.Sid AS 'Student ID',\n" +
                                        "	student.Fname AS 'First Name',\n" +
                                        "        student.Lname AS 'Last Name',\n" +
                                        "        student.Course,\n" +
                                        "        student.Phone,\n" +
                                        "        student.Address\n" +
                                        "FROM student;";
    
    private String selectAttendanceQuery = "SELECT attendance.Sid AS 'Student ID',\n" +
                                            "		attendance.Lessons,\n" +
                                            "		attendance.Attended\n" +
                                            "FROM attendance;";
    
    private String findStudentQuery = "SELECT student.Sid AS 'Student ID',\n" +
                                        "		student.Fname AS 'First Name',\n" +
                                        "        student.Lname AS 'Last Name',\n" +
                                        "        student.Course,\n" +
                                        "        student.Phone,\n" +
                                        "        student.Address\n" +
                                        "FROM student\n" +
                                        "WHERE student.Sid = ?;";
    
    private String deleteStudentQuery = "DELETE FROM `programming_db`.`student` WHERE `Sid`=?;";
    
    private String createStudentQuery = "INSERT INTO `programming_db`.`student` (`Sid`, `Fname`, `Lname`, `Course`, `Phone`, `Address`) "
                                            + "VALUES (?, ?, ?, ?, ?, ?);";
    
    private String modifyStudentQuery = "UPDATE `programming_db`.`student` SET "
                                        + "`Fname`=?, `Lname`=?,"
                                        + " `Course`=?, `Phone`=?, "
                                        + "`Address`=?"
                                        + " WHERE `Sid`=?;";
    
    private String findAttendance = "SELECT attendance.Sid AS 'Student ID',\n" +
                                        "		attendance.Lessons,\n" +
                                        "		attendance.Attended\n" +
                                        "FROM attendance\n" +
                                        "WHERE attendance.Sid = ?;";
    
    private String createAttendance = "INSERT INTO `programming_db`.`attendance` (`Sid`, `Lessons`, `Attended`) "
                                        + "VALUES (?, ?, ?);";
    
    private String deleteAttendance = "DELETE FROM `programming_db`.`attendance` WHERE `Sid`=?;";
    
    private String modifyAttendance = "UPDATE `programming_db`.`attendance` "
                                        + "SET `Lessons`=?, `Attended`=? "
                                        + "WHERE `Sid`=?;";
    
    private String selectGroupDetails = "SELECT group_details.Gid AS 'Group ID',\n" +
                                        "		group_details.Cid AS 'Course ID',\n" +
                                        "        group_details.Superviser AS 'Superviser ID',\n" +
                                        "        faculty_members.Fname AS 'Superviser Name',\n" +
                                        "        faculty_members.Lname AS 'Superviser Last Name'\n" +
                                        "FROM (group_details\n" +
                                        "	INNER JOIN faculty_members ON group_details.Superviser = faculty_members.Fid);";
    
    private String selectGroups = "SELECT groups.Gid AS 'Group ID',\n" +
                                "		groups.Sid AS 'Student ID',\n" +
                                "        student.Fname AS 'Student First Name',\n" +
                                "        student.Lname AS 'Student Last Name'\n" +
                                "FROM (groups\n" +
                                "	INNER JOIN student ON groups.Sid = student.Sid);";
    
    private String createGroupDetails = "INSERT INTO `programming_db`.`group_details` (`Gid`, `Cid`, `Superviser`) VALUES (?, ?, ?);";
    
    private String deleteGroupDetails = "DELETE FROM `programming_db`.`group_details` WHERE `Gid`=? and`Cid`=?;";
    
    private String modifyGroupDetails = "UPDATE `programming_db`.`group_details` SET `Superviser`=? WHERE `Gid`=? and`Cid`=?;";
    
    private String createGroups = "INSERT INTO `programming_db`.`groups` (`Sid`, `Gid`) VALUES (?, ?);";
    
    private String deleteGroups = "DELETE FROM `programming_db`.`groups` WHERE `Sid`=? and`Gid`=?;";
    
    private String selectTimetable = "SELECT timetable.Gid AS 'Group ID',\n" +
                                    "		timetable.Day,\n" +
                                    "        timetable.Subject,\n" +
                                    "        timetable.Start,\n" +
                                    "        timetable.End\n" +
                                    "FROM timetable;";
    
    private String timetableFill = "SELECT group_details.Gid\n" +
                                    "FROM group_details;";
    
    private String timetableFill2 = "SELECT subjects.Subject\n" +
                                    "FROM subjects;";
    
    private String timetableCreate = "INSERT INTO `programming_db`.`timetable` "
                                + "(`Gid`, `Day`, `Subject`, `Start`, `End`) VALUES "
                                + "(?, ?, ?, ?, ?);";
    
    private String timetableModify = "UPDATE `programming_db`.`timetable` SET "
                                + "`Subject`=?, `Start`=?, `End`=? WHERE "
                                + "`Gid`=? and`Day`=?;";
    
    private String timetableDelete = "DELETE FROM `programming_db`.`timetable` WHERE "
                                + "`Gid`=? and`Day`=?;";

    public adminGUI() {
        initComponents();
        readConnectionDetails();
        
        updateStudentStudentTable(selectQuery(selectStudentQuery));
        updateStudentAttendanceTable(selectQuery(selectAttendanceQuery));
        updateGroupGroupDetailsTabel(selectQuery(selectGroupDetails));
        updateGroupGroupsTabel(selectQuery(selectGroups));
        updateTimetableTabel(selectQuery(selectTimetable));
        timetableFill();
    }
    
    
    
    private void updateStudentStudentTable(ResultSet rs){
        studentStudentTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    private void updateStudentAttendanceTable(ResultSet rs){
        studentAttendanceTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    private void updateDirectTable (ResultSet rs){
        directTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    private void updateGroupGroupDetailsTabel (ResultSet rs){
        groupGroupDetailsTabel.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    private void updateGroupGroupsTabel (ResultSet rs){
        groupGroupsTabel.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    private void updateTimetableTabel (ResultSet rs){
        timetableTabel.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    private void createTimetable(){
        ResultSet resultSet = null;
        String startTime = timetableStartHoursCombo.getSelectedItem().toString()+":"+
                timetableStartMinCombo.getSelectedItem().toString()+":00";
        String endTime = timetableEndHoursCombo.getSelectedItem().toString()+":"+
                timetableEndMinCombo.getSelectedItem().toString()+":00";;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(timetableCreate);
            PrepStatement.setString(1, timetableGroupCombo.getSelectedItem().toString());
            PrepStatement.setString(2, timetableDayCombo.getSelectedItem().toString());
            PrepStatement.setString(3, timetableSubjectCombo.getSelectedItem().toString());
            PrepStatement.setString(4, startTime);
            PrepStatement.setString(5, endTime);
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modifyTimetable(){
        ResultSet resultSet = null;
        String startTime = timetableStartHoursCombo.getSelectedItem().toString()+":"+
                timetableStartMinCombo.getSelectedItem().toString()+":00";
        String endTime = timetableEndHoursCombo.getSelectedItem().toString()+":"+
                timetableEndMinCombo.getSelectedItem().toString()+":00";;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(timetableModify);
            PrepStatement.setString(4, timetableGroupCombo.getSelectedItem().toString());
            PrepStatement.setString(5, timetableDayCombo.getSelectedItem().toString());
            PrepStatement.setString(1, timetableSubjectCombo.getSelectedItem().toString());
            PrepStatement.setString(2, startTime);
            PrepStatement.setString(3, endTime);
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteTimetable(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(timetableDelete);
            PrepStatement.setString(1, timetableGroupCombo.getSelectedItem().toString());
            PrepStatement.setString(2, timetableDayCombo.getSelectedItem().toString());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void timetableFill() {
        try {
            ResultSet rs = selectQuery(timetableFill);
            
            while(rs.next())
                timetableGroupCombo.addItem(rs.getString(1));
            
            rs = selectQuery(timetableFill2);
            
            while(rs.next())
                timetableSubjectCombo.addItem(rs.getString(1));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    
    
    private void createGroupDetails (){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(createGroupDetails);
            PrepStatement.setString(1, groupField1.getText());
            PrepStatement.setString(2, groupField2.getText());
            PrepStatement.setString(3, groupField3.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteGroupDetails(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(deleteGroupDetails);
            PrepStatement.setString(1, groupField1.getText());
            PrepStatement.setString(2, groupField2.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }    
    }
    
    private void modifyGroupDetais(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(modifyGroupDetails);
            PrepStatement.setString(2, groupField1.getText());
            PrepStatement.setString(3, groupField2.getText());
            PrepStatement.setString(1, groupField3.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }    
    }
    
    private void createGroups(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(createGroups);
            PrepStatement.setString(1, groupField5.getText());
            PrepStatement.setString(2, groupField4.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }    
    }
    
    private void deleteGroups(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(deleteGroups);
            PrepStatement.setString(1, groupField5.getText());
            PrepStatement.setString(2, groupField4.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }     
    }
    
    private ResultSet directExecute (String query){
        ResultSet resultSet = null;
        directQuery = query;
        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(query);
            
            resultSet = PrepStatement.executeQuery();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return resultSet;
    }
    
    private void directExecuteUpdate(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(directTextArea.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modifyAttendance(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(modifyAttendance);
            
            PrepStatement.setString(3,sidField2.getText());
            PrepStatement.setString(1,lessonsField.getText());
            PrepStatement.setString(2,lessonsField2.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteAttendance(String sid){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(deleteAttendance);
            
            PrepStatement.setString(1,sid);;
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createAttendance (){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(createAttendance);
            
            PrepStatement.setString(1,sidField2.getText());
            PrepStatement.setString(2,lessonsField.getText());
            PrepStatement.setString(3,lessonsField2.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void findAttendance (String sid){
        ResultSet resultSet = null;
        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(findAttendance);
            PrepStatement.setString(1,sid);
            
            resultSet = PrepStatement.executeQuery();
            
            while(resultSet.next()){
                sidField2.setText(resultSet.getString(1));
                lessonsField.setText(resultSet.getString(2));
                lessonsField2.setText(resultSet.getString(3));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modifyStudent(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(modifyStudentQuery);
            
            PrepStatement.setString(6,sidField1.getText());
            PrepStatement.setString(1,FnameField.getText());
            PrepStatement.setString(2,LnameField.getText());
            PrepStatement.setString(3,courseField.getText());
            PrepStatement.setString(4,phoneField.getText());
            PrepStatement.setString(5,addressArea.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createStudent (){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(createStudentQuery);
            
            PrepStatement.setString(1,sidField1.getText());
            PrepStatement.setString(2,FnameField.getText());
            PrepStatement.setString(3,LnameField.getText());
            PrepStatement.setString(4,courseField.getText());
            PrepStatement.setString(5,phoneField.getText());
            PrepStatement.setString(6,addressArea.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteStudent(String sid){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(deleteStudentQuery);
            
            PrepStatement.setString(1,sid);;
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void findStudent (String sid){
        ResultSet resultSet = null;
        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(findStudentQuery);
            PrepStatement.setString(1,sid);
            
            resultSet = PrepStatement.executeQuery();
            
            while(resultSet.next()){
                sidField1.setText(resultSet.getString(1));
                FnameField.setText(resultSet.getString(2));
                LnameField.setText(resultSet.getString(3));
                courseField.setText(resultSet.getString(4));
                phoneField.setText(resultSet.getString(5));
                addressArea.setText(resultSet.getString(6));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
        private ResultSet selectQuery(String query){
        ResultSet resultSet = null;
        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(query);
            
            resultSet = PrepStatement.executeQuery();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        
        return resultSet;
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        studentPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        studentStudentTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        studentAttendanceTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        sidField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        addressArea = new javax.swing.JTextArea();
        phoneField = new javax.swing.JTextField();
        courseField = new javax.swing.JTextField();
        LnameField = new javax.swing.JTextField();
        FnameField = new javax.swing.JTextField();
        applyButton1 = new javax.swing.JButton();
        findButton1 = new javax.swing.JButton();
        createButton1 = new javax.swing.JButton();
        deleteButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        sidField2 = new javax.swing.JTextField();
        findButton2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lessonsField2 = new javax.swing.JTextField();
        lessonsField = new javax.swing.JTextField();
        applyButton2 = new javax.swing.JButton();
        deleteButton2 = new javax.swing.JButton();
        createButton2 = new javax.swing.JButton();
        groupsPanel = new javax.swing.JPanel();
        groupLabel1 = new javax.swing.JLabel();
        groupLabel2 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        groupGroupDetailsTabel = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        groupGroupsTabel = new javax.swing.JTable();
        groupLabel3 = new javax.swing.JLabel();
        groupLabel4 = new javax.swing.JLabel();
        groupLabel5 = new javax.swing.JLabel();
        groupField1 = new javax.swing.JTextField();
        groupField2 = new javax.swing.JTextField();
        groupField3 = new javax.swing.JTextField();
        groupButton = new javax.swing.JButton();
        groupButton3 = new javax.swing.JButton();
        groupButton2 = new javax.swing.JButton();
        groupLabel6 = new javax.swing.JLabel();
        groupLabel7 = new javax.swing.JLabel();
        groupField4 = new javax.swing.JTextField();
        groupField5 = new javax.swing.JTextField();
        groupButton4 = new javax.swing.JButton();
        groupButton5 = new javax.swing.JButton();
        timetablePanel = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        timetableTabel = new javax.swing.JTable();
        timetableLabel = new javax.swing.JLabel();
        timetableLabel2 = new javax.swing.JLabel();
        timetableLabel3 = new javax.swing.JLabel();
        timetableLabel4 = new javax.swing.JLabel();
        timetableLabel5 = new javax.swing.JLabel();
        timetableStartHoursCombo = new javax.swing.JComboBox<>();
        timetableLabel6 = new javax.swing.JLabel();
        timetableLabel7 = new javax.swing.JLabel();
        timetableStartMinCombo = new javax.swing.JComboBox<>();
        timetableLabel8 = new javax.swing.JLabel();
        timetableLabel9 = new javax.swing.JLabel();
        timetableEndHoursCombo = new javax.swing.JComboBox<>();
        timetableEndMinCombo = new javax.swing.JComboBox<>();
        timetableGroupCombo = new javax.swing.JComboBox<>();
        timetableDayCombo = new javax.swing.JComboBox<>();
        timetableSubjectCombo = new javax.swing.JComboBox<>();
        timetableCreateButton = new javax.swing.JButton();
        TimetableModifyButton = new javax.swing.JButton();
        timetableDeleteButton = new javax.swing.JButton();
        coursesPanel = new javax.swing.JPanel();
        Payments = new javax.swing.JPanel();
        branchesPanel = new javax.swing.JPanel();
        gradesPanel = new javax.swing.JPanel();
        assignmentPanel = new javax.swing.JPanel();
        facultyPanel = new javax.swing.JPanel();
        loginPanel = new javax.swing.JPanel();
        DirectDBaccess = new javax.swing.JPanel();
        directOutputLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        directTable = new javax.swing.JTable();
        directInputLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        directTextArea = new javax.swing.JTextArea();
        directExecuteButton = new javax.swing.JButton();
        directExecuteUpdateButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Admin");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Students");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Attendance");

        studentStudentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(studentStudentTable);

        studentAttendanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(studentAttendanceTable);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Student ID");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("First Name");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Last Name");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Course");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Phone");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Address");

        addressArea.setColumns(20);
        addressArea.setLineWrap(true);
        addressArea.setRows(5);
        jScrollPane3.setViewportView(addressArea);

        applyButton1.setText("Apply Changes");
        applyButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButton1ActionPerformed(evt);
            }
        });

        findButton1.setText("Find by ID");
        findButton1.setToolTipText("");
        findButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findButton1ActionPerformed(evt);
            }
        });

        createButton1.setText("Create");
        createButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButton1ActionPerformed(evt);
            }
        });

        deleteButton1.setText("Delete");
        deleteButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButton1ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Student ID");

        findButton2.setText("Find by ID");
        findButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findButton2ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Lessons");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Lessons Attended");

        applyButton2.setText("Apply");
        applyButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButton2ActionPerformed(evt);
            }
        });

        deleteButton2.setText("Delete");
        deleteButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButton2ActionPerformed(evt);
            }
        });

        createButton2.setText("Create");
        createButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout studentPanelLayout = new javax.swing.GroupLayout(studentPanel);
        studentPanel.setLayout(studentPanelLayout);
        studentPanelLayout.setHorizontalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addGap(258, 258, 258)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(173, 173, 173))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentPanelLayout.createSequentialGroup()
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel3)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel5)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel6)
                        .addGap(62, 62, 62)
                        .addComponent(jLabel7)
                        .addGap(94, 94, 94)
                        .addComponent(jLabel8)
                        .addGap(165, 165, 165)
                        .addComponent(jLabel9)
                        .addGap(109, 109, 109)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addComponent(jLabel11))
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(studentPanelLayout.createSequentialGroup()
                                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(deleteButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                                    .addComponent(sidField1))
                                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(studentPanelLayout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(FnameField)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(courseField)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentPanelLayout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(createButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(findButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(applyButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(studentPanelLayout.createSequentialGroup()
                                .addComponent(sidField2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lessonsField, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(lessonsField2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(studentPanelLayout.createSequentialGroup()
                                .addComponent(findButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(createButton2)
                                .addGap(26, 26, 26)
                                .addComponent(deleteButton2)
                                .addGap(18, 18, 18)
                                .addComponent(applyButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(56, 56, 56))
        );
        studentPanelLayout.setVerticalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel2))
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addGap(20, 20, 20)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sidField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lessonsField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lessonsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(findButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(applyButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(createButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, studentPanelLayout.createSequentialGroup()
                        .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sidField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(courseField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(applyButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(findButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(createButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(189, 189, 189))
        );

        jTabbedPane1.addTab("Student", studentPanel);

        groupLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        groupLabel1.setText("Groups");

        groupLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        groupLabel2.setText("Students");

        groupGroupDetailsTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(groupGroupDetailsTabel);

        groupGroupsTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(groupGroupsTabel);

        groupLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        groupLabel3.setText("Group ID");

        groupLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        groupLabel4.setText("Course ID");

        groupLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        groupLabel5.setText("Superviser ID");

        groupButton.setText("Create");
        groupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupButtonActionPerformed(evt);
            }
        });

        groupButton3.setText("Modify Superviser");
        groupButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupButton3ActionPerformed(evt);
            }
        });

        groupButton2.setText("Delete");
        groupButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupButton2ActionPerformed(evt);
            }
        });

        groupLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        groupLabel6.setText("Group ID");

        groupLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        groupLabel7.setText("Student ID");

        groupButton4.setText("Create Record");
        groupButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupButton4ActionPerformed(evt);
            }
        });

        groupButton5.setText("Delete Record");
        groupButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout groupsPanelLayout = new javax.swing.GroupLayout(groupsPanel);
        groupsPanel.setLayout(groupsPanelLayout);
        groupsPanelLayout.setHorizontalGroup(
            groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groupsPanelLayout.createSequentialGroup()
                .addGap(307, 307, 307)
                .addComponent(groupLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(groupLabel2)
                .addGap(239, 239, 239))
            .addGroup(groupsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(groupsPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(groupsPanelLayout.createSequentialGroup()
                        .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(groupButton, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(groupField1))
                        .addGap(106, 106, 106)
                        .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(groupButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(groupField2))
                        .addGap(98, 98, 98)
                        .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(groupField3)
                            .addComponent(groupButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                        .addGap(165, 165, 165)
                        .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(groupField4, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(groupButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(groupsPanelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(groupLabel3)
                        .addGap(170, 170, 170)
                        .addComponent(groupLabel4)
                        .addGap(167, 167, 167)
                        .addComponent(groupLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(groupLabel6)
                        .addGap(128, 128, 128)))
                .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(groupField5)
                        .addComponent(groupButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                    .addGroup(groupsPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(groupLabel7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        groupsPanelLayout.setVerticalGroup(
            groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groupsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupLabel1)
                    .addComponent(groupLabel2))
                .addGap(27, 27, 27)
                .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(17, 17, 17)
                .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupLabel4)
                    .addComponent(groupLabel5)
                    .addComponent(groupLabel3)
                    .addComponent(groupLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                    .addComponent(groupButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(groupButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(groupButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(groupButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Groups", groupsPanel);

        timetableTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(timetableTabel);

        timetableLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timetableLabel.setText("Group ID");

        timetableLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timetableLabel2.setText("Day");

        timetableLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timetableLabel3.setText("Subject");

        timetableLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timetableLabel4.setText("Start Time");

        timetableLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timetableLabel5.setText("End Time");

        timetableStartHoursCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "09", "10", "11", "12", "13", "14", "15", "16", "17", "18" }));

        timetableLabel6.setText("Hours");

        timetableLabel7.setText("Minutes");

        timetableStartMinCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "10", "20", "30", "40", "50" }));

        timetableLabel8.setText("Hours");

        timetableLabel9.setText("Minutes");

        timetableEndHoursCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "09", "10", "11", "12", "13", "14", "15", "16", "17", "18" }));

        timetableEndMinCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "10", "20", "30", "40", "50" }));

        timetableDayCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MON", "TUE", "WED", "THU", "FRI" }));

        timetableCreateButton.setText("Create");
        timetableCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableCreateButtonActionPerformed(evt);
            }
        });

        TimetableModifyButton.setText("Modify");
        TimetableModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimetableModifyButtonActionPerformed(evt);
            }
        });

        timetableDeleteButton.setText("Delete");
        timetableDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableDeleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout timetablePanelLayout = new javax.swing.GroupLayout(timetablePanel);
        timetablePanel.setLayout(timetablePanelLayout);
        timetablePanelLayout.setHorizontalGroup(
            timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, timetablePanelLayout.createSequentialGroup()
                .addGroup(timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timetablePanelLayout.createSequentialGroup()
                        .addGap(0, 29, Short.MAX_VALUE)
                        .addGroup(timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(timetablePanelLayout.createSequentialGroup()
                                .addComponent(timetableGroupCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(timetableDayCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(timetableSubjectCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(timetablePanelLayout.createSequentialGroup()
                                .addComponent(TimetableModifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(timetableDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(55, 55, 55))
                    .addGroup(timetablePanelLayout.createSequentialGroup()
                        .addGroup(timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(timetablePanelLayout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(timetableLabel4)
                                .addGap(104, 104, 104)
                                .addComponent(timetableLabel5))
                            .addGroup(timetablePanelLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(timetableLabel6)
                                    .addComponent(timetableStartHoursCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(timetableCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(timetableLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(timetableStartMinCombo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(61, 61, 61)
                                .addGroup(timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(timetablePanelLayout.createSequentialGroup()
                                        .addComponent(timetableEndHoursCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(timetableEndMinCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(timetablePanelLayout.createSequentialGroup()
                                        .addComponent(timetableLabel8)
                                        .addGap(36, 36, 36)
                                        .addComponent(timetableLabel9)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(timetablePanelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(timetableLabel)
                        .addGap(87, 87, 87)
                        .addComponent(timetableLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(timetableLabel3)
                        .addGap(87, 87, 87)))
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 787, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        timetablePanelLayout.setVerticalGroup(
            timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
            .addGroup(timetablePanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timetableLabel)
                    .addComponent(timetableLabel2)
                    .addComponent(timetableLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timetableGroupCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timetableDayCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timetableSubjectCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timetableLabel4)
                    .addComponent(timetableLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timetableLabel6)
                    .addComponent(timetableLabel7)
                    .addComponent(timetableLabel8)
                    .addComponent(timetableLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timetableEndHoursCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timetableEndMinCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timetableStartMinCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timetableStartHoursCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timetableCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TimetableModifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timetableDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Timetable", timetablePanel);

        javax.swing.GroupLayout coursesPanelLayout = new javax.swing.GroupLayout(coursesPanel);
        coursesPanel.setLayout(coursesPanelLayout);
        coursesPanelLayout.setHorizontalGroup(
            coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
        );
        coursesPanelLayout.setVerticalGroup(
            coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Courses", coursesPanel);

        javax.swing.GroupLayout PaymentsLayout = new javax.swing.GroupLayout(Payments);
        Payments.setLayout(PaymentsLayout);
        PaymentsLayout.setHorizontalGroup(
            PaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
        );
        PaymentsLayout.setVerticalGroup(
            PaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Payments", Payments);

        javax.swing.GroupLayout branchesPanelLayout = new javax.swing.GroupLayout(branchesPanel);
        branchesPanel.setLayout(branchesPanelLayout);
        branchesPanelLayout.setHorizontalGroup(
            branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
        );
        branchesPanelLayout.setVerticalGroup(
            branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Branches", branchesPanel);

        javax.swing.GroupLayout gradesPanelLayout = new javax.swing.GroupLayout(gradesPanel);
        gradesPanel.setLayout(gradesPanelLayout);
        gradesPanelLayout.setHorizontalGroup(
            gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
        );
        gradesPanelLayout.setVerticalGroup(
            gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Grades", gradesPanel);

        javax.swing.GroupLayout assignmentPanelLayout = new javax.swing.GroupLayout(assignmentPanel);
        assignmentPanel.setLayout(assignmentPanelLayout);
        assignmentPanelLayout.setHorizontalGroup(
            assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
        );
        assignmentPanelLayout.setVerticalGroup(
            assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Assignments", assignmentPanel);

        javax.swing.GroupLayout facultyPanelLayout = new javax.swing.GroupLayout(facultyPanel);
        facultyPanel.setLayout(facultyPanelLayout);
        facultyPanelLayout.setHorizontalGroup(
            facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
        );
        facultyPanelLayout.setVerticalGroup(
            facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Faculty", facultyPanel);

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Login", loginPanel);

        directOutputLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        directOutputLabel.setText("Output");

        directTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        directTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(directTable);

        directInputLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        directInputLabel.setText("Input");

        jScrollPane5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        directTextArea.setColumns(20);
        directTextArea.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        directTextArea.setRows(5);
        jScrollPane5.setViewportView(directTextArea);

        directExecuteButton.setText("Execute");
        directExecuteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directExecuteButtonActionPerformed(evt);
            }
        });

        directExecuteUpdateButton.setText("Execute Update");
        directExecuteUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directExecuteUpdateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DirectDBaccessLayout = new javax.swing.GroupLayout(DirectDBaccess);
        DirectDBaccess.setLayout(DirectDBaccessLayout);
        DirectDBaccessLayout.setHorizontalGroup(
            DirectDBaccessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DirectDBaccessLayout.createSequentialGroup()
                .addGroup(DirectDBaccessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DirectDBaccessLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(DirectDBaccessLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(directExecuteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addComponent(directExecuteUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DirectDBaccessLayout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(directInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(directOutputLabel)
                .addGap(351, 351, 351))
        );
        DirectDBaccessLayout.setVerticalGroup(
            DirectDBaccessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DirectDBaccessLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DirectDBaccessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(directOutputLabel)
                    .addComponent(directInputLabel))
                .addGap(18, 18, 18)
                .addGroup(DirectDBaccessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                    .addGroup(DirectDBaccessLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(DirectDBaccessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(directExecuteUpdateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(directExecuteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Direct DB Access", DirectDBaccess);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        logout();
    }//GEN-LAST:event_formWindowClosing

    private void createButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButton2ActionPerformed
        createAttendance();
        updateStudentAttendanceTable(selectQuery(selectAttendanceQuery));
    }//GEN-LAST:event_createButton2ActionPerformed

    private void deleteButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButton2ActionPerformed
        deleteAttendance(sidField2.getText());
        updateStudentAttendanceTable(selectQuery(selectAttendanceQuery));
    }//GEN-LAST:event_deleteButton2ActionPerformed

    private void applyButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButton2ActionPerformed
        modifyAttendance();
        updateStudentAttendanceTable(selectQuery(selectAttendanceQuery));
    }//GEN-LAST:event_applyButton2ActionPerformed

    private void findButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findButton2ActionPerformed
        findAttendance(sidField2.getText());
    }//GEN-LAST:event_findButton2ActionPerformed

    private void deleteButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButton1ActionPerformed
        deleteStudent(sidField1.getText());
        updateStudentStudentTable(selectQuery(selectStudentQuery));
    }//GEN-LAST:event_deleteButton1ActionPerformed

    private void createButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButton1ActionPerformed
        createStudent();
        updateStudentStudentTable(selectQuery(selectStudentQuery));
    }//GEN-LAST:event_createButton1ActionPerformed

    private void findButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findButton1ActionPerformed
        findStudent(sidField1.getText());
    }//GEN-LAST:event_findButton1ActionPerformed

    private void applyButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButton1ActionPerformed
        modifyStudent();
        updateStudentStudentTable(selectQuery(selectStudentQuery));
    }//GEN-LAST:event_applyButton1ActionPerformed

    private void directExecuteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directExecuteButtonActionPerformed
        updateDirectTable(directExecute(directTextArea.getText()));
    }//GEN-LAST:event_directExecuteButtonActionPerformed

    private void directExecuteUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directExecuteUpdateButtonActionPerformed
        directExecuteUpdate();
        updateDirectTable(directExecute(directQuery));
    }//GEN-LAST:event_directExecuteUpdateButtonActionPerformed

    private void groupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupButtonActionPerformed
        createGroupDetails();
        updateGroupGroupDetailsTabel(selectQuery(selectGroupDetails));
    }//GEN-LAST:event_groupButtonActionPerformed

    private void groupButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupButton2ActionPerformed
        deleteGroupDetails();
        updateGroupGroupDetailsTabel(selectQuery(selectGroupDetails));
    }//GEN-LAST:event_groupButton2ActionPerformed

    private void groupButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupButton3ActionPerformed
        modifyGroupDetais();
        updateGroupGroupDetailsTabel(selectQuery(selectGroupDetails));
    }//GEN-LAST:event_groupButton3ActionPerformed

    private void groupButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupButton4ActionPerformed
        createGroups();
        updateGroupGroupsTabel(selectQuery(selectGroups));
    }//GEN-LAST:event_groupButton4ActionPerformed

    private void groupButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupButton5ActionPerformed
        deleteGroups();
        updateGroupGroupsTabel(selectQuery(selectGroups));
    }//GEN-LAST:event_groupButton5ActionPerformed

    private void timetableCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableCreateButtonActionPerformed
        createTimetable();
        updateTimetableTabel(selectQuery(selectTimetable));
    }//GEN-LAST:event_timetableCreateButtonActionPerformed

    private void TimetableModifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimetableModifyButtonActionPerformed
        modifyTimetable();
        updateTimetableTabel(selectQuery(selectTimetable));
    }//GEN-LAST:event_TimetableModifyButtonActionPerformed

    private void timetableDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableDeleteButtonActionPerformed
        deleteTimetable();
        updateTimetableTabel(selectQuery(selectTimetable));
    }//GEN-LAST:event_timetableDeleteButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(adminGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DirectDBaccess;
    private javax.swing.JTextField FnameField;
    private javax.swing.JTextField LnameField;
    private javax.swing.JPanel Payments;
    private javax.swing.JButton TimetableModifyButton;
    private javax.swing.JTextArea addressArea;
    private javax.swing.JButton applyButton1;
    private javax.swing.JButton applyButton2;
    private javax.swing.JPanel assignmentPanel;
    private javax.swing.JPanel branchesPanel;
    private javax.swing.JTextField courseField;
    private javax.swing.JPanel coursesPanel;
    private javax.swing.JButton createButton1;
    private javax.swing.JButton createButton2;
    private javax.swing.JButton deleteButton1;
    private javax.swing.JButton deleteButton2;
    private javax.swing.JButton directExecuteButton;
    private javax.swing.JButton directExecuteUpdateButton;
    private javax.swing.JLabel directInputLabel;
    private javax.swing.JLabel directOutputLabel;
    private javax.swing.JTable directTable;
    private javax.swing.JTextArea directTextArea;
    private javax.swing.JPanel facultyPanel;
    private javax.swing.JButton findButton1;
    private javax.swing.JButton findButton2;
    private javax.swing.JPanel gradesPanel;
    private javax.swing.JButton groupButton;
    private javax.swing.JButton groupButton2;
    private javax.swing.JButton groupButton3;
    private javax.swing.JButton groupButton4;
    private javax.swing.JButton groupButton5;
    private javax.swing.JTextField groupField1;
    private javax.swing.JTextField groupField2;
    private javax.swing.JTextField groupField3;
    private javax.swing.JTextField groupField4;
    private javax.swing.JTextField groupField5;
    private javax.swing.JTable groupGroupDetailsTabel;
    private javax.swing.JTable groupGroupsTabel;
    private javax.swing.JLabel groupLabel1;
    private javax.swing.JLabel groupLabel2;
    private javax.swing.JLabel groupLabel3;
    private javax.swing.JLabel groupLabel4;
    private javax.swing.JLabel groupLabel5;
    private javax.swing.JLabel groupLabel6;
    private javax.swing.JLabel groupLabel7;
    private javax.swing.JPanel groupsPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField lessonsField;
    private javax.swing.JTextField lessonsField2;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JTextField phoneField;
    private javax.swing.JTextField sidField1;
    private javax.swing.JTextField sidField2;
    private javax.swing.JTable studentAttendanceTable;
    private javax.swing.JPanel studentPanel;
    private javax.swing.JTable studentStudentTable;
    private javax.swing.JButton timetableCreateButton;
    private javax.swing.JComboBox<String> timetableDayCombo;
    private javax.swing.JButton timetableDeleteButton;
    private javax.swing.JComboBox<String> timetableEndHoursCombo;
    private javax.swing.JComboBox<String> timetableEndMinCombo;
    private javax.swing.JComboBox<String> timetableGroupCombo;
    private javax.swing.JLabel timetableLabel;
    private javax.swing.JLabel timetableLabel2;
    private javax.swing.JLabel timetableLabel3;
    private javax.swing.JLabel timetableLabel4;
    private javax.swing.JLabel timetableLabel5;
    private javax.swing.JLabel timetableLabel6;
    private javax.swing.JLabel timetableLabel7;
    private javax.swing.JLabel timetableLabel8;
    private javax.swing.JLabel timetableLabel9;
    private javax.swing.JPanel timetablePanel;
    private javax.swing.JComboBox<String> timetableStartHoursCombo;
    private javax.swing.JComboBox<String> timetableStartMinCombo;
    private javax.swing.JComboBox<String> timetableSubjectCombo;
    private javax.swing.JTable timetableTabel;
    // End of variables declaration//GEN-END:variables
}

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
import java.text.SimpleDateFormat;
import java.util.Date;
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
    
    private String coursesSelect = "SELECT courses.Cid AS 'Course ID',\n" +
                                "		courses.Course AS 'Description',\n" +
                                "        courses.Price\n" +
                                "FROM courses;";
    
    private String coursesSelect2 = "SELECT student.Fname AS 'First Name',\n" +
                                "		student.Lname AS 'Last Name',\n" +
                                "        payments.Sid AS 'Student ID',\n" +
                                "        payments.Required,\n" +
                                "        payments.Payed\n" +
                                "FROM (payments\n" +
                                "	INNER JOIN student ON payments.Sid = student.Sid);";
    
    private String courseFind = "SELECT courses.Cid AS 'Course ID',\n" +
                                "		courses.Course AS 'Description',\n" +
                                "        courses.Price\n" +
                                "FROM courses\n" +
                                "WHERE courses.Cid = ?;";
    
    private String courseDelete = "DELETE FROM `programming_db`.`courses` WHERE `Cid`=?;";
    
    private String courseCreate = "INSERT INTO `programming_db`.`courses` "
                                    + "(`Cid`, `Course`, `Price`) VALUES "
                                    + "(?, ?, ?);";
    
    private String courseModify = "UPDATE `programming_db`.`courses` SET "
                                    + "`Course`=?, `Price`=? WHERE `Cid`=?;";
    
    private String paymentFind ="SELECT  payments.Sid AS 'Student ID',\n" +
                                "        payments.Required,\n" +
                                "        payments.Payed\n" +
                                "FROM (payments\n" +
                                "	INNER JOIN student ON payments.Sid = student.Sid)\n" +
                                "WHERE student.Sid = ?;";
    
    private String paymentCreate ="INSERT INTO `programming_db`.`payments` "
                                    + "(`Sid`, `Required`, `Payed`) VALUES "
                                    + "(?, ?, ?);";
    
    private String paymentDelete = "DELETE FROM `programming_db`.`payments` WHERE `Sid`=?;";
    
    private String paymentModify = "UPDATE `programming_db`.`payments` SET "
                                    + "`Required`=?, `Payed`=? WHERE "
                                    + "`Sid`=?;";
    
    private String branchSelect = "SELECT branch_details.Bid AS 'Branch ID',\n" +
                                "		branch_details.Bname AS 'Branch Name',\n" +
                                "        branch_details.Baddress AS 'Address'\n" +
                                "FROM branch_details;";
    
    private String branchSelect2 = "SELECT branches.Bid AS 'Branch ID',\n" +
                                "		branches.Courses AS 'Course ID',\n" +
                                "        courses.Course AS 'Course'\n" +
                                "FROM (branches\n" +
                                "	INNER JOIN courses ON branches.Courses = courses.Cid);";
    
    private String branchFill = "SELECT branch_details.Bid\n" +
                                "FROM branch_details;";
    
    private String branchFill2 = "SELECT courses.Cid\n" +
                                "FROM courses;";
    
    private String branchFind = "SELECT branch_details.Bid AS 'Branch ID',\n" +
                                "		branch_details.Bname AS 'Branch Name',\n" +
                                "        branch_details.Baddress AS 'Address'\n" +
                                "FROM branch_details\n" +
                                "WHERE branch_details.Bid = ?;";
    
    private String branchCreate = "INSERT INTO `programming_db`.`branch_details` "
                                    + "(`Bid`, `Bname`, `Baddress`) VALUES "
                                    + "(?, ?, ?);";
    
    private String branchDelete = "DELETE FROM `programming_db`.`branch_details`"
                                    + " WHERE `Bid`=?;";

    private String branchModify = "UPDATE `programming_db`.`branch_details` SET "
                                + "`Bname`=?, `Baddress`=? "
                                + "WHERE `Bid`=?;";

    private String branchCreate2 = "INSERT INTO `programming_db`.`branches` "
                                    + "(`Bid`, `Courses`) VALUES "
                                    + "(?, ?);";

    private String branchDelete2 = "DELETE FROM `programming_db`.`branches` WHERE "
                                    + "`Courses`=? and`Bid`=?;";
    
    private String gradesSelect = "SELECT grades.Sid AS 'Student ID',\n" +
                                    "		student.Fname AS 'First Name',\n" +
                                    "        student.Lname AS 'Last Name',\n" +
                                    "        grades.SBid AS 'Subject ID',\n" +
                                    "        subjects.Subject AS 'Subject',\n" +
                                    "        grades.Grade,\n" +
                                    "        grades.ExamGrade AS 'Exam Grade'\n" +
                                    "FROM ((grades\n" +
                                    "	INNER JOIN student ON grades.Sid = student.Sid)\n" +
                                    "    INNER JOIN subjects ON grades.SBid = subjects.SBid);";
    
    
    private String gradesSelect2 = "SELECT 	assignment_grades.Aid AS 'Assignment ID',\n" +
                                    "		assignment_grades.Sid AS 'Student ID',\n" +
                                    "		student.Fname AS 'First Name',\n" +
                                    "        student.Lname AS 'Last Name',\n" +
                                    "		assignment_grades.Grade\n" +
                                    "FROM (assignment_grades\n" +
                                    "	INNER JOIN student ON assignment_grades.Sid = student.Sid);";
    
    private String getStudentID = "SELECT student.Sid\n" +
                                    "FROM student\n" +
                                    "ORDER BY student.Sid;";
    
    private String getSubjectID = "SELECT subjects.SBid\n" +
                                    "FROM subjects;";

    private String getAssignmentID = "SELECT assignments.Aid\n" +
                                        "FROM assignments;";
    
    private String gradesFind = "SELECT grades.Grade,\n" +
                                "		grades.ExamGrade\n" +
                                "FROM grades\n" +
                                "WHERE grades.Sid = ? AND grades.SBid = ?";
    
    private String gradesCreate = "INSERT INTO `programming_db`.`grades` "
                                    + "(`Sid`, `SBid`, `Grade`, `ExamGrade`) VALUES "
                                    + "(?, ?, ?, ?);";
    
    private String gradesDelete = "DELETE FROM `programming_db`.`grades` WHERE "
                                + "`Sid`=? and`SBid`=?;";
    
    private String  gradesModify = "UPDATE `programming_db`.`grades` SET "
                                + "`Grade`=?, `ExamGrade`=? WHERE "
                                + "`Sid`=? and`SBid`=?;";
    
    private String assignmentGradesCreate = "INSERT INTO `programming_db`.`assignment_grades` "
                                            + "(`Aid`, `Sid`, `Grade`) VALUES "
                                            + "(?, ?, ?);";
    
    private String assignmentGradesFind = "SELECT assignment_grades.Grade\n" +
                                            "FROM assignment_grades\n" +
                                            "WHERE assignment_grades.Aid = ? AND assignment_grades.Sid = ?;";
    
    private String assignmentGradesDelete = "DELETE FROM `programming_db`.`assignment_grades` WHERE "
                                            + "`Aid`=? and`Sid`=?;";
    
    private String assignmentGradesModify = "UPDATE `programming_db`.`assignment_grades` SET "
                                            + "`Grade`=? WHERE "
                                            + "`Aid`=? and`Sid`=?;";
    
    private String assignmentSelect = "SELECT 	assignments.Cid AS 'Course ID',\n" +
                                    "		courses.Course,\n" +
                                    "        assignments.Aid,\n" +
                                    "        assignments.Subject AS 'Subject ID',\n" +
                                    "        subjects.Subject,\n" +
                                    "        assignments.DueDate AS 'Due Date',\n" +
                                    "        assignments.Visible\n" +
                                    "FROM ((assignments\n" +
                                    "	INNER JOIN courses ON assignments.Cid = courses.Cid)\n" +
                                    "    INNER JOIN subjects ON assignments.Subject = subjects.SBid);";
    
    private String getCourseID = "SELECT courses.Cid\n" +
                                    "FROM courses;";
    
    private String assignmentCreate = "INSERT INTO `programming_db`.`assignments` "
                                    + "(`Cid`, `Aid`, `Subject`, `Visible`, `DueDate`) VALUES "
                                    + "(?, ?, ?, ?, ?);";
    
    private String assignmentDelete = "DELETE FROM `programming_db`.`assignments` WHERE "
                                    + "`Aid`=?;";
    
    private String assignmentModify = "UPDATE `programming_db`.`assignments` SET "
                                    + "`Cid`=?, `Subject`=?, `Visible`=?, `DueDate`=? WHERE "
                                    + "`Aid`=?;";
    
    private String assignmentFind = "SELECT 	assignments.Cid,\n" +
                                    "		assignments.Aid,\n" +
                                    "        assignments.Subject,\n" +
                                    "        assignments.Visible,\n" +
                                    "        assignments.DueDate\n" +
                                    "FROM assignments\n" +
                                    "WHERE assignments.Aid = ?;";
    
    private String facultyMembersSelect = "SELECT faculty_members.Fid AS 'Faculty ID',\n" +
                                        "           faculty_members.Fname AS 'First Name',\n" +
                                        "           faculty_members.Lname AS 'Last Name'\n" +
                                        "FROM faculty_members;";
    
    private String subjectsSelect = "SELECT  subjects.SBid AS 'Subject ID',\n" +
                                    "        subjects.Lecturer AS 'Lecturer ID',\n" +
                                    "        faculty_members.Fname AS 'Lecturer First Name',\n" +
                                    "        faculty_members.Lname AS 'Lecturer Last Name',\n" +
                                    "        subjects.Subject AS 'Subject Name'\n" +
                                    "FROM (subjects\n" +
                                    "	INNER JOIN faculty_members ON subjects.Lecturer = faculty_members.Fid);";
    
    private String getFacultyID = "SELECT faculty_members.Fid\n" +
                                    "FROM faculty_members;";
    
    private String facultyFindButton1 = "SELECT  faculty_members.Fid AS 'Faculty ID',\n" +
                                        "	 faculty_members.Fname AS 'First Name',\n" +
                                        "        faculty_members.Lname AS 'Last Name'\n" +
                                        "FROM faculty_members\n" +
                                        "WHERE faculty_members.Fid = ?;";
    
    private String facultyCreateButton1 = "INSERT INTO `programming_db`.`faculty_members` "
                                            + "(`Fid`, `Fname`, `Lname`) VALUES "
                                            + "(?,?, ?);";
    
    private String facultyDeleteButton1 = "DELETE FROM `programming_db`.`faculty_members` WHERE"
                                        + " `Fid`=?;";
    
    private String facultyModifyButton1 = "UPDATE `programming_db`.`faculty_members` SET "
                                        + "`Fname`=?, `Lname`=? WHERE "
                                        + "`Fid`=?;";
    
    private String subjectsFind = "SELECT * FROM subjects\n" +
                                    "WHERE subjects.SBid = ?;";
    
    private String subjectsCreate = "INSERT INTO `programming_db`.`subjects` "
                                    + "(`SBid`, `Lecturer`, `Subject`) VALUES "
                                    + "(?, ?, ?);";
    
    private String subjectsDelete = "DELETE FROM `programming_db`.`subjects` WHERE "
                                    + "`SBid`=?;";
    
    private String subjectsModify = "UPDATE `programming_db`.`subjects` SET "
                                    + "`Lecturer`=?, `Subject`=? WHERE "
                                    + "`SBid`=?;";
    
    private String loginSelect = "SELECT  login.Username,\n" +
                                "		login.Password,\n" +
                                "		login.Role\n" +
                                "FROM login;";
    
    private String loginCreate = "INSERT INTO `programming_db`.`login` "
                                + "(`Username`, `Password`, `Role`) VALUES "
                                + "(?, 'panadol', ?);";
    
    private String loginDelete = "DELETE FROM `programming_db`.`login` WHERE "
                                + "`Username`=?;";
    
    private String loginModify = "UPDATE `programming_db`.`login` SET "
                                + "`Role`=? WHERE "
                                + "`Username`=?;";
    
    private String loginReset = "UPDATE `programming_db`.`login` SET "
                                + "`Password`='panadol' WHERE "
                                + "`Username`=?;";
   

    public adminGUI() {
        initComponents();
        readConnectionDetails();
        
        updateStudentStudentTable(selectQuery(selectStudentQuery));
        updateStudentAttendanceTable(selectQuery(selectAttendanceQuery));
        
        updateGroupGroupDetailsTabel(selectQuery(selectGroupDetails));
        updateGroupGroupsTabel(selectQuery(selectGroups));
        
        updateTimetableTabel(selectQuery(selectTimetable));
        timetableFill();
        
        updateCoursesTabel(selectQuery(coursesSelect));
        updatePaymentsTabel(selectQuery(coursesSelect2));
        
        updateBranchDetailsTabel(selectQuery(branchSelect));
        updateBranchTabel(selectQuery(branchSelect2));
        branchFill();
        
        updateGradesTable(selectQuery(gradesSelect));
        updateAssignmentGradesTable(selectQuery(gradesSelect2));
        gradesFill();
        
        updateAssignmentsTable(selectQuery(assignmentSelect));
        assignmentFill();
        
        updateFacultyMambersTable(selectQuery(facultyMembersSelect));
        updateSubjectsTable(selectQuery(subjectsSelect));
        facultyFill();
        
        updateLoginTable(selectQuery(loginSelect));
        
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
    private void updateCoursesTabel (ResultSet rs){
        coursesTable.setModel(DbUtils.resultSetToTableModel(rs));
    }    
    private void updatePaymentsTabel (ResultSet rs){
        paymentsTable.setModel(DbUtils.resultSetToTableModel(rs));
    }    
    private void updateBranchDetailsTabel (ResultSet rs){
        branchDetailsTable.setModel(DbUtils.resultSetToTableModel(rs));
    }    
    private void updateBranchTabel (ResultSet rs){
        branchTable.setModel(DbUtils.resultSetToTableModel(rs));
    }    
    private void updateGradesTable (ResultSet rs){
        gradesTable.setModel(DbUtils.resultSetToTableModel(rs));
    }    
    private void updateAssignmentGradesTable (ResultSet rs){
        assignmentGradesTabel.setModel(DbUtils.resultSetToTableModel(rs));
    }
    private void updateAssignmentsTable (ResultSet rs){
        assignmentsTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    private void updateFacultyMambersTable (ResultSet rs){
        facultyMembersTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    private void updateSubjectsTable (ResultSet rs){
        subjectsTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    private void updateLoginTable(ResultSet rs){
        loginTable.setModel(DbUtils.resultSetToTableModel(rs));
        hidePassword();
    }
    
    
    
    private void loginReset(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(loginReset);
            PrepStatement.setString(1, loginUsernameFiled.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loginModify(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(loginModify);
            PrepStatement.setString(2, loginUsernameFiled.getText());
            PrepStatement.setString(1, loginRoleCombo.getSelectedItem().toString());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loginDelete(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(loginDelete);
            PrepStatement.setString(1, loginUsernameFiled.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loginCreate(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(loginCreate);
            PrepStatement.setString(1, loginUsernameFiled.getText());
            PrepStatement.setString(2, loginRoleCombo.getSelectedItem().toString());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hidePassword(){
        
        
        loginTable.getRowCount();
        
        for (int i = 0; i< loginTable.getRowCount(); i++){
            if(loginTable.getValueAt(i, 1).equals("panadol")){
                loginTable.setValueAt("DEFAULT", i, 1);
            }
            else{
                loginTable.setValueAt("CUSTOM", i, 1);
            }
        }
    }
    
    
    
    private void subjectsModify(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(subjectsModify);
            PrepStatement.setString(3, facultySBIDfield.getText());
            PrepStatement.setString(1, facultyFIDCombo.getSelectedItem().toString());
            PrepStatement.setString(2, facultySubjectField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void subjectsDelete(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(subjectsDelete);
            PrepStatement.setString(1, facultySBIDfield.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void subjectsCreate(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(subjectsCreate);
            PrepStatement.setString(1, facultySBIDfield.getText());
            PrepStatement.setString(2, facultyFIDCombo.getSelectedItem().toString());
            PrepStatement.setString(3, facultySubjectField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void subjectsFind(){
        ResultSet resultSet = null;        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(subjectsFind);
            PrepStatement.setString(1, facultySBIDfield.getText());
            
            resultSet = PrepStatement.executeQuery();
            
            while(resultSet.next()){
                facultySBIDfield.setText(resultSet.getString(1));
                facultyFIDCombo.setSelectedItem(resultSet.getObject(2));
                facultySubjectField.setText(resultSet.getString(3));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void facultyMembersModify(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(facultyModifyButton1);
            PrepStatement.setString(3, facultyIdField.getText());
            PrepStatement.setString(1, facultyFnameField.getText());
            PrepStatement.setString(2, facultyLnameField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void facultyMembersDelete(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(facultyDeleteButton1);
            PrepStatement.setString(1, facultyIdField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void facultyMembersCreate(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(facultyCreateButton1);
            PrepStatement.setString(1, facultyIdField.getText());
            PrepStatement.setString(2, facultyFnameField.getText());
            PrepStatement.setString(3, facultyLnameField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void facultyMembersFind(){
        ResultSet resultSet = null;        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(facultyFindButton1);
            PrepStatement.setString(1, facultyIdField.getText());
            
            resultSet = PrepStatement.executeQuery();
            
            while(resultSet.next()){
                facultyIdField.setText(resultSet.getString(1));
                facultyFnameField.setText(resultSet.getString(2));
                facultyLnameField.setText(resultSet.getString(3));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void facultyFill(){
       facultyFIDCombo.removeAllItems();
        
        try {
            ResultSet rs = selectQuery(getFacultyID);
            
           while(rs.next())
                facultyFIDCombo.addItem(rs.getString(1));
                        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    
    
    private void assignmentModify(){
        ResultSet resultSet = null;
        Date date = assignmentDatePicker.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = sdf.format(date);
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(assignmentModify);
            PrepStatement.setString(1, assignmentCourseCombo.getSelectedItem().toString());
            PrepStatement.setString(5, assignmentField.getText());
            PrepStatement.setString(2, assignmentSubjectCombo.getSelectedItem().toString());
            PrepStatement.setString(3, assignmentVisibleCombo.getSelectedItem().toString());
            PrepStatement.setString(4, formatedDate);
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void assignmentDelete(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(assignmentDelete);
            PrepStatement.setString(1, assignmentField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void assignmentCreate(){
        ResultSet resultSet = null;
        Date date = assignmentDatePicker.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = sdf.format(date);
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(assignmentCreate);
            PrepStatement.setString(1, assignmentCourseCombo.getSelectedItem().toString());
            PrepStatement.setString(2, assignmentField.getText());
            PrepStatement.setString(3, assignmentSubjectCombo.getSelectedItem().toString());
            PrepStatement.setString(4, assignmentVisibleCombo.getSelectedItem().toString());
            PrepStatement.setString(5, formatedDate);
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void assignmentFind(){
        ResultSet resultSet = null;        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(assignmentFind);
            PrepStatement.setString(1, assignmentField.getText());
            
            resultSet = PrepStatement.executeQuery();
            
            while(resultSet.next()){
                assignmentCourseCombo.setSelectedItem(resultSet.getObject(1));
                assignmentField.setText(resultSet.getString(2));
                assignmentSubjectCombo.setSelectedItem(resultSet.getObject(3));
                assignmentVisibleCombo.setSelectedItem(resultSet.getObject(4));
                assignmentDatePicker.setDate(resultSet.getDate(5));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void assignmentFill(){
        assignmentCourseCombo.removeAllItems();
        assignmentSubjectCombo.removeAllItems();
        
        try {
            ResultSet rs = selectQuery(getCourseID);
            
           while(rs.next())
                assignmentCourseCombo.addItem(rs.getString(1));
            
            rs = selectQuery(getSubjectID);
            
            while(rs.next())
                assignmentSubjectCombo.addItem(rs.getString(1));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    
    
    private void gradesCreate(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(gradesCreate);
            PrepStatement.setString(1, studentCombo1.getSelectedItem().toString());
            PrepStatement.setString(2, subjectCombo1.getSelectedItem().toString());
            PrepStatement.setString(3, gradeField1.getText());
            PrepStatement.setString(4, examGradeField1.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gradesDelete(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(gradesDelete);
            PrepStatement.setString(1, studentCombo1.getSelectedItem().toString());
            PrepStatement.setString(2, subjectCombo1.getSelectedItem().toString());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gradesFind(){
        ResultSet resultSet = null;        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(gradesFind);
            PrepStatement.setString(1, studentCombo1.getSelectedItem().toString());
            PrepStatement.setString(2, subjectCombo1.getSelectedItem().toString());
            
            resultSet = PrepStatement.executeQuery();
            
            while(resultSet.next()){
                gradeField1.setText(resultSet.getString(1));
                examGradeField1.setText(resultSet.getString(2));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gradesModify(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(gradesModify);
            PrepStatement.setString(1, gradeField1.getText());
            PrepStatement.setString(2, examGradeField1.getText());
            PrepStatement.setString(3, studentCombo1.getSelectedItem().toString());
            PrepStatement.setString(4, subjectCombo1.getSelectedItem().toString());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void assignmentGradesFind(){
        ResultSet resultSet = null;        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(assignmentGradesFind);
            PrepStatement.setString(1, assignmentCombo2.getSelectedItem().toString());
            PrepStatement.setString(2, studentCombo2.getSelectedItem().toString());
            
            resultSet = PrepStatement.executeQuery();
            
            while(resultSet.next()){
                gradeField2.setText(resultSet.getString(1));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void assignmentGradesCreate(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(assignmentGradesCreate);
            PrepStatement.setString(1, assignmentCombo2.getSelectedItem().toString());
            PrepStatement.setString(2, studentCombo2.getSelectedItem().toString());
            PrepStatement.setString(3, gradeField2.getText());
            
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void assignmentGradesDelete(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(assignmentGradesDelete);
            PrepStatement.setString(1, assignmentCombo2.getSelectedItem().toString());
            PrepStatement.setString(2, studentCombo2.getSelectedItem().toString());
            
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void assignmentGradesModify(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(assignmentGradesModify);
            PrepStatement.setString(2, assignmentCombo2.getSelectedItem().toString());
            PrepStatement.setString(3, studentCombo2.getSelectedItem().toString());
            PrepStatement.setString(1, gradeField2.getText());
            
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gradesFill(){
        studentCombo1.removeAllItems();
        subjectCombo1.removeAllItems();
        assignmentCombo2.removeAllItems();
        studentCombo2.removeAllItems();
        
        try {
            ResultSet rs = selectQuery(getStudentID);
            
            while(rs.next()){
                studentCombo1.addItem(rs.getString(1));
                studentCombo2.addItem(rs.getString(1));
            }
            
            rs = selectQuery(getSubjectID);
            
            while(rs.next())
                subjectCombo1.addItem(rs.getString(1));
            
            rs = selectQuery(getAssignmentID);
            
            while(rs.next())
                assignmentCombo2.addItem(rs.getString(1));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    
    
    private void branchFill(){
        courseIdCombo.removeAllItems();
        branchIdCombo.removeAllItems();
        try {
            ResultSet rs = selectQuery(branchFill);
            
            while(rs.next())
                branchIdCombo.addItem(rs.getString(1));
            
            rs = selectQuery(branchFill2);
            
            while(rs.next())
                courseIdCombo.addItem(rs.getString(1));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void branchDetailsCreate(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(branchCreate);
            PrepStatement.setString(1, branchIDField.getText());
            PrepStatement.setString(2, branchNameField.getText());
            PrepStatement.setString(3, branchAddressArea.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void branchDetailsDelete(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(branchDelete);
            PrepStatement.setString(1, branchIDField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void branchDetailsFind(){
        ResultSet resultSet = null;        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(branchFind);
            PrepStatement.setString(1, branchIDField.getText());
            
            resultSet = PrepStatement.executeQuery();
            
            while(resultSet.next()){
                branchIDField.setText(resultSet.getString(1));
                branchNameField.setText(resultSet.getString(2));
                branchAddressArea.setText(resultSet.getString(3));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void branchDetailsModify(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(branchModify);
            PrepStatement.setString(3, branchIDField.getText());
            PrepStatement.setString(1, branchNameField.getText());
            PrepStatement.setString(2, branchAddressArea.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void branchesCreate(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(branchCreate2);
            PrepStatement.setString(1, branchIdCombo.getSelectedItem().toString());
            PrepStatement.setString(2, courseIdCombo.getSelectedItem().toString());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void branchesDelete(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(branchDelete2);
            PrepStatement.setString(1, courseIdCombo.getSelectedItem().toString());
            PrepStatement.setString(2, branchIdCombo.getSelectedItem().toString());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void findPayment(){
        ResultSet resultSet = null;        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(paymentFind);
            PrepStatement.setString(1, coursesStidentIdField.getText());
            
            resultSet = PrepStatement.executeQuery();
            
            while(resultSet.next()){
                coursesStidentIdField.setText(resultSet.getString(1));
                coursesRequiredField.setText(resultSet.getString(2));
                coursesPAyedField.setText(resultSet.getString(3));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modifyPayment(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(paymentModify);
            PrepStatement.setString(3, coursesStidentIdField.getText());
            PrepStatement.setString(1, coursesRequiredField.getText());
            PrepStatement.setString(2, coursesPAyedField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    private void deletePayment(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(paymentDelete);
            PrepStatement.setString(1, coursesStidentIdField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createPayment(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(paymentCreate);
            PrepStatement.setString(1, coursesStidentIdField.getText());
            PrepStatement.setString(2, coursesRequiredField.getText());
            PrepStatement.setString(3, coursesPAyedField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    private void modifyCourse(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(courseModify);
            PrepStatement.setString(3, courseIdField.getText());
            PrepStatement.setString(1, courseArea.getText());
            PrepStatement.setString(2, coursePriceField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }    
    }
    
    private void findCourse(){
        ResultSet resultSet = null;        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(courseFind);
            PrepStatement.setString(1, courseIdField.getText());
            
            resultSet = PrepStatement.executeQuery();
            
            while(resultSet.next()){
                courseIdField.setText(resultSet.getString(1));
                courseArea.setText(resultSet.getString(2));
                coursePriceField.setText(resultSet.getString(3));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteCourse(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(courseDelete);
            PrepStatement.setString(1, courseIdField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createCourse(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(courseCreate);
            PrepStatement.setString(1, courseIdField.getText());
            PrepStatement.setString(2, courseArea.getText());
            PrepStatement.setString(3, coursePriceField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
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
        coursesLAbel1 = new javax.swing.JLabel();
        coursesLabel2 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        coursesTable = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        paymentsTable = new javax.swing.JTable();
        coursesesLabel3 = new javax.swing.JLabel();
        coursesLabel4 = new javax.swing.JLabel();
        coursesLabel5 = new javax.swing.JLabel();
        coursesLabel6 = new javax.swing.JLabel();
        coursesLablel7 = new javax.swing.JLabel();
        coursesLabel8 = new javax.swing.JLabel();
        courseIdField = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        courseArea = new javax.swing.JTextArea();
        coursePriceField = new javax.swing.JTextField();
        coursesFindButton = new javax.swing.JButton();
        coursesCreateButton = new javax.swing.JButton();
        coursesDeleteButton = new javax.swing.JButton();
        coursesModifyButton = new javax.swing.JButton();
        coursesStidentIdField = new javax.swing.JTextField();
        coursesRequiredField = new javax.swing.JTextField();
        coursesPAyedField = new javax.swing.JTextField();
        paymentsFindButton = new javax.swing.JButton();
        paymentsCreateButton = new javax.swing.JButton();
        paymentsDeleteButton = new javax.swing.JButton();
        paymentsModifyButton = new javax.swing.JButton();
        branchesPanel = new javax.swing.JPanel();
        branchLabel1 = new javax.swing.JLabel();
        branchLabel2 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        branchDetailsTable = new javax.swing.JTable();
        jScrollPane13 = new javax.swing.JScrollPane();
        branchTable = new javax.swing.JTable();
        branchLabel3 = new javax.swing.JLabel();
        branchLabel4 = new javax.swing.JLabel();
        branchLabel5 = new javax.swing.JLabel();
        branchLabel6 = new javax.swing.JLabel();
        branchLabel7 = new javax.swing.JLabel();
        courseIdCombo = new javax.swing.JComboBox<>();
        branchIdCombo = new javax.swing.JComboBox<>();
        branchIDField = new javax.swing.JTextField();
        branchNameField = new javax.swing.JTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        branchAddressArea = new javax.swing.JTextArea();
        findBranchButton = new javax.swing.JButton();
        modifyBranchButton = new javax.swing.JButton();
        createBranchButton = new javax.swing.JButton();
        deleteBranchButton = new javax.swing.JButton();
        createBranchButton2 = new javax.swing.JButton();
        deleteBranchButton2 = new javax.swing.JButton();
        gradesPanel = new javax.swing.JPanel();
        gradeBookLabel1 = new javax.swing.JLabel();
        gradeBookLabel2 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        gradesTable = new javax.swing.JTable();
        jScrollPane16 = new javax.swing.JScrollPane();
        assignmentGradesTabel = new javax.swing.JTable();
        gradeBookLabel3 = new javax.swing.JLabel();
        gradeBookLabel4 = new javax.swing.JLabel();
        gradeBookLabel5 = new javax.swing.JLabel();
        gradeBookLabel6 = new javax.swing.JLabel();
        studentCombo1 = new javax.swing.JComboBox<>();
        subjectCombo1 = new javax.swing.JComboBox<>();
        gradeField1 = new javax.swing.JTextField();
        examGradeField1 = new javax.swing.JTextField();
        gradeBookLabel7 = new javax.swing.JLabel();
        gradeBookLabel8 = new javax.swing.JLabel();
        gradeBookLabel9 = new javax.swing.JLabel();
        assignmentCombo2 = new javax.swing.JComboBox<>();
        studentCombo2 = new javax.swing.JComboBox<>();
        gradeField2 = new javax.swing.JTextField();
        gradesFindButton = new javax.swing.JButton();
        gradesModifyButton = new javax.swing.JButton();
        gradesCreateButton = new javax.swing.JButton();
        gradesDeleteButton = new javax.swing.JButton();
        assignmentGradesFindButton = new javax.swing.JButton();
        assignmentGradesModifybutton = new javax.swing.JButton();
        assignmentGradesCreateButton = new javax.swing.JButton();
        assignmentGradesDeleteButton = new javax.swing.JButton();
        assignmentPanel = new javax.swing.JPanel();
        assignmentLabel4 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        assignmentsTable = new javax.swing.JTable();
        assignmentLabel1 = new javax.swing.JLabel();
        assignmentLabel2 = new javax.swing.JLabel();
        assignmentLabel3 = new javax.swing.JLabel();
        assignmentCourseCombo = new javax.swing.JComboBox<>();
        assignmentField = new javax.swing.JTextField();
        assignmentSubjectCombo = new javax.swing.JComboBox<>();
        assignmentLabel5 = new javax.swing.JLabel();
        assignmentLabel6 = new javax.swing.JLabel();
        assignmentVisibleCombo = new javax.swing.JComboBox<>();
        assignmentDatePicker = new com.toedter.calendar.JDateChooser();
        assignmentCreateButton = new javax.swing.JButton();
        assignmentDeleteButton = new javax.swing.JButton();
        assignmentModifyButton = new javax.swing.JButton();
        assignmentFindButton = new javax.swing.JButton();
        assignmentRefreshButton = new javax.swing.JButton();
        facultyPanel = new javax.swing.JPanel();
        facultyLablel1 = new javax.swing.JLabel();
        facultyLablel2 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        facultyMembersTable = new javax.swing.JTable();
        jScrollPane19 = new javax.swing.JScrollPane();
        subjectsTable = new javax.swing.JTable();
        facultyLablel3 = new javax.swing.JLabel();
        facultyLablel4 = new javax.swing.JLabel();
        facultyLablel5 = new javax.swing.JLabel();
        facultyIdField = new javax.swing.JTextField();
        facultyFnameField = new javax.swing.JTextField();
        facultyLnameField = new javax.swing.JTextField();
        facultyLablel6 = new javax.swing.JLabel();
        facultyLablel7 = new javax.swing.JLabel();
        facultyLablel8 = new javax.swing.JLabel();
        facultyFIDCombo = new javax.swing.JComboBox<>();
        facultySBIDfield = new javax.swing.JTextField();
        facultySubjectField = new javax.swing.JTextField();
        subjectsFindButton = new javax.swing.JButton();
        subjectsCreateButton = new javax.swing.JButton();
        subjectsDeleteButton = new javax.swing.JButton();
        subjectsModifyButton = new javax.swing.JButton();
        facultyMembersFindButton = new javax.swing.JButton();
        facultyMembersCreateButton = new javax.swing.JButton();
        facultyMembersModifyButton = new javax.swing.JButton();
        facultyMembersDeleteButton = new javax.swing.JButton();
        loginPanel = new javax.swing.JPanel();
        loginLabel3 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        loginTable = new javax.swing.JTable();
        jScrollPane21 = new javax.swing.JScrollPane();
        loginNOTEarea = new javax.swing.JTextArea();
        loginLabel1 = new javax.swing.JLabel();
        loginLabel2 = new javax.swing.JLabel();
        loginUsernameFiled = new javax.swing.JTextField();
        loginRoleCombo = new javax.swing.JComboBox<>();
        loginCreateButton = new javax.swing.JButton();
        loginModifyButton = new javax.swing.JButton();
        loginDeleteButton = new javax.swing.JButton();
        loginResetButton = new javax.swing.JButton();
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
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
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
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
                .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(groupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(groupLabel4)
                        .addComponent(groupLabel5)
                        .addComponent(groupLabel3)
                        .addComponent(groupLabel7)))
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
                        .addGap(0, 41, Short.MAX_VALUE)
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

        coursesLAbel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        coursesLAbel1.setText("Courses");

        coursesLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        coursesLabel2.setText("Payments");

        coursesTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane9.setViewportView(coursesTable);

        paymentsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(paymentsTable);

        coursesesLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        coursesesLabel3.setText("Course ID");

        coursesLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        coursesLabel4.setText("Description");

        coursesLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        coursesLabel5.setText("Price");

        coursesLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        coursesLabel6.setText("Student ID");

        coursesLablel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        coursesLablel7.setText("Requiered");

        coursesLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        coursesLabel8.setText("Payed");

        courseArea.setColumns(20);
        courseArea.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        courseArea.setLineWrap(true);
        courseArea.setRows(2);
        courseArea.setTabSize(2);
        jScrollPane11.setViewportView(courseArea);

        coursesFindButton.setText("Find By ID");
        coursesFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesFindButtonActionPerformed(evt);
            }
        });

        coursesCreateButton.setText("Create");
        coursesCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesCreateButtonActionPerformed(evt);
            }
        });

        coursesDeleteButton.setText("Delete");
        coursesDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesDeleteButtonActionPerformed(evt);
            }
        });

        coursesModifyButton.setText("Modify");
        coursesModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesModifyButtonActionPerformed(evt);
            }
        });

        paymentsFindButton.setText("Find By ID");
        paymentsFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentsFindButtonActionPerformed(evt);
            }
        });

        paymentsCreateButton.setText("Create");
        paymentsCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentsCreateButtonActionPerformed(evt);
            }
        });

        paymentsDeleteButton.setText("Delete");
        paymentsDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentsDeleteButtonActionPerformed(evt);
            }
        });

        paymentsModifyButton.setText("Modify");
        paymentsModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentsModifyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout coursesPanelLayout = new javax.swing.GroupLayout(coursesPanel);
        coursesPanel.setLayout(coursesPanelLayout);
        coursesPanelLayout.setHorizontalGroup(
            coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coursesPanelLayout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addComponent(coursesLAbel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(coursesLabel2)
                .addGap(267, 267, 267))
            .addGroup(coursesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coursesPanelLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(coursesesLabel3)
                .addGap(97, 97, 97)
                .addComponent(coursesLabel4)
                .addGap(144, 144, 144)
                .addComponent(coursesLabel5)
                .addGap(298, 298, 298)
                .addComponent(coursesLabel6)
                .addGap(117, 117, 117)
                .addComponent(coursesLablel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(coursesLabel8)
                .addGap(87, 87, 87))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coursesPanelLayout.createSequentialGroup()
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(coursesPanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(coursesFindButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(coursesPanelLayout.createSequentialGroup()
                                .addComponent(coursesCreateButton)
                                .addGap(18, 18, 18)
                                .addComponent(coursesDeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(coursesPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(courseIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(coursePriceField)
                    .addComponent(coursesModifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                .addGap(243, 243, 243)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(coursesStidentIdField)
                    .addComponent(paymentsFindButton, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                .addGap(90, 90, 90)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(coursesRequiredField)
                    .addComponent(paymentsCreateButton, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                    .addComponent(paymentsDeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(89, 89, 89)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(coursesPAyedField)
                    .addComponent(paymentsModifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
                .addGap(61, 61, 61))
        );
        coursesPanelLayout.setVerticalGroup(
            coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coursesPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(coursesLAbel1)
                    .addComponent(coursesLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(43, 43, 43)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(coursesesLabel3)
                    .addComponent(coursesLabel4)
                    .addComponent(coursesLabel5)
                    .addComponent(coursesLabel6)
                    .addComponent(coursesLablel7)
                    .addComponent(coursesLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(coursesPanelLayout.createSequentialGroup()
                        .addComponent(courseIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(coursesFindButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(coursesPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(coursesCreateButton)
                            .addComponent(coursesDeleteButton)))
                    .addGroup(coursesPanelLayout.createSequentialGroup()
                        .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(coursePriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(coursesStidentIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(coursesRequiredField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(coursesPAyedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(coursesPanelLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(paymentsModifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(coursesModifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(paymentsFindButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(coursesPanelLayout.createSequentialGroup()
                                .addComponent(paymentsCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(paymentsDeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Courses", coursesPanel);

        branchLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        branchLabel1.setText("Branches");

        branchLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        branchLabel2.setText("Courses");

        branchDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane12.setViewportView(branchDetailsTable);

        branchTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane13.setViewportView(branchTable);

        branchLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        branchLabel3.setText("Branch ID");

        branchLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        branchLabel4.setText("Branch Name");

        branchLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        branchLabel5.setText("Branch Address");

        branchLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        branchLabel6.setText("Branch ID");

        branchLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        branchLabel7.setText("Course ID");

        branchAddressArea.setColumns(20);
        branchAddressArea.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        branchAddressArea.setLineWrap(true);
        branchAddressArea.setRows(2);
        jScrollPane14.setViewportView(branchAddressArea);

        findBranchButton.setText("Find by ID");
        findBranchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findBranchButtonActionPerformed(evt);
            }
        });

        modifyBranchButton.setText("Modify");
        modifyBranchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyBranchButtonActionPerformed(evt);
            }
        });

        createBranchButton.setText("Create");
        createBranchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBranchButtonActionPerformed(evt);
            }
        });

        deleteBranchButton.setText("Delete");
        deleteBranchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBranchButtonActionPerformed(evt);
            }
        });

        createBranchButton2.setText("Create");
        createBranchButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBranchButton2ActionPerformed(evt);
            }
        });

        deleteBranchButton2.setText("Delete");
        deleteBranchButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBranchButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout branchesPanelLayout = new javax.swing.GroupLayout(branchesPanel);
        branchesPanel.setLayout(branchesPanelLayout);
        branchesPanelLayout.setHorizontalGroup(
            branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(branchesPanelLayout.createSequentialGroup()
                .addGap(276, 276, 276)
                .addComponent(branchLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(branchLabel2)
                .addGap(236, 236, 236))
            .addGroup(branchesPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(branchesPanelLayout.createSequentialGroup()
                        .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(branchesPanelLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(branchLabel3)
                                .addGap(102, 102, 102)
                                .addComponent(branchLabel4)
                                .addGap(124, 124, 124)
                                .addComponent(branchLabel5))
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 207, Short.MAX_VALUE)
                        .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(branchesPanelLayout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(branchLabel6)
                                .addGap(120, 120, 120)
                                .addComponent(branchLabel7)))
                        .addGap(58, 58, 58))
                    .addGroup(branchesPanelLayout.createSequentialGroup()
                        .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(branchIDField)
                            .addComponent(findBranchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(69, 69, 69)
                        .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(branchNameField)
                            .addComponent(modifyBranchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addGap(66, 66, 66)
                        .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(branchesPanelLayout.createSequentialGroup()
                                .addComponent(createBranchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(deleteBranchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(branchIdCombo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(createBranchButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(88, 88, 88)
                        .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(courseIdCombo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteBranchButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(141, 141, 141))))
        );
        branchesPanelLayout.setVerticalGroup(
            branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(branchesPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(branchLabel1)
                    .addComponent(branchLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(34, 34, 34)
                .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(branchLabel3)
                    .addComponent(branchLabel4)
                    .addComponent(branchLabel5)
                    .addComponent(branchLabel6)
                    .addComponent(branchLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(branchesPanelLayout.createSequentialGroup()
                        .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(courseIdCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(branchIdCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(branchIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(branchNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(findBranchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(modifyBranchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(branchesPanelLayout.createSequentialGroup()
                        .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(branchesPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(createBranchButton)
                                    .addComponent(deleteBranchButton)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, branchesPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(createBranchButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(deleteBranchButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Branches", branchesPanel);

        gradeBookLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        gradeBookLabel1.setText("Academic Progress");

        gradeBookLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        gradeBookLabel2.setText("Assignment Grades");

        gradesTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane15.setViewportView(gradesTable);

        assignmentGradesTabel.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane16.setViewportView(assignmentGradesTabel);

        gradeBookLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gradeBookLabel3.setText("Studentd ID");

        gradeBookLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gradeBookLabel4.setText("Subject ID");

        gradeBookLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gradeBookLabel5.setText("Grade");

        gradeBookLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gradeBookLabel6.setText("Exam Grade");

        gradeBookLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gradeBookLabel7.setText("Assignment ID");

        gradeBookLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gradeBookLabel8.setText("Student ID");

        gradeBookLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gradeBookLabel9.setText("Grade");

        gradesFindButton.setText("Find By ID");
        gradesFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradesFindButtonActionPerformed(evt);
            }
        });

        gradesModifyButton.setText("Modify");
        gradesModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradesModifyButtonActionPerformed(evt);
            }
        });

        gradesCreateButton.setText("Create");
        gradesCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradesCreateButtonActionPerformed(evt);
            }
        });

        gradesDeleteButton.setText("Delete");
        gradesDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradesDeleteButtonActionPerformed(evt);
            }
        });

        assignmentGradesFindButton.setText("Find By ID");
        assignmentGradesFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentGradesFindButtonActionPerformed(evt);
            }
        });

        assignmentGradesModifybutton.setText("Modify");
        assignmentGradesModifybutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentGradesModifybuttonActionPerformed(evt);
            }
        });

        assignmentGradesCreateButton.setText("Create");
        assignmentGradesCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentGradesCreateButtonActionPerformed(evt);
            }
        });

        assignmentGradesDeleteButton.setText("Delete");
        assignmentGradesDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentGradesDeleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gradesPanelLayout = new javax.swing.GroupLayout(gradesPanel);
        gradesPanel.setLayout(gradesPanelLayout);
        gradesPanelLayout.setHorizontalGroup(
            gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gradesPanelLayout.createSequentialGroup()
                .addGap(237, 237, 237)
                .addComponent(gradeBookLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(gradeBookLabel2)
                .addGap(239, 239, 239))
            .addGroup(gradesPanelLayout.createSequentialGroup()
                .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gradesPanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gradesPanelLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gradesPanelLayout.createSequentialGroup()
                                .addComponent(gradeBookLabel3)
                                .addGap(75, 75, 75)
                                .addComponent(gradeBookLabel4)
                                .addGap(68, 68, 68)
                                .addComponent(gradeBookLabel5)
                                .addGap(37, 37, 37))
                            .addGroup(gradesPanelLayout.createSequentialGroup()
                                .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(gradesFindButton, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                    .addComponent(studentCombo1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(36, 36, 36)
                                .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(subjectCombo1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(gradesModifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                                .addGap(26, 26, 26)
                                .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(gradeField1)
                                    .addComponent(gradesCreateButton, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                                .addGap(17, 17, 17)))
                        .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(gradesDeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gradeBookLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(examGradeField1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(202, 202, 202)
                        .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(gradeBookLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(assignmentCombo2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(assignmentGradesFindButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(gradesPanelLayout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(gradeBookLabel8)
                                .addGap(101, 101, 101)
                                .addComponent(gradeBookLabel9))
                            .addGroup(gradesPanelLayout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(studentCombo2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(assignmentGradesCreateButton, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                                    .addComponent(assignmentGradesDeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(63, 63, 63)
                                .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(gradeField2)
                                    .addComponent(assignmentGradesModifybutton, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))))))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        gradesPanelLayout.setVerticalGroup(
            gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gradesPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gradeBookLabel1)
                    .addComponent(gradeBookLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gradeBookLabel3)
                    .addComponent(gradeBookLabel4)
                    .addComponent(gradeBookLabel5)
                    .addComponent(gradeBookLabel6)
                    .addComponent(gradeBookLabel7)
                    .addComponent(gradeBookLabel8)
                    .addComponent(gradeBookLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(studentCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subjectCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gradeField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(examGradeField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assignmentCombo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentCombo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gradeField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(assignmentGradesModifybutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(assignmentGradesFindButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gradesFindButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gradesModifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(gradesPanelLayout.createSequentialGroup()
                        .addComponent(assignmentGradesCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(assignmentGradesDeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                    .addComponent(gradesDeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gradesCreateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Grade Book", gradesPanel);

        assignmentLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        assignmentLabel4.setText("Assignments");

        assignmentsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane17.setViewportView(assignmentsTable);

        assignmentLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        assignmentLabel1.setText("Course ID");

        assignmentLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        assignmentLabel2.setText("Assignment ID");

        assignmentLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        assignmentLabel3.setText("Subject ID");

        assignmentLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        assignmentLabel5.setText("Visible");

        assignmentLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        assignmentLabel6.setText("Due Date");

        assignmentVisibleCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "yes", "no" }));

        assignmentCreateButton.setText("Create");
        assignmentCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentCreateButtonActionPerformed(evt);
            }
        });

        assignmentDeleteButton.setText("Delete");
        assignmentDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentDeleteButtonActionPerformed(evt);
            }
        });

        assignmentModifyButton.setText("Modify");
        assignmentModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentModifyButtonActionPerformed(evt);
            }
        });

        assignmentFindButton.setText("Find By Assignment ID");
        assignmentFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentFindButtonActionPerformed(evt);
            }
        });

        assignmentRefreshButton.setText("Refresh Table");
        assignmentRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentRefreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout assignmentPanelLayout = new javax.swing.GroupLayout(assignmentPanel);
        assignmentPanel.setLayout(assignmentPanelLayout);
        assignmentPanelLayout.setHorizontalGroup(
            assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assignmentPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(assignmentLabel4)
                .addGap(340, 340, 340))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assignmentPanelLayout.createSequentialGroup()
                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(assignmentPanelLayout.createSequentialGroup()
                                .addComponent(assignmentLabel5)
                                .addGap(113, 113, 113)
                                .addComponent(assignmentLabel6))
                            .addGroup(assignmentPanelLayout.createSequentialGroup()
                                .addComponent(assignmentVisibleCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69)
                                .addComponent(assignmentDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(assignmentPanelLayout.createSequentialGroup()
                                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assignmentPanelLayout.createSequentialGroup()
                                            .addComponent(assignmentLabel1)
                                            .addGap(70, 70, 70))
                                        .addGroup(assignmentPanelLayout.createSequentialGroup()
                                            .addComponent(assignmentCourseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(59, 59, 59)))
                                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                                        .addComponent(assignmentCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(assignmentField)
                                            .addComponent(assignmentLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(assignmentPanelLayout.createSequentialGroup()
                                                .addGap(86, 86, 86)
                                                .addComponent(assignmentLabel3))
                                            .addGroup(assignmentPanelLayout.createSequentialGroup()
                                                .addGap(69, 69, 69)
                                                .addComponent(assignmentSubjectCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                                        .addComponent(assignmentDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                                        .addComponent(assignmentModifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(assignmentPanelLayout.createSequentialGroup()
                                .addComponent(assignmentFindButton, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(assignmentRefreshButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(67, 67, 67)))
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        assignmentPanelLayout.setVerticalGroup(
            assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assignmentPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(assignmentLabel4)
                .addGap(18, 18, 18)
                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(assignmentLabel1)
                            .addComponent(assignmentLabel2)
                            .addComponent(assignmentLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(assignmentCourseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(assignmentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(assignmentSubjectCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(assignmentLabel5)
                            .addComponent(assignmentLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(assignmentVisibleCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(assignmentDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71)
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(assignmentModifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(assignmentDeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(assignmentCreateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(39, 39, 39)
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(assignmentFindButton, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                            .addComponent(assignmentRefreshButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Assignments", assignmentPanel);

        facultyLablel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        facultyLablel1.setText("Faculty Members");

        facultyLablel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        facultyLablel2.setText("Subjects");

        facultyMembersTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane18.setViewportView(facultyMembersTable);

        subjectsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane19.setViewportView(subjectsTable);

        facultyLablel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        facultyLablel3.setText("Faculty ID");

        facultyLablel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        facultyLablel4.setText("First Name");

        facultyLablel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        facultyLablel5.setText("Last Name");

        facultyLablel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        facultyLablel6.setText("Subject ID");

        facultyLablel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        facultyLablel7.setText("Lecturer ID");

        facultyLablel8.setText("Subject Name");

        subjectsFindButton.setText("Find By ID");
        subjectsFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectsFindButtonActionPerformed(evt);
            }
        });

        subjectsCreateButton.setText("Create");
        subjectsCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectsCreateButtonActionPerformed(evt);
            }
        });

        subjectsDeleteButton.setText("Delete");
        subjectsDeleteButton.setToolTipText("");
        subjectsDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectsDeleteButtonActionPerformed(evt);
            }
        });

        subjectsModifyButton.setText("Modify");
        subjectsModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectsModifyButtonActionPerformed(evt);
            }
        });

        facultyMembersFindButton.setText("Find By ID");
        facultyMembersFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyMembersFindButtonActionPerformed(evt);
            }
        });

        facultyMembersCreateButton.setText("Create");
        facultyMembersCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyMembersCreateButtonActionPerformed(evt);
            }
        });

        facultyMembersModifyButton.setText("Modify");
        facultyMembersModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyMembersModifyButtonActionPerformed(evt);
            }
        });

        facultyMembersDeleteButton.setText("Delete");
        facultyMembersDeleteButton.setToolTipText("");
        facultyMembersDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyMembersDeleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout facultyPanelLayout = new javax.swing.GroupLayout(facultyPanel);
        facultyPanel.setLayout(facultyPanelLayout);
        facultyPanelLayout.setHorizontalGroup(
            facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(facultyPanelLayout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addComponent(facultyLablel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(facultyLablel2)
                .addGap(328, 328, 328))
            .addGroup(facultyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(jScrollPane19)
                .addContainerGap())
            .addGroup(facultyPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(facultyIdField, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                    .addComponent(facultyMembersFindButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addGroup(facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(facultyFnameField, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(facultyMembersCreateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(facultyMembersDeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(61, 61, 61)
                .addGroup(facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(facultyLnameField, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(facultyMembersModifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(facultyPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(facultySBIDfield, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)
                        .addComponent(facultyFIDCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(305, 305, 305))
                    .addGroup(facultyPanelLayout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(subjectsFindButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(subjectsCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(subjectsDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(subjectsModifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(facultyPanelLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(facultyLablel3)
                .addGap(79, 79, 79)
                .addComponent(facultyLablel4)
                .addGap(90, 90, 90)
                .addComponent(facultyLablel5)
                .addGap(264, 264, 264)
                .addComponent(facultyLablel6)
                .addGap(127, 127, 127)
                .addComponent(facultyLablel7)
                .addGroup(facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(facultyPanelLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(facultySubjectField, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(facultyPanelLayout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(facultyLablel8)))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        facultyPanelLayout.setVerticalGroup(
            facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(facultyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(facultyLablel1)
                    .addComponent(facultyLablel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(facultyLablel3)
                    .addComponent(facultyLablel4)
                    .addComponent(facultyLablel5)
                    .addComponent(facultyLablel6)
                    .addComponent(facultyLablel7)
                    .addComponent(facultyLablel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(facultyIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(facultyFnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(facultyLnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(facultyFIDCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(facultySBIDfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(facultySubjectField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(facultyMembersFindButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subjectsModifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(subjectsDeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subjectsCreateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subjectsFindButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(facultyPanelLayout.createSequentialGroup()
                        .addComponent(facultyMembersCreateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(facultyMembersDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(facultyMembersModifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Faculty", facultyPanel);

        loginLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        loginLabel3.setText("Accounts");

        loginTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane20.setViewportView(loginTable);

        loginNOTEarea.setEditable(false);
        loginNOTEarea.setColumns(20);
        loginNOTEarea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        loginNOTEarea.setRows(5);
        loginNOTEarea.setText("NOTE:\n\nDefault password : panadol. Username: Faculty ID \\ Student ID \\ Admin ID\n\nYou are NOT allowed to see CUSTOM password. \n\nBefore reseting password you MUST notify account holder(s).");
        jScrollPane21.setViewportView(loginNOTEarea);

        loginLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        loginLabel1.setText("Username");

        loginLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        loginLabel2.setText("Role");

        loginRoleCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "student", "faculty", "admin" }));

        loginCreateButton.setText("Create User");
        loginCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginCreateButtonActionPerformed(evt);
            }
        });

        loginModifyButton.setText("Set Role");
        loginModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginModifyButtonActionPerformed(evt);
            }
        });

        loginDeleteButton.setText("Delete User");
        loginDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginDeleteButtonActionPerformed(evt);
            }
        });

        loginResetButton.setText("Reset Password");
        loginResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginResetButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(loginPanelLayout.createSequentialGroup()
                                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(loginCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(loginDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(113, 113, 113)
                                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(loginModifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                    .addComponent(loginResetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(loginPanelLayout.createSequentialGroup()
                                .addComponent(loginUsernameFiled, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(113, 113, 113)
                                .addComponent(loginRoleCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(167, 167, 167))
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(loginPanelLayout.createSequentialGroup()
                                .addGap(152, 152, 152)
                                .addComponent(loginLabel1)
                                .addGap(197, 197, 197)
                                .addComponent(loginLabel2))
                            .addGroup(loginPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)))
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginPanelLayout.createSequentialGroup()
                        .addComponent(loginLabel3)
                        .addGap(305, 305, 305))))
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(loginLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(loginLabel1)
                            .addComponent(loginLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(loginUsernameFiled, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(loginRoleCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(loginCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(loginModifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(loginDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(loginResetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))
                .addContainerGap())
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
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

    private void coursesFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesFindButtonActionPerformed
        findCourse();
    }//GEN-LAST:event_coursesFindButtonActionPerformed

    private void coursesCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesCreateButtonActionPerformed
        createCourse();
        updateCoursesTabel(selectQuery(coursesSelect));
    }//GEN-LAST:event_coursesCreateButtonActionPerformed

    private void coursesDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesDeleteButtonActionPerformed
        deleteCourse();
        updateCoursesTabel(selectQuery(coursesSelect));
    }//GEN-LAST:event_coursesDeleteButtonActionPerformed

    private void coursesModifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesModifyButtonActionPerformed
        modifyCourse();
        updateCoursesTabel(selectQuery(coursesSelect));
    }//GEN-LAST:event_coursesModifyButtonActionPerformed

    private void paymentsFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentsFindButtonActionPerformed
        findPayment();
    }//GEN-LAST:event_paymentsFindButtonActionPerformed

    private void paymentsCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentsCreateButtonActionPerformed
        createPayment();
        updatePaymentsTabel(selectQuery(coursesSelect2));
    }//GEN-LAST:event_paymentsCreateButtonActionPerformed

    private void paymentsDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentsDeleteButtonActionPerformed
        deletePayment();
        updatePaymentsTabel(selectQuery(coursesSelect2));
    }//GEN-LAST:event_paymentsDeleteButtonActionPerformed

    private void paymentsModifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentsModifyButtonActionPerformed
        modifyPayment();
        updatePaymentsTabel(selectQuery(coursesSelect2));
    }//GEN-LAST:event_paymentsModifyButtonActionPerformed

    private void findBranchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findBranchButtonActionPerformed
       branchDetailsFind();
    }//GEN-LAST:event_findBranchButtonActionPerformed

    private void modifyBranchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyBranchButtonActionPerformed
       branchDetailsModify();
       updateBranchDetailsTabel(selectQuery(branchSelect));
    }//GEN-LAST:event_modifyBranchButtonActionPerformed

    private void createBranchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBranchButtonActionPerformed
        branchDetailsCreate();
        updateBranchDetailsTabel(selectQuery(branchSelect));
        branchFill();
    }//GEN-LAST:event_createBranchButtonActionPerformed

    private void deleteBranchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBranchButtonActionPerformed
        branchDetailsDelete();
        updateBranchDetailsTabel(selectQuery(branchSelect));
        branchFill();
    }//GEN-LAST:event_deleteBranchButtonActionPerformed

    private void createBranchButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBranchButton2ActionPerformed
        branchesCreate();
        updateBranchTabel(selectQuery(branchSelect2));
    }//GEN-LAST:event_createBranchButton2ActionPerformed

    private void deleteBranchButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBranchButton2ActionPerformed
        branchesDelete();
        updateBranchTabel(selectQuery(branchSelect2));
    }//GEN-LAST:event_deleteBranchButton2ActionPerformed

    private void gradesFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradesFindButtonActionPerformed
        gradesFind();
    }//GEN-LAST:event_gradesFindButtonActionPerformed

    private void gradesModifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradesModifyButtonActionPerformed
        gradesModify();
        updateGradesTable(selectQuery(gradesSelect));
    }//GEN-LAST:event_gradesModifyButtonActionPerformed

    private void gradesCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradesCreateButtonActionPerformed
        gradesCreate();
        updateGradesTable(selectQuery(gradesSelect));
    }//GEN-LAST:event_gradesCreateButtonActionPerformed

    private void gradesDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradesDeleteButtonActionPerformed
        gradesDelete();
        updateGradesTable(selectQuery(gradesSelect));
    }//GEN-LAST:event_gradesDeleteButtonActionPerformed

    private void assignmentGradesFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentGradesFindButtonActionPerformed
       assignmentGradesFind();
    }//GEN-LAST:event_assignmentGradesFindButtonActionPerformed

    private void assignmentGradesCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentGradesCreateButtonActionPerformed
        assignmentGradesCreate();
        updateAssignmentGradesTable(selectQuery(gradesSelect2));
    }//GEN-LAST:event_assignmentGradesCreateButtonActionPerformed

    private void assignmentGradesDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentGradesDeleteButtonActionPerformed
        assignmentGradesDelete();
        updateAssignmentGradesTable(selectQuery(gradesSelect2));
    }//GEN-LAST:event_assignmentGradesDeleteButtonActionPerformed

    private void assignmentGradesModifybuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentGradesModifybuttonActionPerformed
        assignmentGradesModify();
        updateAssignmentGradesTable(selectQuery(gradesSelect2));
    }//GEN-LAST:event_assignmentGradesModifybuttonActionPerformed

    private void assignmentCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentCreateButtonActionPerformed
        assignmentCreate();
        updateAssignmentsTable(selectQuery(assignmentSelect));
    }//GEN-LAST:event_assignmentCreateButtonActionPerformed

    private void assignmentDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentDeleteButtonActionPerformed
        assignmentDelete();
        updateAssignmentsTable(selectQuery(assignmentSelect));
    }//GEN-LAST:event_assignmentDeleteButtonActionPerformed

    private void assignmentModifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentModifyButtonActionPerformed
        assignmentModify();
        updateAssignmentsTable(selectQuery(assignmentSelect));
    }//GEN-LAST:event_assignmentModifyButtonActionPerformed

    private void assignmentFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentFindButtonActionPerformed
        assignmentFind();
    }//GEN-LAST:event_assignmentFindButtonActionPerformed

    private void assignmentRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentRefreshButtonActionPerformed
        updateAssignmentsTable(selectQuery(assignmentSelect));
    }//GEN-LAST:event_assignmentRefreshButtonActionPerformed

    private void facultyMembersFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyMembersFindButtonActionPerformed
        facultyMembersFind();
    }//GEN-LAST:event_facultyMembersFindButtonActionPerformed

    private void facultyMembersCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyMembersCreateButtonActionPerformed
        facultyMembersCreate();
        updateFacultyMambersTable(selectQuery(facultyMembersSelect));
        facultyFill();
    }//GEN-LAST:event_facultyMembersCreateButtonActionPerformed

    private void facultyMembersDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyMembersDeleteButtonActionPerformed
        facultyMembersDelete();
        updateFacultyMambersTable(selectQuery(facultyMembersSelect));
        facultyFill();
    }//GEN-LAST:event_facultyMembersDeleteButtonActionPerformed

    private void facultyMembersModifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyMembersModifyButtonActionPerformed
        facultyMembersModify();
        updateFacultyMambersTable(selectQuery(facultyMembersSelect));
    }//GEN-LAST:event_facultyMembersModifyButtonActionPerformed

    private void subjectsFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectsFindButtonActionPerformed
        subjectsFind();
    }//GEN-LAST:event_subjectsFindButtonActionPerformed

    private void subjectsCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectsCreateButtonActionPerformed
        subjectsCreate();
        updateSubjectsTable(selectQuery(subjectsSelect));
    }//GEN-LAST:event_subjectsCreateButtonActionPerformed

    private void subjectsDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectsDeleteButtonActionPerformed
        subjectsDelete();
        updateSubjectsTable(selectQuery(subjectsSelect));
    }//GEN-LAST:event_subjectsDeleteButtonActionPerformed

    private void subjectsModifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectsModifyButtonActionPerformed
        subjectsModify();
        updateSubjectsTable(selectQuery(subjectsSelect));
    }//GEN-LAST:event_subjectsModifyButtonActionPerformed

    private void loginCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginCreateButtonActionPerformed
        loginCreate();
        updateLoginTable(selectQuery(loginSelect));
    }//GEN-LAST:event_loginCreateButtonActionPerformed

    private void loginModifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginModifyButtonActionPerformed
        loginModify();
        updateLoginTable(selectQuery(loginSelect));
    }//GEN-LAST:event_loginModifyButtonActionPerformed

    private void loginDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginDeleteButtonActionPerformed
        loginDelete();
        updateLoginTable(selectQuery(loginSelect));
    }//GEN-LAST:event_loginDeleteButtonActionPerformed

    private void loginResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginResetButtonActionPerformed
        loginReset();
        updateLoginTable(selectQuery(loginSelect));
    }//GEN-LAST:event_loginResetButtonActionPerformed

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
    private javax.swing.JButton TimetableModifyButton;
    private javax.swing.JTextArea addressArea;
    private javax.swing.JButton applyButton1;
    private javax.swing.JButton applyButton2;
    private javax.swing.JComboBox<String> assignmentCombo2;
    private javax.swing.JComboBox<String> assignmentCourseCombo;
    private javax.swing.JButton assignmentCreateButton;
    private com.toedter.calendar.JDateChooser assignmentDatePicker;
    private javax.swing.JButton assignmentDeleteButton;
    private javax.swing.JTextField assignmentField;
    private javax.swing.JButton assignmentFindButton;
    private javax.swing.JButton assignmentGradesCreateButton;
    private javax.swing.JButton assignmentGradesDeleteButton;
    private javax.swing.JButton assignmentGradesFindButton;
    private javax.swing.JButton assignmentGradesModifybutton;
    private javax.swing.JTable assignmentGradesTabel;
    private javax.swing.JLabel assignmentLabel1;
    private javax.swing.JLabel assignmentLabel2;
    private javax.swing.JLabel assignmentLabel3;
    private javax.swing.JLabel assignmentLabel4;
    private javax.swing.JLabel assignmentLabel5;
    private javax.swing.JLabel assignmentLabel6;
    private javax.swing.JButton assignmentModifyButton;
    private javax.swing.JPanel assignmentPanel;
    private javax.swing.JButton assignmentRefreshButton;
    private javax.swing.JComboBox<String> assignmentSubjectCombo;
    private javax.swing.JComboBox<String> assignmentVisibleCombo;
    private javax.swing.JTable assignmentsTable;
    private javax.swing.JTextArea branchAddressArea;
    private javax.swing.JTable branchDetailsTable;
    private javax.swing.JTextField branchIDField;
    private javax.swing.JComboBox<String> branchIdCombo;
    private javax.swing.JLabel branchLabel1;
    private javax.swing.JLabel branchLabel2;
    private javax.swing.JLabel branchLabel3;
    private javax.swing.JLabel branchLabel4;
    private javax.swing.JLabel branchLabel5;
    private javax.swing.JLabel branchLabel6;
    private javax.swing.JLabel branchLabel7;
    private javax.swing.JTextField branchNameField;
    private javax.swing.JTable branchTable;
    private javax.swing.JPanel branchesPanel;
    private javax.swing.JTextArea courseArea;
    private javax.swing.JTextField courseField;
    private javax.swing.JComboBox<String> courseIdCombo;
    private javax.swing.JTextField courseIdField;
    private javax.swing.JTextField coursePriceField;
    private javax.swing.JButton coursesCreateButton;
    private javax.swing.JButton coursesDeleteButton;
    private javax.swing.JButton coursesFindButton;
    private javax.swing.JLabel coursesLAbel1;
    private javax.swing.JLabel coursesLabel2;
    private javax.swing.JLabel coursesLabel4;
    private javax.swing.JLabel coursesLabel5;
    private javax.swing.JLabel coursesLabel6;
    private javax.swing.JLabel coursesLabel8;
    private javax.swing.JLabel coursesLablel7;
    private javax.swing.JButton coursesModifyButton;
    private javax.swing.JTextField coursesPAyedField;
    private javax.swing.JPanel coursesPanel;
    private javax.swing.JTextField coursesRequiredField;
    private javax.swing.JTextField coursesStidentIdField;
    private javax.swing.JTable coursesTable;
    private javax.swing.JLabel coursesesLabel3;
    private javax.swing.JButton createBranchButton;
    private javax.swing.JButton createBranchButton2;
    private javax.swing.JButton createButton1;
    private javax.swing.JButton createButton2;
    private javax.swing.JButton deleteBranchButton;
    private javax.swing.JButton deleteBranchButton2;
    private javax.swing.JButton deleteButton1;
    private javax.swing.JButton deleteButton2;
    private javax.swing.JButton directExecuteButton;
    private javax.swing.JButton directExecuteUpdateButton;
    private javax.swing.JLabel directInputLabel;
    private javax.swing.JLabel directOutputLabel;
    private javax.swing.JTable directTable;
    private javax.swing.JTextArea directTextArea;
    private javax.swing.JTextField examGradeField1;
    private javax.swing.JComboBox<String> facultyFIDCombo;
    private javax.swing.JTextField facultyFnameField;
    private javax.swing.JTextField facultyIdField;
    private javax.swing.JLabel facultyLablel1;
    private javax.swing.JLabel facultyLablel2;
    private javax.swing.JLabel facultyLablel3;
    private javax.swing.JLabel facultyLablel4;
    private javax.swing.JLabel facultyLablel5;
    private javax.swing.JLabel facultyLablel6;
    private javax.swing.JLabel facultyLablel7;
    private javax.swing.JLabel facultyLablel8;
    private javax.swing.JTextField facultyLnameField;
    private javax.swing.JButton facultyMembersCreateButton;
    private javax.swing.JButton facultyMembersDeleteButton;
    private javax.swing.JButton facultyMembersFindButton;
    private javax.swing.JButton facultyMembersModifyButton;
    private javax.swing.JTable facultyMembersTable;
    private javax.swing.JPanel facultyPanel;
    private javax.swing.JTextField facultySBIDfield;
    private javax.swing.JTextField facultySubjectField;
    private javax.swing.JButton findBranchButton;
    private javax.swing.JButton findButton1;
    private javax.swing.JButton findButton2;
    private javax.swing.JLabel gradeBookLabel1;
    private javax.swing.JLabel gradeBookLabel2;
    private javax.swing.JLabel gradeBookLabel3;
    private javax.swing.JLabel gradeBookLabel4;
    private javax.swing.JLabel gradeBookLabel5;
    private javax.swing.JLabel gradeBookLabel6;
    private javax.swing.JLabel gradeBookLabel7;
    private javax.swing.JLabel gradeBookLabel8;
    private javax.swing.JLabel gradeBookLabel9;
    private javax.swing.JTextField gradeField1;
    private javax.swing.JTextField gradeField2;
    private javax.swing.JButton gradesCreateButton;
    private javax.swing.JButton gradesDeleteButton;
    private javax.swing.JButton gradesFindButton;
    private javax.swing.JButton gradesModifyButton;
    private javax.swing.JPanel gradesPanel;
    private javax.swing.JTable gradesTable;
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
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField lessonsField;
    private javax.swing.JTextField lessonsField2;
    private javax.swing.JButton loginCreateButton;
    private javax.swing.JButton loginDeleteButton;
    private javax.swing.JLabel loginLabel1;
    private javax.swing.JLabel loginLabel2;
    private javax.swing.JLabel loginLabel3;
    private javax.swing.JButton loginModifyButton;
    private javax.swing.JTextArea loginNOTEarea;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JButton loginResetButton;
    private javax.swing.JComboBox<String> loginRoleCombo;
    private javax.swing.JTable loginTable;
    private javax.swing.JTextField loginUsernameFiled;
    private javax.swing.JButton modifyBranchButton;
    private javax.swing.JButton paymentsCreateButton;
    private javax.swing.JButton paymentsDeleteButton;
    private javax.swing.JButton paymentsFindButton;
    private javax.swing.JButton paymentsModifyButton;
    private javax.swing.JTable paymentsTable;
    private javax.swing.JTextField phoneField;
    private javax.swing.JTextField sidField1;
    private javax.swing.JTextField sidField2;
    private javax.swing.JTable studentAttendanceTable;
    private javax.swing.JComboBox<String> studentCombo1;
    private javax.swing.JComboBox<String> studentCombo2;
    private javax.swing.JPanel studentPanel;
    private javax.swing.JTable studentStudentTable;
    private javax.swing.JComboBox<String> subjectCombo1;
    private javax.swing.JButton subjectsCreateButton;
    private javax.swing.JButton subjectsDeleteButton;
    private javax.swing.JButton subjectsFindButton;
    private javax.swing.JButton subjectsModifyButton;
    private javax.swing.JTable subjectsTable;
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

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
import javax.swing.JOptionPane;

/**
 *
 * @author Maksym Vavilov 16856
 */
public abstract class adminDatabase extends facultyDatabase{
    
    protected String directQuery;
    
    protected String selectStudentQuery = "SELECT student.Sid AS 'Student ID',\n" +
                                        "	student.Fname AS 'First Name',\n" +
                                        "        student.Lname AS 'Last Name',\n" +
                                        "        student.Course,\n" +
                                        "        student.Phone,\n" +
                                        "        student.Address\n" +
                                        "FROM student;";
    
    protected String selectAttendanceQuery = "SELECT attendance.Sid AS 'Student ID',\n" +
                                            "		attendance.Lessons,\n" +
                                            "		attendance.Attended\n" +
                                            "FROM attendance;";
    
    protected String findStudentQuery = "SELECT student.Sid AS 'Student ID',\n" +
                                        "		student.Fname AS 'First Name',\n" +
                                        "        student.Lname AS 'Last Name',\n" +
                                        "        student.Course,\n" +
                                        "        student.Phone,\n" +
                                        "        student.Address\n" +
                                        "FROM student\n" +
                                        "WHERE student.Sid = ?;";
    
    protected String deleteStudentQuery = "DELETE FROM `programming_db`.`student` WHERE `Sid`=?;";
    
    protected String createStudentQuery = "INSERT INTO `programming_db`.`student` (`Sid`, `Fname`, `Lname`, `Course`, `Phone`, `Address`) "
                                            + "VALUES (?, ?, ?, ?, ?, ?);";
    
    protected String modifyStudentQuery = "UPDATE `programming_db`.`student` SET "
                                        + "`Fname`=?, `Lname`=?,"
                                        + " `Course`=?, `Phone`=?, "
                                        + "`Address`=?"
                                        + " WHERE `Sid`=?;";
    
    protected String findAttendance = "SELECT attendance.Sid AS 'Student ID',\n" +
                                        "		attendance.Lessons,\n" +
                                        "		attendance.Attended\n" +
                                        "FROM attendance\n" +
                                        "WHERE attendance.Sid = ?;";
    
    protected String createAttendance = "INSERT INTO `programming_db`.`attendance` (`Sid`, `Lessons`, `Attended`) "
                                        + "VALUES (?, ?, ?);";
    
    protected String deleteAttendance = "DELETE FROM `programming_db`.`attendance` WHERE `Sid`=?;";
    
    protected String modifyAttendance = "UPDATE `programming_db`.`attendance` "
                                        + "SET `Lessons`=?, `Attended`=? "
                                        + "WHERE `Sid`=?;";
    
    protected String selectGroupDetails = "SELECT group_details.Gid AS 'Group ID',\n" +
                                        "		group_details.Cid AS 'Course ID',\n" +
                                        "        group_details.Superviser AS 'Superviser ID',\n" +
                                        "        faculty_members.Fname AS 'Superviser Name',\n" +
                                        "        faculty_members.Lname AS 'Superviser Last Name'\n" +
                                        "FROM (group_details\n" +
                                        "	INNER JOIN faculty_members ON group_details.Superviser = faculty_members.Fid);";
    
    protected String selectGroups = "SELECT groups.Gid AS 'Group ID',\n" +
                                "		groups.Sid AS 'Student ID',\n" +
                                "        student.Fname AS 'Student First Name',\n" +
                                "        student.Lname AS 'Student Last Name'\n" +
                                "FROM (groups\n" +
                                "	INNER JOIN student ON groups.Sid = student.Sid);";
    
    protected String createGroupDetails = "INSERT INTO `programming_db`.`group_details` (`Gid`, `Cid`, `Superviser`) VALUES (?, ?, ?);";
    
    protected String deleteGroupDetails = "DELETE FROM `programming_db`.`group_details` WHERE `Gid`=? and`Cid`=?;";
    
    protected String modifyGroupDetails = "UPDATE `programming_db`.`group_details` SET `Superviser`=? WHERE `Gid`=? and`Cid`=?;";
    
    protected String createGroups = "INSERT INTO `programming_db`.`groups` (`Sid`, `Gid`) VALUES (?, ?);";
    
    protected String deleteGroups = "DELETE FROM `programming_db`.`groups` WHERE `Sid`=? and`Gid`=?;";
    
    protected String selectTimetable = "SELECT timetable.Gid AS 'Group ID',\n" +
                                    "		timetable.Day,\n" +
                                    "        timetable.Subject,\n" +
                                    "        timetable.Start,\n" +
                                    "        timetable.End\n" +
                                    "FROM timetable;";
    
    protected String timetableFill = "SELECT group_details.Gid\n" +
                                    "FROM group_details;";
    
    protected String timetableFill2 = "SELECT subjects.Subject\n" +
                                    "FROM subjects;";
    
    protected String timetableCreate = "INSERT INTO `programming_db`.`timetable` "
                                + "(`Gid`, `Day`, `Subject`, `Start`, `End`) VALUES "
                                + "(?, ?, ?, ?, ?);";
    
    protected String timetableModify = "UPDATE `programming_db`.`timetable` SET "
                                + "`Subject`=?, `Start`=?, `End`=? WHERE "
                                + "`Gid`=? and`Day`=?;";
    
    protected String timetableDelete = "DELETE FROM `programming_db`.`timetable` WHERE "
                                + "`Gid`=? and`Day`=?;";
    
    protected String coursesSelect = "SELECT courses.Cid AS 'Course ID',\n" +
                                "		courses.Course AS 'Description',\n" +
                                "        courses.Price\n" +
                                "FROM courses;";
    
    protected String coursesSelect2 = "SELECT student.Fname AS 'First Name',\n" +
                                "		student.Lname AS 'Last Name',\n" +
                                "        payments.Sid AS 'Student ID',\n" +
                                "        payments.Required,\n" +
                                "        payments.Payed\n" +
                                "FROM (payments\n" +
                                "	INNER JOIN student ON payments.Sid = student.Sid);";
    
    protected String courseFind = "SELECT courses.Cid AS 'Course ID',\n" +
                                "		courses.Course AS 'Description',\n" +
                                "        courses.Price\n" +
                                "FROM courses\n" +
                                "WHERE courses.Cid = ?;";
    
    protected String courseDelete = "DELETE FROM `programming_db`.`courses` WHERE `Cid`=?;";
    
    protected String courseCreate = "INSERT INTO `programming_db`.`courses` "
                                    + "(`Cid`, `Course`, `Price`) VALUES "
                                    + "(?, ?, ?);";
    
    protected String courseModify = "UPDATE `programming_db`.`courses` SET "
                                    + "`Course`=?, `Price`=? WHERE `Cid`=?;";
    
    protected String paymentFind ="SELECT  payments.Sid AS 'Student ID',\n" +
                                "        payments.Required,\n" +
                                "        payments.Payed\n" +
                                "FROM (payments\n" +
                                "	INNER JOIN student ON payments.Sid = student.Sid)\n" +
                                "WHERE student.Sid = ?;";
    
    protected String paymentCreate ="INSERT INTO `programming_db`.`payments` "
                                    + "(`Sid`, `Required`, `Payed`) VALUES "
                                    + "(?, ?, ?);";
    
    protected String paymentDelete = "DELETE FROM `programming_db`.`payments` WHERE `Sid`=?;";
    
    protected String paymentModify = "UPDATE `programming_db`.`payments` SET "
                                    + "`Required`=?, `Payed`=? WHERE "
                                    + "`Sid`=?;";
    
    protected String branchSelect = "SELECT branch_details.Bid AS 'Branch ID',\n" +
                                "		branch_details.Bname AS 'Branch Name',\n" +
                                "        branch_details.Baddress AS 'Address'\n" +
                                "FROM branch_details;";
    
    protected String branchSelect2 = "SELECT branches.Bid AS 'Branch ID',\n" +
                                "		branches.Courses AS 'Course ID',\n" +
                                "        courses.Course AS 'Course'\n" +
                                "FROM (branches\n" +
                                "	INNER JOIN courses ON branches.Courses = courses.Cid);";
    
    protected String branchFill = "SELECT branch_details.Bid\n" +
                                "FROM branch_details;";
    
    protected String branchFill2 = "SELECT courses.Cid\n" +
                                "FROM courses;";
    
    protected String branchFind = "SELECT branch_details.Bid AS 'Branch ID',\n" +
                                "		branch_details.Bname AS 'Branch Name',\n" +
                                "        branch_details.Baddress AS 'Address'\n" +
                                "FROM branch_details\n" +
                                "WHERE branch_details.Bid = ?;";
    
    protected String branchCreate = "INSERT INTO `programming_db`.`branch_details` "
                                    + "(`Bid`, `Bname`, `Baddress`) VALUES "
                                    + "(?, ?, ?);";
    
    protected String branchDelete = "DELETE FROM `programming_db`.`branch_details`"
                                    + " WHERE `Bid`=?;";

    protected String branchModify = "UPDATE `programming_db`.`branch_details` SET "
                                + "`Bname`=?, `Baddress`=? "
                                + "WHERE `Bid`=?;";

    protected String branchCreate2 = "INSERT INTO `programming_db`.`branches` "
                                    + "(`Bid`, `Courses`) VALUES "
                                    + "(?, ?);";

    protected String branchDelete2 = "DELETE FROM `programming_db`.`branches` WHERE "
                                    + "`Courses`=? and`Bid`=?;";
    
    protected String gradesSelect = "SELECT grades.Sid AS 'Student ID',\n" +
                                    "		student.Fname AS 'First Name',\n" +
                                    "        student.Lname AS 'Last Name',\n" +
                                    "        grades.SBid AS 'Subject ID',\n" +
                                    "        subjects.Subject AS 'Subject',\n" +
                                    "        grades.Grade,\n" +
                                    "        grades.ExamGrade AS 'Exam Grade'\n" +
                                    "FROM ((grades\n" +
                                    "	INNER JOIN student ON grades.Sid = student.Sid)\n" +
                                    "    INNER JOIN subjects ON grades.SBid = subjects.SBid);";
    
    
    protected String gradesSelect2 = "SELECT 	assignment_grades.Aid AS 'Assignment ID',\n" +
                                    "		assignment_grades.Sid AS 'Student ID',\n" +
                                    "		student.Fname AS 'First Name',\n" +
                                    "        student.Lname AS 'Last Name',\n" +
                                    "		assignment_grades.Grade\n" +
                                    "FROM (assignment_grades\n" +
                                    "	INNER JOIN student ON assignment_grades.Sid = student.Sid);";
    
    protected String getStudentID = "SELECT student.Sid\n" +
                                    "FROM student\n" +
                                    "ORDER BY student.Sid;";
    
    protected String getSubjectID = "SELECT subjects.SBid\n" +
                                    "FROM subjects;";

    protected String getAssignmentID = "SELECT assignments.Aid\n" +
                                        "FROM assignments;";
    
    protected String gradesFind = "SELECT grades.Grade,\n" +
                                "		grades.ExamGrade\n" +
                                "FROM grades\n" +
                                "WHERE grades.Sid = ? AND grades.SBid = ?";
    
    protected String gradesCreate = "INSERT INTO `programming_db`.`grades` "
                                    + "(`Sid`, `SBid`, `Grade`, `ExamGrade`) VALUES "
                                    + "(?, ?, ?, ?);";
    
    protected String gradesDelete = "DELETE FROM `programming_db`.`grades` WHERE "
                                + "`Sid`=? and`SBid`=?;";
    
    protected String  gradesModify = "UPDATE `programming_db`.`grades` SET "
                                + "`Grade`=?, `ExamGrade`=? WHERE "
                                + "`Sid`=? and`SBid`=?;";
    
    protected String assignmentGradesCreate = "INSERT INTO `programming_db`.`assignment_grades` "
                                            + "(`Aid`, `Sid`, `Grade`) VALUES "
                                            + "(?, ?, ?);";
    
    protected String assignmentGradesFind = "SELECT assignment_grades.Grade\n" +
                                            "FROM assignment_grades\n" +
                                            "WHERE assignment_grades.Aid = ? AND assignment_grades.Sid = ?;";
    
    protected String assignmentGradesDelete = "DELETE FROM `programming_db`.`assignment_grades` WHERE "
                                            + "`Aid`=? and`Sid`=?;";
    
    protected String assignmentGradesModify = "UPDATE `programming_db`.`assignment_grades` SET "
                                            + "`Grade`=? WHERE "
                                            + "`Aid`=? and`Sid`=?;";
    
    protected String assignmentSelect = "SELECT 	assignments.Cid AS 'Course ID',\n" +
                                    "		courses.Course,\n" +
                                    "        assignments.Aid,\n" +
                                    "        assignments.Subject AS 'Subject ID',\n" +
                                    "        subjects.Subject,\n" +
                                    "        assignments.DueDate AS 'Due Date',\n" +
                                    "        assignments.Visible\n" +
                                    "FROM ((assignments\n" +
                                    "	INNER JOIN courses ON assignments.Cid = courses.Cid)\n" +
                                    "    INNER JOIN subjects ON assignments.Subject = subjects.SBid);";
    
    protected String getCourseID = "SELECT courses.Cid\n" +
                                    "FROM courses;";
    
    protected String assignmentCreate = "INSERT INTO `programming_db`.`assignments` "
                                    + "(`Cid`, `Aid`, `Subject`, `Visible`, `DueDate`) VALUES "
                                    + "(?, ?, ?, ?, ?);";
    
    protected String assignmentDelete = "DELETE FROM `programming_db`.`assignments` WHERE "
                                    + "`Aid`=?;";
    
    protected String assignmentModify = "UPDATE `programming_db`.`assignments` SET "
                                    + "`Cid`=?, `Subject`=?, `Visible`=?, `DueDate`=? WHERE "
                                    + "`Aid`=?;";
    
    protected String assignmentFind = "SELECT 	assignments.Cid,\n" +
                                    "		assignments.Aid,\n" +
                                    "        assignments.Subject,\n" +
                                    "        assignments.Visible,\n" +
                                    "        assignments.DueDate\n" +
                                    "FROM assignments\n" +
                                    "WHERE assignments.Aid = ?;";
    
    protected String facultyMembersSelect = "SELECT faculty_members.Fid AS 'Faculty ID',\n" +
                                        "           faculty_members.Fname AS 'First Name',\n" +
                                        "           faculty_members.Lname AS 'Last Name'\n" +
                                        "FROM faculty_members;";
    
    protected String subjectsSelect = "SELECT  subjects.SBid AS 'Subject ID',\n" +
                                    "        subjects.Lecturer AS 'Lecturer ID',\n" +
                                    "        faculty_members.Fname AS 'Lecturer First Name',\n" +
                                    "        faculty_members.Lname AS 'Lecturer Last Name',\n" +
                                    "        subjects.Subject AS 'Subject Name'\n" +
                                    "FROM (subjects\n" +
                                    "	INNER JOIN faculty_members ON subjects.Lecturer = faculty_members.Fid);";
    
    protected String getFacultyID = "SELECT faculty_members.Fid\n" +
                                    "FROM faculty_members;";
    
    protected String facultyFindButton1 = "SELECT  faculty_members.Fid AS 'Faculty ID',\n" +
                                        "	 faculty_members.Fname AS 'First Name',\n" +
                                        "        faculty_members.Lname AS 'Last Name'\n" +
                                        "FROM faculty_members\n" +
                                        "WHERE faculty_members.Fid = ?;";
    
    protected String facultyCreateButton1 = "INSERT INTO `programming_db`.`faculty_members` "
                                            + "(`Fid`, `Fname`, `Lname`) VALUES "
                                            + "(?,?, ?);";
    
    protected String facultyDeleteButton1 = "DELETE FROM `programming_db`.`faculty_members` WHERE"
                                        + " `Fid`=?;";
    
    protected String facultyModifyButton1 = "UPDATE `programming_db`.`faculty_members` SET "
                                        + "`Fname`=?, `Lname`=? WHERE "
                                        + "`Fid`=?;";
    
    protected String subjectsFind = "SELECT * FROM subjects\n" +
                                    "WHERE subjects.SBid = ?;";
    
    protected String subjectsCreate = "INSERT INTO `programming_db`.`subjects` "
                                    + "(`SBid`, `Lecturer`, `Subject`) VALUES "
                                    + "(?, ?, ?);";
    
    protected String subjectsDelete = "DELETE FROM `programming_db`.`subjects` WHERE "
                                    + "`SBid`=?;";
    
    protected String subjectsModify = "UPDATE `programming_db`.`subjects` SET "
                                    + "`Lecturer`=?, `Subject`=? WHERE "
                                    + "`SBid`=?;";
    
    protected String loginSelect = "SELECT  login.Username,\n" +
                                "		login.Password,\n" +
                                "		login.Role\n" +
                                "FROM login;";
    
    protected String loginCreate = "INSERT INTO `programming_db`.`login` "
                                + "(`Username`, `Password`, `Role`) VALUES "
                                + "(?, 'panadol', ?);";
    
    protected String loginDelete = "DELETE FROM `programming_db`.`login` WHERE "
                                + "`Username`=?;";
    
    protected String loginModify = "UPDATE `programming_db`.`login` SET "
                                + "`Role`=? WHERE "
                                + "`Username`=?;";
    
    protected String loginReset = "UPDATE `programming_db`.`login` SET "
                                + "`Password`='panadol' WHERE "
                                + "`Username`=?;";
    
    public adminDatabase(){
    
    }
    
    protected final ResultSet selectQuery(String query){
        ResultSet resultSet = null;
        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(query);
            
            resultSet = PrepStatement.executeQuery();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        
        return resultSet;
    }
}


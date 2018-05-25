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
import java.util.Stack;
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
    
    protected String deleteStudentQuery = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`student` WHERE `Sid`=?;";
    
    protected String createStudentQuery = "INSERT INTO `maksym_vavilov_16856_provisional_project`.`student` (`Sid`, `Fname`, `Lname`, `Course`, `Phone`, `Address`) "
                                            + "VALUES (?, ?, ?, ?, ?, ?);";
    
    protected String modifyStudentQuery = "UPDATE `maksym_vavilov_16856_provisional_project`.`student` SET "
                                        + "`Fname`=?, `Lname`=?,"
                                        + " `Course`=?, `Phone`=?, "
                                        + "`Address`=?"
                                        + " WHERE `Sid`=?;";
    
    protected String findAttendance = "SELECT attendance.Sid AS 'Student ID',\n" +
                                        "		attendance.Lessons,\n" +
                                        "		attendance.Attended\n" +
                                        "FROM attendance\n" +
                                        "WHERE attendance.Sid = ?;";
    
    protected String createAttendance = "INSERT INTO `maksym_vavilov_16856_provisional_project`.`attendance` (`Sid`, `Lessons`, `Attended`) "
                                        + "VALUES (?, ?, ?);";
    
    protected String deleteAttendance = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`attendance` WHERE `Sid`=?;";
    
    protected String modifyAttendance = "UPDATE `maksym_vavilov_16856_provisional_project`.`attendance` "
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
    
    protected String createGroupDetails = "INSERT INTO `maksym_vavilov_16856_provisional_project`.`group_details` (`Gid`, `Cid`, `Superviser`) VALUES (?, ?, ?);";
    
    protected String deleteGroupDetails = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`group_details` WHERE `Gid`=? and`Cid`=?;";
    
    protected String modifyGroupDetails = "UPDATE `maksym_vavilov_16856_provisional_project`.`group_details` SET `Superviser`=? WHERE `Gid`=? and`Cid`=?;";
    
    protected String createGroups = "INSERT INTO `maksym_vavilov_16856_provisional_project`.`groups` (`Sid`, `Gid`) VALUES (?, ?);";
    
    protected String deleteGroups = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`groups` WHERE `Sid`=? and`Gid`=?;";
    
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
    
    protected String timetableCreate = "INSERT INTO `maksym_vavilov_16856_provisional_project`.`timetable` "
                                + "(`Gid`, `Day`, `Subject`, `Start`, `End`) VALUES "
                                + "(?, ?, ?, ?, ?);";
    
    protected String timetableModify = "UPDATE `maksym_vavilov_16856_provisional_project`.`timetable` SET "
                                + "`Subject`=?, `Start`=?, `End`=? WHERE "
                                + "`Gid`=? and`Day`=?;";
    
    protected String timetableDelete = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`timetable` WHERE "
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
    
    protected String courseDelete = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`courses` WHERE `Cid`=?;";
    
    protected String courseCreate = "INSERT INTO `maksym_vavilov_16856_provisional_project`.`courses` "
                                    + "(`Cid`, `Course`, `Price`) VALUES "
                                    + "(?, ?, ?);";
    
    protected String courseModify = "UPDATE `maksym_vavilov_16856_provisional_project`.`courses` SET "
                                    + "`Course`=?, `Price`=? WHERE `Cid`=?;";
    
    protected String paymentFind ="SELECT  payments.Sid AS 'Student ID',\n" +
                                "        payments.Required,\n" +
                                "        payments.Payed\n" +
                                "FROM (payments\n" +
                                "	INNER JOIN student ON payments.Sid = student.Sid)\n" +
                                "WHERE student.Sid = ?;";
    
    protected String paymentCreate ="INSERT INTO `maksym_vavilov_16856_provisional_project`.`payments` "
                                    + "(`Sid`, `Required`, `Payed`) VALUES "
                                    + "(?, ?, ?);";
    
    protected String paymentDelete = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`payments` WHERE `Sid`=?;";
    
    protected String paymentModify = "UPDATE `maksym_vavilov_16856_provisional_project`.`payments` SET "
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
    
    protected String branchCreate = "INSERT INTO `maksym_vavilov_16856_provisional_project`.`branch_details` "
                                    + "(`Bid`, `Bname`, `Baddress`) VALUES "
                                    + "(?, ?, ?);";
    
    protected String branchDelete = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`branch_details`"
                                    + " WHERE `Bid`=?;";

    protected String branchModify = "UPDATE `maksym_vavilov_16856_provisional_project`.`branch_details` SET "
                                + "`Bname`=?, `Baddress`=? "
                                + "WHERE `Bid`=?;";

    protected String branchCreate2 = "INSERT INTO `maksym_vavilov_16856_provisional_project`.`branches` "
                                    + "(`Bid`, `Courses`) VALUES "
                                    + "(?, ?);";

    protected String branchDelete2 = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`branches` WHERE "
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
    
    protected String gradesCreate = "INSERT INTO `maksym_vavilov_16856_provisional_project`.`grades` "
                                    + "(`Sid`, `SBid`, `Grade`, `ExamGrade`) VALUES "
                                    + "(?, ?, ?, ?);";
    
    protected String gradesDelete = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`grades` WHERE "
                                + "`Sid`=? and`SBid`=?;";
    
    protected String  gradesModify = "UPDATE `maksym_vavilov_16856_provisional_project`.`grades` SET "
                                + "`Grade`=?, `ExamGrade`=? WHERE "
                                + "`Sid`=? and`SBid`=?;";
    
    protected String assignmentGradesCreate = "INSERT INTO `maksym_vavilov_16856_provisional_project`.`assignment_grades` "
                                            + "(`Aid`, `Sid`, `Grade`) VALUES "
                                            + "(?, ?, ?);";
    
    protected String assignmentGradesFind = "SELECT assignment_grades.Grade\n" +
                                            "FROM assignment_grades\n" +
                                            "WHERE assignment_grades.Aid = ? AND assignment_grades.Sid = ?;";
    
    protected String assignmentGradesDelete = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`assignment_grades` WHERE "
                                            + "`Aid`=? and`Sid`=?;";
    
    protected String assignmentGradesModify = "UPDATE `maksym_vavilov_16856_provisional_project`.`assignment_grades` SET "
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
    
    protected String assignmentCreate = "INSERT INTO `maksym_vavilov_16856_provisional_project`.`assignments` "
                                    + "(`Cid`, `Aid`, `Subject`, `Visible`, `DueDate`) VALUES "
                                    + "(?, ?, ?, ?, ?);";
    
    protected String assignmentDelete = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`assignments` WHERE "
                                    + "`Aid`=?;";
    
    protected String assignmentModify = "UPDATE `maksym_vavilov_16856_provisional_project`.`assignments` SET "
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
    
    protected String facultyCreateButton1 = "INSERT INTO `maksym_vavilov_16856_provisional_project`.`faculty_members` "
                                            + "(`Fid`, `Fname`, `Lname`) VALUES "
                                            + "(?,?, ?);";
    
    protected String facultyDeleteButton1 = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`faculty_members` WHERE"
                                        + " `Fid`=?;";
    
    protected String facultyModifyButton1 = "UPDATE `maksym_vavilov_16856_provisional_project`.`faculty_members` SET "
                                        + "`Fname`=?, `Lname`=? WHERE "
                                        + "`Fid`=?;";
    
    protected String subjectsFind = "SELECT * FROM subjects\n" +
                                    "WHERE subjects.SBid = ?;";
    
    protected String subjectsCreate = "INSERT INTO `maksym_vavilov_16856_provisional_project`.`subjects` "
                                    + "(`SBid`, `Lecturer`, `Subject`) VALUES "
                                    + "(?, ?, ?);";
    
    protected String subjectsDelete = "DELETE FROM `maksym_vavilov_16856_provisional_project`.`subjects` WHERE "
                                    + "`SBid`=?;";
    
    protected String subjectsModify = "UPDATE maksym_vavilov_16856_provisional_project.subjects SET "
                                    + "`Lecturer`=?, `Subject`=? WHERE "
                                    + "`SBid`=?;";
    
    protected String loginSelect = "SELECT  login.Username,\n" +
                                "		login.Password,\n" +
                                "		login.Role\n" +
                                "FROM login;";
    
    protected String loginCreate = "INSERT INTO maksym_vavilov_16856_provisional_project.login "
                                + "(`Username`, `Password`, `Role`) VALUES "
                                + "(?, 'panadol', ?);";
    
    protected String loginDelete = "DELETE FROM maksym_vavilov_16856_provisional_project.login WHERE "
                                + "`Username`=?;";
    
    protected String loginModify = "UPDATE  maksym_vavilov_16856_provisional_project.login SET "
                                + "`Role`=? WHERE "
                                + "`Username`=?;";
    
    protected String loginReset = "UPDATE maksym_vavilov_16856_provisional_project.login SET "
                                + "`Password`='panadol' WHERE "
                                + "`Username`=?;";
    
    protected Stack st = new Stack();
    
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


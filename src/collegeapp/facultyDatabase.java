/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeapp;

/**
 *
 * @author Maksym Vavilov 16856
 */
public abstract class facultyDatabase  extends studentDatabase {
    
    protected static String Fid;
    
    protected String timetableQuery = "SELECT timetable.Day,\n" +
                                    "		timetable.Subject,\n" +
                                    "        timetable.Start,\n" +
                                    "        timetable.End\n" +
                                    "FROM (timetable\n" +
                                    "	INNER JOIN group_details ON timetable.Gid = group_details.Gid)\n" +
                                    "    WHERE timetable.Subject = (\n" +
                                    "				SELECT Subject\n" +
                                    "				FROM subjects\n" +
                                    "                WHERE Lecturer = ?)\n" +
                                    "    ORDER BY Subject;";
    
    protected String assignmentsQuery = "SELECT assignments.Aid AS 'Assignment',\n" +
                                    "		subjects.Subject,\n" +
                                    "        assignments.Cid AS 'Course',\n" +
                                    "        assignments.DueDate AS 'Due date',\n" +
                                    "        assignments.Visible\n" +
                                    "FROM (assignments\n" +
                                    "	INNER JOIN subjects ON assignments.Subject = subjects.SBid)\n" +
                                    "    WHERE subjects.Lecturer = ?;";
    
    protected String assignmentFillQuery = "SELECT assignments.Aid,\n" +
                                        "		subjects.Subject,\n" +
                                        "        assignments.Cid\n" +
                                        "FROM (assignments\n" +
                                        "	INNER JOIN subjects ON assignments.Subject = subjects.SBid)\n" +
                                        "WHERE assignments.Subject = (SELECT subjects.SBid\n" +
                                        "							FROM subjects\n" +
                                        "                            WHERE subjects.Lecturer = ?);";
    
    protected String createAssignmentQuery = "INSERT INTO ` maksym_vavilov_16856_provisional_project`.`assignments` "
            + "(`Cid`, `Aid`, `Subject`, `Visible`, `DueDate`) VALUES (?, ?, ?, ?, ?);";
    
    protected String getSubjectQuery = "SELECT subjects.SBid\n" +
                                    "FROM subjects\n" +
                                    "WHERE subjects.Subject = ?;";
    
    protected String modifyGetQuery = "SELECT subjects.Subject,\n" +
                                    "		assignments.Cid,\n" +
                                    "        assignments.DueDate,\n" +
                                    "        assignments.Visible\n" +
                                    "FROM (assignments\n" +
                                    "	INNER JOIN subjects ON assignments.Subject = subjects.SBid)\n" +
                                    "WHERE assignments.Aid = ?;";
    
    protected String modifyQuery = "UPDATE ` maksym_vavilov_16856_provisional_project`.`assignments` SET `Visible`=?, `DueDate`=? WHERE `Aid`=?;";
    
    protected String deleteQuery = "DELETE FROM ` maksym_vavilov_16856_provisional_project`.`assignments` WHERE `Aid`=?;";
    
    protected String studentQuery = "SELECT student.Sid AS 'Student ID',\n" +
                                "		student.Fname AS 'First Name',\n" +
                                "        student.Lname AS 'Last Name',\n" +
                                "        student.Phone AS 'Contact Phone'\n" +
                                "FROM (student\n" +
                                "	INNER JOIN group_details ON student.Course = group_details.Cid)\n" +
                                "WHERE group_details.Superviser = ?\n" +
                                "ORDER BY student.Sid;";
    
    protected String studentGradeQuery = "SELECT assignment_grades.Aid AS 'Assignment',\n" +
                                    "		assignment_grades.Sid AS 'Student',\n" +
                                    "        assignment_grades.Grade\n" +
                                    "FROM ((assignment_grades\n" +
                                    "	INNER JOIN assignments ON assignment_grades.Aid = assignments.Aid)\n" +
                                    "    INNER JOIN subjects ON assignments.Subject = subjects.SBid)\n" +
                                    "WHERE subjects.Lecturer = ?;";
    
    protected String studentFillQuery = "SELECT assignments.Aid\n" +
                                    "FROM (assignments\n" +
                                    "	INNER JOIN subjects ON assignments.Subject = subjects.SBid)\n" +
                                    "WHERE subjects.Lecturer = ?;";
    
    protected String setStudentQuery = "SELECT assignment_grades.Sid\n" +
                                    "FROM assignment_grades\n" +
                                    "WHERE assignment_grades.Aid = ?;";
    
    protected String setStudentGradeQuery = "SELECT assignment_grades.Grade\n" +
                                            "FROM assignment_grades\n" +
                                            "WHERE assignment_grades.Aid = ? AND assignment_grades.Sid = ?;";
    
    protected String modifyStudentQuery = "UPDATE `maksym_vavilov_16856_provisional_project`.`assignment_grades` SET `Grade`=? WHERE `Aid`=? and`Sid`=?;";
    
    
    
    
    facultyDatabase(){
    
    }
    
    
    
}

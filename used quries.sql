use maksym_vavilov_16856_provisional_project;

/* STUDENT */
SELECT assignments.Aid AS 'Assignment',
		subjects.Subject,
        assignments.DueDate AS 'Due date',
        assignment_grades.Grade
FROM ((assignments
	INNER JOIN subjects ON assignments.Subject = subjects.SBid)
    INNER JOIN assignment_grades ON assignments.Aid = assignment_grades.Aid)
    WHERE Sid = 'S01' AND Visible = 'yes';
    
    
SELECT subjects.Subject,
		grades.Grade,
        grades.ExamGrade AS 'Exam Grade'
FROM (grades
	INNER JOIN subjects ON grades.SBid = subjects.SBid)
    WHERE Sid = 'S01';
    
    
SELECT timetable.Day,
		timetable.Subject,
        timetable.Start,
        timetable.End
FROM (timetable
	INNER JOIN groups ON timetable.Gid = groups.Gid)
    WHERE Sid = 'S01'
    ORDER BY Subject;
    
    
SELECT login.Password FROM login
WHERE login.Username = 'S01';

/* FACULTY */
SELECT timetable.Day,
		timetable.Subject,
        timetable.Start,
        timetable.End
FROM (timetable
	INNER JOIN group_details ON timetable.Gid = group_details.Gid)
    WHERE timetable.Subject = (
				SELECT Subject
				FROM subjects
                WHERE Lecturer = 'F02')
    ORDER BY Subject;
    
    
    
SELECT assignments.Aid AS 'Assignment',
		subjects.Subject,
        assignments.Cid AS 'Course',
        assignments.DueDate AS 'Due date',
        assignments.Visible
FROM (assignments
	INNER JOIN subjects ON assignments.Subject = subjects.SBid)
    WHERE subjects.Lecturer = 'F02';
    
    
SELECT assignments.Aid,
		subjects.Subject,
        assignments.Cid
FROM (assignments
	INNER JOIN subjects ON assignments.Subject = subjects.SBid)
WHERE assignments.Subject = (SELECT subjects.SBid
							FROM subjects
                            WHERE subjects.Lecturer = 'F02');
                            
                            
SELECT subjects.SBid
FROM subjects
WHERE subjects.Subject = 'Programming';

SELECT subjects.Subject,
		assignments.Cid,
        assignments.DueDate,
        assignments.Visible
FROM (assignments
	INNER JOIN subjects ON assignments.Subject = subjects.SBid)
WHERE assignments.Aid = 'A01';

SELECT student.Sid AS 'Student ID',
		student.Fname AS 'First Name',
        student.Lname AS 'Last Name',
        student.Phone AS 'Contact Phone'
FROM (student
	INNER JOIN group_details ON student.Course = group_details.Cid)
WHERE group_details.Superviser = 'F02'
ORDER BY student.Sid;


SELECT assignment_grades.Aid AS 'Assignment',
		assignment_grades.Sid AS 'Student',
        assignment_grades.Grade
FROM ((assignment_grades
	INNER JOIN assignments ON assignment_grades.Aid = assignments.Aid)
    INNER JOIN subjects ON assignments.Subject = subjects.SBid)
WHERE subjects.Lecturer = 'F02';


SELECT assignments.Aid
FROM (assignments
	INNER JOIN subjects ON assignments.Subject = subjects.SBid)
WHERE subjects.Lecturer = 'F02';


SELECT assignment_grades.Sid
FROM assignment_grades
WHERE assignment_grades.Aid = 'A01';


SELECT assignment_grades.Grade
FROM assignment_grades
WHERE assignment_grades.Aid = 'A01' AND assignment_grades.Sid = 'S01';



/* ADMIN */

SELECT student.Sid AS 'Student ID',
		student.Fname AS 'First Name',
        student.Lname AS 'Last Name',
        student.Course,
        student.Phone,
        student.Address
FROM student;

SELECT attendance.Sid AS 'Student ID',
		attendance.Lessons,
		attendance.Attended
FROM attendance;

SELECT student.Sid AS 'Student ID',
		student.Fname AS 'First Name',
        student.Lname AS 'Last Name',
        student.Course,
        student.Phone,
        student.Address
FROM student
WHERE student.Sid = 'S01';

SELECT attendance.Sid AS 'Student ID',
		attendance.Lessons,
		attendance.Attended
FROM attendance
WHERE attendance.Sid = 'S01';


SELECT group_details.Gid AS 'Group ID',
		group_details.Cid AS 'Course ID',
        group_details.Superviser AS 'Superviser ID',
        faculty_members.Fname AS 'Superviser Name',
        faculty_members.Lname AS 'Superviser Last Name'
FROM (group_details
	INNER JOIN faculty_members ON group_details.Superviser = faculty_members.Fid);
    
SELECT groups.Gid AS 'Group ID',
		groups.Sid AS 'Student ID',
        student.Fname AS 'Student First Name',
        student.Lname AS 'Student Last Name'
FROM (groups
	INNER JOIN student ON groups.Sid = student.Sid);
    
SELECT timetable.Gid AS 'Group ID',
		timetable.Day,
        timetable.Subject,
        timetable.Start,
        timetable.End
FROM timetable;

SELECT group_details.Gid
FROM group_details;

SELECT subjects.Subject
FROM subjects;

SELECT courses.Cid AS 'Course ID',
		courses.Course AS 'Description',
        courses.Price
FROM courses;

SELECT student.Fname AS 'First Name',
		student.Lname AS 'Last Name',
        payments.Sid AS 'Student ID',
        payments.Required,
        payments.Payed
FROM (payments
	INNER JOIN student ON payments.Sid = student.Sid);
    
SELECT courses.Cid AS 'Course ID',
		courses.Course AS 'Description',
        courses.Price
FROM courses
WHERE courses.Cid = 'C1';

SELECT student.Fname AS 'First Name',
		student.Lname AS 'Last Name',
        payments.Sid AS 'Student ID',
        payments.Required,
        payments.Payed
FROM (payments
	INNER JOIN student ON payments.Sid = student.Sid)
WHERE student.Sid = 'S01';


SELECT branch_details.Bid AS 'Branch ID',
		branch_details.Bname AS 'Branch Name',
        branch_details.Baddress AS 'Address'
FROM branch_details;


SELECT branches.Bid AS 'Branch ID',
		branches.Courses AS 'Course ID',
        courses.Course AS 'Course'
FROM (branches
	INNER JOIN courses ON branches.Courses = courses.Cid);
    
    
SELECT branch_details.Bid
FROM branch_details;

SELECT courses.Cid
FROM courses;

SELECT branch_details.Bid AS 'Branch ID',
		branch_details.Bname AS 'Branch Name',
        branch_details.Baddress AS 'Address'
FROM branch_details
WHERE branch_details.Bid = 'B01';

SELECT grades.Sid AS 'Student ID',
		student.Fname AS 'First Name',
        student.Lname AS 'Last Name',
        grades.SBid AS 'Subject ID',
        subjects.Subject AS 'Subject',
        grades.Grade,
        grades.ExamGrade AS 'Exam Grade'
FROM ((grades
	INNER JOIN student ON grades.Sid = student.Sid)
    INNER JOIN subjects ON grades.SBid = subjects.SBid);
    
SELECT subjects.SBid
FROM subjects;

SELECT assignments.Aid
FROM assignments;

SELECT student.Sid
FROM student
ORDER BY student.Sid;


SELECT 	assignment_grades.Aid AS 'Assignment ID',
		assignment_grades.Sid AS 'Student ID',
		student.Fname AS 'First Name',
        student.Lname AS 'Last Name',
		assignment_grades.Grade
FROM (assignment_grades
	INNER JOIN student ON assignment_grades.Sid = student.Sid);
    
    
SELECT grades.Grade,
		grades.ExamGrade
FROM grades
WHERE grades.Sid = 'S01' AND grades.SBid = 'SB02';

SELECT assignment_grades.Grade
FROM assignment_grades
WHERE assignment_grades.Aid = 'A01' AND assignment_grades.Sid = 'S01';


SELECT 	assignments.Cid AS 'Course ID',
		courses.Course,
        assignments.Aid,
        assignments.Subject AS 'Subject ID',
        subjects.Subject,
        assignments.DueDate AS 'Due Date',
        assignments.Visible
FROM ((assignments
	INNER JOIN courses ON assignments.Cid = courses.Cid)
    INNER JOIN subjects ON assignments.Subject = subjects.SBid);
    
SELECT 	assignments.Cid,
		assignments.Aid,
        assignments.Subject,
        assignments.Visible,
        assignments.DueDate
FROM assignments
WHERE assignments.Aid = 'A01';

SELECT courses.Cid
FROM courses;


SELECT 	faculty_members.Fid AS 'Faculty ID',
		faculty_members.Fname AS 'First Name',
        faculty_members.Lname AS 'Last Name'
FROM faculty_members;


SELECT 	subjects.SBid AS 'Subject ID',
		subjects.Lecturer AS 'Lecturer ID',
        faculty_members.Fname AS 'Lecturer First Name',
        faculty_members.Lname AS 'Lecturer Last Name',
        subjects.Subject AS 'Subject Name'
FROM (subjects
	INNER JOIN faculty_members ON subjects.Lecturer = faculty_members.Fid);
    
SELECT faculty_members.Fid
FROM faculty_members;

SELECT 	faculty_members.Fid AS 'Faculty ID',
		faculty_members.Fname AS 'First Name',
        faculty_members.Lname AS 'Last Name'
FROM faculty_members
WHERE faculty_members.Fid = 'F01';

SELECT * FROM subjects
WHERE subjects.SBid = 'SB01';


SELECT  login.Username,
		login.Password,
		login.Role
FROM login;

SELECT login.Password
FROM login;


SELECT login.Password
FROM login
WHERE login.Username = 'F02';



SELECT group_details.Cid 
FROM group_details
WHERE group_details.Superviser = 'F02';
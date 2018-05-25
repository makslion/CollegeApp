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
import java.util.EmptyStackException;
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
    
    public adminGUI() {
        initComponents();
        openFile();
        readConnectionDetails();
        closeFile();
        
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
        
        
        //REDRAWINN ELEMENTS TO FIT STYLE
        designTable(studentStudentTable,studentStudentScrollPane);
        designTable(studentAttendanceTable,studentAttendanceScrollPane);
        designTable(groupGroupDetailsTabel,groupGroupDetailsScrollPane);
        designTable(groupGroupsTabel,groupGroupsScrollPane);
        designTable(timetableTabel,timetableScrollPane);
        designTable(coursesTable,coursesScrollPane);
        designTable(paymentsTable,paymentsScrollPane);
        designTable(branchDetailsTable,branchDetailsScrollPane);
        designTable(branchTable,branchScrollPane);
        designTable(gradesTable,gradesScrollPane);
        designTable(assignmentGradesTabel,assignmentGradesScrollPane);
        designTable(assignmentsTable,assignmentsScrollPane);
        designTable(facultyMembersTable,facultyMembersScrollPane);
        designTable(subjectsTable,subjectsScrollPane);
        designTable(loginTable,loginScrolPane);
        designTable(directTable,directScrollPane);
        editCombo(timetableGroupCombo);
        editCombo(timetableDayCombo);
        editCombo(timetableSubjectCombo);
        editCombo(timetableStartHoursCombo);
        editCombo(timetableStartMinCombo);
        editCombo(timetableEndHoursCombo);
        editCombo(timetableEndMinCombo);
        editCombo(branchIdCombo);
        editCombo(courseIdCombo);
        editCombo(studentCombo1);
        editCombo(subjectCombo1);
        editCombo(assignmentCombo2);
        editCombo(studentCombo2);
        editCombo(assignmentCourseCombo);
        editCombo(assignmentSubjectCombo);
        editCombo(assignmentVisibleCombo);
        editCombo(facultyFIDCombo);
        editCombo(loginRoleCombo);
        
        
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
    
    private void stackPush(){
        st.push(directTextArea.getText());
    }
    
    private void stackPop(){
        String pop;
        try {
            pop = st.pop().toString();
            
            if (pop.equalsIgnoreCase(directTextArea.getText()))
                pop = st.pop().toString();
            
            directTextArea.setText(pop);
            
      } catch (EmptyStackException e) {
         JOptionPane.showMessageDialog(this, e, "ERROR", JOptionPane.ERROR_MESSAGE);
      }
    }
    
    private void loginReset(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
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
            
            PrepStatement = conn.prepareStatement(subjectsDelete);
            PrepStatement.setString(1, facultySBIDfield.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        fillAll();
    }
    
    private void subjectsCreate(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(subjectsCreate);
            PrepStatement.setString(1, facultySBIDfield.getText());
            PrepStatement.setString(2, facultyFIDCombo.getSelectedItem().toString());
            PrepStatement.setString(3, facultySubjectField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        fillAll();
    }
    
    private void subjectsFind(){
        ResultSet resultSet = null;        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            
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
            
            PrepStatement = conn.prepareStatement(facultyDeleteButton1);
            PrepStatement.setString(1, facultyIdField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        fillAll();
    }
    
    private void facultyMembersCreate(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(facultyCreateButton1);
            PrepStatement.setString(1, facultyIdField.getText());
            PrepStatement.setString(2, facultyFnameField.getText());
            PrepStatement.setString(3, facultyLnameField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        fillAll();
    }
    
    private void facultyMembersFind(){
        ResultSet resultSet = null;        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            
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
            
            PrepStatement = conn.prepareStatement(assignmentDelete);
            PrepStatement.setString(1, assignmentField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        fillAll();
    }
    
    private void assignmentCreate(){
        ResultSet resultSet = null;
        Date date = assignmentDatePicker.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = sdf.format(date);
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
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
        fillAll();
    }
    
    private void assignmentFind(){
        ResultSet resultSet = null;        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            
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
            
            PrepStatement = conn.prepareStatement(branchCreate);
            PrepStatement.setString(1, branchIDField.getText());
            PrepStatement.setString(2, branchNameField.getText());
            PrepStatement.setString(3, branchAddressArea.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        fillAll();
    }
    
    private void branchDetailsDelete(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(branchDelete);
            PrepStatement.setString(1, branchIDField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        fillAll();
    }
    
    private void branchDetailsFind(){
        ResultSet resultSet = null;        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            
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
            
            PrepStatement = conn.prepareStatement(courseDelete);
            PrepStatement.setString(1, courseIdField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        fillAll();
    }
    
    private void createCourse(){
        ResultSet resultSet = null;
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(courseCreate);
            PrepStatement.setString(1, courseIdField.getText());
            PrepStatement.setString(2, courseArea.getText());
            PrepStatement.setString(3, coursePriceField.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        fillAll();
    }
    
    
    
    private void createTimetable(){
        ResultSet resultSet = null;
        String startTime = timetableStartHoursCombo.getSelectedItem().toString()+":"+
                timetableStartMinCombo.getSelectedItem().toString()+":00";
        String endTime = timetableEndHoursCombo.getSelectedItem().toString()+":"+
                timetableEndMinCombo.getSelectedItem().toString()+":00";;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
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
        fillAll();
    }
    
    private void modifyTimetable(){
        ResultSet resultSet = null;
        String startTime = timetableStartHoursCombo.getSelectedItem().toString()+":"+
                timetableStartMinCombo.getSelectedItem().toString()+":00";
        String endTime = timetableEndHoursCombo.getSelectedItem().toString()+":"+
                timetableEndMinCombo.getSelectedItem().toString()+":00";;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
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
            
            PrepStatement = conn.prepareStatement(timetableDelete);
            PrepStatement.setString(1, timetableGroupCombo.getSelectedItem().toString());
            PrepStatement.setString(2, timetableDayCombo.getSelectedItem().toString());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        fillAll();
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
            
            PrepStatement = conn.prepareStatement(createGroupDetails);
            PrepStatement.setString(1, groupField1.getText());
            PrepStatement.setString(2, groupField2.getText());
            PrepStatement.setString(3, groupField3.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        fillAll();
    }
    
    private void deleteGroupDetails(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(deleteGroupDetails);
            PrepStatement.setString(1, groupField1.getText());
            PrepStatement.setString(2, groupField2.getText());
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }    
        fillAll();
    }
    
    private void modifyGroupDetais(){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
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
        fillAll();
    }
    
    private void deleteStudent(String sid){
        ResultSet resultSet = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(deleteStudentQuery);
            
            PrepStatement.setString(1,sid);;
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        fillAll();
    }
    
    private void findStudent (String sid){
        ResultSet resultSet = null;
        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            
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
    
    private void fillAll(){
        timetableFill();
        branchFill();
        gradesFill();
        assignmentFill();
        facultyFill();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        studentPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        studentStudentScrollPane = new javax.swing.JScrollPane();
        studentStudentTable = new javax.swing.JTable();
        studentAttendanceScrollPane = new javax.swing.JScrollPane();
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
        studentButton = new javax.swing.JButton();
        bg = new javax.swing.JPanel();
        logoutButton10 = new javax.swing.JButton();
        studentFacultyButton = new javax.swing.JButton();
        studenAssignmentstButton = new javax.swing.JButton();
        studentGroupsButton = new javax.swing.JButton();
        studenTimetabletButton = new javax.swing.JButton();
        studenCoursestButton = new javax.swing.JButton();
        studentBranchesButton = new javax.swing.JButton();
        studentGradeBookButton = new javax.swing.JButton();
        studentLoginButton = new javax.swing.JButton();
        studenDirectButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        groupsPanel = new javax.swing.JPanel();
        groupLabel1 = new javax.swing.JLabel();
        groupLabel2 = new javax.swing.JLabel();
        groupGroupDetailsScrollPane = new javax.swing.JScrollPane();
        groupGroupDetailsTabel = new javax.swing.JTable();
        groupGroupsScrollPane = new javax.swing.JScrollPane();
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
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        groupsStudentButton = new javax.swing.JButton();
        bg1 = new javax.swing.JPanel();
        logoutButton9 = new javax.swing.JButton();
        groupsFacultyButton = new javax.swing.JButton();
        groupsAssignmentsButton = new javax.swing.JButton();
        groupsButton = new javax.swing.JButton();
        groupsTimetableButton = new javax.swing.JButton();
        groupsCoursesButton = new javax.swing.JButton();
        groupsBranchesButton = new javax.swing.JButton();
        groupsGradeBookButton = new javax.swing.JButton();
        groupsLoginButton = new javax.swing.JButton();
        groupsDirectButton = new javax.swing.JButton();
        timetablePanel = new javax.swing.JPanel();
        timetableScrollPane = new javax.swing.JScrollPane();
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
        timetableStudentButton = new javax.swing.JButton();
        bg2 = new javax.swing.JPanel();
        logoutButton8 = new javax.swing.JButton();
        timetableFacultyButton = new javax.swing.JButton();
        timetableAssignmentsButton = new javax.swing.JButton();
        timetableGroupsButton = new javax.swing.JButton();
        timetableButton = new javax.swing.JButton();
        timetableCoursesButton = new javax.swing.JButton();
        timetableBranchesButton = new javax.swing.JButton();
        timetableGradeBookButton = new javax.swing.JButton();
        timetableLoginButton = new javax.swing.JButton();
        timetableDirectButton = new javax.swing.JButton();
        coursesPanel = new javax.swing.JPanel();
        coursesLAbel1 = new javax.swing.JLabel();
        coursesLabel2 = new javax.swing.JLabel();
        coursesScrollPane = new javax.swing.JScrollPane();
        coursesTable = new javax.swing.JTable();
        paymentsScrollPane = new javax.swing.JScrollPane();
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
        jSeparator14 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        jSeparator18 = new javax.swing.JSeparator();
        coursesStudentButton = new javax.swing.JButton();
        bg3 = new javax.swing.JPanel();
        logoutButton7 = new javax.swing.JButton();
        coursesFacultyButton = new javax.swing.JButton();
        coursesAssignmentsButton = new javax.swing.JButton();
        coursesGroupsButton = new javax.swing.JButton();
        coursesTimetableButton = new javax.swing.JButton();
        coursesButton = new javax.swing.JButton();
        coursesBranchesButton = new javax.swing.JButton();
        coursesGradeBookButton = new javax.swing.JButton();
        coursesLoginButton = new javax.swing.JButton();
        coursesDirectButton = new javax.swing.JButton();
        branchesPanel = new javax.swing.JPanel();
        branchLabel1 = new javax.swing.JLabel();
        branchLabel2 = new javax.swing.JLabel();
        branchDetailsScrollPane = new javax.swing.JScrollPane();
        branchDetailsTable = new javax.swing.JTable();
        branchScrollPane = new javax.swing.JScrollPane();
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
        jSeparator19 = new javax.swing.JSeparator();
        jSeparator20 = new javax.swing.JSeparator();
        branchesStudentButton = new javax.swing.JButton();
        bg4 = new javax.swing.JPanel();
        logoutButton6 = new javax.swing.JButton();
        branchesFacultyButton = new javax.swing.JButton();
        branchesAssignmentsButton = new javax.swing.JButton();
        branchesGroupsButton = new javax.swing.JButton();
        branchesTimetableButton = new javax.swing.JButton();
        branchesCoursesButton = new javax.swing.JButton();
        branchesButton = new javax.swing.JButton();
        branchesGradeBookButton = new javax.swing.JButton();
        branchesLoginButton = new javax.swing.JButton();
        branchesDirectButton = new javax.swing.JButton();
        gradesPanel = new javax.swing.JPanel();
        gradeBookLabel1 = new javax.swing.JLabel();
        gradeBookLabel2 = new javax.swing.JLabel();
        gradesScrollPane = new javax.swing.JScrollPane();
        gradesTable = new javax.swing.JTable();
        assignmentGradesScrollPane = new javax.swing.JScrollPane();
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
        jSeparator21 = new javax.swing.JSeparator();
        jSeparator22 = new javax.swing.JSeparator();
        jSeparator23 = new javax.swing.JSeparator();
        bg5 = new javax.swing.JPanel();
        logoutButton5 = new javax.swing.JButton();
        gradeBookFacultyButton = new javax.swing.JButton();
        gradeBookAssignmentsButton = new javax.swing.JButton();
        gradeBookGroupsButton = new javax.swing.JButton();
        gradeBookTimetableButton = new javax.swing.JButton();
        gradeBookCoursesButton = new javax.swing.JButton();
        gradeBookBranchesButton = new javax.swing.JButton();
        jButton63 = new javax.swing.JButton();
        gradeBookLoginButton = new javax.swing.JButton();
        gradeBookDirectButton = new javax.swing.JButton();
        gradeBookStudentButton = new javax.swing.JButton();
        assignmentPanel = new javax.swing.JPanel();
        assignmentLabel4 = new javax.swing.JLabel();
        assignmentsScrollPane = new javax.swing.JScrollPane();
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
        jSeparator24 = new javax.swing.JSeparator();
        bg6 = new javax.swing.JPanel();
        logoutButton4 = new javax.swing.JButton();
        assignmentsFacultyButton = new javax.swing.JButton();
        assignmentsButton = new javax.swing.JButton();
        assignmentsGroupsButton = new javax.swing.JButton();
        assignmentsTimetableButton = new javax.swing.JButton();
        assignmentsCoursesButton = new javax.swing.JButton();
        assignmentsBranchesButton = new javax.swing.JButton();
        assignmentsGradeBookButton = new javax.swing.JButton();
        assignmentsLoginButton = new javax.swing.JButton();
        assignmentsDirectButton = new javax.swing.JButton();
        assignmentsStudentButton = new javax.swing.JButton();
        facultyPanel = new javax.swing.JPanel();
        facultyLablel1 = new javax.swing.JLabel();
        facultyLablel2 = new javax.swing.JLabel();
        facultyMembersScrollPane = new javax.swing.JScrollPane();
        facultyMembersTable = new javax.swing.JTable();
        subjectsScrollPane = new javax.swing.JScrollPane();
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
        jSeparator25 = new javax.swing.JSeparator();
        jSeparator26 = new javax.swing.JSeparator();
        jSeparator27 = new javax.swing.JSeparator();
        jSeparator28 = new javax.swing.JSeparator();
        jSeparator29 = new javax.swing.JSeparator();
        bg7 = new javax.swing.JPanel();
        logoutButton3 = new javax.swing.JButton();
        facultyButton = new javax.swing.JButton();
        facultyAssignmentsButton = new javax.swing.JButton();
        facultyGroupsButton = new javax.swing.JButton();
        facultyTimetableButton = new javax.swing.JButton();
        facultyCoursesButton = new javax.swing.JButton();
        facultyBranchesButton = new javax.swing.JButton();
        facultyGradeBookButton = new javax.swing.JButton();
        facultyLoginButton = new javax.swing.JButton();
        facultyDirectButton = new javax.swing.JButton();
        facultyStudentButton = new javax.swing.JButton();
        loginPanel = new javax.swing.JPanel();
        loginLabel3 = new javax.swing.JLabel();
        loginScrolPane = new javax.swing.JScrollPane();
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
        jSeparator30 = new javax.swing.JSeparator();
        bg8 = new javax.swing.JPanel();
        logoutButton2 = new javax.swing.JButton();
        loginFacultyButton = new javax.swing.JButton();
        loginAssignmentsButton = new javax.swing.JButton();
        loginGroupsButton = new javax.swing.JButton();
        loginTimetableButton = new javax.swing.JButton();
        loginCoursesButton = new javax.swing.JButton();
        loginBranchesButton = new javax.swing.JButton();
        loginGradeBookButton = new javax.swing.JButton();
        loginButton = new javax.swing.JButton();
        loginDirectButton = new javax.swing.JButton();
        loginStudentButton = new javax.swing.JButton();
        DirectDBaccess = new javax.swing.JPanel();
        directOutputLabel = new javax.swing.JLabel();
        directScrollPane = new javax.swing.JScrollPane();
        directTable = new javax.swing.JTable();
        directInputLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        directTextArea = new javax.swing.JTextArea();
        directExecuteButton = new javax.swing.JButton();
        directExecuteUpdateButton = new javax.swing.JButton();
        directPrevButton = new javax.swing.JButton();
        bg9 = new javax.swing.JPanel();
        logoutButton1 = new javax.swing.JButton();
        directFacultyButton = new javax.swing.JButton();
        directAssignmentsButton = new javax.swing.JButton();
        directGroupsButton = new javax.swing.JButton();
        directTimetableButton = new javax.swing.JButton();
        directCoursesButton = new javax.swing.JButton();
        directBranchesButton = new javax.swing.JButton();
        directGradeBookButton = new javax.swing.JButton();
        directButton = new javax.swing.JButton();
        directStudentButton = new javax.swing.JButton();
        directLoginButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Admin");
        setMinimumSize(new java.awt.Dimension(1270, 742));
        setUndecorated(true);
        setResizable(false);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        studentPanel.setBackground(new java.awt.Color(255, 255, 255));
        studentPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        studentPanel.setForeground(new java.awt.Color(255, 255, 255));
        studentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Students");
        studentPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, -1, -1));

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Attendance");
        studentPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 90, -1, -1));

        studentStudentTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        studentStudentScrollPane.setViewportView(studentStudentTable);

        studentPanel.add(studentStudentScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 680, 328));

        studentAttendanceTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        studentAttendanceScrollPane.setViewportView(studentAttendanceTable);

        studentPanel.add(studentAttendanceScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 150, 414, 328));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Student ID");
        studentPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, -1, 20));

        sidField1.setBackground(new java.awt.Color(255, 255, 255));
        sidField1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        sidField1.setBorder(null);
        studentPanel.add(sidField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, 87, 20));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("First Name");
        studentPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 500, -1, 20));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Last Name");
        studentPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 500, -1, 20));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Course");
        studentPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 500, -1, 20));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Phone");
        studentPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 500, -1, 20));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Address");
        studentPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 500, -1, 20));

        addressArea.setColumns(20);
        addressArea.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        addressArea.setLineWrap(true);
        addressArea.setRows(5);
        addressArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane3.setViewportView(addressArea);

        studentPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 530, 157, 120));

        phoneField.setBackground(new java.awt.Color(255, 255, 255));
        phoneField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        phoneField.setBorder(null);
        studentPanel.add(phoneField, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 530, 99, 20));

        courseField.setBackground(new java.awt.Color(255, 255, 255));
        courseField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        courseField.setBorder(null);
        studentPanel.add(courseField, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 530, 90, 20));

        LnameField.setBackground(new java.awt.Color(255, 255, 255));
        LnameField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        LnameField.setBorder(null);
        studentPanel.add(LnameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 530, 89, 20));

        FnameField.setBackground(new java.awt.Color(255, 255, 255));
        FnameField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        FnameField.setBorder(null);
        studentPanel.add(FnameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 530, 99, 20));

        applyButton1.setBackground(new java.awt.Color(102, 102, 102));
        applyButton1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        applyButton1.setForeground(new java.awt.Color(255, 255, 255));
        applyButton1.setText("Apply Changes");
        applyButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButton1ActionPerformed(evt);
            }
        });
        studentPanel.add(applyButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 570, 200, 80));

        findButton1.setBackground(new java.awt.Color(102, 102, 102));
        findButton1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        findButton1.setForeground(new java.awt.Color(255, 255, 255));
        findButton1.setText("Find by ID");
        findButton1.setToolTipText("");
        findButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findButton1ActionPerformed(evt);
            }
        });
        studentPanel.add(findButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 570, 90, 80));

        createButton1.setBackground(new java.awt.Color(102, 102, 102));
        createButton1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        createButton1.setForeground(new java.awt.Color(255, 255, 255));
        createButton1.setText("Create");
        createButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButton1ActionPerformed(evt);
            }
        });
        studentPanel.add(createButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 570, 100, 80));

        deleteButton1.setBackground(new java.awt.Color(102, 102, 102));
        deleteButton1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        deleteButton1.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton1.setText("Delete");
        deleteButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButton1ActionPerformed(evt);
            }
        });
        studentPanel.add(deleteButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 570, 87, 80));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Student ID");
        studentPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 500, -1, 20));

        sidField2.setBackground(new java.awt.Color(255, 255, 255));
        sidField2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        sidField2.setBorder(null);
        studentPanel.add(sidField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 530, 99, 20));

        findButton2.setBackground(new java.awt.Color(102, 102, 102));
        findButton2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        findButton2.setForeground(new java.awt.Color(255, 255, 255));
        findButton2.setText("Find by ID");
        findButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findButton2ActionPerformed(evt);
            }
        });
        studentPanel.add(findButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 570, -1, 80));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Lessons");
        studentPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 500, -1, 20));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Lessons Attended");
        studentPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 500, -1, 20));

        lessonsField2.setBackground(new java.awt.Color(255, 255, 255));
        lessonsField2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        lessonsField2.setBorder(null);
        studentPanel.add(lessonsField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 530, 108, -1));

        lessonsField.setBackground(new java.awt.Color(255, 255, 255));
        lessonsField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        lessonsField.setBorder(null);
        studentPanel.add(lessonsField, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 530, 108, 20));

        applyButton2.setBackground(new java.awt.Color(102, 102, 102));
        applyButton2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        applyButton2.setForeground(new java.awt.Color(255, 255, 255));
        applyButton2.setText("Apply");
        applyButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButton2ActionPerformed(evt);
            }
        });
        studentPanel.add(applyButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 570, 108, 80));

        deleteButton2.setBackground(new java.awt.Color(102, 102, 102));
        deleteButton2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        deleteButton2.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton2.setText("Delete");
        deleteButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButton2ActionPerformed(evt);
            }
        });
        studentPanel.add(deleteButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 570, -1, 80));

        createButton2.setBackground(new java.awt.Color(102, 102, 102));
        createButton2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        createButton2.setForeground(new java.awt.Color(255, 255, 255));
        createButton2.setText("Create");
        createButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButton2ActionPerformed(evt);
            }
        });
        studentPanel.add(createButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 570, -1, 80));

        studentButton.setBackground(new java.awt.Color(255, 255, 255));
        studentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studentButton.setForeground(new java.awt.Color(102, 102, 102));
        studentButton.setText("Student");
        studentButton.setBorder(null);
        studentPanel.add(studentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 40));

        bg.setBackground(new java.awt.Color(102, 102, 102));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutButton10.setBackground(new java.awt.Color(102, 102, 102));
        logoutButton10.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logoutButton10.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton10.setText("X");
        logoutButton10.setBorder(null);
        logoutButton10.setFocusPainted(false);
        logoutButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButton10ActionPerformed(evt);
            }
        });
        bg.add(logoutButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, 40, -1));

        studentFacultyButton.setBackground(new java.awt.Color(102, 102, 102));
        studentFacultyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studentFacultyButton.setForeground(new java.awt.Color(255, 255, 255));
        studentFacultyButton.setText("Faculty");
        studentFacultyButton.setBorder(null);
        studentFacultyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentFacultyButtonActionPerformed(evt);
            }
        });
        bg.add(studentFacultyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 80, 20));

        studenAssignmentstButton.setBackground(new java.awt.Color(102, 102, 102));
        studenAssignmentstButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studenAssignmentstButton.setForeground(new java.awt.Color(255, 255, 255));
        studenAssignmentstButton.setText("Assignments");
        studenAssignmentstButton.setBorder(null);
        studenAssignmentstButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studenAssignmentstButtonActionPerformed(evt);
            }
        });
        bg.add(studenAssignmentstButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 100, 20));

        studentGroupsButton.setBackground(new java.awt.Color(102, 102, 102));
        studentGroupsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studentGroupsButton.setForeground(new java.awt.Color(255, 255, 255));
        studentGroupsButton.setText("Groups");
        studentGroupsButton.setBorder(null);
        studentGroupsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentGroupsButtonActionPerformed(evt);
            }
        });
        bg.add(studentGroupsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 70, 20));

        studenTimetabletButton.setBackground(new java.awt.Color(102, 102, 102));
        studenTimetabletButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studenTimetabletButton.setForeground(new java.awt.Color(255, 255, 255));
        studenTimetabletButton.setText("Timetable");
        studenTimetabletButton.setBorder(null);
        studenTimetabletButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studenTimetabletButtonActionPerformed(evt);
            }
        });
        bg.add(studenTimetabletButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 90, 20));

        studenCoursestButton.setBackground(new java.awt.Color(102, 102, 102));
        studenCoursestButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studenCoursestButton.setForeground(new java.awt.Color(255, 255, 255));
        studenCoursestButton.setText("Courses");
        studenCoursestButton.setBorder(null);
        studenCoursestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studenCoursestButtonActionPerformed(evt);
            }
        });
        bg.add(studenCoursestButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 80, 20));

        studentBranchesButton.setBackground(new java.awt.Color(102, 102, 102));
        studentBranchesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studentBranchesButton.setForeground(new java.awt.Color(255, 255, 255));
        studentBranchesButton.setText("Branches");
        studentBranchesButton.setBorder(null);
        studentBranchesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentBranchesButtonActionPerformed(evt);
            }
        });
        bg.add(studentBranchesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 90, 20));

        studentGradeBookButton.setBackground(new java.awt.Color(102, 102, 102));
        studentGradeBookButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studentGradeBookButton.setForeground(new java.awt.Color(255, 255, 255));
        studentGradeBookButton.setText("Grade Book");
        studentGradeBookButton.setBorder(null);
        studentGradeBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentGradeBookButtonActionPerformed(evt);
            }
        });
        bg.add(studentGradeBookButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 100, 20));

        studentLoginButton.setBackground(new java.awt.Color(102, 102, 102));
        studentLoginButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studentLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        studentLoginButton.setText("Login");
        studentLoginButton.setBorder(null);
        studentLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentLoginButtonActionPerformed(evt);
            }
        });
        bg.add(studentLoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 60, 20));

        studenDirectButton.setBackground(new java.awt.Color(102, 102, 102));
        studenDirectButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studenDirectButton.setForeground(new java.awt.Color(255, 255, 255));
        studenDirectButton.setText("Derict DB Access");
        studenDirectButton.setBorder(null);
        studenDirectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studenDirectButtonActionPerformed(evt);
            }
        });
        bg.add(studenDirectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 130, 20));

        studentPanel.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 70));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        studentPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, 90, 10));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        studentPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 550, 100, 10));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        studentPanel.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 550, 90, 10));

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        studentPanel.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 550, 90, 10));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        studentPanel.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 550, 100, 10));

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        studentPanel.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 550, 100, 10));

        jSeparator7.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        studentPanel.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 550, 110, 10));

        jSeparator8.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        studentPanel.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 550, 110, 10));

        getContentPane().add(studentPanel, "card3");

        groupsPanel.setBackground(new java.awt.Color(255, 255, 255));
        groupsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        groupsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        groupLabel1.setBackground(new java.awt.Color(255, 255, 255));
        groupLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        groupLabel1.setForeground(new java.awt.Color(102, 102, 102));
        groupLabel1.setText("Groups");
        groupsPanel.add(groupLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(307, 90, -1, -1));

        groupLabel2.setBackground(new java.awt.Color(255, 255, 255));
        groupLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        groupLabel2.setForeground(new java.awt.Color(102, 102, 102));
        groupLabel2.setText("Students");
        groupsPanel.add(groupLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(954, 90, -1, -1));

        groupGroupDetailsTabel.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        groupGroupDetailsScrollPane.setViewportView(groupGroupDetailsTabel);

        groupsPanel.add(groupGroupDetailsScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 133, 649, 363));

        groupGroupsTabel.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        groupGroupsScrollPane.setViewportView(groupGroupsTabel);

        groupsPanel.add(groupGroupsScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(705, 133, 545, 363));

        groupLabel3.setBackground(new java.awt.Color(255, 255, 255));
        groupLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        groupLabel3.setForeground(new java.awt.Color(102, 102, 102));
        groupLabel3.setText("Group ID");
        groupsPanel.add(groupLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 513, -1, -1));

        groupLabel4.setBackground(new java.awt.Color(255, 255, 255));
        groupLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        groupLabel4.setForeground(new java.awt.Color(102, 102, 102));
        groupLabel4.setText("Course ID");
        groupsPanel.add(groupLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 510, -1, -1));

        groupLabel5.setBackground(new java.awt.Color(255, 255, 255));
        groupLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        groupLabel5.setForeground(new java.awt.Color(102, 102, 102));
        groupLabel5.setText("Superviser ID");
        groupsPanel.add(groupLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 510, -1, -1));

        groupField1.setBackground(new java.awt.Color(255, 255, 255));
        groupField1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupField1.setBorder(null);
        groupsPanel.add(groupField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 537, 127, -1));

        groupField2.setBackground(new java.awt.Color(255, 255, 255));
        groupField2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupField2.setBorder(null);
        groupsPanel.add(groupField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 537, 127, -1));

        groupField3.setBackground(new java.awt.Color(255, 255, 255));
        groupField3.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupField3.setBorder(null);
        groupsPanel.add(groupField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(487, 537, 145, -1));

        groupButton.setBackground(new java.awt.Color(102, 102, 102));
        groupButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupButton.setForeground(new java.awt.Color(255, 255, 255));
        groupButton.setText("Create");
        groupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupButtonActionPerformed(evt);
            }
        });
        groupsPanel.add(groupButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 577, 127, 80));

        groupButton3.setBackground(new java.awt.Color(102, 102, 102));
        groupButton3.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupButton3.setForeground(new java.awt.Color(255, 255, 255));
        groupButton3.setText("Modify Superviser");
        groupButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupButton3ActionPerformed(evt);
            }
        });
        groupsPanel.add(groupButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(487, 577, 145, 80));

        groupButton2.setBackground(new java.awt.Color(102, 102, 102));
        groupButton2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupButton2.setForeground(new java.awt.Color(255, 255, 255));
        groupButton2.setText("Delete");
        groupButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupButton2ActionPerformed(evt);
            }
        });
        groupsPanel.add(groupButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 577, 127, 80));

        groupLabel6.setBackground(new java.awt.Color(255, 255, 255));
        groupLabel6.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        groupLabel6.setForeground(new java.awt.Color(102, 102, 102));
        groupLabel6.setText("Group ID");
        groupsPanel.add(groupLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 510, -1, -1));

        groupLabel7.setBackground(new java.awt.Color(255, 255, 255));
        groupLabel7.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        groupLabel7.setForeground(new java.awt.Color(102, 102, 102));
        groupLabel7.setText("Student ID");
        groupsPanel.add(groupLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1059, 513, -1, -1));

        groupField4.setBackground(new java.awt.Color(255, 255, 255));
        groupField4.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupField4.setBorder(null);
        groupsPanel.add(groupField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(797, 537, 129, -1));

        groupField5.setBackground(new java.awt.Color(255, 255, 255));
        groupField5.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupField5.setBorder(null);
        groupsPanel.add(groupField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1027, 537, 128, -1));

        groupButton4.setBackground(new java.awt.Color(102, 102, 102));
        groupButton4.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupButton4.setForeground(new java.awt.Color(255, 255, 255));
        groupButton4.setText("Create Record");
        groupButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupButton4ActionPerformed(evt);
            }
        });
        groupsPanel.add(groupButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(797, 577, 129, 80));

        groupButton5.setBackground(new java.awt.Color(102, 102, 102));
        groupButton5.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupButton5.setForeground(new java.awt.Color(255, 255, 255));
        groupButton5.setText("Delete Record");
        groupButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupButton5ActionPerformed(evt);
            }
        });
        groupsPanel.add(groupButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1027, 577, 128, 80));

        jSeparator9.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        groupsPanel.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 130, 10));

        jSeparator10.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        groupsPanel.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 560, 130, 10));

        jSeparator11.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        groupsPanel.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 560, 150, 10));

        jSeparator12.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        groupsPanel.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 560, 140, 10));

        jSeparator13.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));
        groupsPanel.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 560, 140, 10));

        groupsStudentButton.setBackground(new java.awt.Color(102, 102, 102));
        groupsStudentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupsStudentButton.setForeground(new java.awt.Color(255, 255, 255));
        groupsStudentButton.setText("Student");
        groupsStudentButton.setBorder(null);
        groupsStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupsStudentButtonActionPerformed(evt);
            }
        });
        groupsPanel.add(groupsStudentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 20));

        bg1.setBackground(new java.awt.Color(102, 102, 102));
        bg1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutButton9.setBackground(new java.awt.Color(102, 102, 102));
        logoutButton9.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logoutButton9.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton9.setText("X");
        logoutButton9.setBorder(null);
        logoutButton9.setFocusPainted(false);
        logoutButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButton9ActionPerformed(evt);
            }
        });
        bg1.add(logoutButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, 40, -1));

        groupsFacultyButton.setBackground(new java.awt.Color(102, 102, 102));
        groupsFacultyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupsFacultyButton.setForeground(new java.awt.Color(255, 255, 255));
        groupsFacultyButton.setText("Faculty");
        groupsFacultyButton.setBorder(null);
        groupsFacultyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupsFacultyButtonActionPerformed(evt);
            }
        });
        bg1.add(groupsFacultyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 80, 20));

        groupsAssignmentsButton.setBackground(new java.awt.Color(102, 102, 102));
        groupsAssignmentsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupsAssignmentsButton.setForeground(new java.awt.Color(255, 255, 255));
        groupsAssignmentsButton.setText("Assignments");
        groupsAssignmentsButton.setBorder(null);
        groupsAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupsAssignmentsButtonActionPerformed(evt);
            }
        });
        bg1.add(groupsAssignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 100, 20));

        groupsButton.setBackground(new java.awt.Color(255, 255, 255));
        groupsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupsButton.setForeground(new java.awt.Color(102, 102, 102));
        groupsButton.setText("Groups");
        groupsButton.setBorder(null);
        bg1.add(groupsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 70, 40));

        groupsTimetableButton.setBackground(new java.awt.Color(102, 102, 102));
        groupsTimetableButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupsTimetableButton.setForeground(new java.awt.Color(255, 255, 255));
        groupsTimetableButton.setText("Timetable");
        groupsTimetableButton.setBorder(null);
        groupsTimetableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupsTimetableButtonActionPerformed(evt);
            }
        });
        bg1.add(groupsTimetableButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 90, 20));

        groupsCoursesButton.setBackground(new java.awt.Color(102, 102, 102));
        groupsCoursesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupsCoursesButton.setForeground(new java.awt.Color(255, 255, 255));
        groupsCoursesButton.setText("Courses");
        groupsCoursesButton.setBorder(null);
        groupsCoursesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupsCoursesButtonActionPerformed(evt);
            }
        });
        bg1.add(groupsCoursesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 80, 20));

        groupsBranchesButton.setBackground(new java.awt.Color(102, 102, 102));
        groupsBranchesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupsBranchesButton.setForeground(new java.awt.Color(255, 255, 255));
        groupsBranchesButton.setText("Branches");
        groupsBranchesButton.setBorder(null);
        groupsBranchesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupsBranchesButtonActionPerformed(evt);
            }
        });
        bg1.add(groupsBranchesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 90, 20));

        groupsGradeBookButton.setBackground(new java.awt.Color(102, 102, 102));
        groupsGradeBookButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupsGradeBookButton.setForeground(new java.awt.Color(255, 255, 255));
        groupsGradeBookButton.setText("Grade Book");
        groupsGradeBookButton.setBorder(null);
        groupsGradeBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupsGradeBookButtonActionPerformed(evt);
            }
        });
        bg1.add(groupsGradeBookButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 100, 20));

        groupsLoginButton.setBackground(new java.awt.Color(102, 102, 102));
        groupsLoginButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupsLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        groupsLoginButton.setText("Login");
        groupsLoginButton.setBorder(null);
        groupsLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupsLoginButtonActionPerformed(evt);
            }
        });
        bg1.add(groupsLoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 60, 20));

        groupsDirectButton.setBackground(new java.awt.Color(102, 102, 102));
        groupsDirectButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        groupsDirectButton.setForeground(new java.awt.Color(255, 255, 255));
        groupsDirectButton.setText("Derict DB Access");
        groupsDirectButton.setBorder(null);
        groupsDirectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupsDirectButtonActionPerformed(evt);
            }
        });
        bg1.add(groupsDirectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 130, 20));

        groupsPanel.add(bg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 70));

        getContentPane().add(groupsPanel, "card4");

        timetablePanel.setBackground(new java.awt.Color(255, 255, 255));
        timetablePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        timetablePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        timetableTabel.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        timetableScrollPane.setViewportView(timetableTabel);

        timetablePanel.add(timetableScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 121, 787, 547));

        timetableLabel.setBackground(new java.awt.Color(255, 255, 255));
        timetableLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        timetableLabel.setForeground(new java.awt.Color(102, 102, 102));
        timetableLabel.setText("Group ID");
        timetablePanel.add(timetableLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(47, 173, -1, -1));

        timetableLabel2.setBackground(new java.awt.Color(255, 255, 255));
        timetableLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        timetableLabel2.setForeground(new java.awt.Color(102, 102, 102));
        timetableLabel2.setText("Day");
        timetablePanel.add(timetableLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 173, -1, -1));

        timetableLabel3.setBackground(new java.awt.Color(255, 255, 255));
        timetableLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        timetableLabel3.setForeground(new java.awt.Color(102, 102, 102));
        timetableLabel3.setText("Subject");
        timetablePanel.add(timetableLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 173, -1, -1));

        timetableLabel4.setBackground(new java.awt.Color(255, 255, 255));
        timetableLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        timetableLabel4.setForeground(new java.awt.Color(102, 102, 102));
        timetableLabel4.setText("Start Time");
        timetablePanel.add(timetableLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 259, -1, -1));

        timetableLabel5.setBackground(new java.awt.Color(255, 255, 255));
        timetableLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        timetableLabel5.setForeground(new java.awt.Color(102, 102, 102));
        timetableLabel5.setText("End Time");
        timetablePanel.add(timetableLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 259, -1, -1));

        timetableStartHoursCombo.setEditable(true);
        timetableStartHoursCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableStartHoursCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "09", "10", "11", "12", "13", "14", "15", "16", "17", "18" }));
        timetableStartHoursCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        timetablePanel.add(timetableStartHoursCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, 50, -1));

        timetableLabel6.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableLabel6.setText("Hours");
        timetablePanel.add(timetableLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 287, -1, -1));

        timetableLabel7.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableLabel7.setText("Minutes");
        timetablePanel.add(timetableLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 287, -1, -1));

        timetableStartMinCombo.setEditable(true);
        timetableStartMinCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableStartMinCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "10", "20", "30", "40", "50" }));
        timetableStartMinCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        timetablePanel.add(timetableStartMinCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 313, 44, -1));

        timetableLabel8.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableLabel8.setText("Hours");
        timetablePanel.add(timetableLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 287, -1, -1));

        timetableLabel9.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableLabel9.setText("Minutes");
        timetablePanel.add(timetableLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 287, -1, -1));

        timetableEndHoursCombo.setEditable(true);
        timetableEndHoursCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableEndHoursCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "09", "10", "11", "12", "13", "14", "15", "16", "17", "18" }));
        timetableEndHoursCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        timetablePanel.add(timetableEndHoursCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 313, 50, -1));

        timetableEndMinCombo.setEditable(true);
        timetableEndMinCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableEndMinCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "10", "20", "30", "40", "50" }));
        timetableEndMinCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        timetablePanel.add(timetableEndMinCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 313, 50, -1));

        timetableGroupCombo.setEditable(true);
        timetableGroupCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableGroupCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        timetablePanel.add(timetableGroupCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 207, 91, -1));

        timetableDayCombo.setEditable(true);
        timetableDayCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableDayCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MON", "TUE", "WED", "THU", "FRI" }));
        timetableDayCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        timetablePanel.add(timetableDayCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 207, 72, -1));

        timetableSubjectCombo.setEditable(true);
        timetableSubjectCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableSubjectCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        timetablePanel.add(timetableSubjectCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(309, 207, 111, -1));

        timetableCreateButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableCreateButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableCreateButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableCreateButton.setText("Create");
        timetableCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableCreateButtonActionPerformed(evt);
            }
        });
        timetablePanel.add(timetableCreateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 402, 88, 78));

        TimetableModifyButton.setBackground(new java.awt.Color(102, 102, 102));
        TimetableModifyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        TimetableModifyButton.setForeground(new java.awt.Color(255, 255, 255));
        TimetableModifyButton.setText("Modify");
        TimetableModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimetableModifyButtonActionPerformed(evt);
            }
        });
        timetablePanel.add(TimetableModifyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(176, 402, 98, 78));

        timetableDeleteButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableDeleteButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableDeleteButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableDeleteButton.setText("Delete");
        timetableDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableDeleteButtonActionPerformed(evt);
            }
        });
        timetablePanel.add(timetableDeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(306, 402, 114, 78));

        timetableStudentButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableStudentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableStudentButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableStudentButton.setText("Student");
        timetableStudentButton.setBorder(null);
        timetableStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableStudentButtonActionPerformed(evt);
            }
        });
        timetablePanel.add(timetableStudentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 20));

        bg2.setBackground(new java.awt.Color(102, 102, 102));
        bg2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutButton8.setBackground(new java.awt.Color(102, 102, 102));
        logoutButton8.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logoutButton8.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton8.setText("X");
        logoutButton8.setBorder(null);
        logoutButton8.setFocusPainted(false);
        logoutButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButton8ActionPerformed(evt);
            }
        });
        bg2.add(logoutButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, 40, -1));

        timetableFacultyButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableFacultyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableFacultyButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableFacultyButton.setText("Faculty");
        timetableFacultyButton.setBorder(null);
        timetableFacultyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableFacultyButtonActionPerformed(evt);
            }
        });
        bg2.add(timetableFacultyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 80, 20));

        timetableAssignmentsButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableAssignmentsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableAssignmentsButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableAssignmentsButton.setText("Assignments");
        timetableAssignmentsButton.setBorder(null);
        timetableAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableAssignmentsButtonActionPerformed(evt);
            }
        });
        bg2.add(timetableAssignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 100, 20));

        timetableGroupsButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableGroupsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableGroupsButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableGroupsButton.setText("Groups");
        timetableGroupsButton.setBorder(null);
        timetableGroupsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableGroupsButtonActionPerformed(evt);
            }
        });
        bg2.add(timetableGroupsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 70, 20));

        timetableButton.setBackground(new java.awt.Color(255, 255, 255));
        timetableButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableButton.setForeground(new java.awt.Color(102, 102, 102));
        timetableButton.setText("Timetable");
        timetableButton.setBorder(null);
        bg2.add(timetableButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 90, 40));

        timetableCoursesButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableCoursesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableCoursesButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableCoursesButton.setText("Courses");
        timetableCoursesButton.setBorder(null);
        timetableCoursesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableCoursesButtonActionPerformed(evt);
            }
        });
        bg2.add(timetableCoursesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 80, 20));

        timetableBranchesButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableBranchesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableBranchesButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableBranchesButton.setText("Branches");
        timetableBranchesButton.setBorder(null);
        timetableBranchesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableBranchesButtonActionPerformed(evt);
            }
        });
        bg2.add(timetableBranchesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 90, 20));

        timetableGradeBookButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableGradeBookButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableGradeBookButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableGradeBookButton.setText("Grade Book");
        timetableGradeBookButton.setBorder(null);
        timetableGradeBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableGradeBookButtonActionPerformed(evt);
            }
        });
        bg2.add(timetableGradeBookButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 100, 20));

        timetableLoginButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableLoginButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableLoginButton.setText("Login");
        timetableLoginButton.setBorder(null);
        timetableLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableLoginButtonActionPerformed(evt);
            }
        });
        bg2.add(timetableLoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 60, 20));

        timetableDirectButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableDirectButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        timetableDirectButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableDirectButton.setText("Derict DB Access");
        timetableDirectButton.setBorder(null);
        timetableDirectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableDirectButtonActionPerformed(evt);
            }
        });
        bg2.add(timetableDirectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 130, 20));

        timetablePanel.add(bg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 70));

        getContentPane().add(timetablePanel, "card5");

        coursesPanel.setBackground(new java.awt.Color(255, 255, 255));
        coursesPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        coursesPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        coursesLAbel1.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        coursesLAbel1.setForeground(new java.awt.Color(102, 102, 102));
        coursesLAbel1.setText("Courses");
        coursesPanel.add(coursesLAbel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 115, -1, -1));

        coursesLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        coursesLabel2.setForeground(new java.awt.Color(102, 102, 102));
        coursesLabel2.setText("Payments");
        coursesPanel.add(coursesLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(918, 115, -1, -1));

        coursesTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        coursesScrollPane.setViewportView(coursesTable);

        coursesPanel.add(coursesScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 148, 544, 328));

        paymentsTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        paymentsScrollPane.setViewportView(paymentsTable);

        coursesPanel.add(paymentsScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 148, 554, 328));

        coursesesLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        coursesesLabel3.setForeground(new java.awt.Color(102, 102, 102));
        coursesesLabel3.setText("Course ID");
        coursesPanel.add(coursesesLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 519, -1, -1));

        coursesLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        coursesLabel4.setForeground(new java.awt.Color(102, 102, 102));
        coursesLabel4.setText("Description");
        coursesPanel.add(coursesLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(216, 519, -1, -1));

        coursesLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        coursesLabel5.setForeground(new java.awt.Color(102, 102, 102));
        coursesLabel5.setText("Price");
        coursesPanel.add(coursesLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(426, 519, -1, -1));

        coursesLabel6.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        coursesLabel6.setForeground(new java.awt.Color(102, 102, 102));
        coursesLabel6.setText("Student ID");
        coursesPanel.add(coursesLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(752, 519, -1, -1));

        coursesLablel7.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        coursesLablel7.setForeground(new java.awt.Color(102, 102, 102));
        coursesLablel7.setText("Requiered");
        coursesPanel.add(coursesLablel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 519, -1, -1));

        coursesLabel8.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        coursesLabel8.setForeground(new java.awt.Color(102, 102, 102));
        coursesLabel8.setText("Payed");
        coursesPanel.add(coursesLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 520, -1, -1));

        courseIdField.setBackground(new java.awt.Color(255, 255, 255));
        courseIdField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        courseIdField.setBorder(null);
        coursesPanel.add(courseIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 553, 110, -1));

        courseArea.setColumns(20);
        courseArea.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        courseArea.setLineWrap(true);
        courseArea.setRows(2);
        courseArea.setTabSize(2);
        courseArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane11.setViewportView(courseArea);

        coursesPanel.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 553, 164, 71));

        coursePriceField.setBackground(new java.awt.Color(255, 255, 255));
        coursePriceField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursePriceField.setBorder(null);
        coursesPanel.add(coursePriceField, new org.netbeans.lib.awtextra.AbsoluteConstraints(388, 553, 108, -1));

        coursesFindButton.setBackground(new java.awt.Color(102, 102, 102));
        coursesFindButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesFindButton.setForeground(new java.awt.Color(255, 255, 255));
        coursesFindButton.setText("Find By ID");
        coursesFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesFindButtonActionPerformed(evt);
            }
        });
        coursesPanel.add(coursesFindButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 590, 109, 82));

        coursesCreateButton.setBackground(new java.awt.Color(102, 102, 102));
        coursesCreateButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesCreateButton.setForeground(new java.awt.Color(255, 255, 255));
        coursesCreateButton.setText("Create");
        coursesCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesCreateButtonActionPerformed(evt);
            }
        });
        coursesPanel.add(coursesCreateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 631, -1, 41));

        coursesDeleteButton.setBackground(new java.awt.Color(102, 102, 102));
        coursesDeleteButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesDeleteButton.setForeground(new java.awt.Color(255, 255, 255));
        coursesDeleteButton.setText("Delete");
        coursesDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesDeleteButtonActionPerformed(evt);
            }
        });
        coursesPanel.add(coursesDeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 631, 75, 41));

        coursesModifyButton.setBackground(new java.awt.Color(102, 102, 102));
        coursesModifyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesModifyButton.setForeground(new java.awt.Color(255, 255, 255));
        coursesModifyButton.setText("Modify");
        coursesModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesModifyButtonActionPerformed(evt);
            }
        });
        coursesPanel.add(coursesModifyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(388, 590, 108, 79));

        coursesStidentIdField.setBackground(new java.awt.Color(255, 255, 255));
        coursesStidentIdField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesStidentIdField.setBorder(null);
        coursesPanel.add(coursesStidentIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(739, 553, 99, -1));

        coursesRequiredField.setBackground(new java.awt.Color(255, 255, 255));
        coursesRequiredField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesRequiredField.setBorder(null);
        coursesPanel.add(coursesRequiredField, new org.netbeans.lib.awtextra.AbsoluteConstraints(928, 553, 89, -1));

        coursesPAyedField.setBackground(new java.awt.Color(255, 255, 255));
        coursesPAyedField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesPAyedField.setBorder(null);
        coursesPanel.add(coursesPAyedField, new org.netbeans.lib.awtextra.AbsoluteConstraints(1106, 553, 95, -1));

        paymentsFindButton.setBackground(new java.awt.Color(102, 102, 102));
        paymentsFindButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        paymentsFindButton.setForeground(new java.awt.Color(255, 255, 255));
        paymentsFindButton.setText("Find By ID");
        paymentsFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentsFindButtonActionPerformed(evt);
            }
        });
        coursesPanel.add(paymentsFindButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(739, 590, -1, 79));

        paymentsCreateButton.setBackground(new java.awt.Color(102, 102, 102));
        paymentsCreateButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        paymentsCreateButton.setForeground(new java.awt.Color(255, 255, 255));
        paymentsCreateButton.setText("Create");
        paymentsCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentsCreateButtonActionPerformed(evt);
            }
        });
        coursesPanel.add(paymentsCreateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(928, 590, 89, 36));

        paymentsDeleteButton.setBackground(new java.awt.Color(102, 102, 102));
        paymentsDeleteButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        paymentsDeleteButton.setForeground(new java.awt.Color(255, 255, 255));
        paymentsDeleteButton.setText("Delete");
        paymentsDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentsDeleteButtonActionPerformed(evt);
            }
        });
        coursesPanel.add(paymentsDeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(928, 633, 89, 36));

        paymentsModifyButton.setBackground(new java.awt.Color(102, 102, 102));
        paymentsModifyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        paymentsModifyButton.setForeground(new java.awt.Color(255, 255, 255));
        paymentsModifyButton.setText("Modify");
        paymentsModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentsModifyButtonActionPerformed(evt);
            }
        });
        coursesPanel.add(paymentsModifyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1106, 593, 95, 76));

        jSeparator14.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator14.setForeground(new java.awt.Color(0, 0, 0));
        coursesPanel.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 580, 110, 10));

        jSeparator15.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator15.setForeground(new java.awt.Color(0, 0, 0));
        coursesPanel.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 580, 110, 10));

        jSeparator16.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator16.setForeground(new java.awt.Color(0, 0, 0));
        coursesPanel.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 580, 100, 10));

        jSeparator17.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator17.setForeground(new java.awt.Color(0, 0, 0));
        coursesPanel.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 580, 90, 10));

        jSeparator18.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator18.setForeground(new java.awt.Color(0, 0, 0));
        coursesPanel.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 580, 100, 10));

        coursesStudentButton.setBackground(new java.awt.Color(102, 102, 102));
        coursesStudentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesStudentButton.setForeground(new java.awt.Color(255, 255, 255));
        coursesStudentButton.setText("Student");
        coursesStudentButton.setBorder(null);
        coursesStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesStudentButtonActionPerformed(evt);
            }
        });
        coursesPanel.add(coursesStudentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 20));

        bg3.setBackground(new java.awt.Color(102, 102, 102));
        bg3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutButton7.setBackground(new java.awt.Color(102, 102, 102));
        logoutButton7.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logoutButton7.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton7.setText("X");
        logoutButton7.setBorder(null);
        logoutButton7.setFocusPainted(false);
        logoutButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButton7ActionPerformed(evt);
            }
        });
        bg3.add(logoutButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, 40, -1));

        coursesFacultyButton.setBackground(new java.awt.Color(102, 102, 102));
        coursesFacultyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesFacultyButton.setForeground(new java.awt.Color(255, 255, 255));
        coursesFacultyButton.setText("Faculty");
        coursesFacultyButton.setBorder(null);
        coursesFacultyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesFacultyButtonActionPerformed(evt);
            }
        });
        bg3.add(coursesFacultyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 80, 20));

        coursesAssignmentsButton.setBackground(new java.awt.Color(102, 102, 102));
        coursesAssignmentsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesAssignmentsButton.setForeground(new java.awt.Color(255, 255, 255));
        coursesAssignmentsButton.setText("Assignments");
        coursesAssignmentsButton.setBorder(null);
        coursesAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesAssignmentsButtonActionPerformed(evt);
            }
        });
        bg3.add(coursesAssignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 100, 20));

        coursesGroupsButton.setBackground(new java.awt.Color(102, 102, 102));
        coursesGroupsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesGroupsButton.setForeground(new java.awt.Color(255, 255, 255));
        coursesGroupsButton.setText("Groups");
        coursesGroupsButton.setBorder(null);
        coursesGroupsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesGroupsButtonActionPerformed(evt);
            }
        });
        bg3.add(coursesGroupsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 70, 20));

        coursesTimetableButton.setBackground(new java.awt.Color(102, 102, 102));
        coursesTimetableButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesTimetableButton.setForeground(new java.awt.Color(255, 255, 255));
        coursesTimetableButton.setText("Timetable");
        coursesTimetableButton.setBorder(null);
        coursesTimetableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesTimetableButtonActionPerformed(evt);
            }
        });
        bg3.add(coursesTimetableButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 90, 20));

        coursesButton.setBackground(new java.awt.Color(255, 255, 255));
        coursesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesButton.setForeground(new java.awt.Color(102, 102, 102));
        coursesButton.setText("Courses");
        coursesButton.setBorder(null);
        bg3.add(coursesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 80, 40));

        coursesBranchesButton.setBackground(new java.awt.Color(102, 102, 102));
        coursesBranchesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesBranchesButton.setForeground(new java.awt.Color(255, 255, 255));
        coursesBranchesButton.setText("Branches");
        coursesBranchesButton.setBorder(null);
        coursesBranchesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesBranchesButtonActionPerformed(evt);
            }
        });
        bg3.add(coursesBranchesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 90, 20));

        coursesGradeBookButton.setBackground(new java.awt.Color(102, 102, 102));
        coursesGradeBookButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesGradeBookButton.setForeground(new java.awt.Color(255, 255, 255));
        coursesGradeBookButton.setText("Grade Book");
        coursesGradeBookButton.setBorder(null);
        coursesGradeBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesGradeBookButtonActionPerformed(evt);
            }
        });
        bg3.add(coursesGradeBookButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 100, 20));

        coursesLoginButton.setBackground(new java.awt.Color(102, 102, 102));
        coursesLoginButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        coursesLoginButton.setText("Login");
        coursesLoginButton.setBorder(null);
        coursesLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesLoginButtonActionPerformed(evt);
            }
        });
        bg3.add(coursesLoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 60, 20));

        coursesDirectButton.setBackground(new java.awt.Color(102, 102, 102));
        coursesDirectButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        coursesDirectButton.setForeground(new java.awt.Color(255, 255, 255));
        coursesDirectButton.setText("Derict DB Access");
        coursesDirectButton.setBorder(null);
        coursesDirectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesDirectButtonActionPerformed(evt);
            }
        });
        bg3.add(coursesDirectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 130, 20));

        coursesPanel.add(bg3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 70));

        getContentPane().add(coursesPanel, "card6");

        branchesPanel.setBackground(new java.awt.Color(255, 255, 255));
        branchesPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        branchesPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        branchLabel1.setBackground(new java.awt.Color(255, 255, 255));
        branchLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        branchLabel1.setForeground(new java.awt.Color(102, 102, 102));
        branchLabel1.setText("Branches");
        branchesPanel.add(branchLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 125, -1, -1));

        branchLabel2.setBackground(new java.awt.Color(255, 255, 255));
        branchLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        branchLabel2.setForeground(new java.awt.Color(102, 102, 102));
        branchLabel2.setText("Courses");
        branchesPanel.add(branchLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(962, 125, -1, -1));

        branchDetailsTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        branchDetailsScrollPane.setViewportView(branchDetailsTable);

        branchesPanel.add(branchDetailsScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 158, 526, 343));

        branchTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        branchScrollPane.setViewportView(branchTable);

        branchesPanel.add(branchScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(785, 158, 421, 343));

        branchLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        branchLabel3.setForeground(new java.awt.Color(102, 102, 102));
        branchLabel3.setText("Branch ID");
        branchesPanel.add(branchLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 535, -1, -1));

        branchLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        branchLabel4.setForeground(new java.awt.Color(102, 102, 102));
        branchLabel4.setText("Branch Name");
        branchesPanel.add(branchLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 535, -1, -1));

        branchLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        branchLabel5.setForeground(new java.awt.Color(102, 102, 102));
        branchLabel5.setText("Branch Address");
        branchesPanel.add(branchLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(436, 535, -1, -1));

        branchLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        branchLabel6.setForeground(new java.awt.Color(102, 102, 102));
        branchLabel6.setText("Branch ID");
        branchesPanel.add(branchLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(865, 535, -1, -1));

        branchLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        branchLabel7.setForeground(new java.awt.Color(102, 102, 102));
        branchLabel7.setText("Course ID");
        branchesPanel.add(branchLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1046, 535, -1, -1));

        courseIdCombo.setEditable(true);
        courseIdCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        courseIdCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        branchesPanel.add(courseIdCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 559, 93, -1));

        branchIdCombo.setEditable(true);
        branchIdCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchIdCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        branchesPanel.add(branchIdCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(839, 559, 103, -1));

        branchIDField.setBackground(new java.awt.Color(255, 255, 255));
        branchIDField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchIDField.setBorder(null);
        branchesPanel.add(branchIDField, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 559, 99, -1));

        branchNameField.setBackground(new java.awt.Color(255, 255, 255));
        branchNameField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchNameField.setBorder(null);
        branchesPanel.add(branchNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 559, 120, -1));

        branchAddressArea.setColumns(20);
        branchAddressArea.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchAddressArea.setLineWrap(true);
        branchAddressArea.setRows(2);
        branchAddressArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane14.setViewportView(branchAddressArea);

        branchesPanel.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(406, 559, 169, 70));

        findBranchButton.setBackground(new java.awt.Color(102, 102, 102));
        findBranchButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        findBranchButton.setForeground(new java.awt.Color(255, 255, 255));
        findBranchButton.setText("Find by ID");
        findBranchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findBranchButtonActionPerformed(evt);
            }
        });
        branchesPanel.add(findBranchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 597, -1, 82));

        modifyBranchButton.setBackground(new java.awt.Color(102, 102, 102));
        modifyBranchButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        modifyBranchButton.setForeground(new java.awt.Color(255, 255, 255));
        modifyBranchButton.setText("Modify");
        modifyBranchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyBranchButtonActionPerformed(evt);
            }
        });
        branchesPanel.add(modifyBranchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 597, 120, 82));

        createBranchButton.setBackground(new java.awt.Color(102, 102, 102));
        createBranchButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        createBranchButton.setForeground(new java.awt.Color(255, 255, 255));
        createBranchButton.setText("Create");
        createBranchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBranchButtonActionPerformed(evt);
            }
        });
        branchesPanel.add(createBranchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(406, 636, 77, 40));

        deleteBranchButton.setBackground(new java.awt.Color(102, 102, 102));
        deleteBranchButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        deleteBranchButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteBranchButton.setText("Delete");
        deleteBranchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBranchButtonActionPerformed(evt);
            }
        });
        branchesPanel.add(deleteBranchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 636, 80, 40));

        createBranchButton2.setBackground(new java.awt.Color(102, 102, 102));
        createBranchButton2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        createBranchButton2.setForeground(new java.awt.Color(255, 255, 255));
        createBranchButton2.setText("Create");
        createBranchButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBranchButton2ActionPerformed(evt);
            }
        });
        branchesPanel.add(createBranchButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(839, 597, 103, 82));

        deleteBranchButton2.setBackground(new java.awt.Color(102, 102, 102));
        deleteBranchButton2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        deleteBranchButton2.setForeground(new java.awt.Color(255, 255, 255));
        deleteBranchButton2.setText("Delete");
        deleteBranchButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBranchButton2ActionPerformed(evt);
            }
        });
        branchesPanel.add(deleteBranchButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1031, 597, 92, 82));

        jSeparator19.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator19.setForeground(new java.awt.Color(0, 0, 0));
        branchesPanel.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 580, 100, 10));

        jSeparator20.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator20.setForeground(new java.awt.Color(0, 0, 0));
        branchesPanel.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 580, 120, 10));

        branchesStudentButton.setBackground(new java.awt.Color(102, 102, 102));
        branchesStudentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchesStudentButton.setForeground(new java.awt.Color(255, 255, 255));
        branchesStudentButton.setText("Student");
        branchesStudentButton.setBorder(null);
        branchesStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branchesStudentButtonActionPerformed(evt);
            }
        });
        branchesPanel.add(branchesStudentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 20));

        bg4.setBackground(new java.awt.Color(102, 102, 102));
        bg4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutButton6.setBackground(new java.awt.Color(102, 102, 102));
        logoutButton6.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logoutButton6.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton6.setText("X");
        logoutButton6.setBorder(null);
        logoutButton6.setFocusPainted(false);
        logoutButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButton6ActionPerformed(evt);
            }
        });
        bg4.add(logoutButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, 40, -1));

        branchesFacultyButton.setBackground(new java.awt.Color(102, 102, 102));
        branchesFacultyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchesFacultyButton.setForeground(new java.awt.Color(255, 255, 255));
        branchesFacultyButton.setText("Faculty");
        branchesFacultyButton.setBorder(null);
        branchesFacultyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branchesFacultyButtonActionPerformed(evt);
            }
        });
        bg4.add(branchesFacultyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 80, 20));

        branchesAssignmentsButton.setBackground(new java.awt.Color(102, 102, 102));
        branchesAssignmentsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchesAssignmentsButton.setForeground(new java.awt.Color(255, 255, 255));
        branchesAssignmentsButton.setText("Assignments");
        branchesAssignmentsButton.setBorder(null);
        branchesAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branchesAssignmentsButtonActionPerformed(evt);
            }
        });
        bg4.add(branchesAssignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 100, 20));

        branchesGroupsButton.setBackground(new java.awt.Color(102, 102, 102));
        branchesGroupsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchesGroupsButton.setForeground(new java.awt.Color(255, 255, 255));
        branchesGroupsButton.setText("Groups");
        branchesGroupsButton.setBorder(null);
        branchesGroupsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branchesGroupsButtonActionPerformed(evt);
            }
        });
        bg4.add(branchesGroupsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 70, 20));

        branchesTimetableButton.setBackground(new java.awt.Color(102, 102, 102));
        branchesTimetableButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchesTimetableButton.setForeground(new java.awt.Color(255, 255, 255));
        branchesTimetableButton.setText("Timetable");
        branchesTimetableButton.setBorder(null);
        branchesTimetableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branchesTimetableButtonActionPerformed(evt);
            }
        });
        bg4.add(branchesTimetableButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 90, 20));

        branchesCoursesButton.setBackground(new java.awt.Color(102, 102, 102));
        branchesCoursesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchesCoursesButton.setForeground(new java.awt.Color(255, 255, 255));
        branchesCoursesButton.setText("Courses");
        branchesCoursesButton.setBorder(null);
        branchesCoursesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branchesCoursesButtonActionPerformed(evt);
            }
        });
        bg4.add(branchesCoursesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 80, 20));

        branchesButton.setBackground(new java.awt.Color(255, 255, 255));
        branchesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchesButton.setForeground(new java.awt.Color(102, 102, 102));
        branchesButton.setText("Branches");
        branchesButton.setBorder(null);
        bg4.add(branchesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 90, 40));

        branchesGradeBookButton.setBackground(new java.awt.Color(102, 102, 102));
        branchesGradeBookButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchesGradeBookButton.setForeground(new java.awt.Color(255, 255, 255));
        branchesGradeBookButton.setText("Grade Book");
        branchesGradeBookButton.setBorder(null);
        branchesGradeBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branchesGradeBookButtonActionPerformed(evt);
            }
        });
        bg4.add(branchesGradeBookButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 100, 20));

        branchesLoginButton.setBackground(new java.awt.Color(102, 102, 102));
        branchesLoginButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchesLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        branchesLoginButton.setText("Login");
        branchesLoginButton.setBorder(null);
        branchesLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branchesLoginButtonActionPerformed(evt);
            }
        });
        bg4.add(branchesLoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 60, 20));

        branchesDirectButton.setBackground(new java.awt.Color(102, 102, 102));
        branchesDirectButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        branchesDirectButton.setForeground(new java.awt.Color(255, 255, 255));
        branchesDirectButton.setText("Derict DB Access");
        branchesDirectButton.setBorder(null);
        branchesDirectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branchesDirectButtonActionPerformed(evt);
            }
        });
        bg4.add(branchesDirectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 130, 20));

        branchesPanel.add(bg4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 70));

        getContentPane().add(branchesPanel, "card7");

        gradesPanel.setBackground(new java.awt.Color(255, 255, 255));
        gradesPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        gradesPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        gradeBookLabel1.setBackground(new java.awt.Color(255, 255, 255));
        gradeBookLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        gradeBookLabel1.setForeground(new java.awt.Color(102, 102, 102));
        gradeBookLabel1.setText("Academic Progress");
        gradesPanel.add(gradeBookLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 120, -1, -1));

        gradeBookLabel2.setBackground(new java.awt.Color(255, 255, 255));
        gradeBookLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        gradeBookLabel2.setForeground(new java.awt.Color(102, 102, 102));
        gradeBookLabel2.setText("Assignment Grades");
        gradesPanel.add(gradeBookLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(862, 120, -1, -1));

        gradesTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        gradesScrollPane.setViewportView(gradesTable);

        gradesPanel.add(gradesScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 153, 563, 344));

        assignmentGradesTabel.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        assignmentGradesScrollPane.setViewportView(assignmentGradesTabel);

        gradesPanel.add(assignmentGradesScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(672, 153, 530, 344));

        gradeBookLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        gradeBookLabel3.setForeground(new java.awt.Color(102, 102, 102));
        gradeBookLabel3.setText("Studentd ID");
        gradesPanel.add(gradeBookLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 515, -1, -1));

        gradeBookLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        gradeBookLabel4.setForeground(new java.awt.Color(102, 102, 102));
        gradeBookLabel4.setText("Subject ID");
        gradesPanel.add(gradeBookLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(229, 515, -1, -1));

        gradeBookLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        gradeBookLabel5.setForeground(new java.awt.Color(102, 102, 102));
        gradeBookLabel5.setText("Grade");
        gradesPanel.add(gradeBookLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(363, 515, -1, -1));

        gradeBookLabel6.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        gradeBookLabel6.setForeground(new java.awt.Color(102, 102, 102));
        gradeBookLabel6.setText("Exam Grade");
        gradesPanel.add(gradeBookLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 515, -1, -1));

        studentCombo1.setEditable(true);
        studentCombo1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studentCombo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gradesPanel.add(studentCombo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 543, 115, -1));

        subjectCombo1.setEditable(true);
        subjectCombo1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        subjectCombo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gradesPanel.add(subjectCombo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 543, 109, -1));

        gradeField1.setBackground(new java.awt.Color(255, 255, 255));
        gradeField1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradeField1.setBorder(null);
        gradesPanel.add(gradeField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(346, 543, 77, -1));

        examGradeField1.setBackground(new java.awt.Color(255, 255, 255));
        examGradeField1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        examGradeField1.setBorder(null);
        gradesPanel.add(examGradeField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 543, 79, -1));

        gradeBookLabel7.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        gradeBookLabel7.setForeground(new java.awt.Color(102, 102, 102));
        gradeBookLabel7.setText("Assignment ID");
        gradesPanel.add(gradeBookLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(721, 515, 99, -1));

        gradeBookLabel8.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        gradeBookLabel8.setForeground(new java.awt.Color(102, 102, 102));
        gradeBookLabel8.setText("Student ID");
        gradesPanel.add(gradeBookLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(901, 515, -1, -1));

        gradeBookLabel9.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        gradeBookLabel9.setForeground(new java.awt.Color(102, 102, 102));
        gradeBookLabel9.setText("Grade");
        gradesPanel.add(gradeBookLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1078, 515, -1, -1));

        assignmentCombo2.setEditable(true);
        assignmentCombo2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentCombo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gradesPanel.add(assignmentCombo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(721, 543, 99, -1));

        studentCombo2.setEditable(true);
        studentCombo2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studentCombo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gradesPanel.add(studentCombo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(884, 543, 98, -1));

        gradeField2.setBackground(new java.awt.Color(255, 255, 255));
        gradeField2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradeField2.setBorder(null);
        gradesPanel.add(gradeField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1045, 543, 95, -1));

        gradesFindButton.setBackground(new java.awt.Color(102, 102, 102));
        gradesFindButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradesFindButton.setForeground(new java.awt.Color(255, 255, 255));
        gradesFindButton.setText("Find By ID");
        gradesFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradesFindButtonActionPerformed(evt);
            }
        });
        gradesPanel.add(gradesFindButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 586, 115, 93));

        gradesModifyButton.setBackground(new java.awt.Color(102, 102, 102));
        gradesModifyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradesModifyButton.setForeground(new java.awt.Color(255, 255, 255));
        gradesModifyButton.setText("Modify");
        gradesModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradesModifyButtonActionPerformed(evt);
            }
        });
        gradesPanel.add(gradesModifyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 586, 109, 93));

        gradesCreateButton.setBackground(new java.awt.Color(102, 102, 102));
        gradesCreateButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradesCreateButton.setForeground(new java.awt.Color(255, 255, 255));
        gradesCreateButton.setText("Create");
        gradesCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradesCreateButtonActionPerformed(evt);
            }
        });
        gradesPanel.add(gradesCreateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(346, 586, 77, 93));

        gradesDeleteButton.setBackground(new java.awt.Color(102, 102, 102));
        gradesDeleteButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradesDeleteButton.setForeground(new java.awt.Color(255, 255, 255));
        gradesDeleteButton.setText("Delete");
        gradesDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradesDeleteButtonActionPerformed(evt);
            }
        });
        gradesPanel.add(gradesDeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 586, 79, 93));

        assignmentGradesFindButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentGradesFindButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentGradesFindButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentGradesFindButton.setText("Find By ID");
        assignmentGradesFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentGradesFindButtonActionPerformed(evt);
            }
        });
        gradesPanel.add(assignmentGradesFindButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(721, 586, -1, 93));

        assignmentGradesModifybutton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentGradesModifybutton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentGradesModifybutton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentGradesModifybutton.setText("Modify");
        assignmentGradesModifybutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentGradesModifybuttonActionPerformed(evt);
            }
        });
        gradesPanel.add(assignmentGradesModifybutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1045, 586, 95, 93));

        assignmentGradesCreateButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentGradesCreateButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentGradesCreateButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentGradesCreateButton.setText("Create");
        assignmentGradesCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentGradesCreateButtonActionPerformed(evt);
            }
        });
        gradesPanel.add(assignmentGradesCreateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(884, 586, 98, 40));

        assignmentGradesDeleteButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentGradesDeleteButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentGradesDeleteButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentGradesDeleteButton.setText("Delete");
        assignmentGradesDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentGradesDeleteButtonActionPerformed(evt);
            }
        });
        gradesPanel.add(assignmentGradesDeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(884, 639, 98, 40));

        jSeparator21.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator21.setForeground(new java.awt.Color(0, 0, 0));
        gradesPanel.add(jSeparator21, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 570, 70, 10));

        jSeparator22.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator22.setForeground(new java.awt.Color(0, 0, 0));
        gradesPanel.add(jSeparator22, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 570, 80, 10));

        jSeparator23.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator23.setForeground(new java.awt.Color(0, 0, 0));
        gradesPanel.add(jSeparator23, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 570, 100, 10));

        bg5.setBackground(new java.awt.Color(102, 102, 102));
        bg5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutButton5.setBackground(new java.awt.Color(102, 102, 102));
        logoutButton5.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logoutButton5.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton5.setText("X");
        logoutButton5.setBorder(null);
        logoutButton5.setFocusPainted(false);
        logoutButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButton5ActionPerformed(evt);
            }
        });
        bg5.add(logoutButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, 40, -1));

        gradeBookFacultyButton.setBackground(new java.awt.Color(102, 102, 102));
        gradeBookFacultyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradeBookFacultyButton.setForeground(new java.awt.Color(255, 255, 255));
        gradeBookFacultyButton.setText("Faculty");
        gradeBookFacultyButton.setBorder(null);
        gradeBookFacultyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeBookFacultyButtonActionPerformed(evt);
            }
        });
        bg5.add(gradeBookFacultyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 80, 20));

        gradeBookAssignmentsButton.setBackground(new java.awt.Color(102, 102, 102));
        gradeBookAssignmentsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradeBookAssignmentsButton.setForeground(new java.awt.Color(255, 255, 255));
        gradeBookAssignmentsButton.setText("Assignments");
        gradeBookAssignmentsButton.setBorder(null);
        gradeBookAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeBookAssignmentsButtonActionPerformed(evt);
            }
        });
        bg5.add(gradeBookAssignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 100, 20));

        gradeBookGroupsButton.setBackground(new java.awt.Color(102, 102, 102));
        gradeBookGroupsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradeBookGroupsButton.setForeground(new java.awt.Color(255, 255, 255));
        gradeBookGroupsButton.setText("Groups");
        gradeBookGroupsButton.setBorder(null);
        gradeBookGroupsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeBookGroupsButtonActionPerformed(evt);
            }
        });
        bg5.add(gradeBookGroupsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 70, 20));

        gradeBookTimetableButton.setBackground(new java.awt.Color(102, 102, 102));
        gradeBookTimetableButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradeBookTimetableButton.setForeground(new java.awt.Color(255, 255, 255));
        gradeBookTimetableButton.setText("Timetable");
        gradeBookTimetableButton.setBorder(null);
        gradeBookTimetableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeBookTimetableButtonActionPerformed(evt);
            }
        });
        bg5.add(gradeBookTimetableButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 90, 20));

        gradeBookCoursesButton.setBackground(new java.awt.Color(102, 102, 102));
        gradeBookCoursesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradeBookCoursesButton.setForeground(new java.awt.Color(255, 255, 255));
        gradeBookCoursesButton.setText("Courses");
        gradeBookCoursesButton.setBorder(null);
        gradeBookCoursesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeBookCoursesButtonActionPerformed(evt);
            }
        });
        bg5.add(gradeBookCoursesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 80, 20));

        gradeBookBranchesButton.setBackground(new java.awt.Color(102, 102, 102));
        gradeBookBranchesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradeBookBranchesButton.setForeground(new java.awt.Color(255, 255, 255));
        gradeBookBranchesButton.setText("Branches");
        gradeBookBranchesButton.setBorder(null);
        gradeBookBranchesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeBookBranchesButtonActionPerformed(evt);
            }
        });
        bg5.add(gradeBookBranchesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 90, 20));

        jButton63.setBackground(new java.awt.Color(255, 255, 255));
        jButton63.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        jButton63.setForeground(new java.awt.Color(102, 102, 102));
        jButton63.setText("Grade Book");
        jButton63.setBorder(null);
        bg5.add(jButton63, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 100, 40));

        gradeBookLoginButton.setBackground(new java.awt.Color(102, 102, 102));
        gradeBookLoginButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradeBookLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        gradeBookLoginButton.setText("Login");
        gradeBookLoginButton.setBorder(null);
        gradeBookLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeBookLoginButtonActionPerformed(evt);
            }
        });
        bg5.add(gradeBookLoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 60, 20));

        gradeBookDirectButton.setBackground(new java.awt.Color(102, 102, 102));
        gradeBookDirectButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradeBookDirectButton.setForeground(new java.awt.Color(255, 255, 255));
        gradeBookDirectButton.setText("Derict DB Access");
        gradeBookDirectButton.setBorder(null);
        gradeBookDirectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeBookDirectButtonActionPerformed(evt);
            }
        });
        bg5.add(gradeBookDirectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 130, 20));

        gradeBookStudentButton.setBackground(new java.awt.Color(102, 102, 102));
        gradeBookStudentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        gradeBookStudentButton.setForeground(new java.awt.Color(255, 255, 255));
        gradeBookStudentButton.setText("Student");
        gradeBookStudentButton.setBorder(null);
        gradeBookStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeBookStudentButtonActionPerformed(evt);
            }
        });
        bg5.add(gradeBookStudentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 20));

        gradesPanel.add(bg5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 70));

        getContentPane().add(gradesPanel, "card8");

        assignmentPanel.setBackground(new java.awt.Color(255, 255, 255));
        assignmentPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        assignmentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        assignmentLabel4.setBackground(new java.awt.Color(255, 255, 255));
        assignmentLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        assignmentLabel4.setForeground(new java.awt.Color(102, 102, 102));
        assignmentLabel4.setText("Assignments");
        assignmentPanel.add(assignmentLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(818, 150, -1, -1));

        assignmentsTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        assignmentsScrollPane.setViewportView(assignmentsTable);

        assignmentPanel.add(assignmentsScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(519, 194, 731, 485));

        assignmentLabel1.setBackground(new java.awt.Color(255, 255, 255));
        assignmentLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentLabel1.setForeground(new java.awt.Color(102, 102, 102));
        assignmentLabel1.setText("Course ID");
        assignmentPanel.add(assignmentLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 194, -1, -1));

        assignmentLabel2.setBackground(new java.awt.Color(255, 255, 255));
        assignmentLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentLabel2.setForeground(new java.awt.Color(102, 102, 102));
        assignmentLabel2.setText("Assignment ID");
        assignmentPanel.add(assignmentLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 194, -1, -1));

        assignmentLabel3.setBackground(new java.awt.Color(255, 255, 255));
        assignmentLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentLabel3.setForeground(new java.awt.Color(102, 102, 102));
        assignmentLabel3.setText("Subject ID");
        assignmentPanel.add(assignmentLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 194, -1, -1));

        assignmentCourseCombo.setEditable(true);
        assignmentCourseCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentCourseCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        assignmentPanel.add(assignmentCourseCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 233, 89, -1));

        assignmentField.setBackground(new java.awt.Color(255, 255, 255));
        assignmentField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentField.setBorder(null);
        assignmentPanel.add(assignmentField, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 235, 96, -1));

        assignmentSubjectCombo.setEditable(true);
        assignmentSubjectCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentSubjectCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        assignmentPanel.add(assignmentSubjectCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 233, 97, -1));

        assignmentLabel5.setBackground(new java.awt.Color(255, 255, 255));
        assignmentLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentLabel5.setForeground(new java.awt.Color(102, 102, 102));
        assignmentLabel5.setText("Visible");
        assignmentPanel.add(assignmentLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 304, -1, -1));

        assignmentLabel6.setBackground(new java.awt.Color(255, 255, 255));
        assignmentLabel6.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentLabel6.setForeground(new java.awt.Color(102, 102, 102));
        assignmentLabel6.setText("Due Date");
        assignmentPanel.add(assignmentLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 304, -1, -1));

        assignmentVisibleCombo.setEditable(true);
        assignmentVisibleCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentVisibleCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "yes", "no" }));
        assignmentVisibleCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        assignmentPanel.add(assignmentVisibleCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 332, 50, -1));

        assignmentDatePicker.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        assignmentPanel.add(assignmentDatePicker, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 332, 126, -1));

        assignmentCreateButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentCreateButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentCreateButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentCreateButton.setText("Create");
        assignmentCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentCreateButtonActionPerformed(evt);
            }
        });
        assignmentPanel.add(assignmentCreateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 428, 110, 60));

        assignmentDeleteButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentDeleteButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentDeleteButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentDeleteButton.setText("Delete");
        assignmentDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentDeleteButtonActionPerformed(evt);
            }
        });
        assignmentPanel.add(assignmentDeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 428, 110, 60));

        assignmentModifyButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentModifyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentModifyButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentModifyButton.setText("Modify");
        assignmentModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentModifyButtonActionPerformed(evt);
            }
        });
        assignmentPanel.add(assignmentModifyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 428, 110, 60));

        assignmentFindButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentFindButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentFindButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentFindButton.setText("Find By Assignment ID");
        assignmentFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentFindButtonActionPerformed(evt);
            }
        });
        assignmentPanel.add(assignmentFindButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 527, 183, 73));

        assignmentRefreshButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentRefreshButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentRefreshButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentRefreshButton.setText("Refresh Table");
        assignmentRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentRefreshButtonActionPerformed(evt);
            }
        });
        assignmentPanel.add(assignmentRefreshButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 527, 178, 73));

        jSeparator24.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator24.setForeground(new java.awt.Color(0, 0, 0));
        assignmentPanel.add(jSeparator24, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 100, 10));

        bg6.setBackground(new java.awt.Color(102, 102, 102));
        bg6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutButton4.setBackground(new java.awt.Color(102, 102, 102));
        logoutButton4.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logoutButton4.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton4.setText("X");
        logoutButton4.setBorder(null);
        logoutButton4.setFocusPainted(false);
        logoutButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButton4ActionPerformed(evt);
            }
        });
        bg6.add(logoutButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, 40, -1));

        assignmentsFacultyButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentsFacultyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentsFacultyButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentsFacultyButton.setText("Faculty");
        assignmentsFacultyButton.setBorder(null);
        assignmentsFacultyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsFacultyButtonActionPerformed(evt);
            }
        });
        bg6.add(assignmentsFacultyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 80, 20));

        assignmentsButton.setBackground(new java.awt.Color(255, 255, 255));
        assignmentsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentsButton.setForeground(new java.awt.Color(102, 102, 102));
        assignmentsButton.setText("Assignments");
        assignmentsButton.setBorder(null);
        bg6.add(assignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 100, 40));

        assignmentsGroupsButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentsGroupsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentsGroupsButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentsGroupsButton.setText("Groups");
        assignmentsGroupsButton.setBorder(null);
        assignmentsGroupsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsGroupsButtonActionPerformed(evt);
            }
        });
        bg6.add(assignmentsGroupsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 70, 20));

        assignmentsTimetableButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentsTimetableButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentsTimetableButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentsTimetableButton.setText("Timetable");
        assignmentsTimetableButton.setBorder(null);
        assignmentsTimetableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsTimetableButtonActionPerformed(evt);
            }
        });
        bg6.add(assignmentsTimetableButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 90, 20));

        assignmentsCoursesButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentsCoursesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentsCoursesButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentsCoursesButton.setText("Courses");
        assignmentsCoursesButton.setBorder(null);
        assignmentsCoursesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsCoursesButtonActionPerformed(evt);
            }
        });
        bg6.add(assignmentsCoursesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 80, 20));

        assignmentsBranchesButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentsBranchesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentsBranchesButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentsBranchesButton.setText("Branches");
        assignmentsBranchesButton.setBorder(null);
        assignmentsBranchesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsBranchesButtonActionPerformed(evt);
            }
        });
        bg6.add(assignmentsBranchesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 90, 20));

        assignmentsGradeBookButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentsGradeBookButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentsGradeBookButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentsGradeBookButton.setText("Grade Book");
        assignmentsGradeBookButton.setBorder(null);
        assignmentsGradeBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsGradeBookButtonActionPerformed(evt);
            }
        });
        bg6.add(assignmentsGradeBookButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 100, 20));

        assignmentsLoginButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentsLoginButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentsLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentsLoginButton.setText("Login");
        assignmentsLoginButton.setBorder(null);
        assignmentsLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsLoginButtonActionPerformed(evt);
            }
        });
        bg6.add(assignmentsLoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 60, 20));

        assignmentsDirectButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentsDirectButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentsDirectButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentsDirectButton.setText("Derict DB Access");
        assignmentsDirectButton.setBorder(null);
        assignmentsDirectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsDirectButtonActionPerformed(evt);
            }
        });
        bg6.add(assignmentsDirectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 130, 20));

        assignmentsStudentButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentsStudentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        assignmentsStudentButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentsStudentButton.setText("Student");
        assignmentsStudentButton.setBorder(null);
        assignmentsStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsStudentButtonActionPerformed(evt);
            }
        });
        bg6.add(assignmentsStudentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 20));

        assignmentPanel.add(bg6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 70));

        getContentPane().add(assignmentPanel, "card9");

        facultyPanel.setBackground(new java.awt.Color(255, 255, 255));
        facultyPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        facultyPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        facultyLablel1.setBackground(new java.awt.Color(255, 255, 255));
        facultyLablel1.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        facultyLablel1.setForeground(new java.awt.Color(102, 102, 102));
        facultyLablel1.setText("Faculty Members");
        facultyPanel.add(facultyLablel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 117, -1, -1));

        facultyLablel2.setBackground(new java.awt.Color(255, 255, 255));
        facultyLablel2.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        facultyLablel2.setForeground(new java.awt.Color(102, 102, 102));
        facultyLablel2.setText("Subjects");
        facultyPanel.add(facultyLablel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(859, 117, -1, -1));

        facultyMembersTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        facultyMembersScrollPane.setViewportView(facultyMembersTable);

        facultyPanel.add(facultyMembersScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 156, -1, 358));

        subjectsTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        subjectsScrollPane.setViewportView(subjectsTable);

        facultyPanel.add(subjectsScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(557, 156, 693, 358));

        facultyLablel3.setBackground(new java.awt.Color(255, 255, 255));
        facultyLablel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        facultyLablel3.setForeground(new java.awt.Color(102, 102, 102));
        facultyLablel3.setText("Faculty ID");
        facultyPanel.add(facultyLablel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(59, 532, -1, -1));

        facultyLablel4.setBackground(new java.awt.Color(255, 255, 255));
        facultyLablel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        facultyLablel4.setForeground(new java.awt.Color(102, 102, 102));
        facultyLablel4.setText("First Name");
        facultyPanel.add(facultyLablel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 532, -1, -1));

        facultyLablel5.setBackground(new java.awt.Color(255, 255, 255));
        facultyLablel5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        facultyLablel5.setForeground(new java.awt.Color(102, 102, 102));
        facultyLablel5.setText("Last Name");
        facultyPanel.add(facultyLablel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(358, 532, -1, -1));

        facultyIdField.setBackground(new java.awt.Color(255, 255, 255));
        facultyIdField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyIdField.setBorder(null);
        facultyPanel.add(facultyIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 560, 99, -1));

        facultyFnameField.setBackground(new java.awt.Color(255, 255, 255));
        facultyFnameField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyFnameField.setBorder(null);
        facultyPanel.add(facultyFnameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 561, 97, -1));

        facultyLnameField.setBackground(new java.awt.Color(255, 255, 255));
        facultyLnameField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyLnameField.setBorder(null);
        facultyPanel.add(facultyLnameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 561, 93, -1));

        facultyLablel6.setBackground(new java.awt.Color(255, 255, 255));
        facultyLablel6.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        facultyLablel6.setForeground(new java.awt.Color(102, 102, 102));
        facultyLablel6.setText("Subject ID");
        facultyPanel.add(facultyLablel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(671, 532, -1, -1));

        facultyLablel7.setBackground(new java.awt.Color(255, 255, 255));
        facultyLablel7.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        facultyLablel7.setForeground(new java.awt.Color(102, 102, 102));
        facultyLablel7.setText("Lecturer ID");
        facultyPanel.add(facultyLablel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 530, -1, -1));

        facultyLablel8.setBackground(new java.awt.Color(255, 255, 255));
        facultyLablel8.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        facultyLablel8.setForeground(new java.awt.Color(102, 102, 102));
        facultyLablel8.setText("Subject Name");
        facultyPanel.add(facultyLablel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 530, -1, -1));

        facultyFIDCombo.setEditable(true);
        facultyFIDCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyFIDCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        facultyPanel.add(facultyFIDCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(861, 560, 96, -1));

        facultySBIDfield.setBackground(new java.awt.Color(255, 255, 255));
        facultySBIDfield.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultySBIDfield.setBorder(null);
        facultyPanel.add(facultySBIDfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(659, 561, 106, -1));

        facultySubjectField.setBackground(new java.awt.Color(255, 255, 255));
        facultySubjectField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultySubjectField.setBorder(null);
        facultyPanel.add(facultySubjectField, new org.netbeans.lib.awtextra.AbsoluteConstraints(1102, 561, 105, -1));

        subjectsFindButton.setBackground(new java.awt.Color(102, 102, 102));
        subjectsFindButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        subjectsFindButton.setForeground(new java.awt.Color(255, 255, 255));
        subjectsFindButton.setText("Find By ID");
        subjectsFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectsFindButtonActionPerformed(evt);
            }
        });
        facultyPanel.add(subjectsFindButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(564, 600, 115, 87));

        subjectsCreateButton.setBackground(new java.awt.Color(102, 102, 102));
        subjectsCreateButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        subjectsCreateButton.setForeground(new java.awt.Color(255, 255, 255));
        subjectsCreateButton.setText("Create");
        subjectsCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectsCreateButtonActionPerformed(evt);
            }
        });
        facultyPanel.add(subjectsCreateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(756, 600, 115, 87));

        subjectsDeleteButton.setBackground(new java.awt.Color(102, 102, 102));
        subjectsDeleteButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        subjectsDeleteButton.setForeground(new java.awt.Color(255, 255, 255));
        subjectsDeleteButton.setText("Delete");
        subjectsDeleteButton.setToolTipText("");
        subjectsDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectsDeleteButtonActionPerformed(evt);
            }
        });
        facultyPanel.add(subjectsDeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(954, 600, 115, 87));

        subjectsModifyButton.setBackground(new java.awt.Color(102, 102, 102));
        subjectsModifyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        subjectsModifyButton.setForeground(new java.awt.Color(255, 255, 255));
        subjectsModifyButton.setText("Modify");
        subjectsModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectsModifyButtonActionPerformed(evt);
            }
        });
        facultyPanel.add(subjectsModifyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1135, 600, 115, 87));

        facultyMembersFindButton.setBackground(new java.awt.Color(102, 102, 102));
        facultyMembersFindButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyMembersFindButton.setForeground(new java.awt.Color(255, 255, 255));
        facultyMembersFindButton.setText("Find By ID");
        facultyMembersFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyMembersFindButtonActionPerformed(evt);
            }
        });
        facultyPanel.add(facultyMembersFindButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 600, -1, 87));

        facultyMembersCreateButton.setBackground(new java.awt.Color(102, 102, 102));
        facultyMembersCreateButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyMembersCreateButton.setForeground(new java.awt.Color(255, 255, 255));
        facultyMembersCreateButton.setText("Create");
        facultyMembersCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyMembersCreateButtonActionPerformed(evt);
            }
        });
        facultyPanel.add(facultyMembersCreateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 600, 97, 40));

        facultyMembersModifyButton.setBackground(new java.awt.Color(102, 102, 102));
        facultyMembersModifyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyMembersModifyButton.setForeground(new java.awt.Color(255, 255, 255));
        facultyMembersModifyButton.setText("Modify");
        facultyMembersModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyMembersModifyButtonActionPerformed(evt);
            }
        });
        facultyPanel.add(facultyMembersModifyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 600, 93, 87));

        facultyMembersDeleteButton.setBackground(new java.awt.Color(102, 102, 102));
        facultyMembersDeleteButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyMembersDeleteButton.setForeground(new java.awt.Color(255, 255, 255));
        facultyMembersDeleteButton.setText("Delete");
        facultyMembersDeleteButton.setToolTipText("");
        facultyMembersDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyMembersDeleteButtonActionPerformed(evt);
            }
        });
        facultyPanel.add(facultyMembersDeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 647, 97, 40));

        jSeparator25.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator25.setForeground(new java.awt.Color(0, 0, 0));
        facultyPanel.add(jSeparator25, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 580, 100, 10));

        jSeparator26.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator26.setForeground(new java.awt.Color(0, 0, 0));
        facultyPanel.add(jSeparator26, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 580, 100, 10));

        jSeparator27.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator27.setForeground(new java.awt.Color(0, 0, 0));
        facultyPanel.add(jSeparator27, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 580, 90, 10));

        jSeparator28.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator28.setForeground(new java.awt.Color(0, 0, 0));
        facultyPanel.add(jSeparator28, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 580, 100, 10));

        jSeparator29.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator29.setForeground(new java.awt.Color(0, 0, 0));
        facultyPanel.add(jSeparator29, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 580, 110, 10));

        bg7.setBackground(new java.awt.Color(102, 102, 102));
        bg7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutButton3.setBackground(new java.awt.Color(102, 102, 102));
        logoutButton3.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logoutButton3.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton3.setText("X");
        logoutButton3.setBorder(null);
        logoutButton3.setFocusPainted(false);
        logoutButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButton3ActionPerformed(evt);
            }
        });
        bg7.add(logoutButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, 40, -1));

        facultyButton.setBackground(new java.awt.Color(255, 255, 255));
        facultyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyButton.setForeground(new java.awt.Color(102, 102, 102));
        facultyButton.setText("Faculty");
        facultyButton.setBorder(null);
        bg7.add(facultyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 80, 40));

        facultyAssignmentsButton.setBackground(new java.awt.Color(102, 102, 102));
        facultyAssignmentsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyAssignmentsButton.setForeground(new java.awt.Color(255, 255, 255));
        facultyAssignmentsButton.setText("Assignments");
        facultyAssignmentsButton.setBorder(null);
        facultyAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyAssignmentsButtonActionPerformed(evt);
            }
        });
        bg7.add(facultyAssignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 100, 20));

        facultyGroupsButton.setBackground(new java.awt.Color(102, 102, 102));
        facultyGroupsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyGroupsButton.setForeground(new java.awt.Color(255, 255, 255));
        facultyGroupsButton.setText("Groups");
        facultyGroupsButton.setBorder(null);
        facultyGroupsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyGroupsButtonActionPerformed(evt);
            }
        });
        bg7.add(facultyGroupsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 70, 20));

        facultyTimetableButton.setBackground(new java.awt.Color(102, 102, 102));
        facultyTimetableButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyTimetableButton.setForeground(new java.awt.Color(255, 255, 255));
        facultyTimetableButton.setText("Timetable");
        facultyTimetableButton.setBorder(null);
        facultyTimetableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyTimetableButtonActionPerformed(evt);
            }
        });
        bg7.add(facultyTimetableButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 90, 20));

        facultyCoursesButton.setBackground(new java.awt.Color(102, 102, 102));
        facultyCoursesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyCoursesButton.setForeground(new java.awt.Color(255, 255, 255));
        facultyCoursesButton.setText("Courses");
        facultyCoursesButton.setBorder(null);
        facultyCoursesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyCoursesButtonActionPerformed(evt);
            }
        });
        bg7.add(facultyCoursesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 80, 20));

        facultyBranchesButton.setBackground(new java.awt.Color(102, 102, 102));
        facultyBranchesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyBranchesButton.setForeground(new java.awt.Color(255, 255, 255));
        facultyBranchesButton.setText("Branches");
        facultyBranchesButton.setBorder(null);
        facultyBranchesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyBranchesButtonActionPerformed(evt);
            }
        });
        bg7.add(facultyBranchesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 90, 20));

        facultyGradeBookButton.setBackground(new java.awt.Color(102, 102, 102));
        facultyGradeBookButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyGradeBookButton.setForeground(new java.awt.Color(255, 255, 255));
        facultyGradeBookButton.setText("Grade Book");
        facultyGradeBookButton.setBorder(null);
        facultyGradeBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyGradeBookButtonActionPerformed(evt);
            }
        });
        bg7.add(facultyGradeBookButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 100, 20));

        facultyLoginButton.setBackground(new java.awt.Color(102, 102, 102));
        facultyLoginButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        facultyLoginButton.setText("Login");
        facultyLoginButton.setBorder(null);
        facultyLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyLoginButtonActionPerformed(evt);
            }
        });
        bg7.add(facultyLoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 60, 20));

        facultyDirectButton.setBackground(new java.awt.Color(102, 102, 102));
        facultyDirectButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyDirectButton.setForeground(new java.awt.Color(255, 255, 255));
        facultyDirectButton.setText("Derict DB Access");
        facultyDirectButton.setBorder(null);
        facultyDirectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyDirectButtonActionPerformed(evt);
            }
        });
        bg7.add(facultyDirectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 130, 20));

        facultyStudentButton.setBackground(new java.awt.Color(102, 102, 102));
        facultyStudentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        facultyStudentButton.setForeground(new java.awt.Color(255, 255, 255));
        facultyStudentButton.setText("Student");
        facultyStudentButton.setBorder(null);
        facultyStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyStudentButtonActionPerformed(evt);
            }
        });
        bg7.add(facultyStudentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 20));

        facultyPanel.add(bg7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 70));

        getContentPane().add(facultyPanel, "card10");

        loginPanel.setBackground(new java.awt.Color(255, 255, 255));
        loginPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        loginPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        loginLabel3.setBackground(new java.awt.Color(255, 255, 255));
        loginLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        loginLabel3.setForeground(new java.awt.Color(102, 102, 102));
        loginLabel3.setText("Accounts");
        loginPanel.add(loginLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 110, -1, 20));

        loginTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        loginScrolPane.setViewportView(loginTable);

        loginPanel.add(loginScrolPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 150, 531, 520));

        loginNOTEarea.setEditable(false);
        loginNOTEarea.setColumns(20);
        loginNOTEarea.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        loginNOTEarea.setForeground(new java.awt.Color(102, 102, 102));
        loginNOTEarea.setRows(5);
        loginNOTEarea.setText("NOTE:\nDefault password : panadol. Username: Faculty ID \\ Student ID \\ Admin ID\n\nYou are NOT allowed to see CUSTOM password. \nBefore reseting password you MUST notify account holder(s).");
        loginNOTEarea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane21.setViewportView(loginNOTEarea);

        loginPanel.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, 617, 129));

        loginLabel1.setBackground(new java.awt.Color(255, 255, 255));
        loginLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        loginLabel1.setForeground(new java.awt.Color(102, 102, 102));
        loginLabel1.setText("Username");
        loginPanel.add(loginLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, 20));

        loginLabel2.setBackground(new java.awt.Color(255, 255, 255));
        loginLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        loginLabel2.setForeground(new java.awt.Color(102, 102, 102));
        loginLabel2.setText("Role");
        loginPanel.add(loginLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, -1, 20));

        loginUsernameFiled.setBackground(new java.awt.Color(255, 255, 255));
        loginUsernameFiled.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginUsernameFiled.setBorder(null);
        loginPanel.add(loginUsernameFiled, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 129, 20));

        loginRoleCombo.setEditable(true);
        loginRoleCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginRoleCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "student", "faculty", "admin" }));
        loginRoleCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        loginPanel.add(loginRoleCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 129, 20));

        loginCreateButton.setBackground(new java.awt.Color(102, 102, 102));
        loginCreateButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginCreateButton.setForeground(new java.awt.Color(255, 255, 255));
        loginCreateButton.setText("Create User");
        loginCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginCreateButtonActionPerformed(evt);
            }
        });
        loginPanel.add(loginCreateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 129, 70));

        loginModifyButton.setBackground(new java.awt.Color(102, 102, 102));
        loginModifyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginModifyButton.setForeground(new java.awt.Color(255, 255, 255));
        loginModifyButton.setText("Set Role");
        loginModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginModifyButtonActionPerformed(evt);
            }
        });
        loginPanel.add(loginModifyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, 129, 70));

        loginDeleteButton.setBackground(new java.awt.Color(102, 102, 102));
        loginDeleteButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginDeleteButton.setForeground(new java.awt.Color(255, 255, 255));
        loginDeleteButton.setText("Delete User");
        loginDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginDeleteButtonActionPerformed(evt);
            }
        });
        loginPanel.add(loginDeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, 129, 70));

        loginResetButton.setBackground(new java.awt.Color(102, 102, 102));
        loginResetButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginResetButton.setForeground(new java.awt.Color(255, 255, 255));
        loginResetButton.setText("Reset Password");
        loginResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginResetButtonActionPerformed(evt);
            }
        });
        loginPanel.add(loginResetButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 330, 129, 70));

        jSeparator30.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator30.setForeground(new java.awt.Color(0, 0, 0));
        loginPanel.add(jSeparator30, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 130, 10));

        bg8.setBackground(new java.awt.Color(102, 102, 102));
        bg8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutButton2.setBackground(new java.awt.Color(102, 102, 102));
        logoutButton2.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logoutButton2.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton2.setText("X");
        logoutButton2.setBorder(null);
        logoutButton2.setFocusPainted(false);
        logoutButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButton2ActionPerformed(evt);
            }
        });
        bg8.add(logoutButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, 40, -1));

        loginFacultyButton.setBackground(new java.awt.Color(102, 102, 102));
        loginFacultyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginFacultyButton.setForeground(new java.awt.Color(255, 255, 255));
        loginFacultyButton.setText("Faculty");
        loginFacultyButton.setBorder(null);
        loginFacultyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginFacultyButtonActionPerformed(evt);
            }
        });
        bg8.add(loginFacultyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 80, 20));

        loginAssignmentsButton.setBackground(new java.awt.Color(102, 102, 102));
        loginAssignmentsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginAssignmentsButton.setForeground(new java.awt.Color(255, 255, 255));
        loginAssignmentsButton.setText("Assignments");
        loginAssignmentsButton.setBorder(null);
        loginAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginAssignmentsButtonActionPerformed(evt);
            }
        });
        bg8.add(loginAssignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 100, 20));

        loginGroupsButton.setBackground(new java.awt.Color(102, 102, 102));
        loginGroupsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginGroupsButton.setForeground(new java.awt.Color(255, 255, 255));
        loginGroupsButton.setText("Groups");
        loginGroupsButton.setBorder(null);
        loginGroupsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginGroupsButtonActionPerformed(evt);
            }
        });
        bg8.add(loginGroupsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 70, 20));

        loginTimetableButton.setBackground(new java.awt.Color(102, 102, 102));
        loginTimetableButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginTimetableButton.setForeground(new java.awt.Color(255, 255, 255));
        loginTimetableButton.setText("Timetable");
        loginTimetableButton.setBorder(null);
        loginTimetableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginTimetableButtonActionPerformed(evt);
            }
        });
        bg8.add(loginTimetableButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 90, 20));

        loginCoursesButton.setBackground(new java.awt.Color(102, 102, 102));
        loginCoursesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginCoursesButton.setForeground(new java.awt.Color(255, 255, 255));
        loginCoursesButton.setText("Courses");
        loginCoursesButton.setBorder(null);
        loginCoursesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginCoursesButtonActionPerformed(evt);
            }
        });
        bg8.add(loginCoursesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 80, 20));

        loginBranchesButton.setBackground(new java.awt.Color(102, 102, 102));
        loginBranchesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginBranchesButton.setForeground(new java.awt.Color(255, 255, 255));
        loginBranchesButton.setText("Branches");
        loginBranchesButton.setBorder(null);
        loginBranchesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBranchesButtonActionPerformed(evt);
            }
        });
        bg8.add(loginBranchesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 90, 20));

        loginGradeBookButton.setBackground(new java.awt.Color(102, 102, 102));
        loginGradeBookButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginGradeBookButton.setForeground(new java.awt.Color(255, 255, 255));
        loginGradeBookButton.setText("Grade Book");
        loginGradeBookButton.setBorder(null);
        loginGradeBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginGradeBookButtonActionPerformed(evt);
            }
        });
        bg8.add(loginGradeBookButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 100, 20));

        loginButton.setBackground(new java.awt.Color(255, 255, 255));
        loginButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginButton.setForeground(new java.awt.Color(102, 102, 102));
        loginButton.setText("Login");
        loginButton.setBorder(null);
        bg8.add(loginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 60, 40));

        loginDirectButton.setBackground(new java.awt.Color(102, 102, 102));
        loginDirectButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginDirectButton.setForeground(new java.awt.Color(255, 255, 255));
        loginDirectButton.setText("Derict DB Access");
        loginDirectButton.setBorder(null);
        loginDirectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginDirectButtonActionPerformed(evt);
            }
        });
        bg8.add(loginDirectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 130, 20));

        loginStudentButton.setBackground(new java.awt.Color(102, 102, 102));
        loginStudentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        loginStudentButton.setForeground(new java.awt.Color(255, 255, 255));
        loginStudentButton.setText("Student");
        loginStudentButton.setBorder(null);
        loginStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginStudentButtonActionPerformed(evt);
            }
        });
        bg8.add(loginStudentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 20));

        loginPanel.add(bg8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 70));

        getContentPane().add(loginPanel, "card11");

        DirectDBaccess.setBackground(new java.awt.Color(255, 255, 255));
        DirectDBaccess.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        DirectDBaccess.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        directOutputLabel.setBackground(new java.awt.Color(255, 255, 255));
        directOutputLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        directOutputLabel.setForeground(new java.awt.Color(102, 102, 102));
        directOutputLabel.setText("Output");
        DirectDBaccess.add(directOutputLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 120, -1, -1));

        directTable.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
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
        directScrollPane.setViewportView(directTable);

        DirectDBaccess.add(directScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 160, 732, 476));

        directInputLabel.setBackground(new java.awt.Color(255, 255, 255));
        directInputLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        directInputLabel.setForeground(new java.awt.Color(102, 102, 102));
        directInputLabel.setText("Input");
        DirectDBaccess.add(directInputLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, -1, -1));

        jScrollPane5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        directTextArea.setColumns(20);
        directTextArea.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        directTextArea.setRows(5);
        directTextArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane5.setViewportView(directTextArea);

        DirectDBaccess.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 488, 378));

        directExecuteButton.setBackground(new java.awt.Color(102, 102, 102));
        directExecuteButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        directExecuteButton.setForeground(new java.awt.Color(255, 255, 255));
        directExecuteButton.setText("Execute");
        directExecuteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directExecuteButtonActionPerformed(evt);
            }
        });
        DirectDBaccess.add(directExecuteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 560, 140, 80));

        directExecuteUpdateButton.setBackground(new java.awt.Color(102, 102, 102));
        directExecuteUpdateButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        directExecuteUpdateButton.setForeground(new java.awt.Color(255, 255, 255));
        directExecuteUpdateButton.setText("Execute Update");
        directExecuteUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directExecuteUpdateButtonActionPerformed(evt);
            }
        });
        DirectDBaccess.add(directExecuteUpdateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 560, 140, 80));

        directPrevButton.setBackground(new java.awt.Color(102, 102, 102));
        directPrevButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        directPrevButton.setForeground(new java.awt.Color(255, 255, 255));
        directPrevButton.setText("Prev. Query");
        directPrevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directPrevButtonActionPerformed(evt);
            }
        });
        DirectDBaccess.add(directPrevButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 560, 140, 80));

        bg9.setBackground(new java.awt.Color(102, 102, 102));
        bg9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutButton1.setBackground(new java.awt.Color(102, 102, 102));
        logoutButton1.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logoutButton1.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton1.setText("X");
        logoutButton1.setBorder(null);
        logoutButton1.setFocusPainted(false);
        logoutButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButton1ActionPerformed(evt);
            }
        });
        bg9.add(logoutButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, 40, -1));

        directFacultyButton.setBackground(new java.awt.Color(102, 102, 102));
        directFacultyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        directFacultyButton.setForeground(new java.awt.Color(255, 255, 255));
        directFacultyButton.setText("Faculty");
        directFacultyButton.setBorder(null);
        directFacultyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directFacultyButtonActionPerformed(evt);
            }
        });
        bg9.add(directFacultyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 80, 20));

        directAssignmentsButton.setBackground(new java.awt.Color(102, 102, 102));
        directAssignmentsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        directAssignmentsButton.setForeground(new java.awt.Color(255, 255, 255));
        directAssignmentsButton.setText("Assignments");
        directAssignmentsButton.setBorder(null);
        directAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directAssignmentsButtonActionPerformed(evt);
            }
        });
        bg9.add(directAssignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 100, 20));

        directGroupsButton.setBackground(new java.awt.Color(102, 102, 102));
        directGroupsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        directGroupsButton.setForeground(new java.awt.Color(255, 255, 255));
        directGroupsButton.setText("Groups");
        directGroupsButton.setBorder(null);
        directGroupsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directGroupsButtonActionPerformed(evt);
            }
        });
        bg9.add(directGroupsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 70, 20));

        directTimetableButton.setBackground(new java.awt.Color(102, 102, 102));
        directTimetableButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        directTimetableButton.setForeground(new java.awt.Color(255, 255, 255));
        directTimetableButton.setText("Timetable");
        directTimetableButton.setBorder(null);
        directTimetableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directTimetableButtonActionPerformed(evt);
            }
        });
        bg9.add(directTimetableButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 90, 20));

        directCoursesButton.setBackground(new java.awt.Color(102, 102, 102));
        directCoursesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        directCoursesButton.setForeground(new java.awt.Color(255, 255, 255));
        directCoursesButton.setText("Courses");
        directCoursesButton.setBorder(null);
        directCoursesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directCoursesButtonActionPerformed(evt);
            }
        });
        bg9.add(directCoursesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 80, 20));

        directBranchesButton.setBackground(new java.awt.Color(102, 102, 102));
        directBranchesButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        directBranchesButton.setForeground(new java.awt.Color(255, 255, 255));
        directBranchesButton.setText("Branches");
        directBranchesButton.setBorder(null);
        directBranchesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directBranchesButtonActionPerformed(evt);
            }
        });
        bg9.add(directBranchesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 90, 20));

        directGradeBookButton.setBackground(new java.awt.Color(102, 102, 102));
        directGradeBookButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        directGradeBookButton.setForeground(new java.awt.Color(255, 255, 255));
        directGradeBookButton.setText("Grade Book");
        directGradeBookButton.setBorder(null);
        directGradeBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directGradeBookButtonActionPerformed(evt);
            }
        });
        bg9.add(directGradeBookButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 100, 20));

        directButton.setBackground(new java.awt.Color(255, 255, 255));
        directButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        directButton.setForeground(new java.awt.Color(102, 102, 102));
        directButton.setText("Derict DB Access");
        directButton.setBorder(null);
        bg9.add(directButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 130, 40));

        directStudentButton.setBackground(new java.awt.Color(102, 102, 102));
        directStudentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        directStudentButton.setForeground(new java.awt.Color(255, 255, 255));
        directStudentButton.setText("Student");
        directStudentButton.setBorder(null);
        directStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directStudentButtonActionPerformed(evt);
            }
        });
        bg9.add(directStudentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 20));

        directLoginButton.setBackground(new java.awt.Color(102, 102, 102));
        directLoginButton.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        directLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        directLoginButton.setText("Login");
        directLoginButton.setBorder(null);
        directLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directLoginButtonActionPerformed(evt);
            }
        });
        bg9.add(directLoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 60, 20));

        DirectDBaccess.add(bg9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 70));

        getContentPane().add(DirectDBaccess, "card12");

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
        stackPush();
    }//GEN-LAST:event_directExecuteButtonActionPerformed

    private void directExecuteUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directExecuteUpdateButtonActionPerformed
        directExecuteUpdate();
        updateDirectTable(directExecute(directQuery));
        stackPush();
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

    private void directPrevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directPrevButtonActionPerformed
        stackPop();
    }//GEN-LAST:event_directPrevButtonActionPerformed

    private void timetableDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableDeleteButtonActionPerformed
        deleteTimetable();
        updateTimetableTabel(selectQuery(selectTimetable));
    }//GEN-LAST:event_timetableDeleteButtonActionPerformed

    private void TimetableModifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimetableModifyButtonActionPerformed
        modifyTimetable();
        updateTimetableTabel(selectQuery(selectTimetable));
    }//GEN-LAST:event_TimetableModifyButtonActionPerformed

    private void timetableCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableCreateButtonActionPerformed
        createTimetable();
        updateTimetableTabel(selectQuery(selectTimetable));
    }//GEN-LAST:event_timetableCreateButtonActionPerformed

    private void logoutButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButton1ActionPerformed
        logout();
    }//GEN-LAST:event_logoutButton1ActionPerformed

    private void logoutButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButton2ActionPerformed
        logout();
    }//GEN-LAST:event_logoutButton2ActionPerformed

    private void logoutButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButton3ActionPerformed
        logout();
    }//GEN-LAST:event_logoutButton3ActionPerformed

    private void logoutButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButton4ActionPerformed
        logout();
    }//GEN-LAST:event_logoutButton4ActionPerformed

    private void logoutButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButton5ActionPerformed
        logout();
    }//GEN-LAST:event_logoutButton5ActionPerformed

    private void logoutButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButton6ActionPerformed
        logout();
    }//GEN-LAST:event_logoutButton6ActionPerformed

    private void logoutButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButton7ActionPerformed
        logout();
    }//GEN-LAST:event_logoutButton7ActionPerformed

    private void logoutButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButton8ActionPerformed
        logout();
    }//GEN-LAST:event_logoutButton8ActionPerformed

    private void logoutButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButton9ActionPerformed
       logout();
    }//GEN-LAST:event_logoutButton9ActionPerformed

    private void logoutButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButton10ActionPerformed
        logout();
    }//GEN-LAST:event_logoutButton10ActionPerformed

    private void studentGroupsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentGroupsButtonActionPerformed
        studentPanel.setVisible(false);
        groupsPanel.setVisible(true);
    }//GEN-LAST:event_studentGroupsButtonActionPerformed

    private void studenTimetabletButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studenTimetabletButtonActionPerformed
        studentPanel.setVisible(false);
        timetablePanel.setVisible(true);
    }//GEN-LAST:event_studenTimetabletButtonActionPerformed

    private void studenCoursestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studenCoursestButtonActionPerformed
        studentPanel.setVisible(false);
        coursesPanel.setVisible(true);
    }//GEN-LAST:event_studenCoursestButtonActionPerformed

    private void studentBranchesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentBranchesButtonActionPerformed
        studentPanel.setVisible(false);
        branchesPanel.setVisible(true);
    }//GEN-LAST:event_studentBranchesButtonActionPerformed

    private void studentGradeBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentGradeBookButtonActionPerformed
        studentPanel.setVisible(false);
        gradesPanel.setVisible(true);
    }//GEN-LAST:event_studentGradeBookButtonActionPerformed

    private void studenAssignmentstButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studenAssignmentstButtonActionPerformed
        studentPanel.setVisible(false);
        assignmentPanel.setVisible(true);
    }//GEN-LAST:event_studenAssignmentstButtonActionPerformed

    private void studentFacultyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentFacultyButtonActionPerformed
        studentPanel.setVisible(false);
        facultyPanel.setVisible(true);
    }//GEN-LAST:event_studentFacultyButtonActionPerformed

    private void studentLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentLoginButtonActionPerformed
        studentPanel.setVisible(false);
        loginPanel.setVisible(true);
    }//GEN-LAST:event_studentLoginButtonActionPerformed

    private void studenDirectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studenDirectButtonActionPerformed
        studentPanel.setVisible(false);
        DirectDBaccess.setVisible(true);
    }//GEN-LAST:event_studenDirectButtonActionPerformed

    private void groupsStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupsStudentButtonActionPerformed
        groupsPanel.setVisible(false);
        studentPanel.setVisible(true);
    }//GEN-LAST:event_groupsStudentButtonActionPerformed

    private void groupsTimetableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupsTimetableButtonActionPerformed
        groupsPanel.setVisible(false);
        timetablePanel.setVisible(true);
    }//GEN-LAST:event_groupsTimetableButtonActionPerformed

    private void groupsCoursesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupsCoursesButtonActionPerformed
        groupsPanel.setVisible(false);
        coursesPanel.setVisible(true);
    }//GEN-LAST:event_groupsCoursesButtonActionPerformed

    private void groupsBranchesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupsBranchesButtonActionPerformed
        groupsPanel.setVisible(false);
        branchesPanel.setVisible(true);
    }//GEN-LAST:event_groupsBranchesButtonActionPerformed

    private void groupsGradeBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupsGradeBookButtonActionPerformed
        groupsPanel.setVisible(false);
        gradesPanel.setVisible(true);
    }//GEN-LAST:event_groupsGradeBookButtonActionPerformed

    private void groupsAssignmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupsAssignmentsButtonActionPerformed
       groupsPanel.setVisible(false);
       assignmentPanel.setVisible(true);
    }//GEN-LAST:event_groupsAssignmentsButtonActionPerformed

    private void groupsFacultyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupsFacultyButtonActionPerformed
        groupsPanel.setVisible(false);
        facultyPanel.setVisible(true);
    }//GEN-LAST:event_groupsFacultyButtonActionPerformed

    private void groupsLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupsLoginButtonActionPerformed
        groupsPanel.setVisible(false);
        loginPanel.setVisible(true);
    }//GEN-LAST:event_groupsLoginButtonActionPerformed

    private void groupsDirectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupsDirectButtonActionPerformed
       groupsPanel.setVisible(false);
        DirectDBaccess.setVisible(true);
    }//GEN-LAST:event_groupsDirectButtonActionPerformed

    private void timetableStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableStudentButtonActionPerformed
        timetablePanel.setVisible(false);
        studentPanel.setVisible(true);
    }//GEN-LAST:event_timetableStudentButtonActionPerformed

    private void timetableGroupsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableGroupsButtonActionPerformed
        timetablePanel.setVisible(false);
        groupsPanel.setVisible(true);
    }//GEN-LAST:event_timetableGroupsButtonActionPerformed

    private void timetableCoursesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableCoursesButtonActionPerformed
        timetablePanel.setVisible(false);
        coursesPanel.setVisible(true);        
    }//GEN-LAST:event_timetableCoursesButtonActionPerformed

    private void timetableBranchesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableBranchesButtonActionPerformed
        timetablePanel.setVisible(false);
        branchesPanel.setVisible(true);
    }//GEN-LAST:event_timetableBranchesButtonActionPerformed

    private void timetableGradeBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableGradeBookButtonActionPerformed
        timetablePanel.setVisible(false);
        gradesPanel.setVisible(true);
    }//GEN-LAST:event_timetableGradeBookButtonActionPerformed

    private void timetableAssignmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableAssignmentsButtonActionPerformed
        timetablePanel.setVisible(false);
        assignmentPanel.setVisible(true);
    }//GEN-LAST:event_timetableAssignmentsButtonActionPerformed

    private void timetableFacultyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableFacultyButtonActionPerformed
       timetablePanel.setVisible(false);
        facultyPanel.setVisible(true);
    }//GEN-LAST:event_timetableFacultyButtonActionPerformed

    private void timetableLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableLoginButtonActionPerformed
        timetablePanel.setVisible(false);
        loginPanel.setVisible(true);
    }//GEN-LAST:event_timetableLoginButtonActionPerformed

    private void timetableDirectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableDirectButtonActionPerformed
        timetablePanel.setVisible(false);
        DirectDBaccess.setVisible(true);
    }//GEN-LAST:event_timetableDirectButtonActionPerformed

    private void coursesStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesStudentButtonActionPerformed
        coursesPanel.setVisible(false);
        studentPanel.setVisible(true);
    }//GEN-LAST:event_coursesStudentButtonActionPerformed

    private void coursesGroupsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesGroupsButtonActionPerformed
        coursesPanel.setVisible(false);
        groupsPanel.setVisible(true);
    }//GEN-LAST:event_coursesGroupsButtonActionPerformed

    private void coursesTimetableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesTimetableButtonActionPerformed
        coursesPanel.setVisible(false);
        timetablePanel.setVisible(true);
    }//GEN-LAST:event_coursesTimetableButtonActionPerformed

    private void coursesBranchesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesBranchesButtonActionPerformed
        coursesPanel.setVisible(false);
        branchesPanel.setVisible(true);
    }//GEN-LAST:event_coursesBranchesButtonActionPerformed

    private void coursesGradeBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesGradeBookButtonActionPerformed
        coursesPanel.setVisible(false);
        gradesPanel.setVisible(true);
    }//GEN-LAST:event_coursesGradeBookButtonActionPerformed

    private void coursesAssignmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesAssignmentsButtonActionPerformed
        coursesPanel.setVisible(false);
        assignmentPanel.setVisible(true);
    }//GEN-LAST:event_coursesAssignmentsButtonActionPerformed

    private void coursesFacultyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesFacultyButtonActionPerformed
        coursesPanel.setVisible(false);
        facultyPanel.setVisible(true);
    }//GEN-LAST:event_coursesFacultyButtonActionPerformed

    private void coursesLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesLoginButtonActionPerformed
        coursesPanel.setVisible(false);
        loginPanel.setVisible(true);
    }//GEN-LAST:event_coursesLoginButtonActionPerformed

    private void coursesDirectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesDirectButtonActionPerformed
        coursesPanel.setVisible(false);
        DirectDBaccess.setVisible(true);
    }//GEN-LAST:event_coursesDirectButtonActionPerformed

    private void branchesStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_branchesStudentButtonActionPerformed
        branchesPanel.setVisible(false);
        studentPanel.setVisible(true);
    }//GEN-LAST:event_branchesStudentButtonActionPerformed

    private void branchesGroupsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_branchesGroupsButtonActionPerformed
        branchesPanel.setVisible(false);
        groupsPanel.setVisible(true);
    }//GEN-LAST:event_branchesGroupsButtonActionPerformed

    private void branchesTimetableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_branchesTimetableButtonActionPerformed
        branchesPanel.setVisible(false);
        timetablePanel.setVisible(true);
    }//GEN-LAST:event_branchesTimetableButtonActionPerformed

    private void branchesCoursesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_branchesCoursesButtonActionPerformed
        branchesPanel.setVisible(false);
        coursesPanel.setVisible(true);
    }//GEN-LAST:event_branchesCoursesButtonActionPerformed

    private void branchesGradeBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_branchesGradeBookButtonActionPerformed
        branchesPanel.setVisible(false);
        gradesPanel.setVisible(true);
    }//GEN-LAST:event_branchesGradeBookButtonActionPerformed

    private void branchesAssignmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_branchesAssignmentsButtonActionPerformed
        branchesPanel.setVisible(false);
        assignmentPanel.setVisible(true);
    }//GEN-LAST:event_branchesAssignmentsButtonActionPerformed

    private void branchesFacultyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_branchesFacultyButtonActionPerformed
        branchesPanel.setVisible(false);
        facultyPanel.setVisible(true);
    }//GEN-LAST:event_branchesFacultyButtonActionPerformed

    private void branchesLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_branchesLoginButtonActionPerformed
        branchesPanel.setVisible(false);
        loginPanel.setVisible(true);
    }//GEN-LAST:event_branchesLoginButtonActionPerformed

    private void branchesDirectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_branchesDirectButtonActionPerformed
        branchesPanel.setVisible(false);
        DirectDBaccess.setVisible(true);
    }//GEN-LAST:event_branchesDirectButtonActionPerformed

    private void gradeBookStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeBookStudentButtonActionPerformed
        gradesPanel.setVisible(false);
        studentPanel.setVisible(true);
    }//GEN-LAST:event_gradeBookStudentButtonActionPerformed

    private void gradeBookGroupsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeBookGroupsButtonActionPerformed
        gradesPanel.setVisible(false);
        groupsPanel.setVisible(true);
    }//GEN-LAST:event_gradeBookGroupsButtonActionPerformed

    private void gradeBookTimetableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeBookTimetableButtonActionPerformed
        gradesPanel.setVisible(false);
        timetablePanel.setVisible(true);
    }//GEN-LAST:event_gradeBookTimetableButtonActionPerformed

    private void gradeBookCoursesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeBookCoursesButtonActionPerformed
        gradesPanel.setVisible(false);
        coursesPanel.setVisible(true);
    }//GEN-LAST:event_gradeBookCoursesButtonActionPerformed

    private void gradeBookBranchesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeBookBranchesButtonActionPerformed
        gradesPanel.setVisible(false);
        branchesPanel.setVisible(true);
    }//GEN-LAST:event_gradeBookBranchesButtonActionPerformed

    private void gradeBookAssignmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeBookAssignmentsButtonActionPerformed
        gradesPanel.setVisible(false);
        assignmentPanel.setVisible(true);        
    }//GEN-LAST:event_gradeBookAssignmentsButtonActionPerformed

    private void gradeBookFacultyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeBookFacultyButtonActionPerformed
        gradesPanel.setVisible(false);
        facultyPanel.setVisible(true);        
    }//GEN-LAST:event_gradeBookFacultyButtonActionPerformed

    private void gradeBookLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeBookLoginButtonActionPerformed
        gradesPanel.setVisible(false);
        loginPanel.setVisible(true);
    }//GEN-LAST:event_gradeBookLoginButtonActionPerformed

    private void gradeBookDirectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeBookDirectButtonActionPerformed
        gradesPanel.setVisible(false);        
        DirectDBaccess.setVisible(true);
    }//GEN-LAST:event_gradeBookDirectButtonActionPerformed

    private void assignmentsStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsStudentButtonActionPerformed
        assignmentPanel.setVisible(false); 
        studentPanel.setVisible(true);
    }//GEN-LAST:event_assignmentsStudentButtonActionPerformed

    private void assignmentsGroupsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsGroupsButtonActionPerformed
        assignmentPanel.setVisible(false); 
        groupsPanel.setVisible(true);
    }//GEN-LAST:event_assignmentsGroupsButtonActionPerformed

    private void assignmentsTimetableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsTimetableButtonActionPerformed
        assignmentPanel.setVisible(false); 
        timetablePanel.setVisible(true);
    }//GEN-LAST:event_assignmentsTimetableButtonActionPerformed

    private void assignmentsCoursesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsCoursesButtonActionPerformed
        assignmentPanel.setVisible(false); 
        coursesPanel.setVisible(true);
    }//GEN-LAST:event_assignmentsCoursesButtonActionPerformed

    private void assignmentsBranchesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsBranchesButtonActionPerformed
        assignmentPanel.setVisible(false); 
        branchesPanel.setVisible(true);
    }//GEN-LAST:event_assignmentsBranchesButtonActionPerformed

    private void assignmentsGradeBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsGradeBookButtonActionPerformed
        assignmentPanel.setVisible(false); 
        gradesPanel.setVisible(true);
    }//GEN-LAST:event_assignmentsGradeBookButtonActionPerformed

    private void assignmentsFacultyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsFacultyButtonActionPerformed
        assignmentPanel.setVisible(false); 
        facultyPanel.setVisible(true);  
    }//GEN-LAST:event_assignmentsFacultyButtonActionPerformed

    private void assignmentsLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsLoginButtonActionPerformed
        assignmentPanel.setVisible(false); 
        loginPanel.setVisible(true);
    }//GEN-LAST:event_assignmentsLoginButtonActionPerformed

    private void assignmentsDirectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsDirectButtonActionPerformed
        assignmentPanel.setVisible(false); 
        DirectDBaccess.setVisible(true);
    }//GEN-LAST:event_assignmentsDirectButtonActionPerformed

    private void facultyStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyStudentButtonActionPerformed
        facultyPanel.setVisible(false);  
        studentPanel.setVisible(true);
    }//GEN-LAST:event_facultyStudentButtonActionPerformed

    private void facultyGroupsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyGroupsButtonActionPerformed
        facultyPanel.setVisible(false);  
        groupsPanel.setVisible(true);
    }//GEN-LAST:event_facultyGroupsButtonActionPerformed

    private void facultyTimetableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyTimetableButtonActionPerformed
        facultyPanel.setVisible(false);  
        timetablePanel.setVisible(true);
    }//GEN-LAST:event_facultyTimetableButtonActionPerformed

    private void facultyCoursesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyCoursesButtonActionPerformed
        facultyPanel.setVisible(false);  
        coursesPanel.setVisible(true);
    }//GEN-LAST:event_facultyCoursesButtonActionPerformed

    private void facultyBranchesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyBranchesButtonActionPerformed
        facultyPanel.setVisible(false);  
        branchesPanel.setVisible(true);
    }//GEN-LAST:event_facultyBranchesButtonActionPerformed

    private void facultyGradeBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyGradeBookButtonActionPerformed
        facultyPanel.setVisible(false);  
        gradesPanel.setVisible(true);
    }//GEN-LAST:event_facultyGradeBookButtonActionPerformed

    private void facultyAssignmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyAssignmentsButtonActionPerformed
        facultyPanel.setVisible(false);  
        assignmentPanel.setVisible(true); 
    }//GEN-LAST:event_facultyAssignmentsButtonActionPerformed

    private void facultyLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyLoginButtonActionPerformed
        facultyPanel.setVisible(false);  
        loginPanel.setVisible(true);
    }//GEN-LAST:event_facultyLoginButtonActionPerformed

    private void facultyDirectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyDirectButtonActionPerformed
        facultyPanel.setVisible(false);  
        DirectDBaccess.setVisible(true);
    }//GEN-LAST:event_facultyDirectButtonActionPerformed

    private void loginStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginStudentButtonActionPerformed
        loginPanel.setVisible(false);
        studentPanel.setVisible(true);
    }//GEN-LAST:event_loginStudentButtonActionPerformed

    private void loginGroupsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginGroupsButtonActionPerformed
        loginPanel.setVisible(false);
        groupsPanel.setVisible(true);
    }//GEN-LAST:event_loginGroupsButtonActionPerformed

    private void loginTimetableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginTimetableButtonActionPerformed
        loginPanel.setVisible(false);
        timetablePanel.setVisible(true);
    }//GEN-LAST:event_loginTimetableButtonActionPerformed

    private void loginCoursesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginCoursesButtonActionPerformed
       loginPanel.setVisible(false);
       coursesPanel.setVisible(true);
    }//GEN-LAST:event_loginCoursesButtonActionPerformed

    private void loginBranchesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBranchesButtonActionPerformed
        loginPanel.setVisible(false);
        branchesPanel.setVisible(true);
    }//GEN-LAST:event_loginBranchesButtonActionPerformed

    private void loginGradeBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginGradeBookButtonActionPerformed
        loginPanel.setVisible(false);
        gradesPanel.setVisible(true);
    }//GEN-LAST:event_loginGradeBookButtonActionPerformed

    private void loginAssignmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginAssignmentsButtonActionPerformed
        loginPanel.setVisible(false);
        assignmentPanel.setVisible(true); 
    }//GEN-LAST:event_loginAssignmentsButtonActionPerformed

    private void loginFacultyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginFacultyButtonActionPerformed
        loginPanel.setVisible(false);
        facultyPanel.setVisible(true);
    }//GEN-LAST:event_loginFacultyButtonActionPerformed

    private void loginDirectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginDirectButtonActionPerformed
        loginPanel.setVisible(false);
        DirectDBaccess.setVisible(true);
    }//GEN-LAST:event_loginDirectButtonActionPerformed

    private void directStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directStudentButtonActionPerformed
        DirectDBaccess.setVisible(false);
        studentPanel.setVisible(true);
    }//GEN-LAST:event_directStudentButtonActionPerformed

    private void directGroupsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directGroupsButtonActionPerformed
        DirectDBaccess.setVisible(false);
        groupsPanel.setVisible(true);
    }//GEN-LAST:event_directGroupsButtonActionPerformed

    private void directTimetableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directTimetableButtonActionPerformed
        DirectDBaccess.setVisible(false);
        timetablePanel.setVisible(true);
    }//GEN-LAST:event_directTimetableButtonActionPerformed

    private void directCoursesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directCoursesButtonActionPerformed
       DirectDBaccess.setVisible(false);
       coursesPanel.setVisible(true);
    }//GEN-LAST:event_directCoursesButtonActionPerformed

    private void directBranchesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directBranchesButtonActionPerformed
        DirectDBaccess.setVisible(false);
        branchesPanel.setVisible(true);
    }//GEN-LAST:event_directBranchesButtonActionPerformed

    private void directGradeBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directGradeBookButtonActionPerformed
        DirectDBaccess.setVisible(false);
        gradesPanel.setVisible(true);
    }//GEN-LAST:event_directGradeBookButtonActionPerformed

    private void directAssignmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directAssignmentsButtonActionPerformed
        DirectDBaccess.setVisible(false);
        assignmentPanel.setVisible(true); 
    }//GEN-LAST:event_directAssignmentsButtonActionPerformed

    private void directFacultyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directFacultyButtonActionPerformed
        DirectDBaccess.setVisible(false);
        facultyPanel.setVisible(true);
    }//GEN-LAST:event_directFacultyButtonActionPerformed

    private void directLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directLoginButtonActionPerformed
        DirectDBaccess.setVisible(false);
        loginPanel.setVisible(true);
    }//GEN-LAST:event_directLoginButtonActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_formMouseDragged

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
            @Override
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
    private javax.swing.JScrollPane assignmentGradesScrollPane;
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
    private javax.swing.JButton assignmentsBranchesButton;
    private javax.swing.JButton assignmentsButton;
    private javax.swing.JButton assignmentsCoursesButton;
    private javax.swing.JButton assignmentsDirectButton;
    private javax.swing.JButton assignmentsFacultyButton;
    private javax.swing.JButton assignmentsGradeBookButton;
    private javax.swing.JButton assignmentsGroupsButton;
    private javax.swing.JButton assignmentsLoginButton;
    private javax.swing.JScrollPane assignmentsScrollPane;
    private javax.swing.JButton assignmentsStudentButton;
    private javax.swing.JTable assignmentsTable;
    private javax.swing.JButton assignmentsTimetableButton;
    private javax.swing.JPanel bg;
    private javax.swing.JPanel bg1;
    private javax.swing.JPanel bg2;
    private javax.swing.JPanel bg3;
    private javax.swing.JPanel bg4;
    private javax.swing.JPanel bg5;
    private javax.swing.JPanel bg6;
    private javax.swing.JPanel bg7;
    private javax.swing.JPanel bg8;
    private javax.swing.JPanel bg9;
    private javax.swing.JTextArea branchAddressArea;
    private javax.swing.JScrollPane branchDetailsScrollPane;
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
    private javax.swing.JScrollPane branchScrollPane;
    private javax.swing.JTable branchTable;
    private javax.swing.JButton branchesAssignmentsButton;
    private javax.swing.JButton branchesButton;
    private javax.swing.JButton branchesCoursesButton;
    private javax.swing.JButton branchesDirectButton;
    private javax.swing.JButton branchesFacultyButton;
    private javax.swing.JButton branchesGradeBookButton;
    private javax.swing.JButton branchesGroupsButton;
    private javax.swing.JButton branchesLoginButton;
    private javax.swing.JPanel branchesPanel;
    private javax.swing.JButton branchesStudentButton;
    private javax.swing.JButton branchesTimetableButton;
    private javax.swing.JTextArea courseArea;
    private javax.swing.JTextField courseField;
    private javax.swing.JComboBox<String> courseIdCombo;
    private javax.swing.JTextField courseIdField;
    private javax.swing.JTextField coursePriceField;
    private javax.swing.JButton coursesAssignmentsButton;
    private javax.swing.JButton coursesBranchesButton;
    private javax.swing.JButton coursesButton;
    private javax.swing.JButton coursesCreateButton;
    private javax.swing.JButton coursesDeleteButton;
    private javax.swing.JButton coursesDirectButton;
    private javax.swing.JButton coursesFacultyButton;
    private javax.swing.JButton coursesFindButton;
    private javax.swing.JButton coursesGradeBookButton;
    private javax.swing.JButton coursesGroupsButton;
    private javax.swing.JLabel coursesLAbel1;
    private javax.swing.JLabel coursesLabel2;
    private javax.swing.JLabel coursesLabel4;
    private javax.swing.JLabel coursesLabel5;
    private javax.swing.JLabel coursesLabel6;
    private javax.swing.JLabel coursesLabel8;
    private javax.swing.JLabel coursesLablel7;
    private javax.swing.JButton coursesLoginButton;
    private javax.swing.JButton coursesModifyButton;
    private javax.swing.JTextField coursesPAyedField;
    private javax.swing.JPanel coursesPanel;
    private javax.swing.JTextField coursesRequiredField;
    private javax.swing.JScrollPane coursesScrollPane;
    private javax.swing.JTextField coursesStidentIdField;
    private javax.swing.JButton coursesStudentButton;
    private javax.swing.JTable coursesTable;
    private javax.swing.JButton coursesTimetableButton;
    private javax.swing.JLabel coursesesLabel3;
    private javax.swing.JButton createBranchButton;
    private javax.swing.JButton createBranchButton2;
    private javax.swing.JButton createButton1;
    private javax.swing.JButton createButton2;
    private javax.swing.JButton deleteBranchButton;
    private javax.swing.JButton deleteBranchButton2;
    private javax.swing.JButton deleteButton1;
    private javax.swing.JButton deleteButton2;
    private javax.swing.JButton directAssignmentsButton;
    private javax.swing.JButton directBranchesButton;
    private javax.swing.JButton directButton;
    private javax.swing.JButton directCoursesButton;
    private javax.swing.JButton directExecuteButton;
    private javax.swing.JButton directExecuteUpdateButton;
    private javax.swing.JButton directFacultyButton;
    private javax.swing.JButton directGradeBookButton;
    private javax.swing.JButton directGroupsButton;
    private javax.swing.JLabel directInputLabel;
    private javax.swing.JButton directLoginButton;
    private javax.swing.JLabel directOutputLabel;
    private javax.swing.JButton directPrevButton;
    private javax.swing.JScrollPane directScrollPane;
    private javax.swing.JButton directStudentButton;
    private javax.swing.JTable directTable;
    private javax.swing.JTextArea directTextArea;
    private javax.swing.JButton directTimetableButton;
    private javax.swing.JTextField examGradeField1;
    private javax.swing.JButton facultyAssignmentsButton;
    private javax.swing.JButton facultyBranchesButton;
    private javax.swing.JButton facultyButton;
    private javax.swing.JButton facultyCoursesButton;
    private javax.swing.JButton facultyDirectButton;
    private javax.swing.JComboBox<String> facultyFIDCombo;
    private javax.swing.JTextField facultyFnameField;
    private javax.swing.JButton facultyGradeBookButton;
    private javax.swing.JButton facultyGroupsButton;
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
    private javax.swing.JButton facultyLoginButton;
    private javax.swing.JButton facultyMembersCreateButton;
    private javax.swing.JButton facultyMembersDeleteButton;
    private javax.swing.JButton facultyMembersFindButton;
    private javax.swing.JButton facultyMembersModifyButton;
    private javax.swing.JScrollPane facultyMembersScrollPane;
    private javax.swing.JTable facultyMembersTable;
    private javax.swing.JPanel facultyPanel;
    private javax.swing.JTextField facultySBIDfield;
    private javax.swing.JButton facultyStudentButton;
    private javax.swing.JTextField facultySubjectField;
    private javax.swing.JButton facultyTimetableButton;
    private javax.swing.JButton findBranchButton;
    private javax.swing.JButton findButton1;
    private javax.swing.JButton findButton2;
    private javax.swing.JButton gradeBookAssignmentsButton;
    private javax.swing.JButton gradeBookBranchesButton;
    private javax.swing.JButton gradeBookCoursesButton;
    private javax.swing.JButton gradeBookDirectButton;
    private javax.swing.JButton gradeBookFacultyButton;
    private javax.swing.JButton gradeBookGroupsButton;
    private javax.swing.JLabel gradeBookLabel1;
    private javax.swing.JLabel gradeBookLabel2;
    private javax.swing.JLabel gradeBookLabel3;
    private javax.swing.JLabel gradeBookLabel4;
    private javax.swing.JLabel gradeBookLabel5;
    private javax.swing.JLabel gradeBookLabel6;
    private javax.swing.JLabel gradeBookLabel7;
    private javax.swing.JLabel gradeBookLabel8;
    private javax.swing.JLabel gradeBookLabel9;
    private javax.swing.JButton gradeBookLoginButton;
    private javax.swing.JButton gradeBookStudentButton;
    private javax.swing.JButton gradeBookTimetableButton;
    private javax.swing.JTextField gradeField1;
    private javax.swing.JTextField gradeField2;
    private javax.swing.JButton gradesCreateButton;
    private javax.swing.JButton gradesDeleteButton;
    private javax.swing.JButton gradesFindButton;
    private javax.swing.JButton gradesModifyButton;
    private javax.swing.JPanel gradesPanel;
    private javax.swing.JScrollPane gradesScrollPane;
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
    private javax.swing.JScrollPane groupGroupDetailsScrollPane;
    private javax.swing.JTable groupGroupDetailsTabel;
    private javax.swing.JScrollPane groupGroupsScrollPane;
    private javax.swing.JTable groupGroupsTabel;
    private javax.swing.JLabel groupLabel1;
    private javax.swing.JLabel groupLabel2;
    private javax.swing.JLabel groupLabel3;
    private javax.swing.JLabel groupLabel4;
    private javax.swing.JLabel groupLabel5;
    private javax.swing.JLabel groupLabel6;
    private javax.swing.JLabel groupLabel7;
    private javax.swing.JButton groupsAssignmentsButton;
    private javax.swing.JButton groupsBranchesButton;
    private javax.swing.JButton groupsButton;
    private javax.swing.JButton groupsCoursesButton;
    private javax.swing.JButton groupsDirectButton;
    private javax.swing.JButton groupsFacultyButton;
    private javax.swing.JButton groupsGradeBookButton;
    private javax.swing.JButton groupsLoginButton;
    private javax.swing.JPanel groupsPanel;
    private javax.swing.JButton groupsStudentButton;
    private javax.swing.JButton groupsTimetableButton;
    private javax.swing.JButton jButton63;
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
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField lessonsField;
    private javax.swing.JTextField lessonsField2;
    private javax.swing.JButton loginAssignmentsButton;
    private javax.swing.JButton loginBranchesButton;
    private javax.swing.JButton loginButton;
    private javax.swing.JButton loginCoursesButton;
    private javax.swing.JButton loginCreateButton;
    private javax.swing.JButton loginDeleteButton;
    private javax.swing.JButton loginDirectButton;
    private javax.swing.JButton loginFacultyButton;
    private javax.swing.JButton loginGradeBookButton;
    private javax.swing.JButton loginGroupsButton;
    private javax.swing.JLabel loginLabel1;
    private javax.swing.JLabel loginLabel2;
    private javax.swing.JLabel loginLabel3;
    private javax.swing.JButton loginModifyButton;
    private javax.swing.JTextArea loginNOTEarea;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JButton loginResetButton;
    private javax.swing.JComboBox<String> loginRoleCombo;
    private javax.swing.JScrollPane loginScrolPane;
    private javax.swing.JButton loginStudentButton;
    private javax.swing.JTable loginTable;
    private javax.swing.JButton loginTimetableButton;
    private javax.swing.JTextField loginUsernameFiled;
    private javax.swing.JButton logoutButton1;
    private javax.swing.JButton logoutButton10;
    private javax.swing.JButton logoutButton2;
    private javax.swing.JButton logoutButton3;
    private javax.swing.JButton logoutButton4;
    private javax.swing.JButton logoutButton5;
    private javax.swing.JButton logoutButton6;
    private javax.swing.JButton logoutButton7;
    private javax.swing.JButton logoutButton8;
    private javax.swing.JButton logoutButton9;
    private javax.swing.JButton modifyBranchButton;
    private javax.swing.JButton paymentsCreateButton;
    private javax.swing.JButton paymentsDeleteButton;
    private javax.swing.JButton paymentsFindButton;
    private javax.swing.JButton paymentsModifyButton;
    private javax.swing.JScrollPane paymentsScrollPane;
    private javax.swing.JTable paymentsTable;
    private javax.swing.JTextField phoneField;
    private javax.swing.JTextField sidField1;
    private javax.swing.JTextField sidField2;
    private javax.swing.JButton studenAssignmentstButton;
    private javax.swing.JButton studenCoursestButton;
    private javax.swing.JButton studenDirectButton;
    private javax.swing.JButton studenTimetabletButton;
    private javax.swing.JScrollPane studentAttendanceScrollPane;
    private javax.swing.JTable studentAttendanceTable;
    private javax.swing.JButton studentBranchesButton;
    private javax.swing.JButton studentButton;
    private javax.swing.JComboBox<String> studentCombo1;
    private javax.swing.JComboBox<String> studentCombo2;
    private javax.swing.JButton studentFacultyButton;
    private javax.swing.JButton studentGradeBookButton;
    private javax.swing.JButton studentGroupsButton;
    private javax.swing.JButton studentLoginButton;
    private javax.swing.JPanel studentPanel;
    private javax.swing.JScrollPane studentStudentScrollPane;
    private javax.swing.JTable studentStudentTable;
    private javax.swing.JComboBox<String> subjectCombo1;
    private javax.swing.JButton subjectsCreateButton;
    private javax.swing.JButton subjectsDeleteButton;
    private javax.swing.JButton subjectsFindButton;
    private javax.swing.JButton subjectsModifyButton;
    private javax.swing.JScrollPane subjectsScrollPane;
    private javax.swing.JTable subjectsTable;
    private javax.swing.JButton timetableAssignmentsButton;
    private javax.swing.JButton timetableBranchesButton;
    private javax.swing.JButton timetableButton;
    private javax.swing.JButton timetableCoursesButton;
    private javax.swing.JButton timetableCreateButton;
    private javax.swing.JComboBox<String> timetableDayCombo;
    private javax.swing.JButton timetableDeleteButton;
    private javax.swing.JButton timetableDirectButton;
    private javax.swing.JComboBox<String> timetableEndHoursCombo;
    private javax.swing.JComboBox<String> timetableEndMinCombo;
    private javax.swing.JButton timetableFacultyButton;
    private javax.swing.JButton timetableGradeBookButton;
    private javax.swing.JComboBox<String> timetableGroupCombo;
    private javax.swing.JButton timetableGroupsButton;
    private javax.swing.JLabel timetableLabel;
    private javax.swing.JLabel timetableLabel2;
    private javax.swing.JLabel timetableLabel3;
    private javax.swing.JLabel timetableLabel4;
    private javax.swing.JLabel timetableLabel5;
    private javax.swing.JLabel timetableLabel6;
    private javax.swing.JLabel timetableLabel7;
    private javax.swing.JLabel timetableLabel8;
    private javax.swing.JLabel timetableLabel9;
    private javax.swing.JButton timetableLoginButton;
    private javax.swing.JPanel timetablePanel;
    private javax.swing.JScrollPane timetableScrollPane;
    private javax.swing.JComboBox<String> timetableStartHoursCombo;
    private javax.swing.JComboBox<String> timetableStartMinCombo;
    private javax.swing.JButton timetableStudentButton;
    private javax.swing.JComboBox<String> timetableSubjectCombo;
    private javax.swing.JTable timetableTabel;
    // End of variables declaration//GEN-END:variables
}

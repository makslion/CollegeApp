/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class facultyGUI extends facultyDatabase {

    /**
     * Creates new form facultyGUI
     */
    
    public facultyGUI(String fid) {
        initComponents();
        openFile();
        readConnectionDetails();
        closeFile();
        
        Fid = fid;
        
        updateTimetableTable(prepareQuery(timetableQuery, Fid));
        updateassignmentsTable(prepareQuery(assignmentsQuery, Fid));
        updateStudentTable(prepareQuery(studentQuery, Fid));
        updateStudentGradesTable(prepareQuery(studentGradeQuery, Fid));
        assignmentFill();
        studentFill();
        
        
    }
    
    private void updatePSWD(){
       String currentPSWD = new String (accountOldPasswordField.getPassword());
        char [] newPSWD = accountNewPasswordField.getPassword();
        char [] newPSWD1 = accountNewPasswordField2.getPassword();
        ResultSet resultSet = null;
        
        if(!java.util.Arrays.equals(newPSWD,newPSWD1))
            JOptionPane.showMessageDialog(this, "Password not equal","Oops",JOptionPane.WARNING_MESSAGE);
        
        
        try{
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(pswdCheck);
            PrepStatement.setString(1, Fid);
            
            resultSet = PrepStatement.executeQuery();
            
            resultSet.next();
            if(!resultSet.getString("Password").equals(currentPSWD)){
                JOptionPane.showMessageDialog(this, "Wrong Password","Oops",JOptionPane.WARNING_MESSAGE);
            }
            else {
                PrepStatement = conn.prepareStatement(pswdQuery);
                
                PrepStatement.setString(1, new String (newPSWD));
                PrepStatement.setString(2, Fid);
                
                int result = PrepStatement.executeUpdate();
                System.out.println(result);
                JOptionPane.showMessageDialog(this, "Password Changed!","Hooray!",JOptionPane.INFORMATION_MESSAGE);
            }
            
            
            
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this, e,"Ooops",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void modifyStudentGrade(String aid, String sid, String grade){
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(modifyStudentQuery);
            PrepStatement.setString(1, grade);
            PrepStatement.setString(2, aid);
            PrepStatement.setString(3, sid);
            
            int rs = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
    private void setStudentGrade(String aid, String sid){
        ResultSet rs = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(setStudentGradeQuery);
            PrepStatement.setString(1, aid);
            PrepStatement.setString(2, sid);
            
            rs = PrepStatement.executeQuery();
            
            while(rs.next()){
                studentGradeField.setText(rs.getString(1));
            
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
    private void setStudent(String aid){
         ResultSet rs = null;
         studentStudentCombo.removeAllItems();
         
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(setStudentQuery);
            PrepStatement.setString(1, aid);
            
            rs = PrepStatement.executeQuery();
            
            
            while(rs.next()){
                studentStudentCombo.addItem(rs.getString(1));
                Sid = rs.getString(1);
                
                
            
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void delete(String aid){
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(deleteQuery);
            PrepStatement.setString(1, aid);
            
            int rs = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modify(String aid){
        Date date = datePicker2.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = sdf.format(date);
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(modifyQuery);
            PrepStatement.setString(3, aid);
            PrepStatement.setString(1, comboBoxModifyAssignment.getSelectedItem().toString());
            PrepStatement.setString(2, formatedDate);
            
            int rs = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void modifyGet(String aid){
        ResultSet rs = null;
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(modifyGetQuery);
            PrepStatement.setString(1, aid);
            
            rs = PrepStatement.executeQuery();
            
            while(rs.next()){
                subjectField2.setText(rs.getString("Subject"));
                courseField2.setText(rs.getString("Cid"));
                datePicker2.setDate(rs.getDate("DueDate"));
                comboBoxModifyAssignment.setSelectedItem(rs.getObject("Visible"));
            
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    
    private void assignmentFill(){
        ResultSet rs = prepareQuery(assignmentFillQuery, Fid);
        
        try {
            while(rs.next()){
                assignmentComboBox.addItem(rs.getString(1));
                subjectField1.setText(rs.getString(2));
                comboBoxCourse.addItem(rs.getString(3));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this , e, "Ooops", JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
    private void studentFill(){
        ResultSet rs = prepareQuery(studentFillQuery, Fid);
        
        try {
            while(rs.next()){
                studentAssignmentCombo.addItem(rs.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this , e, "Ooops", JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
    
    private void createAssignment(){
        ResultSet resultSet = null;
        Date date = datePicker1.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = sdf.format(date);
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(createAssignmentQuery);
            
            PrepStatement.setString(1, String.valueOf(comboBoxCourse.getSelectedItem()));
            PrepStatement.setString(2, assignmentField.getText());
            PrepStatement.setString(3, getSubjectId(subjectField1.getText()));
            PrepStatement.setString(4, String.valueOf(comboBoxCreateAssignment.getSelectedItem()));
            PrepStatement.setString(5, formatedDate);
            
            int result = PrepStatement.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
    private String getSubjectId(String subjectName){
        String subjectId = "";
        ResultSet resultSet = null;
        PreparedStatement ps;
            try {
                Connection conn = DriverManager.getConnection(connectionDetails);

                ps = conn.prepareStatement(getSubjectQuery);
                ps.setString(1, subjectName);

                resultSet = ps.executeQuery();
                
                while(resultSet.next())
                    subjectId = resultSet.getString("SBid");
                
            }
            catch(SQLException e){
                JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        return subjectId;
    }
    
    
    
    private void updateTimetableTable(ResultSet rs){
        timetableTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    private void updateassignmentsTable(ResultSet rs){
        assignmentsTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    private void updateStudentTable(ResultSet rs){
        studentTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    private void updateStudentGradesTable(ResultSet rs) {
        studentGradesTable.setModel(DbUtils.resultSetToTableModel(rs));
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
        timetablePanel = new javax.swing.JPanel();
        timetableRefreshButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        timetableTable = new javax.swing.JTable();
        assignmentPanel = new javax.swing.JPanel();
        assignmentsRefreshButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        assignmentsTable = new javax.swing.JTable();
        createAssignmentLabel = new javax.swing.JLabel();
        assignmentLabel1 = new javax.swing.JLabel();
        subjectLabel1 = new javax.swing.JLabel();
        dueDateLabel1 = new javax.swing.JLabel();
        visibleLabel1 = new javax.swing.JLabel();
        assignmentField = new javax.swing.JTextField();
        subjectField1 = new javax.swing.JTextField();
        comboBoxCreateAssignment = new javax.swing.JComboBox<>();
        createAssignmentButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        assignmentLabel2 = new javax.swing.JLabel();
        subjectLabel2 = new javax.swing.JLabel();
        dueDateLabel2 = new javax.swing.JLabel();
        visibleLabel2 = new javax.swing.JLabel();
        subjectField2 = new javax.swing.JTextField();
        comboBoxModifyAssignment = new javax.swing.JComboBox<>();
        modifyAssignmentButton = new javax.swing.JButton();
        deleteAssignmentButton = new javax.swing.JButton();
        assignmentComboBox = new javax.swing.JComboBox<>();
        courseLabel1 = new javax.swing.JLabel();
        courseLabel2 = new javax.swing.JLabel();
        courseField2 = new javax.swing.JTextField();
        comboBoxCourse = new javax.swing.JComboBox<>();
        datePicker1 = new com.toedter.calendar.JDateChooser();
        datePicker2 = new com.toedter.calendar.JDateChooser();
        studentPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        studentRefreshButton = new javax.swing.JButton();
        studentListLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        studentGradesTable = new javax.swing.JTable();
        assignmentGradesLabel = new javax.swing.JLabel();
        studentModifyLabel = new javax.swing.JLabel();
        studentAssignmentLabel = new javax.swing.JLabel();
        studentStudentLabel = new javax.swing.JLabel();
        studentGradeLabel = new javax.swing.JLabel();
        studentAssignmentCombo = new javax.swing.JComboBox<>();
        studentStudentCombo = new javax.swing.JComboBox<>();
        studentGradeField = new javax.swing.JTextField();
        studentApplyButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        accountLabel1 = new javax.swing.JLabel();
        accountLabel2 = new javax.swing.JLabel();
        accountLabel3 = new javax.swing.JLabel();
        accountLabel4 = new javax.swing.JLabel();
        accountNewPasswordField = new javax.swing.JPasswordField();
        accountNewPasswordField2 = new javax.swing.JPasswordField();
        accountButton = new javax.swing.JButton();
        accountOldPasswordField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Faculty");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        timetableRefreshButton.setText("Refresh");
        timetableRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableRefreshButtonActionPerformed(evt);
            }
        });

        timetableTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(timetableTable);

        javax.swing.GroupLayout timetablePanelLayout = new javax.swing.GroupLayout(timetablePanel);
        timetablePanel.setLayout(timetablePanelLayout);
        timetablePanelLayout.setHorizontalGroup(
            timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timetablePanelLayout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, timetablePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(timetableRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(394, 394, 394))
        );
        timetablePanelLayout.setVerticalGroup(
            timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timetablePanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(timetableRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Timetable", timetablePanel);

        assignmentsRefreshButton.setText("Refresh");
        assignmentsRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsRefreshButtonActionPerformed(evt);
            }
        });

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
        jScrollPane2.setViewportView(assignmentsTable);

        createAssignmentLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        createAssignmentLabel.setText("Create Assignment");

        assignmentLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        assignmentLabel1.setText("Assignment");

        subjectLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        subjectLabel1.setText("Subject");

        dueDateLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dueDateLabel1.setText("Due Date");

        visibleLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        visibleLabel1.setText("Visible");

        subjectField1.setEditable(false);

        comboBoxCreateAssignment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "yes", "no" }));

        createAssignmentButton.setText("Cteate");
        createAssignmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAssignmentButtonActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Modify Assignment");

        assignmentLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        assignmentLabel2.setText("Assignment");

        subjectLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        subjectLabel2.setText("Subject");

        dueDateLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dueDateLabel2.setText("Due Date");

        visibleLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        visibleLabel2.setText("Visible");

        subjectField2.setEditable(false);

        comboBoxModifyAssignment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "yes", "no" }));

        modifyAssignmentButton.setText("Modify");
        modifyAssignmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyAssignmentButtonActionPerformed(evt);
            }
        });

        deleteAssignmentButton.setLabel("Delete");
        deleteAssignmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAssignmentButtonActionPerformed(evt);
            }
        });

        assignmentComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentComboBoxActionPerformed(evt);
            }
        });

        courseLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        courseLabel1.setText("Course");

        courseLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        courseLabel2.setText("Course");

        courseField2.setEditable(false);
        courseField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        comboBoxCourse.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout assignmentPanelLayout = new javax.swing.GroupLayout(assignmentPanel);
        assignmentPanel.setLayout(assignmentPanelLayout);
        assignmentPanelLayout.setHorizontalGroup(
            assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assignmentPanelLayout.createSequentialGroup()
                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(assignmentsRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assignmentPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(createAssignmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(197, 197, 197))
                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(assignmentPanelLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(assignmentLabel2)
                                    .addComponent(assignmentLabel1))
                                .addGap(92, 92, 92)
                                .addComponent(createAssignmentLabel))
                            .addGroup(assignmentPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(assignmentField)
                                    .addComponent(assignmentComboBox, 0, 101, Short.MAX_VALUE))
                                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(subjectLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(subjectLabel2)))
                                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(subjectField1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(subjectField2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                                        .addGap(47, 47, 47)
                                        .addComponent(courseLabel2))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assignmentPanelLayout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assignmentPanelLayout.createSequentialGroup()
                                                .addComponent(courseLabel1)
                                                .addGap(32, 32, 32))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assignmentPanelLayout.createSequentialGroup()
                                                .addComponent(comboBoxCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(11, 11, 11))))
                                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(courseField2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(assignmentPanelLayout.createSequentialGroup()
                                            .addGap(35, 35, 35)
                                            .addComponent(dueDateLabel2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                                            .addComponent(visibleLabel2))
                                        .addGroup(assignmentPanelLayout.createSequentialGroup()
                                            .addGap(30, 30, 30)
                                            .addComponent(dueDateLabel1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(visibleLabel1)))
                                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(comboBoxModifyAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(comboBoxCreateAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(assignmentPanelLayout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(modifyAssignmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(105, 105, 105)
                                .addComponent(deleteAssignmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(27, Short.MAX_VALUE))))
        );
        assignmentPanelLayout.setVerticalGroup(
            assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assignmentPanelLayout.createSequentialGroup()
                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(assignmentPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(createAssignmentLabel)
                                .addGap(18, 18, 18)
                                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(subjectLabel1)
                                    .addComponent(courseLabel1)
                                    .addComponent(dueDateLabel1)
                                    .addComponent(visibleLabel1))
                                .addGap(36, 36, 36)
                                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(subjectField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(assignmentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboBoxCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(comboBoxCreateAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addComponent(createAssignmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(assignmentPanelLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(assignmentLabel1)))
                        .addGap(55, 55, 55)
                        .addComponent(jLabel5)
                        .addGap(29, 29, 29)
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(assignmentLabel2)
                            .addComponent(subjectLabel2)
                            .addComponent(dueDateLabel2)
                            .addComponent(visibleLabel2)
                            .addComponent(courseLabel2)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(assignmentsRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assignmentPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(subjectField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(assignmentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(courseField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboBoxModifyAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(modifyAssignmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteAssignmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))))
        );

        jTabbedPane1.addTab("Assignments", assignmentPanel);

        studentTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(studentTable);

        studentRefreshButton.setText("Refresh");
        studentRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentRefreshButtonActionPerformed(evt);
            }
        });

        studentListLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        studentListLabel.setText("Student list");

        studentGradesTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(studentGradesTable);

        assignmentGradesLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        assignmentGradesLabel.setText("Assignment Grades");

        studentModifyLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        studentModifyLabel.setText("Modify");

        studentAssignmentLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        studentAssignmentLabel.setText("Assignment");

        studentStudentLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        studentStudentLabel.setText("Student");

        studentGradeLabel.setText("Grade");

        studentAssignmentCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentAssignmentComboActionPerformed(evt);
            }
        });

        studentStudentCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentStudentComboActionPerformed(evt);
            }
        });

        studentApplyButton.setText("Apply");
        studentApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentApplyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout studentPanelLayout = new javax.swing.GroupLayout(studentPanel);
        studentPanel.setLayout(studentPanelLayout);
        studentPanelLayout.setHorizontalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(studentListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(assignmentGradesLabel)
                .addGap(223, 223, 223))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentPanelLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(studentRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(studentAssignmentLabel))
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addGap(204, 204, 204)
                        .addComponent(studentAssignmentCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(studentStudentCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studentModifyLabel)
                            .addComponent(studentStudentLabel))))
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(studentGradeLabel))
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(studentGradeField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(studentApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        studentPanelLayout.setVerticalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(studentListLabel)
                    .addComponent(assignmentGradesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(studentModifyLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studentRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(studentPanelLayout.createSequentialGroup()
                                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(studentAssignmentLabel)
                                    .addComponent(studentStudentLabel)
                                    .addComponent(studentGradeLabel))
                                .addGap(18, 18, 18)
                                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(studentAssignmentCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(studentStudentCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(studentGradeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(30, 30, 30))
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(studentApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jTabbedPane1.addTab("Student", studentPanel);

        accountLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        accountLabel1.setText("Password Change");

        accountLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        accountLabel2.setText("Current Password:");

        accountLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        accountLabel3.setText("New Password:");

        accountLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        accountLabel4.setText("Repeat New Password:");

        accountButton.setText("Change");
        accountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(254, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(accountLabel3)
                    .addComponent(accountLabel4)
                    .addComponent(accountLabel2))
                .addGap(136, 136, 136)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(accountNewPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accountOldPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accountNewPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(265, 265, 265))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(391, 391, 391)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(accountLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(accountButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(accountLabel1)
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accountLabel2)
                    .addComponent(accountOldPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accountLabel3)
                    .addComponent(accountNewPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accountLabel4)
                    .addComponent(accountNewPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(accountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144))
        );

        jTabbedPane1.addTab("Accound", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        logout();
    }//GEN-LAST:event_formWindowClosing

    private void timetableRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableRefreshButtonActionPerformed
        updateTimetableTable(prepareQuery(timetableQuery, Fid));
    }//GEN-LAST:event_timetableRefreshButtonActionPerformed

    private void assignmentsRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsRefreshButtonActionPerformed
        updateassignmentsTable(prepareQuery(assignmentsQuery, Fid));
    }//GEN-LAST:event_assignmentsRefreshButtonActionPerformed

    private void createAssignmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAssignmentButtonActionPerformed
        createAssignment();
        assignmentFill();
        updateassignmentsTable(prepareQuery(assignmentsQuery, Fid));
    }//GEN-LAST:event_createAssignmentButtonActionPerformed

    private void assignmentComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentComboBoxActionPerformed
        modifyGet(assignmentComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_assignmentComboBoxActionPerformed

    private void modifyAssignmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyAssignmentButtonActionPerformed
        modify(assignmentComboBox.getSelectedItem().toString());
        updateassignmentsTable(prepareQuery(assignmentsQuery, Fid));
    }//GEN-LAST:event_modifyAssignmentButtonActionPerformed

    private void deleteAssignmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAssignmentButtonActionPerformed
        delete(assignmentComboBox.getSelectedItem().toString());
        updateassignmentsTable(prepareQuery(assignmentsQuery, Fid));
    }//GEN-LAST:event_deleteAssignmentButtonActionPerformed

    private void studentRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentRefreshButtonActionPerformed
        updateStudentTable(prepareQuery(studentQuery, Fid));
    }//GEN-LAST:event_studentRefreshButtonActionPerformed

    private void studentAssignmentComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentAssignmentComboActionPerformed
         setStudent(studentAssignmentCombo.getSelectedItem().toString());
        
    }//GEN-LAST:event_studentAssignmentComboActionPerformed

    private void studentStudentComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentStudentComboActionPerformed
        if (studentStudentCombo.getSelectedItem() != null)
            Sid = studentStudentCombo.getSelectedItem().toString();
        setStudentGrade(studentAssignmentCombo.getSelectedItem().toString(),Sid);
        
    }//GEN-LAST:event_studentStudentComboActionPerformed

    private void studentApplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentApplyButtonActionPerformed
        modifyStudentGrade(studentAssignmentCombo.getSelectedItem().toString(),studentStudentCombo.getSelectedItem().toString(),studentGradeField.getText());
        updateStudentGradesTable(prepareQuery(studentGradeQuery, Fid));
    }//GEN-LAST:event_studentApplyButtonActionPerformed

    private void accountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountButtonActionPerformed
        updatePSWD();
    }//GEN-LAST:event_accountButtonActionPerformed

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
            java.util.logging.Logger.getLogger(facultyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(facultyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(facultyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(facultyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new facultyGUI(Fid).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accountButton;
    private javax.swing.JLabel accountLabel1;
    private javax.swing.JLabel accountLabel2;
    private javax.swing.JLabel accountLabel3;
    private javax.swing.JLabel accountLabel4;
    private javax.swing.JPasswordField accountNewPasswordField;
    private javax.swing.JPasswordField accountNewPasswordField2;
    private javax.swing.JPasswordField accountOldPasswordField;
    private javax.swing.JComboBox<String> assignmentComboBox;
    private javax.swing.JTextField assignmentField;
    private javax.swing.JLabel assignmentGradesLabel;
    private javax.swing.JLabel assignmentLabel1;
    private javax.swing.JLabel assignmentLabel2;
    private javax.swing.JPanel assignmentPanel;
    private javax.swing.JButton assignmentsRefreshButton;
    private javax.swing.JTable assignmentsTable;
    private javax.swing.JComboBox<String> comboBoxCourse;
    private javax.swing.JComboBox<String> comboBoxCreateAssignment;
    private javax.swing.JComboBox<String> comboBoxModifyAssignment;
    private javax.swing.JTextField courseField2;
    private javax.swing.JLabel courseLabel1;
    private javax.swing.JLabel courseLabel2;
    private javax.swing.JButton createAssignmentButton;
    private javax.swing.JLabel createAssignmentLabel;
    private com.toedter.calendar.JDateChooser datePicker1;
    private com.toedter.calendar.JDateChooser datePicker2;
    private javax.swing.JButton deleteAssignmentButton;
    private javax.swing.JLabel dueDateLabel1;
    private javax.swing.JLabel dueDateLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton modifyAssignmentButton;
    private javax.swing.JButton studentApplyButton;
    private javax.swing.JComboBox<String> studentAssignmentCombo;
    private javax.swing.JLabel studentAssignmentLabel;
    private javax.swing.JTextField studentGradeField;
    private javax.swing.JLabel studentGradeLabel;
    private javax.swing.JTable studentGradesTable;
    private javax.swing.JLabel studentListLabel;
    private javax.swing.JLabel studentModifyLabel;
    private javax.swing.JPanel studentPanel;
    private javax.swing.JButton studentRefreshButton;
    private javax.swing.JComboBox<String> studentStudentCombo;
    private javax.swing.JLabel studentStudentLabel;
    private javax.swing.JTable studentTable;
    private javax.swing.JTextField subjectField1;
    private javax.swing.JTextField subjectField2;
    private javax.swing.JLabel subjectLabel1;
    private javax.swing.JLabel subjectLabel2;
    private javax.swing.JPanel timetablePanel;
    private javax.swing.JButton timetableRefreshButton;
    private javax.swing.JTable timetableTable;
    private javax.swing.JLabel visibleLabel1;
    private javax.swing.JLabel visibleLabel2;
    // End of variables declaration//GEN-END:variables
}

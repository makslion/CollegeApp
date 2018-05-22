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
        
        designTable(timetableTable,timetableScrollPane);
        designTable(assignmentsTable,assignmentsScrollPane);
        designTable(studentTable,studentScrollPane);
        designTable(studentGradesTable,studentGradesScrollPane);
    }
    
    private void updatePSWD(){
       String currentPSWD = new String (accountOldPasswordField.getPassword());
        char [] newPSWD = accountNewPasswordField.getPassword();
        char [] newPSWD1 = accountNewPasswordField2.getPassword();
        String newPassword = new String (newPSWD);
        ResultSet resultSet = null;
        
        if(!java.util.Arrays.equals(newPSWD,newPSWD1))
            JOptionPane.showMessageDialog(this, "Password not equal","Oops",JOptionPane.WARNING_MESSAGE);
        
        
        try{
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(pswdCheck);
            PrepStatement.setString(1, Fid);
            
            resultSet = PrepStatement.executeQuery();
            
            resultSet.next();
            if(!resultSet.getString("Password").equals(currentPSWD)&&newPassword.matches(pattern)){
                JOptionPane.showMessageDialog(this, "Wrong Password","Oops",JOptionPane.WARNING_MESSAGE);
            }
            else {
                PrepStatement = conn.prepareStatement(pswdQuery);
                
                PrepStatement.setString(1, newPassword);
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

        timetablePanel = new javax.swing.JPanel();
        timetableRefreshButton = new javax.swing.JButton();
        timetableScrollPane = new javax.swing.JScrollPane();
        timetableTable = new javax.swing.JTable();
        timetableButton = new javax.swing.JButton();
        timetableAssignmentsButton = new javax.swing.JButton();
        timetableStrodentButton = new javax.swing.JButton();
        timetableAccountButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        logout1 = new javax.swing.JButton();
        assignmentPanel = new javax.swing.JPanel();
        assignmentsRefreshButton = new javax.swing.JButton();
        assignmentsScrollPane = new javax.swing.JScrollPane();
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
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        assignmentsTimeetableButton = new javax.swing.JButton();
        assignmentsButton = new javax.swing.JButton();
        assignmentsStudentButton = new javax.swing.JButton();
        assignmentsAccountButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        logout2 = new javax.swing.JButton();
        studentPanel = new javax.swing.JPanel();
        studentScrollPane = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        studentRefreshButton = new javax.swing.JButton();
        studentListLabel = new javax.swing.JLabel();
        studentGradesScrollPane = new javax.swing.JScrollPane();
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
        studentTimetableButton = new javax.swing.JButton();
        studentAssignmentsButton = new javax.swing.JButton();
        studentButton = new javax.swing.JButton();
        studentAccountButton = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        logout3 = new javax.swing.JButton();
        accountPanel = new javax.swing.JPanel();
        accountLabel1 = new javax.swing.JLabel();
        accountLabel2 = new javax.swing.JLabel();
        accountLabel3 = new javax.swing.JLabel();
        accountLabel4 = new javax.swing.JLabel();
        accountNewPasswordField = new javax.swing.JPasswordField();
        accountNewPasswordField2 = new javax.swing.JPasswordField();
        changePSWDbutton = new javax.swing.JButton();
        accountOldPasswordField = new javax.swing.JPasswordField();
        jScrollPane5 = new javax.swing.JScrollPane();
        accountTextArea = new javax.swing.JTextArea();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        accountTimetableButton = new javax.swing.JButton();
        accountAssignmentsButton = new javax.swing.JButton();
        accountAstudentButton = new javax.swing.JButton();
        accountButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        logout4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Faculty");
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

        timetablePanel.setBackground(new java.awt.Color(255, 255, 255));
        timetablePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        timetablePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        timetableRefreshButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableRefreshButton.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        timetableRefreshButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableRefreshButton.setText("Refresh");
        timetableRefreshButton.setBorder(null);
        timetableRefreshButton.setFocusPainted(false);
        timetableRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableRefreshButtonActionPerformed(evt);
            }
        });
        timetablePanel.add(timetableRefreshButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 417, 160, 80));

        timetableScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        timetableScrollPane.setBorder(null);

        timetableTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        timetableTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        timetableTable.setGridColor(new java.awt.Color(0, 0, 0));
        timetableTable.setSelectionBackground(new java.awt.Color(204, 204, 204));
        timetableTable.setSelectionForeground(new java.awt.Color(51, 51, 51));
        timetableScrollPane.setViewportView(timetableTable);

        timetablePanel.add(timetableScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 138, 614, 261));

        timetableButton.setBackground(new java.awt.Color(255, 255, 255));
        timetableButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        timetableButton.setForeground(new java.awt.Color(102, 102, 102));
        timetableButton.setText("Timetable");
        timetableButton.setBorder(null);
        timetableButton.setFocusPainted(false);
        timetablePanel.add(timetableButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 44, 88, 30));

        timetableAssignmentsButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableAssignmentsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        timetableAssignmentsButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableAssignmentsButton.setText("Assignments");
        timetableAssignmentsButton.setBorder(null);
        timetableAssignmentsButton.setFocusPainted(false);
        timetableAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableAssignmentsButtonActionPerformed(evt);
            }
        });
        timetablePanel.add(timetableAssignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 44, 100, 20));

        timetableStrodentButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableStrodentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        timetableStrodentButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableStrodentButton.setText("Student");
        timetableStrodentButton.setBorder(null);
        timetableStrodentButton.setFocusPainted(false);
        timetableStrodentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableStrodentButtonActionPerformed(evt);
            }
        });
        timetablePanel.add(timetableStrodentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(216, 44, 70, -1));

        timetableAccountButton.setBackground(new java.awt.Color(102, 102, 102));
        timetableAccountButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        timetableAccountButton.setForeground(new java.awt.Color(255, 255, 255));
        timetableAccountButton.setText("Account");
        timetableAccountButton.setBorder(null);
        timetableAccountButton.setBorderPainted(false);
        timetableAccountButton.setFocusPainted(false);
        timetableAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetableAccountButtonActionPerformed(evt);
            }
        });
        timetablePanel.add(timetableAccountButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(293, 44, 70, -1));

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        logout1.setBackground(new java.awt.Color(102, 102, 102));
        logout1.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logout1.setForeground(new java.awt.Color(255, 255, 255));
        logout1.setText("X");
        logout1.setBorder(null);
        logout1.setFocusPainted(false);
        logout1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 970, Short.MAX_VALUE)
                .addComponent(logout1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(logout1)
                .addGap(0, 36, Short.MAX_VALUE))
        );

        timetablePanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 70));

        getContentPane().add(timetablePanel, "card3");

        assignmentPanel.setBackground(new java.awt.Color(255, 255, 255));
        assignmentPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        assignmentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        assignmentsRefreshButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentsRefreshButton.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        assignmentsRefreshButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentsRefreshButton.setText("Refresh");
        assignmentsRefreshButton.setBorder(null);
        assignmentsRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsRefreshButtonActionPerformed(evt);
            }
        });
        assignmentPanel.add(assignmentsRefreshButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 456, 160, 80));

        assignmentsTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
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
        assignmentsTable.setGridColor(new java.awt.Color(0, 0, 0));
        assignmentsScrollPane.setViewportView(assignmentsTable);

        assignmentPanel.add(assignmentsScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 94, 384, 337));

        createAssignmentLabel.setBackground(new java.awt.Color(255, 255, 255));
        createAssignmentLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        createAssignmentLabel.setForeground(new java.awt.Color(102, 102, 102));
        createAssignmentLabel.setText("Create Assignment");
        assignmentPanel.add(createAssignmentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(613, 80, -1, -1));

        assignmentLabel1.setBackground(new java.awt.Color(255, 255, 255));
        assignmentLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentLabel1.setForeground(new java.awt.Color(102, 102, 102));
        assignmentLabel1.setText("Assignment");
        assignmentPanel.add(assignmentLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, -1, -1));

        subjectLabel1.setBackground(new java.awt.Color(255, 255, 255));
        subjectLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        subjectLabel1.setForeground(new java.awt.Color(102, 102, 102));
        subjectLabel1.setText("Subject");
        assignmentPanel.add(subjectLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 170, 61, -1));

        dueDateLabel1.setBackground(new java.awt.Color(255, 255, 255));
        dueDateLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        dueDateLabel1.setForeground(new java.awt.Color(102, 102, 102));
        dueDateLabel1.setText("Due Date");
        assignmentPanel.add(dueDateLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 170, -1, -1));

        visibleLabel1.setBackground(new java.awt.Color(255, 255, 255));
        visibleLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        visibleLabel1.setForeground(new java.awt.Color(102, 102, 102));
        visibleLabel1.setText("Visible");
        assignmentPanel.add(visibleLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 170, -1, -1));

        assignmentField.setBackground(new java.awt.Color(255, 255, 255));
        assignmentField.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentField.setBorder(null);
        assignmentPanel.add(assignmentField, new org.netbeans.lib.awtextra.AbsoluteConstraints(417, 206, 101, -1));

        subjectField1.setEditable(false);
        subjectField1.setBackground(new java.awt.Color(255, 255, 255));
        subjectField1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        subjectField1.setBorder(null);
        assignmentPanel.add(subjectField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 206, 90, -1));

        comboBoxCreateAssignment.setEditable(true);
        comboBoxCreateAssignment.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        comboBoxCreateAssignment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "yes", "no" }));
        comboBoxCreateAssignment.setBorder(null);
        assignmentPanel.add(comboBoxCreateAssignment, new org.netbeans.lib.awtextra.AbsoluteConstraints(912, 204, 53, -1));

        createAssignmentButton.setBackground(new java.awt.Color(102, 102, 102));
        createAssignmentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        createAssignmentButton.setForeground(new java.awt.Color(255, 255, 255));
        createAssignmentButton.setText("Cteate");
        createAssignmentButton.setBorder(null);
        createAssignmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAssignmentButtonActionPerformed(evt);
            }
        });
        assignmentPanel.add(createAssignmentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 264, 153, 35));

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Modify Assignment");
        assignmentPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(614, 323, -1, -1));

        assignmentLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentLabel2.setForeground(new java.awt.Color(102, 102, 102));
        assignmentLabel2.setText("Assignment");
        assignmentPanel.add(assignmentLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(429, 367, -1, -1));

        subjectLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        subjectLabel2.setForeground(new java.awt.Color(102, 102, 102));
        subjectLabel2.setText("Subject");
        assignmentPanel.add(subjectLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(553, 367, -1, -1));

        dueDateLabel2.setBackground(new java.awt.Color(255, 255, 255));
        dueDateLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        dueDateLabel2.setForeground(new java.awt.Color(102, 102, 102));
        dueDateLabel2.setText("Due Date");
        assignmentPanel.add(dueDateLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 370, -1, -1));

        visibleLabel2.setBackground(new java.awt.Color(255, 255, 255));
        visibleLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        visibleLabel2.setForeground(new java.awt.Color(102, 102, 102));
        visibleLabel2.setText("Visible");
        assignmentPanel.add(visibleLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(906, 367, -1, -1));

        subjectField2.setEditable(false);
        subjectField2.setBackground(new java.awt.Color(255, 255, 255));
        subjectField2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        subjectField2.setBorder(null);
        assignmentPanel.add(subjectField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 408, 90, -1));

        comboBoxModifyAssignment.setEditable(true);
        comboBoxModifyAssignment.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        comboBoxModifyAssignment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "yes", "no" }));
        comboBoxModifyAssignment.setBorder(null);
        assignmentPanel.add(comboBoxModifyAssignment, new org.netbeans.lib.awtextra.AbsoluteConstraints(906, 406, 53, -1));

        modifyAssignmentButton.setBackground(new java.awt.Color(102, 102, 102));
        modifyAssignmentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        modifyAssignmentButton.setForeground(new java.awt.Color(255, 255, 255));
        modifyAssignmentButton.setText("Modify");
        modifyAssignmentButton.setBorder(null);
        modifyAssignmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyAssignmentButtonActionPerformed(evt);
            }
        });
        assignmentPanel.add(modifyAssignmentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(473, 455, 153, 81));

        deleteAssignmentButton.setBackground(new java.awt.Color(102, 102, 102));
        deleteAssignmentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        deleteAssignmentButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteAssignmentButton.setBorder(null);
        deleteAssignmentButton.setLabel("Delete");
        deleteAssignmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAssignmentButtonActionPerformed(evt);
            }
        });
        assignmentPanel.add(deleteAssignmentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(767, 455, 153, 81));

        assignmentComboBox.setEditable(true);
        assignmentComboBox.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentComboBox.setBorder(null);
        assignmentComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentComboBoxActionPerformed(evt);
            }
        });
        assignmentPanel.add(assignmentComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(417, 406, 101, -1));

        courseLabel1.setBackground(new java.awt.Color(255, 255, 255));
        courseLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        courseLabel1.setForeground(new java.awt.Color(102, 102, 102));
        courseLabel1.setText("Course");
        assignmentPanel.add(courseLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 170, -1, -1));

        courseLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        courseLabel2.setForeground(new java.awt.Color(102, 102, 102));
        courseLabel2.setText("Course");
        assignmentPanel.add(courseLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(668, 367, -1, -1));

        courseField2.setEditable(false);
        courseField2.setBackground(new java.awt.Color(255, 255, 255));
        courseField2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        courseField2.setBorder(null);
        assignmentPanel.add(courseField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(653, 408, 92, -1));

        comboBoxCourse.setEditable(true);
        comboBoxCourse.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        comboBoxCourse.setBorder(null);
        assignmentPanel.add(comboBoxCourse, new org.netbeans.lib.awtextra.AbsoluteConstraints(644, 204, 85, -1));

        datePicker1.setBackground(new java.awt.Color(255, 255, 255));
        datePicker1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentPanel.add(datePicker1, new org.netbeans.lib.awtextra.AbsoluteConstraints(767, 204, 127, 25));

        datePicker2.setBackground(new java.awt.Color(255, 255, 255));
        datePicker2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentPanel.add(datePicker2, new org.netbeans.lib.awtextra.AbsoluteConstraints(767, 406, 117, 25));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        assignmentPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(417, 438, 101, 10));

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        assignmentPanel.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(767, 438, 119, 10));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        assignmentPanel.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(904, 438, 55, 10));

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        assignmentPanel.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(417, 236, 101, 10));

        jSeparator8.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        assignmentPanel.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(644, 236, 85, 10));

        jSeparator9.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        assignmentPanel.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(767, 236, 127, 10));

        jSeparator10.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        assignmentPanel.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(912, 236, 53, 10));

        assignmentsTimeetableButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentsTimeetableButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentsTimeetableButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentsTimeetableButton.setText("Timetable");
        assignmentsTimeetableButton.setBorder(null);
        assignmentsTimeetableButton.setFocusPainted(false);
        assignmentsTimeetableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsTimeetableButtonActionPerformed(evt);
            }
        });
        assignmentPanel.add(assignmentsTimeetableButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 44, 88, -1));

        assignmentsButton.setBackground(new java.awt.Color(255, 255, 255));
        assignmentsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentsButton.setForeground(new java.awt.Color(102, 102, 102));
        assignmentsButton.setText("Assignments");
        assignmentsButton.setBorder(null);
        assignmentsButton.setFocusPainted(false);
        assignmentPanel.add(assignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 44, 100, 30));

        assignmentsStudentButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentsStudentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentsStudentButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentsStudentButton.setText("Student");
        assignmentsStudentButton.setBorder(null);
        assignmentsStudentButton.setFocusPainted(false);
        assignmentsStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsStudentButtonActionPerformed(evt);
            }
        });
        assignmentPanel.add(assignmentsStudentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(217, 44, 70, -1));

        assignmentsAccountButton.setBackground(new java.awt.Color(102, 102, 102));
        assignmentsAccountButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        assignmentsAccountButton.setForeground(new java.awt.Color(255, 255, 255));
        assignmentsAccountButton.setText("Account");
        assignmentsAccountButton.setBorder(null);
        assignmentsAccountButton.setBorderPainted(false);
        assignmentsAccountButton.setFocusPainted(false);
        assignmentsAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignmentsAccountButtonActionPerformed(evt);
            }
        });
        assignmentPanel.add(assignmentsAccountButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(294, 44, 70, -1));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        logout2.setBackground(new java.awt.Color(102, 102, 102));
        logout2.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logout2.setForeground(new java.awt.Color(255, 255, 255));
        logout2.setText("X");
        logout2.setBorder(null);
        logout2.setFocusPainted(false);
        logout2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 970, Short.MAX_VALUE)
                .addComponent(logout2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(logout2)
                .addGap(0, 36, Short.MAX_VALUE))
        );

        assignmentPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 70));

        getContentPane().add(assignmentPanel, "card4");

        studentPanel.setBackground(new java.awt.Color(255, 255, 255));
        studentPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        studentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        studentTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        studentScrollPane.setViewportView(studentTable);

        studentPanel.add(studentScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 136, 346, 273));

        studentRefreshButton.setBackground(new java.awt.Color(102, 102, 102));
        studentRefreshButton.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        studentRefreshButton.setForeground(new java.awt.Color(255, 255, 255));
        studentRefreshButton.setText("Refresh");
        studentRefreshButton.setBorder(null);
        studentRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentRefreshButtonActionPerformed(evt);
            }
        });
        studentPanel.add(studentRefreshButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 456, 160, 80));

        studentListLabel.setBackground(new java.awt.Color(255, 255, 255));
        studentListLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        studentListLabel.setForeground(new java.awt.Color(102, 102, 102));
        studentListLabel.setText("Student list");
        studentPanel.add(studentListLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 92, -1, -1));

        studentGradesTable.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
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
        studentGradesScrollPane.setViewportView(studentGradesTable);

        studentPanel.add(studentGradesScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 136, -1, 273));

        assignmentGradesLabel.setBackground(new java.awt.Color(255, 255, 255));
        assignmentGradesLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        assignmentGradesLabel.setForeground(new java.awt.Color(102, 102, 102));
        assignmentGradesLabel.setText("Assignment Grades");
        studentPanel.add(assignmentGradesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(609, 92, -1, -1));

        studentModifyLabel.setBackground(new java.awt.Color(255, 255, 255));
        studentModifyLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        studentModifyLabel.setForeground(new java.awt.Color(102, 102, 102));
        studentModifyLabel.setText("Modify");
        studentPanel.add(studentModifyLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(631, 427, -1, -1));

        studentAssignmentLabel.setBackground(new java.awt.Color(255, 255, 255));
        studentAssignmentLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        studentAssignmentLabel.setForeground(new java.awt.Color(102, 102, 102));
        studentAssignmentLabel.setText("Assignment");
        studentPanel.add(studentAssignmentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 460, -1, -1));

        studentStudentLabel.setBackground(new java.awt.Color(255, 255, 255));
        studentStudentLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        studentStudentLabel.setForeground(new java.awt.Color(102, 102, 102));
        studentStudentLabel.setText("Student");
        studentPanel.add(studentStudentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(631, 460, -1, -1));

        studentGradeLabel.setBackground(new java.awt.Color(255, 255, 255));
        studentGradeLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studentGradeLabel.setForeground(new java.awt.Color(102, 102, 102));
        studentGradeLabel.setText("Grade");
        studentPanel.add(studentGradeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(745, 461, -1, -1));

        studentAssignmentCombo.setEditable(true);
        studentAssignmentCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studentAssignmentCombo.setBorder(null);
        studentAssignmentCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentAssignmentComboActionPerformed(evt);
            }
        });
        studentPanel.add(studentAssignmentCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 494, 91, -1));

        studentStudentCombo.setEditable(true);
        studentStudentCombo.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studentStudentCombo.setBorder(null);
        studentStudentCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentStudentComboActionPerformed(evt);
            }
        });
        studentPanel.add(studentStudentCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(617, 494, 81, -1));

        studentGradeField.setBackground(new java.awt.Color(255, 255, 255));
        studentGradeField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        studentGradeField.setBorder(null);
        studentPanel.add(studentGradeField, new org.netbeans.lib.awtextra.AbsoluteConstraints(734, 494, 70, -1));

        studentApplyButton.setBackground(new java.awt.Color(102, 102, 102));
        studentApplyButton.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        studentApplyButton.setForeground(new java.awt.Color(255, 255, 255));
        studentApplyButton.setText("Apply");
        studentApplyButton.setBorder(null);
        studentApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentApplyButtonActionPerformed(evt);
            }
        });
        studentPanel.add(studentApplyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(822, 461, 110, 75));

        studentTimetableButton.setBackground(new java.awt.Color(102, 102, 102));
        studentTimetableButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        studentTimetableButton.setForeground(new java.awt.Color(255, 255, 255));
        studentTimetableButton.setText("Timetable");
        studentTimetableButton.setBorder(null);
        studentTimetableButton.setFocusPainted(false);
        studentTimetableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentTimetableButtonActionPerformed(evt);
            }
        });
        studentPanel.add(studentTimetableButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 44, 88, -1));

        studentAssignmentsButton.setBackground(new java.awt.Color(102, 102, 102));
        studentAssignmentsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        studentAssignmentsButton.setForeground(new java.awt.Color(255, 255, 255));
        studentAssignmentsButton.setText("Assignments");
        studentAssignmentsButton.setBorder(null);
        studentAssignmentsButton.setFocusPainted(false);
        studentAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentAssignmentsButtonActionPerformed(evt);
            }
        });
        studentPanel.add(studentAssignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 44, 100, -1));

        studentButton.setBackground(new java.awt.Color(255, 255, 255));
        studentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        studentButton.setForeground(new java.awt.Color(102, 102, 102));
        studentButton.setText("Student");
        studentButton.setBorder(null);
        studentButton.setFocusPainted(false);
        studentPanel.add(studentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(217, 44, 70, 30));

        studentAccountButton.setBackground(new java.awt.Color(102, 102, 102));
        studentAccountButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        studentAccountButton.setForeground(new java.awt.Color(255, 255, 255));
        studentAccountButton.setText("Account");
        studentAccountButton.setBorder(null);
        studentAccountButton.setBorderPainted(false);
        studentAccountButton.setFocusPainted(false);
        studentAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentAccountButtonActionPerformed(evt);
            }
        });
        studentPanel.add(studentAccountButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(294, 44, 70, -1));

        jSeparator11.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        studentPanel.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 526, 91, 10));

        jSeparator12.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        studentPanel.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(617, 526, 81, 10));

        jSeparator13.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));
        studentPanel.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(734, 526, 70, 10));

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        logout3.setBackground(new java.awt.Color(102, 102, 102));
        logout3.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logout3.setForeground(new java.awt.Color(255, 255, 255));
        logout3.setText("X");
        logout3.setBorder(null);
        logout3.setFocusPainted(false);
        logout3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 970, Short.MAX_VALUE)
                .addComponent(logout3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(logout3)
                .addGap(0, 36, Short.MAX_VALUE))
        );

        studentPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 70));

        getContentPane().add(studentPanel, "card5");

        accountPanel.setBackground(new java.awt.Color(255, 255, 255));
        accountPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        accountPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        accountLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        accountLabel1.setForeground(new java.awt.Color(102, 102, 102));
        accountLabel1.setText("Password Change");
        accountPanel.add(accountLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(421, 92, -1, -1));

        accountLabel2.setBackground(new java.awt.Color(255, 255, 255));
        accountLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        accountLabel2.setForeground(new java.awt.Color(102, 102, 102));
        accountLabel2.setText("Current Password:");
        accountPanel.add(accountLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 138, -1, -1));

        accountLabel3.setBackground(new java.awt.Color(255, 255, 255));
        accountLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        accountLabel3.setForeground(new java.awt.Color(102, 102, 102));
        accountLabel3.setText("New Password:");
        accountPanel.add(accountLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 209, -1, -1));

        accountLabel4.setBackground(new java.awt.Color(255, 255, 255));
        accountLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        accountLabel4.setForeground(new java.awt.Color(102, 102, 102));
        accountLabel4.setText("Repeat New Password:");
        accountPanel.add(accountLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 281, -1, -1));

        accountNewPasswordField.setBackground(new java.awt.Color(255, 255, 255));
        accountNewPasswordField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        accountNewPasswordField.setBorder(null);
        accountPanel.add(accountNewPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 207, 133, -1));

        accountNewPasswordField2.setBackground(new java.awt.Color(255, 255, 255));
        accountNewPasswordField2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        accountNewPasswordField2.setBorder(null);
        accountPanel.add(accountNewPasswordField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 279, 133, -1));

        changePSWDbutton.setBackground(new java.awt.Color(102, 102, 102));
        changePSWDbutton.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        changePSWDbutton.setForeground(new java.awt.Color(255, 255, 255));
        changePSWDbutton.setText("Change");
        changePSWDbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePSWDbuttonActionPerformed(evt);
            }
        });
        accountPanel.add(changePSWDbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(427, 330, 139, 71));

        accountOldPasswordField.setBackground(new java.awt.Color(255, 255, 255));
        accountOldPasswordField.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        accountOldPasswordField.setBorder(null);
        accountPanel.add(accountOldPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 136, 133, -1));

        accountTextArea.setEditable(false);
        accountTextArea.setColumns(20);
        accountTextArea.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        accountTextArea.setForeground(new java.awt.Color(102, 102, 102));
        accountTextArea.setRows(5);
        accountTextArea.setText("NOTE:\n\nPassword MUST be AT LEAST 8 characters \n\nPassword MUST contain AT LEAST one lower- or uppercase carater and AT LEAST one digit");
        accountTextArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jScrollPane5.setViewportView(accountTextArea);

        accountPanel.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 427, 971, -1));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        accountPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 168, 133, 10));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        accountPanel.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 239, 133, 10));

        jSeparator7.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        accountPanel.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 307, 133, 10));

        accountTimetableButton.setBackground(new java.awt.Color(102, 102, 102));
        accountTimetableButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        accountTimetableButton.setForeground(new java.awt.Color(255, 255, 255));
        accountTimetableButton.setText("Timetable");
        accountTimetableButton.setBorder(null);
        accountTimetableButton.setFocusPainted(false);
        accountTimetableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountTimetableButtonActionPerformed(evt);
            }
        });
        accountPanel.add(accountTimetableButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 44, 88, -1));

        accountAssignmentsButton.setBackground(new java.awt.Color(102, 102, 102));
        accountAssignmentsButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        accountAssignmentsButton.setForeground(new java.awt.Color(255, 255, 255));
        accountAssignmentsButton.setText("Assignments");
        accountAssignmentsButton.setBorder(null);
        accountAssignmentsButton.setFocusPainted(false);
        accountAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountAssignmentsButtonActionPerformed(evt);
            }
        });
        accountPanel.add(accountAssignmentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 44, 100, -1));

        accountAstudentButton.setBackground(new java.awt.Color(102, 102, 102));
        accountAstudentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        accountAstudentButton.setForeground(new java.awt.Color(255, 255, 255));
        accountAstudentButton.setText("Student");
        accountAstudentButton.setBorder(null);
        accountAstudentButton.setFocusPainted(false);
        accountAstudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountAstudentButtonActionPerformed(evt);
            }
        });
        accountPanel.add(accountAstudentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(217, 44, 70, 20));

        accountButton.setBackground(new java.awt.Color(255, 255, 255));
        accountButton.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        accountButton.setForeground(new java.awt.Color(102, 102, 102));
        accountButton.setText("Account");
        accountButton.setBorder(null);
        accountButton.setBorderPainted(false);
        accountButton.setFocusPainted(false);
        accountPanel.add(accountButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(294, 44, 70, 30));

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        logout4.setBackground(new java.awt.Color(102, 102, 102));
        logout4.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        logout4.setForeground(new java.awt.Color(255, 255, 255));
        logout4.setText("X");
        logout4.setBorder(null);
        logout4.setFocusPainted(false);
        logout4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 970, Short.MAX_VALUE)
                .addComponent(logout4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(logout4)
                .addGap(0, 36, Short.MAX_VALUE))
        );

        accountPanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 70));

        getContentPane().add(accountPanel, "card6");

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

    private void changePSWDbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePSWDbuttonActionPerformed
        updatePSWD();
    }//GEN-LAST:event_changePSWDbuttonActionPerformed

    private void timetableAssignmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableAssignmentsButtonActionPerformed
        timetablePanel.setVisible(false);
        assignmentPanel.setVisible(true);
    }//GEN-LAST:event_timetableAssignmentsButtonActionPerformed

    private void timetableStrodentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableStrodentButtonActionPerformed
        studentPanel.setVisible(true);
        timetablePanel.setVisible(false);
    }//GEN-LAST:event_timetableStrodentButtonActionPerformed

    private void timetableAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableAccountButtonActionPerformed
        accountPanel.setVisible(true);
        timetablePanel.setVisible(false);
    }//GEN-LAST:event_timetableAccountButtonActionPerformed

    private void assignmentsTimeetableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsTimeetableButtonActionPerformed
        timetablePanel.setVisible(true);
        assignmentPanel.setVisible(false);
    }//GEN-LAST:event_assignmentsTimeetableButtonActionPerformed

    private void assignmentsStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsStudentButtonActionPerformed
        studentPanel.setVisible(true);
        assignmentPanel.setVisible(false);
    }//GEN-LAST:event_assignmentsStudentButtonActionPerformed

    private void assignmentsAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsAccountButtonActionPerformed
        accountPanel.setVisible(true);
        assignmentPanel.setVisible(false);
    }//GEN-LAST:event_assignmentsAccountButtonActionPerformed

    private void studentTimetableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentTimetableButtonActionPerformed
        studentPanel.setVisible(false);
        timetablePanel.setVisible(true);
    }//GEN-LAST:event_studentTimetableButtonActionPerformed

    private void studentAssignmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentAssignmentsButtonActionPerformed
        studentPanel.setVisible(false);
        assignmentPanel.setVisible(true);
    }//GEN-LAST:event_studentAssignmentsButtonActionPerformed

    private void studentAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentAccountButtonActionPerformed
        studentPanel.setVisible(false);
        accountPanel.setVisible(true);
    }//GEN-LAST:event_studentAccountButtonActionPerformed

    private void accountTimetableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountTimetableButtonActionPerformed
        accountPanel.setVisible(false);
        timetablePanel.setVisible(true);
    }//GEN-LAST:event_accountTimetableButtonActionPerformed

    private void accountAssignmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountAssignmentsButtonActionPerformed
        accountPanel.setVisible(false);
        assignmentPanel.setVisible(true);
    }//GEN-LAST:event_accountAssignmentsButtonActionPerformed

    private void accountAstudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountAstudentButtonActionPerformed
        accountPanel.setVisible(false);
        studentPanel.setVisible(true);
    }//GEN-LAST:event_accountAstudentButtonActionPerformed

    private void logout4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logout4ActionPerformed
        logout();
    }//GEN-LAST:event_logout4ActionPerformed

    private void logout3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logout3ActionPerformed
        logout();
    }//GEN-LAST:event_logout3ActionPerformed

    private void logout2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logout2ActionPerformed
        logout();
    }//GEN-LAST:event_logout2ActionPerformed

    private void logout1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logout1ActionPerformed
        logout();
    }//GEN-LAST:event_logout1ActionPerformed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_formMousePressed

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
    private javax.swing.JButton accountAssignmentsButton;
    private javax.swing.JButton accountAstudentButton;
    private javax.swing.JButton accountButton;
    private javax.swing.JLabel accountLabel1;
    private javax.swing.JLabel accountLabel2;
    private javax.swing.JLabel accountLabel3;
    private javax.swing.JLabel accountLabel4;
    private javax.swing.JPasswordField accountNewPasswordField;
    private javax.swing.JPasswordField accountNewPasswordField2;
    private javax.swing.JPasswordField accountOldPasswordField;
    private javax.swing.JPanel accountPanel;
    private javax.swing.JTextArea accountTextArea;
    private javax.swing.JButton accountTimetableButton;
    private javax.swing.JComboBox<String> assignmentComboBox;
    private javax.swing.JTextField assignmentField;
    private javax.swing.JLabel assignmentGradesLabel;
    private javax.swing.JLabel assignmentLabel1;
    private javax.swing.JLabel assignmentLabel2;
    private javax.swing.JPanel assignmentPanel;
    private javax.swing.JButton assignmentsAccountButton;
    private javax.swing.JButton assignmentsButton;
    private javax.swing.JButton assignmentsRefreshButton;
    private javax.swing.JScrollPane assignmentsScrollPane;
    private javax.swing.JButton assignmentsStudentButton;
    private javax.swing.JTable assignmentsTable;
    private javax.swing.JButton assignmentsTimeetableButton;
    private javax.swing.JButton changePSWDbutton;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton logout1;
    private javax.swing.JButton logout2;
    private javax.swing.JButton logout3;
    private javax.swing.JButton logout4;
    private javax.swing.JButton modifyAssignmentButton;
    private javax.swing.JButton studentAccountButton;
    private javax.swing.JButton studentApplyButton;
    private javax.swing.JComboBox<String> studentAssignmentCombo;
    private javax.swing.JLabel studentAssignmentLabel;
    private javax.swing.JButton studentAssignmentsButton;
    private javax.swing.JButton studentButton;
    private javax.swing.JTextField studentGradeField;
    private javax.swing.JLabel studentGradeLabel;
    private javax.swing.JScrollPane studentGradesScrollPane;
    private javax.swing.JTable studentGradesTable;
    private javax.swing.JLabel studentListLabel;
    private javax.swing.JLabel studentModifyLabel;
    private javax.swing.JPanel studentPanel;
    private javax.swing.JButton studentRefreshButton;
    private javax.swing.JScrollPane studentScrollPane;
    private javax.swing.JComboBox<String> studentStudentCombo;
    private javax.swing.JLabel studentStudentLabel;
    private javax.swing.JTable studentTable;
    private javax.swing.JButton studentTimetableButton;
    private javax.swing.JTextField subjectField1;
    private javax.swing.JTextField subjectField2;
    private javax.swing.JLabel subjectLabel1;
    private javax.swing.JLabel subjectLabel2;
    private javax.swing.JButton timetableAccountButton;
    private javax.swing.JButton timetableAssignmentsButton;
    private javax.swing.JButton timetableButton;
    private javax.swing.JPanel timetablePanel;
    private javax.swing.JButton timetableRefreshButton;
    private javax.swing.JScrollPane timetableScrollPane;
    private javax.swing.JButton timetableStrodentButton;
    private javax.swing.JTable timetableTable;
    private javax.swing.JLabel visibleLabel1;
    private javax.swing.JLabel visibleLabel2;
    // End of variables declaration//GEN-END:variables
}

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
    private static String Fid;
    
    private String timetableQuery = "SELECT timetable.Day,\n" +
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
    
    private String assignmentsQuery = "SELECT assignments.Aid AS 'Assignment',\n" +
                                    "		subjects.Subject,\n" +
                                    "        assignments.Cid AS 'Course',\n" +
                                    "        assignments.DueDate AS 'Due date',\n" +
                                    "        assignments.Visible\n" +
                                    "FROM (assignments\n" +
                                    "	INNER JOIN subjects ON assignments.Subject = subjects.SBid)\n" +
                                    "    WHERE subjects.Lecturer = ?;";
    
    private String assignmentFillQuery = "SELECT assignments.Aid,\n" +
                                        "		subjects.Subject,\n" +
                                        "        assignments.Cid\n" +
                                        "FROM (assignments\n" +
                                        "	INNER JOIN subjects ON assignments.Subject = subjects.SBid)\n" +
                                        "WHERE assignments.Subject = (SELECT subjects.SBid\n" +
                                        "							FROM subjects\n" +
                                        "                            WHERE subjects.Lecturer = ?);";
    
    private String createAssignmentQuery = "INSERT INTO `programming_db`.`assignments` "
            + "(`Cid`, `Aid`, `Subject`, `Visible`, `DueDate`) VALUES (?, ?, ?, ?, ?);";
    
    private String getSubjectQuery = "SELECT subjects.SBid\n" +
                                    "FROM subjects\n" +
                                    "WHERE subjects.Subject = ?;";
    
    private String modifyGetQuery = "SELECT subjects.Subject,\n" +
                                    "		assignments.Cid,\n" +
                                    "        assignments.DueDate,\n" +
                                    "        assignments.Visible\n" +
                                    "FROM (assignments\n" +
                                    "	INNER JOIN subjects ON assignments.Subject = subjects.SBid)\n" +
                                    "WHERE assignments.Aid = ?;";
    
    private String modifyQuery = "UPDATE `programming_db`.`assignments` SET `Visible`=?, `DueDate`=? WHERE `Aid`=?;";
    
    private String deleteQuery = "DELETE FROM `programming_db`.`assignments` WHERE `Aid`=?;";
    
    private String studentQuery = "SELECT student.Sid AS 'Student ID',\n" +
                                "		student.Fname AS 'First Name',\n" +
                                "        student.Lname AS 'Last Name',\n" +
                                "        student.Phone AS 'Contact Phone'\n" +
                                "FROM (student\n" +
                                "	INNER JOIN group_details ON student.Course = group_details.Cid)\n" +
                                "WHERE group_details.Superviser = ?\n" +
                                "ORDER BY student.Sid;";
    
    public facultyGUI(String fid) {
        initComponents();
        readConnectionDetails();
        
        Fid = fid;
        
        updateTimetableTable(prepareQuery(timetableQuery));
        updateassignmentsTable(prepareQuery(assignmentsQuery));
        updateStudentTable(prepareQuery(studentQuery));
        assignmentFill();
        
        
    }
    
    private void delete(String aid){
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String formatedDate = sdf.format(date);
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
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
            connected = true;
            
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
        ResultSet rs = prepareQuery(assignmentFillQuery);
        
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
    
    
    private void createAssignment(){
        ResultSet resultSet = null;
        Date date = datePicker1.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String formatedDate = sdf.format(date);
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
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
                connected = true;

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
    
    
    private ResultSet prepareQuery (String query){
        ResultSet resultSet = null;
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(query);
            PrepStatement.setString(1, Fid);
            
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
        datePicker1 = new org.jdesktop.swingx.JXDatePicker();
        datePicker2 = new org.jdesktop.swingx.JXDatePicker();
        studentPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        studentRefreshButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Faculty");
        setPreferredSize(new java.awt.Dimension(955, 531));
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
                .addContainerGap(72, Short.MAX_VALUE))
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
                                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(comboBoxCreateAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                        .addGap(7, 7, 7)
                                        .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(comboBoxModifyAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(assignmentPanelLayout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(modifyAssignmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(105, 105, 105)
                                .addComponent(deleteAssignmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(23, Short.MAX_VALUE))))
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
                                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(subjectField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(assignmentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(comboBoxCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(comboBoxCreateAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(39, 39, 39)
                                .addComponent(createAssignmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(assignmentPanelLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(assignmentLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(55, 55, 55)
                        .addComponent(jLabel5)
                        .addGap(29, 29, 29)
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(assignmentLabel2)
                            .addComponent(subjectLabel2)
                            .addComponent(dueDateLabel2)
                            .addComponent(visibleLabel2)
                            .addComponent(courseLabel2)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(assignmentPanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(assignmentsRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assignmentPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(assignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(subjectField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(assignmentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(courseField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboBoxModifyAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        javax.swing.GroupLayout studentPanelLayout = new javax.swing.GroupLayout(studentPanel);
        studentPanel.setLayout(studentPanelLayout);
        studentPanelLayout.setHorizontalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentPanelLayout.createSequentialGroup()
                .addContainerGap(408, Short.MAX_VALUE)
                .addComponent(studentRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(382, 382, 382))
        );
        studentPanelLayout.setVerticalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(studentRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Student", studentPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 955, Short.MAX_VALUE)
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
        updateTimetableTable(prepareQuery(timetableQuery));
    }//GEN-LAST:event_timetableRefreshButtonActionPerformed

    private void assignmentsRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsRefreshButtonActionPerformed
        updateassignmentsTable(prepareQuery(assignmentsQuery));
    }//GEN-LAST:event_assignmentsRefreshButtonActionPerformed

    private void createAssignmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAssignmentButtonActionPerformed
        createAssignment();
        assignmentFill();
        updateassignmentsTable(prepareQuery(assignmentsQuery));
    }//GEN-LAST:event_createAssignmentButtonActionPerformed

    private void assignmentComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentComboBoxActionPerformed
        modifyGet(assignmentComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_assignmentComboBoxActionPerformed

    private void modifyAssignmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyAssignmentButtonActionPerformed
        modify(assignmentComboBox.getSelectedItem().toString());
        updateassignmentsTable(prepareQuery(assignmentsQuery));
    }//GEN-LAST:event_modifyAssignmentButtonActionPerformed

    private void deleteAssignmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAssignmentButtonActionPerformed
        delete(assignmentComboBox.getSelectedItem().toString());
        updateassignmentsTable(prepareQuery(assignmentsQuery));
    }//GEN-LAST:event_deleteAssignmentButtonActionPerformed

    private void studentRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentRefreshButtonActionPerformed
        updateStudentTable(prepareQuery(studentQuery));
    }//GEN-LAST:event_studentRefreshButtonActionPerformed

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
            public void run() {
                new facultyGUI(Fid).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> assignmentComboBox;
    private javax.swing.JTextField assignmentField;
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
    private org.jdesktop.swingx.JXDatePicker datePicker1;
    private org.jdesktop.swingx.JXDatePicker datePicker2;
    private javax.swing.JButton deleteAssignmentButton;
    private javax.swing.JLabel dueDateLabel1;
    private javax.swing.JLabel dueDateLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton modifyAssignmentButton;
    private javax.swing.JPanel studentPanel;
    private javax.swing.JButton studentRefreshButton;
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

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
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Maksym Vavilov 16856
 */

public class studentGUI extends studentDatabase {

    /**
     * Creates new form studentGUI
     */
    
    private static String Sid;
    private String assignmentQuery = "SELECT assignments.Aid AS 'Assignment',\n" +
                                    "        subjects.Subject,\n" +
                                    "        assignments.DueDate AS 'Due date',\n" +
                                    "        assignment_grades.Grade\n" +
                                    "FROM ((assignments\n" +
                                    "	INNER JOIN subjects ON assignments.Subject = subjects.SBid)\n" +
                                    "   INNER JOIN assignment_grades ON assignments.Aid = assignment_grades.Aid)\n" +
                                    "   WHERE Sid = ? AND Visible = 'yes';";
    
    private String gradesQuery = "SELECT subjects.Subject,\n" +
                                "		grades.Grade,\n" +
                                "        grades.ExamGrade AS 'Exam Grade'\n" +
                                "FROM (grades\n" +
                                "	INNER JOIN subjects ON grades.SBid = subjects.SBid)\n" +
                                "    WHERE Sid = ?;";
    
    private String timetableQuery = "SELECT timetable.Day,\n" +
                                "		timetable.Subject,\n" +
                                "        timetable.Start,\n" +
                                "        timetable.End\n" +
                                "FROM (timetable\n" +
                                "	INNER JOIN groups ON timetable.Gid = groups.Gid)\n" +
                                "    WHERE Sid = ?"+ 
                                "    ORDER BY Subject;";
    
    private String pswdQuery = "UPDATE login SET `Password`=? WHERE `Username`=?;";
    private String pasdCheck = "SELECT login.Password FROM login WHERE login.Username = ?;";
    
    
    public studentGUI(String sid) {
        initComponents();
        
        Sid = sid;
        readConnectionDetails();
        
        updateAssignmentTable(prepareQuery(assignmentQuery));
        
        updateGradesTable(prepareQuery(gradesQuery));
        
        updateTimetableTable(prepareQuery(timetableQuery));
        
    }
    
    private void updateAssignmentTable(ResultSet rs){
        assignmentsTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    private void updateGradesTable(ResultSet rs){
        gradesTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    private void updateTimetableTable(ResultSet rs){
        timetableTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    private ResultSet prepareQuery (String query){
        ResultSet resultSet = null;
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(query);
            PrepStatement.setString(1, Sid);
            
            resultSet = PrepStatement.executeQuery();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return resultSet;
    }
    
    private void updatePSWD(){
       String currentPSWD = new String (currentPSWDfield.getPassword());
        char [] newPSWD = newPSWDfield.getPassword();
        char [] newPSWD1 = newPSWDfield.getPassword();
        ResultSet resultSet = null;
        
        if(!java.util.Arrays.equals(newPSWD,newPSWD1))
            JOptionPane.showMessageDialog(this, "Password not equal","Oops",JOptionPane.WARNING_MESSAGE);
        
        
        try{
            Connection conn = DriverManager.getConnection(connectionDetails);
            connected = true;
            
            PrepStatement = conn.prepareStatement(pasdCheck);
            PrepStatement.setString(1, Sid);
            
            resultSet = PrepStatement.executeQuery();
            
            resultSet.next();
            if(!resultSet.getString("Password").equals(currentPSWD)){
                JOptionPane.showMessageDialog(this, "Wrong Password","Oops",JOptionPane.WARNING_MESSAGE);
            }
            else {
                PrepStatement = conn.prepareStatement(pswdQuery);
                
                PrepStatement.setString(1, new String (newPSWD));
                PrepStatement.setString(2, Sid);
                
                int result = PrepStatement.executeUpdate();
                System.out.println(result);
                JOptionPane.showMessageDialog(this, "Password Changed!","Hooray!",JOptionPane.INFORMATION_MESSAGE);
            }
            
            if(connected)
                conn.close();
            
            
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this, e,"Ooops",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        assignmentsPanel = new javax.swing.JPanel();
        assignmentsRefreshButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        assignmentsTable = new javax.swing.JTable();
        gradesPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        gradesTable = new javax.swing.JTable();
        gradesRefreshButton = new javax.swing.JButton();
        timetablePanel = new javax.swing.JPanel();
        timetableRefreshButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        timetableTable = new javax.swing.JTable();
        acountPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        currentPSWDlabel = new javax.swing.JLabel();
        newPSWDlabel = new javax.swing.JLabel();
        newPSWDlabel1 = new javax.swing.JLabel();
        currentPSWDfield = new javax.swing.JPasswordField();
        newPSWDfield = new javax.swing.JPasswordField();
        newPSWDfield1 = new javax.swing.JPasswordField();
        changePSWDbutton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Student");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

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
        jScrollPane1.setViewportView(assignmentsTable);

        javax.swing.GroupLayout assignmentsPanelLayout = new javax.swing.GroupLayout(assignmentsPanel);
        assignmentsPanel.setLayout(assignmentsPanelLayout);
        assignmentsPanelLayout.setHorizontalGroup(
            assignmentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
            .addGroup(assignmentsPanelLayout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(assignmentsRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        assignmentsPanelLayout.setVerticalGroup(
            assignmentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assignmentsPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(assignmentsRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        tabbedPane.addTab("Assignments", assignmentsPanel);

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
        jScrollPane2.setViewportView(gradesTable);

        gradesRefreshButton.setText("Refresh");
        gradesRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradesRefreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gradesPanelLayout = new javax.swing.GroupLayout(gradesPanel);
        gradesPanel.setLayout(gradesPanelLayout);
        gradesPanelLayout.setHorizontalGroup(
            gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
            .addGroup(gradesPanelLayout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addComponent(gradesRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gradesPanelLayout.setVerticalGroup(
            gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gradesPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(gradesRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        tabbedPane.addTab("Grades", gradesPanel);

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
        jScrollPane3.setViewportView(timetableTable);

        javax.swing.GroupLayout timetablePanelLayout = new javax.swing.GroupLayout(timetablePanel);
        timetablePanel.setLayout(timetablePanelLayout);
        timetablePanelLayout.setHorizontalGroup(
            timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
            .addGroup(timetablePanelLayout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(timetableRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        timetablePanelLayout.setVerticalGroup(
            timetablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, timetablePanelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(timetableRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        tabbedPane.addTab("Timetable", timetablePanel);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Password Change");

        currentPSWDlabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        currentPSWDlabel.setText("Current Password");

        newPSWDlabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        newPSWDlabel.setText("New Password");

        newPSWDlabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        newPSWDlabel1.setText("Repeat New Passord");

        changePSWDbutton.setText("Change");
        changePSWDbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePSWDbuttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout acountPanelLayout = new javax.swing.GroupLayout(acountPanel);
        acountPanel.setLayout(acountPanelLayout);
        acountPanelLayout.setHorizontalGroup(
            acountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acountPanelLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(acountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newPSWDlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currentPSWDlabel)
                    .addComponent(newPSWDlabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(acountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(newPSWDfield1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(newPSWDfield)
                    .addComponent(currentPSWDfield))
                .addGap(91, 91, 91))
            .addGroup(acountPanelLayout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addGroup(acountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changePSWDbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(238, Short.MAX_VALUE))
        );
        acountPanelLayout.setVerticalGroup(
            acountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acountPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(acountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currentPSWDlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currentPSWDfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(acountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newPSWDlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newPSWDfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(acountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newPSWDlabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newPSWDfield1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(changePSWDbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159))
        );

        tabbedPane.addTab("Account", acountPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        logout();
    }//GEN-LAST:event_formWindowClosing

    private void assignmentsRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignmentsRefreshButtonActionPerformed
        updateAssignmentTable(prepareQuery(assignmentQuery));
    }//GEN-LAST:event_assignmentsRefreshButtonActionPerformed

    private void gradesRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradesRefreshButtonActionPerformed
        updateGradesTable(prepareQuery(gradesQuery));
    }//GEN-LAST:event_gradesRefreshButtonActionPerformed

    private void timetableRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetableRefreshButtonActionPerformed
        updateTimetableTable(prepareQuery(timetableQuery));
    }//GEN-LAST:event_timetableRefreshButtonActionPerformed

    private void changePSWDbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePSWDbuttonActionPerformed
        updatePSWD();
    }//GEN-LAST:event_changePSWDbuttonActionPerformed

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
            java.util.logging.Logger.getLogger(studentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(studentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(studentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(studentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new studentGUI(Sid).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel acountPanel;
    private javax.swing.JPanel assignmentsPanel;
    private javax.swing.JButton assignmentsRefreshButton;
    private javax.swing.JTable assignmentsTable;
    private javax.swing.JButton changePSWDbutton;
    private javax.swing.JPasswordField currentPSWDfield;
    private javax.swing.JLabel currentPSWDlabel;
    private javax.swing.JPanel gradesPanel;
    private javax.swing.JButton gradesRefreshButton;
    private javax.swing.JTable gradesTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPasswordField newPSWDfield;
    private javax.swing.JPasswordField newPSWDfield1;
    private javax.swing.JLabel newPSWDlabel;
    private javax.swing.JLabel newPSWDlabel1;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JPanel timetablePanel;
    private javax.swing.JButton timetableRefreshButton;
    private javax.swing.JTable timetableTable;
    // End of variables declaration//GEN-END:variables
}

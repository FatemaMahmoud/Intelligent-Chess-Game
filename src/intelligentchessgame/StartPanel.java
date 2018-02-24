/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intelligentchessgame;

import java.awt.Color;
import javax.swing.BorderFactory;


public class StartPanel extends javax.swing.JPanel {

    private int diffLvl;
    private int clr;
    
    public StartPanel() {
        initComponents();
        diffLvl = 0;
        clr = -1;
    }
    
    public int getDiffLvl(){
        return diffLvl;
    }
    
    public int getClr(){
        return this.clr;
    }
    
    public String getName(){
        return this.jTextField1.getText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setAutoscrolls(true);
        setPreferredSize(new java.awt.Dimension(880, 880));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 51, 0));
        jLabel1.setText("Difficulty Level:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 580, -1, -1));

        jTextField1.setFont(new java.awt.Font("Myanmar Text", 1, 18)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 670, 143, 33));

        jLabel2.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 51, 0));
        jLabel2.setText("Player's Name:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 580, -1, -1));

        jLabel3.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 51, 0));
        jLabel3.setText("I play as:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 580, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/d197374.png"))); // NOI18N
        jLabel4.setText("jLabel4");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 640, 100, 100));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CHESSPIECEBISHOP.png"))); // NOI18N
        jLabel5.setText("jLabel4");
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 640, 100, 100));
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, -1, 430));

        jLabel7.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 70)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 0));
        jLabel7.setText("CHESS GAME");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        jLabel8.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Easy");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 660, 60, 53));

        jLabel9.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Hard");
        jLabel9.setToolTipText("");
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 660, 70, 53));

        jLabel13.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Expert");
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 660, 80, 53));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/38678.png"))); // NOI18N
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 560, 430));
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        int lvl = 1;
        if (diffLvl != lvl){
            jLabel8.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            diffLvl = lvl;
        }
        else
            jLabel8.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
        int lvl = 2;
        if (diffLvl != lvl){
            jLabel9.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            diffLvl = lvl;
        }
        else
            jLabel9.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        int lvl = 8;
        if (diffLvl != lvl){
            jLabel13.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            diffLvl = lvl;
        }
        else
            jLabel13.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        int c = 1;
        if (c != clr){
            jLabel5.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            clr = c;
        }
        else
            jLabel5.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        int c = 0;
        if (c != clr){
            jLabel4.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            clr = c;
        }
        else
            jLabel4.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }//GEN-LAST:event_jLabel4MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

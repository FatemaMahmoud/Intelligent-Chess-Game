
package intelligentchessgame;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ChessFrame extends javax.swing.JFrame {

    public static ChessBoard cb;
    public StartPanel sp;
    public static GameStatePanel gsp;
    public static String name;
    
    public ChessFrame() {
        initComponents();
        scndInitComps();
    }
    
    private void scndInitComps(){
       
        //this.setResizable(false);
        this.getContentPane().setBackground(Color.WHITE);
        this.setTitle("Chess Game");
        ImageIcon i = new ImageIcon(this.getClass().getClassLoader().getResource("38678.png"));
        this.setIconImage(i.getImage());
        sp = new StartPanel();
        sp.setBounds(0, 0, 839, 800);
        this.add(sp);
        
        cb = new ChessBoard();
        cb.setBounds(0, 100, 820 , 820);
        this.add(cb);
        cb.setVisible(false);
        this.setBounds(420, 20, 839, 967);
        name = "";
    }
    
   public static void save(){
       cb.saveGame();
   }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton7 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 800));
        getContentPane().setLayout(null);

        jButton7.setBackground(new java.awt.Color(51, 102, 0));
        jButton7.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 36)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Let's PLAY!");
        jButton7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7);
        jButton7.setBounds(330, 790, 180, 47);

        jButton1.setBackground(new java.awt.Color(153, 102, 0));
        jButton1.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Load Game");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(350, 860, 150, 39);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(sp.getDiffLvl() == 0){
            JOptionPane.showMessageDialog(this, "Please choose difficulty level");
            return;
        }
        if(sp.getClr() == -1){
            JOptionPane.showMessageDialog(this, "Please choose either black or white to play with");
            return;
        }
        if(sp.getName().equals("")){
            JOptionPane.showMessageDialog(this, "Please enter your name");
            return;
        }
        gsp = new GameStatePanel(sp.getName());
        gsp.setTurnText();
        gsp.setBounds(0, 0, 825, 100);
        this.add(gsp);
        cb.setVisible(true);
        gsp.setVisible(true);
        sp.setVisible(false);
        this.jButton7.setVisible(false);
        this.jButton1.setVisible(false);
        cb.init_players(1-sp.getClr(), sp.getDiffLvl()+1);
        Controller.start = true;
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cb.load();
        gsp = new GameStatePanel(sp.getName());
        gsp.setTurnText();
        gsp.setBounds(0, 0, 825, 100);
        this.add(gsp);
        cb.setVisible(true);
        gsp.setVisible(true);
        sp.setVisible(false);
        this.jButton7.setVisible(false);
        this.jButton1.setVisible(false);
        gsp.set(name);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ChessFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChessFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChessFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChessFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChessFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton7;
    // End of variables declaration//GEN-END:variables
}

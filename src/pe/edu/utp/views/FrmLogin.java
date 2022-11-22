package pe.edu.utp.views;

import java.util.Stack;

public class FrmLogin extends javax.swing.JFrame {

    public boolean isLogin = false;
    public String user;
    public String password;
    public Stack<Integer> pNumbers = new Stack<Integer>();
    public String caca;

    public FrmLogin() {
        initComponents();
        this.setLocationRelativeTo(null);
        pNumbers.push(7);
        pNumbers.push(8);
        pNumbers.push(9);
        pNumbers.push(4);
        pNumbers.push(5);
        pNumbers.push(6);
        pNumbers.push(1);
        pNumbers.push(2);
        pNumbers.push(3);
        pNumbers.push(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlbIcon = new javax.swing.JLabel();
        jbNumer3 = new javax.swing.JButton();
        txtUserPass = new javax.swing.JTextField();
        jbNumer1 = new javax.swing.JButton();
        jbNumer2 = new javax.swing.JButton();
        jbNumer6 = new javax.swing.JButton();
        jbNumer4 = new javax.swing.JButton();
        jbNumer5 = new javax.swing.JButton();
        jbNumer9 = new javax.swing.JButton();
        jbNumer7 = new javax.swing.JButton();
        jbNumer8 = new javax.swing.JButton();
        jbCheck = new javax.swing.JButton();
        jbClear = new javax.swing.JButton();
        jbNumer10 = new javax.swing.JButton();
        jlbUserPass = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio de Sesión");

        jlbIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario.png"))); // NOI18N

        jbNumer3.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/9.png"))); // NOI18N
        jbNumer3.setBorder(null);
        jbNumer3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNumer3ActionPerformed(evt);
            }
        });

        txtUserPass.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtUserPass.setEnabled(false);

        jbNumer1.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/7.png"))); // NOI18N
        jbNumer1.setBorder(null);
        jbNumer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNumer1ActionPerformed(evt);
            }
        });

        jbNumer2.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/8.png"))); // NOI18N
        jbNumer2.setBorder(null);
        jbNumer2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNumer2ActionPerformed(evt);
            }
        });

        jbNumer6.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/6.png"))); // NOI18N
        jbNumer6.setBorder(null);
        jbNumer6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNumer6ActionPerformed(evt);
            }
        });

        jbNumer4.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/4.png"))); // NOI18N
        jbNumer4.setBorder(null);
        jbNumer4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNumer4ActionPerformed(evt);
            }
        });

        jbNumer5.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/5.png"))); // NOI18N
        jbNumer5.setBorder(null);
        jbNumer5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNumer5ActionPerformed(evt);
            }
        });

        jbNumer9.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/3.png"))); // NOI18N
        jbNumer9.setBorder(null);
        jbNumer9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNumer9ActionPerformed(evt);
            }
        });

        jbNumer7.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/1.png"))); // NOI18N
        jbNumer7.setBorder(null);
        jbNumer7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNumer7ActionPerformed(evt);
            }
        });

        jbNumer8.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/2.png"))); // NOI18N
        jbNumer8.setBorder(null);
        jbNumer8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNumer8ActionPerformed(evt);
            }
        });

        jbCheck.setBackground(new java.awt.Color(242, 242, 242));
        jbCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresar.png"))); // NOI18N
        jbCheck.setBorder(null);
        jbCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCheckActionPerformed(evt);
            }
        });

        jbClear.setBackground(new java.awt.Color(242, 242, 242));
        jbClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/borrar.png"))); // NOI18N
        jbClear.setBorder(null);
        jbClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbClearActionPerformed(evt);
            }
        });

        jbNumer10.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/0.png"))); // NOI18N
        jbNumer10.setBorder(null);
        jbNumer10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNumer10ActionPerformed(evt);
            }
        });

        jlbUserPass.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jlbUserPass.setText("USUARIO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbNumer4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbNumer7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbClear, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbNumer1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jbNumer5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbNumer8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbNumer10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbNumer2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbNumer6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbNumer9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbNumer3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtUserPass, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlbUserPass)
                    .addComponent(jlbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(156, 156, 156))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jlbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlbUserPass)
                .addGap(7, 7, 7)
                .addComponent(txtUserPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbNumer1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbNumer2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbNumer3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbNumer4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbNumer5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbNumer6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbNumer7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbNumer8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbNumer9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbClear, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbNumer10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbClearActionPerformed
        txtUserPass.setText("");
    }//GEN-LAST:event_jbClearActionPerformed

    private void jbNumer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNumer1ActionPerformed
        txtUserPass.setText(txtUserPass.getText() + pNumbers.get(0));
    }//GEN-LAST:event_jbNumer1ActionPerformed

    private void jbNumer2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNumer2ActionPerformed
        txtUserPass.setText(txtUserPass.getText() + pNumbers.get(1));
    }//GEN-LAST:event_jbNumer2ActionPerformed

    private void jbNumer3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNumer3ActionPerformed
        txtUserPass.setText(txtUserPass.getText() + pNumbers.get(2));
    }//GEN-LAST:event_jbNumer3ActionPerformed

    private void jbNumer4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNumer4ActionPerformed
        txtUserPass.setText(txtUserPass.getText() + pNumbers.get(3));
    }//GEN-LAST:event_jbNumer4ActionPerformed

    private void jbNumer5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNumer5ActionPerformed
        txtUserPass.setText(txtUserPass.getText() + pNumbers.get(4));
    }//GEN-LAST:event_jbNumer5ActionPerformed

    private void jbNumer6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNumer6ActionPerformed
        txtUserPass.setText(txtUserPass.getText() + pNumbers.get(5));
    }//GEN-LAST:event_jbNumer6ActionPerformed

    private void jbNumer7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNumer7ActionPerformed
        txtUserPass.setText(txtUserPass.getText() + pNumbers.get(6));
    }//GEN-LAST:event_jbNumer7ActionPerformed

    private void jbNumer8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNumer8ActionPerformed
        txtUserPass.setText(txtUserPass.getText() + pNumbers.get(7));
    }//GEN-LAST:event_jbNumer8ActionPerformed

    private void jbNumer9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNumer9ActionPerformed
        txtUserPass.setText(txtUserPass.getText() + pNumbers.get(8));
    }//GEN-LAST:event_jbNumer9ActionPerformed

    private void jbNumer10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNumer10ActionPerformed
        txtUserPass.setText(txtUserPass.getText() + pNumbers.get(9));
    }//GEN-LAST:event_jbNumer10ActionPerformed

    private void jbCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCheckActionPerformed
        user = txtUserPass.getText();
        aleatorio();
        txtUserPass.setText("");
        jlbIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/bloquear.png"))); 
        jlbUserPass.setText("PASSWORD");
    }//GEN-LAST:event_jbCheckActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }

    public void aleatorio() {
        int position;
        pNumbers.clear();
        for (int i = 0; i < 10; i++) {
            position = (int) Math.floor(Math.random() * 10);
            while (pNumbers.contains(position)) {
                position = (int) Math.floor(Math.random() * 10);
            }
            pNumbers.push(position);
        }
        
        jbNumer1.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/" + pNumbers.get(0) + ".png"))); // NOI18N
        jbNumer1.setBorder(null);
        
        jbNumer2.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/" + pNumbers.get(1) + ".png"))); // NOI18N
        jbNumer2.setBorder(null);
        
        jbNumer3.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/" + pNumbers.get(2) + ".png"))); // NOI18N
        jbNumer3.setBorder(null);
        
        jbNumer4.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/" + pNumbers.get(3) + ".png"))); // NOI18N
        jbNumer4.setBorder(null);
        
        jbNumer5.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/" + pNumbers.get(4) + ".png"))); // NOI18N
        jbNumer5.setBorder(null);
        
        jbNumer6.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/" + pNumbers.get(5) + ".png"))); // NOI18N
        jbNumer6.setBorder(null);
        
        jbNumer7.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/" + pNumbers.get(6) + ".png"))); // NOI18N
        jbNumer7.setBorder(null);
        
        jbNumer8.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/" + pNumbers.get(7) + ".png"))); // NOI18N
        jbNumer8.setBorder(null);
        
        jbNumer9.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/" + pNumbers.get(8) + ".png"))); // NOI18N
        jbNumer9.setBorder(null);
        
        jbNumer10.setBackground(new java.awt.Color(242, 242, 242));
        jbNumer10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/" + pNumbers.get(9) + ".png"))); // NOI18N
        jbNumer10.setBorder(null);

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbCheck;
    private javax.swing.JButton jbClear;
    private javax.swing.JButton jbNumer1;
    private javax.swing.JButton jbNumer10;
    private javax.swing.JButton jbNumer2;
    private javax.swing.JButton jbNumer3;
    private javax.swing.JButton jbNumer4;
    private javax.swing.JButton jbNumer5;
    private javax.swing.JButton jbNumer6;
    private javax.swing.JButton jbNumer7;
    private javax.swing.JButton jbNumer8;
    private javax.swing.JButton jbNumer9;
    private javax.swing.JLabel jlbIcon;
    private javax.swing.JLabel jlbUserPass;
    private javax.swing.JTextField txtUserPass;
    // End of variables declaration//GEN-END:variables
}

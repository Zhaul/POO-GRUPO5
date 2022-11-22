
package pe.edu.utp.view;

import java.awt.Color;

public class MainClass {
    public static void main(String[] args) {
        FrmSplash frmSplash = new FrmSplash();
        FrmLogin frmLogin = new FrmLogin();
        frmSplash.setVisible(true);
        try {
            for (int row = 0; row <= 100; row++) {
                Thread.sleep(50);
                frmSplash.pgbCargando.setValue(row);
                frmSplash.lblPorcentaje.setText("Cargando " + row + " %");
                if (row == 100) {
                    frmSplash.setVisible(false);
                    frmLogin.setVisible(true);
                }
            }
        } catch (Exception e) {
        }
    }
}

/**
 * Este código ha sido construido por Antony García González y el Equipo
 * Creativo de Panama Hitek.
 *
 * Está protegido bajo la licencia LGPL v 2.1, cuya copia se puede encontrar en
 * el siguiente enlace: http://www.gnu.org/licenses/lgpl.txt
 *
 * Para su funcionamiento utiliza el código de la librería JSSC (anteriormente
 * RXTX) que ha permanecido intacto sin modificación alguna de parte de nuestro
 * equipo creativo. Agradecemos al creador de la librería JSSC, Alexey Sokolov
 * por esta herramienta tan poderosa y eficaz que ha hecho posible el
 * mejoramiento de nuestra librería.
 *
 * Esta librería es de código abierto y ha sido diseñada para que los usuarios,
 * desde principiantes hasta expertos puedan contar con las herramientas
 * apropiadas para el desarrollo de sus proyectos, de una forma sencilla y
 * agradable.
 *
 * Se espera que se en cualquier uso de este código se reconozca su procedencia.
 * Este algoritmo fue diseñado en la República de Panamá por Antony García
 * Gónzález, estudiante de la Universidad de Panamá en la carrera de
 * Licenciatura en Ingeniería Electromecánica, desde el año 2013 hasta el
 * presente. Su diseñador forma parte del Equipo Creativo de Panama Hitek, una
 * organización sin fines de lucro dedicada a la enseñanza del desarrollo de
 * software y hardware a través de su sitio web oficial http://panamahitek.com
 *
 * Solamente deseamos que se reconozca esta compilación de código como un
 * trabajo hecho por panameños para Panamá y el mundo.
 *
 * Si desea contactarnos escríbanos a creativeteam@panamahitek.com
 */
package examples.liveplots;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_MultiMessage;
import com.panamahitek.liveinterfaces.PanamaHitek_DualDialChart;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * Este ejemplo permite graficar datos recibidos desde Arduino en la forma de un
 * reloj analogico simple, de una sola aguja. Los datos son recibidos y
 * graficados manualmente en la grafica.
 *
 * @author Antony Garcia
 *
 * Utilizar con el ejemplo double_data_send.ino corriendo en el Arduino
 */
public class ManualDualDialChart extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public ManualDualDialChart() {
        initComponents();
        //Se crea una grafica con los titulos especificados
        PanamaHitek_DualDialChart dial = new PanamaHitek_DualDialChart("Grafico de prueba", "Temperatura");
        //Limite inferior y superior los medidores analogos
        dial.setChartBottonLimitValues(0, 0);
        dial.setChartTopLimitValues(100, 100);
        //Cantidad de divisiones (rayitas) de los graficos
        dial.setChartMinorDivisions(10, 10);
        dial.setChartMajorDivisions(20, 10);
        //Se agrega el grafico al panel insertado en el JFrame
        dial.insertToPanel(jPanel1);

        //Se crea una instancia de la libreria PanamaHitek_Arduino
        PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
        /**
         * Es necesario crear una instancia de la clase para manejar varios
         * mensajes a la vez, inducando que se van a recibir datos de dos
         * sensores simultaneos.
         */
        PanamaHitek_MultiMessage multi = new PanamaHitek_MultiMessage(2, ino);
        //Se crea un listener para "escuchar" el puerto serie
        SerialPortEventListener event = (SerialPortEvent serialPortEvent) -> {
            try {
                //Se recibe la informacion y se inserta en la grafica
                if (multi.dataReceptionCompleted()) {
                    /**
                     * Se reciben los datos de los sensores 0 y 1 y se insertan
                     * en la grafica. El primer sensor ira al circulo exterior y
                     * el segundo sensor al circulo interior
                     */
                    dial.setValue(Double.parseDouble(multi.getMessage(0)),
                            Double.parseDouble(multi.getMessage(1)));
                
                    //Se limpia el buffer
                    multi.flushBuffer();
                }

            } catch (SerialPortException | ArduinoException ex) {
                Logger.getLogger(ManualSingleDialChart.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        try {
            //Instruccion que crea la conexion con el Arduino en el puerto COM20
            ino.arduinoRX("COM21", 115200, event);
        } catch (ArduinoException | SerialPortException ex) {
            Logger.getLogger(ManualSingleDialChart.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 576, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 574, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ManualDualDialChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManualDualDialChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManualDualDialChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManualDualDialChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManualDualDialChart().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

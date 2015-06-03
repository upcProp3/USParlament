/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upc.fib.prop.usParlament.presentation;

import es.upc.fib.prop.usParlament.misc.*;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 *
 * @author miquel
 */
public class ShowComMPsInfoWindow extends javax.swing.JFrame {

    private PresentationController pc;

    /**
     * Creates new form ShowMPInfoWindow
     * @param pc
     */
    public ShowComMPsInfoWindow(PresentationController pc, String comName) {
        initComponents();
        this.pc = pc;

        actu(comName);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ShowMPInfoTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        doneButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ShowMPInfoTable.setModel(new DefaultTableModel(
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
        ShowMPInfoTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(ShowMPInfoTable);

        jLabel1.setText("MPs in community information");

        doneButton.setText("Done");
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(126, 126, 126)
                                .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 107, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(doneButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void actu(String comN) {
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        JSONObject jattrd = pc.getAttrDefs();
        JSONArray a = ((JSONArray)jattrd.getJSONByKey("Attribute Definitions"));

        dtm.addColumn("State");
        dtm.addColumn("District");
        dtm.addColumn("Name");

        for(JSON jo:a.getArray()){
            String s =((JSONString)((JSONObject)jo).getJSONByKey("AttrDefName")).getValue();
            dtm.addColumn(s);
        }

        System.out.println(pc.getMPsCurrentPartition(comN));




        for(JSONObject mpShort:pc.getMPsCurrentPartition(comN)) {

            State st = State.valueOf((((JSONString) mpShort.getJSONByKey("State")).getValue()));
            Integer dist = Integer.parseInt(((JSONString) mpShort.getJSONByKey("District")).getValue());

            JSONObject mp = pc.getMPInfo(st,dist);

            Vector<String> row = new Vector<String>();
            row.add(((JSONString) mp.getJSONByKey("State")).getValue());
            row.add(((JSONString) mp.getJSONByKey("District")).getValue());
            row.add(((JSONString) mp.getJSONByKey("Name")).getValue());

            JSONArray ja = (JSONArray) mp.getJSONByKey("Attributes");
            for (int cnum = 3; cnum < dtm.getColumnCount(); ++cnum) {
                boolean found = false;
                for (JSON j : ja.getArray()) {
                    JSONObject jo = (JSONObject) j;
                    if (((JSONString) jo.getJSONByKey("AttrDefName")).getValue().equals(dtm.getColumnName(cnum))) {
                        found = true;
                        row.add(((JSONString) jo.getJSONByKey("AttrValue")).getValue());
                        break;
                    }
                }
                if (!found) row.add("-");
            }
            dtm.addRow(row);
        }
        ShowMPInfoTable.setModel(dtm);
        ShowMPInfoTable.getTableHeader().setReorderingAllowed(false);

        ShowMPInfoTable.setAutoCreateRowSorter(true);
        ShowMPInfoTable.getRowSorter().toggleSortOrder(0);

    }


    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_doneButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ShowComMPsInfoWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShowComMPsInfoWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShowComMPsInfoWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowComMPsInfoWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
             //   new ShowMPInfoWindow(pc).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ShowMPInfoTable;
    private javax.swing.JButton doneButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.neuroph.netbeans.wizards;

import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Stefan
 */
public class ConvolutionalLayerPanel extends javax.swing.JPanel {

    /**
     * Creates new form ConvolutionalLayerPanel
     */
    ConvolutionalNetworkVisualPanel2 cv2;
    int j = 0;

    public ConvolutionalLayerPanel(ConvolutionalNetworkVisualPanel2 cv2, int j) {
        initComponents();
        this.cv2 = cv2;
        this.j = j;

    }

    ConvolutionalLayerPanel(ConvolutionalNetworkVisualPanel2 aThis, JPanel jp, int cols) {
        initComponents();
        this.cv2 = aThis;
        j = cols;
        ConvolutionalLayerPanel cp = (ConvolutionalLayerPanel) jp;
        this.jtxtNumberOfMaps.setText(cp.getJtxtNumberOfMaps().getText());
        this.jtxtKernelWidth.setText(cp.getJtxtKernelWidth().getText());
        String s = cp.getJtxtKernelWidth().getText().trim();
        this.jtxtKernelheight.setText(cp.getJtxtKernelheight().getText());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtxtNumberOfMaps = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtxtKernelWidth = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtKernelheight = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(ConvolutionalLayerPanel.class, "ConvolutionalLayerPanel.border.title"))); // NOI18N
        setToolTipText(org.openide.util.NbBundle.getMessage(ConvolutionalLayerPanel.class, "ConvolutionalLayerPanel.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ConvolutionalLayerPanel.class, "ConvolutionalLayerPanel.jLabel1.text")); // NOI18N
        add(jLabel1);

        jtxtNumberOfMaps.setColumns(3);
        jtxtNumberOfMaps.setText(org.openide.util.NbBundle.getMessage(ConvolutionalLayerPanel.class, "ConvolutionalLayerPanel.jtxtNumberOfMaps.text")); // NOI18N
        add(jtxtNumberOfMaps);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ConvolutionalLayerPanel.class, "ConvolutionalLayerPanel.jLabel2.text")); // NOI18N
        add(jLabel2);

        jtxtKernelWidth.setColumns(3);
        jtxtKernelWidth.setText(org.openide.util.NbBundle.getMessage(ConvolutionalLayerPanel.class, "ConvolutionalLayerPanel.jtxtKernelWidth.text")); // NOI18N
        add(jtxtKernelWidth);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(ConvolutionalLayerPanel.class, "ConvolutionalLayerPanel.jLabel3.text")); // NOI18N
        add(jLabel3);

        jtxtKernelheight.setColumns(3);
        jtxtKernelheight.setText(org.openide.util.NbBundle.getMessage(ConvolutionalLayerPanel.class, "ConvolutionalLayerPanel.jtxtKernelheight.text")); // NOI18N
        jtxtKernelheight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtKernelheightActionPerformed(evt);
            }
        });
        add(jtxtKernelheight);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(ConvolutionalLayerPanel.class, "ConvolutionalLayerPanel.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtKernelheightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtKernelheightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtKernelheightActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int z = JOptionPane.showConfirmDialog(this, "This will also delete all layers below. Are you sure?", "Delete layers", JOptionPane.YES_NO_OPTION);
        if (z == JOptionPane.YES_OPTION) {
            int k = cv2.getPanelList().size() - 1;
            while (k >= j) {

                cv2.getPanelList().remove(k);
                k--;

            }
            cv2.deleteLayers(cv2.getPanelList());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public JTextField getJtxtKernelWidth() {
        return jtxtKernelWidth;
    }

    public void setJtxtKernelWidth(JTextField jtxtKernelWidth) {
        this.jtxtKernelWidth = jtxtKernelWidth;
    }

    public JTextField getJtxtKernelheight() {
        return jtxtKernelheight;
    }

    public void setJtxtKernelheight(JTextField jtxtKernelheight) {
        this.jtxtKernelheight = jtxtKernelheight;
    }

    public JTextField getJtxtNumberOfMaps() {
        return jtxtNumberOfMaps;
    }

    public void setJtxtNumberOfMaps(JTextField jtxtNumberOfMaps) {
        this.jtxtNumberOfMaps = jtxtNumberOfMaps;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jtxtKernelWidth;
    private javax.swing.JTextField jtxtKernelheight;
    private javax.swing.JTextField jtxtNumberOfMaps;
    // End of variables declaration//GEN-END:variables
}

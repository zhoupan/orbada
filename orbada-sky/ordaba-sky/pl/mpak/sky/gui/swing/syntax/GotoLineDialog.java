/*
 * LineNumberDialog.java
 *
 * Created on 29 październik 2007, 21:02
 */

package pl.mpak.sky.gui.swing.syntax;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import pl.mpak.sky.Messages;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.SwingUtil;

/**
 *
 * @author  akaluza
 */
public class GotoLineDialog extends javax.swing.JDialog {
  private static final long serialVersionUID = -7690040313604436927L;
  
  private int modalResult = ModalResult.NONE;
  private int lineCount;
  
  /** Creates new form LineNumberDialog
   * @param parent
   * @param lineCount
   */
  public GotoLineDialog(java.awt.Frame parent, int lineCount) {
    super(parent);
    this.lineCount = lineCount;
    initComponents();
    init();
  }
  
  public static int show(java.awt.Frame parent, int lineCount) {
    GotoLineDialog dialog = new GotoLineDialog(parent, lineCount);
    dialog.setVisible(true);
    if (dialog.modalResult == ModalResult.OK) {
      return Integer.parseInt(dialog.textLineNo.getText()) -1;
    } else {
      return -1;
    }
  }
  
  private void init() {
    labelLines.setText(labelLines.getText() +" (1.." +lineCount +"):"); //$NON-NLS-1$ //$NON-NLS-2$
    textLineNo.getDocument().addDocumentListener(new DocumentListener() {
      public void insertUpdate(DocumentEvent e) {
        textChanged();
      }
      public void removeUpdate(DocumentEvent e) {
        textChanged();
      }
      public void changedUpdate(DocumentEvent e) {
      }
    });
    
    getRootPane().setDefaultButton(buttonOk);
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(cmCancel.getShortCut(), "cmCancel"); //$NON-NLS-1$
    getRootPane().getActionMap().put("cmCancel", cmCancel); //$NON-NLS-1$
    SwingUtil.setButtonSizesTheSame(new AbstractButton[] {buttonOk, buttonCancel});
    pack();
    pl.mpak.sky.gui.swing.SwingUtil.centerWithinScreen(this);
  }
  
  private void textChanged() {
    try {
      int newLine = Integer.parseInt(textLineNo.getText());
      cmOk.setEnabled(newLine >= 1 && newLine <= lineCount);
    } catch (NumberFormatException ex) {
      cmOk.setEnabled(false);
    }
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    cmOk = new pl.mpak.sky.gui.swing.Action();
    cmCancel = new pl.mpak.sky.gui.swing.Action();
    labelLines = new javax.swing.JLabel();
    textLineNo = new pl.mpak.sky.gui.swing.comp.TextField();
    buttonOk = new javax.swing.JButton();
    buttonCancel = new javax.swing.JButton();

    cmOk.setEnabled(false);
    cmOk.setText(Messages.getString("GotoLineDialog.ok")); //$NON-NLS-1$
    cmOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmOkActionPerformed(evt);
      }
    });

    cmCancel.setShortCut(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
    cmCancel.setText(Messages.getString("GotoLineDialog.cancel")); //$NON-NLS-1$
    cmCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmCancelActionPerformed(evt);
      }
    });

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle(Messages.getString("GotoLineDialog.goto-line")); //$NON-NLS-1$
    setModal(true);

    labelLines.setText(Messages.getString("GotoLineDialog.input-line-no")); //$NON-NLS-1$

    textLineNo.setPreferredSize(new java.awt.Dimension(150, 22));

    buttonOk.setAction(cmOk);
    buttonOk.setMargin(new java.awt.Insets(2, 2, 2, 2));
    buttonOk.setPreferredSize(new java.awt.Dimension(85, 24));

    buttonCancel.setAction(cmCancel);
    buttonCancel.setMargin(new java.awt.Insets(2, 2, 2, 2));
    buttonCancel.setPreferredSize(new java.awt.Dimension(85, 24));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(labelLines)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textLineNo, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(labelLines)
          .addComponent(textLineNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents
  
private void cmCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmCancelActionPerformed
  modalResult = ModalResult.CANCEL;
  dispose();
}//GEN-LAST:event_cmCancelActionPerformed

private void cmOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmOkActionPerformed
  modalResult = ModalResult.OK;
  dispose();
}//GEN-LAST:event_cmOkActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonCancel;
  private javax.swing.JButton buttonOk;
  private pl.mpak.sky.gui.swing.Action cmCancel;
  private pl.mpak.sky.gui.swing.Action cmOk;
  private javax.swing.JLabel labelLines;
  private pl.mpak.sky.gui.swing.comp.TextField textLineNo;
  // End of variables declaration//GEN-END:variables
  
}

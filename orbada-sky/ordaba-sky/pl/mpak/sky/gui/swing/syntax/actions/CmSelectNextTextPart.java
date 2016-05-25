package pl.mpak.sky.gui.swing.syntax.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.text.DefaultEditorKit;

import pl.mpak.sky.Messages;
import pl.mpak.sky.gui.swing.SwingUtil;

public class CmSelectNextTextPart extends CmTextArea {
  private static final long serialVersionUID = -3214162298393341515L;

  public CmSelectNextTextPart(JTextArea textArea) {
    super(textArea, Messages.getString("CmSelectNextTextPart.text"), null); //$NON-NLS-1$
    setActionCommandKey(DefaultEditorKit.selectionNextWordAction);
    addActionListener(createActionListener());
  }

  private ActionListener createActionListener() {
    return new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int pos = SwingUtil.getNextWord(textArea, textArea.getCaretPosition());
        textArea.moveCaretPosition(pos);
      }
    };
  }
  
}

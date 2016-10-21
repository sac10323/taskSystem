package jp.co.sac.routineTaskSystem.log;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import jp.co.sac.routineTaskSystem.constant.Const;

/**
 *
 * @author shogo_saito
 */
public class ShowMessage extends Thread {

    private String message = null;
    private String title = null;

    @Override
    public void run() {
        try {
            if (Const.isWindows()) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } else if (Const.isMac()) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.mac.MacLookAndFeel");
            }
//            else if (Const.isLinux()) {
//                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            } else if (Const.isSunOS()) {
//                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            }
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

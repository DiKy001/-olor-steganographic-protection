import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class App {
    private JButton encryptButton;
    private JButton decryptButton;
    private JPanel panelMain;
    private JTextPane textPane1;
    private JTextPane textPane2;
    private JTextPane textPane3;
    private JTextPane textPane4;
    private JTextPane textPane5;
    private JEditorPane editorPane1;

    public JPanel getPanelMain() { return panelMain; }

    public App() {
        try(FileReader reader = new FileReader("text.txt")) {
            StringBuilder text = new StringBuilder();
            int ch;
            while ((ch = reader.read()) != -1) {
                text.append((char)ch);
            }
            textPane1.setText(text.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        encryptButton.addActionListener(e -> encryptText());
    }

    private void encryptText() {
        textPane2.setText("");
        textPane4.setText("");
        byte[] bytes = new byte[0];
        try {
            bytes = textPane3.getText().getBytes("Cp866");
        } catch (UnsupportedEncodingException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        String secret = "";
        for (byte el : bytes) {
            secret = secret.concat(Integer.toBinaryString(el));
        }
        textPane4.setText(secret);
        String sourceText = textPane1.getText();
        try {
            for (int i = 0; i < secret.length(); i++) {
                if (secret.charAt(i) == '1') {
                    appendToPane(textPane2, String.valueOf(sourceText.charAt(i)), new Color(1, 0, 0));
                } else {
                    appendToPane(textPane2, String.valueOf(sourceText.charAt(i)), Color.black);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet asset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.black);
        textPane2.setCharacterAttributes(asset, false);
        textPane2.replaceSelection(sourceText.substring(secret.length()));
    }

    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet asset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        tp.setCharacterAttributes(asset, false);
        tp.replaceSelection(msg);
    }
}

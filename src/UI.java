import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by calin on 1/4/16.
 */
public class UI extends JFrame{
    private JButton btnSimulate;
    private JButton btnStep;
    private JButton btnCompile;
    private JButton btnExport;
    private JEditorPane editor;
    private JLabel lblStatus;
    private JLabel lblA;
    private JTable tblMemory;
    private JTable tblStack;
    private JLabel lblB;
    private JLabel lblMbr;
    private JLabel lblPc;
    private JPanel mainPanel;

    private Document doc;

    public UI() {
        super("8-bit Assembler");

        setContentPane(mainPanel);

        init();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();


        editor.addKeyListener(new KeyAdapter() {
            int position = 0;
            @Override
            public void keyReleased(KeyEvent e) {

                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN
                        || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT){
                    position = editor.getCaretPosition();
                    return;
                }

                position = editor.getCaretPosition();


                org.jsoup.nodes.Document doc = Jsoup.parse(editor.getText());
                Elements elem = doc.select("p");

                StringBuilder highlightedText = new StringBuilder((new SyntaxHighlighter()).returnHtml(elem.html()));

                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    highlightedText.append("&nbsp");
                    elem.html(highlightedText.toString());
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    highlightedText.append("<br>");
                    elem.html(highlightedText.toString());
                }

               // System.out.println(doc.html().toString());
                System.out.println(editor.getCaretPosition());

                editor.setText(doc.html().toString());

//                if(position != 1)
//                    editor.setCaretPosition(position++);
//                position++;
            }
        });
        editor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(editor.getCaretPosition());
            }
        });
    }

    private void init() {
        initTables();
    }

    private void initTables() {

    }


}


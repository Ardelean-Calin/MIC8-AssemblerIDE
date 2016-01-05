import javafx.stage.FileChooser;
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
import java.awt.event.*;
import java.io.*;


public class UI extends JFrame{
    private JButton btnSimulate;
    private JButton btnStep;
    private JButton btnCompile;
    private JButton btnExport;
    private JEditorPane editor;
    private JLabel lblStatus;
    private JLabel lblA;
    private JTable tblStack;
    private JLabel lblB;
    private JLabel lblMbr;
    private JLabel lblPc;
    private JPanel mainPanel;
    private JTable tblMemory;
    private JTextArea areaMemory;
    private JFileChooser fc;
    private JMenuBar myMenu;

    private Document doc;

    private int[] programMemory;

    public UI() {
        super("8-bit Assembler");

        setContentPane(mainPanel);

        fc = new JFileChooser();
        myMenu = new JMenuBar();

        init();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();


        JMenu fileMenu = new JMenu("File");
        myMenu.add(fileMenu);

        JMenuItem openOption = new JMenuItem("Open");
        fileMenu.add(openOption);
        openOption.setVisible(true);
        openOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File file_to_open = null;
                int rVal = fc.showOpenDialog(mainPanel);
                if(rVal == JFileChooser.APPROVE_OPTION){
                    file_to_open = fc.getSelectedFile();
                }

                try{
                    FileReader fstream = new FileReader(file_to_open);
                    BufferedReader in = new BufferedReader(fstream);

                    String line = null;
                    StringBuilder s = new StringBuilder();

                    while((line = in.readLine()) != null ){
                        s.append(line);
                    }

                    editor.setText(s.toString());

                    in.close();
                    fstream.close();
                }catch (IOException er){
                    er.printStackTrace();
                }
            }
        });


        JMenuItem saveOption = new JMenuItem("Save");
        fileMenu.add(saveOption);
        saveOption.setVisible(true);
        saveOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file_to_save = null;

                int rVal = fc.showSaveDialog(mainPanel);
                if(rVal == JFileChooser.APPROVE_OPTION){
                    file_to_save = fc.getSelectedFile();
                }

                try {
                    FileWriter fstream = new FileWriter(file_to_save);
                    BufferedWriter out = new BufferedWriter(fstream);

                    out.write(editor.getText());

                    out.close();
                    fstream.close();
                } catch (IOException er) {
                    er.printStackTrace();
                }

                lblStatus.setText("Status: Saved file successfully.");
            }
        });

        this.setJMenuBar(myMenu);


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
                //System.out.println(editor.getCaretPosition());

                editor.setText(doc.html().toString());
            }
        });

        editor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(editor.getCaretPosition());
            }
        });

        btnCompile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                org.jsoup.nodes.Document doc = Jsoup.parse(editor.getText());
                Elements elem = doc.select("p");
                String myCode = elem.text();
                //System.out.println(elem.toString());
                // returns the compiled memory
                programMemory = (new Compiler()).compile(myCode);
                Memory mem = new Memory();
                mem.writeMemory(programMemory);
            }
        });

        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File myFile = null;

                btnCompile.doClick();
                lblStatus.setText("Status: Compiled successfully. Select folder. ");

                int rVal = fc.showSaveDialog(mainPanel);
                if(rVal == JFileChooser.APPROVE_OPTION){
                    myFile = fc.getSelectedFile();
                }

                (new Compiler()).toFile(myFile, programMemory);
                lblStatus.setText("Status: Exported successfully.");
            }
        });
    }

    private void init() {
        initTables();
    }

    private void initTables() {
//        String columnNames[] = { "", "00", "01", "02", "03", "04", "05", "06", "07", "08",
//            "09", "0a", "0b", "0c", "0d", "0e", "0f"};
//        String data[][] = new String[16][];
//        for(int i = 0; i<16; i++){
//            data[i] = new String[16];
//            for(int j=0; i<16; j++){
//                data[i][j] = new String("00");
//            }
//        }
    }


}


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class LearnChineseGUI implements ActionListener{
    JFrame frame;
    JMenuBar menuBar;
    JMenu fileM;
    JMenuItem openMI;
    JComboBox numWordsCB;
    JComboBox langCB;
    JButton goB;
    JButton showB;
    
    Color panelColor = new Color(194,214,235); //Metallic blue
    Color bgColor = new Color(255,250,240); //Light beige

    boolean fileLoaded = false;
    String fileName;

    @SuppressWarnings("unchecked")
    public void createGUI(){
        //Set up frame
        frame = new JFrame("Learn Chinese!  No file loaded");
        frame.setBounds(200, 200, 700, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up menu bar
        menuBar = new JMenuBar();
        fileM = new JMenu("File");
        openMI = new JMenuItem("Open");
        openMI.addActionListener(this);
        fileM.add(openMI);
        menuBar.add(fileM);
        frame.setJMenuBar(menuBar);
        
        //Main JPanel
        JPanel mainP = new JPanel(); //Use default border layout
        mainP.setBackground(panelColor);
        
        //Set up top panel with combo boxes and Go button
        JPanel topP = new JPanel();
        topP.setLayout(new GridLayout(1, 3, 10, 0)); //1x3 with 10px hgap
        
        JPanel langP = new JPanel();
        langP.setBorder(new TitledBorder("Select language"));
        langCB = new JComboBox();
        langCB.insertItemAt("Show English", 0);
        langCB.insertItemAt("Show Chinese", 1);
        langCB.setEnabled(false); //Don't enable until file chosen
        langCB.addActionListener(this);
        langP.add(langCB);
        
        JPanel numWordsP = new JPanel();
        numWordsP.setBorder(new TitledBorder("Number of words to display"));
        numWordsCB = new JComboBox();
        for(int i = 1; i <= 10; ++i){
            numWordsCB.insertItemAt(""+i, i-1);
        }
        numWordsCB.setEnabled(false);
        numWordsCB.addActionListener(this);
        numWordsP.add(numWordsCB);
        
        JPanel goP = new JPanel();
        goP.setBorder(new EmptyBorder(5, 5, 5, 5));
        goB = new JButton("Please choose a file first");
        goB.setEnabled(false);
        goB.addActionListener(this);
        goP.add(goB);
        
        topP.add(langP);
        topP.add(numWordsP);
        topP.add(goP);
        
        mainP.add(topP, BorderLayout.PAGE_START);
        
        frame.add(mainP);
        frame.setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent e){
    }
}
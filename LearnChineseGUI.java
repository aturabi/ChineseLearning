import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class LearnChineseGUI implements ActionListener{
    //GUI components
    JFrame frame;
    JMenuBar menuBar;
    JMenu fileM;
    JMenuItem openMI;
    JComboBox numWordsCB;
    JComboBox langCB;
    JButton goB;
    JButton showB;
    JPanel buttonP;
    JPanel textP;

    Color panelColor = new Color(194,214,235); //Metallic blue
    Color bgColor = new Color(255,250,240); //Light beige

    String fileName;

    Translations translations;

    //Create the GUI for our program
    @SuppressWarnings("unchecked")
    public void createGUI(){
        //Set up frame
        frame = new JFrame("Learn Chinese!  No file loaded");
        frame.setBounds(150, 150, 800, 800);
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
        topP.setPreferredSize(new Dimension(750, 75));
        topP.setLayout(new GridLayout(1, 3, 10, 0)); //1x3 with 10px hgap

        JPanel langP = new JPanel();
        langP.setBorder(new TitledBorder("Select language"));
        langCB = new JComboBox();
        langCB.insertItemAt("Show English", 0);
        langCB.insertItemAt("Show Chinese", 1);
        langCB.setEnabled(false); //Don't enable until file chosen
        //langCB.addActionListener(this);
        langP.add(langCB);

        JPanel numWordsP = new JPanel();
        numWordsP.setBorder(new TitledBorder("Number of words to display"));
        numWordsCB = new JComboBox();
        numWordsCB.setEnabled(false);
        //numWordsCB.addActionListener(this);
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

        //Set up structure of center panel for text and buttons
        JPanel centerP = new JPanel();
        centerP.setPreferredSize(new Dimension(750, 600));
        centerP.setLayout(new GridLayout(1, 2, 10, 0)); //1x2 with 10px hgap
        centerP.setBackground(panelColor);
        buttonP = new JPanel();
        buttonP.setLayout(new BoxLayout(buttonP, BoxLayout.PAGE_AXIS));
        textP = new JPanel();
        textP.setLayout(new BoxLayout(textP, BoxLayout.PAGE_AXIS));
        centerP.add(textP);
        centerP.add(buttonP);

        mainP.add(topP, BorderLayout.PAGE_START);
        mainP.add(centerP, BorderLayout.CENTER);

        frame.add(mainP);
        frame.setVisible(true);
    }

    //Dynamically add buttons onto the GUI
    private void addButtons(int num){
        buttonP.removeAll();
        for(int i = 0; i < num; i++){
            JButton j = new JButton("addButtons");
            j.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        j.setVisible(false);
                    }
                });
            buttonP.add(j);
        }
    }

    private void addText(int num, String[] words, int lang){
        textP.removeAll();
        for(int i = 0; i < num; i++){
            //Take advantage of english = 0, chinese = 1 for lang
            //This just avoids a few if-statements
            JLabel wordL = new JLabel(words[2*i + lang]);
            JLabel meaningL = new JLabel(words[2*i + (lang+1)%2]);
            meaningL.setVisible(false);
            /*
            JButton j = new JButton("addText");
            j.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            j.setVisible(false);
            meaningL.setVisible(true);
            }	
            });
            textP.add(j);
             */
            textP.add(wordL);
            buttonP.add(meaningL);
        }
    }

    @SuppressWarnings("unchecked") //Risky business
    private void enableInput(){
        langCB.setEnabled(true);
        langCB.setSelectedIndex(0);
        numWordsCB.setEnabled(true);
        //Add options for num words, 10 max
        for(int i = 1; i <= 10 && i <= translations.getNumTranslations(); ++i){
            numWordsCB.insertItemAt(i, i-1);
        }
        numWordsCB.setSelectedIndex(0);
        goB.setEnabled(true);
        goB.setText("Go!");
        frame.setTitle("Learn Chinese! Reading from " + fileName);
    }

    private void disableInput(){
        langCB.setEnabled(false);
        numWordsCB.setEnabled(false);
        goB.setEnabled(false);
        goB.setText("Please choose a file first");
        frame.setTitle("Learn Chinese!  No file loaded");
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == openMI){
            translations = new Translations();
            JFileChooser fc = new JFileChooser();
            int approve = fc.showOpenDialog(null);
            if(approve == JFileChooser.APPROVE_OPTION){
                fileName = fc.getSelectedFile().getName();
                try{
                    translations.readFile(fileName);
                    enableInput();
                } catch(Exception ioex){
                    translations = null;
                    disableInput();
                    JOptionPane.showMessageDialog(null,"Error reading file","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if(e.getSource() == goB){
            int numWords = numWordsCB.getSelectedIndex() + 1;
            int lang = langCB.getSelectedIndex();
            String[] words = translations.getRandomWords(numWords);
            addText(numWords, words, lang);
            addButtons(numWords);

            //Force components to update
            textP.validate();
            buttonP.validate();
        }
    }
}

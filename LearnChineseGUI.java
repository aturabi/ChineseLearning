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
    JPanel topP;
    JPanel centerP;
    JPanel wordP;
    JPanel buttonP;
    JPanel meaningP;

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
        topP = new JPanel();
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
        centerP = new JPanel();
        centerP.setPreferredSize(new Dimension(750, 600));
        centerP.setLayout(new GridLayout(1, 3, 10, 0)); //1x2 with 10px hgap
        centerP.setBackground(panelColor);

        wordP = new JPanel();
        wordP.setLayout(new GridLayout(0, 1, 0, 10)); //nx1 with 10px vgap
        buttonP = new JPanel();
        buttonP.setLayout(new GridLayout(0, 1, 0, 10));
        meaningP = new JPanel();
        meaningP.setLayout(new GridLayout(0, 1, 0, 10));

        centerP.add(wordP);
        centerP.add(buttonP);
        centerP.add(meaningP);

        mainP.add(topP, BorderLayout.PAGE_START);
        mainP.add(centerP, BorderLayout.CENTER);

        frame.add(mainP);
        frame.setVisible(true);
    }

    //Dynamically add a button onto the GUI
    //On click, reveal associated label
    private void addButton(JLabel label){
        JButton j = new JButton("Click to view translation");
        j.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    j.setVisible(false);
                    label.setVisible(true);
                }
            });
        buttonP.add(j);
    }

    private void addText(int num, String[] words, int lang){
        for(int i = 0; i < num; i++){
            //Take advantage of english = 0, chinese = 1 for lang
            //This just avoids a few if-statements
            JLabel wordL = new JLabel(words[2*i + lang]);
            JLabel meaningL = new JLabel(words[2*i + (lang+1)%2]);
            meaningL.setVisible(false);

            addButton(meaningL);
            wordP.add(wordL);
            meaningP.add(meaningL);
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
                    JOptionPane.showMessageDialog(null,"Error reading file\n" + ioex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                } finally {
                    topP.revalidate();
                    topP.repaint();
                }
            }
        } else if(e.getSource() == goB){
            int numWords = numWordsCB.getSelectedIndex() + 1;
            int lang = langCB.getSelectedIndex();
            String[] words = translations.getRandomWords(numWords);

            //Remove old components
            wordP.removeAll();
            buttonP.removeAll();
            meaningP.removeAll();

            addText(numWords, words, lang);

            //Force components to repaint
            centerP.revalidate();
            centerP.repaint();
        }
    }
}

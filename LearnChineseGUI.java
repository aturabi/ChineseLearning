import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class LearnChineseGUI implements ActionListener{

    int WINDOW_WIDTH = 1000;
    int WINDOW_HEIGHT = 1000;
    int TOP_WIDTH = 900;
    int TOP_HEIGHT = 75;
    int CENTER_WIDTH = 900;
    int CENTER_HEIGHT = 825;
    int FONT_SIZE = 24;
    private int FONT_SIZE2 = 25;
    private int FONT_SIZE3 = 25;   
    private int FONT_SIZE4 = 25;

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

    JFileChooser fc = new JFileChooser();
    Color panelColor = new Color(194,214,235); //Metallic blue
    Color bgColor = new Color(255,250,240); //Light beige
    Font font = new Font(Font.DIALOG, Font.PLAIN, FONT_SIZE);

    String fileName;

    Translations translations;

    //Create the GUI for our program
    @SuppressWarnings("unchecked")
    public void createGUI(){
        //Set up dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WINDOW_WIDTH = screenSize.width * 2 / 3;
        WINDOW_HEIGHT = screenSize.height;
        TOP_WIDTH = WINDOW_WIDTH - 100;
        TOP_HEIGHT = WINDOW_HEIGHT * 1 / 10;
        CENTER_WIDTH = TOP_WIDTH;
        CENTER_HEIGHT = WINDOW_HEIGHT * 7 / 10;
        FONT_SIZE = WINDOW_WIDTH / 36;
        FONT_SIZE2 = WINDOW_WIDTH/40;
        FONT_SIZE3 = WINDOW_WIDTH/50;
        FONT_SIZE4 = WINDOW_WIDTH/55;

        Font topFont = new Font(Font.DIALOG, Font.PLAIN, FONT_SIZE3);
        Font topFontSml = new Font(Font.DIALOG, Font.PLAIN, FONT_SIZE4);

        //Set up frame
        frame = new JFrame("Learn Chinese!  No file loaded");
        frame.setBounds(25, 25, WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up menu bar
        Font f = new Font("sans-serif", Font.PLAIN, FONT_SIZE2);
        UIManager.put("Menu.font", topFont);
        UIManager.put("MenuItem.font", topFont);
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
        topP.setPreferredSize(new Dimension(TOP_WIDTH, TOP_HEIGHT));
        topP.setLayout(new GridLayout(1, 3, 10, 0)); //1x3 with 10px hgap

        JPanel langP = new JPanel();
        langP.setBorder(new TitledBorder("Select language"));
        ((javax.swing.border.TitledBorder)langP.getBorder()).setTitleFont(topFontSml);
        langCB = new JComboBox();
        langCB.setFont(topFont);
        langCB.insertItemAt("Show English", 0);
        langCB.insertItemAt("Show Chinese", 1);
        langCB.setEnabled(false); //Don't enable until file chosen
        //langCB.addActionListener(this);
        langP.add(langCB);

        JPanel numWordsP = new JPanel();
        numWordsP.setBorder(new TitledBorder("Number of words to display"));
        ((javax.swing.border.TitledBorder)numWordsP.getBorder()).setTitleFont(topFontSml);
        numWordsCB = new JComboBox();
        numWordsCB.setFont(topFont);
        numWordsCB.setEnabled(false);
        //numWordsCB.addActionListener(this);
        numWordsP.add(numWordsCB);

        JPanel goP = new JPanel();
        goP.setLayout(new BorderLayout());
        goP.setBorder(new EmptyBorder(10, 10, 10, 10));
        goB = new JButton("Please choose a file first");
        goB.setFont(topFont);
        goB.setEnabled(false);
        goB.addActionListener(this);
        goP.add(goB);

        topP.add(langP);
        topP.add(numWordsP);
        topP.add(goP);

        //Set up structure of center panel for text and buttons
        centerP = new JPanel();
        centerP.setPreferredSize(new Dimension(CENTER_WIDTH, CENTER_HEIGHT));
        centerP.setLayout(new GridLayout(1, 3, 10, 0)); //1x2 with 10px hgap
        centerP.setBackground(panelColor);

        wordP = new JPanel();
        wordP.setLayout(new GridLayout(0, 1, 0, 10)); //nx1 with 10px vgap
        buttonP = new JPanel();
        buttonP.setLayout(new GridLayout(0, 1, 0, 10));
        meaningP = new JPanel();
        meaningP.setLayout(new GridLayout(0, 1, 0, 10));

        JScrollPane scroll1 = new JScrollPane(wordP);
        JScrollPane scroll2 = new JScrollPane(buttonP);
        JScrollPane scroll3 = new JScrollPane(meaningP);

        centerP.add(scroll1);
        centerP.add(scroll2);
        centerP.add(scroll3);

        mainP.add(topP, BorderLayout.PAGE_START);
        mainP.add(centerP, BorderLayout.CENTER);

        frame.add(mainP);
        frame.setVisible(true);
    }

    //Dynamically add a button onto the GUI
    //On click, reveal associated label
    private void addButton(JLabel label){
        JButton j = new JButton("View translation");
        j.setFont(font);
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
            wordL.setFont(font);
            JLabel meaningL = new JLabel(words[2*i + (lang+1)%2]);
            meaningL.setFont(font);
            wordL.setHorizontalAlignment(JLabel.CENTER);
            meaningL.setHorizontalAlignment(JLabel.CENTER);
            meaningL.setVisible(false);

            addButton(meaningL);
            wordP.add(wordL);
            meaningP.add(meaningL);
        }
    }

    @SuppressWarnings("unchecked")
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

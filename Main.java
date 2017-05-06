import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.util.Random;
import java.io.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import javax.swing.OverlayLayout;
import javax.swing.JLayeredPane;

public class Main extends JPanel {

   static public int max;
   static public int numToPrint;
   static public int lines[];
   static public JPanel panel;
   static public JPanel buttonPanel;
   static public FileInputStream fs;



   /* main
   */
   public static void main(String[] args) {
   	  if(args.length != 2){
      	System.out.println("Please make sure you are using the correct format");
      }
      // take care of comm line arguments
   	  max = Integer.parseInt(args[0]);
      numToPrint = Integer.parseInt(args[1]);

      // create main frame
      JFrame.setDefaultLookAndFeelDecorated(true);
      JFrame mainFrame = new JFrame("Learn Chinese");
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainFrame.setSize(800, 500);

      // create panels for text and buttons
      panel = new JPanel(new GridLayout(0, 2));
      buttonPanel = new JPanel(new GridLayout(0, 2));

      //mainFrame.add(panel);
      JLabel jlabel = new JLabel("Learn Chinese");
      jlabel.setFont(new Font("Courier New", Font.PLAIN, 50));
      JLabel jlabel2 = new JLabel("");
   	  panel.add(jlabel);
   	  panel.add(jlabel2);
   	  addText();
      addButtons();


      	JPanel overlay = new JPanel();
		overlay.setLayout( new OverlayLayout(overlay) );
		//overlay.add(buttonPanel, BorderLayout.CENTER); // add transparent panel first
		overlay.add(panel, BorderLayout.CENTER);


		mainFrame.setLayout(new BorderLayout()); 
		mainFrame.add(overlay);

      mainFrame.setVisible(true);


   }



   public static void addButtons(){
   	JLabel blank = new JLabel("hi");
   	//blank.setVisible(false);
   	buttonPanel.add(blank);
   	buttonPanel.add(blank);
   	buttonPanel.add(blank);
      for(int i=0; i < numToPrint; i++){
      	JButton j = new JButton("Click here to see the meaning of this word");
      	j.addActionListener(new ActionListener(){
      		public void actionPerformed(ActionEvent e){
    			j.setVisible(false);
  			}
      	});
      	buttonPanel.add(j);
      	buttonPanel.add(blank);
      }

   }


   public static void addText(){

      lines = new int[max];
      for(int k = 0; k < max; k++){
      	lines[k] = 0;
      }
      Random rand = new Random();
      int min = 0;
      int randomNum = 0;
      try{
      	for(int i = 0; i < numToPrint;){
      		randomNum = rand.nextInt((max - 1 - min) + 1) + min;
      		 System.out.println(randomNum);
      		if(lines[randomNum] == 0){
      			lines[randomNum] = 1;
      			fs= new FileInputStream("chinese.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int j = 0; j < randomNum; ++j)
  					br.readLine();
				String lineIWant = br.readLine();
				StringTokenizer st = new StringTokenizer(lineIWant, ",");
     			String word = st.nextToken();
     			String meaning = st.nextToken();
				JLabel jlabel1 = new JLabel(word);
				JLabel jlabel2 = new JLabel(meaning);
				jlabel1.setHorizontalAlignment(JLabel.CENTER);
				jlabel2.setHorizontalAlignment(JLabel.CENTER);
				jlabel1.setFont(new Font("Courier New", Font.PLAIN, 30));
				jlabel2.setFont(new Font("Courier New", Font.PLAIN, 30));
				panel.add(jlabel1);
				panel.add(jlabel2);
				i++;
      		}
      	} 
  	  }
  	 catch(IOException s){
      	System.out.println("File does Not Exist Please Try Again: ");
      }
   }

}

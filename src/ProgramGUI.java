/* 
* synchro
* 5431038821  
* Ms.Vibhavee Trairattanapa 
*/
import java.awt.*;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ProgramGUI {
	
	public static ProgramGUI program;	
	private FactoryBelt factory;
	private LogisticBelt logistic1;
	private LogisticBelt logistic2;
	private JTextField textBelt01, textBelt02, textFactory, status;
	private JToggleButton btnBelt01, btnBelt02, btnFactory;
	
	public ProgramGUI(LogisticBelt logistic1,LogisticBelt logistic2,FactoryBelt factory) {
		
		//Constructors
		super();
		this.logistic1 = logistic1;
		this.logistic2 = logistic2;
		this.factory = factory;

		//Create Frame
		JFrame.setDefaultLookAndFeelDecorated(false);
		JFrame frame = new JFrame("Logistic Simulation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(480,300));
		
		//Create Container and set to Border Layout
		Container cp = frame.getContentPane();
		cp.setLayout(new FlowLayout());
		
		//Create all Text Fields
		textBelt01 = new JTextField("Belt1",20);
		textBelt02 = new JTextField("Belt2",20);
		textFactory = new JTextField("Factory",20);
		
		//Create all JToggleButtons
		btnBelt01 = new JToggleButton("Run Belt01");
		btnBelt02 = new JToggleButton("Run Belt02");
		btnFactory = new JToggleButton("Run Main");
		
		//Add ItemListener to buttons
		btnBelt01.addItemListener(new ButtonListener(btnBelt01,"Run Belt01","Stop Belt01",1));
		btnBelt02.addItemListener(new ButtonListener(btnBelt02,"Run Belt02","Stop Belt02",2));
		btnFactory.addItemListener(new ButtonListener(btnFactory,"Run Main","Stop Main",0));
		
		//Create Panels to put both texts and buttons in
		JPanel panelBelt = new JPanel(new GridLayout(2,4,20,10));
		JPanel panelFactory = new JPanel(new GridLayout(3,1,20,0));

		JLabel topSpace = new JLabel();
		topSpace.setPreferredSize(new Dimension(480,100));

		JLabel bottomSpace = new JLabel();
		bottomSpace.setPreferredSize(new Dimension(480,100));
		
		JLabel leftSpace = new JLabel();
		leftSpace.setPreferredSize(new Dimension(50,10));
		
		//Add Text and Button to each panel
		panelBelt.add(topSpace);
		panelBelt.add(leftSpace);
		panelBelt.add(btnBelt01);
		panelBelt.add(btnBelt02);
		panelBelt.add(new JLabel());
		panelBelt.add(new JLabel());
		panelBelt.add(textBelt01);
		panelBelt.add(textBelt02);
		panelFactory.add(btnFactory);
		panelFactory.add(textFactory);
		panelFactory.add(bottomSpace);
		
		//Add Panel to the Container
		cp.add(topSpace);
		cp.add(panelBelt);
		cp.add(panelFactory);

		//Ready to go
        frame.pack();
        frame.setVisible(true);
	}
	
	//Set text in the TextField
	public void setText(String text,int k){
		if(k==0)
			textFactory.setText(text);
		else if(k==1)
			textBelt01.setText(text);
		else if(k==2)
			textBelt02.setText(text);
		else if(k==3)
			status.setText(text);
	}
	
	class ButtonListener implements ItemListener {

		private JToggleButton btnName;
		private String onText,offText;
		private int num;
		
		//Constructor
		public ButtonListener(JToggleButton btnName, String onText, String offText, int num) {
			super();
			this.onText = onText;
			this.offText = offText;
			this.btnName = btnName;
			this.num = num;
		}
		
		//What happens when clicking the button
		public void itemStateChanged(ItemEvent e) {
	        if(e.getStateChange() == ItemEvent.SELECTED){
	        	btnName.setText(offText);
	    		if(num==0)
	    			factory.start();
	    		else if(num==1)
	    			logistic1.start();
	    		else if(num==2)
	    			logistic2.start();
	        }
	        else {
	        	btnName.setText(onText);
	    		if(num==0)
	    			factory.stop();
	    		else if(num==1)
	    			logistic1.stop();
	    		else if(num==2)
	    			logistic2.stop();
	        }
		}
	}
}

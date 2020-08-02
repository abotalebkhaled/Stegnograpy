import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frame;
	private JTextField pnum;
	private JTextField Iname;
	private JTextArea message;
	private JTextField bit;
	private JTextArea printm;
	public static String P;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 480, 308);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Encode", null, panel, null);
		
		pnum = new JTextField();
		pnum.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Bits :");
		
		JLabel lblNewLabel_1 = new JLabel("Message :");
		
		JLabel lblNewLabel_2 = new JLabel("Browsing Photo :");
		
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser x = new JFileChooser();
				x.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileNameExtensionFilter fil = new FileNameExtensionFilter("*.Images","jpg","gif","png");
				x.addChoosableFileFilter(fil);
				int result = x.showSaveDialog(null);
				if( result == JFileChooser.APPROVE_OPTION ) 
				{
					try 
					{			
						File selectedFile = x.getSelectedFile();
						Image.img = ImageIO.read(selectedFile);
						Image.readImage();
					}
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnNewButton_1 = new JButton("Encode");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				Image.getPixlesValues();
				Image.TransafrePixlesToBinary();
				Image.enCodeMessage(message.getText()+"\0", Integer.parseInt(pnum.getText()));
				try 
				{
					Image.saveEncodedImage(Iname.getText());
				}
				catch (IOException e1) 
				{

					e1.printStackTrace();
				}
				Image.img = null;
				Image.pixles.clear();
				Image.availableHeightPixles = Image.availableWidthPixles =  Image.width = Image.height = 0;
			}
		});
		
		Iname = new JTextField();
		Iname.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addGap(8))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(pnum, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addGap(37)
							.addComponent(lblNewLabel_2)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE))
					.addGap(6))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(135)
					.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
					.addGap(119))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(155)
					.addComponent(Iname, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
					.addGap(139))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(lblNewLabel_2)
						.addComponent(pnum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(39)
							.addComponent(lblNewLabel_1))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(18)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(Iname, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(23, Short.MAX_VALUE))
		);
		
		message = new JTextArea();
		scrollPane.setViewportView(message);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Decode", null, panel_1, null);
		
		JButton btnDecode = new JButton("Decode");
		btnDecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				Image.getPixlesValues();
				Image.TransafrePixlesToBinary();
				Image.deCodeMessage(Integer.parseInt(bit.getText()));
				
				printm.append(P);
				
				Image.img = null;
				Image.pixles.clear();
				Image.availableHeightPixles = Image.availableWidthPixles =  Image.width = Image.height = 0;		
			}
		});
		
		JLabel label = new JLabel("Browsing Photo :");
		
		JButton brow = new JButton("Browse");
		brow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser x = new JFileChooser();
				x.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileNameExtensionFilter fil = new FileNameExtensionFilter("*.Images","jpg","gif","png");
				x.addChoosableFileFilter(fil);
				int result = x.showSaveDialog(null);
				if( result == JFileChooser.APPROVE_OPTION ) 
				{
					try 
					{			
						File selectedFile = x.getSelectedFile();
						Image.img = ImageIO.read(selectedFile);
						Image.readImage();
					}
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		});
		
		JLabel label_1 = new JLabel("Message :");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JLabel lblBits = new JLabel("Bits :");
		
		bit = new JTextField();
		bit.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
							.addGap(63)
							.addComponent(btnDecode, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
							.addGap(118))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(8)
									.addComponent(lblBits, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(bit, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
									.addGap(41)
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(brow, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(31)
									.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)))
							.addGap(56))))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(bit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label)
						.addComponent(brow)
						.addComponent(lblBits))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(18)
							.addComponent(btnDecode, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(51)
							.addComponent(label_1)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(41, Short.MAX_VALUE))
		);
		
		printm = new JTextArea();
		scrollPane_1.setViewportView(printm);
		panel_1.setLayout(gl_panel_1);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
					.addGap(18))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane)
					.addGap(17))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}

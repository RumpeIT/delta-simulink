package installer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollBar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JRadioButton;

//import installer.InstallerWelcome;
//import installer.InstallerPath;

public class InstallerLicense extends JFrame {

	private JPanel contentPane;
	
	final int width = 576;
	final int height = 430;
	final int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
	final int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;

	/**
	 * Launch the application.
	 */
	public static void callFrmLicense() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstallerLicense frame = new InstallerLicense();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InstallerLicense() {
		setTitle("DeltaSimulink Setup");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(w, h, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		InstallerLicense.this.setResizable(false);
		
		InstallerLicense.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		InstallerLicense.this.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent e) {
				   int ans = JOptionPane.showConfirmDialog(null, "Setup is not complete. If you exit now, the program will not be installed.\n"
							+ "You may run Setup again at another time to complete the installation.\n"
							+ "Exit Setup?","Exit Setup",JOptionPane.OK_CANCEL_OPTION);
		        	if (ans == 0)
		        		System.exit(0);
			   }
		});
		
		JLabel lblNewLabel = new JLabel("Licence Agreement");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(30, 11, 178, 19);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Please review the licence terms before installing DeltaSimulink.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(40, 33, 340, 14);
		contentPane.add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 58, 570, 2);
		contentPane.add(separator);
		
		JLabel lblPressDownTo = new JLabel("Press Page Down to see the rest of the Agreement.");
		lblPressDownTo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPressDownTo.setBounds(30, 67, 302, 19);
		contentPane.add(lblPressDownTo);
		
		JLabel lblIfYouAccept = new JLabel("If you accept the terms of the agreement, select the first option below. You must accept the agreement");
		lblIfYouAccept.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIfYouAccept.setBounds(30, 233, 520, 19);
		contentPane.add(lblIfYouAccept);
		
		JLabel lblThe = new JLabel("to install DeltaSimulink. Click Next to continue.");
		lblThe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblThe.setBounds(30, 263, 283, 14);
		contentPane.add(lblThe);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 341, 570, 2);
		contentPane.add(separator_1);
		
		JButton btnBack = new JButton("<Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstallerLicense.this.dispose();
				InstallerWelcome.main(null);
			}
		});
		btnBack.setBounds(225, 354, 89, 27);
		contentPane.add(btnBack);
		
		final JButton btnNext = new JButton("Next>");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstallerLicense.this.dispose();
				
				InstallerPath frmPath = new InstallerPath();
				frmPath.setVisible(true);
								
				//frmPath.setLocation(w, h);

				//frmPath.setSize(width,height);
			}
		});
		btnNext.setEnabled(false);
		btnNext.setBounds(325, 354, 89, 27);
		contentPane.add(btnNext);
		
		final JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ans = JOptionPane.showConfirmDialog(null, "Setup is not complete. If you exit now, the program will not be installed.\n"
						+ "You may run Setup again at another time to complete the installation.\n"
						+ "Exit Setup?","Exit Setup",JOptionPane.OK_CANCEL_OPTION);
	        	if (ans == 0)
	        		System.exit(0);
			}
		});
		btnCancel.setBounds(471, 354, 89, 27);
		contentPane.add(btnCancel);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(30, 90, 501, 132);
		contentPane.add(textArea);
		
		JRadioButton rdbtnYes = new JRadioButton("I agree the terms in the License Agreement");
		rdbtnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNext.setEnabled(true);
			}
		});
		rdbtnYes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnYes.setBounds(30, 292, 283, 23);
		contentPane.add(rdbtnYes);			
		
		JRadioButton rdbtnNo = new JRadioButton("I do not accept the terms in the License Agreement");
		rdbtnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNext.setEnabled(false);
			}
		});
		rdbtnNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnNo.setBounds(30, 315, 283, 23);
		contentPane.add(rdbtnNo);
		rdbtnNo.setSelected(true); // selected by default
		
		//
		ButtonGroup rdbGroup=new ButtonGroup();
		rdbGroup.add(rdbtnYes);
		rdbGroup.add(rdbtnNo);
		//
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(461, 0, 89, 60);
		contentPane.add(lblLogo);
		
		// add a Logo on right-up corner
		BufferedImage img=null;
		try {
			img = ImageIO.read(InstallerWelcome.class.getClassLoader().getResource("SmallLogo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image resizedImg = img.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon deltaIcon = new ImageIcon(resizedImg);
		lblLogo.setIcon(deltaIcon);
		
	}
}

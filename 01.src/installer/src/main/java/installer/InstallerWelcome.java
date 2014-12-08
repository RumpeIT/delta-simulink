package installer;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;




//import installer.InstallerLicense;

import javax.swing.JSeparator;

public class InstallerWelcome {

	private JFrame frmDeltaSimulinkSetup;
	
	final int width = 576;
	final int height = 430;
	final int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
	final int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;

	/**
	 * Launch the application.
	 */
	public static void callFrmWelcome() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstallerWelcome window = new InstallerWelcome();
					window.frmDeltaSimulinkSetup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InstallerWelcome() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDeltaSimulinkSetup = new JFrame();
		frmDeltaSimulinkSetup.setTitle("DeltaSimulink Setup");
		frmDeltaSimulinkSetup.getContentPane().setLayout(null);
			
		frmDeltaSimulinkSetup.setLocation(w, h);
		frmDeltaSimulinkSetup.setPreferredSize(new Dimension(width, height));
		frmDeltaSimulinkSetup.pack();
		frmDeltaSimulinkSetup.setResizable(false);
		
		frmDeltaSimulinkSetup.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmDeltaSimulinkSetup.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent e) {
				   int ans = JOptionPane.showConfirmDialog(null, "Setup is not complete. If you exit now, the program will not be installed.\n"
							+ "You may run Setup again at another time to complete the installation.\n"
							+ "Exit Setup?","Exit Setup",JOptionPane.OK_CANCEL_OPTION);
		        	if (ans == 0)
		        		System.exit(0);
			   }
		});
		
		JButton btnNext = new JButton("Next>");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frmDeltaSimulinkSetup.dispose();
				
				//InstallerLicense frmNext = new InstallerLicense();
				//frmNext.setVisible(true);
				
				InstallerPath frmPath = new InstallerPath();
				frmPath.setVisible(true);						
			}
		});
		btnNext.setBounds(325, 354, 89, 27);
		frmDeltaSimulinkSetup.getContentPane().add(btnNext);
		
		JButton btnBack = new JButton("<Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnBack.setBounds(225, 354, 89, 27);
		frmDeltaSimulinkSetup.getContentPane().add(btnBack);
		btnBack.setEnabled(false);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				int ans = JOptionPane.showConfirmDialog(null, "Setup is not complete. If you exit now, the program will not be installed.\n"
						+ "You may run Setup again at another time to complete the installation.\n"
						+ "Exit Setup?","Exit Setup",JOptionPane.OK_CANCEL_OPTION);
	        	if (ans == 0)
	        		System.exit(0);
			}
		});
		btnCancel.setBounds(471, 354, 89, 27);
		frmDeltaSimulinkSetup.getContentPane().add(btnCancel);
		
		JLabel lblDeltaIcon = new JLabel("");
		lblDeltaIcon.setBounds(0, 0, 216, 341);
		frmDeltaSimulinkSetup.getContentPane().add(lblDeltaIcon);
		
		JLabel lblWelcome = new JLabel("Welcome to the DeltaSimulink");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWelcome.setBounds(247, 38, 294, 40);
		frmDeltaSimulinkSetup.getContentPane().add(lblWelcome);
		
		//
		
		BufferedImage img=null;
		try {
			img = ImageIO.read(InstallerWelcome.class.getClassLoader().getResource("Delta-Simulink-Logo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image resizedImg = img.getScaledInstance(lblDeltaIcon.getWidth(), lblDeltaIcon.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon deltaIcon = new ImageIcon(resizedImg);
		lblDeltaIcon.setIcon(deltaIcon);
		
		JLabel lblNewLabel = new JLabel("Setup Wizard");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(257, 75, 239, 40);
		frmDeltaSimulinkSetup.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("The wizard will install DeltaSimulink on your computer. ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(247, 169, 273, 27);
		frmDeltaSimulinkSetup.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Click\"Next\" to continue or \"Cancel\" to exit the Setup Wizard. ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(247, 197, 308, 27);
		frmDeltaSimulinkSetup.getContentPane().add(lblNewLabel_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 341, 570, 2);
		frmDeltaSimulinkSetup.getContentPane().add(separator);
		
	}
}

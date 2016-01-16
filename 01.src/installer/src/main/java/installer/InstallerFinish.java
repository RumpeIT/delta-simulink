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

public class InstallerFinish {

	private JFrame frmSetupFinish;
	
	final int width = 576;
	final int height = 430;
	final int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
	final int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;

	/**
	 * Launch the application.
	 */
	public static void callFrmFinish() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstallerFinish window = new InstallerFinish();
					window.frmSetupFinish.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InstallerFinish() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSetupFinish = new JFrame();
		frmSetupFinish.setTitle("DeltaSimulink Setup");
		frmSetupFinish.getContentPane().setLayout(null);
			
		frmSetupFinish.setLocation(w, h);
		frmSetupFinish.setPreferredSize(new Dimension(width, height));
		frmSetupFinish.pack();
		frmSetupFinish.setResizable(false);
		
//		frmSetupFinish.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmSetupFinish.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent e) {
		        		System.exit(0);
			   }
		});
		
		JButton btnNext = new JButton("Finish");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNext.setBounds(325, 354, 89, 27);
		frmSetupFinish.getContentPane().add(btnNext);
		
		JLabel lblDeltaIcon = new JLabel("");
		lblDeltaIcon.setBounds(0, 0, 216, 341);
		frmSetupFinish.getContentPane().add(lblDeltaIcon);		
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
		
		JLabel lblWelcome = new JLabel("Completing the DeltaSimulink");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWelcome.setBounds(247, 38, 294, 40);
		frmSetupFinish.getContentPane().add(lblWelcome);
		
		JLabel lblNewLabel = new JLabel("Setup Wizard");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(257, 75, 239, 40);
		frmSetupFinish.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Setup has finished installing Delta-Simulink on your computer. The");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(247, 155, 294, 27);
		frmSetupFinish.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("application may be launched by selecting the installed icons.");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(247, 178, 308, 27);
		frmSetupFinish.getContentPane().add(lblNewLabel_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 341, 570, 2);
		frmSetupFinish.getContentPane().add(separator);
		
		JLabel lblNewLabel_3 = new JLabel("Click Finish to exit Setup.");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(247, 242, 167, 14);
		frmSetupFinish.getContentPane().add(lblNewLabel_3);
		
	}
}

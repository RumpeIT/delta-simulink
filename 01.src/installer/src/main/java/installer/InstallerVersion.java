package installer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

//import installer.InstallerPath;




import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class InstallerVersion extends JFrame {

	private JPanel contentPane;
	
	public String DeltaSimulinkVersion;
	
	final int width = 576;
	final int height = 430;
	final int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
	final int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;

	/**
	 * Launch the application.
	 */
	public static void callFrmVersion() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstallerVersion frame = new InstallerVersion();
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
	public InstallerVersion() {
		setTitle("DeltaSimulink Setup");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(w, h, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		InstallerVersion.this.setResizable(false);
		
		InstallerVersion.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		InstallerVersion.this.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent e) {
				   int ans = JOptionPane.showConfirmDialog(null, "Setup is not complete. If you exit now, the program will not be installed.\n"
							+ "You may run Setup again at another time to complete the installation.\n"
							+ "Exit Setup?","Exit Setup",JOptionPane.OK_CANCEL_OPTION);
		        	if (ans == 0)
		        		System.exit(0);
			   }
		});
		
		
		JButton btnBack = new JButton("<Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstallerVersion.this.dispose();
				InstallerPath.callFrmPath();
			}
		});
		contentPane.setLayout(null);
		btnBack.setBounds(225, 354, 89, 27);
		contentPane.add(btnBack);
		
		final JButton btnNext = new JButton("Install");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ExtractAndStart.install();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
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
		
		JRadioButton rdbtnDS1 = new JRadioButton("Delta-Simulink 1.x");
		rdbtnDS1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNext.setEnabled(true);
				DeltaSimulinkVersion = "DeltaSimulinkVersion='1.0'";
				//System.out.println(DeltaSimulinkVersion);
			}
		});
		rdbtnDS1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnDS1.setBounds(112, 200, 140, 23);
		contentPane.add(rdbtnDS1);
		
		JRadioButton rdbtnDS2 = new JRadioButton("Delta-Simulink 2.x");
		rdbtnDS2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNext.setEnabled(true);
				DeltaSimulinkVersion = "DeltaSimulinkVersion='2.0'";
				//System.out.println(DeltaSimulinkVersion);
			}
		});
		rdbtnDS2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnDS2.setBounds(112, 251, 140, 23);
		contentPane.add(rdbtnDS2);
		
		//
		ButtonGroup rdbGroup=new ButtonGroup();
		rdbGroup.add(rdbtnDS1);
		rdbGroup.add(rdbtnDS2);
		//
		
		JLabel lblDelaSimulinkOffers = new JLabel("Dela simulink offers two backends: ");
		lblDelaSimulinkOffers.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDelaSimulinkOffers.setBounds(24, 71, 380, 27);
		contentPane.add(lblDelaSimulinkOffers);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 58, 570, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 341, 570, 2);
		contentPane.add(separator_1);
		
		JLabel lblNewLabel = new JLabel("Select Version");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(24, 11, 116, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Which version of DeltaSimulink should be installed?");
		lblNewLabel_1.setBounds(34, 33, 304, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u25CF The 1.x backend supports only Deltas for Subsystems and Model References.");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(41, 109, 460, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u25CF The 2.x backend supports arbitrary Deltas.");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(41, 146, 404, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("( Delta Simulink 2.x is still experimental, please use 1.x for now )");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(132, 282, 355, 14);
		contentPane.add(lblNewLabel_4);
		
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

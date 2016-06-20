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
import java.awt.Color;
import javax.swing.JProgressBar;

//import installer.InstallerWelcome;
//import installer.InstallerPath;

public class InstallerProgress extends JFrame {

	private JPanel contentPane;
	
	final int width = 576;
	final int height = 430;
	final int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
	final int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
	
	public static InstallerProgress frame = new InstallerProgress();

	/**
	 * Launch the application.
	 */
	public static void callFrmProgress() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//InstallerProgress frame = new InstallerProgress();
					//frame = new InstallerProgress();
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
	public InstallerProgress() {
		setTitle("DeltaSimulink Setup");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(w, h, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		InstallerProgress.this.setResizable(false);
		
		InstallerProgress.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		InstallerProgress.this.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent e) {
				   int ans = JOptionPane.showConfirmDialog(null, "Setup is not complete. If you exit now, the program will not be installed.\n"
							+ "You may run Setup again at another time to complete the installation.\n"
							+ "Exit Setup?","Exit Setup",JOptionPane.OK_CANCEL_OPTION);
		        	if (ans == 0)
		        		System.exit(0);
			   }
		});
		
		JLabel lblNewLabel = new JLabel("Installing");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(30, 11, 178, 19);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Please wait \twhile Setup installs DeltaSimulink on your computer.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(40, 33, 340, 14);
		contentPane.add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 58, 570, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 341, 570, 2);
		contentPane.add(separator_1);
		
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
		
		JLabel lblNewLabel_2 = new JLabel("Delta Simulink is Free Software.");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(189, 103, 202, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblPleaseVisti = new JLabel("Please visit");
		lblPleaseVisti.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPleaseVisti.setBounds(231, 128, 70, 14);
		contentPane.add(lblPleaseVisti);
		
		JLabel lblNewLabel_3 = new JLabel("http://www.se-rwth.de/");
		lblNewLabel_3.setForeground(new Color(0, 139, 139));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(189, 153, 244, 27);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("for free updates.");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(225, 191, 89, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblInstalling = new JLabel("Please wait a second, Setup is extracting files to your computer...");
		lblInstalling.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblInstalling.setBounds(105, 247, 445, 19);
		contentPane.add(lblInstalling);

		
	}
}

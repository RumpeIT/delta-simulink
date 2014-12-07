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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JLabel;

import installer.InstallerLicense;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.JTextField;

//import installer.InstallerVersion;

public class InstallerPath extends JFrame {

	private JPanel contentPane;
	
	final int width = 576;
	final int height = 430;
	final int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
	final int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
	
	protected JTextField txtPath;
	protected JTextField txtModel;
	
	static public String installingPath;
	static public String modelPath;

	/**
	 * Launch the application.
	 */
	public static void callFrmPath() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstallerPath frame = new InstallerPath();
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
	public InstallerPath() {
		setTitle("DeltaSimulink Setup");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(w, h, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		InstallerPath.this.setResizable(false);
		
		InstallerPath.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		InstallerPath.this.addWindowListener(new WindowAdapter() {
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
				InstallerPath.this.dispose();
				InstallerLicense.callFrmLicense();
			}
		});
		btnBack.setBounds(225, 354, 89, 27);
		contentPane.add(btnBack);
		
		final JButton btnNext = new JButton("Next>");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				installingPath = txtPath.getText();
				modelPath = txtModel.getText();
				
				
		    	if(!isPathValid(installingPath))
		    	{
		    		JOptionPane.showConfirmDialog(null, "The given installing directory is not valid.","Location Wrong",JOptionPane.DEFAULT_OPTION);
		    	} else if (!isPathValid(modelPath))
		    	{
		    		JOptionPane.showConfirmDialog(null, "The given model directory is not valid.","Location Wrong",JOptionPane.DEFAULT_OPTION);
		    	} else {
		    	
		    		InstallerPath.this.dispose();
		    		InstallerVersion.callFrmVersion();
				
		    	}
				//System.out.println(installingPath);
				//System.out.println(modelPath);
				
				
			}
		});
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
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
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 58, 570, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 341, 570, 2);
		contentPane.add(separator_1);
		
		
		JLabel lblNewLabel = new JLabel("Select Destination Location");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(30, 11, 243, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Where should DeltaSimulink be installed?");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(40, 33, 243, 14);
		contentPane.add(lblNewLabel_1);
		
		
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
		
		//////
		JLabel lblFolder = new JLabel("");
		lblFolder.setBounds(40, 71, 38, 44);
		contentPane.add(lblFolder);
		
		BufferedImage imgFoler=null;
		try {
			imgFoler = ImageIO.read(InstallerWelcome.class.getClassLoader().getResource("folder-subfolder-icon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image resizedImgFolder = imgFoler.getScaledInstance(lblFolder.getWidth(), lblFolder.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon imgFolderIcon = new ImageIcon(resizedImgFolder);
		lblFolder.setIcon(imgFolderIcon);
		//////
		
		JLabel lblNewLabel_3 = new JLabel("Setup will install DeltaSimulink and set up the Model directory into the following folders.");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(88, 86, 462, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("To continue, click next. If you would like to select a different folder for each, click Browse.");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(40, 118, 447, 14);
		contentPane.add(lblNewLabel_2);
		
		txtPath = new JTextField();
		txtPath.setBounds(40, 180, 344, 20);
		contentPane.add(txtPath);
		txtPath.setColumns(10);
		txtPath.setText("C:\\MATLAB_DL\\");
		
		JButton btnBrowseDL = new JButton("Browse...");
		btnBrowseDL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooserDL = new JFileChooser(); 
				chooserDL.setDialogTitle("Choose a folder");
			    chooserDL.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooserDL.setAcceptAllFileFilterUsed(false);
				if (chooserDL.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				     txtPath.setText(chooserDL.getSelectedFile().getAbsolutePath());
				    }
			}
		});
		
		btnBrowseDL.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnBrowseDL.setBounds(398, 179, 89, 23);
		contentPane.add(btnBrowseDL);
		
		JLabel lblNewLabel_4 = new JLabel("At least 50.0 MB of free disk space is required.");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(40, 319, 344, 14);
		contentPane.add(lblNewLabel_4);
		
		txtModel = new JTextField();
		txtModel.setText("C:\\MATLAB\\");
		txtModel.setBounds(39, 267, 345, 20);
		contentPane.add(txtModel);
		txtModel.setColumns(10);
		
		JButton btnBrowseModel = new JButton("Browse...");
		btnBrowseModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooserModel = new JFileChooser(); 
				chooserModel.setDialogTitle("Choose a folder");
			    chooserModel.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooserModel.setAcceptAllFileFilterUsed(false);
			    if (chooserModel.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				     txtModel.setText(chooserModel.getSelectedFile().getAbsolutePath());
				    }
			}
		});
		
		btnBrowseModel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnBrowseModel.setBounds(398, 266, 89, 23);
		contentPane.add(btnBrowseModel);
		
		JLabel lblNewLabel_5 = new JLabel("Location to save Model:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_5.setBounds(40, 242, 233, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Location to install DeltaSimulink:");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_6.setBounds(40, 155, 243, 14);
		contentPane.add(lblNewLabel_6);
	}

	public boolean isPathValid(String path)
	{
		if (path.startsWith(":\\", 1))
			return true;
		else return false;
	}
}

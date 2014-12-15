package installer;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class ExtractAndStart
{
	private static String targetPath0 ;
	private static String targetPath1 ;
	
	private static  String[] subPath0 = {"Delta\\", "Model\\", "Product\\"};
	private static  String[] subPath1 = {"lib\\", "Simulink-Frontend\\"};
	
	
	public static void main(String[] args) {
		try {
		      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    }
		    catch (Exception e) {
		      System.err.println("Error setting native LAF: " + e);
		    }

		InstallerWelcome.callFrmWelcome();
	}
	
    public static void install() throws URISyntaxException
    {
    	targetPath0 = InstallerPath.modelPath;
    	targetPath1 = InstallerPath.installingPath;
    	//System.out.println(targetPath0);
		//System.out.println(targetPath1);
        try {
        		//Check if Simulink exists and has the right version 2011b
        		//possibly check where simulink using where command
        		//grep for version string
        		//Create Folders Check if they exist in case they exist empty them
        		//Ask the user before you do that
        		// copy frontend files
        	
        		//TODO:Put link to startup.m on desktop
        		//TODO:add folders to start menu
        	     	
        	
        		Process process = Runtime.getRuntime().exec("matlab -h");
        		InputStream is = process.getInputStream();
        		InputStreamReader isr = new InputStreamReader(is);
        		BufferedReader br = new BufferedReader(isr);
        		String line;

        		//System.out.printf("Output of running %s is:", Arrays.toString(args));
        		String tmp = "";
        		while ((line = br.readLine()) != null) {
        			if(line.equals(""))
        				;
        			else{
        				tmp = line;
        				System.out.println(tmp);
        			}
        		}
        		//compare matlab version
        		if (!tmp.contains("Version:"))
        		{
        			JOptionPane.showConfirmDialog(null, "Matlab could not be found!","Wrong",JOptionPane.DEFAULT_OPTION);
        			return;
        		}
        		if(tmp.indexOf("7.13", 0) == -1)
        		{
        			JOptionPane.showConfirmDialog(null, "Matlab version 2011b should be installed!","Wrong",JOptionPane.DEFAULT_OPTION);
        			return;
        		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
        // copy files into the target folders
        if (existedFile(targetPath0) || existedFile(targetPath1))
        {
        	int ans = JOptionPane.showConfirmDialog(null, "Files already installed, remove and reinstalled them?","Notification",JOptionPane.OK_CANCEL_OPTION);
        	if (ans == 2 )
        		return;
        	else{
        		//delete old files
        		File dir0 = new File(targetPath0);
        		File dir1 = new File(targetPath1);
        		deleteDir(dir0);
        		deleteDir(dir1);
        	}
        }
        
        //System.out.println("deleting files!");
        
        //creating folders
        for(int i = 0; i < subPath0.length; i++)
        {
        	File dir = new File(targetPath0 + subPath0[i]);
        	dir.mkdirs();
        }
        
        //
        
        for(int i = 0; i < subPath1.length; i++)
        {
        	File dir = new File(targetPath1 + subPath1[i]);
        	dir.mkdirs();
        }
        
        //System.out.println("creating files!");
        
        //extracting files to folders
        try {
			extractFile("delta-simulink-be-1.3.0-SNAPSHOT.jar", targetPath1 + subPath1[0]+ "delta-simulink-be-1.3.0-SNAPSHOT.jar");
			
			// extracting files in Simulink-Frontend 
			extractFromFolder() ;	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //generate Startup.m
        generateStartup(targetPath1 ,targetPath0, InstallerVersion.DeltaSimulinkVersion);

        
        //addpath('C:\MATLAB_DL\Simulink-Frontend')
        try {
        	Runtime.getRuntime().exec("matlab -nodesktop -r \"addpath('"+targetPath1+"'Simulink-Frontend');savepath;exit;\"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    //iterated deleting files and folders
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        //
        return dir.delete();
    }
    
    //
    private static boolean existedFile(String path)
    {
    	File target = new File(path);

    	if (target.exists())
    		return true;
    	else 
    		return false;
    }
    
    // Extract files from folder in resources
    // handling separately the situation 
    // between running in jar file and in IDE
    private static void extractFromFolder() throws IOException, URISyntaxException
    {
    	String filePath = "Simulink-Frontend/";
    	
    	final File jarFile = new File(ExtractAndStart.class.getProtectionDomain().getCodeSource().getLocation().getPath());

    	if(jarFile.isFile()) 
    	{  // Run with JAR file
    	    final JarFile jar = new JarFile(jarFile);
    	    final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
    	    while(entries.hasMoreElements()) 
    	    {
    	        final String name = entries.nextElement().getName();
    	        if (name.startsWith(filePath) && !name.equals(filePath)) 
    	        { //filter according to the path
    	        	extractFile(name, targetPath1 + name);
    	        }
    	    }
    	jar.close();
    	}else
    	{ // Run within IDE
    		URL url = ExtractAndStart.class.getClassLoader().getResource(filePath);
    		if (url != null) 
    		{
    			File dir = new File(url.toURI());
    			String[] files = dir.list();
    			//System.out.printf("files[0]");
    			for (int i = 0; i < files.length; i++) 
    			{
    				extractFile(filePath+files[i], targetPath1 + subPath1[1] + files[i]);
    			}
    		}
	    }
    }
    
    // Extract single file form resources
    private static void extractFile(String name,String targetPath) throws IOException
    {
        ClassLoader cl = ExtractAndStart.class.getClassLoader();
        File target = new File(targetPath);
        //if (target.exists())
          //  return;

        FileOutputStream out = new FileOutputStream(target);
        InputStream in = cl.getResourceAsStream(name);

        byte[] buf = new byte[8*1024];
        int len;
        while((len = in.read(buf)) != -1)
        {
            out.write(buf,0,len);
        }
        out.close();
        in.close();
    }
    
    //writing startup.m file
    private static void generateStartup(String installerDir, String modelDir, String version){
    	PrintWriter writer = null;
		try {
			writer = new PrintWriter(installerDir+"Simulink-Frontend\\startup.m", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	writer.println("% Delta-Simulink");
    	writer.println("% Copyright (c) 2013, RIT, All rights reserved.");
    	writer.println("% ");
    	writer.println("% This project is free software; you can redistribute it and/or");
    	writer.println("% modify it under the terms of the GNU Lesser General Public");
    	writer.println("% License as published by the Free Software Foundation; either");
    	writer.println("% version 3.0 of the License, or (at your option) any later version.");
    	writer.println("% This library is distributed in the hope that it will be useful,");
    	writer.println("% but WITHOUT ANY WARRANTY; without even the implied warranty of");
    	writer.println("% MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU");
    	writer.println("% Lesser General Public License for more details.");
    	writer.println("% ");
        writer.println("% You should have received a copy of the GNU Lesser General Public");
    	writer.println("% License along with this project.");
    	writer.println("% ");
    	writer.println("% ");
    	writer.println("global Delta_Simulink_Version;");
    	writer.println("global DeltaSimulinkJARPath;");
    	writer.println("global DeltaSimulinkClasspath;");
    	writer.println("global model_dir;");
    	writer.println("global delta_dir;");
    	writer.println("global conf_file;");
    	writer.println("global product_dir;");
    	writer.println("global DeltaSimulinkJARPath;");
    	
    	writer.println("Delta_Simulink_Version='"+ version +"';");
    	writer.println("DeltaSimulinkJARPath='" + installerDir + "lib\\delta-simulink-be-1.3.0-SNAPSHOT.jar';");
    	writer.println("DeltaSimulinkClasspath='" + installerDir + "lib\\';");
    	writer.println("model_dir='" + modelDir + "Model\\';");
    	writer.println("delta_dir='" + modelDir + "Delta\\';");
    	writer.println("conf_file='" + modelDir + "DefaultConfig.delta';");
    	writer.println("product_dir='" + modelDir + "Product\\';");
    	writer.println("appendClassPath(DeltaSimulinkJARPath);");
    	writer.println("warning off MATLAB:dispatcher:nameConflict");
    	
    	writer.close(); 
	
    }
    

    
    
    
    
    

    
}

package installer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class ExtractAndStart
{
	private static final String targetPath0 = "C:\\MATLAB\\";
	private static final String targetPath1 = "C:\\MATLAB_DL\\";
	
	private static final String[] subPath0 = {"Delta\\", "Model\\", "Product\\"};
	private static final String[] subPath1 = {"lib\\", "Simulink-Frontend\\"};
	
	
    public static void main(String[] args) throws URISyntaxException
    {
        try {
        		//Check if Simulink exists and has the right version 2011b
        		//possibly check where simulink using where command
        		//grep for version string
        		//Create Folders Check if they exist in case they exist empty them
        		//Ask the user before you do that
        		//TODO: copy frontend files
        	
        		// TODO:Put link to startup.m on desktop
        		// TODO:add folders to start menu
        	     	
				extractFile("Lenna.png","C:\\test\\Lenna.png");
				extractFile("test.m","C:\\test\\test.m");
				//extractFile("Simulnk-Frontend/startup.m", targetPath1+subPath1[1]+"startup.m");
			
        		//Process process = new ProcessBuilder("maltab","-h").start();
        	
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
        //creating folders
        for(int i = 0; i < subPath0.length; i++)
        {
        	File dir = new File(targetPath0 + subPath0[i]);
        	dir.mkdirs();
        }
        for(int i = 0; i < subPath1.length; i++)
        {
        	File dir = new File(targetPath1 + subPath1[i]);
        	dir.mkdirs();
        }
        //extracting files to folders
        try {
			extractFile("delta-simulink-be-1.3.0-SNAPSHOT.jar", targetPath1 + subPath1[0]+ "delta-simulink-be-1.3.0-SNAPSHOT.jar");
			
			// extracting files in Simulink-Frontend 
			System.out.printf("adfdaf");
			extractAllFile() ;	
			//URL url = getClass().getResource("resources/");
			//URL url = ExtractAndStart.class.getResource("resources/Simulink-Frontend");
			//System.out.printf(url.toString());
			//File dir = new File(url.toURI());
			
			//String resPath = "/src/main/resources/Simulink-Frondend";
			
			//String resPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			//System.out.printf(resPath);
			//File dir = new File(resPath);
			//final String path = "resources/Simulink-Frontend";
			//final URL url = installer.ExtractAndStart.class.getResource("/" + path);
//			String filepath = this.getClass().getClassLoader().getResource("/").getPath();//+"/installer" ;   
//			System.out.printf(filepath);
//			
//		    if (filepath != null) {
//		        //final File apps = new File(url.toURI());
//				//for (File app : apps.listFiles()) {
//				  //  System.out.println(app);
//				//}
//				//File dir = new File(url.toURI());
//				File dir = new File(filepath);
//				String[] files = dir.list();
//				System.out.printf(files[0]);
//				System.out.printf("files[0]");
//				for (int i = 0; i < files.length; i++) 
//				{
//					extractFile(files[i], targetPath1 + subPath1[1]);
//				}
//		    }
//			
			System.out.printf("adfdaf");
			
//			String[] files = dir.list();
//			System.out.printf(files[0]);
//			System.out.printf("files[0]");
//			for (int i = 0; i < files.length; i++) 
//			{
//				extractFile(files[i], targetPath1 + subPath1[1]);
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //addpath('C:\MATLAB_DL\Simulink-Frontend')
        try {
			Process process;
			process = Runtime.getRuntime().exec("matlab -nodesktop -r \"addpath('C:\\MATLAB_DL\\Simulink-Frontend');savepath;exit;\"");
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
    
    private static void extractAllFile() throws IOException, URISyntaxException
    {
    	//String filepath = Class.class.getClass().getResource("/").getPath();//+"/installer" ;   
		//System.out.printf(filepath);
    	
    	//URL url = Class.class.getClass().getResource("Simulink-Frondend/");
    	//System.out.printf(url.toString());
    	//InputStream is = 
    	//		ExtractAndStart.class.getClassLoader().getResourceAsStream("Simulink-Frontend/");
    	
    	//System.out.println(is.toString());
    	String filepath = "Simulink-Frontend/";
    	
    	URL url = ExtractAndStart.class.getClassLoader().getResource(filepath);
    	//String path = ExtractAndStart.class.getClassLoader().getResource(filepath).getPath();
    	//System.out.printf(url.toString());
    	///System.out.println();
    	//System.out.printf(path);
	    if (url != null) {
//	        //final File apps = new File(url.toURI());
//			//for (File app : apps.listFiles()) {
//			  //  System.out.println(app);
//			//}
			File dir = new File(url.toURI());
			//File dir = new File(path);
			String[] files = dir.list();
			//File[] files = dir.listFiles();
			//System.out.printf(files[0]);
			//System.out.printf("files[0]");
			for (int i = 0; i < files.length; i++) 
			{
				extractFile(filepath+files[i], targetPath1 + subPath1[1] + files[i]);
			}
	    }
    }
    
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
}


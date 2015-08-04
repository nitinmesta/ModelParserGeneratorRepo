import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;



public class RepositoryGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File srcRootDirecory=new File("E:\\ModelParserGenerator\\src\\");
		File codeBaseDirectory=new File("E:\\ModelParserGenerator\\src\\com\\app\\imdvo\\Module\\DAO\\Model\\QuickQuote\\Master\\");
		
		
		String packageName="com.app.imdvo.Module.DAO.Model.QuickQuote.Master";
							
		try {
			
			URL url=srcRootDirecory.toURL();
			URL[] urls=new URL[]{url};
			ClassLoader cl=new URLClassLoader(urls);
			
			for(File file:codeBaseDirectory.listFiles())
			{
			
			File templateFile=new File("E:\\ModelParserGenerator\\template\\repositoryTemplate.txt");
			
			
			FileInputStream fInputStream=new FileInputStream(templateFile);		
			
			
			Class srcClass=cl.loadClass(packageName+"."+(file.getName().replace(".java", "")));	
				
			
			File targetFile=new File("E:\\ModelParserGenerator\\Output\\Repositories\\"+srcClass.getSimpleName()+"Repository.java");
			
			
			copyFileUsingFileStreams(templateFile, targetFile);
			
			String searchClassName="@ClassName@";
			String replaceClassName=srcClass.getSimpleName();
			
			replaceSelected(targetFile, searchClassName, replaceClassName);	
			
			
			String searchObjectName="@ObjectName@";
			String replaceObjectName=srcClass.getSimpleName().toLowerCase();
			
			replaceSelected(targetFile, searchObjectName, replaceObjectName);	
			
			
			}
			System.out.println("Generation completed!!");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void replaceSelected(File inputFile,String strToReplace, String toBeReplaceWith) {
	    try { 
	        // input the file content to the String "input" 
	        BufferedReader file = new BufferedReader(new FileReader(inputFile));
	        String line;String input = "";
	 
	        while ((line = file.readLine()) != null) input += line + '\n';
	 
	        file.close();

	        input=input.replaceAll(strToReplace, toBeReplaceWith);
	        // write the new String with the replaced line OVER the same file 
	        FileOutputStream fileOut = new FileOutputStream(inputFile);
	        fileOut.write(input.getBytes());
	        fileOut.close();
	 
	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    } 
	} 
	
	
	
	private static void copyFileUsingFileStreams(File source, File dest)
			throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			input.close();
			output.close();
		}
	}

}

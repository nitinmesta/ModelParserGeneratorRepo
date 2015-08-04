import java.beans.FeatureDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.print.attribute.standard.Finishings;



public class ParserGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		/*File srcRootDirecory=new File("E:\\GameDevelopment\\LibGDX\\ModelToParserGenerator\\src\\");
		File codeBaseDirectory=new File("E:\\GameDevelopment\\LibGDX\\ModelToParserGenerator\\src\\com\\app\\imdvo\\Module\\DAO\\Model\\");*/
		
		File srcRootDirecory=new File("E:\\ModelParserGenerator\\src\\");
		File codeBaseDirectory=new File("E:\\ModelParserGenerator\\src\\com\\app\\imdvo\\Module\\DAO\\Model\\QuickQuote\\Master\\");
		
		
		//com\s4tech\model\Customer.java
		String packageName="com.app.imdvo.Module.DAO.Model.QuickQuote.Master";
							
		try {
			
			URL url=srcRootDirecory.toURL();
			URL[] urls=new URL[]{url};
			ClassLoader cl=new URLClassLoader(urls);
			
			for(File file:codeBaseDirectory.listFiles())
			{
			
			File templateFile=new File("E:\\ModelParserGenerator\\template\\parserTemplate.txt");
			
			
			FileInputStream fInputStream=new FileInputStream(templateFile);		
			
			
			Class srcClass=cl.loadClass(packageName+"."+(file.getName().replace(".java", "")));	
				
			
			File targetFile=new File("E:\\ModelParserGenerator\\Output\\Parser"+srcClass.getSimpleName()+".java");
			
			
			copyFileUsingFileStreams(templateFile, targetFile);
			
			String searchClassName="@ClassName@";
			String replaceClassName=srcClass.getSimpleName();
			
			replaceSelected(targetFile, searchClassName, replaceClassName);	
			
			
			String searchObjectName="@ObjectName@";
			String replaceObjectName=srcClass.getSimpleName().toLowerCase();
			
			replaceSelected(targetFile, searchObjectName, replaceObjectName);	
			

			String searchStubString="@STUB@";
			String replaceStubString="";
			
			for(Field field:srcClass.getDeclaredFields())
				{
					/*System.out.println((field.getModifiers()==2?"private":"public" )+" "+ field.getGenericType().getTypeName()+ " " + field.getName());*/
					
					//System.out.println((field.getModifiers()==2?"private":"public" )+" "+ field.getGenericType().getTypeName()+ " " + field.getName());
					
					replaceStubString+=srcClass.getSimpleName().toLowerCase()+"."+field.getName()+"="+"(index=cursor.getColumnIndex(\""+field.getName()+"\"))!=-1?cursor.get";
					
					String defaultValue="";
					if(field.getType().isAssignableFrom(Integer.TYPE)||field.getType().isAssignableFrom(Boolean.TYPE) )
					{
						replaceStubString+="Int";
						defaultValue="0";
					}
					else if(field.getType().isAssignableFrom(String.class))
					{
						replaceStubString+="String";
						defaultValue="\"NA\"";
					}
					else if(field.getType().isAssignableFrom(Long.TYPE))
					{
						replaceStubString+="Long";
						defaultValue="0";
					}
					else if(field.getType().isAssignableFrom(Double.TYPE))
					{
						replaceStubString+="Double";
						defaultValue="0.0f";
					}
					
					
					//String res=+;
					
					/*((Constants.index = res.getColumnIndex(ConstantsColumnNames.COLUMN_FIELD_ID)) != Constants.NOT_EXISTING_COLUMN_INDEX
		                    ? Integer.parseInt(res.getString(Constants.index)) : Constants.NOT_AVAILABLE_INTEGER);*/
					
					replaceStubString+="(index):"+defaultValue+";\n\t\t\t\t";
					
					
					
				}
			
			replaceSelected(targetFile, searchStubString, replaceStubString);	
			
			}
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
	
	
	/*private static List<Class> getClassesForPackage(String pckgname) throws ClassNotFoundException {
        // This will hold a list of directories matching the pckgname. There may be more than one if a package is split over multiple jars/paths 
        ArrayList<File> directories = new ArrayList<File>();
        try { 
            ClassLoader cld = Thread.currentThread().getContextClassLoader();
            if (cld == null) {
                throw new ClassNotFoundException("Can't get class loader.");
            } 
            String path = pckgname.replace('.', '/');
            // Ask for all resources for the path 
            Enumeration<URL> resources = cld.getResources(path);
            while (resources.hasMoreElements()) {
                directories.add(new File(URLDecoder.decode(resources.nextElement().getPath(), "UTF-8")));
            } 
        } catch (NullPointerException x) {
            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Null pointer exception)");
        } catch (UnsupportedEncodingException encex) {
            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Unsupported encoding)");
        } catch (IOException ioex) {
            throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + pckgname);
        } 

        ArrayList<Class> classes = new ArrayList<Class>();
        // For every directory identified capture all the .class files 
        for (File directory : directories) {
            if (directory.exists()) {
                // Get the list of the files contained in the package 
                String[] files = directory.list();
                for (String file : files) {
                    // we are only interested in .class files 
                    if (file.endsWith(".class")) {
                        // removes the .class extension 
                      try 
                      { 
                        classes.add(Class.forName(pckgname + '.' + file.substring(0, file.length() - 6)));                      
                      } 
                      catch (NoClassDefFoundError e)
                      { 
                        // do nothing. this class hasn't been found by the loader, and we don't care. 
                      } 
                    } 
                } 
            } else { 
                throw new ClassNotFoundException(pckgname + " (" + directory.getPath() + ") does not appear to be a valid package");
            } 
        } 
        return classes;
    }   */
	
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

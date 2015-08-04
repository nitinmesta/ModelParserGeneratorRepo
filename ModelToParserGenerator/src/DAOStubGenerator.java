import java.beans.FeatureDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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



public class DAOStubGenerator {

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
			
			System.out.println("Code Snipet Generation Started!");
			StringBuffer stringBuffer=new StringBuffer();
			
			for(File file:codeBaseDirectory.listFiles())
			{
			
			Class srcClass=cl.loadClass(packageName+"."+(file.getName().replace(".java", "")));	
			stringBuffer.append("\n // Model class for "+srcClass.getSimpleName()+"\n");
			stringBuffer.append("Entity "+srcClass.getSimpleName().toLowerCase()+"=schema.addEntity(\""+srcClass.getSimpleName()+"\");\n");
			stringBuffer.append(srcClass.getSimpleName().toLowerCase()+".addIdProperty();\n");
				
			for(Field field:srcClass.getDeclaredFields())
				{
					
					
					if(field.getType().isAssignableFrom(Integer.TYPE))
					{
						stringBuffer.append(srcClass.getSimpleName().toLowerCase()+".addIntProperty(\""+field.getName()+"\");\n");
						
					}
					else if(field.getType().isAssignableFrom(Boolean.TYPE))
					{
						stringBuffer.append(srcClass.getSimpleName().toLowerCase()+".addBooleanProperty(\""+field.getName()+"\");\n");
						
					}
					else if(field.getType().isAssignableFrom(String.class))
					{
						stringBuffer.append(srcClass.getSimpleName().toLowerCase()+".addStringProperty(\""+field.getName()+"\");\n");
						
					}
					else if(field.getType().isAssignableFrom(Long.TYPE))
					{
						stringBuffer.append(srcClass.getSimpleName().toLowerCase()+".addLongProperty(\""+field.getName()+"\");\n");
						
					}
					else if(field.getType().isAssignableFrom(Double.TYPE))
					{
						stringBuffer.append(srcClass.getSimpleName().toLowerCase()+".addDoubleProperty(\""+field.getName()+"\");\n");
						
					}
					else if(field.getType().isAssignableFrom(Float.TYPE))
					{
						stringBuffer.append(srcClass.getSimpleName().toLowerCase()+".addFloatProperty(\""+field.getName()+"\");\n");
						
					}
					
				}
			
		
			
			
			}
			
			// Write String buffere to file
			BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(new File("E:\\ModelParserGenerator\\Output\\DAOCodeSnippet\\daoCodeSnippet.txt")));
			bufferedWriter.write(stringBuffer.toString());
			bufferedWriter.flush();
			bufferedWriter.close();
			
			System.out.println("Code Snipet Generated");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}

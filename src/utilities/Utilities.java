package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

public class Utilities {
	
	
	public static void printingGrafic(PrintWriter out, SortedSet<String> set, TreeMap<String,Integer> map)
	   {   //Drawing table for graph
		   out.println("<table align='center' cellpadding='2' cellspacing='2' border='0'>");
		   out.println("<tbody align='center'>");
		   out.println("<tr>");
		   int i = 1; 
		   String colors[] = {"#BDDA4C","#FF9A68","#69ABBF","#FFDE68","#AB6487"};
		   int j = 0;
		   for (String setElement : set)
		   {	double val = map.get(setElement);
		   	if (map.get(setElement) > 50*i)
		   	{	i++;
		   		val/=i;
		   	}
		   	out.println("<td align = 'center' valign='bottom'><div class='barrasv' style='width:50px;height:"+ val + "px;background-color:"+colors[j]+"'>&nbsp;</div></td>");
		   	if (j < colors.length)
		   	{  	j++;                    	
		   	}
		   	else
		   	{	j = 0;                    		
		   	}
		   }
		   out.println("</tr>");
		   out.println("<tr>");
		   int k = 0;
		   String[] titles = new String[set.size()];
		   //Getting processed values to draw the graph
		   for (String setElement : set)
		   {	out.println("<td align = 'center'>" + map.get(setElement) + "</td>");
		   		titles[k] = setElement;
		   		k++;
		   }
		   out.println("</tr>");
		   out.println("<tr>");
		   //Getting bar's titles 
		   for (String setElement : titles)
		   {	out.println("<td><div class='verticalmente'>"+ setElement +"</div></td>");
		   }
		   out.println("</tr>");
		   out.println("</tbody>");
		   out.println("</table>");
	   }
	
	public static String readingUploadFile(HttpServletRequest request, PrintWriter out)
	   {	String fileContent = "";           
	   		InputStream content =null;
	   		try {
	   			//Reading file from form
	   			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request); 
	   			for (FileItem item : items) {
		       		//Reading the file 		
		            content = item.getInputStream();
		            //Extracting string from file
		            fileContent = Streams.asString(content);
		       	}
	   	 } catch (FileUploadException e) {

	         try {
				throw new ServletException("Parsing file upload failed.", e);
			} catch (ServletException e1) {
				out.print(e1.getMessage());
				e1.printStackTrace();
			}

	     } catch (IOException e) {
	    	 out.print(e.getMessage());
			e.printStackTrace();
		}
	   	 return fileContent;
	   }
	   
	   public static String bringingOriginalFile(String root, PrintWriter out)
	   {   

		   //Bringing original file content
		   String stringContent = "";
		   try {
			   //Reading existing data file
			   FileInputStream fstream = new FileInputStream(root + "/datafinal.txt");
			   DataInputStream entry = new DataInputStream(fstream);
			   BufferedReader buffer = new BufferedReader(new InputStreamReader(entry));
			   
			   String strLinea = buffer.readLine();
			   if (strLinea == null)
			   {	
				   stringContent = "{\"message\": [\n\n]}";
			   }
			   else
			   {
				   //Saving strings from file
				   while (strLinea != null)   {
				       stringContent+=strLinea +"\n";
				       strLinea = buffer.readLine();
				   }
			   }
			   entry.close();
		} catch (FileNotFoundException e) {
			out.print(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			out.print(e.getMessage());
			e.printStackTrace();
		}
		   
		   return stringContent;
	   }
	   
	   
	   public static void writingFile(String root, String stringContent, String newContent, PrintWriter out)
	   {	try {
				//Opening file to write
				BufferedWriter fileOut = new BufferedWriter(new FileWriter(root + "/datafinal.txt", false));
				fileOut.flush();
				String[] bracesCount = stringContent.split("}");
				String comma = "";
				if (bracesCount.length > 2)
				{ 	comma = ",";					
				}
				//Preparing to add new data to file
				stringContent = stringContent.substring(0,stringContent.lastIndexOf("]}")-1)  + comma;
			    fileOut.write(stringContent);
				fileOut.newLine();
				fileOut.write(newContent);
				fileOut.newLine();
				fileOut.append("]}");
				fileOut.close();
				
			} catch (IOException e) {
				out.print(e.getMessage());
				e.printStackTrace();
			}
		   
	   }
	   
	   public static void processingFile(String root, PrintWriter out)
	   {   
		   	   
		   InputStream is = null;
			try {
				is = new FileInputStream(new File(root + "/datafinal.txt"));
			} catch (FileNotFoundException e) {
				out.print(e.getMessage());
				e.printStackTrace();
			}
			//Converting data file into JSON objects
		   JsonReader rdr = Json.createReader(is);
		   JsonObject obj = rdr.readObject();
		   JsonArray results = obj.getJsonArray("message");
		   SortedSet<String> currencyFrom = new TreeSet<String>();
		   SortedSet<String> currencyTo = new TreeSet<String>();
		   TreeMap<String,Integer> currencyFromMap = new TreeMap<String,Integer>();
		   TreeMap<String,Integer> currencyToMap = new TreeMap<String,Integer>();
		   double sumRates = 0.0;
		   //Processing JSON objects to get asked values 
		   for (JsonObject result : results.getValuesAs(JsonObject.class)) {
		     //Getting amount of currencies from         	
		  	   if(!currencyFrom.add(result.getString("currencyFrom")))
		       {	currencyFromMap.put(result.getString("currencyFrom"), currencyFromMap.get(result.getString("currencyFrom"))+1);                        	
		       }
		       else
		       {	currencyFromMap.put(result.getString("currencyFrom"), 1);
		       }
		  	   
		  	//Getting amount of currencies to
		       if(!currencyTo.add(result.getString("currencyTo")))
		       {	currencyToMap.put(result.getString("currencyTo"), currencyToMap.get(result.getString("currencyTo"))+1);                        	
		       }
		       else
		       {	currencyToMap.put(result.getString("currencyTo"), 1);
		       }
		       //Getting sum of rates to calculate the average
		       sumRates+=result.getJsonNumber("rate").doubleValue();                        
		                  
		   }
		              
		   //Drawing results (graphs and average)           
		   double average = sumRates/results.getValuesAs(JsonObject.class).size();
		   out.println("<div align = 'center' style = 'width:100%'><div style = 'float:left'><h4>Currency From:</h4>");
		   Utilities.printingGrafic(out, currencyFrom, currencyFromMap);
		   out.println("</div><div style='float:left;width:500px'><label/></div><div style = 'float:left'><h4>Currency To:</h4>");
		   Utilities.printingGrafic(out, currencyTo, currencyToMap);
		   out.println("<div style = 'float:left'><br><h5>Rate Average:" + average + "</h5></div>");
		   out.println("</div></div>");
		   
	   }
	
	

}

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class FilterCSV {
	private static String[] ntModels = {"NTMT326GP4", "GPAPI5C32R", "PHNT64PSIMC4MB",
			"NTMTEX431G", "ALCATEL A554C", "NTZEZ750CPTGP", "NTC261P4"};
	
	private static String[] tcModels = {"TCHUH881CPTGP", "TCZEZ660GP5", "Android 4.0 & 4.1", "ALCATEL A554C",
		"TCSAS275GBK3P7P"};
//	"STLG800GP5", 
	private static String[] stModels = {"HUAWEI H867G", "STSAR375CP", "Android 4.0 & 4.1",
		"STHUH226CPWP", "STLG420GP4"};
	
	private static String[] tfModels = {"TFLG200CP", "TFSAT404GTMP4", 
		"TFLG220CP", "TFLG420GP4"};
//	, "TFSAT105GP5""TFSAT404GTMP4""TFLG501CPDM",, "TFSAS150GP"
	
	private static String[] udModels = {"PHSM64PSIMT5MB,SMNS0010MILD", "PHSM64PSIMT5MB,SM64PSIMT5B,33178,SMNAPP0040UNL",
		"TFLG800GP5DM",	"airwavewireless@yahoo.com", "NTSAS960CPTGP", "NTLG620GP4-N3"};
//	 "NTLGL95GP5"
	private static String smModel = "PHSM64PSIMT5BLC";
	
	private static String[] claro = {"CL7","Past"};
	
	public FilterCSV() {
		
	}

	public static void main(String[] args) throws IOException {
	List<File> parentFolders = new ArrayList<File>();
		File ntFolder = new File("X:/projects/Twist/twist-net10E-web/scenarios");
		parentFolders.add(ntFolder);
		File tcFolder = new File("X:/projects/Twist/twist-telcel-web/scenarios");
		parentFolders.add(tcFolder);
		File stFolder = new File("X:/projects/Twist/twist-straighttalk-web/scenarios");
		parentFolders.add(stFolder);
		File tfFolder = new File("X:/projects/Twist/twist-tracfone-web/scenarios");
		parentFolders.add(tfFolder);
		File udFolder = new File("X:/projects/Twist/twist-dealerportal/scenarios");
		parentFolders.add(udFolder);
		File tasFolder = new File("X:/projects/Twist/Twist-TAS/scenarios");
		parentFolders.add(tasFolder);
		File smFolder = new File("X:/projects/Twist/Twist-SimpleMobile-Web/scenarios");
		parentFolders.add(smFolder);
		File totalFolder = new File("X:/projects/Twist/twist-total-web/scenarios");
		parentFolders.add(totalFolder);
		File gsFolder = new File("X:/projects/Twist/twist-GoSmart-web/scenarios");
		parentFolders.add(gsFolder);
		
		for (File folder: parentFolders) {
			System.out.println(folder.getParent());
			List<File> csvFiles = listFilesForFolder(folder);
			
			for (File file : csvFiles) {
				filterOutCSV(file, claro);
				pickRandomThree(file);
			}
			System.out.println();
			
		}
	}
	
	private static void pickFirstMatch(File d, String match) throws IOException {
		List<String> lines = Files.readLines(d, Charsets.US_ASCII);		
		List<String> outLines = new ArrayList<String>();
		outLines.add(lines.get(0));
		
		for (String line : lines) {
			if (line.contains(match)) {
				outLines.add(line);
				break;
			}
		}
		
		if (outLines.size() <= 1 && lines.size() >= 2) {
			outLines.add(lines.get(1));
		}
		
		writeToFile(d, outLines);
	}

	private static void pickRandomThree(File d) throws IOException {
		List<String> lines = Files.readLines(d, Charsets.US_ASCII);		
		List<String> outLines = new ArrayList<String>();
		int length = lines.size();
		int spread = (length-1) / 3;
		int max;
		if (spread < 1) {
			spread = 1;
			max = length;
		} else {
			max = 4;
		}
		outLines.add(lines.get(0));
		for (int i = 1; i < max; i++) {
			try {
				outLines.add(lines.get(i*spread));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		writeToFile(d, outLines);
	}
	
	
	private static void keepOnlyRowsThatMatch(File d, String[] models) throws IOException {
		List<String> lines = Files.readLines(d, Charsets.US_ASCII);		
		List<String> outLines = new ArrayList<String>();
		outLines.add(lines.get(0));
		
		for (String line : lines) {
			for (String model : models) {
				if (line.contains(model)) {
					outLines.add(line);
					break;
				}
			}			
		}
		if (outLines.size() <= 1 && lines.size() >= 2) {
			outLines.add(lines.get(1));
		}
		
		writeToFile(d, outLines);
	}
	
	private static void filterOutCSV(File d, String[] filter) throws IOException {
		List<String> lines = Files.readLines(d, Charsets.US_ASCII);		
		if (d.getName().contains("RAC")) {
			System.out.println(d.getPath());
		}
		Iterator<String> i = lines.iterator();
		while (i.hasNext()) {
			String line = i.next();
			for (String f : filter) {
				if (line.contains(f)) {
					i.remove();
					break;
				}
			}
		}
		writeToFile(d, lines);
	}
	
	private static void countCSV(File d) throws IOException {
		List<String> lines = Files.readLines(d, Charsets.US_ASCII);
		int size = lines.size()-1;
		System.out.println(d.getName().substring(0, d.getName().length()-8) + "," + size);
	}
	
	public static List<File> listFilesForFolder(File folder) {
		List<File> csvFiles = new ArrayList<File>();
	    for (final File fileEntry : folder.listFiles()) {
//	        if (fileEntry.isDirectory()) {
//	            listFilesForFolder(fileEntry, csvFiles);
//	        } else 
	        if (fileEntry.getName().endsWith(".scn.csv")){
	         	csvFiles.add(fileEntry);
//	         	System.out.println(fileEntry.getAbsolutePath());
	        }
	    }
	    return csvFiles;
	}
	
	private static void writeToFile(File d, List<String> outLines) throws IOException {
		BufferedWriter writer = null;
		try	{
			writer = new BufferedWriter(new FileWriter(d));
			for (String line : outLines) {
				writer.write(line);
				writer.write("\r\n");	
			}
		} finally {
			try	{
				if (writer != null)
					writer.close( );
			} catch (IOException e) { }
	     }
	}
	
	private static void selectOneAndThenRandom(File d, String model) throws IOException {
        List<String> lines = Files.readLines(d, Charsets.US_ASCII);          
        List<String> outLines = new ArrayList<String>();
        outLines.add(lines.get(0));
        
		for (String line : lines) {
			if (line.contains(model)) {
				outLines.add(line);
				lines.remove(line);
				break;
			}
		}

        int length = lines.size();
        int spread = (length-1) / 5;
        int max;
        if (spread < 1) {
               spread = 1;
               max = length;
        } else {
               max = 5;
        }

        for (int i = outLines.size(); i < max; i++) {
			try {
				outLines.add(lines.get(i * spread));
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
        writeToFile(d, outLines);
 }


}
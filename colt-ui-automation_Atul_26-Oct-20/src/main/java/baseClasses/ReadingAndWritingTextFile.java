package baseClasses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadingAndWritingTextFile {
	// Program for reading and writing text file

	FileReader filename;
	BufferedReader inputFile;
	String userCounter;
	String ucount;
	FileWriter writefile;
	BufferedWriter outputFile;
	String emailID;

	public String getQuoteId() {
		try {
			try {
				filename = new FileReader(
						".\\src\\main\\resources\\quoteid.txt");
				inputFile = new BufferedReader(filename);
				userCounter = inputFile.readLine();
				ucount = userCounter;
			} catch (FileNotFoundException e) {
				System.out
				.println("File :- \'quoteid.txt\' isn't found at location.");
			} finally {
				inputFile.close();
				filename.close();
			}
		} catch (IOException e) {
			System.out.println("Exception while reading a file "
					+ e.getMessage());
		}
		return ucount;
	}

	public void putQuoteId(String quoteId) {
		try {
			try {
				writefile = new FileWriter(
						".\\src\\main\\resources\\quoteid.txt");
				outputFile = new BufferedWriter(writefile);
				outputFile.write(quoteId);
			} catch (FileNotFoundException e) {
				System.out
				.println("File :- \'quoteid.txt\' isn't found at location.");
			} finally {
				outputFile.close();
				writefile.close();
			}
		} catch (IOException ex) {
			System.out.println("Exception while writing a file");
		}
	}
	
	public void writeToTextLog(String filePath, String updateCount,String mode)
	{
		try {
			try {
				if(mode.trim().equalsIgnoreCase("append"))
				{
					writefile = new FileWriter(filePath,true);	
					outputFile = new BufferedWriter(writefile);
					outputFile.write(updateCount);
					outputFile.newLine();

				}else if(mode.trim().equalsIgnoreCase("appendNoNewLine"))
				{
					writefile = new FileWriter(filePath,true);
					outputFile = new BufferedWriter(writefile);
					outputFile.write(updateCount);

				}else if(mode.trim().equalsIgnoreCase("newNoNewLine"))
				{
					writefile = new FileWriter(filePath);
					outputFile = new BufferedWriter(writefile);
					outputFile.write(updateCount);
				}else
				{
					writefile = new FileWriter(filePath);
					outputFile = new BufferedWriter(writefile);
					outputFile.write(updateCount);
					outputFile.newLine();
				}
				//outputFile = new BufferedWriter(writefile);
				//outputFile.write(updateCount);
				//outputFile.newLine();
			} catch (FileNotFoundException e) {
				Report.LogInfo("Exception", "Exception in writeToTextLog "+e.getMessage(), "FAIL");
			} finally {
				outputFile.close();
				writefile.close();
			}
		} catch (IOException ex) {
			System.out.println("Exception while writing a file");
		}
	}	
	public String readEntireFile(String filename) throws IOException {
		FileReader in = new FileReader(filename);
		StringBuilder contents = new StringBuilder();
		char[] buffer = new char[4096];
		int read = 0;
		do {
			contents.append(buffer, 0, read);
			read = in.read(buffer);
		} while (read >= 0);
		return contents.toString();
	}


}

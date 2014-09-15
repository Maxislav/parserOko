package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogTxt {
		PrintWriter out;
		TimeStamp time;
	
		public LogTxt() throws IOException{
			
			//save("ololo");
			time = new TimeStamp();
			
		}
		public void save(String txt) throws IOException{
			out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
			String t = time.getDateTimeNormal();
		    out.println(t+"  "+txt+"\r\n"+"\r\n ");
		    out.close();
			
			
		}
}

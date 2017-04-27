package yummy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class fetch {
public static void main(String[] args) throws IOException{
	String username="myusername";
    String password="mypassword";
    String url="https://api.locu.com/v2/venue/search/";
       String[] command = {"curl.exe", url};
        ProcessBuilder process = new ProcessBuilder("C:/Program Files/curl/curl", "www.google.com"); 
        
        Process p;
        try
        {
            p = process.start();
             BufferedReader reader =  new BufferedReader(new InputStreamReader(p.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ( (line = reader.readLine()) != null) {
                        builder.append(line);
                        builder.append(System.getProperty("line.separator"));
                }
                String result = builder.toString();
                System.out.print(result);
                
        }
        catch (IOException e)
        {   System.out.print("error");
            e.printStackTrace();
        }
}
}

package yummy;

import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class WebScraping {
/*
	public static void main(String[] args) throws JauntException, FileNotFoundException{
		UserAgent user = new UserAgent();
		user.visit("http://google.com");
		user.doc.apply("Pizzeria Prima Strada menu");
		user.doc.submit("Google Search");
		Elements links = user.doc.findEvery("<h3 class=r>").findEvery("<a>"); //find search result links
		String final_url = null;
		
		for (Element link : links){
	//		System.out.println(link.getAt("href"));
		}
		for (Element link : links){
			final_url = link.getAt("href");
			break;
		}
		
		System.out.println(final_url);
		String[] temp = final_url.split("&amp");
		
		
		
		temp = temp[0].split("=");
		
		final_url = temp[1];
		
		System.out.println(final_url);
		user  = new UserAgent();
		user.visit(final_url);
		
	//	user.doc.submit("Burgers, Sandwiches, & Wraps");
		PrintWriter output = new PrintWriter("output.txt");
		//user.doc.fillout("Location", "Hamilton");
		output.write(user.doc.innerXML());
		
		user.doc.apply("Hamilton");
//		user.doc.apply("Hamilton");
//		user.doc.submit();
		
		System.out.println(user.doc.innerText());
	}
	*/
}

package tools;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.MutableAttributeSet;

public class WebTools {
	
	private static ArrayList<URL> urls = new ArrayList<URL> ();
	
	public static void loadURLs(String urlList) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(urlList));
			String curLine = null;
			while ((curLine = br.readLine()) != null) {
				//System.out.println(curLine);
				urls.add(new URL(curLine));
			}
		} catch(IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void downLoadWebPage(Integer urlIndex, String path) {
		
		URL url = urls.get(urlIndex);
		InputStream is = null;
		String curLine = null;
		BufferedReader br = null;

		try {
			File output = new File(path+"\\\\page"+urlIndex.toString()+".htm");
			BufferedWriter wt = new BufferedWriter(new FileWriter(output));
			try {
				is = url.openStream();
				br = new BufferedReader(new InputStreamReader(is));
				while ((curLine = br.readLine()) != null) {	
					//System.out.println(curLine);
					wt.write(curLine+"\n");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			wt.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private static ArrayList<String> extractText (Reader reader) {
		final ArrayList<String> list = new ArrayList<String>();

	    ParserDelegator parserDelegator = new ParserDelegator();
	    ParserCallback parserCallback = new ParserCallback() {
	      public void handleText(final char[] data, final int pos) {
	        list.add(new String(data));
	      }
	      public void handleStartTag(Tag tag, MutableAttributeSet attribute, int pos) { }
	      public void handleEndTag(Tag t, final int pos) {  }
	      public void handleSimpleTag(Tag t, MutableAttributeSet a, final int pos) { }
	      public void handleComment(final char[] data, final int pos) { }
	      public void handleError(final java.lang.String errMsg, final int pos) { }
	    };
	    try {
	    	parserDelegator.parse(reader, parserCallback, true);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    return list;
	}
	
	public static void parseWebPage(Integer pageIndex, String path) {
		try {
			FileReader reader = new FileReader(path+"\\\\page"+pageIndex.toString()+".htm");
			File output = new File(path+"\\\\page"+pageIndex.toString()+".txt");
			BufferedWriter wt = new BufferedWriter(new FileWriter(output));
			ArrayList<String> list = extractText(reader);
			for (String line : list) wt.write(line);
			reader.close();
			wt.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteWebPage(Integer pageIndex, String path) {
		try {
			File file = new File(path+"\\\\page"+pageIndex.toString()+".htm");
			if(file.exists())file.delete();
			else System.out.println("file does not exist");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getURLsNumber() {
		return urls.size();
	}
}

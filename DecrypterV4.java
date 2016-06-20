import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DecrypterV4 extends Application 
{

	private static File path;
	private static File orig;
	private static File dec;
	private static int count = 0;
	private static String decryptedText = "";
	private static String encryptionCode = "";
	private static boolean newFile = true;
	
	private void decrypt() throws Exception
	{
		BufferedReader a = new BufferedReader(new FileReader(orig));
		encryptionCode=a.readLine();
		while(hasNext(a))
		{
			String h = a.readLine();
			decryptedText += fromCode(from4(fromDNA(h)))+"\n";
		}
		a.close();
		if(!newFile)
			setup();
	}
	
	private static boolean hasNext(BufferedReader br) throws Exception
	{
		br.mark(10);
		if(br.read() == -1)
			return false;
		br.reset();
		return true;
	}
	
	private static String fromDNA(String h)
	{
		String g = "";
		for(int o = 0; o < h.length(); o++)
		{
			switch(h.charAt(o))
			{
				case 'A':
					g+="2";
					break;
				case 'G':
					g+="0";
					break;
				case 'C':
					g+="1";
					break;
				case 'T':
					g+="3";
					break;
			}
		}
		return g;
	}
	
	private static String from4(String h)
	{
		String g = "";
		while(h.length()>0)
		{
			int y = Integer.parseInt(h.substring(0,4),4);
			g+=(char)y;
			h=h.substring(4);
		}
		return g;
	}
	
	private static String fromCode(String h)
	{
		String forwards = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String forwards2 = forwards.toLowerCase();
		String backwards = new StringBuilder(forwards).reverse().toString();
		String backwards2 = backwards.toLowerCase();
		if(encryptionCode.charAt(0)=='A')
		{
			for(int o = 0; o < h.length(); o++)
			{
				if(Character.isLetter(h.charAt(o)))
				{
					if(h.charAt(o)>=97)
					{
						int pos = forwards2.indexOf(h.charAt(o));
						h=(o>0?h.substring(0,o):"")+forwards2.charAt(pos-1>=0?pos-1:pos+25)+h.substring(o+1);
					}
					else
					{
						int pos = forwards.indexOf(h.charAt(o));
						h=(o>0?h.substring(0,o):"")+forwards.charAt(pos-1>=0?pos-1:pos+25)+h.substring(o+1);
					}
				}
			}
		}
		if(encryptionCode.charAt(0)=='B')
		{
			for(int o = 0; o < h.length(); o++)
			{
				if(Character.isLetter(h.charAt(o)))
				{
					if(h.charAt(o)>=97)
					{
						int pos = forwards2.indexOf(h.charAt(o));
						h=(o>0?h.substring(0,o):"")+forwards2.charAt(pos-5>=0?pos-5:pos-5+26)+h.substring(o+1);
					}
					else
					{
						int pos = forwards.indexOf(h.charAt(o));
						h=(o>0?h.substring(0,o):"")+forwards.charAt(pos-5>0?pos-5:pos-5+26)+h.substring(o+1);
					}
				}
			}
		}
		if(encryptionCode.charAt(0)=='C')
		{
			for(int o = 0; o < h.length(); o++)
			{
				if(Character.isLetter(h.charAt(o)))
				{
					if(h.charAt(o)>=97)
					{
						int pos = forwards2.indexOf(h.charAt(o));
						h=(o>0?h.substring(0,o):"")+backwards2.charAt(pos)+h.substring(o+1);
					}
					else
					{
						int pos = forwards.indexOf(h.charAt(o));
						h=(o>0?h.substring(0,o):"")+backwards.charAt(pos)+h.substring(o+1);
					}
				}
			}
		}
		if(encryptionCode.charAt(0)=='D')
		{
			HashMap<String, Character> m = new HashMap<String, Character>();
			m.put("AAAAA", 'A');
			m.put("AAAAB", 'B');
			m.put("AAABA", 'C');
			m.put("AABAA", 'D');
			m.put("ABAAA", 'E');
			m.put("BAAAA", 'F');
			m.put("AAABB", 'G');
			m.put("AABBA", 'H');
			m.put("ABBAA", 'I');
			m.put("BBAAA", 'J');
			m.put("AABBB", 'K');
			m.put("ABBBA", 'L');
			m.put("BBBAA", 'M');
			m.put("ABBBB", 'N');
			m.put("BBBBA", 'O');
			m.put("BBBBB", 'P');
			m.put("AAAAC", 'Q');
			m.put("AAACA", 'R');
			m.put("AACAA", 'S');
			m.put("ACAAA", 'T');
			m.put("CAAAA", 'U');
			m.put("AAACC", 'V');
			m.put("AACCA", 'W');
			m.put("ACCAA", 'X');
			m.put("CCAAA", 'Y');
			m.put("AACCC", 'Z');
			m.put("ACCCA", 'a');
			m.put("CCCAA", 'b');
			m.put("ACCCC", 'c');
			m.put("CCCCA", 'd');
			m.put("CCCCC", 'e');
			m.put("AAAAD", 'f');
			m.put("AAADA", 'g');
			m.put("AADAA", 'h');
			m.put("ADAAA", 'i');
			m.put("DAAAA", 'j');
			m.put("AAADD", 'k');
			m.put("AADDA", 'l');
			m.put("ADDAA", 'm');
			m.put("DDAAA", 'n');
			m.put("AADDD", 'o');
			m.put("ADDDA", 'p');
			m.put("DDDAA", 'q');
			m.put("ADDDD", 'r');
			m.put("DDDDA", 's');
			m.put("DDDDD", 't');
			m.put("AAAAE", 'u');
			m.put("AAAEA", 'v');
			m.put("AAEAA", 'w');
			m.put("AEAAA", 'x');
			m.put("EAAAA", 'y');
			m.put("AAAEE", 'z');
			m.put("AAEEA", '.');
			m.put("AEEAA", '!');
			m.put("EEAAA", '?');
			m.put("AAEEE", ',');
			m.put("AEEEA", '@');
			m.put("EEEAA", '#');
			m.put("AEEEE", '$');
			m.put("EEEEA", '%');
			m.put("EEEEE", '*');
			m.put("AAAAF", '-');
			m.put("AAAFA", '+');
			m.put("AAFAA", '\'');
			m.put("AFAAA", '"');
			m.put("FAAAA", ':');
			m.put("AAAFF", ';');
			
			m.put("AAFFA", '0');
			m.put("AFFAA", '1');
			m.put("FFAAA", '2');
			m.put("AAFFF", '3');
			m.put("AFFFA", '4');
			m.put("FFFAA", '5');
			m.put("AFFFF", '6');
			m.put("FFFFA", '7');
			m.put("FFFFF", '8');
			m.put("AAAAG", '9');
			
			for(int o = 0; o < h.length(); o++)
			{
				if(Character.isLetter(h.charAt(o)))
				{
					String g = h.substring(o,o+5);
					h=h.substring(0,o)+m.get(g)+h.substring(o+5);
				}
			}
		}
		return h;	
	}
	
	private static void setup() throws Exception
	{
		FileWriter fw = new FileWriter(dec);
		PrintWriter pw = new PrintWriter(fw);
		BufferedReader it = new BufferedReader(new StringReader(decryptedText));
		pw.println(encryptionCode+"\n");
		while(hasNext(it))
		{
			pw.println(it.readLine());
		}
		it.close();
		pw.close();
		fw.close();
	}
	
	public static void main(String[] args) throws Exception 
	{
		path = new File(DecrypterV4.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		path = new File((""+path).substring(0,(""+path).length()-15)+"\\");
		launch(args);
	}

	public void start(Stage stage)
	{
		BorderPane g = new BorderPane();
		g.setPadding(new Insets(20,20,20,20));
		
		TextArea area = new TextArea();
		TextField field = new TextField();
		field.setOnAction(event ->
		{
			switch(count++)
			{
				case 0:
					area.setText("What is the name of the output file?");
					orig = new File(path.toString()+"\\"+field.getText()+".dat");
					field.setText("");
					break;
				case 1:
					newFile=field.getText().equals("read")?newFile:!newFile;
					dec = new File(path.toString()+"\\"+field.getText()+".dat");
					field.setText("");
					try
					{
						decrypt();
					}
					catch(Exception e)
					{
						decryptedText="Error Decrypting";
					}
					finally
					{
						area.setText(decryptedText);
					}
					break;
				case 2:
					System.exit(0);
					break;
				default:
					System.exit(1);
			}
		});
		
		area.setText("What is the name of the file to be decrypted?");
		
		g.setTop(field);
		g.setBottom(area);
		
		Scene scene = new Scene(g, 600, 350, Color.BLACK);
		stage.setScene(scene);
		stage.show();
	}
	
}

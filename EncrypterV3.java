import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EncrypterV3 extends Application 
{

	private static File path;
	private static File orig;
	private static File enc;
	private static int count = 0;
	private static String encryptedText = "";
	private static String encryptionCode = "";
	private static CheckBox rot1;
	private static CheckBox atbash;
	private static CheckBox baconian;
	private static CheckBox rot5;
	
	public static void main(String[] args) throws Exception
	{
		path = new File(EncrypterV3.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		path = new File((""+path).substring(0,(""+path).length()-15)+"\\");
		launch(args);
	}
	
	private void encrypt() throws Exception
	{
		encryptionCode = genCode();
		Scanner a = new Scanner(orig);
		while(a.hasNext())
			encryptedText += dna(four(coded(a.nextLine())))+"\n";
		a.close();
		setup();
	}
	
	private static String coded(String h)
	{
		String forwards = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String forwards2 = forwards.toLowerCase();
		String backwards = new StringBuilder(forwards).reverse().toString();
		String backwards2 = backwards.toLowerCase();
		if(rot1.isSelected())
		{
			for(int o = 0; o < h.length(); o++)
			{
				if(Character.isLetter(h.charAt(o)))
				{
					if(h.charAt(o)>=97)
					{
						int pos = forwards2.indexOf(h.charAt(o));
						h=(o>0?h.substring(0,o):"")+forwards2.charAt(pos+1<26?pos+1:pos-25)+h.substring(o+1);
					}
					else
					{
						int pos = forwards.indexOf(h.charAt(o));
						h=(o>0?h.substring(0,o):"")+forwards.charAt(pos+1<26?pos+1:pos-25)+h.substring(o+1);
					}
				}
			}
		}
		if(rot5.isSelected())
		{
			for(int o = 0; o < h.length(); o++)
			{
				if(Character.isLetter(h.charAt(o)))
				{
					if(h.charAt(o)>=97)
					{
						int pos = forwards2.indexOf(h.charAt(o));
						h=(o>0?h.substring(0,o):"")+forwards2.charAt(pos+5<26?pos+5:pos-21)+h.substring(o+1);
					}
					else
					{
						int pos = forwards.indexOf(h.charAt(o));
						h=(o>0?h.substring(0,o):"")+forwards.charAt(pos+5<26?pos+5:pos-21)+h.substring(o+1);
					}
				}
			}
		}
		if(atbash.isSelected())
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
		if(baconian.isSelected())
		{
			HashMap<Character, String> m = new HashMap<Character, String>();
			m.put('A', "AAAAA");
			m.put('B', "AAAAB");
			m.put('C', "AAABA");
			m.put('D', "AABAA");
			m.put('E', "ABAAA");
			m.put('F', "BAAAA");
			m.put('G', "AAABB");
			m.put('H', "AABBA");
			m.put('I', "ABBAA");
			m.put('J', "BBAAA");
			m.put('K', "AABBB");
			m.put('L', "ABBBA");
			m.put('M', "BBBAA");
			m.put('N', "ABBBB");
			m.put('O', "BBBBA");
			m.put('P', "BBBBB");
			m.put('Q', "AAAAC");
			m.put('R', "AAACA");
			m.put('S', "AACAA");
			m.put('T', "ACAAA");
			m.put('U', "CAAAA");
			m.put('V', "AAACC");
			m.put('W', "AACCA");
			m.put('X', "ACCAA");
			m.put('Y', "CCAAA");
			m.put('Z', "AACCC");
			m.put('a', "ACCCA");
			m.put('b', "CCCAA");
			m.put('c', "ACCCC");
			m.put('d', "CCCCA");
			m.put('e', "CCCCC");
			m.put('f', "AAAAD");
			m.put('g', "AAADA");
			m.put('h', "AADAA");
			m.put('i', "ADAAA");
			m.put('j', "DAAAA");
			m.put('k', "AAADD");
			m.put('l', "AADDA");
			m.put('m', "ADDAA");
			m.put('n', "DDAAA");
			m.put('o', "AADDD");
			m.put('p', "ADDDA");
			m.put('q', "DDDAA");
			m.put('r', "ADDDD");
			m.put('s', "DDDDA");
			m.put('t', "DDDDD");
			m.put('u', "AAAAE");
			m.put('v', "AAAEA");
			m.put('w', "AAEAA");
			m.put('x', "AEAAA");
			m.put('y', "EAAAA");
			m.put('z', "AAAEE");
			m.put('.', "AAEEA");
			m.put('!', "AEEAA");
			m.put('?', "EEAAA");
			m.put(',', "AAEEE");
			m.put('@', "AEEEA");
			m.put('#', "EEEAA");
			m.put('$', "AEEEE");
			m.put('%', "EEEEA");
			m.put('*', "EEEEE");
			m.put('-', "AAAAF");
			m.put('+', "AAAFA");
			m.put('\'', "AAFAA");
			m.put('"', "AFAAA");
			m.put(':', "FAAAA");
			m.put(';', "AAAFF");
			
			m.put('0', "AAFFA");
			m.put('1', "AFFAA");
			m.put('2', "FFAAA");
			m.put('3', "AAFFF");
			m.put('4', "AFFFA");
			m.put('5', "FFFAA");
			m.put('6', "AFFFF");
			m.put('7', "FFFFA");
			m.put('8', "FFFFF");
			m.put('9', "AAAAG");
			
			for(int o = 0; o < h.length(); o++)
			{
				if(Character.isLetter(h.charAt(o)))
				{
					h=(o>0?h.substring(0,o):"")+m.get(h.charAt(o))+h.substring(o+1);
					o+=4;
				}
			}
		}
		return h;
	}
	
	private static String genCode()
	{
		return (rot1.isSelected()?"A":rot5.isSelected()?"B":atbash.isSelected()?"C":baconian.isSelected()?"D":"E");
	}
	
	private static String four(String h)
	{
		String g = "";
		String y = "";
		for(int i = 0; i < h.length(); i++)
		{
			y = Integer.toString(h.charAt(i), 4);
			while(y.length() < 4)
				y = "0"+y;
			g+=y;
		}
		return g;
	}

	private static String dna(String h)
	{
		String g = "";
		for(int i = 0; i < h.length(); i++)
			switch(h.charAt(i))
			{
				case '0':
					g+="G";
					break;
				case '1':
					g+="C";
					break;
				case '2':
					g+="A";
					break;
				case '3':
					g+="T";
					break;
			}
		return g;
	}
	
	private static void setup() throws Exception
	{
		FileWriter fw = new FileWriter(enc);
		PrintWriter pw = new PrintWriter(fw);
		Scanner it = new Scanner(encryptedText);
		pw.println(encryptionCode);
		while(it.hasNext())
		{
			pw.println(it.next());
		}
		it.close();
		pw.close();
		fw.close();
	}
	
	public void start(Stage stage)
	{
		BorderPane g = new BorderPane();
		g.setPadding(new Insets(20,20,20,20));
		
		TextArea area = new TextArea();
		TextField field = new TextField();
		field.setOnAction((event)->
		{
			switch(count++)
			{
				case 0:
					area.setText("What is the name of the output file?");
					orig=new File(path.toString()+"\\"+field.getText()+".dat");
					field.setText("");
					break;
				case 1:
					enc=new File(path.toString()+"\\"+field.getText()+".dat");
					field.setText("");
					try
					{
						encrypt();
					}
					catch(Exception e)
					{
						encryptedText="Error Encrypting";
					}
					finally
					{
						area.setText(encryptedText);
					}
					break;
				case 2:
					System.exit(0);
					break;
				default:
					System.exit(1);
			}
		});
		
		area.setText("What is the name of the file to be encrypted?");
		
		g.setTop(field);
		g.setBottom(area);
		
		rot1 = new CheckBox("Rot1");
		rot5 = new CheckBox("Rot5");
		atbash = new CheckBox("Atbash");
		baconian = new CheckBox("Baconian");
		
		rot1.setOnAction(e->{
			if(rot1.isSelected())
			{
				rot5.setDisable(true);
				atbash.setDisable(true);
				baconian.setDisable(true);
			}
			else
			{
				rot5.setDisable(false);
				atbash.setDisable(false);
				baconian.setDisable(false);
			}
		});
		rot5.setOnAction(e->{
			if(rot5.isSelected())
			{
				rot1.setDisable(true);
				atbash.setDisable(true);
				baconian.setDisable(true);
			}
			else
			{
				rot1.setDisable(false);
				atbash.setDisable(false);
				baconian.setDisable(false);
			}
		});
		atbash.setOnAction(e->{
			if(atbash.isSelected())
			{
				rot5.setDisable(true);
				rot1.setDisable(true);
				baconian.setDisable(true);
			}
			else
			{
				rot5.setDisable(false);
				rot1.setDisable(false);
				baconian.setDisable(false);
			}
		});
		baconian.setOnAction(e->{
			if(baconian.isSelected())
			{
				rot5.setDisable(true);
				atbash.setDisable(true);
				rot1.setDisable(true);
			}
			else
			{
				rot5.setDisable(false);
				atbash.setDisable(false);
				rot1.setDisable(false);
			}
		});
		
		
		
		
		rot1.setPadding(new Insets(0,25,25,0));
		rot5.setPadding(new Insets(0,25,25,0));
		atbash.setPadding(new Insets(0,25,25,0));
		baconian.setPadding(new Insets(0,25,25,0));
		
		FlowPane fp = new FlowPane();
		fp.setPadding(new Insets(50,25,50,0));
		
		fp.getChildren().add(rot1);
		fp.getChildren().add(rot5);
		fp.getChildren().add(atbash);
		fp.getChildren().add(baconian);
		
		
		g.setRight(fp);
		
		Scene scene = new Scene(g, 600, 350, Color.BLACK);
		stage.setScene(scene);
		stage.show();
	}
	
}
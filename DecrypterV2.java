import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DecrypterV2 extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	private static String name = "";
	private static String newName = "";
	private static int count = 0;
	private static String decryptedText = "";

	private synchronized static void decrypt() throws Exception
	{
		String part = ""+path(); 
		part = part.substring(0,part.length()-15);//-13 for when made into jar file
		File path = new File(part+"\\"+name+".dat");
		File newFile = new File(part + "\\"+newName+".dat");
		Scanner read = new Scanner(path);
		ArrayList<String> list = new ArrayList<String>();
		while(read.hasNextLine())
			list.add(read.nextLine());
		read.close();

		list = textDecrypt(binDecrypt(list));
		if(newName.equals("read"))
		{
			ListIterator<String> it = list.listIterator();
			while(it.hasNext())
			{
				decryptedText += it.next() + "\n";
			}
		}
		else
		{
			FileWriter fw = new FileWriter(newFile);
			PrintWriter pw = new PrintWriter(fw);
			ListIterator<String> it = list.listIterator();
			while(it.hasNext())
			{
				pw.println(it.next());
				decryptedText += it.previous() + "\n";it.next();
			}
			pw.close();fw.close();
		}
	}
	private synchronized static ArrayList<String> binDecrypt(ArrayList<String> list)
	{
		ArrayList<String> list2 = new ArrayList<String>();
		ListIterator<String> it = list.listIterator();
		while(it.hasNext())
		{
			char[] dna = it.next().toCharArray();
			String bin = "";
			for(int o = 0; o < dna.length; o++)
			{
				switch(dna[o])
				{
					case 'G':
						bin+="00";
						break;
					case 'C':
						bin+="01";
						break;
					case 'A':
						bin+="10";
						break;
					case 'T':
						bin+="11";
				}
			}
			list2.add(bin);
		}
		return list2;
	}
	private synchronized static ArrayList<String> textDecrypt(ArrayList<String> list)
	{
		ArrayList<String> list2 = new ArrayList<String>();
		ListIterator<String> it = list.listIterator();
		while(it.hasNext())
		{
			String bin = it.next();
			String text = "";
			while(bin.length() > 0)
			{
				String part = bin.substring(0, 8);
				part = part.substring(part.indexOf('1'));
				text += (char)Integer.parseInt(part, 2);
				bin = bin.substring(8);
			}
			list2.add(text);
		}
		return list2;
	}
	
	private static File path() throws Exception
	{
		return new File(DecrypterV2.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
	}
	
	@Override
	public void start(Stage stage) throws Exception
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
					name=field.getText();
					field.setText("");
					break;
				case 1:
					newName=field.getText();
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

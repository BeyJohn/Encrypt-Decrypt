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
public class EncrypterV2 extends Application
{
	private static String name = "";
	private static String newName = "";
	private static int count = 0;
	private static String encryptedText = "";
	
	public static void main(String[] args) throws Exception
	{
		launch(args);
	}

	private synchronized static void encrypt() throws Exception
	{
		long startTime = System.currentTimeMillis();
		String part = ""+path();
		part = part.substring(0,part.length()-15);
		File path = new File(part+"\\"+name+".dat");
		File newFile = new File(part + "\\"+newName+".dat");
		Scanner read = new Scanner(path);
		ArrayList<String> list = new ArrayList<String>();
		while(read.hasNextLine())
			list.add(read.nextLine());
		read.close();
		list = dnaEncrypt(binEncrypt(list));
		FileWriter fw = new FileWriter(newFile);
		PrintWriter pw = new PrintWriter(fw);
		ListIterator<String> it = list.listIterator();
		while(it.hasNext())
		{
			pw.println(it.next());
			encryptedText += it.previous() + "\n";it.next();
		}
		long endTime = System.currentTimeMillis();
		pw.println();
		pw.println(endTime-startTime);
		pw.close();
		fw.close();
		
	}
	private static ArrayList<String> binEncrypt(ArrayList<String> list)
	{
		ArrayList<String> list2 = new ArrayList<String>();
		ListIterator<String> it = list.listIterator();
		while(it.hasNext())
		{
			char[] line = it.next().toCharArray();
			String bin = "";
			for(int o = 0; o < line.length; o++)
			{
				if(Integer.toBinaryString(line[o]).length() < 8)
				{
					switch(8-Integer.toBinaryString(line[o]).length())
					{
						case 0:
							bin += Integer.toBinaryString(line[o]);
							break;
						case 1:
							bin += "0"+Integer.toBinaryString((int)line[o]);
							break;
						case 2:
							bin += "00"+Integer.toBinaryString(line[o]);
							break;
						case 3:
							bin += "000"+Integer.toBinaryString(line[o]);
							break;
						case 4:
							bin += "0000"+Integer.toBinaryString(line[o]);
							break;
					}
				}
			}
			list2.add(bin);
		}
		return list2;
	}
	private static ArrayList<String> dnaEncrypt(ArrayList<String> list)
	{
		ArrayList<String> list2 = new ArrayList<String>();
		ListIterator<String> it = list.listIterator();
		while(it.hasNext())
		{
			String bin = it.next();
			String dna = "";
			while(bin.length() > 0)
			{
				switch(bin.substring(0, 2))
				{
					case "00":
						dna+="G";
						break;
					case "01":
						dna+="C";
						break;
					case "10":
						dna+="A";
						break;
					case "11":
						dna+="T";
				}
				bin = bin.substring(2);
			}
			list2.add(dna);
		}
		return list2;
	}
	
	private static File path() throws Exception
	{
		return new File(EncrypterV2.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
	}
	
	@Override
	public void start(Stage stage) throws Exception
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
					name=field.getText();
					field.setText("");
					break;
				case 1:
					newName=field.getText();
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
		
		Scene scene = new Scene(g, 600, 350, Color.BLACK);
		stage.setScene(scene);
		stage.show();
	}
}

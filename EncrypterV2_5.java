import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EncrypterV2_5 extends Application 
{
	private static String name = "";
	private static String newName = "";
	private static int count = 0;
	private static String encryptedText = "";
	
	private static long startTime;
	private static long endTime;
	
	private static void encrypt() throws Exception
	{
		startTime = System.currentTimeMillis();
		Scanner a = new Scanner(new File((path()+"").substring(0,(path()+"").length()-17)+name+".dat"));
		while(a.hasNext())
			encryptedText += dna(four(a.nextLine()))+"\n";
		a.close();
		setup();
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
		FileWriter fw = new FileWriter(new File((path()+"").substring(0,(path()+"").length()-17)    +newName+".dat"));
		PrintWriter pw = new PrintWriter(fw);
		Scanner it = new Scanner(encryptedText);
		it.useDelimiter("\n");
		while(it.hasNext())
		{
			pw.println(it.next());
		}
		it.close();
		endTime = System.currentTimeMillis();
		pw.println();
		pw.println(endTime-startTime);
		pw.close();
		fw.close();
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

	private static File path() throws Exception
	{
		return new File(EncrypterV2_5.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class Encrypter extends JFrame
{
	private static final long serialVersionUID = 737918159573160634L;
	private JPanel contentPane;
	private JTextField txtFilename;
	private static JTextPane textPane;
	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Encrypter frame = new Encrypter();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
	}
	private static String name = "";
	private static String newName = "";
	private static int count = 0;
	private static String encryptedText = "";
	private synchronized static void encrypt() throws Exception
	{
		String part = ""+path();
		part = part.substring(0,part.length()-13);//-13 for when make into jar file
		File path = new File(part+"\\"+name+".dat");
		File newFile = new File(part + "\\"+newName+".dat");
		Scanner read = new Scanner(path);
		ArrayList<String> list = new ArrayList<String>();
		while(read.hasNextLine())
			list.add(read.nextLine());
		read.close();
		//TODO encrypt
		list = dnaEncrypt(binEncrypt(list));
		FileWriter fw = new FileWriter(newFile);
		PrintWriter pw = new PrintWriter(fw);
		ListIterator<String> it = list.listIterator();
		while(it.hasNext())
		{
			pw.println(it.next());
			encryptedText += it.previous() + "\n";it.next();
		}
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
	/**
	 * Find folder 
	 */
	private static File path() throws Exception
	{
		return new File(Encrypter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
	}
	/**
	 * Create the frame.
	 */
	public Encrypter() throws Exception
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		txtFilename = new JTextField();
		txtFilename.setHorizontalAlignment(SwingConstants.CENTER);
		
		contentPane.add(txtFilename, BorderLayout.NORTH);
		txtFilename.setColumns(10);
		
		textPane = new JTextPane();
		contentPane.add(textPane, BorderLayout.CENTER);
		textPane.setText("Enter name of file for encryption.");
		JButton btnContinue = new JButton("Continue");
		contentPane.add(btnContinue, BorderLayout.WEST);
		btnContinue.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				count++;
				switch(count)
				{
					case 1:
						name = txtFilename.getText();
						textPane.setText("Enter file name after encryption.");
						txtFilename.grabFocus();
						break;
					case 2:
						newName = txtFilename.getText();
						textPane.setText("Encrypting now.");
						try{encrypt();} catch (Exception e){}
						txtFilename.grabFocus();
						break;
					case 3:
						textPane.setText(encryptedText);
						txtFilename.grabFocus();
						break;
					default:
						System.exit(0);
				}
			}
			
		});
	}
}

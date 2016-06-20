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


public class Decrypter extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFilename;
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
					Decrypter frame = new Decrypter();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
	}
	private static File path() throws Exception
	{
		return new File(Decrypter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
	}
	private static String name = "";
	private static String newName = "";
	private static int count = 0;
	private static String decryptedText = "";
	private synchronized static void decrypt() throws Exception
	{
		String part = ""+path(); 
		part = part.substring(0,part.length()-13);//-13 for when made into jar file
		File path = new File(part+"\\"+name+".dat");
		File newFile = new File(part + "\\"+newName+".dat");
		Scanner read = new Scanner(path);
		ArrayList<String> list = new ArrayList<String>();
		while(read.hasNextLine())
			list.add(read.nextLine());
		read.close();

		list = textDecrypt(binDecrypt(list));
		FileWriter fw = new FileWriter(newFile);
		PrintWriter pw = new PrintWriter(fw);
		ListIterator<String> it = list.listIterator();
		while(it.hasNext())
		{
			if(!newName.equals("read"))
				pw.println(it.next());
			else
				it.next();
			decryptedText += it.previous() + "\n";it.next();
		}
		if(!newName.equals("read"))
		{
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
	/**
	 * Create the frame.
	 */
	public Decrypter()
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
		
		final JTextPane textPane = new JTextPane();
		contentPane.add(textPane, BorderLayout.CENTER);
		textPane.setText("Enter name of file for decryption.");
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
						textPane.setText("Enter file name after decryption.");
						txtFilename.grabFocus();
						break;
					case 2:
						newName = txtFilename.getText();
						try{decrypt();} catch (Exception e){}
						textPane.setText("Decrypting now.");
						txtFilename.grabFocus();
						break;
					case 3:
						textPane.setText(decryptedText);
						txtFilename.grabFocus();
						break;
					default:
						System.exit(0);
				}
			}
			
		});
	}
}

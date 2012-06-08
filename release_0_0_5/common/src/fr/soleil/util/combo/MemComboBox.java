package fr.soleil.util.combo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 * This class allows to create a memory combobox 
 * @author MARECHAL
 *
 */
public class MemComboBox extends JComboBox
{
	public static final int MAX_MEM_LEN = 30; //max memorized items

	/**
	 * Default constructor
	 *
	 */
	public MemComboBox()
	{
		super();
		setEditable(true);
	}

	/**
	 * Add element in the combobox
	 * @param item
	 */
	public void add(String item)
	{
		removeItem(item);
		insertItemAt(item, 0);
		setSelectedItem(item);
		if (getItemCount() > MAX_MEM_LEN)
			removeItemAt(getItemCount()-1);
	}

	/**
	 * Load items in combobox read from config file
	 * @param String file's path
	 */
	public void load(String fName)
	{	
		if(new File(fName).exists())
		{
			if(new File(fName).isFile())
			{
				BufferedReader reader = null;
			    String strLine;
		
			    try
			    {
			    	reader = new BufferedReader(new FileReader(fName));
			    	try 
				    {
						while ((strLine = reader.readLine()) != null)
						{
							if(!strLine.trim().equals(""))
								add(strLine);
						}
					} 
				    catch (IOException e) 
				    {
						e.printStackTrace();
					}
				    try 
				    {
				    	reader.close();
					} 
				    catch (IOException e)
				    {
						e.printStackTrace();
					}
			    }
			    catch(FileNotFoundException exc)
			    {
			    	exc.printStackTrace();
			    } 
			}
		}
	}

	public void save(String fName)
	{
		File confFile = new File(fName);  
		if(! confFile.exists())
		{
			try 
			{
				confFile.createNewFile();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}
		else 
		{
			if(!confFile.isFile())
			{
				try 
				{
					confFile.createNewFile();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	  //save in the specified file
	  //save in the default file : BoleroLogs.txt
	  BufferedWriter writer = null;
	  try 
	  {
		  writer = new BufferedWriter(new FileWriter(fName, true));
	  } 
	  catch (IOException e) 
	  {
		e.printStackTrace();
	  }
	  try 
	  {
		  writer.newLine();
		  writer.append(this.getSelectedItem().toString());
		  writer.flush();
		  writer.close();
	  } 
	  catch (IOException e) 
	  {
		e.printStackTrace();
	  } 	  
	}
}

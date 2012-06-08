package fr.soleil.util.textarea;

import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class TextAreaRenderer extends JScrollPane implements TableCellRenderer
{
	JTextArea textarea;
	
	public TextAreaRenderer()
	{
		textarea = new JTextArea();
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		getViewport().add(textarea);
		//hide scroll bar
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
	boolean isSelected, boolean hasFocus,
	int row, int column)
	{	
		textarea.setText((String) value);
		textarea.setCaretPosition(0);
		
		//set adapted size to this cell
		int height_wanted = (int)getPreferredSize().getHeight();
	    if (height_wanted != table.getRowHeight(row))
	        table.setRowHeight(row, height_wanted);
		return this;
	}
}


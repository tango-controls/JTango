package fr.soleil.util.textarea;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class TextAreaEditor extends DefaultCellEditor
{
	protected JScrollPane scrollpane;
	protected JTextArea textarea;
	
	public TextAreaEditor()
	{
		super(new JCheckBox());
		scrollpane = new JScrollPane();
		textarea = new JTextArea();
		textarea.setBorder(BorderFactory.createEmptyBorder());
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		scrollpane.getViewport().add(textarea);
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value,
	boolean isSelected, int row, int column)
	{
		textarea.setText((String) value);
		return scrollpane;
	}
	
	public Object getCellEditorValue()
	{
		return textarea.getText();
	}
}
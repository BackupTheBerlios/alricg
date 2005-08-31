/*
 * Created on 04.04.2005 / 15:37:59
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.test.prototypeX;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class JButtonTableExample extends JFrame {

	public JButtonTableExample() {
		super("JButtonTable Example");

		DefaultTableModel dm = new DefaultTableModel() {
			public void setValueAt(Object aValue, int rowIndex, int columnIndex)  {
				super.setValueAt(aValue, rowIndex, columnIndex);
				System.out.println(aValue.toString());
			}
			
		};
		dm.setDataVector(new Object[][] { 
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" }
				}, 
				new Object[] { "Button", "String", "Bool", "String" });

		JTable table = new JTable(dm) {
	        
			public Class getColumnClass(int c) {
	            return getValueAt(0, c).getClass();
	        }
		};
		//dm.addTableModelListener()
		
		JButton button = new JButton("+");
		table.getColumn("Button").setCellRenderer(new ButtonRenderer(new JButton("+")));
		table.getColumn("Button").setCellEditor(
				new ButtonEditor(button));
		
		
		JScrollPane scroll = new JScrollPane(table);
		getContentPane().add(scroll);
		setSize(400, 100);
		setVisible(true);
	}

	public static void main(String[] args) {
		JButtonTableExample frame = new JButtonTableExample();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}

class ButtonRenderer implements TableCellRenderer {
	JButton button;
	
	public ButtonRenderer(JButton button) {
		this.button = button;
		
		button.setOpaque(true);
		button.setMargin(new Insets(1,1,1,1));
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(UIManager.getColor("Button.background"));
		}
		
		//button.setText((value == null) ? "" : value.toString());
		
		return button;
	}
}

class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
	protected JButton button;

	public ButtonEditor(JButton button) {
		this.button = button;
		 button.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		fireEditingStopped();
		 	}
		 });
		 System.out.println("Listener!");
		 /*button.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		System.out.println("lala..!");
		 	}
		 });*/
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
						boolean isSelected, int row, int column) {
		
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(table.getBackground());
		}

		return button;
	}

	public Object getCellEditorValue() {
		return new String(button.getText());
	}

	public boolean stopCellEditing() {
		return super.stopCellEditing();
	}

	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}

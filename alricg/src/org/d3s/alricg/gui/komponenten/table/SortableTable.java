/*
 * Created on 04.04.2005 / 10:04:35
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 */
package org.d3s.alricg.gui.komponenten.table;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.d3s.alricg.gui.komponenten.MultiIcon;
import org.d3s.alricg.gui.views.SpaltenSchema;

import com.sun.java.swing.plaf.motif.MotifGraphicsUtils;

/**
 * <u>Beschreibung:</u><br>
 * Diese JTable Arbeitet mit dem AbstractSortableTableModel zusammen.
 * Es Ermöglicht 
 * - Sortierung nach Spalten
 * - Anzeigen von ToolTips für einzelne Zellen und Tablellenköpfe
 * - Leichter Einbau von Buttons als Tabellen Elemente
 * 
 * @author V. Strelow
 */
public class SortableTable extends JTable {
	SortableTableModelInterface model;
	
	// Pfeile für die Sortierung
    private final ImageIcon UP_ICON = new ImageIcon(MotifGraphicsUtils.class
            .getResource("icons/ScrollUpArrow.gif"));
    private ImageIcon DOWN_ICON = new ImageIcon(MotifGraphicsUtils.class
            .getResource("icons/ScrollDownArrow.gif"));
    
    
    /* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.JTable#setModel(javax.swing.table.TableModel)
	 */
	public void setModel(SortableTableModelInterface model) {
		super.setModel(model);
		this.model = model;
		init();
	}
    
    private void init() {
    	
    	// Anzeige der Pfeile für die Sortierung der Spalten
    	this.getTableHeader().setDefaultRenderer(
    		new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(
                        JTable table, Object value, boolean isSelected,
                        boolean hasFocus, int row, int column) {

                    JTableHeader header = table.getTableHeader();
                    setForeground(header.getForeground());
                    setBackground(header.getBackground());
                    setFont(header.getFont());

                    setText(value == null ? "" : value.toString());
                    setBorder(UIManager.getBorder("TableHeader.cellBorder"));

                    // Wenn die Spalte nicht sortierbar ist, wird auch 
                    // kein Pfeil angezeigt
                    if (!model.isSortable(column)) {
                    	setIcon(null);
                    	return this;
                    }
                    
                    setHorizontalAlignment(SwingConstants.CENTER);
                    setHorizontalTextPosition(SwingConstants.LEFT);
                    
                    // Setzen des Pfeils aabhängig von der Sortierung
                    if (model.isSortColumnDesc(column)) {
                        setIcon(UP_ICON);
                    } else {
                        setIcon(DOWN_ICON);
                    }

                    return this;
                }
            }
        );
        
        // Listener für die Sortierung der Spalten
        this.getTableHeader().addMouseListener(
        	new MouseAdapter() {
	            public void mousePressed(MouseEvent evt) {
	            	int column = columnAtPoint(evt.getPoint());
	            	column = convertColumnIndexToModel(column);
	            	
	            	// Sortieren wenn sortierbar
	            	if (model.isSortable(column)) {
	            		model.sortTableByColumn(column);
	            		updateUI();
	            	}
	            }
        	}
        );
    }

    /* (non-Javadoc) Methode überschrieben.
     * Ermöglicht ToolTips für jede Zelle Extra
     * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
     */
    public String getToolTipText(MouseEvent e) {
        java.awt.Point p = e.getPoint();
        
        // Bestimmen der Zeile/ Spalte um umrechnen auf das Modell
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        int realColumnIndex = convertColumnIndexToModel(colIndex);

        return model.getToolTip(rowIndex, colIndex);
    }
    

    /* (non-Javadoc) Methode überschrieben
     * Ermöglicht ToolTips für die TableHeader
     * @see javax.swing.JTable#createDefaultTableHeader()
     */
    protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {
            public String getToolTipText(MouseEvent e) {
            	
            	// Bestimmen der Spalte
                int index = columnModel.getColumnIndexAtX(e.getPoint().x);
                index = convertColumnIndexToModel(index);
                
                return model.getHeaderToolTip(index);
            }
        };
    }
    
    
    /**
     * Hiermit wird eine Spalte so eingestellt, das sie einen Button anzeigt.
     * @param column Die Spalte die per Button bedient werden soll
     * @param buttonText Der text auf dem Button
     */
    public void setColumnButton(int column, String buttonText) {
		
    	// Der Renderer wird mit einem neuen Button erstellt, der 
    	// den selben Text trägt wie der gegebende Button
		this.getColumn(getColumnName(column)).setCellRenderer(
				new ButtonRenderer(new JButton(buttonText)));
		// Der Editor wird mit dem ORIGINAL Button erstellt
		this.getColumn(getColumnName(column)).setCellEditor(
				new ButtonEditor(new JButton(buttonText)));
    }
    
    /**
     * Hiermit wird eine Spalte so eingestellt, das sie ein oder mehrer Icons 
     * anzeigt. Dafür muß die Spalte "column" 
     * @param column Die Spalte die ein Bild anzeigen soll
     * @param buttonText Der text auf dem Button
     */
    public void setColumnImage(int column) {
    	this.getColumn(getColumnName(column)).setCellRenderer(new ImageRenderer());
    }
    
    /**
     * Liefert das benutze Schema zum Anzeigen der Elemente
     * @return Das Benutze Schema für die Elemente
     */
    public SpaltenSchema getViewSchema() {
    	return model.getSpaltenSchema();
    } 
    
}

// *****************************************************************************

/**
 * <u>Beschreibung:</u><br> 
 *	Für die Anzeige eines Buttons in einer Tabelle.
 * @author V. Strelow
 */
class ImageRenderer extends DefaultTableCellRenderer {
	protected JLabel label;
	protected MultiIcon multiIcon;
	
	/**
	 * Konstruktor
	 */
	public ImageRenderer() {
		label = new JLabel();
		multiIcon = new MultiIcon();
		
		label.setOpaque(true);
		label.setIcon(multiIcon);
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
		
		// Prüfen, ob dort überhaupt ein Bild hin soll
		if (value instanceof Icon[]) {
			multiIcon.replaceAll((Icon[]) value);
			// Wenn nicht wird ein Label eingefügt
			if (isSelected) {
				label.setForeground(table.getSelectionForeground());
				label.setBackground(table.getSelectionBackground());
			} else {
				label.setForeground(table.getForeground());
				label.setBackground(table.getBackground());
			}
		} else {
			return super.getTableCellRendererComponent(table, value, 
									isSelected, hasFocus, row, column);
		}
		
		return label;
	}
}


/**
 * <u>Beschreibung:</u><br> 
 *	Für die Anzeige eines Buttons in einer Tabelle.
 * @author V. Strelow
 */
class ButtonRenderer implements TableCellRenderer {
	protected JButton button;
	protected JLabel label;
	
	/**
	 * Konstruktor
	 */
	public ButtonRenderer(JButton button) {
		this.button = button;
		this.label = new JLabel();
		
		label.setOpaque(true);
		button.setOpaque(true);
		button.setMargin(new Insets(1,1,1,1));
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {

		// Prüfen, ob dort überhaupt ein Button hin soll
		if (!value.equals(SpaltenSchema.buttonValue)) {
			// Wenn nicht wird ein Label eingefügt
			if (isSelected) {
				label.setForeground(table.getSelectionForeground());
				label.setBackground(table.getSelectionBackground());
			} else {
				label.setForeground(table.getForeground());
				label.setBackground(table.getBackground());
			}
			return label;
		}
		
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(UIManager.getColor("Button.background"));
		}
		
		return button;
	}
}
/**
 * 
 * <u>Beschreibung:</u><br> 
 * Für die Anzeige eines Buttons in einer Tabelle und das editieren der Tabelle
 * mit dem Button.
 * @author V. Strelow
 */
class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
	protected JButton button;
	protected JLabel label;
	
	/**
	 * Konstruktor
	 */
	public ButtonEditor(JButton button) {
		this.button = button;
		this.label = new JLabel();
		
		label.setOpaque(true);
		button.setOpaque(true);
		button.setMargin(new Insets(1,1,1,1));
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value,
						boolean isSelected, int row, int column) {
		
		// Prüfen, ob dort überhaupt ein Button hin soll
		if (!value.equals(SpaltenSchema.buttonValue)) {
			// Wenn nicht wird ein Label eingefügt
			if (isSelected) {
				label.setForeground(table.getSelectionForeground());
				label.setBackground(table.getSelectionBackground());
			} else {
				label.setForeground(table.getForeground());
				label.setBackground(table.getBackground());
			}
			return label;
		}
		
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(table.getBackground());
		}

		return button;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	public Object getCellEditorValue() {
		return new String(button.getText());
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.CellEditor#stopCellEditing()
	 */
	public boolean stopCellEditing() {
		return super.stopCellEditing();
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.AbstractCellEditor#fireEditingStopped()
	 */
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}


/*
 * Created on 17.03.2005 / 16:48:16
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.test;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.d3s.alricg.gui.komponenten.table.AbstractTreeTableModel;
import org.d3s.alricg.gui.komponenten.table.TreeTableModel;

/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class TestTreeTableModel extends AbstractTreeTableModel {
	DefaultMutableTreeNode root;
	String[] columns = {"ein", "zwei", "drei"};
	Class[]  cTypes = {TreeTableModel.class, String.class, String.class, String.class};
    
	public TestTreeTableModel() {
    	super (new DefaultMutableTreeNode("root"));
    	
    	((DefaultMutableTreeNode) this.getRoot()).add(new DefaultMutableTreeNode("1"));
    	((DefaultMutableTreeNode) this.getRoot()).add(new DefaultMutableTreeNode("2"));
    	((DefaultMutableTreeNode) this.getRoot()).add(new DefaultMutableTreeNode("3"));
    	((DefaultMutableTreeNode) ((DefaultMutableTreeNode) this.getRoot()).getChildAt(0))
    		.add(new DefaultMutableTreeNode("1-1"));
    
    }
    
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.Test.TreeTable.TreeTableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columns.length;
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.Test.TreeTable.TreeTableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		return columns[column];
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.Test.TreeTable.TreeTableModel#getValueAt(java.lang.Object, int)
	 */
	public Object getValueAt(Object node, int column) {
		if (column == 0) {
			return node;
		} else if (column == 1) {
			return ((DefaultMutableTreeNode) node).getUserObject();
		} else {
			return "loo";
		}
		
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.Test.TreeTable.TreeTableModel#isCellEditable(java.lang.Object, int)
	 */
	public boolean isCellEditable(Object node, int column) {
		return true;
	}
	
	/* (non-Javadoc) Methode überschrieben
	 * @see org.d3s.alricg.Test.TreeTable.TreeTableModel#setValueAt(java.lang.Object, java.lang.Object, int)
	 */
	public void setValueAt(Object aValue, Object node, int column) {
		// TODO Auto-generated method stub
		super.setValueAt(aValue, node, column);
	}
	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
	 */
	public Object getChild(Object node, int i) {
		return ((TreeNode)node).getChildAt(i);
	}

	/* (non-Javadoc) Methode überschrieben
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 */
	public int getChildCount(Object node) {
		return ((TreeNode)node).getChildCount();
	}
	
    public Class getColumnClass(int column) {
    	return cTypes[column];
    }

}

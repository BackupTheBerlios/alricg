/*
 * Created on 04.04.2005 / 17:35:01
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui.komponenten.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

import org.d3s.alricg.charKomponenten.CharElement;
import org.d3s.alricg.charKomponenten.Herkunft;
import org.d3s.alricg.charKomponenten.HerkunftVariante;
import org.d3s.alricg.gui.views.ViewSchema;

/**
 * <u>Beschreibung:</u><br>
 * Dient als Model zur Darstellung von Daten in einer TreeTable (SortableTreeTable). 
 * In dieser Klasse werden alle Grundfunktionen daf�r gehalten, um auf die Daten zu 
 * zugreifen und diese zu verwalten.
 * Die spezifischen Funktionen, die von der Art der Daten (Talent, Zauber, Rasse, usw.)
 * abh�ngen, werden in einem Schema gehalten. W�hrend dieses Modell speziell f�r die
 * Darstellung in einer TreeTable gemacht ist, ist das Schema unabh�ngig von der Art der
 * Darstellung und kann somit auch f�r die JTable verwendet werden.
 *
 * @author V. Strelow
 */
public class SortableTreeModel<E> extends AbstractTreeTableModel {
	private final Object[] columns; // Die Spalten-Titel
	private final ViewSchema schema; // Spezifische Methoden f�r Typ <E>
	private final DefaultMutableTreeNode root; // Wurzel-Knoten
	private boolean[] lastAscSorted; // Sortierrichtung
	private NodeComparator nodeComp; // Comparator f�r Nodes
	
	public SortableTreeModel(ViewSchema schema, Object[] columns, String rootText) {
		super(new DefaultMutableTreeNode(rootText));
    	
		this.columns = columns;
		this.schema = schema;
		this.root = (DefaultMutableTreeNode) this.getRoot();
		this.lastAscSorted = new boolean[columns.length];
		this.nodeComp = new NodeComparator();
		
		Arrays.fill(lastAscSorted, false); // Damit �berall ein Wert steht
	}
	
	/**
	 * Wird genutzt von dem TreeTableModelAdapter
	 * @return Das benutze Schema dieses Modells
	 */
	public ViewSchema getSchema() {
		return schema;
	}
	
	/**
	 * Ordnet alle �bergebenen Elemente nach den Ordnern und f�gt sie als Nodes
	 * zu dem �bergebenen Node hinzu.
	 * WICHTIG: Diese Methode benutzt "getOrdinalFromElement(CharElement element)" 
	 * und "getEnums()"!Die Methoden m�ssen korrekt und passend implementiert sein
	 * 
	 * @param elemListe Diese Elemente werden geordnete als Nodes sortiert hinzugef�gt  
	 * @param nodeToAdd Zu diesem Node werden die neuen Nodes hinzugef�gt
	 */
	protected void ordneNachOrdnern(ArrayList<E> elemListe,
										DefaultMutableTreeNode nodeToAdd) 
	{
		Object[] ordner = schema.getSortOrdner();
		ArrayList<DefaultMutableTreeNode>[] tmpArray;
		DefaultMutableTreeNode tmpNode;
		int[] tmpIntArray;
		
		// Gibt es �berhaupt Elemente zum einordnen?
		if (elemListe.size() == 0) {
			return; 
		}
		
		// Gibt es �berhaupt "Ordner" zu einordnen?
		if (ordner == null) {
			ordner = new Object[0];
			tmpArray = new ArrayList[1];
			tmpArray[0] = new ArrayList<DefaultMutableTreeNode>(); 
		} else {
			tmpArray = new ArrayList[ordner.length];
		}
		
// 		Array von ArrayListen erzeugen, so viele wie es Ordner gibt
		for (int i = 0; i < ordner.length; i++) {
			tmpArray[i] = new ArrayList<DefaultMutableTreeNode>();
		}
		
//		Jedes Element der list in ein oder mehrer Arrays einordenen,
//		passend zur Ordner
		for (int i = 0; i < elemListe.size(); i++) {
			tmpIntArray = schema.getOrdinalFromElement(elemListe.get(i));
			
			for (int ii = 0; ii < tmpIntArray.length; ii++) {
				tmpArray[tmpIntArray[ii]].add(new DefaultMutableTreeNode(elemListe.get(i)));
			}
		}
		
//		F�r jeden Ordner einen Node anlegen, passende Elemente hinzuf�gen 
		for (int i = 0; i < ordner.length; i++) {
			
//			 Fall dieser ordner keine Elemente enthalten w�rde, wird er nicht angelegt
			if (tmpArray[i].size() == 0) {
				continue; 
			}
			
			// "Ordner" erstellen
			tmpNode = new DefaultMutableTreeNode(ordner[i].toString());
			nodeToAdd.add(tmpNode);
			
			// Sortiert dieses Array nach Sammelbegriffen
			ordneNachSammelbegriff(tmpArray[i]);
			
			// Knoten hinzuf�gen
			for (int ii = 0; ii < tmpArray[i].size(); ii++) {
				tmpNode.add(tmpArray[i].get(ii));
			}
		}
	}
	
	/**
	 * Sucht alle enthaltenen Sammelbegriffe heraus sortiert die Knoten
	 * entsprechend. Sind die Elemente nicht f�r Sammelbegriffe geeignet,
	 * so passiert nichts.
	 * @param array Diese array wird nach Sammelbegriffen sortiert
	 */
	private void ordneNachSammelbegriff(ArrayList<DefaultMutableTreeNode> array) {
		HashMap<String, DefaultMutableTreeNode> hash;
		String tmpString;
		DefaultMutableTreeNode tmpNode;
		
		// Guard ob diese Ordnung m�glich ist (nur bei Herkunft)
		if (!schema.hasSammelbegriff()) {
			return;
		}
		
		hash = new HashMap<String, DefaultMutableTreeNode>();
		
		// Erster Durchlauf: Alle Sammelbegriffe Suchen und 
		// zum Hash und Array hinzuf�gen
		for (int i = 0; i < array.size(); i++) {
			if (((CharElement) array.get(i).getUserObject())
					.hasSammelBegriff()) 
			{
				tmpString = ((CharElement) array.get(i)
									.getUserObject())
									.getSammelBegriff();
				if (!hash.containsKey(tmpString)) {
					// Hinzuf�gen zum Hash & array
					tmpNode = new DefaultMutableTreeNode(tmpString);
					hash.put(tmpString, tmpNode);
					array.add(0, tmpNode); 
					i++; // Da ein Element hinzugef�gt wurde
				}
			}
		}
		
		/* Zweiter Durchlauf: Alle Elemente mit Sammelbegriffen
		 * zuordnen und aus dem ArrayL�schen.
		 * WICHTIG: Erst bei "hash.size()" anfangen da sonst die
		 * Sammelbegriffe selbst mit �berpr�ft werden
		 */ 
		for (int i = hash.size(); i < array.size(); i++) {
			if (((CharElement) array.get(i).getUserObject())
					.hasSammelBegriff()) 
			{
				tmpString = ((CharElement) array.get(i)
						.getUserObject())
						.getSammelBegriff();
				hash.get(tmpString).add(array.get(i));
				array.remove(i--);
			}
		}
	}
	
	/**
	 * Durchsucht alle Elemente der Liste nach Varianten und ordent diese
	 * entsprechend (Varianten werden als Knoten an den "Eltern-Knoten"
	 * der Varianten gehangen).
	 * WICHTIG: Nach Aufruf dieser Methode sind alle Elemente der liste 
	 * als Teil des Baumes an den �bergebenen Node gehangen. Daher ist ein
	 * aufruf auch sinnvoll, wenn keine Varianten vorhanden sind
	 *  
	 * @param list Die Liste mit Elementen
	 * @param nodeToAdd Der Node, der als root dient
	 *
	private ArrayList<DefaultMutableTreeNode> ordneNachVariante(ArrayList<E> list) {
		Herkunft tmpHerk;
		HashMap<Herkunft, DefaultMutableTreeNode> tmpHash;
		DefaultMutableTreeNode tmpNode;
		ArrayList<DefaultMutableTreeNode> array;
		
		array = new ArrayList<DefaultMutableTreeNode>(list.size());

		// Guard ob diese Ordnung m�glich ist (nur bei Herkunft)
		if (!schema.isHerkunft()) {
			for (int i = 0; i < list.size(); i++) {
				array.add(new DefaultMutableTreeNode(list.get(i)));
			}
			return array;
		}
		
		tmpHash = new HashMap<Herkunft, DefaultMutableTreeNode>();
		
		// Erster Durchlauf: Alle Varianten finden und ins Hash schreiben
		for (int i = 0; i < list.size(); i++) {
			tmpHerk = (Herkunft) list.get(i);
			if (tmpHerk instanceof HerkunftVariante) {
				// Hat eine Variante, wird an diesen Knoten hinzugef�gt
				if (!tmpHash.containsKey(tmpHerk.getVarianteVon())) {
					// VarianteVon ins Hash eintragen & zu node hinzuf�gen
					tmpNode = new DefaultMutableTreeNode(tmpHerk.getVarianteVon());
					tmpHash.put(tmpHerk.getVarianteVon(), tmpNode);
					array.add(tmpNode);
				}
			}
		}
		
		// Zweiter Durchlauf: Alle Elemente einordenen
		for (int i = 0; i < list.size(); i++) {
			tmpHerk = (Herkunft) list.get(i);
			
			if (tmpHerk.isVariante()) {
				// Ist eine Variante und wird zu dieser Hinzugef�gt
				tmpNode = tmpHash.get(tmpHerk.getVarianteVon());
				tmpNode.add(new DefaultMutableTreeNode(tmpHerk));
			} else { 
				// Ist keine Variante, wird daher direkt hinzugef�gt
				tmpNode = new DefaultMutableTreeNode(tmpHerk);
				array.add(new DefaultMutableTreeNode(tmpHerk));
			}
		}
		
		return array;
	}
	*/
	
	/**
	 * Setzt die Elemente des DatenModells
	 * @param elemListe Liste von allem Elementen die die Tabelle anzeigen soll
	 */
	public void setData(ArrayList<E> elemListe) { 
		//ArrayList<DefaultMutableTreeNode> array;
		
		// array = ordneNachVariante(elemListe);
		
		
		/*
		array = new ArrayList<DefaultMutableTreeNode>(elemListe.size());

		for (int i = 0; i < elemListe.size(); i++) {
			array.add(new DefaultMutableTreeNode(elemListe.get(i)));
		}
		*/
		
		/* TestDaten
		System.out.println(array.get(1).getUserObject().toString());
		System.out.println(array.get(2).getUserObject().toString());
		System.out.println(array.get(3).getUserObject().toString());
		
		array.get(1).add(array.get(2));
		array.get(1).add(array.get(3));
		array.remove(3);
		array.remove(2); */
		
		ordneNachOrdnern(elemListe, root);
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int col) {
		return columns[col].toString();
	}
	
	/**
	 * Jede Spalte ist in einem SortableTreeTableModel ist mit einer Enum 
	 * gegenzeichnet 
	 * @param col Die Spalte zu der die Enum geliefert wird
	 * @return Liefert die Enum zu einer Spalte
	 */
	public Object getColumnObject(int col) {
		return columns[col];
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.Test.treeTable.TreeTableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columns.length;
	}


	/**
	 * Pr�ft ob eine Spalte sortierbar ist.
	 * Wird benutzt um Listener anzumelden und Pfeile einzublenden.
	 * @param column Die Spalte die �berpr�ft werden soll
	 * @return true: Die Spalte mit der Nummer "column" ist sortierbar, sonst false
	 */
	public boolean isSortable(int column) {
		return schema.isSortable(columns[column]);
	}

	/**
	 * Sortiert den gesamten TreeTable nach der �bergebenen Spalte
	 * @param column Die Spalte nach der Sortiert werden soll
	 */
	public void sortTableByColumn(int column) {
		
		// Den "echten" Comparator setzen
		nodeComp.setRealComparator(schema.getComparator(columns[column]));
		
		sortHelp ((DefaultMutableTreeNode) root, 
				nodeComp, 
				lastAscSorted[column], 
				(column == 0));

		// Somit wird beim zweiten klick die Reihenfolge vertauscht
		if (lastAscSorted[column]) {
			lastAscSorted[column] = false;
		} else {
			lastAscSorted[column] = true;
		}
	}

	/**
	 * Sortier alle Kinder (und Kindeskinder usw.) des Knotens "node" mit 
	 * dem comparator "comp". 
	 * @param node Der Knoten dessen Kinder Sortiert werden sollen
	 * @param comp Der Comparator mit dem die Sortierung erfolgt
	 * @param reverse true: Die Sortierung wird "umgedreht", ansonsten false
	 * @param firstColum true: Es wird die erste Spalte sortiert, sonst false
	 */
	private void sortHelp(DefaultMutableTreeNode node, Comparator comp, 
							boolean reverse, boolean firstColum) 
		{
		ArrayList<DefaultMutableTreeNode> array = new ArrayList<DefaultMutableTreeNode>();
		ArrayList<DefaultMutableTreeNode> arrayOrdner = new ArrayList<DefaultMutableTreeNode>();
		
		for (int i = 0; i < node.getChildCount(); i++) {
			if (!node.getChildAt(i).isLeaf()) {
				// Rekursiver Aufruf
				sortHelp((DefaultMutableTreeNode) node.getChildAt(i), 
						comp, 
						reverse, 
						firstColum);
			}
			
			if (((DefaultMutableTreeNode) node.getChildAt(i)).getUserObject()
					instanceof String ) 
			{
				// Alle "Ordner", die keine Varianten sind
				arrayOrdner.add((DefaultMutableTreeNode) node.getChildAt(i));
			} else {
				// Alle Elemente des schema-Typs
				array.add((DefaultMutableTreeNode) node.getChildAt(i));
			}
			node.remove(i--);
		}
		
		// Sortierung
		Collections.sort(array, comp);
		if ( reverse ) {
			// Somit wird beim zweiten klick die Reihenfolge vertauscht
			Collections.reverse(array);
		}
		
		// Nur bei der Sortierung der ersten Spalte werden die
		// "Ordner" mit einbezogen
		if (firstColum) {
			Collections.sort(arrayOrdner, comp);
			if ( reverse ) {
				Collections.reverse(arrayOrdner);
			}
		}

		// Erst die "normalen" Elemente einf�gen
		for (int i = 0; i < array.size(); i++) {
			node.insert(array.get(i), i);
		}
		// Dann die Ordner - so sind die Ordner immer oben in der Anzeige
		for (int i = 0; i < arrayOrdner.size(); i++) {
			node.insert(arrayOrdner.get(i), i);
		}
	}
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.GUI.komponenten.table.SortableTableModelInterface#isSortColumnDesc(int)
	 */
	public boolean isSortColumnDesc(int column) {
		return lastAscSorted[column];
	}

    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.Test.treeTable.TreeTableModel#isCellEditable(java.lang.Object, int)
     */
    public boolean isCellEditable(Object node, int column) {

    	return schema.isCellEditable(
    			((DefaultMutableTreeNode) node).getUserObject(), 
				columns[column]);
   }

    
    
	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.gui.komponenten.table.AbstractTreeTableModel#setValueAt(java.lang.Object, java.lang.Object, int)
	 */
	@Override
	public void setValueAt(Object aValue, Object node, int column) {

		schema.setCellValue(aValue, 
				((DefaultMutableTreeNode) node).getUserObject(), 
				columns[column]);
	}

	/* (non-Javadoc) Methode �berschrieben
	 * @see org.d3s.alricg.Test.treeTable.TreeTableModel#getValueAt(java.lang.Object, int)
	 */
	public Object getValueAt(Object node, int column) {
		DefaultMutableTreeNode mutNode;
		
		mutNode = ((DefaultMutableTreeNode) node);
				
		if (mutNode.getUserObject() instanceof String) {
			// Es ist nur ein Ordner, der zur sortierung dient
			
			if (column == 0) {
				return node; // R�ckgabe als De.MutableNode f�r den Tree
			} else {
				// TODO andere L�sung f�r Komplexe Problme n�tig
				return "-";
			}
		}
		
		return schema.getCellValue(mutNode.getUserObject(), columns[column]);
	}

    /* (non-Javadoc) Methode �berschrieben
     * @see org.d3s.alricg.Test.treeTable.TreeTableModel#getColumnClass(int)
     */
    public Class getColumnClass(int column) {
    	if (column == 0) {
    		return TreeTableModel.class;
    	}
    	
    	return super.getColumnClass(column);
    }
	
	/* (non-Javadoc) Methode �berschrieben
	 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
	 */
	public Object getChild(Object parent, int idx) {
		return ((DefaultMutableTreeNode) parent).getChildAt(idx);
	}

	/* (non-Javadoc) Methode �berschrieben
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 */
	public int getChildCount(Object node) {
		return ((DefaultMutableTreeNode) node).getChildCount();
	}
	
	/**
	 * <u>Beschreibung:</u><br> 
	 * Dient ledeglich als Wapper damit die Objekte in einem DefaultMutableTreeNode 
	 * miteinander verglichen werden k�nnen.
	 * @author V. Strelow
	 */
	class NodeComparator implements Comparator<DefaultMutableTreeNode> {
		private Comparator realComp;
		
		public void setRealComparator(Comparator comp) {
			realComp = comp;
		}

		/* (non-Javadoc) Methode �berschrieben
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(DefaultMutableTreeNode node0, DefaultMutableTreeNode node1) {
			return realComp.compare(node0.getUserObject(), node1.getUserObject());
		}
		
	}
	
}




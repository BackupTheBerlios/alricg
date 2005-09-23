/*
 * Created on 21.01.2005 / 16:20:34
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GPL licence.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.test;

import java.awt.Color;
import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Serializer;

import org.d3s.alricg.charKomponenten.Talent;
import org.d3s.alricg.charKomponenten.charZusatz.WuerfelSammlung;
import org.d3s.alricg.controller.CharKomponente;
import org.d3s.alricg.controller.ProgAdmin;
import org.d3s.alricg.gui.komponenten.table.SortableTable;
import org.d3s.alricg.gui.komponenten.table.SortableTableModel;
import org.d3s.alricg.gui.komponenten.table.SortableTreeModel;
import org.d3s.alricg.gui.komponenten.table.SortableTreeTable;
import org.d3s.alricg.gui.komponenten.table.TreeTableModel;
import org.d3s.alricg.gui.views.SpaltenSchema;
import org.d3s.alricg.gui.views.SpaltenSchema.SpaltenArt;
import org.d3s.alricg.gui.views.talent.TalentSchema;
import org.d3s.alricg.gui.views.talent.TalentSpalten;
import org.d3s.alricg.gui.views.zauber.ZauberSchema;
import org.d3s.alricg.gui.views.zauber.ZauberSpalten;
import org.d3s.alricg.store.FactoryFinder;
import org.d3s.alricg.test.treeTable.JTreeTable;

/**
 * <u>Beschreibung:</u><br> 
 * Diese Klasse dient den Testen von Alricg!
 * @author V. Strelow
 */
public class TestDriver {
	ProgAdmin pa;
	
	
	public static void main(String[] args) {
		
		TestDriver TD = new TestDriver();
	}
	
	public TestDriver()  {
		
		ProgAdmin.main(null);
		
		testSortableTableTalent();
		//testSortableTableZauber();
		
		/*
		System.out.println(
				FormelSammlung.getSktWert(KostenKlasse.A, 1)
		);
		*/
		
		/*
		ToolTipTest ttt = new ToolTipTest();
		ttt.show();
		*/
		
		/*
		startAlricg();
		xmlWriteTest(null);
		*/
		
		//testSortableTable();
		
		//JTableExample te = new JTableExample();
		//JButtonTableExample bte = new JButtonTableExample();
		
		//te.setVisible(true);
		//bte.setVisible(true);
		
		//enumTest();
		//testTreeTable();
		
		//testLoadAndWrite();
		
		/*pa = new ProgAdmin();
		pa.initProgAdmin();
		
		xmlWriteTest(null);*/
		
		
		//testFile();
		//testGeneric();
		//testGeneric(new Integer(4));
	}
	
	public void startAlricg() {
		// Programm Starten und Datein einladen
		ProgAdmin.main(null);
	}
	
	public void testSortableTableZauber() {
		JFrame frame = new JFrame("SortableTableTest");
		ArrayList<Talent> array;
		SortableTreeModel treeModel = new SortableTreeModel(new ZauberSpalten(), new ZauberSchema(), SpaltenSchema.SpaltenArt.objektDirekt, "Zauber");

		array = (ArrayList<Talent>) new ArrayList(
                FactoryFinder.find().getData().getUnmodifieableCollection(CharKomponente.zauber)
			);
		
		treeModel.setData(array);
		
		SortableTreeTable sTree = new SortableTreeTable(treeModel);
		
		treeModel.getSpaltenSchema().initTable(sTree, SpaltenSchema.SpaltenArt.objektDirekt);
		
		//JTreeTable treeTable = new JTreeTable(treeModel);
		
		JScrollPane sp = new JScrollPane(sTree);
		
		frame.getContentPane().add(sp);
		frame.pack();
		frame.setVisible(true);

	}
	
	public void testSortableTableTalent() {
		JFrame frame = new JFrame("SortableTableTest");
		ArrayList<Talent> array;
		
		SortableTableModel tableModel = new SortableTableModel(new TalentSpalten(), new TalentSchema(), SpaltenArt.editorGewaehlt );
		SortableTreeModel treeModel = new SortableTreeModel(new TalentSpalten(), new TalentSchema(), SpaltenArt.editorGewaehlt, "Talente");
		
		array = (ArrayList<Talent>) new ArrayList(
                FactoryFinder.find().getData().getUnmodifieableCollection(CharKomponente.talent)
				);
		
		tableModel.setData(array);
		treeModel.setData(array);

		/*DefaultTableModel dm = new DefaultTableModel() {
			public void setValueAt(Object aValue, int rowIndex, int columnIndex)  {
				super.setValueAt(aValue, rowIndex, columnIndex);
				System.out.println(aValue.toString());
			}
			
		};
		dm.setDataVector(new Object[][] { 
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" },
					{ "btnFoo", "foo", new Boolean(false), "ruft uta" }
				}, 
				new Object[] { "Button", "String", "Bool", "String" });

		JTable st = new JTable(dm);*/
		
		SortableTreeTable sTree = new SortableTreeTable(treeModel);
		SortableTable sTable = new SortableTable();
		sTable.setModel(tableModel);
		
		treeModel.getSpaltenSchema().initTable(sTree, SpaltenArt.editorGewaehlt);
		tableModel.getSpaltenSchema().initTable(sTable, SpaltenArt.editorGewaehlt);
		
		/*
		TableColumn sportColumn = sTree.getColumnModel().getColumn(2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Snowboarding");
		comboBox.addItem("Rowing");
		comboBox.addItem("Chasing toddlers");
		comboBox.addItem("Speed reading");
		comboBox.addItem("Teaching high school");
		comboBox.addItem("None");
		sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
		*/
		
		//sTree.setColumnImage(4);
		//sTree.setColumnButton(5, "+");
		//sTree.setColumnButton(6, "-");
		
		//JTreeTable treeTable = new JTreeTable(treeModel);
		
		JScrollPane sp = new JScrollPane(sTree);
		
		frame.getContentPane().add(sp);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void enumTest() {
		Art art = Art.basis;
		System.out.println(((Enum) art).ordinal());
	}
	
	public enum Art {
		basis("basis"), 
		spezial("spezial"), 
		beruf("beruf");
		private String xmlValue; // XML-Tag des Elements
		
		private Art(String xmlValue) {
			this.xmlValue = xmlValue;
		}
		
		public String getXmlValue() {
			return xmlValue;
		}	
	}
	
	public void testTreeTable()  {

		JFrame frame = new JFrame("TreeTableTest");

		Container       cPane = frame.getContentPane();
		TreeTableModel  model = new TestTreeTableModel();

		
		JTreeTable treeTable = new JTreeTable(model);
		
	        JScrollPane sp = new JScrollPane(treeTable);
	        sp.getViewport().setBackground(Color.white);
		cPane.add(sp);

		TableColumn column = treeTable.getColumnModel().getColumn(1);
		
		/*JComboBox comboBox = new JComboBox();
		comboBox.addItem("Snowboarding");
		comboBox.addItem("Rowing");
		comboBox.addItem("Chasing toddlers");
		comboBox.addItem("Speed reading");
		comboBox.addItem("Teaching high school");
		comboBox.addItem("None");
		column.setCellEditor(new DefaultCellEditor(comboBox));*/
		
		//column.setCellEditor(new DefaultCellEditor(new JButton("Trara")));
		
		
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Startet das Programm und l‰ﬂt die XML-Dateinen einlesen. Dann werden die
	 * XML-Dateien rausgeschrieben und die neue Datei mit der original Datei 
	 * verglichen.
	 */
	public void testLoadAndWrite() {
		File oldFile, newFile;
		Element configRoot, oldRoot, newRoot;

		// Programm Starten und Datein einladen
		pa = new ProgAdmin();
		//pa.initProgAdmin();
		
		// Daten in Datei Schreiben
		newFile = new File("ressourcen" + File.separator + "test" 
									+ File.separator + "xmlOutput.xml");
		xmlWriteTest(newFile);
		
		// XML-Dateien erneut einlesen und vergleichen
		//configRoot = getRootElement(new File(ProgAdmin.DATEI_CONFIG));
		/*oldFile = new File(ProgAdmin.PFAD_XML_DATEN_D3S 
						+ configRoot.getFirstChildElement("xmlRuleFilesD3S")
									.getFirstChildElement("readFile").getValue()
							);
							*/
		
		/*oldRoot = getRootElement(oldFile);
		oldRoot.removeChild(oldRoot.getFirstChildElement("preamble"));
		newRoot = getRootElement(newFile);
		
		vergleicheElement(oldRoot, newRoot);*/
		
	}
	
	private void vergleicheElement(Element oldRoot, Element newRoot) {
		int idx = 0;
		Elements oldElements, newElements;
		ArrayList<String> arrayList = new ArrayList<String>();
		
		// Vergelich der Werte selbst
		
		if ( 	oldRoot.getChildCount() == 0 &&
				!oldRoot.getValue().trim().equals(newRoot.getValue().trim()) ) {
			
			
			writeErrorMessage("Werte sind ungleich: ", oldRoot, newRoot);

			/*System.out.println("Werte sind ungleich: ");
			System.out.println(oldRoot.getValue());
			System.out.println(newRoot.getValue());
			System.out.println("----------------------------");*/
		}
		
		// Vergelich der Attribute / Werte und ob Attribute alle vorhanden
		if ( oldRoot.getAttributeCount() != newRoot.getAttributeCount() ) {
			writeErrorMessage("Attribute ist nur einseitig vorhanden: ", oldRoot, newRoot);
		} else if (oldRoot.getAttributeCount() > 0 ){
			// Alle Attribute vergleichen
			for (int i = 0; i < oldRoot.getAttributeCount(); i++) {
				arrayList.add(oldRoot.getAttribute(i).getValue());
			}
			for (int i = 0; i < newRoot.getAttributeCount(); i++) {
				if ( !arrayList.contains(newRoot.getAttribute(i).getValue()) ) {
					writeErrorMessage("Werte von Attributen sind ungleich: ", oldRoot, newRoot);
				}
			}
			arrayList.clear();
		}
		
		oldElements = oldRoot.getChildElements();
		newElements = newRoot.getChildElements();
		
		if (oldElements.size() != newElements.size()) {
			writeErrorMessage("Anzahl Kind-Elemente unterschiedlich: ", oldRoot, newRoot);
		} else {
			// Rekursiver aufruf
			for (int i = 0; i < oldElements.size(); i++) {
				vergleicheElement(oldElements.get(i), newElements.get(i));
			}
		}
	}
	
	private void writeErrorMessage(String mes, Element oldRoot, Element newRoot) {
		System.out.println(mes);
		System.out.println(oldRoot.toXML());
		System.out.println(newRoot.toXML());
		System.out.println("----------------------------");
	}
	
	/**
	 * Schreib die im "ProgAdmin.charKompAdmin" enthaltenen Elemente in ein XML File.
	 * Falls kein File angegeben wird, wird das file auf der Konsole ausgegeben
	 * @param file Das File in das geschrieben wird, oder "null" f¸r eine ausgabe 
	 * 		auf die Konsole
	 */
	public void xmlWriteTest(File file) {
		OutputStream oStream = System.out;
		/*
		Document doc = new Document(ProgAdmin.charKompAdmin.writeXML());
		
		// Fall kein file angegeben wurde, wie das XML File auf der Konsole
		// ausgegeben
		if (file != null) {
			try {
				oStream = new FileOutputStream(file);
			} catch(FileNotFoundException ex) {
				System.err.println(ex);
			}
		}
		
	    try {
	        Serializer serializer = new Serializer(oStream, "ISO-8859-1");
	        serializer.setIndent(4);
	        serializer.setMaxLength(0);
	        serializer.write(doc);
	      } catch (IOException ex) {
	         System.err.println(ex);
	      }*/
	}
	
	private void testWuerfel() {
		WuerfelSammlung w = new WuerfelSammlung(5, null, new int[]{6});
		
		System.out.println(w.getWuerfelWurf());
		System.out.println(w.getWuerfelWurf());
		System.out.println(w.getWuerfelWurf());
		System.out.println(w.getWuerfelWurf());
		System.out.println(w.getWuerfelWurf());
		System.out.println(w.getWuerfelWurf());
		
	}
	
	/**
	 * Lieﬂt ein XML-File ein und gibt das RootElement zur¸ck.
	 * @param xmlFile Das File das eingelesen werden soll
	 * @return Das rootElement des XML-Files oder null, falls die Datei nicht geladen 
	 * werden konnte!
	 */
	private Element getRootElement(File xmlFile) {
		Document doc = null;
		
		try {
			Builder parser = new Builder(); // Auf true setzen f¸r Validierung
			doc = parser.build(xmlFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return doc.getRootElement();
	}
}

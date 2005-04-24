/*
 * Created on 29.03.2005 / 01:24:12
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.gui.komponenten;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * <u>Beschreibung:</u><br> 
 * Eine neue Swing-Komponente, bestehend aus einem Text-Feld und einem Button. Wird der
 * Button gedrückt wird eine Auswahl-Liste "aufgeklappt" wie bei ComboBoxen. Es kann
 * dann ein Wert für das TextFeld ausgewählt werden.
 * TODO Den Button mit einem Bild ausstatten.
 * @author V. Strelow
 */
public class TextFieldList extends JPanel {
	private static int LIST_HEIGHT = 75;
	private JTextField txtText = null;
	private JButton butList = null;
	private JList lstPopList = null;
	private JScrollPane panPopList = null;
	private DefaultListModel listModel = null;
	/**
	 * This is the default constructor
	 */
	public TextFieldList() {
		super();
		initialize();

	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(126, 20);
		this.add(getTxtText(), java.awt.BorderLayout.CENTER);
		this.add(getButList(), java.awt.BorderLayout.EAST);
		
		// Listener Hinzufügen für Größenanpassung bei 
		this.addComponentListener(
				new ComponentAdapter() {
					public void componentMoved(ComponentEvent e) {
						doResizeList();
					}
					public void componentResized(ComponentEvent e) {
						doResizeList();
					}
					
					public void componentHidden(ComponentEvent e) {
						doResizeList();
					}
				}
		);
		
		panPopList = getPanPopList();
		panPopList.setVisible(false);
	}
	
	/**
	 * This method initializes jTextField
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtText() {
		if (txtText == null) {
			txtText = new JTextField();
		}
		return txtText;
	}
	
	/**
	 * This method initializes jButton
	 * @return javax.swing.JButton	
	 */    
	private JButton getButList() {
		if (butList == null) {
			butList = new JButton();
			butList.setMargin(new java.awt.Insets(0,0,0,0));
			butList.setText("v");
			
			// Listener zum schließen der PopList
			butList.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (!panPopList.isVisible()) {
							showPopList();
						} else {
							hidePopList();
						}
					}
				}
			);
			
		}
		return butList;
	}
	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getLstPopList() {
		if (lstPopList == null) {
			listModel = new DefaultListModel();
			lstPopList = new JList(listModel);
			lstPopList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			lstPopList.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaptionBorder,1));

			
			// Listener zum schließen der PopList
			lstPopList.addFocusListener(
					new FocusAdapter() {
						public void focusLost(FocusEvent e) {
							// Panel schließen, wenn nicht auf den Button geklick
							// wurde (der Button übernimmt dann das schließen)
							if (!e.getOppositeComponent().equals(butList)) {
								hidePopList();
							}
						}
					}
			);
			// Listener für die Auswahl mit der Maus
			lstPopList.addMouseListener(
					new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							listItemToText();
						}
				        public void mouseClicked(MouseEvent evt) {
				        	if (evt.getClickCount() == 2) {
				        		listItemToText();
				        		hidePopList();
				            }
				        }
					}
			);
			// Listener für die Auswahl mit "Enter"
			lstPopList.addKeyListener(
					new KeyAdapter() {
						
						public void keyPressed(KeyEvent event) {
							if (event.getKeyCode() == KeyEvent.VK_ENTER) {
				        		listItemToText();
				        		hidePopList();
							}
						}

					}
			);
		}
		return lstPopList;
	}
	
	/**
	 * This method initializes jScrollPane	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getPanPopList() {
		if (panPopList == null) {
			panPopList = new JScrollPane();
			panPopList.setViewportView(getLstPopList());
		}
		return panPopList;
	}
	
	/**
	 * Zeigt die "Button-Liste" an und ermöglicht so eine Auswahl 
	 */
	private void showPopList() {
		
		this.getRootPane().getLayeredPane().add(panPopList, JLayeredPane.POPUP_LAYER);
		panPopList.setVisible(true);
		
		doResizeList(); // Anpassen and die Position und Größe
		lstPopList.requestFocus();
	}
	
	/**
	 * Passt die Liste an die Position und die Größe des TextFields und des Buttons 
	 * an. Wird beim Anzeigen und bei Größenveränderungen aufgerufen. 
	 */
	private void doResizeList() {
		
		if (panPopList != null && panPopList.isVisible()) {
		    // Errechne die Richtigen Koordinaten auf dem LayeredPane
			Point pt = SwingUtilities.convertPoint(
		    		this.getParent(), 
		    		this.getLocation(),
		    		this.getRootPane().getLayeredPane()
		    );
			
			// Setzen der Größe
			panPopList.setBounds(
					pt.x, 
					pt.y + this.getHeight(),  
					this.getWidth(), LIST_HEIGHT
			);
			panPopList.updateUI(); // Updaten für Anzeige
		}
		
	}
	
	/**
	 * Schließt die Liste wieder
	 */
	private void hidePopList() {
		panPopList.setVisible(false);
		this.getRootPane().getLayeredPane().remove(panPopList);
	}
	
	/**
	 * Sorg dafür, dass die Auswahl der Liste in das TextField kopiert wird.
	 */
	private void listItemToText() {
		if (panPopList.isVisible() && !lstPopList.isSelectionEmpty()) {
			txtText.setText(lstPopList.getSelectedValue().toString());
		}
	}
	
	/**
	 * @return Liefert den Text zurück, der in TextField steht
	 */
	public String getText() {
		return txtText.getText();
	}
	
	/**
	 * Setzt den angezeigten Text neu
	 * @param text Der neue Text des TextFields
	 */
	public void setText(String text) {
		txtText.setText(text);
	}
	
	/**
	 * Fügt einen neuen Eintrag zu der Auswahl-Liste hinzu.
	 * @param entry Neues Element für die Auswahlliste.
	 */
	public void addListValue(String entry) {
		listModel.addElement(entry);
	}
	
 }  //  @jve:decl-index=0:visual-constraint="10,10"

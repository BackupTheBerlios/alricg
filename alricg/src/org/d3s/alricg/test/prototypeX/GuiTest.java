/*
 * Created on 25.03.2005 / 23:33:19
 *
 * This file is part of the project ALRICG. The file is copyright
 * protected and under the GNU General Public License.
 * For more information see "http://alricg.die3sphaere.de/".
 *
 */
package org.d3s.alricg.test.prototypeX;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

import org.d3s.alricg.gui.editor.panels.panGrunddaten;
/**
 * <u>Beschreibung:</u><br> 
 *
 * @author V. Strelow
 */
public class GuiTest extends JFrame {

	private javax.swing.JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JSplitPane jSplitPane1 = null;
	private JSplitPane jSplitPane2 = null;
	private JSplitPane jSplitPane3 = null;  //  @jve:decl-index=0:visual-constraint="432,9"
	private JPanel jPanel = null;
	private JEditorPane jEditorPane = null;
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getJSplitPane1());
			jSplitPane.setRightComponent(getJSplitPane2());
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jSplitPane1	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setPreferredSize(new java.awt.Dimension(150,64));
			jSplitPane1.setMinimumSize(new java.awt.Dimension(108,64));
		}
		return jSplitPane1;
	}
	/**
	 * This method initializes jSplitPane2	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setRightComponent(getJSplitPane3());
			jSplitPane2.setLeftComponent(getJPanel());
		}
		return jSplitPane2;
	}
	/**
	 * This method initializes jSplitPane3	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane3() {
		if (jSplitPane3 == null) {
			jSplitPane3 = new JSplitPane();
			jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

			jSplitPane3.setPreferredSize(new java.awt.Dimension(150,64));
			jSplitPane3.setTopComponent(getJEditorPane());
		}
		return jSplitPane3;
	}
    
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new panGrunddaten();
		}
		return jPanel;
	}
	/**
	 * This method initializes jEditorPane	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */    
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			jEditorPane = new JEditorPane();
			jEditorPane.setContentType("text/html");
			jEditorPane.setEditable(false);
			jEditorPane.addHyperlinkListener(new Hyperactive());
			
			try {
				
				String tmp = "ressourcen/test/test.html";
				File file = new File(tmp);
				jEditorPane.setPage("file:/"+file.getAbsolutePath());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jEditorPane;
	}
     	public static void main(String[] args) {
		GuiTest gt = new GuiTest();
		gt.setVisible(true);
	}
	
	/**
	 * This is the default constructor
	 */
	public GuiTest() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setPreferredSize(new java.awt.Dimension(800,102));
		this.setMinimumSize(new java.awt.Dimension(800,102));
		this.setSize(800, 280);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		if ( this.getCursor().getType() == Cursor.CROSSHAIR_CURSOR) {
			System.out.println("lallaalal");
		}


	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	
	class TestMouseListener extends MouseAdapter {
		
		/* (non-Javadoc) Methode überschrieben
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased(MouseEvent arg0) {
			System.out.println("> " + arg0.getComponent());
			System.out.println("- " + arg0.getSource());
			// TODO Auto-generated method stub
			super.mouseReleased(arg0);
		}
}
	
    class Hyperactive implements HyperlinkListener {
    	 
    	         public void hyperlinkUpdate(HyperlinkEvent e) {
    	             if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
    	                 JEditorPane pane = (JEditorPane) e.getSource();
    	                 if (e instanceof HTMLFrameHyperlinkEvent) {
    	                     HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
    	                     HTMLDocument doc = (HTMLDocument)pane.getDocument();
    	                     doc.processHTMLFrameHyperlinkEvent(evt);
    	                 } else {
    	                     try {
    	                         pane.setPage(e.getURL());
    	                     } catch (Throwable t) {
    	                         t.printStackTrace();
    	                     }
    	                 }
    	             }
    	         }
    	     }
}  //  @jve:decl-index=0:visual-constraint="10,10"

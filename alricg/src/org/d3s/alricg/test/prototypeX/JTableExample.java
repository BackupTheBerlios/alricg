 /* Created on 07.02.2005@10:19:11
 *
 * TODO Licence info
 */
package org.d3s.alricg.test.prototypeX;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.sun.java.swing.plaf.motif.MotifGraphicsUtils;

/**
 * @author Administrator
 * 
 * TODO Explain me
 */
public class JTableExample extends JFrame {

    private JTable table;

    private JPanel filterPanel;

    private JTextField txtFilter;

    private JButton btnFilter, btnShowAll;

    private FooTableModel model, filteredModel;

    private boolean filteredModelInUse;

    private final ImageIcon UP_ICON = new ImageIcon(MotifGraphicsUtils.class
            .getResource("icons/ScrollUpArrow.gif"));

    private ImageIcon DOWN_ICON = new ImageIcon(MotifGraphicsUtils.class
            .getResource("icons/ScrollDownArrow.gif"));

    public JTableExample() {
        super("JTableExample");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Object[][] rowData = { { "1", "a", "c" }, { "2", "b", "b" },
                { "3", "f", "r" }, { "4", "q", "a" }, { "5", "w", "z" },
                { "6", "c", "a" }, { "7", "a", "q" } };

        final Object[] columnHeaders = { "ID", "Header1", "Header2" };

        model = new FooTableModel(rowData, columnHeaders);
        table = new JTable(model);
        
        table.getTableHeader().setDefaultRenderer(
        		
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
                        setHorizontalAlignment(SwingConstants.CENTER);
                        setHorizontalTextPosition(SwingConstants.LEFT);
                        
                        if (model.sortColumnDesc[column]) {
                            setIcon(UP_ICON);
                        } else {
                            setIcon(DOWN_ICON);
                        }

                        return this;
                    }
                });

        table.getTableHeader().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                model.sortByColumn(table.columnAtPoint(evt.getPoint()));
            }
        });

        filterPanel = new JPanel(new BorderLayout());
        txtFilter = new JTextField();
        btnFilter = new JButton("filter");
        btnFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filterRegex = txtFilter.getText();
                Pattern pattern = Pattern.compile(filterRegex);
                int i = 0;

                List filteredItems = new ArrayList();

                for (Iterator iter = model.getDataVector().iterator(); iter
                        .hasNext(); i++) {
                    Vector v = (Vector) iter.next();
                    String str = (String) v.get(1); // Nur zweite Spalte Filtern
                    Matcher m = pattern.matcher(str);

                    if (m.find()) {
                        filteredItems.add(v);
                    }
                }

                int size = filteredItems.size();
                if (size == model.getRowCount()) {
                    filteredModelInUse = false;
                } else {
                    filteredModelInUse = true;
                    Object[][] rowData = new Object[size][columnHeaders.length];

                    int j = 0;
                    for (Iterator iter = filteredItems.iterator(); iter
                            .hasNext(); j++) {
                        Vector element = (Vector) iter.next();
                        rowData[j] = element.toArray();
                    }

                    filteredModel = new FooTableModel(rowData, columnHeaders);
                    table.setModel(filteredModel);
                    table.updateUI();
                }
            }
        });

        btnShowAll = new JButton("Zeige alles");
        btnShowAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table.setModel(model);
                filteredModelInUse = false;
            }
        });

        filterPanel.add(btnShowAll, BorderLayout.WEST);
        filterPanel.add(txtFilter, BorderLayout.CENTER);
        filterPanel.add(btnFilter, BorderLayout.EAST);

        Container c = getContentPane();
        c.add(new JScrollPane(table), BorderLayout.CENTER);
        c.add(filterPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new JTableExample();
    }

    public boolean isFilteredModelVisible() {
        return filteredModelInUse;
    }

    class FooTableModel extends DefaultTableModel {

        private boolean[] sortColumnDesc;

        private int currentSortColumn = 0;

        private Comparator comparator = new Comparator() {
            public int compare(Object o1, Object o2) {
                Vector v1 = (Vector) o1;
                Vector v2 = (Vector) o2;

                int size1 = v1.size();
                if (currentSortColumn >= size1)
                    throw new IllegalArgumentException("max column idx: "
                            + size1);

                Comparable c1 = (Comparable) v1.get(currentSortColumn);
                Comparable c2 = (Comparable) v2.get(currentSortColumn);

                int cmp = c1.compareTo(c2);

                if (sortColumnDesc[currentSortColumn]) {
                    cmp *= -1;
                }

                return cmp;
            }
        };

        public FooTableModel(Object[][] rowData, Object[] headers) {
            super(rowData, headers);
            sortColumnDesc = new boolean[headers.length];
        }

        public void sortByColumn(final int clm) {
            if (clm == 0) // erste Spalte nicht Sortieren...
                return;

            currentSortColumn = clm;

            Vector v = null;
            if (JTableExample.this.isFilteredModelVisible()) {
                v = JTableExample.this.filteredModel.dataVector;
            } else {
                v = JTableExample.this.model.dataVector;
            }

            Collections.sort(v, comparator);
            model.sortColumnDesc[clm] ^= true;
        }
    }
}
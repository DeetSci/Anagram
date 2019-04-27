package project.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import project.tester.Anagram;
import project.db.AnagramDB;
import project.db.DBException;

@SuppressWarnings("serial")
public class AnagramManagerFrame extends JFrame {
    private JTable anagramTable;
    private AnagramTableModel anagramTableModel;
    
    public AnagramManagerFrame() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        setTitle("Anagram Database Manager");
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(buildButtonPanel(), BorderLayout.SOUTH);
        anagramTable = buildAnagramTable();
        add(new JScrollPane(anagramTable), BorderLayout.CENTER);
        pack();
        setVisible(true);        
    }
    
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        
        JButton addButton = new JButton("Add");
        addButton.setToolTipText("Add anagram");
        addButton.addActionListener((ActionEvent) -> {
            doAddButton();
        });
        panel.add(addButton);
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.setToolTipText("Delete selected anagram");
        deleteButton.addActionListener((ActionEvent) -> {
            doDeleteButton();
        });
        panel.add(deleteButton);
        
        JButton returnButton = new JButton("Return");
        returnButton.setToolTipText("Return to Welcome screen");
        returnButton.addActionListener((ActionEvent) -> {
        		doReturnButton();
        });
        panel.add(returnButton);
        
        return panel;
    }
    
    private void doReturnButton() {
    		dispose();
        new StartupWindow();
    }
    
    private void doAddButton() {
    	 	AnagramForm anagramForm = new AnagramForm(this, "Add Anagram", true);
    	 	anagramForm.setLocationRelativeTo(this);
    	 	anagramForm.setVisible(true);
    		dispose();
        new StartupWindow();
    }
    
    private void doDeleteButton() {
        int selectedRow = anagramTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "No product is currently selected.", 
                    "No product selected", JOptionPane.ERROR_MESSAGE);
        } else {
            Anagram c = anagramTableModel.getAnagram(selectedRow);
            int ask = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete " + 
                        c.getFirstAnagram() + " from the database?",
                    "Confirm delete", JOptionPane.YES_NO_OPTION);
            if (ask == JOptionPane.YES_OPTION) {
                try {                    
                    AnagramDB.delete(c);
                    fireDatabaseUpdatedEvent();
                } catch (DBException e) {
                    System.out.println(e);
                }
            }
        }
    }
    
    public void fireDatabaseUpdatedEvent() {
        anagramTableModel.databaseUpdated();
    }    
    
    private JTable buildAnagramTable() {
        anagramTableModel = new AnagramTableModel();
        JTable table = new JTable(anagramTableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBorder(null);
        return table;
    }    
}
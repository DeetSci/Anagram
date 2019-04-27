package project.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import project.tester.Anagram;
import project.db.AnagramDB;
import project.db.DBException;

@SuppressWarnings("serial")
public class AnagramTableModel extends AbstractTableModel {
    private List<Anagram> anagram;
    private static final String[] COLUMN_NAMES = {
        "First Anagram", "Second Anagram"
    };

    
    public AnagramTableModel() {
        try {
            anagram = AnagramDB.getAnagram();
        } catch (DBException e) {
            System.out.println(e);
        }
    }
    
    Anagram getAnagram(int rowIndex) {
        return anagram.get(rowIndex);
    }
    
    void databaseUpdated() {
        try {
            anagram = AnagramDB.getAnagram();
            fireTableDataChanged();
        } catch (DBException e) {
            System.out.println(e);
        }        
    }

    @Override
    public int getRowCount() {
        return anagram.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {	
        switch (columnIndex) {
            case 0:
                return anagram.get(rowIndex).getFirstAnagram();
            case 1:
                return anagram.get(rowIndex).getSecondAnagram();
            default:
                return null;
        }
    }   
}
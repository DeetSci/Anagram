package project.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import project.tester.Anagram;
import project.db.DBException;
import project.db.AnagramDB;

@SuppressWarnings("serial")
public class AnagramForm extends JDialog {
    private JTextField firstAnagramField;
    private JTextField secondAnagramField;
    private JButton confirmButton;
    private JButton cancelButton;

    public Anagram anagram = new Anagram();

    public AnagramForm(java.awt.Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initComponents();
    }
    
    public AnagramForm(java.awt.Frame parent, String title,
            boolean modal, Anagram anagram) {
        this(parent, title, modal);        
        this.anagram = anagram;
        confirmButton.setText("Save");
        firstAnagramField.setText(anagram.getFirstAnagram());
        secondAnagramField.setText(anagram.getSecondAnagram());
    }

	private void initComponents() {
    		firstAnagramField = new JTextField();
    		secondAnagramField = new JTextField();
        cancelButton = new JButton();
        confirmButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);     
        
        Dimension dim = new Dimension(160, 20);
        firstAnagramField.setPreferredSize(dim);
        firstAnagramField.setMinimumSize(dim);       
        secondAnagramField.setPreferredSize(dim);
        secondAnagramField.setMinimumSize(dim);
        
        cancelButton.setText("Cancel");
        cancelButton.addActionListener((ActionEvent) -> {
            cancelButtonActionPerformed();
        });

        confirmButton.setText("Add");
        confirmButton.addActionListener((ActionEvent) -> {
            confirmButtonActionPerformed();
        });

        JPanel anagramPanel = new JPanel();
        anagramPanel.setLayout(new GridBagLayout());
        anagramPanel.add(new JLabel("First Anagram:"), 
                getConstraints(0, 0, GridBagConstraints.LINE_END));
        anagramPanel.add(firstAnagramField,
                getConstraints(1, 0, GridBagConstraints.LINE_START));
        anagramPanel.add(new JLabel("Second Anagram:"), 
                getConstraints(0, 1, GridBagConstraints.LINE_END));
        anagramPanel.add(secondAnagramField, 
                getConstraints(1, 1, GridBagConstraints.LINE_START));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(anagramPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();        
    }
    
    private GridBagConstraints getConstraints(int x, int y, int anchor) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);
        c.gridx = x;
        c.gridy = y;
        c.anchor = anchor;
        return c;
    }

    private void cancelButtonActionPerformed() {                                             
        dispose();
    }                                            

    private void confirmButtonActionPerformed() {
        if (validateData()) {
            setData();
            if (confirmButton.getText().equals("Add")) {
                doAdd();
            } else {
                doEdit();
            }
        }
    }
    
    private boolean validateData() {
        String firstAnagram = firstAnagramField.getText();
        String secondAnagram = secondAnagramField.getText();
        if (firstAnagramField == null || secondAnagramField == null ||
                firstAnagram.isEmpty() || secondAnagram.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                    "Missing Fields", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void setData() {
        String firstAnagram = firstAnagramField.getText();
        String secondAnagram = secondAnagramField.getText();
        anagram.setFirstAnagram(firstAnagram);
        anagram.setSecondAnagram(secondAnagram);
    }
    
    private void doEdit() {
        try {
            AnagramDB.update(anagram);
            dispose();
            fireDatabaseUpdatedEvent();
        } catch (DBException e) {
            System.out.println(e);
        }
    }
    
    public AnagramForm doAdd() {
        try {
            AnagramDB.add(anagram, anagram);
            dispose();
            fireDatabaseUpdatedEvent();
        } catch(DBException e) {
            System.out.println(e);
        }
		return null;
    }
    
    public void fireDatabaseUpdatedEvent() {
        AnagramManagerFrame mainWindow = (AnagramManagerFrame) getOwner();
        mainWindow.fireDatabaseUpdatedEvent();
    }
}
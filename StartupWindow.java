package project.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import project.tester.Anagram;


@SuppressWarnings("serial")
public class StartupWindow extends JFrame implements ActionListener
{

    public StartupWindow() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        setTitle("Welcome to the Anagram Test");
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(buildButtonPanel(), BorderLayout.SOUTH);
        add(buildLabelPanel());
        setSize(500,150);
        setVisible(true);        

    }
    
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        
        JButton testButton = new JButton("Test");
        testButton.setToolTipText("Test Anagram");
        testButton.setActionCommand("Test");
        testButton.addActionListener(this);
        panel.add(testButton);

        JButton searchButton = new JButton("Search");
        searchButton.setToolTipText("Search the Anagram Database");
        searchButton.setActionCommand("Search");
        searchButton.addActionListener(this);
        panel.add(searchButton);
        
        return panel;
    }

    private JLabel buildLabelPanel() {
    		JLabel label= new JLabel();
    		
    		Border border = BorderFactory.createLineBorder(Color.BLACK);
    		label.setBorder(border);
    		label.setPreferredSize(new Dimension(550, 100));
    		label.setText("Please select an option below, Test to test, or Search to view the database.");
    		label.setHorizontalAlignment(JLabel.CENTER);
    		label.setVerticalAlignment(JLabel.CENTER);
    		label.setVisible(true);
    		Font font = new Font("SansSerif", Font.ITALIC, 12);
    		label.setFont(font);
			
    		return label;
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();

        if(cmd.equals("Search"))
        {
            dispose();
            new AnagramManagerFrame();
        }
        else if(cmd.equals("Test"))
        {
        		dispose();
        		new Anagram();
        }
      
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run()
            {
                new StartupWindow().setVisible(true);
            }

        });
    }
}
package project.tester;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;


import project.ui.StartupWindow;
import project.ui.AnagramForm;

@SuppressWarnings("serial")
public class Anagram extends JFrame implements ActionListener 
{

    private int id;
    private String firstAnagram;
    private String secondAnagram;
    
    public Anagram() 
    {
        id = 0;
        firstAnagram = "";
        secondAnagram = "";
        try 
        {
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) 
        {
            System.out.println(e);
        }
        setTitle("Anagram Tester");
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(buildButtonPanel(), BorderLayout.SOUTH);
        add(buildLabelPanel());
        
        setSize(300,150);
        setVisible(true);
    }
    
    public JPanel buildButtonPanel() 
    {
        JPanel testPanel = new JPanel();
        
        JButton testButton = new JButton("Test");
        testButton.setToolTipText("Test Anagram");
        testButton.addActionListener((ActionEvent) -> 
        {
            doTestButton();
        });
        testPanel.add(testButton);
        
        JButton returnButton = new JButton("Return");
        returnButton.setToolTipText("eturn Anagram");
        returnButton.addActionListener((ActionEvent) -> 
        {
        		doReturnButton();
        });
        testPanel.add(returnButton);
        
        return testPanel;
    }
    
    private JLabel buildLabelPanel() 
    {
		JLabel label= new JLabel("<html>Please select an option below, Test to test, or Return to return to the welcome screen.<br/>"
				+ "Please follow the prompts to test two anagrams if you select test</html>", SwingConstants.CENTER);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		label.setBorder(border);
		label.setPreferredSize(new Dimension(550, 100));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setVisible(true);
		Font font = new Font("SansSerif", Font.ITALIC, 12);
		label.setFont(font);
		
		return label;
    }
    

    public Anagram(int id, String firstAnagram, String secondAnagram) 
    {
        this.id = id;
        this.firstAnagram = firstAnagram;
        this.secondAnagram = secondAnagram;
    }
    
    public void doTestButton() 
    {
        String testFirstAnagram = JOptionPane.showInputDialog("Enter First Anagram:");
        firstAnagram = testFirstAnagram;
        
        String testSecondAnagram = JOptionPane.showInputDialog("Enter Second Anagram:");
        secondAnagram = testSecondAnagram;
        
        
        boolean isAnagram = Anagram.isAnagramCharArraySort(testFirstAnagram, testSecondAnagram);
        String result = (isAnagram) ? "an Anagram" : "not an Anagram";
        JOptionPane.showMessageDialog(null, 
        		"The words \"" + testFirstAnagram + "\" & \"" + testSecondAnagram + "\" are " + result);
    }
    
    public static boolean isAnagramCharArraySort(String testFirstAnagram, String testSecondAnagram) 
    {
        boolean isAnagram = false;
        boolean fail = false;
        int wordLength = testFirstAnagram.length();
        int anagramLength = testSecondAnagram.length();
        	if (wordLength == 0)
        	{
        		JOptionPane.showMessageDialog(null, 
        			"Please enter two words to be checked");
        		for (int i = 0; i < 4; i++)
        		{
        			fail = true;
        		}
        	}
        
        if (testFirstAnagram != null && testSecondAnagram != null && wordLength == anagramLength && wordLength != 0) 
        {
            char[] wordCharArray = testFirstAnagram.toCharArray();
            char[] anagramCharArray = testSecondAnagram.toCharArray();
            Arrays.sort(wordCharArray);
            Arrays.sort(anagramCharArray);
            isAnagram = Arrays.equals(wordCharArray, anagramCharArray);
            
            if(isAnagram == true) 
            {
            		AnagramForm add;
            		add = new AnagramForm(null, testSecondAnagram, fail);
            		add.doAdd();
            }
        }
        else if (fail == true)
        {
        		
        		JOptionPane.showMessageDialog(null, 
        			"You will be returned to the welcome screen");
        		new StartupWindow();
        }
       return isAnagram;
        
    }

	private void doReturnButton() 
    {
		dispose();
		new StartupWindow();
    }
    
    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }
    
    public void setFirstAnagram(String firstAnagram) 
    {
        this.firstAnagram = firstAnagram;
    }

    public String getFirstAnagram() 
    {
        return firstAnagram;
    }

    public void setSecondAnagram(String secondAnagram) 
    {
        this.secondAnagram = secondAnagram;
    }

    public String getSecondAnagram() 
    {
        return secondAnagram;
    }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
      
    }
	
}
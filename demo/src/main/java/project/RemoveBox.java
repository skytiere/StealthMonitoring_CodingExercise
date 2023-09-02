package project;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;

public class RemoveBox implements ActionListener {

    private JFrame f;
	private JPanel p1, p2, p3;
	private JLabel l1, l2;
	private JButton b1, b2;
    private JTextField tf;
    
    App app = new App();

    private ResultSet resultSet;
	
    public RemoveBox() {
		// container
		// frame
		f = new JFrame("FrontDeskApp");
		
		// panels
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		// components
		// labels
		l1 = new JLabel("Enter the following details:");
        l2 = new JLabel("User ID: ");

        tf = new JTextField("", 10);
		
		// buttons
        b1 = new JButton("Remove Box");
        b2 = new JButton("Back");
	}
	
	public void launchFrame() throws SQLException {
		p1.add(l1);

        l2.setHorizontalAlignment(JLabel.CENTER);
        tf.setHorizontalAlignment(JTextField.CENTER);
		
		// p2.setLayout(new GridLayout(1,2));
		p2.add(l2);
        p2.add(tf);
		
		// p3.setLayout(new GridLayout(1,1));
        p3.add(b1);
        p3.add(b2);
		
		f.add(p1, BorderLayout.NORTH);
		f.add(p2, FlowLayout.CENTER);
		f.add(p3, BorderLayout.SOUTH);
		
		f.pack();
		f.setSize(450,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		
		// events
        // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.addWindowListener(new CloseButton());
        b1.addActionListener(this);
        b2.addActionListener(this);
	}
    
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        try {
            // System.out.println(app.connection.isClosed());
            
            if (source == b1) {
                int userID = Integer.parseInt(tf.getText());
                String sql = "SELECT * FROM Users WHERE UserID = " + userID + ";";
                resultSet = app.statement.executeQuery(sql);
                
                // String sql = "SELECT * FROM Users";
                // System.out.println("Problem is here1");
                
                // System.out.println("Problem is here2");

                // String timeString = resultSet.getString(7);
                String timeString = "";

                while (resultSet.next()) {
                    timeString = resultSet.getString("TimeReleased");
                }

                if (timeString == null) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");	
                    LocalDateTime now = LocalDateTime.now();
                    
                    sql = "UPDATE Users SET TimeReleased = '" + dtf.format(now) + "' WHERE UserID = " + userID + ";" ;

                    app.statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(f, "Box has been removed from storage.", "Notice",
                            JOptionPane.INFORMATION_MESSAGE);
                    tf.setText(null);
                } else {
                    JOptionPane.showMessageDialog(f, "Box has already been removed from storage.", "Notice",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (source == b2) {
                f.dispose();
			    new App().launchFrame();
            }
        } catch (Exception ex) {
			JOptionPane.showMessageDialog(f, "There has been an error.", "Notice", JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
		}

    }
}

package project;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements ActionListener {
    private String firstName, lastName, number, boxSize;

    private JFrame f;
    private JPanel p1, p2;
    private JScrollPane sp;
    private JTextArea ta;
	private JButton b1;
    
    App app = new App();
    ResultSet resultSet;

    public User(String firstName, String lastName, String number, String boxSize) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.boxSize = boxSize;
    }

    // Get Methods
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNumber() {
        return number;
    }

    public String getBoxSize() {
        return boxSize;
    }

    public User()	{
		// container
		// frame
		f = new JFrame("FrontDeskApp");
        
        ta = new JTextArea(16,12);
		
		// panels
		p1 = new JPanel();
		p2 = new JPanel();
		
        sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// components
		// buttons
		b1 = new JButton("Back");
	}
	
	public void launchFrame() throws SQLException {
		ta.setLineWrap(false);
		ta.setEditable(false);
		ta.setText("UserID\tFirstName\t\tLastName\t\tPhoneNumber\t\tBoxSize\t\tTimeStored\t\tTimeReleased\n");
		p1.setLayout(new GridLayout(1,1));
		p1.add(sp);
		
		p2.add(b1);
		
		f.add(p1, BorderLayout.CENTER);
		f.add(p2, BorderLayout.SOUTH);
		
		f.pack();
		f.setSize(1100,400);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		
		// events
        b1.addActionListener(this);
        f.addWindowListener(new CloseButton());
        
        String sql = "SELECT * FROM Users";

        ResultSet resultSet = app.statement.executeQuery(sql);

        int userID;

        String firstName, lastName, phoneNumber, boxSize, timeStored, timeReleased;

        while (resultSet.next()) {
            // count++;
 
            userID = resultSet.getInt("UserID");
            firstName = resultSet.getString("FirstName");
            lastName = resultSet.getString("LastName");
            phoneNumber = resultSet.getString("PhoneNumber");
            boxSize = resultSet.getString("BoxSize");
            timeStored = resultSet.getString("TimeStored");
            timeReleased = resultSet.getString("TimeReleased");

            if (timeReleased == null) {
                timeReleased = "";
            }

            ta.append(userID + "\t" + firstName + "\t\t" + lastName + "\t\t" + phoneNumber + "\t\t" + boxSize + "\t\t" + timeStored + "\t" + timeReleased + "\n");
        }
	}
	
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();

        if (source == b1) {
            f.dispose();
            new App().launchFrame();
        }
    }
    
}

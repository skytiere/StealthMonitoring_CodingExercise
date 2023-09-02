package project;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class App implements ActionListener {
	
	// containers and components
    private JFrame f;
	private JPanel p1, p2, p3;
	private JLabel l1, l2, l3, l4;
	private JButton b1, b2, b3;
	
	// values 
	static int smallStorage = 5, mediumStorage = 3, largeStorage = 2, userID = 1;

	// database
	static Connection connection;
	static Statement statement;
	
	public App() {
		// container
		// frame
		f = new JFrame("FrontDeskApp");
		
		// panels
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		// components
		// labels
		l1 = new JLabel("Current Capacity: ");
		l2 = new JLabel("Small Box Area: " + smallStorage + " free out of 5");
		l3 = new JLabel("Medium Box Area: " + mediumStorage + " free out of 3");
		l4 = new JLabel("Large Box Area: " + largeStorage + " free out of 2");
		
		// buttons
		b1 = new JButton("Store Box");
		b2 = new JButton("Remove Box");
		b3 = new JButton("See Users");
	}
	
	public void launchFrame() {
		// panel 1
		p1.add(l1);

		// panel 2
		l2.setHorizontalAlignment(JLabel.CENTER);
		l3.setHorizontalAlignment(JLabel.CENTER);
		l4.setHorizontalAlignment(JLabel.CENTER);
		
		p2.setLayout(new GridLayout(3, 1));
		p2.add(l2);
		p2.add(l3);
		p2.add(l4);
		
		// panel 3
		p3.add(b1);
		p3.add(b2);
		p3.add(b3);
		
		// frame
		f.add(p1, BorderLayout.NORTH);
		f.add(p2, BorderLayout.CENTER);
		f.add(p3, BorderLayout.SOUTH);
		
		f.pack();
		f.setSize(450,250);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		
		// events
		f.addWindowListener(new CloseButton()); 
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent ae)	{
		Object source = ae.getSource();

		f.dispose();
		if (source == b1) {
			new StoreBox().launchFrame();
		} else if (source == b2) {
			try {
				new RemoveBox().launchFrame();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		} else if (source == b3) {
			try {
				new User().launchFrame();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws SQLException {
		new App().launchFrame();
		connection = DriverManager.getConnection("jdbc:h2:mem:test;MODE=LEGACY");

		System.out.println("Connected to H2 in-memory database.");

		String sql = "CREATE TABLE Users " +
				"(UserID int primary key not null, FirstName varchar(255), LastName varchar(255), PhoneNumber varchar(25), BoxSize varchar(255), TimeStored varchar(30), TimeReleased varchar(30));";

		statement = connection.createStatement();

		statement.execute(sql);
	}
}

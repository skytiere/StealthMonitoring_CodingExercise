package project;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class StoreBox implements ActionListener{

    private JFrame f;
	private JOptionPane op;
	private JPanel p1, p2, p3;
	private JTextField tf1, tf2, tf3;
	private JLabel l1, l2, l3, l4, l5;
	private JButton b1, b2;
	private JComboBox<String> cb;
    
	App app = new App();
	
    public StoreBox() {
		// container
		// frame
		f = new JFrame("FrontDeskApp");
		
		// dialog
		op = new JOptionPane();
		
		// panels
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		// components
		// labels
		l1 = new JLabel("Enter the following details:");
        l2 = new JLabel("First Name: ");
        l3 = new JLabel("Last Name: ");
        l4 = new JLabel("Phone Number: ");
        l5 = new JLabel("Box Size: ");
		
		// text fields 
		tf1 = new JTextField("");
		tf2 = new JTextField("");
		tf3 = new JTextField("");

		// combo box
		String boxArr[] = { "SMALL", "MEDIUM", "LARGE" };
		cb = new JComboBox<String>(boxArr);
		
		// buttons
		b1 = new JButton("Add Box");
		b2 = new JButton("Back");
	}
	
	public void launchFrame() {
		p1.add(l1);

		l2.setHorizontalAlignment(JLabel.CENTER);
		l3.setHorizontalAlignment(JLabel.CENTER);
		l4.setHorizontalAlignment(JLabel.CENTER);
		l5.setHorizontalAlignment(JLabel.CENTER);
		
		p2.setLayout(new GridLayout(4,2));
		p2.add(l2);
		p2.add(tf1);
		p2.add(l3);
		p2.add(tf2);
		p2.add(l4);
		p2.add(tf3);
		p2.add(l5);
		p2.add(cb);
		
		// p3.setLayout(new GridLayout(1,1));
		p3.add(b1);
		p3.add(b2);
		
		f.add(p1, BorderLayout.NORTH);
		f.add(p2, FlowLayout.CENTER);
		f.add(p3, BorderLayout.SOUTH);
		
		f.pack();
		f.setSize(450,250);
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
        String text1 = tf1.getText();
        String text2 = tf2.getText();
        String text3 = tf3.getText();
        String textVar[] = { text1, text2, text3 };
		String boxSize = (String) cb.getSelectedItem();
        // String[] textArray = text.split("");

		// recordList.add(new User(text1, text2, text3, (String) cb.getSelectedItem()));
		
		
		if (source == b1) {
			if (boxSizeChecker(boxSize) != 0) {
				for (int i = 0; i < 3; i++) {
				i = nullChecker(textVar[i], i);
				}

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");	
				LocalDateTime now = LocalDateTime.now();

				String sql = "INSERT INTO Users (UserID, FirstName, LastName, PhoneNumber, BoxSize, TimeStored) VALUES ('"
						+ app.userID + "' , '" + text1.toUpperCase() + "' , '" + text2.toUpperCase() + "' , '" + text3
						+ "' , '" + boxSize + "' , '" + dtf.format(now) + "');";
				app.userID++;
				try {
					app.statement.execute(sql);
					JOptionPane.showMessageDialog(f, "Box has been added to storage.", "Notice",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(f, "There has been an error.", "Notice", JOptionPane.INFORMATION_MESSAGE);
					ex.printStackTrace();
				}

				tf1.setText(null);
				tf2.setText(null);
				tf3.setText(null);
				cb.setSelectedIndex(0);
			} else {
				op.showMessageDialog(f, boxSize + " Boxes Area Full.", "Message", op.INFORMATION_MESSAGE);
			}
			
		} else if (source == b2) {
			f.dispose();
			new App().launchFrame();
		}
    }

	public int boxSizeChecker(String boxSize) {
		App app = new App();

		switch (boxSize) {
			case "SMALL":
				if (app.smallStorage > 0) {
					app.smallStorage--;
					System.out.println(app.smallStorage);
				} else {
					return 0;
				}
				break;
			case "MEDIUM":
				if (app.mediumStorage > 0) {
					app.mediumStorage--;
					System.out.println(app.mediumStorage);
				} else {
					return 0;
				}
				break;
			case "LARGE":
				if (app.largeStorage > 0) {
					app.largeStorage--;
					System.out.println(app.largeStorage);
				} else {
					return 0;
				}
				break;
		}

		return 1;
	}
	
	private int nullChecker(String text, int i) {
		if (text.equals("")) {
            op.showMessageDialog(f, "Please enter values in all fields.", "Message", op.INFORMATION_MESSAGE);
			return i+10;
		} else {
			return i;
		}
	}
}

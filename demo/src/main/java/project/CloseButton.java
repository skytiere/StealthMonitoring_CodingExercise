package project;

import javafx.stage.WindowEvent;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;

public class CloseButton extends WindowAdapter {
    public void windowClosing(WindowEvent we) throws SQLException {
        new App().connection.close();
        System.out.println("Connection Closed");
		System.exit(0);	// Ends the Program
	}
}

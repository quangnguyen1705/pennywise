module sjsu.edu.pennywise {
	 requires javafx.controls;
	 requires javafx.fxml;
	 requires java.sql;
	 requires javafx.base;
	requires javafx.graphics;
	 exports sjsu.edu.pennywise;
	 exports sjsu.edu.pennywise.Controllers;
	 opens sjsu.edu.pennywise.Controllers to javafx.fxml;
}

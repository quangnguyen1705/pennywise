module sjsu.edu.pennywise {
	 requires javafx.controls;
	 requires javafx.fxml;
	 requires java.sql;
	 requires javafx.base;
	requires javafx.graphics;
	 exports sjsu.edu.application;
	 exports sjsu.edu.application.Controllers;
	 opens sjsu.edu.application.Controllers to javafx.fxml;
}

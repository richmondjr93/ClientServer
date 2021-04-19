package application;
	
import java.awt.Checkbox;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private CheckBox select;

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			this.select = new CheckBox();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public CheckBox getSelect()
	{
		return select;
	}
	
	public void setSelect(CheckBox select) {
		this.select = select;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package Client.GUI.States;

import Client.ClientConstants;
import Client.MainWindow;
import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDraw;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Credits extends GameState implements IDraw {	
	
	private GridPane root;
	private Button btnTest;
	
	public static Credits create(StateManager manager) {
		return new Credits(new GridPane(), ClientConstants.SCREEN_WIDTH, ClientConstants.SCREEN_HEIGHT, manager);
	}
	
	private Credits(GridPane root, float width, float height, StateManager manager) {
		super(root, width, height, manager);	
		
		this.root = root;
		this.root.setBackground(new Background(new BackgroundFill(Paint.valueOf(Color.CORNFLOWERBLUE.toString()), new CornerRadii(0), new Insets(0))));	
		
		this.btnTest = new Button("Klick mich!");
		this.root.getChildren().add(this.btnTest);
		this.btnTest.setOnAction(new EventHandler<ActionEvent>() {
			 
            @Override
            public void handle(ActionEvent event) {
            	Credits menu = (Credits) ((Button) event.getSource()).getScene();
            	menu.manager.pop();
            }
        });
	}

	@Override
	public void draw(float elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entered() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leaving() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void obscuring() {
		// TODO Auto-generated method stub
	}

	@Override
	public void revealed() {
		// TODO Auto-generated method stub
	}
	
}

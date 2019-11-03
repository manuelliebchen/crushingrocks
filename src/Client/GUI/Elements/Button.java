package Client.GUI.Elements;

import Client.InputManager;
import Client.InputManager.InputMouseListener;
import Client.InputManager.MouseEventType;
import Client.InputManager.MouseKeyEventType;
import Client.Rendering.Drawing.ImageManager;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 * Image buttons with customs images and text.
 * Registers if a user pressed on it.
 */
public final class Button implements InputMouseListener {

	public enum ButtonAlignment { CENTER, LEFT, RIGHT, UP, DOWN}
	
	//Button status
	boolean isEnabled = true;

	//Drawing status
	private String buttonText;
	private Point2D centeredPositioOffset;
	private Color textColor;
	private Point2D relativPosition;
	private Point2D position;
	private Point2D size;
	private Image imgUp;
	private Image imgDown;
	private Image imgInActive;
	private Font font = Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 24);
	private ButtonAlignment verticalAlignment = ButtonAlignment.LEFT;
	private ButtonAlignment horizontalAlignment = ButtonAlignment.UP;
	
	//Mouse status
	private boolean mousePressed = false;

	
	/**
	 * Default button constructor with default buttons images and text color (black).
	 * @param position The position where the button will be drawn (top-left).
	 * @param size The size of the button (e.g. image size).
	 * @param buttonText The Text, which will be displayed on the button.
	 */
	public Button(Point2D relativPosition, Point2D size, String buttonText){
		this(relativPosition, size, buttonText, ButtonAlignment.LEFT, ButtonAlignment.UP);
	}

	
	/**
	 * Default button constructor with default buttons images and text color (black).
	 * @param position The position where the button will be drawn (top-left).
	 * @param size The size of the button (e.g. image size).
	 * @param buttonText The Text, which will be displayed on the button.
	 */
	public Button(Point2D relativPosition, Point2D size, String buttonText, ButtonAlignment verticalAlignment, ButtonAlignment horizontalAlignment){
		this(relativPosition, size, buttonText, ImageManager.getInstance().loadImage("buttons/defaultButtonUp.png"), ImageManager.getInstance().loadImage("buttons/defaultButtonDown.png"), ImageManager.getInstance().loadImage("buttons/defaultButtonInActive.png"), Color.BLACK, verticalAlignment, horizontalAlignment);
	}
	
	/**
	 * Complex Button constructor with default buttons and text color.
	 * @param position The position where the button will be drawn (top-left).
	 * @param size The size of the button (e.g. image size).
	 * @param buttonText The Text, which will be displayed on the button.
	 * @param imageButtonUp The image of the button which will be shown if there is no hover (default view).
	 * @param imageButtonDown The image of the button which will be shown if there is a hover (mouse hover view).
	 * @param imageInActive The image of the button which will be shown if the button is disabled.
	 * @param textColor The text color of the button
	 */
	public Button(Point2D relativPosition, Point2D size, String buttonText, Image imageButtonUp, Image imageButtonDown, Image imageInActive, Color textColor, ButtonAlignment verticalAlignment, ButtonAlignment horizontalAlignment){
		this.buttonText = buttonText;
		this.relativPosition = relativPosition;
		this.position = relativPosition;
		this.size = size;
		this.imgUp = imageButtonUp;
		this.imgDown = imageButtonDown;
		this.imgInActive = imageInActive;
		this.textColor = textColor;
		
		InputManager.get().addMouseKeyListener(this);
		calcButtonTextProperties();
		this.verticalAlignment = verticalAlignment;
		this.horizontalAlignment = horizontalAlignment;
	}
	/**
	 * Updates buttonTextSize and centeredPosition if font oder text has changed
	 * @see changeFont(Font font)
	 * @see changeText(String buttonText)
	 */
	private void calcButtonTextProperties(){
		Text text = new Text(buttonText);
		text.setFont(font);
		
		Point2D buttonTextSize = new Point2D (text.getLayoutBounds().getWidth(),text.getLayoutBounds().getHeight());		
		centeredPositioOffset = new Point2D((size.getX() - buttonTextSize.getX())/2,size.getY()/2 + buttonTextSize.getY()/4);
	}
	/**
	 * Change font of button.
	 * Updates text properties
	 * @see calcButtonTextProperties()
	 * @param font 
	 */
	public void changeFont(Font font){
		this.font = font;
		calcButtonTextProperties();
		
	}
	/**
	 * Change text of button.
	 * Updates text properties
	 * @see calcButtonTextProperties()
	 * @param buttonText 
	 */
	public void changeText(String buttonText){
		this.buttonText = buttonText;
		calcButtonTextProperties();
	}
	/**
	 * Change color of button text.
	 * @param color 
	 */
	public void changeTextColor(Color color){
		this.textColor = color;
	}

	/**
	 * Changes if the button is enabled or not.
	 * If not -> show button image: imageInActive and isPressed() is false.
	 * @see isPressed()
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.isEnabled = enabled;
	}
	
	/**
	 * Returns true if the button isEnable and the user pressed on it.
	 * @return true or false.
	 */
	public boolean isPressed() {
		return this.isEnabled && isMouseOver() && mousePressed;
	}
	/**
	 * Checks if the mouse hovered the button 
	 * @return true or false
	 */
	private boolean isMouseOver() {
		Point2D pos = InputManager.get().getMousePosition();
		return pos.getX() >= position.getX() && pos.getX() <= position.getX()+size.getX() && pos.getY() >= position.getY() && pos.getY() <= position.getY() + size.getY();
	}
	/**
	 * Draws the button. First button image (imgUp,imgDown,imgInActive). Second: button text
	 * @param graphics
	 */
	public void draw(GraphicsContext graphics){
		
		Canvas canvas = graphics.getCanvas();
		
		double drawingPositionX = 0;
		double drawingPositionY = 0;
		
		if(verticalAlignment == ButtonAlignment.LEFT) {
			drawingPositionX = relativPosition.getX();
		} else if(verticalAlignment == ButtonAlignment.CENTER) {
			drawingPositionX = (relativPosition.getX() + canvas.getWidth()) / 2;
		} else if(verticalAlignment == ButtonAlignment.RIGHT) {
			drawingPositionX = (canvas.getWidth() - relativPosition.getX());
		}
		
		if(horizontalAlignment == ButtonAlignment.UP) {
			drawingPositionY = relativPosition.getY();
		} else if(horizontalAlignment == ButtonAlignment.CENTER) {
			drawingPositionY = (relativPosition.getY() + canvas.getHeight()) / 2;
		} else if(horizontalAlignment == ButtonAlignment.DOWN) {
			drawingPositionY = (canvas.getHeight() - relativPosition.getY());
		}
		
		position = new Point2D(drawingPositionX, drawingPositionY);
		
		if(!this.isEnabled) {
			graphics.drawImage(imgInActive, position.getX(), position.getY(), size.getX(), size.getY());
		} else if(isMouseOver()){
			graphics.drawImage(imgDown, position.getX(), position.getY(), size.getX(), size.getY());
		}
		else{
			graphics.drawImage(imgUp, position.getX(), position.getY(), size.getX(), size.getY());
		}

		
		graphics.setFont(font);
		graphics.setFill(textColor);
		graphics.fillText(buttonText, position.getX() + centeredPositioOffset.getX(), position.getY() + centeredPositioOffset.getY());

	}
	/**
 	* Get the position of the button
 	* @return position
 	*/
	public Point2D getPosition() {
		return position;
	}
	/**
 	* Get the size of the button
 	* @return size
 	*/
	public Point2D getSize() {
		return size;
	}
	/**
	 * Changes state of mousePressed (LeftButton pressed: mousePressed=true, LeftButton released: mousePressed=false)
	 */
	@Override
	public void mouseKeyEvent(MouseKeyEventType type, MouseButton code) {
		if(type == MouseKeyEventType.MOUSE_RELEASED){
			if(code == MouseButton.PRIMARY){
				mousePressed = false;
			}
		}
		else if(type == MouseKeyEventType.MOUSE_PRESSED){
			if(code == MouseButton.PRIMARY){
				mousePressed = true;
			}
		}
		
	}
	@Override
	public void mouseEvent(MouseEventType type) {
	}
	
	
	
}

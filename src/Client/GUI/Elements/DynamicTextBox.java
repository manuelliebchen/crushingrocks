package Client.GUI.Elements;

import java.util.function.Supplier;

import Client.GUI.States.Interfaces.IDrawable;
import Constants.DesignConstants;
import Constants.DesignConstants.Alignment;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Dynamic TextBox.
 * 
 * @author Manuel Liebchen
 */
public final class DynamicTextBox implements IDrawable {

	// Drawing status
	private Supplier<String> textSupplier;
	private Color textColor = DesignConstants.FOREGROUND_COLOR;
	private Point2D relativPosition;
	private Point2D size;
	private Alignment verticalAlignment = Alignment.LEFT;
	private Alignment horizontalAlignment = Alignment.TOP;
	private Font font = DesignConstants.STANDART_FONT;

	/**
	 * Default button constructor with default buttons images and text color
	 * (black).
	 * 
	 * @param position   The position where the button will be drawn (top-left).
	 * @param size       The size of the button (e.g. image size).
	 * @param buttonText The Text, which will be displayed on the button.
	 */
	public DynamicTextBox(Point2D relativPosition, Supplier<String> textSupplier) {
		this.textSupplier = textSupplier;
		this.relativPosition = relativPosition;

	}

	/**
	 * Updates buttonTextSize and centeredPosition if font oder text has changed
	 * 
	 * @see changeFont(Font font)
	 * @see changeText(String buttonText)
	 */
	private void calcFontTextSize() {
		Text text = new Text(textSupplier.get());
		text.setFont(font);

		size = new Point2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
	}
	
	public DynamicTextBox setVerticalAlignment(Alignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
		return this;
	}
	
	public DynamicTextBox setHorizontalAlignment(Alignment horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		return this;
	}
	
	public DynamicTextBox setTextColor(Color textColor) {
		this.textColor = textColor;
		return this;
	}
	
	public DynamicTextBox setFont(Font font) {
		this.font = font;
		calcFontTextSize();
		return this;
	}

	/**
	 * Change color of button text.
	 * 
	 * @param color
	 */
	public void changeTextColor(Color color) {
		this.textColor = color;
	}

	/**
	 * Draws the button. First button image (imgUp,imgDown,imgInActive). Second:
	 * button text
	 * 
	 * @param graphics
	 */
	public void draw(GraphicsContext graphics) {
		calcFontTextSize();

		Canvas canvas = graphics.getCanvas();

		double drawingPositionX = 0;
		double drawingPositionY = 0;

		if (verticalAlignment == Alignment.LEFT) {
			drawingPositionX = relativPosition.getX();
		} else if (verticalAlignment == Alignment.CENTER) {
			drawingPositionX = (relativPosition.getX() + canvas.getWidth() - size.getX()) / 2;
		} else if (verticalAlignment == Alignment.RIGHT) {
			drawingPositionX = (canvas.getWidth() - relativPosition.getX() - size.getX());
		}

		if (horizontalAlignment == Alignment.TOP) {
			drawingPositionY = relativPosition.getY() + size.getY();
		} else if (horizontalAlignment == Alignment.CENTER) {
			drawingPositionY = (relativPosition.getY() + canvas.getHeight() - size.getY()) / 2;
		} else if (horizontalAlignment == Alignment.BOTTOM) {
			drawingPositionY = canvas.getHeight() - relativPosition.getY() - size.getY();
		}

		graphics.setFont(font);
		graphics.setFill(textColor);
		graphics.fillText(textSupplier.get(), drawingPositionX, drawingPositionY);
	}
}

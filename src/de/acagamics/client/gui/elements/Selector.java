package de.acagamics.client.gui.elements;

import java.util.function.IntFunction;

import de.acagamics.client.gui.elements.Button.BUTTON_TYPE;
import de.acagamics.client.gui.interfaces.IClickable;
import de.acagamics.constants.DesignConstants.ALINGNMENT;
import de.acagamics.game.types.Vec2f;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Selector implements IClickable {

	private int value;

	private Button minusButton;
	private Button plusButton;
	private DynamicTextBox textbox;

	public Selector(Vec2f position, int width, int minValue, int maxValue, IntFunction<String> textGenerator) {
		value = minValue;
		minusButton = new Button(new Vec2f(position.getX() - width, position.getY()), BUTTON_TYPE.SQUARE, "<",
				() -> value = (value - 1) < minValue ? maxValue : (value - 1));
		textbox = new DynamicTextBox(position, () -> textGenerator.apply(value));
		plusButton = new Button(new Vec2f(position.getX() + width, position.getY()), BUTTON_TYPE.SQUARE, ">",
				() -> value = (value + 1) > maxValue ? minValue : (value + 1));

	}

	public Selector setVerticalAlignment(ALINGNMENT verticalAlignment) {
		minusButton.setVerticalAlignment(verticalAlignment);
		textbox.setVerticalAlignment(verticalAlignment);
		plusButton.setVerticalAlignment(verticalAlignment);
		return this;
	}

	public Selector setHorizontalAlignment(ALINGNMENT horizontalAlignment) {
		minusButton.setHorizontalAlignment(horizontalAlignment);
		textbox.setHorizontalAlignment(horizontalAlignment);
		plusButton.setHorizontalAlignment(horizontalAlignment);
		return this;
	}

	public Selector setTextColor(Color textColor) {
		minusButton.setTextColor(textColor);
		textbox.setTextColor(textColor);
		plusButton.setTextColor(textColor);
		return this;
	}

	public Selector setEnabled(boolean enabled) {
		minusButton.setEnabled(enabled);
		plusButton.setEnabled(enabled);
		return this;
	}

	public Selector setKeyCode(KeyCode minusKey, KeyCode plusKey) {
		minusButton.setKeyCode(minusKey);
		plusButton.setKeyCode(plusKey);
		return this;
	}

	public int getValue() {
		return value;
	}

	@Override
	public void handle(InputEvent event) {
		minusButton.handle(event);
		plusButton.handle(event);
	}

	@Override
	public void draw(GraphicsContext context) {
		minusButton.draw(context);
		plusButton.draw(context);
		textbox.draw(context);
	}

}

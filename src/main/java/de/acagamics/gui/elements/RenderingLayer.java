package de.acagamics.gui.elements;

import java.util.ArrayList;

import de.acagamics.gui.interfaces.GUIElement;
import de.acagamics.gui.interfaces.IDrawable;
import javafx.scene.canvas.GraphicsContext;

public class RenderingLayer extends ArrayList<GUIElement> implements IDrawable{
	private static final long serialVersionUID = -70755726171419444L;

	@Override
	public void draw(GraphicsContext context) {
		for(IDrawable drawable : this) {
			drawable.draw(context);
		}
	}
}

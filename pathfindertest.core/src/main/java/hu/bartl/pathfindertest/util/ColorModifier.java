package hu.bartl.pathfindertest.util;

import java.awt.Color;

import com.google.inject.Singleton;

@Singleton
public class ColorModifier {
	public Color modifyColor(Color baseColor, int modifier) {
		int red = modifyColorComponent(baseColor.getRed(), modifier);
		int green = modifyColorComponent(baseColor.getGreen(), modifier);
		int blue = modifyColorComponent(baseColor.getBlue(), modifier);
		return new Color(red, green, blue);
	}

	private int modifyColorComponent(int colorComponent, int modifier) {
		modifier = Math.abs(modifier - 6);
		if (modifier == 0) {
			return colorComponent;
		}

		modifier = modifier + 40;

		if (colorComponent + modifier <= 255 && colorComponent + modifier >= 0) {
			return colorComponent + modifier;
		}

		if (colorComponent - modifier > 0) {
			return colorComponent - modifier;
		}

		if (Math.abs(modifier) <= 255) {
			return Math.abs(modifier);
		}

		return colorComponent;
	}
}

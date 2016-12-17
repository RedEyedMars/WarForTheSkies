package com.rem.core.gui.graphics;

public class R {
	
	public static final int DRAW_MODE_VERTICES = 0;
	public static final int DRAW_MODE_LINE = 1;
	
	/**
	 * The id of the top draw layer, no other layer will cover this layer of {@link com.rem.core.gui.graphics.elements.GraphicElement}'s.
	 */
	public static final int TOP_LAYER = 2;
	/**
	 * The id of the middle draw layer, any {@link com.rem.core.gui.graphics.elements.GraphicElement} in the top layer will cover elements in this layer, additionally any elements in this layer will cover any elements in the bototm layer.
	 */
	public static final int MID_LAYER = 1;
	/**
	 * The id of the bottom draw layer, any {@link com.rem.core.gui.graphics.elements.GraphicElement} in the above layers will cover the elements in this layer.
	 */
	public static final int BOT_LAYER = 0;

	public static final int COLOUR_WHITE = 0;
	public static final int COLOUR_BLACK = 1;
	public static final int COLOUR_RED = 2;
	public static final int COLOUR_GREEN = 3;
	public static final int COLOUR_BLUE = 4;
	public static final int COLOUR_PURPLE = 5;
	public static final int COLOUR_YELLOW = 6;
	public static final int COLOUR_CYAN = 7;
	
	public static int arial;
	public static int impact;
	public static int blank;
	public static int solid_colour;
	public static int speech_bubble;
	public static int music_player_icons;
}

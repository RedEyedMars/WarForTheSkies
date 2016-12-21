package com.rem.core.gui.music;

import java.util.Collections;

import com.rem.core.gui.graphics.MenuButton;
import com.rem.core.gui.graphics.R;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;

public class MusicPlayer extends MenuButton {

	private static final float MAXIMIZED_WIDTH = 0.26f;
	private static final float MINIMIZED_WIDTH = 0.08f;
	private static final float MAXIMIZED_HEIGHT = 0.16f;
	private static final float MINIMIZED_HEIGHT = 0.08f;
	private static final boolean MINIMIZE = true;
	private static final boolean MAXIMIZE = false;
	private ITrack currentTrack;
	private int index;
	private boolean playing = true;	
	private boolean minimized = true;

	private GraphicElement minimizeButton;
	private GraphicElement playPauseButton;
	private GraphicElement skipButton;

	private GraphicElement volumeAdjuster;
	private float volume = 0.8f;

	public MusicPlayer(){
		this(null);
	}
	public MusicPlayer(ITrack track) {
		super("");
		if(Library.tracks.size()==0)return;
		Collections.shuffle(Library.tracks);
		if(track == null){
			currentTrack = Library.tracks.get(0);
			index = 0;
		}
		else {
			currentTrack = track;
			index = Library.tracks.indexOf(track);
		}
		text.setFontSize(GraphicText.FONT_SIZE_REGULAR);
		playPauseButton = new GraphicElement(R.music_player_icons,2,R.MID_LAYER){
			@Override
			public boolean onClick(ClickEvent event){
				if(super.onClick(event)){
					if(event.getAction()==ClickEvent.ACTION_UP){
						togglePlayPause();
					}
				return true;
				}
				else return false;
			}
		};
		tree.addChild(playPauseButton);

		skipButton = new GraphicElement(R.music_player_icons,3,R.MID_LAYER){
			@Override
			public boolean onClick(ClickEvent event){
				if(super.onClick(event)){
					if(event.getAction()==ClickEvent.ACTION_UP){
						next();
					}
					return true;
				}
				else return false;
			}
		};
		skipButton.setFrame(3);
		tree.addChild(skipButton);

		minimizeButton = new GraphicElement(R.music_player_icons,0,R.MID_LAYER);
		tree.addChild(minimizeButton);

		volumeAdjuster = 
				new GraphicElement(R.solid_colour,R.COLOUR_BLACK,R.MID_LAYER){
			private GraphicElement indicator = new GraphicElement(
					R.solid_colour,R.COLOUR_YELLOW,R.MID_LAYER);
			{
				indicator.setShape(GraphicElement.SHAPE_BOTTOM_RIGHT_TRIANGLE);
				tree.addChild(indicator);
			}


			@Override
			public boolean onClick(ClickEvent event){
				if(super.onClick(event)){
					if(event.getAction()==ClickEvent.ACTION_UP){
						volume = (event.getX()-dim.getX())*0.8f/dim.getWidth();
						currentTrack.adjustVolume(volume);
						resize(dim.getWidth(),dim.getHeight());
					}
					return true;
				}
				else return false;
			}

			@Override
			public void resize(float x, float y){
				super.resize(x, y);
				if(indicator!=null){
					indicator.resize(x*volume/0.8f, y*volume/0.8f);
				}
			}
		};
		volumeAdjuster.setShape(GraphicElement.SHAPE_BOTTOM_RIGHT_TRIANGLE);
		tree.addChild(volumeAdjuster);

		minmax(MINIMIZE);
		currentTrack.play(volume);
		playing = true;

		resize(MINIMIZED_WIDTH,MINIMIZED_WIDTH);
		reposition(0.03f,0.88f);

		for(GraphicElement child:tree){
			child.setLayer(R.TOP_LAYER);
		}
	}

	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element == playPauseButton){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return element.dim.getWidth()*0f+0.01f;
				}
				@Override
				public float getY(){
					return 0.005f;
				}
			};
		}
		else if(element == skipButton){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return element.dim.getWidth()*1f+0.01f;
				}
				@Override
				public float getY(){
					return 0.005f;
				}
			};
		}
		else if(element == volumeAdjuster){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return element.dim.getWidth()*2f+0.015f;
				}
				@Override
				public float getY(){
					return 0.015f;
				}
			};
		}
		else if(element == minimizeButton){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return dim.getWidth()-element.dim.getWidth();
				}
				@Override
				public float getY(){
					return 0.005f;
				}
			};
		}
		else if(element == text){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return 0.01f;
				}
				@Override
				public float getY(){
					return 0.015f+(MAXIMIZED_HEIGHT-MINIMIZED_HEIGHT);
				}
			};
		}
		else return super.createOffsetHandler(element);
	}
	
	@Override
	public void resize(float x, float y){
		super.resize(x, y);
		tree.getChild(0).resize(0.045f, y);
		tree.getChild(2).resize(0.045f, y);
		tree.getChild(1).resize(x-MINIMIZED_WIDTH, y);

		if(playPauseButton!=null)
			playPauseButton.resize(0.05f, 0.07f);
		if(minimizeButton!=null)
			minimizeButton.resize(0.07f, 0.07f);
		if(skipButton!=null)
			skipButton.resize(0.05f, 0.07f);
		if(volumeAdjuster!=null)
			volumeAdjuster.resize(0.07f, 0.05f);
	}


	public void next(){
		if(playing&&!currentTrack.isFinished()){
			currentTrack.skip();
		}
		else {
			currentTrack.reset();
		}
		if(index<Library.tracks.size()-1){
			++index;
		}
		else {
			ITrack track = Library.tracks.get(index);
			do{
				Collections.shuffle(Library.tracks);
			} while(track==Library.tracks.get(0));
			index = 0;
		}
		currentTrack = Library.tracks.get(index);
		currentTrack.play(volume);

		scroller = 0;
		playing = true;
		playPauseButton.setFrame(2);//pause
	}

	public void adjustVolume(float newVolume) {
		this.volume = newVolume;
		if(playing&&currentTrack!=null){
			currentTrack.adjustVolume(newVolume);
		}
	}
	public boolean pause() {
		if(playing){
			currentTrack.pause();
			playing = false;
			playPauseButton.setFrame(1);//play
			return true;
		}
		else return false;
	}
	public void unpause(){
		if(!playing){
			currentTrack.play(volume);
			playPauseButton.setFrame(2);//pause

			playing = true;
		}
	}
	public void togglePlayPause(){
		if(playing){
			pause();
		}
		else {
			unpause();
		}
	}

	private void minmax(boolean minimize){
		minimized = minimize;
		minimizeButton.setVisible(minimize);
		playPauseButton.setVisible(!minimize);
		skipButton.setVisible(!minimize);
		volumeAdjuster.setVisible(!minimize);
		text.setVisible(!minimize);
		if(minimize){
			resize(MINIMIZED_WIDTH,MINIMIZED_HEIGHT);
			reposition(dim.getX(),dim.getY()+(MAXIMIZED_HEIGHT-MINIMIZED_HEIGHT)/2f);
		}
		else {
			resize(MAXIMIZED_WIDTH,MAXIMIZED_HEIGHT);
			reposition(dim.getX(),dim.getY()-(MAXIMIZED_HEIGHT-MINIMIZED_HEIGHT)/2f);
		}
		reposition(dim.getX(),dim.getY());
	}

	@Override
	public boolean onClick(ClickEvent e){
		if(!minimized){
			return super.onClick(e);
		}
		else return onHover(new HoverEvent(e.getX(),e.getY()));
	}

	@Override
	public boolean onHover(HoverEvent e){
		if(isWithin(e.getX(),e.getY())){
			if(minimized){
				minmax(MAXIMIZE);
			}
			return true;
		}
		else {
			if(!minimized){
				minmax(MINIMIZE);
			}
			return false;
		}
	}

	int scroller = 0;
	@Override
	public void animate(){
		if(!minimized){
			String suffix = "";
			String prefix = "";
			if(scroller<currentTrack.getFullName().length()-1){
				++scroller;
			}
			else {
				scroller = 0;
			}
			if(scroller>=currentTrack.getFullName().length()-16){
				prefix = currentTrack.getFullName().substring(scroller, currentTrack.getFullName().length());
				suffix = "//"+currentTrack.getFullName().substring(0, scroller-(currentTrack.getFullName().length()-16));
			}
			else {
				prefix = currentTrack.getFullName().substring(scroller, scroller+16);
			}
			text.change(prefix+suffix);
		}
		if(currentTrack.isFinished()){
			next();
		}
	}
	public boolean isMaximized() {
		return !minimized;
	}
	public boolean isPlaying() {
		return playing;
	}
	public float getVolume() {
		return volume;
	}
}

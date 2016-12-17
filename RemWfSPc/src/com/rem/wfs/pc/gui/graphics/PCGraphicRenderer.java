package com.rem.wfs.pc.gui.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.rem.core.gui.graphics.GraphicRenderer;
import com.rem.core.gui.graphics.VisualBundle;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.wfs.pc.gui.gl.GLApp;
import com.rem.wfs.pc.gui.gl.GLImage;

public class PCGraphicRenderer extends GraphicRenderer{
	private float[] cameraPos = new float[]{0f, 0f, 1f};
	private float[] cameraLook = new float[]{0f, 0f, 0f};
	private int previousTexture = -1;
	@Override
	public void setupRenderCycle(){
		// clear depth buffer and color buffers
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		// select model view for subsequent transforms
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		// set the viewpoint
		GLU.gluLookAt(cameraPos[0], cameraPos[1], cameraPos[2],  // where is the eye
				cameraLook[0], cameraLook[1], cameraLook[2],    // what point are we looking at
				0f, 1f, 0f);    // which way is up

		//GL11.glTranslatef(-0f, -0f, -1f);
		GL11.glTranslatef(-0.7521f+viewX, -0.565f+viewY, -1.107f+viewZ);
		GL11.glScalef(1.504f, 1.12875f, 1f);
		//GL11.glScalef(0.5f, 0.5f, 1f);
		animate = System.currentTimeMillis()-last>animationInterval;
		if(animate){
			last = System.currentTimeMillis();
		}
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glFrontFace(GL11.GL_CW);

		GL11.glPushMatrix();
	}
	
	@Override
	public void cleanupRenderCycle(){		
		GL11.glPopMatrix();
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	}

	@Override
	public void bindTexture(GraphicElement d){
		if(previousTexture != texMap[d.getTexture()]){
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texMap[d.getTexture()]);
			previousTexture = texMap[d.getTexture()];
		}
		GL11.glTexCoordPointer(2, 0, d.shape().getTextureBuffer(sizMap[d.getTexture()])[d.getFrame()]);
	}

	@Override
	protected void createFont(int texId, String fontName, int fontStyle, int size, float[] foreground, float[] background) {
		GLImage image = new GLImage(createCharImage(
				texId, 
				new Font(fontName, fontStyle, size),size, foreground, background));
		int tex = GLApp.makeTexture(image);
		addTexture(texId,tex,16,16);
	}


	/**
	 * return a BufferedImage containing the given character drawn with the given font.
	 * Character will be drawn on its baseline, and centered horizontally in the image.
	 * 
	 * @param text     a single character to render
	 * @param font     the font to render with
	 * @param fgColor  foreground (text) color as rgb or rgba values in range 0-1
	 * @param bgColor  background color as rgb or rgba values in range 0-1 (set alpha to 0 to make transparent)
	 * @return
	 */
	public BufferedImage createCharImage(int fontName, Font font, int size, float[] fgColor, float[] bgColor) {
		Color bg = bgColor==null? new Color(0,0,0,0) : (bgColor.length==3? new Color(bgColor[0],bgColor[1],bgColor[2],1) : new Color(bgColor[0],bgColor[1],bgColor[2],bgColor[3]));
		Color fg = fgColor==null? new Color(1,1,1,1) : (fgColor.length==3? new Color(fgColor[0],fgColor[1],fgColor[2],1) : new Color(fgColor[0],fgColor[1],fgColor[2],fgColor[3]));
		boolean isAntiAliased = true;
		boolean usesFractionalMetrics = false;

		// get size of texture image needed to hold largest character of this font
		//int maxCharSize = getFontSize(font);
		int imgSizeW = size*16;
		int imgSizeH = size*16;
		if (imgSizeW > 2048) {
			System.err.println("GLFont.createCharImage(): texture size will be too big (" + imgSizeW + ") Make the font size smaller.");
			return null;
		}

		// we'll draw text into this image
		BufferedImage image = new BufferedImage(imgSizeW, imgSizeH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();

		// Clear image with background color (make transparent if color has alpha value)
		if (bg.getAlpha() < 255) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, (float)bg.getAlpha()/255f));
		}
		g.setColor(bg);
		g.fillRect(0,0,imgSizeW,imgSizeH);

		// prepare to draw character in foreground color
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g.setColor(fg);
		g.setFont(font);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, isAntiAliased? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, usesFractionalMetrics? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		// place the character (on baseline, centered horizontally)
		FontMetrics fm = g.getFontMetrics();
		int cwidth = fm.charWidth('M');
		int height = fm.getHeight();
		int ascent = fm.getAscent();
		int hborder = 2;
		int vborder = height-ascent/2-1;

		char[] data = new char[128];
		for(int i=0;i<128;++i){
			data[i]=((char)i);
		}
		int index = 0;
		for(int y=0;y<8;++y){
			for(int x=0;x<16;++x){
				letterWidths.get(fontName).add((float) (fm.charWidth(data[index]))/cwidth);
				g.drawChars(data, index, 1, hborder+x*size, vborder+y*size+1);
				++index;
			}
		}

		g.dispose();
		return image;
	}

	@Override
	public void drawVisual(VisualBundle visual) {
		GL11.glPushMatrix();
		GL11.glTranslatef(visual.getX(), visual.getY(), 0.0f);
		if(visual.getAngle()!=0f){
			GL11.glTranslatef(visual.getWidth()/2f, visual.getHeight()/2f, 0.0f);
			GL11.glRotatef(visual.getAngle(), 0, 0, 1);	
			GL11.glTranslatef(-visual.getWidth()/2f, -visual.getHeight()/2f, 0.0f);
		}
		GL11.glScalef(visual.getWidth(), visual.getHeight(), 1f);
		GL11.glVertexPointer(3, 0, visual.getVertexBuffer());
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, visual.getNumberOfVertices());
		GL11.glPopMatrix();
	}

	@Override
	public void changeDrawMode(int oldMode, int newMode) {
		if(oldMode==R.DRAW_MODE_VERTICES){
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
		}
		if(newMode==R.DRAW_MODE_VERTICES){
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LIGHTING);
		}
		else if(newMode==R.DRAW_MODE_LINE){
			GL11.glLineWidth(2);
		}
		
		
	}
}

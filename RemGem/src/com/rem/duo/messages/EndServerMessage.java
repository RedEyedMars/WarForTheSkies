package com.rem.duo.messages;

import com.rem.duo.MessageHandler;

/**
 * Exclusively used to tell the Server to close. This can be used by a Host that has created it's own private Server using
 * java -jar server.jar to try to end that Server peacefully.
 * @author Geoffrey
 *
 */
public class EndServerMessage extends Message{
	//For Message sending.
	private static final long serialVersionUID = 3793857596783606873L;

	/**
	 * Does nothing on {@link com.rem.duo.client.Client} side receiving this {@link duo.messages.Mesasge}.
	 */
	@Override
	public void act(MessageHandler handler) {
	}

}

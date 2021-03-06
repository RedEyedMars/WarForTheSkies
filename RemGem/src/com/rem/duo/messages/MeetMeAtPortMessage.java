package com.rem.duo.messages;

import com.rem.duo.MessageHandler;

/**
 * Sent by the Server, this {@link com.rem.duo.messages.Message} is for linking the Client not to the main service port (8000),
 * but to the designated exclusive port for this {@link com.rem.duo.client.Client}'s use only.
 * @author Geoffrey
 *
 */
public class MeetMeAtPortMessage extends Message{
	//For Message sending.
	private static final long serialVersionUID = -5324638348684615251L;

	//The port to meet at, this port is on the server and is opened for this client to use in future communications.
	private int port;
	
	/**
	 * When the {@link com.rem.duo.client.Client} receives this message, it uses the port number contained for the {@link com.rem.duo.MessageHandler} to set itself up with the Server.
	 */
	@Override
	public void act(MessageHandler handler) {
		//Set's up the handler based on the port number provided.
		handler.setup(port);
	}

}

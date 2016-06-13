package de.hsb.smaevers.ws;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class ChatMessageEncoder implements javax.websocket.Encoder.Text<ChatMessage>{

	private Gson gson;
	
	@Override
	public void destroy() {

	}

	@Override
	public void init(EndpointConfig arg0) {
		gson = new Gson();
	}

	@Override
	public String encode(ChatMessage msg) throws EncodeException {
		return gson.toJson(msg);
	}

}

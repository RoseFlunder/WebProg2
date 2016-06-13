package de.hsb.smaevers.ws;

import javax.websocket.DecodeException;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class ChatMessageDecoder implements javax.websocket.Decoder.Text<ChatMessage>{

	private Gson gson;
	
	@Override
	public void destroy() {

	}

	@Override
	public void init(EndpointConfig arg0) {
		gson = new Gson();
	}

	@Override
	public ChatMessage decode(String s) throws DecodeException {
		return gson.fromJson(s, ChatMessage.class);
	}

	@Override
	public boolean willDecode(String s) {
		return s != null;
	}


}

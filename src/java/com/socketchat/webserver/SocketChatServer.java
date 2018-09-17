/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.socketchat.webserver;

import com.socketchat.modelo.Mensaje;
import java.io.IOException;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.enterprise.context.ApplicationScoped;

import	java.io.StringReader;
import	java.util.logging.Level;
import	java.util.logging.Logger;
import	javax.inject.Inject;
import	javax.json.JsonObject;
import	javax.json.JsonReader;
import	javax.json.Json;
import javax.websocket.EncodeException;
/**
 *
 * @author Andres
 */
@ApplicationScoped
@ServerEndpoint("/actions")
public class SocketChatServer {
    
    @Inject
    private SocketChatHandler sessionHandler;
    
    @OnOpen
    public void open(Session session){sessionHandler.addSession(session);}

    @OnClose
    public void close(Session session){sessionHandler.removeSession(session);}

    @OnError
    public void onError(Throwable error){
    Logger.getLogger(SocketChatServer.class.getName()).log(Level.SEVERE, null, error);}
    
    @OnMessage
   public void handleMessage(String message, Session session) throws IOException, EncodeException{
       try (JsonReader reader= Json.createReader(new StringReader (message))){
       JsonObject jsonMessage= reader.readObject();
   if	("enviar".equals(jsonMessage.getString("action")))	{
        Mensaje	mensaje	=	new	Mensaje();
	mensaje.setNombre(jsonMessage.getString("nombre"));
	mensaje.setChat(jsonMessage.getString("chat"));
	
	sessionHandler.addMensaje(mensaje);
	}
   /*
	if	("remove".equals(jsonMessage.getString("action")))	{
	   int	id	=	(int)	jsonMessage.getInt("id");
	   sessionHandler.removeDevice(id);
	}
	*/
       }
    
}
}

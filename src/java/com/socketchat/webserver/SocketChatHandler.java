/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.socketchat.webserver;

import com.socketchat.modelo.Mensaje;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 *
 * @author Andres
 */
@ApplicationScoped
public class SocketChatHandler {
    
    private final List<Mensaje> mensaj = new ArrayList<>();
    private final List<Session> sessions = new ArrayList<>();
    
    public void addSession(Session session){
      sessions.add(session);
    }
    
    public void removeSession(Session session){
      sessions.remove(session);
    }
    
    private void sendToAllConnectedSessions(JsonObject message) throws IOException, EncodeException{
    for	(Session	session	:	sessions)	{
	  session.getBasicRemote().sendObject(message);
          //sendToSession(session,	message);
          }
       }
    public void addMensaje(Mensaje device) throws IOException, EncodeException{
     
     mensaj.add(device);
     
     JsonObject addMessage	= codeMensaje(device);
      sendToAllConnectedSessions(addMessage);
     }
    
    public JsonObject codeMensaje(Mensaje object){
         JsonObject json= Json.createObjectBuilder()
                            .add("nombre", object.getNombre())
                            .add("chat", object.getChat()).build();
         //try(JsonWriter jsonWriter = Json.CreateWriter(writer)){
         //    jsonWriter.writeObject(json);        }
        return json;
    }
    
    public void encodeMensaje(Reader reader){
        Mensaje mensaje= new Mensaje();
        try(JsonReader jsonR= Json.createReader(reader)){
           JsonObject json = jsonR.readObject();
           mensaje.setNombre(json.getString("nombre"));
           mensaje.setChat(json.getString("chat"));
                   
        }
    }
    
}

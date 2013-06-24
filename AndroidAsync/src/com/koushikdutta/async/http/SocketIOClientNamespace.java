package com.koushikdutta.async.http;

import org.json.JSONArray;
import org.json.JSONObject;

import com.koushikdutta.async.callback.CompletedCallback;

public class SocketIOClientNamespace {
    
    public class ConnectCallback {    
        public void onConnectCompleted(final String message) {
            connected = true;
        }
    }
    
    public static interface JSONCallback {
        public void onJSON(JSONObject json);
    }
    
    public static interface StringCallback {
        public void onString(String string);
    }
    
    public static interface EventCallback {
        public void onEvent(String event, JSONArray arguments);
    }

    public void emit(String name, JSONArray args) {
        client.emit(namespace,name, args);
    }

    public void emit(final String message) {
        client.emit(namespace,message);
    }
    
    public void emit(final JSONObject jsonMessage) {
        client.emit(namespace,jsonMessage);
    }
    
    ConnectCallback connectCallback;
    public ConnectCallback getConnectCallabck() {
        return connectCallback;
    }
    public void setConnectCallback(final ConnectCallback callback) {
        connectCallback = callback;
    }
    
    
    CompletedCallback closedCallback;
    public CompletedCallback getClosedCallback() {
        return closedCallback;
    }
    public void setClosedCallback(CompletedCallback callback) {
        closedCallback = callback;
    }
    
    JSONCallback jsonCallback;
    public JSONCallback getJSONCallback() {
        return jsonCallback;
    }
    public void setJSONCallback(JSONCallback callback) {
        jsonCallback = callback;
    }
    
    StringCallback stringCallback;
    public StringCallback getStringCallback() {
        return stringCallback;
    }
    public void setStringCallback(StringCallback callback) {
        stringCallback = callback;
    }
    
    EventCallback eventCallback;
    public EventCallback getEventCallback() {
        return eventCallback;
    }
    public void setEventCallback(EventCallback callback) {
        eventCallback = callback;
    }   
    
    String namespace;
    public String getNamespace() {
        return namespace;
    }
    
    SocketIOClient client;
    public SocketIOClientNamespace(final SocketIOClient client,final String namespace) {
        this.client = client;
        this.namespace = namespace;
        connectCallback = new ConnectCallback();
    }
    
    boolean connected;
    public boolean isConnected() {
        return connected && client.isConnected();
    }
    
    public void connect() {
        client.emitConnect(namespace);
    }
    
    public void disconnect() {
        connected = false;
        client.emitDisconect(namespace);
        if (closedCallback != null) {
            closedCallback.onCompleted(null);
        }
    }

    /**
     *  @TODO do we need reconnect, I would expect this to be a feature for the client
     *  as we are only virtual
     *  But it is not clear to me if we need to emit the connect again after the client restores 
     *  the connection
     */
    /*
    private void reconnect(final SocketIOConnectCallback callback, final FutureImpl ret) {

    }
     */

    
}

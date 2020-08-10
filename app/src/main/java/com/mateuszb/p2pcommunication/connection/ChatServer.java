package com.mateuszb.p2pcommunication.connection;

import com.mateuszb.p2pcommunication.pojo.ClientInfo;
import com.mateuszb.p2pcommunication.callbacks.MessageReceivedCallback;
import com.mateuszb.p2pcommunication.data.BaseMessage;
import com.mateuszb.p2pcommunication.data.JoinMessage;
import com.mateuszb.p2pcommunication.data.LeaveMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 26.03.2020.
 */
public class ChatServer extends ChatConnection {

    private Thread mServerThread;
    private java.net.ServerSocket mServerSocket;

    private List<ServerClient> ConnectedClients = new ArrayList<>();

    public ChatServer(MessageReceivedCallback callback) {
        super(callback);
        this.mServerThread = new Thread(this.ServerLoop);
        this.mServerThread.start();
    }

    private Runnable ServerLoop = new Runnable() {
        @Override
        public void run() {
            while (!ChatServer.this.shouldStop) {
                try {
                    if (ChatServer.this.mServerSocket != null) {
                        ChatServer.this.mServerSocket.close();
                    }
                    ChatServer.this.mServerSocket = new ServerSocket(CHAT_PORT);
                    ChatServer.this.isConnected = true;
                    while (!ChatServer.this.shouldStop) {
                        try {
                            Socket clientSocket = ChatServer.this.mServerSocket.accept();
                            PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream());
                            ServerClient client = new ServerClient(clientSocket, clientWriter);
                            ChatServer.this.ConnectedClients.add(client);
                            Thread t = new Thread(client);
                            t.start();
                        } catch (Exception ex) {
                            // Log.d here: client accept failed
                        }
                    }
                } catch (IOException ioex) {
                    ChatServer.this.isConnected = false;
                }
            }
        }
    };

    private class ServerClient implements Runnable {

        private Socket ClientSocket;
        private PrintWriter ClientWriter;
        private Thread ClientThread;
        private ClientInfo Info = null;

        public ServerClient(Socket socket, PrintWriter writer) {
            this.ClientSocket = socket;
            this.ClientWriter = writer;
        }

        public void send(String message) {
            this.ClientWriter.println(message);
            this.ClientWriter.flush();
        }

        @Override
        public void run() {
            this.ClientThread = Thread.currentThread();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.ClientSocket.getInputStream()));
                String message = reader.readLine();
                while (!(ChatServer.this.shouldStop || message == null)) {
                    for (ServerClient client : ChatServer.this.ConnectedClients) {
                        if (client != this) {
                            client.send(message);
                        }
                    }
                    BaseMessage received = BaseMessage.Deserialize(message);
                    if (received instanceof JoinMessage) {
                        this.Info = ((JoinMessage)received).getmClient();
                    }
                    ChatServer.this.Callback.ReceiveMessage(received);
                    message = reader.readLine();
                }
                reader.close();
                this.ClientWriter.close();
                this.ClientSocket.close();
            } catch (IOException ioex) {
                // Log: client disconnected
            } finally {
                ChatServer.this.ConnectedClients.remove(this);
                if (this.Info != null) {
                    LeaveMessage leaveMessage = new LeaveMessage(this.Info);
                    ChatServer.this.SendMessage(leaveMessage);
                }
            }
        }

        @SuppressWarnings("deprecation")
        public void stop() {
            try {
                if (this.ClientSocket != null) {
                    this.ClientSocket.close();
                }
                if (this.ClientThread != null) {
                    this.ClientThread.join();
                }
            } catch (IOException ioex) {
                // Log internal error while closing
            } catch (InterruptedException irex) {
                // if the thread is interrupted for any reason, stop it
                this.ClientThread.stop();
            }
        }

    }

    @Override
    public void SendMessage(BaseMessage message) {
        String msg = message.serialize();
        for (ServerClient client : this.ConnectedClients) {
            client.send(msg);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void Stop() {
        super.Stop();
        try {
            if (this.mServerSocket != null) {
                this.mServerSocket.close();
            }
            if (this.mServerThread != null) {
                this.mServerThread.join();
            }
        } catch (IOException ioex) {
            // Log internal error while closing
        } catch (InterruptedException irex) {
            // if the thread is interrupted for any reason, stop it
            this.mServerThread.stop();
        }
        ServerClient[] clients = new ServerClient[this.ConnectedClients.size()];
        this.ConnectedClients.toArray(clients);
        for (ServerClient client : clients) {
            client.stop();
        }
    }

}

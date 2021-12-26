package marmara.marmara.service.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import marmara.marmara.model.RegistryConnection;
import marmara.marmara.model.UDPConnection;
import marmara.marmara.service.RegistryHandlings;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

@Builder
@AllArgsConstructor
public class RegistryHandlingsImpl implements RegistryHandlings {

    private InputStreamReader inputStreamReader;
    private BufferedReader reader;
    private BufferedInputStream bufferedInputStream;

    public RegistryHandlingsImpl() {
        this.inputStreamReader = new InputStreamReader(System.in);
        this.reader = new BufferedReader(inputStreamReader);
        this.bufferedInputStream = null;
    }

    @Override
    public void connectRegistry(RegistryConnection registryConnection) {

        // establish a connection
        try {
            String address = InetAddress.getLocalHost().getHostAddress();

            int port = 8080;

            registryConnection.setSocket(new Socket(address, port));
            System.out.println("Connected");

            // takes input from terminal
            registryConnection.setInput(new InputStreamReader(System.in));

            // takes input from socket
            // bufferedInputStream = new BufferedInputStream(registryConnection.getSocket().getInputStream());

            registryConnection.setServerIn(new DataInputStream(registryConnection.getSocket().getInputStream()));


            // sends output to the socket
            registryConnection.setOut(new DataOutputStream(registryConnection.getSocket().getOutputStream()));
        } catch (IOException u) {
            System.out.println(u);
        }

        UDPConnection udpConnection = new UDPConnection();
        registryConnection.start();
        Thread t = new Thread(udpConnection);
        t.start();

    }


    @Override
    public String updateStatus(String iAmOnline) {
        return null;
    }

}

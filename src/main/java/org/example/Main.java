package org.example;

import Frames.LoginFrame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("127.0.0.1", 2525);
        ObjectOutputStream coos = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream cois = new ObjectInputStream(clientSocket.getInputStream());
        LoginFrame logFrame = new LoginFrame(cois, coos);
    }
}
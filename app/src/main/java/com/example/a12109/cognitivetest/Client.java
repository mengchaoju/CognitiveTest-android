package com.example.a12109.cognitivetest;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket socket = new Socket();
    private InputStream in;
    private OutputStream out;
    private BufferedReader inreader;
    private PrintWriter outwriter;

    Client(Socket socket) throws IOException {
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        inreader = new BufferedReader(new InputStreamReader(in));
        outwriter = new PrintWriter(out, true);
        this.socket = socket;
    }

    public void sendData(String str) {
        try {
            out=socket.getOutputStream();  //the output stream
            outwriter=new PrintWriter(out);
            outwriter.write(str);
            outwriter.flush();
            socket.shutdownOutput();  //close the output stream
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String getData() {
        String data = "";
        try {
            in=socket.getInputStream();  //the input stream
            inreader = new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=inreader.readLine())!=null){
                System.out.println("Server："+line);
                data = data + line +"/n";
            }
            in.close();
            inreader.close();
            socket.shutdownInput();  //close the input stream
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }



}



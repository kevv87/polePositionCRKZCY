package logic;
// A Java program for a Client
import java.net.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client
{
    // initialize socket and input output streams
    private Socket socket        = null;
    private DataInputStream input = null;
    private DataOutputStream out     = null;
    private BufferedWriter bufferedWriter = null;
    private boolean counter;

    // constructor to put ip address and port
    public Client(String address, int port) {
        // establish a connection
        counter = false;
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input = new DataInputStream(System.in);

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException u) {
            System.out.println(u);
        }
    }
    public String read() {
        // string to read message from input
        String input_line = "";
        String answer_line = "";

        // keep reading until "Over" is input
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedReader.read();
                answer_line = bufferedReader.readLine();
                System.out.println(answer_line);
            } catch (IOException i) {
                System.out.println(i);
            }
            return answer_line;

    }

    public void writeI(String msg) throws IOException {
        if(counter){
            return;
        }
        counter = !counter;
        bufferedWriter.write("I"+msg);
        bufferedWriter.flush();
    }
    public void writeR(String msg) throws IOException {
        if(!counter){
            return;
        }
        counter = !counter;
        bufferedWriter.write("R"+msg);
        bufferedWriter.flush();
    }

    public void close(){
        // close the connection
        try{
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i){
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Client client = new Client("25.28.229.147", 8888);
        try {
            client.writeI("Ola");
            client.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.close();
    }
}

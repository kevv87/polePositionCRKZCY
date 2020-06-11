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

    // constructor to put ip address and port 
    public Client(String address, int port)
    {
        // establish a connection 
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal 
            input = new DataInputStream(System.in);

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // string to read message from input
        String input_line = "";
        String answer_line = "";

        // keep reading until "Over" is input 
        while (!input_line.equals("Over"))
        {
            try
            {
                input_line = input.readLine();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write(input_line);
                bufferedWriter.flush();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedReader.read();
                answer_line = bufferedReader.readLine();
                System.out.println(answer_line);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }

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
        Client client = new Client("127.0.0.1", 8888);
    }
} 
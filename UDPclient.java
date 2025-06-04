import java.io.*;
import java.net.*;
import java.util.*;

public class UDPclient
{
    //The data block size (1000 bytes).
    public int bufferSize = 1000;
    //The hostname and port number of the server, as well as the list of files containing the file names to be downloaded.
    public String sHost;
    public int sPort;
    public String fileListName;
    //The UDP socket, used for communication.
    public DatagramSocket cSocket;
    //Make sure there is enough space to receive these data packets.
    public int bigBufferSize = 10000;

    public UDPclient(String h, int p, String f)
    {
        this.sHost = h;
        this.sPort = p;
        this.fileListName = f;
        try
        {
            //To create a UDP socket.
            cSocket = new DatagramSocket();
        }
        catch(SocketException e)
        {
            //If the creation of the socket fails, print the error message and exit the program.
            System.err.println("Error!The client socket cannot be created.");
            System.exit(1);
        }
    }

    public List<String> readFileList(String name) throws IOException
    {
        //To create a list of file names.
        List<String> files = new ArrayList<>();
        //To use BufferedReader to read the file list.
        try(BufferedReader reader = new BufferedReader(new FileReader(name)))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                //To remove the first and last whitespace characters and ignore the blank lines.
                line = line.trim();
                if(!line.isEmpty())
                {
                    files.add(line);
                }
            }
        }
        return files;
    }
    //To send a message and wait for receiving.
    public String sendAndReceive(String m, String h, int p) throws IOException
    {
        //TO parse the address of the target host.
        InetAddress sAdress = InetAddress.getByName(h);
        //To convert the message to a byte array and create a receiving buffer.
        byte[] sendData = m.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, sAdress, p);
        byte[] rBuffer = new byte[bigBufferSize];
        DatagramPacket receivPacket = new DatagramPacket(rBuffer, rBuffer.length);
        //To send data packets and receive responses from the server.
        cSocket.send(sendPacket);
        System.out.println("Send: " + m);
        cSocket.receive(receivPacket);
        String response = new String(receivPacket.getData(), 0, receivPacket.getLength());
        System.out.println("Receive: " + response);
        return response;
    }
    public void run()
    {
        try
        {
            //To read the file list and obtain the list of file names that need to be downloaded.
            List<String> files = readFileList(fileListName);
            for(String file : files)
            {
                //To traverse the file list and download each file in sequence.
            }
        }
        catch(IOException e)
        {
            System.out.println("Client error: " + e.getMessage());
        }
    }

    public void main(String[] args) 
    {
        if(args.length != 3)
        {
            System.out.println("Usage: java UDPclient < Server hostname > < Port number > < File list >.");
        }
        try
        {
            //To parse the command-line parameters.
            sHost = args[0];
            sPort = Integer.parseInt(args[1]);
            fileListName = args[2];
            //To create the client instance and start it.
            UDPclient client = new UDPclient(sHost, sPort, fileListName);
            client.run();
        }
        catch(NumberFormatException e)
        {
            System.out.println("Error!The port number must be an integer.");
        }
    }
}
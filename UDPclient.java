import java.net.*;

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

    public void run()
    {
       //To add the file list and download the files.
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
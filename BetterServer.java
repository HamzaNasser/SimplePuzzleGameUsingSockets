import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class BetterServer {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static BufferedReader in;
    private static PrintWriter out;
    private static String output = "";
    private static String eor = "[EOR]"; // a code for end-of-response
    private static String username;
    private static String passwd;
    private static BufferedReader userin;
    private static BufferedReader passwdin;
    
    // establishing a connection
    private static void setup() throws IOException {
        
        serverSocket = new ServerSocket(0);
        toConsole("Server port is " + serverSocket.getLocalPort());
        
        clientSocket = serverSocket.accept();

        // get the input stream and attach to a buffered reader
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        // get the output stream and attach to a printwriter
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        toConsole("Accepted connection from "
                 + clientSocket.getInetAddress() + " at port "
                 + clientSocket.getPort());
            
        sendGreeting();
    }
    
    // the initial message sent from server to client
    private static void sendGreeting()
    {
        appendOutput("Welcome to Wordnet!\n");
        appendOutput("Enter the username: ");
        sendOutput();
    }
    
    // what happens while client and server are connected
    private static void talk() throws IOException {
        /* placing echo functionality into a separate private method allows it to be easily swapped for a different behaviour */
        echoClient();
        disconnect();
    }
    
    // repeatedly take input from client and send back in upper case
    private static void echoClient() throws IOException
    {
        String inputLine; //for the username
        int i=0;
        int g=0;
        while ((inputLine = in.readLine()) != null) {
            
            if (inputLine.equals("Sammy")) { //if teh useername is correct 
                appendOutput("Enter password: ");
                sendOutput();

                inputLine = in.readLine();
                if (inputLine.equals("WOOF")){ //if passwd is right
                appendOutput("Welcome Sammy");
                appendOutput("Guess the mystery six-letter word: ");
                sendOutput();
                break;
                }
                else {
                    appendOutput("Password not recognised");
                    i++;
                    if (i >= 5){
                        i = 0;
                        disconnect();
                    }
                    }
                }
                
            else {
                
                appendOutput("Username not recognised");
                i++;
                if (i >=5){
                disconnect();
                
                }
                
                
                
                }

            appendOutput("Enter the username: ");
            sendOutput();

        }
        int tries = 1; // counter for how many tries the user did
        inputLine = in.readLine();
        inputLine = inputLine.toUpperCase();
        String topple = "TOPPLE";
        while (!inputLine.toUpperCase().equals("TOPPLE"))
        {
            if (inputLine.length() != 6 || inputLine.contains(" ") || inputLine.contains("0") || inputLine.contains("1") || inputLine.contains("2")
        || inputLine.contains("3") || inputLine.contains("4") || inputLine.contains("5") || inputLine.contains("6") || inputLine.contains("7")
        || inputLine.contains("8") || inputLine.contains("9")){
            appendOutput("Oops! You need enter a six letter word containing only alphabetical characters and no spaces");
            appendOutput("Try again:");
            tries++;
            sendOutput();
            inputLine = in.readLine();
            inputLine = inputLine.toUpperCase();
            continue;
            }
            else{
                String blank = "";
                for (int j=0; j < inputLine.length(); j++) {
                    char s = inputLine.charAt(j);
                    if (s == topple.charAt(j)){
                        blank = blank + s;                        
                    }
                    else{
                        blank = blank + "*";
                    }
                }
                appendOutput(blank);
                appendOutput("Try again:"); 
                sendOutput();
                tries++;
                inputLine = in.readLine();
                inputLine = inputLine.toUpperCase();
            }
        }
        appendOutput("TOPPLE"); // if the user guess the word - disconnect 
        appendOutput("You got it in "+ tries +" turns - well done and goodbye!");
        sendOutput();
        disconnect();
    }
    private static void disconnect() throws IOException {
        out.close();
        toConsole("Disconnected.");
        System.exit(0);
    }
    
    // add a line to the next message to be sent to the client
    private static void appendOutput(String line) {
        output += line + "\r";
    }
    
    // send next message to client
    private static void sendOutput() {
        out.println( output + "[EOR]");
        out.flush();
        output = "";
    }
    
    // because it makes life easier!
    private static void toConsole(String message) {
        System.out.println(message);
    }
    
    public static void main(String[] args) {
        try {
            setup();
            talk();
        }
        catch( IOException ioex ) {
            toConsole("Error: " + ioex );
        }
    }
    
}
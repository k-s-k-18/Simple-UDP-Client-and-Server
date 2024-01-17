import java.io.*;
import java.net.*;

public class client1{
    public static void main(String[] args){

        if(args.length<2){
            System.out.println("IP Address Format has to be: client1.java <ipaddr> <port>");
            return;
        }

        String hostaddress = args[0];
        int portNo = Integer.parseInt(args[1]);

        //Checking if port no is a valid entry

        if(portNo > 65535 || portNo < 0){
            System.out.println("Port Number Invalid");
            return;
        }

        try{

            InetAddress address = InetAddress.getByName(hostaddress);
            DatagramSocket socket = new DatagramSocket();

            //Code for reading information from text files
            String filePath = "./messages.txt";
            FileReader fileReader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fileReader);
            String line;

            while((line = br.readLine()) != null){
                byte[] buffer = line.getBytes();

                DatagramPacket response = new DatagramPacket(buffer, buffer.length, address, portNo);
                socket.send(response);

                Thread.sleep(3000);

            }

            socket.close();
            br.close();
        } catch(SocketTimeoutException ex){
            System.out.println("Timeout error: "+ex.getMessage());
        } catch(IOException ex){
            System.out.println("Client error: "+ex.getMessage());
        }catch(InterruptedException ex){
            
        }
    }
}
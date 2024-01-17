import java.io.*;
import java.net.*;

public class server1 {

    public static void main(String[] args){

        if(args.length<1){
            System.out.println("Port No error: server1.java <portno>");
            return;
        }

        int portNo = Integer.parseInt(args[0]);

        if(portNo>65535 || portNo < 0){
            System.out.println("Invalid Port No.");
            return;
        }

        try{

            while(true){

                byte[] buff = new byte[1024];
                DatagramSocket socket = new DatagramSocket(portNo);
                DatagramPacket response = new DatagramPacket(buff,buff.length);
    
                socket.receive(response);
    
                String res = new String(buff,0,response.getLength());

                //Printing Buffer in format
                int i = 0, j = 0;
                char[] pairs = new char[100];
                System.out.println();
                System.out.println(String.format("%-30s%s", "Name","Value"));
                // System.out.println("Name"++"Value");
                while(i<res.length()){
                    if(res.charAt(i)!=':' && res.charAt(i) != ' ' && res.charAt(i)!='"'){
                        pairs[j++] = res.charAt(i++);
                    }
                    else if(res.charAt(i)==':'){
                        System.out.print(String.format("%-30s", new String(pairs,0,j)));
                        j=0;
                        i++;
                    }
                    else if(res.charAt(i)=='\"'){
                        pairs[j++] = res.charAt(i++);
                        while(res.charAt(i)!='\"'){
                            pairs[j++] = res.charAt(i++);
                        }
                        pairs[j++]=res.charAt(i);
                        System.out.println(new String(pairs,0,j));
                        j=0;
                        i++;
                    }
                    else{
                            System.out.println(new String(pairs, 0, j));
                            j = 0;
                            i++;
                    }
                }
                
                socket.close();

            }

        } catch(SocketException ex){
            System.out.println("Socket Connection Error: "+ex.getMessage());
        } catch(IOException ex){
            System.out.println("Socket Connection Error: "+ex.getMessage());
        }
    }
    
}

import java.io.*;
import java.util.Scanner;
import java.net.*;



public class proj3
{
   
   
   public void send(String webadd,int port) throws UnknownHostException, IOException
   {   
     
      BufferedReader rd;  
      try
      {  
    	  Socket socket = null;
    	   PrintWriter output = null;
    	   BufferedReader in = null;
    	   
         String webadd1;
         String bb="";
         int i=0;
         
         String delims = "[/]";
         String[] tokens = webadd.split(delims);
         String host=tokens[2];
         String filename="";
         for(i=3;i<tokens.length;i++)
         {
            filename+=tokens[i]+"/";
         } 
         //System.out.println("filename: "+filename);
         //String filename=tokens[3]; 
         socket=new Socket(host,port); 
        

         OutputStream to_file;
         InputStream from_server = socket.getInputStream();
         PrintWriter to_server = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
         //System.out.println(webadd1);
         to_file = System.out;
         
        	 filename=filename.substring(0,filename.length()-1);
        	 to_server.println("GET /"+filename+" HTTP/1.0\r\nHost: "+host+" \r\n\r\n");
         
         to_server.flush();  
         byte[] buffer = new byte[4096];
         byte[] buffer1=new byte[500000];
         int bytes_read;
         int count=0;
         while((bytes_read = from_server.read(buffer)) != -1)
        {
                     
            for(i=0;i<4096;i++)
            {
               buffer1[count+i]=buffer[i];
            } 
            count+=4096;
        } 
        String s=new String(buffer1);
        //System.out.println(s);
        String s1=s.substring(s.indexOf("<body>")+6,s.indexOf("</body>"));
       
        
        char [] string= s1.toCharArray();
        //System.out.println(string);
        int k=0;
        char img[] = new char[50];
        int m,q=0;
        for(int j=0;j<string.length;j++){
              	if(string[j]=='<'){
        		while(string[j]!='>'){
        		string[j]='\0' ;
        		j++;
        		}
        		string[j]='\0';
        	}
        }
       boolean finish=false;
        
        if(finish==false){
        for(int j=0;j<string.length;j++)
        	System.out.print(string[j]);
        }
        
        
 
         
        System.out.println();
        System.out.println();
        socket.close();
        //to_file.close();
        
      
     	    
        
        
        
      }   
      catch (IOException e)
      {
         System.out.println("Some Problem with URL");
         System.exit(1);
      }
        
   }
     
   
  
   	

   public static void main(String[] args) throws UnknownHostException, IOException
   {
      //if (args.length != 1)
    // {
    	//  System.out.println("Usage:  client hostname port");
	  //   System.exit(1);
    // }

      Clientonly client = new Clientonly();
      int port=80;
      String webadd = args[0];    
      //int port = Integer.valueOf(args[1]);

      
      client.send(webadd,port);

   }
}

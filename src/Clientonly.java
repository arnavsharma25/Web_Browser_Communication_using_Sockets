import java.io.*;
import java.util.Scanner;
import java.net.*;



public class Clientonly
{
   Socket socket = null;
   PrintWriter output = null;
   BufferedReader in = null;
   
   public void send(String webadd,int port) throws UnknownHostException, IOException
   {   
     if((webadd.charAt(webadd.length()-1)!='g' && webadd.charAt(webadd.length()-2)!='p')){ //Checking if it is an image request or not
      BufferedReader rd;  
      try
      {  
         String webadd1;
         String bb="";
         int i=0;
         if(webadd.charAt(5)!=webadd.charAt(6)) //Checking if has entered the web address incorrectly or not
         {
            webadd1=webadd.substring(0,5)+"/"+webadd.substring(5,webadd.length());            
         }
         else
         {
            webadd1=webadd;
         }
         //System.out.println(webadd1);

         String delims = "[/]";  //Separating address by '/' to get Host and Clientname
         String[] tokens = webadd1.split(delims);
         String host=tokens[2];
         String filename="";
         for(i=3;i<tokens.length;i++)
         {
            filename+=tokens[i]+"/";
         } 
         //System.out.println("filename: "+filename);
         //String filename=tokens[3]; 
         socket=new Socket(host,port);  // Creating a new socket
        

         OutputStream to_file; // Creating an OutputStream
         InputStream from_server = socket.getInputStream();  //Creating an InputStream object
         PrintWriter to_server = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())); //Creating a PrintWriter object
         //System.out.println(webadd1);
         to_file = System.out; //Giving printout command to to_file
         if(webadd1.charAt(7)=='p' && webadd1.charAt(8)=='o'){  //Checking if the webaddress has filename or not if not then it enters here
        	 to_server.println("GET /"+filename+" HTTP/1.0\r\nHost: "+host+":"+port+" \r\n\r\n");
         }
         else{
        	 filename=filename.substring(0,filename.length()-1);
        	 to_server.println("GET /"+filename+" HTTP/1.0\r\nHost: "+host+" \r\n\r\n");
         }
         to_server.flush();  
         byte[] buffer = new byte[4096]; //Creating a new Byte Array
         byte[] buffer1=new byte[500000]; //Creating a new Byte Array
         int bytes_read;
         int count=0;
         while((bytes_read = from_server.read(buffer)) != -1) //Reading line by line and storing as a byte in the array
        {
                     
            for(i=0;i<4096;i++)
            {
               buffer1[count+i]=buffer[i];
            } 
            count+=4096;
        } 
        String s=new String(buffer1);
        //System.out.println(s);
        String s1=s.substring(s.indexOf("<body>")+6,s.indexOf("</body>")); //Removing the Header from the received input
       
        
        char [] string= s1.toCharArray();
        //System.out.println(string);
        int k=0;
        char img[] = new char[50];
        int m,q=0;
        for(int j=0;j<string.length;j++){
              	if(string[j]=='<'){  //Checking if there are Image Tags
              		if((webadd1.charAt(7)!='p') && (webadd1.charAt(11)!='u')){  //Checking when there image tags separating the 2 images stored in 1 array
              		if((string[j+1]=='i') && (string[j+2]=='m') && (string[j+3]=='g')){ 
            			m=j+10;
            			while(string[m]!='"'){
            				img[q]=string[m];
            				q++;
            				m++;
            			}
            				
            			
            		}
              		}
        		while(string[j]!='>'){  //If tage are there fill them will Null spaces and remove HTML Tags
        		string[j]='\0' ;
        		j++;
        		}
        		string[j]='\0';
        	}
        }
       boolean finish=false;
        for(int j=0;j<string.length;j++){ //Checking to see if the received information has a statement called page not found
        	if(string[j]=='P' && string[j+1]=='a' && string[j+2]=='g' && string[j+3]=='e' && string[j+5]=='N' && string[j+6]=='o' && string[j+7]=='t'){ 
        		finish=true;
        	}
        	 
        }
        if(finish){ //If page not found statement is there then print the Header 1st Line
        	System.out.print(s.substring(0,'\r')+s.substring('\r',24));
        }
        
        if(finish==false){ //If not then print the entire String which has the received stream of data
        for(int j=0;j<string.length;j++)
        	System.out.print(string[j]);
        }
        
        
        char img1[] = new char[33];
        char img2[] = new char[17];
       
        if(img[2]!='\u0000'){  //Storing Image names in 2 different arrays so that they could use in the GET request
        int w=0,t=0;       
        for(int j=0;j<img.length;j++){
        	
        	img2[j]=img[j];
        	
        	if(img[j]=='j'&&img[j+1]=='p'&&img[j+2]=='g'){
        		img2[j]='j';
        		img2[j+1]='p';
        		img2[j+2]='g';
        		int c=j+3;
        		for(w=c;w<img.length;w++){
        			img1[t]=img[c];
        			t++;
        			c++;
        		}        		
        		break;	
        	}
        }
        	
	        if(img1[2]!='\u0000' && img2[2]!='\u0000'){  //If image1 and Image2 is present
	        	System.out.println();
	        	String a=new String(img2); 
	        	String delims1 = "[/]";
	            String[] tok3 = a.split(delims1);
		        bb="http://www.htmldog.com/examples/"+a;
		        System.out.println("Image1 = " +tok3[1]);
		        image(bb,port); //Call the function to GET Image
		       
		        System.out.println();
		    	String e=new String(img1);
		    	String delims2 = "[/]";
		        String[] tok4 = e.split(delims2);
		    	System.out.println("Image2 = " +tok4[3]);
		    	image(e,port); //Call the function to GET Image
	        }
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
     else{
    	 image(webadd,port); // Call the function to Download Image from the Web link directly
    	 System.out.println("Image Downloaded");
     }
  
   }
   
   
   public static void image(String webadd, int port) throws UnknownHostException, IOException{ //Image Download Method
	   Socket socket1 = null;
	   String imageurl1=new String(webadd); //Storing Webaddress Into a String
       //System.out.println(imageurl1);
       String url = imageurl1;

       String delim = "[/]";  // Splitting the Web address
       String[] token1 = url.split(delim);
       String host1=token1[2];
       //System.out.println(host1);

       String filename1="";
       for(int i=3;i<token1.length;i++) // Splitting the Web address
       {
          filename1+=token1[i]+"/";
       } 
       socket1=new Socket(host1,port);  //Creating a socket
      // System.out.println(host1);
       
       filename1=filename1.substring(0,filename1.length()-1); //Creating Filename from the Web Address
      //System.out.println(filename1);
       
       PrintWriter zz=new PrintWriter(new OutputStreamWriter(socket1.getOutputStream()),true); //Creating a Printwriter Object

       zz.println("GET /"+filename1+" HTTP/1.0\r\nHost: "+host1+" \r\n\r\n"); //Sending GET Request
       
       //System.out.println("Response Code : " + qq);
       int f;
       InputStream qq = socket1.getInputStream(); //Creating InputStream object
        byte[] ww = new byte[99999];
        
       ByteArrayOutputStream b=new ByteArrayOutputStream();      // Creating OutputStram object
       //OutputStream out = new FileOutputStream("/Users/Arnav/Desktop/badge1.gif");
       boolean flag1=false;
   	   boolean flag=false;
       while ((f = qq.read(ww)) != -1){ //Printing out the required part of the Image data
       	int off=0;
       //System.out.println(f);
       	for(int i=0;i<f;i++)
       	{
       		
       		
       		if(ww[i]=='\r' && ww[i+1]=='\n' && ww[i+2]=='\r' && ww[i+3]=='\n') //Finding in the image data if there is 2 consecutive \n\r
       		{
       			off=i+4;
       			flag1=true;
       		}
       	}
       	if(flag1)
       	b.write(ww,off,f-off); //Writing in a file without Header
       }
       if(webadd.charAt(webadd.length()-1)=='f'){
    	   File file=new File(filename1);  
    	   FileOutputStream fos=new FileOutputStream(file);       //Writing in a File of time gif
           fos.write(b.toByteArray(),0,b.toByteArray().length);
           fos.close();
       }
       else if(webadd.charAt(webadd.length()-1)=='g'){
    	   String delims = "[/]";
           String[] tok = filename1.split(delims);
    	   File file=new File(tok[(tok.length)-1]);
    	   FileOutputStream fos=new FileOutputStream(file);         //Writing in a File of time jpg
           fos.write(b.toByteArray(),0,b.toByteArray().length);
           fos.close();
       }
    	    
         qq.close();
   }
   	

   public static void main(String[] args) throws UnknownHostException, IOException //Calling Main Method
   {
     if (args.length != 1) //If argument does not have 1 argument
     {
    	System.out.println("Usage:  client hostname port");
	    System.exit(1);
     }
     
      Clientonly client = new Clientonly(); //Opening a Client socket
      int port=80;
      String webadd = args[0];    

      if(webadd.charAt(20)=='8' && webadd.charAt(21)=='0'){ 
    	  String delims = "[:]";
          String[] tok = webadd.split(delims);
    	  webadd=tok[0]+":"+tok[1];
    	  String p=tok[2].substring(0,tok[2].length()-1);
    	  port=Integer.parseInt(p);
      }
        
      client.send(webadd,port);

   }
}

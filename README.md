# Web_Browser_Communication_using_Sockets
This code will utilize Sockets for communication between processes.  

The browser will:<br />
1)	Accept a URL as a command line argument or typed into the GUI address box.<br />
2)	Connect to the web server (default port is 80 unless specified in the URL).<br />
3)	Send an HTTP GET request to the server for the requested page.<br />
4)	Parse the returned HTML or display returned error message.<br />
5)	For any images in the HTML, send additional HTTP GET requests for each one.<br />
6)	Output a page consisting of the text found in the page and the images.<br />
<br />
<br />
Parsing:<br />
1)	Most tags are ignored.<br />
2)	The image tag (<img src=…) should be recognized and processed.<br />
3)	Though there are software libraries that can parse HTML, do not use these.  Just use the normal language features to do parsing.<br />
<br />

1)	Display the page content vertically.<br />
2)	For images, display “Image:  filename” where filename is the name of the image file which has been retrieved and stored in the current directory.<br />

// Import required java libraries

import java.io.*;

import jakarta.servlet.*;

import jakarta.servlet.http.*;

// Extend HttpServlet class

public class HelloWorld extends HttpServlet {
  private String message;

  private String title;

  public void init() throws ServletException {

     // Do required initialization

     message = "Hello World";

  title = "my first servlet";

  }
  
      public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
        
         // Set response content type

         response.setContentType("text/html");

         // Actual logic goes here.

         PrintWriter out = response.getWriter();

         out.println("<html><head><title>" + title + "</title></head>");

		out.println("<body><h1>" + message + "</h1></body>");

		out.println("</html>");

      }

      public void destroy() {

         // do nothing.

      }

    }





  
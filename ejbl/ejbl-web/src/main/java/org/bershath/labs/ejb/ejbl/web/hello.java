package org.bershath.labs.ejb.ejbl.web;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bershath.labs.ejb.ejbl.Second;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;

@WebServlet("/hello")
public class hello extends HttpServlet {
    public hello(){}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String greeting;
        try {
            Context ctx = new InitialContext();
            Second second = (Second) ctx.lookup("java:global/elbl-two-1.0-SNAPSHOT/SecondBean!org.bershath.labs.ejb.ejbl.Second");
            greeting = second.sayWorld();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

        response.getWriter().append("Here's the album name: "+ greeting).append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}

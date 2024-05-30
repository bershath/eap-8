package org.bershath.labs.web;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bershath.labs.ejb.MessageSender;

import java.io.IOException;

@WebServlet("/messagesendersvlt")
public class MessageSenderServlet extends HttpServlet {

    @EJB
    MessageSender messageSender;
    public MessageSenderServlet() {
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        messageSender.sendMessage("Test Message");
        response.getWriter().append("Served at: ").append(request.getContextPath());

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

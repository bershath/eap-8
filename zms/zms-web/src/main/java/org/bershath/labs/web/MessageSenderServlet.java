package org.bershath.labs.web;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bershath.labs.ejb.jms.MessageSender;
import org.bershath.labs.ejb.jmx.JmxMessageCount;
import org.jboss.logging.Logger;
import java.io.IOException;

@WebServlet("/messagesendersvlt")
public class MessageSenderServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(MessageSenderServlet.class);

    @EJB
    MessageSender messageSender;

    @EJB
    JmxMessageCount jmxMessageCount;

    public MessageSenderServlet() {
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        messageSender.sendMessage("Test Message");
        response.getWriter().append("Process completed successfully.").append(request.getContextPath());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/qstatsvlt");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

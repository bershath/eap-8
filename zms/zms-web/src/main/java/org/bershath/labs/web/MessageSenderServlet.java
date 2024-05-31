package org.bershath.labs.web;

import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bershath.labs.ejb.jms.MessageSender;
import java.io.IOException;
import java.io.Serial;

@WebServlet("/messagesendersvlt")
public class MessageSenderServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = -4344698950385746737L;

    @EJB
    MessageSender messageSender;

    public MessageSenderServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        messageSender.sendMessage("Test Message");
        response.getWriter().append("Process completed successfully.").append(request.getContextPath());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/qstatsvlt");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}

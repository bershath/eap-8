package org.bershath.labs.web;

import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bershath.labs.ejb.jmx.JmxMessageCount;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/qstatsvlt")
public class QueueStatServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(QueueStatServlet.class);

    @Serial
    private static final long serialVersionUID = -2017156064064939535L;
    @EJB
    JmxMessageCount jmxMessageCount;

    public QueueStatServlet(){}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long msgCount;
        try {
            msgCount = jmxMessageCount.getMessageCount("jms.queue.Z");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.getWriter().append("<html><body>Number of Messages in Queue Z: " + msgCount);
        response.getWriter().append("<a href='/messagesendersvlt'><br/>Send another message</a></body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }



}

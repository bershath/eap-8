import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bershath.labs.ejb.First;
import org.bershath.labs.ejb.Second;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;


@WebServlet("/album")
public class album extends HttpServlet {
    album(){}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String albumName;
        try {
            Context ctx = new InitialContext();
            Second second = (Second) ctx.lookup("java:global/lint-etwo-1.0-SNAPSHOT/SecondBean!org.bershath.labs.ejb.Second");
            albumName = second.getAlbumArtistName();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

        response.getWriter().append("Here's the album name: "+ albumName).append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
package org.bershath.labs.ejb;

import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@LocalBean
@Stateless
public class SecondBean implements Second{


    /**
     * @return
     */
    @Override
    public String getAlbumArtistName() {
        try {
            Context ctx = new InitialContext();
            First first = (First) ctx.lookup("java:global/lint-eone-1.0-SNAPSHOT/FirstBean!org.bershath.labs.ejb.First");
            return first.getArtistName() + " - As falls wichita so falls wichita falls ";
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}

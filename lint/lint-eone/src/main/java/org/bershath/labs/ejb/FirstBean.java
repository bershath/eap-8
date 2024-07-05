package org.bershath.labs.ejb;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless
@LocalBean
public class FirstBean implements First{
    /**
     *
     */
    @Override
    public String getArtistName() {
        return "Pat Metheny";
    }
}

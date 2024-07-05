package org.bershath.labs.ejb;

import jakarta.ejb.Local;

@Local
public interface First {
    public String getArtistName();
}

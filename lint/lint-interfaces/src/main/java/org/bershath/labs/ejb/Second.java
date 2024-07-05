package org.bershath.labs.ejb;

import jakarta.ejb.Local;

@Local
public interface Second {
    public String getAlbumArtistName();
}

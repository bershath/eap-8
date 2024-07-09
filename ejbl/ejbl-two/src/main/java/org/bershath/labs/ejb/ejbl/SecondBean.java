package org.bershath.labs.ejb.ejbl;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@LocalBean
@Stateless
public class SecondBean implements Second{
    /**
     * @return
     */
    @Override
    public String sayWorld() {
        return " World";
    }
}

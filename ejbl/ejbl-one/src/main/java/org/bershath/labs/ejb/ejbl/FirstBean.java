package org.bershath.labs.ejb.ejbl;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@LocalBean
@Stateless
public class FirstBean implements First{
    /**
     * @return
     */
    @Override
    public String sayHello() {
        return "Hello";
    }
}

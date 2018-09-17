/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.socketchat.ejb;

import com.socketchat.modelo.Mensaje;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Andres
 */
@Local
public interface MensajeFacadeLocal {

    void create(Mensaje mensaje);

    void edit(Mensaje mensaje);

    void remove(Mensaje mensaje);

    Mensaje find(Object id);

    List<Mensaje> findAll();

    List<Mensaje> findRange(int[] range);

    int count();
    
}

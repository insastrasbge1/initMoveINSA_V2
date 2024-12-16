/*
    Copyright 2000-2014 Francois de Bertrand de Beuvron

    This file is part of UtilsBeuvron.

    UtilsBeuvron is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    UtilsBeuvron is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with UtilsBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.beuvron.utils.divers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Numérote une collection d'objets par les entiers 0..n-1
 * implémnentation basé sur une liste et une hashmap
 * @param Obj type des objets
 * @author francois
 */
public class Numeroteur<Obj> {
    
    private List<Obj> objets;
    private HashMap<Obj,Integer> indexes;

    public Numeroteur() {
        this(new ArrayList<>());
    }

    public Numeroteur(Collection<Obj> objets) {
        this.objets = new ArrayList<>(objets);
        this.indexes = new HashMap<>(this.objets.size());
        for (int i = 0 ; i < this.objets.size() ; i ++) {
            this.indexes.put(this.objets.get(i), i);
        }
    }
    
    public int getOrAdd(Obj obj) {
        Integer res = this.indexes.get(obj);
        if (res == null) {
            res = this.objets.size();
            this.indexes.put(obj, res);
            this.objets.add(obj);
        }
        return res;
    }
     
     /**
     * retourne l'objet correspondant à un index.
     * @param index int
     * @return Object
     */
    public Obj getObject(int index) {
        Obj res = this.objets.get(index);
        if (res == null) {
            throw new IndexOutOfBoundsException();
        }
        return res;
    }

    /**
     * @return toutes les objets contenues dans le numéroteur
     */
    public Collection<Obj> getAllObjects() {
        return this.objets;
    }

    /**
     * retourne l'index correspondant à une variable.
     * @param var Variable var
     * @return int
     */
    public int getIndex(Obj var) {
        Integer res = this.indexes.get(var);
        if(res == null) {
            throw new IndexOutOfBoundsException();
        }
        return res;
    }

    public boolean add(Obj obj) {
        if (this.indexes.get(obj) == null) {
            this.indexes.put(obj, this.objets.size());
            this.objets.add(obj);
            return true;
        } else {
            return false;
        }
    }
    
    public int size() {
        return this.objets.size();
    }
    
    
}

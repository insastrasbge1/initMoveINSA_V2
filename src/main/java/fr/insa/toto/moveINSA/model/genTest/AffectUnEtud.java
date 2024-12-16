/*
Copyright 2000- Francois de Bertrand de Beuvron

This file is part of CoursBeuvron.

CoursBeuvron is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CoursBeuvron is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.toto.moveINSA.model.genTest;

import java.util.Optional;

/**
 *
 * @author francois
 */
public class AffectUnEtud {
    
    private EtudGen etud;
    private Optional<String> refOffre;

    public AffectUnEtud(EtudGen etud, Optional<String> refOffre) {
        this.etud = etud;
        this.refOffre = refOffre;
    }

    @Override
    public String toString() {
        return this.etud.getIne() + " --> " + (refOffre.isEmpty() ? "rien" : refOffre.get());
    }


    /**
     * @return the numOffre
     */
    public Optional<String> getRefOffre() {
        return refOffre;
    }

    /**
     * @return the etud
     */
    public EtudGen getEtud() {
        return etud;
    }
    
}

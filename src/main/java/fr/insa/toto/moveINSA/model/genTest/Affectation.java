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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Pour tester un peu les TestMobilite générés: on calcule l'affectation.
 *
 * @author francois
 */
public class Affectation {

    private String ineEtud;
    private Optional<Integer> numOffre;

    public Affectation(String ineEtud, Optional<Integer> numOffre) {
        this.ineEtud = ineEtud;
        this.numOffre = numOffre;
    }

    @Override
    public String toString() {
        return "Affectation{" + "ineEtud=" + ineEtud + " --> " 
                + (numOffre.isEmpty() ? "rien" : numOffre.get()) + '}';
    }

    public static List<Affectation> affecte(TestMobilite sit) {
        // je construit d'abord des tableaux correspondant aux places libres dans les offres
        int nbrOffre = sit.getOffres().size();
        int nbrSpe = sit.getSpecialites().size();
        int nbrEtud = sit.getEtudiants().size();
        int[] totPlaces = new int[nbrOffre];
        for (int numOffre = 0; numOffre < nbrOffre; numOffre++) {
            totPlaces[numOffre] = sit.getOffres().get(numOffre).getTotPlaces();
        }
        int[][] placesParSpe = new int[nbrOffre][nbrSpe];
        for (int numOffre = 0; numOffre < nbrOffre; numOffre++) {
            for (int numSpe = 0; numSpe < nbrSpe; numSpe++) {
                placesParSpe[numOffre][numSpe] = sit.getOffres().get(numOffre).getPlacesParSpe().get(numSpe);
            }
        }
        // On prend tous les étudiants triés par score décroissant
        List<EtudGen> etuds = new ArrayList<>(sit.getEtudiants());
        etuds.sort(Comparator.comparingDouble((etud) -> ((EtudGen)etud).getScore()).reversed());
        ArrayList<Affectation> res = new ArrayList<>(nbrEtud);
        for (EtudGen etud : etuds) {
            int nbrVoeux = etud.getVoeux().size();
            int found = -1;
            int i = 0;
            while (found == -1 && i < nbrVoeux) {
                int numOffre = etud.getVoeux().get(i);
                int numSpe = sit.getSpecialites().indexOf(etud.getSpecialite());
                if ( totPlaces[numOffre-1] > 0 && placesParSpe[numOffre-1][numSpe] > 0) {
                    found = numOffre;
                    totPlaces[numOffre-1] --;
                    placesParSpe[numOffre-1][numSpe] --;
                }
                i ++;
            }
            if (found != -1) {
                res.add(new Affectation(etud.getIne(), Optional.of(found)));
            } else {
                res.add(new Affectation(etud.getIne(), Optional.empty()));
            }
        }
        return res;
    }

    /**
     * @return the ineEtud
     */
    public String getIneEtud() {
        return ineEtud;
    }

    /**
     * @return the numOffre
     */
    public Optional<Integer> getNumOffre() {
        return numOffre;
    }
    
    public static void testPetit() {
        TestMobilite petit = TestMobilite.tirage(Params.paramsPetit(), 12398);
        System.out.println(petit);
        List<Affectation> affect = affecte(petit);
        System.out.println(affect.stream().map(Affectation::toString).collect(Collectors.joining("\n")));
    }
    
    public static void main(String[] args) {
        testPetit();
    }

    
}

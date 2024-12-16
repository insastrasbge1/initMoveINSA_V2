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

import fr.insa.beuvron.utils.probas.TiragesAlea2;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author francois
 */
public class OffreGen {
    
    private String refOffre;
    private String partenaire;
    private int totPlaces;
    private List<Integer> placesParSpe;

    public OffreGen(String refOffre, String partenaire, int totPlaces, List<Integer> placesParSpe) {
        this.refOffre = refOffre;
        this.partenaire = partenaire;
        this.totPlaces = totPlaces;
        this.placesParSpe = placesParSpe;
    }

    @Override
    public String toString() {
        return "OffreGen{" + refOffre + " : " + partenaire + " tot=" + totPlaces + " parSpe=" + placesParSpe + '}';
    }

    public static OffreGen OffreAlea(String refOffre, String partenaire, Params p, Random r) {
        int totPlaces = TiragesAlea2.choixIndicePondere(p.getProbasTotPlaceParOffre(), r)+1;
        // deux cas particuliers, et un cas général
        double alea = r.nextDouble();
        int nbrSpe = p.getSpecialites().size();
        List<Integer> parSpe;
        System.out.println("libres : " + p.getPercentOffresLibres());
        System.out.println("contraintes : " + p.getPercentOffresContraintes());
        if (alea < p.getPercentOffresContraintes()) {
            // une offre telle que totPlace = somme(places offertes par spécialité)
            // System.out.println(" contrainte");
            parSpe = new ArrayList(IntStream.generate(() -> 0).limit(nbrSpe).boxed().toList());
            for (int i = 0; i < totPlaces; i++) {
                int numSpe = r.nextInt(nbrSpe);
                parSpe.set(numSpe, parSpe.get(numSpe) + 1);
            }
        } else if (alea < p.getPercentOffresContraintes() + p.getPercentOffresLibres()) {
            // une offre telle que places(spe) = totPlace pour toutes les spés
            // System.out.println(" libre");
            parSpe = IntStream.generate(() -> totPlaces).limit(nbrSpe).boxed().toList();
        } else {
            // une offre panachée :
            // pour chaque spé, le nombre de place plSpe est compris entre
            // 0 et totPlaces
            // avec somme(places offertes par spécialité) >= totPlaces
            // System.out.println(" panachee");
            int placesOffertes = 0;
            parSpe = new ArrayList();
            for (int numSpe = 0; numSpe < nbrSpe; numSpe++) {
                int pourSpe = r.nextInt(totPlaces + 1);
                parSpe.add(pourSpe);
                placesOffertes = placesOffertes + pourSpe;
            }
            // j'ajoute si besoin pour arriver à somme(places offertes par spécialité) >= totPlaces
            for (int i = placesOffertes; i < totPlaces; i++) {
                int numSpe = r.nextInt(nbrSpe);
                parSpe.set(numSpe, parSpe.get(numSpe) + 1);
            }
        }
        return new OffreGen(refOffre, partenaire, totPlaces, parSpe);
    }

    public String formatCSV() {
        return this.placesParSpe.stream().map(n -> "" + n).collect(Collectors.joining(";", this.refOffre + ";" + this.partenaire + ";" + this.totPlaces + ";", ""));
    }

    public static List<OffreGen> offresPossibles(List<OffreGen> toutes, int numSpe) {
        return toutes.stream().filter(o -> o.placesParSpe.get(numSpe) != 0).toList();
    }

    /**
     * @return the partenaire
     */
    public String getPartenaire() {
        return partenaire;
    }

    /**
     * @return the totPlaces
     */
    public int getTotPlaces() {
        return totPlaces;
    }

    /**
     * @return the placesParSpe
     */
    public List<Integer> getPlacesParSpe() {
        return placesParSpe;
    }

    /**
     * @return the refOffre
     */
    public String getRefOffre() {
        return refOffre;
    }
    
}

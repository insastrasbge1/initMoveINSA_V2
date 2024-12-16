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

import fr.insa.beuvron.utils.divers.Numeroteur;
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

    private TestMobilite situation;
    private List<AffectUnEtud> affectationEtuds;

    private Affectation(TestMobilite situation, List<AffectUnEtud> affectationEtuds) {
        this.situation = situation;
        this.affectationEtuds = affectationEtuds;
    }

     @Override
    public String toString() {
        return "Affectation{\n"
                + this.affectationEtuds.stream().map(AffectUnEtud::toString).collect(Collectors.joining("\n"))
                + "\n}";
    }

    private int nombreOfIemeChoix(int numChoix) {
        return (int) this.affectationEtuds.stream()
                .filter(ae -> !ae.getRefOffre().isEmpty())
                .filter(ae -> ae.getEtud().getVoeux().size() > numChoix)
                .filter(ae -> ae.getEtud().getVoeux().get(numChoix).equals(ae.getRefOffre().get()))
                .count();
    }
    
    public String toStringAvecStat() {
        String res = "";
        int maxChoix = this.situation.getParams().getProbasNbrVoeux().size() - 1;
        for (int numChoix = 0; numChoix < maxChoix; numChoix++) {
            res = res + "choix N° " + (numChoix+1) + " obtenu : " + nombreOfIemeChoix(numChoix) + "\n";
        }
        long aucunVeux = this.situation.getEtudiants().stream()
                .filter(e -> e.getVoeux().isEmpty())
                .count();
        long nonAffecte = this.affectationEtuds.stream()
                .filter(ae -> ae.getRefOffre().isEmpty())
                .count();
        res = res + "aucun voeux : " + aucunVeux + "\n";
        res = res + "au moins un voeux mais aucune affectation : " + (nonAffecte - aucunVeux) + "\n";
        res = res + "-------- detail ---------" + "\n";
        res = res + this.toString() + "\n";
        res = res + "\n";
        return res;
    }

    /** affecte les étudiants en fonction de leur score et de leurs voeux.
     * <pre>
     * Algo simplifié :
     * <ul>
     *   <li> soit 
     *   <ul>
     *     <li> nbrSpe : le nombre de spécialité </li>
     *     <li> nbrOffre : le nombre d'offres </li>
     *   </ul>
     *   <li> on numérote les offres : 0 .. nbrOffre-1 </li>
     *   <li> on calcule le tableau totPlace[o] = nombre total de places pour l'offre N°o </li>
     *   <li> on calcule la matrice placesParSpe[o][s] = nombre de places pour l'offre N°o pour la spécialité s </li>
     *   <li> on trie les étudiants par score décroissant </li>
     *   <li> pour chaque étudiant etud </li>
     *   <ul>
     *     <li> spe : la spécialité de l'étudiant </li>
     *     <li> listeVoeux : la liste des offres choisies par l'étudiant (par ordre de préférence) </li>
     *     <li> on cherche dans listeVoeux la première offre o telle que  </li>
     *     <ul>
     *       <li> totPlace[o] > 0 </li>
     *       <li> placesParSpe[o][spe] > 0 </li>
     *     </ul>
     *     <li> si l'on a trouvé telle offre oOK  </li>
     *     <ul>
     *       <li> etud obtient oOK  </li>
     *       <li> on décrémente totPlace[oOK]  </li>
     *       <li> on décrémente placesParSpe[oOK][spe] > 0 </li>
     *     </ul>
     *     <li> sinon  </li>
     *     <ul>
     *       <li> etud n'obtient aucune affectation </li>
     *     </ul>
     *   </ul>
     * </ul>
     * </pre>
     * @param sit
     * @return 
     */
    public static Affectation affecte(TestMobilite sit) {
        // je construit d'abord des tableaux correspondant aux places libres dans les offres
        int nbrOffre = sit.getOffres().size();
        int nbrSpe = sit.getSpecialites().size();
        int nbrEtud = sit.getEtudiants().size();
        // pour passer de la ref de l'offre {@code <--> index dans les tableaux}
        Numeroteur<String> refToIndexOffre = new Numeroteur<>();
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
        etuds.sort(Comparator.comparingDouble((etud) -> ((EtudGen) etud).getScore()).reversed());
        ArrayList<AffectUnEtud> res = new ArrayList<>(nbrEtud);
        for (EtudGen etud : etuds) {
            int nbrVoeux = etud.getVoeux().size();
            int found = -1;
            int i = 0;
            while (found == -1 && i < nbrVoeux) {
                int numOffre = refToIndexOffre.getOrAdd(etud.getVoeux().get(i));
                int numSpe = sit.getSpecialites().indexOf(etud.getSpecialite());
                if (totPlaces[numOffre] > 0 && placesParSpe[numOffre][numSpe] > 0) {
                    found = numOffre;
                    totPlaces[numOffre]--;
                    placesParSpe[numOffre][numSpe]--;
                }
                i++;
            }
            if (found != -1) {
               res.add(new AffectUnEtud(etud, Optional.of(refToIndexOffre.getObject(found))));
            } else {
                res.add(new AffectUnEtud(etud, Optional.empty()));
            }
        }
        return new Affectation(sit, res);
    }

    public static void test() {
//        TestMobilite test = TestMobilite.tirage(Params.paramsPetit(), 2991);  // best : 2991
        TestMobilite test = TestMobilite.tirage(GenereTests.paramsINSA(), 2991);  
        System.out.println(test);
        Affectation affect = Affectation.affecte(test);
        System.out.println(affect.toStringAvecStat());
    }

    public static void main(String[] args) {
        test();
    }

    /**
     * @return the affectationEtuds
     */
    public List<AffectUnEtud> getAffectationEtuds() {
        return affectationEtuds;
    }

}

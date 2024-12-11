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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Une classe permettant de générer aléatoirement (suivant certains paramètres)
 * un ensemble de tests.
 * <pre>
 * <p>
 * Génère :
 * <ul>
 *   <li>un ensemble de partenaires</li>
 *   <li>un ensemble d'offres</li>
 *   <li>un ensemble d'étudiants</li>
 *   <li>un ensemble de voeux des étudiants sur les offres</li>
 * </ul>
 * </p>
 * <p>
 * on doit fournir une liste de spécialité, chaque spécialité étant définie par :
 * <ul>
 *   <li> son nom (une String)
 * </ul>
 * </p>
 * <p>
 * Les partenaires sont définies par :
 * <ul>
 *   <li> une ref (une String) </li>
 * </ul>
 * </p>
 * <p>
 * Les offres sont définies par :
 * <ul>
 *   <li> un partenaire (sa ref) </li>
 *   <li> un nombre total de places : totPlaces</li>
 *   <li> pour chacune des spécialité :
 *   <ul>
 *     <li> le nombre de places ouvertes à cette spécialité </li>
 *     <li> peut être 0 : l'offre est interdite à cette spécialité <li>
 *    <li>
 *    trois type d'offres (exemples pour trois spécialités GE,GM,GC):
 *     <ul>
 *       <li>une offre très contrainte : le total des places est égal à la
 *       somme des places dans les diverses spécialités.
 *       Exemple: totPlace = 2 ; places(GE) = 1 ; places(GM) = 1 ; places(GC) = 0 {@code ==>}
 *           il peut y avoir au plus un étudiant de GE et un étudiant de GM
 *       </li>
 *       <li>une offre très libre : le nombre de places dans chaque spécialité
 *       est égal au nombre total de places.
 *       Exemple: totPlace = 2 ; places(GE) = 2 ; places(GM) = 2 ; places(GC) = 2 {@code ==>}
 *           2 places en tout ouvertes à toutes les spécialités
 *       </li>
 *       <li>une offre panachée : pas de contrainte sinon que le nombre total de places
 *       et en général inférieur à la somme des places offertes par spécialité.
 *       Exemple: totPlace = 3 ; places(GE) = 2 ; places(GM) = 2 ; places(GC) = 1 {@code ==>}
 *           3 places en tout {@code ==>} en tout au plus 3 étudiants affectés à l'offre avec au plus,
 *           parmi ces trois étudiants 2 GE, 2 GM et 1 GC
 *       </li>
 *     </ul>
 *     </li>
 *     <p> on aura pourcent d'offres panachées = 1 - percentOffresContraintes - percentOffresLibres
 *   </ul>
 * </ul>
 * </p>
 * <p>
 * Note : pour structurer ce programme, on devrait utiliser les classes du modele pour
 * représenter les semestres, les partenaires, les étudiants ...
 * Mais comme on veut une solution qui ne soit pas liée au reste du modèle (car chaque
 * groupe de projet les aura défini differement), on se crée de petites classes
 * spécifiques pour la génération : OffreGen, EtudGen
 * </p>
 * </pre>
 *
 * @author francois
 */
public class GenereTests {

    public static void test1() {
        try {
            Params p = Params.paramsPetit();
            TestMobilite t = TestMobilite.tirage(p, 0);
            System.out.println(t);
            t.saveInCSV(new File("C:\\temp\\genMoveINSA"), "t1");
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    /** calcule un exemple de mobilite avec des paramètres "proches" de l'INSA Strasbourg.
     * recalculera le même résultat pour un même seed, et des résultats différents
     * pour deux seed différents.
     * Vous pouvez donner un seed de 0 pour tirer le seed au hasard à chaque appel
     * (et donc avoir un résultat différent à chaque appel)
     * 
     * @param outputDir
     * @param seed si 0 tiré au hasard à chaque appel
     * @throws IOException 
     */
    public static void genereSimuINSA(File outputDir,long seed) throws IOException {
        Params p = Params.paramsINSA();
        TestMobilite t = TestMobilite.tirage(p, seed);
        System.out.println(t);
        t.saveInCSV(outputDir, "simuINSA");
    }

    public static void main(String[] args) {
                test1();
//        try {
//            // on fixe le seed pour obtenir toujours le résultat fourni
//            // vous pouvez enlever le seed si vous voulez tester différents
//            // résultats
//            genereSimuINSA(new File("C:\\temp\\genMoveINSA"),5644684);
//        } catch (IOException ex) {
//            throw new Error(ex);
//        }
    }

}

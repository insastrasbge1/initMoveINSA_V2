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

import com.nimbusds.jose.util.StandardCharset;
import fr.insa.beuvron.utils.probas.TiragesAlea2;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author francois
 */
public class TestMobilite {

    private Params params;
    private long seed;
    private List<String> specialites;
    private List<String> partenaires;
    private List<OffreGen> offres;
    private List<EtudGen> etudiants;

    public TestMobilite(Params params, long seed, List<String> specialites,List<String> partenaires, List<OffreGen> offres, List<EtudGen> etudiants) {
        this.seed = seed;
        this.params = params;
        this.specialites = specialites;
        this.partenaires = partenaires;
        this.offres = offres;
        this.etudiants = etudiants;
    }

    /**
     * tirage aléatoire d'une configuration de mobilite. Retournera le même
     * recalculera le même résultat pour un même seed, et des résultats différents
     * pour deux seed différents.
     * Vous pouvez donner un seed de 0 pour tirer le seed au hasard à chaque appel
     * (et donc avoir un résultat différent à chaque appel)
     *
     * @param p les paramètres du problème
     * @param seed pour initialiser le générateur aléatoire. Si zero seed tiré aléatoirement
     * @return
     */
    public static TestMobilite tirage(Params p, long seed) {
        if (seed == 0) {
            seed = new Random().nextLong();
        }
        Random r = new Random(seed);
        // tirage des partenaires
        int nbrPart = r.nextInt(p.getMinNbrPartenaires(), p.getMaxNbrPartenaires() + 1);
        List<String> partenaires = IntStream.range(1, nbrPart + 1).boxed().map(i -> "Univ" + String.format("%03d", i)).toList();
        List<OffreGen> offres = new ArrayList<>();
        int numOffre = 1;
        for (String curPar : partenaires) {
            int nbrOffres = TiragesAlea2.choixIndicePondere(p.getProbasNbrOffres(), r);
            for (int i = 0; i < nbrOffres; i++) {
                offres.add(OffreGen.OffreAlea(numOffre, curPar, p, r));
                numOffre++;
            }
        }
        int numEtud = 1;
        List<EtudGen> etuds = new ArrayList<>();
        List<String> prenoms = p.getPrenomsPossibles();
        for (int numSpe = 0; numSpe < p.getSpecialites().size(); numSpe++) {
            String curSpe = p.getSpecialites().get(numSpe);
            int nbrEtudDansSpe = r.nextInt(p.getMinEffectifsSpe().get(numSpe), p.getMaxEffectifsSpe().get(numSpe) + 1);
            for (int i = 0; i < nbrEtudDansSpe; i++) {
                List<OffreGen> possibles = OffreGen.offresPossibles(offres, numSpe);
                int nbrChoisi = TiragesAlea2.choixIndicePondere(p.getProbasNbrVoeux(), r);
                List<OffreGen> voeux;
                if (possibles.size() <= nbrChoisi) {
                    voeux = new ArrayList<>(possibles);
                } else {
                    voeux = TiragesAlea2.choixAleaMultiple(possibles, nbrChoisi, r);
                }
                System.out.println("voeux : " + voeux);
                Collections.shuffle(voeux, r);
                List<Integer> numVoeux = voeux.stream().map(v -> v.getNumOffre()).toList();
                etuds.add(new EtudGen(p.getPrefixINEEtudiant() + String.format("%04d", numEtud),
                        p.getPrefixNomEtudiant() + String.format("%04d", r.nextInt(p.getNombreDeNomDifferents()) + 1),
                        prenoms.get(r.nextInt(prenoms.size())),
                        curSpe,
                        r.nextDouble(),
                        numVoeux));
                numEtud++;
            }
        }
        return new TestMobilite(p, seed,p.getSpecialites(), partenaires, offres, etuds);
    }

    public void saveInCSV(File inDir, String prefixNomsFichiers) throws IOException {
        try (BufferedWriter outPart = new BufferedWriter(new FileWriter(inDir.toPath().resolve(prefixNomsFichiers + "_specialites.txt").toFile(), StandardCharset.UTF_8))) {
            outPart.append(this.params.getSpecialites().stream().collect(Collectors.joining(";")) + "\n");
        }
        try (BufferedWriter outPart = new BufferedWriter(new FileWriter(inDir.toPath().resolve(prefixNomsFichiers + "_partenaires.txt").toFile(), StandardCharset.UTF_8))) {
            for (String part : this.partenaires) {
                outPart.append(part + "\n");
            }
        }
        try (BufferedWriter outOffres = new BufferedWriter(new FileWriter(inDir.toPath().resolve(prefixNomsFichiers + "_offres.txt").toFile(), StandardCharset.UTF_8))) {
            for (OffreGen offre : this.offres) {
                outOffres.append(offre.formatCSV() + "\n");
            }
        }
        try (BufferedWriter outEtuds = new BufferedWriter(new FileWriter(inDir.toPath().resolve(prefixNomsFichiers + "_etudiants.txt").toFile(), StandardCharset.UTF_8))) {
            for (EtudGen etud : this.etudiants) {
                outEtuds.append(etud.formatCSV() + "\n");
            }
        }
        try (BufferedWriter outVoeux = new BufferedWriter(new FileWriter(inDir.toPath().resolve(prefixNomsFichiers + "_voeux.txt").toFile(), StandardCharset.UTF_8))) {
            for (int curEtud = 0; curEtud < this.etudiants.size(); curEtud++) {
                String ine = this.etudiants.get(curEtud).getIne();
                String voeux = this.etudiants.get(curEtud).getVoeux().stream()
                        .map(n -> "" + n).collect(Collectors.joining(";", ine + ";", ""));
                outVoeux.append(voeux + "\n");
            }
        }
        String readme = """
Données pour simuler la gestion des mobilités
Ces données ont été générées par la méthode tirage de la classe TestMobilite en utilisant :
seed = $SEED
paramètres = $PARAMS
Fichiers générés :
  - $PREFIX_specialites.txt : noms des spécialités
    . une seule ligne
    . les noms séparés par des ;
  - $PREFIX_partenaires.txt : références des partenaires
    . une référence par ligne
  - $PREFIX_offres.txt : offres proposées par les partenaires
    . une offre par ligne
    . format (infos sont séparées par des ;) :
      . le numéro de l'offre
      . la ref du partenaire
      . le nombre total de places offertes
      . le nombre de places pour chaque spécialité, donné dans l'odre des spécialités défini dans le fichier $PREFIX_specialites.txt
  - $PREFIX_etudiants.txt : les étudiants
    . un étudiant par ligne
    . format (infos sont séparées par des ;) :
      . le numéro INE
      . le nom
      . le prénom
      . la spécialité
  - $PREFIX_voeux.txt : les voeux des étudiants
    . les voeux d'un étudiant par ligne
    . format :
      . INE;voeux1;voeux2; ... ;voeuxn
      . chaque voeux est le numéro de l'offre sur laquelle l'étudiant postule
      . cas particulier si pas de voeux : INE;
"""
                .replace("$SEED", "" + this.seed)
                .replace("$PARAMS", this.params.toString())
                .replace("$PREFIX", prefixNomsFichiers);
        try (BufferedWriter outReadme = new BufferedWriter(new FileWriter(inDir.toPath().resolve(prefixNomsFichiers + "_README.txt").toFile(), StandardCharset.UTF_8))) {
            outReadme.append(readme + "\n");
        }
    }

    @Override
    public String toString() {
        return "TestMobilite{"
                + "\nparams=" + params
                + "\nseed=" + seed
                + "\npartenaires=" + this.partenaires.stream().collect(Collectors.joining("\n", "{\n", "\n}"))
                + "\noffres=" + offres.stream().map(Object::toString).collect(Collectors.joining("\n", "{\n", "\n}"))
                + "\netudiants=" + etudiants.stream().map(Object::toString).collect(Collectors.joining("\n", "{\n", "\n}"))
                + "\nnombre de partenaires=" + this.partenaires.size()
                + "\nnombre d'offres=" + this.offres.size()
                + "\nnombre total de places offertes=" + this.offres.stream().map(o -> o.getTotPlaces()).reduce(0, (a, b) -> a + b)
                + "\nnombre d'étudiants=" + this.etudiants.size()
                + "\nnombre d'étudiants sans voeux =" + this.etudiants.stream().filter(e -> e.getVoeux().isEmpty()).count()
                + "\n}";
    }

    /**
     * @return the offres
     */
    public List<OffreGen> getOffres() {
        return offres;
    }

    /**
     * @return the etudiants
     */
    public List<EtudGen> getEtudiants() {
        return etudiants;
    }

    /**
     * @return the params
     */
    public Params getParams() {
        return params;
    }

    /**
     * @return the seed
     */
    public long getSeed() {
        return seed;
    }

    /**
     * @return the specialites
     */
    public List<String> getSpecialites() {
        return specialites;
    }

    /**
     * @return the partenaires
     */
    public List<String> getPartenaires() {
        return partenaires;
    }

}

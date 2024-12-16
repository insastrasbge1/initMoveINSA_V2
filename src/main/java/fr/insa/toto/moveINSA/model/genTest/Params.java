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

import java.util.List;

/**
 *
 * @author francois
 */
public class Params {

    /**
     * liste des noms des specialites
     */
    private List<String> specialites;
    /**
     * pour chaque specialité, le nombre minimum d'étudiants participant au
     * mouvement. le nombre effectif sera tiré aléatoirement entre min (inclu)
     * et max (inclu)
     */
    private List<Integer> minEffectifsSpe;
    /**
     * pour chaque specialité, le nombre maximum d'étudiants participant au
     * mouvement. le nombre effectif sera tiré aléatoirement entre min (inclu)
     * et max (inclu)
     */
    private List<Integer> maxEffectifsSpe;
    /**
     * prefix sur le nom les refs des partenaires.
     */
    private String prefixPartenaires;
    /**
     * le nombre minimum de partenaires . le nombre effectif sera tiré
     * aléatoirement entre min (inclu) et max (inclu)
     */
    private int minNbrPartenaires;
    /**
     * le nombre maximum de partenaires . le nombre effectif sera tiré
     * aléatoirement entre min (inclu) et max (inclu)
     */
    private int maxNbrPartenaires;

    /**
     * indique les "probabilités" que les partenaires proposent 0,1,2,...n-1
     * offres.
     * <pre>
     * Ce ne sont pas des vraies proba mais des "chances relatives". Pour
     * obtenir des probas, il faudrait diviser chaque élément par la somme des
     * éléments. Exemples :
     * <ul>
     *   <li> [0.1,0.3,0.6] là ce sont effectivement les probabilité
     *     d'avoir 0,1 ou 2 offres puisque somme = 1. </li>
     *   <li> [10,30,60] représente la même chose
     *     (puisqu'il faut diviser par 100 pour avoir les probas. </li>
     *   <li> [3,9,18] encore la même chose. </li>
     * </ul>
     * </pre>
     */
    private List<Double> probasNbrOffres;

    /**
     * indique les "probabilités" qu'une offre comporte proposent 1,2,...n
     * places au total.
     * <pre>
     * Ce ne sont pas des vraies proba mais des "chances relatives". Pour
     * obtenir des probas, il faudrait diviser chaque élément par la somme des
     * éléments. Exemples :
     * <ul>
     *   <li> [0.1,0.3,0.6] là ce sont effectivement les probabilité
     *     d'avoir 1,2 ou 3 places puisque somme = 1. </li>
     *   <li> [10,30,60] représente la même chose
     *     (puisqu'il faut diviser par 100 pour avoir les probas. </li>
     *   <li> [3,9,18] encore la même chose. </li>
     * </ul>
     * </pre>
     */
    private List<Double> probasTotPlaceParOffre;
    /**
     * prefix pour les reférences des offres.*
     */
    private String prefixRefOffres;
    /**
     * Pourcentage d'offre "contraintes" parmis les offres.*
     */
    private double percentOffresContraintes;
    /**
     * Pourcentage d'offre "libres" parmis les offres.*
     */
    private double percentOffresLibres;
    /**
     * les noms possibles iront de prefix+1 à prefix+n, ou n est fixé par
     * nombreDeNomDifferents.*
     */
    private String prefixNomEtudiant;
    /**
     * les INE des étudiant iront de prefix+1 à prefix+ne, ou ne est le nombre
     * total d'étudiants.*
     */
    private String prefixINEEtudiant;
    /**
     * les noms possibles iront de prefix+1 à prefix+n. permet d'avoir plus ou
     * moins de noms communs pour les étudiants*
     */
    private int nombreDeNomDifferents;
    /**
     * donne une liste des prénoms possibles pour les étudiants *
     */
    private List<String> prenomsPossibles;
    /**
     * indique les "probabilités" qu'un étudiant ait fait 0,1,2,...n-1 voeux.
     * <pre>
     * Ce ne sont pas des vraies proba mais des "chances relatives". Pour
     * obtenir des probas, il faudrait diviser chaque élément par la somme des
     * éléments. Exemples :
     * <ul>
     *   <li> [0.1,0.3,0.6] là ce sont effectivement les probabilité
     *     d'avoir 0,1 ou 2 voeux puisque somme = 1. </li>
     *   <li> [10,30,60] représente la même chose
     *     (puisqu'il faut diviser par 100 pour avoir les probas. </li>
     *   <li> [3,9,18] encore la même chose. </li>
     * </ul>
     * </pre>
     */
    private List<Double> probasNbrVoeux;

    public Params(List<String> specialites, List<Integer> minEffectifsSpe, List<Integer> maxEffectifsSpe,
            String prefixPartenaire,
            int minNbrPartenaires, int maxNbrPartenaires,
            List<Double> probasNbrOffres,
            List<Double> probasTotPlaceParOffre,
            String prefixRefOffres,
            double percentOffresContraintes, double percentOffresLibres,
            String prefixINEEtudiant, String prefixNomEtudiant,
            int nombreDeNomDifferents, List<String> prenomsPossibles,
            List<Double> probasNbrVoeux) {
        this.specialites = specialites;
        this.minEffectifsSpe = minEffectifsSpe;
        this.maxEffectifsSpe = maxEffectifsSpe;
        this.prefixPartenaires = prefixPartenaire;
        this.minNbrPartenaires = minNbrPartenaires;
        this.maxNbrPartenaires = maxNbrPartenaires;
        this.probasNbrOffres = probasNbrOffres;
        this.probasTotPlaceParOffre = probasTotPlaceParOffre;
        this.prefixRefOffres = prefixRefOffres;
        this.percentOffresContraintes = percentOffresContraintes;
        this.percentOffresLibres = percentOffresLibres;
        this.prefixINEEtudiant = prefixINEEtudiant;
        this.prefixNomEtudiant = prefixNomEtudiant;
        this.nombreDeNomDifferents = nombreDeNomDifferents;
        this.prenomsPossibles = prenomsPossibles;
        this.probasNbrVoeux = probasNbrVoeux;
    }

    @Override
    public String toString() {
        return "Params{"
                + "\n  specialites=" + specialites
                + "\n  minEffectifsSpe=" + minEffectifsSpe + "\n  maxEffectifsSpe=" + maxEffectifsSpe
                + "\n  minNbrPartenaires=" + minNbrPartenaires + "\n  maxNbrPartenaires=" + maxNbrPartenaires
                + "\n  probasNbrOffres=" + probasNbrOffres
                + "\n  probasTotPlaceParOffre=" + probasTotPlaceParOffre 
                + "\n  prefixRefOffres=" + prefixRefOffres + "\n"
                + "\n  percentOffresContrainte=" + percentOffresContraintes + "\n  percentOffresLibres=" + percentOffresLibres
                + "\n  prefixINEEtudiant=" + prefixINEEtudiant + "\n  prefixNomEtudiant=" + prefixNomEtudiant
                + "\n  nombreDeNomDifferents=" + nombreDeNomDifferents + "\n  prenomsPossibles=" + prenomsPossibles
                + "\n  probasNbrVoeux=" + probasNbrVoeux 
                + "\n}";
    }

    /**
     * @return the specialites
     */
    public List<String> getSpecialites() {
        return specialites;
    }

    /**
     * @param specialites the specialites to set
     */
    public void setSpecialites(List<String> specialites) {
        this.specialites = specialites;
    }

    /**
     * @return the minEffectifsSpe
     */
    public List<Integer> getMinEffectifsSpe() {
        return minEffectifsSpe;
    }

    /**
     * @param minEffectifsSpe the minEffectifsSpe to set
     */
    public void setMinEffectifsSpe(List<Integer> minEffectifsSpe) {
        this.minEffectifsSpe = minEffectifsSpe;
    }

    /**
     * @return the maxEffectifsSpe
     */
    public List<Integer> getMaxEffectifsSpe() {
        return maxEffectifsSpe;
    }

    /**
     * @param maxEffectifsSpe the maxEffectifsSpe to set
     */
    public void setMaxEffectifsSpe(List<Integer> maxEffectifsSpe) {
        this.maxEffectifsSpe = maxEffectifsSpe;
    }

    /**
     * @return the minNbrPartenaires
     */
    public int getMinNbrPartenaires() {
        return minNbrPartenaires;
    }

    /**
     * @param minNbrPartenaires the minNbrPartenaires to set
     */
    public void setMinNbrPartenaires(int minNbrPartenaires) {
        this.minNbrPartenaires = minNbrPartenaires;
    }

    /**
     * @return the maxNbrPartenaires
     */
    public int getMaxNbrPartenaires() {
        return maxNbrPartenaires;
    }

    /**
     * @param maxNbrPartenaires the maxNbrPartenaires to set
     */
    public void setMaxNbrPartenaires(int maxNbrPartenaires) {
        this.maxNbrPartenaires = maxNbrPartenaires;
    }

    /**
     * @return the percentOffresContraintes
     */
    public double getPercentOffresContraintes() {
        return percentOffresContraintes;
    }

    /**
     * @param percentOffresContraintes the percentOffresContraintes to set
     */
    public void setPercentOffresContraintes(double percentOffresContraintes) {
        this.percentOffresContraintes = percentOffresContraintes;
    }

    /**
     * @return the percentOffresLibres
     */
    public double getPercentOffresLibres() {
        return percentOffresLibres;
    }

    /**
     * @param percentOffresLibres the percentOffresLibres to set
     */
    public void setPercentOffresLibres(double percentOffresLibres) {
        this.percentOffresLibres = percentOffresLibres;
    }

    /**
     * @return the prefixNomEtudiant
     */
    public String getPrefixNomEtudiant() {
        return prefixNomEtudiant;
    }

    /**
     * @param prefixNomEtudiant the prefixNomEtudiant to set
     */
    public void setPrefixNomEtudiant(String prefixNomEtudiant) {
        this.prefixNomEtudiant = prefixNomEtudiant;
    }

    /**
     * @return the prefixINEEtudiant
     */
    public String getPrefixINEEtudiant() {
        return prefixINEEtudiant;
    }

    /**
     * @param prefixINEEtudiant the prefixINEEtudiant to set
     */
    public void setPrefixINEEtudiant(String prefixINEEtudiant) {
        this.prefixINEEtudiant = prefixINEEtudiant;
    }

    /**
     * @return the nombreDeNomDifferents
     */
    public int getNombreDeNomDifferents() {
        return nombreDeNomDifferents;
    }

    /**
     * @param nombreDeNomDifferents the nombreDeNomDifferents to set
     */
    public void setNombreDeNomDifferents(int nombreDeNomDifferents) {
        this.nombreDeNomDifferents = nombreDeNomDifferents;
    }

    /**
     * @return the prenomsPossibles
     */
    public List<String> getPrenomsPossibles() {
        return prenomsPossibles;
    }

    /**
     * @param prenomsPossibles the prenomsPossibles to set
     */
    public void setPrenomsPossibles(List<String> prenomsPossibles) {
        this.prenomsPossibles = prenomsPossibles;
    }


    /**
     * @return the probasNbrOffres
     */
    public List<Double> getProbasNbrOffres() {
        return probasNbrOffres;
    }

    /**
     * @param probasNbrOffres the probasNbrOffres to set
     */
    public void setProbasNbrOffres(List<Double> probasNbrOffres) {
        this.probasNbrOffres = probasNbrOffres;
    }

    /**
     * @return the probasTotPlaceParOffre
     */
    public List<Double> getProbasTotPlaceParOffre() {
        return probasTotPlaceParOffre;
    }

    /**
     * @param probasTotPlaceParOffre the probasTotPlaceParOffre to set
     */
    public void setProbasTotPlaceParOffre(List<Double> probasTotPlaceParOffre) {
        this.probasTotPlaceParOffre = probasTotPlaceParOffre;
    }

    /**
     * @return the probasNbrVoeux
     */
    public List<Double> getProbasNbrVoeux() {
        return probasNbrVoeux;
    }

    /**
     * @param probasNbrVoeux the probasNbrVoeux to set
     */
    public void setProbasNbrVoeux(List<Double> probasNbrVoeux) {
        this.probasNbrVoeux = probasNbrVoeux;
    }

    /**
     * @return the prefixRefOffres
     */
    public String getPrefixRefOffres() {
        return prefixRefOffres;
    }

    /**
     * @param prefixRefOffres the prefixRefOffres to set
     */
    public void setPrefixRefOffres(String prefixRefOffres) {
        this.prefixRefOffres = prefixRefOffres;
    }

    /**
     * @return the prefixPartenaires
     */
    public String getPrefixPartenaires() {
        return prefixPartenaires;
    }

    /**
     * @param prefixPartenaires the prefixPartenaires to set
     */
    public void setPrefixPartenaires(String prefixPartenaires) {
        this.prefixPartenaires = prefixPartenaires;
    }

}

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
public class EtudGen {
    
    private String ine;
    private String nom;
    private String prenom;
    private String specialite;
    private double score;
    private List<String> voeux;

    public EtudGen(String ine, String nom, String prenom, String specialite,double score,List<String> voeux) {
        this.ine = ine;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        this.score=score;
        this.voeux = voeux;
    }

    @Override
    public String toString() {
        return "EtudGen{" + "ine=" + ine + ", nom=" + nom + ", prenom=" + prenom 
                + ", specialite=" + specialite 
                + ", score=" + score 
                + ", voeux=" + voeux + '}';
    }

    public String formatCSV() {
        return ine + ";" + nom + ";" + prenom + ";" + specialite+ ";" + score;
    }

    /**
     * @return the ine
     */
    public String getIne() {
        return ine;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @return the specialite
     */
    public String getSpecialite() {
        return specialite;
    }

    /**
     * @return the voeux
     */
    public List<String> getVoeux() {
        return voeux;
    }

    /**
     * @return the score
     */
    public double getScore() {
        return score;
    }
    
}

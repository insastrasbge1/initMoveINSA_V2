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
package fr.insa.toto.moveINSA.model;

import fr.insa.beuvron.utils.ConsoleFdB;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe "miroir" de la table offremobilite.
 * <p>
 * pour un commentaire plus détaillé sur ces classes "miroir", voir dans la
 * classe Partenaire
 * </p>
 *
 * @author francois
 */
public class OffreMobilite {

   private int id;
    private String ref;
    private int nbrPlaces;
    private int proposePar;

    /**
     * création d'une nouvelle Offre en mémoire, non existant dans la Base de
     * donnée.
     */
    public OffreMobilite(String ref,int nbrPlaces, int proposePar) {
        this(-1, ref,nbrPlaces, proposePar);
    }

    /**
     * création d'une Offre retrouvée dans la base de donnée.
     */
    public OffreMobilite(int id,String ref, int nbrPlaces, int proposePar) {
        this.id = id;
        this.ref = ref;
        this.nbrPlaces = nbrPlaces;
        this.proposePar = proposePar;
    }

    @Override
    public String toString() {
        return "OffreMobilite{" + "id=" + this.getId() + " ; ref=" + ref+ " ; nbrPlaces=" + nbrPlaces + " ; proposePar=" + proposePar + '}';
    }

    /**
     * Sauvegarde une nouvelle entité et retourne la clé affecté automatiquement
     * par le SGBD.
     * <p>
     * la clé est également sauvegardée dans l'attribut id
     * </p>
     *
     * @param con
     * @return la clé de la nouvelle entité dans la table de la BdD
     * @throws EntiteDejaSauvegardee si l'id de l'entité est différent de -1
     * @throws SQLException si autre problème avec la BdD
     */
    public int saveInDB(Connection con) throws SQLException {
        if (this.getId() != -1) {
            throw new fr.insa.toto.moveINSA.model.EntiteDejaSauvegardee();
        }
        try (PreparedStatement insert = con.prepareStatement(
                "insert into offremobilite (ref,nbrplaces,proposepar) values (?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            insert.setString(1, ref);
            insert.setInt(2, this.nbrPlaces);
            insert.setInt(3, this.proposePar);
            insert.executeUpdate();
            try (ResultSet rid = insert.getGeneratedKeys()) {
                rid.next();
                this.id = rid.getInt(1);
                return this.getId();
            }
        }
    }

    public static class InvalidOffreException extends IOException {
        public InvalidOffreException(int numLigne,String message) {
            super("offre invalide en ligne" + numLigne + " : " + message);
        }
    }
    public static void createFromCSV(Connection con, BufferedReader bin) throws IOException, SQLException {
       String line;
       int numLigne = 1;
       try {
        while ((line = bin.readLine()) != null) {
            if (! line.trim().isEmpty()) {
                String[] decompose = line.split(";");
                String refPartenaire = decompose[1];
                Optional<Partenaire> part = Partenaire.trouvePartaire(con, refPartenaire);
                if (part.isEmpty()) {
                    throw new InvalidOffreException(numLigne,"partenaire " + refPartenaire + " inconnu");
                }
                // les places par spécialité sont ignorées puisqu'elles n'existent pas dans ce modèle simplifié
                OffreMobilite o = new OffreMobilite(decompose[0],Integer.parseInt(decompose[2]), part.get().getId());
                o.saveInDB(con);
            }
        }
       } 
       // on laisse passer les exceptions déjà prévues
       catch (IOException | SQLException ex) {
           throw ex;
       } 
       // on gère les autres exceptions : du genre il n'y a pas assez de ; sur la ligne
       catch (Exception ex) {
           throw new InvalidOffreException(numLigne, ex.getLocalizedMessage());
       }
    }

     public static List<OffreMobilite> toutesLesOffres(Connection con) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "select id,ref,nbrplaces,proposepar from offremobilite")) {
            ResultSet rs = pst.executeQuery();
            List<OffreMobilite> res = new ArrayList<>();
            while (rs.next()) {
                res.add(new OffreMobilite(rs.getInt(1), rs.getString(2),rs.getInt(3), rs.getInt(4)));
            }
            return res;
        }
    }

    public static int creeConsole(Connection con) throws SQLException {
        Partenaire p = Partenaire.selectInConsole(con);
        String ref = ConsoleFdB.entreeString("ref de l'offre");
        int nbr = ConsoleFdB.entreeInt("nombre de places : ");
        OffreMobilite nouveau = new OffreMobilite(ref,nbr, p.getId());
        return nouveau.saveInDB(con);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

}

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
package fr.insa.toto.moveINSA.gui.vues;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.insa.beuvron.vaadin.utils.ConnectionPool;
import fr.insa.toto.moveINSA.gui.MainLayout;
import fr.insa.toto.moveINSA.gui.session.SessionInfo;
import fr.insa.toto.moveINSA.model.OffreMobilite;
import fr.insa.toto.moveINSA.model.Partenaire;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Utilisation du composant Upload de vaadin pour charger une liste de
 * partenaires. voir https://vaadin.com/docs/latest/components/upload
 *
 * @author francois
 */
@PageTitle("MoveINSA")
@Route(value = "offres/upload", layout = MainLayout.class)
public class UploadOffres extends VerticalLayout {

    private Upload upload;
    private MultiFileMemoryBuffer buffer;

    public UploadOffres() {
        this.buffer = new MultiFileMemoryBuffer();
        this.upload = new Upload(buffer);
        this.add(new H3("sélectionnez un fichier contenant une liste d'offres"));
        this.add(new Paragraph("format attendu : une offre par ligne"));
        this.add(new Paragraph("format d'une ligne : <ref offre>;<ref du partenaire>;<nombre de places> (;<nbrPlacesParSpe>)*"));
        this.add(new Paragraph("encodage attendu : UTF8"));
        this.add(this.upload);
        this.upload.addSucceededListener((t) -> {
            String filename = t.getFileName();
            InputStream in = buffer.getInputStream(filename);
            try (Connection con = ConnectionPool.getConnection()) {
                OffreMobilite.createFromCSV(con, new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));
            }
            catch (IOException | SQLException ex) {
                Notification.show("Problem uploading partenaires : " + ex.getLocalizedMessage());
            }
        });
    }

}

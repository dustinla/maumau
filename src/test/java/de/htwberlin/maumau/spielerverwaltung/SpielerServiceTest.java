/**
 * @author Joerg Lehmann, Christian Fiebelkorn, Dustin Lange
 * @version 20181113
 *
 */

package de.htwberlin.maumau.spielerverwaltung;

import de.htwberlin.maumau.kartenverwaltung.entity.Farbe;
import de.htwberlin.maumau.kartenverwaltung.entity.Karte;
import de.htwberlin.maumau.spielerverwaltung.entity.Spieler;
import de.htwberlin.maumau.spielerverwaltung.export.SpielerService;
import de.htwberlin.maumau.spielerverwaltung.impl.SpielerServiceImpl;
import de.htwberlin.maumau.spielverwaltung.entity.Spiel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SpielerServiceTest {



    List<Karte> handkarten = new ArrayList<>();
    private SpielerService spielerService;
    private Karte karte1 = new Karte(Farbe.PIK, "10");
    private Karte karte2 = new Karte(Farbe.PIK, "9");
    private Karte karte3 = new Karte(Farbe.HERZ, "9");
    private Spieler spieler = new Spieler();
    private Spiel spiel = new Spiel();


    @Before
    public void setUp() {
        spielerService = new SpielerServiceImpl();
        handkarten.add(karte1);
        handkarten.add(karte2);
        spieler.setHandkarten(handkarten);
    }

    @Test
    public void testKarteZuHandblatthinzufuegen() {
        spielerService.karteZuHandblatthinzufuegen(karte3, spieler);
        handkarten.add(karte3);
        assertEquals(handkarten, spieler.getHandkarten());
    }

    @Test
    public void testKarteausHandblattentfernden() {
        spielerService.karteausHandblattentfernden(karte2, spieler);
        handkarten.remove(karte2);
        assertEquals(handkarten, spieler.getHandkarten());
    }

    @Test
    public void testNeuerSpielerAnlegen() {
        Spieler spielerHans = spielerService.neuerSpielerAnlegen("Hans");
        spieler.setName("Hans");
        assertEquals(spieler.getName(), spielerHans.getName());
    }


}
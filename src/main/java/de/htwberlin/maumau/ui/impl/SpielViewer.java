/**
 * @author Joerg Lehmann, Christian Fiebelkorn, Dustin Lange
 * @version 20190203
 */
package de.htwberlin.maumau.ui.impl;

import de.htwberlin.maumau.kartenverwaltung.entity.Farbe;

public class SpielViewer {

    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";


    /**
     * Diese Methode fragt ab, ob ein weiterer menschlicher Spieler hinzugefuegt werden soll
     *
     */
    public void sollSpielerHinzugefuegtWerden() {
        System.out.println();
        System.out.println("Möchtest du einen weiteren menschlichen Spieler zum Spiel hinzufügen?");
    }

    /**
     * Diese Methode gibt erst eine gestrichelte Linie und im Anschluss eine leere Zeile aus
     */
    public void leereZeileMitStichen() {
        System.out.println("-------------------------------------------------------");
        System.out.println();
    }

    /**
     * Diese Methode fordert den Spieler nach fehlerhafter ja nein Eingabe auf,
     * entweder ja oder nein einzugeben
     */
    public void jaNeinAbfrageFehlermeldung() {
        System.out.println(ANSI_RED + "Deine Eingabe war fehlerhaft, bitte gib \"ja\" oder \"nein\" ein." + ANSI_RESET);
    }

    /**
     * Diese Methode zeigt die Regeln des Spieles an.
     */
    public void anzeigenRegeln() {
        System.out.println("\n----  DIE REGELN ----\n" +
                "- Die einfachen Regeln -\n" +
                "Jeder Spieler darf im Uhrzeigersinn der Reihe nach jeweils eine Karte ablegen.\n" +
                "Es darf nur eine Karte gelegt werden, wenn diese entweder in Farbe (Herz, Kreuz, Pik, Karo) \n" +
                "oder in Wert (zum Beispiel: König oder 7) übereinstimmen.\n" +
                "Sollte ein Spieler nicht legen können, so muss der eine Karte ziehen und der nächste ist an der Reihe.\n\n" +
                "Wenn ein Spieler seine vorletzte Karte legen will, muss er zuvor \"m\" für Mau eingeben.\n" +
                "Wird \"Mau\" vergessen, bekommt der Spieler 2 Strafkarten auf die Hand.");
        System.out.println("\n" +
                "- Die erweiterten Regeln - \n" +
                "Die nachfolgenden Reglen gelten zusätzlich zu den einfachen Regeln.\n" +
                "Wenn ein Spieler einen \"Buben\" legt, muss er sich eine Farbe wünschen, dabei spielt es keine Rolle,\n" +
                "um welche Farbe es sich handelt.\n" +
                "Aber auf einen Buben darf kein weiterer gelegt werden.\n" +
                "Legt ein Spieler eine \"7\", so muss der nächste Spieler 2 Karten ziehen.\n" +
                "Legt ein Spieler eine \"8\", so muss der nachfolgende Aussetzen.\n" +
                "Legt hingegen ein Spieler eine \"9\" so wird die Spielrichtung umgedreht.\n");
    }

    /**
     * Feedback, wenn der Spieler Mau gesagt hat
     */
    public void maugesagt() {
        System.out.println("Du hast soeben \"Mau\" gesagt.");
        System.out.println("Welche Karte möchtest du legen?");
    }

    /**
     * Gibt die Infos zu einer Karte aus
     *
     * @param kartennummer - Kartennummer auf der Hand
     * @param farbe - Farbe der Karte
     * @param wert - Wert der Karte
     */
    public void ausgabeKarte(int kartennummer, Farbe farbe, String wert) {
        System.out.println("Kartennummer " + kartennummer + " : " + farbe + " " + wert);
    }

    /**
     * Methode fragt, welche Karte der Spieler ablegen will oder ob er eine Ziehen will
     * Außerdem das der Spieler hier Mau sagen koennte
     */
    public void welcheKarteAblegen() {
        System.out.println("\nWelche Karte möchtest du legen? (Sollte Mau nötig sein, gib es zuerst ein \"m\" \nund bestätige dies mit ENTER.)\n" +
                "Wenn keine Karte möglich ist, einfach \"z\" für ziehen eingeben. \n" +
                "Gib bitte die Kartennummer ein.");
    }

    /**
     * Methode sagt dem Spieler, das die Nummer seiner Handkarte, die er legen wollte, unsinnig ist
     */
    public void kartennummerUnsinnig() {
        System.out.println(ANSI_RED + "Du hast eine Eingabe getätigt, die bei deinen Handkarten nicht sinnig ist.\n" +
                "Bitte tätige eine sinnige Eingabe." + ANSI_RESET);
    }

    /**
     * Methode begruesst die Spieler zu einem neuen Spiel
     * und fragt ihn ob er ein neues Spiel starten oder ein altes fortsetzen will
     */
    public void willkommen() {
        System.out.println("\n" +
                "-----------------------------------------------------------" +
                "\nWillkommen beim MauMau Spiel.");
        System.out.println("Wenn du ein neues Spiel beginnen willst, gibt bitte die 1 ein"); //Vorbereitung Aufgabe 4
        System.out.println("Wenn du ein Spiel fortsetzen möchtest, wähle die 2");
        System.out.println("Welche Variante möchtest du spielen?");
    }

    /**
     * Methode fragt nach dem Namen des menschlichen Spieler
     */
    public void spielerNamenAnfragen() {
        System.out.println();
        System.out.println("Welchen Namen soll der menschliche Spieler haben?");
    }

    /**
     * Methode fragt, ob die Regeln angezeigt werden sollen
     */
    public void sollenRegelnAngezeigtWerden() {
        System.out.println();
        System.out.println("Möchtet ihr euch die Regeln anzeigen lassen,\n" +
                "bevor ihr entscheidet ob ihr mit einfachen oder erweiterten Regeln spielt?\n" +
                "ACHTUNG: Die Regeln können nur jetzt angesehen werden.");
    }

    /**
     * Methode fragt, ob nach erweiterten Regeln gespielt werden soll
     */
    public void sollNachErweitertenRegelnGespieltWerden() {
        System.out.println("Möchtet ihr nach erweiterten Regeln spielen?");
    }

    /**
     * Diese Methode zeigt dem jeweils naechsten Spieler alle wichtigen Infos fuer seinen Zug an
     *
     * @param spielername - Name des Spielers
     * @param anzahlGezogenerKarten - Anzahl der Karten die er zu beginn seines Zuges ziehen musste
     */
    public void infosfuerNaechstenSpieler(String spielername, int anzahlGezogenerKarten) {
        System.out.println("\n\n-- Aktueller Spieler --");
        System.out.println(spielername);
        System.out.println("\nDu musstest " + anzahlGezogenerKarten + " Karten ziehen.");

    }

    /**
     * Diese Methode gibt aus, welche Karte oben auf dem Ablagestapel liegt
     *
     * @param obersteKarteAblagestapelFarbe - Farbe der obersten Karte auf dem Ablagestapel
     * @param obersteKarteAblagestapelWert - Wert der obersten Karte auf dem Ablagestapel
     */
    public void ablagestapelZeigt(Farbe obersteKarteAblagestapelFarbe, String obersteKarteAblagestapelWert) {
        System.out.println("Die obsterste Karte des Ablagestapels zeigt: " + obersteKarteAblagestapelFarbe + " " + obersteKarteAblagestapelWert);
    }

    /**
     * Methode weisst den Spieler darauf hin, dass er eine falsche Karte legen wollte
     */
    public void falscheKarte() {
        System.out.println(ANSI_RED + "Du hast versucht eine nicht mögliche Karte zulegen, " +
                "bitte lege nun eine mögliche." + ANSI_RESET);
    }

    /**
     * Methode bitten den Spieler, nach dem Legen eines Buben, eine Farbe zu waehlen
     */
    public void farbeWaehlen() {
        System.out.println("Du hast einen Buben gelegt bitte wähle die Zahl der Farbe:\n" +
                "Zur Auswahl stehen\n1: Herz\n2: Kreuz\n3: Karo\n4: Pik\n");
    }

    /**
     * Methode weisst den naechsten Spieler daraufhin, dass durch einen Buben soeben die Farbe gewechselt wurde
     * @param farbeNachBube - Die Farbe die nun nach dem Buben gilt
     */
    public void spielerInfoNachBube(Farbe farbeNachBube) {
        System.out.println("Der letzte Spieler hat einen Buben gelegt und sich die Farbe \"" + farbeNachBube + "\" gewünscht.");
    }

    /**
     * Information, dass der Spieler vergessen habe Mau zu sagen und daher zwei Strafkarten auf die Hand bekommen hat
     */
    public void strafkartenVergessenesMau(String spielername) {
        System.out.println(ANSI_RED + "Der Spieler " + spielername + " hat vergessen Mau zu sagen und musste daher 2 Strafkarten ziehen" + ANSI_RESET);
    }

    /**
     * Informiert, dass automatisch ein weiterer Spieler hinzugefuegt wurde, da die Mindestspielerzahl 2 betraegt
     */
    public void weitererSpielerNoetig() {
        System.out.println("Da für ein Spiel mindestens zwei Spieler nötig sind,\nmuss ein weiterer hinzugefügt werden.");
    }

    /**
     * Methode gibt aus, das ein anderer gespielt hat
     * @param spielerName - Name des Spielers der gespielt hat
     */
    public void kiHatGespielt(String spielerName) {
        System.out.println(spielerName + " hat gespielt.");
    }

    /**
     * Fragt den Spieler ob er ein neues Spiel starten will
     */
    public void weitereSpielStarten() {
        System.out.println("Möchtest du ein weiteres Spiel starten?");
    }

    /**
     * Methode gibt aus, das ein Spieler gewonnen hat.
     * @param name - Name des Gewinners
     */
    public void siegerAusgabe(String name) {
        System.out.println("Gewonnen hat " + name);
    }

    /**
     * Methode informiert den Spieler das ein Karte ziehen nicht moeglich ist,
     * da alle Karten bereits auf den Spielerhaenden liegen
     */
    public void spielerBetruegen() {
        System.out.println(ANSI_RED + "Ein Kartenziehen ist nicht mehr möglich, da ihr betrogen habt " +
                "\nund sich nun alle Karten auf euren Händen befinden " + ANSI_RESET);
    }

    /**
     * Diese Methode informiert über einen weiteren Mitspieler und dessen Anzahl an Handkarten
     * @param mitspielername - Name des Mitspielers
     * @param mitspielerhandkarten - Anzahl der Handkarten jenes Mitspielers
     */
    public void infosUeberAndereSpieler(String mitspielername, int mitspielerhandkarten) {
        System.out.println("Der Mitspieler " + mitspielername + " hat " + mitspielerhandkarten + " Handkarte(n)");
    }

    /**
     * Methode informiert darueber das ein anderer Spieler eine Karte gezogen hat
     * @param name - Name des Spielers, der eine Karte gezogen hat
     */
    public void pchatgezogen(String name) {
        System.out.println(name + " hat gezogen.");
    }

    /**
     * Methode gibt aus, das der letzte Spieler Mau sagte
     */
    public void spielerSagteMau() {
        System.out.println("Der letzte Spieler sagte Mau");
    }

    /**
     * Methode zeigt die Frage, wie viele pc gesteuerte Spieler gewuenscht sind
     */
    public void anzahlKI() {
        System.out.println("Wie viele computergesteuerte Spieler möchtest du haben?");
    }

    /**
     * Methode gibt an, dass eine gewisse Zahleneingabe erwartet wurde, diese aber nicht erfuellt wurde
     *
     * @param mininaleZahl - kleinste erwartete Zahl
     * @param maximaleZahl - groesste erwartete Zahl
     */
    public void eingabeZahlFehlerhaft(int mininaleZahl, int maximaleZahl) {
        System.out.println("Deine Eingabe war fehlerhaft, bitte gib eine Zahl zwischen " + mininaleZahl + " und "
                + maximaleZahl + " ein.");
    }

    /**
     * Methode gibt dank aus, das der Spieler dieses Spiel spielte
     */
    public void spielende() {
        System.out.println("Es war schön mit dir zu spielen. Bitte schaue bald wieder vorbei.");
    }

    /**
     * gibt Aufforderung zum Eingeben der SpielID aus.
     */
    public void spielIDeingeben() {
        System.out.println("Bitte die SpielID eingeben");
    }

    /**
     * Methode gibt zum laufenden Spiel die Info ueber die aktuelle SpielID aus.
     *
     * @param spielid - ID, die ausgegeben wird
     */
    public void anzeigeSpielID(Long spielid) {
        System.out.println(ANSI_RED + "Für dieses Spiel lautet die SpielID: " + spielid +
                "\nDiese wird benötigt um das Spiel später fortzusetzen.\n" + ANSI_RESET);
    }

    /**
     * Methode gibt an, dass die SpielID nicht vorhanden ist
     */
    public void falscheID() {
        System.out.println(ANSI_RED + "Diese SpielID gibt es nicht!" + ANSI_RESET);
    }

    /**
     * Methode gibt an, dass das geswuenschte Spiel bereits beendet ist
     * Außerdem informiert sie uber den Gewinner
     *
     * @param sieger - Name des Gewinners
     */
    public void spielBereitsBeendet(String sieger) {
        System.out.println(ANSI_RED + "Diese ID hat schon einen Sieger! Der Sieger war " + sieger + ANSI_RESET);
    }

    /**
     * Methode gibt aus, dass das ziehen von Strafkarten nicht moeglich war
     */
    public void strafkartenNichtMoeglich() {
        System.out.println(ANSI_RED + "Das Ziehen von Strafkarten nach vergessem Mau war " +
                "\nnicht möglich, da es keine Karten mehr gibt" + ANSI_RESET);
    }
}

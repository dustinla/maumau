/**
 * @author Joerg Lehmann, Christian Fiebelkorn, Dustin Lange
 * @version 20181212
 *
 */

package de.htwberlin.maumau.ui.impl;

import de.htwberlin.maumau.karten.entity.Farbe;
import de.htwberlin.maumau.spiel.entity.Spiel;
import de.htwberlin.maumau.spiel.impl.SpielServiceImpl;
import de.htwberlin.maumau.ui.export.SpielController;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpielControllerImpl implements SpielController {

    private SpielServiceImpl spielService = new SpielServiceImpl();
    private SpielViewer view = new SpielViewer();
    private List<String> spielerliste = new ArrayList();
    private Spiel dasSpiel = new Spiel();
    private boolean spielLaeuft = true;
    private int spielrundenindex = 0;
    private boolean erweiterteRegeln;
    private Scanner sc = new Scanner(System.in);
    private static Logger log = Logger.getRootLogger();


    public void run(){
        log.setLevel(Level.WARN);//ALL, DEBUG, INFO, WARN, ERROR, FATAL, OFF
        log.debug("run");

        view.willkommen();
//        if(welcheSpielart()==1){ //vorbereitung nächste Abgabe
            erweiterteRegeln=erweiterteRegeln();
            do {
//                if(sollSpielerMenschSein()==true){//Vorbereitung nächste Abgabe
                    spielerliste.add(spielerHinzufuegen());
//                }else{
//                    System.out.println("KI Spieler hinzufügen, diese Funktion ist bisher nicht implementiert");
//                }
            }while(weitererSpieler()==true);
            dasSpiel=spielService.anlegenSpiel(spielerliste,erweiterteRegeln);
            while(spielLaeuft){
                if(spielrundenindex >0 ){
                    spielService.naechsterSpieler(dasSpiel);
                }
                spielService.zuZiehendeKarte(dasSpiel.getSummeZuziehendeKarten(), dasSpiel.getZiehstapelkarten(), dasSpiel.getAktiverSpieler());
                dasSpiel=spielerInfos(dasSpiel);
                dasSpiel=kartelegen(dasSpiel);
                dasSpiel=mauPruefung(dasSpiel);
                spielService.setzeMau(dasSpiel.getAktiverSpieler(),false);
                spielService.mussGemischtWerden(dasSpiel); // Wenn die Spieler betrügen, kann es zu einer Exception kommen, diese wird bei der nächsten Abgabe gefangen
                spielLaeuft=spielService.ermittleSpielende(dasSpiel.getAktiverSpieler());
                if(!spielLaeuft){
                    System.out.println("Gewonnen hat " + dasSpiel.getAktiverSpieler().getName());
                }
                spielrundenindex++;
            }
//        }else{ //vorbereitung nächste Abgabe
//            System.out.println("Danke, dass du ein Spiel fortsetzen möchtest, diese Funktion gibt es noch nicht");
//            System.out.println("Bitte komme später wieder");
//        }
    }

    /**
     * Diese Methode fragt ob ein weiterer Spieler gewuenscht ist
     *
     * @return boolean - der angibt, ob es einen weiteren Spieler geben soll
     */
    private boolean weitererSpieler(){
        log.debug("weitererSpieler");
        if(spielerliste.size()<2){
            view.weitererSpielerNoetig();
            return true;
        }else{
            return sollSpielerHinzugefuegtWerden();
        }
    }

    /**
     * Methode prueft ob es der Spieler Mau gesagt hat, oder ob er Strafkarten ziehen muss und sorgt
     * falls nicht dafuer, dass Strafkarten gezogen werden und fuer eine Info
     *
     * @param spiel - Das Spiel mit dem enthaltenen Spieler, der geprueft werden soll
     * @return - Das Spiel mit dem ggfs geaenderten Spieler
     */
    private Spiel mauPruefung(Spiel spiel) {
        log.debug("mauPruefung");
        int anzahlHandkartenVorPruefung;
        int anzahlHandkartenNachPruefung;

        anzahlHandkartenVorPruefung=spiel.getAktiverSpieler().getHandkarten().size();
        spiel = spielService.pruefeAufMau(spiel);
        anzahlHandkartenNachPruefung=spiel.getAktiverSpieler().getHandkarten().size();

        if (anzahlHandkartenNachPruefung>anzahlHandkartenVorPruefung){
            view.strafkartenVergessenesMau();
        }
        return spiel;
    }

    /**
     * Gibt alle Infos aus, die der Spieler zu Beginn seiner Runde braucht
     * Ausserdem werden dem Spieler ggfs Karten auf die Hand gegeben, falls er ziehen muss
     * Und der Zahler im Spiel, wie viele Karten gezogen werden muessen wird resettet
     *
     * @param spiel - Aus dem die Infos entnommen werden sollen
     * @return - Das angepasste Spiel
     */
    private Spiel spielerInfos(Spiel spiel) {
        log.debug("spielerInfos");
        Farbe obersteKarteAblagestapelFarbe;
        String obersteKarteAblagestapelWert;
        String spielername;
        Farbe farbeNachBube;
        int anzahlGezogenerKarten;

        obersteKarteAblagestapelFarbe = spiel.getAblagestapelkarten().get(spiel.getAblagestapelkarten().size()-1).getFarbe();
        obersteKarteAblagestapelWert = spiel.getAblagestapelkarten().get(spiel.getAblagestapelkarten().size()-1).getWert();
        farbeNachBube = spiel.getFarbe();
        spielername = spiel.getAktiverSpieler().getName();
        anzahlGezogenerKarten = spiel.getSummeZuziehendeKarten();

        view.infosfuerNaechstenSpieler(obersteKarteAblagestapelFarbe, obersteKarteAblagestapelWert,spielername, anzahlGezogenerKarten);

        if(obersteKarteAblagestapelWert.equals("Bube")){
            if(spiel.getFarbe()!=null) {
                view.spielerInfoNachBube(farbeNachBube);
            }
        }
        spiel.setSummeZuziehendeKarten(0);

        return spiel;
    }

    /**
     * Diese Methode kuemmert sich um das legen einer Karte. Dafuer werden dem Spieler erst einmal alle benoetigten
     * Informationen zu seinen Moeglichkleiten angezeigt
     * Der Spieler muss eine Entscheidung treffen und die Karte wird gespielt
     *
     * @param spiel - das zu aendernde Spiel
     * @return - das Spiel in neuer Form
     */
    private Spiel kartelegen(Spiel spiel){
        log.debug("kartelegen");
        String antwort;
        boolean erneutesFragen=false;
        int antwortAlsZahl;

        view.welcheKarteAblegen();
        for (int kartennummer = 0; kartennummer<spiel.getAktiverSpieler().getHandkarten().size();kartennummer++){
            Farbe farbe = spiel.getAktiverSpieler().getHandkarten().get(kartennummer).getFarbe();
            String wert = spiel.getAktiverSpieler().getHandkarten().get(kartennummer).getWert();
            view.ausgabeKarte(kartennummer,farbe,wert);
        }
        do{
            antwort=sc.next();
            antwort=antwort.toLowerCase();
            if(antwort.equals("mau")){
                view.maugesagt();
                spielService.setzeMau(spiel.getAktiverSpieler(), true);
                erneutesFragen=true;
            }else if(antwort.equals("ziehen")){
                spiel=spielService.ziehenKarteVomZiehstapel(spiel);
                erneutesFragen=false;
            }else{
                try{
                    antwortAlsZahl = Integer.parseInt(antwort);
                    if(antwortAlsZahl>=0){
                        if(antwortAlsZahl<spiel.getAktiverSpieler().getHandkarten().size()){
                            spielService.legeKarte(spiel.getAktiverSpieler().getHandkarten().get(antwortAlsZahl), spiel.getAktiverSpieler(), spiel);
                            erneutesFragen=!spiel.isErfolgreichgelegt();
                            if(erneutesFragen){
                                view.falscheKarte();
                            }else if(spiel.isMussFarbeWuenschen()){
                                spiel=farbeWaehlen(spiel);
                            }
                        }else{
                            erneutesFragen=true;
                            view.kartennummerUnsinnig();
                        }
                    }else{
                        erneutesFragen=true;
                        view.kartennummerUnsinnig();
                    }
                }catch (java.lang.NumberFormatException e){
                    view.kartennummerUnsinnig();
                    erneutesFragen=true;
                }
            }
        }while (erneutesFragen);

        return spiel;
    }

    /**
     * Methode ist da, damit der Spieler nach einem legen eines Buben aufgefordert wird eine Farbe zu waehlen.
     *
     * @param spiel - Das veraendert werden soll
     * @return - dasSpiel, was uebergeben wurde, nachdem es veraendert wurde
     */
    private Spiel farbeWaehlen(Spiel spiel) {
        log.debug("farbewaehlen");
        String antwort;
        Farbe farbe = null;
        boolean keineErfolgreicheWahl = true;

        do{
            view.farbeWaehlen();
            antwort=sc.next();
            switch (antwort){
                case "1":   farbe=Farbe.HERZ;
                            keineErfolgreicheWahl=false;
                            break;
                case "2":   farbe=Farbe.KREUZ;
                            keineErfolgreicheWahl=false;
                            break;
                case "3":   farbe=Farbe.KARO;
                            keineErfolgreicheWahl=false;
                            break;
                case "4":   farbe=Farbe.PIK;
                            keineErfolgreicheWahl=false;
                            break;
                default:    view.fehlerhafteEingabe();
            }
        }while(keineErfolgreicheWahl);

        spielService.farbeGewaehlt(spiel, farbe);

        return spiel;
    }

    /**
     * Diese Methode fragt ab, ob ein neues Spiel gestartet werden soll oder ein vorheriges fortgesetzt
     *
     * @return - 1 fuer neues Spiel, 2 fuer fortsetzen
     */
    private Integer welcheSpielart(){
        log.debug("welcheSpielart");
        int spielart=0;
        view.willkommen();

        while(spielart==0){
            String eingabe = sc.next();
            if(eingabe.equals("1")){
                spielart=1;
            }else if (eingabe.equals("2")){
                spielart=2;
            }else{
                view.fehlerhafteEingabeEinsZwei();
            }
        }
        return spielart;
    }

    /**
     * Diese Methode fragt ab, ob ein weiterer Spieler hinzugefuegt werden soll
     *
     * @return boolean, ob weiterer Spieler erwuenscht ist
     */
    private boolean sollSpielerHinzugefuegtWerden() {
        log.debug("sollSpielerHinzugefuegtWerden");
        view.sollSpielerHinzugefuegtWerden();
        return jaNeinAbfrage();
    }

    /**
     * Diese Methode liest die Konsoleneingabe und prueft, ob mit ja oder nein geantwortet wurde,
     * sofern dies nicht der Fall ist, wird ein Fehler ausgegeben und der Benutzer wird aufgefordert mit
     * ja oder nein zu antworten
     *
     * @return - boolean: true fuer ja, false fuer nein
     */
    private boolean jaNeinAbfrage(){
        log.debug("jaNeinAbfrage");
        boolean weitererDurchgang=true;
        boolean rueckgabe=false;

        while(weitererDurchgang){
            String antwort = sc.next();
            antwort=antwort.toLowerCase();
            if(antwort.equals("ja")){
                rueckgabe = true;
                weitererDurchgang=false;
            }else if(antwort.equals("nein")){
                rueckgabe = false;
                weitererDurchgang=false;
            }else{
                view.jaNeinAbfrageFehlermeldung();
            }
        }
        return rueckgabe;
    }

    /**
     * Diese Methode fragt ab, ob der neue Spieler ein Mensch sein soll, oder sonst ein KI
     *
     * @return - boolean, der angibt ob der neuste Spieler ein Mensch sein soll
     */
    private boolean sollSpielerMenschSein() {
        log.debug("sollSpielerMenschSein");
        view.sollSpielerMenschSein();

        return jaNeinAbfrage();
    }

    /**
     * Methode fragt den Namen des hinzuzufuegenden Spielers ab
     * und speichert diese Information als String
     *
     * @return - String mit dem Name des Spielers
     */
    private String spielerHinzufuegen(){
        log.debug("spielerHinzufuegen");
        view.spielerNamenAnfragen();
        String name = sc.next();
        return name;
    }

    /**
     * Diese Methode fragt erst ab, ob die Spieler die Regeln lesen wollen und im Anschluss
     * nach welchen Regeln gespielt werden soll.
     *
     * @return - boolean, der angibt ob die erweiterten Regeln gewuenscht sind
     */
    private boolean erweiterteRegeln(){
        log.debug("erweiterteRegeln");
        boolean antwort;
        view.sollenRegelnAngezeigtWerden();
        antwort=jaNeinAbfrage();
        if(antwort==true){
            view.anzeigenRegeln();
        }
        view.sollNachErweitertenRegelnGespieltWerden();
        antwort=jaNeinAbfrage();
        return antwort;
    }

}

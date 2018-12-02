package com.example.rauliyrjana.harjoitus7;

/**
 * Created by miksa on 2/20/18.
 */

public class Aku {

    private String kirjanNumero;
    private String kirjanNimi;
    private String hankinta;
    private String painos;

    public String getKirjanNimi() {
        return kirjanNimi;
    }
    public void setKirjanNimi(String kirjanNimi) {
        this.kirjanNimi = kirjanNimi;
    }

    public String getKirjanNumero() {
        return kirjanNumero;
    }
    public void setKirjanNumero(String kirjanNumero) {
        this.kirjanNumero = kirjanNumero;
    }

    public String getHankinta() {
        return hankinta;
    }
    public void setHankinta(String hankinta) {
        this.hankinta = hankinta;
    }

    public String getPainos() { return painos; }
    public void setPainos(String painos) {
        this.painos = painos;
    }


    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return kirjanNumero + ". " + kirjanNimi + "\n" + ". " + hankinta + ". " + painos  ;
    }

}

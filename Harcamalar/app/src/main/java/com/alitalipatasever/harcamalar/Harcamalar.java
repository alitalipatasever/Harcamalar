package com.alitalipatasever.harcamalar;

import android.content.Intent;

public class Harcamalar {

    public String email;
    public String tarih;
    public String aciklama;
    public String tutar;
    public String id;

    public Harcamalar() {
    }

    public Harcamalar(String email, String tarih, String aciklama, String tutar, String id) {
        this.email = email;
        this.tarih = tarih;
        this.aciklama = aciklama;
        this.tutar = tutar;
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public void setTutar(String tutar) {
        this.tutar = tutar;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getTarih() {
        return tarih;
    }

    public String getAciklama() {
        return aciklama;
    }

    public String getTutar() {
        return tutar;
    }

    public String getId() {
        return id;
    }
}

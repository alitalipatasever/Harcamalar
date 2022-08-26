package com.alitalipatasever.harcamalar;

import java.util.ArrayList;
import java.util.List;

public class Listeler {

    public String email;
    public String tarih;
    public String listeAdi;
    public String id;
    private ArrayList<User> users = null;

    public Listeler() {
    }

    public Listeler(String email, String tarih, String listeAdi, String id) {
        this.email = email;
        this.tarih = tarih;
        this.listeAdi = listeAdi;
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public void setlisteAdi(String listeAdi) {
        this.listeAdi = listeAdi;
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

    public String getlisteAdi() {
        return listeAdi;
    }
    
    public String getId() {
        return id;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}

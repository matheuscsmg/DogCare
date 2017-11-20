package com.estagio3.dog_care;

/**
 * Created by matheus on 18/11/2017.
 */

public class Remedio {

    private String nome;
    private String id;
    private String genre;

    public Remedio(){

    }

    public Remedio(String nome, String id, String genre){
        this.nome = nome;
        this.id = id;
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

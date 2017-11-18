package com.estagio3.dog_care;

import android.widget.RadioButton;

import java.io.Serializable;

/**
 * Created by matheus on 07/11/2017.
 */

public class Animal implements Serializable {

    private String id;
    private String nomeDog;
    private String raca;
    private String idade;
    private String peso;
    private String sexo;
    private String photo;

    public Animal(){

    }

    public Animal (String id, String nomeDog, String raca, String idade, String peso, String sexo, String photo){
        this.id = id;
        this.nomeDog = nomeDog;
        this.raca = raca;
        this.idade = idade;
        this.peso = peso;
        this.sexo = sexo;
        this.photo = photo;
    }

    public String getNomeDog() {
        return nomeDog;
    }

    public String getRaca() {
        return raca;
    }

    public String getIdade() {
        return idade;
    }

    public String getPeso() {
        return peso;
    }

    public String getSexo() {
        return sexo;
    }

    public String getPhoto() {return  photo; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public void setNomeDog(String nomeDog) {
        this.nomeDog = nomeDog;
    }

    public void setRaça(String raca) {
        this.raca = raca;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setPhoto (String photo) {this.photo = photo;}
}

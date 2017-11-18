package com.estagio3.dog_care;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Cacai on 26/09/2017.
 */

public class Usuario implements Serializable {

    String id;
    String name;
    String email;
    String senha;
    String idAnimal;

    public Usuario() {
    }

    public Usuario(String id, String name, String email, String senha, String idAnimal) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.idAnimal = idAnimal;
    }

    public void setIdAnimal(String idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdAnimal() {
        return idAnimal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

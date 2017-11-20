package com.estagio3.dog_care;

import android.widget.CheckBox;

/**
 * Created by matheus on 19/11/2017.
 */

public class Vacinas {

    String id_vacina;
    String data_vacina;
    CheckBox checkbox_vacina;

    public Vacinas(){}

    public Vacinas(String id_vacina, String data_vacina, CheckBox checkbox_vacina1) {
        this.id_vacina = id_vacina;
        this.data_vacina = data_vacina;
        this.checkbox_vacina = checkbox_vacina1;
    }


    public String getId_vacina() {
        return id_vacina;
    }

    public void setId_vacina(String id_vacina) {
        this.id_vacina = id_vacina;
    }

    public String getData_vacina() {
        return data_vacina;
    }

    public void setData_vacina(String data_vacina) {
        this.data_vacina = data_vacina;
    }

    public CheckBox getCheckbox_vacina() {
        return checkbox_vacina;
    }

    public void setCheckbox_vacina(CheckBox checkbox_vacina) {
        this.checkbox_vacina = checkbox_vacina;
    }






}



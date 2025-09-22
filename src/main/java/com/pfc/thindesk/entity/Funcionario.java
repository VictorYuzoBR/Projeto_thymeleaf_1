package com.pfc.thindesk.entity;

import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;



@Data
public class Funcionario extends Usuario {

    private String nome;
    private String telefone;
    private String setor;


}

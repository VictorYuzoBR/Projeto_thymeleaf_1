package com.pfc.thindesk.entity;


import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class Cliente extends Usuario {


    private String nome;

    private String telefone;

}

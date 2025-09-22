package com.pfc.thindesk.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
@Document(collection = "chamados")
public class Chamado{
    
    @Id
    private String id;

    private String descricao;
    private String status;
    private String tipo;
    private String tecnico;
    private String usuario;

    
}

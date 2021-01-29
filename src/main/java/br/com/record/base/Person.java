package br.com.record.base;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_person")
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;

}

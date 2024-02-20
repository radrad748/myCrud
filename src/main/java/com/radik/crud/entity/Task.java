package com.radik.crud.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@ToString
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Description must not be blank")
    @Length(max = 30, message = "Description must not be longer 30 characters")
    private String description;
    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "int")
    private Status status;
}

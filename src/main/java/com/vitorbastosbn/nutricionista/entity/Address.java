package com.vitorbastosbn.nutricionista.entity;

import com.vitorbastosbn.nutricionista.entity.enums.State;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Size(max = 100)
    @Column(name = "city", length = 100)
    private String city;

    @Size(max = 60)
    @Column(name = "complement", length = 60)
    private String complement;

    @Size(max = 60)
    @Column(name = "country", length = 60)
    private String country;

    @Size(max = 100)
    @Column(name = "neighborhood", length = 100)
    private String neighborhood;

    @Size(max = 10)
    @Column(name = "number", length = 10)
    private String number;

    @Enumerated(EnumType.STRING)
    @Size(min = 2, max = 2)
    @Column(name = "state", length = 2)
    private State state;

    @Size(max = 100)
    @Column(name = "street", length = 100)
    private String street;

    @Size(max = 10)
    @Column(name = "zip_code", length = 10)
    private String zipCode;

}

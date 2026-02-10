package com.vitorbastosbn.nutricionista.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Size(max = 150)
    @Column(name = "emergency_contact", length = 150, nullable = false)
    private String emergencyContact;

    @Size(max = 20)
    @Column(name = "emergency_phone", length = 20, nullable = false)
    private String emergencyPhone;

    @Size(max = 20)
    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Size(max = 20)
    @Column(name = "alternative_phone", length = 20)
    private String alternativePhone;

    @Column(name = "whatsapp", nullable = false)
    private boolean whatsapp;
}

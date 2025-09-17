package com.module.hrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import lombok.Data;
import lombok.Getter;

/**
 * A Authority.
 */
@Entity
@Table(name = "jhi_authority")
@Data
@Getter
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jhi_authority_id_seq")
    @SequenceGenerator(name = "jhi_authority_id_seq", sequenceName = "jhi_authority_id_seq", allocationSize = 50)
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100, unique = true, nullable = false)
    @JsonProperty("code")
    private String name;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    @JsonProperty("name")
    private String description;

    @JoinColumn(name = "parent_id")
    private Long parentId;

    @Column(name = "display_order", columnDefinition = "INT DEFAULT 0")
    private Integer displayOrder;
}

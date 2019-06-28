
package com.educacionit.forex.rest.entity;


import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "currency")
public class CurrencyEntity {


    @Id
    @Min (1)
    private Long id;

    @NotEmpty
    @NotNull
    @Size (max = 10)
    private String name;

    @NotEmpty
    @NotNull
    private String description;

    @Min (0)
    @Max (100)
    private Double value;

    private Boolean enabled;
}
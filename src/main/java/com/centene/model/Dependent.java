package com.centene.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dependent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Enrollee enrollee;

    public Optional<Dependent> replace(Dependent anotherDependent) {
        return Optional.ofNullable(anotherDependent)
            .map(dependent -> {
                this.name = dependent.name;
                this.dateOfBirth = dependent.dateOfBirth;

                this.enrollee = dependent.enrollee;

                return this;
            });
    }
}

package com.centene.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
public class Enrollee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private Boolean activationStatus;

    @Pattern(regexp = "(?:\\d{3}-){2}\\d{4}")
    private String phoneNumber;

    @OneToMany(
        mappedBy = "enrollee",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Dependent> dependents = new ArrayList<>();

    public Optional<Enrollee> replace(Enrollee anotherEnrollee) {
        return Optional.ofNullable(anotherEnrollee)
            .map(ae -> {
                this.name = ae.name;
                this.dateOfBirth = ae.dateOfBirth;
                this.activationStatus = ae.activationStatus;
                this.phoneNumber = ae.phoneNumber;

                this.dependents.clear();
                this.addDependent(ae.getDependents());

                return this;
            });
    }

    public void addDependent(List<Dependent> dependents) {
        dependents.forEach(this::addDependent);
    }

    public void addDependent(Dependent dependent) {
        Optional.ofNullable(dependent)
            .ifPresent(dep -> {
                this.dependents.add(dep);
                dep.setEnrollee(this);
            });
    }

    public void removeDependent(Dependent dependent) {
        Optional.ofNullable(dependent)
            .ifPresent(dep -> {
                this.dependents.remove(dependent);
                dependent.setEnrollee(null);
            });
    }
}
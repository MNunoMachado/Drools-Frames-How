package org.sma.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class AccessoriesSealants {

    public String exteriorSealant;
    public String interiorSealant;
    public int glassThickness;
    public String glazingBead;

    public AccessoriesSealants() {
    }
}

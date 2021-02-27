package org.sma.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class CasementOrFixedWindowsSeries {
    public String name;
    public String qualityRange;
    public double maxThickness;
    public double maxSize;
    public double maxWeight;

    public CasementOrFixedWindowsSeries() {
    }

}

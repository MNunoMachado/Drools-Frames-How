package org.sma.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.sma.model.FrameCategory;

import java.util.Objects;

@Setter
@Getter
public class FrameSystem {

    private String name;
    private String qualityRange;
    private double windowWidth;
    private double windowHeight;
    private int glassThickness;
    private int windowThickness;
    private FrameCategory frameCategory;

    public FrameSystem() {
    }

    public FrameSystem(double width, double height, int thickness) {
        this.windowWidth = width;
        this.windowHeight = height;
        this.glassThickness = thickness;
    }

    public boolean hasValues() {
        return windowHeight > 0 && windowWidth > 0 && windowThickness > 0 && glassThickness > 0;
    }

    public String getPerimeter() {
        return String.valueOf(2 * windowWidth + 2 * windowHeight);
    }

    //#region    getters and setters
    public double getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(double windowWidth) {
        if (windowWidth > 0)
            this.windowWidth = windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(double windowHeight) {
        if (windowHeight > 0)
            this.windowHeight = windowHeight;
    }

    public int getGlassThickness() {
        return glassThickness;
    }

    public void setGlassThickness(int glassThickness) {
        if (glassThickness > 0)
            this.glassThickness = glassThickness;
    }

    public int getWindowThickness() {
        return windowThickness;
    }

    public void setWindowThickness(int windowThickness) {
        this.windowThickness = windowThickness;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualityRange() {
        return qualityRange;
    }

    public void setQualityRange(String qualityRange) {
        this.qualityRange = qualityRange;
    }

    public FrameCategory getFrameCategory() {
        return frameCategory;
    }

    public void setFrameCategory(FrameCategory frameCategory) {
        this.frameCategory = frameCategory;
    }
    //#endregion


    //#         region  equals hashCode and toString

    @Override
    public String toString() {
        return "Window{" +
                "windowWidth=" + windowWidth +
                ", windowHeight=" + windowHeight +
                ", glassThickness=" + glassThickness +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrameSystem frameSystem = (FrameSystem) o;
        return Double.compare(frameSystem.windowWidth, windowWidth) == 0 &&
                Double.compare(frameSystem.windowHeight, windowHeight) == 0 &&
                frameSystem.glassThickness == glassThickness;
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, glassThickness);
    }
//endregion
}

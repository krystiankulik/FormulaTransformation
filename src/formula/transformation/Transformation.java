package formula.transformation;

import formula.transformation.operator.Equalizer;

import java.util.ArrayList;
import java.util.List;

public class Transformation {
    private Equalizer equalizer;
    private List<String> transformations = new ArrayList<>();

    public Transformation() {
    }

    public Transformation(Equalizer equalizer) {
        this.equalizer = equalizer;
    }

    public Transformation(Equalizer equalizer, List<String> transformations) {
        this.equalizer = equalizer;
        this.transformations = transformations;
    }

    public Equalizer getEqualizer() {
        return equalizer;
    }

    public void setEqualizer(Equalizer equalizer) {
        this.equalizer = equalizer;
    }

    public List<String> getTransformations() {
        return transformations;
    }

    public void setTransformations(List<String> transformations) {
        this.transformations = transformations;
    }
}

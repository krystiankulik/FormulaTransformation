package formula.transformation.operator;

import formula.transformation.Component;
import formula.transformation.Formulable;

import java.util.List;

public class Equalizer implements Formulable {
    private Component left;
    private Component right;

    public Equalizer(Component arg1, Component arg2) {
        this.left = arg1;
        this.right = arg2;
    }


    @Override
    public String getFormula() {
        return left.getFormula() + "=" + right.getFormula();
    }

    public boolean containsOperand(String operand) {
        return left.containsOperand(operand) || right.containsOperand(operand);
    }

    public List<String> getArguments() {
        List<String> combined = left.getArguments();
        combined.addAll(right.getArguments());
        return combined;
    }


    public Component getLeft() {
        return left;
    }

    public void setLeft(Component left) {
        this.left = left;
    }

    public Component getRight() {
        return right;
    }

    public void setRight(Component right) {
        this.right = right;
    }

    public Equalizer copy() {
        return new Equalizer(this.left.copy(), this.right.copy());
    }
}

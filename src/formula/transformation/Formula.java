package formula.transformation;

import formula.transformation.operand.FormulaArgument;

import java.util.List;
import java.util.function.BinaryOperator;

public abstract class Formula implements Component {

    protected Component left;
    protected Component right;
    protected BinaryOperator<Double> numberOperator;


    public Formula(Component left, Component right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double getResult() {
        return numberOperator.apply(left.getResult(), right.getResult());
    }

    @Override
    public boolean containsOperand(String operand) {
        return left.containsOperand(operand) || right.containsOperand(operand);
    }

    @Override
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

    @Override
    public abstract Component copy();

    @Override
    public void replaceOperandWithComponent(Component component, String name) {

        if (left instanceof FormulaArgument && left.getFormula().equals(name)) {
            left = component;
        } else {
            left.replaceOperandWithComponent(component, name);
        }

        if (right instanceof FormulaArgument && right.getFormula().equals(name)) {
            this.right = component;
        } else {
            right.replaceOperandWithComponent(component, name);
        }


    }
}

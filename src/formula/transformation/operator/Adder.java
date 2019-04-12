package formula.transformation.operator;

import formula.transformation.Component;
import formula.transformation.Formula;

public class Adder extends Formula {

    public Adder(Component left, Component right) {
        super(left, right);
        super.numberOperator = (a, b) -> a + b;
    }

    @Override
    public String getFormula() {
        return left.getFormula() + "+" + right.getFormula();
    }

    @Override
    public Component copy() {
        return new Adder(this.left.copy(), this.right.copy());
    }

}

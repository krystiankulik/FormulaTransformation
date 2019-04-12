package formula.transformation.operator;

import formula.transformation.Component;
import formula.transformation.Formula;

public class Divider extends Formula {

    public Divider(Component left, Component right) {
        super(left, right);
        super.numberOperator = (a, b) -> a / b;
    }

    @Override
    public String getFormula() {
        String f1 = left.getFormula();
        String f2 = right.getFormula();

        if(left instanceof Adder || left instanceof Substractor) {
            f1 = "(" + f1 + ")";
        }

        if(right instanceof Adder || right instanceof Substractor ||
                right instanceof Multiplier || right instanceof Divider) {
            f2 = "(" + f2 + ")";
        }

        return  f1 + "/" + f2;
    }

    @Override
    public Component copy() {
        return new Divider(this.left.copy(), this.right.copy());
    }
}

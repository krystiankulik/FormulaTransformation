package formula.transformation.operator;

import formula.transformation.Component;
import formula.transformation.Formula;

public class Multiplier extends Formula {

    public Multiplier(Component arg1, Component arg2) {
        super(arg1, arg2);
        super.numberOperator = (a, b) -> a * b;
    }

    @Override
    public String getFormula() {
        String f1 = left.getFormula();
        String f2 = right.getFormula();

        if(left instanceof Adder || left instanceof Substractor) {
            f1 = "(" + f1 + ")";
        }

        if(right instanceof Adder || right instanceof Substractor) {
            f2 = "(" + f2 + ")";
        }

        return  f1 + "*" + f2;
    }

    @Override
    public Component copy() {
        return new Multiplier(this.left.copy(), this.right.copy());
    }
}

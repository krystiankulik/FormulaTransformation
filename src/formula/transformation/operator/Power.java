package formula.transformation.operator;

import formula.transformation.Component;
import formula.transformation.Formula;
import formula.transformation.operand.FormulaArgument;
import formula.transformation.operand.FormulaParameter;

public class Power extends Formula {


    public Power(Component left, Component right) {
        super(left, right);
        super.numberOperator = Math::pow;
    }

    @Override
    public String getFormula() {
        String leftFormula = left.getFormula();
        String rightFormula = right.getFormula();

        if (!(left instanceof FormulaParameter || left instanceof FormulaArgument || left instanceof RootCalculator))
            leftFormula = "(" + leftFormula + ")";

        if (!(right instanceof FormulaParameter || right instanceof FormulaArgument || right instanceof RootCalculator))
            rightFormula = "(" + rightFormula + ")";

        return leftFormula + "^" + rightFormula + "";

    }

    @Override
    public Component copy() {
        return new Power(this.left.copy(), this.right.copy());
    }
}

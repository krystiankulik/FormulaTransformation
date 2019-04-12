package formula.transformation.operator;

import formula.transformation.Component;
import formula.transformation.Formula;
import formula.transformation.operand.FormulaArgument;
import formula.transformation.operand.FormulaParameter;

public class RootCalculator extends Formula {

    public RootCalculator(Component left, Component right) {
        super(left, right);
        super.numberOperator = (number, n) -> Math.pow(number, 1 / n);
    }

    @Override
    public String getFormula() {
        String leftFormula = left.getFormula();
        if (!(left instanceof FormulaParameter || left instanceof FormulaArgument || left instanceof RootCalculator))
            leftFormula = "(" + leftFormula + ")";

        return leftFormula + "^(" + new Divider(new FormulaParameter(1), right).getFormula() + ")";

    }

    @Override
    public Component copy() {
        return new RootCalculator(this.left.copy(), this.right.copy());
    }
}

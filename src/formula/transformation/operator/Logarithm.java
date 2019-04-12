package formula.transformation.operator;

import formula.transformation.Component;
import formula.transformation.Formula;
import formula.transformation.operand.FormulaArgument;
import formula.transformation.operand.FormulaParameter;

public class Logarithm extends Formula {

    public Logarithm(Component left, Component right) {
        super(left, right);
    }

    @Override
    public String getFormula() {
        String rightText = right.getFormula();
        if (!(right instanceof FormulaParameter || right instanceof FormulaArgument))
            rightText = "[" + rightText + "]";
        return "log_" + rightText + "(" + left.getFormula() + ")";
    }

    @Override
    public Component copy() {
        return new Logarithm(this.left.copy(), this.right.copy());
    }
}

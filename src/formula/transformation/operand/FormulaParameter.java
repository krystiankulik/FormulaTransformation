package formula.transformation.operand;

import formula.transformation.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FormulaParameter implements Component {

    private double value;

    public FormulaParameter(double value) {
        this.value = value;
    }

    @Override
    public double getResult() {
        return value;
    }

    @Override
    public String getFormula() {
        DecimalFormat format = new DecimalFormat("0.###");
        return String.valueOf(format.format(value));
    }

    @Override
    public List<String> getArguments() {
        return new ArrayList<>();
    }

    @Override
    public boolean containsOperand(String operand) {
        return false;
    }

    @Override
    public Component copy() {
        return  new FormulaParameter(value);
    }

    @Override
    public void replaceOperandWithComponent(Component component, String name) {

    }
}

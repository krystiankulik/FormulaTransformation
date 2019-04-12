package formula.transformation.operand;

import formula.transformation.Component;

import java.util.ArrayList;
import java.util.List;

public class FormulaArgument implements Component {


    private Double value;
    private String key;

    public FormulaArgument(Double value, String key) {
        this.value = value;
        this.key = key;
    }

    public FormulaArgument(String key) {
        this.key = key;
    }

    @Override
    public double getResult() {
        return value;
    }

    @Override
    public String getFormula() {
        return key;
    }

    @Override
    public boolean containsOperand(String operand) {
        return key.equals(operand);

    }

    @Override
    public List<String> getArguments() {
        List<String> args =  new ArrayList<>();
        args.add(key);
        return args;
    }

    @Override
    public Component copy() {
        return new FormulaArgument(value, key);
    }

    @Override
    public void replaceOperandWithComponent(Component component, String name) {

    }
}

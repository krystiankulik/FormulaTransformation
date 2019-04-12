package formula.transformation;

import java.util.List;

public interface Component extends Calculable, Formulable{
    boolean containsOperand(String operand);
    List<String> getArguments();
    Component copy();
    void replaceOperandWithComponent(Component component, String name);
}

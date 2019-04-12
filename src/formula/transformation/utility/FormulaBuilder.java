package formula.transformation.utility;

import formula.transformation.Component;
import formula.transformation.operand.FormulaArgument;
import formula.transformation.operand.FormulaParameter;
import formula.transformation.operator.Adder;
import formula.transformation.operator.Divider;
import formula.transformation.operator.Equalizer;
import formula.transformation.operator.Multiplier;
import formula.transformation.operator.Power;
import formula.transformation.operator.RootCalculator;
import formula.transformation.operator.Substractor;

public class FormulaBuilder {

    public static Component add(Component c1, Component c2) {
        return new Adder(c1, c2);
    }

    public static Component div(Component c1, Component c2) {
        return new Divider(c1, c2);
    }

    public  static Component mul(Component c1, Component c2) {
        return  new Multiplier(c1, c2);
    }

    public static Component sub(Component c1, Component c2) {
        return new Substractor(c1, c2);
    }

    public static Component arg(double value, String key ) {
        return new FormulaArgument(value, key);
    }

    public static Component pow(Component c1,Component c2 ) {
        return  new Power(c1, c2);
    }

    public static Component root(Component c1, Component c2) {
        return  new RootCalculator(c1, c2);
    }

    public static Component arg(String key) {
        return new FormulaArgument(key);
    }

    public static Component constant(double value) {
        return new FormulaParameter(value);
    }

    public static Equalizer eq(Component c1, Component c2) {
        return  new Equalizer(c1,c2);
    }
}

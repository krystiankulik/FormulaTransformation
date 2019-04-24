package formula.transformation;

import formula.transformation.operator.Equalizer;
import formula.transformation.utility.EquationTransformer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static formula.transformation.utility.FormulaBuilder.add;
import static formula.transformation.utility.FormulaBuilder.arg;
import static formula.transformation.utility.FormulaBuilder.constant;
import static formula.transformation.utility.FormulaBuilder.div;
import static formula.transformation.utility.FormulaBuilder.eq;
import static formula.transformation.utility.FormulaBuilder.mul;
import static formula.transformation.utility.FormulaBuilder.pow;


public class FormulaTransformer {

    private Map<Equation, Equalizer> formulas = new HashMap<>();

    {
        Equalizer massUsingDensity = eq(arg("m"), mul(arg("V"), arg("d")));
        formulas.put(Equation.MASS_USING_DENSITY, massUsingDensity);


        Equalizer potentialEnergy = eq(arg("Ep"), mul(arg("m"), mul(arg("g"), arg("h"))));
        formulas.put(Equation.POTENTIAL_ENERGY, potentialEnergy);

        Equalizer kineticEnergy = eq(arg("Ek"), div(mul(arg("m"), pow(arg("v"), constant(2))), constant(2)));
        formulas.put(Equation.KINETIC_ENERGY, kineticEnergy);

        Equalizer gravityForce = eq(arg("Fg"), mul(arg("m"), arg("g")));
        formulas.put(Equation.GRAVITY_FORCE, gravityForce);

        Equalizer uniformMotion = eq(arg("v"), div(arg("s"), arg("t")));
        formulas.put(Equation.UNIFORM_MOTION, uniformMotion);

        Equalizer work = eq(arg("W"), mul(arg("F"), arg("s")));
        formulas.put(Equation.WORK, work);

        Equalizer power = eq(arg("P"), div(arg("W"), arg("t")));
        formulas.put(Equation.POWER, power);

        Equalizer perimeter = eq(arg("O"), mul(constant(4),add(add(arg("a"), arg("b")), arg("h"))));
        formulas.put(Equation.PERIMETER, perimeter);

        Equalizer volume = eq(arg("V"), mul(arg("a"), mul(arg("b"), arg("h"))));
        formulas.put(Equation.VOLUME, volume);
    }

    public Map<Equation, Equalizer> getFormulas() {
        return formulas;
    }

    public void transform(Equation equation, String operandName) {
        Equalizer equalizer = formulas.get(equation);
        transform(equalizer, operandName);
    }

    public Transformation transform(Equalizer equalizer, String operandName) {
        equalizer = equalizer.copy();
        List<String> transformations = new ArrayList<>();

        if (equalizer == null) {
            throw new IllegalArgumentException();
        }


        transformations.add("-> " + equalizer.getFormula());
        if (EquationTransformer.initialize(equalizer, operandName)) {
            transformations.add(equalizer.getFormula());
        }

        String oldFormula = "";
        while (!oldFormula.equals(equalizer.getFormula())) {
            oldFormula = equalizer.getFormula();
            if (EquationTransformer.simplifyDividing(equalizer)) {
                transformations.add(equalizer.getFormula());

            } else {
                EquationTransformer.performStep(equalizer, operandName);
                if (!equalizer.getFormula().equals(oldFormula)) {
                    transformations.add(equalizer.getFormula());
                }
            }
        }

        return new Transformation(equalizer, transformations);

    }


    public List<String> getFailingArguments(List<String> needed, List<String> given) {
        List<String> failingArgs = new ArrayList<>();
        for (String arg : needed) {
            if (!given.contains(arg)) {
                failingArgs.add(arg);
            }
        }
        return failingArgs;
    }

    public boolean areArgumentsSufficient(List<String> needed, List<String> given) {

        for (String arg : needed) {
            if (!given.contains(arg)) {
                return false;
            }
        }
        return true;
    }

    public Map<Equation, Equalizer> copyFormulas(Map<Equation, Equalizer> formulas) {
        Map<Equation, Equalizer> map = new HashMap<>();
        for (Map.Entry<Equation, Equalizer> entry : formulas.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public String resolve(String searched, List<String> given) {

        if(given.contains(searched)) {
            return "Szukana zmienna " + searched + " jest podana jako argument";
        } else {
            Transformation t = combine(searched, given);
            if(t != null) {
                return  t.getTransformations().stream().reduce("", (a, b) -> a + "\n" + b);
            } else {
                return "Podano zbyt małą liczbę argumentów!";
            }
        }
    }

    private Transformation combine(String searched, List<String> given) {
        return combineHelp(formulas, searched, given);
    }

    private Transformation combineHelp(Map<Equation, Equalizer> formulasLeft, String searched, List<String> given) {
        for (Map.Entry<Equation, Equalizer> entry : formulasLeft.entrySet()) {
            if (entry.getValue().containsOperand(searched)) {
                Transformation t = transform(entry.getValue(), searched);
                List<String> allArgs = entry.getValue().getArguments();
                allArgs.remove(searched);
                if (areArgumentsSufficient(allArgs, given)) {
                    return transform(entry.getValue(), searched);
                } else if (!formulasLeft.isEmpty()) {
                    List<String> failingArgs = getFailingArguments(allArgs, given);

                    boolean allFound = true;
                    for (String arg : failingArgs) {
                        Map<Equation, Equalizer> newFormulas = copyFormulas(formulasLeft);
                        newFormulas.remove(entry.getKey());
                        Transformation toInsert = combineHelp(newFormulas, arg, given);
                        if (toInsert != null) {
                            t.getEqualizer().getRight().replaceOperandWithComponent(toInsert.getEqualizer().getRight(), arg);
                            t.getTransformations().addAll(toInsert.getTransformations());
                            t.getTransformations().add(t.getEqualizer().getFormula());
                        } else {
                            allFound = false;
                            break;
                        }
                    }
                    if (allFound) {
                        return t;
                    }
                }
            }
        }
        return null;
    }
}

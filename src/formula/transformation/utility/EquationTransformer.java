package formula.transformation.utility;

import formula.transformation.Component;
import formula.transformation.Formula;
import formula.transformation.operator.Adder;
import formula.transformation.operator.Divider;
import formula.transformation.operator.Equalizer;
import formula.transformation.operator.Logarithm;
import formula.transformation.operator.Multiplier;
import formula.transformation.operator.Power;
import formula.transformation.operator.RootCalculator;
import formula.transformation.operator.Substractor;

public class EquationTransformer {

    public static boolean initialize(Equalizer equalizer, String searchedOperand) {
        if (equalizer.getRight().containsOperand(searchedOperand)) {
            swapArguments(equalizer);
            return true;
        }
        return false;
    }

    public static void swapArguments(Equalizer equalizer) {
        Component temp = equalizer.getRight();
        equalizer.setRight(equalizer.getLeft());
        equalizer.setLeft(temp);
    }


    public static void performStep(Equalizer equalizer, String searchedOperand) {
        Formula leftSide;
        if (equalizer.getLeft() instanceof Formula) {
            leftSide = (Formula) equalizer.getLeft();
            if (leftSide instanceof Divider) {
                handleDivide(equalizer, searchedOperand);
            } else if (leftSide instanceof Multiplier) {
                handleMultiply(equalizer, searchedOperand);
            } else if (leftSide instanceof Adder) {
                handleAdd(equalizer, searchedOperand);
            } else if (leftSide instanceof Substractor) {
                handleSubstract(equalizer, searchedOperand);
            } else if (leftSide instanceof Power) {
                handlePower(equalizer, searchedOperand);
            } else if (leftSide instanceof RootCalculator) {
                handleRoot(equalizer, searchedOperand);
            }

        }
    }

    private static void handleAdd(Equalizer equalizer, String searchedOperand) {
        Formula leftSide = (Formula) equalizer.getLeft();
        if (leftSide.getLeft().containsOperand(searchedOperand)) {
            Substractor substractor = new Substractor(equalizer.getRight(), leftSide.getRight());
            equalizer.setLeft(leftSide.getLeft());
            equalizer.setRight(substractor);

        } else if (leftSide.getRight().containsOperand(searchedOperand)) {
            Substractor substractor = new Substractor(equalizer.getRight(), leftSide.getLeft());
            equalizer.setLeft(leftSide.getRight());
            equalizer.setRight(substractor);

        }
    }

    private  static void handleSubstract(Equalizer equalizer, String searchedOperand) {
        Formula leftSide = (Formula) equalizer.getLeft();
        if (leftSide.getLeft().containsOperand(searchedOperand)) {
            Adder adder = new Adder(equalizer.getRight(), leftSide.getRight());
            equalizer.setLeft(leftSide.getLeft());
            equalizer.setRight(adder);

        } else if (leftSide.getRight().containsOperand(searchedOperand)) {
            Substractor substractor = new Substractor(leftSide.getRight(), equalizer.getRight());
            equalizer.setLeft(leftSide.getRight());
            equalizer.setRight(substractor);

        }
    }

    private  static void handleMultiply(Equalizer equalizer, String searchedOperand) {
        Formula leftSide = (Formula) equalizer.getLeft();
        if (leftSide.getLeft().containsOperand(searchedOperand)) {
            Divider divider = new Divider(equalizer.getRight(), leftSide.getRight());
            equalizer.setLeft(leftSide.getLeft());
            equalizer.setRight(divider);

        } else if (leftSide.getRight().containsOperand(searchedOperand)) {
            Divider divider = new Divider(equalizer.getRight(), leftSide.getLeft());
            equalizer.setLeft(leftSide.getRight());
            equalizer.setRight(divider);

        }
    }

    private static void handleDivide(Equalizer equalizer, String searchedOperand) {
        Formula leftSide = (Formula) equalizer.getLeft();
        if (leftSide.getLeft().containsOperand(searchedOperand)) {
            Multiplier multiplier = new Multiplier(equalizer.getRight(), leftSide.getRight());
            equalizer.setLeft(leftSide.getLeft());
            equalizer.setRight(multiplier);

        } else if (leftSide.getRight().containsOperand(searchedOperand)) {
            Divider divider = new Divider(leftSide.getLeft(), equalizer.getRight());
            equalizer.setLeft(leftSide.getRight());
            equalizer.setRight(divider);

        }
    }

    private  static void handlePower(Equalizer equalizer, String searchedOperand) {
        Formula leftSide = (Formula) equalizer.getLeft();
        if (leftSide.getLeft().containsOperand(searchedOperand)) {
            RootCalculator root = new RootCalculator(equalizer.getRight(), leftSide.getRight());
            equalizer.setLeft(leftSide.getLeft());
            equalizer.setRight(root);
        } else if (leftSide.getRight().containsOperand(searchedOperand)) {
            Logarithm logarithm = new Logarithm(equalizer.getRight(), leftSide.getLeft());
            equalizer.setLeft(leftSide.getRight());
            equalizer.setRight(logarithm);
            return;
        }
    }

    private  static void handleRoot(Equalizer equalizer, String searchedOperand) {
        Formula leftSide = (Formula) equalizer.getLeft();
        if (leftSide.getLeft().containsOperand(searchedOperand)) {
            Power power = new Power(equalizer.getRight(), leftSide.getRight());
            equalizer.setLeft(leftSide.getLeft());
            equalizer.setRight(power);
        } else if (leftSide.getRight().containsOperand(searchedOperand)) {
            Logarithm logarithm = new Logarithm(leftSide.getLeft(), equalizer.getRight());
            equalizer.setLeft(leftSide.getRight());
            equalizer.setRight(logarithm);
        }
    }

    public static boolean simplifyDividing(Equalizer equalizer) {
        if (equalizer.getRight() instanceof Divider) {
            Formula formula = (Formula) equalizer.getRight();
            if (formula.getLeft() instanceof Divider && formula.getRight() instanceof Divider) {
                Formula leftDivider = (Formula) formula.getLeft();
                Formula rightDivider = (Formula) formula.getRight();
                Multiplier leftMultiplier = new Multiplier(leftDivider.getLeft(), rightDivider.getRight());
                Multiplier rightMultiplier = new Multiplier(rightDivider.getLeft(), leftDivider.getRight());
                formula.setLeft(leftMultiplier);
                formula.setRight(rightMultiplier);
                return true;
            } else if (formula.getLeft() instanceof Divider) {
                Formula leftDivider = (Formula) formula.getLeft();
                Multiplier multiplier = new Multiplier(leftDivider.getRight(), formula.getRight());
                formula.setRight(multiplier);
                formula.setLeft(leftDivider.getLeft());
                return true;
            } else if (formula.getRight() instanceof Divider) {
                Formula rightDivider = (Formula) formula.getRight();
                Multiplier multiplier = new Multiplier(formula.getLeft(), rightDivider.getRight());
                formula.setLeft(multiplier);
                formula.setRight(rightDivider.getLeft());
                return true;
            }
        }
        return false;
    }


}

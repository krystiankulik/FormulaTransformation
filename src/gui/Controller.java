package gui;

import formula.transformation.FormulaTransformer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private HBox checkBoxContainer;

    @FXML
    private TextArea transformationArea;

    private FormulaTransformer transformer = new FormulaTransformer();

    @FXML
    public void initialize() {

        Set<String> set = new HashSet<>();
        transformer.getFormulas().forEach((k, v) -> set.addAll(v.getArguments()));
        choiceBox.setItems(FXCollections.observableArrayList(set));
        choiceBox.setValue(set.stream().findFirst().orElse(""));
        checkBoxContainer.getChildren().addAll(set.stream().map(CheckBox::new)
                        .peek(box -> box.setPadding(new Insets(5, 8, 5, 5)))
                        .collect(Collectors.toList()));
    }


    @FXML
    public void transform() {
        List<String> given = checkBoxContainer
                .getChildren()
                .stream()
                .filter(CheckBox.class::isInstance)
                .map(CheckBox.class::cast)
                .filter(CheckBox::isSelected)
                .map(CheckBox::getText)
                .collect(Collectors.toList());
        String searched = (String)choiceBox.getValue();

        transformationArea.setText(transformer.resolve(searched, given));

    }
}

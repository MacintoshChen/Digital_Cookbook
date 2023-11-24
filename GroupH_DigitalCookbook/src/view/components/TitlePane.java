package view.components;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Title of ingredients
 */
public class TitlePane extends HBox {
    public TextField name;
    public TextField amount;
    public TextField unit;
    public Pane parent;

    /**
     * Constructor
     * @param _parent The parent pane
     */
    public TitlePane(Pane _parent) {
        parent = _parent;
        setPrefHeight(25);
        setPrefWidth(498);
        initializeUI();
    }

    private void initializeUI() {
        name = new TextField("Name");
        name.setPrefHeight(25.0);
        name.setPrefWidth(166.0);
        name.setEditable(false);

        amount = new TextField("Amount");
        amount.setPrefHeight(25.0);
        amount.setPrefWidth(166.0);
        amount.setEditable(false);

        unit = new TextField("Unit");
        unit.setPrefHeight(25.0);
        unit.setPrefWidth(166.0);
        unit.setEditable(false);

        getChildren().addAll(name, amount, unit);
    }
}

package view.components;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Component pane to add ingredients
 */
public class IngredientAddPane extends HBox {
    public TextField textField1;
    public TextField textField2;
    public TextField textField3;
    public Button deleteButton;
    public Pane parent;

    /**
     * Constructor
     * @param _parent Parent pane of this, Used to delete from parent pane
     */
    public IngredientAddPane(Pane _parent) {
        parent = _parent;
        setPrefHeight(25);
        setPrefWidth(498);
        initializeUI();
    }

    /**
     * Initialize UI
     */
    private void initializeUI() {
        textField1 = new TextField();
        textField1.setPrefHeight(25.0);
        textField1.setPrefWidth(166.0);

        textField2 = new TextField();
        textField2.setPrefHeight(25.0);
        textField2.setPrefWidth(166.0);

        textField3 = new TextField();
        textField3.setPrefHeight(25.0);
        textField3.setPrefWidth(166.0);

        deleteButton = new Button();
        deleteButton.setMnemonicParsing(false);
        deleteButton.setPrefHeight(25.0);
        deleteButton.setText("X");
        deleteButton.addEventHandler(ActionEvent.ACTION, actionEvent -> parent.getChildren().remove(this));

        getChildren().addAll(textField1, textField2, textField3, deleteButton);
    }


    public void setName(String name) {
        this.textField1.setText(name);
    }
    public void setAmount(String amount) {
        this.textField2.setText(amount);
    }
    public void setUnit(String unit) {
        this.textField3.setText(unit);
    }


}

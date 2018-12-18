package by.bsuir.client.mediator;

import javafx.scene.control.RadioButton;

import java.util.ArrayList;
import java.util.List;

public class ButtonMediator {
    private List<RadioButton> buttons;

    public ButtonMediator() {
        this.buttons = new ArrayList<>();
    }

    public void addButtons(RadioButton... buttons) {
        for (RadioButton radio : buttons) {
            radio.setOnAction(event -> {
                activate(radio);
            });
            this.buttons.add(radio);
        }
    }

    private void activate(RadioButton radio) {
        setAllUnactive();
        radio.setSelected(true);
    }

    public void setAllUnactive() {
        for (RadioButton radioButton : buttons) {
            radioButton.setSelected(false);
        }
    }

    public int getSelectedNumber() {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).isScaleShape()) {
                return i + 1;
            }
        }
        return 0;
    }
}

package io.github.yehwankim23.ioscalculator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class Controller {
    private final Model MODEL = new Model();
    private final String ZERO = "0";

    private String answer = ZERO;
    private char operation = ' ';
    private String input = ZERO;

    private boolean isOperationInitialized = false;
    private boolean isInputDecimal = false;
    private boolean isAnswerDisplayed = false;
    private boolean isOperationFocused = false;
    private boolean isNumberClicked = false;

    @FXML
    private Label label;
    @FXML
    private Button button;

    private void resetInput() {
        input = ZERO;
        isInputDecimal = false;
    }

    public void clickOnFunction(MouseEvent mouseEvent) {
        switch (((Button) mouseEvent.getSource()).getText().charAt(0)) {
            case 'A':
            case 'C':
                answer = ZERO;
                operation = ' ';
                isOperationInitialized = false;
                resetInput();
                isOperationFocused = false;
                isNumberClicked = false;
                label.setText(input);
                isAnswerDisplayed = false;
                button.setText("AC");
                break;
            case '±':
                if (isAnswerDisplayed) {
                    answer = MODEL.calculate(answer, '×', "-1");
                    label.setText(answer);
                } else {
                    input = MODEL.calculate(input, '×', "-1");
                    label.setText(input);
                }

                break;
            case '%':
                if (isAnswerDisplayed) {
                    answer = MODEL.calculate(answer, '÷', "100");
                    label.setText(answer);
                } else {
                    if (Double.parseDouble(input) == 0) {
                        input = ZERO;
                    } else {
                        input = MODEL.calculate(input, '÷', "100");
                    }

                    label.setText(input);
                }

                break;
            default:
                throw new UnsupportedOperationException("Controller.clickOnFunction().switch().default");
        }
    }

    public void clickOnOperation(MouseEvent mouseEvent) {
        char thisOperation = ((Button) mouseEvent.getSource()).getText().charAt(0);

        if (thisOperation == '=') {
            if (isOperationInitialized) {
                answer = MODEL.calculate(answer, operation, input);
                label.setText(answer);
            }

            isOperationFocused = false;
            isNumberClicked = false;
            isAnswerDisplayed = true;
        } else {
            if (!isOperationFocused) {
                if (isNumberClicked) {
                    answer = input;
                }

                resetInput();
            } else {
                if (isNumberClicked) {
                    answer = MODEL.calculate(answer, operation, input);
                    resetInput();
                    label.setText(answer);
                    isAnswerDisplayed = true;
                }
            }

            operation = thisOperation;
            isOperationInitialized = true;
            isOperationFocused = true;
            isNumberClicked = false;
        }
    }

    public void clickOnNumber(MouseEvent mouseEvent) {
        if (isAnswerDisplayed && !isOperationFocused) {
            answer = ZERO;
            resetInput();
        }

        char thisInput = ((Button) mouseEvent.getSource()).getText().charAt(0);

        if (input.equals(ZERO) && thisInput == '0') {
            return;
        }

        if (thisInput == '.') {
            if (isInputDecimal) {
                return;
            }

            isInputDecimal = true;
        }

        isNumberClicked = true;
        button.setText("C");

        if (input.equals(ZERO) && thisInput != '.') {
            input = Character.toString(thisInput);
        } else if (input.length() < 6) {
            input += thisInput;
        }

        label.setText(input);
        isAnswerDisplayed = false;
    }
}

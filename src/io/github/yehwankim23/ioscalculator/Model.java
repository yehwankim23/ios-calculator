package io.github.yehwankim23.ioscalculator;

public class Model {
    public String calculate(String answer, char operation, String input) {
        String ERROR = "Error";

        if (answer.equals(ERROR) || (operation == '÷' && input.equals("0"))) {
            return ERROR;
        }

        double answerAsDouble = Double.parseDouble(answer);
        double inputAsDouble = Double.parseDouble(input);

        switch (operation) {
            case '+':
                answerAsDouble += inputAsDouble;
                break;
            case '–':
                answerAsDouble -= inputAsDouble;
                break;
            case '×':
                answerAsDouble *= inputAsDouble;
                break;
            case '÷':
                answerAsDouble /= inputAsDouble;
                break;
            default:
                throw new UnsupportedOperationException("Model.calculate().switch().default");
        }

        String result = answerAsDouble % 1 == 0 ? Integer.toString((int) answerAsDouble) : Double.toString(answerAsDouble);

        if (result.length() > 6) {
            return ERROR;
        }

        return result;
    }
}

package kz.yessenov.finance.model;

public class Question {
    private String text;
    private String[] options;
    private int correctOptionIndex;

    public Question(String text, String[] options, int correctOptionIndex) {
        this.text = text;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getText() { return text; }
    public String[] getOptions() { return options; }
    public boolean isCorrect(int answer) { return answer == correctOptionIndex; }
}
package com.jhobahego.questionsgpt.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Question {

    String category;

    String question;

    String[] options;

    String answer;

    String explanation;
}

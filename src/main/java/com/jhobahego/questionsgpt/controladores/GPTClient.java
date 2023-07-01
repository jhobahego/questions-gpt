package com.jhobahego.questionsgpt.controladores;

import com.jhobahego.questionsgpt.servicios.ChatgtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/api")
@RestController
public class GPTClient {

    ChatgtpService chatgtpService;

    @Autowired
    public GPTClient(ChatgtpService chatgtpService) {
        this.chatgtpService = chatgtpService;
    }

    @GetMapping("/question/{categoria}")
    public ResponseEntity<?> obtenerPregunta(@PathVariable("categoria") String categoria) {
        String respuesta = "No se ha creado ninguna pregunta";
        try {
            String promptMessage = "Estoy haciendo una trivia necesito que me des una pregunta de la categoria "+categoria+" siguiendo el siguiente formato en json, tu respuesta debe ser solo el archivo json y el campo answer debe ser identificado con el indice de la opcion dentro del arreglado options empezando por el indice 0 hasta el indice 3.\n" +
                    "\n" +
                    "{\n" +
                    "  \"category\": \"Música\",\n" +
                    "  \"question\": \"¿Cuál es el álbum más vendido de todos los tiempos?\",\n" +
                    "  \"options\": [\n" +
                    "    \"Thriller\",\n" +
                    "    \"Back in Black\",\n" +
                    "    \"The Dark Side of the Moon\"\n" +
                    "  ],\n" +
                    "  \"answer\": 0,\n" +
                    "  \"explanation\": \"El álbum más vendido de todos los tiempos es 'Thriller' de Michael Jackson, que ha vendido más de 66 millones de copias en todo el mundo desde su lanzamiento en 1982.\"\n" +
                    "}\n" +
                    "tu respuesta debe incluir todos los campos del formato del json.";

            String model = "text-davinci-003";
            String promp = "{\"model\": \"" + model + "\", \"messages\": [{ \role\": \"user\", \"content\": \"" + promptMessage + "\" }]}";
            String gptResponse = chatgtpService.generarPregunta(promp);
            System.out.println(gptResponse);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok(respuesta);
    }
}

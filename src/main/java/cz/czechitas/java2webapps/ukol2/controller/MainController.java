package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private final Random rndGenerator = new Random();
    private final List<String> quotes;

    public MainController() throws IOException {
        //pictures = Arrays.asList("a", "b", "c");
        quotes = readAllLines("citaty.txt");
    }

    private static List<String> readAllLines(String resource) throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try (InputStream inputStream = classLoader.getResourceAsStream(resource);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/")    //lomítko = namapuj se na cestu; lomítko je lomítko za localhost:8080 ??
    public ModelAndView displayQuote() throws IOException {
        int randomNumberForPics = rndGenerator.nextInt(3) + 1;
        ModelAndView newView = new ModelAndView("quote");
        newView.addObject("randomQuote", quotes.get(rndGenerator.nextInt(quotes.size())));
        newView.addObject("randomBackgroundPic", String.format("/images/backgroundPic-%d.jpg", randomNumberForPics));
        System.out.println("randomNumberForPics: " + randomNumberForPics);
        return newView;
    }
}
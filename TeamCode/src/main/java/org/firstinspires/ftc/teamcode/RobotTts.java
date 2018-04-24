package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Random;

import static org.firstinspires.ftc.teamcode.RobotTts.Language.CHINESE;
import static org.firstinspires.ftc.teamcode.RobotTts.Language.ENGLISH;
import static org.firstinspires.ftc.teamcode.RobotTts.Language.GERMAN;
import static org.firstinspires.ftc.teamcode.RobotTts.Language.JAPANESE;
import static org.firstinspires.ftc.teamcode.RobotTts.Language.KOREAN;

public class RobotTts {

    TextToSpeech tts;
    Random generator = new Random();

    RobotTts(Context context) {
        tts = new TextToSpeech(context, null);
        lang = randomLang();
        tts.setLanguage(langToLocale(lang));
        tts.setPitch(1.5f);
        tts.setSpeechRate(1.5f);
    }

    void speak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    Language randomLang() {
        int i = generator.nextInt(5);
        switch (i) {
            case 0:
                return JAPANESE;
            case 1:
                return ENGLISH;
            case 2:
                return KOREAN;
            case 3:
                return GERMAN;
            case 4:
                return CHINESE;
            default:
                return ENGLISH;
        }
    }

    Locale langToLocale(Language language) {
        switch (language) {
            case JAPANESE:
                return Locale.JAPAN;
            case KOREAN:
                return Locale.KOREA;
            case CHINESE:
                return Locale.CHINA;
            case ENGLISH:
                return Locale.UK;
            case GERMAN:
                return Locale.GERMANY;
            default:
                return Locale.ITALY;
        }
    }

    String welcomeText(){
        switch (lang) {
            case JAPANESE:
                return "Kawaii neko robotto-chan is ready, senpai";
            case KOREAN:
                return "안녕하세요";
            case CHINESE:
                return "大家好。我们开始";
            case ENGLISH:
                return "Robot is ready to disappoint";
            case GERMAN:
                return "Willkommen in unserem Panzerkampfwagen";
            default:
                return null;
        }
    }

    TextToSpeech getTts() {
        return tts;
    }

    enum Language {
        ENGLISH,
        JAPANESE,
        KOREAN,
        CHINESE,
        GERMAN
    }

    Language lang;
}

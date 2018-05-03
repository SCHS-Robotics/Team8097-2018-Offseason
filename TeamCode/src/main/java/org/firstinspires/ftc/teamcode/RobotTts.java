package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;

import java.util.Locale;
import java.util.Random;

import static org.firstinspires.ftc.teamcode.RobotTts.Language.CHINESE;
import static org.firstinspires.ftc.teamcode.RobotTts.Language.ENGLISH;
import static org.firstinspires.ftc.teamcode.RobotTts.Language.GERMAN;
import static org.firstinspires.ftc.teamcode.RobotTts.Language.JAPANESE;
import static org.firstinspires.ftc.teamcode.RobotTts.Language.KOREAN;

public class RobotTts {

    MediaPlayer media;
    TextToSpeech tts;

    final String SOUND_PATH = "/sdcard/RobotSounds/";

    final String LAUGH_TRACK = "laugh.mp3";

    Random generator = new Random();

    Language[] languages = new Language[] {
            JAPANESE,
            ENGLISH,
            KOREAN,
            GERMAN,
            CHINESE
    };

    RobotTts(Context context) {
        tts = new TextToSpeech(context, null);
        media = new MediaPlayer();
        media.setLooping(false);
        tts.setPitch(1.5f);
        tts.setSpeechRate(1.5f);
    }

    void playSound(String sound) {
        try {
            media.setDataSource(SOUND_PATH + sound);
            media.prepare();
        } catch (Exception IOException) {
            media.release();
        }
        media.start();
    }

    void toggleLoop() {
        media.setLooping(!media.isLooping());
    }

    public void pauseSound() {
        if (media.isPlaying()) media.pause();
    }

    public void stopSound() {
        if (media.isPlaying()) media.stop();
    }

    void speak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    void setLanguage() {
        tts.setLanguage(langToLocale(lang));
    }

    void setLanguage(Language newLanguage) {
        lang = newLanguage;
        tts.setLanguage(langToLocale(lang));
    }

    String[] randomLines(){
        switch (lang) {
            case JAPANESE:
                return new String[] {
                        "Nico Nico Nii",
                        "Be u best"
                };
            case KOREAN:
                return new String[] {
                        "감사합니다",
                        "대박. 우리는 해냈다.",
                        "오빠, 내 마음이 펄럭 거든."
                };
            case CHINESE:
                return new String[] {
                        "我想我们已经输了",
                        "妈妈你看接了吗",
                        "我们想修这个机器人",
                        "机器猫，准备摧毁羊",
                        "四是四。十是十。十四是四十。四十是四十。四十四是四十四",
                        "吃 葡 萄 不 吐 葡 萄 皮 ,不 吃 葡 萄 倒 吐 葡 萄 皮"
                };
            case ENGLISH:
                return new String[] {
                        "Speed and power",
                        "Green is my pepper",
                        "Install Gentoo",
                        "Be your best"
                };
            case GERMAN:
                return new String[] {
                        "Geschwindigkeit und Kraft",
                        "Sei dein Bestes",
                        "Grün ist mein Pfeffer"
                };
            default:
                return null;
        }
    }

    String getRandomLine() {
        int i = generator.nextInt(randomLines().length);
        return randomLines()[i];
    }

    String getRandomLine(String[] collection) {
        int i = generator.nextInt(collection.length);
        return collection[i];
    }

    Language randomLang() {
        int i = generator.nextInt(5);
        return chooseLang(i);
    }

    Language chooseLang(int i) {
        i = (i > 4) ? 0 : i;
        return languages[i];
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

    enum Language {
        ENGLISH,
        JAPANESE,
        KOREAN,
        CHINESE,
        GERMAN
    }

    Language lang;
}

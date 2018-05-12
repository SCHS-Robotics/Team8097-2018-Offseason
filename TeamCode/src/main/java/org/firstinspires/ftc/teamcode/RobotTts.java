package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.IOException;
import java.util.EventListener;
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
    MediaPlayer.OnCompletionListener onCompletionListener;

    final String SOUND_PATH = "/sdcard/RobotSounds/";

    final String LAUGH_TRACK = "laugh.mp3";
    final String LITTLE_BOXES = "boxes.mp3";

    private RobotLog debugLogger;
    private ElapsedTime time;

    Random generator = new Random();

    Language[] languages = new Language[] {
            JAPANESE,
            ENGLISH,
            KOREAN,
            GERMAN,
            CHINESE
    };

    RobotTts(Context context, ElapsedTime time, RobotLog debugLogger) {
        tts = new TextToSpeech(context, null);
        media = new MediaPlayer();
        media.setLooping(false);
        tts.setPitch(1.5f);
        tts.setSpeechRate(1.5f);
        this.time = time;
        this.debugLogger = debugLogger;
        if (this.debugLogger.loggingEnabled) {
            this.debugLogger.addDbgMessage(
                    RobotLog.DbgLevel.INFO,
                    "TTS",
                    "Initialized"
            );
        }
    }

    void readySound(String sound) {
        if (media.isPlaying()) media.stop();
        try {
            media.setDataSource(SOUND_PATH + sound);
            media.prepareAsync();

        } catch (IOException e) {
            if (debugLogger.loggingEnabled) {
                debugLogger.addDbgMessage(
                        RobotLog.DbgLevel.ERROR,
                        "TTS.playSound",
                        "File " + SOUND_PATH + "Not Found"
                );
            }
        }
    }

    void playSound() {
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
        if (debugLogger.isLoggingEnabled()) {
            debugLogger.addDbgMessage(
                    RobotLog.DbgLevel.INFO,
                    "TTS",
                    text
            );
        }
    }

    void setLanguage() {
        tts.setLanguage(langToLocale(lang));

        if (debugLogger.isLoggingEnabled()) {
            debugLogger.addDbgMessage(
                    RobotLog.DbgLevel.INFO,
                    "TTS",
                    "Language set to " + lang
            );
        }
    }

    void setLanguage(Language newLanguage) {
        lang = newLanguage;
        tts.setLanguage(langToLocale(lang));
        if (debugLogger.isLoggingEnabled()) {
            debugLogger.addDbgMessage(
                    RobotLog.DbgLevel.INFO,
                    "TTS",
                    "Language set to " + lang
            );
        }
    }

    void stopTalking() {
        tts.stop();
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
                        "Be de le beep boop",
                        "Green is my pepper",
                        "Install Gentoo",
                        "Be your best",
                        "I have low self esteem",
                        "Indeed, thus he spoke to his disciples, 'it lacks but little, " +
                                "and this long twilight will come. Alas, how shall " +
                                "I rescue my light to the other side.'",
                        "I am conscious of my existence as determined in time. " +
                                "All time-determination presupposes something persistent in perception. " +
                                "This persistent thing, however, cannot be something in me, since my own " +
                                "existence in time can first be determined only through this persistent thing. " +
                                "Thus, the perception of this persistent thing is possible only through a thing " +
                                "outside me and not through the mere representation of a thing outside me. " +
                                "Consequently, the determination of my existence in time is possible only " +
                                "by means of the existence of actual things that I perceive outside myself.",
                        "The endeavor I wish to commence on is a short thesis on suffering pertaining to solidarity, " +
                                "leading to my topic using metaphysics, epistemology and sociology. Foundations " +
                                "of my project will begin with the blossoming of my articulation of language; " +
                                "for while my language is feeble ambiguity will consume it. At the end of my project, " +
                                "finalizing it with a nice bow, I wish to spread ideals of adequate articulations " +
                                "through a paper, perhaps I may put it in the sage. Muses condemn man to frivolous " +
                                "work and depriving attitudes: I have not yet been condemned; for it is my hope " +
                                "through thought I may be enlightened and awoken from ineptitude. With much effort " +
                                "I will try to propel myself forward, reading philosophy book in order to expand " +
                                "my views; I have started reading “The Division of Labour in Society” by " +
                                "Emile Durkheim and wish to finish my readings with “Critique of Pure Reason” by " +
                                "Immanuel Kant. Starting with sociology I will move into some existentialism, " +
                                "finally concluding with epistemology; one must open one’s mind towards different " +
                                "ideals insofar as they with to share and from new ideals. My wishes are to make am " +
                                "entertaining work, spreading new ideals and a disregard of ignorance; one may say " +
                                "this is my purpose, yet assumptions would cloud that thought for this is my " +
                                "punishment. One must love the punishment for it adapts one’s purpose, yet is it " +
                                "justified for one’s to refer to this activity as punishment; through solidarity " +
                                "one’s adaptations are justified and the whole group finds these truths to be a " +
                                "priori cognitions.",
                        "I'd just like to interject for a moment. What you’re referring to as Linux, is in fact, " +
                                "GNU/Linux, or as I’ve recently taken to calling it, GNU plus Linux. Linux is " +
                                "not an operating system unto itself, but rather another free component of a " +
                                "fully functioning GNU system made useful by the GNU corelibs, shell utilities " +
                                "and vital system components comprising a full OS as defined by POSIX. Many " +
                                "computer users run a modified version of the GNU system every day, without " +
                                "realizing it. Through a peculiar turn of events, the version of GNU which is " +
                                "widely used today is often called “Linux”, and many of its users are not aware " +
                                "that it is basically the GNU system, developed by the GNU Project. " +
                                "There really is a Linux, and these people are using it, but it is just a part " +
                                "of the system they use.Linux is the kernel: the program in the system that " +
                                "allocates the machine’s resources to the other programs that you run. " +
                                "The kernel is an essential part of an operating system, but useless by itself; " +
                                "it can only function in the context of a complete operating system. Linux is " +
                                "normally used in combination with the GNU operating system: the whole system " +
                                "is basically GNU with Linux added, or GNU/Linux. All the so-called “Linux” " +
                                "distributions are really distributions of GNU/Linux"

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

    Language randomLang() {
        int i = generator.nextInt(languages.length);
        return languages[i];
    }

    private Locale langToLocale(Language language) {
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
    Language lastLang;
}

// java -jar test.jar &

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.KeyStroke;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.sun.jna.*;




public class KeyExample implements NativeKeyListener {



    // 일반 분해
    private final static char[] KO_INIT_S =
            {
                    'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ',
                    'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
            }; // 19

    private final static char[] KO_INIT_M =
            {
                    'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ',
                    'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
            }; // 21
    private final static char[] KO_INIT_E =
            {
                    0, 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ',
                    'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
            }; // 28

    // 완전 분해
    private final static char[][] KO_ATOM_S =
            {
                    { 'ㄱ' }, { 'ㄱ', 'ㄱ' }, { 'ㄴ' }, { 'ㄷ' }, { 'ㄷ', 'ㄷ' }, { 'ㄹ' }, { 'ㅁ' },
                    { 'ㅂ' }, { 'ㅂ', 'ㅂ' }, { 'ㅅ' }, { 'ㅅ', 'ㅅ' }, { 'ㅇ' }, { 'ㅈ' }, { 'ㅈ', 'ㅈ' }, { 'ㅊ' }, { 'ㅋ' }, { 'ㅌ' },
                    { 'ㅍ' }, { 'ㅎ' }
            };
    private final static char[][] KO_ATOM_M =
            {
                    { 'ㅏ' }, { 'ㅐ' }, { 'ㅑ' }, { 'ㅒ' }, { 'ㅓ' }, { 'ㅔ' }, { 'ㅕ' }, { 'ㅖ' },
                    { 'ㅗ' }, { 'ㅗ', 'ㅏ' }, { 'ㅗ', 'ㅐ' }, { 'ㅗ', 'ㅣ' }, { 'ㅛ' }, { 'ㅜ' }, { 'ㅜ', 'ㅓ' }, { 'ㅜ', 'ㅔ' },
                    { 'ㅜ', 'ㅣ' }, { 'ㅠ' }, { 'ㅡ' }, { 'ㅡ', 'ㅣ' }, { 'ㅣ' }
            };
    private final static char[][] KO_ATOM_E =
            {
                    {}, { 'ㄱ' }, { 'ㄱ', 'ㄱ' }, { 'ㄱ', 'ㅅ' }, { 'ㄴ' }, { 'ㄴ', 'ㅈ' },
                    { 'ㄴ', 'ㅎ' }, { 'ㄷ' }, { 'ㄹ' }, { 'ㄹ', 'ㄱ' }, { 'ㄹ', 'ㅁ' }, { 'ㄹ', 'ㅂ' }, { 'ㄹ', 'ㅅ' }, { 'ㄹ', 'ㅌ' },
                    { 'ㄹ', 'ㅍ' }, { 'ㄹ', 'ㅎ' }, { 'ㅁ' }, { 'ㅂ' }, { 'ㅂ', 'ㅅ' }, { 'ㅅ' }, { 'ㅅ', 'ㅅ' }, { 'ㅇ' }, { 'ㅈ' },
                    { 'ㅊ' }, { 'ㅋ' }, { 'ㅌ' }, { 'ㅍ' }, { 'ㅎ' }
            };
    // 쌍자음이나 이중모음을 분해
    private final static char[][] KO_ATOM_P =
            {
                    { 'ㄱ' }, { 'ㄱ', 'ㄱ' }, { 'ㄱ', 'ㅅ' }, { 'ㄴ' }, { 'ㄴ', 'ㅈ' },
                    { 'ㄴ', 'ㅎ' }, { 'ㄷ' }, { 'ㄸ' }, { 'ㄹ' }, { 'ㄹ', 'ㄱ' }, { 'ㄹ', 'ㅁ' }, { 'ㄹ', 'ㅂ' }, { 'ㄹ', 'ㅅ' },
                    { 'ㄹ', 'ㄷ' }, { 'ㄹ', 'ㅍ' }, { 'ㄹ', 'ㅎ' }, { 'ㅁ' }, { 'ㅂ' }, { 'ㅂ', 'ㅂ' }, { 'ㅂ', 'ㅅ' }, { 'ㅅ' },
                    { 'ㅅ', 'ㅅ' }, { 'ㅇ' }, { 'ㅈ' }, { 'ㅈ', 'ㅈ' }, { 'ㅊ' }, { 'ㅋ' }, { 'ㅌ' }, { 'ㅍ' }, { 'ㅎ' }, { 'ㅏ' }, { 'ㅐ' },
                    { 'ㅑ' }, { 'ㅒ' }, { 'ㅓ' }, { 'ㅔ' }, { 'ㅕ' }, { 'ㅖ' }, { 'ㅗ' }, { 'ㅗ', 'ㅏ' }, { 'ㅗ', 'ㅐ' }, { 'ㅗ', 'ㅣ' },
                    { 'ㅛ' }, { 'ㅜ' }, { 'ㅜ', 'ㅓ' }, { 'ㅜ', 'ㅔ' }, { 'ㅜ', 'ㅣ' }, { 'ㅠ' }, { 'ㅡ' }, { 'ㅡ', 'ㅣ' }, { 'ㅣ' }
            };

    /** 한글부분을 초성으로 교체합니다. */
    public static String toKoChosung(String text)
    {
        if (text == null) { return null; }

        // 한글자가 한글자와 그대로 대응됨.
        // 때문에 기존 텍스트를 토대로 작성된다.
        char[] rv = text.toCharArray();
        char ch;

        for (int i = 0 ; i < rv.length ; i++)
        {
            ch = rv[i];
            if (ch >= '가' && ch <= '힣')
            {
                rv[i] = KO_INIT_S[(ch - '가') / 588]; // 21 * 28
            }
        }

        return new String(rv);
    }

    /** 한글부분을 자소로 분리합니다. <br>많다 = [ㅁㅏㄶㄷㅏ] */
    public static String toKoJaso(String text)
    {
        if (text == null) { return null; }
        // StringBuilder의 capacity가 0으로 등록되는 것 방지.
        if (text.length() == 0) { return ""; }

        // 한글자당 최대 3글자가 될 수 있다.
        // 추가 할당 없이 사용하기위해 capacity 를 최대 글자 수 만큼 지정하였다.
        StringBuilder rv = new StringBuilder(text.length() * 3);

        for (char ch : text.toCharArray())
        {
            if (ch >= '가' && ch <= '힣')
            {
                // 한글의 시작부분을 구함
                int ce = ch - '가';
                // 초성을 구함
                rv.append(KO_INIT_S[ce / (588)]); // 21 * 28
                // 중성을 구함
                rv.append(KO_INIT_M[(ce = ce % (588)) / 28]); // 21 * 28
                // 종성을 구함
                if ((ce = ce % 28) != 0)
                {
                    rv.append(KO_INIT_E[ce]);
                }
            }
            else
            {
                rv.append(ch);
            }
        }

        return rv.toString();
    }

    /** 한글부분을 자소로 완전 분리합니다. <br>많다 = [ㅁㅏㄴㅎㄷㅏ]*/
    public static String toKoJasoAtom(String text)
    {
        if (text == null) { return null; }
        // StringBuilder의 capacity가 0으로 등록되는 것 방지.
        if (text.length() == 0) { return ""; }

        // 한글자당 최대 6글자가 될 수 있다.
        // 추가 할당 없이 사용하기위해 capacity 를 최대 글자 수 만큼 지정하였다.
        StringBuilder rv = new StringBuilder(text.length() * 6);

        for (char ch : text.toCharArray())
        {
            if (ch >= '가' && ch <= '힣')
            {
                // 한글의 시작부분을 구함
                int ce = ch - '가';
                // 초성을 구함
                rv.append(KO_ATOM_S[ce / (588)]); // 21 * 28
                // 중성을 구함
                rv.append(KO_ATOM_M[(ce = ce % (588)) / 28]); // 21 * 28
                // 종성을 구함
                if ((ce = ce % 28) != 0)
                {
                    rv.append(KO_ATOM_E[ce]);
                }
            }
            // 쌍자음과 이중모음 분리
            else if (ch >= 'ㄱ' && ch <= 'ㅣ')
            {
                rv.append(KO_ATOM_P[ch - 'ㄱ']);
            }
            else
            {
                rv.append(ch);
            }
        }

        return rv.toString();
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent arg0) {
        String param = NativeKeyEvent.getKeyText(arg0.getKeyCode());
        System.out.println(param);
        if(param == "F7" && TypeExample.enabled == true) {
            System.out.println("중지");
            TypeExample.enabled = false;
        } else if(param == "F7" && TypeExample.enabled == false) {
            System.out.println("리섬");
            TypeExample.enabled = true;
        } else if(param == "F8") {
            TypeExample.reset = true;
            TypeExample.enabled = true;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent arg0) {
        // TODO Auto-generated method stub

    }
}

class TypeExample implements Runnable {
    public interface User32jna extends Library {
        User32jna INSTANCE = (User32jna) Native.loadLibrary("user32.dll", User32jna.class);
        public void keybd_event(byte bVk, byte bScan, int dwFlags, int dwExtraInfo);
    }
    User32jna u32 = User32jna.INSTANCE;
    public static boolean enabled = true;
    public static boolean reset = false;
    private List<String> list;
    private byte currentKey = 0;
    private final static char[] KO_INIT_M =
            {
                    'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ',
                    'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
            }; // 21
    private final static char[] KO_INIT_E =
            {
                    'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ',
                    'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
            }; // 28

    TypeExample(List<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        Robot robot;
        try {
            robot = new Robot();
            mainloop:
            for(int count = 0; count < list.size();) {
                if(reset) {
                    enabled = true;
                    reset = false;
                    break mainloop;
                }

                boolean isKorean = false;
                char compare = list.get(count).charAt(0);
                loop:
                for(int it1 = 0; it1 < KO_INIT_E.length; it1++ ) {
                    for(int it2 = 0; it2 < KO_INIT_M.length; it2++ ) {
                        if(KO_INIT_E[it1] == compare || KO_INIT_M[it2] == compare) {
                            isKorean = true;
                            break loop;
                        }
                    }
                }

                if(enabled && !reset) {
                    if (isKorean) {
                        try {
                            int keyStroke = MyRobot.ConvertToLowerKey(list.get(count).charAt(0));
                            robot.keyPress(keyStroke);
                            robot.keyRelease(keyStroke);

                            Thread.sleep((int) (Math.random() * 200) + 50);
                        } catch (Exception e) {
                            System.out.println(list.get(count).charAt(0) + " 단어");
                        }
                    } else {
                        try {
                            Thread.sleep(20);
                        } catch (Exception e) {
                            System.out.println("Exception" + e);
                        }
                        String c = "" + list.get(count).charAt(0);
                        StringSelection selection = new StringSelection(c);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(selection, null);
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_V);
                        robot.keyRelease(KeyEvent.VK_V);
                        robot.keyRelease(KeyEvent.VK_CONTROL);

                        Thread.sleep((int) (Math.random() * 200) + 50);
                    }
                    count++;
                }

                if(!enabled) {
                    Thread.sleep(100);
                    continue mainloop;
                }
            }
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String copyString = "";
            if(copyString != null)
            {
                Transferable contents = new StringSelection(copyString);
                clipboard.setContents(contents, null);
            }
            App.updateTimer = true;
            enabled = true;
            reset = false;
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
import java.io.File;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.jnativehook.GlobalScreen;

import java.awt.Toolkit;
import java.awt.datatransfer.*;

public class App extends JFrame {
    public static boolean updateTimer = true;

    public App() {
        super("");
        setVisible(false);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        App m = new App();
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        try {
            GlobalScreen.registerNativeHook();
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        GlobalScreen.addNativeKeyListener(new KeyExample());

        if (updateTimer) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    updateTimer = false;
                    Transferable contents = null;
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    if(clipboard != null)
                        contents = clipboard.getContents(clipboard);
                    if (contents != null) {
                        try {
                            String pasteString = (String) (contents.getTransferData(DataFlavor.stringFlavor));
                            List<String> AnsList = new ArrayList<String>();
                            List<String> getList = new ArrayList<String>();
                            File file = new File("D:/Data.txt");
                            if (file.exists()) {
                                try {
                                    getList = Files.readAllLines(file.toPath(), Charset.defaultCharset());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                if (getList.isEmpty())
                                    return;
                            }
                            for (String line : getList) {
                                String[] res = line.split(";;;");
                                if (res != null) {
                                    if (res[0].equals(pasteString)) {
                                        for (int count = 0; count < res[1].length(); count++) {
                                            if (Pattern.matches("[a-z]", "" + res[1].charAt(count))
                                                    || Pattern.matches("[A-Z]", "" + res[1].charAt(count))
                                                    || Pattern.matches("[0-9]", "" + res[1].charAt(count))
                                                    || Pattern.matches("[ ]", "" + res[1].charAt(count))) {
                                                AnsList.add("" + res[1].charAt(count));
                                            } else if (Pattern.matches("^[ㄱ-ㅎ가-힣]*$", "" + res[1].charAt(count))) {
                                                String splitedKorean = KeyExample
                                                        .toKoJasoAtom("" + res[1].charAt(count));
                                                for (int it = 0; it < splitedKorean.length(); it++) {
                                                    AnsList.add("" + splitedKorean.charAt(it));
                                                }
                                            } else {
                                                AnsList.add("" + res[1].charAt(count));
                                            }
                                        }
                                        TypeExample te = new TypeExample(AnsList);
                                        Thread t = new Thread(te);
                                        te.enabled = true;
                                        te.reset = false;
                                        t.start();
                                    }
                                }
                            }
                        } catch (Exception e) {
                        }

                    }
                }

            };
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(task, 0, 2000);
        }
    }
}
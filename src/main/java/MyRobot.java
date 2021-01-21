import java.awt.event.KeyEvent;

public class MyRobot {
    public static byte ConvertToUpperKey(char param) {
        switch(param) {
            case 'ㅃ' :
                return KeyEvent.VK_Q;
            case 'ㅉ' :
                return KeyEvent.VK_W;
            case 'ㄸ' :
                return KeyEvent.VK_E;
            case 'ㄲ' :
                return KeyEvent.VK_R;
            case 'ㅆ' :
                return KeyEvent.VK_T;
            case 'ㅒ' :
                return KeyEvent.VK_U;
            case 'ㅖ' :
                return KeyEvent.VK_I;
        }
        return 0;
    }

    public static byte ConvertToLowerKey(char param) {
        switch(param) {
            case 'ㅂ' :
                return KeyEvent.VK_Q;
            case 'ㅈ' :
                return KeyEvent.VK_W;
            case 'ㄷ' :
                return KeyEvent.VK_E;
            case 'ㄱ' :
                return KeyEvent.VK_R;
            case 'ㅅ' :
                return KeyEvent.VK_T;
            case 'ㅛ' :
                return KeyEvent.VK_Y;
            case 'ㅕ' :
                return KeyEvent.VK_U;
            case 'ㅑ' :
                return KeyEvent.VK_I;
            case 'ㅐ' :
                return KeyEvent.VK_O;
            case 'ㅔ' :
                return KeyEvent.VK_P;
            case 'ㅁ' :
                return KeyEvent.VK_A;
            case 'ㄴ' :
                return KeyEvent.VK_S;
            case 'ㅇ' :
                return KeyEvent.VK_D;
            case 'ㄹ' :
                return KeyEvent.VK_F;
            case 'ㅎ' :
                return KeyEvent.VK_G;
            case 'ㅗ' :
                return KeyEvent.VK_H;
            case 'ㅓ' :
                return KeyEvent.VK_J;
            case 'ㅏ' :
                return KeyEvent.VK_K;
            case 'ㅣ' :
                return KeyEvent.VK_L;
            case 'ㅋ' :
                return KeyEvent.VK_Z;
            case 'ㅌ' :
                return KeyEvent.VK_X;
            case 'ㅊ' :
                return KeyEvent.VK_C;
            case 'ㅍ' :
                return KeyEvent.VK_V;
            case 'ㅠ' :
                return KeyEvent.VK_B;
            case 'ㅜ' :
                return KeyEvent.VK_N;
            case 'ㅡ' :
                return KeyEvent.VK_M;
        }
        return 0;
    }
}

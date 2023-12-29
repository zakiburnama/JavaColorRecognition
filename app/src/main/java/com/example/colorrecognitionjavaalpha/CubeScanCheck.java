package com.example.colorrecognitionjavaalpha;

import android.util.Log;

public class CubeScanCheck {


    public char cekWarna(double hue, double saturation, double value) {
// #### check color based on HSV and save it to char
        char color = 'X';
        if (saturation < 70) {
            color = 'W';
        } else if (value < 70) {
            color = 'X';
        } else {
            if (hue < 20) {
                color = 'O';
            }  else if (hue < 64) {
                color = 'Y';
            } else if (hue < 90) {
                color = 'G';
            } else if (hue < 130) {
                color = 'B';
            } else {
                color = 'R';
            }
        }

        return color;
    }


    public Boolean cekIsSolved(char[][][] warnaSisiRubik) {
// #### cek in one side, is all color there same with color at the center
        int flag = 0;
        char charSide;

        // cek in one side, is all color there same with color at the center
        for (int side = 0; side < 6; side++) {
            charSide = warnaSisiRubik[side][1][1];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (charSide != warnaSisiRubik[side][i][j]) {
                        flag++;
                        break;
                    }
                }
                if (flag > 0 ) break;
            }
            if (flag > 0 ) break;
        }

        return flag <= 0;
    }

    public Boolean cekIsColorComplete(char[][][] warnaSisiRubik) {
// #### count all color r g y b o w, if the number is 9 then its true
        int flagr = 0,
                flagy = 0,
                flagg = 0,
                flagb = 0,
                flago = 0,
                flagw = 0;

        // count all color r g y b o w, if the number is 9 then its true
        for (int side = 0; side < 6; side++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    switch (warnaSisiRubik[side][i][j]) {
                        case 'R':
                            flagr++;
                            break;
                        case 'Y':
                            flagy++;
                            break;
                        case 'G':
                            flagg++;
                            break;
                        case 'B':
                            flagb++;
                            break;
                        case 'O':
                            flago++;
                            break;
                        case 'W':
                            flagw++;
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        return flagr == 9 && flagy == 9 && flagg == 9 && flagb == 9 && flago == 9 && flagw == 9;
    }
}
package com.example.colorrecognitionjavaalpha;

public class Cube {

    //Stores the state of the cube as an object of 26 cubies
    private Cubie[][][] cubiePos = new Cubie[3][3][3];

    /**
     * Constructs the Cube object by instantiating a Cubie for each position in three-dimensional space
     * When the cube is held with Yellow facing up and Green facing front, x increases going from left to right,
     * y increases going from the front to the back, and z increases going from the top to the bottom.
     * x, y, and z are zero-indexed.
     * The core of the cube is not an actual cubie, but is instantiated as one to prevent runtime error
     */
    public Cube() {
        //Up, Front Row
        cubiePos[0][0][0] = new Cubie(0,0,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('R','L'), new CubieColor('G','F')}, true, false);
        cubiePos[1][0][0] = new Cubie(1,0,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('G','F')}, false, true);
        cubiePos[2][0][0] = new Cubie(2,0,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('G','F'), new CubieColor('O','R')}, true, false);

        //Front, E Row
        cubiePos[0][0][1] = new Cubie(0,0,1,
                new CubieColor[]{ new CubieColor('R','L'), new CubieColor('G','F')}, false, true);
        cubiePos[1][0][1] = new Cubie(1,0,1,
                new CubieColor[]{ new CubieColor('G','F')}, false, false);
        cubiePos[2][0][1] = new Cubie(2,0,1,
                new CubieColor[]{ new CubieColor('G','F'), new CubieColor('O','R')}, false, true);

        //Down, Front Row
        cubiePos[0][0][2] = new Cubie(0,0,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('R','L'), new CubieColor('G','F')}, true, false);
        cubiePos[1][0][2] = new Cubie(1,0,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('G','F')}, false, true);
        cubiePos[2][0][2] = new Cubie(2,0,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('G','F'), new CubieColor('O','R')}, true, false);

        //Up, S Row
        cubiePos[0][1][0] = new Cubie(0,1,0,
                new CubieColor[]{ new CubieColor('R','L'), new CubieColor('Y','U')}, false, true);
        cubiePos[1][1][0] = new Cubie(1,1,0,
                new CubieColor[]{ new CubieColor('Y','U')}, false, false);
        cubiePos[2][1][0] = new Cubie(2,1,0,
                new CubieColor[]{ new CubieColor('Y','U'), new CubieColor('O','R')}, false, true);

        //E, S Row
        cubiePos[0][1][1] = new Cubie(0,1,1,
                new CubieColor[]{ new CubieColor('R','L')}, false, false);
        cubiePos[1][1][1] = new Cubie(1,1,1,
                new CubieColor[]{ new CubieColor('A','A')}, //Just giving random, non-legitimate values for color and direction
                false, false);
        cubiePos[2][1][1] = new Cubie(2,1,1,
                new CubieColor[]{ new CubieColor('O','R')}, false, false);

        //Down, S Row
        cubiePos[0][1][2] = new Cubie(0,1,2,
                new CubieColor[]{ new CubieColor('R','L'), new CubieColor('W','D')}, false, true);
        cubiePos[1][1][2] = new Cubie(1,1,2,
                new CubieColor[]{ new CubieColor('W','D')}, false, false);
        cubiePos[2][1][2] = new Cubie(2,1,2,
                new CubieColor[]{ new CubieColor('W','D'), new CubieColor('O','R')}, false, true);

        //Up, Back Row
        cubiePos[0][2][0] = new Cubie(0,2,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('R','L'), new CubieColor('B','B')}, true, false);
        cubiePos[1][2][0] = new Cubie(1,2,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('B','B')}, false, true);
        cubiePos[2][2][0] = new Cubie(2,2,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('B','B'), new CubieColor('O','R')}, true, false);

        //E, Back Row
        cubiePos[0][2][1] = new Cubie(0,2,1,
                new CubieColor[]{ new CubieColor('R','L'), new CubieColor('B','B')}, false, true);
        cubiePos[1][2][1] = new Cubie(1,2,1,
                new CubieColor[]{ new CubieColor('B','B')}, false, false);
        cubiePos[2][2][1] = new Cubie(2,2,1,
                new CubieColor[]{ new CubieColor('B','B'), new CubieColor('O','R')}, false, true);

        //Down, Back Row
        cubiePos[0][2][2] = new Cubie(0,2,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('R','L'), new CubieColor('B','B')}, true, false);
        cubiePos[1][2][2] = new Cubie(1,2,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('B','B')}, false, true);
        cubiePos[2][2][2] = new Cubie(2,2,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('B','B'), new CubieColor('O','R')}, true, false);

    }

    /**
     * Sets all the colors of the cube to the colors inputed by the user during color selection mode.
     * Invoked from the CubePainter class when user decides to proceed to solution after inputing colors.
     * The colors inputed as the colors[][][] parameter are in a slightly different state than the colors
     * produced by the getColors() method. If the side is not the yellow or white side, then the user
     * inputed the colors when yellow is above and white is below the desired face. If the face is the yellow
     * face, the user inputed as if blue was above and green was below the yellow face, the blue and green
     * being in the opposite orientation for when inputing colors on the white face.
     * @param colors all colors to be put into the cube
     */
    public void setAllColors(char[][][] colors) {
        //Set Left colors
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                cubiePos[0][Math.abs(j-2)][i].setColorOfDir('L', colors[0][i][j]);
            }
        }
        //Set Up colors
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                cubiePos[j][Math.abs(i-2)][0].setColorOfDir('U', colors[1][i][j]);
            }
        }
        //Set Front colors
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                cubiePos[j][0][i].setColorOfDir('F', colors[2][i][j]);
            }
        }
        //Set Back colors
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                cubiePos[Math.abs(j-2)][2][i].setColorOfDir('B', colors[3][i][j]);
            }
        }
        //Set Right colors
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                cubiePos[2][j][i].setColorOfDir('R', colors[4][i][j]);
                colors[4][i][j] = cubiePos[2][j][i].getColorOfDir('R');
            }
        }
        //Set Down colors
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                cubiePos[j][i][2].setColorOfDir('D', colors[5][i][j]);
            }
        }
    }

}

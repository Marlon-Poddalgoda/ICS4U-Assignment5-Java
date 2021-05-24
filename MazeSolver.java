import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
/*
* This program imports text mazes and outputs the solution.
*
* @author  Marlon Poddalgoda
* @version 1.0
* @since   2021-05-23
*/

public final class MazeSolver {
    private MazeSolver() {
        // Throw an exception if this ever *is* called
        throw new AssertionError("Instantiating utility class.");
    }

    /**
    * This global variable is the maze in a bool array.
    */
    private static boolean[][] listBool;

    /**
    * This method prints the maze to the console.
    * @param arrayMaze This value is the maze as an array.
    * @param length This value is the length of the maze.
    * @param width This value is the width of the maze.
    */
    static void mazePrint(final char[][] arrayMaze, final int length,
                                                    final int width) {
        // Printing the columns containing the maze information
        for (int countX = 0; countX < width; countX++) {
            for (int countY = 0; countY < length; countY++) {
                char printElem = (char) (arrayMaze[countX][countY]);
                System.out.print(printElem + " ");
            }
            System.out.println("");
        }
    }


    /**
    * This method locates the Start and End points.
    * @param currentMaze This value is the maze as an array.
    * @return pointLocation
    */
    static int[] locatePoints(final char[][] currentMaze) {
        // variables
        final int four = 4;
        final int three = 3;

        // Setting up a list for coordinates as well as coordinate variables
        int[] pointLocation = new int[four];
        int startingX = 0;
        int startingY = 0;
        int endingX = 0;
        int endingY = 0;

        // Searching the array for a starting point
        for (int countX = 0; countX < currentMaze.length; countX++) {
            for (int countY = 0; countY < currentMaze[countX].length;
                                                            countY++) {
                if (currentMaze[countX][countY] == 'S') {
                    startingX = countX;
                    startingY = countY;
                    break;
                }
            }
        }

        // Searching the array for a starting point
        for (int endX = 0; endX < currentMaze.length; endX++) {
            for (int endY = 0; endY < currentMaze[endX].length; endY++) {
                if (currentMaze[endX][endY] == 'G') {
                    endingX = endX;
                    endingY = endY;
                    break;
                }
            }
        }

        // Adding the coordinates to an array
        pointLocation[0] = startingX;
        pointLocation[1] = startingY;
        pointLocation[2] = endingX;
        pointLocation[three] = endingY;

        // Returning the array with the coordinates
        return pointLocation;
    }


    /**
    * This method finds a solution to the maze using recursion.
    * @param mazeBool This value is the maze as an boolean array.
    * @param travelPath This value is the path of travel in boolean.
    * @param finalMaze This value is the final maze as a boolean array.
    * @param coordX This is the X coordinate of the current path.
    * @param coordY This is the Y coordinate of the current path.
    * @param goalX This is the X coordinate of the goal.
    * @param goalY this is the Y coordinate of the goal.
    * @return Boolean expression.
    */
    static boolean solutionMaze(final boolean[][] mazeBool,
                                final boolean[][] travelPath,
                                final boolean[][] finalMaze, final int coordX,
                                final int coordY, final int goalX,
                                final int goalY) {
        // check if program reached
        if (coordX == goalX && coordY == goalY) {
            return true;
        }
        // check if program hit a wall or already been
        if (mazeBool[coordX][coordY] || travelPath[coordX][coordY]) {
            return false;
        }

        // marking path of program
        travelPath[coordX][coordY] = true;

        // check if program is on left edge
        if (coordX != 0) {
            if (solutionMaze(mazeBool, travelPath, finalMaze, coordX - 1,
                            coordY, goalX, goalY)) {
                listBool[coordX][coordY] = true;
                return true;
            }
        }
        // check if program on right edge
        if (coordX != mazeBool.length - 1) {
            if (solutionMaze(mazeBool, travelPath, finalMaze, coordX + 1,
                     coordY, goalX, goalY)) {
                listBool[coordX][coordY] = true;
                return true;
            }
        }
        // check if program on top edge
        if (coordY != 0) {
            if (solutionMaze(mazeBool, travelPath, finalMaze, coordX,
                     coordY - 1, goalX, goalY)) {
                listBool[coordX][coordY] = true;
                return true;
            }
        }
        // check if program on bottom edge
        if (coordY != mazeBool[0].length - 1) {
            if (solutionMaze(mazeBool, travelPath, finalMaze, coordX,
                     coordY + 1, goalX, goalY)) {
                listBool[coordX][coordY] = true;
                return true;
            }
        }

        // return statement
        return false;
    }


    /**
    * This method finds if there is a possible solution.
    * @param mazePoints This value holds the points of the maze.
    * @param startEnd This value is the Start and End of the maze.
    * @param rowSize This value is the row size.
    * @param columnSize This value is the column size.
    * @return solvedMaze This is the solved maze.
    */
    static char[][] locateSolution(final char[][] mazePoints,
                                   final int[] startEnd,
                                   final int rowSize, final int columnSize) {
        // variables
        final int three = 3;

        // Creating a boolean version of the maze
        listBool = new boolean[columnSize][rowSize];
        boolean[][] mapBool = new boolean[columnSize][rowSize];
        boolean[][] listBool1 = new boolean[columnSize][rowSize];
        boolean[][] listBool2 = new boolean[columnSize][rowSize];

        // Creating a boolean equivalent to the maze
        for (int mapRow = 0; mapRow < mazePoints.length; mapRow++) {
            for (int mapColumn = 0; mapColumn < mazePoints[0].length;
                                                        mapColumn++) {
                if (mazePoints[mapRow][mapColumn] == '#') {
                    mapBool[mapRow][mapColumn] = true;
                } else {
                    mapBool[mapRow][mapColumn] = false;
                }
            }
        }

        // Filling the other arrays with boolean values
        for (int rowBool = 0; rowBool < mazePoints.length; rowBool++) {
            for (int colBool = 0; colBool < mazePoints[rowBool].length;
                    colBool++) {
                listBool[rowBool][colBool] = false;
                listBool1[rowBool][colBool] = false;
                listBool2[rowBool][colBool] = false;
            }
        }

        // Extracting important coordinates from the passed in array
        int beginX = startEnd[0];
        int beginY = startEnd[1];
        int stopX = startEnd[2];
        int stopY = startEnd[three];

        // Finding a solution for the maze
        boolean solution = solutionMaze(mapBool, listBool1, listBool2,
                                        beginX, beginY, stopX, stopY);

        // Telling the user if a solution was found or not
        if (solution) {
            System.out.println("Program has found a solution.");
        } else {
            System.out.println("Sorry, no solution was found.");
        }

        // variable
        char[][] solvedMaze = new char[columnSize][rowSize];

        // returning boolean version back to normal
        for (int rows = 0; rows < columnSize; rows++) {
            for (int cols = 0; cols < rowSize; cols++) {
                if (listBool[rows][cols]) {
                    solvedMaze[rows][cols] = '+';
                } else if (listBool[rows][cols] == false
                            && mazePoints[rows][cols] == '.') {
                    solvedMaze[rows][cols] = '.';
                } else {
                    solvedMaze[rows][cols] = '#';
                }
            }
        }

        // resetting start and end coords
        solvedMaze[beginX][beginY] = 'S';
        solvedMaze[stopX][stopY] = 'G';

        // Returning the newly solved maze
        return solvedMaze;
    }


    /**
    * This method creates a new maze copy.
    * @param mazeFile This is the maze file.
    * @param length This value is the length of the maze.
    * @param width This value is the width of the maze.
    * @return newMaze This is the newly generated maze.
    */
    static char[][] newMaze(final File mazeFile, final int length,
                            final int width) {
        // Creating array for the function to return containing the maze info
        char[][] newMaze = new char[width][length];

        try {
            // take elemens of txt file, pushing into array
            Scanner mazeScan = new Scanner(mazeFile);
            for (int row = 0; mazeScan.hasNextLine() && row < width; row++) {
                char[] tempArray = mazeScan.nextLine().toCharArray();
                for (int col = 0; col < length && col < tempArray.length;
                                                                col++) {
                    newMaze[row][col] = tempArray[col];
                }
            }

        // error catch
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, please enter a maze.txt file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Sorry, please enter a valid maze.");
        }

        // return statement
        return newMaze;
    }


    /**
    * This method takes in the maze txt files and passes to methods.
    * @param args
    */
    public static void main(final String[] args) {
        // variables
        final int six = 6;
        final int twelve = 12;
        final int nineteen = 19;
        final int nine = 9;

        // Creating the first maze
        File mazeFile1 = new File("Maze1.txt");
        char[][] maze1 = newMaze(mazeFile1, six, six);

        // basic instructions
        System.out.println("This program solves any .txt maze.");
        System.out.println();

        // Printing the first maze at its starting point
        System.out.println("Maze 1:");
        mazePrint(maze1, six, six);
        System.out.println("");

        // Finding the start and stop points of the first maze
        int[] breakPoints1 = locatePoints(maze1);

        // Finding a solution to the first maze and printing it out
        char[][] firstSolved = locateSolution(maze1, breakPoints1, six, six);
        mazePrint(firstSolved, six, six);
        System.out.println("");
        System.out.println("");

        // Creating the second maze
        File mazeFile2 = new File("Maze2.txt");
        char[][] maze2 = newMaze(mazeFile2, six, twelve);

        // Printing the second maze at its starting point
        System.out.println("Maze 2:");
        mazePrint(maze2, six, twelve);
        System.out.println("");

        // Finding the start and stop points of the second maze
        int[] breakPoints2 = locatePoints(maze2);

        // Finding a solution to the second maze and printing it out
        char[][] secondSolved = locateSolution(maze2, breakPoints2, six,
                                                twelve);
        mazePrint(secondSolved, six, twelve);
        System.out.println("");
        System.out.println("");

        // Creating the third maze
        File mazeFile3 = new File("Maze3.txt");
        char[][] maze3 = newMaze(mazeFile3, nineteen, nine);

        // Printing the third maze at its starting point
        System.out.println("Maze 3:");
        mazePrint(maze3, nineteen, nine);
        System.out.println("");

        // Finding the start and stop points of the third maze
        int[] breakPoints3 = locatePoints(maze3);

        // Finding a solution to the third maze and printing it out
        char[][] thirdSolved = locateSolution(maze3, breakPoints3, nineteen,
                                                nine);
        mazePrint(thirdSolved, nineteen, nine);
    }
}

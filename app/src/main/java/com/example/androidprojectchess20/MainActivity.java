/**
 * @author Group 20: Jonathan Lin (jkl130) & Jason Cariaga (jmc803)
 * @version 1.0
 */
package com.example.androidprojectchess20;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //TextView mStartText = (TextView)findViewById(R.id.startText);
    String firstInput = "";
    String secondInput = "";
    String totalInput = "";
    String playback = "";
    String backupPlayback = "";

    /*
    PORTED CODE
     */
    public String[][] chessboard = new String[8][8];
    public String[][] backupChessboard = new String[8][8];
    public String phase = "White";
    public int[] castleMoves = new int[6];
    public boolean isDraw = false;
    public boolean gameEnd = false;
    public boolean isIllegal = false;
    public boolean gameOngoing = false;
    public boolean undoUsed = true;
    public boolean kingInCheck = false;
    public boolean drawButtonBool = false;
    public static boolean playbackMode = false;
    public static String feed = "";
    public static int turnCount = 0;
    Scanner sc;

    /**
     * This method is responsible for playing past games.
     */
    public void playbackRecord()
    {
        //EditText thing = (EditText)findViewById(R.id.sourceInput);
        //thing.setText(feed);
        if(turnCount == 0)
        {
            Button drawButton = (Button)findViewById((R.id.draw_button));
            drawButton.setText(" ");;
            Button aiButton = (Button)findViewById((R.id.button4));
            aiButton.setText(" ");;
            Button resignButton = (Button)findViewById(R.id.resign);
            resignButton.setText(" ");
            Button undoButton = (Button)findViewById((R.id.undo_button));
            undoButton.setText(" ");
            playbackMode = true;
            resetChessboard();
            printChessboard();
            playback = "";
            phase = "White";
            drawButtonBool = false;
            isDraw = false;
            gameOngoing = false;
            gameEnd = false;
            Button nextMove = (Button)findViewById(R.id.restart_button);
            nextMove.setText("Next Move");
            sc = new Scanner(feed);
            EditText sourceText = (EditText)findViewById(R.id.sourceInput);
            sourceText.setText("Press Next Move");
            turnCount++;
        } else {
            if(sc.hasNextLine())
            {
                totalInput = sc.nextLine();
                EditText sourceText = (EditText)findViewById(R.id.sourceInput);
                sourceText.setText(phase+": "+totalInput);
                playerLoop();
            } else {
                playbackMode = false;
                Button nextMove = (Button)findViewById(R.id.restart_button);
                nextMove.setText("Restart Game");
                Button drawButton = (Button)findViewById((R.id.draw_button));
                drawButton.setText("Draw");;
                Button aiButton = (Button)findViewById((R.id.button4));
                aiButton.setText("AI");;
                Button resignButton = (Button)findViewById(R.id.resign);
                resignButton.setText("Resign");
                Button undoButton = (Button)findViewById((R.id.undo_button));
                undoButton.setText("Undo");

                resetChessboard();
                printChessboard();
                playback = "";
                EditText sourceText = (EditText)findViewById(R.id.sourceInput);
                sourceText.setText("White: ?? to ??");
                phase = "White";
                drawButtonBool = false;
                isDraw = false;
                gameOngoing = false;
                gameEnd = false;
                openRecordActivity();
            }
            turnCount++;
        }
    }

    /**
     * This method prompts the user if they'd like to save the replay.
     */
    public void gameOver()
    {
        AlertDialog restart = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Game Over")
                .setMessage("Would you like to save a replay of this game?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try(ObjectOutputStream replays = new ObjectOutputStream(new FileOutputStream("replays.txt"))) {

                        } catch(EOFException eof)
                        {

                        } catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    /**
     * This method resets the entire chessboard and its pieces to their original positions.
     */
    public void resetChessboard()
    {
        isDraw = false;
        for(int i = 0; i < 6; i++)
        {
            castleMoves[i] = 0;
        }
        /*
        Set all black pieces.
         */
        chessboard[0][0] = "bR";
        chessboard[0][1] = "bN";
        chessboard[0][2] = "bB";
        chessboard[0][3] = "bQ";
        chessboard[0][4] = "bK";
        chessboard[0][5] = "bB";
        chessboard[0][6] = "bN";
        chessboard[0][7] = "bR";
        for(int i = 0; i < 8; i++)
        {
            chessboard[1][i] = "bp";
        }

        /*
        Empty the middle four rows.
         */
        for(int i = 2; i < 6; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                chessboard[i][j] = "  ";
            }
        }

        /*
        Set all white pieces.
         */
        for(int i = 0; i < 8; i++)
        {
            chessboard[6][i] = "wp";
        }
        chessboard[7][0] = "wR";
        chessboard[7][1] = "wN";
        chessboard[7][2] = "wB";
        chessboard[7][3] = "wQ";
        chessboard[7][4] = "wK";
        chessboard[7][5] = "wB";
        chessboard[7][6] = "wN";
        chessboard[7][7] = "wR";

        /*
        Test cases
         */
//        chessboard[6][3] = "  ";
//        chessboard[6][4] = "  ";
//        chessboard[5][4] = "bQ";
//        for(int i = 0; i < 8; i++)
//        {
//        	for(int j = 0; j < 8; j++)
//        	{
//        		chessboard[i][j] = "  ";
//        	}
//        }
//        chessboard[0][6] = "bK";
//        chessboard[5][5] = "bQ";
//        chessboard[5][6] = "bQ";
//        chessboard[6][5] = "bQ";
//        chessboard[6][6] = "bQ";
//        chessboard[7][7] = "wK";
    }

    /**
     * This method simply creates a backup copy of the chessboard to be used in other test cases, such as undoing a move and failing a move due to a check.
     */
    public void copyChessboard()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                backupChessboard[i][j] = chessboard[i][j];
            }
        }
        backupPlayback = playback;
    }

    /**
     * This method reverts the main chessboard to a backup copy of the chessboard.
     */
    public void revertChessboard()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                chessboard[i][j] = backupChessboard[i][j];
            }
        }
        playback = backupPlayback;
    }

    /**
     * This method is responsible for printing the chessboard, only to be called after each successful turn.
     * Note: This method is not called if a player makes an illegal move.
     */
    public void printChessboard()
    {
        ImageView nodeA1 = (ImageView)findViewById(R.id.a1);
        ImageView nodeA2 = (ImageView)findViewById(R.id.a2);
        ImageView nodeA3 = (ImageView)findViewById(R.id.a3);
        ImageView nodeA4 = (ImageView)findViewById(R.id.a4);
        ImageView nodeA5 = (ImageView)findViewById(R.id.a5);
        ImageView nodeA6 = (ImageView)findViewById(R.id.a6);
        ImageView nodeA7 = (ImageView)findViewById(R.id.a7);
        ImageView nodeA8 = (ImageView)findViewById(R.id.a8);
        ImageView nodeB1 = (ImageView)findViewById(R.id.b1);
        ImageView nodeB2 = (ImageView)findViewById(R.id.b2);
        ImageView nodeB3 = (ImageView)findViewById(R.id.b3);
        ImageView nodeB4 = (ImageView)findViewById(R.id.b4);
        ImageView nodeB5 = (ImageView)findViewById(R.id.b5);
        ImageView nodeB6 = (ImageView)findViewById(R.id.b6);
        ImageView nodeB7 = (ImageView)findViewById(R.id.b7);
        ImageView nodeB8 = (ImageView)findViewById(R.id.b8);
        ImageView nodeC1 = (ImageView)findViewById(R.id.c1);
        ImageView nodeC2 = (ImageView)findViewById(R.id.c2);
        ImageView nodeC3 = (ImageView)findViewById(R.id.c3);
        ImageView nodeC4 = (ImageView)findViewById(R.id.c4);
        ImageView nodeC5 = (ImageView)findViewById(R.id.c5);
        ImageView nodeC6 = (ImageView)findViewById(R.id.c6);
        ImageView nodeC7 = (ImageView)findViewById(R.id.c7);
        ImageView nodeC8 = (ImageView)findViewById(R.id.c8);
        ImageView nodeD1 = (ImageView)findViewById(R.id.d1);
        ImageView nodeD2 = (ImageView)findViewById(R.id.d2);
        ImageView nodeD3 = (ImageView)findViewById(R.id.d3);
        ImageView nodeD4 = (ImageView)findViewById(R.id.d4);
        ImageView nodeD5 = (ImageView)findViewById(R.id.d5);
        ImageView nodeD6 = (ImageView)findViewById(R.id.d6);
        ImageView nodeD7 = (ImageView)findViewById(R.id.d7);
        ImageView nodeD8 = (ImageView)findViewById(R.id.d8);
        ImageView nodeE1 = (ImageView)findViewById(R.id.e1);
        ImageView nodeE2 = (ImageView)findViewById(R.id.e2);
        ImageView nodeE3 = (ImageView)findViewById(R.id.e3);
        ImageView nodeE4 = (ImageView)findViewById(R.id.e4);
        ImageView nodeE5 = (ImageView)findViewById(R.id.e5);
        ImageView nodeE6 = (ImageView)findViewById(R.id.e6);
        ImageView nodeE7 = (ImageView)findViewById(R.id.e7);
        ImageView nodeE8 = (ImageView)findViewById(R.id.e8);
        ImageView nodeF1 = (ImageView)findViewById(R.id.f1);
        ImageView nodeF2 = (ImageView)findViewById(R.id.f2);
        ImageView nodeF3 = (ImageView)findViewById(R.id.f3);
        ImageView nodeF4 = (ImageView)findViewById(R.id.f4);
        ImageView nodeF5 = (ImageView)findViewById(R.id.f5);
        ImageView nodeF6 = (ImageView)findViewById(R.id.f6);
        ImageView nodeF7 = (ImageView)findViewById(R.id.f7);
        ImageView nodeF8 = (ImageView)findViewById(R.id.f8);
        ImageView nodeG1 = (ImageView)findViewById(R.id.g1);
        ImageView nodeG2 = (ImageView)findViewById(R.id.g2);
        ImageView nodeG3 = (ImageView)findViewById(R.id.g3);
        ImageView nodeG4 = (ImageView)findViewById(R.id.g4);
        ImageView nodeG5 = (ImageView)findViewById(R.id.g5);
        ImageView nodeG6 = (ImageView)findViewById(R.id.g6);
        ImageView nodeG7 = (ImageView)findViewById(R.id.g7);
        ImageView nodeG8 = (ImageView)findViewById(R.id.g8);
        ImageView nodeH1 = (ImageView)findViewById(R.id.h1);
        ImageView nodeH2 = (ImageView)findViewById(R.id.h2);
        ImageView nodeH3 = (ImageView)findViewById(R.id.h3);
        ImageView nodeH4 = (ImageView)findViewById(R.id.h4);
        ImageView nodeH5 = (ImageView)findViewById(R.id.h5);
        ImageView nodeH6 = (ImageView)findViewById(R.id.h6);
        ImageView nodeH7 = (ImageView)findViewById(R.id.h7);
        ImageView nodeH8 = (ImageView)findViewById(R.id.h8);

        /* NODE MAP */
        ImageView[][] nodeMap = new ImageView[8][8];
        // ROW 8
        nodeMap[0][0] = nodeA8;
        nodeMap[0][1] = nodeB8;
        nodeMap[0][2] = nodeC8;
        nodeMap[0][3] = nodeD8;
        nodeMap[0][4] = nodeE8;
        nodeMap[0][5] = nodeF8;
        nodeMap[0][6] = nodeG8;
        nodeMap[0][7] = nodeH8;
        // ROW 7
        nodeMap[1][0] = nodeA7;
        nodeMap[1][1] = nodeB7;
        nodeMap[1][2] = nodeC7;
        nodeMap[1][3] = nodeD7;
        nodeMap[1][4] = nodeE7;
        nodeMap[1][5] = nodeF7;
        nodeMap[1][6] = nodeG7;
        nodeMap[1][7] = nodeH7;
        // ROW 6
        nodeMap[2][0] = nodeA6;
        nodeMap[2][1] = nodeB6;
        nodeMap[2][2] = nodeC6;
        nodeMap[2][3] = nodeD6;
        nodeMap[2][4] = nodeE6;
        nodeMap[2][5] = nodeF6;
        nodeMap[2][6] = nodeG6;
        nodeMap[2][7] = nodeH6;
        // ROW 5
        nodeMap[3][0] = nodeA5;
        nodeMap[3][1] = nodeB5;
        nodeMap[3][2] = nodeC5;
        nodeMap[3][3] = nodeD5;
        nodeMap[3][4] = nodeE5;
        nodeMap[3][5] = nodeF5;
        nodeMap[3][6] = nodeG5;
        nodeMap[3][7] = nodeH5;
        // ROW 4
        nodeMap[4][0] = nodeA4;
        nodeMap[4][1] = nodeB4;
        nodeMap[4][2] = nodeC4;
        nodeMap[4][3] = nodeD4;
        nodeMap[4][4] = nodeE4;
        nodeMap[4][5] = nodeF4;
        nodeMap[4][6] = nodeG4;
        nodeMap[4][7] = nodeH4;
        // ROW 3
        nodeMap[5][0] = nodeA3;
        nodeMap[5][1] = nodeB3;
        nodeMap[5][2] = nodeC3;
        nodeMap[5][3] = nodeD3;
        nodeMap[5][4] = nodeE3;
        nodeMap[5][5] = nodeF3;
        nodeMap[5][6] = nodeG3;
        nodeMap[5][7] = nodeH3;
        // ROW 2
        nodeMap[6][0] = nodeA2;
        nodeMap[6][1] = nodeB2;
        nodeMap[6][2] = nodeC2;
        nodeMap[6][3] = nodeD2;
        nodeMap[6][4] = nodeE2;
        nodeMap[6][5] = nodeF2;
        nodeMap[6][6] = nodeG2;
        nodeMap[6][7] = nodeH2;
        // ROW 1
        nodeMap[7][0] = nodeA1;
        nodeMap[7][1] = nodeB1;
        nodeMap[7][2] = nodeC1;
        nodeMap[7][3] = nodeD1;
        nodeMap[7][4] = nodeE1;
        nodeMap[7][5] = nodeF1;
        nodeMap[7][6] = nodeG1;
        nodeMap[7][7] = nodeH1;

        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(chessboard[i][j].equals("bR")) // BLACK PIECES
                {
                    if((i+j) % 2 == 0)
                    {
                        nodeMap[i][j].setImageResource(R.drawable.blackrook_darkorange);
                    } else {
                        nodeMap[i][j].setImageResource(R.drawable.blackrook_lightorange);
                    }
                } else if(chessboard[i][j].equals("bK"))
                {
                    if((i+j) % 2 == 0)
                    {
                        nodeMap[i][j].setImageResource(R.drawable.blackking_darkorange);
                    } else {
                        nodeMap[i][j].setImageResource(R.drawable.blackking_lightorange);
                    }
                } else if(chessboard[i][j].equals("bB")) {
                    if((i+j) % 2 == 0)
                    {
                        nodeMap[i][j].setImageResource(R.drawable.blackbishop_darkorange);
                    } else {
                        nodeMap[i][j].setImageResource(R.drawable.blackbishop_lightorange);
                    }
                } else if(chessboard[i][j].equals("bN")) {
                    if((i+j) % 2 == 0)
                    {
                        nodeMap[i][j].setImageResource(R.drawable.blackknight_darkorange);
                    } else {
                        nodeMap[i][j].setImageResource(R.drawable.blackknight_lightorange);
                    }
                } else if(chessboard[i][j].equals("bQ")) {
                    if((i+j) % 2 == 0)
                    {
                        nodeMap[i][j].setImageResource(R.drawable.blackqueen_darkorange);
                    } else {
                        nodeMap[i][j].setImageResource(R.drawable.blackqueen_lightorange);
                    }
                } else if(chessboard[i][j].equals("bp")) {
                    if((i+j) % 2 == 0)
                    {
                        nodeMap[i][j].setImageResource(R.drawable.blackpawn_darkorange);
                    } else {
                        nodeMap[i][j].setImageResource(R.drawable.blackpawn_lightorange);
                    }
                } else if(chessboard[i][j].equals("wR")) // WHITE PIECES
                {
                    if((i+j) % 2 == 0)
                    {
                        nodeMap[i][j].setImageResource(R.drawable.whiterook_darkorange);
                    } else {
                        nodeMap[i][j].setImageResource(R.drawable.whiterook_lightorange);
                    }
                } else if(chessboard[i][j].equals("wK"))
                {
                    if((i+j) % 2 == 0)
                    {
                        nodeMap[i][j].setImageResource(R.drawable.whiteking_darkorange);
                    } else {
                        nodeMap[i][j].setImageResource(R.drawable.whiteking_lightorange);
                    }
                } else if(chessboard[i][j].equals("wB")) {
                    if((i+j) % 2 == 0)
                    {
                        nodeMap[i][j].setImageResource(R.drawable.whitebishop_darkorange);
                    } else {
                        nodeMap[i][j].setImageResource(R.drawable.whitebishop_lightorange);
                    }
                } else if(chessboard[i][j].equals("wN")) {
                    if((i+j) % 2 == 0)
                    {
                        nodeMap[i][j].setImageResource(R.drawable.whiteknight_darkorange);
                    } else {
                        nodeMap[i][j].setImageResource(R.drawable.whiteknight_lightorange);
                    }
                } else if(chessboard[i][j].equals("wQ")) {
                    if((i+j) % 2 == 0)
                    {
                        nodeMap[i][j].setImageResource(R.drawable.whitequeen_darkorange);
                    } else {
                        nodeMap[i][j].setImageResource(R.drawable.whitequeen_lightorange);
                    }
                } else if(chessboard[i][j].equals("wp")) {
                    if((i+j) % 2 == 0)
                    {
                        nodeMap[i][j].setImageResource(R.drawable.whitepawn_darkorange);
                    } else {
                        nodeMap[i][j].setImageResource(R.drawable.whitepawn_lightorange);
                    }
                } else {
                    if((i+j) % 2 == 0)
                    {
                        nodeMap[i][j].setImageResource(R.drawable.blank_darkorange);
                    } else {
                        nodeMap[i][j].setImageResource(R.drawable.blank_lightorange);
                    }
                }
            }
        }
        //nodeA1.setImageResource(R.drawable.whiterook);
    }

    /**
     *
     * @param x The x value of the designated node.
     * @param y The y value of the designated node.
     * @return Returns the command to be used for when a move is to be made.
     */
    public String nodenumToCommand(int x, int y)
    {
        String finalString = "";
        String first = "";
        String second = "";
        String[] letters = new String[8];
        letters[0] = "a";
        letters[1] = "b";
        letters[2] = "c";
        letters[3] = "d";
        letters[4] = "e";
        letters[5] = "f";
        letters[6] = "g";
        letters[7] = "h";
        String[] numbers = new String[8];
        numbers[0] = "8";
        numbers[1] = "7";
        numbers[2] = "6";
        numbers[3] = "5";
        numbers[4] = "4";
        numbers[5] = "3";
        numbers[6] = "2";
        numbers[7] = "1";
        if(x == 0)
        {
            first = "a";
        } else if(x == 1)
        {
            first = "b";
        } else if(x == 2)
        {
            first = "c";
        } else if(x == 3)
        {
            first = "d";
        } else if(x == 4)
        {
            first = "e";
        } else if(x == 5)
        {
            first = "f";
        } else if(x == 6)
        {
            first = "g";
        } else
        {
            first = "h";
        }

        if(y == 0)
        {
            second = "1";
        } else if(y == 1)
        {
            second = "2";
        } else if(y == 2)
        {
            second = "3";
        } else if(y == 3)
        {
            second = "4";
        } else if(y == 4)
        {
            second = "5";
        } else if(y == 5)
        {
            second = "6";
        } else if(y == 6)
        {
            second = "7";
        } else
        {
            second = "8";
        }
        finalString = first + second;
        return finalString;
    }

    /**
     * This method converts user input into indexes that Java arrays can easily read.
     * @param command A position that is converted into integer indexes that can be easily read by Java arrays.
     * @param mode Two modes exist for this method. When mode = 1, the method converts x coordinates. When mode = 2, the method converts y coordinates.
     * @return Returns an integer.
     */
    public int convertCommand(String command, int mode)
    {
        int nodeNum = -1;
        //System.out.println("testing "+command);
        if(mode == 1) /* x mode */
        {
            if(command.charAt(0) == 'a')
            {
                nodeNum = 0;
            } else if(command.charAt(0) == 'b')
            {
                nodeNum = 1;
            } else if(command.charAt(0) == 'c')
            {
                nodeNum = 2;
            } else if(command.charAt(0) == 'd')
            {
                nodeNum = 3;
            } else if(command.charAt(0) == 'e')
            {
                nodeNum = 4;
            } else if(command.charAt(0) == 'f')
            {
                nodeNum = 5;
            } else if(command.charAt(0) == 'g')
            {
                nodeNum = 6;
            } else if(command.charAt(0) == 'h')
            {
                nodeNum = 7;
            } else {
                nodeNum = -1;
            }
        } else if (mode == 2) /* y mode */
        {
            if(command.charAt(1) == '8')
            {
                nodeNum = 0;
            } else if(command.charAt(1) == '7')
            {
                nodeNum = 1;
            } else if(command.charAt(1) == '6')
            {
                nodeNum = 2;
            } else if(command.charAt(1) == '5')
            {
                nodeNum = 3;
            } else if(command.charAt(1) == '4')
            {
                nodeNum = 4;
            } else if(command.charAt(1) == '3')
            {
                nodeNum = 5;
            } else if(command.charAt(1) == '2')
            {
                nodeNum = 6;
            } else if(command.charAt(1) == '1')
            {
                nodeNum = 7;
            } else {
                nodeNum = -1;
            }
        } else { /* unknown mode */
            nodeNum = -1;
        }
        //System.out.println(nodeNum);
        return nodeNum;
    }

    public void setDraw()
    {
        if(!drawButtonBool)
        {
            drawButtonBool = true;
            AlertDialog restart = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Draw")
                    .setMessage(phase+" has declared Draw")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        } else {
            showErrorMessage("You have already declared a draw.");
        }
    }

    public void drawDecision()
    {
        String temp = "";
        if(phase.equals("Black"))
        {
            temp = "White";
        } else {
            temp = "Black";
        }
        AlertDialog restart = new AlertDialog.Builder(MainActivity.this)
                .setTitle(temp+" has declared a draw")
                .setMessage(phase+", would you like to call the game a draw?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        totalInput = "draw";
                        playerLoop();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        drawButtonBool = false;
                        isDraw = false;
                        if(phase.equals("Black"))
                        {
                            showErrorMessage(phase+" has denied the Draw request, White's turn.");
                            phase = "White";
                        } else {
                            showErrorMessage(phase+" has denied the Draw request, Black's turn.");
                            phase = "Black";
                        }
                        //phase = temp;
                        return;
                    }
                })
                .show();
    }

    /**
     * This method checks whether or not the two parameter user inputs constitutes a legal move.
     * @param x1Node x-coordinate of the first point
     * @param y1Node y-coordinate of the first point
     * @param x2Node x-coordinate of the second point
     * @param y2Node y-coordinate of the second point
     * @return Returns true or false, whether a move is considered legal or not.
     */
    public boolean checkLegality(int x1Node, int y1Node, int x2Node, int y2Node)
    {
        //System.out.println("x1 = "+x1Node+", y1 = "+y1Node+", x2 = "+x2Node+", y2 = "+y2Node);
        /* Check legality for Rooks */
        String src = chessboard[y1Node][x1Node];
        String dest = chessboard[y2Node][x2Node];
        if(src.equals("wp")) // WHITE PAWN
        {
            if(x1Node == x2Node)
            {
                if(y1Node == 6)
                {
                    if((y1Node - y2Node) == 2)
                    {
                        if(chessboard[y1Node-1][x1Node].equals("  ") && chessboard[y1Node-2][x1Node].equals("  "))
                        {
                            return true;
                        } else {
                            return false;
                        }
                    } else if(y1Node - y2Node == 1) {
                        if(chessboard[y1Node-1][x1Node].equals("  "))
                        {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    if(y1Node - y2Node == 1) {
                        if(chessboard[y1Node-1][x1Node].equals("  "))
                        {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                if((x1Node - 1) == x2Node || (x1Node + 1) == x2Node)
                {
                    if((y1Node - 1) == y2Node && !chessboard[y2Node][x2Node].equals("  "))
                    {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else if(src.equals("bp")) // BLACK PAWN
        {
            if(x1Node == x2Node)
            {
                if(y1Node == 1)
                {
                    if((y2Node - y1Node) == 2)
                    {
                        if(chessboard[y1Node+1][x1Node].equals("  ") && chessboard[y1Node+2][x1Node].equals("  "))
                        {
                            return true;
                        } else {
                            return false;
                        }
                    } else if(y2Node - y1Node == 1) {
                        if(chessboard[y1Node+1][x1Node].equals("  "))
                        {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    if(y2Node - y1Node == 1) {
                        if(chessboard[y1Node+1][x1Node].equals("  "))
                        {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                if((x1Node - 1) == x2Node || (x1Node + 1) == x2Node)
                {
                    if((y1Node + 1) == y2Node && !chessboard[y2Node][x2Node].equals("  "))
                    {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else if(src.charAt(1) == 'R') // BLACK & WHITE ROOK
        {
            if(chessboard[y2Node][x2Node].charAt(0) == chessboard[y1Node][x1Node].charAt(0))
            {
                return false;
            }
            if(x1Node == x2Node)
            {
                int temp1 = 0;
                int temp2 = 0;
                if(y1Node < y2Node)
                {
                    temp1 = y1Node;
                    temp2 = y2Node;
                } else {
                    temp1 = y2Node;
                    temp2 = y1Node;
                }
                if(temp2 - temp1 == 1)
                {
                    return true;
                } else {
                    for(int i = temp1+1; i < temp2; i++)
                    {
                        if(!chessboard[i][x1Node].equals("  "))
                        {
                            return false;
                        }
                    }
                    return true;
                }
            } else if (y1Node == y2Node)
            {
                int temp1 = 0;
                int temp2 = 0;
                if(x1Node < x2Node)
                {
                    temp1 = x1Node;
                    temp2 = x2Node;
                } else {
                    temp1 = x2Node;
                    temp2 = x1Node;
                }
                if(temp2 - temp1 == 1)
                {
                    return true;
                } else {
                    for(int i = temp1+1; i < temp2; i++)
                    {
                        if(!chessboard[y1Node][i].equals("  "))
                        {
                            return false;
                        }
                    }
                    return true;
                }
            } else {
                return false;
            }
        } else if(src.charAt(1) == 'B') // BLACK & WHITE BISHOP
        {
            if(chessboard[y2Node][x2Node].charAt(0) == chessboard[y1Node][x1Node].charAt(0))
            {
                return false;
            }
            int temp11, temp12, temp21, temp22, mode; // 1 means smaller, 2 means bigger
            if(x1Node < x2Node) // MOVE TO THE RIGHT
            {
                temp11 = x1Node;
                temp12 = x2Node;
                mode = 1;
            } else { // MOVE TO THE LEFT
                temp11 = x2Node;
                temp12 = x1Node;
                mode = 2;
            }
            if(y1Node < y2Node) // MOVE DOWNWARDS
            {
                temp21 = y1Node;
                temp22 = y2Node;
                if(mode == 1)
                {
                    mode = 4;
                } else if(mode == 2)
                {
                    mode = 3;
                }
            } else { // MOVE UPWARDS
                temp21 = y2Node;
                temp22 = y1Node;
            }
            int diff1 = temp12 - temp11;
            int diff2 = temp22 - temp21;
            if(diff1 < 0)
            {
                diff1 *= -1;
            }
            if(diff2 < 0)
            {
                diff2 *= -1;
            }
            if(diff1 == diff2) {
                if(mode == 1)
                {
                    //System.out.println("Bishop is moving top-right");
                    for (int i = 1; i < (diff1); i++)
                    {
                        if(!chessboard[y1Node-i][x1Node+i].equals("  "))
                        {
                            return false;
                        }
                    }
                } else if(mode == 2)
                {
                    //System.out.println("Bishop is moving top-left");
                    for (int i = 1; i < (diff1); i++)
                    {
                        if(!chessboard[y1Node-i][x1Node-i].equals("  "))
                        {
                            return false;
                        }
                    }
                } else if(mode == 3)
                {
                    //System.out.println("Bishop is moving bottom-left");
                    for (int i = 1; i < (diff1); i++)
                    {
                        if(!chessboard[y1Node+i][x1Node-i].equals("  "))
                        {
                            return false;
                        }
                    }
                } else if(mode == 4)
                {
                    //System.out.println("Bishop is moving bottom-right");
                    for (int i = 1; i < (diff1); i++)
                    {
                        if(!chessboard[y1Node+i][x1Node+i].equals("  "))
                        {
                            return false;
                        }
                    }
                }
            } else {
                return false;
            }
            return true;
        } else if(src.charAt(1) == 'N') // BLACK & WHITE KNIGHT
        {
            if(chessboard[y2Node][x2Node].charAt(0) == chessboard[y1Node][x1Node].charAt(0))
            {
                return false;
            }
            int temp11, temp12, temp21, temp22, mode;
            if(x1Node < x2Node)
            {
                temp11 = x1Node;
                temp12 = x2Node;
                mode = 0;
            } else {
                temp11 = x2Node;
                temp12 = x1Node;
                mode = 1;
            }
            if(y1Node < y2Node)
            {
                temp21 = y1Node;
                temp22 = y2Node;
                mode += 1;
            } else {
                temp21 = y2Node;
                temp22 = y1Node;
                mode += 2;
            }
            if(temp12-temp11 > 2)
            {
                return false;
            }
            if(temp22-temp21 > 2)
            {
                return false;
            }
            if((temp12-temp11)+(temp22-temp21) != 3)
            {
                return false;
            }
            boolean legal1 = true;
            boolean legal2 = true;

            if(!(legal1 && legal2))
            {
                return false;
            }
            return true;
        } else if(src.charAt(1) == 'Q') // BLACK & WHITE QUEEN
        {
            if(chessboard[y2Node][x2Node].charAt(0) == chessboard[y1Node][x1Node].charAt(0))
            {
                return false;
            }
            if(x1Node == x2Node)
            {
                int temp1 = 0;
                int temp2 = 0;
                if(y1Node < y2Node)
                {
                    temp1 = y1Node;
                    temp2 = y2Node;
                } else {
                    temp1 = y2Node;
                    temp2 = y1Node;
                }
                if(temp2 - temp1 == 1)
                {
                    return true;
                } else {
                    for(int i = temp1+1; i < temp2; i++)
                    {
                        if(!chessboard[i][x1Node].equals("  "))
                        {
                            return false;
                        }
                    }
                    return true;
                }
            } else if (y1Node == y2Node)
            {
                int temp1 = 0;
                int temp2 = 0;
                if(x1Node < x2Node)
                {
                    temp1 = x1Node;
                    temp2 = x2Node;
                } else {
                    temp1 = x2Node;
                    temp2 = x1Node;
                }
                if(temp2 - temp1 == 1)
                {
                    return true;
                } else {
                    for(int i = temp1+1; i < temp2; i++)
                    {
                        if(!chessboard[y1Node][i].equals("  "))
                        {
                            return false;
                        }
                    }
                    return true;
                }
            } else {
                if(chessboard[y2Node][x2Node].charAt(0) == chessboard[y1Node][x1Node].charAt(0))
                {
                    return false;
                }
                int temp11, temp12, temp21, temp22, mode; // 1 means smaller, 2 means bigger
                if(x1Node < x2Node) // MOVE TO THE RIGHT
                {
                    temp11 = x1Node;
                    temp12 = x2Node;
                    mode = 1;
                } else { // MOVE TO THE LEFT
                    temp11 = x2Node;
                    temp12 = x1Node;
                    mode = 2;
                }
                if(y1Node < y2Node) // MOVE DOWNWARDS
                {
                    temp21 = y1Node;
                    temp22 = y2Node;
                    if(mode == 1)
                    {
                        mode = 4;
                    } else if(mode == 2)
                    {
                        mode = 3;
                    }
                } else { // MOVE UPWARDS
                    temp21 = y2Node;
                    temp22 = y1Node;
                }
                int diff1 = temp12 - temp11;
                int diff2 = temp22 - temp21;
                if(diff1 < 0)
                {
                    diff1 *= -1;
                }
                if(diff2 < 0)
                {
                    diff2 *= -1;
                }
                if(diff1 == diff2) {
                    if(mode == 1)
                    {
                        //System.out.println("Queen is moving top-right");
                        for (int i = 1; i < (diff1); i++)
                        {
                            if(!chessboard[y1Node-i][x1Node+i].equals("  "))
                            {
                                return false;
                            }
                        }
                    } else if(mode == 2)
                    {
                        //System.out.println("Queen is moving top-left");
                        for (int i = 1; i < (diff1); i++)
                        {
                            if(!chessboard[y1Node-i][x1Node-i].equals("  "))
                            {
                                return false;
                            }
                        }
                    } else if(mode == 3)
                    {
                        //System.out.println("Queen is moving bottom-left");
                        for (int i = 1; i < (diff1); i++)
                        {
                            if(!chessboard[y1Node+i][x1Node-i].equals("  "))
                            {
                                return false;
                            }
                        }
                    } else if(mode == 4)
                    {
                        //System.out.println("Queen is moving bottom-right");
                        for (int i = 1; i < (diff1); i++)
                        {
                            if(!chessboard[y1Node+i][x1Node+i].equals("  "))
                            {
                                return false;
                            }
                        }
                    }
                } else {
                    return false;
                }
            }
            return true;
        } else if(src.charAt(1) == 'K') // BLACK & WHITE KING
        {
            if(chessboard[y2Node][x2Node].charAt(0) == chessboard[y1Node][x1Node].charAt(0))
            {
                return false;
            }
            if((x1Node - x2Node) < 2 && (x1Node - x2Node) > -2 && (y1Node - y2Node) < 2 && (y1Node - y2Node) > -2)
            {

            } else {
                if(phase.equals("White") && y1Node == y2Node && y2Node == 7) // WHITE KING CASTLING
                {
                    if(x1Node - x2Node == 2 && castleMoves[0] == 0 && castleMoves[1] == 0)
                    {
                        for(int i = 0; i < 5; i++)
                        {
                            if(checkKing(i,7,"White"))
                            {
                                kingInCheck = true;
                                return false;
                            }
                        }
                        for(int i = 1; i < 4; i++)
                        {
                            if(!chessboard[7][i].equals("  "))
                            {
                                return false;
                            }
                        }
                        chessboard[7][3] = "wR";
                        chessboard[7][0] = "  ";
                        castleMoves[0] = 1;
                        castleMoves[1] = 1;
                        return true;
                    } else if(x1Node - x2Node == -2 && castleMoves[0] == 0 && castleMoves[2] == 0)
                    {
                        for(int i = 4; i < 8; i++)
                        {
                            if(checkKing(i,7,"White"))
                            {
                                kingInCheck = true;
                                return false;
                            }
                        }
                        for(int i = 5; i < 7; i++)
                        {
                            if(!chessboard[7][i].equals("  "))
                            {
                                return false;
                            }
                        }
                        chessboard[7][5] = "wR";
                        chessboard[7][7] = "  ";
                        castleMoves[0] = 1;
                        castleMoves[2] = 1;
                        return true;
                    }
                } else if(phase.equals("Black")) // BLACK KING CASTLING
                {
                    if(x1Node - x2Node == 2 && y1Node == y2Node && y2Node == 0 && castleMoves[3] == 0 && castleMoves[4] == 0)
                    {
                        for(int i = 0; i < 5; i++)
                        {
                            if(checkKing(i,0,"Black"))
                            {
                                return false;
                            }
                        }
                        for(int i = 1; i < 4; i++)
                        {
                            if(!chessboard[0][i].equals("  "))
                            {
                                return false;
                            }
                        }
                        chessboard[0][3] = "bR";
                        chessboard[0][0] = "  ";
                        castleMoves[3] = 1;
                        castleMoves[4] = 1;
                        return true;
                    } else if(x1Node - x2Node == -2 && castleMoves[3] == 0 && castleMoves[5] == 0)
                    {
                        for(int i = 4; i < 8; i++)
                        {
                            if(checkKing(i,0,"Black"))
                            {
                                return false;
                            }
                        }
                        for(int i = 5; i < 7; i++)
                        {
                            if(!chessboard[0][i].equals("  "))
                            {
                                return false;
                            }
                        }
                        chessboard[0][5] = "bR";
                        chessboard[0][7] = "  ";
                        castleMoves[3] = 1;
                        castleMoves[5] = 1;
                        return true;
                    }
                }
                return false;
            }
            if(checkKing(x2Node,y2Node,phase))
            {
                kingInCheck = true;
                return false;
            }
        }
        return true;
    }

    /**
     * This method is responsible for placing a check on the King.
     * Called whenever a King moves or at the beginning of each turn.
     * @param xPos x-coordinate of the point to be tested for a possible Check
     * @param yPos y-coordinate of the point to be tested for a possible Check
     * @param phase Either "White" or "Black", depending on which player's King is being checked
     * @return Returns true or false, depending on whether or not a player's King is in Check.
     */
    public boolean checkKing(int xPos, int yPos, String phase)
    {
        String oppPiece = "";
        // CHECK FOR PAWNS
        if(phase.equals("White"))
        {
            oppPiece = "bp";
            if(yPos-1 > -1 && yPos-1 < 8 && xPos-1 > -1 && xPos-1 < 8)
            {
                if(chessboard[yPos-1][xPos-1].equals(oppPiece))
                {
                    return true;
                }
            }
            if(yPos-1 > -1 && yPos-1 < 8 && xPos+1 > -1 && xPos+1 < 8)
            {
                if(chessboard[yPos-1][xPos+1].equals(oppPiece))
                {
                    return true;
                }
            }
        } else {
            oppPiece = "wp";
            if(yPos+1 > -1 && yPos+1 < 8 && xPos-1 > -1 && xPos-1 < 8)
            {
                if(chessboard[yPos+1][xPos-1].equals(oppPiece))
                {
                    return true;
                }
            }
            if(yPos+1 > -1 && yPos+1 < 8 && xPos+1 > -1 && xPos+1 < 8)
            {
                if(chessboard[yPos+1][xPos+1].equals(oppPiece))
                {
                    return true;
                }
            }
        }
        // CHECK FOR KNIGHTS
        if(phase.equals("White"))
        {
            oppPiece = "bN";
        } else {
            oppPiece = "wN";
        }
        if(yPos+2 < 8 && yPos+2 > -1 && xPos-1 > -1 && xPos-1 < 8)
        {
            if(chessboard[yPos+2][xPos-1].equals(oppPiece))
            {
                return true;
            }
        }
        if(yPos+2 < 8 && yPos+2 > -1 && xPos+1 > -1 && xPos+1 < 8)
        {
            if(chessboard[yPos+2][xPos+1].equals(oppPiece))
            {
                return true;
            }
        }
        if(yPos-2 < 8 && yPos-2 > -1 && xPos+1 > -1 && xPos+1 < 8)
        {
            if(chessboard[yPos-2][xPos+1].equals(oppPiece))
            {
                return true;
            }
        }
        if(yPos-2 < 8 && yPos-2 > -1 && xPos-1 > -1 && xPos-1 < 8)
        {
            if(chessboard[yPos-2][xPos-1].equals(oppPiece))
            {
                return true;
            }
        }
        if(yPos-1 < 8 && yPos-1 > -1 && xPos-2 > -1 && xPos-2 < 8)
        {
            if(chessboard[yPos-1][xPos-2].equals(oppPiece))
            {
                return true;
            }
        }
        if(yPos-1 < 8 && yPos-1 > -1 && xPos+2 > -1 && xPos+2 < 8)
        {
            if(chessboard[yPos-1][xPos+2].equals(oppPiece))
            {
                return true;
            }
        }
        if(yPos+1 < 8 && yPos+1 > -1 && xPos+2 > -1 && xPos+2 < 8)
        {
            if(chessboard[yPos+1][xPos+2].equals(oppPiece))
            {
                return true;
            }
        }
        if(yPos+1 < 8 && yPos+1 > -1 && xPos-2 > -1 && xPos-2 < 8)
        {
            if(chessboard[yPos+1][xPos-2].equals(oppPiece))
            {
                return true;
            }
        }
        // CHECK FOR BISHOPS AND QUEENS
        //System.out.println("CHECKING QUEENS");
        int tempX = xPos;
        int tempY = yPos;
        int count = 1;
        String oppPiece2 = "";
        // top-right diagonal check
        while((tempX+count) <= 7 && (tempX+count) >= 0 && (tempY-count) <= 7 && (tempY-count) >= 0)
        {
            if(phase.equals("White"))
            {
                oppPiece = "bB";
                oppPiece2 = "bQ";
            } else {
                oppPiece = "wB";
                oppPiece2 = "wQ";
            }
            if(chessboard[yPos-count][xPos+count].equals(oppPiece) || chessboard[yPos-count][xPos+count].equals(oppPiece2))
            {
                return true;
            }
            if(!chessboard[yPos-count][xPos+count].equals("  "))
            {
                break;
            }
            count++;
        }
        // bottom-right diagonal check
        count = 1;
        while((tempX+count) <= 7 && (tempX+count) >= 0 && (tempY+count) <= 7 && (tempY+count) >= 0)
        {
            if(phase.equals("White"))
            {
                oppPiece = "bB";
                oppPiece2 = "bQ";
            } else {
                oppPiece = "wB";
                oppPiece2 = "wQ";
            }
            if(chessboard[yPos+count][xPos+count].equals(oppPiece) || chessboard[yPos+count][xPos+count].equals(oppPiece2))
            {
                return true;
            }
            if(!chessboard[yPos+count][xPos+count].equals("  "))
            {
                break;
            }
            count++;
        }
        // bottom-left diagonal check
        count = 1;
        while((tempX-count) <= 7 && (tempX-count) >= 0 && (tempY+count) <= 7 && (tempY+count) >= 0)
        {
            if(phase.equals("White"))
            {
                oppPiece = "bB";
                oppPiece2 = "bQ";
            } else {
                oppPiece = "wB";
                oppPiece2 = "wQ";
            }
            if(chessboard[yPos+count][xPos-count].equals(oppPiece) || chessboard[yPos+count][xPos-count].equals(oppPiece2))
            {
                return true;
            }
            if(!chessboard[yPos+count][xPos-count].equals("  "))
            {
                break;
            }
            count++;
        }
        // top-left diagonal check
        count = 1;
        while((tempX-count) <= 7 && (tempX-count) >= 0 && (tempY-count) <= 7 && (tempY-count) >= 0)
        {
            if(phase.equals("White"))
            {
                oppPiece = "bB";
                oppPiece2 = "bQ";
            } else {
                oppPiece = "wB";
                oppPiece2 = "wQ";
            }
            if(chessboard[yPos-count][xPos-count].equals(oppPiece) || chessboard[yPos-count][xPos-count].equals(oppPiece2))
            {
                return true;
            }
            if(!chessboard[yPos-count][xPos-count].equals("  "))
            {
                break;
            }
            count++;
        }
        // CHECK FOR ROOKS AND QUEENS
        tempX = xPos;
        tempY = yPos;
        count = 1;
        oppPiece = "";
        oppPiece2 = "";
        // right horizontal check
        while((tempX+count) <= 7 && (tempX+count) >= 0)
        {
            if(phase.equals("White"))
            {
                oppPiece = "bB";
                oppPiece2 = "bQ";
            } else {
                oppPiece = "wB";
                oppPiece2 = "wQ";
            }
            if(chessboard[yPos][xPos+count].equals(oppPiece) || chessboard[yPos][xPos+count].equals(oppPiece2))
            {
                return true;
            }
            if(!chessboard[yPos][xPos+count].equals("  "))
            {
                break;
            }
            count++;
        }
        // left horizontal check
        count = 1;
        while((tempX-count) <= 7 && (tempX-count) >= 0)
        {
            if(phase.equals("White"))
            {
                oppPiece = "bB";
                oppPiece2 = "bQ";
            } else {
                oppPiece = "wB";
                oppPiece2 = "wQ";
            }
            if(chessboard[yPos][xPos-count].equals(oppPiece) || chessboard[yPos][xPos-count].equals(oppPiece2))
            {
                return true;
            }
            if(!chessboard[yPos][xPos-count].equals("  "))
            {
                break;
            }
            count++;
        }
        // top vertical check
        count = 1;
        while((tempY-count) <= 7 && (tempY-count) >= 0)
        {
            if(phase.equals("White"))
            {
                oppPiece = "bB";
                oppPiece2 = "bQ";
            } else {
                oppPiece = "wB";
                oppPiece2 = "wQ";
            }
            if(chessboard[yPos-count][xPos].equals(oppPiece) || chessboard[yPos-count][xPos].equals(oppPiece2))
            {
                return true;
            }
            if(!chessboard[yPos-count][xPos].equals("  "))
            {
                break;
            }
            count++;
        }
        // bottom vertical check
        count = 1;
        while((tempY+count) <= 7 && (tempY+count) >= 0)
        {
            if(phase.equals("White"))
            {
                oppPiece = "bB";
                oppPiece2 = "bQ";
            } else {
                oppPiece = "wB";
                oppPiece2 = "wQ";
            }
            if(chessboard[yPos+count][xPos].equals(oppPiece) || chessboard[yPos+count][xPos].equals(oppPiece2))
            {
                return true;
            }
            if(!chessboard[yPos+count][xPos].equals("  "))
            {
                break;
            }
            count++;
        }
        // CHECK FOR KINGS
        if(phase.equals("White"))
        {
            oppPiece = "bK";
        } else {
            oppPiece = "wK";
        }
        for(int i = -1; i <= 1; i++)
        {
            for(int j = -1; j <= 1; j++)
            {
                if(yPos+i > -1 && yPos+i < 8 && xPos+j > -1 && xPos+j < 8) {
                    if(chessboard[yPos+i][xPos+j].equals(oppPiece))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method determines whether or not a player is in Checkmate.
     * A player that is in Checkmate loses the game.
     * @param playerTurn Either "White" or "Black", depending on who is checked for a possible Checkmate
     * @return Returns true or false. If true is returned, the playerTurn loses.
     */
    public boolean isCheckmate(String playerTurn)
    {
        char startingChar = 'a';
        if(playerTurn.equals("White"))
        {
            startingChar = 'w';
        } else {
            startingChar = 'b';
        }
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(chessboard[j][i].charAt(0) == startingChar)
                {
                    for(int k = 0; k < 8; k++)
                    {
                        for(int l = 0; l < 8; l++)
                        {
                            if(checkLegality(i,j,k,l))
                            {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * This method checks if a player is in Stalemate.
     * A Stalemate is determined if a King is not in Check, but has no other legal moves available. Thus, the match ends in a draw.
     * @param playerTurn Either "White" or "Black", depending on who is checked for a possible Stalemate
     * @return Returns true or false. If true, end the game in a draw.
     */
    public boolean isStalemate(String playerTurn)
    {
        char startingChar = 'a';
        if(playerTurn.equals("White"))
        {
            startingChar = 'w';
        } else {
            startingChar = 'b';
        }
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(chessboard[j][i].charAt(0) == startingChar)
                {
                    for(int k = 0; k < 8; k++)
                    {
                        for(int l = 0; l < 8; l++)
                        {
                            if(i != k && j != l)
                            {
                                if(checkLegality(i,j,k,l))
                                {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * This method is called each turn, alternating between white and black.
     */
    public void makeYourMove(String arg1, String arg2)
    {
        copyChessboard();
        undoUsed = true;
        String kingPiece = "";
        if(phase.equals("White"))
        {
            kingPiece = "wK";
        } else {
            kingPiece = "bK";
        }
        int xPos = 0;
        int yPos = 0;
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(chessboard[i][j].equals(kingPiece))
                {
                    xPos = j;
                    yPos = i;
                }
            }
        }
        //System.out.println(kingPiece + ": "+xPos+", "+yPos);
        if(isCheckmate(phase))
        {
            System.out.println("Checkmate");
            if(phase.equals("White"))
            {
                System.out.println("Black wins");
                showErrorMessage("Black wins");
            } else {
                System.out.println("White wins");
                showErrorMessage("White wins");
            }
            gameEnd = true;
            if(playbackMode)
            {
                showMessage("Press End Game to return back to Records Menu");
                Button nextMove = (Button)findViewById(R.id.restart_button);
                nextMove.setText("End Game");
            } else {
                gameOver();
            }
            return;
        }
        if(checkKing(xPos,yPos,phase))
        {
            System.out.println("Check");
        } else {
            if(isStalemate(phase))
            {
                System.out.println("Stalemate");
                System.out.println("draw");
                return;
            }
        }
        //sc.close();
        if(isDraw && arg1.equals("draw"))
        {
            System.out.println("draw");
            return;
        } else if(isDraw && !arg1.equals("draw")) {
            System.out.println();
            if(phase.equals("White"))
            {
                //phase = "Black";
                printChessboard();
            } else {
                //phase = "White";
                printChessboard();
            }
            return;
        }
        if(arg1.equals("resign"))
        {
            if(phase.equals("White"))
            {
                System.out.println("Black wins");
                gameEnd = true;
            } else {
                System.out.println("White wins");
                gameEnd = true;
            }
            if(playbackMode)
            {
                showMessage("Press End Game to return back to Records Menu");
                Button nextMove = (Button)findViewById(R.id.restart_button);
                nextMove.setText("End Game");
            } else {
                gameOver();
            }
            return;
        }
        String thirdArg = "";
        /*
        if(commandArr.length > 2)
        {
        	if(commandArr[2].equals("draw?"))
        	{
        		isDraw = true;
        		if(phase.equals("White"))
        		{
                    phase = "Black";
                    printChessboard();
        		} else {
                    phase = "White";
                    printChessboard();
        		}
                makeYourMove(phase);
        		return;
        	}
        	thirdArg = commandArr[2];
        }
        if(commandArr.length < 2)
        {
            System.out.println("Illegal move, try again");
            makeYourMove(phase);
            return;
        }
        if(commandArr[0].length() < 2)
        {
        	System.out.println("Illegal move, try again");
            makeYourMove(phase);
            return;
        }
        if(commandArr[1].length() < 2)
        {
        	System.out.println("Illegal move, try again");
            makeYourMove(phase);
            return;
        }
        */
        int x1Node = convertCommand(arg1,1);
        int y1Node = convertCommand(arg1,2);
        int x2Node = convertCommand(arg2,1);
        int y2Node = convertCommand(arg2,2);
        //System.out.println(chessboard[y1Node][x1Node]);
        //System.out.println(chessboard[y2Node][x2Node]);
        String src = "";
        String dest = "";
        if(x1Node == -1 || x2Node == -1 || y1Node == -1 || y2Node == -1)
        {
            System.out.println("Illegal move, try again");
            showErrorMessage("Illegal move, try again");
            isIllegal = true;
            return;
        }

        /* Legality checks */
        try
        {
            src = chessboard[y1Node][x1Node];
            dest = chessboard[y2Node][x2Node];
            if(phase.equals("White"))
            {
                if(src.charAt(0) != 'w')
                {
                    System.out.println("Illegal move, try again");
                    showErrorMessage("Illegal move, try again");
                    isIllegal = true;
                    return;
                }
            } else if(phase.equals("Black"))
            {
                if(src.charAt(0) != 'b')
                {
                    System.out.println("Illegal move, try again");
                    showErrorMessage("Illegal move, try again");
                    isIllegal = true;
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Illegal move, try again");
            showErrorMessage("Illegal move, try again");
            isIllegal = true;
            return;
        }
        int phaseAdder = 0;
        if(phase.equals("White"))
        {
            phaseAdder = 0;
        } else {
            phaseAdder = 6;
        }
        if(checkLegality(x1Node,y1Node,x2Node,y2Node))
        {
            //System.out.println("This is a legal move.");
            chessboard[y2Node][x2Node] = src;
            chessboard[y1Node][x1Node] = "  ";
        } else {

            System.out.println("Illegal move, try again");
            if(kingInCheck)
            {
                showErrorMessage("Illegal move, King is still in check");
            } else {
                showErrorMessage("Illegal move, try again");
            }
            isIllegal = true;
            return;


        }
        if(x1Node == 0 && y1Node == 7)
        {
            castleMoves[1] = 1;
        } else if(x1Node == 7 && y1Node == 7)
        {
            castleMoves[2] = 1;
        } else if(x1Node == 4 && y1Node == 7)
        {
            castleMoves[0] = 1;
        } else if(x1Node == 0 && y1Node == 0)
        {
            castleMoves[4] = 1;
        } else if(x1Node == 0 && y1Node == 0)
        {
            castleMoves[5] = 1;
        } else if(x1Node == 4 && y1Node == 0)
        {
            castleMoves[3] = 1;
        }
        // PROMOTION
        if(chessboard[y2Node][x2Node].equals("wp") && y2Node == 0)
        {
            if(thirdArg.equals("K"))
            {
                chessboard[y2Node][x2Node] = "wK";
            } else if(thirdArg.equals("N"))
            {
                chessboard[y2Node][x2Node] = "wN";
            } else if(thirdArg.equals("R"))
            {
                chessboard[y2Node][x2Node] = "wR";
            } else {
                chessboard[y2Node][x2Node] = "wQ";
            }
        }
        if(chessboard[y2Node][x2Node].equals("bp") && y2Node == 0)
        {
            if(thirdArg.equals("K"))
            {
                chessboard[y2Node][x2Node] = "bK";
            } else if(thirdArg.equals("N"))
            {
                chessboard[y2Node][x2Node] = "bN";
            } else if(thirdArg.equals("R"))
            {
                chessboard[y2Node][x2Node] = "bR";
            } else {
                chessboard[y2Node][x2Node] = "bQ";
            }
        }
        System.out.println();
        if(checkKing(xPos,yPos,phase))
        {
            revertChessboard();
            isIllegal = true;
            System.out.println("Illegal move, try again");
            showErrorMessage("Illegal move, King is still in check");
            printChessboard();
            return;
        }
        printChessboard();
        /*
        CHECK FOR OPPONENT'S CHECK
         */
        String tempPhase = "";
        String tempKing = "";
        int tempX = 0;
        int tempY = 0;
        if(phase.equals("Black"))
        {
            tempPhase = "White";
            tempKing = "wK";
        } else {
            tempPhase = "Black";
            tempKing = "bK";
        }
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(chessboard[i][j].equals(tempKing))
                {
                    tempX = j;
                    tempY = i;
                }
            }
        }
        if(checkKing(tempX,tempY,tempPhase))
        {
            System.out.println("Check");
            AlertDialog restart = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("WARNING!")
                    .setMessage(tempPhase+" is in Check")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        } else {
            if(isStalemate(tempPhase))
            {
                System.out.println("Stalemate");
                System.out.println("draw");
                return;
            }
        }
        return;
    }

    public void playerLoop()
    {
        gameOngoing = true;
        kingInCheck = false;
        System.out.print(phase+"'s move: ");
        String command = totalInput;
        String[] temp = command.split("\n");
        command = "";
        for(String s : temp)
        {
            if(command.equals(""))
            {
                command = command + s;
            } else {
                command = command + " " + s;
            }
        }
        //System.out.println(command);
        String[] commandArr = command.split(" ");
        ArrayList<String> commandArrList = new ArrayList<String>();
        for(String s : commandArr)
        {
            commandArrList.add(s);
        }
        while(commandArrList.size() > 0)
        {
            if(isDraw && commandArrList.get(0).equals("draw"))
            {
                gameEnd = true;
                showErrorMessage("Game over: Draw");
                return;
            }
            if(commandArrList.get(0).equals("undo") && !undoUsed)
            {
                revertChessboard();
                printChessboard();
                firstInput = "";
                secondInput = "";
                if(phase.equals("White"))
                {
                    phase = "Black";
                    showErrorMessage(phase+" would like to redo their move");
                } else {
                    phase = "White";
                    showErrorMessage(phase+" would like to redo their move");
                }
                EditText sourceText = (EditText)findViewById(R.id.sourceInput);
                sourceText.setText(phase+": ?? to ??");
                undoUsed = true;
                playback = playback + "undo\n";
                return;
            }
            if(commandArrList.get(0).equals("resign"))
            {
                if(phase.equals("White"))
                {
                    System.out.println("Black wins");
                    showMessage("Game over: Black wins");
                    gameEnd = true;
                } else {
                    System.out.println("White wins");
                    showMessage("Game over: White wins");
                    gameEnd = true;
                }
                playback = playback + command + "\n";
                System.out.println("Playback: "+playback);
                if(playbackMode)
                {
                    showMessage("Press End Game to return back to Records Menu");
                    Button nextMove = (Button)findViewById(R.id.restart_button);
                    nextMove.setText("End Game");
                } else {
                    gameOver();
                }
                return;
            }
            isIllegal = false;
            String arg1 = commandArrList.get(0);
            if(isDraw && commandArrList.get(0).equals("draw"))
            {
                gameEnd = true;
                System.out.println("draw");
                break;
            }
            if(commandArrList.size() < 2)
            {
                System.out.println("\nIllegal move, try again");
                showErrorMessage("Illegal move, try again");
                break;
            }
            String arg2 = commandArrList.get(1);
            if(commandArrList.size() > 2)
            {
                if(commandArrList.get(2).equals("draw?"))
                {
                    isDraw = true;
                }
            }
            makeYourMove(arg1, arg2);
            if(isIllegal)
            {
                break;
            }
            if(phase.equals("White"))
            {
                phase = "Black";
            } else {
                phase = "White";
            }
            if(isDraw)
            {
                for(int i = 0; i < 3; i++)
                {
                    commandArrList.remove(0);
                }
            } else {
                for(int i = 0; i < 2; i++)
                {
                    commandArrList.remove(0);
                }
            }
            if(commandArrList.size() > 0)
            {
                if(commandArrList.get(0).equals("resign"))
                {
                    if(phase.equals("White"))
                    {
                        System.out.println("Black wins");
                        showErrorMessage("Game over: Black wins");
                        gameEnd = true;
                    } else {
                        System.out.println("White wins");
                        showErrorMessage("Game over: White wins");
                        gameEnd = true;
                    }
                    playback = playback + command + "\n";
                    System.out.println("Playback: "+playback);
                    if(playbackMode)
                    {
                        showMessage("Press End Game to return back to Records Menu");
                        Button nextMove = (Button)findViewById(R.id.restart_button);
                        nextMove.setText("End Game");
                    } else {
                        gameOver();
                    }
                    return;
                }
            }
        }
        if(!isIllegal)
        {
            showErrorMessage(phase+"'s turn");
            playback = playback + command + "\n";
            System.out.println("Playback: "+playback);
            undoUsed = false;
        }
        if(isIllegal)
        {
            EditText sourceText = (EditText)findViewById(R.id.sourceInput);
            sourceText.setText(phase+": ?? to ??");
        }
        if(isDraw)
        {
            drawDecision();
        }
        /*
        if(!gameEnd)
        {
            playerLoop();
        }
        */

    }

    /*
    ANDROID STUFF
     */

    public void chooseMove(String input)
    {
        if(playbackMode)
        {
            return;
        }
        if(gameEnd)
        {
            showErrorMessage("Game is not in session. Please restart the game.");
        } else {
            EditText sourceText = (EditText)findViewById(R.id.sourceInput);
            if(firstInput.equals("")) {
                firstInput = input;
                secondInput = "";
                sourceText.setText(phase+": "+firstInput +" to ??");
            } else {
                secondInput = input;
                sourceText.setText(phase+": "+firstInput + " to " + secondInput);
                totalInput = firstInput.toLowerCase() + " " + secondInput.toLowerCase();
                System.out.println(totalInput);
                if(drawButtonBool)
                {
                    totalInput = totalInput + " draw?";
                }
                playerLoop();
                firstInput = "";
                secondInput = "";
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetChessboard();
        copyChessboard();
        printChessboard();
        /*
        printChessboard();
        playerLoop();
        */
        final EditText sourceText = (EditText)findViewById(R.id.sourceInput);
        sourceText.setText("White: ?? to ??");
        Button drawButton = (Button)findViewById(R.id.draw_button);
        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(playbackMode)
                {

                } else {
                    setDraw();
                }
            }
        });

        Button undoButton = (Button)findViewById(R.id.undo_button);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(playbackMode)
                {

                } else {
                    if(!gameOngoing)
                    {
                        System.out.println("Illegal move, try again");
                        showErrorMessage("Cannot undo on turn 1!");
                        return;
                    }
                    if(undoUsed)
                    {
                        System.out.println("Illegal move, try again");
                        showErrorMessage("Cannot undo further!");
                    } else {
                        totalInput = "undo";
                        playerLoop();
                    }
                }
            }
        });

        final Button playbackButton = (Button)findViewById(R.id.playback);
        playbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(playbackMode)
                {
                    AlertDialog restart = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Exit")
                            .setMessage("A game is currently being played. Would you like to exit?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    playbackMode = false;
                                    Button nextMove = (Button)findViewById(R.id.restart_button);
                                    nextMove.setText("Restart Game");
                                    Button drawButton = (Button)findViewById((R.id.draw_button));
                                    drawButton.setText("Draw");;
                                    Button aiButton = (Button)findViewById((R.id.button4));
                                    aiButton.setText("AI");;
                                    Button resignButton = (Button)findViewById(R.id.resign);
                                    resignButton.setText("Resign");
                                    Button undoButton = (Button)findViewById((R.id.undo_button));
                                    undoButton.setText("Undo");

                                    resetChessboard();
                                    printChessboard();
                                    playback = "";
                                    EditText sourceText = (EditText)findViewById(R.id.sourceInput);
                                    sourceText.setText("White: ?? to ??");
                                    phase = "White";
                                    drawButtonBool = false;
                                    isDraw = false;
                                    gameOngoing = false;
                                    gameEnd = false;
                                    openRecordActivity();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                } else {
                    openRecordActivity();
                }
            }
        });

        Button aiButton = (Button)findViewById(R.id.button4);
        aiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(playbackMode)
                {

                } else {
                    String kingPiece = "";
                    if(phase.equals("White"))
                    {
                        kingPiece = "wK";
                    } else {
                        kingPiece = "bK";
                    }
                    int xPos = 0;
                    int yPos = 0;
                    for(int i = 0; i < 8; i++)
                    {
                        for(int j = 0; j < 8; j++)
                        {
                            if(chessboard[i][j].equals(kingPiece))
                            {
                                xPos = j;
                                yPos = i;
                            }
                        }
                    }
                    if(checkKing(xPos,yPos,phase))
                    {
                        System.out.println("Illegal move, try again");
                        showErrorMessage("You're in check!");
                        return;
                    } else {
                        firstInput = "";
                        secondInput = "";
                        char tempChar = 'a';
                        if(phase.equals("Black"))
                        {
                            tempChar = 'b';
                        } else {
                            tempChar = 'w';
                        }
                        ArrayList<String> phasePieces = new ArrayList<String>();
                        ArrayList<String> phaseSpots = new ArrayList<String>();
                        for(int i = 0; i < 8; i++)
                        {
                            for(int j = 0; j < 8; j++)
                            {
                                if(chessboard[j][i].charAt(0) == tempChar)
                                {
                                    String temp = nodenumToCommand(i,j);
                                    phasePieces.add(chessboard[j][i]);
                                    phaseSpots.add(temp);
                                }
                            }
                        }
                        for(int i = 0; i < phasePieces.size(); i++)
                        {
                            System.out.print(phasePieces.get(i) + " ");
                        }
                        System.out.println();
                        //String randomPiece = phasePieces.get((int)(Math.random()*phasePieces.size()));
                        //System.out.println(randomPiece);
                        do {
                            int random = (int)(Math.random()*phasePieces.size());
                            String randomPiece = phasePieces.get(random);
                            String startPos = "";
                    /*
                    PIECE-BASED AI
                     */
                            int nodeX = convertCommand(phaseSpots.get(random),1);
                            int nodeY = convertCommand(phaseSpots.get(random),2);
                            int destX = 0;
                            int destY = 0;
                            System.out.println("Piece: "+randomPiece);
                            System.out.println("Node X: "+nodeX);
                            System.out.println("Node Y: "+nodeY);
                            System.out.println("Final Node: "+nodenumToCommand(nodeX,nodeY));

                            if(phasePieces.get(random).charAt(1) == 'p') // PAWN
                            {
                                int move = 0;
                                if(phase.equals("Black"))
                                {
                                    if(nodeY == 6)
                                    {
                                        move = (int)(Math.random()*2)+1;
                                    } else {
                                        move = 1;
                                    }
                                    destX = nodeX;
                                    destY = nodeY-move;
                                } else {
                                    if(nodeY == 1)
                                    {
                                        move = (int)(Math.random()*2)+1;
                                    } else {
                                        move = 1;
                                    }
                                    destX = nodeX;
                                    destY = nodeY+move;
                                }
                            } else if(phasePieces.get(random).charAt(1) == 'R')
                            {
                                int mode = (int)(Math.random()*2);
                                if(mode == 0)
                                {
                                    destX = (int)(Math.random()*8);
                                    destY = nodeY;
                                } else {
                                    destX = nodeX;
                                    destY = (int)(Math.random()*8);
                                }
                            } else if(phasePieces.get(random).charAt(1) == 'N')
                            {
                                int mode = (int)(Math.random()*8);
                                if(mode == 0)
                                {
                                    destX = nodeX + 2;
                                    destY = nodeY + 1;
                                } else if(mode == 1)
                                {
                                    destX = nodeX + 1;
                                    destY = nodeY + 2;
                                } else if(mode == 2)
                                {
                                    destX = nodeX - 1;
                                    destY = nodeY + 2;
                                } else if(mode == 3)
                                {
                                    destX = nodeX - 2;
                                    destY = nodeY + 1;
                                } else if(mode == 4)
                                {
                                    destX = nodeX + 1;
                                    destY = nodeY - 2;
                                } else if(mode == 5)
                                {
                                    destX = nodeX + 2;
                                    destY = nodeY - 1;
                                } else if(mode == 6)
                                {
                                    destX = nodeX - 1;
                                    destY = nodeY - 2;
                                } else if(mode == 7)
                                {
                                    destX = nodeX - 2;
                                    destY = nodeY - 1;
                                }
                            } else if(phasePieces.get(random).charAt(1) == 'B')
                            {
                                int rando = (int)(Math.random()*8);
                                int move = rando-nodeX;
                                destX = nodeX + move;
                                destY = nodeY + move;
                            } else if(phasePieces.get(random).charAt(1) == 'K')
                            {
                                int move = (int)(Math.random()*3);
                                destX = nodeX - 1 + move;
                                destY = nodeY - 1 + move;
                            } else if(phasePieces.get(random).charAt(1) == 'Q')
                            {
                                int mode = (int)(Math.random()*4);
                                if(mode == 0)
                                {
                                    destX = (int)(Math.random()*8);
                                    destY = nodeY;
                                } else if(mode == 1) {
                                    destX = nodeX;
                                    destY = (int)(Math.random()*8);
                                } else {
                                    int rando = (int)(Math.random()*8);
                                    int move = rando-nodeX;
                                    destX = nodeX + move;
                                    destY = nodeY + move;
                                }
                            }

                            //secondInput = letters[(int)(Math.random()*8)]+numbers[(int)(Math.random()*8)];
                            totalInput = nodenumToCommand(nodeX,nodeY) + " " + nodenumToCommand(destX,destY);
                            System.out.println(totalInput);
                            EditText sourceText = (EditText)findViewById(R.id.sourceInput);
                            sourceText.setText(phase+": "+nodenumToCommand(nodeX,nodeY).toUpperCase() + " to " + nodenumToCommand(destX,destY).toUpperCase());
                            playerLoop();
                        } while(isIllegal);
                    }
                }
            }
        });

        Button resignButton = (Button)findViewById(R.id.resign);
        resignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(playbackMode)
                {

                } else {
                    if(gameEnd)
                    {
                        showErrorMessage("Game is not in session. Please restart the game.");
                    } else {
                        sourceText.setText(phase+": resign");
                        totalInput = "resign";
                        System.out.println(totalInput);
                        playerLoop();
                    }
                }
            }
        });

        Button restartButton = (Button)findViewById(R.id.restart_button);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(playbackMode)
                {
                    playbackRecord();
                } else {
                    if(gameOngoing)
                    {
                        AlertDialog restart = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Restart Game")
                                .setMessage("Are you sure you want to restart the game?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        resetChessboard();
                                        playback = "";
                                        printChessboard();
                                        sourceText.setText("White: ?? to ??");
                                        phase = "White";
                                        drawButtonBool = false;
                                        isDraw = false;
                                        gameOngoing = false;
                                        gameEnd = false;
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .show();
                    } else {
                        showErrorMessage("You haven't started a game yet!");
                    }
                }
            }
        });

        ImageView nodeA1 = (ImageView)findViewById(R.id.a1);
        ImageView nodeA2 = (ImageView)findViewById(R.id.a2);
        ImageView nodeA3 = (ImageView)findViewById(R.id.a3);
        ImageView nodeA4 = (ImageView)findViewById(R.id.a4);
        ImageView nodeA5 = (ImageView)findViewById(R.id.a5);
        ImageView nodeA6 = (ImageView)findViewById(R.id.a6);
        ImageView nodeA7 = (ImageView)findViewById(R.id.a7);
        ImageView nodeA8 = (ImageView)findViewById(R.id.a8);
        ImageView nodeB1 = (ImageView)findViewById(R.id.b1);
        ImageView nodeB2 = (ImageView)findViewById(R.id.b2);
        ImageView nodeB3 = (ImageView)findViewById(R.id.b3);
        ImageView nodeB4 = (ImageView)findViewById(R.id.b4);
        ImageView nodeB5 = (ImageView)findViewById(R.id.b5);
        ImageView nodeB6 = (ImageView)findViewById(R.id.b6);
        ImageView nodeB7 = (ImageView)findViewById(R.id.b7);
        ImageView nodeB8 = (ImageView)findViewById(R.id.b8);
        ImageView nodeC1 = (ImageView)findViewById(R.id.c1);
        ImageView nodeC2 = (ImageView)findViewById(R.id.c2);
        ImageView nodeC3 = (ImageView)findViewById(R.id.c3);
        ImageView nodeC4 = (ImageView)findViewById(R.id.c4);
        ImageView nodeC5 = (ImageView)findViewById(R.id.c5);
        ImageView nodeC6 = (ImageView)findViewById(R.id.c6);
        ImageView nodeC7 = (ImageView)findViewById(R.id.c7);
        ImageView nodeC8 = (ImageView)findViewById(R.id.c8);
        ImageView nodeD1 = (ImageView)findViewById(R.id.d1);
        ImageView nodeD2 = (ImageView)findViewById(R.id.d2);
        ImageView nodeD3 = (ImageView)findViewById(R.id.d3);
        ImageView nodeD4 = (ImageView)findViewById(R.id.d4);
        ImageView nodeD5 = (ImageView)findViewById(R.id.d5);
        ImageView nodeD6 = (ImageView)findViewById(R.id.d6);
        ImageView nodeD7 = (ImageView)findViewById(R.id.d7);
        ImageView nodeD8 = (ImageView)findViewById(R.id.d8);
        ImageView nodeE1 = (ImageView)findViewById(R.id.e1);
        ImageView nodeE2 = (ImageView)findViewById(R.id.e2);
        ImageView nodeE3 = (ImageView)findViewById(R.id.e3);
        ImageView nodeE4 = (ImageView)findViewById(R.id.e4);
        ImageView nodeE5 = (ImageView)findViewById(R.id.e5);
        ImageView nodeE6 = (ImageView)findViewById(R.id.e6);
        ImageView nodeE7 = (ImageView)findViewById(R.id.e7);
        ImageView nodeE8 = (ImageView)findViewById(R.id.e8);
        ImageView nodeF1 = (ImageView)findViewById(R.id.f1);
        ImageView nodeF2 = (ImageView)findViewById(R.id.f2);
        ImageView nodeF3 = (ImageView)findViewById(R.id.f3);
        ImageView nodeF4 = (ImageView)findViewById(R.id.f4);
        ImageView nodeF5 = (ImageView)findViewById(R.id.f5);
        ImageView nodeF6 = (ImageView)findViewById(R.id.f6);
        ImageView nodeF7 = (ImageView)findViewById(R.id.f7);
        ImageView nodeF8 = (ImageView)findViewById(R.id.f8);
        ImageView nodeG1 = (ImageView)findViewById(R.id.g1);
        ImageView nodeG2 = (ImageView)findViewById(R.id.g2);
        ImageView nodeG3 = (ImageView)findViewById(R.id.g3);
        ImageView nodeG4 = (ImageView)findViewById(R.id.g4);
        ImageView nodeG5 = (ImageView)findViewById(R.id.g5);
        ImageView nodeG6 = (ImageView)findViewById(R.id.g6);
        ImageView nodeG7 = (ImageView)findViewById(R.id.g7);
        ImageView nodeG8 = (ImageView)findViewById(R.id.g8);
        ImageView nodeH1 = (ImageView)findViewById(R.id.h1);
        ImageView nodeH2 = (ImageView)findViewById(R.id.h2);
        ImageView nodeH3 = (ImageView)findViewById(R.id.h3);
        ImageView nodeH4 = (ImageView)findViewById(R.id.h4);
        ImageView nodeH5 = (ImageView)findViewById(R.id.h5);
        ImageView nodeH6 = (ImageView)findViewById(R.id.h6);
        ImageView nodeH7 = (ImageView)findViewById(R.id.h7);
        ImageView nodeH8 = (ImageView)findViewById(R.id.h8);

        nodeA1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                chooseMove("A1");
            }
        });
        nodeA2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                chooseMove("A2");
            }
        });
        nodeA3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                chooseMove("A3");
            }
        });
        nodeA4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                chooseMove("A4");
            }
        });
        nodeA5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                chooseMove("A5");
            }
        });
        nodeA6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                chooseMove("A6");
            }
        });
        nodeA7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                chooseMove("A7");
            }
        });
        nodeA8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                chooseMove("A8");
            }
        });
        nodeB1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("B1");
            }
        });
        nodeB2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("B2");
            }
        });
        nodeB3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("B3");
            }
        });
        nodeB4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("B4");
            }
        });
        nodeB5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("B5");
            }
        });
        nodeB6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("B6");
            }
        });
        nodeB7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("B7");
            }
        });
        nodeB8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("B8");
            }
        });
        nodeC1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("C1");
            }
        });
        nodeC2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("C2");
            }
        });
        nodeC3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("C3");
            }
        });
        nodeC4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("C4");
            }
        });
        nodeC5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("C5");
            }
        });
        nodeC6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("C6");
            }
        });
        nodeC7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("C7");
            }
        });
        nodeC8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("C8");
            }
        });
        nodeD1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("D1");
            }
        });
        nodeD2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("D2");
            }
        });
        nodeD3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("D3");
            }
        });
        nodeD4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("D4");
            }
        });
        nodeD5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("D5");
            }
        });
        nodeD6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("D6");
            }
        });
        nodeD7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("D7");
            }
        });
        nodeD8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("D8");
            }
        });
        nodeE1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("E1");
            }
        });
        nodeE2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("E2");
            }
        });
        nodeE3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("E3");
            }
        });
        nodeE4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("E4");
            }
        });
        nodeE5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("E5");
            }
        });
        nodeE6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("E6");
            }
        });
        nodeE7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("E7");
            }
        });
        nodeE8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("E8");
            }
        });
        nodeF1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("F1");
            }
        });
        nodeF2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("F2");
            }
        });
        nodeF3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("F3");
            }
        });
        nodeF4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("F4");
            }
        });
        nodeF5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("F5");
            }
        });
        nodeF6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("F6");
            }
        });
        nodeF7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("F7");
            }
        });
        nodeF8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("F8");
            }
        });
        nodeG1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("G1");
            }
        });
        nodeG2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("G2");
            }
        });
        nodeG3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("G3");
            }
        });
        nodeG4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("G4");
            }
        });
        nodeG5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("G5");
            }
        });
        nodeG6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("G6");
            }
        });
        nodeG7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("G7");
            }
        });
        nodeG8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("G8");
            }
        });
        nodeH1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("H1");
            }
        });
        nodeH2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("H2");
            }
        });
        nodeH3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("H3");
            }
        });
        nodeH4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("H4");
            }
        });
        nodeH5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("H5");
            }
        });
        nodeH6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("H6");
            }
        });
        nodeH7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("H7");
            }
        });
        nodeH8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseMove("H8");
            }
        });
    }

    public void showErrorMessage(String input)
    {
        if(!playbackMode)
        {
            Context context = getApplicationContext();
            CharSequence text = input;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void showMessage(String input)
    {
        Context context = getApplicationContext();
        CharSequence text = input;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void openRecordActivity()
    {
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }
}


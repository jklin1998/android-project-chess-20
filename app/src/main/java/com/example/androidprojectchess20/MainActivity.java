/**
 * @author Group 20: Jonathan Lin (jkl130) & Jason Cariaga (jmc803)
 * @version 1.0
 */
package com.example.androidprojectchess20;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //TextView mStartText = (TextView)findViewById(R.id.startText);
    String firstInput = "";
    String secondInput = "";
    String totalInput = "";
    String playback = "";

    /*
    PORTED CODE
     */
    public String[][] chessboard = new String[8][8];
    public String phase = "White";
    public int[] castleMoves = new int[6];
    public boolean isDraw = false;
    public boolean gameEnd = false;
    public boolean isIllegal = false;

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
        System.out.println("Hi");
        nodeA1.setImageResource(R.drawable.whiterook);
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

    /**
     * This method checks whether or not the two parameter user inputs constitutes a legal move.
     * @param x1Node x-coordinate of the first point
     * @param y1Node y-coordinate of the first point
     * @param x2Node x-coordinate of the second point
     * @param y2Node y-coordinate of the second point
     * @return Returns true or false, whether a move is considered level or not.
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
            if(y1Node < x2Node)
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
            } else {
                System.out.println("White wins");
            }
            gameEnd = true;
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
            } else {
                System.out.println("White wins");
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
                    isIllegal = true;
                    return;
                }
            } else if(phase.equals("Black"))
            {
                if(src.charAt(0) != 'b')
                {
                    System.out.println("Illegal move, try again");
                    isIllegal = true;
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Illegal move, try again");
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
        printChessboard();
        return;
    }

    public void playerLoop()
    {
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
            if(commandArrList.get(0).equals("resign"))
            {
                if(phase.equals("White"))
                {
                    System.out.println("Black wins");
                } else {
                    System.out.println("White wins");
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
                    } else {
                        System.out.println("White wins");
                    }
                    return;
                }
            }
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
        EditText sourceText = (EditText)findViewById(R.id.sourceInput);
        if(firstInput.equals("")) {
            firstInput = input;
            secondInput = "";
            sourceText.setText(firstInput +" to ??");
        } else {
            secondInput = input;
            sourceText.setText(firstInput + " to " + secondInput);
            totalInput = firstInput.toLowerCase() + " to " + secondInput.toLowerCase();
            playerLoop();
            firstInput = "";
            secondInput = "";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetChessboard();
        printChessboard();
        /*
        printChessboard();
        playerLoop();
        */
        final EditText sourceText = (EditText)findViewById(R.id.sourceInput);
        sourceText.setText("?? to ??");
        Button drawButton = (Button)findViewById(R.id.draw_button);
        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sourceText.setText("Hi");
            }
        });

        Button undoButton = (Button)findViewById(R.id.undo_button);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sourceText.setText("eat a dic");
            }
        });

        ImageView nodeA1 = (ImageView)findViewById(R.id.a1);
        nodeA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                chooseMove("A1");
            }
        });
    }

    public void playermove(){}

    public void undo(android.view.View temp){
        //mStartText.setText("Hi");
    }

    public void draw(android.view.View temp){
        System.out.println("Draw My Life");
    }

    public void resign(){}

    public void AImove(){}

    public void resetboard(){}

    public void chooseGame(){}



}


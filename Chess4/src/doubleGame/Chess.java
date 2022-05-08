package doubleGame;

import java.awt.*;

public class Chess {
    Chessboard cp; 	//棋盘
    int row;		//横坐标
    int col;		//纵坐标
    Color color;	//棋子颜色
    public static final int BANJING = 50;
    private static int dis = 62;
    private static int X = 130;
    private static int Y = 80;
    private boolean last = false; //是否最后一步棋子


    public Chess(Chessboard cp, int col, int row, Color color) {
        this.cp = cp;
        this.col = col;
        this.row = row;
        this.color = color;
    }

    //画棋子
    public void drawChess(Graphics g) {
        //定义棋子圆心
        int xPos = col * dis + X;
        int yPos = row * dis + Y;

        Graphics2D g2d = (Graphics2D) g;

        RadialGradientPaint paint = null;
        Color[] c = { Color.WHITE, Color.black };
        Color[] c2 = { Color.gray, Color.BLACK };
        float[] f = { 0f, 1f };
        int x = xPos + 3;
        int y = yPos - 3;
        if (color == Color.WHITE) {
            paint = new RadialGradientPaint(x, y, BANJING * 3, f, c);
        } else {
            paint = new RadialGradientPaint(x, y, BANJING, f, c2);
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(paint);
        g2d.fillOval(xPos - BANJING / 2, yPos - BANJING / 2, BANJING, BANJING);
        if(last){
            g.setColor(Color.red);
            g.drawRect(x-BANJING/2-5,y-BANJING/2,BANJING+3,BANJING+3);
            g.drawRect(x-BANJING/2-6,y-BANJING/2-1,BANJING+5,BANJING+5);

        }
    }


    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getCol() {
        return col;
    }
    public void setCol(int col) {
        this.col = col;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

}


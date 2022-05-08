package doubleGame;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class Chessboard extends JPanel {
    private static int dis = 62;//棋子间距
    private static int X = 130; //chess[0][0]的x，y坐标
    private static int Y = 80;

    Chess[] chessList = new Chess[15 * 15];
    public Pointer[][] pointers = new Pointer[15][15]; // 创建指示器的二维数组
    int chessCount = 0;//棋子数
    boolean iso = false;
    boolean isBlack = true;
    String message = "黑棋先下";

    public Chessboard() {
        //创建指示器
        createPointer();
        //创建鼠标移动
        createMouseListener();
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                if (iso) {
                    return;
                }
                int col, row;
                col = (e.getX() - X + dis/2) / dis;
                row = (e.getY() - Y + dis/2) / dis;

                if (col > 15 || col < 0 || row > 15 || row < 0) {
                    return;
                } else {
                    if (HasChess(col, row)) {
                        return;
                    } else {
                        Color c = Color.BLACK;
                        if (isBlack) {
                            c = Color.BLACK;
                            message = "轮到白棋";
                        } else {
                            c = Color.WHITE;
                            message = "轮到黑棋";
                        }
                        Chess cc = new Chess(Chessboard.this, col, row, c);
                        cc.setLast(true);
                        ClearLast();
                        chessList[chessCount++] = cc;

                        repaint();

                        if (isWin(col, row)) {
                            UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("楷体", Font.ITALIC, 40)));
                            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("楷体", Font.ITALIC, 40)));
                            if (c == Color.BLACK) {
                                JOptionPane.showMessageDialog(Chessboard.this, "     黑棋获胜！     ");
                            } else if (c == Color.WHITE) {
                                JOptionPane.showMessageDialog(Chessboard.this, "     白旗获胜！     ");

                            }
                            iso = true;
                            return;
                        }
                        isBlack = !isBlack;
                    }
                }
            }
        });
    }

    @Override
    public void paint(Graphics e) {

        super.paint(e);
        //背景图片插入
        BackgroundPicture(e);
        //绘制棋盘
        chessboard(e);
        //绘制指示器
        drawPointer(e);
        //绘制棋子
        for (int i = 0; i < chessCount; i++) {
            chessList[i].drawChess(e);
        }
        //绘制游戏提示
        e.setColor(Color.black);
        e.setFont(new Font("黑体", Font.BOLD, 45));
        e.drawString("游戏提示:" + message, 141, 1023);



    }
    //创建数组内容
    private void createPointer(){
        Pointer pointer;
        for(int i = 0 ; i < 15 ; i++){
            for(int j = 0 ; j < 15 ; j++ ){
                int x = j * dis + X;
                int y = i * dis + Y;
                pointer = new Pointer(i,j,x,y);
                pointers[i][j] = pointer;
            }
        }
    }
    //绘制指示器
    private  void drawPointer(Graphics g){
        Pointer pointer;
        for(int i = 0 ; i < 15; i++){
            for(int j = 0 ; j < 15 ; j++ ){
                pointer = pointers[i][j];
                if(pointer!=null){
                    pointer.drawPointer(g);
                }
            }
        }
    }

    private boolean HasChess(int col, int row) {
        boolean result = false;
        for (int i = 0; i < chessCount; i++) {
            Chess cc = chessList[i];
            if (cc != null && cc.getCol() == col && cc.getRow() == row) {
                return true;
            }
        }
        return result;
    }
    private boolean haschess(int col, int row, Color c) {
        Boolean result = false;
        for (int i = 0; i < chessCount; i++) {
            Chess ch = chessList[i];
            if (ch != null && ch.getCol() == col && ch.getRow() == row && ch.getColor() == c) {
                result = true;
            }
        }
        return result;
    }
    //判断输赢
    private boolean isWin(int col, int row) {
        boolean result = false;
        int CountCh = 1;
        Color c = null;
        if (isBlack) {
            c = Color.BLACK;
        } else {
            c = Color.WHITE;
        }

        // 水平向左
        for (int x = col - 1; x >= 0; x--) {
            if (haschess(x, row, c)) {
                CountCh++;
            } else {
                break;
            }
        }
        // 水平向右
        for (int x = col + 1; x <= 14; x++) {
            if (haschess(x, row, c)) {
                CountCh++;
            } else {
                break;
            }
        }
        // 水平取胜
        if (CountCh >= 5) {
            result = true;
            message = "游戏结束";
        } else {
            result = false;
            CountCh = 1;
        }
        // 竖直向上
        for (int y = row - 1; y >= 0; y--) {
            if (haschess(col, y, c)) {
                CountCh++;
            } else {
                break;
            }
        }
        // 竖直向下
        for (int y = row + 1; y <= 14; y++) {
            if (haschess(col, y, c)) {
                CountCh++;
            } else {
                break;
            }
        }
        // 竖直取胜
        if (CountCh >= 5) {
            result = true;
            message = "游戏结束";
        } else {
            result = false;
            CountCh = 1;
        }
        // 斜向右上
        for (int x = col + 1, y = row - 1; x <= 14 && y >= 0; x++, y--) {
            if (haschess(x, y, c)) {
                CountCh++;
            } else {
                break;
            }
        }
        // 斜向左下
        for (int x = col - 1, y = row + 1; x >= 0 && y <= 14; x--, y++) {
            if (haschess(x, y, c)) {
                CountCh++;
            } else {
                break;
            }
        }
        // 斜向取胜
        if (CountCh >= 5) {
            result = true;
            message = "游戏结束";
        } else {
            result = false;
            CountCh = 1;
        }
        // 斜向左上
        for (int x = col - 1, y = row - 1; x >= 0 && y >= 0; x--, y--) {
            if (haschess(x, y, c)) {
                CountCh++;
            } else {
                break;
            }
        }
        // 斜向右下
        for (int x = col + 1, y = row + 1; x <= 14 && y <= 14; x++, y++) {
            if (haschess(x, y, c)) {
                CountCh++;
            } else {
                break;
            }
        }
        // 斜向取胜
        if (CountCh >= 5) {
            result = true;
            message = "游戏结束";
        } else {
            result = false;
            CountCh = 1;
        }

        return result;
    }
    //开始游戏
    public void againGame() {
        for (int i = 0; i < chessList.length; i++) {
            chessList[i] = null;
        }
        chessCount = 0;
        iso = false;
        message = "开局黑棋先手";
        repaint();
    }
    //悔棋
    public void huiqi() {
        if (iso) {
            return;
        }
        chessList[chessCount - 1] = null;
        chessCount--;
        if (isBlack) {
            message = "白棋悔棋，白方下棋";
        } else {
            message = "黑棋悔棋，黑方下棋";
        }
        isBlack = !isBlack;
        repaint();
    }
    //绘制棋盘
    public static void chessboard(Graphics g){
        int x1 = X;
        int y1 = Y;

        for( int i = 0 ; i < 15 ; i++){
            g.setColor(Color.black);
            g.drawLine(x1,y1+dis*i,x1+dis*14,y1+dis*i);
            g.drawLine(x1+dis*i,y1,x1+dis*i,y1+dis*14);
            g.drawLine(x1+1,y1+dis*i+1,x1+dis*14+1,y1+dis*i+1);
            g.drawLine(x1+dis*i+1,y1+1,x1+dis*i+1,y1+dis*14+1);
        }
        //绘制五点
        g.fillArc(x1 + dis*3 - 6,y1 + dis*3 - 6,12,12,0,360);
        g.fillArc(x1 + dis*11 - 6,y1 + dis*3 - 6,12,12,0,360);
        g.fillArc(x1 + dis*3 - 6,y1 + dis*11 - 6,12,12,0,360);
        g.fillArc(x1 + dis*11 - 6,y1 + dis*11 - 6,12,12,0,360);
        g.fillArc(x1 + dis*7 - 7,y1 + dis*7 - 7,14,14,0,360);
    }
    //背景图片
    public static void BackgroundPicture(Graphics g){
        //获取图片地址
        URL resource = Chessboard.class.getResource("/images/Chessboard.jpg");
        ImageIcon imageIcon = new ImageIcon(resource);
        Image image = imageIcon.getImage();
        g.drawImage(image,0,0,null);
    }
    //鼠标监听
    private void createMouseListener(){
        MouseAdapter mouseAdapter = new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
//                System.out.println("鼠标移动");
                int x = e.getX();
                int y = e.getY();
                Pointer pointer;
                for(int i = 0 ; i < 15 ; i++){
                    for(int j = 0 ; j < 15 ; j++ ){
                        pointer = pointers[i][j];
                        if(pointer.IsPointer(x,y)&&pointer.getType()==0 ){
                            pointer.setShow(true);
                        }else{
                            pointer.setShow(false);
                        }
                    }
                }
                repaint();

            }
        };

        addMouseMotionListener(mouseAdapter);
        addMouseListener(mouseAdapter);

    }
    //清除最后一步的指示器
    private void ClearLast(){
        Chess c ;
        for(int i = 0 ; i < chessCount ; i++){
            c = chessList[i];
            c.setLast(false);
        }
    }
}


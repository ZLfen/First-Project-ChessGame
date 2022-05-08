package doubleGame;

import aichess.OneGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//主菜单
public class GameMenu extends JFrame {
    public GameMenu() throws HeadlessException {
        setTitle("秃头五子棋");
        setSize(500,600);
        setLocationRelativeTo(null);       // 关闭系统排序
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //关闭后自动退出
        setLocationRelativeTo(getOwner());     //窗口居中
        setResizable(false);  //锁定窗口
        JButton btn1 = new JButton("双人游戏");
        btn1.setFont(new Font("黑体",Font.BOLD,30));
        btn1.setBounds(150,80,200,100);

        JButton btn2 = new JButton("单人游戏");
        btn2.setFont(new Font("黑体",Font.BOLD,30));
        btn2.setBounds(150,230,200,100);

        JButton btn3 = new JButton("退出");
        btn3.setFont(new Font("黑体",Font.BOLD,30));
        btn3.setBounds(150,380,200,100);

        add(btn1);
        add(btn2);
        add(btn3);
        setVisible(true);

        //双人游戏按键事件
        btn1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                setVisible(false);
                DoubleGame singleGame = new DoubleGame();
            }

        });
        //单人游戏按键事件
        btn2.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                setVisible(false);
                OneGame oneGame = new OneGame();
            }

        });
        //退出按键事件
        btn3.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                System.exit(0);
            }

        });


    }

}

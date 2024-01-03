import javax.swing.*;
import java.awt.*;
public class inputTaskPage extends JFrame{
    static String[] a = new String[main.backnum];
    static int[][] b = new int[main.backnum][4];
    static JPanel[] panels = new JPanel[main.backnum];
    public static JButton yes = new JButton();
    //panels[0] = new JPanel();
    main a1 = new main();
    static JTextField[][] textFields = new JTextField[main.backnum][5];
    inputTaskPage(){
        this.setTitle("三级调度") ;
        setSize(770, 200);
        Box vbox = Box.createVerticalBox();
        Font textFont=new Font("宋体", Font.PLAIN, 20);
        vbox.add(new JLabel("请按顺序输入：作业名称，空间，到来时间，运行时间，优先级"));
        //this.add(vbox);

        for(int i = 0;i<main.backnum;i++){
            panels[i] = new JPanel();
            System.out.println("sdasdasd");
            JLabel label = new JLabel("作业"+i+":");
            System.out.println("sdasdasd");
            panels[i].add(label);
            System.out.println("sdasdasd");
            for(int j = 0;j<5;j++){
                textFields[i][j] = new JTextField(10);
                panels[i].add(textFields[i][j]);
            }
            vbox.add(panels[i]);
        }

        yes = new JButton("确认");
        JPanel panel = new JPanel();
        panel.add(yes);
        yes.addActionListener(a1);
        vbox.add(panel);this.add(vbox);
        setLocation(400,400);
        setVisible(true);
    }
    static void inputtask(){
        String[] a = new String[main.backnum];
        int[][] b = new int[main.backnum][4];


        for(int i = 0;i<main.backnum;i++){
            task t = new task();
            a[i] = textFields[i][0].getText();
            b[i][0] = Integer.parseInt(textFields[i][1].getText());
            b[i][1] = Integer.parseInt(textFields[i][2].getText());
            b[i][2] = Integer.parseInt(textFields[i][3].getText());
            b[i][3] = Integer.parseInt(textFields[i][4].getText());
            t.setTask(a[i],b[i][0],b[i][1],b[i][2],b[i][3]);
            main.backTask.add(t);
        }

    }
}

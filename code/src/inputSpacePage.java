import javax.swing.*;
import java.awt.*;

public class inputSpacePage extends JFrame{
    public static JButton hello = new JButton();
    public static JPanel panel1;
    public static JTextField jTextField1 = new JTextField(5);
    public static JTextField jTextField2 = new JTextField(5);
    public static JTextField jTextField3 = new JTextField(5);

    main a1 = new main();
    public JPanel setpenal1(){
        Font textFont=new Font("宋体", Font.PLAIN, 40);
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();

        //panel1.setBorder(BorderFactory.createTitledBorder(null, "选择", TitledBorder.CENTER, TitledBorder.TOP));
//        JLabel label1 = new JLabel("请输入内存大小（MB）");
//        JLabel label2 = new JLabel("请输入虚存大小（MB）");
//        JLabel label3 = new JLabel("请输入作业数量");
//        JTextField textField1 = new JTextField();
//        label1.setBounds(10, 20, 200, 20);
//        textField1.setBounds(60,40,200,40);
        panel1.add(new JLabel("请输入内存大小（MB）"),textFont);
        panel1.setFont(textFont);
        panel1.add(jTextField1);
        panel2.add(new JLabel("请输入虚存大小（MB）"),textFont);
        panel2.setFont(textFont);
        panel2.add(jTextField2);
        panel3.add(new JLabel("请输入作业数量"),textFont);
        panel3.setFont(textFont);
        panel3.add(jTextField3);
        hello = new JButton("确认");
        panel4.add(hello);
        Box vbox = Box.createVerticalBox();
        vbox.add(panel1);
        vbox.add(panel2);
        vbox.add(panel3);
        vbox.add(panel4);
//        panel.add(label2);
//        panel.add(label3);
//        panel.add(textField1);
        //panel.add(label2);
        //panel.add(label3);
        panel.add(vbox);
        return panel;
    }
    public void setpenal2(){
        this.add(new JLabel("yes"));
    }
    public inputSpacePage(){

        Font textFont=new Font("宋体", Font.PLAIN, 20);
        panel1 = setpenal1();
        setSize(770, 200);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("三级调度") ;
        this.add(panel1);

        hello.addActionListener(a1);
        setLocation(400,400);
        setVisible(true);
    }
    static void inputspace(){
        main.leftmainspace = Integer.parseInt(jTextField1.getText());
        main.leftvirtualspace = Integer.parseInt(jTextField2.getText());
        main.backnum = Integer.parseInt(jTextField3.getText());

    }

    public static void main(String[] args) {

        inputSpacePage a1 = new inputSpacePage();
    }

}

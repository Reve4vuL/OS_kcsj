import javax.swing.*;
import java.awt.*;
public class showpage extends JFrame{
    static JTextArea textArea1 = new JTextArea(10,5);
    static JTextArea textArea2 = new JTextArea(10,5);
    static JTextArea textArea3 = new JTextArea(10,5);
    static JTextArea textArea4 = new JTextArea(10,5);
    static JTextArea showtextArea = new JTextArea();
    static JButton end = new JButton("下一个时钟周期");
    main a1 = new main();

    showpage(){
        Font textFont=new Font("宋体", Font.PLAIN, 20);
        setSize(1000, 600);
        Box vbox = Box.createVerticalBox();
        Box hbox = Box.createHorizontalBox();
        Box hbox1 = Box.createHorizontalBox();
        Box hbox2 = Box.createHorizontalBox();
        JPanel panel = new JPanel();
        panel.setSize(1,1);
        JLabel label1 = new JLabel("后备作业队列             ");
        JLabel label2 = new JLabel("内存作业             ");
        JLabel label3 = new JLabel("虚存作业                ");
        JLabel label4 = new JLabel("CPU             ");
        end.addActionListener(a1);
        textArea1.setColumns(5);
        textArea2.setColumns(5);
        textArea3.setColumns(5);
        textArea4.setColumns(5);
        label1.setFont(textFont);
        label2.setFont(textFont);
        label3.setFont(textFont);
        label4.setFont(textFont);
        textArea1.setFont(textFont);
        textArea2.setFont(textFont);
        textArea3.setFont(textFont);
        textArea4.setFont(textFont);
        showtextArea.setFont(textFont);
        hbox.add(label1);
        hbox.add(label2);
        hbox.add(label3);
        hbox.add(label4);
        hbox1.add(textArea1);
        hbox1.add(textArea2);
        hbox1.add(textArea3);
        hbox1.add(textArea4);
        hbox2.add(showtextArea);

        vbox.add(hbox);
        vbox.add(panel);
        vbox.add(hbox1);
        vbox.add(hbox2);
        setVisible(true);

        vbox.add(end);
        this.add(vbox);
        setLocation(400,400);
    }

    public static void main(String[] args) {
        showpage showpage = new showpage();
    }

}

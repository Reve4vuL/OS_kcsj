import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class main implements ActionListener {
    static Scanner scanner = new Scanner(System.in);
    static int time = 0;//cpu时间
    static int mainspace;//主存大小
    static int leftmainspace;//剩余主存大小
    static int virtualspace;//虚存大小
    static int leftvirtualspace;//剩余虚存大小
    static List<task> backTask = new ArrayList<>();//后备作业队列
    static List<task>mainTask = new ArrayList<>();//主存作业
    static List<task>virtualTask = new ArrayList<>();//虚存作业
    static List<task>cputask = new ArrayList<>();//cpu上作业
    static int backnum = 0;//后备队列作业数
    static int mainnum = 0;//主存作业数
    static int virtualnum = 0;//虚存作业数
    static int cpunum = 0;//处理机上的进程数
    static boolean end = false;//结束标志


    public static void init(){
        mainspace = leftmainspace ;
        virtualspace = leftvirtualspace;

//        for(int i = 0;i<backnum;i++){
//            System.out.println("请输入第"+(i+1)+"个作业的名称，空间，到来时间，运行时间，优先级");
//            task a = new task();
//            String x5;
//            int x1,x2,x3,x4;
//            x5 = scanner.next();
//            x1 = scanner.nextInt();
//            x2 = scanner.nextInt();
//            x3 = scanner.nextInt();
//            x4 = scanner.nextInt();
//            a.setTask(x5,x1,x2,x3,x4);
//            backTask.add(a);
//
//        }
    }
    static int getMaxHNNIndex(){//获取后备作业队列中响应比最高的作业
        double max = 0;
        int index = -1;
        for(int i = 0;i<backnum && time>=backTask.get(i).comingTime;i++){
            //System.out.println("&&&&");
            if(max< backTask.get(i).getHNN()){
                max = backTask.get(i).getHNN();
                index = i;
            }
            else if(max == backTask.get(i).getHNN()){//相应比一样先来先服务
                if(backTask.get(index).comingTime>backTask.get(i).comingTime){
                    index = i;
                }
            }
        }
        return index;
    }
    static int getMaxSpaceLessPrio(int p){//获取内存中优先级比p小且存储最大的
        int max = 0,index = -1,prio = 0;
        //System.out.println("p = "+p);
        //System.out.println("mainnum = "+mainnum);
//        for(int i = 0;i<mainnum;i++){
//            System.out.println(i+":"+"maintaskprio"+mainTask.get(i).priority+" "+mainTask.get(i).state);
//        }
        for(int i = 0;i<mainnum && mainTask.get(i).priority<p  ;i++){
            //System.out.println("jinlaile!");
            if(mainTask.get(i).state!=3) {
                if(max<mainTask.get(i).space){//如果空间比最大值小
                    index = i;
                    max = mainTask.get(i).space;
                    prio = mainTask.get(i).priority;
                }
                else if(max == mainTask.get(i).space){//如果空间一样但是优先级更低
                    if(prio>mainTask.get(i).priority){
                        prio = mainTask.get(i).priority;
                        index = i;
                    }
                }
            }

        }
        //System.out.println("max index = "+index);
        return index;
    }
    static int getMaxPrio(){
        int max = 0,index = -1,mintime = 10000;
        for(int i = 0;i<mainnum;i++){
            if(mainTask.get(i).priority>max){
                max = mainTask.get(i).priority;
                index = i;
            }
            else if(mainTask.get(i).priority == max){
                if(mainTask.get(i).CPUtime < mintime){
                    mintime = mainTask.get(i).CPUtime;
                    index = i;
                }
            }
        }
        return index;
    }
    static int getMaxPrio2(){
        int max = 0,index = -1,mintime = 10000;
        for(int i = 0;i<mainnum&&mainTask.get(i).state!=3;i++){//考虑是否在处理机器上（虚存转内存用）
            if(mainTask.get(i).priority>max){
                max = mainTask.get(i).priority;
                index = i;
            }
            else if(mainTask.get(i).priority == max){
                if(mainTask.get(i).CPUtime < mintime){
                    mintime = mainTask.get(i).CPUtime;
                    index = i;
                }
            }
        }
        return index;
    }
    static int getMaxSpace(){//虚存放入主存中时，找到主存中最大的且优先级比待放入要小的
        int max = 0,index = -1,minprio = 10000;
        for(int i = 0;i<mainnum&&mainTask.get(i).state!=3;i++){//考虑是否在处理机器上（虚存转内存用）
            if(mainTask.get(i).space>max){
                max = mainTask.get(i).space;
                index = i;
            }
            else if(mainTask.get(i).space == max){
                if(mainTask.get(i).priority < minprio){
                    minprio = mainTask.get(i).CPUtime;
                    index = i;
                }
            }
        }
        return index;
    }
    static void createTask(int dex){//后备作业队列作业dex创建成功加入内存
        mainnum++;
        backnum--;
        backTask.get(dex).state = 1;
        System.out.println("作业 "+backTask.get(dex).name+" 已创建成功进入内存");
        showpage.showtextArea.append("作业 "+backTask.get(dex).name+" 已创建成功进入内存\n");
        mainTask.add(backTask.get(dex));
        leftmainspace-=backTask.get(dex).space;
        backTask.remove(dex);
    }
    static void createTaskToVirtual(int dex){//后备作业队列作业dex创建成功加入内存
        virtualnum++;
        backnum--;
        backTask.get(dex).state = 2;
        System.out.println("作业 "+backTask.get(dex).name+" 已创建成功进入虚存");
        showpage.showtextArea.append("作业 "+backTask.get(dex).name+" 已创建成功进入虚存\n");
        virtualTask.add(backTask.get(dex));
        leftvirtualspace-=backTask.get(dex).space;
        backTask.remove(dex);
    }
    static void switchToVirtual(int dex){//内存中作业放入虚存
        virtualnum++;
        mainnum--;
        mainTask.get(dex).state = 2;
        virtualTask.add(mainTask.get(dex));
        System.out.println("作业 "+mainTask.get(dex).name+" 挂起，从内存调入外存");
        showpage.showtextArea.append("作业 "+mainTask.get(dex).name+" 挂起，从内存调入外存\n");
        leftmainspace+=mainTask.get(dex).space;
        leftvirtualspace-=mainTask.get(dex).space;
        mainTask.remove(dex);
    }
    static void switchToMain(int dex){//虚存中作业放入主存
        mainnum++;
        virtualnum--;
        virtualTask.get(dex).state = 1;
        mainTask.add(virtualTask.get(dex));
        System.out.println("作业 "+virtualTask.get(dex).name+" 激活，从外村进入内存");
        showpage.showtextArea.append("作业 "+virtualTask.get(dex).name+" 激活，从外村进入内存\n");
        leftvirtualspace+=virtualTask.get(dex).space;
        leftmainspace-=virtualTask.get(dex).space;
        virtualTask.remove(dex);
    }
    static void switchMainAndVirtual(int i,int max){
        virtualTask.get(i).state = 1;
        mainTask.get(max).state = 2;
        task a = virtualTask.get(i);
        task b = mainTask.get(max);
        System.out.println("虚存作业 "+virtualTask.get(i).name+"和"+"主存作业"+mainTask.get(max).name+" 交换成功，从外村进入内存");
        showpage.showtextArea.append("虚存作业 "+virtualTask.get(i).name+"和"+"主存作业"+mainTask.get(max).name+" 交换成功，从外村进入内存\n");
        leftvirtualspace = leftvirtualspace+virtualTask.get(i).space-mainTask.get(max).space;
        leftmainspace = leftmainspace-virtualTask.get(i).space+mainTask.get(max).space;
        virtualTask.remove(i);
        mainTask.remove(max);
        virtualTask.add(b);
        mainTask.add(a);
    }
    static void switchToCPU(int dex){//内存中作业上处理机，注意这里内存不会被释放。
        //mainnum--;
        cpunum++;
        System.out.println("作业 "+mainTask.get(dex).name+" 上CPU运行");
        showpage.showtextArea.append("作业 "+mainTask.get(dex).name+" 上CPU运行\n");
        mainTask.get(dex).state = 3;
        cputask.add(mainTask.get(dex));
        //mainTask.remove(dex);
    }
    public static void runback(){//后被作业队列上处理机器和因为后备作业队列优先级更高而内存移入虚存的情况
        int dex = getMaxHNNIndex();
        //System.out.println("dex = "+dex);
        if(dex == -1)return;
        if(leftmainspace>=backTask.get(dex).space){//主存足够放下响应比最高的作业
            createTask(dex);
        }
        else if(getMaxSpaceLessPrio(backTask.get(dex).priority) != -1&&leftmainspace+mainTask.get(getMaxSpaceLessPrio(backTask.get(dex).priority)).space>=backTask.get(dex).space){//主存放不下相应比最高的作业
            //如果剩余内存空间加内存中最大空间且优先级满足一定要求之和大于相应比最高的作业，放入内存，选中作业调出虚存
                if(leftvirtualspace>mainTask.get(getMaxSpaceLessPrio(backTask.get(dex).priority)).space){//如果虚存足够
                    switchToVirtual(getMaxSpaceLessPrio(backTask.get(dex).priority));
                    createTask(dex);
                }

        }
        else if(leftvirtualspace>backTask.get(dex).space){
            createTaskToVirtual(dex);
        }
    }
    public static void runmain2(){//内存作业选出优先级最高的上处理机
        //System.out.println("runmain2");
        int dex = getMaxPrio();
        //System.out.println("dex="+dex);
        if(dex == -1)return;
        if(cputask.size()<1){
            switchToCPU(dex);
        }
    }
    public static void runmain1(){
        //System.out.println("runmain1");
        int maxPrio = getMaxPrio2();
        for(int i = 0;i<virtualnum&&virtualTask.get(i).priority>maxPrio;i++){//判断虚存中作业优先级是否超过内存中
            if(leftmainspace>=virtualTask.get(i).space){//主存足够容纳此作业
                switchToMain(i);
            }
            else{//不够
                int maintaskid = getMaxSpace();
                if (maintaskid == -1)break;
                if(leftmainspace+mainTask.get(maintaskid).space>=virtualTask.get(i).space&&leftvirtualspace+virtualTask.get(i).space>=mainTask.get(maintaskid).space){
                    //优先级比虚存中低
                    switchMainAndVirtual(i,maintaskid);
                }
            }
        }
    }
    public static void CPU(){//每次判断处理机上作业是否运行完毕
        //System.out.println("CPU");
        if(cputask.size()==1){
            if(cputask.get(0).CPUtime==0){
                leftmainspace += cputask.get(0).space;
                cpunum--;
                cputask.remove(0);
                for(int i = 0;i<mainnum;i++){
                    if(mainTask.get(i).state == 3){
                        System.out.println("作业 "+mainTask.get(i).name+" 运行完毕，CPU释放");
                        showpage.showtextArea.append("作业 "+mainTask.get(i).name+" 运行完毕，CPU释放\n");
                        mainTask.remove(i);
                        break;
                    }
                }
                mainnum--;
            }
        }
    }
    static void timepass(){
        time++;
        for(int i = 0;i<backnum;i++){//所有作业等待时间加一
            backTask.get(i).waitTine++;
        }
        for(int i = 0;i<mainnum&&mainTask.get(i).state!=3;i++){//所有作业等待时间加一
            mainTask.get(i).waitTine++;
        }
        for(int i = 0;i<virtualnum;i++){//所有作业等待时间加一
            virtualTask.get(i).waitTine++;
        }
        for(int i = 0;i<cpunum;i++){
            cputask.get(i).CPUtime--;
        }
    }
    public static void run() throws InterruptedException {//运行主函数

            CPU();
            runback();//磁盘到内存的时间设为三个时钟周期
            //System.out.println(backnum);
            runmain1();//磁盘到内存的时间设为三个时钟周期
            runmain2();//内存转到处理机设为一个时钟周期
            showOnPage();show();
            timepass();
            judge();
            System.out.println("time:"+time);
            if(end) {
                System.out.println("所有作业均已运行完毕");
                showpage.showtextArea.append("所有作业均已运行完毕\n");
            }
            Thread.sleep(500);

    }
    static void judge(){
        if(backnum == 0 && mainnum == 0 && virtualnum == 0 && cpunum==0)end = true;
    }

    public static void show(){
        System.out.println("后备作业队列：");
        for(int i = 0;i<backnum;i++){
            backTask.get(i).show1();
        }
        System.out.println("内存作业队列：");
        for(int i = 0;i<mainnum;i++){
            mainTask.get(i).show1();
        }
        System.out.println("虚存作业队列：");
        for(int i = 0;i<virtualnum;i++){
            virtualTask.get(i).show1();
        }
        System.out.println("CPU作业队列：");
        for(int i = 0;i<cpunum;i++){
            cputask.get(i).show1();
        }
        showpage.showtextArea.append("内存占用："+(mainspace-leftmainspace)+"/"+mainspace+"\n");
        showpage.showtextArea.append("虚存占用："+(virtualspace-leftvirtualspace)+"/"+virtualspace+"\n");
        System.out.println();
        System.out.println("-------------------------------");
        System.out.println();
    }
    public static void showOnPage(){
        showpage.textArea1.setText("");
        showpage.textArea2.setText("");
        showpage.textArea3.setText("");
        showpage.textArea4.setText("");
        for(int i = 0;i<backnum;i++){
            showpage.textArea1.append(backTask.get(i).show()+'\n');
        }
        for(int i = 0;i<mainnum;i++){
            showpage.textArea2.append(mainTask.get(i).show()+'\n');
        }
        for(int i = 0;i<virtualnum;i++){
            showpage.textArea3.append(virtualTask.get(i).show()+'\n');
        }
        for(int i = 0;i<cpunum;i++){
            showpage.textArea4.append(cputask.get(i).show()+'\n');
        }
        showpage.textArea1.invalidate();
        showpage.textArea1.repaint();
    }
    public static void main(String[] args) throws InterruptedException {
        //init();


        //showpage showpage = new showpage();
        inputSpacePage threeStage = new inputSpacePage();

        //threeStage.setVisible(false);
        //threeStage.dispose();

        //inputTaskPage taskPage = new inputTaskPage();
        //run();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == inputSpacePage.hello){
            System.out.println("nb");
            inputSpacePage.inputspace();
            init();
            inputTaskPage taskPage = new inputTaskPage();
        }
        //else if();
        else if(e.getSource() == inputTaskPage.yes){
            inputTaskPage.inputtask();
            showpage showpage = new showpage();
        }
        else if(e.getSource() == showpage.end){
            try {
                run();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }
}

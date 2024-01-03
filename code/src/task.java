public class task {
    int space;//空间大小
    int comingTime;//到达时间
    int waitTine;//等待时间
    int CPUtime;//运行时间
    int state;//0在后备作业队列，1在主存，2在虚存，3在处理机器,4表示完成
    int priority;//优先级（越大越高）
    String name;
    public void setTask(String name,int space,int comingTime,int CPUtime,int priority) {
        this.space = space;
        this.CPUtime = CPUtime;
        this.comingTime = comingTime;
        this.priority = priority;
        this.state = 0;
        this.waitTine = 0;
        this.name = name;
    }
    public void show1(){
        System.out.println("名称，空间，到达时间，等待时间，运行时间，优先级，状态分别为");
        System.out.println(this.name +" "+ this.space +" "+ this.comingTime +" "+ this.waitTine +" "+ this.CPUtime +" "+ this.priority +" "+ this.state);
    }
    public String show(){
        return(this.name +" "+ this.space +" "+ this.comingTime +" "+ this.waitTine +" "+ this.CPUtime +" "+ this.priority +" "+ this.state);
        //System.out.println("名称，空间，到达时间，等待时间，运行时间，优先级，状态分别为");
        //System.out.println(this.name +" "+ this.space +" "+ this.comingTime +" "+ this.waitTine +" "+ this.CPUtime +" "+ this.priority +" "+ this.state);
    }

    public double getHNN(){
        return (waitTine+CPUtime)*1.0/CPUtime;
    }
}

/* 
* synchro
* 5431038821  
* Ms.Vibhavee Trairattanapa 
*/
public class LogisticBelt implements Runnable {

	int num,logisticNum;
	boolean work;
	FactoryBelt factory;
	ProgramGUI program;
	
	public LogisticBelt(int num,FactoryBelt factory,ProgramGUI program, int logisticNum){
		this.num = num;
		this.factory = factory;
		this.program = program;
		this.logisticNum = logisticNum;
	}
	public int getNum(){
		return num;
	}
	public synchronized void increase(int val){
		this.num += val;
	}
	public void run(){
		while(true){
			synchronized(factory){
				if(factory.getNum() <= 0){
					try {
						System.out.println(Thread.currentThread().getName()+" Waiting");
						factory.wait();
					}
					catch (InterruptedException e) {
						System.err.print("Error");
					}
				}
				else{
					this.increase(1);
					program.setText(getNum()+"",logisticNum);
					System.out.println(Thread.currentThread().getName()+" Belt +1 "+getNum());
					factory.decrease(1);
					program.setText(factory.getNum()+"",0);
					System.out.println(Thread.currentThread().getName()+" Factory -1 "+factory.getNum());
					try {
						System.out.println(Thread.currentThread().getName()+" Sleeping");
						Thread.sleep(1500);
					}
					catch (InterruptedException e) {
						System.err.print("Error");
					}
				}
			}
			synchronized(factory){
			}
		}
	}
}

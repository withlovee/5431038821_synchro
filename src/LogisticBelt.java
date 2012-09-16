/* 
* synchro
* 5431038821  
* Ms.Vibhavee Trairattanapa 
*/
public class LogisticBelt implements Runnable {

	private int num,logisticNum;
	private FactoryBelt factory;
	//private ProgramGUI program;
	private boolean run;
	
	public LogisticBelt(int num,FactoryBelt factory, int logisticNum){
		this.num = num;
		this.factory = factory;
		//this.program = program;
		this.logisticNum = logisticNum;
		run = false;
	}
	public int getNum(){
		return num;
	}
	public synchronized void start(){
		run = true;
		System.out.println(Thread.currentThread().getName()+" starts");
	}
	public synchronized void stop(){
		run = false;
		System.out.println(Thread.currentThread().getName()+" stops");
	}
	public synchronized void increase(int val){
		this.num += val;
	}
	public void run(){
		boolean skipSleep = false;
		while(true){
			
			synchronized(factory){
				if(factory.getNum() <= 0 || !run){
					try {
						System.out.println(Thread.currentThread().getName()+" Waiting");
						factory.wait();
						skipSleep = true;
					}
					catch (InterruptedException e) {
						System.err.print("Error");
					}
				}
				else{
					skipSleep = false;
					this.increase(1);
					ProgramGUI.program.setText(getNum()+"",logisticNum);
					System.out.println(Thread.currentThread().getName()+" Belt +1 "+getNum());
					factory.decrease(1);
					ProgramGUI.program.setText(factory.getNum()+"",0);
					System.out.println(Thread.currentThread().getName()+" Factory -1 "+factory.getNum());
				}
			}
			synchronized(factory){
				if(!skipSleep){
					try {
						System.out.println(Thread.currentThread().getName()+" Sleeping");
						Thread.sleep(1500);
						skipSleep = false;
					}
					catch (InterruptedException e) {
						System.err.print("Error");
					}
				}
			}
		}
	}
}

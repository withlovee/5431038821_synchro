/* 
* synchro
* 5431038821  
* Ms.Vibhavee Trairattanapa 
*/
public class FactoryBelt implements Runnable{
	int num;
	boolean work;
	ProgramGUI program;
	
	public FactoryBelt(int num){
		this.num = num;
	}
	public FactoryBelt(int num,ProgramGUI program){
		this.num = num;
		this.program = program;
	}
	public int getNum(){
		return num;
	}
	public synchronized void increase(int val){
		this.num += val;
	}
	public synchronized void decrease(int val){
		this.num -= val;
	}	
	public void run(){
		while(true){
			synchronized(this){
				if(getNum() > 0){
					program.setText("Notify All",3);
					notifyAll();
				}
			}
			synchronized(this){
				if(getNum() <= 30)
					work = true;
				else if(getNum() >= 50)
					work = false;
			}
			if(work){
				this.increase(1);
				program.setText(getNum()+"",0);
				System.out.println(Thread.currentThread().getName()+" Factory +1 "+getNum());
			}
			try {
				if(work){
					System.out.println(Thread.currentThread().getName()+" Sleeping");
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				System.err.print("Error");
			}
			
		}
	}
	public static void main(String[] args) {
		
		ProgramGUI program = new ProgramGUI();
		FactoryBelt factory = new FactoryBelt(0,program);
		LogisticBelt logistic1 = new LogisticBelt(0,factory,program,1);
		LogisticBelt logistic2 = new LogisticBelt(0,factory,program,2);
		
		Thread belt00 = new Thread(factory);
		Thread belt01 = new Thread(logistic1);
		Thread belt02 = new Thread(logistic2);

		belt00.setName("Factory");
		belt01.setName("Belt1");
		belt02.setName("Belt2");
		
		belt00.start();
		belt01.start();
		belt02.start();
	}
}
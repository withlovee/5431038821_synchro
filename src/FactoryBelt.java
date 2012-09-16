/* 
* synchro
* 5431038821  
* Ms.Vibhavee Trairattanapa 
*/
public class FactoryBelt implements Runnable{
	private int num;
	private boolean work,run;
	
	public FactoryBelt(int num){
		this.num = num;
		this.run = false;
	}
	public int getNum(){
		return num;
	}
	public synchronized void start(){
		run = true;
		//System.out.println(Thread.currentThread().getName()+" starts");
	}
	public synchronized void stop(){
		run = false;
		//System.out.println(Thread.currentThread().getName()+" stops");
	}
	public synchronized void increase(int val){
		this.num += val;
	}
	public synchronized void decrease(int val){
		this.num -= val;
	}	
	public void run(){
		while(true){
			
			//Check if the factory should produce more items
			synchronized(this){
				if(getNum() <= 30 && run)
					work = true;
				else if(getNum() >= 50 || !run)
					work = false;
			}
			
			//Product item
			synchronized(this){
				if(work){
					this.increase(1);
					ProgramGUI.program.setText(getNum()+"",0);
					//System.out.println(Thread.currentThread().getName()+" Factory +1 "+getNum());
				}
			}
			
			//Notify all Belts that there is item(s) available
			synchronized(this){
				if(run){
					notifyAll();
				}
			}
			
			//Go to sleep for 100 ms
			try {
				if(work){
					//System.out.println(Thread.currentThread().getName()+" Sleeping");
					Thread.sleep(100);
				}
			} 
			catch (InterruptedException e) {
				System.err.print("Error");
			}
			
		}
	}
	public static void main(String[] args) {
		
		//Create objects needed
		FactoryBelt factory = new FactoryBelt(0);
		LogisticBelt logistic1 = new LogisticBelt(0, factory, 1);
		LogisticBelt logistic2 = new LogisticBelt(0, factory, 2);
		
		//Create GUI
		ProgramGUI.program = new ProgramGUI(logistic1, logistic2, factory);
		
		//Create threads
		Thread belt00 = new Thread(factory);
		Thread belt01 = new Thread(logistic1);
		Thread belt02 = new Thread(logistic2);

		//Set names of threads
		belt00.setName("Factory");
		belt01.setName("Belt1");
		belt02.setName("Belt2");
		
		//Start working!
		belt00.start();
		belt01.start();
		belt02.start();
	}
}

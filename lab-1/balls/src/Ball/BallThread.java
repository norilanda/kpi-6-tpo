package Ball;

public class BallThread extends Thread {
    private Ball b;
    public BallThread(Ball ball){
        b = ball;
    }
    @Override
    public void run(){
        try{
            for(int i=1; i<10000; i++){
                var canMoveFurther = b.move();
//                System.out.println("Thread name = "
//                        + Thread.currentThread().getName());

                if(!canMoveFurther)
                    break;
                Thread.sleep(5);
            }
        } catch(InterruptedException ex){
                System.out.println("InterruptedException in thread: " + Thread.currentThread().getName());
        }
    }
}
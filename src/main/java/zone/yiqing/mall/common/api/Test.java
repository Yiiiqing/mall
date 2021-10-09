package zone.yiqing.mall.common.api;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-08.
 */
public class Test {

  public volatile int inc = 0;

  public void increase() {
    inc++;
  }

  public static void main(String[] args) {
    final Test test = new Test();
    for (int i = 0; i < 10; i++) {
      new Thread() {
        public void run() {
          System.out.println(Thread.currentThread().getName());
          for (int j = 0; j < 1000; j++) {
            test.increase();
          }
        }
      }.start();
    }

    while (Thread.activeCount() > 1) {
      //保证前面的线程都执行完
//      Thread.yield();
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getName() + " " +Thread.activeCount());
    }

    System.out.println(test.inc);
  }
}

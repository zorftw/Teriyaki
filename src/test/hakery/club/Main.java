package test.hakery.club;

import hakery.club.Teriyaki;
import hakery.club.event.Callable;
import test.hakery.club.events.MyCancellableEvent;
import test.hakery.club.events.MyCustomEvent;

public class Main {

    public static void main(String[] args) {
        Teriyaki.getInstance().register(event -> {
            if (event instanceof MyCancellableEvent) {
                MyCancellableEvent cancellableEvent = (MyCancellableEvent) event;

                System.out.printf("Event called, state: %b\n", cancellableEvent.cancelled());
            }

            if (event instanceof MyCustomEvent) {
                MyCustomEvent customEvent = (MyCustomEvent) event;

                System.out.printf("Event called, with ms: %d\n", customEvent.getMs());
            }
        });

        new Test();

        new Thread(() -> {
            while (true) {
                try {
                    Teriyaki.getInstance().call(new MyCancellableEvent());

                    Teriyaki.getInstance().call(new MyCustomEvent(System.currentTimeMillis()));

                    Thread.sleep(500);

                    Teriyaki.getInstance().push(new MyCustomEvent(System.currentTimeMillis()));

                    Thread.sleep(250);

                    Teriyaki.getInstance().push(new MyCustomEvent(System.currentTimeMillis()));

                    Thread.sleep(250);

                    Teriyaki.getInstance().push(new MyCustomEvent(System.currentTimeMillis()));

                    Thread.sleep(500);

                    Teriyaki.getInstance().pop();

                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

class Test {
    public Test() {
        Teriyaki.getInstance().register(this);
    }

    @Callable
    public void onEvent(MyCustomEvent event) {
        System.out.printf("Called event with ms: %d\n", event.getMs());
    }
}

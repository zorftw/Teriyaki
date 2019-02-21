package hakery.club;

import hakery.club.event.Callable;
import hakery.club.event.Event;
import hakery.club.listener.Listener;
import javafx.util.Pair;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Teriyaki {

    private static Teriyaki instance = new Teriyaki();

    /**
     * Registered Stuff
     */
    private ArrayList<Pair<Object, Method>> registeredObjects;
    private ArrayList<Listener> registeredListeners;

    /**
     * Stack of events to be released then Teriyaki#pop is called
     */
    private ArrayList<Event> stack;

    /* private constructor */
    private Teriyaki() {
        this.registeredObjects = new ArrayList<>();
        this.registeredListeners = new ArrayList<>();
        this.stack = new ArrayList<>();
    }

    /* return instance of Teriyaki */
    public static Teriyaki getInstance() {
        return instance;
    }

    /**
     * Pop the current stack of events, therefore calling them
     */
    public void pop() {
        this.stack.forEach(event -> this.call(event));
        this.stack.clear();
    }

    /**
     * Push an event to the stack
     *
     * @param event
     */
    public void push(Event event) {
        /* if SAME event is already on the stack */
        if (stack.stream().anyMatch(event1 -> event1 == event))
            return;

        stack.add(event);
    }

    /**
     * Register an object to the registry
     *
     * @param obj The object to be registered
     */
    public void register(Object obj) {
        Arrays.stream(obj.getClass().getMethods()).forEach(m -> {
            m.setAccessible(true);

            /** skip this iteration if it doesn't match our criteria **/
            if (m.getParameterTypes().length != 1 || !m.isAnnotationPresent(Callable.class))
                return;

            if (!registered(obj, m)) {
                this.registeredObjects.add(new Pair<>(obj, m));
            }
        });
    }

    /**
     * Register a listener
     *
     * @param listener the listener to be registered
     */
    public void register(Listener listener) {
        if (this.registeredListeners.contains(listener))
            return;

        this.registeredListeners.add(listener);
    }

    /**
     * Unregister a listener
     *
     * @param listener the listener to be unregistered
     */
    public void unregister(Listener listener) {
        if (!this.registeredListeners.contains(listener))
            return;

        new Thread(() -> {
            Iterator<Listener> it = this.registeredListeners.iterator();

            while (it.hasNext()) {
                Listener tm = it.next();

                if (listener.equals(tm))
                    it.remove();
            }
        }).start();
    }

    /**
     * Unregister an object and all its methods
     *
     * @param obj The object to be unregistered
     */
    public void unregister(Object obj) {
        new Thread(() -> {
            Iterator<Pair<Object, Method>> iterator = this.registeredObjects.iterator();

            while (iterator.hasNext()) {
                Pair<Object, Method> pair = iterator.next();

                if (pair.getKey() == obj)
                    iterator.remove();
            }
        }).start();
    }

    /**
     * Call an event
     */
    public void call(Event call) {
        this.registeredObjects.forEach(pair -> {
            try {
                if (pair.getValue().getParameterTypes()[0] == call.getClass()) {
                    pair.getValue().invoke(pair.getKey(), call);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        this.registeredListeners.forEach(listener -> listener.call(call));
    }

    /**
     * @param obj The associated object
     * @param m   The method of the object
     * @return true when the object & method are registered
     */
    public boolean registered(Object obj, Method m) {
        for (Pair<Object, Method> pair : this.registeredObjects) {
            if (pair.getKey() == obj)
                if (pair.getValue() == m)
                    return true;
        }
        return false;
    }
}

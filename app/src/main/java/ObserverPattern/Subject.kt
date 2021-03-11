package ObserverPattern

//观察者模式
class Subject {

    var state: Int = Int.MAX_VALUE
        set(value) {
            field = value
            notifyAllObserver()
        }

    private val observers = ArrayList<Observer>()

    fun attach(observer: Observer) {
        observers.add(observer)
    }

    fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    fun notifyAllObserver() {
        observers.forEach {
            it.update()
        }
    }
}

//使用
//Subject subject = new Subject();
//
//new HexaObserver(subject);
//new OctalObserver(subject);
//new BinaryObserver(subject);
//
//System.out.println("First state change: 15");
//subject.setState(15);
//System.out.println("Second state change: 10");
//subject.setState(10);
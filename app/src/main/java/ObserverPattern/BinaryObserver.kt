package ObserverPattern

class BinaryObserver(subject: Subject) : Observer() {

    init {
        this.subject = subject
        subject.attach(this)
    }

    override fun update() {

    }
}
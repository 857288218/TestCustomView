package DesignMode.ObserverPattern

abstract class Observer {

    protected var subject: Subject? = null
    abstract fun update()
}
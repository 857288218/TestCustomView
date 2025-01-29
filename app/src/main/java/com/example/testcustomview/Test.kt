package com.example.testcustomview

class InitializeSDKTask : Task {
    override fun execute() {
        println("Initializing SDK...")
    }

    override fun getDependencies(): List<Task> = emptyList()
}


class LoadUserDataTask(
    private val dependencies: List<Task>
) : Task {
    override fun execute() {
        println("Loading user data...")
    }

    override fun getDependencies(): List<Task> = dependencies
}

class SetupUITask(
    private val dependencies: List<Task>
) : Task {
    override fun execute() {
        println("Setting up UI...")
    }

    override fun getDependencies(): List<Task> = dependencies
}

class DeviceIdInitTask(
    val dependencies: List<Task>
) : Task {
    override fun execute() {
        println("DeviceIdInitTask UI...")
    }

    override fun getDependencies(): List<Task> = dependencies
}

// 使用示例
fun main() {
    val scheduler = TaskScheduler()
    // 一些初始化任务
    val sdkTask = InitializeSDKTask()
    val deviceIdTask = DeviceIdInitTask(arrayListOf(sdkTask))
    val userDataTask = LoadUserDataTask(arrayListOf(sdkTask, deviceIdTask))
    val uiTask = SetupUITask(arrayListOf(userDataTask))

    scheduler.addTask(deviceIdTask)
    scheduler.addTask(uiTask)
    scheduler.addTask(userDataTask)
    scheduler.addTask(sdkTask)


    val resultList = scheduler.getExecuteList()
    //最终resultList输出：InitializeSDKTask，DeviceIdInitTask，LoadUserDataTask，SetupUITask
}

interface Task {
    fun execute()

    fun getDependencies(): List<Task>
}

class TaskScheduler {
    private val taskList = ArrayList<Task>()
    fun addTask(task: Task) {
        // TODO 需要编写
        taskList.add(task)
    }

    fun getExecuteList(): List<Task> {
        // TODO 需要编写.返回一个符合任务依赖条件的列表
        val result = ArrayList<Task>()
        val hashMap = HashMap<Task, Int>()
        for (task in taskList) {
            hashMap[task] = task.getDependencies().size
        }
        val iterator = hashMap.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.value == 0) {
                result.add(next.key)
            }
            val iteratorInner = hashMap.iterator()
            while (iteratorInner.hasNext()) {
                val nextInner = iteratorInner.next()
                if (nextInner.key.getDependencies().contains(next.key)) {
                    hashMap[nextInner.key] = (hashMap[nextInner.key] ?: 0) - 1
                }
            }
        }
        return result
    }
}

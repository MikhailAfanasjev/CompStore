package com.example.compstore.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.compstore.db.Store
import com.example.compstore.worker.StoreRememberWorker

// BroadcastReceiver, который реагирует на системные события, такие как перезагрузка устройства
class Receiver: BroadcastReceiver(){

    //    // Метод вызывается при получении соответствующего события (например, после перезагрузки устройства)
//    override fun onReceive(context: Context?, intent: Intent?) {
//        // Проверяем, не является ли это событие завершением загрузки системы (BOOT_COMPLETED)
//        if (intent != null) {
//            if (Intent.ACTION_BOOT_COMPLETED == intent.action){
//                // Запускаем фоновую задачу в отдельном потоке для планирования задач
//                CoroutineScope(Dispatchers.IO).launch {
//                    if (context != null) {
//                        scheduleTasks(context) // Планируем все задачи после перезагрузки
//                    }
//                }
//            }
//        }
//    }
//
//    private suspend fun scheduleTasks(context: Context) {
//        // Получаем инстанс базы данных и через taskDao() извлекаем список задач
//        TaskDatabase.getInstance(context)?.taskDao()?.getTasks()?.first()?.forEach { task ->
//            try {
//                // Рассчитываем время для задачи и проверяем, что оно в будущем
//                calculateTime(task).takeIf { it > System.currentTimeMillis() }?.let {
//                    // Если время в будущем, планируем напоминание для задачи
//                    scheduleTaskReminder(context, task, it)
//                }
//            } catch (e: Exception) {
//            }
//        }
//    }
//
//
//    // Метод для расчета времени срабатывания задачи на основе даты и времени задачи
//    private fun calculateTime(task: Task): Long {
//        // Разделяем строку времени и даты на части (часы, минуты, дни, месяцы и годы)
//        val timeParts = task.time.split(":").map { it.toInt()}
//        val dateParts = task.date.split("/").map { it.toInt()}
//        // Настраиваем объект Calendar с указанной датой и временем задачи
//        val calendar = Calendar.getInstance().apply {
//            set(Calendar.YEAR, dateParts[2])
//            set(Calendar.MONTH, dateParts[1]-1) // Месяцы в Calendar начинаются с 0
//            set(Calendar.DAY_OF_MONTH, dateParts[0])
//            set(Calendar.HOUR_OF_DAY, timeParts[0])
//            set(Calendar.MINUTE, timeParts[1])
//            set(Calendar.SECOND, 0) // Сбрасываем секунды на 0
//            set(Calendar.MILLISECOND, 0) // Сбрасываем миллисекунды на 0
//        }
//        return calendar.timeInMillis // Возвращаем рассчитанное время в миллисекундах
//    }
//
//    // Метод для планирования задачи с помощью WorkManager
//    private fun scheduleTaskReminder(context: Context, task: Task, triggerTime: Long){
//        // Создаем данные для WorkManager (передаем имя задачи)
//        val data = workDataOf("task_name" to task.name)
//        // Создаем одноразовый запрос для TaskRememberWorker с задержкой до времени задачи
//        val workRequest = OneTimeWorkRequestBuilder<StoreRememberWorker>()
//            .setInitialDelay(triggerTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS) // Устанавливаем задержку до времени задачи
//            .setInputData(data) // Передаем данные (имя задачи)
//            .build()
//        // Добавляем запрос в очередь WorkManager
//        WorkManager.getInstance(context).enqueue(workRequest)
//    }
    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("Not yet implemented")
    }
}
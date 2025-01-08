package src.temp

import src.sealed.Response
import java.lang.management.ManagementFactory
import java.lang.management.ThreadMXBean
import kotlin.system.measureTimeMillis

class TempClazz {
    fun handleResponse(response: Response) {
        when (response) {
            is Response.Success<*> -> println("성공: ${response.data}")
            is Response.Error<*> -> println("에러: ${response.errorMessage}")
            Response.Loading -> println("로딩중..")
        }
        measureTimeMillis {

        }
    }

    fun measureMemoryAndCpu(task: () -> Unit) {
        measureTimeMillis {

        }
        val runtime = Runtime.getRuntime()
        val threadMXBean: ThreadMXBean = ManagementFactory.getThreadMXBean()

        val initialMemory = runtime.totalMemory() - runtime.freeMemory()
        val initialCpuTime = threadMXBean.currentThreadCpuTime

        task()

        val finalMemory = runtime.totalMemory() - runtime.freeMemory()
        val finalCpuTime = threadMXBean.currentThreadCpuTime

        val memoryUsed = finalMemory - initialMemory
        val cpuTimeUsed = finalCpuTime - initialCpuTime

        println("Memory used: $memoryUsed bytes")
        println("CPU time used: $cpuTimeUsed ns")
    }


}
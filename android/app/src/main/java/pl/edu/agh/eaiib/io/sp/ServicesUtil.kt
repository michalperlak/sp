package pl.edu.agh.eaiib.io.sp

import java.util.concurrent.ConcurrentHashMap


object ServicesUtil {
    private val services = ConcurrentHashMap<String, Any>()

    fun <T : Any> getService(serviceClass: Class<T>): T {
        val serviceClassName = serviceClass.canonicalName ?: throw IllegalArgumentException("Service class does not have canonical name")
        val service = services[serviceClassName] ?: throw IllegalArgumentException("Service for class: $serviceClassName not registered")
        return service as T
    }

    fun unregisterService(service: Any) {
        val serviceClass = service.javaClass.canonicalName ?: throw IllegalArgumentException("Service class does not have canonical name")
        services.remove(serviceClass)
    }

    fun registerService(service: Any) {
        val serviceClass = service.javaClass.canonicalName ?: throw IllegalArgumentException("Service class does not have canonical name")
        val value = services.putIfAbsent(serviceClass, service)

        if (value != null) {
            throw IllegalStateException("Service with class $serviceClass already registered")
        }
    }
}
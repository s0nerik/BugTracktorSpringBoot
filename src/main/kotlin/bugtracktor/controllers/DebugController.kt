package bugtracktor.controllers

import bugtracktor.SampleDataInitializer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DebugController(
        val sampleDataInitializer: SampleDataInitializer
) {
    @PostMapping("/reset")
    fun reset() {
        sampleDataInitializer.init(true)
    }
}

package bugtracktor.models

import com.fasterxml.jackson.databind.ObjectMapper

data class CreateProjectData(val name: String, val shortDescription: String, val fullDescription: String?)
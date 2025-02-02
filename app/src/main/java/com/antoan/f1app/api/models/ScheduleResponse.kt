import com.antoan.f1app.api.models.Schedule
import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("Schedule")
    val schedule: Schedule
)
package com.pbear.mainfunctionalserver.main.easycalendar

import com.pbear.mainfunctionalserver.common.util.NoArg

@NoArg
data class ReqPostCalendarTemplate(
    val title: String,
    val summary: String,
    val type: CalendarTemplateType,
    val properties: Map<String, Any>?,
)

@NoArg
data class ResCalendarTemplate(
    val id: Long,
    val title: String,
    val summary: String,
    val type: CalendarTemplateType,
    val properties: Map<String, Any>?,
)

@NoArg
data class ReqPostCalendarEvents(
    val summary: String,
    val startTime: String,
    val endTime: String,
)
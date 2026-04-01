package com.example.proyectoreloj.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel that manages dynamic motivational messages for the Home and Activity screens.
 *
 * In production, these messages would be driven by the user's mood, treatment progress,
 * and other contextual data. For now, it cycles through a list of predefined Spanish messages
 * as a placeholder/mock for that dynamic behavior.
 */
class MotivationalViewModel : ViewModel() {

    // ── Home Screen messages (general reminders & mood) ──────────────────────

    private val homeMessages: List<String> = listOf(
        "Sal a dar un paseo de 15 minutos por el parque.",
        "¡Lo estás haciendo genial! Sigue así.",
        "Recuerda beber agua y mantenerte hidratado hoy.",
        "Un pequeño paseo al día hace una gran diferencia.",
        "Tu cuerpo se está recuperando, dale el tiempo que necesita.",
        "Tómate un respiro si lo necesitas, tú marcas el ritmo."
    )

    private val _currentHomeMessageIndex = MutableStateFlow(0)

    private val _currentHomeMessage = MutableStateFlow(homeMessages[0])
    val currentHomeMessage: StateFlow<String> = _currentHomeMessage.asStateFlow()

    // ── Activity Screen messages (progress-based) ───────────────────────────

    private val activityMessages: List<String> = listOf(
        "¡Ya vas por la mitad! Puedes salir al exterior a dar un paseo, pero recuerda: ¡solo 15 minutos!",
        "¡Excelente progreso! Estás cumpliendo todos tus objetivos del tratamiento.",
        "Un día más superado. ¡Sigue con esta buena racha!",
        "¡Ya casi completas tu objetivo de hoy!"
    )

    private val _currentActivityMessageIndex = MutableStateFlow(0)

    private val _currentActivityMessage = MutableStateFlow(activityMessages[0])
    val currentActivityMessage: StateFlow<String> = _currentActivityMessage.asStateFlow()

    // ── Treatment day tracking ──────────────────────────────────────────────

    private val _treatmentDay = MutableStateFlow(8)
    val treatmentDay: StateFlow<Int> = _treatmentDay.asStateFlow()

    // ── Public API ──────────────────────────────────────────────────────────

    /**
     * Advances to the next home motivational message, cycling back to the start.
     * In production this would be triggered by mood changes, time of day, etc.
     */
    fun nextHomeMessage() {
        val nextIndex = (_currentHomeMessageIndex.value + 1) % homeMessages.size
        _currentHomeMessageIndex.value = nextIndex
        _currentHomeMessage.value = homeMessages[nextIndex]
    }

    /**
     * Advances to the next activity motivational message, cycling back to the start.
     * In production this would be triggered by treatment progress milestones.
     */
    fun nextActivityMessage() {
        val nextIndex = (_currentActivityMessageIndex.value + 1) % activityMessages.size
        _currentActivityMessageIndex.value = nextIndex
        _currentActivityMessage.value = activityMessages[nextIndex]
    }

    /**
     * Returns all home messages (useful for previewing the full list).
     */
    fun getAllHomeMessages(): List<String> = homeMessages

    /**
     * Returns all activity messages (useful for previewing the full list).
     */
    fun getAllActivityMessages(): List<String> = activityMessages
}

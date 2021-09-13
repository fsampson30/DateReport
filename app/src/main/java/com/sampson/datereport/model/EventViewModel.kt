package com.sampson.datereport.model

import androidx.lifecycle.*
import com.sampson.datereport.controller.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    val allEvents: LiveData<MutableList<Event>> = repository.allEvents.asLiveData()
    val onTimeEvents : LiveData<MutableList<Event>> = repository.onTimeEvents.asLiveData()
    val warningEvents : LiveData<MutableList<Event>> = repository.warningEvents.asLiveData()
    val expiredEvents : LiveData<MutableList<Event>> = repository.expiredEvents.asLiveData()

    fun insert(event: Event) = viewModelScope.launch {
        repository.insert(event)
    }

    fun delete() = viewModelScope.launch {
        repository.delete()
    }

    fun deleteEvent(event: Event ) = viewModelScope.launch {
        repository.deleteEvent(event)
    }

}

class EventViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return EventViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
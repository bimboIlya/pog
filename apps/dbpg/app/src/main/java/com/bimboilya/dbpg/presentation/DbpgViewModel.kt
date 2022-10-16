package com.bimboilya.dbpg.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimboilya.common.ktx.jvm.combineState
import com.bimboilya.dbpg.domain.entity.Passengers
import com.bimboilya.dbpg.domain.entity.Segment
import com.bimboilya.dbpg.domain.entity.SubParams
import com.bimboilya.dbpg.domain.entity.TripClass
import com.bimboilya.dbpg.domain.repository.SubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@HiltViewModel
class DbpgViewModel @Inject constructor(
    private val subRepo: SubRepository,
) : ViewModel() {

    private val originCity = MutableStateFlow("")
    private val originAirport = MutableStateFlow("")
    private val destinationCity = MutableStateFlow("")
    private val destinationAirport = MutableStateFlow("")
    private val isRoundTrip = MutableStateFlow(false)
    private val adults = MutableStateFlow(1)
    private val children = MutableStateFlow(0)
    private val infants = MutableStateFlow(0)
    private val tripClass = MutableStateFlow(TripClass.ECONOMY)
    private val isInputValidated = MutableStateFlow(false)

    val state = viewModelScope.combineState(
        originCity,
        originAirport,
        destinationCity,
        destinationAirport,
        isRoundTrip,
        adults,
        children,
        infants,
        tripClass,
        isInputValidated,
        ::DbpgState,
    )

    private val _isSubscribedEvent = Channel<Boolean>(Channel.UNLIMITED)
    val isSubscribedEvent = _isSubscribedEvent.receiveAsFlow()

    init {
        combine(
            originCity,
            destinationCity,
            adults,
            ::isValid
        )
            .onEach(isInputValidated::tryEmit)
            .launchIn(viewModelScope)
    }

    private fun isValid(originCity: String, destinationCity: String, adults: Int): Boolean =
        originCity.isNotBlank() && destinationCity.isNotBlank() && adults != 0

    fun onOriginCityChange(newValue: String) {
        originCity.value = newValue
    }

    fun onOriginAirportChange(newValue: String) {
        originAirport.value = newValue
    }

    fun onDestinationCityChange(newValue: String) {
        destinationCity.value = newValue
    }

    fun onDestinationAirportChange(newValue: String) {
        destinationAirport.value = newValue
    }

    fun onRoundTripChange(newValue: Boolean) {
        isRoundTrip.value = newValue
    }

    fun onAdultsChange(newValue: String) {
        adults.value = newValue.toIntOrNull() ?: 0
    }

    fun onChildrenChange(newValue: String) {
        children.value = newValue.toIntOrNull() ?: 0
    }

    fun onInfantsChange(newValue: String) {
        infants.value = newValue.toIntOrNull() ?: 0
    }

    fun onTripClassSelection(newValue: TripClass) {
        tripClass.value = newValue
    }

    fun save() {
        viewModelScope.launch {
            val params = createParams()
                .also { Timber.d(it.toString()) }

            subRepo.create(params)
        }
    }

    @OptIn(ExperimentalTime::class)
    fun doesSubscriptionExist() {
        viewModelScope.launch {
            val (doesExist, time) = measureTimedValue {
                subRepo.getByParams(createParams()) != null
            }

            Timber.d("POOP | time: ${time.inWholeMilliseconds}ms")

            _isSubscribedEvent.trySend(doesExist)
        }
    }

    private fun createParams(): SubParams =
        SubParams(
            segments = buildList {
                Segment(
//                    date = LocalDate.now(),
                    originCityIata = requireNotEmpty(originCity.value),
                    originAirportIata = originAirport.value.orNullIfEmpty(),
                    destinationCityIata = requireNotEmpty(destinationCity.value),
                    destinationAirportIata = destinationAirport.value.orNullIfEmpty()
                ).let(::add)

                if (isRoundTrip.value) {
                    Segment(
//                        date = LocalDate.now().plusDays(3),
                        originCityIata = requireNotEmpty(destinationCity.value),
                        originAirportIata = destinationAirport.value.orNullIfEmpty(),
                        destinationCityIata = requireNotEmpty(originCity.value),
                        destinationAirportIata = originAirport.value.orNullIfEmpty()
                    ).let(::add)
                }
            },
            passengers = Passengers(
                adults = requireAboveZero(adults.value),
                children = children.value,
                infants = infants.value,
            ),
            tripClass = tripClass.value,
        )

    private fun String.orNullIfEmpty(): String? =
        ifEmpty { null }

    private fun requireNotEmpty(text: String): String {
        require(text.isNotEmpty())
        return text
    }

    private fun requireAboveZero(number: Int): Int {
        require(number > 0)
        return number
    }
}

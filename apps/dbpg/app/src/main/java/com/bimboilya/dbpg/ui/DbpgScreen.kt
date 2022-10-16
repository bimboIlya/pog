package com.bimboilya.dbpg.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bimboilya.common.ktx.android.collectAsStateWithLifecycle
import com.bimboilya.common.ktx.android.collectInComposition
import com.bimboilya.dbpg.domain.entity.TripClass
import com.bimboilya.dbpg.domain.entity.TripClass.BUSINESS
import com.bimboilya.dbpg.domain.entity.TripClass.ECONOMY
import com.bimboilya.dbpg.presentation.DbpgState
import com.bimboilya.dbpg.presentation.DbpgViewModel

@Composable
fun DbpgScreen() {
    val viewModel = viewModel<DbpgViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    viewModel.isSubscribedEvent.collectInComposition {
        val text = if (it) "yep" else "nope"
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Content(
            state,
            viewModel::onOriginCityChange,
            viewModel::onOriginAirportChange,
            viewModel::onDestinationCityChange,
            viewModel::onDestinationAirportChange,
            viewModel::onRoundTripChange,
            viewModel::onAdultsChange,
            viewModel::onChildrenChange,
            viewModel::onInfantsChange,
            viewModel::onTripClassSelection,
            viewModel::save,
            viewModel::doesSubscriptionExist,
        )
    }
}

@Composable
private fun Content(
    state: DbpgState,
    onOriginCityChange: (String) -> Unit,
    onOriginAirportChange: (String) -> Unit,
    onDestinationCityChange: (String) -> Unit,
    onDestinationAirportChange: (String) -> Unit,
    onRoundTripChange: (Boolean) -> Unit,
    onAdultsChange: (String) -> Unit,
    onChildrenChange: (String) -> Unit,
    onInfantsChange: (String) -> Unit,
    onTripClassSelection: (TripClass) -> Unit,
    saveSubParams: () -> Unit,
    doesSubscriptionExist: () -> Unit,
) {
    Row(Modifier.fillMaxWidth().padding(top = 16.dp)) {
        OutlinedTextField(
            value = state.originCity,
            onValueChange = onOriginCityChange,
            Modifier.weight(1f).padding(end = 4.dp),
            label = { Text("Origin city") }
        )
        OutlinedTextField(
            value = state.originAirport,
            onValueChange = onOriginAirportChange,
            Modifier.weight(1f).padding(start = 4.dp),
            label = { Text("Origin airport") }
        )
    }

    Row(Modifier.fillMaxWidth().padding(top = 16.dp)) {
        OutlinedTextField(
            value = state.destinationCity,
            onValueChange = onDestinationCityChange,
            Modifier.weight(1f).padding(end = 4.dp),
            label = { Text("Destination city") }
        )
        OutlinedTextField(
            value = state.destinationAirport,
            onValueChange = onDestinationAirportChange,
            Modifier.weight(1f).padding(start = 4.dp),
            label = { Text("Destination airport") })
    }

    Row(Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        OutlinedTextField(
            value = state.adults.toString(),
            onValueChange = onAdultsChange,
            Modifier.weight(1f).padding(end = 4.dp),
            label = { Text("Adults") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        OutlinedTextField(
            value = state.children.toString(),
            onValueChange = onChildrenChange,
            Modifier.weight(1f).padding(horizontal = 4.dp),
            label = { Text("Children") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        OutlinedTextField(
            value = state.infants.toString(),
            onValueChange = onInfantsChange,
            Modifier.weight(1f).padding(start = 4.dp),
            label = { Text("Infants") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }

    Row(
        Modifier.fillMaxWidth().padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            Modifier.noRippleClickable { onRoundTripChange(!state.isRoundTrip) },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(checked = state.isRoundTrip, onCheckedChange = onRoundTripChange)
            Text("Is round trip?")
        }

        OutlinedButton(onClick = doesSubscriptionExist, enabled = state.isInputValidated) {
            Text("Is subscribed?")
        }
    }

    Row(
        Modifier.fillMaxWidth().padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            Modifier.noRippleClickable { onTripClassSelection(ECONOMY) },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(selected = state.tripClass == ECONOMY, onClick = { onTripClassSelection(ECONOMY) })
            Text("Economy")
        }
        Row(
            Modifier
                .padding(start = 8.dp)
                .noRippleClickable { onTripClassSelection(BUSINESS) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = state.tripClass == BUSINESS, onClick = { onTripClassSelection(BUSINESS) })
            Text("Business")
        }
    }

    Button(onClick = { saveSubParams() }, enabled = state.isInputValidated, modifier = Modifier.fillMaxWidth()) {
        Text("SAVE")
    }
}

private fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    composed {
        clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick,
        )
    }

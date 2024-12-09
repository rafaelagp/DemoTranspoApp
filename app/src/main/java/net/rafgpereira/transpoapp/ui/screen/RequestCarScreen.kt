package net.rafgpereira.transpoapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RequestCarScreen(modifier: Modifier,) {
    var userId by remember { mutableStateOf("") }
    var originAddress by remember { mutableStateOf("") }
    var destinationAddress by remember { mutableStateOf("") }
    var costEstimate by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.Start,
        ) {
            TextField(
                modifier = modifier.width(200.dp),
                value = userId,
                onValueChange = { userId = it },
                label = { Text(text = "Id do Usuário",) },
            )
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = originAddress,
                onValueChange = { originAddress = it },
                label = { Text(text = "Endereço de Origem",) },
            )
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = destinationAddress,
                onValueChange = { destinationAddress = it },
                label = { Text(text = "Endereço de Destino",) },
            )
            CostEstimateFieldAndButton(
                modifier = modifier,
                costEstimate = costEstimate,
            )
            //TODO add debounce
            //TODO add on-going request animation
            //TODO implement request and navigation to next screen
            Button(
                modifier = modifier
                    .padding(PaddingValues(0.dp, 8.dp, 0.dp, 0.dp))
                    .align(Alignment.CenterHorizontally),
                onClick = {},
            ) { Text("Solicitar",) }
        }
    }
}

@Composable
fun CostEstimateFieldAndButton(
    modifier: Modifier,
    costEstimate: String,
) = Row(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    TextField(
        modifier = modifier.width(200.dp),
        value = costEstimate,
        onValueChange = {},
        label = { Text(text = "Estimativa de Custo",) },
        enabled = false,
    )
    //TODO add debounce
    //TODO add on-going request animation
    //TODO implement calculation request
    FilledTonalButton(
        modifier = modifier.fillMaxWidth(),
        onClick = {},
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(
            text = "Calcular",
            maxLines = 1,
        )
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun RequestCarScreenPreview() = RequestCarScreen(Modifier)
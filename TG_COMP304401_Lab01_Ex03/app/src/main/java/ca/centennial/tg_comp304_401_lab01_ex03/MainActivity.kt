// Tristan G - 301374901 - 2026-02-06
// COMP 304 - Lab Assignment 01 - Exercise 03 - Pizza Order App

package ca.centennial.tg_comp304_401_lab01_ex03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.centennial.tg_comp304_401_lab01_ex03.ui.theme.TG_COMP304401_Lab01_Ex03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TG_COMP304401_Lab01_Ex03Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PizzaApp()

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaApp() {

    // Popup variables
    var showPop by remember { mutableStateOf(false) }
    var summary by remember { mutableStateOf("") }

    // Dropdown variables
    var selectedSize by remember { mutableStateOf("Select Pizza Size") }
    var expanded by remember { mutableStateOf(false) }
    val sizes = listOf("Small", "Medium", "Large", "X-Large")

    // Toppings for checkboxes (String List)
    val toppingsList =
        listOf("Pepperoni", "Mushrooms", "Onions", "Sausage", "Olives", "Extra Cheese")
    val selectedToppings = remember { mutableStateListOf<String>() }

    // Delivery options for radio buttons
    val deliveryOptions = listOf("Home Delivery", "Walk-In", "Pick Up")
    var selectedDelivery by remember { mutableStateOf(deliveryOptions[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pizza Order Page") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        // Main column wrap
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(14.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Dropdown
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Button(onClick = { expanded = true }) {
                    Text(selectedSize, fontWeight = FontWeight.Bold)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    sizes.forEach { size ->
                        DropdownMenuItem(
                            text = { Text(size) },
                            onClick = {
                                selectedSize = size
                                expanded = false
                            }
                        )
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.padding(top = 16.dp), thickness = 5.dp)

            // Checkboxes
            Text(
                "Choose your favorite toppings:",
                modifier = Modifier.padding(top = 16.dp),
                fontWeight = FontWeight.Bold
            )
            toppingsList.forEach { topping ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = selectedToppings.contains(topping),
                        onCheckedChange = {
                            if (it) selectedToppings.add(topping) else selectedToppings.remove(
                                topping
                            )
                        }
                    )
                    Text(topping)
                }
            }
            HorizontalDivider(modifier = Modifier.padding(top = 16.dp), thickness = 5.dp)


            // Radiobuttons
            Text(
                "Select Delivery Options:",
                modifier = Modifier.padding(top = 16.dp),
                fontWeight = FontWeight.Bold
            )
            deliveryOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedDelivery),
                            onClick = { selectedDelivery = text },
                            role = Role.RadioButton // For accessibility
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedDelivery == text),
                        onClick = null,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Text(text)
                }
            }
            HorizontalDivider(modifier = Modifier.padding(top = 16.dp), thickness = 5.dp)


            // Place Order
            Button(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                onClick = {
                    val toppingsMsg =
                        if (selectedToppings.isEmpty()) {
                            "No toppings"
                        } else {
                            selectedToppings.joinToString(
                                ", "
                            )
                        }
                    summary =
                        "Size: $selectedSize\nToppings: $toppingsMsg\nDelivery: $selectedDelivery"

                    // Show popup box
                    showPop = true
                }
            ) {
                Text("Place Order")
            }

            if (showPop) {
                AlertDialog(
                    onDismissRequest = { showPop = false },
                    title = { Text("Order Sent to Kitchen") },
                    text = { Text(summary, lineHeight = 24.sp) },
                    confirmButton = {
                        TextButton(onClick = {
                            showPop = false
                            summary = ""
                            selectedSize = "Select Pizza Size"
                            selectedToppings.clear()
                            selectedDelivery = deliveryOptions[0]

                        }) {
                            Text("Confirm")
                        }
                    })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PizzaAppPreview() {
    TG_COMP304401_Lab01_Ex03Theme {
        PizzaApp()
    }
}
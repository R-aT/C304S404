// Tristan Grist 301374901 Test 01 - Second Activity

package ca.centennial.tristang_comp304_401_test01


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ca.centennial.tristang_comp304_401_test01.data.Contact
import ca.centennial.tristang_comp304_401_test01.data.Favourite
import ca.centennial.tristang_comp304_401_test01.ui.theme.TristanG_COMP304_401_Test01Theme
import kotlinx.coroutines.launch

class GristActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TristanG_COMP304_401_Test01Theme {
                ContactView()

            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactView() {
    val context = LocalContext.current

    // Snackbar state and scope
    val snackbarState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Dropdown
    var expanded by remember { mutableStateOf(false) }

    // Data input fields
    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Business") }
    var favourite by remember { mutableStateOf(Favourite.Normal) }
    val contactList = remember { mutableStateListOf<Contact>() }

    Scaffold(
        // Snackbar and floating ADD CONTACT button
        snackbarHost = { SnackbarHost(snackbarState) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Validate ID is between 1001 and 9999 (4 digits) and phone number is 10 digits
                if (id.toIntOrNull() in 1001 until 10000 && phone.length == 10) {
                    val newC = Contact(
                        id.toIntOrNull()!!,
                        name.trim(),
                        phone,
                        email.trim(),
                        type,
                        favourite
                    )
                    contactList.add(newC)
                    scope.launch {
                        snackbarState
                            .showSnackbar(
                                message = "Added $type contact: ${newC.name} @ $email",
                                actionLabel = "OK",
                                duration = SnackbarDuration.Indefinite
                            )
                    }
                } else {
                    scope.launch {
                        snackbarState.showSnackbar("Invalid ID or Phone Number")
                    }
                }
            }) {
                Text(
                    "ADD CONTACT",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(124.dp)
                )
            }
        }
    ) { padding ->
        // Name,phone,email, type (business/work), favourite (dropdown)
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
        ) {
            Spacer(Modifier.height(38.dp))
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("ID (1001-9999)") })
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") })
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") })

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = type == "Business", onClick = { type = "Business" })
                Text("Business")
                RadioButton(selected = type == "Work", onClick = { type = "Work" })
                Text("Work")
            }

            // Dropdown to pick favourite from enum
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = favourite.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Favourite") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    Favourite.entries.map { it.name }.forEach { p ->
                        DropdownMenuItem(
                            text = { Text(p) },
                            onClick = { favourite = Favourite.valueOf(p); expanded = false })
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(contactList) { contact ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            "ID: ${contact.id}: ${contact.name} (${contact.type})" +
                                    "\nPhone: ${contact.phone} \nEmail: ${contact.email}" +
                                    "\nFavourite: ${contact.favourite}",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
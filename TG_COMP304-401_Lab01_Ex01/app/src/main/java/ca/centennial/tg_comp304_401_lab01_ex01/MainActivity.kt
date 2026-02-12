// Tristan G - 301374901 - 2026-02-06
// COMP 304 - Lab Assignment 01 - Exercise 01 - Loan Calculator App

package ca.centennial.tg_comp304_401_lab01_ex01

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.centennial.tg_comp304_401_lab01_ex01.ui.theme.TG_COMP304401_Lab01_Ex01Theme
import java.text.NumberFormat
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TG_COMP304401_Lab01_Ex01Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoanCalculator()

                }
            }
        }
    }
}

@Composable
fun LoanCalculator() {
    // Required context for toast message
    val context = LocalContext.current

    // Variables to hold values for fields
    var carPrice by remember { mutableStateOf("") }
    var downPay by remember { mutableStateOf("") }
    var interestRate by remember { mutableStateOf("") }
    var loanLengthYr by remember { mutableStateOf("") }
    var paymentMonthly by remember { mutableStateOf("$0.00") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Car Loan Calculator",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text("Monthly Payment", style = MaterialTheme.typography.headlineSmall)
        Text(
            paymentMonthly,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = carPrice,
            onValueChange = { carPrice = it },
            label = { Text("Car Price") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = downPay,
            onValueChange = { downPay = it },
            label = { Text("Down Payment") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = interestRate,
            onValueChange = { interestRate = it },
            label = { Text("Interest Rate (%)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = loanLengthYr,
            onValueChange = { loanLengthYr = it },
            label = { Text("Loan duration (Years)") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = {
                    if (carPrice.isEmpty() || downPay.isEmpty() || interestRate.isEmpty() || loanLengthYr.isEmpty()) {
                        Toast.makeText(context, "Please fill empty fields", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        // Avoid bad data types by converting to null
                        val down = downPay.toDoubleOrNull() ?: 0.0
                        val price = (carPrice.toDoubleOrNull() ?: 0.0) - down
                        val rate = ((interestRate.toDoubleOrNull() ?: 1200.0) / 100) / 12
                        val dur = (loanLengthYr.toDoubleOrNull() ?: 0.0) * 12
                        if (price <= 0 || dur <= 0.0 || rate >= 1) {
                            Toast.makeText(context, "Please enter valid data", Toast.LENGTH_LONG)
                                .show()
                        } else {
                            val payment = if (rate > 0) {
                                // Formula for amortization with compound interest
                                price * (rate * (1 + rate).pow(dur)) / ((1 + rate).pow(dur) - 1)
                            } else {
                                price / dur
                            }
                            // Format to local currency
                            val currencyFormat = NumberFormat.getCurrencyInstance()

                            if (down == 0.0) {
                                downPay = "0"
                            }
                            paymentMonthly = currencyFormat.format(payment)
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text("Calculate")
            }

            Button(
                onClick = {
                    // Provide clean fields
                    carPrice = ""
                    downPay = ""
                    interestRate = ""
                    loanLengthYr = ""
                    paymentMonthly = "$0.00"
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text("Clear")
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun LoanCalculatorScreenPreview() {
    TG_COMP304401_Lab01_Ex01Theme {
        LoanCalculator()
    }
}



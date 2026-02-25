// Tristan Grist 301374901 Test 01 - Main Activity

package ca.centennial.tristang_comp304_401_test01

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.centennial.tristang_comp304_401_test01.ui.theme.Blue
import ca.centennial.tristang_comp304_401_test01.ui.theme.Cyan
import ca.centennial.tristang_comp304_401_test01.ui.theme.Purple
import ca.centennial.tristang_comp304_401_test01.ui.theme.TristanG_COMP304_401_Test01Theme

class TristanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TristanG_COMP304_401_Test01Theme {
                MainView()

            }
        }
    }
}

@Composable
fun MainView() {
    val context = LocalContext.current
    val gradient = listOf(Purple, Blue, Cyan)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "MY BUSINESS CONTACTS",
            fontSize = 26.sp,
            minLines = 2,
            fontWeight = FontWeight.Bold,
            style = TextStyle(brush = Brush.linearGradient(colors = gradient))
        )

        IconButton(
            onClick = {
                context.startActivity(Intent(context, GristActivity::class.java))
            },
            modifier = Modifier.size(80.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.icon_contact),
                contentDescription = "Button to Contacts"
            )
        }
    }
}

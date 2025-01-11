package com.example.moviecollection.ui.screens.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.ui.navigation.NavigationRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KSuspendFunction2

const val TAG = "LOGIN"

@Composable
fun LoginScreen(
    navController: NavHostController,
    actions: LoginActions,
    state: LoginState,
    onLogin: KSuspendFunction2<String, String, Boolean>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.login_screen_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = state.username,
            onValueChange = actions::setUsername,
            label = { Text(text = stringResource(R.string.username_field_label))}
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = actions::setPassword,
            label = {  Text(text = stringResource(R.string.password_field_label))}
        )
        Button(
            onClick = {
                /*TODO ADD SNACKBARS FOR ERRORS*/
                if (state.canSubmit) {
                    val loginResult = runBlocking(Dispatchers.IO) {
                        onLogin(state.username, state.password)
                    }
                    if (loginResult) {
                        navController.navigate(NavigationRoute.Home.route)
                    } else {
                        Log.println(
                            Log.DEBUG,
                            TAG,
                            "Nope wrong credentials"
                        )
                    }
                } else {
                    Log.println(
                        Log.DEBUG,
                        TAG,
                        "Nope empty fields"
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.width(150.dp)
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(text = stringResource(R.string.login_button))
                Icon(
                    imageVector = Icons.Outlined.Login,
                    contentDescription = stringResource(R.string.login_button)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.signup_link_text),
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.signup_button),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        navController.navigate(NavigationRoute.Signup.route)
                    }
            )
        }
    }
}
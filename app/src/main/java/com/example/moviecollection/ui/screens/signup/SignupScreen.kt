package com.example.moviecollection.ui.screens.signup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.data.database.entities.User
import com.example.moviecollection.ui.navigation.NavigationRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KSuspendFunction1

const val TAG = "SIGNUP"

@Composable
fun SignupScreen(
    navController: NavHostController,
    actions: SignupActions,
    state: SignupState,
    onSignup: KSuspendFunction1<User, Boolean>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.signup_screen_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = state.username,
            onValueChange = actions::setUsername,
            label = { Text(text = stringResource(R.string.username_field_label)) }
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = actions::setPassword,
            label = {  Text(text = stringResource(R.string.password_field_label)) }
        )
        OutlinedTextField(
            value = state.email,
            onValueChange = actions::setEmail,
            label = {  Text(text = stringResource(R.string.email_field_label)) }
        )
        Button(
            onClick = {
                /*TODO ADD ALERT TO ELSE CLAUSES*/
                if (state.canSubmit) {
                    val signupResult = runBlocking(Dispatchers.IO) { onSignup(state.toUser()) }
                    if (signupResult) {
                        navController.navigate(NavigationRoute.Login.route)
                    } else {
                        Log.println(
                            Log.DEBUG,
                            TAG,
                            "Nope email already in use"
                        )
                    }
                } else {
                    Log.println(
                        Log.DEBUG,
                        TAG,
                        "Nope some fields are empty"
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
                Text(text = stringResource(R.string.signup_button))
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = stringResource(R.string.signup_button)
                )
            }
        }
    }
}
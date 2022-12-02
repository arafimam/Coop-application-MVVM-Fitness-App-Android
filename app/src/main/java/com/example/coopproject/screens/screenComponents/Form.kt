package com.example.coopproject.screens.screenComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.coopproject.R
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coopproject.ui.theme.AppThemeColor
import com.example.coopproject.ui.theme.FadedTextColor
import com.example.coopproject.ui.theme.LighterAppThemeColor
import com.example.coopproject.ui.theme.PADDING_SMALL

@Composable
fun UserForm(
    modifier: Modifier = Modifier,
    getEmailAddress: (String) -> Unit,
    getPassword: (String) -> Unit
){
    val emailAddress = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Email Address",
            color = Color.White,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = PADDING_SMALL)
        )
        TextField(
            modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(5.dp)),
            text = emailAddress.value,
            onTextChange = {emailAddress.value = it},
            icon = Icons.Default.Email,
            placeholder = "ABC@gmail.com")
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Password",
            color = Color.White,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = PADDING_SMALL)
        )
        PasswordTextField(
            modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(5.dp)),
            text = password.value,
            onTextChange = {password.value = it},
            placeholder = "Password")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    text: String,
    maxLine: Int = 1,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit = {},
    bgColor: Color = Color.White,
    textColor: Color = Color.Black,
    placeholder: String
){
    var passwordVisible = remember {
        mutableStateOf(false)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        //modifier = modifier.background(Color.White, RoundedCornerShape(5.dp)),
        modifier =  modifier,
        value = text,
        placeholder = {
            Text(text = placeholder)
        },
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(textColor = textColor, backgroundColor = bgColor,
            focusedIndicatorColor = LighterAppThemeColor, unfocusedIndicatorColor = AppThemeColor,
            focusedLabelColor = LighterAppThemeColor, unfocusedLabelColor = AppThemeColor
        ),
        maxLines = maxLine,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        trailingIcon = {
            if (passwordVisible.value){
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_visibility_24), contentDescription = "Password visible")
                }
            }
            else {
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_visibility_off_24), contentDescription = "Password visible")
                }
            }
            

        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()

    )
}




@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextField(
    modifier: Modifier = Modifier,
    text: String,
    maxLine: Int = 1,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit = {},
    bgColor: Color = Color.White,
    textColor: Color = Color.Black,
    icon: ImageVector,
    placeholder: String
){
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        //modifier = modifier.background(Color.White, RoundedCornerShape(5.dp)),
        modifier =  modifier,
        value = text,
        placeholder = {
          Text(text = placeholder)
        },
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(textColor = textColor, backgroundColor = bgColor,
            focusedIndicatorColor = LighterAppThemeColor, unfocusedIndicatorColor = AppThemeColor,
            focusedLabelColor = LighterAppThemeColor, unfocusedLabelColor = AppThemeColor
        ),
        maxLines = maxLine,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        trailingIcon = {
            Icon(imageVector = icon, contentDescription = "Profile Icon",
                tint = FadedTextColor
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun Preview(){
    UserForm(getEmailAddress = {}, getPassword = {})
}
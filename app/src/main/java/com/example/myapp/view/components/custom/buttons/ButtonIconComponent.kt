package com.example.myapp.view.components.custom.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.view.utils.ButtonIcon

@Composable
fun ButtonIconComposable(
    buttonIcon: ButtonIcon,
    onClick: () -> Unit = {},
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onSurface,
    enabled: Boolean = true
) {

    val windowSizeConstant = LocalWindowSizeConstant.current
    
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
    ) {
        when (buttonIcon) {
            is ButtonIcon.Vector -> {
                Icon(
                    imageVector = buttonIcon.imageVector,
                    contentDescription = contentDescription,
                    tint = tint,
                    modifier = Modifier.size(windowSizeConstant.iconPadding),
                )
            }
            is ButtonIcon.Resource -> {
                Icon(
                    painter = painterResource(id = buttonIcon.drawableRes),
                    contentDescription = contentDescription,
                    tint = tint,
                    modifier = Modifier.size(windowSizeConstant.iconPadding),
                )
            }
        }
    }
}
package com.example.myapp.view.components.custom.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.utils.ButtonIcon

@Composable
fun CustomOutlinedButton(
    modifier: Modifier = Modifier.width(MaterialTheme.customSpacing.xxxLarge),
    useSmallWidth: Boolean = false,
    @DrawableRes label: Int = 0,
    enabled: Boolean = true,
    onClick: () -> Unit,
    shape: RoundedCornerShape = RoundedCornerShape(MaterialTheme.customSpacing.customNormal),
    icon: ButtonIcon? = null,
    contentDescription: String = "",
    tintColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    iconModifier: Modifier = Modifier.size(MaterialTheme.customSpacing.normalLarge),
) {

    val windowSizeConstant = LocalWindowSizeConstant.current

    OutlinedButton(
        onClick, shape = shape, enabled = enabled,
        modifier = if(useSmallWidth) Modifier.width(MaterialTheme.customSpacing.mediumLarge) else modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Show icon if provided
            icon?.let { buttonIcon ->
                when (buttonIcon) {
                    is ButtonIcon.Resource -> {
                        Icon(
                            painter = painterResource(id = buttonIcon.drawableRes),
                            contentDescription = contentDescription,
                            tint = tintColor,
                            modifier = iconModifier.size(windowSizeConstant.iconPadding)
                        )
                    }

                    is ButtonIcon.Vector -> {
                        Icon(
                            imageVector = buttonIcon.imageVector,
                            contentDescription = contentDescription,
                            tint = tintColor,
                            modifier = iconModifier.size(windowSizeConstant.iconPadding)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(MaterialTheme.customSpacing.small))
            }
            Text(
                text = stringResource(label),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

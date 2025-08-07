package com.example.myapp.view.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.colors
import com.example.myapp.ui.theme.customSpacing

/**
 * HeadlineWidget - A flexible composable for displaying a headline section with optional leading, middle, sub-middle, and trailing content.
 *
 * This widget is typically used at the top of screens or sections to provide a prominent title,
 * optional subtitle, and optional leading/trailing actions or icons.
 *
 * @param modifier Modifier to be applied to the Row container.
 * @param leading Optional string resource for leading text (e.g., section label).
 * @param leadingStr Optional direct string for leading text.
 * @param middleText Optional string resource for the main headline.
 * @param middleTextStr Optional direct string for the main headline.
 * @param subMiddleText Optional string resource for the sub-headline (subtitle).
 * @param subMiddleTextStr Optional direct string for the sub-headline.
 * @param trailing Optional composable for trailing content (e.g., action button or icon).
 * @param verticalAlignment Alignment for the row's vertical axis.
 *
 * Usage:
 * ```
 * HeadlineWidget(
 *     leading = R.string.section_label,
 *     middleText = R.string.headline,
 *     subMiddleText = R.string.subtitle,
 *     trailing = { Icon(...) }
 * )
 * ```
 */
@Composable
fun HeadlineWidget(
    modifier: Modifier = Modifier,
    @StringRes leading: Int? = null,
    leadingStr: String? = null,
    @StringRes middleText: Int? = null,
    middleTextStr: String? = null,
    @StringRes subMiddleText: Int? = null,
    subMiddleTextStr: String? = null,
    trailing: (@Composable () -> Unit)? = null,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically
) {

    val windowSizeConstant = LocalWindowSizeConstant.current

    PaddedSection {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.customSpacing.none),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = verticalAlignment
        ) {
            // Leading text or spacer
            val resolvedLeading = leading?.let { stringResource(it) } ?: leadingStr
            if (!resolvedLeading.isNullOrBlank()) {
                Text(
                    text = resolvedLeading,
                    style = windowSizeConstant.appHeadLineTextStyle,
                )
            } else {
                Spacer(modifier = Modifier.width(MaterialTheme.customSpacing.none))
            }

            // Middle section: main headline and optional sub-headline
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val resolvedMiddle = middleText?.let { stringResource(it) } ?: middleTextStr
                if (!resolvedMiddle.isNullOrBlank()) {
                    Text(
                        text = resolvedMiddle,
                        maxLines = 4,
                        style = windowSizeConstant.appHeadLineTextStyle,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                CustomSpacer(modifier = Modifier.height(MaterialTheme.customSpacing.smaller))

                val resolvedSubMiddle = subMiddleText?.let { stringResource(it) } ?: subMiddleTextStr
                if (!resolvedSubMiddle.isNullOrBlank()) {
                    Text(
                        text = resolvedSubMiddle,
                        style = windowSizeConstant.appSubHeadLineTextStyle,
                        color = MaterialTheme.colorScheme.outline,
                        textAlign = TextAlign.Center,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            // Trailing composable or spacer
            trailing?.invoke() ?: Spacer(modifier = Modifier.width(0.dp))
        }
    }
}


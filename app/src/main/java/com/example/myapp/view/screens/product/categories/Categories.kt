package com.example.myapp.view.screens.product.categories

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SettingsCell
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapp.R
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.CustomLazyRow
import com.example.myapp.view.components.custom.buttons.ButtonIconComposable
import com.example.myapp.view.utils.ButtonIcon
import kotlinx.coroutines.launch

/**
 * Categories - Composable function for displaying product categories
 *
 * This composable displays a horizontal list of categories, each with an icon and label.
 * Users can select a category, which highlights it and triggers a callback.
 *
 * @param modifier Modifier to be applied to the row
 * @param onCategoryClick Callback when a category is selected
 */
@Composable
fun Categories(
    modifier: Modifier = Modifier,
    onCategoryClick: (String) -> Unit = {}
) {
    val categories = listOf(
        CategoryItem(R.string.electronics_category, ButtonIcon.Vector(Icons.Default.ShoppingCart)),
        CategoryItem(R.string.pcs_and_accessories_category, ButtonIcon.Vector(Icons.Default.Movie)),
        CategoryItem(
            R.string.clothing_and_apparel_category,
            ButtonIcon.Vector(Icons.Default.Movie)
        ),
        CategoryItem(R.string.home_and_garden_category, ButtonIcon.Vector(Icons.Default.Movie)),
        CategoryItem(R.string.plumbing_and_hardware, ButtonIcon.Vector(Icons.Default.Movie)),
        CategoryItem(R.string.stationery_products_category, ButtonIcon.Vector(Icons.Default.Movie)),
        CategoryItem(R.string.girls_apparel_category, ButtonIcon.Vector(Icons.Default.Movie)),
        CategoryItem(R.string.boys_apparel_category, ButtonIcon.Vector(Icons.Default.Movie)),
        CategoryItem(R.string.home_and_garden_category, ButtonIcon.Vector(Icons.Default.Movie)),
    )

    val windowSizeConstant = LocalWindowSizeConstant.current

    // Set the first category as default selected (index 0)
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }

    CustomLazyRow{
        items(
            count = categories.size
        ) { index ->
            val category = categories[index]
            CategoryItemComposable(
                isSelected = selectedCategoryIndex == index, // Check if this index is selected
                category = category,
                onClick = {
                    selectedCategoryIndex = index // Update selected index
                    onCategoryClick(category.name.toString())
                }
            )
        }
    }
}

/**
 * CategoryItemComposable - Composable for displaying a single category item
 *
 * This composable displays a category with an icon and label.
 * It can be selected or deselected.
 *
 * @param modifier Modifier to be applied to the row
 * @param category The category data
 * @param onClick Callback when the category is clicked
 * @param isSelected Whether the category is currently selected
 */
@Composable
fun CategoryItemComposable(
    modifier: Modifier = Modifier,
    category: CategoryItem,
    onClick: () -> Unit = {},
    isSelected: Boolean
) {
    val windowSizeConstant = LocalWindowSizeConstant.current
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .clip(MaterialTheme.shapes.extraLarge)
            .background
                (
                if (isSelected)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(MaterialTheme.customSpacing.customNormal)
        ){
        Text(
            text = stringResource(category.name),
            style = windowSizeConstant.labelTextStyle,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
    }
}

data class CategoryItem(
    @StringRes val name: Int,
    val icon: ButtonIcon
)


package com.example.myapp.view.screens.bottom_bar

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.colors
import com.example.myapp.view.components.CustomLazyColumn
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.HeadlineWidget
import com.example.myapp.view.components.NavigationBarShimmer
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.ProfileCardShimmer
import com.example.myapp.view.components.custom.buttons.CustomButton
import kotlinx.coroutines.delay

data class UserProfile(
    val name: String,
    val location: String,
    val email: String,
    val phone: String,
    val joinDate: String,
)

/**
 * Profile screen that displays user's profile information and allows editing.
 * Shows user's avatar, name, contact info, and a sign-in button.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onSignInClick: () -> Unit = {}) {

    val windowSizeConstant = LocalWindowSizeConstant.current

    var isEditing by remember { mutableStateOf(false) }
    var profile by remember {
        mutableStateOf(
            UserProfile(
                name = "Grayson Comrade",
                location = "Mzuzu City, Malawi",
                email = "grayson@gmail.com",
                phone = "+265992629908",
                joinDate = "December 2025",
            )
        )
    }

    var editedProfile by remember { mutableStateOf(profile) }

    rememberScrollState()
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1500) // simulate initial loading delay
        loading = false
    }

    CustomScaffoldContainer(
        onRefresh = {
            // Add your refresh logic here
            println("Refreshing login screen...")
            // Example: reload user data, refresh tokens, etc.
        },
        showBottomBar = false,
        showTopBar = false,
        showBackArrow = false,
        content = {
            if (loading) {
                ProfileCardShimmer()
            } else {
                CustomLazyColumn {
                    item {
                        // Gradient Header
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primary,
                                            MaterialTheme.colorScheme.secondary,
                                            MaterialTheme.colorScheme.tertiary
                                        )
                                    )
                                )
                        )
                        PaddedSection {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                // Profile Card positioned higher
                                val pair = profileCard(profile, isEditing, editedProfile)
                                editedProfile = pair.first
                                isEditing = pair.second

                                //contact
                                Column(
                                    modifier = Modifier
                                        .padding(top = windowSizeConstant.adaptiveProfileVerticalSpacer),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    // Contact Info Section
                                    Column(
                                        modifier = windowSizeConstant.adaptiveWidthModifier
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        HeadlineWidget(middleText = R.string.contact_info)
                                        CustomSpacer()
                                        ContactInfoCard()
                                    }

                                    CustomSpacer()

                                    CustomButton(
                                        label = R.string.sign_in,
                                        onClick = {  onSignInClick()}
                                    )
                                    Spacer(modifier = Modifier.height(32.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

/**
 * Composable function for displaying the profile card.
 * Shows user's initials, name, and an edit button.
 * Returns a Pair of the edited profile and editing state.
 */
@Composable
private fun BoxScope.profileCard(
    profile: UserProfile,
    isEditing: Boolean,
    editedProfile: UserProfile
): Pair<UserProfile, Boolean> {
    val windowSizeConstant = LocalWindowSizeConstant.current

    var isEditing1 = isEditing
    var editedProfile1 = editedProfile

    ElevatedCard(
        modifier = Modifier
            .size(windowSizeConstant.profileCardPadding)
            .align(Alignment.TopCenter)
            .absoluteOffset(y = (-120).dp),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar circle with user initials
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary,
                                )
                            )
                        )
                        .clickable { /* Handle avatar click */ },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = profile.name.split(" ").map { it.first() }.joinToString(""),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp),
                        color = MaterialTheme.colors.white,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Display profile name
                Text(
                    text = stringResource(R.string.profile_name),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Edit icon at top-end of the card
            IconButton(
                onClick = {
                    isEditing1 = true
                    editedProfile1 = profile
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
    return Pair(editedProfile1, isEditing1)
}

/**
 * Composable function for displaying the user's contact information.
 * Shows email, phone, and location in a styled card.
 */
@Composable
fun ContactInfoCard(
    modifier: Modifier = Modifier,
    email: String = "graysoncomradetchumbu7@gmail.com",
    phone: String = "+265 992629908",
    location: String = "Northern Region, Mzuzu, Malawi",
) {

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceDim
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Contact Fields (Email, Phone, Location)
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ContactField(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = email
                )

                ContactField(
                    icon = Icons.Default.Phone,
                    label = "Phone",
                    value = phone
                )

                ContactField(
                    icon = Icons.Default.LocationOn,
                    label = "Location",
                    value = location
                )
            }
        }
    }
}

/**
 * Composable function for displaying a single contact field (icon, label, value).
 */
@Composable
fun ContactField(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Icon Container with Material 3 expressive styling
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(48.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Text Content for label and value
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

/**
 * Preview function for the ProfileScreen in night mode.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProfileScreenPreview() {
    var isEditing by remember { mutableStateOf(false) }
    var profile by remember {
        mutableStateOf(
            UserProfile(
                name = "Grayson Comrade",
                location = "Mzuzu City, Malawi",
                email = "grayson@gmail.com",
                phone = "+265992629908",
                joinDate = "December 2025",
            )
        )
    }
    val nav = rememberNavController()
    var editedProfile by remember { mutableStateOf(profile) }

    val scrollState = rememberScrollState()
    val headerAlpha by animateFloatAsState(
        targetValue = if (scrollState.value > 100) 0.9f else 0.3f,
        animationSpec = tween(300)
    )

    MyAppTheme(
        dynamicColor = false,
        content = {
            ProfileScreen()
        }
    )
}

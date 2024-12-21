package com.only.janitriassignment.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import com.only.janitriassignment.R
import com.only.janitriassignment.data.entity.ColorEntity
import com.only.janitriassignment.presentation.viewmodel.ColorViewModel
import com.only.janitriassignment.ui.theme.ButtonBackground
import com.only.janitriassignment.ui.theme.TopBar
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun First_Screen(
    modifier: Modifier,
    viewModel: ColorViewModel
) {

    val colors by viewModel.colors.collectAsState()
    val unsyncedCount by viewModel.unsyncedCount.collectAsState()
    val syncStatus by viewModel.syncStatus.collectAsState(initial = false)
    val context = LocalContext.current
    val isConnected = rememberSaveable { mutableStateOf(context.isNetworkAvailable()) }

    LaunchedEffect(Unit) {
        while (true) {
            isConnected.value = context.isNetworkAvailable()
            delay(2000)
        }
    }

    val view = LocalView.current
    val window = (view.context as Activity).window
    window.statusBarColor = TopBar.toArgb()
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false

    DisposableEffect(context) {
        context.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        onDispose {
            context.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    val inriaSansFamily = remember {
        FontFamily(
            Font(R.font.inriasans, FontWeight.Normal)
        )
    }

    var rotationState by rememberSaveable { mutableFloatStateOf(0f) }


    if (syncStatus) {
        Toast.makeText(context, "Data synced successfully!", Toast.LENGTH_SHORT).show()
    }

    Scaffold (
        topBar = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(TopBar),
            ) {
                Row(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp)
                        .background(TopBar),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    Box(
                        modifier = Modifier
                            .requiredSize(width = 130.dp, height = 40.dp)
                            .background(TopBar),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Color App",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontFamily = inriaSansFamily,
                            modifier = Modifier.wrapContentSize()
                        )
                    }

                    Box(
                        modifier = Modifier
                            .requiredSize(width = 64.dp, height = 40.dp)
                            .background(ButtonBackground, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = "$unsyncedCount",
                                fontSize = 20.sp,
                                color = Color.White,
                                fontFamily = inriaSansFamily
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            val rotation by animateFloatAsState(
                                targetValue = rotationState,
                                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
                                label = ""
                            )

                            Image(
                                modifier = Modifier
                                    .size(25.dp)
                                    .then(
                                        if (isConnected.value) {
                                            Modifier.clickable {
                                                viewModel.syncColors()
                                                rotationState += 360f
                                            }
                                        } else {
                                            Modifier.alpha(0.5f)
                                        }
                                    )
                                    .graphicsLayer(rotationZ = rotation),
                                painter = painterResource(R.drawable.refresh),
                                contentDescription = "Refresh"
                            )
                        }
                    }
                }
            }
        },
        content = { padding->
            Column(
                modifier = Modifier
                    .padding(top = 100.dp)
                    .background(Color.White)
            ) {
                Connection(isConnected)

                if(colors.isEmpty()){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "No colors added yet",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontFamily = inriaSansFamily
                        )
                    }

                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(colors) { color ->
                            Items(color, inriaSansFamily)
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.addColor() },
                interactionSource = remember { MutableInteractionSource() },
                contentColor = Color.White,
                containerColor = ButtonBackground,
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .width(123.dp)
                    .height(40.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Add Color",
                        color = TopBar,
                        fontSize = 18.sp,
                        fontFamily = inriaSansFamily
                    )

                    Spacer(
                        modifier = Modifier
                            .width(5.dp)
                    )

                    Image(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(R.drawable.add),
                        contentDescription = "Add"
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.EndOverlay
    )
}

@Composable
fun Items(color: ColorEntity, inriaSansFamily: FontFamily) {

    val formattedDate = remember(color.time) {
        val date = Date(color.time)
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(Color(color.color.toColorInt()), shape = RoundedCornerShape(10.dp))
            .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp)
                .drawBehind {
                    val textWidth = size.width
                    drawLine(
                        color = Color.White,
                        start = Offset(0f, size.height),
                        end = Offset(textWidth, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                },
            text = color.color,
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = inriaSansFamily
        )

        Spacer(
            modifier = Modifier
                .height(12.dp)
        )

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Created at",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = inriaSansFamily
            )

            Text(
                text = formattedDate,
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = inriaSansFamily
            )
        }
    }
}

@Composable
fun Connection(isConnected: MutableState<Boolean>) {
    if (!isConnected.value) {
        AnimatedVisibility(
            visible = !isConnected.value,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(Color.Black, Color.Red)
                    )
                )
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Offline",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {

        AnimatedVisibility(
            visible = false,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(Color.Black, Color.Green)
                    )
                )
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Online",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun Context.requireActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("No activity was present but it is required.")
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
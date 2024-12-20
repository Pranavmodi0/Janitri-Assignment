package com.only.janitriassignment.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.only.janitriassignment.R
import com.only.janitriassignment.ui.theme.ButtonBackground
import com.only.janitriassignment.ui.theme.TopBar

@Composable
fun First_Screen(
    modifier: Modifier
) {

    val inriaSansFamily = FontFamily(
        Font(R.font.inriasans, FontWeight.Normal)
    )

    Scaffold (
        topBar = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(top = 5.dp)
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
                            .requiredSize(width = 71.dp, height = 40.dp)
                            .background(ButtonBackground, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = "12",
                                fontSize = 20.sp,
                                color = Color.White,
                                fontFamily = inriaSansFamily
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Image(
                                painter = painterResource(R.drawable.refresh),
                                contentDescription = "Refresh",
                                modifier = Modifier.size(25.dp)
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

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(5) {count->
                        Items(count, inriaSansFamily)
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
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
fun Items(count: Int, inriaSansFamily: FontFamily) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(Color.Black, shape = RoundedCornerShape(10.dp))
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
            text = "#FFAABB$count",
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
                text = "12/05/2023",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = inriaSansFamily
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun First_ScreenPreview() {
    First_Screen(
        modifier = Modifier
    )
}
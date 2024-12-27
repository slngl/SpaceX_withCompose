package com.slngl.spacexwithcompose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.slngl.spacexcompose.model.HistoryListItem
import com.slngl.spacexwithcompose.viewmodel.HistoryListViewModel

@Composable
fun HistoryListScreen(
    navController: NavController,
    viewModel: HistoryListViewModel = hiltViewModel(),
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            Text(
                "SpaceX History",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                textAlign = TextAlign.Center,
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(10.dp))

            SearchBar(
                hint = "Search...",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
            ) {
                viewModel.searchHistoryList(it)
            }

            Spacer(modifier = Modifier.height(10.dp))

            val histories by remember { viewModel.historyList }
            val errorMsg by remember { viewModel.errorMessage }
            val isLoading by remember { viewModel.isLoading }

            Box(modifier = Modifier.fillMaxSize()) {
                HistoryListView(histories, navController)

                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                if (errorMsg.isNotEmpty()) {
                    RetryView(errorMsg) {
                        viewModel.loadHistories()
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {},
) {
    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Blue),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .shadow(5.dp, CircleShape)
                    .background(Color.White, CircleShape)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .onFocusChanged {
                        isHintDisplayed = it.isFocused != true && text.isEmpty()
                    },
        )

        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            )
        }
    }
}

@Composable
fun HistoryRow(
    navController: NavController,
    historyListItem: HistoryListItem,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
                .clickable {
                    navController.navigate("history_detail_screen/${historyListItem.id}")
                }.padding(8.dp),
    ) {
        Text(
            text = historyListItem.title ?: "HISTORY DATA",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = historyListItem.details ?: "DETAILS",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Composable
fun HistoryListView(
    histories: List<HistoryListItem>?,
    navController: NavController,
) {
    histories?.let {
        LazyColumn(
            contentPadding = PaddingValues(5.dp),
        ) {
            items(histories.size) { index ->
                val historyListItem = histories[index]
                HistoryRow(navController, historyListItem)
            }
        }
    } ?: run {
        Text(
            text = "No history available",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Composable
fun RetryView(
    error: String,
    onRetry: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(error, color = MaterialTheme.colorScheme.error, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text("RETRY")
        }
    }
}

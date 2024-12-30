package com.slngl.spacexwithcompose.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.slngl.spacexcompose.model.HistoryData
import com.slngl.spacexwithcompose.util.Resource
import com.slngl.spacexwithcompose.viewmodel.HistoryDetailViewModel

@Composable
fun HistoryDetailScreen(
    id: Int,
    viewModel: HistoryDetailViewModel = hiltViewModel(),
) {
    val historyItem by produceState<Resource<HistoryData>>(initialValue = Resource.Loading()) {
        value = viewModel.getHistoryDetail(id)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
        ) {
            when (historyItem) {
                is Resource.Success -> {
                    val data = (historyItem as Resource.Success).data

                    // Event Title
                    Text(
                        text = data?.title ?: "No Title",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp, top = 20.dp),
                    )

                    // Details
                    Text(
                        text = data?.details ?: "No Details",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 16.dp),
                    )

                    // Links Section
                    data?.links?.let { links ->
                        Text(
                            text = "Related Links",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp),
                        )

                        // Wikipedia Link
                        links.wikipedia?.let { wikipediaUrl ->
                            LinkText(
                                text = "Wikipedia",
                                url = wikipediaUrl,
                                modifier = Modifier.padding(bottom = 8.dp),
                            )
                        }

                        // Article Link
                        links.article?.let { articleUrl ->
                            LinkText(
                                text = "Article",
                                url = articleUrl,
                                modifier = Modifier.padding(bottom = 8.dp),
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    val message = (historyItem as Resource.Error).message ?: "Unknown Error"
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displayLarge,
                    )
                }
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@Composable
fun LinkText(
    text: String,
    url: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Text(
        text = text,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodyLarge,
        modifier =
            modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            },
    )
}

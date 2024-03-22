package com.giannism13.movieflix.detailsScreen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giannism13.movieflix.ktorClient.responses.Review
import com.giannism13.movieflix.ui.theme.MovieFlixTheme

@Composable
fun ReviewItem(review: Review) {
	Surface(color = MaterialTheme.colorScheme.primaryContainer) {
		Column(modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp)){
			val firstRow = buildAnnotatedString {
				withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
					append(review.author)
				}
				withStyle(SpanStyle(fontWeight = FontWeight.ExtraLight)) {
					append(" - ${review.createdAt}")
				}
			}
			Text(firstRow, modifier = Modifier.padding(bottom = 10.dp))
			Text(review.content, overflow = TextOverflow.Visible, textAlign = TextAlign.Start)
		}
	}
}

@Composable
@Preview
private fun ReviewItemPreview() {
	MovieFlixTheme {
		ReviewItem(Review("Author", "buildAnnotatedStringbuildAnnotatedStringbuildAnnotatedStringbuildAnnotatedStringbuildAnnotatedStringbuildAnnotatedStringbuildAnnotatedStringbuildAnnotatedStringbuildAnnotatedStringbuildAnnotatedStringbuildAnnotatedStringbuildAnnotatedStringbuildAnnotatedStringbuildAnnotatedStringbuildAnnotatedString", "Date"))
	}
}
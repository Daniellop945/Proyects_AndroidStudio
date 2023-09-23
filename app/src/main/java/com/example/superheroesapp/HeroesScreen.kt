package com.example.superheroesapp

import android.R
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superheroesapp.data.HeroesRepository
import com.example.superheroesapp.model.Hero
import com.example.superheroesapp.ui.theme.SuperHeroesAppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HeroList(
    heroes: List<Hero>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    
    AnimatedVisibility(
        visible =  true,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyColumn(contentPadding = contentPadding){
            itemsIndexed(heroes){index, hero ->  
                HeroListItem(
                    hero = hero,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = StiffnessVeryLow,
                                    dampingRatio = DampingRatioLowBouncy
                                ),
                                initialOffsetY = { it * (index + 1) }
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun HeroListItem(
    hero:Hero,
    modifier: Modifier = Modifier
){
    Card (
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ){
        Row (
            modifier = modifier
                .fillMaxWidth()
                .padding(32.dp)
                .sizeIn(minHeight = 72.dp)
        ){
            Column(modifier = Modifier.weight(1f)) {
                Text(
                   text = stringResource(hero.nameRes),
                   style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = stringResource(hero.descriptionRes),
                    style = MaterialTheme.typography.bodyLarge
                )
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp))
                ){
                    Image(
                        painter = painterResource(hero.imageRes),
                        contentDescription = null,
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
}

@Preview("Heroes List")
@Composable
fun HeroesPreview(){
    SuperHeroesAppTheme (darkTheme = false){
        Surface(
            color = MaterialTheme.colorScheme.background
        ){
            HeroList(heroes = HeroesRepository.heroes)
        }
    }
}
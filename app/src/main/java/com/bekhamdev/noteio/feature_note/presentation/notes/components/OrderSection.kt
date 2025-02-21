package com.bekhamdev.noteio.feature_note.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.bekhamdev.noteio.core.feature_note.domain.util.NoteOrder
import com.bekhamdev.noteio.core.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val screenWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
        val dropdownWidth = 200.dp

        DropdownMenu(
            modifier = Modifier
                .width(dropdownWidth)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            offset = DpOffset(
                x = screenWidth - 200.dp,
                y = 0.dp
            )
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        "Sort By",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.outline
                        )
                    )
                },
                onClick = {},
                enabled = false
            )

            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = noteOrder is NoteOrder.Title,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                unselectedColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                        Text(
                            "Title",
                            modifier = Modifier.padding(start = 12.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }
                },
                onClick = {
                    onOrderChange(NoteOrder.Title(noteOrder.currentOrderType()))
                    onExpandedChange(false)
                }
            )

            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = noteOrder is NoteOrder.Date,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                unselectedColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                        Text(
                            "Date",
                            modifier = Modifier.padding(start = 12.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }
                },
                onClick = {
                    onOrderChange(
                        NoteOrder.Date(noteOrder.currentOrderType())
                    )
                    onExpandedChange(false)
                }
            )

            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = noteOrder is NoteOrder.Color,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                unselectedColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                        Text(
                            "Color", modifier = Modifier.padding(start = 12.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }
                },
                onClick = {
                    onOrderChange(NoteOrder.Color(noteOrder.currentOrderType()))
                    onExpandedChange(false)
                }
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

            DropdownMenuItem(
                text = {
                    Text(
                        text = "Order",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.outline
                        )
                    )
                },
                onClick = {},
                enabled = false
            )

            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = noteOrder.currentOrderType() == OrderType.Ascending,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                unselectedColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                        Text(
                            "Ascending", modifier = Modifier.padding(start = 12.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }
                },
                onClick = {
                    onOrderChange(noteOrder.copyWithOrderType(OrderType.Ascending))
                    onExpandedChange(false)
                }
            )

            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = noteOrder.currentOrderType() == OrderType.Descending,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                unselectedColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                        Text(
                            "Descending", modifier = Modifier.padding(start = 12.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }
                },
                onClick = {
                    onOrderChange(noteOrder.copyWithOrderType(OrderType.Descending))
                    onExpandedChange(false)
                }
            )
        }
    }
}
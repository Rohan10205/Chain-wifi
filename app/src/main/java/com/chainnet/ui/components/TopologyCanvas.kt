package com.chainnet.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.chainnet.domain.model.TopologyGraph
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TopologyCanvas(graph: TopologyGraph, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        if (graph.nodes.isEmpty()) return@Canvas
        val radius = size.minDimension * 0.3f
        val center = Offset(size.width / 2f, size.height / 2f)
        val nodePositions = graph.nodes.mapIndexed { index, node ->
            val angle = (2 * Math.PI * index / graph.nodes.size).toFloat()
            val position = Offset(
                (center.x + radius * cos(angle)),
                (center.y + radius * sin(angle))
            )
            node.identity.id to position
        }.toMap()

        graph.edges.forEach { edge ->
            val from = nodePositions[edge.fromNodeId] ?: center
            val to = nodePositions[edge.toNodeId] ?: center
            drawLine(color = Color.Gray, start = from, end = to, strokeWidth = 4f)
        }

        graph.nodes.forEach { node ->
            val position = nodePositions[node.identity.id] ?: center
            drawCircle(color = Color(0xFF4CAF50), radius = 18f, center = position)
        }
    }
}

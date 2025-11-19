package com.yaquobi.arkitektura2.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import dev.romainguy.kotlin.math.Float3
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode

object Utils {
    val colleges = mapOf(

        "CCIS" to "CCIS_Final_Model_v1.glb",
        "CCIS Lobby" to "CCIS_Lobby_v2.glb",
        "CCIS Room A" to "CCIS_RoomA_v1.glb",
        "CCIS Room B" to "CCIS_RoomB_v1.glb",
        "CCIS Room C" to "CCIS_RoomC_v1.glb",
        "CCIS Room D" to "CCIS_RoomD_v1.glb",
        "CCIS Hyflex 1" to "CCIS_Hyflex1_v1.glb",
        "CCIS Hyflex 2" to "CCIS_Hyflex2_v1.glb",
        "CCIS Lab Room 1" to "CCIS_LabRm1_v1.glb",
        "CCIS Lab Room 2" to "CCIS_LabRm2_v1.glb",
        "CCIS Lab Room 3" to "CCIS_LabRm3_v1.glb",
        "CCIS Lab Room 4" to "CCIS_LabRm4_v1.glb",
        "CCIS Lab Room 5" to "CCIS_LabRm5_v1.glb",
        "CCIS COD Room" to "CCIS_COD_v1.glb",
        "CCIS DCS" to "CCIS_DCS_v1.glb",
        "CCIS DIT" to "CCIS_DIT_v1.glb",
    )


    fun getModelForCollege(college: String): String {
        val modelName = colleges[college] ?: error("Model not found")
        return "models/$modelName"
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun createAnchorNode(
        engine: Engine,
        modelLoader: ModelLoader,
        materialLoader: MaterialLoader,
        modelInstance: MutableList<ModelInstance>,
        anchor: Anchor,
        model: String
    ): AnchorNode {
        val anchorNode = AnchorNode(engine = engine, anchor = anchor)
        val modelNode = ModelNode(
            modelInstance = modelInstance.apply {
                if (isEmpty()) {
                    this += modelLoader.createInstancedModel(model, 10)
                }
            }.removeLast(),
            scaleToUnits = 0.8f
        ).apply {
            isEditable = true

            onEditingChanged = { transforms ->
                val minScale = 5.0f
                val maxScale = 1.0f

                // Clamp current scale
                val clamped = scale.clamp(minScale, maxScale)
                if (clamped != scale) {
                    scale = clamped
                }
            }
        }
        val boundingBox = CubeNode(
            engine = engine,
            size = modelNode.extents,
            center = modelNode.center,
            materialInstance = materialLoader.createColorInstance(Color.White)
        ).apply {
            isVisible = false
        }
        modelNode.addChildNode(boundingBox)
        anchorNode.addChildNode(modelNode)
        listOf(modelNode, anchorNode).forEach {
            it.onEditingChanged = { editingTransforms ->
                boundingBox.isVisible = editingTransforms.isNotEmpty()
            }
        }
        return anchorNode

    }

    fun Float3.clamp(min: Float, max: Float): Float3 {
        return Float3(
            x.coerceIn(min, max),
            y.coerceIn(min, max),
            z.coerceIn(min, max)
        )
    }
}
package com.yaquobi.arkitektura2.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Plane
import com.google.ar.core.TrackingFailureReason
import com.yaquobi.arkitektura2.util.Utils
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.getUpdatedPlanes
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberView

private const val TAG = "CCISModelScreen"

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun CCISModelScreen(navController: NavController, modelKey: String) {

    Box(modifier = Modifier.fillMaxSize()) {

        // 🔹 Model selection state (Pair of key & .glb path)
        val model = remember { mutableStateOf(Pair(modelKey, Utils.getModelForCollege(modelKey))) }

        // 🔹 Dropdown state
        val colleges = mapOf(
            "CCIS" to "CCIS_Final_Model_w_Lobby.glb",
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
        var expanded by remember { mutableStateOf(false) }
        var selectedKey by remember { mutableStateOf(modelKey) }

        // 🔹 Scene setup
        val engine = rememberEngine()
        val modelLoader = rememberModelLoader(engine = engine)
        val materialLoader = rememberMaterialLoader(engine = engine)
        val cameraNode = rememberARCameraNode(engine = engine)
        val childNodes = rememberNodes()
        val view = rememberView(engine = engine)
        val collisionSystem = rememberCollisionSystem(view = view)
        val planeRenderer = remember { mutableStateOf(true) }
        val modelInstance = remember { mutableListOf<ModelInstance>() }
        val trackingFailureReason = remember { mutableStateOf<TrackingFailureReason?>(null) }
        val frame = remember { mutableStateOf<Frame?>(null) }

        // 🔹 AR Scene View
        ARScene(
            modifier = Modifier.fillMaxSize(),
            childNodes = childNodes,
            engine = engine,
            view = view,
            modelLoader = modelLoader,
            collisionSystem = collisionSystem,
            planeRenderer = planeRenderer.value,
            cameraNode = cameraNode,
            materialLoader = materialLoader,
            onTrackingFailureChanged = { trackingFailureReason.value = it },
            onSessionUpdated = { _, updatedFrame ->
                frame.value = updatedFrame
                // Place the first model when AR starts detecting a plane
                if (childNodes.isEmpty()) {
                    try {
                        updatedFrame.getUpdatedPlanes()
                            .firstOrNull { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING }
                            ?.let { plane ->
                                plane.createAnchorOrNull(plane.centerPose)?.let { anchor ->
                                    val node = Utils.createAnchorNode(
                                        engine = engine,
                                        anchor = anchor,
                                        modelLoader = modelLoader,
                                        materialLoader = materialLoader,
                                        modelInstance = modelInstance,
                                        model = model.value.second
                                    )
                                    childNodes += node
                                }
                            }
                    } catch (ex: Exception) {
                        Log.e(TAG, "Error creating anchor/node: ${ex.message}", ex)
                    }
                }
            },
            sessionConfiguration = { session, config ->
                config.depthMode = if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC))
                    Config.DepthMode.AUTOMATIC else Config.DepthMode.DISABLED
                config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
            }
        )

        // 🆕 DROPDOWN MENU (Top of the screen)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .background(
                    color = Color(android.graphics.Color.parseColor("#21224A")),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedKey,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Model", color = Color.White) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White
                    ),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    colleges.keys.forEach { key ->
                        DropdownMenuItem(
                            text = { Text(key) },
                            onClick = {
                                selectedKey = key
                                expanded = false

                                model.value = key to Utils.getModelForCollege(key)

                                // Resets Models🥰
                                childNodes.clear()
                                modelInstance.clear()
                                frame.value = null
                            }
                        )
                    }
                }
            }
        }

        // 🆕 RELOAD MODEL WHEN SELECTION CHANGES
        LaunchedEffect(model.value) {
            if (frame.value != null && model.value.second.isNotEmpty()) {
                try {
                    childNodes.clear()
                    frame.value?.getUpdatedPlanes()
                        ?.firstOrNull { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING }
                        ?.let { plane ->
                            plane.createAnchorOrNull(plane.centerPose)?.let { anchor ->
                                val node = Utils.createAnchorNode(
                                    engine = engine,
                                    anchor = anchor,
                                    modelLoader = modelLoader,
                                    materialLoader = materialLoader,
                                    modelInstance = modelInstance,
                                    model = model.value.second
                                )
                                childNodes += node
                            }
                        }
                } catch (ex: Exception) {
                    Log.e(TAG, "Error loading new model: ${ex.message}", ex)
                }
            }
        }

        // 🔹 Footer label at bottom center
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .background(
                    color = Color(android.graphics.Color.parseColor("#21224A")),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = selectedKey,
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

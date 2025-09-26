package com.yaquobi.arkitektura2.screen

import android.os.Build
import android.view.MotionEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yaquobi.arkitektura2.util.Utils
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Plane
import com.google.ar.core.TrackingFailureReason
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
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import org.w3c.dom.Text

private const val TAG = "CCISModelScreen"

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun CCISModelScreen(navController: NavController, modelKey: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Make a Pair like QuizScreen: (label, modelPath)
        val model = remember {
            mutableStateOf(Pair(modelKey, Utils.getModelForCollege(modelKey)))
        }

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
            onTrackingFailureChanged = {
                trackingFailureReason.value = it
            },
            onSessionUpdated = { _, updatedFrame ->
                frame.value = updatedFrame
                // Mirror QuizScreen logic: place model once when no child nodes
                if (childNodes.isEmpty()) {
                    try {
                        updatedFrame.getUpdatedPlanes()
                            .firstOrNull { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING }
                            ?.let { plane ->
                                plane.createAnchorOrNull(plane.centerPose)
                                    ?.let { anchor ->
                                        // createAnchorNode returns an AnchorNode (your util)
                                        val node = Utils.createAnchorNode(
                                            engine = engine,
                                            anchor = anchor,
                                            modelLoader = modelLoader,
                                            materialLoader = materialLoader,
                                            modelInstance = modelInstance,
                                            model = model.value.second // path string
                                        )
                                        childNodes += node
                                    } ?: Log.w(TAG, "createAnchorOrNull returned null for plane ${plane}")
                            }
                    } catch (ex: Exception) {
                        Log.e(TAG, "Error while creating anchor/node: ${ex.message}", ex)
                    }
                }
            },
            sessionConfiguration = { session, config ->
                config.depthMode = when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                    true -> Config.DepthMode.AUTOMATIC
                    else -> Config.DepthMode.DISABLED
                }
                config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
            }
        )

            // Simple header
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "CCIS Model",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 24.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}
